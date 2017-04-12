package com.xescm.ofc.domain;

import java.math.BigDecimal;

/**
 * Created by hujintao on 2017/3/28.
 */
public class OrderCountResult {
    /**
     * 大区名称
     */
    private String areaName;
    /**
     * 大区编码
     */
    private String areaCode;
    /**
     * 基地名称
     */
    private String baseName;
    /**
     * 基地编码
     */
    private String baseCode;
    /**
     * 订单总数
     */
    private BigDecimal orderCount;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }

    public BigDecimal getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(BigDecimal orderCount) {
        this.orderCount = orderCount;
    }


}
