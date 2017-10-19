package com.xescm.ofc.model.dto.ofc;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hujintao on 2017/7/11.
 */
@ApiModel("订单财务信息dto")
@Data
public class OfcFinanceInformationDTO {
    /**
     * 服务费
     */
    private BigDecimal serviceCharge;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 货款金额
     */
    private BigDecimal paymentAmount;

    /**
     * 代收贷款金额
     */
    private BigDecimal collectLoanAmount;

    /**
     * 代收服务费
     */
    private BigDecimal collectServiceCharge;

    /**
     * 代收标记
     */
    private String collectFlag;

    /**
     * 结算标记
     */
    private String countFlag;

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
     * 是否打印发票(电商)
     */
    private String printInvoice;

    /**
     * 买家支付方式(电商)
     */
    private String buyerPaymentMethod;

    /**
     * 是否保价(电商)
     */
    private String insure;

    /**
     * 保价金额(电商)
     */
    private BigDecimal insureValue;

    /**
     * 是否上门提货
     */
    private String pickUpGoods;

    /**
     * 上门提货费用
     */
    private BigDecimal homeDeliveryFee;

    /**
     * 货物保险费用
     */
    private BigDecimal cargoInsuranceFee;

    /**
     * 保险金额
     */
    private BigDecimal insurance;

    /**
     * 是否二次配送
     */
    private String twoDistribution;

    /**
     * 二次配送费用
     */
    private BigDecimal twoDistributionFee;

    /**
     * 是否签单返回
     */
    private String returnList;

    /**
     * 签单返回费用
     */
    private BigDecimal returnListFee;

    /**
     * 费用支付方
     */
    private String expensePaymentParty;

    /**
     * 支付方式
     */
    private String payment;

    /**
     * 现结金额
     */
    private BigDecimal currentAmount;

    /**
     * 到付金额
     */
    private BigDecimal toPayAmount;

    /**
     * 回付金额
     */
    private BigDecimal returnAmount;

    /**
     * 月结金额
     */
    private BigDecimal monthlyAmount;

    /**
     * 运费
     */
    private BigDecimal luggage;

    /**
     * 是否开发票(货主)
     */
    private String openInvoices;

}
