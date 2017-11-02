package com.xescm.ofc.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Data
@Table(name = "ofc_storage_template")
public class OfcStorageTemplate implements Serializable{
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    /**
     * 模板序号
     */
    @Column(name = "index_num")
    private int indexNum;

    /**
     * 模板编码
     */
    @Column(name = "template_code")
    private String templateCode;

    /**
     * 模板类型
     */
    @Column(name = "template_type")
    private String templateType;

    /**
     * 模板名称
     */
    @Column(name = "template_name")
    private String templateName;

    /**
     * 客户编码
     */
    @Column(name = "cust_code")
    private String custCode;

    /**
     * 客户名称
     */
    @Column(name = "cust_name")
    private String custName;

    /**
     * 平台标准列编码
     */
    @Column(name = "standard_col_code")
    private String standardColCode;

    /**
     * 平台标准列名
     */
    @Column(name = "standard_col_name")
    private String standardColName;

    /**
     * 映射模板名称
     */
    @Column(name = "reflect_col_name")
    private String reflectColName;

    /**
     * 列默认值
     */
    @Column(name = "col_default_val")
    private String colDefaultVal;

    /**
     * 创建时间
     */
    @Column(name = "creat_time")
    private Date creatTime;

    /**
     * 创建人编码
     */
    private String creator;

    /**
     * 创建人名称
     */
    @Column(name = "creator_name")
    private String creatorName;

    /**
     * 操作时间
     */
    @Column(name = "oper_time")
    private Date operTime;

    /**
     * 操作人编码
     */
    private String operator;

    /**
     * 操作人名称
     */
    @Column(name = "operator_name")
    private String operatorName;

    /**
     * 服务产品编码
     */
    private String serviceProductCode;

    /**
     * 服务产品名称
     */
    private String serviceProductName;
}