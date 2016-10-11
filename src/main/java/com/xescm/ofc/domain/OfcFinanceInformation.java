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
}