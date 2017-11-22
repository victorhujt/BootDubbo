package com.xescm.ofc.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "cwb_finance_information")
public class CwbFinanceInformation {
    /**
     * 服务费
     */
    @Column(name = "service_charge")
    private BigDecimal serviceCharge;

    /**
     * 订单金额
     */
    @Column(name = "order_amount")
    private BigDecimal orderAmount;

    /**
     * 货款金额
     */
    @Column(name = "payment_amount")
    private BigDecimal paymentAmount;

    /**
     * 代收贷款金额
     */
    @Column(name = "collect_loan_amount")
    private BigDecimal collectLoanAmount;

    /**
     * 代收服务费
     */
    @Column(name = "collect_service_charge")
    private BigDecimal collectServiceCharge;

    /**
     * 代收标记
     */
    @Column(name = "collect_flag")
    private String collectFlag;

    /**
     * 结算标记
     */
    @Column(name = "count_flag")
    private String countFlag;

    /**
     * 订单编号
     */
    @Id
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 备注
     */
    private String notes;

    /**
     * 创建日期
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
     * 是否打印发票(电商)
     */
    @Column(name = "print_invoice")
    private String printInvoice;

    /**
     * 买家支付方式(电商)
     */
    @Column(name = "buyer_payment_method")
    private String buyerPaymentMethod;

    /**
     * 是否保价(电商)
     */
    private String insure;

    /**
     * 保价金额(电商)
     */
    @Column(name = "insure_value")
    private BigDecimal insureValue;

    /**
     * 是否上门提货
     */
    @Column(name = "pick_up_goods")
    private String pickUpGoods;

    /**
     * 上门提货费用
     */
    @Column(name = "home_delivery_fee")
    private BigDecimal homeDeliveryFee;

    /**
     * 货物保险费用
     */
    @Column(name = "cargo_insurance_fee")
    private BigDecimal cargoInsuranceFee;

    /**
     * 保险金额
     */
    private BigDecimal insurance;

    /**
     * 是否二次配送
     */
    @Column(name = "two_distribution")
    private String twoDistribution;

    /**
     * 二次配送费用
     */
    @Column(name = "two_distribution_fee")
    private BigDecimal twoDistributionFee;

    /**
     * 是否签单返回
     */
    @Column(name = "return_list")
    private String returnList;

    /**
     * 签单返回费用
     */
    @Column(name = "return_list_fee")
    private BigDecimal returnListFee;

    /**
     * 费用支付方
     */
    @Column(name = "expense_payment_party")
    private String expensePaymentParty;

    /**
     * 支付方式
     */
    private String payment;

    /**
     * 现结金额
     */
    @Column(name = "current_amount")
    private BigDecimal currentAmount;

    /**
     * 到付金额
     */
    @Column(name = "to_pay_amount")
    private BigDecimal toPayAmount;

    /**
     * 回付金额
     */
    @Column(name = "return_amount")
    private BigDecimal returnAmount;

    /**
     * 月结金额
     */
    @Column(name = "monthly_amount")
    private BigDecimal monthlyAmount;

    /**
     * 运费
     */
    private BigDecimal luggage;

    /**
     * 是否开发票(货主)
     */
    @Column(name = "open_invoices")
    private String openInvoices;

    /**
     * 删除标志位 0或者空表示未删除  1:表示已经删除
     */
    @Column(name = "dr")
    private int dr = 0;
}