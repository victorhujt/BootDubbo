package com.xescm.ofc.model.dto.epc;

/**
 * Created by hiyond on 2016/11/21.
 */
public class CancelOrderDto {

    //客户订单编号
    private String custOrderCode;

    //货主编码
    private String custCode;

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getCustOrderCode() {
        return custOrderCode;
    }

    public void setCustOrderCode(String custOrderCode) {
        this.custOrderCode = custOrderCode;
    }
}
