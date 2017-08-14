package com.xescm.ofc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "ofc_except_order")
public class OfcExceptOrder {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    /**
     * 订单编号
     */
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 客户订单号
     */
    @Column(name = "cust_order_code")
    private String custOrderCode;

    /**
     * 客户编码
     */
    @Column(name = "cust_code")
    private String custCode;

    /**
     * 客户名称
     */
    @Column(name = "cust_name")
    private String custName;

    /**
     * 订单类型
     */
    @Column(name = "order_type")
    private String orderType;

    /**
     * 业务类型
     */
    @Column(name = "business_type")
    private String businessType;// two_distribution

    /**
     * 业务类型
     */
    @Column(name = "two_distribution")
    private String twoDistribution;

    /**
     * 运输单号
     */
    @Column(name = "trans_code")
    private String transCode;

    /**
     * 是否提供运输
     */
    @Column(name = "provide_transport")
    private String provideTransport;

    /**
     * 大区编码
     */
    @Column(name = "area_code")
    private String areaCode;

    /**
     * 大区名称
     */
    @Column(name = "area_name")
    private String areaName;

    /**
     * 基地编码
     */
    @Column(name = "base_code")
    private String baseCode;

    /**
     * 基地名称
     */
    @Column(name = "base_name")
    private String baseName;

    /**
     * 订单来源
     */
    @Column(name = "order_source")
    private String orderSource;

    /**
     * 订单创建日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "creation_time")
    private Date creationTime;

    @Column(name = "from_sys")
    private String fromSys;

    /**
     * 状态
     */
    private String status;

    /**
     * 结点类型
     */
    @Column(name = "pot_type")
    private String potType;

    /**
     * 结点时间
     */
    @Column(name = "pot_time")
    private Date potTime;

    @Column(name = "record_msg")
    private String recordMsg;

    /**
     * 记录时间
     */
    @Column(name = "record_time")
    private Date recordTime;


    /**
     * 异常原因
     */
    @Column(name = "except_reason")
    private String exceptReason;

    /**
     * 处理状态
     */
    @Column(name = "deal_status")
    private String dealStatus;

    /**
     * 有效标志位(0有效, 1无效)
     */
    @Column(name = "yn")
    private String yn;

    /**
     * 操作人ID
     */
    @Column(name = "operator_id")
    private String operatorId;

    /**
     * 操作人名称
     */
    @Column(name = "operator_name")
    private String operatorName;

    public OfcExceptOrder(){}

    public OfcExceptOrder(String orderCode) {
        this.orderCode = orderCode;
    }
}