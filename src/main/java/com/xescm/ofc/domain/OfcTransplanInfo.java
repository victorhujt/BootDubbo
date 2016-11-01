package com.xescm.ofc.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "ofc_transplan_info")
public class OfcTransplanInfo {
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
     * 日期
     */
    private Date dates;

    /**
     * 货主编码
     */
    @Column(name = "custCode")
    private String custcode;

    /**
     * 预计发货时间
     */
    @Column(name = "estimatedTimeOfDelivery")
    private Date estimatedtimeofdelivery;

    /**
     * 预计到达时间
     */
    @Column(name = "estimatedTimeOfArrival")
    private Date estimatedtimeofarrival;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 数量
     */
    private BigDecimal number;

    /**
     * 体积
     */
    private BigDecimal volume;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 发货客户代码
     */
    @Column(name = "shippinCustomerCode")
    private String shippincustomercode;

    /**
     * 发货客户地址
     */
    @Column(name = "shippingAddress")
    private String shippingaddress;

    /**
     * 发货客户联系人
     */
    @Column(name = "shippingCustomerContact")
    private String shippingcustomercontact;

    /**
     * 发货客户联系电话
     */
    @Column(name = "customerContactPhone")
    private String customercontactphone;

    /**
     * 出发省份
     */
    @Column(name = "departureProvince")
    private String departureprovince;

    /**
     * 出发城市
     */
    @Column(name = "departureCity")
    private String departurecity;

    /**
     * 出发区县
     */
    @Column(name = "departureDistrict")
    private String departuredistrict;

    /**
     * 出发乡镇
     */
    @Column(name = "departureTowns")
    private String departuretowns;

    /**
     * 收货客户代码
     */
    @Column(name = "receivingCustomerCode")
    private String receivingcustomercode;

    /**
     * 收货客户地址
     */
    @Column(name = "receivingCustomerAddress")
    private String receivingcustomeraddress;

    /**
     * 收货客户联系人
     */
    @Column(name = "receivingCustomerContact")
    private String receivingcustomercontact;

    /**
     * 收货客户联系电话
     */
    @Column(name = "receivingCustomerContactPhone")
    private String receivingcustomercontactphone;

    /**
     * 目的省份
     */
    @Column(name = "destinationProvince")
    private String destinationprovince;

    /**
     * 目的城市
     */
    @Column(name = "destinationCity")
    private String destinationcity;

    /**
     * 目的区县
     */
    @Column(name = "destinationDistrict")
    private String destinationdistrict;

    /**
     * 目的乡镇
     */
    @Column(name = "destinationTown")
    private String destinationtown;

    /**
     * 收货地址经度
     */
    @Column(name = "receivingAddressLongitude")
    private String receivingaddresslongitude;

    /**
     * 收货地址纬度
     */
    @Column(name = "receivingAddressLatitude")
    private String receivingaddresslatitude;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 销售组织
     */
    @Column(name = "salesOrganization")
    private String salesorganization;

    /**
     * 产品组
     */
    @Column(name = "productGroup")
    private String productgroup;

    /**
     * 销售部门
     */
    @Column(name = "salesDepartment")
    private String salesdepartment;

    /**
     * 销售组
     */
    @Column(name = "salesGroup")
    private String salesgroup;

    /**
     * 销售部门描述
     */
    @Column(name = "salesTeamDescription")
    private String salesteamdescription;

    /**
     * 销售组描述
     */
    @Column(name = "salesDepartmentDescription")
    private String salesdepartmentdescription;

    /**
     * 运输单来源
     */
    @Column(name = "singleSourceOfTransport")
    private String singlesourceoftransport;

    /**
     * 服务费用
     */
    @Column(name = "serviceCharge")
    private BigDecimal servicecharge;

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
     * 获取预计发货时间
     *
     * @return estimatedTimeOfDelivery - 预计发货时间
     */
    public Date getEstimatedtimeofdelivery() {
        return estimatedtimeofdelivery;
    }

    /**
     * 设置预计发货时间
     *
     * @param estimatedtimeofdelivery 预计发货时间
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
     * 获取重量
     *
     * @return weight - 重量
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * 设置重量
     *
     * @param weight 重量
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * 获取数量
     *
     * @return number - 数量
     */
    public BigDecimal getNumber() {
        return number;
    }

    /**
     * 设置数量
     *
     * @param number 数量
     */
    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    /**
     * 获取体积
     *
     * @return volume - 体积
     */
    public BigDecimal getVolume() {
        return volume;
    }

    /**
     * 设置体积
     *
     * @param volume 体积
     */
    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    /**
     * 获取金额
     *
     * @return money - 金额
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置金额
     *
     * @param money 金额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取发货客户代码
     *
     * @return shippinCustomerCode - 发货客户代码
     */
    public String getShippincustomercode() {
        return shippincustomercode;
    }

