package com.xescm.ofc.domain.dto.csc;

import java.io.Serializable;

/**
 * Created by Liqd on 2016/11/19.
 */
public class QueryCustomerCodeDto implements Serializable {

    private String id;

    /**
     * 客户编码
     */
    private String customerCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
}
