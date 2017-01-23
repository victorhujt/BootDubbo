package com.xescm.ofc.enums;

/**
 * @description: 订单类型
 * @author: nothing
 * @date: 2017/1/23 11:01
 */
public enum OrderTypeEnum {

    TRANS_ORDER("60", "运输订单"),
    WAREHOUSE_DIST_ORDER("61", "仓配订单");

    /** 编码 */
    private String code;
    /** 名称 */
    private String name;

    OrderTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(String code) {
        for (OrderTypeEnum typeEnum : OrderTypeEnum.values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum.getName();
            }
        }

        return null;
    }

    public static String getCodeByName(String name) {
        for (OrderTypeEnum typeEnum : OrderTypeEnum.values()) {
            if (typeEnum.getName().equals(name)) {
                return typeEnum.getCode();
            }
        }

        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
