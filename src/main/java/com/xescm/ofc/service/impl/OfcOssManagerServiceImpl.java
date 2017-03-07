package com.xescm.ofc.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.xescm.ofc.config.OSSConfigureConfig;
import com.xescm.ofc.service.OfcOssManagerService;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URL;
import java.util.Date;

/**
 *
 * Created by hujintao on 2016/12/18.
 */
@Service
public class OfcOssManagerServiceImpl implements OfcOssManagerService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OSSConfigureConfig ossConfigure;
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
                logger.error("生成URL签名失败={}", e.getMessage(), e);
            }
        }
        logger.info("==> 获取文件路径 filePath = {}", filePath);
        return signedUrl;
    }

    @Override
    public void deleteFile(String filePath) {
        logger.info("==> 开始删除文件...");
        logger.info("==> 删除文件 ossConfigure = {}", ossConfigure);
        logger.info("==> 删除文件 filePath = {}", filePath);
        OSSClient ossClient = new OSSClient(ossConfigure.getEndpoint(), ossConfigure.getAccessKeyId(), ossConfigure.getAccessKeySecret());
        ossClient.deleteObject(ossConfigure.getBucketName(), filePath);
        logger.info("==> 删除文件成功 filePath = {}", filePath);
    }

}

