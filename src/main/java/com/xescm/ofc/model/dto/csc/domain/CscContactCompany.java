package com.xescm.ofc.model.dto.csc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tbl_csc_contact_company")
public class CscContactCompany implements Serializable {

    private static final long serialVersionUID = 5252045185601135617L;
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
     * 客户编码
     */
    @Column(name = "customer_code")
    private String customerCode;

    /**
     * 类型。1、企业公司；2、个人
     */
    private String type;

    /**
     * 联系人所属公司编码
     */
    @Column(name = "contact_company_code")
    private String contactCompanyCode;

    /**
     * 联系人所属公司
     */
    @Column(name = "contact_company_name")
    private String contactCompanyName;

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
     * 获取客户编码
     *
     * @return customer_code - 客户编码
     */
    public String getCustomerCode() {
        return customerCode;
    }

    /**
     * 设置客户编码
     *
     * @param customerCode 客户编码
     */
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    /**
     * 获取类型。1、企业公司；2、个人
     *
     * @return type - 类型。1、企业公司；2、个人
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型。1、企业公司；2、个人
     *
     * @param type 类型。1、企业公司；2、个人
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取联系人所属公司编码
     *
     * @return contact_company_code - 联系人所属公司编码
     */
    public String getContactCompanyCode() {
        return contactCompanyCode;
    }

    /**
     * 设置联系人所属公司编码
     *
     * @param contactCompanyCode 联系人所属公司编码
     */
    public void setContactCompanyCode(String contactCompanyCode) {
        this.contactCompanyCode = contactCompanyCode;
    }

    /**
     * 获取联系人所属公司
     *
     * @return contact_company_name - 联系人所属公司
     */
    public String getContactCompanyName() {
        return contactCompanyName;
    }

    /**
     * 设置联系人所属公司
     *
     * @param contactCompanyName 联系人所属公司
     */
    public void setContactCompanyName(String contactCompanyName) {
        this.contactCompanyName = contactCompanyName;
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

    @Override
    public String toString() {
        return "CscContactCompany{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", customerCode='" + customerCode + '\'' +
                ", type='" + type + '\'' +
                ", contactCompanyCode='" + contactCompanyCode + '\'' +
                ", contactCompanyName='" + contactCompanyName + '\'' +
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