package com.xescm.ofc.model.dto.wms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel(description = "返厂出库单实体类")
public class WhcRework {
    private String id;

    @ApiModelProperty(notes = "订单号码",required = true)
    private String shipNo;

    @ApiModelProperty(notes = "订单号码",required = true)
    private String orderNo;

    @ApiModelProperty(notes = "订单类型",required = true)
    private String shioType;

    @ApiModelProperty(notes = "流水号",required = true)
    private String whcBillno;

    @ApiModelProperty(notes = "来源平台",required = true)
    private String fromSystem;

    @ApiModelProperty(notes = "订单类型",required = true)
    private String orderType;

    @ApiModelProperty(notes = "订单创建时间",required = true)
    private Date createTime;

    @ApiModelProperty(notes = "预期发货时间",required = true)
    private Date expectedShipmentTime;

    @ApiModelProperty(notes = "要求交货时间",required = true)
    private Date requiredDeliveryTime;

    @ApiModelProperty(notes = "仓库编码",required = true)
    private String warehouseCode;

    @ApiModelProperty(notes = "仓库名称",required = true)
    private String warehouseName;

    @ApiModelProperty(notes = "客户编码",required = true)
    private String customerCode;

    @ApiModelProperty(notes = "客户名称",required = true)
    private String customerName;

    @ApiModelProperty(notes = "承运商编码",required = true)
    private String carrierCode;

    @ApiModelProperty(notes = "承运商名称",required = true)
    private String carrierName;

    @ApiModelProperty(notes = "收货人编码",required = true)
    private String consigneeid;

    @ApiModelProperty(notes = "收货人名称",required = true)
    private String consigneeName;

    @ApiModelProperty(notes = "国家")
    private String cCountry;

    @ApiModelProperty(notes = "省份",required = true)
    private String cProvince;

    @ApiModelProperty(notes = "城市",required = true)
    private String cCity;

    @ApiModelProperty(notes = "县")
    private String cDistrict;

    @ApiModelProperty(notes = "乡")
    private String cTown;

    @ApiModelProperty(notes = "手机号码",required = true)
    private String cTel1;

    @ApiModelProperty(notes = "固话号码")
    private String cTel2;

    @ApiModelProperty(notes = "邮编")
    private String cZip;

    @ApiModelProperty(notes = "Email")
    private String cMail;

    @ApiModelProperty(notes = "详细地址",required = true)
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

    @ApiModelProperty(notes = "是否打印发票")
    private String printinvoice;

    @ApiModelProperty(notes = "支付方式",required = true)
    private String payment;

    @ApiModelProperty(notes = "总价",required = true)
    private Long total;

    @ApiModelProperty(notes = "已付金额",required = true)
    private Long paid;

    @ApiModelProperty(notes = "是否货到付款（Y、N）",required = true)
    private String payDeliveryFlag;

    @ApiModelProperty(notes = "应付金额",required = true)
    private Long amountPayable;

    @ApiModelProperty(notes = "是否保价（Y、N）",required = true)
    private String insuredFlag;

    @ApiModelProperty(notes = "保价金额")
    private Long insuredMoney;

    @ApiModelProperty(notes = "运费",required = true)
    private Long freight;

    @ApiModelProperty(notes = "寄件地代码")
    private String fromAddressCode;

    @ApiModelProperty(notes = "目的地代码")
    private String toAdderessCode;

    @ApiModelProperty(notes = "渠道")
    private String channel;

    @ApiModelProperty(notes = "来源仓库编号")
    private String fromWarehouseCode;

    @ApiModelProperty(notes = "目标仓库")
    private String toWarehouseCode;

    private List<WhcReworkDetails> detailsList;



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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getConsigneeid() {
        return consigneeid;
    }

    public void setConsigneeid(String consigneeid) {
        this.consigneeid = consigneeid;
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

    public List<WhcReworkDetails> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<WhcReworkDetails> detailsList) {
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

    public String getToWarehouseCode() {
        return toWarehouseCode;
    }

    public void setToWarehouseCode(String toWarehouseCode) {
        this.toWarehouseCode = toWarehouseCode;
    }
}