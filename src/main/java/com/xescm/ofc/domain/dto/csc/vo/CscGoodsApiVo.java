package com.xescm.ofc.domain.dto.csc.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Liqd on 2016/11/22.
 */
public class CscGoodsApiVo implements Serializable {

    private String id;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 货品小类id
     */
    private String goodsType;
    /**
     * 货品大类id
     */
    private String goodsTypeId;
    /**
     * 货品大类名称
     */
    private String goodsTypeParentName;
    /**
     * 货品小类名称
     */
    private String goodsTypeName;

    /**
     * 货品编码
     */
    private String goodsCode;

    /**
     * 货品名称
     */
    private String goodsName;

    /**
     * 规格
     */
    private String specification;

    /**
     * 单位
     */
    private String unit;

    /**
     * 成本价
     */
    private String costPrice;

    /**
     * 单价
     */
    private String unitPrice;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建人ID
     */
    private String creatorId;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 最近操作人
     */
    private String lastOperator;

    /**
     * 最后操作人ID
     */
    private String lastOperatorId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除(1-已删除；0-未删除)
     */
    private Integer yn;

    /**
     * 客户ID
     */
    private String customerId;
    /**
     * 条形码
     * */
    private String barCode;
    /**
     * 状态(0-停用；1-正常)
     * */
    private String state;
    /**
     * 保质期限
     * */
    private String expiryDate;
    /**
     * 长
     * */
    private String length;
    /**
     * 宽
     * */
    private String width;
    /**
     * 高
     * */
    private String height;
    /**
     * 体积
     * */
    private String volume;
    /**
     * 重量
     * */
    private String weight;
    /**
     * 货品描述
     * */
    private String description;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 温度带
     */
    private String keeptemperate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
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

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getLastOperator() {
        return lastOperator;
    }

    public void setLastOperator(String lastOperator) {
        this.lastOperator = lastOperator;
    }

    public String getLastOperatorId() {
        return lastOperatorId;
    }

    public void setLastOperatorId(String lastOperatorId) {
        this.lastOperatorId = lastOperatorId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getKeeptemperate() {
        return keeptemperate;
    }

    public void setKeeptemperate(String keeptemperate) {
        this.keeptemperate = keeptemperate;
    }

    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public String getGoodsTypeParentName() {
        return goodsTypeParentName;
    }

    public void setGoodsTypeParentName(String goodsTypeParentName) {
        this.goodsTypeParentName = goodsTypeParentName;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }
}
