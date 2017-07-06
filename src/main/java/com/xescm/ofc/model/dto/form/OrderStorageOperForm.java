package com.xescm.ofc.model.dto.form;

import java.util.List;

/**
 * Created by hujintao on 2017/2/22.
 */
public class OrderStorageOperForm extends OrderOperForm{

    private List<String> businessTypes;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    private String tag;

    public List<String> getBusinessTypes() {
        return businessTypes;
    }

    public void setBusinessTypes(List<String> businessTypes) {
        this.businessTypes = businessTypes;
    }
}
