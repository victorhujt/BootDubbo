package com.xescm.ofc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wangsongtao on 16/3/2.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = RestConfig.REST_PREFIX)
public class RestConfig {

    public final static String REST_PREFIX = "restful";

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
     * 客户中心前端Url
     */
    private String cscWebUrl;

    /**
     * 订单中心前端URL
     */
    private String ofcWebUrl;

    /**
     * 地址库地址
     */
    private String addrUrl;

    /**
     * 分拣中心地址
     */
    private String dmsUrl;

    /**
     * 运输中心地址
     *
     * @return
     */
    private String tfcUrl;

    /**
     * 仓储中心的地址
     */
    private String whcUrl;

    /**
     * EPC
     */
    private String epcUrl;

    /**
     * 结算中心
     */
    private String acUrl;

    /**
     * 官网
     */
    private String officalApiUrl;

    private String officalApiShortUrl;

    private String officalApiDevUrl;

    /**
     * 订单中心报表
     */
    private String report;

    /**
     * 移动代理
     */
    private String mproxy;

    /**
     * 前后端分离
     */
    private String paas;

    private String paasDev;
}
