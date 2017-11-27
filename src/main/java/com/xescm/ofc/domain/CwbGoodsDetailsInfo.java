package com.xescm.ofc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "cwb_goods_details_info")
public class CwbGoodsDetailsInfo {

    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

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
    @Column(name = "unit")
    private String unit;

    /**
     * 数量
     */
    @Column(name = "quantity")
    private BigDecimal quantity;
    /**
     * 实收数量
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "production_time")
    private Date productionTime;

    /**
     * 失效日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "invalid_time")
    private Date invalidTime;

    /**
     * 订单编号
     */
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 备注
     */
    private String notes;

    /**
     * 创建日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "creation_time")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "oper_time")
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
    @Column(name = "total_box")
    private Integer totalBox;

    /**
     * 货品种类
     */
    @Column(name = "goods_type")
    private String goodsType;

    /**
     * 货品类别
     */
    @Column(name = "goods_category")
    private String goodsCategory;

    /**
     * 包装
     */
    private String pack;

    /**
     * 数量单价
     */
    @Column(name = "quantity_unit_price")
    private BigDecimal quantityUnitPrice;

    /**
     * 重量单价
     */
    @Column(name = "weight_unit_price")
    private BigDecimal weightUnitPrice;

    /**
     * 体积单价
     */
    @Column(name = "volume_unit_price")
    private BigDecimal volumeUnitPrice;

    /**
     * 记账重量
     */
    @Column(name = "billing_weight")
    private BigDecimal billingWeight;

    /**
     * 计费方式
     */
    @Column(name = "charging_ways")
    private String chargingWays;

    /**
     * 计费数量
     */
    @Column(name = "charging_quantity")
    private BigDecimal chargingQuantity;

    /**
     * 计费单价
     */
    @Column(name = "charging_unit_price")
    private BigDecimal chargingUnitPrice;
    /**
     * 货品供应商批次
     */
    @Column(name = "support_batch")
    private String supportBatch;

    /**
     * 包装名称
     */
    @Column(name = "package_name")
    private String packageName;

    /**
     * 包装类型
     *
     * @return
     */
    @Column(name = "package_type")
    private String packageType;

    /**
     * 与主单位的换算规格
     *
     * @return
     */
    @Column(name = "conversion_rate")
    private BigDecimal conversionRate;

    @Column(name = "primary_quantity")
    private BigDecimal primaryQuantity;

    /**
     * 获取货品代码
     *
     * @return goods_code - 货品代码
     */
    public String getGoodsCode() {
        return goodsCode;
    }

}