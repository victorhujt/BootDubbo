package com.xescm.ofc.domain.dto.coo;

import java.util.List;

/**
 * 订单中心创建订单（鲜易网）
 * 订单主表信息
 * Created by hiyond on 2016/11/14.
 */
public class CreateOrderEntity {

    /**
     * 客户订单编号 Y
     * 传入对应平台的订单编号
     */
    private String cust_order_code;

    /**
     * 订单日期 Y
     * 订单日期
     */
    private String order_time;

    /**
     * 货主编码 Y
     * 固定平台客户的代码(确定接入后分配)
     */
    private String cust_code;

    /**
     * 货主名称 Y
     * 固定平台客户的公司名称
     */
    private String cust_name;

    /**
     * 订单类型 Y
     * 60-运输订单, 仅运输业务
     * 61-仓配订单，有合作仓同时配送业务
     */
    private String order_type;

    /**
     * 业务类型 Y
     * 订单类型：【60】
     * 业务类型：600-【城配】
     * 601-【干线】
     * 订单类型：【61】
     * 业务类型:610-【销售出库】
     * 611-【调拨出库】
     * 612-【报损出库】
     * 613-【其他出库】
     * 620-【采购入库】
     * 621-【调拨入库】
     * 622-【退货入库】
     * 623-【加工入库】
     */
    private String business_type;

    /**
     * 备注 Y
     */
    private String notes;

    /**
     * 店铺编码 Y
     * 在工作台维护，客户方系统店铺编码，用于区分渠道,传空为默认
     */
    private String store_code;

    /**
     * 订单来源 Y
     * 【平台、EDI、手工录入】
     */
    private String order_source;

    /**
     * 销售组织
     * SAP专用
     */
    private String expand_sale_org;

    /**
     * 产品组
     * SAP专用
     */
    private String expand_pro_group;

    /**
     * 销售部门
     * SAP专用
     */
    private String expand_sale_dep;

    /**
     * 销售组
     * SAP专用
     */
    private String expand_sale_group;

    /**
     * 销售部门描述
     * SAP专用
     */
    private String expand_sale_dep_des;

    /**
     * 销售组描述
     * SAP专用
     */
    private String expand_sale_group_des;

    /**
     * 数量
     * 【数量、重量、体积】三选一，运输订单时判断
     */
    private String quantity;

    /**
     * 重量
     * 【数量、重量、体积】三选一，运输订单时判断
     */
    private String weight;

    /**
     * 体积
     * 【数量、重量、体积】三选一，运输订单时判断
     */
    private String cubage;

    /**
     * 合计标准箱
     * 最大值9999
     */
    private String total_standard_box;

    /**
     * 运输要求
     */
    private String trans_require;

    /**
     * 取货时间
     */
    private String pickup_time;

    /**
     * 期望送货时间
     */
    private String expected_arrived_time;

    /**
     * 发货方名称
     */
    private String consignor_name;

    /**
     * 发货方联系人
     */
    private String consignor_contact;

    /**
     * 发货方联系电话
     */
    private String consignor_phone;

    /**
     * 发货方传真
     */
    private String consignor_fax;

    /**
     * 发货方Email
     */
    private String consignor_email;

    /**
     * 发货方邮编
     */
    private String consignor_zip;

    /**
     * 发货方省（汉字）
     */
    private String consignor_province;

    /**
     * 发货方市（汉字）
     */
    private String consignor_city;

    /**
     * 发货方区县（汉字）
     */
    private String consignor_county;

    /**
     * 发货方乡镇街道
     */
    private String consignor_town;

    /**
     * 发货方地址
     */
    private String consignor_address;

    /**
     * 收货方名称
     */
    private String consignee_name;

    /**
     * 收货方联系人
     */
    private String consignee_contact;

    /**
     * 收货方联系电话
     */
    private String consignee_phone;

    /**
     * 收货方传真
     */
    private String consignee_fax;

    /**
     * 收货方email
     */
    private String consignee_email;

    /**
     * 收货方邮编
     */
    private String consignee_zip;

    /**
     * 收货方省
     */
    private String consignee_province;

    /**
     * 收货方市
     */
    private String consignee_city;

    /**
     * 收货方区县
     */
    private String consignee_county;

    /**
     * 收货方乡镇街道
     */
    private String consignee_town;

