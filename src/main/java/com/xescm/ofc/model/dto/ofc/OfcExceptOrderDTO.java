package com.xescm.ofc.model.dto.ofc;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class OfcExceptOrderDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endDate;
    private String areaName;
    private String areaSerialNo;
    private String baseName;
    private String baseSerialNo;
    private String custCode;
    private String custName;
    private String orderType;
    private String businessType;
    private String orderSource;
    private String orderCode;
    private String custOrderCode;
    private String transCode;
    private String exceptReason;
    private String exceptPot;
    private String dealStatus;
    private Integer pageSize;
    private Integer pageNum;
}
