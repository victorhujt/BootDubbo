package com.xescm.ofc.domain;

import java.util.Date;

/**
 * Created by victor on 2016/11/28.
 */
public class OfcSiloprogramInfoVo {




    /**
     * 计划单编号
     */
    private String planCode;

    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 订单批次号
     */
    private String orderBatchNumber;

    /**
     * 计划序号
     */
    private String programSerialNumber;

    /**
     * 类型
     */
    private String businessType;

    /**
     * 单据类型
     */
    private String documentType;

    /**
     * 日期
     */
    private Date orderTime;

    /**
     * 货主编码
     */
    private String custCode;

    /**
     * 仓库编码
     */
    private String warehouseCode;

    /**
     * 仓库名称
     */
    private String warehouseName;

    /**
     * 出库发货时间
     */
    private Date estimatedTimeOfDelivery;

    /**
     * 预计到达时间
     */
    private Date arriveTime;

    /**
     * 装货地
     */
    private String loadingPlace;

    /**
     * 卸货地
     */
    private String unloadingPlace;

    /**
     * 交货地
     */
    private String deliveryPlace;

    /**
     * 收货月台
     */
    private String eceivingPlatform;

    /**
     * 总货值
     */
    private String theTotalValueOf;

    /**
     * 供应商编码
     */
    private String supportCode;

    /**
     * 供应商名称
     */
    private String supportName;

    /**
     * 备注
     */
    private String notes;

    /**
     * 收货方编码
     */
    private String consigneeCode;

    /**
     * 收货方名称
     */
    private String consigneeName;

    /**
     * 收货方联系人
     */
    private String consigneeContact;

    /**
     * 收货方联系电话
     */
    private String consigneeContactPhone;

    /**
     * 收货方传真
     */
    private String consigneeFax;

    /**
     * 收货方Email
     */
    private String consigneeEmail;

    /**
     * 收货方邮编
     */
    private String consigneePostCode;

    /**
     * 收货方省
     */
    private String consigneeProvince;

    /**
     * 收货方市
     */
    private String consigneeCity;

    /**
     * 收货方区县
     */
    private String consigneeDistrictAndCounty;

    /**
     * 收货方乡镇街道
     */
    private String consigneeTownshipStreets;

    /**
     * 收货方地址
     */
    private String consigneeAddress;

    /**
     * 是否打印发票
     */
    private String printInvoice;

    /**
     * 支付方式
     */
    private String buyerPaymentMethod;

    /**
     * 订单金额
     */
    private String orderAmount;

    /**
     * 是否货到付款
     */
    private String collectFlag;

    /**
     * 是否保价
     */
    private String insure;

    /**
     * 保价金额
     */
    private String insureValue;

    /**
     * 仓储作业单号
     */
    private String warehouseNumber;

    /**
     * 服务费用
     */
    private String serviceCharge;

    /**
     * 创建时间
     */
    private Date creationTime;

    /**
     * 创建人员
     */
    private String createPersonnel;

    /**
     * 作废人员
     */
    private String voidPersonnel;

    /**
     * 作废时间
     */
    private Date voidTime;

    /**
     * 获取计划单编号
     *
     * @return plan_code - 计划单编号
     */
    public String getPlanCode() {
        return planCode;
    }

