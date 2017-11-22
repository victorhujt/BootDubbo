package com.xescm.ofc.model.vo.ofc;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
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

}