package com.xescm.ofc.model.vo.epc;

import java.io.Serializable;

/**
 * 鲜易网订单取消返回
 * Created by hiyond on 2016/11/21.
 */
public class CannelOrderVo implements Serializable {

    private static final long serialVersionUID = 4613899483360238209L;
    /**
     * 客户订单编号
     */
    private String custOrderCode;

    /**
     * 货主编码
     */
    private String custCode;

    /**
     * 结果代码
     * 0-	执行失败
     * 1-	执行成功
     */
    private String resultCode;

    /**
     * 原因
     */
    private String reason;

    public String getCustOrderCode() {
        return custOrderCode;
    }

    public void setCustOrderCode(String custOrderCode) {
        this.custOrderCode = custOrderCode;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
