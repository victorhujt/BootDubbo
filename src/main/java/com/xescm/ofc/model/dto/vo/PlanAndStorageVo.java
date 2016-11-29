package com.xescm.ofc.model.dto.vo;

import java.util.Date;

/**
 * 仓储-计划
 * Created by hiyond on 2016/11/25.
 */
public class PlanAndStorageVo {

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

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getResourceAllocationStatus() {
        return resourceAllocationStatus;
    }

    public void setResourceAllocationStatus(String resourceAllocationStatus) {
        this.resourceAllocationStatus = resourceAllocationStatus;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public String getServiceProviderContact() {
        return serviceProviderContact;
    }

    public void setServiceProviderContact(String serviceProviderContact) {
        this.serviceProviderContact = serviceProviderContact;
    }

    public String getServiceProviderContactPhone() {
        return serviceProviderContactPhone;
    }

    public void setServiceProviderContactPhone(String serviceProviderContactPhone) {
        this.serviceProviderContactPhone = serviceProviderContactPhone;
    }

    public String getPlannedSingleState() {
        return plannedSingleState;
    }

    public void setPlannedSingleState(String plannedSingleState) {
        this.plannedSingleState = plannedSingleState;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }
}
