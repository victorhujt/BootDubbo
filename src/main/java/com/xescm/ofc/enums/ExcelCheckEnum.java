package com.xescm.ofc.enums;

/**
 *
 * Created by lyh on 2017/1/20.
 */
public enum ExcelCheckEnum {
    CUST_ORDER_CODE("custOrderCode", "客户订单号"),

    ORDER_TIME("orderTime", "订单日期"),

    CONSIGNEE_NAME("consigneeName", "收货方名称"),

    CONSIGNEECONTACT_NAME("consigneeContactName", "联系人"),

    CONSIGNEECONTACT_PHONE("consigneeContactPhone", "联系电话"),

    CONSIGNEE_ADDRESS("consigneeAddress", "地址"),

    GOODS_CODE("goodsCode", "货品编码"),

    GOODS_NAME("goodsName", "货品名称"),

    GOODS_SPEC("goodsSpec", "规格"),

    GOODS_UNIT("goodsUnit", "单位"),

    GOODS_AMOUNT("goodsAmount", "数量"),

    GOODS_UNITPIRCE("goodsUnitPirce", "单价");


    private String code;
    private String desc;

    ExcelCheckEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
