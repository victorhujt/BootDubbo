package com.xescm.ofc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wangsongtao on 16/3/2.
 */
@Configuration
@ConfigurationProperties(prefix = RestConfig.REST_PREFIX)
public class RestConfig {

    public final static String REST_PREFIX="restful";


    /**
     * 订单中心的URL地址
     */
    private String ofcUrl;

    /**
     * 用户中心UAM地址
     */
    private String uamUrl;
    
    /**
     * 资源中心View调用
     */
    private String rmcUrl;

    /**
     * 客户中心
     */
    private String cscUrl;

    /**
     * 地址库地址
     * @return
     */
    private String addrUrl;

    /**
     * 分拣中心地址
     * @return
     */
    private String dmsUrl;

    public String getDmsUrl() {
        return dmsUrl;
    }

    public void setDmsUrl(String dmsUrl) {
        this.dmsUrl = dmsUrl;
    }

    public String getAddrUrl() {
        return addrUrl;
    }

    public void setAddrUrl(String addrUrl) {
        this.addrUrl = addrUrl;
    }

    public String getOfcUrl() {
        return ofcUrl;
    }

    public void setOfcUrl(String ofcUrl) {
        this.ofcUrl = ofcUrl;
    }

    public String getUamUrl() {
        return uamUrl;
    }

    public void setUamUrl(String uamUrl) {
        this.uamUrl = uamUrl;
    }

    public String getRmcUrl() {
        return rmcUrl;
    }

    public void setRmcUrl(String rmcUrl) {
        this.rmcUrl = rmcUrl;
    }

    public String getCscUrl() {
        return cscUrl;
    }

    public void setCscUrl(String cscUrl) {
        this.cscUrl = cscUrl;
    }
}
