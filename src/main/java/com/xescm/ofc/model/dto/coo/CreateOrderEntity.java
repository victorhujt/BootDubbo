package com.xescm.ofc.model.dto.coo;

import com.xescm.ofc.utils.DateUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单中心创建订单（鲜易网）
 * 订单主表信息
 * Created by hiyond on 2016/11/14.
 */
public class CreateOrderEntity {

    /**
     * 客户订单编号 Y
     * 传入对应平台的订单编号
     */
    private String custOrderCode;

    /**
     * 订单日期 Y
     * 订单日期
     */
    private String orderTime;

    /**
     * 货主编码 Y
     * 固定平台客户的代码(确定接入后分配)
     */
    private String custCode;

    /**
     * 货主名称 Y
     * 固定平台客户的公司名称
     */
    private String custName;

    /**
     *二级客户编码
     */
    private String secCustCode;

    /**
     * 二级客户名称
     */
    private String secCustName;

    /**
     * 订单类型 Y
     * 60-运输订单, 仅运输业务
     * 61-仓配订单，有合作仓同时配送业务
     */
    private String orderType;

    /**
     * 业务类型 Y
     * 订单类型：【60】
     * 业务类型：600-【城配】
     * 601-【干线】
     * 订单类型：【61】
     * 业务类型:610-【销售出库】
     * 611-【调拨出库】
     * 612-【报损出库】
     * 613-【其他出库】
     * 620-【采购入库】
     * 621-【调拨入库】
     * 622-【退货入库】
     * 623-【加工入库】
     */
    private String businessType;

    /**
     * 运输类型
     */
    private String transportType;

    /**
     * 备注 Y
     */
    private String notes;

    /**
     * 店铺编码 Y
     * 在工作台维护，客户方系统店铺编码，用于区分渠道,传空为默认
     */
    private String storeCode;

    /**
     * 平台类型
     */
    private String platformType;

    /**
     * 订单来源 Y
     * 【平台、EDI、手工录入】
     */
    private String orderSource;

    /**
     * 销售组织
     * SAP专用
     */
    private String expandSaleOrg;

    /**
     * 产品组
     * SAP专用
     */
    private String expandProGroup;

    /**
     * 销售部门
     * SAP专用
     */
    private String expandSaleDep;

    /**
     * 销售组
     * SAP专用
     */
    private String expandSaleGroup;

    /**
     * 销售部门描述
     * SAP专用
     */
    private String expandSaleDepDes;

    /**
     * 销售组描述
     * SAP专用
     */
    private String expandSaleGroupDes;

    /**
     * 数量
     * 【数量、重量、体积】三选一，运输订单时判断
     */
    private String quantity;

    /**
     * 重量
     * 【数量、重量、体积】三选一，运输订单时判断
     */
    private String weight;

    /**
     * 体积
     * 【数量、重量、体积】三选一，运输订单时判断
     */
    private String cubage;

    /**
     * 合计标准箱
     * 最大值9999
     */
    private String totalStandardBox;

    /**
     * 运输要求
     */
    private String transRequire;

    /**
     * 取货时间
     */
    private String pickupTime;

    /**
     * 期望送货时间
     */
    private String expectedArrivedTime;

    /**
     * 发货方编码
     */
    private String consignorCode;

    /**
     * 发货方名称
     */
    private String consignorName;

    /**
     * 发货方联系人
     */
    private String consignorContact;

    /**
     * 发货方联系电话
     */
    private String consignorPhone;

    /**
     * 发货方传真
     */
    private String consignorFax;

    /**
     * 发货方Email
     */
    private String consignorEmail;

    /**
     * 发货方邮编
     */
    private String consignorZip;

    /**
     * 发货方省（汉字）
     */
    private String consignorProvince;

    /**
     * 发货方省（编码）
     */
    private String consignorProvinceCode;

    /**
     * 发货方市（汉字）
     */
    private String consignorCity;

    /**
     * 发货方市（编码）
     */
    private String consignorCityCode;

    /**
     * 发货方区县（汉字）
     */
    private String consignorCounty;

    /**
     * 发货方区县（编码）
     */
    private String consignorCountyCode;

    /**
     * 发货方乡镇街道
     */
    private String consignorTown;

    /**
     * 发货方乡镇街道(编码)
     */
    private String consignorTownCode;

    /**
     * 发货方地址
     */
    private String consignorAddress;

    /**
     * 收货方编码
     */
    private String consigneeCode;
    /**
     * 收货方名称
     */
    private String consigneeName;

    /**
     * 收货方联系人
     */
    private String consigneeContact;

    /**
     * 收货方联系电话
     */
    private String consigneePhone;

    /**
     * 收货方传真
     */
    private String consigneeFax;

    /**
     * 收货方email
     */
    private String consigneeEmail;

