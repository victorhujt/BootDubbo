package com.xescm.ofc.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tmp_zp_miss_order")
public class TmpZpMissOrder {
    /**
     * 客户订单号
     */
    @Column(name = "cust_order_code")
    private String custOrderCode;

    /**
     * 错误日期

     */
    @Column(name = "error_date")
    private Date errorDate;

    /**
     * 获取客户订单号
     *
     * @return cust_order_code - 客户订单号
     */
    public String getCustOrderCode() {
        return custOrderCode;
    }

    /**
     * 设置客户订单号
     *
     * @param custOrderCode 客户订单号
     */
    public void setCustOrderCode(String custOrderCode) {
        this.custOrderCode = custOrderCode;
    }

    /**
     * 获取错误日期

     *
     * @return error_date - 错误日期

     */
    public Date getErrorDate() {
        return errorDate;
    }

    /**
     * 设置错误日期

     *
     * @param errorDate 错误日期

     */
    public void setErrorDate(Date errorDate) {
        this.errorDate = errorDate;
    }
}