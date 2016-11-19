package com.xescm.ofc.domain.dto.coo;

import com.alibaba.fastjson.JSONObject;
import com.xescm.ofc.utils.JSONUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单中心创建订单返回
 * Created by hiyond on 2016/11/15.
 */
public class CreateOrderResult {

    /**
     * 返回结果 true/false
     */
    private boolean result;

    /**
     * 原因
     */
    private String reason;

    /**
     * 客户订单编号
     */
    private String typeId;

    /**
     * 订单编号
     * 创建成功后返回的订单编号
     */
    private String orderCode;

    public CreateOrderResult() {
    }

    public CreateOrderResult(boolean result, String reason, String typeId, String orderCode) {
        this.result = result;
        this.reason = reason;
        this.typeId = typeId;
        this.orderCode = orderCode;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean getResult() {
        return result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Override
    public String toString() {
        return "CreateOrderResult{" +
                "result=" + result +
                ", reason='" + reason + '\'' +
                ", typeId='" + typeId + '\'' +
                ", orderCode='" + orderCode + '\'' +
                '}';
    }

}
