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
    private String goods_code;

    /**
     * 货品名称 Y
     */
    private String goods_name;

    /**
     * 货品规格
     */
    private String goods_spec;

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
    private String unit_price;

    /**
     * 生产批次
     */
    private String production_batch;

    /**
     * 生产日期
     */
    private String production_time;

    /**
     * 失效日期
     */
    private String invalid_time;

    public String getGoods_code() {
        return goods_code;
    }

    public void setGoods_code(String goods_code) {
        this.goods_code = goods_code;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_spec() {
        return goods_spec;
    }

    public void setGoods_spec(String goods_spec) {
        this.goods_spec = goods_spec;
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

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getProduction_batch() {
        return production_batch;
    }

    public void setProduction_batch(String production_batch) {
        this.production_batch = production_batch;
    }

    public String getProduction_time() {
        return production_time;
    }

    public void setProduction_time(String production_time) {
        this.production_time = production_time;
    }

    public String getInvalid_time() {
        return invalid_time;
    }

    public void setInvalid_time(String invalid_time) {
        this.invalid_time = invalid_time;
    }

    @Override
    public String toString() {
        return "XebestCreateOrderGoodsInfo{" +
                "goods_code='" + goods_code + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", goods_spec='" + goods_spec + '\'' +
                ", unit='" + unit + '\'' +
                ", quantity='" + quantity + '\'' +
                ", unit_price='" + unit_price + '\'' +
                ", production_batch='" + production_batch + '\'' +
                ", production_time='" + production_time + '\'' +
                ", invalid_time='" + invalid_time + '\'' +
                '}';
    }

}
