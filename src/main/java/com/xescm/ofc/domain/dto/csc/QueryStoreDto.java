package com.xescm.ofc.domain.dto.csc;

import java.io.Serializable;

/**
 * Created by gsfeng on 2016/10/18.
 */
public class QueryStoreDto implements Serializable {
    private static final long serialVersionUID = -5857295434322193459L;
    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "CscStoreDto{" +
                "customerId='" + customerId + '\'' +
                '}';
    }
}
