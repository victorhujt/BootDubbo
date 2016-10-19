package com.xescm.ofc.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tbl_csc_store")
public class CscStore {
    /**
     * 主键Id
     */
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 货主编码
     */
    @Column(name = "customer_code")
    private String customerCode;

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
     * 创建日期
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建人id
     */
    @Column(name = "creator_id")
    private String creatorId;

    /**
     * 最后操作人
     */
    @Column(name = "last_operator")
    private String lastOperator;

    /**
     * 最后操作人id
     */
    @Column(name = "last_operator_id")
    private String lastOperatorId;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 1.已删除 0.未删除
     */
    private Integer yn;

    /**
     * 客户Id
     */
    @Column(name = "customer_id")
    private String customerId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取主键Id
     *
     * @return id - 主键Id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键Id
     *
     * @param id 主键Id
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
     * 获取货主编码
     *
     * @return customer_code - 货主编码
     */
    public String getCustomerCode() {
        return customerCode;
    }

    /**
     * 设置货主编码
     *
     * @param customerCode 货主编码
     */
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    /**
     * 获取店铺编码
     *
     * @return store_code - 店铺编码
     */
    public String getStoreCode() {
        return storeCode;
    }

    /**
     * 设置店铺编码
     *
     * @param storeCode 店铺编码
     */
    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    /**
     * 获取店铺名称
     *
     * @return store_name - 店铺名称
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * 设置店铺名称
     *
     * @param storeName 店铺名称
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * 获取平台类型
     *
     * @return platform_type - 平台类型
     */
    public String getPlatformType() {
        return platformType;
    }

    /**
     * 设置平台类型
     *
     * @param platformType 平台类型
     */
    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    /**
     * 获取创建日期
     *
     * @return create_time - 创建日期
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建日期
     *
     * @param createTime 创建日期
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取创建人
     *
     * @return create - 创建人
     */
    public String getCreate() {
        return creator;
    }

    /**
     * 设置创建人
     *
     * @param create 创建人
     */
    public void setCreate(String create) {
        this.creator = create;
    }

    /**
     * 获取创建人id
     *
     * @return creator_id - 创建人id
     */
    public String getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建人id
     *
     * @param creatorId 创建人id
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取最后操作人
     *
     * @return last_operator - 最后操作人
     */
    public String getLastOperator() {
        return lastOperator;
    }

    /**
     * 设置最后操作人
     *
     * @param lastOperator 最后操作人
     */
    public void setLastOperator(String lastOperator) {
        this.lastOperator = lastOperator;
    }

    /**
     * 获取最后操作人id
     *
     * @return last_operator_id - 最后操作人id
     */
    public String getLastOperatorId() {
        return lastOperatorId;
    }

    /**
     * 设置最后操作人id
     *
     * @param lastOperatorId 最后操作人id
     */
    public void setLastOperatorId(String lastOperatorId) {
        this.lastOperatorId = lastOperatorId;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取1.已删除 0.未删除
     *
     * @return yn - 1.已删除 0.未删除
     */
    public Integer getYn() {
        return yn;
    }

    /**
     * 设置1.已删除 0.未删除
     *
     * @param yn 1.已删除 0.未删除
     */
    public void setYn(Integer yn) {
        this.yn = yn;
    }

    /**
     * 获取客户Id
     *
     * @return customer_id - 客户Id
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * 设置客户Id
     *
     * @param customerId 客户Id
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "CscStore{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", customerCode='" + customerCode + '\'' +
                ", storeCode='" + storeCode + '\'' +
                ", storeName='" + storeName + '\'' +
                ", platformType='" + platformType + '\'' +
                ", createTime=" + createTime +
                ", creator='" + creator + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", lastOperator='" + lastOperator + '\'' +
                ", lastOperatorId='" + lastOperatorId + '\'' +
                ", updateTime=" + updateTime +
                ", yn=" + yn +
                ", customerId='" + customerId + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}