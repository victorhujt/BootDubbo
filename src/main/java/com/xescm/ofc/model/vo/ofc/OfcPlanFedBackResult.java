package com.xescm.ofc.model.vo.ofc;

/**
 * Created by lyh on 2016/10/10.
 */
public class OfcPlanFedBackResult {
    @Override
    public String toString() {
        return "OfcPlanFedBackResult{" +
                "transportNo='" + transportNo + '\'' +
                ", result='" + result + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }

    private String transportNo;//运输单号
    private String result;//结果
    private String reason;//原因

    public String getTransportNo() {
        return transportNo;
    }

    public void setTransportNo(String transportNo) {
        this.transportNo = transportNo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
