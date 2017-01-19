package com.xescm.ofc.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Data
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




}