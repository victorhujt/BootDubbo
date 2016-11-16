package com.xescm.ofc.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "ofc_transplan_info")
public class OfcTransplanInfo {
    /**
     * 是否完成标记，查询时使用，非表中字段
     */
    @Transient
    private String ifFinished;

    /**
     * 计划单编号
     */
    @Id
    @Column(name = "plan_code")
    private String planCode;

    /**
     * 订单编号
     */
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 订单批次号
     */
    @Column(name = "order_batch_number")
    private String orderBatchNumber;

    /**
     * 计划序号
     */
    @Column(name = "program_serial_number")
    private String programSerialNumber;

    /**
     * 类型
     */
    @Column(name = "business_type")
    private String businessType;

    /**
     * 日期
     */
    @Column(name = "order_time")
    private Date orderTime;

    /**
     * 货主编码
     */
    @Column(name = "cust_code")
    private String custCode;

    /**
     * 预计发货时间
     */
    @Column(name = "pickup_time")
    private Date pickupTime;

    /**
     * 预计到达时间
     */
    @Column(name = "expected_arrived_time")
    private Date expectedArrivedTime;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 数量
     */
    private BigDecimal quantity;

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
    @Column(name = "shippin_customer_code")
    private String shippinCustomerCode;

    /**
     * 发货客户代码
     */
    @Column(name = "shippin_customer_name")
    private String shippinCustomerName;

    /**
     * 发货客户地址
     */
    @Column(name = "shipping_address")
    private String shippingAddress;

    /**
     * 发货客户联系人
     */
    @Column(name = "shipping_customer_contact")
    private String shippingCustomerContact;

    /**
     * 发货客户联系电话
     */
    @Column(name = "customer_contact_phone")
    private String customerContactPhone;

    /**
     * 出发省份
     */
    @Column(name = "departure_province")
    private String departureProvince;

    /**
     * 出发城市
     */
    @Column(name = "departure_city")
    private String departureCity;

    /**
     * 出发区县
     */
    @Column(name = "departure_district")
    private String departureDistrict;

    /**
     * 出发乡镇
     */
    @Column(name = "departure_towns")
    private String departureTowns;

    /**
     * 出发地区域编码
     */
    @Column(name = "departure_place_code")
    private String departurePlaceCode;

    /**
     * 收货客户代码
     */
    @Column(name = "receiving_customer_code")
    private String receivingCustomerCode;

    /**
     * 收货客户代码
     */
    @Column(name = "receiving_customer_name")
    private String receivingCustomerName;

    /**
     * 收货客户地址
     */
    @Column(name = "receiving_customer_address")
    private String receivingCustomerAddress;

    /**
     * 收货客户联系人
     */
    @Column(name = "receiving_customer_contact")
    private String receivingCustomerContact;

    /**
     * 收货客户联系电话
     */
    @Column(name = "receiving_customer_contact_phone")
    private String receivingCustomerContactPhone;

    /**
     * 目的省份
     */
    @Column(name = "destination_province")
    private String destinationProvince;

    /**
     * 目的城市
     */
    @Column(name = "destination_city")
    private String destinationCity;

    /**
     * 目的区县
     */
    @Column(name = "destination_district")
    private String destinationDistrict;

    /**
     * 目的乡镇
     */
    @Column(name = "destination_town")
    private String destinationTown;

    /**
     * 目的地区域编码
     */
    @Column(name = "destination_code")
    private String destinationCode;


    /**
     * 收货地址经度
     */
    @Column(name = "receiving_address_longitude")
    private String receivingAddressLongitude;

    /**
     * 收货地址纬度
     */
    @Column(name = "receiving_address_latitude")
    private String receivingAddressLatitude;

    /**
     * 备注
     */
    private String notes;

    /**
     * 销售组织
     */
    @Column(name = "sale_organization")
    private String saleOrganization;

    /**
     * 产品组
     */
    @Column(name = "product_group")
    private String productGroup;

    /**
     * 销售部门
     */
    @Column(name = "sale_department")
    private String saleDepartment;

    /**
     * 销售组
     */
    @Column(name = "sale_group")
    private String saleGroup;

    /**
     * 销售部门描述
     */
    @Column(name = "sale_department_desc")
    private String saleDepartmentDesc;

    /**
     * 销售组描述
     */
    @Column(name = "sale_group_desc")
    private String saleGroupDesc;

