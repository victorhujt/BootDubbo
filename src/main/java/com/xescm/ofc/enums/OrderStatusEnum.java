package com.xescm.ofc.enums;

import java.util.Arrays;
import java.util.List;

/**
 * 订单状态
 * Created by hujintao on 2017/11/06.
 */

public enum OrderStatusEnum {

    PEND_AUDIT("10", "待审核"),
    ALREADY_ACCEPTED("30", "已受理"),
    OUTPUT_COMPLETED("32", "出库完成"),
    ALREADY_SHIPPED("34", "已发运"),
    ALREADY_SIGNED("36", "已签收"),
    INPUT_COMPLETED("38", "入库完成"),
    BEEN_COMPLETED("40", "已完成"),
    BEEN_CANCELED("50", "已取消");

    private String code;

    private String desc;

    OrderStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

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

    public static List<OrderStatusEnum> queryList() {
        return Arrays.asList(OrderStatusEnum.values());
    }

}
