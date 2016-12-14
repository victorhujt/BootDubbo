package com.xescm.ofc.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 业务类型
 * Created by hiyond on 2016/11/28.
 */
public enum BusinessTypeEnum {

    WITH_CITY("600", "城配"),
    MAIN_LINE("601", "干线"),
    CABANNES("602", "卡班"),
    SALE_OUT("610", "销售出库"),
    ALLOT_OUT("611", "调拨出库"),
    SCRAP_OUT("612", "报损出库"),
    OTHER_OUT("613", "其他出库"),
    PURCHASE_OUT("620", "采购入库"),
    ALLOT_IN("621", "调拨入库"),
    REFUNDS_OUT("622", "退货入库"),
    WORK_CITY("623", "加工入库");

    BusinessTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;

    private String desc;

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

    public static List<BusinessTypeEnum> getList() {
        return Arrays.asList(BusinessTypeEnum.values());
    }

    public static String getBusinessTypeDescByCode(String code) {
        for (BusinessTypeEnum typeEnum : getList()) {
            if(StringUtils.equals(typeEnum.getCode(), code)){
                return typeEnum.getDesc();
            }
        }
        return code;
    }
}