    /**
     * 运输单来源
     */
    @Column(name = "single_source_of_transport")
    private String singleSourceOfTransport;

    /**
     * 服务费用
     */
    @Column(name = "service_charge")
    private BigDecimal serviceCharge;

    /**
     * 基地ID(电商)
     */
    @Column(name = "base_id")
    private String baseId;

    /**
     * 创建时间
     */
    @Column(name = "creation_time")
    private Date creationTime;

    /**
     * 创建人员
     */
    @Column(name = "create_personnel")
    private String createPersonnel;

    /**
     * 作废人员
     */
    @Column(name = "void_personnel")
    private String voidPersonnel;

    /**
     * 作废时间
     */
    @Column(name = "void_time")
    private Date voidTime;

    public String getShippinCustomerName() {
        return shippinCustomerName;
    }

    public void setShippinCustomerName(String shippinCustomerName) {
        this.shippinCustomerName = shippinCustomerName;
    }

    public String getReceivingCustomerName() {
        return receivingCustomerName;
    }

    public void setReceivingCustomerName(String receivingCustomerName) {
        this.receivingCustomerName = receivingCustomerName;
    }

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
     * 获取预计发货时间
     *
     * @return pickup_time - 预计发货时间
     */
    public Date getPickupTime() {
        return pickupTime;
    }

    /**
     * 设置预计发货时间
     *
     * @param pickupTime 预计发货时间
     */
    public void setPickupTime(Date pickupTime) {
        this.pickupTime = pickupTime;
    }

    /**
     * 获取预计到达时间
     *
     * @return expected_arrived_time - 预计到达时间
     */
    public Date getExpectedArrivedTime() {
        return expectedArrivedTime;
    }