    /**
     * 设置发货客户代码
     *
     * @param shippincustomercode 发货客户代码
     */
    public void setShippincustomercode(String shippincustomercode) {
        this.shippincustomercode = shippincustomercode;
    }

    /**
     * 获取发货客户地址
     *
     * @return shippingAddress - 发货客户地址
     */
    public String getShippingaddress() {
        return shippingaddress;
    }

    /**
     * 设置发货客户地址
     *
     * @param shippingaddress 发货客户地址
     */
    public void setShippingaddress(String shippingaddress) {
        this.shippingaddress = shippingaddress;
    }

    /**
     * 获取发货客户联系人
     *
     * @return shippingCustomerContact - 发货客户联系人
     */
    public String getShippingcustomercontact() {
        return shippingcustomercontact;
    }

    /**
     * 设置发货客户联系人
     *
     * @param shippingcustomercontact 发货客户联系人
     */
    public void setShippingcustomercontact(String shippingcustomercontact) {
        this.shippingcustomercontact = shippingcustomercontact;
    }

    /**
     * 获取发货客户联系电话
     *
     * @return customerContactPhone - 发货客户联系电话
     */
    public String getCustomercontactphone() {
        return customercontactphone;
    }

    /**
     * 设置发货客户联系电话
     *
     * @param customercontactphone 发货客户联系电话
     */
    public void setCustomercontactphone(String customercontactphone) {
        this.customercontactphone = customercontactphone;
    }

    /**
     * 获取出发省份
     *
     * @return departureProvince - 出发省份
     */
    public String getDepartureprovince() {
        return departureprovince;
    }

    /**
     * 设置出发省份
     *
     * @param departureprovince 出发省份
     */
    public void setDepartureprovince(String departureprovince) {
        this.departureprovince = departureprovince;
    }

    /**
     * 获取出发城市
     *
     * @return departureCity - 出发城市
     */
    public String getDeparturecity() {
        return departurecity;
    }

    /**
     * 设置出发城市
     *
     * @param departurecity 出发城市
     */
    public void setDeparturecity(String departurecity) {
        this.departurecity = departurecity;
    }

    /**
     * 获取出发区县
     *
     * @return departureDistrict - 出发区县
     */
    public String getDeparturedistrict() {
        return departuredistrict;
    }

    /**
     * 设置出发区县
     *
     * @param departuredistrict 出发区县
     */
    public void setDeparturedistrict(String departuredistrict) {
        this.departuredistrict = departuredistrict;
    }

    /**
     * 获取出发乡镇
     *
     * @return departureTowns - 出发乡镇
     */
    public String getDeparturetowns() {
        return departuretowns;
    }

    /**
     * 设置出发乡镇
     *
     * @param departuretowns 出发乡镇
     */
    public void setDeparturetowns(String departuretowns) {
        this.departuretowns = departuretowns;
    }

    /**
     * 获取收货客户代码
     *
     * @return receivingCustomerCode - 收货客户代码
     */
    public String getReceivingcustomercode() {
        return receivingcustomercode;
    }

    /**
     * 设置收货客户代码
     *
     * @param receivingcustomercode 收货客户代码
     */
    public void setReceivingcustomercode(String receivingcustomercode) {
        this.receivingcustomercode = receivingcustomercode;
    }

    /**
     * 获取收货客户地址
     *
     * @return receivingCustomerAddress - 收货客户地址
     */
    public String getReceivingcustomeraddress() {
        return receivingcustomeraddress;
    }

    /**
     * 设置收货客户地址
     *
     * @param receivingcustomeraddress 收货客户地址
     */
    public void setReceivingcustomeraddress(String receivingcustomeraddress) {
        this.receivingcustomeraddress = receivingcustomeraddress;
    }

    /**
     * 获取收货客户联系人
     *
     * @return receivingCustomerContact - 收货客户联系人
     */
    public String getReceivingcustomercontact() {
        return receivingcustomercontact;
    }

    /**
     * 设置收货客户联系人
     *
     * @param receivingcustomercontact 收货客户联系人
     */
    public void setReceivingcustomercontact(String receivingcustomercontact) {
        this.receivingcustomercontact = receivingcustomercontact;
    }

    /**
     * 获取收货客户联系电话
     *
     * @return receivingCustomerContactPhone - 收货客户联系电话
     */
    public String getReceivingcustomercontactphone() {
        return receivingcustomercontactphone;
    }

    /**
     * 设置收货客户联系电话
     *
     * @param receivingcustomercontactphone 收货客户联系电话
     */
    public void setReceivingcustomercontactphone(String receivingcustomercontactphone) {
        this.receivingcustomercontactphone = receivingcustomercontactphone;
    }

    /**
     * 获取目的省份
     *
     * @return destinationProvince - 目的省份
     */
    public String getDestinationprovince() {
        return destinationprovince;
    }

    /**
     * 设置目的省份
     *
     * @param destinationprovince 目的省份
     */
    public void setDestinationprovince(String destinationprovince) {
        this.destinationprovince = destinationprovince;
    }