    /**
     * 收货方地址
     */
    private String consignee_address;

    /**
     * 仓库编码
     * 在平台的仓库编码
     */
    private String warehouse_code;

    /**
     * 仓库名称
     */
    private String warehouse_name;

    /**
     * 是否需要提供运输
     * 0-否 1-是
     */
    private String provide_transport;

    /**
     * 供应商名称
     */
    private String support_name;

    /**
     * 供应商联系人
     */
    private String support_contact;

    /**
     * 供应商联系电话
     */
    private String support_phone;

    /**
     * 供应商传真
     */
    private String support_fax;

    /**
     * 供应商Email
     */
    private String support_email;

    /**
     * 供应商邮编
     */
    private String support_zip;

    /**
     * 供应商省
     */
    private String support_province;

    /**
     * 供应商市
     */
    private String support_city;

    /**
     * 供应商区县
     */
    private String support_county;

    /**
     * 供应商街道
     */
    private String support_town;

    /**
     * 供应商地址
     */
    private String support_address;

    /**
     * 入库预计到达时间
     */
    private String arrive_time;

    /**
     * 车牌号
     */
    private String plate_number;

    /**
     * 司机姓名
     * 入库或出库自带车辆
     */
    private String driver_name;

    /**
     * 联系电话
     * 入库或出库自带车辆
     */
    private String contact_number;

    /**
     * 服务费
     * 平台运费
     */
    private String service_charge;

    /**
     * 订单金额
     */
    private String order_amount;

    /**
     * 货款金额
     */
    private String payment_amount;

    /**
     * 代收货款金额
     */
    private String collect_loan_amount;

    /**
     * 代收服务费
     */
    private String collect_service_charge;

    /**
     * 代收标记
     */
    private String collect_flag;

    /**
     * 是否打印发票(电商)
     */
    private String print_invoice;

    /**
     * 买家支付方式(电商)
     */
    private String buyer_payment_me;

    /**
     * 是否保价(电商)
     */
    private String insure;

    /**
     * 保价金额(电商)
     */
    private String insure_value;

    /**
     * 基地ID(电商)
     */
    private String Base_id;

    /**
     * API订单货品明细
     */
    private List<XebestCreateOrderGoodsInfo> xebestCreateOrderGoodsInfos;

    public String getCust_order_code() {
        return cust_order_code;
    }