    /**
     * 设置预计到达时间
     *
     * @param expectedArrivedTime 预计到达时间
     */
    public void setExpectedArrivedTime(Date expectedArrivedTime) {
        this.expectedArrivedTime = expectedArrivedTime;
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
     * @return quantity - 数量
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     * 设置数量
     *
     * @param quantity 数量
     */
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    /**
     * 获取体积
     *
     * @return cubage - 体积
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
     * @return shippin_customer_code - 发货客户代码
     */
    public String getShippinCustomerCode() {
        return shippinCustomerCode;
    }

    /**
     * 设置发货客户代码
     *
     * @param shippinCustomerCode 发货客户代码
     */
    public void setShippinCustomerCode(String shippinCustomerCode) {
        this.shippinCustomerCode = shippinCustomerCode;
    }

    /**
     * 获取发货客户地址
     *
     * @return shipping_address - 发货客户地址
     */
    public String getShippingAddress() {
        return shippingAddress;
    }

    /**
     * 设置发货客户地址
     *
     * @param shippingAddress 发货客户地址
     */
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    /**
     * 获取发货客户联系人
     *
     * @return shipping_customer_contact - 发货客户联系人
     */
    public String getShippingCustomerContact() {
        return shippingCustomerContact;
    }

    /**
     * 设置发货客户联系人
     *
     * @param shippingCustomerContact 发货客户联系人
     */
    public void setShippingCustomerContact(String shippingCustomerContact) {
        this.shippingCustomerContact = shippingCustomerContact;
    }

    /**
     * 获取发货客户联系电话
     *
     * @return customer_contact_phone - 发货客户联系电话
     */
    public String getCustomerContactPhone() {
        return customerContactPhone;
    }

    /**
     * 设置发货客户联系电话
     *
     * @param customerContactPhone 发货客户联系电话
     */
    public void setCustomerContactPhone(String customerContactPhone) {
        this.customerContactPhone = customerContactPhone;
    }

    /**
     * 获取出发省份
     *
     * @return departure_province - 出发省份
     */
    public String getDepartureProvince() {
        return departureProvince;
    }

    /**
     * 设置出发省份
     *
     * @param departureProvince 出发省份
     */
    public void setDepartureProvince(String departureProvince) {
        this.departureProvince = departureProvince;
    }

    /**
     * 获取出发城市
     *
     * @return departure_city - 出发城市
     */
    public String getDepartureCity() {
        return departureCity;
    }

    /**
     * 设置出发城市
     *
     * @param departureCity 出发城市
     */
    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    /**
     * 获取出发区县
     *
     * @return departure_district - 出发区县
     */
    public String getDepartureDistrict() {
        return departureDistrict;
    }

    /**
     * 设置出发区县
     *
     * @param departureDistrict 出发区县
     */
    public void setDepartureDistrict(String departureDistrict) {
        this.departureDistrict = departureDistrict;
    }

    /**
     * 获取出发乡镇
     *
     * @return departure_towns - 出发乡镇
     */
    public String getDepartureTowns() {
        return departureTowns;
    }

    /**
     * 设置出发乡镇
     *
     * @param departureTowns 出发乡镇
     */
    public void setDepartureTowns(String departureTowns) {
        this.departureTowns = departureTowns;
    }

    /**
     * 获取出发地区域编码
     *
     * @return departure_place_code - 出发地区域编码
     */
    public String getDeparturePlaceCode() {
        return departurePlaceCode;
    }

    /**
     * 设置出发地区域编码
     *
     * @param departurePlaceCode 出发地区域编码
     */
    public void setDeparturePlaceCode(String departurePlaceCode) {
        this.departurePlaceCode = departurePlaceCode;
    }


    /**
     * 获取收货客户代码
     *
     * @return receiving_customer_code - 收货客户代码
     */
    public String getReceivingCustomerCode() {
        return receivingCustomerCode;
    }

    /**
     * 设置收货客户代码
     *
     * @param receivingCustomerCode 收货客户代码
     */
    public void setReceivingCustomerCode(String receivingCustomerCode) {
        this.receivingCustomerCode = receivingCustomerCode;
    }

    /**
     * 获取收货客户地址
     *
     * @return receiving_customer_address - 收货客户地址
     */
    public String getReceivingCustomerAddress() {
        return receivingCustomerAddress;
    }

    /**
     * 设置收货客户地址
     *
     * @param receivingCustomerAddress 收货客户地址
     */
    public void setReceivingCustomerAddress(String receivingCustomerAddress) {
        this.receivingCustomerAddress = receivingCustomerAddress;
    }

    /**
     * 获取收货客户联系人
     *
     * @return receiving_customer_contact - 收货客户联系人
     */
    public String getReceivingCustomerContact() {
        return receivingCustomerContact;
    }

    /**
     * 设置收货客户联系人
     *
     * @param receivingCustomerContact 收货客户联系人
     */
    public void setReceivingCustomerContact(String receivingCustomerContact) {
        this.receivingCustomerContact = receivingCustomerContact;
    }

    /**
     * 获取收货客户联系电话
     *
     * @return receiving_customer_contact_phone - 收货客户联系电话
     */
    public String getReceivingCustomerContactPhone() {
        return receivingCustomerContactPhone;
    }

    /**
     * 设置收货客户联系电话
     *
     * @param receivingCustomerContactPhone 收货客户联系电话
     */
    public void setReceivingCustomerContactPhone(String receivingCustomerContactPhone) {
        this.receivingCustomerContactPhone = receivingCustomerContactPhone;
    }

    /**
     * 获取目的省份
     *
     * @return destination_province - 目的省份
     */
    public String getDestinationProvince() {
        return destinationProvince;
    }

    /**
     * 设置目的省份
     *
     * @param destinationProvince 目的省份
     */
    public void setDestinationProvince(String destinationProvince) {
        this.destinationProvince = destinationProvince;
    }

    /**
     * 获取目的城市
     *
     * @return destination_city - 目的城市
     */
    public String getDestinationCity() {
        return destinationCity;
    }

    /**
     * 设置目的城市
     *
     * @param destinationCity 目的城市
     */
    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    /**
     * 获取目的区县
     *
     * @return destination_district - 目的区县
     */
    public String getDestinationDistrict() {
        return destinationDistrict;
    }

    /**
     * 设置目的区县
     *
     * @param destinationDistrict 目的区县
     */
    public void setDestinationDistrict(String destinationDistrict) {
        this.destinationDistrict = destinationDistrict;
    }

    /**
     * 获取目的乡镇
     *
     * @return destination_town - 目的乡镇
     */
    public String getDestinationTown() {
        return destinationTown;
    }

    /**
     * 设置目的乡镇
     *
     * @param destinationTown 目的乡镇
     */
    public void setDestinationTown(String destinationTown) {
        this.destinationTown = destinationTown;
    }

    /**
     * 获取目的地区域编码
     *
     * @return destination_code - 目的地区域编码
     */
    public String getDestinationCode() {
        return destinationCode;
    }

    /**
     * 设置目的地区域编码
     *
     * @param destinationCode 目的地区域编码
     */
    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    /**
     * 获取收货地址经度
     *
     * @return receiving_address_longitude - 收货地址经度
     */
    public String getReceivingAddressLongitude() {
        return receivingAddressLongitude;
    }

    /**
     * 设置收货地址经度
     *
     * @param receivingAddressLongitude 收货地址经度
     */
    public void setReceivingAddressLongitude(String receivingAddressLongitude) {
        this.receivingAddressLongitude = receivingAddressLongitude;
    }

    /**
     * 获取收货地址纬度
     *
     * @return receiving_address_latitude - 收货地址纬度
     */
    public String getReceivingAddressLatitude() {
        return receivingAddressLatitude;
    }

    /**
     * 设置收货地址纬度
     *
     * @param receivingAddressLatitude 收货地址纬度
     */
    public void setReceivingAddressLatitude(String receivingAddressLatitude) {
        this.receivingAddressLatitude = receivingAddressLatitude;
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
     * 获取销售组织
     *
     * @return sale_organization - 销售组织
     */
    public String getSaleOrganization() {
        return saleOrganization;
    }

    /**
     * 设置销售组织
     *
     * @param saleOrganization 销售组织
     */
    public void setSaleOrganization(String saleOrganization) {
        this.saleOrganization = saleOrganization;
    }

    /**
     * 获取产品组
     *
     * @return product_group - 产品组
     */
    public String getProductGroup() {
        return productGroup;
    }

    /**
     * 设置产品组
     *
     * @param productGroup 产品组
     */
    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    /**
     * 获取销售部门
     *
     * @return sale_department - 销售部门
     */
    public String getSaleDepartment() {
        return saleDepartment;
    }

    /**
     * 设置销售部门
     *
     * @param saleDepartment 销售部门
     */
    public void setSaleDepartment(String saleDepartment) {
        this.saleDepartment = saleDepartment;
    }

    /**
     * 获取销售组
     *
     * @return sale_group - 销售组
     */
    public String getSaleGroup() {
        return saleGroup;
    }

    /**
     * 设置销售组
     *
     * @param saleGroup 销售组
     */
    public void setSaleGroup(String saleGroup) {
        this.saleGroup = saleGroup;
    }

    /**
     * 获取销售部门描述
     *
     * @return sale_department_desc - 销售部门描述
     */
    public String getSaleDepartmentDesc() {
        return saleDepartmentDesc;
    }

    /**
     * 设置销售部门描述
     *
     * @param saleDepartmentDesc 销售部门描述
     */
    public void setSaleDepartmentDesc(String saleDepartmentDesc) {
        this.saleDepartmentDesc = saleDepartmentDesc;
    }

    /**
     * 获取销售组描述
     *
     * @return sale_group_desc - 销售组描述
     */
    public String getSaleGroupDesc() {
        return saleGroupDesc;
    }

    /**
     * 设置销售组描述
     *
     * @param saleGroupDesc 销售组描述
     */
    public void setSaleGroupDesc(String saleGroupDesc) {
        this.saleGroupDesc = saleGroupDesc;
    }

    /**
     * 获取运输单来源
     *
     * @return single_source_of_transport - 运输单来源
     */
    public String getSingleSourceOfTransport() {
        return singleSourceOfTransport;
    }

    /**
     * 设置运输单来源
     *
     * @param singleSourceOfTransport 运输单来源
     */
    public void setSingleSourceOfTransport(String singleSourceOfTransport) {
        this.singleSourceOfTransport = singleSourceOfTransport;
    }

    /**
     * 获取服务费用
     *
     * @return service_charge - 服务费用
     */
    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    /**
     * 设置服务费用
     *
     * @param serviceCharge 服务费用
     */
    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    /**
     * 获取基地ID(电商)
     *
     * @return base_id - 基地ID(电商)
     */
    public String getBaseId() {
        return baseId;
    }

    /**
     * 设置基地ID(电商)
     *
     * @param baseId 基地ID(电商)
     */
    public void setBaseId(String baseId) {
        this.baseId = baseId;
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

    public String getIfFinished() {
        return ifFinished;
    }

    public void setIfFinished(String ifFinished) {
        this.ifFinished = ifFinished;
    }
}