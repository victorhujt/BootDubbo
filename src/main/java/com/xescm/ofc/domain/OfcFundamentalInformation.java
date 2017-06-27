package com.xescm.ofc.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "ofc_fundamental_information")
public class OfcFundamentalInformation {

    /**
     * 订单编号
     */
    @Id
    @Column(name = "order_code")
    private String orderCode;


    @Column(name = "order_batch_number")
    private String orderBatchNumber;

    /**
     * 订单日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "order_time")
    private Date orderTime;

    /**
     * 货主编码
     */
    @Column(name = "cust_code")
    private String custCode;

    /**
     * 货主名称
     */
    @Column(name = "cust_name")
    private String custName;

    /**
     * 二级客户编码
     */
    @Column(name = "sec_cust_code")
    private String secCustCode;

    /**
     * 二级客户名称
     */
    @Column(name = "sec_cust_name")
    private String secCustName;

    /**
     * 订单类型
     */
    @Column(name = "order_type")
    private String orderType;

    /**
     * 业务类型
     */
    @Column(name = "business_type")
    private String businessType;

    /**
     * 客户订单编号
     */
    @Column(name = "cust_order_code")
    private String custOrderCode;

    /**
     * 备注
     */
    private String notes;

    /**
     * 店铺编码
     */
    @Column(name = "store_code")
    private String storeCode;

    /**
     * 店铺名称
     */
    @Column(name = "store_name")
    private String storeName;

    /**
     * 平台类型
     */
    @Column(name = "platform_type")
    private String platformType;

    /**
     * 订单来源
     */
    @Column(name = "order_source")
    private String orderSource;

    /**
     * 服务产品类型
     */
    @Column(name = "product_type")
    private String productType;

    /**
     * 服务产品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 完成日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "finished_time")
    private Date finishedTime;

    /**
     * 作废标记
     */
    @Column(name = "abolish_mark")
    private Integer abolishMark;

    /**
     * 作废时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "abolish_time")
    private Date abolishTime;

    /**
     * 作废人员
     */
    private String abolisher;

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
     * 作废人员名称
     */
    private String abolisherName;
    /**
     * 创建人员名称
     */
    private String creatorName;

    /**
     * 操作人员名称
     */
    private String operatorName;
    /**
     * 校验客户订单编号专用字段
     */
    @Transient
    private String selfCustOrderCode;

    /**
     * 销售组织(众品SAP专用)
     */
    private String saleOrganization;
    /**
     * 产品组(众品SAP专用)
     */
    private String productGroup;
    /**
     * 销售部门(众品SAP专用)
     */
    private String saleDepartment;
    /**
     * 销售组(众品SAP专用)
     */
    private String saleGroup;
    /**
     * 销售部门描述(众品SAP专用)
     */
    private String saleDepartmentDesc;
    /**
     * 销售组描述(众品SAP专用)
     */
    private String saleGroupDesc;

    /**
     * 操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "oper_time")
    private Date operTime;

    /**
     * 开单员
     */
    private String merchandiser;

    /**
     * 运输类型
     */
    @Column(name = "transport_type")
    private String transportType;

    /**
     * 基地编码
     */
    @Column(name = "base_code")
    private String baseCode;

    /**
     * 基地名称
     */
    @Column(name = "base_name")
    private String baseName;

    /**
     * 大区编码
     */
    @Column(name = "area_code")
    private String areaCode;

    /**
     * 大区名称
     */
    @Column(name = "area_name")
    private String areaName;
    /**
     * 删除标志位 0或者空表示未删除  1:表示已经删除
     */
//    @Column(name = "is_delete")
//    private String isDelete;
//
//    public String getIsDelete() {
//        return isDelete;
//    }
//
//    public void setIsDelete(String isDelete) {
//        this.isDelete = isDelete;
//    }
    /**
     * 订单的异常状态  0或者空为正常状态 1:异常状态
      */
    @Column(name = "is_exception")
    private String isException;

    public String getIsException() {
        return isException;
    }

    public void setIsException(String isException) {
        this.isException = isException;
    }

    /**
     * 异常原因
     */
    @Column(name ="exception_reason")
    private String exceptionReason;


    /**
     * 发货拨次号
     */
    @Column(name = "consignment_batch_number")
    private String consignmentBatchNumber;

    /**
     * 是否需要发送签收短信 1:是 0:否
     * @param signedSms
     */
    @Column(name = "signed_sms")
    private String signedSms;


    public void setExceptionReason(String exceptionReason) {
        this.exceptionReason = exceptionReason;
    }

    public String getSaleOrganization() {
        return saleOrganization;
    }

    public void setSaleOrganization(String saleOrganization) {
        this.saleOrganization = saleOrganization;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getSaleDepartment() {
        return saleDepartment;
    }

    public void setSaleDepartment(String saleDepartment) {
        this.saleDepartment = saleDepartment;
    }

    public String getSaleGroup() {
        return saleGroup;
    }

    public void setSaleGroup(String saleGroup) {
        this.saleGroup = saleGroup;
    }

    public String getSaleDepartmentDesc() {
        return saleDepartmentDesc;
    }

    public void setSaleDepartmentDesc(String saleDepartmentDesc) {
        this.saleDepartmentDesc = saleDepartmentDesc;
    }

    public String getSaleGroupDesc() {
        return saleGroupDesc;
    }

    public void setSaleGroupDesc(String saleGroupDesc) {
        this.saleGroupDesc = saleGroupDesc;
    }

}