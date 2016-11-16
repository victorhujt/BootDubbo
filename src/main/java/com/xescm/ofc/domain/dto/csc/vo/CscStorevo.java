package com.xescm.ofc.domain.dto.csc.vo;

import java.io.Serializable;

/**
 * Created by gsfeng on 2016/10/18.
 */
public class CscStorevo implements Serializable {
    private static final long serialVersionUID = 2506388345067865258L;
    /**
     * 主键Id
     */
    private String id;

    /**
     * 店铺编码
     */
    private String storeCode;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 平台类型。1、线下；2、天猫3、京东；4、鲜易网
     */
    private String platformType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    @Override
    public String toString() {
        return "CscStorevo{" +
                "id='" + id + '\'' +
                ", storeCode='" + storeCode + '\'' +
                ", storeName='" + storeName + '\'' +
                ", platformType='" + platformType + '\'' +
                '}';
    }
}
