package com.xescm.ofc.model.dto.ofc;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 仓储出入库批量导入实体
 * @author: nothing
 * @date: 02/11/2017 16:46
 */
@Data
public class OfcStorageImportDTO implements Serializable {

    /**
     * 订单集合
     */
    private List<OfcStorageTemplateDto> orderList;

    /**
     * 服务产品编码
     */
    private String serviceProductCode;

    /**
     * 服务产品名称
     */
    private String serviceProductName;
}
