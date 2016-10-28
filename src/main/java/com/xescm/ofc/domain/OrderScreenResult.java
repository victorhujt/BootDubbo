package com.xescm.ofc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by lyh on 2016/10/10.
 */
public class OrderScreenResult {
    @Override
    public String toString() {
        return "OrderScreenResult{" +
                "orderCode='" + orderCode + '\'' +
                ", custOrderCode='" + custOrderCode + '\'' +
                ", orderTime=" + orderTime +
                ", orderType='" + orderType + '\'' +
                ", businessType='" + businessType + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", consigneeName='" + consigneeName + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", notes='" + notes + '\'' +
                ", store='" + store + '\'' +
                '}';
    }

    private String orderCode;
    private String custOrderCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderTime;
    /*
    订单类型: 0为运输订单 1为仓配订单
     */
    private String orderType;
    /*
    业务类型
    00 城配
    01 干线

    10 销售出库
    11 调拨出库
    12 报损出库
    13 其他出库

    14 采购入库
    15 调拨入库
    16 退货入库
    17 加工入库
     */
    private String businessType;
    /*
     订单状态:
     待审核:0
     已审核:1
     执行中:2
     已完成:3
     已取消:4
     */
    private String orderStatus;
    private String consigneeName;
    private String warehouseName;
    private String notes;
    private String store;//店铺

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCustOrderCode() {
        return custOrderCode;
    }

    public void setCustOrderCode(String custOrderCode) {
        this.custOrderCode = custOrderCode;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }
}
