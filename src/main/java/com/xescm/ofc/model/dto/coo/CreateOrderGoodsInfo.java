package com.xescm.ofc.model.dto.coo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单中心创建订单（鲜易网）
 * 订单货品明细信息
 * Created by hiyond on 2016/11/14.
 */
@XStreamAlias("orderDetail")
public class CreateOrderGoodsInfo implements Serializable {

    private static final long serialVersionUID = 7909253575176134994L;
    /*客户名称*/
    private String custName;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    /**客户编码*/

    private String custCode;

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
     * 货品大类
     */
    private String goodsType;

    /**
     * 货品小类
     */
    private String goodsCategory;

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


    /**
     * 货品供应商批次
     */
    private String supportBatch;

    /**
     * 货品供应商名称
     */
    private String supportName;
    /**
     * 包装名称
     */
    private String packageName;

    /**
     *包装类型
     * @return
     */
    private String packageType;

    /**
     * 与主单位的换算规格
     * @return
     */
    private BigDecimal conversionRate;

    /** 主单位数量 */
    private BigDecimal primaryQuantity;
    /**
     * 行号
     */
    private Integer lineNo;
    /**
     * 厂商批次
     */
    private String manufactureBatchLotatt;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSupportBatch() {
        return supportBatch;
    }

    public void setSupportBatch(String supportBatch) {
        this.supportBatch = supportBatch;
    }

    public String getSupportName() {
        return supportName;
    }

    public void setSupportName(String supportName) {
        this.supportName = supportName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public BigDecimal getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(BigDecimal conversionRate) {
        this.conversionRate = conversionRate;
    }

    public BigDecimal getPrimaryQuantity() {
        return primaryQuantity;
    }

    public void setPrimaryQuantity(BigDecimal primaryQuantity) {
        this.primaryQuantity = primaryQuantity;
    }

    public Integer getLineNo() {
        return lineNo;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    public String getManufactureBatchLotatt() {
        return manufactureBatchLotatt;
    }

    public void setManufactureBatchLotatt(String manufactureBatchLotatt) {
        this.manufactureBatchLotatt = manufactureBatchLotatt;
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

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(String goodsCategory) {
        this.goodsCategory = goodsCategory;
    }
}
