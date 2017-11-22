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
 * 首页展示DTO
 *
 * @author  杨东旭
 * @create 2017-04-13 15:00
 **/
@ApiModel("首页展示DTO")
@Data
public class HomeDisplayDto {
    @ApiModelProperty("运输类订单未完成数")
    private String transportOrderIncompleteNumber;
    @ApiModelProperty("仓储类订单未完成数")
    private String storageOrderIncompleteNumber;
    @ApiModelProperty("月创建订单数")
    private String monthCreateOrderNumber;
    @ApiModelProperty("月完成订单数")
    private String monthCompletedOrderNumber;
}
