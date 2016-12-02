package com.xescm.ofc.domain;

import java.util.List;

/**
 * Created by victor on 2016/12/2.
 */
public class ofcWarehouseFeedBackCondition {
    /**
     *计划单编号
     */
    private  String planCode;
    /**
     * 仓储作业单号
     */
    private String whcBillno;

    private List<OfcPlannedDetail> plannedDetails;

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public List<OfcPlannedDetail> getPlannedDetails() {
        return plannedDetails;
    }

    public void setPlannedDetails(List<OfcPlannedDetail> plannedDetails) {
        this.plannedDetails = plannedDetails;
    }

    public String getWhcBillno() {
        return whcBillno;
    }

    public void setWhcBillno(String whcBillno) {
        this.whcBillno = whcBillno;
    }
}
