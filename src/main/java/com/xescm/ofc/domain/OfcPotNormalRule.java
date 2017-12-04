package com.xescm.ofc.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "ofc_pot_normal_rule")
public class OfcPotNormalRule {
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    /**
     * 订单号
     */
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 当前节点开始时间
     */
    @Column(name = "pot_start_time")
    private Date potStartTime;

    /**
     * 当前节点结束时间
     */
    @Column(name = "pot_end_time")
    private Date potEndTime;

    /**
     * 节点类型
     */
    @Column(name = "pot_type")
    private String potType;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取订单号
     *
     * @return order_code - 订单号
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * 设置订单号
     *
     * @param orderCode 订单号
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    /**
     * 获取当前节点开始时间
     *
     * @return pot_start_time - 当前节点开始时间
     */
    public Date getPotStartTime() {
        return potStartTime;
    }

    /**
     * 设置当前节点开始时间
     *
     * @param potStartTime 当前节点开始时间
     */
    public void setPotStartTime(Date potStartTime) {
        this.potStartTime = potStartTime;
    }

    /**
     * 获取当前节点结束时间
     *
     * @return pot_end_time - 当前节点结束时间
     */
    public Date getPotEndTime() {
        return potEndTime;
    }

    /**
     * 设置当前节点结束时间
     *
     * @param potEndTime 当前节点结束时间
     */
    public void setPotEndTime(Date potEndTime) {
        this.potEndTime = potEndTime;
    }

    /**
     * 获取节点类型
     *
     * @return pot_type - 节点类型
     */
    public String getPotType() {
        return potType;
    }

    /**
     * 设置节点类型
     *
     * @param potType 节点类型
     */
    public void setPotType(String potType) {
        this.potType = potType;
    }
}