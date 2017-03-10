package com.xescm.ofc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lyh on 2016/10/10.
 */
@Data
public class OfcPlanScreenResult {


    private String orderCode;
    private String planCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderTime;
    /*
    订单类型: 0为运输订单 1为仓配订单
     */
    private String orderBatchNumber;
    /*
    业务类型
    00 城配
    01 干线

    10 销售出库
    11 调拨出库
    12 报损出库
    13 其他出库

    14 采购入库
    15 调拨入库
    16 退货入库
    17 加工入库
     */
    private String businessType;
    /*
     订单状态:
     待审核:0
     已审核:1
     执行中:2
     已完成:3
     已取消:4
     */
    private String plannedSingleState;
    private String resourceAllocationStatus;
    private String custName;
    private String custCode;
    private String serviceProviderName;
    private String departureProvince;
    private String departureCity;

    private String departureDistrict;

    private String departureTowns;
    private String destinationProvince;
    private String destinationCity;
    private String destinationDistrict;
    private String destinationTown;
    private String resourceConfirmation;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date resourceConfirmationTime;
    private String serviceProviderContact;
    private String serviceProviderContactPhone;
    private BigDecimal weight;
    private BigDecimal quantity;

}
