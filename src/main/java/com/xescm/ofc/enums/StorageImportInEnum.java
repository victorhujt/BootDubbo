package com.xescm.ofc.enums;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 *
 * Created by lyh on 2017/2/27.
 */

public enum StorageImportInEnum {

    CUST_ORDER_CODE("custOrderCode","客户订单号"),
    ORDER_TIME("orderTime","订单日期"),
    MERCHANDISER("merchandiser","开单员"),
    WAREHOUSE_NAME("warehouseName","仓库名称"),
    BUSINESS_TYPE("businessType","业务类型"),
    NOTES("notes","备注"),
    GOODS_CODE("goodsCode","货品编码"),
    GOODS_NAME("goodsName","货品名称"),
    GOODS_SPEC("goodsSpec","规格"),
    UNIT("unit","单位"),
    UNIT_PRICE("unitPrice","单价"),
    QUANTITY("quantity","入库数量"),
    PRODUCTION_BATCH("productionBatch","批次号"),
    PRODUCTION_TIME("productionTime","生产日期"),
    INVALID_TIME("invalidTime","失效日期"),
    SUPPORT_NAME("supportName","供应商名称"),
    ARRIVE_TIME("arriveTime","预计入库时间"),
    PROVIDE_TRANSPORT("provideTransport","是否提供运输服务"),
    TRANS_CODE("transCode","运输单号"),
    PLATE_NUMBER("plateNumber","车牌号"),
    DRIVER_NAME("driverName","司机姓名"),
    CONTACT_NUMBER("contactNumber","联系电话"),
    SUPPORT_BATCH("supportBatch","供应商批次"),
    CONSIGNOR_NAME("consignorName","发货方名称");


    private String standardColCode;

    private String standardColName;

    public String getStandardColCode() {
        return standardColCode;
    }

    public void setStandardColCode(String standardColCode) {
        this.standardColCode = standardColCode;
    }

    public String getStandardColName() {
        return standardColName;
    }

    public void setStandardColName(String standardColName) {
        this.standardColName = standardColName;
    }

    StorageImportInEnum(String standardColCode, String standardColName) {
        this.standardColCode = standardColCode;
        this.standardColName = standardColName;
    }

    public static List<StorageImportInEnum> queryList() {
        return Arrays.asList(StorageImportInEnum.values());
    }
}
