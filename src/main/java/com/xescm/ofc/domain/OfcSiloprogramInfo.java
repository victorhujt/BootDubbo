package com.xescm.ofc.domain;

import javax.persistence.*;
import java.util.Date;

@Table(name = "ofc_siloprogram_info")
public class OfcSiloprogramInfo {
    /**
     * 计划单编号
     */
    @Id
    @Column(name = "planCode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String plancode;

    /**
     * 订单编号
     */
    @Column(name = "orderCode")
    private String ordercode;

    /**
     * 订单批次号
     */
    @Column(name = "orderBatchNumber")
    private String orderbatchnumber;

    /**
     * 计划序号
     */
    @Column(name = "programSerialNumber")
    private String programserialnumber;

    /**
     * 类型
     */
    @Column(name = "serviceType")
    private String servicetype;

    /**
     * 单据类型
     */
    @Column(name = "documentType")
    private String documenttype;

    /**
     * 日期
     */
    private Date dates;

    /**
     * 货主编码
     */
    @Column(name = "custCode")
    private String custcode;

    /**
     * 仓库编码
     */
    @Column(name = "warehouseCode")
    private String warehousecode;

    /**
     * 仓库名称
     */
    @Column(name = "warehouseName")
    private String warehousename;

    /**
     * 出库发货时间
     */
    @Column(name = "estimatedTimeOfDelivery")
    private Date estimatedtimeofdelivery;

    /**
     * 预计到达时间
     */
    @Column(name = "estimatedTimeOfArrival")
    private Date estimatedtimeofarrival;

    /**
     * 装货地
     */
    @Column(name = "loadingPlace")
    private String loadingplace;

    /**
     * 卸货地
     */
    @Column(name = "unloadingPlace")
    private String unloadingplace;

    /**
     * 交货地
     */
    @Column(name = "deliveryPlace")
    private String deliveryplace;

    /**
     * 收货月台
     */
    @Column(name = "eceivingPlatform")
    private String eceivingplatform;

    /**
     * 总货值
     */
    @Column(name = "theTotalValueOf")
    private String thetotalvalueof;

    /**
     * 供应商编码
     */
    @Column(name = "supplierCode")
    private String suppliercode;

    /**
     * 供应商名称
     */
    @Column(name = "supplierName")
    private String suppliername;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 收货方编码
     */
    @Column(name = "consigneeCode")
    private String consigneecode;

    /**
     * 收货方名称
     */
    @Column(name = "consigneeName")
    private String consigneename;

    /**
     * 收货方联系人
     */
    @Column(name = "consigneeContact")
    private String consigneecontact;

    /**
     * 收货方联系电话
     */
    @Column(name = "consigneeContactPhone")
    private String consigneecontactphone;

    /**
     * 收货方传真
     */
    @Column(name = "consigneeFax")
    private String consigneefax;

    /**
     * 收货方Email
     */
    @Column(name = "consigneeEmail")
    private String consigneeemail;

    /**
     * 收货方邮编
     */
    @Column(name = "consigneePostCode")
    private String consigneepostcode;

    /**
     * 收货方省
     */
    @Column(name = "consigneeProvince")
    private String consigneeprovince;

    /**
     * 收货方市
     */
    @Column(name = "consigneeCity")
    private String consigneecity;

    /**
     * 收货方区县
     */
    @Column(name = "consigneeDistrictAndCounty")
    private String consigneedistrictandcounty;

    /**
     * 收货方乡镇街道
     */
    @Column(name = "consigneeTownshipStreets")
    private String consigneetownshipstreets;

    /**
     * 收货方地址
     */
    @Column(name = "consigneeAddress")
    private String consigneeaddress;

    /**
     * 是否打印发票
     */
    @Column(name = "printInvoices")
    private String printinvoices;

    /**
     * 支付方式
     */
    @Column(name = "aymentMethod")
    private String aymentmethod;

    /**
     * 订单金额
     */
    @Column(name = "orderAmount")
    private String orderamount;

    /**
     * 是否货到付款
     */
    @Column(name = "cashOnDelivery")
    private String cashondelivery;

    /**
     * 是否保价
     */
    private String insured;

    /**
     * 保价金额
     */
    @Column(name = "insuredValue")
    private String insuredvalue;

    /**
     * 仓储作业单号
     */
    @Column(name = "warehouseNumber")
    private String warehousenumber;

    /**
     * 服务费用
     */
    @Column(name = "serviceCharge")
    private String servicecharge;

    /**
     * 创建时间
     */
    @Column(name = "creationTime")
    private Date creationtime;

    /**
     * 创建人员
     */
    @Column(name = "createPersonnel")
    private String createpersonnel;

    /**
     * 作废人员
     */
    @Column(name = "voidPersonnel")
    private String voidpersonnel;

    /**
     * 作废时间
     */
    @Column(name = "voidTime")
    private Date voidtime;

    /**
     * 获取计划单编号
     *
     * @return planCode - 计划单编号
     */
    public String getPlancode() {
        return plancode;
    }

    /**
     * 设置计划单编号
     *
     * @param plancode 计划单编号
     */
    public void setPlancode(String plancode) {
        this.plancode = plancode;
    }

    /**
     * 获取订单编号
     *
     * @return orderCode - 订单编号
     */
    public String getOrdercode() {
        return ordercode;
    }

    /**
     * 设置订单编号
     *
     * @param ordercode 订单编号
     */
    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }

