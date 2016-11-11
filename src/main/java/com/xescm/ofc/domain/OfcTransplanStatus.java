package com.xescm.ofc.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "ofc_transplan_status")
public class OfcTransplanStatus {
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
     * 获取计划单状态
     *
     * @return planned_single_state - 计划单状态
     */
    public String getPlannedSingleState() {
        return plannedSingleState;
    }

    /**
     * 设置计划单状态
     *
     * @param plannedSingleState 计划单状态
     */
    public void setPlannedSingleState(String plannedSingleState) {
        this.plannedSingleState = plannedSingleState;
    }

    /**
     * 获取计划开始时间
     *
     * @return planned_start_time - 计划开始时间
     */
    public Date getPlannedStartTime() {
        return plannedStartTime;
    }

    /**
     * 设置计划开始时间
     *
     * @param plannedStartTime 计划开始时间
     */
    public void setPlannedStartTime(Date plannedStartTime) {
        this.plannedStartTime = plannedStartTime;
    }

    /**
     * 获取计划完成时间
     *
     * @return planned_completion_time - 计划完成时间
     */
    public Date getPlannedCompletionTime() {
        return plannedCompletionTime;
    }

    /**
     * 设置计划完成时间
     *
     * @param plannedCompletionTime 计划完成时间
     */
    public void setPlannedCompletionTime(Date plannedCompletionTime) {
        this.plannedCompletionTime = plannedCompletionTime;
    }

    /**
     * 获取任务开始时间
     *
     * @return task_start_time - 任务开始时间
     */
    public Date getTaskStartTime() {
        return taskStartTime;
    }

    /**
     * 设置任务开始时间
     *
     * @param taskStartTime 任务开始时间
     */
    public void setTaskStartTime(Date taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    /**
     * 获取任务完成时间
     *
     * @return task_completion_time - 任务完成时间
     */
    public Date getTaskCompletionTime() {
        return taskCompletionTime;
    }

    /**
     * 设置任务完成时间
     *
     * @param taskCompletionTime 任务完成时间
     */
    public void setTaskCompletionTime(Date taskCompletionTime) {
        this.taskCompletionTime = taskCompletionTime;
    }
}