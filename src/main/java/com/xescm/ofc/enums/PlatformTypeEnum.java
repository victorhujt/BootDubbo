package com.xescm.ofc.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 平台类型
 * Created by hiyond on 2016/11/28.
 */
public enum PlatformTypeEnum {

    XIAN_YI("4", "鲜易网");

    PlatformTypeEnum(String code, String desc) {
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

    public static List<PlatformTypeEnum> getList() {
        return Arrays.asList(PlatformTypeEnum.values());
    }

    public static String getDescBycode(String code) {
        for (PlatformTypeEnum typeEnum : getList())
            if (StringUtils.equals(typeEnum.getCode(), code))
                return typeEnum.getDesc();
        return code;
    }
}
