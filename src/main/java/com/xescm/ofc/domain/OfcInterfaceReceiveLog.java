package com.xescm.ofc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xescm.ofc.edas.enums.LogSourceSysEnum;
import com.xescm.ofc.edas.enums.LogStatusEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "ofc_interface_receive_log")
public class OfcInterfaceReceiveLog {

    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    /**
     * 业务类型 {@link com.xescm.ofc.edas.enums.LogBusinessTypeEnum}
     */
    @Column(name = "log_business_type")
    private String logBusinessType;

    /**
     * 来源系统 {@link com.xescm.ofc.edas.enums.LogSourceSysEnum}
     */
    @Column(name = "log_from_sys")
    private String logFromSys;

    /**
     * 接收系统 {@link com.xescm.ofc.edas.enums.LogSourceSysEnum}
     */
    @Column(name = "log_to_sys")
    private String logToSys = LogSourceSysEnum.OFC.getCode();

    /**
     * 相关单据号
     */
    @Column(name = "ref_no")
    private String refNo;

    /**
     * 日志类型 {@link com.xescm.ofc.edas.enums.LogInterfaceTypeEnum}
     */
    @Column(name = "log_type")
    private String logType;

    /**
     * 接口数据
     */
    @Column(name = "log_data")
    private String logData;

    /**
     * 状态 {@link com.xescm.ofc.edas.enums.LogStatusEnum}
     */
    @Column(name = "log_status")
    private Integer logStatus = LogStatusEnum.PENDING.getInnerCode();

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "creation_time")
    private Date creationTime = new Date();

    /**
     * 处理时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "process_time")
    private Date processTime;

    /**
     * 是否删除：0-否；1-是；
     */
    @Column(name = "dr")
    private Integer dr = 0;

    /**
     * 处理次数
     */
    @Column(name = "process_count")
    private Integer processCount = 0;

    /**
     * 处理结果
     */
    @Column(name = "process_result")
    private String processResult;
}