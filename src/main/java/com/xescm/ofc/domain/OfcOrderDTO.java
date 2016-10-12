package com.xescm.ofc.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lyh on 2016/10/11.
 */
public class OfcOrderDTO {
    /*基本信息表*/
    private String orderCode;
    private Date orderTime;
    private String custCode;
    private String custName;
    private String secCustCode;
    private String secCustName;
    private String orderType;
    private String businessType;
    private String custOrderCode;
    private String notes;
    private String storeCode;
    private String storeName;
    private String platformType;
    private String orderSource;
    private String productType;
    private String productName;
    private Date finishedTime;
    private Integer abolishMark;
    private Date abolishTime;
    private String abolisher;
    private String creator;
    private Date creationTime;
    private String operator;
    private Date operTime;

    /*订单状态表*/

//    private String orderCode;
//    private String orderType;
//    private String businessType;
//    private String custCode;
//    private String custName;
    private String orderStatus;
    private String statusDesc;
    private Date lastedOperTime;
//    private String notes;
//    private String operator;

    /*运输订单信息*/
    private String transCode;
    private String goodsType;
    private Integer urgent;
    private String departurePlace;
    private String departurePlaceCode;
    private String destination;
    private String destinationCode;
    private BigDecimal quantity;
    private BigDecimal weight;
    private String cubage;
    private Integer totalStandardBox;
    private String transRequire;
    private Date pickupTime;
    private Date expectedArrivedTime;
    private String consignorCode;
    private String consignorName;
    private String consigneeCode;
    private String consigneeName;
    private String carrierCode;
    private String carrierName;
//    private String orderCode;
//    private Date creationTime;
//    private String creator;
//    private String operator;
//    private Date operTime;//???????????

    /*仓配信息*/
    private String supportName;
    private String supportCode;
    private Integer provideTransport;
    private Date shipmentTime;
    private Date arriveTime;
    private String warehouseName;
    private String plateNumber;
    private String driverName;
    private String contactNumber;
//    private String orderCode;
//    private Date creationTime;
//    private String creator;
//    private String operator;
//    private Date operTime;

    /*商品表不用*/
    /*财务表*/
    private BigDecimal serviceCharge;
    private BigDecimal orderAmount;
    private BigDecimal paymentAmount;
    private BigDecimal collectLoanAmount;
    private BigDecimal collectServiceCharge;
    private String collectFlag;
    private String countFlag;
//    private String orderCode;
//    private String notes;
//    private Date creationTime;
//    private String creator;
//    private String operator;
//    private Date operTime;


