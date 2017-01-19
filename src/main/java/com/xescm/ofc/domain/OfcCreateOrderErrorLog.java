package com.xescm.ofc.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 *
 * Created by hiyond on 2016/11/18.
 */
@Data
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


}
