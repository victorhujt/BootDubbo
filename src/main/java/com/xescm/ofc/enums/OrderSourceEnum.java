package com.xescm.ofc.enums;

import com.xescm.ofc.constant.CreateOrderApiConstant;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 订单来源
 * Created by hiyond on 2016/12/2.
 */
public enum OrderSourceEnum {

    XEBEST("6001", "鲜易网", CreateOrderApiConstant.XEBEST_CUST_CODE),
    SAP("6002", "SAP", CreateOrderApiConstant.SAP_CUST_CODE),
    OTHERS("6003", "三方", "");

    OrderSourceEnum(String code, String desc, String custCode) {
        this.code = code;
        this.desc = desc;
        this.custCode = custCode;
    }

    private String code;

    private String desc;

    private String custCode;

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

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public static List<OrderSourceEnum> getList() {
        return Arrays.asList(OrderSourceEnum.values());
    }

    public static OrderSourceEnum getOrderSourceEnumByDesc(String desc) {
        for (OrderSourceEnum sourceEnum : getList()) {
            if (StringUtils.equals(sourceEnum.getDesc(), desc))
                return sourceEnum;
        }
        return null;
    }

    public static String getCodeByCustCode(String custCode) {
        for (OrderSourceEnum sourceEnum : getList()) {
            if (StringUtils.equals(sourceEnum.getCustCode(), custCode))
                return sourceEnum.getCode();
        }
        return OrderSourceEnum.OTHERS.getCode();
    }

}
