package com.xescm.ofc.domain;

import javax.persistence.*;
import java.util.Date;

@Table(name = "ofc_storage_template")
public class OfcStorageTemplate {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取模板类型
     *
     * @return template_type - 模板类型
     */
    public String getTemplateType() {
        return templateType;
    }

    /**
     * 设置模板类型
     *
     * @param templateType 模板类型
     */
    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    /**
     * 获取模板名称
     *
     * @return template_name - 模板名称
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * 设置模板名称
     *
     * @param templateName 模板名称
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * 获取客户编码
     *
     * @return cust_code - 客户编码
     */
    public String getCustCode() {
        return custCode;
    }

    /**
     * 设置客户编码
     *
     * @param custCode 客户编码
     */
    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    /**
     * 获取客户名称
     *
     * @return cust_name - 客户名称
     */
    public String getCustName() {
        return custName;
    }

    /**
     * 设置客户名称
     *
     * @param custName 客户名称
     */
    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
     * 获取平台标准列编码
     *
     * @return standard_col_code - 平台标准列编码
     */
    public String getStandardColCode() {
        return standardColCode;
    }

    /**
     * 设置平台标准列编码
     *
     * @param standardColCode 平台标准列编码
     */
    public void setStandardColCode(String standardColCode) {
        this.standardColCode = standardColCode;
    }

    /**
     * 获取平台标准列名
     *
     * @return standard_col_name - 平台标准列名
     */
    public String getStandardColName() {
        return standardColName;
    }

    /**
     * 设置平台标准列名
     *
     * @param standardColName 平台标准列名
     */
    public void setStandardColName(String standardColName) {
        this.standardColName = standardColName;
    }

    /**
     * 获取映射模板名称
     *
     * @return reflect_col_name - 映射模板名称
     */
    public String getReflectColName() {
        return reflectColName;
    }

    /**
     * 设置映射模板名称
     *
     * @param reflectColName 映射模板名称
     */
    public void setReflectColName(String reflectColName) {
        this.reflectColName = reflectColName;
    }

    /**
     * 获取列默认值
     *
     * @return col_default_val - 列默认值
     */
    public String getColDefaultVal() {
        return colDefaultVal;
    }

    /**
     * 设置列默认值
     *
     * @param colDefaultVal 列默认值
     */
    public void setColDefaultVal(String colDefaultVal) {
        this.colDefaultVal = colDefaultVal;
    }

    /**
     * 获取创建时间
     *
     * @return creat_time - 创建时间
     */
    public Date getCreatTime() {
        return creatTime;
    }

    /**
     * 设置创建时间
     *
     * @param creatTime 创建时间
     */
    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    /**
     * 获取创建人编码
     *
     * @return creator - 创建人编码
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人编码
     *
     * @param creator 创建人编码
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 获取创建人名称
     *
     * @return creator_name - 创建人名称
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * 设置创建人名称
     *
     * @param creatorName 创建人名称
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    /**
     * 获取操作时间
     *
     * @return oper_time - 操作时间
     */
    public Date getOperTime() {
        return operTime;
    }

    /**
     * 设置操作时间
     *
     * @param operTime 操作时间
     */
    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    /**
     * 获取操作人编码
     *
     * @return operator - 操作人编码
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置操作人编码
     *
     * @param operator 操作人编码
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 获取操作人名称
     *
     * @return operator_name - 操作人名称
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * 设置操作人名称
     *
     * @param operatorName 操作人名称
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}