package com.xescm.ofc.model.dto.csc;

/**
 * Created by hiyond on 2016/11/27.
 */
public class QueryCustomerNameAvgueDto {

    private String customerName;

    private int pageNum;

    private int pageSize;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
