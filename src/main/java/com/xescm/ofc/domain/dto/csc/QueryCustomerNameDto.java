package com.xescm.ofc.domain.dto.csc;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Liqd on 2016/11/18.
 */
public class QueryCustomerNameDto implements Serializable {

    /**
     * 客户名称
     */
    private List<String> customerNames;

    public List<String> getCustomerNames() {
        return customerNames;
    }

    public void setCustomerNames(List<String> customerNames) {
        this.customerNames = customerNames;
    }
}
