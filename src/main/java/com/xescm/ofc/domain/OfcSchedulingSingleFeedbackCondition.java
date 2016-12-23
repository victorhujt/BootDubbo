package com.xescm.ofc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
public class OfcSchedulingSingleFeedbackCondition {
    @Override
    public String toString() {
        return "OfcSchedulingSingleFeedbackCondition{" +
                ", deliveryNo='" + deliveryNo + '\'' +
                ", transportType='" + transportType + '\'' +
                ", vehical='" + vehical + '\'' +
                ", driver='" + driver + '\'' +
                ", tel='" + tel + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    private List<String> transportNo;//运输计划单号
    private String deliveryNo;//调度单号
    private String transportType;//运输类型
    private String vehical;//车牌号
    private String driver;//司机姓名
    private String tel;//联系电话
    private String vehicleType;//车型
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;//调度单时间
    private String lineName;


    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public List<String> getTransportNo() {
        return transportNo;
    }

    public void setTransportNo(List<String> transportNo) {
        this.transportNo = transportNo;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getVehical() {
        return vehical;
    }

    public void setVehical(String vehical) {
        this.vehical = vehical;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
