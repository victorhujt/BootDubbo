package com.xescm.ofc.model.dto.csc;

import java.io.Serializable;

/**
 * Created by hiyond on 2016/11/27.
 */
public class QueryCustomerNameAvgueDto implements Serializable {

    private static final long serialVersionUID = -1371177868464999370L;

    private String customerName;

    private int pageNum = 1;

    private int pageSize = 20;

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
