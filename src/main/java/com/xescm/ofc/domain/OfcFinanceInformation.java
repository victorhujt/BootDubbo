package com.xescm.ofc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "ofc_finance_information")
public class OfcFinanceInformation {
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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
    private String insureValue;

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
     * 获取服务费
     *
     * @return service_charge - 服务费
     */
    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    /**
     * 设置服务费
     *
     * @param serviceCharge 服务费
     */
    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    /**
     * 获取订单金额
     *
     * @return order_amount - 订单金额
     */
    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    /**
     * 设置订单金额
     *
     * @param orderAmount 订单金额
     */
    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    /**
     * 获取货款金额
     *
     * @return payment_amount - 货款金额
     */
    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * 设置货款金额
     *
     * @param paymentAmount 货款金额
     */
    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    /**
     * 获取代收贷款金额
     *
     * @return collect_loan_amount - 代收贷款金额
     */
    public BigDecimal getCollectLoanAmount() {
        return collectLoanAmount;
    }

    /**
     * 设置代收贷款金额
     *
     * @param collectLoanAmount 代收贷款金额
     */
    public void setCollectLoanAmount(BigDecimal collectLoanAmount) {
        this.collectLoanAmount = collectLoanAmount;
    }

    /**
     * 获取代收服务费
     *
     * @return collect_service_charge - 代收服务费
     */
    public BigDecimal getCollectServiceCharge() {
        return collectServiceCharge;
    }

    /**
     * 设置代收服务费
     *
     * @param collectServiceCharge 代收服务费
     */
    public void setCollectServiceCharge(BigDecimal collectServiceCharge) {
        this.collectServiceCharge = collectServiceCharge;
    }

    /**
     * 获取代收标记
     *
     * @return collect_flag - 代收标记
     */
    public String getCollectFlag() {
        return collectFlag;
    }

    /**
     * 设置代收标记
     *
     * @param collectFlag 代收标记
     */
    public void setCollectFlag(String collectFlag) {
        this.collectFlag = collectFlag;
    }

    /**
     * 获取结算标记
     *
     * @return count_flag - 结算标记
     */
    public String getCountFlag() {
        return countFlag;
    }

    /**
     * 设置结算标记
     *
     * @param countFlag 结算标记
     */
    public void setCountFlag(String countFlag) {
        this.countFlag = countFlag;
    }

    /**
     * 获取订单编号
     *
     * @return order_code - 订单编号
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * 设置订单编号
     *
     * @param orderCode 订单编号
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    /**
     * 获取备注
     *
     * @return notes - 备注
     */
    public String getNotes() {
        return notes;
    }

    /**
     * 设置备注
     *
     * @param notes 备注
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * 获取创建日期
     *
     * @return creation_time - 创建日期
     */
    public Date getCreationTime() {
        return creationTime;
    }

    /**
     * 设置创建日期
     *
     * @param creationTime 创建日期
     */
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * 获取创建人员
     *
     * @return creator - 创建人员
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人员
     *
     * @param creator 创建人员
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 获取操作人员
     *
     * @return operator - 操作人员
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置操作人员
     *
     * @param operator 操作人员
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 获取操作时间
     *
     * @return oper_time - 操作时间
     */
    public Date getOperTime() {
        return operTime;
    }

    /**
     * 设置操作时间
     *
     * @param operTime 操作时间
     */
    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    /**
     * 获取是否打印发票(电商)
     *
     * @return print_invoice - 是否打印发票(电商)
     */
    public String getPrintInvoice() {
        return printInvoice;
    }

    /**
     * 设置是否打印发票(电商)
     *
     * @param printInvoice 是否打印发票(电商)
     */
    public void setPrintInvoice(String printInvoice) {
        this.printInvoice = printInvoice;
    }

    /**
     * 获取买家支付方式(电商)
     *
     * @return buyer_payment_method - 买家支付方式(电商)
     */
    public String getBuyerPaymentMethod() {
        return buyerPaymentMethod;
    }

    /**
     * 设置买家支付方式(电商)
     *
     * @param buyerPaymentMethod 买家支付方式(电商)
     */
    public void setBuyerPaymentMethod(String buyerPaymentMethod) {
        this.buyerPaymentMethod = buyerPaymentMethod;
    }

    /**
     * 获取是否保价(电商)
     *
     * @return insure - 是否保价(电商)
     */
    public String getInsure() {
        return insure;
    }

    /**
     * 设置是否保价(电商)
     *
     * @param insure 是否保价(电商)
     */
    public void setInsure(String insure) {
        this.insure = insure;
    }

    /**
     * 获取保价金额(电商)
     *
     * @return insure_value - 保价金额(电商)
     */
    public String getInsureValue() {
        return insureValue;
    }

    /**
     * 设置保价金额(电商)
     *
     * @param insureValue 保价金额(电商)
     */
    public void setInsureValue(String insureValue) {
        this.insureValue = insureValue;
    }

    /**
     * 获取是否上门提货
     *
     * @return pick_up_goods - 是否上门提货
     */
    public String getPickUpGoods() {
        return pickUpGoods;
    }

