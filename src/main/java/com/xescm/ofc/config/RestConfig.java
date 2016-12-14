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

    public String getCscWebUrl() {
        return cscWebUrl;
    }

    public void setCscWebUrl(String cscWebUrl) {
        this.cscWebUrl = cscWebUrl;
    }

    /**
     * 客户中心前端Url
     */
    private String cscWebUrl;

    /**
     * 订单中心前端URL
     */
    private String ofcWebUrl;

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

    /**
     * 运输中心地址
     * @return
     */
    private String tfcUrl;

    public String getWhcUrl() {
        return whcUrl;
    }

    public void setWhcUrl(String whcUrl) {
        this.whcUrl = whcUrl;
    }

    /**
     * 仓储中心的地址

     */
    private String whcUrl;

    /**
     * EPC
     * @return
     */
    private String epcUrl;

    /**
     * 结算中心
     */
    private String acUrl;

    public String getAcUrl() {
        return acUrl;
    }

    public void setAcUrl(String acUrl) {
        this.acUrl = acUrl;
    }

    public String getEpcUrl() {
        return epcUrl;
    }

    public void setEpcUrl(String epcUrl) {
        this.epcUrl = epcUrl;
    }

    public String getOfcWebUrl() {
        return ofcWebUrl;
    }

    public void setOfcWebUrl(String ofcWebUrl) {
        this.ofcWebUrl = ofcWebUrl;
    }

    public String getTfcUrl() {
        return tfcUrl;
    }

    public void setTfcUrl(String tfcUrl) {
        this.tfcUrl = tfcUrl;
    }

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
