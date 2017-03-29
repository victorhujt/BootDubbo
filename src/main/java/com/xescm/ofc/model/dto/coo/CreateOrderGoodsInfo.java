package com.xescm.ofc.model.dto.coo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * 订单中心创建订单（鲜易网）
 * 订单货品明细信息
 * Created by hiyond on 2016/11/14.
 */
@XStreamAlias("orderDetail")
public class CreateOrderGoodsInfo implements Serializable {

    private static final long serialVersionUID = 7909253575176134994L;
    /**
     * 货品代码 (必填)
     */
    private String goodsCode;

    /**
     * 货品名称 (必填)
     */
    private String goodsName;

    /**
     * 货品规格(必填)
     */
    private String goodsSpec;

    /**
     * 单位(必填)
     */
    private String unit;

    /**
     * 数量
     */
    private String quantity;
    /**
     * 重量
     */
    private String weight;
    /**
     * 体积
     */
    private String cubage;

    /**
     * 销售单价 (必填)
     */
    private String unitPrice;

    /**
     * 生产批次(必填)
     */
    private String productionBatch;

    /**
     * 生产日期(必填)
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

    public String getWeight() {
        return weight;
    }

    public String getCubage() {
        return cubage;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setCubage(String cubage) {
        this.cubage = cubage;
    }
}
