package com.xescm.ofc.model.dto.csc;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Dzy on 2016/11/2.
 */
@Table(name = "tbl_csc_goods_type")
public class CscGoodsType implements Serializable {

    private static final long serialVersionUID = 550401642376237675L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    /**
     * 货品类别
     */
    @Column(name = "goods_type_name")
    private String goodsTypeName;

    /**
     * 客户ID
     */
    @Column(name = "customer_id")
    private String customerId;

    /**
     * 货品类型编码
     */
    @Column(name="code")
    private String code;

    /**
     * 关联id
     */
    @Column(name="pid")
    private String pid;

    /**
     * 流水号
     */
    @Column(name="serial_no")
    private String serialNo;


    /**
     * 版本号
     */
    private Long version;

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
     * 删除(1-已删除；0-未删除)
     */
    private Integer yn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getLastOperator() {
        return lastOperator;
    }

    public void setLastOperator(String lastOperator) {
        this.lastOperator = lastOperator;
    }

    public String getLastOperatorId() {
        return lastOperatorId;
    }

    public void setLastOperatorId(String lastOperatorId) {
        this.lastOperatorId = lastOperatorId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    @Override
    public String toString() {
        return "CscGoodsType{" +
                "id='" + id + '\'' +
                ", goodsTypeName='" + goodsTypeName + '\'' +
                ", version=" + version +
                ", creator='" + creator + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", createdTime=" + createdTime +
                ", lastOperator='" + lastOperator + '\'' +
                ", lastOperatorId='" + lastOperatorId + '\'' +
                ", updateTime=" + updateTime +
                ", yn=" + yn +
                '}';
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}
