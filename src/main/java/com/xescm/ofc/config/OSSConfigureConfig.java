package com.xescm.ofc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hujintao on 2016/12/12.
 */
@Configuration
@ConfigurationProperties(prefix = OSSConfigureConfig.OSS_CONF)
public class OSSConfigureConfig {

    public final static String OSS_CONF = "oss_conf";

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getOutEndpoint() {
        return outEndpoint;
    }

    public void setOutEndpoint(String outEndpoint) {
        this.outEndpoint = outEndpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public int getDelayHour() {
        return delayHour;
    }

    public void setDelayHour(int delayHour) {
        this.delayHour = delayHour;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    /**
     * OSS 访问域名[内网]

     */
    private String endpoint;

    /**
     * OSS 访问域名[外网]
     */
    private String outEndpoint;
    /**
     * key ID
     */
    private String accessKeyId;

    /**
     * key密钥
     */
    private String accessKeySecret;

    /**
     * 文件服务器根目录
     */
    private String bucketName;

    /**
     * 延迟生效时间 单位:h
     */
    private int delayHour;

    /**
     * 远端路径
     */
    private String remotePath;


}
