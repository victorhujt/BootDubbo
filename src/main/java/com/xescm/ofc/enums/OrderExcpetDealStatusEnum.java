package com.xescm.ofc.enums;

public enum OrderExcpetDealStatusEnum {

    UN_DEAL("未处理", "00"),
    DEALING("处理中", "01"),
    IS_EXCEPTION("已标记异常", "02");

    private String desc;
    private String code;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    OrderExcpetDealStatusEnum(String desc, String code) {
        this.desc = desc;
        this.code = code;
    }
}
