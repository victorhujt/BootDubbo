package com.xescm.ofc.model.dto.ofc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * Created by lyh on 2016/10/11.
 */
@Data
public class OfcOrderDTO {

    /*基本信息表*/
    private String orderCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderTime;
    /**
     * 货主编码
     */
    private String custCode;
    /**
     * 货主名称
     */
    private String custName;
    /**
     * 二级客户编码
     */
    private String secCustCode;
    /**
     * 二级客户名称
     */
    private String secCustName;
    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 客户订单编号
     */
    private String custOrderCode;
    /**
     * 备注
     */
    private String notes;
    /**
     * 店铺编码
     */
    private String storeCode;
    /**
     * 店铺名称
     */
    private String storeName;
    /**
     * 平台类型
     */
    private String platformType;
    /**
     * 订单来源
     */
    private String orderSource;
    /**
     * 服务产品类型
     */
    private String productType;
    /**
     * 服务产品名称
     */
    private String productName;
    /**
     * 完成日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date finishedTime;
    /**
     * 作废标记
     */
    private Integer abolishMark;
    /**
     * 作废时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date abolishTime;
    /**
     * 作废人
     */
    private String abolisher;
    /**
     * 创建人
     */
    private String creator;
    /**
     *创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date creationTime;
    /**
     * 操作人
     */
    private String operator;
    /**
     * 操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date operTime;
    /**
     * 作废人名称
     */
    private String abolisherName;
    /**
     * 创建人名称
     */
    private String creatorName;
    /**
     * 操作人名称
     */
    private String operatorName;
    /**
     * 开单员
     */
    private String merchandiser;

    /**
     * 是否发送签收短信
     */
    private String signedSms;

    /**
     * 运输类型
     */
    private String transportType;

    private String batchNumber;
    /**
     * 订单批次号
     */
    private String orderBatchNumber;

    /*订单状态表*/

//    private String orderCode;
//    private String orderType;
//    private String businessType;
//    private String custCode;
//    private String custName;
    private String orderStatus;
    private String statusDesc;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastedOperTime;
//    private String notes;
//    private String operator;

    /*运输订单信息*/
    /**
     * 运输单号
     */
    private String transCode;
    //private String goodsType;

    private String selfTransCode;
    /**
     * 是否加急
     */
    private Integer urgent;
    /**
     * 出发地
     */
    private String departurePlace;//完整地址
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
    private String departurePlaceCode;//完整地址编码
    /**
     * 目的地
     */
    private String destination;//完整地址
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
    private String destinationCode;//完整地址编码   111/2222/3333
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date pickupTime;
    /**
     *期望送货时间 [承诺到达时间]
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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
     * 承运商编码
     */
    private String carrierCode;
    /**
     * 承运商名称
     */
    private String carrierName;
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
     * 发货人类型
     */
    private String consignorType;
    /**
     * 收货人类型
     */
    private String consigneeType;
    /**
     * 发货方联系人电话
     */
    private String consignorContactPhone;
    /**
     * 收货方联系人电话
     */
    private String consigneeContactPhone;
    private String goodsType;
    private String goodsTypeName;
   // private List<OfcGoodsDetailsInfo> goodsList = new ArrayList<OfcGoodsDetailsInfo>();
    private List<OfcGoodsDetailsInfo> goodsList = new ArrayList<>();




//    private String orderCode;
//    private Date creationTime;
//    private String creator;
//    private String operator;
//    private Date operTime;//???????????

    /*仓配信息*/
    /**
     * 供应商名称
     */
    private String supportName;
    /**
     * 供应商编码
     */
    private String supportCode;
    /**
     * 是否需要提供运输
     */
    private Integer provideTransport;

    private String groundDistribution;
    /**
     *出库发货时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date shipmentTime;
    /**
     * 入库预计到达时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date arriveTime;
    /**
     *仓库名称
     */
    private String warehouseName;
    /**
     * 仓库编码
     */
    private String warehouseCode;
    /**
     *车牌号
     */
    private String plateNumber;
    /**
     *司机姓名
     */
    private String driverName;
    /**
     * 联系电话
     */
    private String contactNumber;
    /**
     * 供应商联系人编码
     */
    private String supportContactCode;
    /**
     * 供应商联系人
     */
    private String supportContactName;
//    private String orderCode;
//    private Date creationTime;
//    private String creator;
//    private String operator;
//    private Date operTime;

    /*商品表不用*/
    /*财务表*/
    private BigDecimal serviceCharge;
    private BigDecimal orderAmount;
    private BigDecimal paymentAmount;
    private BigDecimal collectLoanAmount;
    private BigDecimal collectServiceCharge;
    private String collectFlag;
    private String countFlag;
//    private String orderCode;
//    private String notes;
//    private Date creationTime;
//    private String creator;
//    private String operator;
//    private Date operTime;
    private String printInvoice;
    private String buyerPaymentMethod;
    private String insure;
    private BigDecimal insureValue;
    private String pickUpGoods;
    private BigDecimal homeDeliveryFee;
    private BigDecimal cargoInsuranceFee;
    private BigDecimal insurance;
    private String twoDistribution;
    private BigDecimal twoDistributionFee;
    private String returnList;
    private BigDecimal returnListFee;
    private String expensePaymentParty;
    private String payment;
    private BigDecimal currentAmount;
    private BigDecimal toPayAmount;
    private BigDecimal returnAmount;
    private BigDecimal monthlyAmount;
    private BigDecimal luggage;
    private String openInvoices;


    private String selfCustOrderCode;

    private String selfCustCode;

    private String consignmentBatchNumber;


    private CscContantAndCompanyDto consignor;

    private CscContantAndCompanyDto consignee;

    private CscSupplierInfoDto supplier;

    private String baseName;

    private String baseCode;

    private String areaName;

    private String areaCode;


}