    /**
     * 收货方邮编
     */
    private String consigneeZip;

    /**
     * 收货方省
     */
    private String consigneeProvince;
    /**
     * 收货方省(编码)
     */
    private String consigneeProvinceCode;

    /**
     * 收货方市
     */
    private String consigneeCity;

    /**
     * 收货方市(编码)
     */
    private String consigneeCityCode;

    /**
     * 收货方区县
     */
    private String consigneeCounty;

    /**
     * 收货方区县 (编码)
     */
    private String consigneeCountyCode;

    /**
     * 收货方乡镇街道
     */
    private String consigneeTown;

    /**
     * 收货方乡镇街道(编码)
     */
    private String consigneeTownCode;

    /**
     * 收货方地址
     */
    private String consigneeAddress;

    /**
     * 仓库编码
     * 在平台的仓库编码
     */
    private String warehouseCode;

    /**
     * 仓库名称
     */
    private String warehouseName;

    /**
     * 是否需要提供运输
     * 0-否 1-是
     */
    private String provideTransport;

    /**
     * 供应商名称
     */
    private String supportName;
    /**
     * 供应商编码
     */
    private String supportCode;

    /**
     * 供应商联系人
     */
    private String supportContact;

    /**
     * 供应商联系电话
     */
    private String supportPhone;

    /**
     * 供应商传真
     */
    private String supportFax;

    /**
     * 供应商Email
     */
    private String supportEmail;

    /**
     * 供应商邮编
     */
    private String supportZip;

    /**
     * 供应商省
     */
    private String supportProvince;

    /**
     * 供应商市
     */
    private String supportCity;

    /**
     * 供应商区县
     */
    private String supportCounty;

    /**
     * 供应商街道
     */
    private String supportTown;

    /**
     * 供应商地址
     */
    private String supportAddress;

    /**
     * 入库预计到达时间
     */
    private String arriveTime;

    /**
     * 车牌号
     */
    private String plateNumber;

    /**
     * 司机姓名
     * 入库或出库自带车辆
     */
    private String driverName;

    /**
     * 联系电话
     * 入库或出库自带车辆
     */
    private String contactNumber;

    /**
     * 服务费
     * 平台运费
     */
    private String serviceCharge;

    /**
     * 订单金额
     */
    private String orderAmount;

    /**
     * 货款金额
     */
    private String paymentAmount;

    /**
     * 代收货款金额
     */
    private String collectLoanAmount;

    /**
     * 代收服务费
     */
    private String collectServiceCharge;

    /**
     * 代收标记
     */
    private String collectFlag;

    /**
     * 是否打印发票(电商)
     */
    private String printInvoice;

    /**
     * 买家支付方式(电商)
     */
    private String buyerPaymentMe;

    /**
     * 是否保价(电商)
     */
    private String insure;

    /**
     * 保价金额(电商)
     */
    private BigDecimal insureValue;

    /**
     * 基地ID(电商)
     */
    private String BaseId;

    /**
     * 基地编码
     */
    private String baseCode;

    /**
     * 基地名称
     */
    private String baseName;

    /**
     * 大区编码
     */
    private String areaCode;

    /**
     * 大区名称
     */
    private String areaName;

    /**
     * API订单货品明细
     */
    private List<CreateOrderGoodsInfo> createOrderGoodsInfos;

    /**
     * 是否进行二次配送
     * "1"是, "0"否
     */
    private String twoDistribution;

    /**
     * 是否进行上门提货
     * "1"是, "0"否
     */
    private String pickUpGoods;


    public String getTwoDistribution() {
        return twoDistribution;
    }

    public void setTwoDistribution(String twoDistribution) {
        this.twoDistribution = twoDistribution;
    }

    public String getPickUpGoods() {
        return pickUpGoods;
    }

    public void setPickUpGoods(String pickUpGoods) {
        this.pickUpGoods = pickUpGoods;
    }

    public String getConsignorProvinceCode() {
        return consignorProvinceCode;
    }

    public void setConsignorProvinceCode(String consignorProvinceCode) {
        this.consignorProvinceCode = consignorProvinceCode;
    }

    public String getConsignorCityCode() {
        return consignorCityCode;
    }

    public void setConsignorCityCode(String consignorCityCode) {
        this.consignorCityCode = consignorCityCode;
    }

    public String getConsignorCountyCode() {
        return consignorCountyCode;
    }

    public void setConsignorCountyCode(String consignorCountyCode) {
        this.consignorCountyCode = consignorCountyCode;
    }

    public String getConsignorTownCode() {
        return consignorTownCode;
    }

    public void setConsignorTownCode(String consignorTownCode) {
        this.consignorTownCode = consignorTownCode;
    }

    public String getConsigneeProvinceCode() {
        return consigneeProvinceCode;
    }

