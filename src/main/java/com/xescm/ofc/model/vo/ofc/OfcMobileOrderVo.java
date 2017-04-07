package com.xescm.ofc.model.vo.ofc;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by hujintao on 2016/12/18.
 */
public class OfcMobileOrderVo {
    /**
     * 流水号
     */
    private String mobileOrderCode;

    /**
     * 上传日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date uploadDate;

    /**
     * 钉钉账号
     */
    private String dingdingAccountNo;

    /**
     * 开单员
     */
    private String operator;

    /**
     * 订单类型（默认值 60 运输订单）
     */
    private String orderType;

    /**
     * 业务类型 【602】-卡班    ,【601】－干线，【600】－城配
     */
    private String businessType;

    /**
     * 运输单号
     */
    private String tranCode;

    /**
     * 订单状态 0:未受理 1:已受理 默认值未受理
     */
    private String mobileOrderStatus;

    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 订单受理人
     */
    private String accepter;

    /**
     * 受理时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date appcetDate;

    /**
     * 图片路径集合
     */
    private List<String> urls;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    /**
     * 获取流水号
     *
     * @return mobile_order_code - 流水号
     */
    public String getMobileOrderCode() {
        return mobileOrderCode;
    }

    /**
     * 设置流水号
     *
     * @param mobileOrderCode 流水号
     */
    public void setMobileOrderCode(String mobileOrderCode) {
        this.mobileOrderCode = mobileOrderCode;
    }

    /**
     * 获取上传日期
     *
     * @return upload_date - 上传日期
     */
    public Date getUploadDate() {
        return uploadDate;
    }

    /**
     * 设置上传日期
     *
     * @param uploadDate 上传日期
     */
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * 获取钉钉账号
     *
     * @return dingding_account_no - 钉钉账号
     */
    public String getDingdingAccountNo() {
        return dingdingAccountNo;
    }

    /**
     * 设置钉钉账号
     *
     * @param dingdingAccountNo 钉钉账号
     */
    public void setDingdingAccountNo(String dingdingAccountNo) {
        this.dingdingAccountNo = dingdingAccountNo;
    }

    /**
     * 获取开单员
     *
     * @return operator - 开单员
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置开单员
     *
     * @param operator 开单员
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 获取订单类型（默认值 60 运输订单）
     *
     * @return order_type - 订单类型（默认值 60 运输订单）
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * 设置订单类型（默认值 60 运输订单）
     *
     * @param orderType 订单类型（默认值 60 运输订单）
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * 获取业务类型 【602】-卡班    ,【601】－干线，【600】－城配
     *
     * @return buniess_type - 业务类型 【602】-卡班    ,【601】－干线，【600】－城配
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     * 设置业务类型 【602】-卡班    ,【601】－干线，【600】－城配
     *
     * @param businessType 业务类型 【602】-卡班    ,【601】－干线，【600】－城配
     */
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    /**
     * 获取运输单号
     *
     * @return tran_code - 运输单号
     */
    public String getTranCode() {
        return tranCode;
    }

    /**
     * 设置运输单号
     *
     * @param tranCode 运输单号
     */
    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    /**
     * 获取订单状态 0:未受理 1:已受理 默认值未受理
     *
     * @return mobile_order_status - 订单状态 0:未受理 1:已受理 默认值未受理
     */
    public String getMobileOrderStatus() {
        return mobileOrderStatus;
    }

    /**
     * 设置订单状态 0:未受理 1:已受理 默认值未受理
     *
     * @param mobileOrderStatus 订单状态 0:未受理 1:已受理 默认值未受理
     */
    public void setMobileOrderStatus(String mobileOrderStatus) {
        this.mobileOrderStatus = mobileOrderStatus;
    }

    /**
     * 获取订单编号
     *
     * @return order_code - 订单编号
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * 设置订单编号
     *
     * @param orderCode 订单编号
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    /**
     * 获取订单受理人
     *
     * @return accepter - 订单受理人
     */
    public String getAccepter() {
        return accepter;
    }

    /**
     * 设置订单受理人
     *
     * @param accepter 订单受理人
     */
    public void setAccepter(String accepter) {
        this.accepter = accepter;
    }

    /**
     * 获取受理时间
     *
     * @return appcet_date - 受理时间
     */
    public Date getAppcetDate() {
        return appcetDate;
    }

    /**
     * 设置受理时间
     *
     * @param appcetDate 受理时间
     */
    public void setAppcetDate(Date appcetDate) {
        this.appcetDate = appcetDate;
    }


}
