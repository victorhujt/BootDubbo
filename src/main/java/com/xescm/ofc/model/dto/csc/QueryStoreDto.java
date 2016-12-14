package com.xescm.ofc.model.dto.csc;

import java.io.Serializable;

/**
 * Created by gsfeng on 2016/10/18.
 */
public class QueryStoreDto implements Serializable {
    private static final long serialVersionUID = -5857295434322193459L;
    private String customerCode;

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
}
