package com.xescm.ofc.domain;

import java.math.BigDecimal;
import java.util.Date;

public class OfcOrderOper {
	
	/*****ofc_fundamental_information******/

    private String orderCode;

    private String orderBatchNumber;

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

    private Date creationTime;

    private String creator;

    private String operator;

    private Date operTime;

    private String saleOrganization;

    private String productGroup;

    private String saleDepartment;

    private String saleGroup;

    private String saleDepartmentDesc;

    private String saleGroupDesc;

    private String creatorName;

    private String operatorName;

    private String abolisherName;

    private String merchandiser;

    private String transportType;

    /*******ofc_distribution_basic_info***/

    private String transCode;

    private Integer urgent;

    private String departurePlace;

    private String departureProvince;

    private String departureCity;

    private String departureDistrict;

    private String departureTowns;

    private String departurePlaceCode;

    private String destination;

    private String destinationProvince;

    private String destinationCity;

    private String destinationDistrict;

    private String destinationTowns;

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

    private String plateNumber;

    private String driverName;

    private String contactNumber;

    private String consignorContactName;

    private String consignorContactCode;

    private String consigneeContactName;

    private String consigneeContactCode;

    private String consignorType;

    private String consigneeType;

    private String baseId;

    private String consignorContactPhone;

    private String consigneeContactPhone;

    /********ofc_finance_information*********/
    private BigDecimal serviceCharge;

    private BigDecimal orderAmount;

    private BigDecimal paymentAmount;

    private BigDecimal collectLoanAmount;

    private BigDecimal collectServiceCharge;

    private String collectFlag;

    private String countFlag;

    private String printInvoice;

    private String buyerPaymentMethod;

    private String insure;

    private String insureValue;

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

    /********ofc_order_status*********/

    private String orderStatus;

    private String statusDesc;

    private Date lastedOperTime;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public String getOrderBatchNumber() {
        return orderBatchNumber;
    }

