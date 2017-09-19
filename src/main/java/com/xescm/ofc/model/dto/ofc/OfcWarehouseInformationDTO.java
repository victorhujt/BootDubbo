package com.xescm.ofc.model.dto.ofc;

import com.xescm.ofc.domain.OfcWarehouseInformation;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by hujintao on 2017/7/11.
 */
@ApiModel("仓储信息dto")
@Data
public class OfcWarehouseInformationDTO extends OfcWarehouseInformation implements Serializable{

    private static final long serialVersionUID = 1945534650067959456L;
}
