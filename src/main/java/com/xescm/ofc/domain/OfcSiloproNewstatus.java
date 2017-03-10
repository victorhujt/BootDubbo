package com.xescm.ofc.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "ofc_silopro_newstatus")
public class OfcSiloproNewstatus {
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
     * 作业最新状态
     */
    @Column(name = "job_new_status")
    private String jobNewStatus;

    /**
     * 作业状态更新时间
     */
    @Column(name = "job_status_update_time")
    private Date jobStatusUpdateTime;


}