package com.xescm.ofc.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * Created by lyh on 2017/2/28.
 */
public enum OutRquiredItem {

    CUST_ORDER_CODE("custOrderCode", "客户订单编号"),
    MERCHANDISER("merchandiser", "开单员"),
    WAREHOUSE_NAME("warehouseName", "仓库编码"),
    BUSINESS_TYPE("businessType", "业务类型"),
    GOODS_CODE("goodsCode", "货品编码"),
    QUANTITY("quantity", "出库数量"),
    CONSIGNEE_NAME("consigneeName", "收货方名称");



    private String standardColCode;
    private String standardColName;

    public String getStandardColCode() {
        return standardColCode;
    }

    public void setStandardColCode(String standardColCode) {
        this.standardColCode = standardColCode;
    }

    public String getStandardColName() {
        return standardColName;
    }

    public void setStandardColName(String standardColName) {
        this.standardColName = standardColName;
    }


    OutRquiredItem(String standardColCode, String standardColName) {
        this.standardColCode = standardColCode;
        this.standardColName = standardColName;
    }

    public static List<OutRquiredItem> getList() {
        return Arrays.asList(OutRquiredItem.values());
    }

    public static List<String> getstandardCodeList(){
        List<String> codeList = new ArrayList<>();
        for (OutRquiredItem OutRquiredItem : getList()) {
            codeList.add(OutRquiredItem.getStandardColCode());
        }
        return codeList;
    }

    public static List<String> getstandardNameList(){
        List<String> codeList = new ArrayList<>();
        for (OutRquiredItem OutRquiredItem : getList()) {
            codeList.add(OutRquiredItem.getStandardColName());
        }
        return codeList;
    }

    public static OutRquiredItem getAnyByStandardCode(String standardColCode){
        for (OutRquiredItem OutRquiredItemIn : getList()) {
            if(StringUtils.equals(OutRquiredItemIn.getStandardColCode(), standardColCode)){
                return OutRquiredItemIn;
            }
        }
        return null;
    }
}
