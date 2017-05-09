package com.xescm.ofc.constant;

/**
 * Created by ydx on 2016/10/11.
 */
public final class OrderConstConstant {

    public static final String WITH_THE_CITY="600";       //城配
    public static final String WITH_THE_TRUNK="601";      //干线
    public static final String WITH_THE_KABAN="602";      //卡班

    public static final String SALES_OUT_OF_THE_LIBRARY="610";      //销售出库
    public static final String TRANSFER_OUT_OF_THE_LIBRARY="611";        //调拨出库
    public static final String LOSS_OF_REPORTING="612";        //报损出库
    public static final String OTHER_OUT_OF_THE_LIBRARY="613";       //其他出库
    public static final String ALLOT_OUT_OF_THE_LIBRARY="614";       //分拨出库
    public static final String PURCHASING_AND_STORAGE="620";       //采购入库
    public static final String ALLOCATE_STORAGE="621";        //调拨入库
    public static final String RETURN_WAREHOUSING="622";      //退货入库
    public static final String PROCESSING_STORAGE="623";      //加工入库
    public static final String INVENTORY_PROFIT_STORAGE="624";      //盘盈入库
    public static final String CIRCULATE_STORAGE="625";      //流通入库
    public static final String OTHER_STORAGE="626";      //其他入库
    public static final String ALLOT_STORAGE="627";      //分拨入库

    public static final String PENDING_AUDIT="10";       //待审核
    public static final String ALREADY_EXAMINE="20";     //已审核
    public static final String IMPLEMENTATION_IN="30";       //执行中
    public static final String HASBEEN_COMPLETED="40";       //已完成
    public static final String HASBEEN_CANCELED="50";       //已取消

    public static final String ISEXCEPTION="1";       //异常订单标志位

    public static final Integer WEARHOUSE_WITH_TRANS=1;       //仓储订单提供运输
    public static final Integer WAREHOUSE_NO_TRANS=0;       //仓储订单不提供运输

    public static final Integer DISTRIBUTION_ORDER_URGENT=1;       //运输订单加急
    public static final Integer DISTRIBUTION_ORDER_NOT_URGENT=0;       //运输订单不加急

    public static final Integer ORDER_WAS_ABOLISHED=1;       //订单作废
    public static final Integer ORDER_WASNOT_ABOLISHED=0;       //订单未作废

    public static final String CONTACT_PURPOSE_CONSIGNOR = "2"; //联系人用途,发货人
    public static final String CONTACT_PURPOSE_CONSIGNEE = "1"; //联系人用途,收货人

    /**
     * 运输计划单资源分配状态
     */
    public static final String TO_BE_ASSIGNED="10";  //待分配
    public static final String UNALLOCATED="20";  //未分配
    public static final String ALREADY_ASSIGNED="30";   //已分配
    public static final String ALREADY_DETERMINED="40";  //已确定

    /**
     * 计划单状态
     */
    public static final String RESOURCE_ALLOCATION="10";  //资源分配中
    public static final String ALREADY_PUSH="20";  //已推送
    public static final String TASK="30";   //任务中
    public static final String TASK_ACCOMPLISHED="40";  //任务完成
    public static final String ALREADY_CANCELLED="50";  //已作废
    /** 跨域URL */
    public static final String CROSS_DOMAIN_URL = "crossDomainUrl";
    /** OFC 绝对路径 */
    public static final String OFC_URL = "OFC_URL";
    /** 订单中心标记，用于Redis分区 */
    public static final String OFC_ENV = "OFC";
    /**四级地址*/
    public static final String ADDR_URL = "ADDR_URL";
    /**CSC地址*/
    public static final String CSC_URL = "CSC_URL";
    /**
     * CSC
     */
    public static final String CSC_URL_LOCAL = "CSC_URL_LOCAL";


    /**OFC_WEB_URL*/
    public static final String OFC_WEB_URL = "OFC_WEB_URL";
    /** OFC 报表地址 */
    public static final String REPORT = "REPORT";


    /**
     * 运输中心运输计划单最新状态
     */
    public static final String YIDIAODU="10";  //已调度
    public static final String YIFAYUN="20";  //已发运
    public static final String YIDAODA="30";   //已到达
    public static final String YIQIANSHOU="40";  //已签收
    public static final String YIHUIDAN="50";  //已回单



    //通过接口创建的订单
    public static final String CREATE_ORDER_BYAPI = "系统对接";

    //是或者不是
    public static final Integer YES=1;      //是
    public static final Integer NO=0;      //否

    //钉钉 订单来源
    public static  final String DING_DING = "钉钉";

    public static  final String MANUAL = "手动";


    public static final String OFC_WHC_OUT_TYPE = "出库";

    public static final String OFC_WHC_IN_TYPE = "入库";

    public static final String TRACE_STATUS_1 = "00";

    public static final String TRACE_STATUS_2 = "30";

    public static final String TRACE_STATUS_3 = "40";

    public static final String TRACE_STATUS_4 = "60";

    public static final String TRACE_STATUS_5 = "61";

    public static final String TRACE_STATUS_6 = "66";

    public static final String TRACE_STATUS_7 = "80";

    public static final String TRACE_STATUS_8 = "90";

    public static final String TRACE_STATUS_9 = "99";

    public static final String ORDER_TYPE_OUT="CK";

    public static final String ORDER_TYPE_IN="RK";

    public static final String MODEL_TYPE_ACROSS = "MODEL_TYPE_ACROSS";

    public static final String MODEL_TYPE_BORADWISE = "MODEL_TYPE_BORADWISE";

    public static final String UN_TREATED="0";//钉钉录单状态  未处理

    public static final String TREATED="1";//钉钉录单状态  已处理

    public static final String TREATING="2";//钉钉录单状态  处理中

    public static final String STR_YES = "1";

    public static final String STR_NO = "0";

    public static final String MOBILE_PENDING_ORDER_LIST = "DdMobilePendingOrderList";

    public OrderConstConstant() {
    }
}
