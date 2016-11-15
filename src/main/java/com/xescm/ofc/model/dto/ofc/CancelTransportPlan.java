package com.xescm.ofc.model.dto.ofc;

/**
 * Created by sql on 2016/11/15.
 */
public class CancelTransportPlan {

    private Integer code;

    private String reslut;

    private String transportNo;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getReslut() {
        return reslut;
    }

    public void setReslut(String reslut) {
        this.reslut = reslut;
    }

    public String getTransportNo() {
        return transportNo;
    }

    public void setTransportNo(String transportNo) {
        this.transportNo = transportNo;
    }
}
