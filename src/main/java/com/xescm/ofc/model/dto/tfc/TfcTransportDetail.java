package com.xescm.ofc.model.dto.tfc;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
@Data
public class TfcTransportDetail implements Serializable {

    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    private Integer id;

    /**
     * 运输单ID
     */
    @Column(name = "TRANSPORT_ID")
    private Integer transportId;

    /**
     * 运输管理中心运输单
     */
    @Column(name = "TFC_BILL_NO")
    private String tfcBillNo;

    /**
     * 来源平台
     */
    @Column(name = "FROM_SYSTEM")
    private String fromSystem;

    /**
     * 运输单号
     */
    @Column(name = "TRANSPORT_NO")
    private String transportNo;

    /**
     * 商品编码
     */
    @Column(name = "ITEM_CODE")
    private String itemCode;

    /**
     * 商品名称
     */
    @Column(name = "ITEM_NAME")
    private String itemName;

    /**
     * 体积
     */
    @Column(name = "QTY")
    private Double qty;

    /**
     * 重量
     */
    @Column(name = "WEIGHT")
    private Double weight;

    /**
     * 数量
     */
    @Column(name = "VOLUME")
    private Double volume;

    /**
     * 单价
     */
    @Column(name = "PRICE")
    private Double price;

    /**
     * 金额
     */
    @Column(name = "MONEY")
    private Double money;

    /**
     * 单位
     */
    @Column(name = "UOM")
    private String uom;

    /**
     * 箱数
     */
    @Column(name = "CONTAINER_QTY")
    private String containerQty;

    private String standard;

    private String pono;

    private String qtyPicked;

    private String marketUnitl;

    private String qtyPickedEach;

    private String basicUnits1;

    private String qtyOrdered;

    private String marketUnit2;

    private String qtyOrderedEach;

    private String basicUnits2;
    //以下为新加属性
    private String productionBatch;//生产批次

    private String goodsCategory;

    private String goodsType;

    private String orderCode;

    /**
     * 包装
     */
    private String pack;

    /**
     * 计费方式
     */
    @Column(name = "charging_ways")
    private String chargingWays;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date productionTime;//生产日期

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date invalidTime;//失效日期

    private Integer totalBox;//合计标准箱

    /**
     * 平台行号
     */
    @Column(name = "paas_line_no")
    private Long paasLineNo;
}