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
    @Column(name = "planCode")
    private String plancode;

    /**
     * 货品代码
     */
    @Column(name = "goodsCode")
    private String goodscode;

    /**
     * 货品名称
     */
    @Column(name = "goodsName")
    private String goodsname;

    /**
     * 货品规格
     */
    @Column(name = "goodsSpec")
    private String goodsspec;

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
    @Column(name = "realQuantity")
    private BigDecimal realquantity;

    /**
     * 单价
     */
    @Column(name = "unitPrice")
    private BigDecimal unitprice;

    /**
     * 生产批次
     */
    @Column(name = "productionBatch")
    private String productionbatch;

    /**
     * 生产日期
     */
    @Column(name = "manufactureDate")
    private Date manufacturedate;

    /**
     * 失效日期
     */
    @Column(name = "expirationDate")
    private Date expirationdate;

    /**
     * 获取计划单编号
     *
     * @return planCode - 计划单编号
     */
    public String getPlancode() {
        return plancode;
    }

    /**
     * 设置计划单编号
     *
     * @param plancode 计划单编号
     */
    public void setPlancode(String plancode) {
        this.plancode = plancode;
    }

    /**
     * 获取货品代码
     *
     * @return goodsCode - 货品代码
     */
    public String getGoodscode() {
        return goodscode;
    }

    /**
     * 设置货品代码
     *
     * @param goodscode 货品代码
     */
    public void setGoodscode(String goodscode) {
        this.goodscode = goodscode;
    }

    /**
     * 获取货品名称
     *
     * @return goodsName - 货品名称
     */
    public String getGoodsname() {
        return goodsname;
    }

    /**
     * 设置货品名称
     *
     * @param goodsname 货品名称
     */
    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    /**
     * 获取货品规格
     *
     * @return goodsSpec - 货品规格
     */
    public String getGoodsspec() {
        return goodsspec;
    }

    /**
     * 设置货品规格
     *
     * @param goodsspec 货品规格
     */
    public void setGoodsspec(String goodsspec) {
        this.goodsspec = goodsspec;
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
     * @return realQuantity - 实发数量
     */
    public BigDecimal getRealquantity() {
        return realquantity;
    }

    /**
     * 设置实发数量
     *
     * @param realquantity 实发数量
     */
    public void setRealquantity(BigDecimal realquantity) {
        this.realquantity = realquantity;
    }

    /**
     * 获取单价
     *
     * @return unitPrice - 单价
     */
    public BigDecimal getUnitprice() {
        return unitprice;
    }

    /**
     * 设置单价
     *
     * @param unitprice 单价
     */
    public void setUnitprice(BigDecimal unitprice) {
        this.unitprice = unitprice;
    }

    /**
     * 获取生产批次
     *
     * @return productionBatch - 生产批次
     */
    public String getProductionbatch() {
        return productionbatch;
    }

    /**
     * 设置生产批次
     *
     * @param productionbatch 生产批次
     */
    public void setProductionbatch(String productionbatch) {
        this.productionbatch = productionbatch;
    }

    /**
     * 获取生产日期
     *
     * @return manufactureDate - 生产日期
     */
    public Date getManufacturedate() {
        return manufacturedate;
    }

    /**
     * 设置生产日期
     *
     * @param manufacturedate 生产日期
     */
    public void setManufacturedate(Date manufacturedate) {
        this.manufacturedate = manufacturedate;
    }

    /**
     * 获取失效日期
     *
     * @return expirationDate - 失效日期
     */
    public Date getExpirationdate() {
        return expirationdate;
    }

    /**
     * 设置失效日期
     *
     * @param expirationdate 失效日期
     */
    public void setExpirationdate(Date expirationdate) {
        this.expirationdate = expirationdate;
    }
}