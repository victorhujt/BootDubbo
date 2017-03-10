package com.xescm.ofc.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "ofc_silopro_status")
public class OfcSiloproStatus {
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
     * 计划单状态
     */
    @Column(name = "planned_single_state")
    private String plannedSingleState;

    /**
     * 计划开始时间
     */
    @Column(name = "planned_start_time")
    private Date plannedStartTime;

    /**
     * 计划完成时间
     */
    @Column(name = "planned_completion_time")
    private Date plannedCompletionTime;

    /**
     * 任务开始时间
     */
    @Column(name = "task_start_time")
    private Date taskStartTime;

    /**
     * 任务完成时间
     */
    @Column(name = "task_completion_time")
    private Date taskCompletionTime;


}