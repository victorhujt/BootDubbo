package com.xescm.ofc.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 供应商信息DTO
 * Created by Dzy on 2016/10/15.
 */
@ApiModel("供应商信息DTO")
public class CscSupplierInfoDto implements Serializable {

    private static final long serialVersionUID = 1468153499881167236L;
    /**
     * 主键
     */
    @ApiModelProperty()
    private String id;

    /**
     * 用户中心的组织ID
     */
    @ApiModelProperty()
    private String groupId;

    /**
     * 客户编码
     */
    private String customerCode;

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 供应商分类
     */
    private String supplierClassification;

    /**
     * 供应商备注
     */
    private String supplierRemark;

    /**
     * 客户ID
     */
    private String customerId;

    /**
     * 联系人所属供应商ID
     */
    private String supplierId;

    /**
     * 联系人编码
     */
    private String contactCode;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 传真
     */
    private String fax;

    /**
     * Email
     */
    private String email;

    /**
     * 邮编
     */
    private String postCode;

    /**
     * 地址
     */
    private String address;

    /**
     * 省
     */
    private String province;

    private String provinceName;

    /**
     * 市
     */
    private String city;

    private String cityName;

    /**
     * 区
     */
    private String area;

    private String areaName;

    /**
     * 乡镇街道
     */
    private String street;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierClassification() {
        return supplierClassification;
    }

    public void setSupplierClassification(String supplierClassification) {
        this.supplierClassification = supplierClassification;
    }

    public String getSupplierRemark() {
        return supplierRemark;
    }

    public void setSupplierRemark(String supplierRemark) {
        this.supplierRemark = supplierRemark;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getContactCode() {
        return contactCode;
    }

    public void setContactCode(String contactCode) {
        this.contactCode = contactCode;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return "CscSupplierInfoDto{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", customerCode='" + customerCode + '\'' +
                ", supplierCode='" + supplierCode + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", supplierClassification='" + supplierClassification + '\'' +
                ", supplierRemark='" + supplierRemark + '\'' +
                ", customerId='" + customerId + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", contactCode='" + contactCode + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", fax='" + fax + '\'' +
                ", email='" + email + '\'' +
                ", postCode='" + postCode + '\'' +
                ", address='" + address + '\'' +
                ", province='" + province + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", city='" + city + '\'' +
                ", cityName='" + cityName + '\'' +
                ", area='" + area + '\'' +
                ", areaName='" + areaName + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}
