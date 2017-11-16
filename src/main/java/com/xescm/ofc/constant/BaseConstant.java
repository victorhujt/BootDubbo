package com.xescm.ofc.constant;

/**
 * @description: 常用常量类
 * @author: nothing
 * @date: 2017/4/17 10:22
 */
public class BaseConstant {

    /** Redis 锁前缀 */
    public static String REDIS_LOCK_PREFIX = "OFC:MQ:";

    /** 下单TAG */
    public static String MQ_TAG_OrderToOfc = "xeOrderToOfc";

    /** 交货量同步TAG */
    public static String MQ_TAG_GoodsAmountSync = "goodsAmountSync";
}
