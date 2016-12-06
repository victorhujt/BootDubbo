package com.xescm.ofc.model.dto.whc;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 出库单详细实体类
 * */
public class WhcDeliveryDetails {

    /** 主键ID */
    private String id;

    /** 入库单号 */
    private String whcBillno;

    /** 行号 */
    private Integer lineNo;

    /** 货品编码 */
    private String itemCode;

    /** 货品名称 */
    private String itemName;

    /** 货品规格 */
    private String standard;

    /** 单位 */
    private String uom;

    /** 单价 */
    private BigDecimal price;

    /** 出库数量 */
    private BigDecimal qty;

    /** 实际出库数量 */
    private BigDecimal sendQty;

    /** 批次 */
    private String lotInfo;

    /** 生产日期 */
    private Date productionDate;

    /** 失效日期 */
    private Date expiryDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWhcBillno() {
        return whcBillno;
    }

    public void setWhcBillno(String whcBillno) {
        this.whcBillno = whcBillno;
    }

    public Integer getLineNo() {
        return lineNo;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getSendQty() {
        return sendQty;
    }

    public void setSendQty(BigDecimal sendQty) {
        this.sendQty = sendQty;
    }

    public String getLotInfo() {
        return lotInfo;
    }

    public void setLotInfo(String lotInfo) {
        this.lotInfo = lotInfo;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}