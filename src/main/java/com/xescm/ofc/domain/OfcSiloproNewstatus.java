package com.xescm.ofc.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

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
     * 获取作业最新状态
     *
     * @return job_new_status - 作业最新状态
     */
    public String getJobNewStatus() {
        return jobNewStatus;
    }

    /**
     * 设置作业最新状态
     *
     * @param jobNewStatus 作业最新状态
     */
    public void setJobNewStatus(String jobNewStatus) {
        this.jobNewStatus = jobNewStatus;
    }

    /**
     * 获取作业状态更新时间
     *
     * @return job_status_update_time - 作业状态更新时间
     */
    public Date getJobStatusUpdateTime() {
        return jobStatusUpdateTime;
    }

    /**
     * 设置作业状态更新时间
     *
     * @param jobStatusUpdateTime 作业状态更新时间
     */
    public void setJobStatusUpdateTime(Date jobStatusUpdateTime) {
        this.jobStatusUpdateTime = jobStatusUpdateTime;
    }
}