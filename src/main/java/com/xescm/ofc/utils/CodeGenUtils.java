/*
package com.xescm.ofc.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

*/
/**
 * Created by Ptrk on 2015/11/25.
 *//*

@Component
public class CodeGenUtils {
	public static final String OFC_ENV = "OFC";
	@Autowired
	private StringRedisTemplate rt;


	*/
/**
	 * 从Redis上获取最新的编码
	 * @param envLock 环境前缀(如中视购物=ZSGW)
	 * @param prefix 单号前缀（订单=OD）
	 * @param prefixLen 流水号长度
	 * @return
	 *//*

	public String getNewCode(String envLock, String prefix, int prefixLen){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String dateStr = df.format(new Date());
		dateStr = dateStr.substring(2);
		String key = envLock+ prefix +dateStr;
		String valuePrefix = prefix +dateStr;
		ValueOperations<String,String> ops  = rt.opsForValue();
		Long newValue =1L;
		if(!rt.hasKey(key)){
			ops.set(key,String.valueOf(1L));
			rt.expire(key,1L,TimeUnit.DAYS); //一天过期
		}else{
			newValue = ops.increment(key,1L);
		}
		String zeroStr = "";
		//补零处理
		if(!PubUtils.isNull(newValue) && prefixLen >0 && String.valueOf(newValue).length()< prefixLen){
			for (int i = 0; i < prefixLen -String.valueOf(newValue).length(); i++) {
				zeroStr+="0";
			}
		}
		return valuePrefix+zeroStr+newValue;
	}

}
*/
