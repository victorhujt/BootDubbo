package com.xescm.ofc.model.dto.coo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单中心创建订单（鲜易网）
 * 订单主表信息
 * Created by hiyond on 2016/11/14.
 */
@Data
public class CreateOrderEntity {

    /**
     * 客户订单编号 Y
     * 传入对应平台的订单编号
     */
    private String custOrderCode;

    /**
     * 订单日期 Y
     * 订单日期
     */
    private String orderTime;

    /**
     * 货主编码 Y
     * 固定平台客户的代码(确定接入后分配)
     */
    private String custCode;

    /**
     * 货主名称 Y
     * 固定平台客户的公司名称
     */
    private String custName;

    /**
     *二级客户编码
     */
    private String secCustCode;

    /**
     * 二级客户名称
     */
    private String secCustName;

    /**
     * 订单类型 Y
     * 60-运输订单, 仅运输业务
     * 61-仓配订单，有合作仓同时配送业务
     */
    private String orderType;

    /**
     * 业务类型 Y
     * 订单类型：【60】
     * 业务类型：600-【城配】
     * 601-【干线】
     * 订单类型：【61】
     * 业务类型:610-【销售出库】
     * 611-【调拨出库】
     * 612-【报损出库】
     * 613-【其他出库】
     * 620-【采购入库】
     * 621-【调拨入库】
     * 622-【退货入库】
     * 623-【加工入库】
     */
    private String businessType;

    /**
     * 运输类型
     */
    private String transportType;

    /**
     * 备注 Y
     */
    private String notes;

    /**
     * 店铺编码 Y
     * 在工作台维护，客户方系统店铺编码，用于区分渠道,传空为默认
     */
    private String storeCode;

    /**
     * 平台类型
     */
    private String platformType;

    /**
     * 订单来源 Y
     * 【平台、EDI、手工录入】
     */
    private String orderSource;

    /**
     * 销售组织
     * SAP专用
     */
    private String expandSaleOrg;

    /**
     * 产品组
     * SAP专用
     */
    private String expandProGroup;

    /**
     * 销售部门
     * SAP专用
     */
    private String expandSaleDep;

    /**
     * 销售组
     * SAP专用
     */
    private String expandSaleGroup;

    /**
     * 销售部门描述
     * SAP专用
     */
    private String expandSaleDepDes;

    /**
     * 销售组描述
     * SAP专用
     */
    private String expandSaleGroupDes;

    /**
     * 数量
     * 【数量、重量、体积】三选一，运输订单时判断
     */
    private String quantity;

    /**
     * 重量
     * 【数量、重量、体积】三选一，运输订单时判断
     */
    private String weight;

    /**
     * 体积
     * 【数量、重量、体积】三选一，运输订单时判断
     */
    private String cubage;

    /**
     * 合计标准箱
     * 最大值9999
     */
    private String totalStandardBox;

    /**
     * 运输要求
     */
    private String transRequire;

    /**
     * 取货时间
     */
    private String pickupTime;

    /**
     * 期望送货时间
     */
    private String expectedArrivedTime;

    /**
     * 发货方编码
     */
    private String consignorCode;

    /**
     * 发货方名称
     */
    private String consignorName;

    /**
     * 发货方联系人
     */
    private String consignorContact;

    /**
     * 发货方联系电话
     */
    private String consignorPhone;

    /**
     * 发货方传真
     */
    private String consignorFax;

    /**
     * 发货方Email
     */
    private String consignorEmail;

    /**
     * 发货方邮编
     */
    private String consignorZip;

    /**
     * 发货方省（汉字）
     */
    private String consignorProvince;

    /**
     * 发货方省（编码）
     */
    private String consignorProvinceCode;

    /**
     * 发货方市（汉字）
     */
    private String consignorCity;

    /**
     * 发货方市（编码）
     */
    private String consignorCityCode;

    /**
     * 发货方区县（汉字）
     */
    private String consignorCounty;

    /**
     * 发货方区县（编码）
     */
    private String consignorCountyCode;

    /**
     * 发货方乡镇街道
     */
    private String consignorTown;

    /**
     * 发货方乡镇街道(编码)
     */
    private String consignorTownCode;

