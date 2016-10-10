package com.xescm.ofc.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "ofc_warehouse_information")
public class OfcWarehouseInformation {
    /**
     * 供应商名称
     */
    @Column(name = "support_name")
    private String supportName;

    /**
     * 供应商编码
     */
    @Column(name = "support_code")
    private String supportCode;

    /**
     * 是否需要提供运输
     */
    @Column(name = "provide_transport")
    private Integer provideTransport;

    /**
     * 出库发货时间
     */
    @Column(name = "shipment_time")
    private Date shipmentTime;

    /**
     * 入库预计到达时间
     */
    @Column(name = "arrive_time")
    private Date arriveTime;

    /**
     * 仓库名称
     */
    @Column(name = "warehouse_name")
    private String warehouseName;

    /**
     * 车牌号
     */
    @Column(name = "plate_number")
    private String plateNumber;

    /**
     * 司机姓名
     */
    @Column(name = "driver_name")
    private String driverName;

    /**
     * 联系电话
     */
    @Column(name = "contact_number")
    private String contactNumber;

    /**
     * 订单编号
     */
    @Column(name = "order_code")
    private String orderCode;

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
     * 获取是否需要提供运输
     *
     * @return provide_transport - 是否需要提供运输
     */
    public Integer getProvideTransport() {
        return provideTransport;
    }

    /**
     * 设置是否需要提供运输
     *
     * @param provideTransport 是否需要提供运输
     */
    public void setProvideTransport(Integer provideTransport) {
        this.provideTransport = provideTransport;
    }

    /**
     * 获取出库发货时间
     *
     * @return shipment_time - 出库发货时间
     */
    public Date getShipmentTime() {
        return shipmentTime;
    }

    /**
     * 设置出库发货时间
     *
     * @param shipmentTime 出库发货时间
     */
    public void setShipmentTime(Date shipmentTime) {
        this.shipmentTime = shipmentTime;
    }

    /**
     * 获取入库预计到达时间
     *
     * @return arrive_time - 入库预计到达时间
     */
    public Date getArriveTime() {
        return arriveTime;
    }

    /**
     * 设置入库预计到达时间
     *
     * @param arriveTime 入库预计到达时间
     */
    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
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
     * 获取车牌号
     *
     * @return plate_number - 车牌号
     */
    public String getPlateNumber() {
        return plateNumber;
    }

    /**
     * 设置车牌号
     *
     * @param plateNumber 车牌号
     */
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    /**
     * 获取司机姓名
     *
     * @return driver_name - 司机姓名
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * 设置司机姓名
     *
     * @param driverName 司机姓名
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    /**
     * 获取联系电话
     *
     * @return contact_number - 联系电话
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * 设置联系电话
     *
     * @param contactNumber 联系电话
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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
}