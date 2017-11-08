package com.xescm.ofc.model.vo.ofc;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *
 * Created by hiyond on 2016/11/25.
 */
@Data
public class OfcBatchOrderVo {

    private String orderCode;

    private String batchOrderNumber;

    private String custName;

    private String merchandiser;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderTime;

    private String warehouseName;

    private String notes;

    private String consignorName;

    private String consignorContactName;

    private String consignorContactPhone;

    private String departure;

    private String orderStatus;

    private String consigneeName;

    private String consigneeContactName;

    private String consigneeContactPhone;

}
