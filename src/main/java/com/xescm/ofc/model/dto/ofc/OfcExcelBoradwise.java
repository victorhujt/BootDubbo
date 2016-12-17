package com.xescm.ofc.model.dto.ofc;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lyh on 2016/12/16.
 */
public class OfcExcelBoradwise {



    private String custOrderCode;
    private Date orderTime;
    private String consigneeName;
    private String consigneeContactName;
    private String consigneeContactPhone;
    private String consigneeAddress;
    private String goodsCode;
    private String goodsName;
    private String goodsSpec;
    private String goodsUnit;
    private BigDecimal goodsAmount;
    private BigDecimal goodsUnitPirce;

    public String getConsigneeContactPhone() {
        return consigneeContactPhone;
    }

    public void setConsigneeContactPhone(String consigneeContactPhone) {
        this.consigneeContactPhone = consigneeContactPhone;
    }

    public String getCustOrderCode() {
        return custOrderCode;
    }

    public void setCustOrderCode(String custOrderCode) {
        this.custOrderCode = custOrderCode;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneeContactName() {
        return consigneeContactName;
    }

    public void setConsigneeContactName(String consigneeContactName) {
        this.consigneeContactName = consigneeContactName;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public BigDecimal getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(BigDecimal goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public BigDecimal getGoodsUnitPirce() {
        return goodsUnitPirce;
    }

    public void setGoodsUnitPirce(BigDecimal goodsUnitPirce) {
        this.goodsUnitPirce = goodsUnitPirce;
    }

    public OfcExcelBoradwise(){}

    public OfcExcelBoradwise(String custOrderCode, Date orderTime, String consigneeName, String consigneeContactName, String consigneeContactPhone, String consigneeAddress, String goodsCode, String goodsName, String goodsSpec, String goodsUnit, BigDecimal goodsAmount, BigDecimal goodsUnitPirce) {
        this.custOrderCode = custOrderCode;
        this.orderTime = orderTime;
        this.consigneeName = consigneeName;
        this.consigneeContactName = consigneeContactName;
        this.consigneeContactPhone = consigneeContactPhone;
        this.consigneeAddress = consigneeAddress;
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.goodsSpec = goodsSpec;
        this.goodsUnit = goodsUnit;
        this.goodsAmount = goodsAmount;
        this.goodsUnitPirce = goodsUnitPirce;
    }
}
