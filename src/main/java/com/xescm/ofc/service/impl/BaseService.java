package com.xescm.ofc.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.core.constant.UamConstant;
import com.xescm.core.exception.BusinessException;
import com.xescm.core.utils.ContextTypeUtils;
import com.xescm.core.utils.PubUtils;
import com.xescm.core.utils.PublicUtil;
import com.xescm.core.utils.ThreadLocalMap;
import com.xescm.ofc.config.OSSConfigureConfig;
import com.xescm.ofc.domain.OfcAttachment;
import com.xescm.ofc.enums.SerialNoEnum;
import com.xescm.ofc.service.IService;
import org.apache.commons.lang.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import tk.mybatis.mapper.common.Mapper;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class BaseService<T> implements IService<T> {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String OFC_ENV = "OFC";

	@Autowired
	private StringRedisTemplate rt;

	@Autowired
	private OSSConfigureConfig ossConfigure;

	@Autowired
	protected Mapper<T> mapper;

	public Mapper<T> getMapper() {
        return mapper;
    }
	
	@Override
	public List<T> select(T record) {
		return mapper.select(record);
	}

	@Override
	public T selectByKey(Object key) {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<T> selectAll() {
		return mapper.selectAll();
	}

	@Override
	public T selectOne(T record) {
		return mapper.selectOne(record);
	}

	@Override
	public int selectCount(T record) {
		return mapper.selectCount(record);
	}

	@Override
	public List<T> selectByExample(Object example) {
		return mapper.selectByExample(example);
	}

	@Override
	public int save(T record) {
		return mapper.insertSelective(record);
	}

	@Override
	public int update(T entity) {
		return mapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public int delete(T record) {
		return mapper.delete(record);
	}

	@Override
	public int deleteByKey(Object key) {
		return mapper.deleteByPrimaryKey(key);
	}
	
	@Override
	public int batchDelete(List<T> list) {
		int result = 0;
		for (T record : list) {
			int count = mapper.delete(record);
			if(count < 1){
				throw new RuntimeException("插入数据失败!");
			}
			result += count;
		}
		return result;
	}

	@Override
	public int selectCountByExample(Object example) {
		mapper.selectCountByExample(example);
		return 0;
	}

	@Override
	public int updateByExample(T record, Object example) {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int deleteByExample(Object example) {
		return mapper.deleteByPrimaryKey(example);
	}

	@Override
	public List<T> selectByRowBounds(T record, RowBounds rowBounds) {
		return mapper.selectByRowBounds(record, rowBounds);
	}

	@Override
	public List<T> selectByExampleAndRowBounds(Object example, RowBounds rowBounds) {
		return mapper.selectByExampleAndRowBounds(example, rowBounds);
	}

	protected AuthResDto getAuthResDtoByToken(){
		AuthResDto authResDto = (AuthResDto) ThreadLocalMap.get(UamConstant.TOKEN_AUTH_DTO);
		if(PublicUtil.isEmpty(authResDto)){
			throw new BusinessException("验证token失败");
		}
		return authResDto;
	}

	/**
	 * 从Redis上获取最新的编码
	 * @param envLock 环境前缀(如分拣系统=DMS)
	 * @param prefix 单号前缀（订单=OD）
	 * @param prefixLen 流水号长度
	 * @return
	 */
	protected String getNewCode(String envLock, String prefix, int prefixLen){
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		String dateStr = df.format(new Date());
		String key = envLock + prefix + dateStr;
		String valuePrefix = prefix +dateStr;
		Long newValue = 1L;
		try {
			synchronized (this) {
				ValueOperations<String, String> ops  = rt.opsForValue();
				if (!rt.hasKey(key)) {
					ops.set(key, String.valueOf(1L));
					rt.expire(key, 1L, TimeUnit.DAYS);
				} else {
					newValue = ops.increment(key, 1L);
				}
			}
		} catch (Exception ex) {
			logger.error("Redis生成序列号发生错误！", ex);
		}
		String zeroStr = "";
		// 补零处理
		if (!PubUtils.isNull(newValue) && prefixLen > 0 && String.valueOf(newValue).length() < prefixLen) {
			for (int i = 0; i < prefixLen - String.valueOf(newValue).length(); i++) {
				zeroStr += "0";
			}
		}
		return valuePrefix + zeroStr + newValue;
	}

	protected String getNewWaterCode(String prefix, int prefisLen) {
		return getNewCode(OFC_ENV, prefix, prefisLen);
	}

	@Override
	public URL getFileURL(String filePath) {
		logger.info("==> 开始获取文件路径...");
		logger.info("==> 获取文件路径 filePath = {}", filePath);
		// 生成URL签名(HTTP GET请求)
		URL signedUrl = null;
		OSSClient ossServer = new OSSClient(ossConfigure.getOutEndpoint(), ossConfigure.getAccessKeyId(), ossConfigure.getAccessKeySecret());
		boolean flag = ossServer.doesObjectExist(ossConfigure.getBucketName(), filePath);
		if (flag) {
			Date expiration = DateUtils.addHours(new Date(), ossConfigure.getDelayHour());
			GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(ossConfigure.getBucketName(), filePath, HttpMethod.GET);
			//设置过期时间
			request.setExpiration(expiration);
			try {
				signedUrl = ossServer.generatePresignedUrl(request);
			} catch (ClientException e) {
				logger.error("OFC生成URL签名失败={}", e.getMessage(), e);
			}
		}
		logger.info("==> 获取文件路径 filePath = {}", filePath);
		return signedUrl;
	}

	@Override
	public OfcAttachment uploadFile(InputStream inputStream, String fileName, String ossFilePath) {
		logger.info("==> 开始上传文件...");
		logger.info("==> 上传文件 fileName = {}", fileName);
		logger.info("==> 上传文件 ossFilePath = {}", ossFilePath);
		OfcAttachment attachment = null;
		String serialNo = null;
		if (PubUtils.isNull(ossFilePath, fileName, ossFilePath)) {
			logger.error("服务器路路径没有配置");
			throw new BusinessException("服务器路路径没有配置");
		}
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
		if(PublicUtil.isEmpty(fileType)){
			throw new BusinessException("上传文件类型错误");
		}
		String contentType = ContextTypeUtils.contentType(fileType);
		if(contentType == null){
			throw new BusinessException("上传文件格式错误");
		}
		try {
			OSSClient ossClient = new OSSClient(ossConfigure.getEndpoint(), ossConfigure.getAccessKeyId(), ossConfigure.getAccessKeySecret());
			ossFilePath = ossFilePath.substring(0, ossFilePath.length()).replaceAll("\\\\", "/") + "/";
			//创建上传Object的Metadata
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(inputStream.available());
			objectMetadata.setCacheControl("no-cache");
			objectMetadata.setHeader("Pragma", "no-cache");
			objectMetadata.setContentType(contentType);
			serialNo = getNewWaterCode(SerialNoEnum.AD.getType(), SerialNoEnum.AD.getLength());
			fileName = serialNo + PubUtils.UNDER_LINE + fileName;
			objectMetadata.setContentDisposition("inline;filename=" + fileName);
			//上传文件
			ossClient.putObject(ossConfigure.getBucketName(), ossFilePath + fileName, inputStream, objectMetadata);
			attachment = new OfcAttachment();
			attachment.setName(fileName);
			attachment.setSerialNo(serialNo);
			attachment.setType(fileType);
			attachment.setPath(ossFilePath);
			logger.info("==> 上传文件成功 fileName={}", ossFilePath + fileName);
		} catch (Exception e) {
			logger.error("==> 上传文件失败={}", e.getMessage(), e);
		}
		return attachment;
	}
}
