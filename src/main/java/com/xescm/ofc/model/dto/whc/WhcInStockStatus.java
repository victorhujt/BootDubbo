package com.xescm.ofc.model.dto.whc;

import java.util.Date;

/**
 * 入库单状态实体类
 * */
public class WhcInStockStatus {

    /** 主键ID */
    private String id;

    /** 出库单号 */
    private String whcBillno;

    /** 出库类型 */
    private String orderType;

    /** 状态 */
    private String status;

    /** 订单号 */
    private String orderNo;

    /** 跟踪时间 */
    private Date traceTime;

    /** 操作时间 */
    private Date operatingTime;

    /** 客户编码 */
    private String customerCode;

    /** 仓库编码 */
    private String warehouseCode;

    /** 操作人 */
    private String operator;

    /** 备注 */
    private String notes;

    /** wms单号 */
    private String wmsNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWhcBillno() {
        return whcBillno;
    }

    public void setWhcBillno(String whcBillno) {
        this.whcBillno = whcBillno;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getTraceTime() {
        return traceTime;
    }

    public void setTraceTime(Date traceTime) {
        this.traceTime = traceTime;
    }

    public Date getOperatingTime() {
        return operatingTime;
    }

    public void setOperatingTime(Date operatingTime) {
        this.operatingTime = operatingTime;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getWmsNo() {
        return wmsNo;
    }

    public void setWmsNo(String wmsNo) {
        this.wmsNo = wmsNo;
    }
}