package com.xescm.ofc.enums;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: ExceBusinessTypeEnum. </p>
 * <p>Description 异常录入业务类型 </p>
 * <p>Company: http://www.hnxianyi.com </p>
 *
 * @Author 袁宝龙
 * @CreateDate 2017/11/7 10:42
 */
public enum ExceBusinessTypeEnum {


    WITH_CITY("600", "城配"),
    MAIN_LINE("601", "干线"),
    CABANNES("602", "卡班"),
    WAREHOUSE("603", "仓储");

    ExceBusinessTypeEnum(String code, String desc) {
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

    public static List<Map<String, Object>> getMap2List() {
        List<Map<String, Object>> list = Lists.newArrayList();
        for (ExceBusinessTypeEnum ele : ExceBusinessTypeEnum.values()) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("desc", ele.getDesc());
            map.put("code", ele.getCode());
            list.add(map);
        }
        return list;
    }
}
