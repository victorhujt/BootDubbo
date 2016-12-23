package com.xescm.ofc.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 计划单资源分配状态枚举类
 * Created by hiyond on 2016/11/27.
 */
public enum ResourceEnum {

    TO_BE_DISTRIBUTED("10", "待分配"),
    NOT_DISTRIBUTED("20", "未分配"),
    ALREADY_DISTRIBUTED("30", "已分配"),
    ALREADY_SURE("40", "已确定");

    ResourceEnum(String code, String desc) {
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

    public static List<ResourceEnum> getList() {
        return Arrays.asList(ResourceEnum.values());
    }

    public static String getDescByCode(String code) {
        for (ResourceEnum resourceEnum : getList()) {
            if (StringUtils.equals(resourceEnum.getCode(), code)) {
                return resourceEnum.getDesc();
            }
        }
        return code;
    }

}
