package com.xescm.ofc.model.vo.ofc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xescm.ofc.edas.enums.LogBusinessTypeEnum;
import com.xescm.ofc.edas.enums.LogStatusEnum;
import com.xescm.ofc.edas.enums.TaskLogSourceEnum;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class OfcTaskInterfaceLogVo {

    private Long id;

    /**
     * 任务类型 {@link com.xescm.ofc.edas.enums.LogBusinessTypeEnum}
     */
    private String taskType;

    /**
     * 业务单号
     */
    private String refNo;

    /**
     * 任务来源 {@link com.xescm.ofc.edas.enums.TaskLogSourceEnum}
     */
    private String taskSource;

    /**
     * 来源ID
     */
    private String sourceLogId;

    /**
     * 任务数据
     */
    private String taskData;

    /**
     * 执行次数
     */
    private Integer taskExeCount;

    /**
     * 执行实例服务器IP
     */
    private String exeInstanceIp;

    /**
     * 执行状态： 1 –待处理 2 –处理中 3 –处理成功 4 –处理失败
     * {@link com.xescm.ofc.edas.enums.LogStatusEnum}
     */
    private String taskStatus;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date creationTime;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date beginDate;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endDate;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    /**
     * 执行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date exeTime;

    /**
     * 是否删除 0 - 否 ；1 是
     */
    private Integer dr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskType() {
        return LogBusinessTypeEnum.getLogBusinessTypeByCode(taskType);
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getTaskSource() {
        return TaskLogSourceEnum.getTaskLogSourceNameByCode(taskSource);
    }

    public void setTaskSource(String taskSource) {
        this.taskSource = taskSource;
    }

    public String getSourceLogId() {
        return sourceLogId;
    }

    public void setSourceLogId(String sourceLogId) {
        this.sourceLogId = sourceLogId;
    }

    public String getTaskData() {
        return taskData;
    }

    public void setTaskData(String taskData) {
        this.taskData = taskData;
    }

    public Integer getTaskExeCount() {
        return taskExeCount;
    }

    public void setTaskExeCount(Integer taskExeCount) {
        this.taskExeCount = taskExeCount;
    }

    public String getExeInstanceIp() {
        return exeInstanceIp;
    }

    public void setExeInstanceIp(String exeInstanceIp) {
        this.exeInstanceIp = exeInstanceIp;
    }

    public String getTaskStatus() {
        return LogStatusEnum.getLogStatusNameByInnerCode(taskStatus);
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getExeTime() {
        return exeTime;
    }

    public void setExeTime(Date exeTime) {
        this.exeTime = exeTime;
    }

    public Integer getDr() {
        return dr;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }
}