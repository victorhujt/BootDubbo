package com.xescm.ofc.utils;

import com.xescm.core.utils.PubUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.xescm.ofc.constant.OrderConstConstant.OFC_ENV;

/**
 * Created by Ptrk on 2015/11/25.
 */
@Component
public class CodeGenUtils {

	private Logger logger = LoggerFactory.getLogger(CodeGenUtils.class);

	@Autowired
	private StringRedisTemplate rt;

	public String getNewWaterCode(String prefix,int prefisLen){
		return getNewCode(OFC_ENV,prefix,prefisLen);
	}
	/**
	 * 从Redis上获取最新的编码
	 * @param envLock 环境前缀(如中视购物=ZSGW)
	 * @param prefix 单号前缀（订单=OD）
	 * @param prefixLen 流水号长度
	 * @return
	 */
	public String getNewCode(String envLock, String prefix, int prefixLen){
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		String dateStr = df.format(new Date());
//		dateStr = dateStr.substring(2);
		String key = envLock+ prefix +dateStr;
		String valuePrefix = prefix +dateStr;
		Long newValue =1L;
		try {
			synchronized (this) {
				ValueOperations<String,String> ops  = rt.opsForValue();
				if (!rt.hasKey(key)) {
					ops.set(key, String.valueOf(1L));
					rt.expire(key, 1L, TimeUnit.DAYS); //一天过期
				} else {
					newValue = ops.increment(key, 1L);
				}
			}
		} catch (Exception ex) {
			logger.error("Redis生成单号发生错误！", ex);
		}
		String zeroStr = "";
		//补零处理
		if (!PubUtils.isNull(newValue) && prefixLen > 0 && String.valueOf(newValue).length() < prefixLen) {
			for (int i = 0; i < prefixLen - String.valueOf(newValue).length(); i++) {
				zeroStr += "0";
			}
		}
		return valuePrefix+zeroStr+newValue;
	}

	public String getSmsCode(int size){
		Random random = new Random();// 创建
		String s = "0123456789";
		String validateCode = "";
		for (int i = 0; i < size; i++) {
			String ch = String.valueOf(s.charAt(random.nextInt(s.length())));
			validateCode += ch;
		}
		return validateCode;
	}
}
