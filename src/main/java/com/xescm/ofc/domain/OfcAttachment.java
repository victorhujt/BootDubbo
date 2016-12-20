package com.xescm.ofc.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "ofc_attachment")
public class OfcAttachment {


    /**
     * 附件流水号
     */
    @Column(name = "serial_no")
    private String serialNo;

    /**
     * 上传附件的相关业务流水号
     */
    @Column(name = "ref_no")
    private String refNo;

    /**
     * 附件名称
     */
    private String name;

    /**
     * 附件存储相对路径
     */
    private String path;

    /**
     * 附件类型
     */
    private String type;

    /**
     * 附件格式
     */
    private String format;

    /**
     * 备注
     */
    private String description;

    /**
     * 版本号
     */
    private Long version;

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
     * 创建日期
     */
    @Column(name = "created_time")
    private Date createdTime;

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
     * 更新日期
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 删除标识(1-已删除；0-未删除)
     */
    private Integer yn;

    private String picParam;

    public String getPicParam() {
        return picParam;
    }

    public void setPicParam(String picParam) {
        this.picParam = picParam;
    }

    /**
     * 获取附件流水号
     *
     * @return serial_no - 附件流水号
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * 设置附件流水号
     *
     * @param serialNo 附件流水号
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * 获取上传附件的相关业务流水号
     *
     * @return ref_no - 上传附件的相关业务流水号
     */
    public String getRefNo() {
        return refNo;
    }

    /**
     * 设置上传附件的相关业务流水号
     *
     * @param refNo 上传附件的相关业务流水号
     */
    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    /**
     * 获取附件名称
     *
     * @return name - 附件名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置附件名称
     *
     * @param name 附件名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取附件存储相对路径
     *
     * @return path - 附件存储相对路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置附件存储相对路径
     *
     * @param path 附件存储相对路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取附件类型
     *
     * @return type - 附件类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置附件类型
     *
     * @param type 附件类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取附件格式
     *
     * @return format - 附件格式
     */
    public String getFormat() {
        return format;
    }

    /**
     * 设置附件格式
     *
     * @param format 附件格式
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * 获取备注
     *
     * @return description - 备注
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置备注
     *
     * @param description 备注
     */
    public void setDescription(String description) {
        this.description = description;
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
     * 获取创建日期
     *
     * @return created_time - 创建日期
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 设置创建日期
     *
     * @param createdTime 创建日期
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
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
     * 获取更新日期
     *
     * @return update_time - 更新日期
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新日期
     *
     * @param updateTime 更新日期
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取删除标识(1-已删除；0-未删除)
     *
     * @return yn - 删除标识(1-已删除；0-未删除)
     */
    public Integer getYn() {
        return yn;
    }

    /**
     * 设置删除标识(1-已删除；0-未删除)
     *
     * @param yn 删除标识(1-已删除；0-未删除)
     */
    public void setYn(Integer yn) {
        this.yn = yn;
    }
}