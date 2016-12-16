package com.xescm.ofc.model.dto.csc;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description   货品导入实体
 *
 * @Author        zhangft
 * @CreateDate    2016/12/16 12:52
 */
@Data
public class CscGoodsImportDto implements Serializable {

    private static final long serialVersionUID = 626828340488869922L;

    /**
     * 主键
     */
    private String id;

    /**
     * 货品编码
     */
    private String goodsCode;

    /**
     * 货品名称
     */
    private String goodsName;

    /**
     * 货品类别id
     */
    private String goodsType;

    /**
     * 小类别id
     */
    private String smallGoodsType;

    /**
     * 温度带
     */
    private String keeptemperate;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 规格
     */
    private String specification;

    /**
     * 单位
     */
    private String unit;

    /**
     * 客户code
     */
    private String customerCode;

}
