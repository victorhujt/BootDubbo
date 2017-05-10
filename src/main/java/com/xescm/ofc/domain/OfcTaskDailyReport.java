package com.xescm.ofc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "ofc_task_daily_report")
public class OfcTaskDailyReport {

    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    /**
     * 任务类型
     */
    @Column(name = "task_type")
    private String taskType;

    /**
     * 业务单号
     */
    @Column(name = "ref_no")
    private String refNo;

    /**
     * 客户编码
     */
    @Column(name = "cust_code")
    private String custCode;

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
    private String exeInstanceIp;

    /**
     * 执行状态： 1 –待处理 2 –处理中 3 –处理成功 4 –处理失败
     */
    @Column(name = "task_status")
    private Integer taskStatus = 1;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "creation_time")
    private Date creationTime = new Date();

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 执行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "exe_time")
    private Date exeTime;

    /**
     * 是否有效 0 无效 1 有效
     */
    private Integer yn = 1;

}