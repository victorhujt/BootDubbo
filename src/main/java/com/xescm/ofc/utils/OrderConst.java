package com.xescm.ofc.utils;

/**
 * Created by ydx on 2016/10/11.
 */
public final class OrderConst {
    public static final String TRANSPORTORDER="0";     //运输订单
    public static final String WAREHOUSEDISTRIBUTIONORDER="1";     //仓配订单

    public static final String WITHTHECITY="00";       //城配
    public static final String WITHTHETRUNK="01";      //干线

    public static final String SALESOUTOFTHELIBRARY="10";      //销售出库
    public static final String TRANSFEROUTOFTHELIBRARY="11";        //调拨出库
    public static final String LOSSOFREPORTING="12";        //报损出库
    public static final String OTHEROUTOFTHELIBRARY="13";       //其他出库
    public static final String PURCHASINGANDSTORAGE="14";       //采购入库
    public static final String ALLOCATESTORAGE="15";        //调拨入库
    public static final String RETURNWAREHOUSING="16";      //退货入库
    public static final String PROCESSINGSTORAGE="17";      //加工入库

    public static final String PENDINGAUDIT="0";       //待审核
    public static final String ALREADYEXAMINE="1";     //已审核
    public static final String IMPLEMENTATIONIN="2";       //执行中
    public static final String HASBEENCOMPLETED="3";       //已完成
    public static final String HASBEENCANCELED="4";       //已取消

    public OrderConst() {
    }
}
