package com.xescm.ofc.model.dto.csc;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lyh on 2017/7/11.
 */
public class CscGoodsApiDTO implements Serializable{

    private String customerCode;
    private String goodsCode;
    private String goodsName;
    private String goodsTypeId;
    private String goodsTypeSonId;
    private String barCode;
    private String fromSys;
    private String warehouseCode;
    private int pageNum;
    private int pageSize;

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
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

    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public String getGoodsTypeSonId() {
        return goodsTypeSonId;
    }

    public void setGoodsTypeSonId(String goodsTypeSonId) {
        this.goodsTypeSonId = goodsTypeSonId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getFromSys() {
        return fromSys;
    }

    public void setFromSys(String fromSys) {
        this.fromSys = fromSys;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
