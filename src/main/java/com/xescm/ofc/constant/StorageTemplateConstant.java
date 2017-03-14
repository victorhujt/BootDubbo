package com.xescm.ofc.constant;

/**
 * Created by lyh on 2017/3/2.
 */
public class StorageTemplateConstant {

    //请选择客户
    public static final Integer ERROR_CUST = 501;

    //请选择模板
    public static final Integer ERROR_TEMPLATE = 502;

    //验证token失败
    public static final Integer ERROR_AUTH = 503;

    //入库
    public static final String STORAGE_IN = "storageIn";

    //出库
    public static final String STORAGE_OUT = "storageOut";

    //标准模板
    public static final String STANDARD = "standard";

    //yyyy-MM-dd 正则
    public static final String REGEX_YYYYMMDD = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";

    //数字正则
    public static final String SIX_POT_THREE = "\\d{1,6}\\.\\d{1,3}";

    public static final String INTEGER_SIX = "\\d{1,6}";


}
