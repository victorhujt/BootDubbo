package com.xescm.ofc.model.vo.ofc;

import com.xescm.ofc.domain.OfcSiloprogramInfo;

import java.util.Date;

/**
 * Created by victor on 2016/11/28.
 */
public class OfcSiloprogramInfoVo extends OfcSiloprogramInfo {
    private String driverName;

    private String plateNumber;

    private String contactNumber;
    /**
     * 服务商编码
     */
    private String serviceProviderCode;
    /**

     * 服务商名称
     */
    private String serviceProviderName;
    /**
     * 仓储计划单状态
     */
    private String plannedSingleState;

    private String transCode;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getServiceProviderCode() {
        return serviceProviderCode;
    }

    public void setServiceProviderCode(String serviceProviderCode) {
        this.serviceProviderCode = serviceProviderCode;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public String getPlannedSingleState() {
        return plannedSingleState;
    }

    public void setPlannedSingleState(String plannedSingleState) {
        this.plannedSingleState = plannedSingleState;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }
}
