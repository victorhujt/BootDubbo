package com.xescm.ofc.enums;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: ExceBusinessTypeEnum. </p>
 * <p>Description 异常录入类型 </p>
 * <p>Company: http://www.hnxianyi.com </p>
 *
 * @Author 袁宝龙
 * @CreateDate 2017/11/7 10:42
 */
public enum ExceTypeEnum {

    CAR_TEM("CAR_TEM", "车辆温度(订单)"),
    WAREHOUSE_VARIANCE("WAREHOUSE_VARIANCE", "仓储货差"),
    WAREHOUSE_DAMAGE("WAREHOUSE_DAMAGE", "仓储货损"),
    LOADING_TIME("LOADING_TIME", "装卸时效(订单)"),
    SORTING_ERROR("SORTING_ERROR", "分拣差错(订单)"),
    WAREHOUSE_TEM("WAREHOUSE_TEM", "仓储温度"),
    TRANSPORT_DAMAGE("TRANSPORT_DAMAGE", "运输货损(订单)"),
    CAR_HYGIENE("CAR_HYGIENE", "车辆卫生(订单)"),
    RECEIPT_MANAGE("RECEIPT_MANAGE", "回单管理(订单)"),
    DELIVERY_DELAYED("DELIVERY_DELAYED", "配送晚点(订单)"),
    NATURAL_DISASTERS("NATURAL_DISASTERS", "自然灾害"),
    GOODS_LOST("GOODS_LOST", "货物丢失"),
    AMERCE("AMERCE", "罚款");


    ExceTypeEnum(String code, String desc) {
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
        for (ExceTypeEnum ele : ExceTypeEnum.values()) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("desc", ele.getDesc());
            map.put("code", ele.getCode());
            list.add(map);
        }
        return list;
    }
}
