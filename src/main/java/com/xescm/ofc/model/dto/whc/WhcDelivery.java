package com.xescm.ofc.model.dto.whc;



import java.util.Date;
import java.util.List;

/**
 * 出库单实体类
 * */
public class WhcDelivery {

    /** 主键ID */
    private String id;

    /** 出库单号 */
    private String whcBillno;

    /** 出入类型 */
    private String billType;

    /** 出库日期 */
    private Date outDate;

    /** 创建日期 */
    private Date createTime;

    /** 创建人 */
    private String creator;

    /** 操作人 */
    private String opetator;

    /** 操作日期 */
    private Date operatingTime;

    /** 出库单状态 */
    private String status;

    /** 仓库编码 */
    private String wareHouseCode;

    /** 仓库名称 */
    private String wareHouseName;

    /** 货主编码 */
    private String customerCode;

    /** 货主名称 */
    private String customerName;

    /** 预计出库时间 */
    private Date expectedArriveTime;

    /** 订单编号 */
    private String orderNo;

    /** WMS单据号 */
    private String wmsNo;

    /** 备注 */
    private String notes;

    /** 收货方编码 */
    private String consigneeCode;

    /** 收货方名称 */
    private String consigneeName;

    /** 收货方联系人 */
    private String consigneeContact;

    /** 收货方联系电话 */
    private String consigneeTel;

    /** 收货方省 */
    private String cProvince;

    /** 收货方市 */
    private String cCity;

    /** 收货方区县 */
    private String cDistrict;

    /** 收货方街道 */
    private String cStreet;

    /** 收货方地址 */
    private String consigneeAddr;

    /** 承运人编码 */
    private String carrierCode;

    /** 承运人名称 */
    private String carrierName;

    /** 车牌号码 */
    private String vehical;

    /** 司机名称 */
    private String driver;

    /** 司机联系电话 */
    private String driverTel;

    /** 仓储费用 */
    private String wareHouseExpense;

    /** 是否打印发票 */
    private String printInvoice;

    /** 发票抬头 */
    private String invoiceTitle;

    /** 发票内容 */
    private String invoiceContent;

    /** 是否代收 */
    private String collect;

    /** 代收金额 */
    private String collectAmount;

    /************************/

    /** 客户订单编号 */
    private String customerOrderNo;

    public String getCustomerOrderNo() {
        return customerOrderNo;
    }

    public void setCustomerOrderNo(String customerOrderNo) {
        this.customerOrderNo = customerOrderNo;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    /** 计划单编号 */
    private String planNo;

    /************************/

    private List<WhcDeliveryDetails> detailsList;

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

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
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

    public String getOpetator() {
        return opetator;
    }

    public void setOpetator(String opetator) {
        this.opetator = opetator;
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

    public String getConsigneeCode() {
        return consigneeCode;
    }

    public void setConsigneeCode(String consigneeCode) {
        this.consigneeCode = consigneeCode;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneeContact() {
        return consigneeContact;
    }

    public void setConsigneeContact(String consigneeContact) {
        this.consigneeContact = consigneeContact;
    }

    public String getConsigneeTel() {
        return consigneeTel;
    }

    public void setConsigneeTel(String consigneeTel) {
        this.consigneeTel = consigneeTel;
    }

    public String getcProvince() {
        return cProvince;
    }

    public void setcProvince(String cProvince) {
        this.cProvince = cProvince;
    }

    public String getcCity() {
        return cCity;
    }

    public void setcCity(String cCity) {
        this.cCity = cCity;
    }

    public String getcDistrict() {
        return cDistrict;
    }

    public void setcDistrict(String cDistrict) {
        this.cDistrict = cDistrict;
    }

    public String getcStreet() {
        return cStreet;
    }

    public void setcStreet(String cStreet) {
        this.cStreet = cStreet;
    }

    public String getConsigneeAddr() {
        return consigneeAddr;
    }

    public void setConsigneeAddr(String consigneeAddr) {
        this.consigneeAddr = consigneeAddr;
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

    public String getWareHouseExpense() {
        return wareHouseExpense;
    }

    public void setWareHouseExpense(String wareHouseExpense) {
        this.wareHouseExpense = wareHouseExpense;
    }

    public String getPrintInvoice() {
        return printInvoice;
    }

    public void setPrintInvoice(String printInvoice) {
        this.printInvoice = printInvoice;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public String getCollect() {
        return collect;
    }

    public void setCollect(String collect) {
        this.collect = collect;
    }

    public String getCollectAmount() {
        return collectAmount;
    }

    public void setCollectAmount(String collectAmount) {
        this.collectAmount = collectAmount;
    }

    public List<WhcDeliveryDetails> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<WhcDeliveryDetails> detailsList) {
        this.detailsList = detailsList;
    }


}