package com.xescm.ofc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "cwb_distribution_basic_info")
public class CwbDistributionBasicInfo {
    /**
     * 运输单号
     */
    @Column(name = "trans_code")
    private String transCode;

    /**
     * 是否加急
     */
    private Integer urgent;

    /**
     * 出发地
     */
    @Column(name = "departure_place")
    private String departurePlace;

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
     * 目的地
     */
    private String destination;

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
    @Column(name = "destination_towns")
    private String destinationTowns;


    /**
     * 目的地区域编码
     */
    @Column(name = "destination_code")
    private String destinationCode;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 体积
     */
    private String cubage;

    /**
     * 合计标准箱
     */
    @Column(name = "total_standard_box")
    private Integer totalStandardBox;

    /**
     * 运输要求
     */
    @Column(name = "trans_require")
    private String transRequire;

    /**
     * 取货时间
     */

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "pickup_time")
    private Date pickupTime;

    /**
     * 期望送货时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "expected_arrived_time")
    private Date expectedArrivedTime;

    /**
     * 发货方编码
     */
    @Column(name = "consignor_code")
    private String consignorCode;

    /**
     * 发货方名称
     */
    @Column(name = "consignor_name")
    private String consignorName;

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
     * 发货方联系人编码
     */
    @Column(name = "consignor_contact_code")
    private String consignorContactCode;

    /**
     * 发货方联系人名称
     */
    @Column(name = "consignor_contact_name")
    private String consignorContactName;

    /**
     * 收货方联系人编码
     */
    @Column(name = "consignee_contact_code")
    private String consigneeContactCode;

    /**
     * 收货方联系人名称
     */
    @Column(name = "consignee_contact_name")
    private String consigneeContactName;

    /**
     * 收货方联系人类型
     * 1、企业公司；2、个人',
     */
    @Column(name = "consignor_type")
    private String consignorType;

    /**
     * 收货方联系人类型
     * 1、企业公司；2、个人',
     */
    @Column(name = "consignee_type")
    private String consigneeType;

    /**
     * 承运商编码
     */
    @Column(name = "carrier_code")
    private String carrierCode;

    /**
     * 承运商名称
     */
    @Column(name = "carrier_name")
    private String carrierName;

    /**
     * 订单编号
     */
    @Id
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 车牌号
     */
    @Column(name = "plate_number")
    private String plateNumber;

    /**
     * 司机姓名
     */
    @Column(name = "driver_name")
    private String driverName;

    /**
     * 基地ID(电商)
     */
    @Column(name = "base_id")
    private String baseId;

    /**
     * 联系电话
     */
    @Column(name = "contact_number")
    private String contactNumber;

    /**
     * 创建时间
     */
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
    @Column(name = "oper_time")
    private Date operTime;

    /**
     * 发货方联系人电话
     */
    @Column(name = "consignor_contact_phone")
    private String consignorContactPhone;

    /**
     * 收货方联系人电话
     */
    @Column(name = "consignee_contact_phone")
    private String consigneeContactPhone;

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
     * 校验运输单号专用字段
     */
    @Transient
    private String selfTransCode;

    /*
     * 删除标志位 0或者空表示未删除  1:表示已经删除
     */
    @Column(name = "dr")
    private int dr = 0;
}