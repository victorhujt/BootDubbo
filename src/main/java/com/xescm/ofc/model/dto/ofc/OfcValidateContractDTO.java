package com.xescm.ofc.model.dto.ofc;

import lombok.Data;

import java.io.Serializable;

/**@author hujintao
 * Created by  on 2017/12/25.
 */
@Data
public class OfcValidateContractDTO implements Serializable {
    /**
     * 客户名称
     */
    private String customerCode;
    /**
     * 产品分类
     */
    private String productCatlogCode;
}
