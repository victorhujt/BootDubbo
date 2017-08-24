package com.xescm.ofc.model.dto.ofc;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class OfcOrderPotDTO implements Serializable{

    /**
     * 订单号
     */
    private String orderCode;
    /**
     * 状态结点描述
     */
    private String potDesc;
    /**
     * 状态结点编码
     */
    private String potCode;
    /**
     * 状态结点时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date potTime;

    /**
     * 是否二次配送(卡班订单, 1是 0 否)
     */
    private String twoDistribution;
    /**
     * 操作人ID
     */
    private String operatorId;
    /**
     * 操作人名称
     */
    private String operatorName;
    /**
     * 数据来源
     */
    private String fromSys;
}
