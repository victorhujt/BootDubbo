package com.xescm.ofc.model.dto.addr;

/**
 * @author Chen zhen gang
 * @date 2016/11/10
 * @time 10:22
 */

public class QueryAddress {
    private String code;

    private String type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "QueryAddress{" +
                "code='" + code + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
