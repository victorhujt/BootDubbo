package com.xescm.ofc.model.vo.ofc;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lyh on 2016/10/10.
 */
public class OfcPlanScreenResult {
    @Override
    public String toString() {
        return "OfcPlanScreenResult{" +
                "orderCode='" + orderCode + '\'' +
                ", planCode='" + planCode + '\'' +
                ", orderTime=" + orderTime +
                ", orderBatchNumber='" + orderBatchNumber + '\'' +
                ", businessType='" + businessType + '\'' +
                ", resourceAllocationStatus='" + resourceAllocationStatus + '\'' +
                ", custName='" + custName + '\'' +
                ", serviceProviderName='" + serviceProviderName + '\'' +
                ", departureProvince='" + departureProvince + '\'' +
                ", departureCity='" + departureCity + '\'' +
                ", departureDistrict='" + departureDistrict + '\'' +
                ", departureTowns='" + departureTowns + '\'' +
                ", destinationProvince='" + destinationProvince + '\'' +
                ", destinationCity='" + destinationCity + '\'' +
                ", destinationDistrict='" + destinationDistrict + '\'' +
                ", destinationTown='" + destinationTown + '\'' +
                ", resourceConfirmation='" + resourceConfirmation + '\'' +
                ", resourceConfirmationTime=" + resourceConfirmationTime +
                ", serviceProviderContact='" + serviceProviderContact + '\'' +
                ", serviceProviderContactPhone='" + serviceProviderContactPhone + '\'' +
                ", weight=" + weight +
                ", quantity=" + quantity +
                '}';
    }

    private String orderCode;
    private String planCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderTime;
    /*
    订单类型: 0为运输订单 1为仓配订单
     */
    private String orderBatchNumber;
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
    private String plannedSingleState;
    private String resourceAllocationStatus;
    private String custName;
    private String custCode;
    private String serviceProviderName;
    private String departureProvince;
    private String departureCity;

    private String departureDistrict;

    private String departureTowns;
    private String destinationProvince;
    private String destinationCity;
    private String destinationDistrict;
    private String destinationTown;
    private String resourceConfirmation;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date resourceConfirmationTime;
    private String serviceProviderContact;
    private String serviceProviderContactPhone;
    private BigDecimal weight;
    private BigDecimal quantity;
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderBatchNumber() {
        return orderBatchNumber;
    }

    public void setOrderBatchNumber(String orderBatchNumber) {
        this.orderBatchNumber = orderBatchNumber;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getResourceAllocationStatus() {
        return resourceAllocationStatus;
    }

    public void setResourceAllocationStatus(String resourceAllocationStatus) {
        this.resourceAllocationStatus = resourceAllocationStatus;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public String getDepartureProvince() {
        return departureProvince;
    }

    public void setDepartureProvince(String departureProvince) {
        this.departureProvince = departureProvince;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDepartureDistrict() {
        return departureDistrict;
    }

    public void setDepartureDistrict(String departureDistrict) {
        this.departureDistrict = departureDistrict;
    }

    public String getDepartureTowns() {
        return departureTowns;
    }

    public void setDepartureTowns(String departureTowns) {
        this.departureTowns = departureTowns;
    }

    public String getDestinationProvince() {
        return destinationProvince;
    }

    public void setDestinationProvince(String destinationProvince) {
        this.destinationProvince = destinationProvince;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getDestinationDistrict() {
        return destinationDistrict;
    }

    public void setDestinationDistrict(String destinationDistrict) {
        this.destinationDistrict = destinationDistrict;
    }

    public String getDestinationTown() {
        return destinationTown;
    }

    public void setDestinationTown(String destinationTown) {
        this.destinationTown = destinationTown;
    }

    public String getResourceConfirmation() {
        return resourceConfirmation;
    }

    public void setResourceConfirmation(String resourceConfirmation) {
        this.resourceConfirmation = resourceConfirmation;
    }

    public Date getResourceConfirmationTime() {
        return resourceConfirmationTime;
    }

    public void setResourceConfirmationTime(Date resourceConfirmationTime) {
        this.resourceConfirmationTime = resourceConfirmationTime;
    }

    public String getServiceProviderContact() {
        return serviceProviderContact;
    }

    public void setServiceProviderContact(String serviceProviderContact) {
        this.serviceProviderContact = serviceProviderContact;
    }

    public String getServiceProviderContactPhone() {
        return serviceProviderContactPhone;
    }

    public void setServiceProviderContactPhone(String serviceProviderContactPhone) {
        this.serviceProviderContactPhone = serviceProviderContactPhone;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getPlannedSingleState() {
        return plannedSingleState;
    }

    public void setPlannedSingleState(String plannedSingleState) {
        this.plannedSingleState = plannedSingleState;
    }
}