    /**
     * 获取订单批次号
     *
     * @return orderBatchNumber - 订单批次号
     */
    public String getOrderbatchnumber() {
        return orderbatchnumber;
    }

    /**
     * 设置订单批次号
     *
     * @param orderbatchnumber 订单批次号
     */
    public void setOrderbatchnumber(String orderbatchnumber) {
        this.orderbatchnumber = orderbatchnumber;
    }

    /**
     * 获取计划序号
     *
     * @return programSerialNumber - 计划序号
     */
    public String getProgramserialnumber() {
        return programserialnumber;
    }

    /**
     * 设置计划序号
     *
     * @param programserialnumber 计划序号
     */
    public void setProgramserialnumber(String programserialnumber) {
        this.programserialnumber = programserialnumber;
    }

    /**
     * 获取类型
     *
     * @return serviceType - 类型
     */
    public String getServicetype() {
        return servicetype;
    }

    /**
     * 设置类型
     *
     * @param servicetype 类型
     */
    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    /**
     * 获取单据类型
     *
     * @return documentType - 单据类型
     */
    public String getDocumenttype() {
        return documenttype;
    }

    /**
     * 设置单据类型
     *
     * @param documenttype 单据类型
     */
    public void setDocumenttype(String documenttype) {
        this.documenttype = documenttype;
    }

    /**
     * 获取日期
     *
     * @return dates - 日期
     */
    public Date getDates() {
        return dates;
    }

    /**
     * 设置日期
     *
     * @param dates 日期
     */
    public void setDates(Date dates) {
        this.dates = dates;
    }

    /**
     * 获取货主编码
     *
     * @return custCode - 货主编码
     */
    public String getCustcode() {
        return custcode;
    }

    /**
     * 设置货主编码
     *
     * @param custcode 货主编码
     */
    public void setCustcode(String custcode) {
        this.custcode = custcode;
    }

    /**
     * 获取仓库编码
     *
     * @return warehouseCode - 仓库编码
     */
    public String getWarehousecode() {
        return warehousecode;
    }

    /**
     * 设置仓库编码
     *
     * @param warehousecode 仓库编码
     */
    public void setWarehousecode(String warehousecode) {
        this.warehousecode = warehousecode;
    }

    /**
     * 获取仓库名称
     *
     * @return warehouseName - 仓库名称
     */
    public String getWarehousename() {
        return warehousename;
    }

    /**
     * 设置仓库名称
     *
     * @param warehousename 仓库名称
     */
    public void setWarehousename(String warehousename) {
        this.warehousename = warehousename;
    }

    /**
     * 获取出库发货时间
     *
     * @return estimatedTimeOfDelivery - 出库发货时间
     */
    public Date getEstimatedtimeofdelivery() {
        return estimatedtimeofdelivery;
    }

