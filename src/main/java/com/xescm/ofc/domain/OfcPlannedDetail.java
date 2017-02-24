package com.xescm.ofc.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "ofc_planned_detail")
public class OfcPlannedDetail {
    /**
     * 计划单编号
     */
    @Column(name = "plan_code")
    private String planCode;

    /**
     * 货品代码
     */
    @Column(name = "goods_code")
    private String goodsCode;

    /**
     * 货品名称
     */
    @Column(name = "goods_name")
    private String goodsName;

    /**
     * 货品规格
     */
    @Column(name = "goods_spec")
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
     * 实发数量
     */
    @Column(name = "real_quantity")
    private BigDecimal realQuantity;

    /**
     * 单价
     */
    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    /**
     * 生产批次
     */
    @Column(name = "production_batch")
    private String productionBatch;

    /**
     * 生产日期
     */
    @Column(name = "production_time")
    private Date productionTime;

    /**
     * 失效日期
     */
    @Column(name = "invalid_time")
    private Date invalidTime;

    private BigDecimal weight;

    private BigDecimal cubage;

    private Integer totalBox;

    /**
     * 记账重量
     */
    @Column(name = "billing_weight")
    private BigDecimal billingWeight;


}