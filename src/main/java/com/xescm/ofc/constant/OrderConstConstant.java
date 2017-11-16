package com.xescm.ofc.constant;

/**@author: hujintao
 * @date:2016/10/11.
 */
public final class OrderConstConstant {
    /**城配**/
    public static final String WITH_THE_CITY = "600";
    /**干线**/
    public static final String WITH_THE_TRUNK = "601";
    /**卡班**/
    public static final String WITH_THE_KABAN = "602";
    /**落地配**/
    public static final String WITH_THE_GROUND_DISTRIBUTION = "606";
    /**是否二次配送**/
    public static final String TWO_DISTRIBUTION = "1";
    /**待审核**/
    public static final String PENDING_AUDIT = "10";
    /**已审核**/
    public static final String ALREADY_EXAMINE = "20";
    /**执行中**/
    public static final String IMPLEMENTATION_IN = "30";
    /**已完成**/
    public static final String HASBEEN_COMPLETED = "40";
    /**已取消**/
    public static final String HASBEEN_CANCELED = "50";
    /**异常订单标志位**/
    public static final String ISEXCEPTION = "1";
    /**仓储订单提供运输**/
    public static final Integer WEARHOUSE_WITH_TRANS = 1;
    /**仓储订单不提供运输**/
    public static final Integer WAREHOUSE_NO_TRANS = 0;
    /**运输订单不加急**/
    public static final Integer DISTRIBUTION_ORDER_NOT_URGENT = 0;
    /**订单未作废**/
    public static final Integer ORDER_WASNOT_ABOLISHED = 0;
    /**联系人用途,发货人**/
    public static final String CONTACT_PURPOSE_CONSIGNOR = "2";
    /**联系人用途,收货人**/
    public static final String CONTACT_PURPOSE_CONSIGNEE = "1";
    /** 跨域URL */
    public static final String CROSS_DOMAIN_URL = "crossDomainUrl";
    /** OFC 绝对路径 */
    public static final String OFC_URL = "OFC_URL";
    /** 订单中心标记，用于Redis分区 */
    public static final String OFC_ENV = "OFC";
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



    /**通过接口创建的订单**/
    public static final String CREATE_ORDER_BYAPI = "系统对接";

    /**是或者不是**/
    public static final Integer YES = 1;
    /**零担**/
    public static final String BREAKCHEAP = "10";
    /**返回签单**/
    public static final String IS_RETURNLIST = "1";
    /**钉钉 订单来源**/
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

    public static final String MODEL_TYPE_ACROSS = "MODEL_TYPE_ACROSS";

    public static final String MODEL_TYPE_BORADWISE = "MODEL_TYPE_BORADWISE";
    /**钉钉录单状态 未处理*/
    public static final String UN_TREATED="0";
    /**钉钉录单状态  已处理*/
    public static final String TREATED="1";
    /**钉钉录单状态  处理中*/
    public static final String TREATING="2";

    public static final String STR_YES = "1";

    public static final String MOBILE_PENDING_ORDER_LIST = "DdMobilePendingOrderList";

    public static final String QUERY_REQUEST_COUNT = "requestCount";

    public static final String FIRST_REQUEST_TIME = "firstRequestTime";
    /**黑名单*/
    public static final String IS_BLACK = "1";
    public static final String SENDSMS_REQUEST_COUNT="sendSmsRequestCount";
    /**查单接口*/
    public static final String INTERFACE_STATUS = "quey_order_switch";
    /**落地配*/
    public static final String IS_NEED_GROUND_DISTRIBUTION = "1";
    /**入库类型**/
    public static final String[] INBUSINESSTYPES = {"620","621","622","623","624","625","626"};
    /**入库类型**/
    public static final String[] OUTBUSINESSTYPES = {"610","611","612","613","614","617","618"};
    /**入库**/
    public static final String IN = "in";
    /**出库**/
    public static final String OUT = "out";



    public OrderConstConstant() {
    }
}
