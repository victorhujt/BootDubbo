package com.xescm.ofc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "ofc_mobile_order")
public class OfcMobileOrder {
    /**
     * 流水号
     */
    @Id
    @Column(name = "mobile_order_code")
    private String mobileOrderCode;

    /**
     * 上传日期
     */
    @Column(name = "upload_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date uploadDate;

    /**
     * 钉钉账号
     */
    @Column(name = "dingding_account_no")
    private String dingdingAccountNo;

    /**
     * 开单员
     */
    private String operator;

    /**
     * 订单类型（默认值 60 运输订单）
     */
    @Column(name = "order_type")
    private String orderType;

    /**
     * 业务类型 【602】-卡班    ,【601】－干线，【600】－城配
     */
    @Column(name = "business_type")
    private String businessType;

    /**
     * 运输单号
     */
    @Column(name = "tran_code")
    private String tranCode;

    /**
     * 订单状态 0:未受理 1:已受理 默认值未受理
     */
    @Column(name = "mobile_order_status")
    private String mobileOrderStatus;

    /**
     * 订单编号
     */
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 订单受理人
     */
    private String accepter;

    /**
     * 受理时间
     */
    @Column(name = "appcet_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date appcetDate;

    /**
     * 附件流水号
     */
    @Column(name = "serial_no")
    private String serialNo;


}

