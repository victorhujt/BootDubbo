package com.xescm.ofc.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
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
}