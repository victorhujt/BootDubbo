package com.xescm.ofc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取订单编号
     *
     * @return order_code - 订单编号
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * 设置订单编号
     *
     * @param orderCode 订单编号
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    /**
     * 获取订单状态
     *
     * @return order_status - 订单状态
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * 设置订单状态
     *
     * @param orderStatus 订单状态
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 获取状态描述
     *
     * @return status_desc - 状态描述
     */
    public String getStatusDesc() {
        return statusDesc;
    }

    /**
     * 设置状态描述
     *
     * @param statusDesc 状态描述
     */
    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    /**
     * 获取最近操作时间
     *
     * @return lasted_oper_time - 最近操作时间
     */
    public Date getLastedOperTime() {
        return lastedOperTime;
    }

    /**
     * 设置最近操作时间
     *
     * @param lastedOperTime 最近操作时间
     */
    public void setLastedOperTime(Date lastedOperTime) {
        this.lastedOperTime = lastedOperTime;
    }

    /**
     * 获取备注
     *
     * @return notes - 备注
     */
    public String getNotes() {
        return notes;
    }

    /**
     * 设置备注
     *
     * @param notes 备注
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * 获取操作人员
     *
     * @return operator - 操作人员
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置操作人员
     *
     * @param operator 操作人员
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}