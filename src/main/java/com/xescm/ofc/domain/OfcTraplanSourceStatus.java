package com.xescm.ofc.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Data
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


}