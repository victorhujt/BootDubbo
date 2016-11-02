package com.xescm.ofc.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "ofc_transplan_newstatus")
public class OfcTransplanNewstatus {
    /**
     * 计划单编号
     */
    @Column(name = "plan_code")
    private String planCode;

    /**
     * 订单编号
     */
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 运输单最新状态
     */
    @Column(name = "transport_single_latest_status")
    private String transportSingleLatestStatus;

    /**
     * 运输单更新时间
     */
    @Column(name = "transport_single_update_time")
    private Date transportSingleUpdateTime;

    /**
     * 获取计划单编号
     *
     * @return plan_code - 计划单编号
     */
    public String getPlanCode() {
        return planCode;
    }

    /**
     * 设置计划单编号
     *
     * @param planCode 计划单编号
     */
    public void setPlanCode(String planCode) {
        this.planCode = planCode;
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
     * 获取运输单最新状态
     *
     * @return transport_single_latest_status - 运输单最新状态
     */
    public String getTransportSingleLatestStatus() {
        return transportSingleLatestStatus;
    }

    /**
     * 设置运输单最新状态
     *
     * @param transportSingleLatestStatus 运输单最新状态
     */
    public void setTransportSingleLatestStatus(String transportSingleLatestStatus) {
        this.transportSingleLatestStatus = transportSingleLatestStatus;
    }

    /**
     * 获取运输单更新时间
     *
     * @return transport_single_update_time - 运输单更新时间
     */
    public Date getTransportSingleUpdateTime() {
        return transportSingleUpdateTime;
    }

    /**
     * 设置运输单更新时间
     *
     * @param transportSingleUpdateTime 运输单更新时间
     */
    public void setTransportSingleUpdateTime(Date transportSingleUpdateTime) {
        this.transportSingleUpdateTime = transportSingleUpdateTime;
    }
}