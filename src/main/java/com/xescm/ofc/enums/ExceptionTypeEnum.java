package com.xescm.ofc.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @description: 异常枚举
 * @author: nothing
 * @date: 2017/4/14 12:29
 */
public enum ExceptionTypeEnum {

    LOCK_SUCCESS("L0000", "加锁成功"),
    LOCK_FAIL("L0001", "加锁失败"),
    LOCK_EXCEPTION("L0002", "加锁异常"),
    LOCK_EXIST("L0003", "锁存在");

    ExceptionTypeEnum(String code, String desc) {
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

    public static List<ExceptionTypeEnum> getList() {
        return Arrays.asList(ExceptionTypeEnum.values());
    }

    public static String getExceptionTypeByCode(String code) {
        for (ExceptionTypeEnum typeEnum : getList()) {
            if(StringUtils.equals(typeEnum.getCode(), code)){
                return typeEnum.getDesc();
            }
        }
        return code;
    }
}
