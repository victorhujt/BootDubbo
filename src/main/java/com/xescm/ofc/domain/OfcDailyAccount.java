package com.xescm.ofc.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "ofc_daily_account")
public class OfcDailyAccount {
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    /**
     * 大区名称
     */
    @Column(name = "area_name")
    private String areaName;

    /**
     * 大区编码
     */
    @Column(name = "area_code")
    private String areaCode;

    /**
     * 基地名称
     */
    @Column(name = "base_name")
    private String baseName;

    /**
     * 基地编码
     */
    @Column(name = "base_code")
    private String baseCode;

    /**
     * 两小时的订单总计
     */
    @Column(name = "two_hour_account")
    private BigDecimal twoHourAccount;

    /**
     * 前一天的开单总计
     */
    @Column(name = "yesterday_account")
    private BigDecimal yesterdayAccount;

    /**
     * 收入确认的订单(数据来源于结算中心)
     */
    @Column(name = "have_income_order_account")
    private BigDecimal haveIncomeOrderAccount;

    /**
     * 应付确认车的数量(数据来源于结算中心)
     */
    @Column(name = "payable_vehicle_account")
    private BigDecimal payableVehicleAccount;
    /**
     * 外部车辆发运的数量(数据来源于运输中心)
     */
    @Column(name = "external_vehicle_account")
    private BigDecimal externalVehicleAccount;

    /**
     * 创建时间
     */

    /**
     * 事后补录订单百分比
     */
    @Column(name = "additional_order_percent")
    private String additionalOrderPercent;

    /**
     *  应收确认日清百分比
     */
    @Column(name = "receivable_percent")
    private String receivablePercent;

    public String getTotalPercent() {
        return totalPercent;
    }

    public void setTotalPercent(String totalPercent) {
        this.totalPercent = totalPercent;
    }

    /**
     * 应收确认日清 + 应付确认日清 - 事后补录订单

     */
    @Column(name = "total_percent")
    private String totalPercent;
    /**
     * 事后补录订单 小数
     */
    @Column(name = "additional_order")
    private BigDecimal additionalOrder;

    /**
     * 应收确认日清 小数
     */
    @Column(name = "receivable")
    private BigDecimal receivable;
    /**
     * 应付确认日清  小数
     */
    @Column(name = "payable")
    private BigDecimal payable;

    public BigDecimal getAdditionalOrder() {
        return additionalOrder;
    }

    public void setAdditionalOrder(BigDecimal additionalOrder) {
        this.additionalOrder = additionalOrder;
    }

    public BigDecimal getReceivable() {
        return receivable;
    }

    public void setReceivable(BigDecimal receivable) {
        this.receivable = receivable;
    }

    public BigDecimal getPayable() {
        return payable;
    }

    public void setPayable(BigDecimal payable) {
        this.payable = payable;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * 应收确认日清 + 应付确认日清 - 事后补录订单 小数

     */
    @Column(name = "total")
    private BigDecimal total;




    public String getAdditionalOrderPercent() {
        return additionalOrderPercent;
    }

    public void setAdditionalOrderPercent(String additionalOrderPercent) {
        this.additionalOrderPercent = additionalOrderPercent;
    }

    public String getReceivablePercent() {
        return receivablePercent;
    }

    public void setReceivablePercent(String receivablePercent) {
        this.receivablePercent = receivablePercent;
    }

    public String getPayablePercent() {
        return payablePercent;
    }

    public void setPayablePercent(String payablePercent) {
        this.payablePercent = payablePercent;
    }

    /**
     * 应付确认日清百分比

     */
    @Column(name = "payable_percent")
    private String payablePercent;



    @Column(name = "gmt_create")
    private Date gmtCreate;


    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;


    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取大区名称
     *
     * @return area_name - 大区名称
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * 设置大区名称
     *
     * @param areaName 大区名称
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * 获取大区编码
     *
     * @return area_code - 大区编码
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * 设置大区编码
     *
     * @param areaCode 大区编码
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * 获取基地名称
     *
     * @return base_name - 基地名称
     */
    public String getBaseName() {
        return baseName;
    }

    /**
     * 设置基地名称
     *
     * @param baseName 基地名称
     */
    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    /**
     * 获取基地编码
     *
     * @return base_code - 基地编码
     */
    public String getBaseCode() {
        return baseCode;
    }

    /**
     * 设置基地编码
     *
     * @param baseCode 基地编码
     */
    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }

    /**
     * 获取两小时的订单总计
     *
     * @return two_hour_account - 两小时的订单总计
     */
    public BigDecimal getTwoHourAccount() {
        return twoHourAccount;
    }

    /**
     * 设置两小时的订单总计
     *
     * @param twoHourAccount 两小时的订单总计
     */
    public void setTwoHourAccount(BigDecimal twoHourAccount) {
        this.twoHourAccount = twoHourAccount;
    }

    /**
     * 获取前一天的开单总计
     *
     * @return yesterday_account - 前一天的开单总计
     */
    public BigDecimal getYesterdayAccount() {
        return yesterdayAccount;
    }

    /**
     * 设置前一天的开单总计
     *
     * @param yesterdayAccount 前一天的开单总计
     */
    public void setYesterdayAccount(BigDecimal yesterdayAccount) {
        this.yesterdayAccount = yesterdayAccount;
    }

    /**
     * 获取收入确认的订单(数据来源于结算中心)
     *
     * @return have_income_order_account - 收入确认的订单(数据来源于结算中心)
     */
    public BigDecimal getHaveIncomeOrderAccount() {
        return haveIncomeOrderAccount;
    }

    /**
     * 设置收入确认的订单(数据来源于结算中心)
     *
     * @param haveIncomeOrderAccount 收入确认的订单(数据来源于结算中心)
     */
    public void setHaveIncomeOrderAccount(BigDecimal haveIncomeOrderAccount) {
        this.haveIncomeOrderAccount = haveIncomeOrderAccount;
    }

    /**
     * 获取应付确认车的数量(数据来源于结算中心)
     *
     * @return  payable_vehicle_account - 应付确认车的数量(数据来源于结算中心)
     */
    public BigDecimal getPayableVehicleAccount() {
        return payableVehicleAccount;
    }

    /**
     * 设置应付确认车的数量(数据来源于结算中心)
     *
     * @param payableVehicleAccount 应付确认车的数量(数据来源于结算中心)
     */
    public void setPayableVehicleAccount(BigDecimal payableVehicleAccount) {
        this.payableVehicleAccount = payableVehicleAccount;
    }

    /**
     * 获取外部车辆发运的数量(数据来源于运输中心)
     *
     * @return external_vehicle_account - 外部车辆发运的数量(数据来源于运输中心)
     */
    public BigDecimal getExternalVehicleAccount() {
        return externalVehicleAccount;
    }

    /**
     * 设置外部车辆发运的数量(数据来源于运输中心)
     *
     * @param externalVehicleAccount 外部车辆发运的数量(数据来源于运输中心)
     */
    public void setExternalVehicleAccount(BigDecimal externalVehicleAccount) {
        this.externalVehicleAccount = externalVehicleAccount;
    }

    /**
     * 获取创建时间
     *
     * @return gmt_create - 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置创建时间
     *
     * @param gmtCreate 创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取修改时间
     *
     * @return gmt_modified - 修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 设置修改时间
     *
     * @param gmtModified 修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}