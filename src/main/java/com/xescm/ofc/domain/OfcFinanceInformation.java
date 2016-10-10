package com.xescm.ofc.domain;

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
    private Date orderCode;

    /**
     * 备注
     */
    private String notes;

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
    public Date getOrderCode() {
        return orderCode;
    }

    /**
     * 设置订单编号
     *
     * @param orderCode 订单编号
     */
    public void setOrderCode(Date orderCode) {
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
}