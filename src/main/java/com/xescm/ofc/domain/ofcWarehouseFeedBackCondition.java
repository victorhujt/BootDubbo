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
    private String whcBillNo;

    private String buniessType;

    public String getBuniessType() {
        return buniessType;
    }

    public void setBuniessType(String buniessType) {
        this.buniessType = buniessType;
    }

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

    public String getWhcBillNo() {
        return whcBillNo;
    }

    public void setWhcBillNo(String whcBillNo) {
        this.whcBillNo = whcBillNo;
    }
}
