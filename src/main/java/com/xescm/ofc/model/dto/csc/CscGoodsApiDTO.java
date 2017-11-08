package com.xescm.ofc.model.dto.csc;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lyh on 2017/7/11.
 */
@Data
public class CscGoodsApiDTO implements Serializable{

    private String customerCode;
    private String goodsCode;
    private String goodsName;
    private String goodsTypeId;
    private String goodsTypeSonId;
    private String barCode;
    private String fromSys;
    private String warehouseCode;
    private int pageNum;
    private int pageSize;
}
