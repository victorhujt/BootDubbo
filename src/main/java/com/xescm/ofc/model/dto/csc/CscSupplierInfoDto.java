package com.xescm.ofc.model.dto.csc;

import com.xescm.ofc.model.dto.base.AuthDto;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * 供应商信息DTO
 * Created by Dzy on 2016/10/15.
 */
@ApiModel("供应商信息DTO")
public class CscSupplierInfoDto extends AuthDto implements Serializable {

    private static final long serialVersionUID = 5459176940946689162L;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 客户ID
     */
    private String customerId;

    /**
     * 客户编码
     */
    private String customerCode;

    /**
     * 供应商ID
     */
    private String supplierId;

    /**
     * 供应商流水号
     */
    private String supplierSerialNo;

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 所属行业
     */
    private String supplierIndustry;

    /**
     * 供应产品
     */
    private String supplierProduct;

    /**
     * 供应商备注
     */
    private String supplierRemark;

    /**
     * 联系人主键
     */
    private String contactId;

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
     * 部门职位
     */
    private String departmentPosition;

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

    private String streetName;

    /**
     * 地址
     */
    private String address;

    /**
     * 完整地址
     */
    private String completeAddress;

    /**
     * 自身供应商编码
     */
    private String selfCode;

    /**
     * 自身供应商名称
     */
    private String selfName;

    /**
     * 自身联系人电话
     */
    private String selfPhone;


    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String getCustomerId() {
        return customerId;
    }

    @Override
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String getCustomerCode() {
        return customerCode;
    }

    @Override
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierSerialNo() {
        return supplierSerialNo;
    }

    public void setSupplierSerialNo(String supplierSerialNo) {
        this.supplierSerialNo = supplierSerialNo;
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

    public String getSupplierIndustry() {
        return supplierIndustry;
    }

    public void setSupplierIndustry(String supplierIndustry) {
        this.supplierIndustry = supplierIndustry;
    }

    public String getSupplierProduct() {
        return supplierProduct;
    }

    public void setSupplierProduct(String supplierProduct) {
        this.supplierProduct = supplierProduct;
    }

    public String getSupplierRemark() {
        return supplierRemark;
    }

    public void setSupplierRemark(String supplierRemark) {
        this.supplierRemark = supplierRemark;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
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

    public String getDepartmentPosition() {
        return departmentPosition;
    }

    public void setDepartmentPosition(String departmentPosition) {
        this.departmentPosition = departmentPosition;
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

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompleteAddress() {
        return completeAddress;
    }

    public void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }

    public String getSelfCode() {
        return selfCode;
    }

    public void setSelfCode(String selfCode) {
        this.selfCode = selfCode;
    }

    public String getSelfName() {
        return selfName;
    }

    public void setSelfName(String selfName) {
        this.selfName = selfName;
    }

    public String getSelfPhone() {
        return selfPhone;
    }

    public void setSelfPhone(String selfPhone) {
        this.selfPhone = selfPhone;
    }

    @Override
    public String toString() {
        return "CscSupplierInfoDto{" +
                "version=" + version +
                ", customerId='" + customerId + '\'' +
                ", customerCode='" + customerCode + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", supplierSerialNo='" + supplierSerialNo + '\'' +
                ", supplierCode='" + supplierCode + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", supplierIndustry='" + supplierIndustry + '\'' +
                ", supplierProduct='" + supplierProduct + '\'' +
                ", supplierRemark='" + supplierRemark + '\'' +
                ", contactId='" + contactId + '\'' +
                ", contactCode='" + contactCode + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", departmentPosition='" + departmentPosition + '\'' +
                ", fax='" + fax + '\'' +
                ", email='" + email + '\'' +
                ", postCode='" + postCode + '\'' +
                ", province='" + province + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", city='" + city + '\'' +
                ", cityName='" + cityName + '\'' +
                ", area='" + area + '\'' +
                ", areaName='" + areaName + '\'' +
                ", street='" + street + '\'' +
                ", streetName='" + streetName + '\'' +
                ", address='" + address + '\'' +
                ", completeAddress='" + completeAddress + '\'' +
                ", selfCode='" + selfCode + '\'' +
                ", selfName='" + selfName + '\'' +
                ", selfPhone='" + selfPhone + '\'' +
                '}';
    }
}
