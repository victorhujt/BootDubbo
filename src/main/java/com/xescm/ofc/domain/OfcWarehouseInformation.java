package com.xescm.ofc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Data
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "shipment_time")
    private Date shipmentTime;

    /**
     * 承诺到达时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "expected_arrived_time")
    private Date expectedArrivedTime;

    /**
     * 入库预计到达时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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
     * 创建日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "creation_time")
    private Date creationTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "oper_time")
    private Date operTime;

    /**
     * 仓库编码
     */
    @Column(name = "warehouse_code")
    private String warehouseCode;

    /**
     * 删除标志位 0或者空表示未删除  1:表示已经删除
     */
//    @Column(name = "is_delete")
//    private String isDelete;
//
//    public String getIsDelete() {
//        return isDelete;
//    }
//
//    public void setIsDelete(String isDelete) {
//        this.isDelete = isDelete;
//    }

    /**
     * 获取供应商名称
     *
     * @return support_name - 供应商名称
     */
    public String getSupportName() {
        return supportName;
    }

}
