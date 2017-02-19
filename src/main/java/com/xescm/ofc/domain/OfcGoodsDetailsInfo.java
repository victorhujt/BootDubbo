package com.xescm.ofc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "ofc_goods_details_info")
public class OfcGoodsDetailsInfo {

    @Id
    @GeneratedValue(generator = "UUID")
    private String id;
    /**
     * 货品代码
     */
    @Column(name = "goods_code")
    private String goodsCode;

    /**
     * 货品名称
     */
    @Column(name = "goods_name")
    private String goodsName;

    /**
     * 货品规格
     */
    @Column(name = "goods_spec")
    private String goodsSpec;

    /**
     * 单位
     */
    private String unit;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 单价
     */
    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    /**
     * 生产批次
     */
    @Column(name = "production_batch")
    private String productionBatch;

    /**
     * 生产日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "production_time")
    private Date productionTime;

    /**
     * 失效日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "invalid_time")
    private Date invalidTime;

    /**
     * 订单编号
     */
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 备注
     */
    private String notes;

    /**
     * 创建日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "oper_time")
    private Date operTime;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 体积
     */
    private BigDecimal cubage;

    /**
     * 合计标准箱
     */
    @Column(name = "total_box")
    private Integer totalBox;

    /**
     * 货品种类
     */
    @Column(name = "goods_type")
    private String goodsType;

    /**
     * 货品类别
     */
    @Column(name = "goods_category")
    private String goodsCategory;

    /**
     * 包装
     */
    private String pack;

    /**
     * 数量单价
     */
    @Column(name = "quantity_unit_price")
    private BigDecimal quantityUnitPrice;

    /**
     * 重量单价
     */
    @Column(name = "weight_unit_price")
    private BigDecimal weightUnitPrice;

    /**
     * 体积单价
     */
    @Column(name = "volume_unit_price")
    private BigDecimal volumeUnitPrice;

    /**
     * 记账重量
     */
    @Column(name = "billing_weight")
    private BigDecimal billingWeight;

    /**
     * 计费方式
     */
    @Column(name = "charging_ways")
    private String chargingWays;

    /**
     * 计费数量
     */
    @Column(name = "charging_quantity")
    private BigDecimal chargingQuantity;

    /**
     * 计费单价
     */
    @Column(name = "charging_unit_price")
    private BigDecimal chargingUnitPrice;

    /**
     * 删除标志位 0或者空表示未删除  1:表示已经删除
     */
//    @Column(name = "is_delete")
//    private String isDelete;
//
//    public String getIsDelete() {
//        return isDelete;
//    }
//
//    public void setIsDelete(String isDelete) {
//        this.isDelete = isDelete;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取货品代码
     *
     * @return goods_code - 货品代码
     */
    public String getGoodsCode() {
        return goodsCode;
    }

    /**
     * 设置货品代码
     *
     * @param goodsCode 货品代码
     */
    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    /**
     * 获取货品名称
     *
     * @return goods_name - 货品名称
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * 设置货品名称
     *
     * @param goodsName 货品名称
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * 获取货品规格
     *
     * @return goods_spec - 货品规格
     */
    public String getGoodsSpec() {
        return goodsSpec;
    }

