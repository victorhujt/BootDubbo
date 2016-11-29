package com.xescm.ofc.model.dto.wms;



import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@ApiModel(description = "入库单")
public class WhcTransReceive {

    private String id;
    @ApiModelProperty(value = "入库单号", required = true)
    private String orderNo;
    @ApiModelProperty(value = "仓库中心入库单", required = true)
    private String whcBillno;
    @ApiModelProperty(value = "入库单类型", required = true)
    private String ordertype;
    @ApiModelProperty(value = "调拨出库单号", required = true)
    private String oaOrderNo;
    @ApiModelProperty(value = "订单来源", required = true)
    private String fromSystem;
    @ApiModelProperty(value = "货主编号", required = true)
    private String customerCode;
    @ApiModelProperty(value = "货主姓名", required = true)
    private String customerName;
    @ApiModelProperty(value = "承运人编号", required = true)
    private String carrierCode;
    @ApiModelProperty(value = "承运人姓名", required = true)
    private String carrierName;
    @ApiModelProperty(value = "仓库编号", required = true)
    private String warehouseCode;
    @ApiModelProperty(value = "仓库名称", required = true)
    private String warehouseName;
    @ApiModelProperty(value = "来源仓库编码", required = true)
    private String sourceWarehouseCode;
    @ApiModelProperty(value = "来源仓库名称", required = true)
    private String sourceWarehouseName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "预计货物到达时间", required = true)
    private Date expectedArriveTime;

    private String userDefine;

    private String userDefine2;

    private String userDefine3;

    private String userDefine4;

    private String userDefine5;

    private String userDefine6;

    private String userDefine7;
    @ApiModelProperty(value = "备注")
    private String notes;

    private List<WhcTransReceiveDetail> detailList;


    public List<WhcTransReceiveDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<WhcTransReceiveDetail> detailList) {
        this.detailList = detailList;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getWhcBillno() {
        return whcBillno;
    }

    public void setWhcBillno(String whcBillno) {
        this.whcBillno = whcBillno == null ? null : whcBillno.trim();
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype == null ? null : ordertype.trim();
    }

    public String getOaOrderNo() {
        return oaOrderNo;
    }

    public void setOaOrderNo(String oaOrderNo) {
        this.oaOrderNo = oaOrderNo == null ? null : oaOrderNo.trim();
    }

    public String getFromSystem() {
        return fromSystem;
    }

    public void setFromSystem(String fromSystem) {
        this.fromSystem = fromSystem == null ? null : fromSystem.trim();
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode == null ? null : customerCode.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode == null ? null : carrierCode.trim();
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName == null ? null : carrierName.trim();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
    }

    public String getSourceWarehouseCode() {
        return sourceWarehouseCode;
    }

    public void setSourceWarehouseCode(String sourceWarehouseCode) {
        this.sourceWarehouseCode = sourceWarehouseCode == null ? null : sourceWarehouseCode.trim();
    }

    public String getSourceWarehouseName() {
        return sourceWarehouseName;
    }

    public void setSourceWarehouseName(String sourceWarehouseName) {
        this.sourceWarehouseName = sourceWarehouseName == null ? null : sourceWarehouseName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpectedArriveTime() {
        return expectedArriveTime;
    }

    public void setExpectedArriveTime(Date expectedArriveTime) {
        this.expectedArriveTime = expectedArriveTime;
    }

    public String getUserDefine() {
        return userDefine;
    }

    public void setUserDefine(String userDefine) {
        this.userDefine = userDefine == null ? null : userDefine.trim();
    }

    public String getUserDefine2() {
        return userDefine2;
    }

    public void setUserDefine2(String userDefine2) {
        this.userDefine2 = userDefine2 == null ? null : userDefine2.trim();
    }

    public String getUserDefine3() {
        return userDefine3;
    }

    public void setUserDefine3(String userDefine3) {
        this.userDefine3 = userDefine3 == null ? null : userDefine3.trim();
    }

    public String getUserDefine4() {
        return userDefine4;
    }

    public void setUserDefine4(String userDefine4) {
        this.userDefine4 = userDefine4 == null ? null : userDefine4.trim();
    }

    public String getUserDefine5() {
        return userDefine5;
    }

    public void setUserDefine5(String userDefine5) {
        this.userDefine5 = userDefine5 == null ? null : userDefine5.trim();
    }

    public String getUserDefine6() {
        return userDefine6;
    }

    public void setUserDefine6(String userDefine6) {
        this.userDefine6 = userDefine6 == null ? null : userDefine6.trim();
    }

    public String getUserDefine7() {
        return userDefine7;
    }

    public void setUserDefine7(String userDefine7) {
        this.userDefine7 = userDefine7 == null ? null : userDefine7.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    @Override
    public String toString() {
        return "AllocateInOrder{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", whcBillno='" + whcBillno + '\'' +
                ", ordertype='" + ordertype + '\'' +
                ", oaOrderNo='" + oaOrderNo + '\'' +
                ", fromSystem='" + fromSystem + '\'' +
                ", customerCode='" + customerCode + '\'' +
                ", customerName='" + customerName + '\'' +
                ", carrierCode='" + carrierCode + '\'' +
                ", carrierName='" + carrierName + '\'' +
                ", warehouseCode='" + warehouseCode + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", sourceWarehouseCode='" + sourceWarehouseCode + '\'' +
                ", sourceWarehouseName='" + sourceWarehouseName + '\'' +
                ", createTime=" + createTime +
                ", expectedArriveTime=" + expectedArriveTime +
                ", userDefine='" + userDefine + '\'' +
                ", userDefine2='" + userDefine2 + '\'' +
                ", userDefine3='" + userDefine3 + '\'' +
                ", userDefine4='" + userDefine4 + '\'' +
                ", userDefine5='" + userDefine5 + '\'' +
                ", userDefine6='" + userDefine6 + '\'' +
                ", userDefine7='" + userDefine7 + '\'' +
                ", notes='" + notes + '\'' +
                ", detailList=" + detailList +
                "" +
                '}';
    }
}