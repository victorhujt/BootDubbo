package com.xescm.ofc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

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
     * 出发地区域编码
     */
    @Column(name = "departure_place_code")
    private String departurePlaceCode;

    /**
     * 目的地
     */
    private String destination;

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
}