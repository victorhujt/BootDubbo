package com.xescm.ofc.model.dto.ofc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by hujintao on 2017/7/6.
 */
@ApiModel("实收详情DTO")
@Data
public class RealGoodsDTO {
    @ApiModelProperty("订单号")
    private String orderCode;
    @ApiModelProperty("入库 RK 出库 CK")
    private String businessType;
}
