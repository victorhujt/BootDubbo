package com.xescm.ofc.model.dto.csc;

import java.io.Serializable;

/**
 * Created by gsfeng on 2016/11/2.
 */
public class QueryCustomerIdDto implements Serializable {
    private static final long serialVersionUID = 2484347375511834375L;

    private String customerId;
    private String customerCode;
    private String groupId;

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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }


    @Override
    public String toString() {
        return "QueryCustomerIdDto{" +
                "customerId='" + customerId + '\'' +
                ", customerCode='" + customerCode + '\'' +
                ", groupId='" + groupId + '\'' +
                '}';
    }
}
