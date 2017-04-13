package com.xescm.ofc.model.dto.tfc;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TfcTransport implements Serializable {

    private Integer id;

    private String fromSystem;//系统来源

    private String transportNo;//运输单号
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;//创建时间

    private String status;//状态

    private String billType;//运单类型

    private String itemType;//商品类型

    private String customerCode;//客户编码

    private String customerName;//客户名称

    private String customerTel;//客户电话

    private String fromTransportName;//运输单产生机构
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date expectedShipmentTime;//预计发货时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date expectedArriveTime;//预计到达时间

    private Double weight;//重量

    private Double qty;//数量

    private Double volume;//体积

    private Double money;//金额

    private String fromCustomerCode;//发货客户编码

    private String fromCustomerName;//发货客户联系人

    private String fromCustomerNameCode;//发货客户联系人编码

    private String fromCustomerAddress;//发货客户地址

    private String fromCustomer;//发货客户

    private String fromCustomerTle;//发货客户电话

    private String fromProvince;//发货省份

    private String fromCity;//发货城市

    private String fromDistrict;//发货县区

    private String fromTown;//发货乡镇

    private String toCustomerCode;//收货客户编码

    private String toCustomerName;//收货客户名称

    private String toCustomerNameCode;//收货客户编码

    private String toCustomerAddress;//收货客户地址

    private String toCustomer;//收货客户

    private String toCustomerTle;//收货客户电话

    private String toProvince;//收货省份

    private String toCity;//收货城市

    private String toDistrict;//收货县区

    private String toTown;//收货乡镇

    private String toLon;//收货经度

    private String toLat;//收货纬度

    private String wareHouesCode;//仓库编码

    private String deliveryNo;//调度单号

    private String notes;//备注

    private String marketOrg;//销售组织

    private String productTeam;//产品组

    private String marketDep;//销售部门

    private String marketTeam;//销售组

    private String marketDepDes;//销售部门描述

    private String marketTeamDes;//销售组描述

    private String transportSource;//运单来源

    private String baseName;//基地名称

    private List<TfcTransportDetail> productDetail;

    private String interfaceStatus = "待处理";

    private String customerOrderCode;//客户订单号
    //以下为新加属性
    private String orderCode;//订单编号

    private String orderBatchNumber;//订单批次号

    private String programSerialNumber;//计划序号

    private String destinationCode;//目的地区域编码

    private BigDecimal serviceCharge;//服务费用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderTime;//日期

    private String createPersonnel;//创建人员

    private String voidPersonnel;//作废人员
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date voidTime;//作废时间

    private String merchandiser;//开单员

    private String businessType;//类型

    private String goodsTypeName;//货品种类名称

    private String twoDistribution;//是否二次配送

    private String transportPool;//运力池

    private String matchingMode;//匹配方式

    private String schedulingState;//调度状态

    private String transportPoolName;//运力池名称

    private String fromProvinceCode;//出发省份编码

    private String fromCityCode;//出发城市编码

    private String fromDistrictCode;//出发县区编码

    private String toProvinceCode;//到达省份编码

    private String toCityCode;//到达城市编码

    private String toDistrictCode;//到达县区编码

    private String faceOrder;

    /**
     * 运费
     */
    private BigDecimal luggage;

    /**
     * 代收服务费
     */
    private BigDecimal collectServiceCharge;

    private String transportType;

    //基地编码
    private String baseCode;
    //基地名称
    private String baseDesignation;
    //大区编码
    private String areaCode;
    //大区名称
    private String areaName;
    /**
     * 取货时间
     */

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "pickup_time")
    private Date pickupTime;


    public Date getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(Date pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }

    public String getBaseDesignation() {
        return baseDesignation;
    }

    public void setBaseDesignation(String baseDesignation) {
        this.baseDesignation = baseDesignation;
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

    public String getCustomerOrderCode() {
        return customerOrderCode;
    }

    public void setCustomerOrderCode(String customerOrderCode) {
        this.customerOrderCode = customerOrderCode;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getInterfaceStatus() {
        return interfaceStatus;
    }

    public void setInterfaceStatus(String interfaceStatus) {
        this.interfaceStatus = interfaceStatus;
    }

    public String getMarketOrg() {
        return marketOrg;
    }

    public void setMarketOrg(String marketOrg) {
        this.marketOrg = marketOrg;
    }

    public String getProductTeam() {
        return productTeam;
    }

    public void setProductTeam(String productTeam) {
        this.productTeam = productTeam;
    }

    public String getMarketDep() {
        return marketDep;
    }

    public void setMarketDep(String marketDep) {
        this.marketDep = marketDep;
    }

    public String getMarketTeam() {
        return marketTeam;
    }

    public void setMarketTeam(String marketTeam) {
        this.marketTeam = marketTeam;
    }

    public String getMarketDepDes() {
        return marketDepDes;
    }

    public void setMarketDepDes(String marketDepDes) {
        this.marketDepDes = marketDepDes;
    }

    public String getMarketTeamDes() {
        return marketTeamDes;
    }

    public void setMarketTeamDes(String marketTeamDes) {
        this.marketTeamDes = marketTeamDes;
    }

    public String getTransportSource() {
        return transportSource;
    }

    public void setTransportSource(String transportSource) {
        this.transportSource = transportSource;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFromSystem() {
        return fromSystem;
    }

    public void setFromSystem(String fromSystem) {
        this.fromSystem = fromSystem == null ? null : fromSystem.trim();
    }

    public String getTransportNo() {
        return transportNo;
    }

    public void setTransportNo(String transportNo) {
        this.transportNo = transportNo == null ? null : transportNo.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType == null ? null : billType.trim();
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType == null ? null : itemType.trim();
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode == null ? null : customerCode.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel == null ? null : customerTel.trim();
    }

    public String getFromCustomerNameCode() {
        return fromCustomerNameCode;
    }

    public void setFromCustomerNameCode(String fromCustomerNameCode) {
        this.fromCustomerNameCode = fromCustomerNameCode;
    }

    public String getToCustomerNameCode() {
        return toCustomerNameCode;
    }

    public void setToCustomerNameCode(String toCustomerNameCode) {
        this.toCustomerNameCode = toCustomerNameCode;
    }

    public String getFromTransportName() {
        return fromTransportName;
    }

    public void setFromTransportName(String fromTransportName) {
        this.fromTransportName = fromTransportName == null ? null : fromTransportName.trim();
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

    public Date getExpectedArriveTime() {
        return expectedArriveTime;
    }

    public void setExpectedArriveTime(Date expectedArriveTime) {
        this.expectedArriveTime = expectedArriveTime;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getFromCustomerCode() {
        return fromCustomerCode;
    }

    public void setFromCustomerCode(String fromCustomerCode) {
        this.fromCustomerCode = fromCustomerCode == null ? null : fromCustomerCode.trim();
    }

    public String getFromCustomerName() {
        return fromCustomerName;
    }

    public void setFromCustomerName(String fromCustomerName) {
        this.fromCustomerName = fromCustomerName == null ? null : fromCustomerName.trim();
    }

    public String getFromCustomerAddress() {
        return fromCustomerAddress;
    }

    public void setFromCustomerAddress(String fromCustomerAddress) {
        this.fromCustomerAddress = fromCustomerAddress == null ? null : fromCustomerAddress.trim();
    }

    public String getFromCustomer() {
        return fromCustomer;
    }

    public void setFromCustomer(String fromCustomer) {
        this.fromCustomer = fromCustomer == null ? null : fromCustomer.trim();
    }

    public String getFromCustomerTle() {
        return fromCustomerTle;
    }

    public void setFromCustomerTle(String fromCustomerTle) {
        this.fromCustomerTle = fromCustomerTle == null ? null : fromCustomerTle.trim();
    }

    public String getFromProvince() {
        return fromProvince;
    }

    public void setFromProvince(String fromProvince) {
        this.fromProvince = fromProvince == null ? null : fromProvince.trim();
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity == null ? null : fromCity.trim();
    }

    public String getFromDistrict() {
        return fromDistrict;
    }

    public void setFromDistrict(String fromDistrict) {
        this.fromDistrict = fromDistrict == null ? null : fromDistrict.trim();
    }

    public String getFromTown() {
        return fromTown;
    }

    public void setFromTown(String fromTown) {
        this.fromTown = fromTown == null ? null : fromTown.trim();
    }

    public String getToCustomerCode() {
        return toCustomerCode;
    }

    public void setToCustomerCode(String toCustomerCode) {
        this.toCustomerCode = toCustomerCode == null ? null : toCustomerCode.trim();
    }

    public String getToCustomerName() {
        return toCustomerName;
    }

    public void setToCustomerName(String toCustomerName) {
        this.toCustomerName = toCustomerName == null ? null : toCustomerName.trim();
    }

    public String getToCustomerAddress() {
        return toCustomerAddress;
    }

    public void setToCustomerAddress(String toCustomerAddress) {
        this.toCustomerAddress = toCustomerAddress == null ? null : toCustomerAddress.trim();
    }

    public String getToCustomer() {
        return toCustomer;
    }

    public void setToCustomer(String toCustomer) {
        this.toCustomer = toCustomer == null ? null : toCustomer.trim();
    }

    public String getToCustomerTle() {
        return toCustomerTle;
    }

    public void setToCustomerTle(String toCustomerTle) {
        this.toCustomerTle = toCustomerTle == null ? null : toCustomerTle.trim();
    }

    public String getToProvince() {
        return toProvince;
    }

    public void setToProvince(String toProvince) {
        this.toProvince = toProvince == null ? null : toProvince.trim();
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity == null ? null : toCity.trim();
    }

    public String getToDistrict() {
        return toDistrict;
    }

    public void setToDistrict(String toDistrict) {
        this.toDistrict = toDistrict == null ? null : toDistrict.trim();
    }

    public String getToTown() {
        return toTown;
    }

    public void setToTown(String toTown) {
        this.toTown = toTown == null ? null : toTown.trim();
    }

    public String getToLon() {
        return toLon;
    }

    public void setToLon(String toLon) {
        this.toLon = toLon == null ? null : toLon.trim();
    }

    public String getToLat() {
        return toLat;
    }

    public void setToLat(String toLat) {
        this.toLat = toLat == null ? null : toLat.trim();
    }

    public String getWareHouesCode() {
        return wareHouesCode;
    }

    public void setWareHouesCode(String wareHouesCode) {
        this.wareHouesCode = wareHouesCode == null ? null : wareHouesCode.trim();
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public List<TfcTransportDetail> getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(List<TfcTransportDetail> productDetail) {
        this.productDetail = productDetail;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderBatchNumber() {
        return orderBatchNumber;
    }

    public void setOrderBatchNumber(String orderBatchNumber) {
        this.orderBatchNumber = orderBatchNumber;
    }

    public String getProgramSerialNumber() {
        return programSerialNumber;
    }

    public void setProgramSerialNumber(String programSerialNumber) {
        this.programSerialNumber = programSerialNumber;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getCreatePersonnel() {
        return createPersonnel;
    }

    public void setCreatePersonnel(String createPersonnel) {
        this.createPersonnel = createPersonnel;
    }

    public String getVoidPersonnel() {
        return voidPersonnel;
    }

    public void setVoidPersonnel(String voidPersonnel) {
        this.voidPersonnel = voidPersonnel;
    }

    public Date getVoidTime() {
        return voidTime;
    }

    public void setVoidTime(Date voidTime) {
        this.voidTime = voidTime;
    }

    public String getMerchandiser() {
        return merchandiser;
    }

    public void setMerchandiser(String merchandiser) {
        this.merchandiser = merchandiser;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public String getTwoDistribution() {
        return twoDistribution;
    }

    public void setTwoDistribution(String twoDistribution) {
        this.twoDistribution = twoDistribution;
    }

    public String getTransportPool() {
        return transportPool;
    }

    public void setTransportPool(String transportPool) {
        this.transportPool = transportPool;
    }

    public String getMatchingMode() {
        return matchingMode;
    }

    public void setMatchingMode(String matchingMode) {
        this.matchingMode = matchingMode;
    }

    public String getSchedulingState() {
        return schedulingState;
    }

    public void setSchedulingState(String schedulingState) {
        this.schedulingState = schedulingState;
    }

    public String getTransportPoolName() {
        return transportPoolName;
    }

    public void setTransportPoolName(String transportPoolName) {
        this.transportPoolName = transportPoolName;
    }

    public String getFromProvinceCode() {
        return fromProvinceCode;
    }

    public void setFromProvinceCode(String fromProvinceCode) {
        this.fromProvinceCode = fromProvinceCode;
    }

    public String getFromCityCode() {
        return fromCityCode;
    }

    public void setFromCityCode(String fromCityCode) {
        this.fromCityCode = fromCityCode;
    }

    public String getFromDistrictCode() {
        return fromDistrictCode;
    }

    public void setFromDistrictCode(String fromDistrictCode) {
        this.fromDistrictCode = fromDistrictCode;
    }

    public String getToProvinceCode() {
        return toProvinceCode;
    }

    public void setToProvinceCode(String toProvinceCode) {
        this.toProvinceCode = toProvinceCode;
    }

    public String getToCityCode() {
        return toCityCode;
    }

    public void setToCityCode(String toCityCode) {
        this.toCityCode = toCityCode;
    }

    public String getToDistrictCode() {
        return toDistrictCode;
    }

    public void setToDistrictCode(String toDistrictCode) {
        this.toDistrictCode = toDistrictCode;
    }

    public String getFaceOrder() {
        return faceOrder;
    }

    public void setFaceOrder(String faceOrder) {
        this.faceOrder = faceOrder;
    }

    public BigDecimal getLuggage() {
        return luggage;
    }

    public void setLuggage(BigDecimal luggage) {
        this.luggage = luggage;
    }

    public BigDecimal getCollectServiceCharge() {
        return collectServiceCharge;
    }

    public void setCollectServiceCharge(BigDecimal collectServiceCharge) {
        this.collectServiceCharge = collectServiceCharge;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }
}