    /**
     * 设置出库发货时间
     *
     * @param estimatedtimeofdelivery 出库发货时间
     */
    public void setEstimatedtimeofdelivery(Date estimatedtimeofdelivery) {
        this.estimatedtimeofdelivery = estimatedtimeofdelivery;
    }

    /**
     * 获取预计到达时间
     *
     * @return estimatedTimeOfArrival - 预计到达时间
     */
    public Date getEstimatedtimeofarrival() {
        return estimatedtimeofarrival;
    }

    /**
     * 设置预计到达时间
     *
     * @param estimatedtimeofarrival 预计到达时间
     */
    public void setEstimatedtimeofarrival(Date estimatedtimeofarrival) {
        this.estimatedtimeofarrival = estimatedtimeofarrival;
    }

    /**
     * 获取装货地
     *
     * @return loadingPlace - 装货地
     */
    public String getLoadingplace() {
        return loadingplace;
    }

    /**
     * 设置装货地
     *
     * @param loadingplace 装货地
     */
    public void setLoadingplace(String loadingplace) {
        this.loadingplace = loadingplace;
    }

    /**
     * 获取卸货地
     *
     * @return unloadingPlace - 卸货地
     */
    public String getUnloadingplace() {
        return unloadingplace;
    }

    /**
     * 设置卸货地
     *
     * @param unloadingplace 卸货地
     */
    public void setUnloadingplace(String unloadingplace) {
        this.unloadingplace = unloadingplace;
    }

    /**
     * 获取交货地
     *
     * @return deliveryPlace - 交货地
     */
    public String getDeliveryplace() {
        return deliveryplace;
    }

    /**
     * 设置交货地
     *
     * @param deliveryplace 交货地
     */
    public void setDeliveryplace(String deliveryplace) {
        this.deliveryplace = deliveryplace;
    }

    /**
     * 获取收货月台
     *
     * @return eceivingPlatform - 收货月台
     */
    public String getEceivingplatform() {
        return eceivingplatform;
    }

    /**
     * 设置收货月台
     *
     * @param eceivingplatform 收货月台
     */
    public void setEceivingplatform(String eceivingplatform) {
        this.eceivingplatform = eceivingplatform;
    }

    /**
     * 获取总货值
     *
     * @return theTotalValueOf - 总货值
     */
    public String getThetotalvalueof() {
        return thetotalvalueof;
    }

    /**
     * 设置总货值
     *
     * @param thetotalvalueof 总货值
     */
    public void setThetotalvalueof(String thetotalvalueof) {
        this.thetotalvalueof = thetotalvalueof;
    }

    /**
     * 获取供应商编码
     *
     * @return supplierCode - 供应商编码
     */
    public String getSuppliercode() {
        return suppliercode;
    }

    /**
     * 设置供应商编码
     *
     * @param suppliercode 供应商编码
     */
    public void setSuppliercode(String suppliercode) {
        this.suppliercode = suppliercode;
    }

    /**
     * 获取供应商名称
     *
     * @return supplierName - 供应商名称
     */
    public String getSuppliername() {
        return suppliername;
    }

    /**
     * 设置供应商名称
     *
     * @param suppliername 供应商名称
     */
    public void setSuppliername(String suppliername) {
        this.suppliername = suppliername;
    }

    /**
     * 获取备注
     *
     * @return remarks - 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置备注
     *
     * @param remarks 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 获取收货方编码
     *
     * @return consigneeCode - 收货方编码
     */
    public String getConsigneecode() {
        return consigneecode;
    }

    /**
     * 设置收货方编码
     *
     * @param consigneecode 收货方编码
     */
    public void setConsigneecode(String consigneecode) {
        this.consigneecode = consigneecode;
    }

    /**
     * 获取收货方名称
     *
     * @return consigneeName - 收货方名称
     */
    public String getConsigneename() {
        return consigneename;
    }

    /**
     * 设置收货方名称
     *
     * @param consigneename 收货方名称
     */
    public void setConsigneename(String consigneename) {
        this.consigneename = consigneename;
    }

