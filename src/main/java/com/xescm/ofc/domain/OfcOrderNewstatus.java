package com.xescm.ofc.domain;

import javax.persistence.*;
import java.util.Date;

@Table(name = "ofc_order_newstatus")
public class OfcOrderNewstatus {
    /**
     * 订单编号
     */
    @Id
    @Column(name = "order_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderCode;

    /**
     * 订单最新状态
     */
    @Column(name = "order_latest_status")
    private String orderLatestStatus;

    /**
     * 订单状态更新时间
     */
    @Column(name = "status_update_time")
    private Date statusUpdateTime;

    /**
     * 订单状态创建时间
     */
    @Column(name = "status_create_time")
    private Date statusCreateTime;

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
     * 获取订单最新状态
     *
     * @return order_latest_status - 订单最新状态
     */
    public String getOrderLatestStatus() {
        return orderLatestStatus;
    }

    /**
     * 设置订单最新状态
     *
     * @param orderLatestStatus 订单最新状态
     */
    public void setOrderLatestStatus(String orderLatestStatus) {
        this.orderLatestStatus = orderLatestStatus;
    }

    /**
     * 获取订单状态更新时间
     *
     * @return status_update_time - 订单状态更新时间
     */
    public Date getStatusUpdateTime() {
        return statusUpdateTime;
    }

    /**
     * 设置订单状态更新时间
     *
     * @param statusUpdateTime 订单状态更新时间
     */
    public void setStatusUpdateTime(Date statusUpdateTime) {
        this.statusUpdateTime = statusUpdateTime;
    }

    /**
     * 获取订单状态创建时间
     *
     * @return status_create_time - 订单状态创建时间
     */
    public Date getStatusCreateTime() {
        return statusCreateTime;
    }

    /**
     * 设置订单状态创建时间
     *
     * @param statusCreateTime 订单状态创建时间
     */
    public void setStatusCreateTime(Date statusCreateTime) {
        this.statusCreateTime = statusCreateTime;
    }
}