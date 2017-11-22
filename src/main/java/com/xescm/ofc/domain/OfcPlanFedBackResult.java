package com.xescm.ofc.domain;

import lombok.Data;

/**
 * Created by lyh on 2016/10/10.
 */
@Data
public class OfcPlanFedBackResult {
    /**运输单号**/
    private String transportNo;
    /**结果**/
    private String result;
    /**原因**/
    private String reason;

}
