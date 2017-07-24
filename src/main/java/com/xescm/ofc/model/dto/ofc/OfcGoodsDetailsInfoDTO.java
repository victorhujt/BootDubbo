package com.xescm.ofc.model.dto.ofc;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by victor on 2017/7/11.
 */
@Data
public class OfcGoodsDetailsInfoDTO {
    private String id;

    /**
     * 货品代码
     */
    private String goodsCode;

    /**
     * 货品名称
     */
    private String goodsName;

    /**
     * 货品规格
     */
    private String goodsSpec;

    /**
     * 单位
     */
    private String unit;

    /**
     * 数量
     */
    private BigDecimal quantity;
    /**
     * 实收数量
     */
    private BigDecimal realQuantity;


    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 生产批次
     */
    private String productionBatch;

    /**
     * 生产日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date productionTime;

    /**
     * 失效日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date invalidTime;

    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 备注
     */
    private String notes;

    /**
     * 创建日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date creationTime;

    /**
     * 创建人员
     */
    private String creator;

    /**
     * 操作人员
     */
    private String operator;

    /**
     * 操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date operTime;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 体积
     */
    private BigDecimal cubage;

    /**
     * 合计标准箱
     */
    private Integer totalBox;

    /**
     * 货品种类
     */
    private String goodsType;

    /**
     * 货品类别
     */
    private String goodsCategory;

    /**
     * 包装
     */
    private String pack;

    /**
     * 数量单价
     */
    private BigDecimal quantityUnitPrice;

    /**
     * 重量单价
     */
    private BigDecimal weightUnitPrice;

    /**
     * 体积单价
     */
    private BigDecimal volumeUnitPrice;

    /**
     * 记账重量
     */
    private BigDecimal billingWeight;

    /**
     * 计费方式
     */
    private String chargingWays;

    /**
     * 计费数量
     */
    private BigDecimal chargingQuantity;

    /**
     * 计费单价
     */
    private BigDecimal chargingUnitPrice;
    /**
     * 货品供应商批次
     */
    private String supportBatch;

    /**
     * 货品供应商批次名称
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

    private BigDecimal primaryQuantity;
}
