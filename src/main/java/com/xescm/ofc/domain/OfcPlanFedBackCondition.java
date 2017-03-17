package com.xescm.ofc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by lyh on 2016/10/10.
 */
@Data
public class OfcPlanFedBackCondition {

    private String transportNo;//运输单号
    private String orderCode;//订单号
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date traceTime;//跟踪时间
    private String status;//跟踪状态
    private String notes;//备注
    private String description;//状态描述

}
