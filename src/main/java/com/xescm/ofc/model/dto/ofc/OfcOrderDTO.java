package com.xescm.ofc.model.dto.ofc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lyh on 2016/10/11.
 */
public class OfcOrderDTO {

    /*基本信息表*/
    private String orderCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date finishedTime;
    private Integer abolishMark;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date abolishTime;
    private String abolisher;
    private String creator;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date creationTime;
    private String operator;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date operTime;

    private String abolisherName;

    private String creatorName;

    private String operatorName;

    private String merchandiser;

    private String transportType;

    private String batchNumber;

    private String orderBatchNumber;

    /*订单状态表*/

//    private String orderCode;
//    private String orderType;
//    private String businessType;
//    private String custCode;
//    private String custName;
    private String orderStatus;
    private String statusDesc;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastedOperTime;
//    private String notes;
//    private String operator;

    /*运输订单信息*/
    private String transCode;
    //private String goodsType;
    private Integer urgent;
    private String departurePlace;//完整地址
    private String departureProvince;
    private String departureCity;
    private String departureDistrict;
    private String departureTowns;
    private String departurePlaceCode;//完整地址编码
    private String destination;//完整地址
    private String destinationProvince;
    private String destinationCity;
    private String destinationDistrict;
    private String destinationTowns;
    private String destinationCode;//完整地址编码   111/2222/3333
    private BigDecimal quantity;
    private BigDecimal weight;
    private String cubage;
    private Integer totalStandardBox;
    private String transRequire;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date pickupTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date expectedArrivedTime;
    private String consignorCode;
    private String consignorName;
    private String consigneeCode;
    private String consigneeName;
    private String carrierCode;
    private String carrierName;

    private String consignorContactCode;
    private String consignorContactName;
    private String consigneeContactCode;
    private String consigneeContactName;
    private String consignorType;
    private String consigneeType;
    private String consignorContactPhone;
    private String consigneeContactPhone;
    private String goodsType;
   // private List<OfcGoodsDetailsInfo> goodsList = new ArrayList<OfcGoodsDetailsInfo>();
    private List<OfcGoodsDetailsInfo> goodsList = new ArrayList<>();




//    private String orderCode;
//    private Date creationTime;
//    private String creator;
//    private String operator;
//    private Date operTime;//???????????

    /*仓配信息*/
    private String supportName;
    private String supportCode;
    private Integer provideTransport;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date shipmentTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date arriveTime;
    private String warehouseName;
    private String warehouseCode;
    private String plateNumber;
    private String driverName;
    private String contactNumber;
    private String supportContactCode;
    private String supportContactName;
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
    private String printInvoice;
    private String buyerPaymentMethod;
    private String insure;
    private BigDecimal insureValue;
    private String pickUpGoods;
    private BigDecimal homeDeliveryFee;
    private BigDecimal cargoInsuranceFee;
    private BigDecimal insurance;
    private String twoDistribution;
    private BigDecimal twoDistributionFee;
    private String returnList;
    private BigDecimal returnListFee;
    private String expensePaymentParty;
    private String payment;
    private BigDecimal currentAmount;
    private BigDecimal toPayAmount;
    private BigDecimal returnAmount;
    private BigDecimal monthlyAmount;
    private BigDecimal luggage;
    private String openInvoices;


    private String selfCustOrderCode;

    public String getOrderBatchNumber() {
        return orderBatchNumber;
    }

    public void setOrderBatchNumber(String orderBatchNumber) {
        this.orderBatchNumber = orderBatchNumber;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public List<OfcGoodsDetailsInfo> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<OfcGoodsDetailsInfo> goodsList) {
        this.goodsList = goodsList;
    }

    public String getConsignorContactPhone() {
        return consignorContactPhone;
    }

    public void setConsignorContactPhone(String consignorContactPhone) {
        this.consignorContactPhone = consignorContactPhone;
    }

    public String getConsigneeContactPhone() {
        return consigneeContactPhone;
    }

    public void setConsigneeContactPhone(String consigneeContactPhone) {
        this.consigneeContactPhone = consigneeContactPhone;
    }

    public String getSelfCustOrderCode() {
        return selfCustOrderCode;
    }

    public void setSelfCustOrderCode(String selfCustOrderCode) {
        this.selfCustOrderCode = selfCustOrderCode;
    }

    public String getAbolisherName() {
        return abolisherName;
    }

