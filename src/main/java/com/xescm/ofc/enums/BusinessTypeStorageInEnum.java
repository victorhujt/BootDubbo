package com.xescm.ofc.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * Created by lyh on 2017/2/28.
 */
public enum BusinessTypeStorageInEnum {
    PURCHASING_AND_STORAGE("620", "采购入库"),
    ALLOCATE_STORAGE("621", "调拨入库"),
    RETURN_WAREHOUSING("622", "退货入库"),
    PROCESSING_STORAGE("623", "加工入库"),
    INVENTORY_PROFIT_STORAGE("624", "盘盈入库"),
    CIRCULATE_STORAGE("625", "流通入库"),
    OTHER_STORAGE("626", "其他入库"),
    ALLOT_STORAGE("627", "分拨入库");

    BusinessTypeStorageInEnum(String code, String desc) {
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

    public static List<BusinessTypeStorageInEnum> getList() {
        return Arrays.asList(BusinessTypeStorageInEnum.values());
    }

    public static List<String> getCodeList(){
        List<String> codeList = new ArrayList<>();
        for (BusinessTypeStorageInEnum businessTypeStorageInEnum : getList()) {
            codeList.add(businessTypeStorageInEnum.getCode());
        }
        return codeList;
    }

    public static List<String> getDescList(){
        List<String> codeList = new ArrayList<>();
        for (BusinessTypeStorageInEnum businessTypeStorageInEnum : getList()) {
            codeList.add(businessTypeStorageInEnum.getDesc());
        }
        return codeList;
    }



    public static String getBusinessTypeDescByCode(String code) {
        for (BusinessTypeStorageInEnum typeEnum : getList()) {
            if(StringUtils.equals(typeEnum.getCode(), code)){
                return typeEnum.getDesc();
            }
        }
        return code;
    }
}
