package com.xescm.ofc.model.dto.ofc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by hujintao on 2017/7/12.
 */
@Data
@ApiModel("货品种类DTO")
public class GoodsCategoryDTO {
    @ApiModelProperty("货品种类")
    private String cscGoodsType;
}
