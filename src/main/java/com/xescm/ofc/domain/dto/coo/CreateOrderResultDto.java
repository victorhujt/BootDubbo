package com.xescm.ofc.domain.dto.coo;

import java.util.List;

/**
 * 创单返回的dto
 * Created by hiyond on 2016/11/17.
 */
public class CreateOrderResultDto {

    private String code;

    private String reason;

    private List<String> message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }
}