    /**
     * 设置是否上门提货
     *
     * @param pickUpGoods 是否上门提货
     */
    public void setPickUpGoods(String pickUpGoods) {
        this.pickUpGoods = pickUpGoods;
    }

    /**
     * 获取上门提货费用
     *
     * @return home_delivery_fee - 上门提货费用
     */
    public BigDecimal getHomeDeliveryFee() {
        return homeDeliveryFee;
    }

    /**
     * 设置上门提货费用
     *
     * @param homeDeliveryFee 上门提货费用
     */
    public void setHomeDeliveryFee(BigDecimal homeDeliveryFee) {
        this.homeDeliveryFee = homeDeliveryFee;
    }

    /**
     * 获取货物保险费用
     *
     * @return cargo_insurance_fee - 货物保险费用
     */
    public BigDecimal getCargoInsuranceFee() {
        return cargoInsuranceFee;
    }

    /**
     * 设置货物保险费用
     *
     * @param cargoInsuranceFee 货物保险费用
     */
    public void setCargoInsuranceFee(BigDecimal cargoInsuranceFee) {
        this.cargoInsuranceFee = cargoInsuranceFee;
    }

    /**
     * 获取保险金额
     *
     * @return insurance - 保险金额
     */
    public BigDecimal getInsurance() {
        return insurance;
    }

    /**
     * 设置保险金额
     *
     * @param insurance 保险金额
     */
    public void setInsurance(BigDecimal insurance) {
        this.insurance = insurance;
    }

    /**
     * 获取是否二次配送
     *
     * @return two_distribution - 是否二次配送
     */
    public String getTwoDistribution() {
        return twoDistribution;
    }

    /**
     * 设置是否二次配送
     *
     * @param twoDistribution 是否二次配送
     */
    public void setTwoDistribution(String twoDistribution) {
        this.twoDistribution = twoDistribution;
    }

    /**
     * 获取二次配送费用
     *
     * @return two_distribution_fee - 二次配送费用
     */
    public BigDecimal getTwoDistributionFee() {
        return twoDistributionFee;
    }

    /**
     * 设置二次配送费用
     *
     * @param twoDistributionFee 二次配送费用
     */
    public void setTwoDistributionFee(BigDecimal twoDistributionFee) {
        this.twoDistributionFee = twoDistributionFee;
    }

    /**
     * 获取是否签单返回
     *
     * @return return_list - 是否签单返回
     */
    public String getReturnList() {
        return returnList;
    }

    /**
     * 设置是否签单返回
     *
     * @param returnList 是否签单返回
     */
    public void setReturnList(String returnList) {
        this.returnList = returnList;
    }

    /**
     * 获取签单返回费用
     *
     * @return return_list_fee - 签单返回费用
     */
    public BigDecimal getReturnListFee() {
        return returnListFee;
    }

    /**
     * 设置签单返回费用
     *
     * @param returnListFee 签单返回费用
     */
    public void setReturnListFee(BigDecimal returnListFee) {
        this.returnListFee = returnListFee;
    }

    /**
     * 获取费用支付方
     *
     * @return expense_payment_party - 费用支付方
     */
    public String getExpensePaymentParty() {
        return expensePaymentParty;
    }

    /**
     * 设置费用支付方
     *
     * @param expensePaymentParty 费用支付方
     */
    public void setExpensePaymentParty(String expensePaymentParty) {
        this.expensePaymentParty = expensePaymentParty;
    }

    /**
     * 获取支付方式
     *
     * @return payment - 支付方式
     */
    public String getPayment() {
        return payment;
    }

    /**
     * 设置支付方式
     *
     * @param payment 支付方式
     */
    public void setPayment(String payment) {
        this.payment = payment;
    }

    /**
     * 获取现结金额
     *
     * @return current_amount - 现结金额
     */
    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    /**
     * 设置现结金额
     *
     * @param currentAmount 现结金额
     */
    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    /**
     * 获取到付金额
     *
     * @return to_pay_amount - 到付金额
     */
    public BigDecimal getToPayAmount() {
        return toPayAmount;
    }

    /**
     * 设置到付金额
     *
     * @param toPayAmount 到付金额
     */
    public void setToPayAmount(BigDecimal toPayAmount) {
        this.toPayAmount = toPayAmount;
    }

    /**
     * 获取回付金额
     *
     * @return return_amount - 回付金额
     */
    public BigDecimal getReturnAmount() {
        return returnAmount;
    }

    /**
     * 设置回付金额
     *
     * @param returnAmount 回付金额
     */
    public void setReturnAmount(BigDecimal returnAmount) {
        this.returnAmount = returnAmount;
    }

    /**
     * 获取月结金额
     *
     * @return monthly_amount - 月结金额
     */
    public BigDecimal getMonthlyAmount() {
        return monthlyAmount;
    }

    /**
     * 设置月结金额
     *
     * @param monthlyAmount 月结金额
     */
    public void setMonthlyAmount(BigDecimal monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }

    /**
     * 获取运费
     *
     * @return luggage - 运费
     */
    public BigDecimal getLuggage() {
        return luggage;
    }

    /**
     * 设置运费
     *
     * @param luggage 运费
     */
    public void setLuggage(BigDecimal luggage) {
        this.luggage = luggage;
    }
}