package com.xescm.ofc.model.dto.ac;

import java.io.Serializable;

/**
 * 订单中心取消订单--推送到结算中心
 * Created by hiyond on 2016/12/23.
 */
public class CancelOfcOrderDto implements Serializable {
    private static final long serialVersionUID = 6750156855958204415L;

    //订单编号
    private String orderCode;

    //运输单号
    private String transCode;

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
}
