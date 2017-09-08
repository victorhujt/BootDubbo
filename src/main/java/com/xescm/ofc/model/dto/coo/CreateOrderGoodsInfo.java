package com.xescm.ofc.model.dto.coo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.xescm.csc.model.dto.packing.GoodsPackingDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单中心创建订单（鲜易网）
 * 订单货品明细信息
 * Created by hiyond on 2016/11/14.
 */
@XStreamAlias("orderDetail")
@Data
public class CreateOrderGoodsInfo implements Serializable {

    private static final long serialVersionUID = 7909253575176134994L;
    /*客户名称*/
    private String custName;

    /**客户编码*/

    private String custCode;

    /**
     * 货品代码 (必填)
     */
    private String goodsCode;

    /**
     * 货品名称 (必填)
     */
    private String goodsName;

    /**
     * 货品规格(必填)
     */
    private String goodsSpec;

    /**
     * 货品大类
     */
    private String goodsType;

    /**
     * 货品大类编码

     */
    private String goodsTypeCode;

    /**
     * 货品小类
     */
    private String goodsCategory;
    /**
     * 货品小类编码
     */
    private String goodsCategoryCode;

    /**
     * 单位(必填)
     */
    private String unit;

    /**
     * 数量
     */
    private String quantity;
    /**
     * 重量
     */
    private String weight;
    /**
     * 体积
     */
    private String cubage;

    /**
     * 销售单价 (必填)
     */
    private String unitPrice;

    /**
     * 生产批次(必填)
     */
    private String productionBatch;

    /**
     * 生产日期(必填)
     */
    private String productionTime;

    /**
     * 失效日期
     */
    private String invalidTime;


    /**
     * 货品供应商批次
     */
    private String supportBatch;

    /**
     * 货品供应商名称
     */
    private String supportName;
    /**
     * 包装名称
     */
    private String packageName;

    /**
     *包装类型
     * @return
     */
    private String packageType;

    /**
     * 与主单位的换算规格
     * @return
     */
    private BigDecimal conversionRate;

    /** 主单位数量 */
    private BigDecimal primaryQuantity;
    /**
     * 平台行号
     */
    private Long passLineNo;

    /**
     * 外部平台号

     */
    private String externalPaasLineNo;
    /**
     * 厂商批次
     */
    private String manufactureBatchLotatt;

    private List<GoodsPackingDto> skuPackageList;
}
