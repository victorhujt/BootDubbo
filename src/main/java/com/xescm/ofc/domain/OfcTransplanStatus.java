package com.xescm.ofc.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "ofc_transplan_status")
public class OfcTransplanStatus {
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
     * 计划单状态
     */
    @Column(name = "plannedSingleState")
    private String plannedsinglestate;

    /**
     * 计划开始时间
     */
    @Column(name = "plannedStartTime")
    private Date plannedstarttime;

    /**
     * 计划完成时间
     */
    @Column(name = "plannedCompletionTime")
    private Date plannedcompletiontime;

    /**
     * 任务开始时间
     */
    @Column(name = "taskStartTime")
    private Date taskstarttime;

    /**
     * 任务完成时间
     */
    @Column(name = "taskCompletionTime")
    private Date taskcompletiontime;

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
     * 获取计划单状态
     *
     * @return plannedSingleState - 计划单状态
     */
    public String getPlannedsinglestate() {
        return plannedsinglestate;
    }

    /**
     * 设置计划单状态
     *
     * @param plannedsinglestate 计划单状态
     */
    public void setPlannedsinglestate(String plannedsinglestate) {
        this.plannedsinglestate = plannedsinglestate;
    }

    /**
     * 获取计划开始时间
     *
     * @return plannedStartTime - 计划开始时间
     */
    public Date getPlannedstarttime() {
        return plannedstarttime;
    }

    /**
     * 设置计划开始时间
     *
     * @param plannedstarttime 计划开始时间
     */
    public void setPlannedstarttime(Date plannedstarttime) {
        this.plannedstarttime = plannedstarttime;
    }

    /**
     * 获取计划完成时间
     *
     * @return plannedCompletionTime - 计划完成时间
     */
    public Date getPlannedcompletiontime() {
        return plannedcompletiontime;
    }

    /**
     * 设置计划完成时间
     *
     * @param plannedcompletiontime 计划完成时间
     */
    public void setPlannedcompletiontime(Date plannedcompletiontime) {
        this.plannedcompletiontime = plannedcompletiontime;
    }

    /**
     * 获取任务开始时间
     *
     * @return taskStartTime - 任务开始时间
     */
    public Date getTaskstarttime() {
        return taskstarttime;
    }

    /**
     * 设置任务开始时间
     *
     * @param taskstarttime 任务开始时间
     */
    public void setTaskstarttime(Date taskstarttime) {
        this.taskstarttime = taskstarttime;
    }

    /**
     * 获取任务完成时间
     *
     * @return taskCompletionTime - 任务完成时间
     */
    public Date getTaskcompletiontime() {
        return taskcompletiontime;
    }

    /**
     * 设置任务完成时间
     *
     * @param taskcompletiontime 任务完成时间
     */
    public void setTaskcompletiontime(Date taskcompletiontime) {
        this.taskcompletiontime = taskcompletiontime;
    }
}