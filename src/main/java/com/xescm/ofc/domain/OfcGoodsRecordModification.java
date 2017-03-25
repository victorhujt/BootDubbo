package com.xescm.ofc.domain;

import javax.persistence.*;

@Table(name = "ofc_goods_record_modification")
public class OfcGoodsRecordModification {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 修改之前的值
     */
    @Column(name = "value_before_modify_qty")
    private String valueBeforeModifyQty;

    /**
     * 修改之后的值
     */
    @Column(name = "value_after_modify_qty")
    private String valueAfterModifyQty;

    /**
     * 修改之前的值
     */
    @Column(name = "value_before_modify_wet")
    private String valueBeforeModifyWet;

    /**
     * 修改之后的值
     */
    @Column(name = "value_after_modify_wet")
    private String valueAfterModifyWet;

    /**
     * 修改之前的值
     */
    @Column(name = "value_before_modify_vol")
    private String valueBeforeModifyVol;

    /**
     * 修改之后的值
     */
    @Column(name = "value_after_modify_vol")
    private String valueAfterModifyVol;

    /**
     * 修改描述
     */
    @Column(name = "modification_desc")
    private String modificationDesc;

    /**
     * 订单编号
     */
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取修改之前的值
     *
     * @return value_before_modify_qty - 修改之前的值
     */
    public String getValueBeforeModifyQty() {
        return valueBeforeModifyQty;
    }

    /**
     * 设置修改之前的值
     *
     * @param valueBeforeModifyQty 修改之前的值
     */
    public void setValueBeforeModifyQty(String valueBeforeModifyQty) {
        this.valueBeforeModifyQty = valueBeforeModifyQty;
    }

    /**
     * 获取修改之后的值
     *
     * @return value_after_modify_qty - 修改之后的值
     */
    public String getValueAfterModifyQty() {
        return valueAfterModifyQty;
    }

    /**
     * 设置修改之后的值
     *
     * @param valueAfterModifyQty 修改之后的值
     */
    public void setValueAfterModifyQty(String valueAfterModifyQty) {
        this.valueAfterModifyQty = valueAfterModifyQty;
    }

    /**
     * 获取修改之前的值
     *
     * @return value_before_modify_wet - 修改之前的值
     */
    public String getValueBeforeModifyWet() {
        return valueBeforeModifyWet;
    }

    /**
     * 设置修改之前的值
     *
     * @param valueBeforeModifyWet 修改之前的值
     */
    public void setValueBeforeModifyWet(String valueBeforeModifyWet) {
        this.valueBeforeModifyWet = valueBeforeModifyWet;
    }

    /**
     * 获取修改之后的值
     *
     * @return value_after_modify_wet - 修改之后的值
     */
    public String getValueAfterModifyWet() {
        return valueAfterModifyWet;
    }

    /**
     * 设置修改之后的值
     *
     * @param valueAfterModifyWet 修改之后的值
     */
    public void setValueAfterModifyWet(String valueAfterModifyWet) {
        this.valueAfterModifyWet = valueAfterModifyWet;
    }

    /**
     * 获取修改之前的值
     *
     * @return value_before_modify_vol - 修改之前的值
     */
    public String getValueBeforeModifyVol() {
        return valueBeforeModifyVol;
    }

    /**
     * 设置修改之前的值
     *
     * @param valueBeforeModifyVol 修改之前的值
     */
    public void setValueBeforeModifyVol(String valueBeforeModifyVol) {
        this.valueBeforeModifyVol = valueBeforeModifyVol;
    }

    /**
     * 获取修改之后的值
     *
     * @return value_after_modify_vol - 修改之后的值
     */
    public String getValueAfterModifyVol() {
        return valueAfterModifyVol;
    }

    /**
     * 设置修改之后的值
     *
     * @param valueAfterModifyVol 修改之后的值
     */
    public void setValueAfterModifyVol(String valueAfterModifyVol) {
        this.valueAfterModifyVol = valueAfterModifyVol;
    }

    /**
     * 获取修改描述
     *
     * @return modification_desc - 修改描述
     */
    public String getModificationDesc() {
        return modificationDesc;
    }

    /**
     * 设置修改描述
     *
     * @param modificationDesc 修改描述
     */
    public void setModificationDesc(String modificationDesc) {
        this.modificationDesc = modificationDesc;
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
}