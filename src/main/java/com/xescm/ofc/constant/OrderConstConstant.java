package com.xescm.ofc.constant;

/**
 * Created by ydx on 2016/10/11.
 */
public final class OrderConstConstant {
    public static final String TRANSPORTORDER="60";     //运输订单
    public static final String WAREHOUSEDISTRIBUTIONORDER="61";     //仓配订单

    public static final String WITHTHECITY="600";       //城配
    public static final String WITHTHETRUNK="601";      //干线
    public static final String WITHTHEKABAN="602";      //卡班

    public static final String SALESOUTOFTHELIBRARY="610";      //销售出库
    public static final String TRANSFEROUTOFTHELIBRARY="611";        //调拨出库
    public static final String LOSSOFREPORTING="612";        //报损出库
    public static final String OTHEROUTOFTHELIBRARY="613";       //其他出库
    public static final String ALLOTOUTOFTHELIBRARY="614";       //分拨出库
    public static final String PURCHASINGANDSTORAGE="620";       //采购入库
    public static final String ALLOCATESTORAGE="621";        //调拨入库
    public static final String RETURNWAREHOUSING="622";      //退货入库
    public static final String PROCESSINGSTORAGE="623";      //加工入库
    public static final String INVENTORYPROFITSTORAGE="624";      //盘盈入库
    public static final String CIRCULATESTORAGE="625";      //流通入库
    public static final String OTHERSTORAGE="626";      //其他入库
    public static final String ALLOTSTORAGE="627";      //分拨入库

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

    /**
     * 运输计划单资源分配状态
     */
    public static final String DAIFENPEI="10";  //待分配
    public static final String WEIFENPEI="20";  //未分配
    public static final String YIFENPEI="30";   //已分配
    public static final String YIQUEDING="40";  //已确定

    /**
     * 计划单状态
     */
    public static final String ZIYUANFENPEIZ="10";  //资源分配中
    public static final String YITUISONG="20";  //已推送
    public static final String RENWUZHONG="30";   //任务中
    public static final String RENWUWANCH="40";  //任务完成
    public static final String YIZUOFEI="50";  //已作废
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


    //支付方式
    public static final String FINNANCEPAYWAYCASH="6810";//现金支付
    public static final String FINNANCEPAYWAYPOS="6820";//POS刷卡
    public static final String FINNANCEPAYWAYWECHAT="6830";//微信
    public static final String FINNANCEPAYWAYALIPAY="6840";//支付宝
    public static final String FINNANCEPAYWAYBANK="6850";//银行支付
    public static final String FINNANCEPAYWAYACCOUNT="6860";//账户结算
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
    public static final Integer SHI=1;      //是
    public static final Integer FOU=0;      //否

    //运输类型
    public static final Integer LINGDAN=10;       //零担
    public static final Integer ZHENGCHE=20;        //整车

    //费用支付方
    public static final Integer FAHUOFANG=10;       //发货方
    public static final Integer SHOUHUOFANG=20;        //收货方

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

    public OrderConstConstant() {
    }
}
