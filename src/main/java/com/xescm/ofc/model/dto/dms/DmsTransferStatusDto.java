package com.xescm.ofc.model.dto.dms;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Title:    DmsTransferStatusDto. </p>
 * <p>Description 运单状态Dto </p>
 *
 * @Author      <a href="dingzhiyiking@163.com"/>丁志毅</a>
 * @CreateDate  2016/12/9 11:56
 */
public class DmsTransferStatusDto implements Serializable{

    private static final long serialVersionUID = -2495442871868344354L;

    /**
     * 流水号
     */
    private String serialNo;

    /**
     * 运单号
     */
    private String transNo;

    /**
     * 关联业务单据号
     */
    private String refNo;

    /**
     * 单据状态编码
     */
    private String waybillStatusCode;

    /**
     * 单据状态名称
     */
    private String waybillStatusName;

    /**
     * 当前位置(坐标值)
     */
    private String location;

    /**
     * 当前温度
     */
    private String temperature;

    /**
     * 当前湿度
     */
    private String humidity;

    /**
     * 描述
     */
    private String desp;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建人ID
     */
    private String creatorId;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

    /**
     * 仓库流水号
     */
    private String unitCode;

    /**
     * 仓库名称
     */
    private String unit;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getWaybillStatusCode() {
        return waybillStatusCode;
    }

    public void setWaybillStatusCode(String waybillStatusCode) {
        this.waybillStatusCode = waybillStatusCode;
    }

    public String getWaybillStatusName() {
        return waybillStatusName;
    }

    public void setWaybillStatusName(String waybillStatusName) {
        this.waybillStatusName = waybillStatusName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
