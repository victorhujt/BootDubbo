package com.xescm.ofc.model.dto.wms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel(description = "调拨出库单实体类")
public class WhcTransShip {

    private String id;

    @ApiModelProperty(notes = "订单编码")
    private String shipNo;

    @ApiModelProperty(notes = "订单编码")
    private String orderNo;

    @ApiModelProperty(notes = "订单类型")
    private String shioType;

    @ApiModelProperty(notes = "订单类型")
    private String orderType;

    @ApiModelProperty(notes = "订单日期")
    private Date orderTime;

    @ApiModelProperty(notes = "流水号")
    private String whcBillno;

    @ApiModelProperty(notes = "来源平台")
    private String fromSystem;

    @ApiModelProperty(notes = "预期发货时间")
    private Date expectedShipmentTime;

    @ApiModelProperty(notes = "要求交货时间")
    private Date requiredDeliveryTime;

    @ApiModelProperty(notes = "客户编码")
    private String customerCode;

    @ApiModelProperty(notes = "仓库编码")
    private String warehouseCode;

    @ApiModelProperty(notes = "仓库名称")
    private String warehouseName;

    @ApiModelProperty(notes = "目标仓库编码")
    private String targetWarehouseCode;

    @ApiModelProperty(notes = "目标仓库名称")
    private String targetWarehouseName;

    @ApiModelProperty(notes = "承运商编码")
    private String carrierCode;

    @ApiModelProperty(notes = "承运商名称")
    private String carrierName;

    @ApiModelProperty(notes = "收货人编码")
    private String consigneeCode;

    @ApiModelProperty(notes = "收货人名称")
    private String consigneeName;

    @ApiModelProperty(notes = "国家")
    private String cCountry;

    @ApiModelProperty(notes = "省份")
    private String cProvince;

    @ApiModelProperty(notes = "城市")
    private String cCity;

    @ApiModelProperty(notes = "县")
    private String cDistrict;

    @ApiModelProperty(notes = "乡")
    private String cTown;

    @ApiModelProperty(notes = "手机号码")
    private String cTel1;

    @ApiModelProperty(notes = "固话号码")
    private String cTel2;

    @ApiModelProperty(notes = "邮编")
    private String cZip;

    @ApiModelProperty(notes = "Email")
    private String cMail;

    @ApiModelProperty(notes = "详细地址")
    private String cAddress1;

    @ApiModelProperty(notes = "备用地址")
    private String cAddress2;

    @ApiModelProperty(notes = "备用地址")
    private String cAddress3;

    private String userDefine1;

    private String userDefine2;

    private String userDefine3;

    private String userDefine4;

    private String userDefine5;

    private String userDefine6;

    @ApiModelProperty(notes = "备注")
    private String notes;

    @ApiModelProperty(notes = "操作时间")
    private Date createTime;

    @ApiModelProperty(notes = "是否打印发票")
    private String printinvoice;

    @ApiModelProperty(notes = "支付平台")
    private String payment;

    private Long total;

    private Long paid;

    private String payDeliveryFlag;

    private Long amountPayable;

    private String insuredFlag;

    private Long insuredMoney;

    private Long freight;

    private String fromAddressCode;

    private String toAdderessCode;

    private String channel;

    private String fromWarehouseCode;

    private List<WhcTransShipDetails> detailsList;


    @ApiModelProperty(notes = "WMS单号")
    private String wmsNo;

    public String getWmsNo() {
        return wmsNo;
    }

