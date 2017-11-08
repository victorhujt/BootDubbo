package com.xescm.ofc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hujintao on 2017/6/13.
 */
@Configuration
@ConfigurationProperties(prefix = IpLimitRuleConfig.IP_lIMIT_RULE)
@Data
public class IpLimitRuleConfig {

    public final static String IP_lIMIT_RULE="ipLimitRule";
    /**
     * 第一阀值的最小时间
     */
    private int fristMinTime;
    /**
     *  第一阀值的最小请求最小次数
     */
    private int firstThresholdMin;
    /**
     * 第一阀值的最大时间
     */
    private int fristMaxTime;
    /**
     * 第一阀值的最大请求最次次数
     */
    private int firstThresholdMax;
    /**
     * 第二阀值的最小时间
     */
    private int secondMinTime;
    /**
     *  第二阀值的最小请求最小次数
     */
    private int secondThresholdMin;
    /**
     * 第二阀值的最大时间
     */
    private int secondMaxTime;
    /**
     * 第二阀值的最大请求最次次数
     */
    private int secondThresholdMax;
    /**
     * 短信接口的调用次数
     */
    private int sendSmsLimit;
    /**
     * 第三阀值的最小时间
     */

    private int thirdMinTime;
    /**
     *  第三阀值的最小请求最小次数
     */
    private int thirdThresholdMin;


}
