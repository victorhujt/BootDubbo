package com.xescm.ofc.enums;

/**
 * Created by ydx on 2016/10/11.
 */
public final class OrderConstEnum {
    public static final String TRANSPORTORDER="60";     //运输订单
    public static final String WAREHOUSEDISTRIBUTIONORDER="61";     //仓配订单

    public static final String WITHTHECITY="600";       //城配
    public static final String WITHTHETRUNK="601";      //干线

    public static final String SALESOUTOFTHELIBRARY="610";      //销售出库
    public static final String TRANSFEROUTOFTHELIBRARY="611";        //调拨出库
    public static final String LOSSOFREPORTING="612";        //报损出库
    public static final String OTHEROUTOFTHELIBRARY="613";       //其他出库
    public static final String PURCHASINGANDSTORAGE="620";       //采购入库
    public static final String ALLOCATESTORAGE="621";        //调拨入库
    public static final String RETURNWAREHOUSING="622";      //退货入库
    public static final String PROCESSINGSTORAGE="623";      //加工入库

    public static final String PENDINGAUDIT="10";       //待审核
    public static final String ALREADYEXAMINE="20";     //已审核
    public static final String IMPLEMENTATIONIN="30";       //执行中
    public static final String HASBEENCOMPLETED="40";       //已完成
    public static final String HASBEENCANCELED="50";       //已取消

    public static final Integer WAREHOUSEORDERPROVIDETRANS=1;       //仓储订单提供运输
    public static final Integer WAREHOUSEORDERNOTPROVIDETRANS=0;       //仓储订单不提供运输

    public static final Integer DISTRIBUTIONORDERURGENT=1;       //运输订单加急
    public static final Integer DISTRIBUTIONORDERNOTURGENT=0;       //运输订单不加急

    public static final Integer ORDERWASABOLISHED=1;       //订单作废
    public static final Integer ORDERWASNOTABOLISHED=0;       //订单未作废

    public static final String CONTACTPURPOSECONSIGNOR = "2"; //联系人用途,发货人
    public static final String CONTACTPURPOSECONSIGNEE = "1"; //联系人用途,收货人

    public static final String DAIFENPEI="10";  //待分配
    public static final String WEIFENPEI="20";  //未分配
    public static final String YIFENPEI="30";   //已分配
    public static final String YIQUEDING="40";  //已确定

    public OrderConstEnum() {
    }
}
