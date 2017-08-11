package com.xescm.ofc.model.dto.ofc;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by hujintao on 2017/7/11.
 */
@ApiModel("订单基本信息dto")
@Data
public class OfcFundamentalInformationDTO {

    /*基本信息表*/
    private String orderCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderTime;
    /**
     * 货主编码
     */
    private String custCode;
    /**
     * 货主名称
     */
    private String custName;
    /**
     * 二级客户编码
     */
    private String secCustCode;
    /**
     * 二级客户名称
     */
    private String secCustName;
    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 客户订单编号
     */
    private String custOrderCode;
    /**
     * 备注
     */
    private String notes;
    /**
     * 店铺编码
     */
    private String storeCode;
    /**
     * 店铺名称
     */
    private String storeName;
    /**
     * 平台类型
     */
    private String platformType;
    /**
     * 订单来源
     */
    private String orderSource;
    /**
     * 服务产品类型
     */
    private String productType;
    /**
     * 服务产品名称
     */
    private String productName;
    /**
     * 完成日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date finishedTime;
    /**
     * 作废标记
     */
    private Integer abolishMark;
    /**
     * 作废时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date abolishTime;
    /**
     * 作废人
     */
    private String abolisher;
    /**
     * 创建人
     */
    private String creator;
    /**
     *创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date creationTime;
    /**
     * 操作人
     */
    private String operator;
    /**
     * 操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date operTime;
    /**
     * 作废人名称
     */
    private String abolisherName;
    /**
     * 创建人名称
     */
    private String creatorName;
    /**
     * 操作人名称
     */
    private String operatorName;
    /**
     * 开单员
     */
    private String merchandiser;

    /**
     * 是否发送签收短信
     */
    private String signedSms;

    /**
     * 运输类型
     */
    private String transportType;

    private String batchNumber;
    /**
     * 订单批次号
     */
    private String orderBatchNumber;
    private String orderStatus;
    private String statusDesc;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastedOperTime;
    /**
     * 是否落地配  1 是  0 否
     */
    private String groundDistribution;
}
