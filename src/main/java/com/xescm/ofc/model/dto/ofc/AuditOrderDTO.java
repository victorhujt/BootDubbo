package com.xescm.ofc.model.dto.ofc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by hujintao on 2017/7/6.
 */
@ApiModel("审核订单DTO")
@Data
public class AuditOrderDTO {
    @ApiModelProperty("订单号")
    private String orderCode;
    @ApiModelProperty("审核反审核标志")
    private String reviewTag;
}
