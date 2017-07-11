package com.xescm.ofc.model.dto.ofc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by hujintao on 2017/7/5.
 */
@ApiModel("筛选产品信息Dto")
@Data
public class OfcStorageDTO {
    @ApiModelProperty("客户名称")
    private String custName;
    @ApiModelProperty("客户编码")
    private String custCode;
    @ApiModelProperty("客户订单号")
    private String custOrderCode;
    @ApiModelProperty("订单号")
    private String orderCode;
    @ApiModelProperty("订单日期")
    private String orderState;
    @ApiModelProperty("订单类型")
    private String orderType;
    @ApiModelProperty("业务类型")
    private String businessType;
    @ApiModelProperty("订单批次号")
    private String orderBatchNumber;
    @ApiModelProperty("大区编码")
    private String areaSerialNo;
    @ApiModelProperty("基地编码")
    private String baseSerialNo;
    @ApiModelProperty("仓库编码")
    private  String warehouseCode;
    @ApiModelProperty("是否提供运输(0 = 否 1 = 是)")
    private Integer provideTransport;
    @ApiModelProperty("出入库标志(in = 入库,out = 出库)")
    private String tag;
    @ApiModelProperty("业务类型集合")
    private List<String> businessTypes;
}