    public void setWmsNo(String wmsNo) {
        this.wmsNo = wmsNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getWhcBillno() {
        return whcBillno;
    }

    public void setWhcBillno(String whcBillno) {
        this.whcBillno = whcBillno;
    }

    public String getFromSystem() {
        return fromSystem;
    }

    public void setFromSystem(String fromSystem) {
        this.fromSystem = fromSystem;
    }

    public Date getExpectedShipmentTime() {
        return expectedShipmentTime;
    }

    public void setExpectedShipmentTime(Date expectedShipmentTime) {
        this.expectedShipmentTime = expectedShipmentTime;
    }

    public Date getRequiredDeliveryTime() {
        return requiredDeliveryTime;
    }

    public void setRequiredDeliveryTime(Date requiredDeliveryTime) {
        this.requiredDeliveryTime = requiredDeliveryTime;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getTargetWarehouseCode() {
        return targetWarehouseCode;
    }

    public void setTargetWarehouseCode(String targetWarehouseCode) {
        this.targetWarehouseCode = targetWarehouseCode;
    }

    public String getTargetWarehouseName() {
        return targetWarehouseName;
    }

    public void setTargetWarehouseName(String targetWarehouseName) {
        this.targetWarehouseName = targetWarehouseName;
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

    public String getcCountry() {
        return cCountry;
    }

    public void setcCountry(String cCountry) {
        this.cCountry = cCountry;
    }

    public String getcProvince() {
        return cProvince;
    }

    public void setcProvince(String cProvince) {
        this.cProvince = cProvince;
    }

    public String getcCity() {
        return cCity;
    }

    public void setcCity(String cCity) {
        this.cCity = cCity;
    }

    public String getcTel1() {
        return cTel1;
    }

    public void setcTel1(String cTel1) {
        this.cTel1 = cTel1;
    }

    public String getcTel2() {
        return cTel2;
    }

    public void setcTel2(String cTel2) {
        this.cTel2 = cTel2;
    }

    public String getcZip() {
        return cZip;
    }

    public void setcZip(String cZip) {
        this.cZip = cZip;
    }

    public String getcMail() {
        return cMail;
    }

    public void setcMail(String cMail) {
        this.cMail = cMail;
    }

    public String getcAddress1() {
        return cAddress1;
    }

    public void setcAddress1(String cAddress1) {
        this.cAddress1 = cAddress1;
    }

    public String getcAddress2() {
        return cAddress2;
    }

    public void setcAddress2(String cAddress2) {
        this.cAddress2 = cAddress2;
    }

    public String getcAddress3() {
        return cAddress3;
    }

    public void setcAddress3(String cAddress3) {
        this.cAddress3 = cAddress3;
    }

    public String getUserDefine1() {
        return userDefine1;
    }

    public void setUserDefine1(String userDefine1) {
        this.userDefine1 = userDefine1;
    }

    public String getUserDefine2() {
        return userDefine2;
    }

    public void setUserDefine2(String userDefine2) {
        this.userDefine2 = userDefine2;
    }

    public String getUserDefine3() {
        return userDefine3;
    }

    public void setUserDefine3(String userDefine3) {
        this.userDefine3 = userDefine3;
    }

    public String getUserDefine4() {
        return userDefine4;
    }

    public void setUserDefine4(String userDefine4) {
        this.userDefine4 = userDefine4;
    }

    public String getUserDefine5() {
        return userDefine5;
    }

    public void setUserDefine5(String userDefine5) {
        this.userDefine5 = userDefine5;
    }

    public String getUserDefine6() {
        return userDefine6;
    }

    public void setUserDefine6(String userDefine6) {
        this.userDefine6 = userDefine6;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<WhcTransShipDetails> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<WhcTransShipDetails> detailsList) {
        this.detailsList = detailsList;
    }

    public String getShipNo() {
        return shipNo;
    }

    public void setShipNo(String shipNo) {
        this.shipNo = shipNo;
    }

    public String getShioType() {
        return shioType;
    }

    public void setShioType(String shioType) {
        this.shioType = shioType;
    }

    public String getcDistrict() {
        return cDistrict;
    }

    public void setcDistrict(String cDistrict) {
        this.cDistrict = cDistrict;
    }

    public String getcTown() {
        return cTown;
    }

    public void setcTown(String cTown) {
        this.cTown = cTown;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPrintinvoice() {
        return printinvoice;
    }

    public void setPrintinvoice(String printinvoice) {
        this.printinvoice = printinvoice;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getPaid() {
        return paid;
    }

    public void setPaid(Long paid) {
        this.paid = paid;
    }

    public String getPayDeliveryFlag() {
        return payDeliveryFlag;
    }

    public void setPayDeliveryFlag(String payDeliveryFlag) {
        this.payDeliveryFlag = payDeliveryFlag;
    }

    public Long getAmountPayable() {
        return amountPayable;
    }

    public void setAmountPayable(Long amountPayable) {
        this.amountPayable = amountPayable;
    }

    public String getInsuredFlag() {
        return insuredFlag;
    }

    public void setInsuredFlag(String insuredFlag) {
        this.insuredFlag = insuredFlag;
    }

    public Long getInsuredMoney() {
        return insuredMoney;
    }

    public void setInsuredMoney(Long insuredMoney) {
        this.insuredMoney = insuredMoney;
    }

    public Long getFreight() {
        return freight;
    }

    public void setFreight(Long freight) {
        this.freight = freight;
    }

    public String getFromAddressCode() {
        return fromAddressCode;
    }

    public void setFromAddressCode(String fromAddressCode) {
        this.fromAddressCode = fromAddressCode;
    }

    public String getToAdderessCode() {
        return toAdderessCode;
    }

    public void setToAdderessCode(String toAdderessCode) {
        this.toAdderessCode = toAdderessCode;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getFromWarehouseCode() {
        return fromWarehouseCode;
    }

    public void setFromWarehouseCode(String fromWarehouseCode) {
        this.fromWarehouseCode = fromWarehouseCode;
    }
}