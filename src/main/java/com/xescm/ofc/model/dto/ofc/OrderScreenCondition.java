package com.xescm.ofc.model.dto.ofc;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by lyh on 2016/10/10.
 */
public class OrderScreenCondition {
    @Override
    public String toString() {
        return "OrderScreenCondition{" +
                "orderTimePre=" + orderTimePre +
                ", orderTimeSuf=" + orderTimeSuf +
                ", orderCode='" + orderCode + '\'' +
                ", custOrderCode='" + custOrderCode + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderType='" + orderType + '\'' +
                ", businessType='" + businessType + '\'' +
                '}';
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderTimePre;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderTimeSuf;
    private String orderCode;
    private String custOrderCode;
    private String orderStatus;
    private String orderType;
    private String businessType;

    public Date getOrderTimePre() {
        return orderTimePre;
    }

    public void setOrderTimePre(Date orderTimePre) {
        this.orderTimePre = orderTimePre;
    }

    public Date getOrderTimeSuf() {
        return orderTimeSuf;
    }

    public void setOrderTimeSuf(Date orderTimeSuf) {
        this.orderTimeSuf = orderTimeSuf;
    }

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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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
}
