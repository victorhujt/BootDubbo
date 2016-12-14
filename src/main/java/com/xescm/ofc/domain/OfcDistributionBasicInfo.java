package com.xescm.ofc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "ofc_distribution_basic_info")
public class OfcDistributionBasicInfo {
    /**
     * 运输单号
     */
    @Column(name = "trans_code")
    private String transCode;

    /**
     * 是否加急
     */
    private Integer urgent;

    /**
     * 出发地
     */
    @Column(name = "departure_place")
    private String departurePlace;

    /**
     * 出发省份
     */
    @Column(name = "departure_province")
    private String departureProvince;

    /**
     * 出发城市
     */
    @Column(name = "departure_city")
    private String departureCity;

    /**
     * 出发区县
     */
    @Column(name = "departure_district")
    private String departureDistrict;

    /**
     * 出发乡镇
     */
    @Column(name = "departure_towns")
    private String departureTowns;

    /**
     * 出发地区域编码
     */
    @Column(name = "departure_place_code")
    private String departurePlaceCode;

    /**
     * 目的地
     */
    private String destination;

    /**
     * 目的省份
     */
    @Column(name = "destination_province")
    private String destinationProvince;

    /**
     * 目的城市
     */
    @Column(name = "destination_city")
    private String destinationCity;

    /**
     * 目的区县
     */
    @Column(name = "destination_district")
    private String destinationDistrict;

    /**
     * 目的乡镇
     */
    @Column(name = "destination_towns")
    private String destinationTowns;


    /**
     * 目的地区域编码
     */
    @Column(name = "destination_code")
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
    @Column(name = "total_standard_box")
    private Integer totalStandardBox;

    /**
     * 运输要求
     */
    @Column(name = "trans_require")
    private String transRequire;