    @Override
    public String toString() {
        return "OfcOrderDTO{" +
                "orderCode='" + orderCode + '\'' +
                ", orderTime=" + orderTime +
                ", custCode='" + custCode + '\'' +
                ", custName='" + custName + '\'' +
                ", secCustCode='" + secCustCode + '\'' +
                ", secCustName='" + secCustName + '\'' +
                ", orderType='" + orderType + '\'' +
                ", businessType='" + businessType + '\'' +
                ", custOrderCode='" + custOrderCode + '\'' +
                ", notes='" + notes + '\'' +
                ", storeCode='" + storeCode + '\'' +
                ", storeName='" + storeName + '\'' +
                ", platformType='" + platformType + '\'' +
                ", orderSource='" + orderSource + '\'' +
                ", productType='" + productType + '\'' +
                ", productName='" + productName + '\'' +
                ", finishedTime=" + finishedTime +
                ", abolishMark=" + abolishMark +
                ", abolishTime=" + abolishTime +
                ", abolisher='" + abolisher + '\'' +
                ", creator='" + creator + '\'' +
                ", creationTime=" + creationTime +
                ", operator='" + operator + '\'' +
                ", operTime=" + operTime +
                ", orderStatus='" + orderStatus + '\'' +
                ", statusDesc='" + statusDesc + '\'' +
                ", lastedOperTime=" + lastedOperTime +
                ", transCode='" + transCode + '\'' +
                ", goodsType='" + goodsType + '\'' +
                ", urgent=" + urgent +
                ", departurePlace='" + departurePlace + '\'' +
                ", departurePlaceCode='" + departurePlaceCode + '\'' +
                ", destination='" + destination + '\'' +
                ", destinationCode='" + destinationCode + '\'' +
                ", quantity=" + quantity +
                ", weight=" + weight +
                ", cubage='" + cubage + '\'' +
                ", totalStandardBox=" + totalStandardBox +
                ", transRequire='" + transRequire + '\'' +
                ", pickupTime=" + pickupTime +
                ", expectedArrivedTime=" + expectedArrivedTime +
                ", consignorCode='" + consignorCode + '\'' +
                ", consignorName='" + consignorName + '\'' +
                ", consigneeCode='" + consigneeCode + '\'' +
                ", consigneeName='" + consigneeName + '\'' +
                ", carrierCode='" + carrierCode + '\'' +
                ", carrierName='" + carrierName + '\'' +
                ", supportName='" + supportName + '\'' +
                ", supportCode='" + supportCode + '\'' +
                ", provideTransport=" + provideTransport +
                ", shipmentTime=" + shipmentTime +
                ", arriveTime=" + arriveTime +
                ", warehouseName='" + warehouseName + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", driverName='" + driverName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", serviceCharge=" + serviceCharge +
                ", orderAmount=" + orderAmount +
                ", paymentAmount=" + paymentAmount +
                ", collectLoanAmount=" + collectLoanAmount +
                ", collectServiceCharge=" + collectServiceCharge +
                ", collectFlag='" + collectFlag + '\'' +
                ", countFlag='" + countFlag + '\'' +
                '}';
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getSecCustCode() {
        return secCustCode;
    }

    public void setSecCustCode(String secCustCode) {
        this.secCustCode = secCustCode;
    }

    public String getSecCustName() {
        return secCustName;
    }

    public void setSecCustName(String secCustName) {
        this.secCustName = secCustName;
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

    public String getCustOrderCode() {
        return custOrderCode;
    }

    public void setCustOrderCode(String custOrderCode) {
        this.custOrderCode = custOrderCode;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    public Integer getAbolishMark() {
        return abolishMark;
    }

    public void setAbolishMark(Integer abolishMark) {
        this.abolishMark = abolishMark;
    }

    public Date getAbolishTime() {
        return abolishTime;
    }

    public void setAbolishTime(Date abolishTime) {
        this.abolishTime = abolishTime;
    }

    public String getAbolisher() {
        return abolisher;
    }

    public void setAbolisher(String abolisher) {
        this.abolisher = abolisher;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Date getLastedOperTime() {
        return lastedOperTime;
    }

    public void setLastedOperTime(Date lastedOperTime) {
        this.lastedOperTime = lastedOperTime;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public Integer getUrgent() {
        return urgent;
    }

    public void setUrgent(Integer urgent) {
        this.urgent = urgent;
    }

    public String getDeparturePlace() {
        return departurePlace;
    }

    public void setDeparturePlace(String departurePlace) {
        this.departurePlace = departurePlace;
    }

    public String getDeparturePlaceCode() {
        return departurePlaceCode;
    }

    public void setDeparturePlaceCode(String departurePlaceCode) {
        this.departurePlaceCode = departurePlaceCode;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getCubage() {
        return cubage;
    }

    public void setCubage(String cubage) {
        this.cubage = cubage;
    }

    public Integer getTotalStandardBox() {
        return totalStandardBox;
    }

    public void setTotalStandardBox(Integer totalStandardBox) {
        this.totalStandardBox = totalStandardBox;
    }

    public String getTransRequire() {
        return transRequire;
    }

    public void setTransRequire(String transRequire) {
        this.transRequire = transRequire;
    }

    public Date getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(Date pickupTime) {
        this.pickupTime = pickupTime;
    }

    public Date getExpectedArrivedTime() {
        return expectedArrivedTime;
    }

    public void setExpectedArrivedTime(Date expectedArrivedTime) {
        this.expectedArrivedTime = expectedArrivedTime;
    }

    public String getConsignorCode() {
        return consignorCode;
    }

    public void setConsignorCode(String consignorCode) {
        this.consignorCode = consignorCode;
    }

    public String getConsignorName() {
        return consignorName;
    }

    public void setConsignorName(String consignorName) {
        this.consignorName = consignorName;
    }

    public String getConsigneeCode() {
        return consigneeCode;
    }

    public void setConsigneeCode(String consigneeCode) {
        this.consigneeCode = consigneeCode;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getSupportName() {
        return supportName;
    }

    public void setSupportName(String supportName) {
        this.supportName = supportName;
    }

    public String getSupportCode() {
        return supportCode;
    }

    public void setSupportCode(String supportCode) {
        this.supportCode = supportCode;
    }

    public Integer getProvideTransport() {
        return provideTransport;
    }

    public void setProvideTransport(Integer provideTransport) {
        this.provideTransport = provideTransport;
    }

    public Date getShipmentTime() {
        return shipmentTime;
    }

    public void setShipmentTime(Date shipmentTime) {
        this.shipmentTime = shipmentTime;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public BigDecimal getCollectLoanAmount() {
        return collectLoanAmount;
    }

    public void setCollectLoanAmount(BigDecimal collectLoanAmount) {
        this.collectLoanAmount = collectLoanAmount;
    }

    public BigDecimal getCollectServiceCharge() {
        return collectServiceCharge;
    }

    public void setCollectServiceCharge(BigDecimal collectServiceCharge) {
        this.collectServiceCharge = collectServiceCharge;
    }

    public String getCollectFlag() {
        return collectFlag;
    }

    public void setCollectFlag(String collectFlag) {
        this.collectFlag = collectFlag;
    }

    public String getCountFlag() {
        return countFlag;
    }

    public void setCountFlag(String countFlag) {
        this.countFlag = countFlag;
    }
}
