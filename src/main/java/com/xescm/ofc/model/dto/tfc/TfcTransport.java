package com.xescm.ofc.model.dto.tfc;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * Demo class
 *
 * @author hujintao
 * @date 2017/10/17
 */
@Data
public class TfcTransport implements Serializable {

    private Integer id;
    /**
     * 系统来源
     */
    private String fromSystem;
    /**
     * 运输单号
     */
    private String transportNo;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    /**
     * 状态
     */
    private String status;
    /**
     * 运单类型
     */
    private String billType;
    /**
     * 商品类型
     */
    private String itemType;
    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 客户电话
     */
    private String customerTel;
    /**
     * 运输单产生机构
     */
    private String fromTransportName;
    /**
     * 预计发货时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date expectedShipmentTime;
    /**
     * 预计到达时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date expectedArriveTime;
    /**
     * 重量
     */
    private Double weight;
    /**
     * 数量
     */
    private Double qty;
    /**
     * 体积
     */
    private Double volume;
    /**
     * 金额
     */
    private Double money;
    /**
     * 发货客户编码
     */
    private String fromCustomerCode;
    /**
     * 发货客户联系人
     */
    private String fromCustomerName;
    /**
     * 发货客户联系人编码
     */
    private String fromCustomerNameCode;
    /**
     * 发货客户地址
     */
    private String fromCustomerAddress;
    /**
     * 发货客户
     */
    private String fromCustomer;
    /**
     * 发货客户电话
     */
    private String fromCustomerTle;
    /**
     * 发货省份
     */
    private String fromProvince;
    /**
     * 发货城市
     */
    private String fromCity;
    /**
     * 发货县区
     */
    private String fromDistrict;
    /**
     * 发货乡镇
     */
    private String fromTown;
    /**
     * 收货客户编码
     */
    private String toCustomerCode;
    /**
     * 收货客户名称
     */
    private String toCustomerName;
    /**
     * 收货客户编码
     */
    private String toCustomerNameCode;
    /**
     * 收货客户地址
     */
    private String toCustomerAddress;
    /**
     * 收货客户
     */
    private String toCustomer;
    /**
     * 收货客户电话
     */
    private String toCustomerTle;
    /**
     * 收货省份
     */
    private String toProvince;
    /**
     * 收货城市
     */
    private String toCity;
    /**
     * 收货县区
     */
    private String toDistrict;
    /**
     * 收货乡镇
     */
    private String toTown;
    /**
     * 收货经度
     */
    private String toLon;
    /**
     * 收货纬度
     */
    private String toLat;
    /**
     * 仓库编码
     */
    private String wareHouesCode;
    /**
     * 调度单号
     */
    private String deliveryNo;
    /**
     * 备注
     */
    private String notes;
    /**
     * 销售组织
     */
    private String marketOrg;
    /**
     * 产品组
     */
    private String productTeam;
    /**
     * 销售部门
     */
    private String marketDep;
    /**
     * 销售组
     */
    private String marketTeam;
    /**
     * 销售部门描述
     */
    private String marketDepDes;
    /**
     * 销售组描述
     */
    private String marketTeamDes;
    /**
     * 运单来源
     */
    private String transportSource;
    /**
     * 基地名称
     */
    private String baseName;

    private List<TfcTransportDetail> productDetail;

    private String interfaceStatus = "待处理";
    /**
     * 客户订单号
     */
    private String customerOrderCode;
    /**
     * 订单编号
     */
    private String orderCode;
    /**
     * 订单批次号
     */
    private String orderBatchNumber;
    /**
     * 计划序号
     */
    private String programSerialNumber;
    /**
     * 目的地区域编码
     */
    private String destinationCode;
    /**
     * 服务费用
     */
    private BigDecimal serviceCharge;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    /**
     * 日期
     */
    private Date orderTime;
    /**
     * 创建人员
     */
    private String createPersonnel;
    /**
     * 作废人员
     */
    private String voidPersonnel;
    /**
     * 作废时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date voidTime;
    /**
     * 开单员
     */
    private String merchandiser;
    /**
     * 类型
     */
    private String businessType;
    /**
     * 货品种类名称
     */
    private String goodsTypeName;
    /**
     * 是否二次配送
     */
    private String twoDistribution;
    /**
     * 运力池
     */
    private String transportPool;
    /**
     * 匹配方式
     */
    private String matchingMode;
    /**
     * 调度状态
     */
    private String schedulingState;
    /**
     * 运力池名称
     */
    private String transportPoolName;
    /**
     * 出发省份编码
     */
    private String fromProvinceCode;
    /**
     * 出发城市编码
     */
    private String fromCityCode;
    /**
     * 出发县区编码
     */
    private String fromDistrictCode;
    /**
     * 出发街道编码
     */
    private String fromStreetCode;
    /**
     * 到达省份编码
     */
    private String toProvinceCode;
    /**
     * 到达城市编码
     */
    private String toCityCode;
    /**
     * 到达县区编码
     */
    private String toDistrictCode;
    /**
     * 到达街道编码
     */
    private String toStreetCode;
    /**
     * 面单号
     */
    private String faceOrder;

    /**
     * 运费
     */
    private BigDecimal luggage;

    /**
     * 代收服务费
     */
    private BigDecimal collectServiceCharge;
    /**
     * 运输类型
     */
    private String transportType;

    /**
     *    基地编码
     */
    private String baseCode;
    /**
     * 基地名称
     */
    private String baseDesignation;
    /**
     * 大区编码
     */
    private String areaCode;
    /**
     * 大区名称
     */
    private String areaName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date pickupTime;
    /**
     * 平台类型
     */
    private String platformType;
    /**
     * 支付方式
     */
    private String paymentWay;
    /**
     * 费用支付方
     */
    private String carrFeePayer;
    /**
     * 是否签单返回
     */
    private String isReceipt;
    /**
     * 是否落地配
     */
    private String groundDistribution;
    /**
     * 是否提供运输
     */
    private Integer provideTransport;

}