    public void setOrderBatchNumber(String orderBatchNumber) {
        this.orderBatchNumber = orderBatchNumber == null ? null : orderBatchNumber.trim();
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
        this.custCode = custCode == null ? null : custCode.trim();
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public String getSecCustCode() {
        return secCustCode;
    }

    public void setSecCustCode(String secCustCode) {
        this.secCustCode = secCustCode == null ? null : secCustCode.trim();
    }

    public String getSecCustName() {
        return secCustName;
    }

    public void setSecCustName(String secCustName) {
        this.secCustName = secCustName == null ? null : secCustName.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public String getCustOrderCode() {
        return custOrderCode;
    }

    public void setCustOrderCode(String custOrderCode) {
        this.custOrderCode = custOrderCode == null ? null : custOrderCode.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode == null ? null : storeCode.trim();
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName == null ? null : storeName.trim();
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType == null ? null : platformType.trim();
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource == null ? null : orderSource.trim();
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
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
        this.abolisher = abolisher == null ? null : abolisher.trim();
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public String getSaleOrganization() {
        return saleOrganization;
    }

    public void setSaleOrganization(String saleOrganization) {
        this.saleOrganization = saleOrganization == null ? null : saleOrganization.trim();
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup == null ? null : productGroup.trim();
    }

    public String getSaleDepartment() {
        return saleDepartment;
    }

    public void setSaleDepartment(String saleDepartment) {
        this.saleDepartment = saleDepartment == null ? null : saleDepartment.trim();
    }

    public String getSaleGroup() {
        return saleGroup;
    }

    public void setSaleGroup(String saleGroup) {
        this.saleGroup = saleGroup == null ? null : saleGroup.trim();
    }

    public String getSaleDepartmentDesc() {
        return saleDepartmentDesc;
    }

    public void setSaleDepartmentDesc(String saleDepartmentDesc) {
        this.saleDepartmentDesc = saleDepartmentDesc == null ? null : saleDepartmentDesc.trim();
    }

    public String getSaleGroupDesc() {
        return saleGroupDesc;
    }

    public void setSaleGroupDesc(String saleGroupDesc) {
        this.saleGroupDesc = saleGroupDesc == null ? null : saleGroupDesc.trim();
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName == null ? null : creatorName.trim();
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }

    public String getAbolisherName() {
        return abolisherName;
    }

    public void setAbolisherName(String abolisherName) {
        this.abolisherName = abolisherName == null ? null : abolisherName.trim();
    }

    public String getMerchandiser() {
        return merchandiser;
    }

    public void setMerchandiser(String merchandiser) {
        this.merchandiser = merchandiser == null ? null : merchandiser.trim();
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType == null ? null : transportType.trim();
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode == null ? null : transCode.trim();
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
        this.departurePlace = departurePlace == null ? null : departurePlace.trim();
    }

    public String getDepartureProvince() {
        return departureProvince;
    }

    public void setDepartureProvince(String departureProvince) {
        this.departureProvince = departureProvince == null ? null : departureProvince.trim();
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity == null ? null : departureCity.trim();
    }

    public String getDepartureDistrict() {
        return departureDistrict;
    }

    public void setDepartureDistrict(String departureDistrict) {
        this.departureDistrict = departureDistrict == null ? null : departureDistrict.trim();
    }

    public String getDepartureTowns() {
        return departureTowns;
    }

    public void setDepartureTowns(String departureTowns) {
        this.departureTowns = departureTowns == null ? null : departureTowns.trim();
    }

    public String getDeparturePlaceCode() {
        return departurePlaceCode;
    }

    public void setDeparturePlaceCode(String departurePlaceCode) {
        this.departurePlaceCode = departurePlaceCode == null ? null : departurePlaceCode.trim();
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination == null ? null : destination.trim();
    }

    public String getDestinationProvince() {
        return destinationProvince;
    }

    public void setDestinationProvince(String destinationProvince) {
        this.destinationProvince = destinationProvince == null ? null : destinationProvince.trim();
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity == null ? null : destinationCity.trim();
    }

    public String getDestinationDistrict() {
        return destinationDistrict;
    }

    public void setDestinationDistrict(String destinationDistrict) {
        this.destinationDistrict = destinationDistrict == null ? null : destinationDistrict.trim();
    }

    public String getDestinationTowns() {
        return destinationTowns;
    }

    public void setDestinationTowns(String destinationTowns) {
        this.destinationTowns = destinationTowns == null ? null : destinationTowns.trim();
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode == null ? null : destinationCode.trim();
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
        this.cubage = cubage == null ? null : cubage.trim();
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
        this.transRequire = transRequire == null ? null : transRequire.trim();
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
        this.consignorCode = consignorCode == null ? null : consignorCode.trim();
    }

    public String getConsignorName() {
        return consignorName;
    }

    public void setConsignorName(String consignorName) {
        this.consignorName = consignorName == null ? null : consignorName.trim();
    }

    public String getConsigneeCode() {
        return consigneeCode;
    }

    public void setConsigneeCode(String consigneeCode) {
        this.consigneeCode = consigneeCode == null ? null : consigneeCode.trim();
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName == null ? null : consigneeName.trim();
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

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber == null ? null : plateNumber.trim();
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName == null ? null : driverName.trim();
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber == null ? null : contactNumber.trim();
    }

    public String getConsignorContactName() {
        return consignorContactName;
    }

    public void setConsignorContactName(String consignorContactName) {
        this.consignorContactName = consignorContactName == null ? null : consignorContactName.trim();
    }

    public String getConsignorContactCode() {
        return consignorContactCode;
    }

    public void setConsignorContactCode(String consignorContactCode) {
        this.consignorContactCode = consignorContactCode == null ? null : consignorContactCode.trim();
    }

    public String getConsigneeContactName() {
        return consigneeContactName;
    }

    public void setConsigneeContactName(String consigneeContactName) {
        this.consigneeContactName = consigneeContactName == null ? null : consigneeContactName.trim();
    }

    public String getConsigneeContactCode() {
        return consigneeContactCode;
    }

    public void setConsigneeContactCode(String consigneeContactCode) {
        this.consigneeContactCode = consigneeContactCode == null ? null : consigneeContactCode.trim();
    }

    public String getConsignorType() {
        return consignorType;
    }

    public void setConsignorType(String consignorType) {
        this.consignorType = consignorType == null ? null : consignorType.trim();
    }

    public String getConsigneeType() {
        return consigneeType;
    }

    public void setConsigneeType(String consigneeType) {
        this.consigneeType = consigneeType == null ? null : consigneeType.trim();
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId == null ? null : baseId.trim();
    }

    public String getConsignorContactPhone() {
        return consignorContactPhone;
    }

    public void setConsignorContactPhone(String consignorContactPhone) {
        this.consignorContactPhone = consignorContactPhone == null ? null : consignorContactPhone.trim();
    }

    public String getConsigneeContactPhone() {
        return consigneeContactPhone;
    }

    public void setConsigneeContactPhone(String consigneeContactPhone) {
        this.consigneeContactPhone = consigneeContactPhone == null ? null : consigneeContactPhone.trim();
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
        this.collectFlag = collectFlag == null ? null : collectFlag.trim();
    }

    public String getCountFlag() {
        return countFlag;
    }

    public void setCountFlag(String countFlag) {
        this.countFlag = countFlag == null ? null : countFlag.trim();
    }

    public String getPrintInvoice() {
        return printInvoice;
    }

    public void setPrintInvoice(String printInvoice) {
        this.printInvoice = printInvoice == null ? null : printInvoice.trim();
    }

    public String getBuyerPaymentMethod() {
        return buyerPaymentMethod;
    }

    public void setBuyerPaymentMethod(String buyerPaymentMethod) {
        this.buyerPaymentMethod = buyerPaymentMethod == null ? null : buyerPaymentMethod.trim();
    }

    public String getInsure() {
        return insure;
    }

    public void setInsure(String insure) {
        this.insure = insure == null ? null : insure.trim();
    }

    public String getInsureValue() {
        return insureValue;
    }

    public void setInsureValue(String insureValue) {
        this.insureValue = insureValue == null ? null : insureValue.trim();
    }

    public String getPickUpGoods() {
        return pickUpGoods;
    }

    public void setPickUpGoods(String pickUpGoods) {
        this.pickUpGoods = pickUpGoods == null ? null : pickUpGoods.trim();
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
        this.twoDistribution = twoDistribution == null ? null : twoDistribution.trim();
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
        this.returnList = returnList == null ? null : returnList.trim();
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
        this.expensePaymentParty = expensePaymentParty == null ? null : expensePaymentParty.trim();
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment == null ? null : payment.trim();
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



    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc == null ? null : statusDesc.trim();
    }

    public Date getLastedOperTime() {
        return lastedOperTime;
    }

    public void setLastedOperTime(Date lastedOperTime) {
        this.lastedOperTime = lastedOperTime;
    }

}