package com.xescm.ofc.enums;

public enum  SmsTemplatesEnum {

    SMS_QUERY_ORDER_CODE("SMS_70510558", "查单验证码"),
    SMS_ORDER_SIGEND_MSG("SMS_71256076", "运输(卡班)签收");

    SmsTemplatesEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;

    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