    public void setAbolisherName(String abolisherName) {
        this.abolisherName = abolisherName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getConsignorType() {
        return consignorType;
    }

    public void setConsignorType(String consignorType) {
        this.consignorType = consignorType;
    }

    public String getConsigneeType() {
        return consigneeType;
    }

    public void setConsigneeType(String consigneeType) {
        this.consigneeType = consigneeType;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
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

    public String getDestinationTowns() {
        return destinationTowns;
    }

    public void setDestinationTowns(String destinationTowns) {
        this.destinationTowns = destinationTowns;
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

    public String getConsignorContactCode() {
        return consignorContactCode;
    }

    public void setConsignorContactCode(String consignorContactCode) {
        this.consignorContactCode = consignorContactCode;
    }

    public String getConsignorContactName() {
        return consignorContactName;
    }

    public void setConsignorContactName(String consignorContactName) {
        this.consignorContactName = consignorContactName;
    }

    public String getConsigneeContactCode() {
        return consigneeContactCode;
    }

    public void setConsigneeContactCode(String consigneeContactCode) {
        this.consigneeContactCode = consigneeContactCode;
    }

    public String getConsigneeContactName() {
        return consigneeContactName;
    }

    public void setConsigneeContactName(String consigneeContactName) {
        this.consigneeContactName = consigneeContactName;
    }

    public String getSupportContactCode() {
        return supportContactCode;
    }

    public void setSupportContactCode(String supportContactCode) {
        this.supportContactCode = supportContactCode;
    }

    public String getSupportContactName() {
        return supportContactName;
    }

    public void setSupportContactName(String supportContactName) {
        this.supportContactName = supportContactName;
    }

    public String getMerchandiser() {
        return merchandiser;
    }

    public void setMerchandiser(String merchandiser) {
        this.merchandiser = merchandiser;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getPrintInvoice() {
        return printInvoice;
    }

    public void setPrintInvoice(String printInvoice) {
        this.printInvoice = printInvoice;
    }

    public String getBuyerPaymentMethod() {
        return buyerPaymentMethod;
    }

    public void setBuyerPaymentMethod(String buyerPaymentMethod) {
        this.buyerPaymentMethod = buyerPaymentMethod;
    }

    public String getInsure() {
        return insure;
    }

    public void setInsure(String insure) {
        this.insure = insure;
    }

    public BigDecimal getInsureValue() {
        return insureValue;
    }

    public void setInsureValue(BigDecimal insureValue) {
        this.insureValue = insureValue;
    }

    public String getPickUpGoods() {
        return pickUpGoods;
    }

    public void setPickUpGoods(String pickUpGoods) {
        this.pickUpGoods = pickUpGoods;
    }

    public BigDecimal getHomeDeliveryFee() {
        return homeDeliveryFee;
    }

    public void setHomeDeliveryFee(BigDecimal homeDeliveryFee) {
        this.homeDeliveryFee = homeDeliveryFee;
    }

    public BigDecimal getCargoInsuranceFee() {
        return cargoInsuranceFee;
    }

    public void setCargoInsuranceFee(BigDecimal cargoInsuranceFee) {
        this.cargoInsuranceFee = cargoInsuranceFee;
    }

    public BigDecimal getInsurance() {
        return insurance;
    }

    public void setInsurance(BigDecimal insurance) {
        this.insurance = insurance;
    }

    public String getTwoDistribution() {
        return twoDistribution;
    }

    public void setTwoDistribution(String twoDistribution) {
        this.twoDistribution = twoDistribution;
    }

    public BigDecimal getTwoDistributionFee() {
        return twoDistributionFee;
    }

    public void setTwoDistributionFee(BigDecimal twoDistributionFee) {
        this.twoDistributionFee = twoDistributionFee;
    }

    public String getReturnList() {
        return returnList;
    }

    public void setReturnList(String returnList) {
        this.returnList = returnList;
    }

    public BigDecimal getReturnListFee() {
        return returnListFee;
    }

    public void setReturnListFee(BigDecimal returnListFee) {
        this.returnListFee = returnListFee;
    }

    public String getExpensePaymentParty() {
        return expensePaymentParty;
    }

    public void setExpensePaymentParty(String expensePaymentParty) {
        this.expensePaymentParty = expensePaymentParty;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    public BigDecimal getToPayAmount() {
        return toPayAmount;
    }

    public void setToPayAmount(BigDecimal toPayAmount) {
        this.toPayAmount = toPayAmount;
    }

    public BigDecimal getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(BigDecimal returnAmount) {
        this.returnAmount = returnAmount;
    }

    public BigDecimal getMonthlyAmount() {
        return monthlyAmount;
    }

    public void setMonthlyAmount(BigDecimal monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }

    public BigDecimal getLuggage() {
        return luggage;
    }

    public void setLuggage(BigDecimal luggage) {
        this.luggage = luggage;
    }

    public String getOpenInvoices() {
        return openInvoices;
    }

    public void setOpenInvoices(String openInvoices) {
        this.openInvoices = openInvoices;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }
}
