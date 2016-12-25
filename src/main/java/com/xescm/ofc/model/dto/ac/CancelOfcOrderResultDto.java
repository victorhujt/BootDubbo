package com.xescm.ofc.model.dto.ac;

import java.io.Serializable;

/**
 * 订单中心取消订单返回dto
 * Created by hiyond on 2016/12/23.
 */
public class CancelOfcOrderResultDto implements Serializable {

    private static final long serialVersionUID = -2774149715976196002L;
    //订单编号
    private String orderCode;

    //运输单编号
    private String transCode;

    //返回结果[成功：true,失败：false]
    private boolean result;

    //说明
    private String notes;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
