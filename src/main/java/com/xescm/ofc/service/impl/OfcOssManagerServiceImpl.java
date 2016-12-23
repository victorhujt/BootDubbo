package com.xescm.ofc.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.xescm.ofc.config.OSSConfigureConfig;
import com.xescm.ofc.domain.OfcAttachment;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.OfcAttachmentService;
import com.xescm.ofc.service.OfcOssManagerService;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Date;

/**
 * Created by hujintao on 2016/12/18.
 */
@Service
public class OfcOssManagerServiceImpl implements OfcOssManagerService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OSSConfigureConfig ossConfigure;
    @Autowired
    private OfcAttachmentService  ofcAttachmentService;
    @Override
    public URL getFileURL(String filePath) {

        logger.debug("==> 开始获取文件路径...");
        logger.debug("==> 获取文件路径 filePath = {}", filePath);
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
        logger.debug("==> 获取文件路径 filePath = {}", filePath);
        return signedUrl;
    }

    @Override
    public void deleteFile(String filePath) {
        logger.debug("==> 开始删除文件...");
        logger.debug("==> 删除文件 ossConfigure = {}", ossConfigure);
        logger.debug("==> 删除文件 filePath = {}", filePath);
        OSSClient ossClient = new OSSClient(ossConfigure.getEndpoint(), ossConfigure.getAccessKeyId(), ossConfigure.getAccessKeySecret());
        ossClient.deleteObject(ossConfigure.getBucketName(), filePath);
        logger.debug("==> 删除文件成功 filePath = {}", filePath);
    }

    @Override
    public String operateImage(String style ,String serialNo) throws UnsupportedEncodingException {
        String key="";
        OfcAttachment attachment=new OfcAttachment();
        attachment.setSerialNo(serialNo);
        OfcAttachment result=ofcAttachmentService.selectOne(attachment);
        if(result!=null){
            if(!StringUtils.isEmpty(result.getPath())||!StringUtils.isEmpty(result.getName())){
                key=result.getName();
            }else{
                throw new BusinessException("附件文件名或路径为空");
            }
        }else{
            throw new BusinessException("附件不存在");
        }
        logger.info("key is {}",key);
        OSSClient ossClient = new OSSClient(ossConfigure.getEndpoint(), ossConfigure.getAccessKeyId(),  ossConfigure.getAccessKeySecret());
//        GetObjectRequest request = new GetObjectRequest(ossConfigure.getBucketName(), key);
//        request.setProcess(style);
//        File f= new File("example-resize.jpg");
//        ossClient.getObject(request,f);
//        URL  url=getFileURL(result.getPath()+f.getName());
//        logger.info("样式处理后的链接为:{}",url.toString());
//        OfcAttachment updateAtta=new OfcAttachment();
//        updateAtta.setSerialNo(serialNo);
//        updateAtta.setPicParam(style);
//        ofcAttachmentService.updatePicParamByserialNo(updateAtta);
//        return url.toString();
        // 过期时间10分钟
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(ossConfigure.getBucketName(),key,HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);

        if(signedUrl!=null){
            logger.info("样式处理后的链接为:{}",URLDecoder.decode(signedUrl.toString(),"UTF-8"));
            //记录最后一次的样式处理
            OfcAttachment updateAtta=new OfcAttachment();
            updateAtta.setSerialNo(serialNo);
            updateAtta.setPicParam(style);
           ofcAttachmentService.updatePicParamByserialNo(updateAtta);
            return URLDecoder.decode(signedUrl.toString(),"UTF-8");
        }else{
            throw new BusinessException("生成的处理后的url失败");
        }
    }
    }