    public void setCust_order_code(String cust_order_code) {
        this.cust_order_code = cust_order_code;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getCust_code() {
        return cust_code;
    }

    public void setCust_code(String cust_code) {
        this.cust_code = cust_code;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(String business_type) {
        this.business_type = business_type;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStore_code() {
        return store_code;
    }

    public void setStore_code(String store_code) {
        this.store_code = store_code;
    }

    public String getOrder_source() {
        return order_source;
    }

    public void setOrder_source(String order_source) {
        this.order_source = order_source;
    }

    public String getExpand_sale_org() {
        return expand_sale_org;
    }

    public void setExpand_sale_org(String expand_sale_org) {
        this.expand_sale_org = expand_sale_org;
    }

    public String getExpand_pro_group() {
        return expand_pro_group;
    }

    public void setExpand_pro_group(String expand_pro_group) {
        this.expand_pro_group = expand_pro_group;
    }

    public String getExpand_sale_dep() {
        return expand_sale_dep;
    }

    public void setExpand_sale_dep(String expand_sale_dep) {
        this.expand_sale_dep = expand_sale_dep;
    }

    public String getExpand_sale_group() {
        return expand_sale_group;
    }

    public void setExpand_sale_group(String expand_sale_group) {
        this.expand_sale_group = expand_sale_group;
    }

    public String getExpand_sale_dep_des() {
        return expand_sale_dep_des;
    }

    public void setExpand_sale_dep_des(String expand_sale_dep_des) {
        this.expand_sale_dep_des = expand_sale_dep_des;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCubage() {
        return cubage;
    }

    public void setCubage(String cubage) {
        this.cubage = cubage;
    }

    public String getTotal_standard_box() {
        return total_standard_box;
    }

    public void setTotal_standard_box(String total_standard_box) {
        this.total_standard_box = total_standard_box;
    }

    public String getTrans_require() {
        return trans_require;
    }

    public void setTrans_require(String trans_require) {
        this.trans_require = trans_require;
    }

    public String getPickup_time() {
        return pickup_time;
    }

    public void setPickup_time(String pickup_time) {
        this.pickup_time = pickup_time;
    }

    public String getExpected_arrived_time() {
        return expected_arrived_time;
    }

    public void setExpected_arrived_time(String expected_arrived_time) {
        this.expected_arrived_time = expected_arrived_time;
    }

    public String getConsignor_name() {
        return consignor_name;
    }

    public void setConsignor_name(String consignor_name) {
        this.consignor_name = consignor_name;
    }

    public String getConsignor_contact() {
        return consignor_contact;
    }

    public void setConsignor_contact(String consignor_contact) {
        this.consignor_contact = consignor_contact;
    }

    public String getConsignor_phone() {
        return consignor_phone;
    }

    public void setConsignor_phone(String consignor_phone) {
        this.consignor_phone = consignor_phone;
    }

    public String getConsignor_email() {
        return consignor_email;
    }

    public void setConsignor_email(String consignor_email) {
        this.consignor_email = consignor_email;
    }

    public String getConsignor_zip() {
        return consignor_zip;
    }

    public void setConsignor_zip(String consignor_zip) {
        this.consignor_zip = consignor_zip;
    }

    public String getConsignor_province() {
        return consignor_province;
    }

    public void setConsignor_province(String consignor_province) {
        this.consignor_province = consignor_province;
    }

    public String getConsignor_city() {
        return consignor_city;
    }

    public void setConsignor_city(String consignor_city) {
        this.consignor_city = consignor_city;
    }

    public String getConsignor_county() {
        return consignor_county;
    }

    public void setConsignor_county(String consignor_county) {
        this.consignor_county = consignor_county;
    }

    public String getConsignor_town() {
        return consignor_town;
    }

    public void setConsignor_town(String consignor_town) {
        this.consignor_town = consignor_town;
    }

    public String getConsignor_address() {
        return consignor_address;
    }

    public void setConsignor_address(String consignor_address) {
        this.consignor_address = consignor_address;
    }

    public String getConsignee_name() {
        return consignee_name;
    }

    public void setConsignee_name(String consignee_name) {
        this.consignee_name = consignee_name;
    }

    public String getConsignee_contact() {
        return consignee_contact;
    }

    public void setConsignee_contact(String consignee_contact) {
        this.consignee_contact = consignee_contact;
    }

    public String getConsignee_phone() {
        return consignee_phone;
    }

    public void setConsignee_phone(String consignee_phone) {
        this.consignee_phone = consignee_phone;
    }

    public String getConsignee_fax() {
        return consignee_fax;
    }

    public void setConsignee_fax(String consignee_fax) {
        this.consignee_fax = consignee_fax;
    }

    public String getConsignee_email() {
        return consignee_email;
    }

    public void setConsignee_email(String consignee_email) {
        this.consignee_email = consignee_email;
    }

    public String getConsignee_zip() {
        return consignee_zip;
    }

    public void setConsignee_zip(String consignee_zip) {
        this.consignee_zip = consignee_zip;
    }

    public String getConsignee_province() {
        return consignee_province;
    }

    public void setConsignee_province(String consignee_province) {
        this.consignee_province = consignee_province;
    }

    public String getConsignee_city() {
        return consignee_city;
    }

    public void setConsignee_city(String consignee_city) {
        this.consignee_city = consignee_city;
    }

    public String getConsignee_county() {
        return consignee_county;
    }

    public void setConsignee_county(String consignee_county) {
        this.consignee_county = consignee_county;
    }

    public String getConsignee_town() {
        return consignee_town;
    }

    public void setConsignee_town(String consignee_town) {
        this.consignee_town = consignee_town;
    }

    public String getConsignee_address() {
        return consignee_address;
    }

    public void setConsignee_address(String consignee_address) {
        this.consignee_address = consignee_address;
    }

    public String getWarehouse_code() {
        return warehouse_code;
    }

    public void setWarehouse_code(String warehouse_code) {
        this.warehouse_code = warehouse_code;
    }

    public String getWarehouse_name() {
        return warehouse_name;
    }

    public void setWarehouse_name(String warehouse_name) {
        this.warehouse_name = warehouse_name;
    }

    public String getProvide_transport() {
        return provide_transport;
    }

    public void setProvide_transport(String provide_transport) {
        this.provide_transport = provide_transport;
    }

    public String getSupport_name() {
        return support_name;
    }

    public void setSupport_name(String support_name) {
        this.support_name = support_name;
    }

    public String getSupport_contact() {
        return support_contact;
    }

    public void setSupport_contact(String support_contact) {
        this.support_contact = support_contact;
    }

    public String getSupport_phone() {
        return support_phone;
    }

    public void setSupport_phone(String support_phone) {
        this.support_phone = support_phone;
    }

    public String getSupport_fax() {
        return support_fax;
    }

    public void setSupport_fax(String support_fax) {
        this.support_fax = support_fax;
    }

    public String getSupport_email() {
        return support_email;
    }

    public void setSupport_email(String support_email) {
        this.support_email = support_email;
    }

    public String getSupport_zip() {
        return support_zip;
    }

    public void setSupport_zip(String support_zip) {
        this.support_zip = support_zip;
    }

    public String getSupport_province() {
        return support_province;
    }

    public void setSupport_province(String support_province) {
        this.support_province = support_province;
    }

    public String getSupport_city() {
        return support_city;
    }

    public void setSupport_city(String support_city) {
        this.support_city = support_city;
    }

    public String getSupport_county() {
        return support_county;
    }

    public void setSupport_county(String support_county) {
        this.support_county = support_county;
    }

    public String getSupport_town() {
        return support_town;
    }

    public void setSupport_town(String support_town) {
        this.support_town = support_town;
    }

    public String getSupport_address() {
        return support_address;
    }

    public void setSupport_address(String support_address) {
        this.support_address = support_address;
    }

    public String getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(String arrive_time) {
        this.arrive_time = arrive_time;
    }

    public String getPlate_number() {
        return plate_number;
    }

    public void setPlate_number(String plate_number) {
        this.plate_number = plate_number;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getService_charge() {
        return service_charge;
    }

    public void setService_charge(String service_charge) {
        this.service_charge = service_charge;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public String getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(String payment_amount) {
        this.payment_amount = payment_amount;
    }

    public String getCollect_loan_amount() {
        return collect_loan_amount;
    }

    public void setCollect_loan_amount(String collect_loan_amount) {
        this.collect_loan_amount = collect_loan_amount;
    }

    public String getCollect_service_charge() {
        return collect_service_charge;
    }

    public void setCollect_service_charge(String collect_service_charge) {
        this.collect_service_charge = collect_service_charge;
    }

    public String getCollect_flag() {
        return collect_flag;
    }

    public void setCollect_flag(String collect_flag) {
        this.collect_flag = collect_flag;
    }

    public String getPrint_invoice() {
        return print_invoice;
    }

    public void setPrint_invoice(String print_invoice) {
        this.print_invoice = print_invoice;
    }

    public String getBuyer_payment_me() {
        return buyer_payment_me;
    }

    public void setBuyer_payment_me(String buyer_payment_me) {
        this.buyer_payment_me = buyer_payment_me;
    }

    public String getInsure() {
        return insure;
    }

    public void setInsure(String insure) {
        this.insure = insure;
    }

    public String getInsure_value() {
        return insure_value;
    }

    public void setInsure_value(String insure_value) {
        this.insure_value = insure_value;
    }

    public String getBase_id() {
        return Base_id;
    }

    public void setBase_id(String base_id) {
        Base_id = base_id;
    }

    public List<XebestCreateOrderGoodsInfo> getXebestCreateOrderGoodsInfos() {
        return xebestCreateOrderGoodsInfos;
    }

    public void setXebestCreateOrderGoodsInfos(List<XebestCreateOrderGoodsInfo> xebestCreateOrderGoodsInfos) {
        this.xebestCreateOrderGoodsInfos = xebestCreateOrderGoodsInfos;
    }

    public String getExpand_sale_group_des() {
        return expand_sale_group_des;
    }

    public void setExpand_sale_group_des(String expand_sale_group_des) {
        this.expand_sale_group_des = expand_sale_group_des;
    }

    public String getConsignor_fax() {
        return consignor_fax;
    }

    public void setConsignor_fax(String consignor_fax) {
        this.consignor_fax = consignor_fax;
    }

    @Override
    public String toString() {
        return "XebestCreateOrderEntity{" +
                "cust_order_code='" + cust_order_code + '\'' +
                ", order_time='" + order_time + '\'' +
                ", cust_code='" + cust_code + '\'' +
                ", cust_name='" + cust_name + '\'' +
                ", order_type='" + order_type + '\'' +
                ", business_type='" + business_type + '\'' +
                ", notes='" + notes + '\'' +
                ", store_code='" + store_code + '\'' +
                ", order_source='" + order_source + '\'' +
                ", expand_sale_org='" + expand_sale_org + '\'' +
                ", expand_pro_group='" + expand_pro_group + '\'' +
                ", expand_sale_dep='" + expand_sale_dep + '\'' +
                ", expand_sale_group='" + expand_sale_group + '\'' +
                ", expand_sale_dep_des='" + expand_sale_dep_des + '\'' +
                ", quantity='" + quantity + '\'' +
                ", weight='" + weight + '\'' +
                ", cubage='" + cubage + '\'' +
                ", total_standard_box='" + total_standard_box + '\'' +
                ", trans_require='" + trans_require + '\'' +
                ", pickup_time='" + pickup_time + '\'' +
                ", expected_arrived_time='" + expected_arrived_time + '\'' +
                ", consignor_name='" + consignor_name + '\'' +
                ", consignor_contact='" + consignor_contact + '\'' +
                ", consignor_phone='" + consignor_phone + '\'' +
                ", consignor_email='" + consignor_email + '\'' +
                ", consignor_zip='" + consignor_zip + '\'' +
                ", consignor_province='" + consignor_province + '\'' +
                ", consignor_city='" + consignor_city + '\'' +
                ", consignor_county='" + consignor_county + '\'' +
                ", consignor_town='" + consignor_town + '\'' +
                ", consignor_address='" + consignor_address + '\'' +
                ", consignee_name='" + consignee_name + '\'' +
                ", consignee_contact='" + consignee_contact + '\'' +
                ", consignee_phone='" + consignee_phone + '\'' +
                ", consignee_fax='" + consignee_fax + '\'' +
                ", consignee_email='" + consignee_email + '\'' +
                ", consignee_zip='" + consignee_zip + '\'' +
                ", consignee_province='" + consignee_province + '\'' +
                ", consignee_city='" + consignee_city + '\'' +
                ", consignee_county='" + consignee_county + '\'' +
                ", consignee_town='" + consignee_town + '\'' +
                ", consignee_address='" + consignee_address + '\'' +
                ", warehouse_code='" + warehouse_code + '\'' +
                ", warehouse_name='" + warehouse_name + '\'' +
                ", provide_transport='" + provide_transport + '\'' +
                ", support_name='" + support_name + '\'' +
                ", support_contact='" + support_contact + '\'' +
                ", support_phone='" + support_phone + '\'' +
                ", support_fax='" + support_fax + '\'' +
                ", support_email='" + support_email + '\'' +
                ", support_zip='" + support_zip + '\'' +
                ", support_province='" + support_province + '\'' +
                ", support_city='" + support_city + '\'' +
                ", support_county='" + support_county + '\'' +
                ", support_town='" + support_town + '\'' +
                ", support_address='" + support_address + '\'' +
                ", arrive_time='" + arrive_time + '\'' +
                ", plate_number='" + plate_number + '\'' +
                ", driver_name='" + driver_name + '\'' +
                ", contact_number='" + contact_number + '\'' +
                ", service_charge='" + service_charge + '\'' +
                ", order_amount='" + order_amount + '\'' +
                ", payment_amount='" + payment_amount + '\'' +
                ", collect_loan_amount='" + collect_loan_amount + '\'' +
                ", collect_service_charge='" + collect_service_charge + '\'' +
                ", collect_flag='" + collect_flag + '\'' +
                ", print_invoice='" + print_invoice + '\'' +
                ", buyer_payment_me='" + buyer_payment_me + '\'' +
                ", insure='" + insure + '\'' +
                ", insure_value='" + insure_value + '\'' +
                ", Base_id='" + Base_id + '\'' +
                ", xebestCreateOrderGoodsInfos=" + xebestCreateOrderGoodsInfos +
                '}';
    }
}
