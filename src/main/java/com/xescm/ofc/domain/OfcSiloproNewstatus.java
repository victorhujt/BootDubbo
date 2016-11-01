package com.xescm.ofc.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "ofc_silopro_newstatus")
public class OfcSiloproNewstatus {
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
     * 作业最新状态
     */
    @Column(name = "jobNewStatus")
    private String jobnewstatus;

    /**
     * 作业状态更新时间
     */
    @Column(name = "jobStatusUpdateTime")
    private Date jobstatusupdatetime;

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
     * 获取作业最新状态
     *
     * @return jobNewStatus - 作业最新状态
     */
    public String getJobnewstatus() {
        return jobnewstatus;
    }

    /**
     * 设置作业最新状态
     *
     * @param jobnewstatus 作业最新状态
     */
    public void setJobnewstatus(String jobnewstatus) {
        this.jobnewstatus = jobnewstatus;
    }

    /**
     * 获取作业状态更新时间
     *
     * @return jobStatusUpdateTime - 作业状态更新时间
     */
    public Date getJobstatusupdatetime() {
        return jobstatusupdatetime;
    }

    /**
     * 设置作业状态更新时间
     *
     * @param jobstatusupdatetime 作业状态更新时间
     */
    public void setJobstatusupdatetime(Date jobstatusupdatetime) {
        this.jobstatusupdatetime = jobstatusupdatetime;
    }
}