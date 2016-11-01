package com.xescm.ofc.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "ofc_transplan_newstatus")
public class OfcTransplanNewstatus {
    /**
     * 计划单编号
     */
    @Column(name = "planCode")
    private String plancode;

    /**
     * 订单编号
     */
    @Column(name = "orderCode")
    private String ordercode;

    /**
     * 运输单最新状态
     */
    @Column(name = "transportSingleLatestStatus")
    private String transportsinglelateststatus;

    /**
     * 运输单更新时间
     */
    @Column(name = "transportSingleUpdateTime")
    private Date transportsingleupdatetime;

    /**
     * 获取计划单编号
     *
     * @return planCode - 计划单编号
     */
    public String getPlancode() {
        return plancode;
    }

    /**
     * 设置计划单编号
     *
     * @param plancode 计划单编号
     */
    public void setPlancode(String plancode) {
        this.plancode = plancode;
    }

    /**
     * 获取订单编号
     *
     * @return orderCode - 订单编号
     */
    public String getOrdercode() {
        return ordercode;
    }

    /**
     * 设置订单编号
     *
     * @param ordercode 订单编号
     */
    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }

    /**
     * 获取运输单最新状态
     *
     * @return transportSingleLatestStatus - 运输单最新状态
     */
    public String getTransportsinglelateststatus() {
        return transportsinglelateststatus;
    }

    /**
     * 设置运输单最新状态
     *
     * @param transportsinglelateststatus 运输单最新状态
     */
    public void setTransportsinglelateststatus(String transportsinglelateststatus) {
        this.transportsinglelateststatus = transportsinglelateststatus;
    }

    /**
     * 获取运输单更新时间
     *
     * @return transportSingleUpdateTime - 运输单更新时间
     */
    public Date getTransportsingleupdatetime() {
        return transportsingleupdatetime;
    }

    /**
     * 设置运输单更新时间
     *
     * @param transportsingleupdatetime 运输单更新时间
     */
    public void setTransportsingleupdatetime(Date transportsingleupdatetime) {
        this.transportsingleupdatetime = transportsingleupdatetime;
    }
}