package com.xescm.ofc.model.dto.whc;




/**
 * 取消出庫/入庫實體類
 */

public class CancelOrderDTO {


    /** 计划单编号 */
    private String orderNo;

    /** 入库/出库单号（仓储中心提供，订单中心可不传值） */
    private String whcBillNo;

    /** 業務類型（出庫：CK 入庫：RK） */
    private String billType;

    /** 訂單類型 */
    private String orderType;

    /** 货主ID */
    private String customerID;

    /** 仓库ID */
    private String warehouseID;

    /** 取消原因 */
    private String reason;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(String warehouseID) {
        this.warehouseID = warehouseID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getWhcBillNo() {
        return whcBillNo;
    }

    public void setWhcBillNo(String whcBillNo) {
        this.whcBillNo = whcBillNo;
    }
}
