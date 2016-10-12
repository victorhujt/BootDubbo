package com.xescm.ofc.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "ofc_order_status")
public class OfcOrderStatus {
    @Override
    public String toString() {
        return "OfcOrderStatus{" +
                "orderCode='" + orderCode + '\'' +
                ", orderType='" + orderType + '\'' +
                ", businessType='" + businessType + '\'' +
                ", custCode='" + custCode + '\'' +
                ", custName='" + custName + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", statusDesc='" + statusDesc + '\'' +
                ", lastedOperTime=" + lastedOperTime +
                ", notes='" + notes + '\'' +
                ", operator='" + operator + '\'' +
                '}';
    }

    /**
     * 订单编号
     */
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 订单类型
     */
    @Column(name = "order_type")
    private String orderType;

    /**
     * 业务类型
     */
    @Column(name = "business_type")
    private String businessType;

    /**
     * 货主编码
     */
    @Column(name = "cust_code")
    private String custCode;

    /**
     * 货主名称
     */
    @Column(name = "cust_name")
    private String custName;

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
     * 获取订单类型
     *
     * @return order_type - 订单类型
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * 设置订单类型
     *
     * @param orderType 订单类型
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * 获取业务类型
     *
     * @return business_type - 业务类型
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     * 设置业务类型
     *
     * @param businessType 业务类型
     */
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    /**
     * 获取货主编码
     *
     * @return cust_code - 货主编码
     */
    public String getCustCode() {
        return custCode;
    }

    /**
     * 设置货主编码
     *
     * @param custCode 货主编码
     */
    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    /**
     * 获取货主名称
     *
     * @return cust_name - 货主名称
     */
    public String getCustName() {
        return custName;
    }

    /**
     * 设置货主名称
     *
     * @param custName 货主名称
     */
    public void setCustName(String custName) {
        this.custName = custName;
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
}