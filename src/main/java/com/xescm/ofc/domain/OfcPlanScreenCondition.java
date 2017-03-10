package com.xescm.ofc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
@Data
public class OfcPlanScreenCondition {


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderTimePre;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderTimeSuf;
    private String custName;
    private String custCode;
    private String orderBatchNumber;
    private String orderCode;
    private String planCode;
    private String resourceAllocationStatus;
    private List<String> resourceAllocationStatues;

}
