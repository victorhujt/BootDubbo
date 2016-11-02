package com.xescm.ofc.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "ofc_traplan_source_status")
public class OfcTraplanSourceStatus {
    /**
     * 计划单编号
     */
    @Column(name = "plan_code")
    private String planCode;

    /**
     * 订单编号
     */
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 资源分配状态
     */
    @Column(name = "resource_allocation_status")
    private String resourceAllocationStatus;

    /**
     * 服务商编码
     */
    @Column(name = "service_provider_code")
    private String serviceProviderCode;

    /**
     * 服务商名称
     */
    @Column(name = "service_provider_name")
    private String serviceProviderName;

    /**
     * 服务商联系人
     */
    @Column(name = "service_provider_contact")
    private String serviceProviderContact;

    /**
     * 服务商联系电话
     */
    @Column(name = "service_provider_contact_phone")
    private String serviceProviderContactPhone;

    /**
     * 资源确认人员
     */
    @Column(name = "resource_confirmation")
    private String resourceConfirmation;

    /**
     * 资源确认时间
     */
    @Column(name = "resource_confirmation_time")
    private Date resourceConfirmationTime;

    /**
     * 车牌号
     */
    @Column(name = "plate_number")
    private String plateNumber;

    /**
     * 司机姓名
     */
    @Column(name = "driver_name")
    private String driverName;

    /**
     * 司机联系电话
     */
    @Column(name = "contact_number")
    private String contactNumber;

    /**
     * 运输单号
     */
    @Column(name = "trans_code")
    private String transCode;

    /**
     * 获取计划单编号
     *
     * @return plan_code - 计划单编号
     */
    public String getPlanCode() {
        return planCode;
    }

    /**
     * 设置计划单编号
     *
     * @param planCode 计划单编号
     */
    public void setPlanCode(String planCode) {
        this.planCode = planCode;
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
     * 获取资源分配状态
     *
     * @return resource_allocation_status - 资源分配状态
     */
    public String getResourceAllocationStatus() {
        return resourceAllocationStatus;
    }

    /**
     * 设置资源分配状态
     *
     * @param resourceAllocationStatus 资源分配状态
     */
    public void setResourceAllocationStatus(String resourceAllocationStatus) {
        this.resourceAllocationStatus = resourceAllocationStatus;
    }

    /**
     * 获取服务商编码
     *
     * @return service_provider_code - 服务商编码
     */
    public String getServiceProviderCode() {
        return serviceProviderCode;
    }

    /**
     * 设置服务商编码
     *
     * @param serviceProviderCode 服务商编码
     */
    public void setServiceProviderCode(String serviceProviderCode) {
        this.serviceProviderCode = serviceProviderCode;
    }

    /**
     * 获取服务商名称
     *
     * @return service_provider_name - 服务商名称
     */
    public String getServiceProviderName() {
        return serviceProviderName;
    }

    /**
     * 设置服务商名称
     *
     * @param serviceProviderName 服务商名称
     */
    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    /**
     * 获取服务商联系人
     *
     * @return service_provider_contact - 服务商联系人
     */
    public String getServiceProviderContact() {
        return serviceProviderContact;
    }

    /**
     * 设置服务商联系人
     *
     * @param serviceProviderContact 服务商联系人
     */
    public void setServiceProviderContact(String serviceProviderContact) {
        this.serviceProviderContact = serviceProviderContact;
    }

    /**
     * 获取服务商联系电话
     *
     * @return service_provider_contact_phone - 服务商联系电话
     */
    public String getServiceProviderContactPhone() {
        return serviceProviderContactPhone;
    }

    /**
     * 设置服务商联系电话
     *
     * @param serviceProviderContactPhone 服务商联系电话
     */
    public void setServiceProviderContactPhone(String serviceProviderContactPhone) {
        this.serviceProviderContactPhone = serviceProviderContactPhone;
    }

    /**
     * 获取资源确认人员
     *
     * @return resource_confirmation - 资源确认人员
     */
    public String getResourceConfirmation() {
        return resourceConfirmation;
    }

    /**
     * 设置资源确认人员
     *
     * @param resourceConfirmation 资源确认人员
     */
    public void setResourceConfirmation(String resourceConfirmation) {
        this.resourceConfirmation = resourceConfirmation;
    }

    /**
     * 获取资源确认时间
     *
     * @return resource_confirmation_time - 资源确认时间
     */
    public Date getResourceConfirmationTime() {
        return resourceConfirmationTime;
    }

    /**
     * 设置资源确认时间
     *
     * @param resourceConfirmationTime 资源确认时间
     */
    public void setResourceConfirmationTime(Date resourceConfirmationTime) {
        this.resourceConfirmationTime = resourceConfirmationTime;
    }

    /**
     * 获取车牌号
     *
     * @return plate_number - 车牌号
     */
    public String getPlateNumber() {
        return plateNumber;
    }

    /**
     * 设置车牌号
     *
     * @param plateNumber 车牌号
     */
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    /**
     * 获取司机姓名
     *
     * @return driver_name - 司机姓名
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * 设置司机姓名
     *
     * @param driverName 司机姓名
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    /**
     * 获取司机联系电话
     *
     * @return contact_number - 司机联系电话
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * 设置司机联系电话
     *
     * @param contactNumber 司机联系电话
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * 获取运输单号
     *
     * @return trans_code - 运输单号
     */
    public String getTransCode() {
        return transCode;
    }

    /**
     * 设置运输单号
     *
     * @param transCode 运输单号
     */
    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }
}