package com.xescm.ofc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "ofc_goods_record_modification")
public class OfcGoodsRecordModification {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    /**
     * 订单编号
     */
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 商品编码
     */
    @Column(name = "goods_code")
    private String goodsCode;

    /**
     * 商品名称
     */
    @Column(name = "goods_name")
    private String goodsName;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "creation_time")
    private Date creationTime = new Date();
}