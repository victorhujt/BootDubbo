package com.xescm.ofc.model.vo.ofc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xescm.ofc.edas.enums.LogSourceSysEnum;
import com.xescm.ofc.edas.enums.LogStatusEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;

@Data
public class OfcInterfaceReceiveLogVo {

    private String id;

    /**
     * 业务类型 {@link com.xescm.ofc.edas.enums.LogBusinessTypeEnum}
     */
    private String logBusinessType;

    /**
     * 来源系统 {@link LogSourceSysEnum}
     */
    private String logFromSys;

    /**
     * 接收系统 {@link LogSourceSysEnum}
     */
    private String logToSys = LogSourceSysEnum.OFC.getCode();

    /**
     * 相关单据号
     */
    private String refNo;

    /**
     * 日志类型 {@link com.xescm.ofc.edas.enums.LogInterfaceTypeEnum}
     */
    private String logType;

    /**
     * 接口数据
     */
    private String logData;

    /**
     * 状态 {@link LogStatusEnum}
     */
    private String logStatus;

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
     * 处理时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date processTime;

    /**
     * 是否删除：0-否；1-是；
     */
    @Column(name = "dr")
    private Integer dr;

    /**
     * 处理次数
     */
    @Column(name = "process_count")
    private Integer processCount;

    /**
     * 处理结果
     */
    @Column(name = "process_result")
    private String processResult;
}