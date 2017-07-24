package com.xescm.ofc.model.dto.ofc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by hujintao on 2017/7/21.
 */
@ApiModel("校验运输单号的dto")
@Data
public class CheckTransCodeDTO {
    @ApiModelProperty("运输单号")
    private String transCode;
    @ApiModelProperty("运输单号")
    private String selfTransCode;

}


