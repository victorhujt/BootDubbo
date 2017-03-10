package com.xescm.ofc.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 仓储-计划
 * Created by hiyond on 2016/11/25.
 */
@Data
public class PlanAndStorage implements Serializable {

    private static final long serialVersionUID = -6636402636202541680L;
    private String orderCode;

    private String planCode;

    private String type;

    private String businessType;

    private String resourceAllocationStatus;

    private String serviceProviderName;

    private String serviceProviderContact;

    private String serviceProviderContactPhone;

    private String plannedSingleState;

    private String departure;

    private String destination;

    private String warehouseName;

    private Date finishedTime;

    private String baseName;

}