    /**
     * 设置货品规格
     *
     * @param goodsSpec 货品规格
     */
    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    /**
     * 获取单位
     *
     * @return unit - 单位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置单位
     *
     * @param unit 单位
     */
    public void setUnit(String unit) {
        this.unit = unit;
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
     * 获取单价
     *
     * @return unit_price - 单价
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * 设置单价
     *
     * @param unitPrice 单价
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * 获取生产批次
     *
     * @return production_batch - 生产批次
     */
    public String getProductionBatch() {
        return productionBatch;
    }

    /**
     * 设置生产批次
     *
     * @param productionBatch 生产批次
     */
    public void setProductionBatch(String productionBatch) {
        this.productionBatch = productionBatch;
    }

    /**
     * 获取生产日期
     *
     * @return production_time - 生产日期
     */
    public Date getProductionTime() {
        return productionTime;
    }

    /**
     * 设置生产日期
     *
     * @param productionTime 生产日期
     */
    public void setProductionTime(Date productionTime) {
        this.productionTime = productionTime;
    }

    /**
     * 获取失效日期
     *
     * @return invalid_time - 失效日期
     */
    public Date getInvalidTime() {
        return invalidTime;
    }

    /**
     * 设置失效日期
     *
     * @param invalidTime 失效日期
     */
    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
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
     * 获取备注
     *
     * @return notes - 备注
     */
    public String getNotes() {
        return notes;
    }

    /**
     * 设置备注
     *
     * @param notes 备注
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * 获取创建日期
     *
     * @return creation_time - 创建日期
     */
    public Date getCreationTime() {
        return creationTime;
    }

    /**
     * 设置创建日期
     *
     * @param creationTime 创建日期
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
    public BigDecimal getCubage() {
        return cubage;
    }

    /**
     * 设置体积
     *
     * @param cubage 体积
     */
    public void setCubage(BigDecimal cubage) {
        this.cubage = cubage;
    }

    /**
     * 获取合计标准箱
     *
     * @return total_box - 合计标准箱
     */
    public Integer getTotalBox() {
        return totalBox;
    }

    /**
     * 设置合计标准箱
     *
     * @param totalBox 合计标准箱
     */
    public void setTotalBox(Integer totalBox) {
        this.totalBox = totalBox;
    }

    /**
     * 获取货品种类
     *
     * @return goods_type - 货品种类
     */
    public String getGoodsType() {
        return goodsType;
    }

    /**
     * 设置货品种类
     *
     * @param goodsType 货品种类
     */
    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    /**
     * 获取货品类别
     *
     * @return goods_category - 货品类别
     */
    public String getGoodsCategory() {
        return goodsCategory;
    }

    /**
     * 设置货品类别
     *
     * @param goodsCategory 货品类别
     */
    public void setGoodsCategory(String goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    /**
     * 获取包装
     *
     * @return pack - 包装
     */
    public String getPack() {
        return pack;
    }

    /**
     * 设置包装
     *
     * @param pack 包装
     */
    public void setPack(String pack) {
        this.pack = pack;
    }

    /**
     * 获取数量单价
     *
     * @return quantity_unit_price - 数量单价
     */
    public BigDecimal getQuantityUnitPrice() {
        return quantityUnitPrice;
    }

    /**
     * 设置数量单价
     *
     * @param quantityUnitPrice 数量单价
     */
    public void setQuantityUnitPrice(BigDecimal quantityUnitPrice) {
        this.quantityUnitPrice = quantityUnitPrice;
    }

    /**
     * 获取重量单价
     *
     * @return weight_unit_price - 重量单价
     */
    public BigDecimal getWeightUnitPrice() {
        return weightUnitPrice;
    }

    /**
     * 设置重量单价
     *
     * @param weightUnitPrice 重量单价
     */
    public void setWeightUnitPrice(BigDecimal weightUnitPrice) {
        this.weightUnitPrice = weightUnitPrice;
    }

    /**
     * 获取体积单价
     *
     * @return volume_unit_price - 体积单价
     */
    public BigDecimal getVolumeUnitPrice() {
        return volumeUnitPrice;
    }

    /**
     * 设置体积单价
     *
     * @param volumeUnitPrice 体积单价
     */
    public void setVolumeUnitPrice(BigDecimal volumeUnitPrice) {
        this.volumeUnitPrice = volumeUnitPrice;
    }

    /**
     * 获取记账重量
     *
     * @return billing_weight - 记账重量
     */
    public BigDecimal getBillingWeight() {
        return billingWeight;
    }

    /**
     * 设置记账重量
     *
     * @param billingWeight 记账重量
     */
    public void setBillingWeight(BigDecimal billingWeight) {
        this.billingWeight = billingWeight;
    }

    /**
     * 获取计费方式
     *
     * @return charging_ways - 计费方式
     */
    public String getChargingWays() {
        return chargingWays;
    }

    /**
     * 设置计费方式
     *
     * @param chargingWays 计费方式
     */
    public void setChargingWays(String chargingWays) {
        this.chargingWays = chargingWays;
    }

    /**
     * 获取计费数量
     *
     * @return charging_quantity - 计费数量
     */
    public BigDecimal getChargingQuantity() {
        return chargingQuantity;
    }

    /**
     * 设置计费数量
     *
     * @param chargingQuantity 计费数量
     */
    public void setChargingQuantity(BigDecimal chargingQuantity) {
        this.chargingQuantity = chargingQuantity;
    }

    /**
     * 获取计费单价
     *
     * @return charging_unit_price - 计费单价
     */
    public BigDecimal getChargingUnitPrice() {
        return chargingUnitPrice;
    }

    /**
     * 设置计费单价
     *
     * @param chargingUnitPrice 计费单价
     */
    public void setChargingUnitPrice(BigDecimal chargingUnitPrice) {
        this.chargingUnitPrice = chargingUnitPrice;
    }
}