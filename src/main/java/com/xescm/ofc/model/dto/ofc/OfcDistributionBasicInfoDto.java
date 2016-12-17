package com.xescm.ofc.model.dto.ofc;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OfcDistributionBasicInfoDto implements Serializable{


    /**
     * 客户编码
     */
    private String custCode;

    /**
     * 客户名称
     */
    private String custName;

    /**
     * 货品类别,第一个货品的大类
     */
    private String goodsType;

    /**
     * 货品类别,第一个货品的大类
     */
    private String goodsTypeName;

    /**
     * 备注
     */
    private String notes;

    /**
     * 运输单号
     */
    private String transCode;

    /**
     * 是否加急
     */
    private Integer urgent;

    /**
     * 出发地
     */
    private String departurePlace;

    /**
     * 出发省份
     */
    private String departureProvince;

    /**
     * 出发城市
     */
    private String departureCity;

    /**
     * 出发区县
     */
    private String departureDistrict;

    /**
     * 出发乡镇
     */
    private String departureTowns;

    /**
     * 出发地区域编码
     */
    private String departurePlaceCode;

    /**
     * 目的地
     */
    private String destination;

    /**
     * 目的省份
     */
    private String destinationProvince;

    /**
     * 目的城市
     */
    private String destinationCity;

    /**
     * 目的区县
     */
    private String destinationDistrict;

    /**
     * 目的乡镇
     */
    private String destinationTowns;


    /**
     * 目的地区域编码
     */
    private String destinationCode;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 体积
     */
    private String cubage;

    /**
     * 合计标准箱
     */
    private Integer totalStandardBox;

    /**
     * 运输要求
     */
    private String transRequire;

    /**
     * 取货时间
     */

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date pickupTime;

    /**
     * 期望送货时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expectedArrivedTime;

    /**
     * 发货方编码
     */
    private String consignorCode;

    /**
     * 发货方名称
     */
    private String consignorName;

    /**
     * 收货方编码
     */
    private String consigneeCode;

    /**
     * 收货方名称
     */
    private String consigneeName;


    /**
     * 发货方联系人编码
     */
    private String consignorContactCode;

    /**
     * 发货方联系人名称
     */
    private String consignorContactName;

    /**
     * 收货方联系人编码
     */
    private String consigneeContactCode;

    /**
     * 收货方联系人名称
     */
    private String consigneeContactName;

    /**
     * 收货方联系人类型
     * 1、企业公司；2、个人',
     */
    private String consignorType;

    /**
     * 收货方联系人类型
     * 1、企业公司；2、个人',
     */
    private String consigneeType;


    /**
     * 承运商编码
     */
    private String carrierCode;

    /**
     * 承运商名称
     */
    private String carrierName;

    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 车牌号
     */
    private String plateNumber;

    /**
     * 司机姓名
     */
    private String driverName;

    /**
     * 基地ID(电商)
     */
    private String baseId;

    /**
     * 联系电话
     */
    private String contactNumber;

    /**
     * 创建时间
     */
    private Date creationTime;

    /**
     * 创建人员
     */
    private String creator;

    /**
     * 操作人员
     */
    private String operator;

    /**
     * 操作时间
     */
    private Date operTime;

    /**
     * 发货方联系人电话
     */
    private String consignorContactPhone;
    /**
     * 收货方联系人电话
     */
    private String consigneeContactPhone;

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

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
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

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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
        this.creator = creator;
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

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }
}