    /**
     * 获取收货方联系人
     *
     * @return consigneeContact - 收货方联系人
     */
    public String getConsigneecontact() {
        return consigneecontact;
    }

    /**
     * 设置收货方联系人
     *
     * @param consigneecontact 收货方联系人
     */
    public void setConsigneecontact(String consigneecontact) {
        this.consigneecontact = consigneecontact;
    }

    /**
     * 获取收货方联系电话
     *
     * @return consigneeContactPhone - 收货方联系电话
     */
    public String getConsigneecontactphone() {
        return consigneecontactphone;
    }

    /**
     * 设置收货方联系电话
     *
     * @param consigneecontactphone 收货方联系电话
     */
    public void setConsigneecontactphone(String consigneecontactphone) {
        this.consigneecontactphone = consigneecontactphone;
    }

    /**
     * 获取收货方传真
     *
     * @return consigneeFax - 收货方传真
     */
    public String getConsigneefax() {
        return consigneefax;
    }

    /**
     * 设置收货方传真
     *
     * @param consigneefax 收货方传真
     */
    public void setConsigneefax(String consigneefax) {
        this.consigneefax = consigneefax;
    }

    /**
     * 获取收货方Email
     *
     * @return consigneeEmail - 收货方Email
     */
    public String getConsigneeemail() {
        return consigneeemail;
    }

    /**
     * 设置收货方Email
     *
     * @param consigneeemail 收货方Email
     */
    public void setConsigneeemail(String consigneeemail) {
        this.consigneeemail = consigneeemail;
    }

    /**
     * 获取收货方邮编
     *
     * @return consigneePostCode - 收货方邮编
     */
    public String getConsigneepostcode() {
        return consigneepostcode;
    }

    /**
     * 设置收货方邮编
     *
     * @param consigneepostcode 收货方邮编
     */
    public void setConsigneepostcode(String consigneepostcode) {
        this.consigneepostcode = consigneepostcode;
    }

    /**
     * 获取收货方省
     *
     * @return consigneeProvince - 收货方省
     */
    public String getConsigneeprovince() {
        return consigneeprovince;
    }

    /**
     * 设置收货方省
     *
     * @param consigneeprovince 收货方省
     */
    public void setConsigneeprovince(String consigneeprovince) {
        this.consigneeprovince = consigneeprovince;
    }

    /**
     * 获取收货方市
     *
     * @return consigneeCity - 收货方市
     */
    public String getConsigneecity() {
        return consigneecity;
    }

    /**
     * 设置收货方市
     *
     * @param consigneecity 收货方市
     */
    public void setConsigneecity(String consigneecity) {
        this.consigneecity = consigneecity;
    }

    /**
     * 获取收货方区县
     *
     * @return consigneeDistrictAndCounty - 收货方区县
     */
    public String getConsigneedistrictandcounty() {
        return consigneedistrictandcounty;
    }

    /**
     * 设置收货方区县
     *
     * @param consigneedistrictandcounty 收货方区县
     */
    public void setConsigneedistrictandcounty(String consigneedistrictandcounty) {
        this.consigneedistrictandcounty = consigneedistrictandcounty;
    }

    /**
     * 获取收货方乡镇街道
     *
     * @return consigneeTownshipStreets - 收货方乡镇街道
     */
    public String getConsigneetownshipstreets() {
        return consigneetownshipstreets;
    }

    /**
     * 设置收货方乡镇街道
     *
     * @param consigneetownshipstreets 收货方乡镇街道
     */
    public void setConsigneetownshipstreets(String consigneetownshipstreets) {
        this.consigneetownshipstreets = consigneetownshipstreets;
    }

    /**
     * 获取收货方地址
     *
     * @return consigneeAddress - 收货方地址
     */
    public String getConsigneeaddress() {
        return consigneeaddress;
    }

    /**
     * 设置收货方地址
     *
     * @param consigneeaddress 收货方地址
     */
    public void setConsigneeaddress(String consigneeaddress) {
        this.consigneeaddress = consigneeaddress;
    }

    /**
     * 获取是否打印发票
     *
     * @return printInvoices - 是否打印发票
     */
    public String getPrintinvoices() {
        return printinvoices;
    }

