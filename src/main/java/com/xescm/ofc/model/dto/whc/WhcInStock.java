package com.xescm.ofc.model.dto.whc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 入库单实体类
 * */
public class WhcInStock {

    /** 主键ID */
    private String id;

    /** 入库单号 */
    private String whcBillno;

    /** 订单编号 */
    private String orderNo;

    /** 单据类型 */
    private String billType;

    /** 入库日期 */
    private Date storageDate;

    /** 创建时间 */
    private Date createTime;

    /** 创建人 */
    private String creator;

    /** 操作人 */
    private String operator;

    /** 操作时间 */
    private Date operatingTime;

    /** 入库单状态 */
    private String status;

    /** 仓库编码 */
    private String wareHouseCode;

    /** 仓库名称 */
    private String wareHouseName;

    /** 货主编码 */
    private String customerCode;

    /** 货主名称 */
    private String customerName;

    /** 预计到仓时间 */
    private Date expectedArriveTime;

    /** wms单号 */
    private String wmsNo;

    /** 备注 */
    private String notes;

    /** 供应商编码 */
    private String supplierCode;

    /** 供应商名称 */
    private String supplierName;

    /** 供应商联系人 */
    private String supplierContact;

    /** 供应商地址 */
    private String supplierAddr;

    /** 承运人编码 */
    private String carrierCode;

    /** 承运人名称 */
    private String carrierName;

    /** 车编号吗 */
    private String vehical;

    /** 司机名称 */
    private String driver;

    /** 司机电话 */
    private String driverTel;

    /** 仓储费用 */
    private BigDecimal wareHouseExpense;

    /**********************  新增字段  ************************************/

    /** 计划单编号 */
    private String planNo;

    /** 客户订单编号 */
    private String customerOrderNo;

    /************************************************************************************/

    private List<WhcInStockDetails> detailsList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWhcBillno() {
        return whcBillno;
    }

    public void setWhcBillno(String whcBillno) {
        this.whcBillno = whcBillno;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public Date getStorageDate() {
        return storageDate;
    }

    public void setStorageDate(Date storageDate) {
        this.storageDate = storageDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperatingTime() {
        return operatingTime;
    }

    public void setOperatingTime(Date operatingTime) {
        this.operatingTime = operatingTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWareHouseCode() {
        return wareHouseCode;
    }

    public void setWareHouseCode(String wareHouseCode) {
        this.wareHouseCode = wareHouseCode;
    }

    public String getWareHouseName() {
        return wareHouseName;
    }

    public void setWareHouseName(String wareHouseName) {
        this.wareHouseName = wareHouseName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getExpectedArriveTime() {
        return expectedArriveTime;
    }

    public void setExpectedArriveTime(Date expectedArriveTime) {
        this.expectedArriveTime = expectedArriveTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getWmsNo() {
        return wmsNo;
    }

    public void setWmsNo(String wmsNo) {
        this.wmsNo = wmsNo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact;
    }

    public String getSupplierAddr() {
        return supplierAddr;
    }

    public void setSupplierAddr(String supplierAddr) {
        this.supplierAddr = supplierAddr;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getVehical() {
        return vehical;
    }

    public void setVehical(String vehical) {
        this.vehical = vehical;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDriverTel() {
        return driverTel;
    }

    public void setDriverTel(String driverTel) {
        this.driverTel = driverTel;
    }

    public BigDecimal getWareHouseExpense() {
        return wareHouseExpense;
    }

    public void setWareHouseExpense(BigDecimal wareHouseExpense) {
        this.wareHouseExpense = wareHouseExpense;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getCustomerOrderNo() {
        return customerOrderNo;
    }

    public void setCustomerOrderNo(String customerOrderNo) {
        this.customerOrderNo = customerOrderNo;
    }

    public List<WhcInStockDetails> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<WhcInStockDetails> detailsList) {
        this.detailsList = detailsList;
    }
}