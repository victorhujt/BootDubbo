package com.xescm.ofc.model.dto.csc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tbl_csc_contact")
public class CscContact implements Serializable {
    private static final long serialVersionUID = 5343469432572636524L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 所属公司编码
     */
    @Column(name = "contact_company_id")
    private String contactCompanyId;

    /**
     * 联系人编码
     */
    @Column(name = "contact_code")
    private String contactCode;

    /**
     * 联系人姓名
     */
    @Column(name = "contact_name")
    private String contactName;

    /**
     * 联系电话
     */
    private String phone;

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
    @Column(name = "post_code")
    private String postCode;

    /**
     * 省code
     */
    private String province;

    /**
     * 省名称
     */
    @Column(name = "province_name")
    private String provinceName;

    /**
     * 市code
     */
    private String city;

    /**
     * 市名称
     */
    @Column(name = "city_name")
    private String cityName;

    /**
     * 区code
     */
    private String area;

    /**
     * 区名称
     */
    @Column(name = "area_name")
    private String areaName;

    /**
     * 乡镇街道
     */
    private String street;
    /**
     * 乡镇街道名称
     */
    @Column(name = "street_name")
    private String streetName;

    /**
     * 地址
     */
    private String address;

    /**
     * 详细地址
     */
    @Column(name = "detail_address")
    private String detailAddress;

    /**
     * 联系人职务
     */
    @Column(name = "contact_job")
    private String contactJob;

    /**
     * 用途。1、收货方；2、发货方；3、收发货方;4、无
     */
    private String purpose;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建人ID
     */
    @Column(name = "creator_id")
    private String creatorId;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

    /**
     * 最近操作人
     */
    @Column(name = "last_operator")
    private String lastOperator;

    /**
     * 最后操作人ID
     */
    @Column(name = "last_operator_id")
    private String lastOperatorId;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 删除（1 - 已删除；0未删除）
     */
    private Integer yn;

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取版本号
     *
     * @return version - 版本号
     */
    public Long getVersion() {
        return version;
    }

    /**
     * 设置版本号
     *
     * @param version 版本号
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * 获取所属公司编码
     *
     * @return contactCompanyId - 所属公司编码
     */
    public String getContactCompanyId() {
        return contactCompanyId;
    }

    /**
     * 设置所属公司编码
     *
     * @param contactCompanyId 所属公司编码
     */
    public void setContactCompanyId(String contactCompanyId) {
        this.contactCompanyId = contactCompanyId;
    }

    /**
     * 获取联系人编码
     *
     * @return contact_code - 联系人编码
     */
    public String getContactCode() {
        return contactCode;
    }

    /**
     * 设置联系人编码
     *
     * @param contactCode 联系人编码
     */
    public void setContactCode(String contactCode) {
        this.contactCode = contactCode;
    }

    /**
     * 获取联系人姓名
     *
     * @return contact_name - 联系人姓名
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * 设置联系人姓名
     *
     * @param contactName 联系人姓名
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * 获取联系电话
     *
     * @return phone - 联系电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置联系电话
     *
     * @param phone 联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取传真
     *
     * @return fax - 传真
     */
    public String getFax() {
        return fax;
    }

    /**
     * 设置传真
     *
     * @param fax 传真
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * 获取Email
     *
     * @return email - Email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置Email
     *
     * @param email Email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取邮编
     *
     * @return post_code - 邮编
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * 设置邮编
     *
     * @param postCode 邮编
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * 获取省code
     *
     * @return province - 省code
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省code
     *
     * @param province 省code
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取省名称
     *
     * @return province_name - 省名称
     */
    public String getProvinceName() {
        return provinceName;
    }

    /**
     * 设置省名称
     *
     * @param provinceName 省名称
     */
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    /**
     * 获取市code
     *
     * @return city - 市code
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置市code
     *
     * @param city 市code
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取市名称
     *
     * @return city_name - 市名称
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * 设置市名称
     *
     * @param cityName 市名称
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * 获取区code
     *
     * @return area - 区code
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置区code
     *
     * @param area 区code
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * 获取区名称
     *
     * @return area_name - 区名称
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * 设置区名称
     *
     * @param areaName 区名称
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * 获取乡镇街道
     *
     * @return street - 乡镇街道
     */
    public String getStreet() {
        return street;
    }

    /**
     * 设置乡镇街道
     *
     * @param street 乡镇街道
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * 获取地址
     *
     * @return address - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取联系人职务
     *
     * @return contact_job - 联系人职务
     */
    public String getContactJob() {
        return contactJob;
    }

    /**
     * 设置联系人职务
     *
     * @param contactJob 联系人职务
     */
    public void setContactJob(String contactJob) {
        this.contactJob = contactJob;
    }

    /**
     * 获取用途。1、收货方；2、发货方；3、收发货方
     *
     * @return purpose - 用途。1、收货方；2、发货方；3、收发货方
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * 设置用途。1、收货方；2、发货方；3、收发货方
     *
     * @param purpose 用途。1、收货方；2、发货方；3、收发货方
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    /**
     * 获取创建人
     *
     * @return creator - 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人
     *
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 获取创建人ID
     *
     * @return creator_id - 创建人ID
     */
    public String getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建人ID
     *
     * @param creatorId 创建人ID
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取创建时间
     *
     * @return created_time - 创建时间
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 设置创建时间
     *
     * @param createdTime 创建时间
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 获取最近操作人
     *
     * @return last_operator - 最近操作人
     */
    public String getLastOperator() {
        return lastOperator;
    }

    /**
     * 设置最近操作人
     *
     * @param lastOperator 最近操作人
     */
    public void setLastOperator(String lastOperator) {
        this.lastOperator = lastOperator;
    }

    /**
     * 获取最后操作人ID
     *
     * @return last_operator_id - 最后操作人ID
     */
    public String getLastOperatorId() {
        return lastOperatorId;
    }

    /**
     * 设置最后操作人ID
     *
     * @param lastOperatorId 最后操作人ID
     */
    public void setLastOperatorId(String lastOperatorId) {
        this.lastOperatorId = lastOperatorId;
    }

    /**
     * 获取 更新时间
     *
     * @return update_time -  更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置 更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取删除（1 - 已删除；0未删除）
     *
     * @return yn - 删除（1 - 已删除；0未删除）
     */
    public Integer getYn() {
        return yn;
    }

    /**
     * 设置删除（1 - 已删除；0未删除）
     *
     * @param yn 删除（1 - 已删除；0未删除）
     */
    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    @Override
    public String toString() {
        return "CscContact{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", contactCompanyId='" + contactCompanyId + '\'' +
                ", contactCode='" + contactCode + '\'' +
                ", contactName='" + contactName + '\'' +
                ", phone='" + phone + '\'' +
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
                ", address='" + address + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", contactJob='" + contactJob + '\'' +
                ", purpose='" + purpose + '\'' +
                ", creator='" + creator + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", createdTime=" + createdTime +
                ", lastOperator='" + lastOperator + '\'' +
                ", lastOperatorId='" + lastOperatorId + '\'' +
                ", updateTime=" + updateTime +
                ", yn=" + yn +
                '}';
    }
}