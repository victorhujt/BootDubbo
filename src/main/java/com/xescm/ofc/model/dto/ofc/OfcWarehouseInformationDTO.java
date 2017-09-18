package com.xescm.ofc.model.dto.ofc;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by hujintao on 2017/7/11.
 */
@ApiModel("仓储信息dto")
@Data
public class OfcWarehouseInformationDTO {
      /*仓配信息*/
    /**
     * 供应商名称
     */
    private String supportName;
    /**
     * 供应商编码
     */
    private String supportCode;
    /**
     * 是否需要提供运输
     */
    private Integer provideTransport;
    /**
     *出库发货时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date shipmentTime;

    /**
     * 承诺到达时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date expectedArrivedTime;

    /**
     * 入库预计到达时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date arriveTime;
    /**
     *仓库名称
     */
    private String warehouseName;
    /**
     * 仓库编码
     */
    private String warehouseCode;
    /**
     *车牌号
     */
    private String plateNumber;
    /**
     *司机姓名
     */
    private String driverName;
    /**
     * 联系电话
     */
    private String contactNumber;
    /**
     * 供应商联系人编码
     */
    private String supportContactCode;
    /**
     * 供应商联系人
     */
    private String supportContactName;

}
