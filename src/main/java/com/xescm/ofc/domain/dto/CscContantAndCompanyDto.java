package com.xescm.ofc.domain.dto;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Date;

/**
 * 封装联系人信息以及联系人所属的公司
 * Created by gsfeng on 2016/10/18.
 */
@ApiModel("联系人信息以及联系人所在公司Dto")
public class CscContantAndCompanyDto implements Serializable {

    private static final long serialVersionUID = 2201253552440049762L;

    private String type;
    private String contactCompanyCode;
    private String contactCompanyName;

    private String contactCompanyId;//联系人所属公司id
    private String contactCode;//联系人id
    private String id;
    private String version;
    private String phone;
    private String fax;
    private String email;
    private String contactName;
    private String postCode;
    private String province;
    private String provinceName;
    private String city;
    private String cityName;
    private String area;
    private String areaName;
    private String street;
    private String address;
    private String contactJob;
    private String purpose;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContactCompanyCode() {
        return contactCompanyCode;
    }

    public void setContactCompanyCode(String contactCompanyCode) {
        this.contactCompanyCode = contactCompanyCode;
    }

    public String getContactCompanyName() {
        return contactCompanyName;
    }

    public void setContactCompanyName(String contactCompanyName) {
        this.contactCompanyName = contactCompanyName;
    }

    public String getContactCompanyId() {
        return contactCompanyId;
    }

    public void setContactCompanyId(String contactCompanyId) {
        this.contactCompanyId = contactCompanyId;
    }

    public String getContactCode() {
        return contactCode;
    }

    public void setContactCode(String contactCode) {
        this.contactCode = contactCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactJob() {
        return contactJob;
    }

    public void setContactJob(String contactJob) {
        this.contactJob = contactJob;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public String toString() {
        return "CscContantAndCompanyDto{" +
                "type='" + type + '\'' +
                ", contactCompanyCode='" + contactCompanyCode + '\'' +
                ", contactCompanyName='" + contactCompanyName + '\'' +
                ", contactCompanyId='" + contactCompanyId + '\'' +
                ", contactCode='" + contactCode + '\'' +
                ", id='" + id + '\'' +
                ", version='" + version + '\'' +
                ", phone='" + phone + '\'' +
                ", fax='" + fax + '\'' +
                ", email='" + email + '\'' +
                ", contactName='" + contactName + '\'' +
                ", postCode='" + postCode + '\'' +
                ", province='" + province + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", city='" + city + '\'' +
                ", cityName='" + cityName + '\'' +
                ", area='" + area + '\'' +
                ", areaName='" + areaName + '\'' +
                ", street='" + street + '\'' +
                ", address='" + address + '\'' +
                ", contactJob='" + contactJob + '\'' +
                ", purpose='" + purpose + '\'' +
                '}';
    }
}