    /**
     * 取货时间
     */

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "pickup_time")
    private Date pickupTime;

    /**
     * 期望送货时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "expected_arrived_time")
    private Date expectedArrivedTime;

    /**
     * 发货方编码
     */
    @Column(name = "consignor_code")
    private String consignorCode;

    /**
     * 发货方名称
     */
    @Column(name = "consignor_name")
    private String consignorName;

    /**
     * 收货方编码
     */
    @Column(name = "consignee_code")
    private String consigneeCode;

    /**
     * 收货方名称
     */
    @Column(name = "consignee_name")
    private String consigneeName;




    /**
     * 发货方联系人编码
     */
    @Column(name = "consignor_contact_code")
    private String consignorContactCode;

    /**
     * 发货方联系人名称
     */
    @Column(name = "consignor_contact_name")
    private String consignorContactName;

    /**
     * 收货方联系人编码
     */
    @Column(name = "consignee_contact_code")
    private String consigneeContactCode;

    /**
     * 收货方联系人名称
     */
    @Column(name = "consignee_contact_name")
    private String consigneeContactName;

    /**
     * 收货方联系人类型
     * 1、企业公司；2、个人',
     */
    @Column(name = "consignor_type")
    private String consignorType;

    /**
     * 收货方联系人类型
     * 1、企业公司；2、个人',
     */
    @Column(name = "consignee_type")
    private String consigneeType;



    /**
     * 承运商编码
     */
    @Column(name = "carrier_code")
    private String carrierCode;

    /**
     * 承运商名称
     */
    @Column(name = "carrier_name")
    private String carrierName;

    /**
     * 订单编号
     */
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 车牌号
     */
    @Column(name = "plate_number")
    private String plateNumber;

    /**
     * 司机姓名
     */
    @Column(name = "driver_name")
    private String driverName;

    /**
     * 基地ID(电商)
     */
    @Column(name = "base_id")
    private String baseId;

    /**
     * 联系电话
     */
    @Column(name = "contact_number")
    private String contactNumber;

    /**
     * 创建时间
     */
    @Column(name = "creation_time")
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
    @Column(name = "oper_time")
    private Date operTime;

    private String consignorContactPhone;
    private String consigneeContactPhone;

    //校验运输单号专用字段
    @Transient
    private String selfTransCode;


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

    /**
     * 获取运输单号
     *
     * @return trans_code - 运输单号
     */
    public String getTransCode() {
        return transCode;
    }

    /**
     * 设置运输单号
     *
     * @param transCode 运输单号
     */
    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    /**
     * 获取是否加急
     *
     * @return urgent - 是否加急
     */
    public Integer getUrgent() {
        return urgent;
    }

    /**
     * 设置是否加急
     *
     * @param urgent 是否加急
     */
    public void setUrgent(Integer urgent) {
        this.urgent = urgent;
    }

    /**
     * 获取出发地
     *
     * @return departure_place - 出发地
     */
    public String getDeparturePlace() {
        return departurePlace;
    }

    /**
     * 设置出发地
     *
     * @param departurePlace 出发地
     */
    public void setDeparturePlace(String departurePlace) {
        this.departurePlace = departurePlace;
    }

    /**
     * 获取出发省份
     *
     * @return departure_province - 出发省份
     */
    public String getDepartureProvince() {
        return departureProvince;
    }

    /**
     * 设置出发省份
     *
     * @param departureProvince 出发省份
     */
    public void setDepartureProvince(String departureProvince) {
        this.departureProvince = departureProvince;
    }

    /**
     * 获取出发城市
     *
     * @return departure_city - 出发城市
     */
    public String getDepartureCity() {
        return departureCity;
    }

    /**
     * 设置出发城市
     *
     * @param departureCity 出发城市
     */
    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    /**
     * 获取出发区县
     *
     * @return departure_district - 出发区县
     */
    public String getDepartureDistrict() {
        return departureDistrict;
    }

    /**
     * 设置出发区县
     *
     * @param departureDistrict 出发区县
     */
    public void setDepartureDistrict(String departureDistrict) {
        this.departureDistrict = departureDistrict;
    }

    /**
     * 获取出发乡镇
     *
     * @return departure_towns - 出发乡镇
     */
    public String getDepartureTowns() {
        return departureTowns;
    }

    /**
     * 设置出发乡镇
     *
     * @param departureTowns 出发乡镇
     */
    public void setDepartureTowns(String departureTowns) {
        this.departureTowns = departureTowns;
    }

    /**
     * 获取出发地区域编码
     *
     * @return departure_place_code - 出发地区域编码
     */
    public String getDeparturePlaceCode() {
        return departurePlaceCode;
    }

    /**
     * 设置出发地区域编码
     *
     * @param departurePlaceCode 出发地区域编码
     */
    public void setDeparturePlaceCode(String departurePlaceCode) {
        this.departurePlaceCode = departurePlaceCode;
    }

    /**
     * 获取目的地
     *
     * @return destination - 目的地
     */
    public String getDestination() {
        return destination;
    }

    /**
     * 设置目的地
     *
     * @param destination 目的地
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * 获取目的省份
     *
     * @return destination_province - 目的省份
     */
    public String getDestinationProvince() {
        return destinationProvince;
    }

    /**
     * 设置目的省份
     *
     * @param destinationProvince 目的省份
     */
    public void setDestinationProvince(String destinationProvince) {
        this.destinationProvince = destinationProvince;
    }

    /**
     * 获取目的城市
     *
     * @return destination_city - 目的城市
     */
    public String getDestinationCity() {
        return destinationCity;
    }

    /**
     * 设置目的城市
     *
     * @param destinationCity 目的城市
     */
    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    /**
     * 获取目的区县
     *
     * @return destination_district - 目的区县
     */
    public String getDestinationDistrict() {
        return destinationDistrict;
    }

    /**
     * 设置目的区县
     *
     * @param destinationDistrict 目的区县
     */
    public void setDestinationDistrict(String destinationDistrict) {
        this.destinationDistrict = destinationDistrict;
    }

    /**
     * 获取目的乡镇
     *
     * @return destination_towns - 目的乡镇
     */
    public String getDestinationTowns() {
        return destinationTowns;
    }

    /**
     * 设置目的乡镇
     *
     * @param destinationTowns 目的乡镇
     */
    public void setDestinationTowns(String destinationTowns) {
        this.destinationTowns = destinationTowns;
    }


    /**
     * 获取目的地区域编码
     *
     * @return destination_code - 目的地区域编码
     */
    public String getDestinationCode() {
        return destinationCode;
    }

    /**
     * 设置目的地区域编码
     *
     * @param destinationCode 目的地区域编码
     */
    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    /**
     * 获取数量
     *
     * @return quantity - 数量
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     * 设置数量
     *
     * @param quantity 数量
     */
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    /**
     * 获取重量
     *
     * @return weight - 重量
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * 设置重量
     *
     * @param weight 重量
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * 获取体积
     *
     * @return cubage - 体积
     */
    public String getCubage() {
        return cubage;
    }

    /**
     * 设置体积
     *
     * @param cubage 体积
     */
    public void setCubage(String cubage) {
        this.cubage = cubage;
    }

    /**
     * 获取合计标准箱
     *
     * @return total_standard_box - 合计标准箱
     */
    public Integer getTotalStandardBox() {
        return totalStandardBox;
    }

    /**
     * 设置合计标准箱
     *
     * @param totalStandardBox 合计标准箱
     */
    public void setTotalStandardBox(Integer totalStandardBox) {
        this.totalStandardBox = totalStandardBox;
    }

    /**
     * 获取运输要求
     *
     * @return trans_require - 运输要求
     */
    public String getTransRequire() {
        return transRequire;
    }

    /**
     * 设置运输要求
     *
     * @param transRequire 运输要求
     */
    public void setTransRequire(String transRequire) {
        this.transRequire = transRequire;
    }

    /**
     * 获取取货时间
     *
     * @return pickup_time - 取货时间
     */
    public Date getPickupTime() {
        return pickupTime;
    }

    /**
     * 设置取货时间
     *
     * @param pickupTime 取货时间
     */
    public void setPickupTime(Date pickupTime) {
        this.pickupTime = pickupTime;
    }

    /**
     * 获取期望送货时间
     *
     * @return expected_arrived_time - 期望送货时间
     */
    public Date getExpectedArrivedTime() {
        return expectedArrivedTime;
    }

    /**
     * 设置期望送货时间
     *
     * @param expectedArrivedTime 期望送货时间
     */
    public void setExpectedArrivedTime(Date expectedArrivedTime) {
        this.expectedArrivedTime = expectedArrivedTime;
    }

    /**
     * 获取发货方编码
     *
     * @return consignor_code - 发货方编码
     */
    public String getConsignorCode() {
        return consignorCode;
    }

    /**
     * 设置发货方编码
     *
     * @param consignorCode 发货方编码
     */
    public void setConsignorCode(String consignorCode) {
        this.consignorCode = consignorCode;
    }

    /**
     * 获取发货方名称
     *
     * @return consignor_name - 发货方名称
     */
    public String getConsignorName() {
        return consignorName;
    }

    /**
     * 设置发货方名称
     *
     * @param consignorName 发货方名称
     */
    public void setConsignorName(String consignorName) {
        this.consignorName = consignorName;
    }

    /**
     * 获取收货方编码
     *
     * @return consignee_code - 收货方编码
     */
    public String getConsigneeCode() {
        return consigneeCode;
    }

    /**
     * 设置收货方编码
     *
     * @param consigneeCode 收货方编码
     */
    public void setConsigneeCode(String consigneeCode) {
        this.consigneeCode = consigneeCode;
    }

    /**
     * 获取收货方名称
     *
     * @return consignee_name - 收货方名称
     */
    public String getConsigneeName() {
        return consigneeName;
    }

    /**
     * 设置收货方名称
     *
     * @param consigneeName 收货方名称
     */
    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    /**
     * 获取承运商编码
     *
     * @return carrier_code - 承运商编码
     */
    public String getCarrierCode() {
        return carrierCode;
    }

    /**
     * 设置承运商编码
     *
     * @param carrierCode 承运商编码
     */
    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    /**
     * 获取承运商名称
     *
     * @return carrier_name - 承运商名称
     */
    public String getCarrierName() {
        return carrierName;
    }

    /**
     * 设置承运商名称
     *
     * @param carrierName 承运商名称
     */
    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    /**
     * 获取订单编号
     *
     * @return order_code - 订单编号
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * 设置订单编号
     *
     * @param orderCode 订单编号
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    /**
     * 获取车牌号
     *
     * @return plate_number - 车牌号
     */
    public String getPlateNumber() {
        return plateNumber;
    }

    /**
     * 设置车牌号
     *
     * @param plateNumber 车牌号
     */
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    /**
     * 获取司机姓名
     *
     * @return driver_name - 司机姓名
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * 设置司机姓名
     *
     * @param driverName 司机姓名
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    /**
     * 获取联系电话
     *
     * @return contact_number - 联系电话
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * 设置联系电话
     *
     * @param contactNumber 联系电话
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * 获取基地ID(电商)
     *
     * @return base_id - 基地ID(电商)
     */
    public String getBaseId() {
        return baseId;
    }

    /**
     * 设置基地ID(电商)
     *
     * @param baseId 基地ID(电商)
     */
    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    /**
     * 获取创建时间
     *
     * @return creation_time - 创建时间
     */
    public Date getCreationTime() {
        return creationTime;
    }

    /**
     * 设置创建时间
     *
     * @param creationTime 创建时间
     */
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * 获取创建人员
     *
     * @return creator - 创建人员
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人员
     *
     * @param creator 创建人员
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 获取操作人员
     *
     * @return operator - 操作人员
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置操作人员
     *
     * @param operator 操作人员
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 获取操作时间
     *
     * @return oper_time - 操作时间
     */
    public Date getOperTime() {
        return operTime;
    }

    /**
     * 设置操作时间
     *
     * @param operTime 操作时间
     */
    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public String getSelfTransCode() {
        return selfTransCode;
    }

    public void setSelfTransCode(String selfTransCode) {
        this.selfTransCode = selfTransCode;
    }
}