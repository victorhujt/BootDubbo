package com.xescm.ofc.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hiyond on 2016/11/27.
 */
public enum ResourceEnum {

    ZIYUANFENPEIZ("10", "资源分配中"),
    YITUISONG("20", "已推送"),
    RENWUZHONG("30", "任务中"),
    RENWUWANCH("40", "任务完成"),
    YIZUOFEI("50", "已作废");

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
