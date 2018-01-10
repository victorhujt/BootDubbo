package com.xescm.ofc.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "ofc_cancel_repeat")
public class OfcCancelRepeat {
    /**
     * 订单号
     */
    @Id
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 是否已经发送  1:是 0：否
     */
    @Column(name = "is_send")
    private String isSend;

    /**
     * 订单的信息的json数据
     */
    @Column(name = "order_data")
    private String orderData;

    /**
     * 获取订单号
     *
     * @return order_code - 订单号
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * 设置订单号
     *
     * @param orderCode 订单号
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    /**
     * 获取是否已经发送  1:是 0：否
     *
     * @return is_send - 是否已经发送  1:是 0：否
     */
    public String getIsSend() {
        return isSend;
    }

    /**
     * 设置是否已经发送  1:是 0：否
     *
     * @param isSend 是否已经发送  1:是 0：否
     */
    public void setIsSend(String isSend) {
        this.isSend = isSend;
    }

    /**
     * 获取订单的信息的json数据
     *
     * @return order_data - 订单的信息的json数据
     */
    public String getOrderData() {
        return orderData;
    }

    /**
     * 设置订单的信息的json数据
     *
     * @param orderData 订单的信息的json数据
     */
    public void setOrderData(String orderData) {
        this.orderData = orderData;
    }
}