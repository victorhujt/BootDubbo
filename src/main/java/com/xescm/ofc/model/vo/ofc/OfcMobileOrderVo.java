package com.xescm.ofc.model.vo.ofc;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by hujintao on 2016/12/18.
 */
@Data
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
     * 订单状态 0:未受理 1:已受理  2:受理中 默认值未受理
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
    /**
     * 附件流水号 多个附件以逗号隔开
     */
    private String serialNo;

}
