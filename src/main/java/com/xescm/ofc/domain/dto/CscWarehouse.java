package com.xescm.ofc.domain.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tbl_csc_warehouse")
public class CscWarehouse {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    private String id;

    /**
     * 仓库编码
     */
    @Column(name = "warehouse_code")
    private String warehouseCode;

    /**
     * 仓库id
     */
    @Column(name = "warehouse_id")
    private String warehouseId;
    /**
     * 客户id
     */
    @Column(name="customer_id")
    private String customerId;
    /**
     * 客户编码
     */
    @Column(name="customer_code")
    private  String customerCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    @Override
    public String toString() {
        return "CscWarehouse{" +
                "id='" + id + '\'' +
                ", warehouseCode='" + warehouseCode + '\'' +
                ", warehouseId='" + warehouseId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerCode='" + customerCode + '\'' +
                '}';
    }

}