package com.xescm.ofc.model.dto.dms;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by maguodong on 2016/12/8.
 * 运单操作记录
 */
public class DmsTransferRecordDto {
    private String id;
    private String serialNo; //记录流水号
    private String transNo;//运单号                                                       // 运输单号
    private String refNo;  //关联业务单据号
    private String recordTypeCode;//操作类型编码                                         //Type
    private String recordTypeName;//操作类型名称
    private Integer type;//操作类型,1新增-1删除
    private String typeName;//操作类型名称
    private String waybillStatusCode;//单据状态编码
    private String waybillStatusName;//运单状态名称
    private String lineName;//线路名称
    private String beginStationCode;//出站编码
    private String beginStationName;//出站名称
    private String arriveStationCode;//到站编码
    private String arriveStationName;//到站名称
    private String transSource;//运单来源
    private String remark;//备注                                                          //Desp
    private String creator;//创建人
    private String creatorId;//创建人ID
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private String createdTime;//创建时间

    public String getId() {
        return id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public String getTransNo() {
        return transNo;
    }

    public String getRefNo() {
        return refNo;
    }

    public String getRecordTypeCode() {
        return recordTypeCode;
    }

    public String getRecordTypeName() {
        return recordTypeName;
    }

    public Integer getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getWaybillStatusCode() {
        return waybillStatusCode;
    }

    public String getWaybillStatusName() {
        return waybillStatusName;
    }

    public String getLineName() {
        return lineName;
    }

    public String getBeginStationCode() {
        return beginStationCode;
    }

    public String getBeginStationName() {
        return beginStationName;
    }

    public String getArriveStationCode() {
        return arriveStationCode;
    }

    public String getArriveStationName() {
        return arriveStationName;
    }

    public String getTransSource() {
        return transSource;
    }

    public String getRemark() {
        return remark;
    }

    public String getCreator() {
        return creator;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public void setRecordTypeCode(String recordTypeCode) {
        this.recordTypeCode = recordTypeCode;
    }

    public void setRecordTypeName(String recordTypeName) {
        this.recordTypeName = recordTypeName;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setWaybillStatusCode(String waybillStatusCode) {
        this.waybillStatusCode = waybillStatusCode;
    }

    public void setWaybillStatusName(String waybillStatusName) {
        this.waybillStatusName = waybillStatusName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public void setBeginStationCode(String beginStationCode) {
        this.beginStationCode = beginStationCode;
    }

    public void setBeginStationName(String beginStationName) {
        this.beginStationName = beginStationName;
    }

    public void setArriveStationCode(String arriveStationCode) {
        this.arriveStationCode = arriveStationCode;
    }

    public void setArriveStationName(String arriveStationName) {
        this.arriveStationName = arriveStationName;
    }

    public void setTransSource(String transSource) {
        this.transSource = transSource;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
