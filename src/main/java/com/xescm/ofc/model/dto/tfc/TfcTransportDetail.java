package com.xescm.ofc.model.dto.tfc;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

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

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getPono() {
        return pono;
    }

    public void setPono(String pono) {
        this.pono = pono;
    }

    public String getQtyPicked() {
        return qtyPicked;
    }

    public void setQtyPicked(String qtyPicked) {
        this.qtyPicked = qtyPicked;
    }

    public String getMarketUnitl() {
        return marketUnitl;
    }

    public void setMarketUnitl(String marketUnitl) {
        this.marketUnitl = marketUnitl;
    }

    public String getQtyPickedEach() {
        return qtyPickedEach;
    }

    public void setQtyPickedEach(String qtyPickedEach) {
        this.qtyPickedEach = qtyPickedEach;
    }

    public String getBasicUnits1() {
        return basicUnits1;
    }

    public void setBasicUnits1(String basicUnits1) {
        this.basicUnits1 = basicUnits1;
    }

    public String getQtyOrdered() {
        return qtyOrdered;
    }

    public void setQtyOrdered(String qtyOrdered) {
        this.qtyOrdered = qtyOrdered;
    }

    public String getMarketUnit2() {
        return marketUnit2;
    }

    public void setMarketUnit2(String marketUnit2) {
        this.marketUnit2 = marketUnit2;
    }

    public String getQtyOrderedEach() {
        return qtyOrderedEach;
    }

    public void setQtyOrderedEach(String qtyOrderedEach) {
        this.qtyOrderedEach = qtyOrderedEach;
    }

    public String getBasicUnits2() {
        return basicUnits2;
    }

    public void setBasicUnits2(String basicUnits2) {
        this.basicUnits2 = basicUnits2;
    }

    /**
     * 获取ID
     *
     * @return ID - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取运输单ID
     *
     * @return TRANSPORT_ID - 运输单ID
     */
    public Integer getTransportId() {
        return transportId;
    }

    /**
     * 设置运输单ID
     *
     * @param transportId 运输单ID
     */
    public void setTransportId(Integer transportId) {
        this.transportId = transportId;
    }

    /**
     * 获取运输管理中心运输单
     *
     * @return TFC_BILL_NO - 运输管理中心运输单
     */
    public String getTfcBillNo() {
        return tfcBillNo;
    }

    /**
     * 设置运输管理中心运输单
     *
     * @param tfcBillNo 运输管理中心运输单
     */
    public void setTfcBillNo(String tfcBillNo) {
        this.tfcBillNo = tfcBillNo;
    }

    /**
     * 获取来源平台
     *
     * @return FROM_SYSTEM - 来源平台
     */
    public String getFromSystem() {
        return fromSystem;
    }

    /**
     * 设置来源平台
     *
     * @param fromSystem 来源平台
     */
    public void setFromSystem(String fromSystem) {
        this.fromSystem = fromSystem;
    }

    /**
     * 获取运输单号
     *
     * @return TRANSPORT_NO - 运输单号
     */
    public String getTransportNo() {
        return transportNo;
    }

    /**
     * 设置运输单号
     *
     * @param transportNo 运输单号
     */
    public void setTransportNo(String transportNo) {
        this.transportNo = transportNo;
    }

    /**
     * 获取商品编码
     *
     * @return ITEM_CODE - 商品编码
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * 设置商品编码
     *
     * @param itemCode 商品编码
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 获取商品名称
     *
     * @return ITEM_NAME - 商品名称
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 设置商品名称
     *
     * @param itemName 商品名称
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 获取体积
     *
     * @return QTY - 体积
     */
    public Double getQty() {
        return qty;
    }

    /**
     * 设置体积
     *
     * @param qty 体积
     */
    public void setQty(Double qty) {
        this.qty = qty;
    }

    /**
     * 获取重量
     *
     * @return WEIGHT - 重量
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * 设置重量
     *
     * @param weight 重量
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    /**
     * 获取数量
     *
     * @return VOLUME - 数量
     */
    public Double getVolume() {
        return volume;
    }

    /**
     * 设置数量
     *
     * @param volume 数量
     */
    public void setVolume(Double volume) {
        this.volume = volume;
    }

    /**
     * 获取单价
     *
     * @return PRICE - 单价
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 设置单价
     *
     * @param price 单价
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * 获取金额
     *
     * @return MONEY - 金额
     */
    public Double getMoney() {
        return money;
    }

    /**
     * 设置金额
     *
     * @param money 金额
     */
    public void setMoney(Double money) {
        this.money = money;
    }

    /**
     * 获取单位
     *
     * @return UOM - 单位
     */
    public String getUom() {
        return uom;
    }

    /**
     * 设置单位
     *
     * @param uom 单位
     */
    public void setUom(String uom) {
        this.uom = uom;
    }

    /**
     * 获取箱数
     *
     * @return CONTAINER_QTY - 箱数
     */
    public String getContainerQty() {
        return containerQty;
    }

    /**
     * 设置箱数
     *
     * @param containerQty 箱数
     */
    public void setContainerQty(String containerQty) {
        this.containerQty = containerQty;
    }

    public String getProductionBatch() {
        return productionBatch;
    }

    public void setProductionBatch(String productionBatch) {
        this.productionBatch = productionBatch;
    }

    public Date getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(Date productionTime) {
        this.productionTime = productionTime;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public Integer getTotalBox() {
        return totalBox;
    }

    public void setTotalBox(Integer totalBox) {
        this.totalBox = totalBox;
    }

    public String getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(String goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getChargingWays() {
        return chargingWays;
    }

    public void setChargingWays(String chargingWays) {
        this.chargingWays = chargingWays;
    }
}