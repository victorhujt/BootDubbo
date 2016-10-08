package com.xescm.ofc.domain;

import java.util.Date;

public class OfcWarehouseInformation {
    private String supportName;

    private String supportCode;

    private Integer provideTransport;

    private Date shipmentTime;

    private Date arriveTime;

    private String warehouseName;

    public String getSupportName() {
        return supportName;
    }

    public void setSupportName(String supportName) {
        this.supportName = supportName == null ? null : supportName.trim();
    }

    public String getSupportCode() {
        return supportCode;
    }

    public void setSupportCode(String supportCode) {
        this.supportCode = supportCode == null ? null : supportCode.trim();
    }

    public Integer getProvideTransport() {
        return provideTransport;
    }

    public void setProvideTransport(Integer provideTransport) {
        this.provideTransport = provideTransport;
    }

    public Date getShipmentTime() {
        return shipmentTime;
    }

    public void setShipmentTime(Date shipmentTime) {
        this.shipmentTime = shipmentTime;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
    }
}