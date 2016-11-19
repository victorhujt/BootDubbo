package com.xescm.ofc.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 *
 * Created by hiyond on 2016/11/18.
 */
@Table(name="ofc_create_order_error_log")
public class OfcCreateOrderErrorLog {

    private Integer id;

    @Column(name="cust_order_code")
    private String custOrderCode;

    @Column(name="cust_code")
    private String custCode;

    @Column(name="order_time")
    private Date orderTime;

    private String errorLog;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustOrderCode() {
        return custOrderCode;
    }

    public void setCustOrderCode(String custOrderCode) {
        this.custOrderCode = custOrderCode;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getErrorLog() {
        return errorLog;
    }

    public void setErrorLog(String errorLog) {
        this.errorLog = errorLog;
    }
}
