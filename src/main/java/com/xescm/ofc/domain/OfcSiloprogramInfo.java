package com.xescm.ofc.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "ofc_siloprogram_info")
public class OfcSiloprogramInfo {
    /**
     * 是否完成标记，查询时使用，非表中字段
     */
    @Transient
    private String ifFinished;

    /**
     * 计划单编号
     */
    @Id
    @Column(name = "plan_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String planCode;

    /**
     * 订单编号
     */
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 订单批次号
     */
    @Column(name = "order_batch_number")
    private String orderBatchNumber;

    /**
     * 计划序号
     */
    @Column(name = "program_serial_number")
    private String programSerialNumber;

    /**
     * 类型
     */
    @Column(name = "business_type")
    private String businessType;

    /**
     * 单据类型
     */
    @Column(name = "document_type")
    private String documentType;

    /**
     * 日期
     */
    @Column(name = "order_time")
    private Date orderTime;

    /**
     * 货主编码
     */
    @Column(name = "cust_code")
    private String custCode;

    /**
     * 仓库编码
     */
    @Column(name = "warehouse_code")
    private String warehouseCode;

    /**
     * 仓库名称
     */
    @Column(name = "warehouse_name")
    private String warehouseName;

    /**
     * 出库发货时间
     */
    @Column(name = "estimated_time_of_delivery")
    private Date estimatedTimeOfDelivery;

    /**
     * 预计到达时间
     */
    @Column(name = "arrive_time")
    private Date arriveTime;

    /**
     * 装货地
     */
    @Column(name = "loading_place")
    private String loadingPlace;

    /**
     * 卸货地
     */
    @Column(name = "unloading_place")
    private String unloadingPlace;

    /**
     * 交货地
     */
    @Column(name = "delivery_place")
    private String deliveryPlace;

    /**
     * 收货月台
     */
    @Column(name = "eceiving_platform")
    private String eceivingPlatform;

    /**
     * 总货值
     */
    @Column(name = "the_total_value_of")
    private String theTotalValueOf;

    /**
     * 供应商编码
     */
    @Column(name = "support_code")
    private String supportCode;

    /**
     * 供应商名称
     */
    @Column(name = "support_name")
    private String supportName;

    /**
     * 备注
     */
    private String notes;

    /**
     * 收货方编码
     */
    @Column(name = "consignee_code")
    private String consigneeCode;

    /**
     * 收货方名称
     */
    @Column(name = "consignee_name")
    private String consigneeName;

    /**
     * 收货方联系人
     */
    @Column(name = "consignee_contact")
    private String consigneeContact;

    /**
     * 收货方联系电话
     */
    @Column(name = "consignee_contact_phone")
    private String consigneeContactPhone;

    /**
     * 收货方传真
     */
    @Column(name = "consignee_fax")
    private String consigneeFax;

    /**
     * 收货方Email
     */
    @Column(name = "consignee_email")
    private String consigneeEmail;

    /**
     * 收货方邮编
     */
    @Column(name = "consignee_post_code")
    private String consigneePostCode;

    /**
     * 收货方省
     */
    @Column(name = "consignee_province")
    private String consigneeProvince;

    /**
     * 收货方市
     */
    @Column(name = "consignee_city")
    private String consigneeCity;

    /**
     * 收货方区县
     */
    @Column(name = "consignee_district_and_county")
    private String consigneeDistrictAndCounty;

    /**
     * 收货方乡镇街道
     */
    @Column(name = "consignee_township_streets")
    private String consigneeTownshipStreets;

    /**
     * 收货方地址
     */
    @Column(name = "consignee_address")
    private String consigneeAddress;

    /**
     * 是否打印发票
     */
    @Column(name = "print_invoice")
    private String printInvoice;

    /**
     * 支付方式
     */
    @Column(name = "buyer_payment_method")
    private String buyerPaymentMethod;

    /**
     * 订单金额
     */
    @Column(name = "order_amount")
    private String orderAmount;

    /**
     * 是否货到付款
     */
    @Column(name = "collect_flag")
    private String collectFlag;

    /**
     * 是否保价
     */
    private String insure;

    /**
     * 保价金额
     */
    @Column(name = "insure_value")
    private String insureValue;

    /**
     * 仓储作业单号
     */
    @Column(name = "warehouse_number")
    private String warehouseNumber;

    /**
     * 服务费用
     */
    @Column(name = "service_charge")
    private String serviceCharge;

    /**
     * 创建时间
     */
    @Column(name = "creation_time")
    private Date creationTime;

    /**
     * 创建人员
     */
    @Column(name = "create_personnel")
    private String createPersonnel;

    /**
     * 作废人员
     */
    @Column(name = "void_personnel")
    private String voidPersonnel;

    /**
     * 作废时间
     */
    @Column(name = "void_time")
    private Date voidTime;


}