    public void setConsigneeProvinceCode(String consigneeProvinceCode) {
        this.consigneeProvinceCode = consigneeProvinceCode;
    }

    public String getConsigneeCityCode() {
        return consigneeCityCode;
    }

    public void setConsigneeCityCode(String consigneeCityCode) {
        this.consigneeCityCode = consigneeCityCode;
    }

    public String getConsigneeCountyCode() {
        return consigneeCountyCode;
    }

    public void setConsigneeCountyCode(String consigneeCountyCode) {
        this.consigneeCountyCode = consigneeCountyCode;
    }

    public String getConsigneeTownCode() {
        return consigneeTownCode;
    }

    public void setConsigneeTownCode(String consigneeTownCode) {
        this.consigneeTownCode = consigneeTownCode;
    }

    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getCustOrderCode() {
        return custOrderCode;
    }

    public void setCustOrderCode(String custOrderCode) {
        this.custOrderCode = custOrderCode;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = DateUtils.dateSubStringGetYMD(orderTime);
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

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getExpandSaleOrg() {
        return expandSaleOrg;
    }

    public void setExpandSaleOrg(String expandSaleOrg) {
        this.expandSaleOrg = expandSaleOrg;
    }

    public String getExpandProGroup() {
        return expandProGroup;
    }

    public void setExpandProGroup(String expandProGroup) {
        this.expandProGroup = expandProGroup;
    }

    public String getExpandSaleDep() {
        return expandSaleDep;
    }

    public void setExpandSaleDep(String expandSaleDep) {
        this.expandSaleDep = expandSaleDep;
    }

    public String getExpandSaleGroup() {
        return expandSaleGroup;
    }

    public void setExpandSaleGroup(String expandSaleGroup) {
        this.expandSaleGroup = expandSaleGroup;
    }

    public String getExpandSaleDepDes() {
        return expandSaleDepDes;
    }

    public void setExpandSaleDepDes(String expandSaleDepDes) {
        this.expandSaleDepDes = expandSaleDepDes;
    }

    public String getExpandSaleGroupDes() {
        return expandSaleGroupDes;
    }

    public void setExpandSaleGroupDes(String expandSaleGroupDes) {
        this.expandSaleGroupDes = expandSaleGroupDes;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCubage() {
        return cubage;
    }

    public void setCubage(String cubage) {
        this.cubage = cubage;
    }

    public String getTotalStandardBox() {
        return totalStandardBox;
    }

    public void setTotalStandardBox(String totalStandardBox) {
        this.totalStandardBox = totalStandardBox;
    }

    public String getTransRequire() {
        return transRequire;
    }

    public void setTransRequire(String transRequire) {
        this.transRequire = transRequire;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getExpectedArrivedTime() {
        return expectedArrivedTime;
    }

    public void setExpectedArrivedTime(String expectedArrivedTime) {
        this.expectedArrivedTime = expectedArrivedTime;
    }

    public String getConsignorName() {
        return consignorName;
    }

    public void setConsignorName(String consignorName) {
        this.consignorName = consignorName;
    }

    public String getConsignorContact() {
        return consignorContact;
    }

    public void setConsignorContact(String consignorContact) {
        this.consignorContact = consignorContact;
    }

    public String getConsignorPhone() {
        return consignorPhone;
    }

    public void setConsignorPhone(String consignorPhone) {
        this.consignorPhone = consignorPhone;
    }

    public String getConsignorFax() {
        return consignorFax;
    }

    public void setConsignorFax(String consignorFax) {
        this.consignorFax = consignorFax;
    }

    public String getConsignorEmail() {
        return consignorEmail;
    }

    public void setConsignorEmail(String consignorEmail) {
        this.consignorEmail = consignorEmail;
    }

    public String getConsignorZip() {
        return consignorZip;
    }

    public void setConsignorZip(String consignorZip) {
        this.consignorZip = consignorZip;
    }

    public String getConsignorProvince() {
        return consignorProvince;
    }

    public void setConsignorProvince(String consignorProvince) {
        this.consignorProvince = consignorProvince;
    }

    public String getConsignorCity() {
        return consignorCity;
    }

    public void setConsignorCity(String consignorCity) {
        this.consignorCity = consignorCity;
    }

    public String getConsignorCounty() {
        return consignorCounty;
    }

    public void setConsignorCounty(String consignorCounty) {
        this.consignorCounty = consignorCounty;
    }

    public String getConsignorTown() {
        return consignorTown;
    }

    public void setConsignorTown(String consignorTown) {
        this.consignorTown = consignorTown;
    }

    public String getConsignorAddress() {
        return consignorAddress;
    }

    public void setConsignorAddress(String consignorAddress) {
        this.consignorAddress = consignorAddress;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneeContact() {
        return consigneeContact;
    }

    public void setConsigneeContact(String consigneeContact) {
        this.consigneeContact = consigneeContact;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public String getConsigneeFax() {
        return consigneeFax;
    }

    public void setConsigneeFax(String consigneeFax) {
        this.consigneeFax = consigneeFax;
    }

    public String getConsigneeEmail() {
        return consigneeEmail;
    }

    public void setConsigneeEmail(String consigneeEmail) {
        this.consigneeEmail = consigneeEmail;
    }

    public String getConsigneeZip() {
        return consigneeZip;
    }

    public void setConsigneeZip(String consigneeZip) {
        this.consigneeZip = consigneeZip;
    }

    public String getConsigneeProvince() {
        return consigneeProvince;
    }

    public void setConsigneeProvince(String consigneeProvince) {
        this.consigneeProvince = consigneeProvince;
    }

    public String getConsigneeCity() {
        return consigneeCity;
    }

    public void setConsigneeCity(String consigneeCity) {
        this.consigneeCity = consigneeCity;
    }

    public String getConsigneeCounty() {
        return consigneeCounty;
    }

    public void setConsigneeCounty(String consigneeCounty) {
        this.consigneeCounty = consigneeCounty;
    }

    public String getConsigneeTown() {
        return consigneeTown;
    }

    public void setConsigneeTown(String consigneeTown) {
        this.consigneeTown = consigneeTown;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
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

    public String getProvideTransport() {
        return provideTransport;
    }

    public void setProvideTransport(String provideTransport) {
        this.provideTransport = provideTransport;
    }

    public String getSupportName() {
        return supportName;
    }

    public void setSupportName(String supportName) {
        this.supportName = supportName;
    }

    public String getSupportContact() {
        return supportContact;
    }

    public void setSupportContact(String supportContact) {
        this.supportContact = supportContact;
    }

    public String getSupportPhone() {
        return supportPhone;
    }

    public void setSupportPhone(String supportPhone) {
        this.supportPhone = supportPhone;
    }

    public String getSupportFax() {
        return supportFax;
    }

    public void setSupportFax(String supportFax) {
        this.supportFax = supportFax;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    public String getSupportZip() {
        return supportZip;
    }

    public void setSupportZip(String supportZip) {
        this.supportZip = supportZip;
    }

    public String getSupportProvince() {
        return supportProvince;
    }

    public void setSupportProvince(String supportProvince) {
        this.supportProvince = supportProvince;
    }

    public String getSupportCity() {
        return supportCity;
    }

    public void setSupportCity(String supportCity) {
        this.supportCity = supportCity;
    }

    public String getSupportCounty() {
        return supportCounty;
    }

    public void setSupportCounty(String supportCounty) {
        this.supportCounty = supportCounty;
    }

    public String getSupportTown() {
        return supportTown;
    }

    public void setSupportTown(String supportTown) {
        this.supportTown = supportTown;
    }

    public String getSupportAddress() {
        return supportAddress;
    }

    public void setSupportAddress(String supportAddress) {
        this.supportAddress = supportAddress;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
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

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getCollectLoanAmount() {
        return collectLoanAmount;
    }

    public void setCollectLoanAmount(String collectLoanAmount) {
        this.collectLoanAmount = collectLoanAmount;
    }

    public String getCollectServiceCharge() {
        return collectServiceCharge;
    }

    public void setCollectServiceCharge(String collectServiceCharge) {
        this.collectServiceCharge = collectServiceCharge;
    }

    public String getCollectFlag() {
        return collectFlag;
    }

    public void setCollectFlag(String collectFlag) {
        this.collectFlag = collectFlag;
    }

    public String getPrintInvoice() {
        return printInvoice;
    }

    public void setPrintInvoice(String printInvoice) {
        this.printInvoice = printInvoice;
    }

    public String getBuyerPaymentMe() {
        return buyerPaymentMe;
    }

    public void setBuyerPaymentMe(String buyerPaymentMe) {
        this.buyerPaymentMe = buyerPaymentMe;
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

    public String getBaseId() {
        return BaseId;
    }

    public void setBaseId(String baseId) {
        BaseId = baseId;
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

    public String getSupportCode() {
        return supportCode;
    }

    public void setSupportCode(String supportCode) {
        this.supportCode = supportCode;
    }

    public List<CreateOrderGoodsInfo> getCreateOrderGoodsInfos() {
        return createOrderGoodsInfos;
    }

    public void setCreateOrderGoodsInfos(List<CreateOrderGoodsInfo> createOrderGoodsInfos) {
        this.createOrderGoodsInfos = createOrderGoodsInfos;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getConsignorCode() {
        return consignorCode;
    }

    public void setConsignorCode(String consignorCode) {
        this.consignorCode = consignorCode;
    }

    public String getConsigneeCode() {
        return consigneeCode;
    }

    public void setConsigneeCode(String consigneeCode) {
        this.consigneeCode = consigneeCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }
}
