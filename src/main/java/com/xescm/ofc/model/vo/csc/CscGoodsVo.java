package com.xescm.ofc.model.vo.csc;

/**
 * 封装货品信息以及货品类别
 * Created by Dzy on 2016/11/2.
 */
public class CscGoodsVo {

    /**
     * 主键
     */
    private String id;

    /**
     * 货品类别
     */
    private String goodsType;

    /**
     * 货品类别名称
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
     * 状态
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
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

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    @Override
    public String toString() {
        return "CscGoodsVo{" +
                "id='" + id + '\'' +
                ", goodsType='" + goodsType + '\'' +
                ", goodsTypeName='" + goodsTypeName + '\'' +
                ", goodsCode='" + goodsCode + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", specification='" + specification + '\'' +
                ", unit='" + unit + '\'' +
                ", costPrice='" + costPrice + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", yn=" + yn +
                ", customerId='" + customerId + '\'' +
                ", barCode='" + barCode + '\'' +
                ", state='" + state + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", length='" + length + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", volume='" + volume + '\'' +
                ", weight='" + weight + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