    /**
     * 发货方地址
     */
    private String consignorAddress;

    /**
     * 收货方编码
     */
    private String consigneeCode;
    /**
     * 收货方名称
     */
    private String consigneeName;

    /**
     * 收货方联系人
     */
    private String consigneeContact;

    /**
     * 收货方联系人编码
     */
    private String consigneeContactCode;

    /**
     * 收货方联系电话
     */
    private String consigneePhone;

    /**
     * 收货方传真
     */
    private String consigneeFax;

    /**
     * 收货方email
     */
    private String consigneeEmail;

    /**
     * 收货方邮编
     */
    private String consigneeZip;

    /**
     * 收货方省
     */
    private String consigneeProvince;
    /**
     * 收货方省(编码)
     */
    private String consigneeProvinceCode;

    /**
     * 收货方市
     */
    private String consigneeCity;

    /**
     * 收货方市(编码)
     */
    private String consigneeCityCode;

    /**
     * 收货方区县
     */
    private String consigneeCounty;

    /**
     * 收货方区县 (编码)
     */
    private String consigneeCountyCode;

    /**
     * 收货方乡镇街道
     */
    private String consigneeTown;

    /**
     * 收货方乡镇街道(编码)
     */
    private String consigneeTownCode;

    /**
     * 收货方地址
     */
    private String consigneeAddress;

    /**
     * 仓库编码
     * 在平台的仓库编码
     */
    private String warehouseCode;

    /**
     * 仓库名称
     */
    private String warehouseName;

    /**
     * 是否需要提供运输
     * 0-否 1-是
     */
    private String provideTransport;

    /**
     * 供应商名称
     */
    private String supportName;
    /**
     * 供应商编码
     */
    private String supportCode;

    /**
     * 供应商联系人
     */
    private String supportContact;

    /**
     * 供应商联系电话
     */
    private String supportPhone;

    /**
     * 供应商传真
     */
    private String supportFax;

    /**
     * 供应商Email
     */
    private String supportEmail;

    /**
     * 供应商邮编
     */
    private String supportZip;

    /**
     * 供应商省
     */
    private String supportProvince;

    /**
     * 供应商市
     */
    private String supportCity;

    /**
     * 供应商区县
     */
    private String supportCounty;

    /**
     * 供应商街道
     */
    private String supportTown;

    /**
     * 供应商地址
     */
    private String supportAddress;

    /**
     * 入库预计到达时间
     */
    private String arriveTime;

    /**
     * 车牌号
     */
    private String plateNumber;

    /**
     * 司机姓名
     * 入库或出库自带车辆
     */
    private String driverName;

    /**
     * 联系电话
     * 入库或出库自带车辆
     */
    private String contactNumber;

    /**
     * 服务费
     * 平台运费
     */
    private String serviceCharge;

    /**
     * 订单金额
     */
    private String orderAmount;

    /**
     * 货款金额
     */
    private String paymentAmount;

    /**
     * 代收货款金额
     */
    private String collectLoanAmount;

    /**
     * 代收服务费
     */
    private String collectServiceCharge;

    /**
     * 代收标记
     */
    private String collectFlag;

    /**
     * 是否打印发票(电商)
     */
    private String printInvoice;

    /**
     * 买家支付方式(电商)
     */
    private String buyerPaymentMe;

    /**
     * 是否保价(电商)
     */
    private String insure;

    /**
     * 保价金额(电商)
     */
    private BigDecimal insureValue;

    /**
     * 基地ID(电商)
     */
    private String BaseId;

    /**
     * 基地编码
     */
    private String baseCode;

    /**
     * 基地名称
     */
    private String baseName;

    /**
     * 大区编码
     */
    private String areaCode;

    /**
     * 大区名称
     */
    private String areaName;

    /**
     * API订单货品明细
     */
    private List<CreateOrderGoodsInfo> createOrderGoodsInfos;

    /**
     * 是否进行二次配送
     * "1"是, "0"否
     */
    private String twoDistribution;

    /**
     * 是否进行上门提货
     * "1"是, "0"否
     */
    private String pickUpGoods;


}
