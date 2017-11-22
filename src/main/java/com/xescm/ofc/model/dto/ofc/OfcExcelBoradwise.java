package com.xescm.ofc.model.dto.ofc;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lyh on 2016/12/16.
 */
@Data
public class OfcExcelBoradwise {
    private String custOrderCode;
    private Date orderTime;
    private String consigneeName;
    private String consigneeContactName;
    private String consigneeContactPhone;
    private String consigneeAddress;
    private String goodsCode;
    private String goodsName;
    private String goodsSpec;
    private String goodsUnit;
    private BigDecimal goodsAmount;
    private BigDecimal goodsUnitPirce;
    public OfcExcelBoradwise(){}

    public OfcExcelBoradwise(String custOrderCode, Date orderTime, String consigneeName, String consigneeContactName, String consigneeContactPhone, String consigneeAddress, String goodsCode, String goodsName, String goodsSpec, String goodsUnit, BigDecimal goodsAmount, BigDecimal goodsUnitPirce) {
        this.custOrderCode = custOrderCode;
        this.orderTime = orderTime;
        this.consigneeName = consigneeName;
        this.consigneeContactName = consigneeContactName;
        this.consigneeContactPhone = consigneeContactPhone;
        this.consigneeAddress = consigneeAddress;
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.goodsSpec = goodsSpec;
        this.goodsUnit = goodsUnit;
        this.goodsAmount = goodsAmount;
        this.goodsUnitPirce = goodsUnitPirce;
    }
}
