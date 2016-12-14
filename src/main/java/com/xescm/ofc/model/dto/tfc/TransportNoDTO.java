package com.xescm.ofc.model.dto.tfc;

import java.io.Serializable;

/**
 * Created by sql on 2016/11/15.
 */
public class TransportNoDTO implements Serializable {

    private String transportNo;

    public String getTransportNo() {
        return transportNo;
    }

    public void setTransportNo(String transportNo) {
        this.transportNo = transportNo;
    }

    @Override
    public String toString() {
        return "TransportNoDTO{" +
                "transportNo='" + transportNo + '\'' +
                '}';
    }
}