    /**
     * 设置计划单编号
     *
     * @param planCode 计划单编号
     */
    public void setPlanCode(String planCode) {
        this.planCode = planCode;
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
     * 获取订单批次号
     *
     * @return order_batch_number - 订单批次号
     */
    public String getOrderBatchNumber() {
        return orderBatchNumber;
    }

    /**
     * 设置订单批次号
     *
     * @param orderBatchNumber 订单批次号
     */
    public void setOrderBatchNumber(String orderBatchNumber) {
        this.orderBatchNumber = orderBatchNumber;
    }

    /**
     * 获取计划序号
     *
     * @return program_serial_number - 计划序号
     */
    public String getProgramSerialNumber() {
        return programSerialNumber;
    }

    /**
     * 设置计划序号
     *
     * @param programSerialNumber 计划序号
     */
    public void setProgramSerialNumber(String programSerialNumber) {
        this.programSerialNumber = programSerialNumber;
    }

    /**
     * 获取类型
     *
     * @return business_type - 类型
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     * 设置类型
     *
     * @param businessType 类型
     */
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    /**
     * 获取单据类型
     *
     * @return document_type - 单据类型
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * 设置单据类型
     *
     * @param documentType 单据类型
     */
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    /**
     * 获取日期
     *
     * @return order_time - 日期
     */
    public Date getOrderTime() {
        return orderTime;
    }

    /**
     * 设置日期
     *
     * @param orderTime 日期
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
     * 获取仓库编码
     *
     * @return warehouse_code - 仓库编码
     */
    public String getWarehouseCode() {
        return warehouseCode;
    }

    /**
     * 设置仓库编码
     *
     * @param warehouseCode 仓库编码
     */
    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    /**
     * 获取仓库名称
     *
     * @return warehouse_name - 仓库名称
     */
    public String getWarehouseName() {
        return warehouseName;
    }

    /**
     * 设置仓库名称
     *
     * @param warehouseName 仓库名称
     */
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    /**
     * 获取出库发货时间
     *
     * @return estimated_time_of_delivery - 出库发货时间
     */
    public Date getEstimatedTimeOfDelivery() {
        return estimatedTimeOfDelivery;
    }

    /**
     * 设置出库发货时间
     *
     * @param estimatedTimeOfDelivery 出库发货时间
     */
    public void setEstimatedTimeOfDelivery(Date estimatedTimeOfDelivery) {
        this.estimatedTimeOfDelivery = estimatedTimeOfDelivery;
    }

    /**
     * 获取预计到达时间
     *
     * @return arrive_time - 预计到达时间
     */
    public Date getArriveTime() {
        return arriveTime;
    }

    /**
     * 设置预计到达时间
     *
     * @param arriveTime 预计到达时间
     */
    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    /**
     * 获取装货地
     *
     * @return loading_place - 装货地
     */
    public String getLoadingPlace() {
        return loadingPlace;
    }

    /**
     * 设置装货地
     *
     * @param loadingPlace 装货地
     */
    public void setLoadingPlace(String loadingPlace) {
        this.loadingPlace = loadingPlace;
    }

    /**
     * 获取卸货地
     *
     * @return unloading_place - 卸货地
     */
    public String getUnloadingPlace() {
        return unloadingPlace;
    }

    /**
     * 设置卸货地
     *
     * @param unloadingPlace 卸货地
     */
    public void setUnloadingPlace(String unloadingPlace) {
        this.unloadingPlace = unloadingPlace;
    }

    /**
     * 获取交货地
     *
     * @return delivery_place - 交货地
     */
    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    /**
     * 设置交货地
     *
     * @param deliveryPlace 交货地
     */
    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    /**
     * 获取收货月台
     *
     * @return eceiving_platform - 收货月台
     */
    public String getEceivingPlatform() {
        return eceivingPlatform;
    }

    /**
     * 设置收货月台
     *
     * @param eceivingPlatform 收货月台
     */
    public void setEceivingPlatform(String eceivingPlatform) {
        this.eceivingPlatform = eceivingPlatform;
    }

    /**
     * 获取总货值
     *
     * @return the_total_value_of - 总货值
     */
    public String getTheTotalValueOf() {
        return theTotalValueOf;
    }

    /**
     * 设置总货值
     *
     * @param theTotalValueOf 总货值
     */
    public void setTheTotalValueOf(String theTotalValueOf) {
        this.theTotalValueOf = theTotalValueOf;
    }

    /**
     * 获取供应商编码
     *
     * @return support_code - 供应商编码
     */
    public String getSupportCode() {
        return supportCode;
    }

    /**
     * 设置供应商编码
     *
     * @param supportCode 供应商编码
     */
    public void setSupportCode(String supportCode) {
        this.supportCode = supportCode;
    }

    /**
     * 获取供应商名称
     *
     * @return support_name - 供应商名称
     */
    public String getSupportName() {
        return supportName;
    }

    /**
     * 设置供应商名称
     *
     * @param supportName 供应商名称
     */
    public void setSupportName(String supportName) {
        this.supportName = supportName;
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
     * 获取收货方编码
     *
     * @return consignee_code - 收货方编码
     */
    public String getConsigneeCode() {
        return consigneeCode;
    }

    /**
     * 设置收货方编码
     *
     * @param consigneeCode 收货方编码
     */
    public void setConsigneeCode(String consigneeCode) {
        this.consigneeCode = consigneeCode;
    }

    /**
     * 获取收货方名称
     *
     * @return consignee_name - 收货方名称
     */
    public String getConsigneeName() {
        return consigneeName;
    }

    /**
     * 设置收货方名称
     *
     * @param consigneeName 收货方名称
     */
    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    /**
     * 获取收货方联系人
     *
     * @return consignee_contact - 收货方联系人
     */
    public String getConsigneeContact() {
        return consigneeContact;
    }

    /**
     * 设置收货方联系人
     *
     * @param consigneeContact 收货方联系人
     */
    public void setConsigneeContact(String consigneeContact) {
        this.consigneeContact = consigneeContact;
    }

    /**
     * 获取收货方联系电话
     *
     * @return consignee_contact_phone - 收货方联系电话
     */
    public String getConsigneeContactPhone() {
        return consigneeContactPhone;
    }

    /**
     * 设置收货方联系电话
     *
     * @param consigneeContactPhone 收货方联系电话
     */
    public void setConsigneeContactPhone(String consigneeContactPhone) {
        this.consigneeContactPhone = consigneeContactPhone;
    }

    /**
     * 获取收货方传真
     *
     * @return consignee_fax - 收货方传真
     */
    public String getConsigneeFax() {
        return consigneeFax;
    }

    /**
     * 设置收货方传真
     *
     * @param consigneeFax 收货方传真
     */
    public void setConsigneeFax(String consigneeFax) {
        this.consigneeFax = consigneeFax;
    }

    /**
     * 获取收货方Email
     *
     * @return consignee_email - 收货方Email
     */
    public String getConsigneeEmail() {
        return consigneeEmail;
    }

    /**
     * 设置收货方Email
     *
     * @param consigneeEmail 收货方Email
     */
    public void setConsigneeEmail(String consigneeEmail) {
        this.consigneeEmail = consigneeEmail;
    }

    /**
     * 获取收货方邮编
     *
     * @return consignee_post_code - 收货方邮编
     */
    public String getConsigneePostCode() {
        return consigneePostCode;
    }

    /**
     * 设置收货方邮编
     *
     * @param consigneePostCode 收货方邮编
     */
    public void setConsigneePostCode(String consigneePostCode) {
        this.consigneePostCode = consigneePostCode;
    }

    /**
     * 获取收货方省
     *
     * @return consignee_province - 收货方省
     */
    public String getConsigneeProvince() {
        return consigneeProvince;
    }

    /**
     * 设置收货方省
     *
     * @param consigneeProvince 收货方省
     */
    public void setConsigneeProvince(String consigneeProvince) {
        this.consigneeProvince = consigneeProvince;
    }

    /**
     * 获取收货方市
     *
     * @return consignee_city - 收货方市
     */
    public String getConsigneeCity() {
        return consigneeCity;
    }

    /**
     * 设置收货方市
     *
     * @param consigneeCity 收货方市
     */
    public void setConsigneeCity(String consigneeCity) {
        this.consigneeCity = consigneeCity;
    }

    /**
     * 获取收货方区县
     *
     * @return consignee_district_and_county - 收货方区县
     */
    public String getConsigneeDistrictAndCounty() {
        return consigneeDistrictAndCounty;
    }

    /**
     * 设置收货方区县
     *
     * @param consigneeDistrictAndCounty 收货方区县
     */
    public void setConsigneeDistrictAndCounty(String consigneeDistrictAndCounty) {
        this.consigneeDistrictAndCounty = consigneeDistrictAndCounty;
    }

    /**
     * 获取收货方乡镇街道
     *
     * @return consignee_township_streets - 收货方乡镇街道
     */
    public String getConsigneeTownshipStreets() {
        return consigneeTownshipStreets;
    }

    /**
     * 设置收货方乡镇街道
     *
     * @param consigneeTownshipStreets 收货方乡镇街道
     */
    public void setConsigneeTownshipStreets(String consigneeTownshipStreets) {
        this.consigneeTownshipStreets = consigneeTownshipStreets;
    }

    /**
     * 获取收货方地址
     *
     * @return consignee_address - 收货方地址
     */
    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    /**
     * 设置收货方地址
     *
     * @param consigneeAddress 收货方地址
     */
    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    /**
     * 获取是否打印发票
     *
     * @return print_invoice - 是否打印发票
     */
    public String getPrintInvoice() {
        return printInvoice;
    }

    /**
     * 设置是否打印发票
     *
     * @param printInvoice 是否打印发票
     */
    public void setPrintInvoice(String printInvoice) {
        this.printInvoice = printInvoice;
    }

    /**
     * 获取支付方式
     *
     * @return buyer_payment_method - 支付方式
     */
    public String getBuyerPaymentMethod() {
        return buyerPaymentMethod;
    }

    /**
     * 设置支付方式
     *
     * @param buyerPaymentMethod 支付方式
     */
    public void setBuyerPaymentMethod(String buyerPaymentMethod) {
        this.buyerPaymentMethod = buyerPaymentMethod;
    }

    /**
     * 获取订单金额
     *
     * @return order_amount - 订单金额
     */
    public String getOrderAmount() {
        return orderAmount;
    }

    /**
     * 设置订单金额
     *
     * @param orderAmount 订单金额
     */
    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    /**
     * 获取是否货到付款
     *
     * @return collect_flag - 是否货到付款
     */
    public String getCollectFlag() {
        return collectFlag;
    }

    /**
     * 设置是否货到付款
     *
     * @param collectFlag 是否货到付款
     */
    public void setCollectFlag(String collectFlag) {
        this.collectFlag = collectFlag;
    }

    /**
     * 获取是否保价
     *
     * @return insure - 是否保价
     */
    public String getInsure() {
        return insure;
    }

    /**
     * 设置是否保价
     *
     * @param insure 是否保价
     */
    public void setInsure(String insure) {
        this.insure = insure;
    }

    /**
     * 获取保价金额
     *
     * @return insure_value - 保价金额
     */
    public String getInsureValue() {
        return insureValue;
    }

    /**
     * 设置保价金额
     *
     * @param insureValue 保价金额
     */
    public void setInsureValue(String insureValue) {
        this.insureValue = insureValue;
    }

    /**
     * 获取仓储作业单号
     *
     * @return warehouse_number - 仓储作业单号
     */
    public String getWarehouseNumber() {
        return warehouseNumber;
    }

    /**
     * 设置仓储作业单号
     *
     * @param warehouseNumber 仓储作业单号
     */
    public void setWarehouseNumber(String warehouseNumber) {
        this.warehouseNumber = warehouseNumber;
    }

    /**
     * 获取服务费用
     *
     * @return service_charge - 服务费用
     */
    public String getServiceCharge() {
        return serviceCharge;
    }

    /**
     * 设置服务费用
     *
     * @param serviceCharge 服务费用
     */
    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    /**
     * 获取创建时间
     *
     * @return creation_time - 创建时间
     */
    public Date getCreationTime() {
        return creationTime;
    }

    /**
     * 设置创建时间
     *
     * @param creationTime 创建时间
     */
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * 获取创建人员
     *
     * @return create_personnel - 创建人员
     */
    public String getCreatePersonnel() {
        return createPersonnel;
    }

    /**
     * 设置创建人员
     *
     * @param createPersonnel 创建人员
     */
    public void setCreatePersonnel(String createPersonnel) {
        this.createPersonnel = createPersonnel;
    }

    /**
     * 获取作废人员
     *
     * @return void_personnel - 作废人员
     */
    public String getVoidPersonnel() {
        return voidPersonnel;
    }

    /**
     * 设置作废人员
     *
     * @param voidPersonnel 作废人员
     */
    public void setVoidPersonnel(String voidPersonnel) {
        this.voidPersonnel = voidPersonnel;
    }

    /**
     * 获取作废时间
     *
     * @return void_time - 作废时间
     */
    public Date getVoidTime() {
        return voidTime;
    }

    /**
     * 设置作废时间
     *
     * @param voidTime 作废时间
     */
    public void setVoidTime(Date voidTime) {
        this.voidTime = voidTime;
    }

    private String driverName;

    private String plateNumber;

    private String contactNumber;

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getDriverName() {

        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    private String transCode;

}
