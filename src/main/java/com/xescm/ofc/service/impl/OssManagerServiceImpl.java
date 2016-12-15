package com.xescm.ofc.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.xescm.ofc.config.OSSConfigureConfig;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.OssManagerService;
import com.xescm.ofc.utils.PubUtils;
import com.xescm.uam.utils.ContextTypeUtils;
import com.xescm.uam.utils.PublicUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created by hujintao on 2016/12/14.
 */
@Service
public class OssManagerServiceImpl implements OssManagerService {

    private static Logger logger = LoggerFactory.getLogger(OssManagerServiceImpl.class);

    @Autowired
    private OSSConfigureConfig ossConfigure;
    @Override
    public void uploadFile(InputStream inputStream, String fileName, String ossFilePath) {
        logger.debug("==> 开始上传文件...");
        logger.debug("==> 上传文件 fileName = {}", fileName);
        logger.debug("==> 上传文件 ossFilePath = {}", ossFilePath);
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
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            ossClient.putObject(ossConfigure.getBucketName(), ossFilePath + fileName, inputStream, objectMetadata);
            logger.debug("==> 上传文件成功 fileName={}", ossFilePath + fileName);
        } catch (Exception e) {
            logger.error("==> 上传文件失败={}", e.getMessage(), e);
        }
    }
}
