package com.xescm.ofc.domain.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tbl_csc_goods")
public class CscGoods implements Serializable{
    private static final long serialVersionUID = 8946219384405647243L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 客户编码
     */
    @Column(name = "customer_code")
    private String customerCode;

    /**
     * 货品类型
     */
    @Column(name = "goods_type")
    private String goodsType;

    /**
     * 货品编码
     */
    @Column(name = "goods_code")
    private String goodsCode;

    /**
     * 货品名称
     */
    @Column(name = "goods_name")
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
    @Column(name = "cost_price")
    private String costPrice;

    /**
     * 单价
     */
    @Column(name = "unit_price")
    private String unitPrice;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建人ID
     */
    @Column(name = "creator_id")
    private String creatorId;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdTime;

    /**
     * 最近操作人
     */
    @Column(name = "last_operator")
    private String lastOperator;

    /**
     * 最后操作人ID
     */
    @Column(name = "last_operator_id")
    private String lastOperatorId;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updateTime;

    /**
     * 删除(1-已删除；0-未删除)
     */
    private Integer yn;

    /**
     * 客户ID
     */
    @Column(name = "customer_id")
    private String customerId;

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
     * 获取客户编码
     *
     * @return customer_code - 客户编码
     */
    public String getCustomerCode() {
        return customerCode;
    }

    /**
     * 设置客户编码
     *
     * @param customerCode 客户编码
     */
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    /**
     * 获取货品类型
     *
     * @return goods_type - 货品类型
     */
    public String getGoodsType() {
        return goodsType;
    }

    /**
     * 设置货品类型
     *
     * @param goodsType 货品类型
     */
    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    /**
     * 获取货品编码
     *
     * @return goods_code - 货品编码
     */
    public String getGoodsCode() {
        return goodsCode;
    }

    /**
     * 设置货品编码
     *
     * @param goodsCode 货品编码
     */
    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    /**
     * 获取货品名称
     *
     * @return goods_name - 货品名称
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * 设置货品名称
     *
     * @param goodsName 货品名称
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * 获取规格
     *
     * @return specification - 规格
     */
    public String getSpecification() {
        return specification;
    }

    /**
     * 设置规格
     *
     * @param specification 规格
     */
    public void setSpecification(String specification) {
        this.specification = specification;
    }

    /**
     * 获取单位
     *
     * @return unit - 单位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置单位
     *
     * @param unit 单位
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 获取成本价
     *
     * @return cost_price - 成本价
     */
    public String getCostPrice() {
        return costPrice;
    }

    /**
     * 设置成本价
     *
     * @param costPrice 成本价
     */
    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    /**
     * 获取单价
     *
     * @return unit_price - 单价
     */
    public String getUnitPrice() {
        return unitPrice;
    }

    /**
     * 设置单价
     *
     * @param unitPrice 单价
     */
    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * 获取创建人
     *
     * @return creator - 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人
     *
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 获取创建人ID
     *
     * @return creator_id - 创建人ID
     */
    public String getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建人ID
     *
     * @param creatorId 创建人ID
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取创建时间
     *
     * @return created_time - 创建时间
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 设置创建时间
     *
     * @param createdTime 创建时间
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 获取最近操作人
     *
     * @return last_operator - 最近操作人
     */
    public String getLastOperator() {
        return lastOperator;
    }

    /**
     * 设置最近操作人
     *
     * @param lastOperator 最近操作人
     */
    public void setLastOperator(String lastOperator) {
        this.lastOperator = lastOperator;
    }

    /**
     * 获取最后操作人ID
     *
     * @return last_operator_id - 最后操作人ID
     */
    public String getLastOperatorId() {
        return lastOperatorId;
    }

    /**
     * 设置最后操作人ID
     *
     * @param lastOperatorId 最后操作人ID
     */
    public void setLastOperatorId(String lastOperatorId) {
        this.lastOperatorId = lastOperatorId;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取删除(1-已删除；0-未删除)
     *
     * @return yn - 删除(1-已删除；0-未删除)
     */
    public Integer getYn() {
        return yn;
    }

    /**
     * 设置删除(1-已删除；0-未删除)
     *
     * @param yn 删除(1-已删除；0-未删除)
     */
    public void setYn(Integer yn) {
        this.yn = yn;
    }

    /**
     * 获取客户ID
     *
     * @return customer_id - 客户ID
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * 设置客户ID
     *
     * @param customerId 客户ID
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "CscGoods{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", customerCode='" + customerCode + '\'' +
                ", goodsType='" + goodsType + '\'' +
                ", goodsCode='" + goodsCode + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", specification='" + specification + '\'' +
                ", unit='" + unit + '\'' +
                ", costPrice='" + costPrice + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", creator='" + creator + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", createdTime=" + createdTime +
                ", lastOperator='" + lastOperator + '\'' +
                ", lastOperatorId='" + lastOperatorId + '\'' +
                ", updateTime=" + updateTime +
                ", yn=" + yn +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}