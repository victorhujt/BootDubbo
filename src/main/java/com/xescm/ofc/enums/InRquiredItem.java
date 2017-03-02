package com.xescm.ofc.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * Created by lyh on 2017/2/28.
 */
public enum InRquiredItem {

    CUST_ORDER_CODE("custOrderCode", "客户订单编号"),
    MERCHANDISER("merchandiser", "开单员"),
    WAREHOUSE_NAME("warehouseName", "仓库编码"),
    BUSINESS_TYPE("businessType", "业务类型"),
    GOODS_CODE("goodsCode", "货品编码"),
    QUANTITY("quantity", "入库数量");



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


    InRquiredItem(String standardColCode, String standardColName) {
        this.standardColCode = standardColCode;
        this.standardColName = standardColName;
    }

    public static List<InRquiredItem> getList() {
        return Arrays.asList(InRquiredItem.values());
    }

    public static List<String> getstandardCodeList(){
        List<String> codeList = new ArrayList<>();
        for (InRquiredItem inRquiredItem : getList()) {
            codeList.add(inRquiredItem.getStandardColCode());
        }
        return codeList;
    }

    public static List<String> getstandardNameList(){
        List<String> codeList = new ArrayList<>();
        for (InRquiredItem inRquiredItem : getList()) {
            codeList.add(inRquiredItem.getStandardColName());
        }
        return codeList;
    }

    public static InRquiredItem getAnyByStandardCode(String standardColCode){
        for (InRquiredItem inRquiredItemIn : getList()) {
            if(StringUtils.equals(inRquiredItemIn.getStandardColCode(), standardColCode)){
                return inRquiredItemIn;
            }
        }
        return null;
    }

}
