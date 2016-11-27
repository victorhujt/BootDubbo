package com.xescm.ofc.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 计划资源分配状态
 * Created by hiyond on 2016/11/27.
 */
public enum PlanEnum {

    ZIYUANFENPEIZ("10", "资源分配中"),
    YITUISONG("20", "已推送"),
    RENWUZHONG("30", "任务中"),
    RENWUWANCH("40", "任务完成"),
    YIZUOFEI("50", "已作废");

    PlanEnum(String code, String desc) {
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

    public static List<PlanEnum> getList() {
        return Arrays.asList(PlanEnum.values());
    }

    public static String getDescByCode(String code) {
        for (PlanEnum planEnum : getList()) {
            if (StringUtils.equals(planEnum.getCode(), code)) {
                return planEnum.getDesc();
            }
        }
        return code;
    }

}
