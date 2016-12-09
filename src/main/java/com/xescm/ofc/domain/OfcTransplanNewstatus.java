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
     * 操作人
     */
    @Column(name = "operator")
    private String operator;

    /**
     * 当前位置
     */
    private String location;

    /**
     * 当前温度
     */
    private String temperature;

    /**
     * 当前湿度
     */
    private String humidity;

    /**
     * 操作单位
     */
    @Column(name = "oper_unit")
    private String operUnit;

    /**
     * 描述信息
     */
    private String desc;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

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

    /**
     * 获取当前位置
     *
     * @return location - 当前位置
     */
    public String getLocation() {
        return location;
    }

    /**
     * 设置当前位置
     *
     * @param location 当前位置
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * 获取当前温度
     *
     * @return temperature - 当前温度
     */
    public String getTemperature() {
        return temperature;
    }

    /**
     * 设置当前温度
     *
     * @param temperature 当前温度
     */
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    /**
     * 获取当前湿度
     *
     * @return humidity - 当前湿度
     */
    public String getHumidity() {
        return humidity;
    }

    /**
     * 设置当前湿度
     *
     * @param humidity 当前湿度
     */
    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    /**
     * 获取操作单位
     *
     * @return oper_unit - 操作单位
     */
    public String getOperUnit() {
        return operUnit;
    }

    /**
     * 设置操作单位
     *
     * @param operUnit 操作单位
     */
    public void setOperUnit(String operUnit) {
        this.operUnit = operUnit;
    }

    /**
     * 获取描述信息
     *
     * @return desc - 描述信息
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 设置描述信息
     *
     * @param desc 描述信息
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
}