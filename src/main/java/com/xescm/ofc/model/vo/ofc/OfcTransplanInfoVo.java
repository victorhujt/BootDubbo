package com.xescm.ofc.model.vo.ofc;

import com.xescm.ofc.domain.OfcTransplanInfo;

/**
 * Created by victor on 2016/12/2.
 */
public class OfcTransplanInfoVo extends OfcTransplanInfo {
    private  String plannedSingleState;

    public String getPlannedSingleState() {
        return plannedSingleState;
    }

    public void setPlannedSingleState(String plannedSingleState) {
        this.plannedSingleState = plannedSingleState;
    }
}
