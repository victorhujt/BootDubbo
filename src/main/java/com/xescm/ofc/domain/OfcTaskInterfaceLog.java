package com.xescm.ofc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xescm.ofc.edas.enums.LogStatusEnum;
import com.xescm.ofc.utils.NetworkUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "ofc_task_interface_log")
public class OfcTaskInterfaceLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 任务类型 {@link com.xescm.ofc.edas.enums.LogBusinessTypeEnum}
     */
    @Column(name = "task_type")
    private String taskType;

    /**
     * 业务单号
     */
    @Column(name = "ref_no")
    private String refNo;

    /**
     * 任务来源 {@link com.xescm.ofc.edas.enums.TaskLogSourceEnum}
     */
    @Column(name = "task_source")
    private String taskSource;

    /**
     * 来源ID
     */
    @Column(name = "source_log_id")
    private String sourceLogId;

    /**
     * 任务数据
     */
    @Column(name = "task_data")
    private String taskData;

    /**
     * 执行次数
     */
    @Column(name = "task_exe_count")
    private Integer taskExeCount = 0;

    /**
     * 执行实例服务器IP
     */
    @Column(name = "exe_instance_ip")
    private String exeInstanceIp = NetworkUtil.getLocalIP();

    /**
     * 执行状态： 1 –待处理 2 –处理中 3 –处理成功 4 –处理失败
     * {@link com.xescm.ofc.edas.enums.LogStatusEnum}
     */
    @Column(name = "task_status")
    private Integer taskStatus = LogStatusEnum.PENDING.getInnerCode();

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "creation_time")
    private Date creationTime = new Date();

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 执行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "exe_time")
    private Date exeTime;

    /**
     * 是否删除 0 - 否 ；1 是
     */
    private Integer dr = 0;

}