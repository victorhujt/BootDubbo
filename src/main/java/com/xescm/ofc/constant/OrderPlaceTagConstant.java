package com.xescm.ofc.constant;

/**
 *
 * Created by lyh on 2017/3/14.
 */
public class OrderPlaceTagConstant {

    //仓储导单创建订单标志位
    public static final String ORDER_TAG_STOCK_IMPORT = "batchSave";

    //城配开单创建订单标志位
    public static final String ORDER_TAG_OPER_DISTRI = "distributionPlace";

    //运输开单创建订单标志位
    public static final String ORDER_TAG_OPER_TRANS = "tranplace";

    //运输开单编辑订单标志位
    public static final String ORDER_TAG_OPER_TRANEDIT = "tranpEdit";

    //仓储开单下单标志位
    public static final String ORDER_TAG_STOCK_SAVE = "save";

    //仓储开单编辑标志位
    public static final String ORDER_TAG_STOCK_EDIT = "edit";

    //客户工作台普通下单
    public static final String ORDER_TAG_NORMAL_PLACE = "place";

    //客户工作台普通编辑
    public static final String ORDER_TAG_NORMAL_EDIT = "manage";

    //审核标志位
    public static final String REVIEW = "review";

    //反审核标志位
    public static final String RE_REVIEW = "rereview";



}
