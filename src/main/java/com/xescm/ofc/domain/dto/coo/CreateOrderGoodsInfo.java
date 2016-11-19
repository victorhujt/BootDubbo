package com.xescm.ofc.domain.dto.coo;

/**
 * 订单中心创建订单（鲜易网）
 * 订单货品明细信息
 * Created by hiyond on 2016/11/14.
 */
public class CreateOrderGoodsInfo {

    /**
     * 货品代码 Y
     */
    private String goodsCode;

    /**
     * 货品名称 Y
     */
    private String goodsName;

    /**
     * 货品规格
     */
    private String goodsSpec;

    /**
     * 单位
     */
    private String unit;

    /**
     * 数量 Y
     */
    private String quantity;

    /**
     * 单价
     */
    private String unitPrice;

    /**
     * 生产批次
     */
    private String productionBatch;

    /**
     * 生产日期
     */
    private String productionTime;

    /**
     * 失效日期
     */
    private String invalidTime;

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getProductionBatch() {
        return productionBatch;
    }

    public void setProductionBatch(String productionBatch) {
        this.productionBatch = productionBatch;
    }

    public String getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(String productionTime) {
        this.productionTime = productionTime;
    }

    public String getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(String invalidTime) {
        this.invalidTime = invalidTime;
    }

    @Override
    public String toString() {
        return "CreateOrderGoodsInfo{" +
                "goodsCode='" + goodsCode + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsSpec='" + goodsSpec + '\'' +
                ", unit='" + unit + '\'' +
                ", quantity='" + quantity + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", productionBatch='" + productionBatch + '\'' +
                ", productionTime='" + productionTime + '\'' +
                ", invalidTime='" + invalidTime + '\'' +
                '}';
    }
}
