package com.xescm.ofc.model.dto.wms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel(description = "销售出库单实体类")
public class WhcSalesDelivery {

    private String id;

    @ApiModelProperty(notes = "订单号",required = true)
    private String shipNo;

    @ApiModelProperty(notes = "客户编码",required = true)
    private String customerCode;

    @ApiModelProperty(notes = "仓库编码",required = true)
    private String warehouseCode;

    @ApiModelProperty(notes = "流水号",required = true)
    private String whcBillno;

    @ApiModelProperty(notes = "来源平台",required = true)
    private String fromSystem;

    @ApiModelProperty(notes = "订单号(同shipNo)",required = true)
    private String orderNo;

    @ApiModelProperty(notes = "订单类型",required = true)
    private String shipType;

    @ApiModelProperty(notes = "订单类型(同shipType)",required = true)
    private String orderType;

    private Date createTime;

    @ApiModelProperty(notes = "2015-09-09 10:10:10")
    private Date expectedShipmentTime;

    @ApiModelProperty(notes = "2015-09-09 10:10:10")
    private Date requiredDeliveryTime;

    @ApiModelProperty(notes = "线路")
    private String routeCode;

    @ApiModelProperty(notes = "站点")
    private String stop;

    @ApiModelProperty(notes = "寄件地代码")
    private String fromAddressCodel;

    @ApiModelProperty(notes = "目的地代码")
    private String toAddressCodel;

    @ApiModelProperty(notes = "渠道")
    private String channel;

    @ApiModelProperty(notes = "来源仓库编号",required = true)
    private String fromWarehouseCode;

    @ApiModelProperty(notes = "目标仓库编号",required = true)
    private String toWarehouseCode;

    @ApiModelProperty(notes = "承运商编码",required = true)
    private String carrierCode;

    @ApiModelProperty(notes = "承运商名称",required = true)
    private String carrierName;

    @ApiModelProperty(notes = "平台订单号",required = true)
    private String platformOrderNo;

    @ApiModelProperty(notes = "店铺名称",required = true)
    private String storeName;

    @ApiModelProperty(notes = "快递单号",required = true)
    private String deliveryNo;

    @ApiModelProperty(notes = "收货人编码",required = true)
    private String consigneeCode;

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

    @ApiModelProperty(notes = "固话号码",required = true)
    private String cTel2;

    @ApiModelProperty(notes = "邮编",required = true)
    private String cZip;

    @ApiModelProperty(notes = "旺旺号",required = true)
    private String cMail;

    @ApiModelProperty(notes = "详细地址",required = true)
    private String cAddress1;

    @ApiModelProperty(notes = "备用地址")
    private String cAddress2;

    @ApiModelProperty(notes = "备用地址")
    private String cAddress3;

    @ApiModelProperty(notes = "平台发货仓库")
    private String deliveryWare;

    @ApiModelProperty(notes = "客服备注")
    private String serviceNotes;

    @ApiModelProperty(notes = "备注（顾客留言）")
    private String notes;

    @ApiModelProperty(notes = "是否打印发票")
    private String printinvoice;

    @ApiModelProperty(notes = "支付方式")
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

    @ApiModelProperty(notes = "运费")
    private Long freight;

    private List<WhcSalesDeliveryDetails> detailsList;

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

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public String getFromAddressCodel() {
        return fromAddressCodel;
    }

    public void setFromAddressCodel(String fromAddressCodel) {
        this.fromAddressCodel = fromAddressCodel;
    }

    public String getToAddressCodel() {
        return toAddressCodel;
    }

    public void setToAddressCodel(String toAddressCodel) {
        this.toAddressCodel = toAddressCodel;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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

    public String getPlatformOrderNo() {
        return platformOrderNo;
    }

    public void setPlatformOrderNo(String platformOrderNo) {
        this.platformOrderNo = platformOrderNo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
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

    public String getDeliveryWare() {
        return deliveryWare;
    }

    public void setDeliveryWare(String deliveryWare) {
        this.deliveryWare = deliveryWare;
    }

    public String getServiceNotes() {
        return serviceNotes;
    }

    public void setServiceNotes(String serviceNotes) {
        this.serviceNotes = serviceNotes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<WhcSalesDeliveryDetails> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<WhcSalesDeliveryDetails> detailsList) {
        this.detailsList = detailsList;
    }

    public String getShipNo() {
        return shipNo;
    }

    public void setShipNo(String shipNo) {
        this.shipNo = shipNo;
    }

    public String getShipType() {
        return shipType;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
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
}