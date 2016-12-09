package com.xescm.ofc.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "ofc_planned_detail")
public class OfcPlannedDetail {
    /**
     * 计划单编号
     */
    @Column(name = "plan_code")
    private String planCode;

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
     * 实发数量
     */
    @Column(name = "real_quantity")
    private BigDecimal realQuantity;

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
    @Column(name = "production_time")
    private Date productionTime;

    /**
     * 失效日期
     */
    @Column(name = "invalid_time")
    private Date invalidTime;

    private BigDecimal weight;

    private BigDecimal cubage;

    private Integer totalBox;

    /**
     * 记账重量
     */
    @Column(name = "billing_weight")
    private BigDecimal billingWeight;

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getCubage() {
        return cubage;
    }

    public void setCubage(BigDecimal cubage) {
        this.cubage = cubage;
    }

    public Integer getTotalBox() {
        return totalBox;
    }

    public void setTotalBox(Integer totalBox) {
        this.totalBox = totalBox;
    }

    /**
     * 获取计划单编号
     *
     * @return plan_code - 计划单编号
     */
    public String getPlanCode() {
        return planCode;
    }

    /**
     * 设置计划单编号
     *
     * @param planCode 计划单编号
     */
    public void setPlanCode(String planCode) {
        this.planCode = planCode;
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
     * 获取实发数量
     *
     * @return real_quantity - 实发数量
     */
    public BigDecimal getRealQuantity() {
        return realQuantity;
    }

    /**
     * 设置实发数量
     *
     * @param realQuantity 实发数量
     */
    public void setRealQuantity(BigDecimal realQuantity) {
        this.realQuantity = realQuantity;
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
}