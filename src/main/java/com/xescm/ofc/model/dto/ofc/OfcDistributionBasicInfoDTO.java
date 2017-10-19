package com.xescm.ofc.model.dto.ofc;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
@ApiModel("订单运输信息Dto")
public class OfcDistributionBasicInfoDTO implements Serializable{

    /**
     * 客户编码
     */
    private String custCode;

    /**
     * 客户名称
     */
    private String custName;

    /**
     * 货品类别,第一个货品的大类
     */
    private String goodsType;

    /**
     * 货品类别,第一个货品的大类
     */
    private String goodsTypeName;

    /**
     * 备注
     */
    private String notes;

    /**
     * 运输单号
     */
    private String transCode;

    /**
     * 自身运输单号
     */
    private String selfTransCode;

    /**
     * 是否加急
     */
    private Integer urgent;

    /**
     * 出发地
     */
    private String departurePlace;

    /**
     * 出发省份
     */
    private String departureProvince;

    /**
     * 出发城市
     */
    private String departureCity;

    /**
     * 出发区县
     */
    private String departureDistrict;

    /**
     * 出发乡镇
     */
    private String departureTowns;

    /**
     * 出发地区域编码
     */
    private String departurePlaceCode;

    /**
     * 目的地
     */
    private String destination;

    /**
     * 目的省份
     */
    private String destinationProvince;

    /**
     * 目的城市
     */
    private String destinationCity;

    /**
     * 目的区县
     */
    private String destinationDistrict;

    /**
     * 目的乡镇
     */
    private String destinationTowns;


    /**
     * 目的地区域编码
     */
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
    private Integer totalStandardBox;

    /**
     * 运输要求
     */
    private String transRequire;

    /**
     * 取货时间
     */

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date pickupTime;

    /**
     * 期望送货时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expectedArrivedTime;

    /**
     * 发货方编码
     */
    private String consignorCode;

    /**
     * 发货方名称
     */
    private String consignorName;

    /**
     * 收货方编码
     */
    private String consigneeCode;

    /**
     * 收货方名称
     */
    private String consigneeName;


    /**
     * 发货方联系人编码
     */
    private String consignorContactCode;

    /**
     * 发货方联系人名称
     */
    private String consignorContactName;

    /**
     * 收货方联系人编码
     */
    private String consigneeContactCode;

    /**
     * 收货方联系人名称
     */
    private String consigneeContactName;

    /**
     * 收货方联系人类型
     * 1、企业公司；2、个人',
     */
    private String consignorType;

    /**
     * 收货方联系人类型
     * 1、企业公司；2、个人',
     */
    private String consigneeType;


    /**
     * 承运商编码
     */
    private String carrierCode;

    /**
     * 承运商名称
     */
    private String carrierName;

    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 车牌号
     */
    private String plateNumber;

    /**
     * 司机姓名
     */
    private String driverName;

    /**
     * 基地ID(电商)
     */
    private String baseId;

    /**
     * 联系电话
     */
    private String contactNumber;

    /**
     * 创建时间
     */
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
    private Date operTime;

    /**
     * 发货方联系人电话
     */
    private String consignorContactPhone;
    /**
     * 收货方联系人电话
     */
    private String consigneeContactPhone;

    /**
     * 是否二次配送
     */
    private String twoDistribution;
}