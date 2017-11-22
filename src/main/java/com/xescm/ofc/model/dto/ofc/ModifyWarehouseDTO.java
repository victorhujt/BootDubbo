package com.xescm.ofc.model.dto.ofc;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Created by hujintao on 2017/11/8.
 */
@ApiModel("修改仓库的DTO")
@Data
public class ModifyWarehouseDTO {
    /**
     * 客户编码
     */
    private String custCode;
    /**
     * 基地编码
     */
    private String serialNo;
}
