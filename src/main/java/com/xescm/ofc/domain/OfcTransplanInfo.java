package com.xescm.ofc.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "ofc_transplan_info")
public class OfcTransplanInfo {
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
     * 预计发货时间
     */
    @Column(name = "pickup_time")
    private Date pickupTime;

    /**
     * 预计到达时间
     */
    @Column(name = "expected_arrived_time")
    private Date expectedArrivedTime;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 体积
     */
    private BigDecimal volume;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 发货客户代码
     */
    @Column(name = "shippin_customer_code")
    private String shippinCustomerCode;

    /**
     * 发货客户代码
     */
    @Column(name = "shippin_customer_name")
    private String shippinCustomerName;

    /**
     * 发货客户地址
     */
    @Column(name = "shipping_address")
    private String shippingAddress;

    /**
     * 发货客户联系人
     */
    @Column(name = "shipping_customer_contact")
    private String shippingCustomerContact;

    /**
     * 发货客户联系电话
     */
    @Column(name = "customer_contact_phone")
    private String customerContactPhone;

    /**
     * 出发省份
     */
    @Column(name = "departure_province")
    private String departureProvince;

    /**
     * 出发城市
     */
    @Column(name = "departure_city")
    private String departureCity;

    /**
     * 出发区县
     */
    @Column(name = "departure_district")
    private String departureDistrict;

    /**
     * 出发乡镇
     */
    @Column(name = "departure_towns")
    private String departureTowns;

    /**
     * 出发地区域编码
     */
    @Column(name = "departure_place_code")
    private String departurePlaceCode;

    /**
     * 收货客户代码
     */
    @Column(name = "receiving_customer_code")
    private String receivingCustomerCode;

    /**
     * 收货客户名称
     */
    @Column(name = "receiving_customer_name")
    private String receivingCustomerName;

    /**
     * 收货客户地址
     */
    @Column(name = "receiving_customer_address")
    private String receivingCustomerAddress;

    /**
     * 收货客户联系人
     */
    @Column(name = "receiving_customer_contact")
    private String receivingCustomerContact;

    /**
     * 收货客户联系电话
     */
    @Column(name = "receiving_customer_contact_phone")
    private String receivingCustomerContactPhone;

    /**
     * 目的省份
     */
    @Column(name = "destination_province")
    private String destinationProvince;

    /**
     * 目的城市
     */
    @Column(name = "destination_city")
    private String destinationCity;

    /**
     * 目的区县
     */
    @Column(name = "destination_district")
    private String destinationDistrict;

    /**
     * 目的乡镇
     */
    @Column(name = "destination_town")
    private String destinationTown;

    /**
     * 目的地区域编码
     */
    @Column(name = "destination_code")
    private String destinationCode;


    /**
     * 收货地址经度
     */
    @Column(name = "receiving_address_longitude")
    private String receivingAddressLongitude;

    /**
     * 收货地址纬度
     */
    @Column(name = "receiving_address_latitude")
    private String receivingAddressLatitude;

    /**
     * 备注
     */
    private String notes;

    /**
     * 销售组织
     */
    @Column(name = "sale_organization")
    private String saleOrganization;

    /**
     * 产品组
     */
    @Column(name = "product_group")
    private String productGroup;

    /**
     * 销售部门
     */
    @Column(name = "sale_department")
    private String saleDepartment;

    /**
     * 销售组
     */
    @Column(name = "sale_group")
    private String saleGroup;

    /**
     * 销售部门描述
     */
    @Column(name = "sale_department_desc")
    private String saleDepartmentDesc;

    /**
     * 销售组描述
     */
    @Column(name = "sale_group_desc")
    private String saleGroupDesc;

    /**
     * 运输单来源
     */
    @Column(name = "single_source_of_transport")
    private String singleSourceOfTransport;

    /**
     * 服务费用
     */
    @Column(name = "service_charge")
    private BigDecimal serviceCharge;

    /**
     * 基地ID(电商)
     */
    @Column(name = "base_id")
    private String baseId;

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

    /**
     * 开单员
     */
    private String merchandiser;

    /**
     * 运输类型
     */
    @Column(name = "transport_type")
    private String transportType;

    /**
     * 基地名称(电商)
     */
    @Column(name = "base_name")
    private String baseName;

    /**
     * 货品种类
     */
    @Column(name = "goods_type")
    private String goodsType;

    /**
     * 货品种类名称
     */
    @Column(name = "goods_type_name")
    private String goodsTypeName;

    /**
     * 货主名称
     */
    @Column(name = "cust_name")
    private String custName;

    /**
     * 是否二次配送
     */
    @Column(name = "two_distribution")
    private String twoDistribution;


}