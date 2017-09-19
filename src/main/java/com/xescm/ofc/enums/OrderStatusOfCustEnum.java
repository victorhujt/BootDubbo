package com.xescm.ofc.enums;

import java.util.Arrays;
import java.util.List;

/**
 *
 * Created by lyh on 2017/9/14.
 */
public enum  OrderStatusOfCustEnum{

    PEND_AUDIT("10", "待审核"),
    UNCONFIRMED("11", "未确认"),
    CONFIRMED("12", "已确认"),
    ALREADY_EXAMINE("20", "已审核"),
    IMPLEMENTATION_IN("30", "执行中"),
    BEEN_COMPLETED("40", "已完成"),
    BEEN_CANCELED("50", "已取消");

    private String code;

    private String desc;

    OrderStatusOfCustEnum(String code, String desc) {
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

    public static List<OrderStatusOfCustEnum> queryList() {
        return Arrays.asList(OrderStatusOfCustEnum.values());
    }

}
