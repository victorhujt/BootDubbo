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
@Table(name = "ofc_order_status")
public class OfcOrderStatus {

    /**
     * ID 主键
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
     * 订单状态
     */
    @Column(name = "order_status")
    private String orderStatus;

    /**
     * 状态描述
     */
    @Column(name = "status_desc")
    private String statusDesc;

    /**
     * 最近操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS",timezone = "GMT+8")
    @Column(name = "lasted_oper_time")
    private Date lastedOperTime;

    /**
     * 备注
     */
    private String notes;

    /**
     * 操作人员
     */
    private String operator;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS",timezone = "GMT+8")
    @Column(name = "creation_time")
    private String creationTime;
}