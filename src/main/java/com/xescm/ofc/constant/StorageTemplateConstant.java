package com.xescm.ofc.constant;

/**
 * Created by lyh on 2017/3/2.
 */
public class StorageTemplateConstant {

    //未知错误
    public static final Integer ERROR = 499;

    //未选客户错误
    public static final Integer ERROR_CUST = 501;

    //未选模板错误
    public static final Integer ERROR_TEMPLATE = 502;

    //校验当前库存失败
    public static final Integer ERROR_STOCK = 503;

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

    //包装代码编码
    public static final String PACKAGE_ID_CODE = "packageId";
    //包装代码名称
    public static final String PACKAGE_ID_NAME = "包装代码";
    //主单位数量编码
    public static final String MAIN_UNIT_NUM_CODE = "mainUnitNum";
    //主单位数量名称
    public static final String MAIN_UNIT_NUM_NAME = "主单位数量";
}
