package com.xescm.ofc.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "ofc_traplan_source_status")
public class OfcTraplanSourceStatus {
    /**
     * 计划单编号
     */
    @Column(name = "planCode")
    private String plancode;

    /**
     * 订单编号
     */
    @Column(name = "orderCode")
    private String ordercode;

    /**
     * 资源分配状态
     */
    @Column(name = "resourceAllocationStatus")
    private String resourceallocationstatus;

    /**
     * 服务商编码
     */
    @Column(name = "serviceProviderCode")
    private String serviceprovidercode;

    /**
     * 服务商名称
     */
    @Column(name = "serviceProviderName")
    private String serviceprovidername;

    /**
     * 服务商联系人
     */
    @Column(name = "serviceProviderContact")
    private String serviceprovidercontact;

    /**
     * 服务商联系电话
     */
    @Column(name = "serviceProviderContactPhone")
    private String serviceprovidercontactphone;

    /**
     * 资源确认人员
     */
    @Column(name = "resourceConfirmation")
    private String resourceconfirmation;

    /**
     * 资源确认时间
     */
    @Column(name = "resourceConfirmationTime")
    private Date resourceconfirmationtime;

    /**
     * 车牌号
     */
    @Column(name = "licensePlateNumber")
    private String licenseplatenumber;

    /**
     * 司机姓名
     */
    @Column(name = "driverName")
    private String drivername;

    /**
     * 司机联系电话
     */
    @Column(name = "driverContactPhone")
    private String drivercontactphone;

    /**
     * 运输单号
     */
    @Column(name = "transportNumber")
    private String transportnumber;

    /**
     * 获取计划单编号
     *
     * @return planCode - 计划单编号
     */
    public String getPlancode() {
        return plancode;
    }

    /**
     * 设置计划单编号
     *
     * @param plancode 计划单编号
     */
    public void setPlancode(String plancode) {
        this.plancode = plancode;
    }

    /**
     * 获取订单编号
     *
     * @return orderCode - 订单编号
     */
    public String getOrdercode() {
        return ordercode;
    }

    /**
     * 设置订单编号
     *
     * @param ordercode 订单编号
     */
    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }

    /**
     * 获取资源分配状态
     *
     * @return resourceAllocationStatus - 资源分配状态
     */
    public String getResourceallocationstatus() {
        return resourceallocationstatus;
    }

    /**
     * 设置资源分配状态
     *
     * @param resourceallocationstatus 资源分配状态
     */
    public void setResourceallocationstatus(String resourceallocationstatus) {
        this.resourceallocationstatus = resourceallocationstatus;
    }

    /**
     * 获取服务商编码
     *
     * @return serviceProviderCode - 服务商编码
     */
    public String getServiceprovidercode() {
        return serviceprovidercode;
    }

    /**
     * 设置服务商编码
     *
     * @param serviceprovidercode 服务商编码
     */
    public void setServiceprovidercode(String serviceprovidercode) {
        this.serviceprovidercode = serviceprovidercode;
    }

    /**
     * 获取服务商名称
     *
     * @return serviceProviderName - 服务商名称
     */
    public String getServiceprovidername() {
        return serviceprovidername;
    }

    /**
     * 设置服务商名称
     *
     * @param serviceprovidername 服务商名称
     */
    public void setServiceprovidername(String serviceprovidername) {
        this.serviceprovidername = serviceprovidername;
    }

    /**
     * 获取服务商联系人
     *
     * @return serviceProviderContact - 服务商联系人
     */
    public String getServiceprovidercontact() {
        return serviceprovidercontact;
    }

    /**
     * 设置服务商联系人
     *
     * @param serviceprovidercontact 服务商联系人
     */
    public void setServiceprovidercontact(String serviceprovidercontact) {
        this.serviceprovidercontact = serviceprovidercontact;
    }

    /**
     * 获取服务商联系电话
     *
     * @return serviceProviderContactPhone - 服务商联系电话
     */
    public String getServiceprovidercontactphone() {
        return serviceprovidercontactphone;
    }

    /**
     * 设置服务商联系电话
     *
     * @param serviceprovidercontactphone 服务商联系电话
     */
    public void setServiceprovidercontactphone(String serviceprovidercontactphone) {
        this.serviceprovidercontactphone = serviceprovidercontactphone;
    }

    /**
     * 获取资源确认人员
     *
     * @return resourceConfirmation - 资源确认人员
     */
    public String getResourceconfirmation() {
        return resourceconfirmation;
    }

    /**
     * 设置资源确认人员
     *
     * @param resourceconfirmation 资源确认人员
     */
    public void setResourceconfirmation(String resourceconfirmation) {
        this.resourceconfirmation = resourceconfirmation;
    }

    /**
     * 获取资源确认时间
     *
     * @return resourceConfirmationTime - 资源确认时间
     */
    public Date getResourceconfirmationtime() {
        return resourceconfirmationtime;
    }

    /**
     * 设置资源确认时间
     *
     * @param resourceconfirmationtime 资源确认时间
     */
    public void setResourceconfirmationtime(Date resourceconfirmationtime) {
        this.resourceconfirmationtime = resourceconfirmationtime;
    }

    /**
     * 获取车牌号
     *
     * @return licensePlateNumber - 车牌号
     */
    public String getLicenseplatenumber() {
        return licenseplatenumber;
    }

    /**
     * 设置车牌号
     *
     * @param licenseplatenumber 车牌号
     */
    public void setLicenseplatenumber(String licenseplatenumber) {
        this.licenseplatenumber = licenseplatenumber;
    }

    /**
     * 获取司机姓名
     *
     * @return driverName - 司机姓名
     */
    public String getDrivername() {
        return drivername;
    }

    /**
     * 设置司机姓名
     *
     * @param drivername 司机姓名
     */
    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    /**
     * 获取司机联系电话
     *
     * @return driverContactPhone - 司机联系电话
     */
    public String getDrivercontactphone() {
        return drivercontactphone;
    }

    /**
     * 设置司机联系电话
     *
     * @param drivercontactphone 司机联系电话
     */
    public void setDrivercontactphone(String drivercontactphone) {
        this.drivercontactphone = drivercontactphone;
    }

    /**
     * 获取运输单号
     *
     * @return transportNumber - 运输单号
     */
    public String getTransportnumber() {
        return transportnumber;
    }

    /**
     * 设置运输单号
     *
     * @param transportnumber 运输单号
     */
    public void setTransportnumber(String transportnumber) {
        this.transportnumber = transportnumber;
    }
}