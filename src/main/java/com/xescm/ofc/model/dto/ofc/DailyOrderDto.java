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
 * 按天统计订单DTO
 *
 * @author  杨东旭
 * @create 2017-04-13 15:00
 **/
@ApiModel("按天统计订单DTO")
@Data
public class DailyOrderDto {
    @ApiModelProperty("日期")
    private String day;
    @ApiModelProperty("订单数")
    private String orderNumber;
}
