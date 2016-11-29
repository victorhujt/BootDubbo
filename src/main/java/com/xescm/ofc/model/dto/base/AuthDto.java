package com.xescm.ofc.model.dto.base;

import java.io.Serializable;

/**
 * Created by gsfeng on 2016/10/27.
 */
public class AuthDto implements Serializable {
    private static final long serialVersionUID = 6675516307495660803L;

    private String userId;
    private String userCode;
    private String userName;
    private String groupId;
    private String groupCode;
    private String groupName;
    private String customerId;
    private String customerCode;
    private String custormerName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public String getCustormerName() {
        return custormerName;
    }

    public void setCustormerName(String custormerName) {
        this.custormerName = custormerName;
    }

    @Override
    public String toString() {
        return "AuthDto{" +
                "userId='" + userId + '\'' +
                ", userCode='" + userCode + '\'' +
                ", userName='" + userName + '\'' +
                ", groupId='" + groupId + '\'' +
                ", groupCode='" + groupCode + '\'' +
                ", groupName='" + groupName + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerCode='" + customerCode + '\'' +
                ", custormerName='" + custormerName + '\'' +
                '}';
    }
}
