package com.xescm.ofc.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "ofc_goods_record_modification")
public class OfcGoodsRecordModification {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 数量修改之前的值
     */
    @Column(name = "value_before_modify_qty")
    private String valueBeforeModifyQty;

    /**
     * 数量修改之后的值
     */
    @Column(name = "value_after_modify_qty")
    private String valueAfterModifyQty;

    /**
     * 重量修改之前的值
     */
    @Column(name = "value_before_modify_wet")
    private String valueBeforeModifyWet;

    /**
     * 重量修改之后的值
     */
    @Column(name = "value_after_modify_wet")
    private String valueAfterModifyWet;

    /**
     * 体积修改之前的值
     */
    @Column(name = "value_before_modify_vol")
    private String valueBeforeModifyVol;

    /**
     * 体积修改之后的值
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
}