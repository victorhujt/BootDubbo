package com.xescm.ofc.model.dto.ofc;/**
 * Created by IntelliJ IDEA.
 * User: Yangdx.
 * Date: 2017/4/13.
 * Time: 15:00.
 * Tags: Code, we are serious.
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 首页展示查询DTO
 *
 * @author  杨东旭
 * @create 2017-04-13 15:00
 **/
@ApiModel("首页展示查询DTO")
@Data
public class HomeDisplaySeleDto {
    @ApiModelProperty("客户编码")
    private String custCode;
}
