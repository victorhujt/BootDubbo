package com.xescm.ofc.domain;

import java.math.BigDecimal;

public class OfcFinanceInformation {
    private BigDecimal serviceCharge;

    private BigDecimal orderAmount;

    private BigDecimal loanAmount;

    private BigDecimal collectLoanAmount;

    private BigDecimal collectServiceCharge;

    private String collectFlag;

    private String countFlag;

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BigDecimal getCollectLoanAmount() {
        return collectLoanAmount;
    }

    public void setCollectLoanAmount(BigDecimal collectLoanAmount) {
        this.collectLoanAmount = collectLoanAmount;
    }

    public BigDecimal getCollectServiceCharge() {
        return collectServiceCharge;
    }

    public void setCollectServiceCharge(BigDecimal collectServiceCharge) {
        this.collectServiceCharge = collectServiceCharge;
    }

    public String getCollectFlag() {
        return collectFlag;
    }

    public void setCollectFlag(String collectFlag) {
        this.collectFlag = collectFlag == null ? null : collectFlag.trim();
    }

    public String getCountFlag() {
        return countFlag;
    }

    public void setCountFlag(String countFlag) {
        this.countFlag = countFlag == null ? null : countFlag.trim();
    }
}