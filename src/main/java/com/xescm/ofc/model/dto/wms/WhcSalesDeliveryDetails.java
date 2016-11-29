package com.xescm.ofc.model.dto.wms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(description = "销售出单详细信息出库单")
public class WhcSalesDeliveryDetails {
    private String id;

    @ApiModelProperty(notes = "流水号",required = true)
    private String whcBillno;

    @ApiModelProperty(notes = "客户编码",required = true)
    private String customerCode;

    @ApiModelProperty(notes = "货品编码",required = true)
    private String itemCode;

    @ApiModelProperty(notes = "货品名称",required = true)
    private String itemName;

    @ApiModelProperty(notes = "价格",required = true)
    private BigDecimal price;

    @ApiModelProperty(notes = "单位",required = true)
    private String uom;

    @ApiModelProperty(notes = "包装",required = true)
    private String packid;

    @ApiModelProperty(notes = "货品重量",required = true)
    private BigDecimal itemWeight;

    @ApiModelProperty(notes = "货品体积",required = true)
    private BigDecimal itemCubic;

    @ApiModelProperty(notes = "订货数",required = true)
    private BigDecimal qtyOrdered;

    private String lotAtt01;

    private String lotAtt02;

    private String lotAtt03;

    private String lotAtt04;

    private String lotAtt05;

    private String lotAtt06;

    private String lotAtt07;

    private String lotAtt08;

    private String lotAtt09;

    private String lotAtt10;

    private String lotAtt11;

    private String lotAtt12;

    private String userDefine1;

    private String userDefine2;

    private String userDefine3;

    private String userDefine4;

    private String userDefine5;

    private String userDefine6;

    @ApiModelProperty(notes = "备注")
    private String notes;

    @ApiModelProperty(notes = "生效时间",required = true)
    private Date makeDate;

    @ApiModelProperty(notes = "失效时间",required = true)
    private Date loseDate;

    @ApiModelProperty(notes = "入库日期",required = true)
    private Date inStorageDate;

    @ApiModelProperty(notes = "是否良品",required = true)
    private String itemStatus;

    @ApiModelProperty(notes = "出货数",required = true)
    private Long outqty;


    private WhcSalesDelivery order;

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

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getPackid() {
        return packid;
    }

    public void setPackid(String packid) {
        this.packid = packid;
    }

    public BigDecimal getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(BigDecimal itemWeight) {
        this.itemWeight = itemWeight;
    }

    public BigDecimal getItemCubic() {
        return itemCubic;
    }

    public void setItemCubic(BigDecimal itemCubic) {
        this.itemCubic = itemCubic;
    }

    public String getLotAtt01() {
        return lotAtt01;
    }

    public void setLotAtt01(String lotAtt01) {
        this.lotAtt01 = lotAtt01;
    }

    public String getLotAtt02() {
        return lotAtt02;
    }

    public void setLotAtt02(String lotAtt02) {
        this.lotAtt02 = lotAtt02;
    }

    public String getLotAtt03() {
        return lotAtt03;
    }

    public void setLotAtt03(String lotAtt03) {
        this.lotAtt03 = lotAtt03;
    }

    public String getLotAtt04() {
        return lotAtt04;
    }

    public void setLotAtt04(String lotAtt04) {
        this.lotAtt04 = lotAtt04;
    }

    public String getLotAtt05() {
        return lotAtt05;
    }

    public void setLotAtt05(String lotAtt05) {
        this.lotAtt05 = lotAtt05;
    }

    public String getLotAtt06() {
        return lotAtt06;
    }

    public void setLotAtt06(String lotAtt06) {
        this.lotAtt06 = lotAtt06;
    }

    public String getLotAtt07() {
        return lotAtt07;
    }

    public void setLotAtt07(String lotAtt07) {
        this.lotAtt07 = lotAtt07;
    }

    public String getLotAtt08() {
        return lotAtt08;
    }

    public void setLotAtt08(String lotAtt08) {
        this.lotAtt08 = lotAtt08;
    }

    public String getLotAtt09() {
        return lotAtt09;
    }

    public void setLotAtt09(String lotAtt09) {
        this.lotAtt09 = lotAtt09;
    }

    public String getLotAtt10() {
        return lotAtt10;
    }

    public void setLotAtt10(String lotAtt10) {
        this.lotAtt10 = lotAtt10;
    }

    public String getLotAtt11() {
        return lotAtt11;
    }

    public void setLotAtt11(String lotAtt11) {
        this.lotAtt11 = lotAtt11;
    }

    public String getLotAtt12() {
        return lotAtt12;
    }

    public void setLotAtt12(String lotAtt12) {
        this.lotAtt12 = lotAtt12;
    }

    public BigDecimal getQtyOrdered() {
        return qtyOrdered;
    }

    public void setQtyOrdered(BigDecimal qtyOrdered) {
        this.qtyOrdered = qtyOrdered;
    }

    public String getUserDefine1() {
        return userDefine1;
    }

    public void setUserDefine1(String userDefine1) {
        this.userDefine1 = userDefine1;
    }

    public String getUserDefine2() {
        return userDefine2;
    }

    public void setUserDefine2(String userDefine2) {
        this.userDefine2 = userDefine2;
    }

    public String getUserDefine3() {
        return userDefine3;
    }

    public void setUserDefine3(String userDefine3) {
        this.userDefine3 = userDefine3;
    }

    public String getUserDefine4() {
        return userDefine4;
    }

    public void setUserDefine4(String userDefine4) {
        this.userDefine4 = userDefine4;
    }

    public String getUserDefine5() {
        return userDefine5;
    }

    public void setUserDefine5(String userDefine5) {
        this.userDefine5 = userDefine5;
    }

    public String getUserDefine6() {
        return userDefine6;
    }

    public void setUserDefine6(String userDefine6) {
        this.userDefine6 = userDefine6;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public WhcSalesDelivery getOrder() {
        return order;
    }

    public void setOrder(WhcSalesDelivery order) {
        this.order = order;
    }

    public Date getMakeDate() {
        return makeDate;
    }

    public void setMakeDate(Date makeDate) {
        this.makeDate = makeDate;
    }

    public Date getLoseDate() {
        return loseDate;
    }

    public void setLoseDate(Date loseDate) {
        this.loseDate = loseDate;
    }

    public Date getInStorageDate() {
        return inStorageDate;
    }

    public void setInStorageDate(Date inStorageDate) {
        this.inStorageDate = inStorageDate;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Long getOutqty() {
        return outqty;
    }

    public void setOutqty(Long outqty) {
        this.outqty = outqty;
    }
}