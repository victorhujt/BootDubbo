package com.xescm.ofc.model.dto.wms;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

@ApiModel(description = "入库详情")
public class WhcTransReceiveDetail {
    private String id;
    @ApiModelProperty(value = "仓库中心入库单号", required = true)
    private String whcBillno;
    @ApiModelProperty(value = "行号", required = true)
    private Integer lineno;
    @ApiModelProperty(value = "货主编号", required = true)
    private String customerCode;
    @ApiModelProperty(value = "货主名称", required = true)
    private String customerName;
    @ApiModelProperty(value = "货品编号", required = true)
    private String itemCode;
    @ApiModelProperty(value = "货品名称", required = true)
    private String itemName;
    @ApiModelProperty(value = "货品数量", required = true)
    private BigDecimal qty;
    @ApiModelProperty(value = "单位", required = true)
    private String uom;
    @ApiModelProperty(value = "包装", required = true)
    private String packid;
    @ApiModelProperty(value = "货品重量", required = true)
    private BigDecimal itemWeight;
    @ApiModelProperty(value = "货品体积", required = true)
    private BigDecimal itemCubic;
    @ApiModelProperty(value = "规格", required = true)
    private String standard;
    @ApiModelProperty(value = "实际入库数量", required = true)
    private BigDecimal receivedQty;

    private String usrdf;

    private String usrdf2;

    private String usrdf3;

    private String usrdf4;

    private String usrdf5;

    private String usrdf6;
    @ApiModelProperty(value = "备注")
    private String notes;

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public BigDecimal getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(BigDecimal receivedQty) {
        this.receivedQty = receivedQty;
    }

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
        this.whcBillno = whcBillno == null ? null : whcBillno.trim();
    }

    public Integer getLineno() {
        return lineno;
    }

    public void setLineno(Integer lineno) {
        this.lineno = lineno;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode == null ? null : customerCode.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom == null ? null : uom.trim();
    }

    public String getPackid() {
        return packid;
    }

    public void setPackid(String packid) {
        this.packid = packid == null ? null : packid.trim();
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

    public String getUsrdf() {
        return usrdf;
    }

    public void setUsrdf(String usrdf) {
        this.usrdf = usrdf == null ? null : usrdf.trim();
    }

    public String getUsrdf2() {
        return usrdf2;
    }

    public void setUsrdf2(String usrdf2) {
        this.usrdf2 = usrdf2 == null ? null : usrdf2.trim();
    }

    public String getUsrdf3() {
        return usrdf3;
    }

    public void setUsrdf3(String usrdf3) {
        this.usrdf3 = usrdf3 == null ? null : usrdf3.trim();
    }

    public String getUsrdf4() {
        return usrdf4;
    }

    public void setUsrdf4(String usrdf4) {
        this.usrdf4 = usrdf4 == null ? null : usrdf4.trim();
    }

    public String getUsrdf5() {
        return usrdf5;
    }

    public void setUsrdf5(String usrdf5) {
        this.usrdf5 = usrdf5 == null ? null : usrdf5.trim();
    }

    public String getUsrdf6() {
        return usrdf6;
    }

    public void setUsrdf6(String usrdf6) {
        this.usrdf6 = usrdf6 == null ? null : usrdf6.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    @Override
    public String toString() {
        return "WhcTransReceiveDetail{" +
                "id='" + id + '\'' +
                ", whcBillno='" + whcBillno + '\'' +
                ", lineno=" + lineno +
                ", customerCode='" + customerCode + '\'' +
                ", customerName='" + customerName + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", qty=" + qty +
                ", uom='" + uom + '\'' +
                ", packid='" + packid + '\'' +
                ", itemWeight=" + itemWeight +
                ", itemCubic=" + itemCubic +
                ", standard='" + standard + '\'' +
                ", receivedQty=" + receivedQty +
                ", usrdf='" + usrdf + '\'' +
                ", usrdf2='" + usrdf2 + '\'' +
                ", usrdf3='" + usrdf3 + '\'' +
                ", usrdf4='" + usrdf4 + '\'' +
                ", usrdf5='" + usrdf5 + '\'' +
                ", usrdf6='" + usrdf6 + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}