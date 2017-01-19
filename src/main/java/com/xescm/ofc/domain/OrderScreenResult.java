package com.xescm.ofc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by lyh on 2016/10/10.
 */
@Data
public class OrderScreenResult {
    private String orderCode;
    private String custOrderCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderTime;

    private String orderType;

    private String businessType;

    private String orderStatus;
    private String consigneeName;
    private String consigneeType;
    private String consigneeContactName;
    private String warehouseName;
    private String notes;
    private String storeName;//店铺

    private String custName;//客户名称


}
