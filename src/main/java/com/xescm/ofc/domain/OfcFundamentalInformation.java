package com.xescm.ofc.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "ofc_fundamental_information")
public class OfcFundamentalInformation {

    /**
     * 订单编号
     */
    @Id
    @Column(name = "order_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderCode;

    /**
     * 订单日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "order_time")
    private Date orderTime;

    /**
     * 货主编码
     */
    @Column(name = "cust_code")
    private String custCode;

    /**
     * 货主名称
     */
    @Column(name = "cust_name")
    private String custName;

    /**
     * 二级客户编码
     */
    @Column(name = "sec_cust_code")
    private String secCustCode;

    /**
     * 二级客户名称
     */
    @Column(name = "sec_cust_name")
    private String secCustName;

    /**
     * 订单类型
     */
    @Column(name = "order_type")
    private String orderType;

    /**
     * 业务类型
     */
    @Column(name = "business_type")
    private String businessType;

    /**
     * 客户订单编号
     */
    @Column(name = "cust_order_code")
    private String custOrderCode;

    /**
     * 备注
     */
    private String notes;

    /**
     * 店铺编码
     */
    @Column(name = "store_code")
    private String storeCode;

    /**
     * 店铺名称
     */
    @Column(name = "store_name")
    private String storeName;

    /**
     * 平台类型
     */
    @Column(name = "platform_type")
    private String platformType;

    /**
     * 订单来源
     */
    @Column(name = "order_source")
    private String orderSource;

    /**
     * 服务产品类型
     */
    @Column(name = "product_type")
    private String productType;

    /**
     * 服务产品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 完成日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "finished_time")
    private Date finishedTime;

    /**
     * 作废标记
     */
    @Column(name = "abolish_mark")
    private Integer abolishMark;

    /**
     * 作废时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "abolish_time")
    private Date abolishTime;

    /**
     * 作废人员
     */
    private String abolisher;

    /**
     * 创建日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "creation_time")
    private Date creationTime;

    /**
     * 创建人员
     */
    private String creator;

    /**
     * 操作人员
     */
    private String operator;


    /**
     * 作废人员名称
     */
    private String abolisherName;
    /**
     * 创建人员名称
     */
    private String creatorName;

    /**
     * 操作人员名称
     */
    private String operatorName;
    /**
     * 校验客户订单编号专用字段
     */
    @Transient
    private String selfCustOrderCode;

   /* private String saleOrganization;
    private String productGroup;
    private String saleDepartment;
    private String saleGroup;
    private String saleDepartment_desc;
    private String saleGroupDesc;
*/

    /**
     * 操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "oper_time")
    private Date operTime;

    public String getSelfCustOrderCode() {
        return selfCustOrderCode;
    }

    public void setSelfCustOrderCode(String selfCustOrderCode) {
        this.selfCustOrderCode = selfCustOrderCode;
    }

    public String getAbolisherName() {
        return abolisherName;
    }

    public void setAbolisherName(String abolisherName) {
        this.abolisherName = abolisherName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    /**
     * 获取订单编号
     *
     * @return order_code - 订单编号
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * 设置订单编号
     *
     * @param orderCode 订单编号
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    /**
     * 获取订单日期
     *
     * @return order_time - 订单日期
     */
    public Date getOrderTime() {
        return orderTime;
    }

    /**
     * 设置订单日期
     *
     * @param orderTime 订单日期
     */
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * 获取货主编码
     *
     * @return cust_code - 货主编码
     */
    public String getCustCode() {
        return custCode;
    }

    /**
     * 设置货主编码
     *
     * @param custCode 货主编码
     */
    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    /**
     * 获取货主名称
     *
     * @return cust_name - 货主名称
     */
    public String getCustName() {
        return custName;
    }

    /**
     * 设置货主名称
     *
     * @param custName 货主名称
     */
    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
     * 获取二级客户编码
     *
     * @return sec_cust_code - 二级客户编码
     */
    public String getSecCustCode() {
        return secCustCode;
    }

    /**
     * 设置二级客户编码
     *
     * @param secCustCode 二级客户编码
     */
    public void setSecCustCode(String secCustCode) {
        this.secCustCode = secCustCode;
    }

    /**
     * 获取二级客户名称
     *
     * @return sec_cust_name - 二级客户名称
     */
    public String getSecCustName() {
        return secCustName;
    }

    /**
     * 设置二级客户名称
     *
     * @param secCustName 二级客户名称
     */
    public void setSecCustName(String secCustName) {
        this.secCustName = secCustName;
    }

    /**
     * 获取订单类型
     *
     * @return order_type - 订单类型
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * 设置订单类型
     *
     * @param orderType 订单类型
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * 获取业务类型
     *
     * @return business_type - 业务类型
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     * 设置业务类型
     *
     * @param businessType 业务类型
     */
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    /**
     * 获取客户订单编号
     *
     * @return cust_order_code - 客户订单编号
     */
    public String getCustOrderCode() {
        return custOrderCode;
    }

    /**
     * 设置客户订单编号
     *
     * @param custOrderCode 客户订单编号
     */
    public void setCustOrderCode(String custOrderCode) {
        this.custOrderCode = custOrderCode;
    }

    /**
     * 获取备注
     *
     * @return notes - 备注
     */
    public String getNotes() {
        return notes;
    }

    /**
     * 设置备注
     *
     * @param notes 备注
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * 获取客户编码
     *
     * @return store_code - 客户编码
     */
    public String getStoreCode() {
        return storeCode;
    }

    /**
     * 设置客户编码
     *
     * @param storeCode 客户编码
     */
    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    /**
     * 获取客户名称
     *
     * @return store_name - 客户名称
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * 设置客户名称
     *
     * @param storeName 客户名称
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * 获取平台类型
     *
     * @return platform_type - 平台类型
     */
    public String getPlatformType() {
        return platformType;
    }

    /**
     * 设置平台类型
     *
     * @param platformType 平台类型
     */
    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    /**
     * 获取订单来源
     *
     * @return order_source - 订单来源
     */
    public String getOrderSource() {
        return orderSource;
    }

    /**
     * 设置订单来源
     *
     * @param orderSource 订单来源
     */
    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    /**
     * 获取服务产品类型
     *
     * @return product_type - 服务产品类型
     */
    public String getProductType() {
        return productType;
    }

    /**
     * 设置服务产品类型
     *
     * @param productType 服务产品类型
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * 获取服务产品名称
     *
     * @return product_name - 服务产品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置服务产品名称
     *
     * @param productName 服务产品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 获取完成日期
     *
     * @return finished_time - 完成日期
     */
    public Date getFinishedTime() {
        return finishedTime;
    }

    /**
     * 设置完成日期
     *
     * @param finishedTime 完成日期
     */
    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    /**
     * 获取作废标记
     *
     * @return abolish_mark - 作废标记
     */
    public Integer getAbolishMark() {
        return abolishMark;
    }

    /**
     * 设置作废标记
     *
     * @param abolishMark 作废标记
     */
    public void setAbolishMark(Integer abolishMark) {
        this.abolishMark = abolishMark;
    }

    /**
     * 获取作废时间
     *
     * @return abolish_time - 作废时间
     */
    public Date getAbolishTime() {
        return abolishTime;
    }

    /**
     * 设置作废时间
     *
     * @param abolishTime 作废时间
     */
    public void setAbolishTime(Date abolishTime) {
        this.abolishTime = abolishTime;
    }

    /**
     * 获取作废人员
     *
     * @return abolisher - 作废人员
     */
    public String getAbolisher() {
        return abolisher;
    }

    /**
     * 设置作废人员
     *
     * @param abolisher 作废人员
     */
    public void setAbolisher(String abolisher) {
        this.abolisher = abolisher;
    }

    /**
     * 获取创建日期
     *
     * @return creation_time - 创建日期
     */
    public Date getCreationTime() {
        return creationTime;
    }

    /**
     * 设置创建日期
     *
     * @param creationTime 创建日期
     */
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * 获取创建人员
     *
     * @return creator - 创建人员
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人员
     *
     * @param creator 创建人员
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 获取操作人员
     *
     * @return operator - 操作人员
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置操作人员
     *
     * @param operator 操作人员
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 获取操作时间
     *
     * @return operTime - 操作时间
     */
    public Date getOperTime() {
        return operTime;
    }

    /**
     * 设置操作时间
     *
     * @param opertime 操作时间
     */
    public void setOperTime(Date opertime) {
        this.operTime = opertime;
    }

}