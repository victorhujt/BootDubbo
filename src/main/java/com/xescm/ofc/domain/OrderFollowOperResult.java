package com.xescm.ofc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by hiyond on 2016/12/6.
 */
@Data
public class OrderFollowOperResult {
    private String orderCode;
    private String custOrderCode;
    private String orderBatchNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderTime;
    private String custName;
    private String orderStatus;
    private String orderType;
    private String businessType;
    private String transportType;
    private String departurePlace;
    private String destination;
    private String transCode;
    private String plateNumber;
    private String driverName;
    private String contactNumber;
    private String destinationProvince;
    private String destinationCity;
    private String destinationDistrict;
    private String destinationTowns;
    private String departureProvince;
    private String departureCity;
    private String departureDistrict;
    private String departureTowns;

}
