package com.xescm.ofc.model.dto.whc;

/**
 * 取消订单实体类:出入库通用
 *
 * @author Chen zhen gang
 * @date 2016/11/19
 * @time 9:32
 */
public class CancelWhcOrderDTO {

    private String OrderNo;// 入库单号 必须

    private String OrderType;// 入库单类型 必须

    private String CustomerID;// 货主ID 必须

    private String WarehouseID;// 仓库ID 必须

    private String Reason;// 取消原因 必须

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getWarehouseID() {
        return WarehouseID;
    }

    public void setWarehouseID(String warehouseID) {
        WarehouseID = warehouseID;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    @Override
    public String toString() {
        return "CancelWhcOrder{" +
                "OrderNo='" + OrderNo + '\'' +
                ", OrderType='" + OrderType + '\'' +
                ", CustomerID='" + CustomerID + '\'' +
                ", WarehouseID='" + WarehouseID + '\'' +
                ", Reason='" + Reason + '\'' +
                '}';
    }
}
