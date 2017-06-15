package com.xescm.ofc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hujintao on 2017/6/13.
 */
@Configuration
@ConfigurationProperties(prefix = IpLimitRuleConfig.IP_lIMIT_RULE)
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

    public int getSendSmsLimit() {
        return sendSmsLimit;
    }

    public void setSendSmsLimit(int sendSmsLimit) {
        this.sendSmsLimit = sendSmsLimit;
    }

    /**
     * 短信接口的调用次数
     */
    private int sendSmsLimit;

    public int getFristMinTime() {
        return fristMinTime;
    }

    public void setFristMinTime(int fristMinTime) {
        this.fristMinTime = fristMinTime;
    }

    public int getFirstThresholdMin() {
        return firstThresholdMin;
    }

    public void setFirstThresholdMin(int firstThresholdMin) {
        this.firstThresholdMin = firstThresholdMin;
    }

    public int getFristMaxTime() {
        return fristMaxTime;
    }

    public void setFristMaxTime(int fristMaxTime) {
        this.fristMaxTime = fristMaxTime;
    }

    public int getFirstThresholdMax() {
        return firstThresholdMax;
    }

    public void setFirstThresholdMax(int firstThresholdMax) {
        this.firstThresholdMax = firstThresholdMax;
    }

    public int getSecondMinTime() {
        return secondMinTime;
    }

    public void setSecondMinTime(int secondMinTime) {
        this.secondMinTime = secondMinTime;
    }

    public int getSecondThresholdMin() {
        return secondThresholdMin;
    }

    public void setSecondThresholdMin(int secondThresholdMin) {
        this.secondThresholdMin = secondThresholdMin;
    }

    public int getSecondMaxTime() {
        return secondMaxTime;
    }

    public void setSecondMaxTime(int secondMaxTime) {
        this.secondMaxTime = secondMaxTime;
    }

    public int getSecondThresholdMax() {
        return secondThresholdMax;
    }

    public void setSecondThresholdMax(int secondThresholdMax) {
        this.secondThresholdMax = secondThresholdMax;
    }

    public int getThirdMinTime() {
        return thirdMinTime;
    }

    public void setThirdMinTime(int thirdMinTime) {
        this.thirdMinTime = thirdMinTime;
    }

    public int getThirdThresholdMin() {
        return thirdThresholdMin;
    }

    public void setThirdThresholdMin(int thirdThresholdMin) {
        this.thirdThresholdMin = thirdThresholdMin;
    }

    /**
     * 第三阀值的最小时间
     */

    private int thirdMinTime;
    /**
     *  第三阀值的最小请求最小次数
     */
    private int thirdThresholdMin;


}
