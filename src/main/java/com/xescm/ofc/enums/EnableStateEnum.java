package com.xescm.ofc.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 启用与禁用
 * Created by hiyond on 2016/12/19.
 */
public enum EnableStateEnum {

    ENABLE("10","启用"),

    DISABLE("20","禁用");

    EnableStateEnum(String code, String desc) {
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

    public static List<EnableStateEnum> getList() {
        return Arrays.asList(EnableStateEnum.values());
    }

    public static String getDescByCode(String code) {
        for (EnableStateEnum enableStateEnum : getList())
            if (StringUtils.equals(enableStateEnum.getCode(), code))
                return enableStateEnum.getDesc();
        return null;
    }
}
