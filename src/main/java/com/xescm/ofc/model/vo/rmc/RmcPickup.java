package com.xescm.ofc.model.vo.rmc;

/**
 * 上门取件实体类
 * */
public class RmcPickup {

    /** 主键ID */
    private String id;

    /** 上门取件 */
    private String goodType;

    /** 仓库编码（TC仓） */
    private String wareHouseCode;

    /** 调度中心（基地）编码 */
    private String dispatchCode;

    /** 省编码 */
    private String provinceCode;

    /** 市编码 */
    private String cityCode;

    /** 区编码 */
    private String districtCode;

    /** 县编码 */
    private String countyCode;

    /** 是否生效 */
    private String isEffect;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodType() {
        return goodType;
    }

    public void setGoodType(String goodType) {
        this.goodType = goodType;
    }

    public String getWareHouseCode() {
        return wareHouseCode;
    }

    public void setWareHouseCode(String wareHouseCode) {
        this.wareHouseCode = wareHouseCode;
    }

    public String getDispatchCode() {
        return dispatchCode;
    }

    public void setDispatchCode(String dispatchCode) {
        this.dispatchCode = dispatchCode;
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

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getIsEffect() {
        return isEffect;
    }

    public void setIsEffect(String isEffect) {
        this.isEffect = isEffect;
    }
}