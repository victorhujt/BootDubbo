package com.xescm.ofc.domain;

import lombok.Data;

import java.util.List;

/**
 * Created by victor on 2016/12/2.
 */
@Data
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

    private List<OfcPlannedDetail> plannedDetails;

}
