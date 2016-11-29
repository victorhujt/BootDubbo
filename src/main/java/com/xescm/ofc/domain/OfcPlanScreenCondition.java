package com.xescm.ofc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
public class OfcPlanScreenCondition {
    @Override
    public String toString() {
        return "OfcPlanScreenCondition{" +
                "orderTimePre=" + orderTimePre +
                ", orderTimeSuf=" + orderTimeSuf +
                ", custName='" + custName + '\'' +
                ", orderBatchNumber='" + orderBatchNumber + '\'' +
                ", resourceAllocationStatus='" + resourceAllocationStatus + '\'' +
                '}';
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderTimePre;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderTimeSuf;
    private String custName;
    private String custCode;
    private String orderBatchNumber;
    private String resourceAllocationStatus;
    private List<String> resourceAllocationStatues;

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

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getOrderBatchNumber() {
        return orderBatchNumber;
    }

    public void setOrderBatchNumber(String orderBatchNumber) {
        this.orderBatchNumber = orderBatchNumber;
    }

    public String getResourceAllocationStatus() {
        return resourceAllocationStatus;
    }

    public void setResourceAllocationStatus(String resourceAllocationStatus) {
        this.resourceAllocationStatus = resourceAllocationStatus;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public List<String> getResourceAllocationStatues() {
        return resourceAllocationStatues;
    }

    public void setResourceAllocationStatues(List<String> resourceAllocationStatues) {
        this.resourceAllocationStatues = resourceAllocationStatues;
    }
}