    /**
     * 设置是否打印发票
     *
     * @param printinvoices 是否打印发票
     */
    public void setPrintinvoices(String printinvoices) {
        this.printinvoices = printinvoices;
    }

    /**
     * 获取支付方式
     *
     * @return aymentMethod - 支付方式
     */
    public String getAymentmethod() {
        return aymentmethod;
    }

    /**
     * 设置支付方式
     *
     * @param aymentmethod 支付方式
     */
    public void setAymentmethod(String aymentmethod) {
        this.aymentmethod = aymentmethod;
    }

    /**
     * 获取订单金额
     *
     * @return orderAmount - 订单金额
     */
    public String getOrderamount() {
        return orderamount;
    }

    /**
     * 设置订单金额
     *
     * @param orderamount 订单金额
     */
    public void setOrderamount(String orderamount) {
        this.orderamount = orderamount;
    }

    /**
     * 获取是否货到付款
     *
     * @return cashOnDelivery - 是否货到付款
     */
    public String getCashondelivery() {
        return cashondelivery;
    }

    /**
     * 设置是否货到付款
     *
     * @param cashondelivery 是否货到付款
     */
    public void setCashondelivery(String cashondelivery) {
        this.cashondelivery = cashondelivery;
    }

    /**
     * 获取是否保价
     *
     * @return insured - 是否保价
     */
    public String getInsured() {
        return insured;
    }

    /**
     * 设置是否保价
     *
     * @param insured 是否保价
     */
    public void setInsured(String insured) {
        this.insured = insured;
    }

    /**
     * 获取保价金额
     *
     * @return insuredValue - 保价金额
     */
    public String getInsuredvalue() {
        return insuredvalue;
    }

    /**
     * 设置保价金额
     *
     * @param insuredvalue 保价金额
     */
    public void setInsuredvalue(String insuredvalue) {
        this.insuredvalue = insuredvalue;
    }

    /**
     * 获取仓储作业单号
     *
     * @return warehouseNumber - 仓储作业单号
     */
    public String getWarehousenumber() {
        return warehousenumber;
    }

    /**
     * 设置仓储作业单号
     *
     * @param warehousenumber 仓储作业单号
     */
    public void setWarehousenumber(String warehousenumber) {
        this.warehousenumber = warehousenumber;
    }

    /**
     * 获取服务费用
     *
     * @return serviceCharge - 服务费用
     */
    public String getServicecharge() {
        return servicecharge;
    }

    /**
     * 设置服务费用
     *
     * @param servicecharge 服务费用
     */
    public void setServicecharge(String servicecharge) {
        this.servicecharge = servicecharge;
    }

    /**
     * 获取创建时间
     *
     * @return creationTime - 创建时间
     */
    public Date getCreationtime() {
        return creationtime;
    }

    /**
     * 设置创建时间
     *
     * @param creationtime 创建时间
     */
    public void setCreationtime(Date creationtime) {
        this.creationtime = creationtime;
    }

    /**
     * 获取创建人员
     *
     * @return createPersonnel - 创建人员
     */
    public String getCreatepersonnel() {
        return createpersonnel;
    }

    /**
     * 设置创建人员
     *
     * @param createpersonnel 创建人员
     */
    public void setCreatepersonnel(String createpersonnel) {
        this.createpersonnel = createpersonnel;
    }

    /**
     * 获取作废人员
     *
     * @return voidPersonnel - 作废人员
     */
    public String getVoidpersonnel() {
        return voidpersonnel;
    }

    /**
     * 设置作废人员
     *
     * @param voidpersonnel 作废人员
     */
    public void setVoidpersonnel(String voidpersonnel) {
        this.voidpersonnel = voidpersonnel;
    }

    /**
     * 获取作废时间
     *
     * @return voidTime - 作废时间
     */
    public Date getVoidtime() {
        return voidtime;
    }

    /**
     * 设置作废时间
     *
     * @param voidtime 作废时间
     */
    public void setVoidtime(Date voidtime) {
        this.voidtime = voidtime;
    }
}