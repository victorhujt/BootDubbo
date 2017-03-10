package com.xescm.ofc.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
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

}