package com.xescm.ofc.mq.constant;

/**
 * Created by MT on 2016/11/10.
 */
public class MqTopicConstants {


    /**
     *订单中心消费topic
     */
    public static final String OFC = "OfcOrderTopic";

    /**
     * 运输中心消费topic
     */
    public static final String TFC = "TfcOrderTopic";

    public static final String WHC = "WhcOrderTopic";
    /**
     * 仓储中心生产topic
     */
    public static final String WHp = "WhpOrderTopic";
    
    /**
     * 仓储中心生产topic下的tag 
     * 采购入库 
     */
    public static final String WHpTag1 = "OFC_WHC_PURCHASE_IN_ORDER_TAG";
    
    /**
     * 仓储中心生产topic下的tag 
     * 调拨入库

     */
    public static final String WHpTag2 = "OFC_WHC_TRANS_IN_ORDER_TAG";
    
    /**
     * 仓储中心生产topic下的tag 
     * 拒收入库
     */
    public static final String WHpTag3 = "OFC_WHC_REFUSED_IN_ORDER_TAG";
    
    /**
     * 仓储中心生产topic下的tag 
     * 退货入库
 
     */
    public static final String WHpTa4 = "OFC_WHC_RETURNS_IN_ORDER_TAG";
    
    /**
     * 采购入库
     */
    public static final String OFC_WHC_PURCHASE_IN_ORDER = "620";
    /**
     * 调拨入库
     */
    public static final String OFC_WHC_TRANS_IN_ORDER = "621";

    /**
     * 退货入库
     */
    public static final String OFC_WHC_RETURNS_IN_ORDER = "622";
    
    
    public static final String OFC_WHC_OUT_TYPE = "出库";
    
    public static final String OFC_WHC_IN_TYPE = "入库";
    
    
}
