package com.xescm.ofc.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "ofc_ip_limit_rule")
public class OfcIplimitRule {
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    /**
     * ip
     */
    private String ip;

    /**
     * 是否发送过短信验证码 0 否 1是
     */
    @Column(name = "sendSms")
    private String sendSms;

    /**
     * 是否冻结 0:否 1:是
     */
    @Column(name = "freeze")
    private String freeze;

    /**
     * 是否解冻过 0:没有 1:有
     */
    @Column(name = "unFreeze")
    private String unFreeze;

    /**
     * 是否是黑名单 0:不是 1:是
     */
    @Column(name = "black")
    private String black;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取ip
     *
     * @return ip - ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置ip
     *
     * @param ip ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取是否发送过短信验证码 0 否 1是
     *
     * @return isSend - 是否发送过短信验证码 0 否 1是
     */
    public String getSendSms() {
        return sendSms;
    }

    /**
     * 设置是否发送过短信验证码 0 否 1是
     *
     * @param sendSms 是否发送过短信验证码 0 否 1是
     */
    public void setSendSms(String sendSms) {
        this.sendSms = sendSms;
    }

    /**
     * 获取是否冻结 0:否 1:是
     *
     * @return isFreeze - 是否冻结 0:否 1:是
     */
    public String getFreeze() {
        return freeze;
    }

    /**
     * 设置是否冻结 0:否 1:是
     *
     * @param freeze 是否冻结 0:否 1:是
     */
    public void setFreeze(String freeze) {
        this.freeze = freeze;
    }

    /**
     * 获取是否解冻过 0:没有 1:有
     *
     * @return isUnFreeze - 是否解冻过 0:没有 1:有
     */
    public String getUnFreeze() {
        return unFreeze;
    }

    /**
     * 设置是否解冻过 0:没有 1:有
     *
     * @param unFreeze 是否解冻过 0:没有 1:有
     */
    public void setunFreeze(String unFreeze) {
        this.unFreeze = unFreeze;
    }

    /**
     * 获取是否是黑名单 0:不是 1:是
     *
     * @return black - 是否是黑名单 0:不是 1:是
     */
    public String getBlack() {
        return black;
    }

    /**
     * 设置是否是黑名单 0:不是 1:是
     *
     * @param black 是否是黑名单 0:不是 1:是
     */
    public void setBlack(String black) {
        this.black = black;
    }
}