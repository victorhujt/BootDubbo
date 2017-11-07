package com.xescm.ofc.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "exce_submitted")
public class ExceSubmitted {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    /**
     * 异常编号
     */
    @Column(name = "exce_code")
    private String exceCode;

    /**
     * 客户编码
     */
    @Column(name = "customer_code")
    private String customerCode;

    /**
     * 客户名称
     */
    @Column(name = "customer_name")
    private String customerName;

    /**
     * 业务类型
     */
    @Column(name = "business_type")
    private String businessType;

    /**
     * 异常类型
     */
    @Column(name = "exce_type")
    private String exceType;

    /**
     * 订单号
     */
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 发生时间
     */
    @Column(name = "occurrence_time")
    private Date occurrenceTime;

    /**
     * 货损产品
     */
    @Column(name = "damage_product")
    private String damageProduct;

    /**
     * 异常数量
     */
    @Column(name = "damage_product_num")
    private String damageProductNum;

    /**
     * 货损金额
     */
    private BigDecimal losses;

    /**
     * 负责人
     */
    @Column(name = "responsible_person")
    private String responsiblePerson;

    /**
     * 异常描述
     */
    @Column(name = "exce_describe")
    private String exceDescribe;

    /**
     * 异常附件图片
     */
    @Column(name = "img_serial_no")
    private String imgSerialNo;

    /**
     * 处理状态
     */
    @Column(name = "exce_state")
    private String exceState;

    /**
     * 上报人编码
     */
    @Column(name = "report_person_code")
    private String reportPersonCode;

    /**
     * 上报人名称
     */
    @Column(name = "report_person_name")
    private String reportPersonName;

    /**
     * 上报时间
     */
    @Column(name = "report_time")
    private Date reportTime;

    /**
     * 上报大区编码
     */
    @Column(name = "report_region_code")
    private String reportRegionCode;

    /**
     * 上报大区名称
     */
    @Column(name = "report_region_name")
    private String reportRegionName;

    /**
     * 上报基地编码
     */
    @Column(name = "report_base_code")
    private String reportBaseCode;

    /**
     * 上报基地名称
     */
    @Column(name = "report_base_name")
    private String reportBaseName;

    /**
     * 处理人编码
     */
    @Column(name = "dealing_person_code")
    private String dealingPersonCode;

    /**
     * 处理人名称
     */
    @Column(name = "dealing_person_name")
    private String dealingPersonName;

    /**
     * 处理时间
     */
    @Column(name = "dealing_time")
    private Date dealingTime;

    /**
     * 处理意见
     */
    @Column(name = "dealing_opinion")
    private String dealingOpinion;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建人ID
     */
    @Column(name = "creator_id")
    private String creatorId;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * 最近操作人
     */
    @Column(name = "last_operator")
    private String lastOperator;

    /**
     * 最后操作人ID
     */
    @Column(name = "last_operator_id")
    private String lastOperatorId;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

}