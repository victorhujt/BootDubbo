package com.xescm.ofc.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Data
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
    private String description;


}