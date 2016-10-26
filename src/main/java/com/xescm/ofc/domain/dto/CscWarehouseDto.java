package com.xescm.ofc.domain.dto;

/**
 * Created by 韦能 on 2016/10/18.
 */
public class CscWarehouseDto {
    /**
     * 仓库ID
     */
    private int id;

    /**
     * 客户ID
     */
    private int customerId;

    /**
     * 客户编码
     */
    private String customerCode;

    /**
     * 客户名字
     */
    private String customerName;

    /**
     * 仓库名称
     */
    private String warehouseName;

    /**
     * 仓库编码
     */
    private String warehouseCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    @Override
    public String toString() {
        return "CscWarehouseDto{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", customerCode='" + customerCode + '\'' +
                ", customerName='" + customerName + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", warehouseCode='" + warehouseCode + '\'' +
                '}';
    }

}
