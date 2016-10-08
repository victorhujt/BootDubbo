package com.xescm.ofc.domain;

import java.math.BigDecimal;

public class OfcGoodsDetailsInfo {
    private String goodsCode;

    private String goodsName;

    private String goodsSpec;

    private String unit;

    private BigDecimal quantity;

    private BigDecimal unitPrice;

    private String reservedField01;

    private String reservedField02;

    private String reservedField03;

    private String reservedField04;

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode == null ? null : goodsCode.trim();
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec == null ? null : goodsSpec.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getReservedField01() {
        return reservedField01;
    }

    public void setReservedField01(String reservedField01) {
        this.reservedField01 = reservedField01 == null ? null : reservedField01.trim();
    }

    public String getReservedField02() {
        return reservedField02;
    }

    public void setReservedField02(String reservedField02) {
        this.reservedField02 = reservedField02 == null ? null : reservedField02.trim();
    }

    public String getReservedField03() {
        return reservedField03;
    }

    public void setReservedField03(String reservedField03) {
        this.reservedField03 = reservedField03 == null ? null : reservedField03.trim();
    }

    public String getReservedField04() {
        return reservedField04;
    }

    public void setReservedField04(String reservedField04) {
        this.reservedField04 = reservedField04 == null ? null : reservedField04.trim();
    }
}