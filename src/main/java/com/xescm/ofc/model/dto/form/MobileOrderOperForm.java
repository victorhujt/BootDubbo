package com.xescm.ofc.model.dto.form;

/**
 * Created by victor on 2016/12/15.
 */
public class MobileOrderOperForm extends BaseForm{
    private String mobileOrderStatus;

    public String getMobileOrderStatus() {
        return mobileOrderStatus;
    }

    public void setMobileOrderStatus(String mobileOrderStatus) {
        this.mobileOrderStatus = mobileOrderStatus;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    private String businessType;

}
