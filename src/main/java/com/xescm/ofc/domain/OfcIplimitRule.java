package com.xescm.ofc.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Data
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

}