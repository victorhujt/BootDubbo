package com.xescm.ofc.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * Created by lyh on 2017/2/28.
 */
public enum BusinessTypeStorageOutEnum {
    SALES_OUT_OF_THE_LIBRARY("610", "销售出库"),
    TRANSFER_OUT_OF_THE_LIBRARY("611", "调拨出库"),
    LOSS_OF_REPORTING("612", "报损出库"),
    OTHER_OUT_OF_THE_LIBRARY("613", "其他出库"),
    ALLOT_OUT_OF_THE_LIBRARY("614", "分拨出库");

    BusinessTypeStorageOutEnum(String code, String desc) {
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

    public static List<BusinessTypeStorageOutEnum> getList() {
        return Arrays.asList(BusinessTypeStorageOutEnum.values());
    }

    public static List<String> getCodeList(){
        List<String> codeList = new ArrayList<>();
        for (BusinessTypeStorageOutEnum BusinessTypeStorageOutEnum : getList()) {
            codeList.add(BusinessTypeStorageOutEnum.getCode());
        }
        return codeList;
    }

    public static List<String> getDescList(){
        List<String> codeList = new ArrayList<>();
        for (BusinessTypeStorageOutEnum BusinessTypeStorageOutEnum : getList()) {
            codeList.add(BusinessTypeStorageOutEnum.getDesc());
        }
        return codeList;
    }
    public static String getBusinessTypeDescByCode(String code) {
        for (BusinessTypeStorageOutEnum typeEnum : getList()) {
            if(StringUtils.equals(typeEnum.getCode(), code)){
                return typeEnum.getDesc();
            }
        }
        return code;
    }
}
