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
public class OfcSchedulingSingleFeedbackCondition {

    private List<String> transportNo;//运输计划单号
    private String deliveryNo;//调度单号
    private String transportType;//运输类型
    private String vehical;//车牌号
    private String driver;//司机姓名
    private String tel;//联系电话
    private String vehicleType;//车型
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;//调度单时间
    private String lineName;


}