    /**
     * 获取目的城市
     *
     * @return destinationCity - 目的城市
     */
    public String getDestinationcity() {
        return destinationcity;
    }

    /**
     * 设置目的城市
     *
     * @param destinationcity 目的城市
     */
    public void setDestinationcity(String destinationcity) {
        this.destinationcity = destinationcity;
    }

    /**
     * 获取目的区县
     *
     * @return destinationDistrict - 目的区县
     */
    public String getDestinationdistrict() {
        return destinationdistrict;
    }

    /**
     * 设置目的区县
     *
     * @param destinationdistrict 目的区县
     */
    public void setDestinationdistrict(String destinationdistrict) {
        this.destinationdistrict = destinationdistrict;
    }

    /**
     * 获取目的乡镇
     *
     * @return destinationTown - 目的乡镇
     */
    public String getDestinationtown() {
        return destinationtown;
    }

    /**
     * 设置目的乡镇
     *
     * @param destinationtown 目的乡镇
     */
    public void setDestinationtown(String destinationtown) {
        this.destinationtown = destinationtown;
    }

    /**
     * 获取收货地址经度
     *
     * @return receivingAddressLongitude - 收货地址经度
     */
    public String getReceivingaddresslongitude() {
        return receivingaddresslongitude;
    }

    /**
     * 设置收货地址经度
     *
     * @param receivingaddresslongitude 收货地址经度
     */
    public void setReceivingaddresslongitude(String receivingaddresslongitude) {
        this.receivingaddresslongitude = receivingaddresslongitude;
    }

    /**
     * 获取收货地址纬度
     *
     * @return receivingAddressLatitude - 收货地址纬度
     */
    public String getReceivingaddresslatitude() {
        return receivingaddresslatitude;
    }

    /**
     * 设置收货地址纬度
     *
     * @param receivingaddresslatitude 收货地址纬度
     */
    public void setReceivingaddresslatitude(String receivingaddresslatitude) {
        this.receivingaddresslatitude = receivingaddresslatitude;
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
     * 获取销售组织
     *
     * @return salesOrganization - 销售组织
     */
    public String getSalesorganization() {
        return salesorganization;
    }

    /**
     * 设置销售组织
     *
     * @param salesorganization 销售组织
     */
    public void setSalesorganization(String salesorganization) {
        this.salesorganization = salesorganization;
    }

    /**
     * 获取产品组
     *
     * @return productGroup - 产品组
     */
    public String getProductgroup() {
        return productgroup;
    }

    /**
     * 设置产品组
     *
     * @param productgroup 产品组
     */
    public void setProductgroup(String productgroup) {
        this.productgroup = productgroup;
    }

    /**
     * 获取销售部门
     *
     * @return salesDepartment - 销售部门
     */
    public String getSalesdepartment() {
        return salesdepartment;
    }

    /**
     * 设置销售部门
     *
     * @param salesdepartment 销售部门
     */
    public void setSalesdepartment(String salesdepartment) {
        this.salesdepartment = salesdepartment;
    }

    /**
     * 获取销售组
     *
     * @return salesGroup - 销售组
     */
    public String getSalesgroup() {
        return salesgroup;
    }

    /**
     * 设置销售组
     *
     * @param salesgroup 销售组
     */
    public void setSalesgroup(String salesgroup) {
        this.salesgroup = salesgroup;
    }

    /**
     * 获取销售部门描述
     *
     * @return salesTeamDescription - 销售部门描述
     */
    public String getSalesteamdescription() {
        return salesteamdescription;
    }

    /**
     * 设置销售部门描述
     *
     * @param salesteamdescription 销售部门描述
     */
    public void setSalesteamdescription(String salesteamdescription) {
        this.salesteamdescription = salesteamdescription;
    }

    /**
     * 获取销售组描述
     *
     * @return salesDepartmentDescription - 销售组描述
     */
    public String getSalesdepartmentdescription() {
        return salesdepartmentdescription;
    }

    /**
     * 设置销售组描述
     *
     * @param salesdepartmentdescription 销售组描述
     */
    public void setSalesdepartmentdescription(String salesdepartmentdescription) {
        this.salesdepartmentdescription = salesdepartmentdescription;
    }

    /**
     * 获取运输单来源
     *
     * @return singleSourceOfTransport - 运输单来源
     */
    public String getSinglesourceoftransport() {
        return singlesourceoftransport;
    }

    /**
     * 设置运输单来源
     *
     * @param singlesourceoftransport 运输单来源
     */
    public void setSinglesourceoftransport(String singlesourceoftransport) {
        this.singlesourceoftransport = singlesourceoftransport;
    }

    /**
     * 获取服务费用
     *
     * @return serviceCharge - 服务费用
     */
    public BigDecimal getServicecharge() {
        return servicecharge;
    }

    /**
     * 设置服务费用
     *
     * @param servicecharge 服务费用
     */
    public void setServicecharge(BigDecimal servicecharge) {
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