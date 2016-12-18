package com.xescm.ofc.model.vo.rmc;

import javax.persistence.Column;

/**
 * Created by wanghw on 2016/12/16.
 */
public class RmcServiceCoverageForOrderVo {

    /**
     * 基地名称
     */
    private String baseName;

    /**
     * 基地编码
     */
    private String baseCode;

    /**
     * 仓库名称
     */
    private String warehouseName;

    /**
     * 仓库编码
     */
    private String warehouseCode;

    /**
     * 1:取货 2：不取货
     */
    private Integer isPickup;

    /**
     * 1:派送 2：不派送
     */
    private Integer isDispatch;

    /**
     * 1:基地 2:仓库
     */
    private Integer coverageType;

    /**
     * TC仓、DC仓或（TC仓和DC仓）
     */
    private String warehouseType;

    /** 省编码 */
    private String provinceCode;

    /** 市编码 */
    private String cityCode;

    /** 区编码 */
    private String districtCode;

    /** 街道编码 */
    private String streetCode;

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

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public Integer getIsPickup() {
        return isPickup;
    }

    public void setIsPickup(Integer isPickup) {
        this.isPickup = isPickup;
    }

    public Integer getIsDispatch() {
        return isDispatch;
    }

    public void setIsDispatch(Integer isDispatch) {
        this.isDispatch = isDispatch;
    }

    public Integer getCoverageType() {
        return coverageType;
    }

    public void setCoverageType(Integer coverageType) {
        this.coverageType = coverageType;
    }

    public String getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(String warehouseType) {
        this.warehouseType = warehouseType;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(String streetCode) {
        this.streetCode = streetCode;
    }

    @Override
    public String toString() {
        return "RmcServiceCoverageForOrderVo{" +
                "baseName='" + baseName + '\'' +
                ", baseCode='" + baseCode + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", warehouseCode='" + warehouseCode + '\'' +
                ", isPickup=" + isPickup +
                ", isDispatch=" + isDispatch +
                ", coverageType=" + coverageType +
                ", warehouseType='" + warehouseType + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", districtCode='" + districtCode + '\'' +
                ", streetCode='" + streetCode + '\'' +
                '}';
    }
}
