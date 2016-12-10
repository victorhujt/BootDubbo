package com.xescm.ofc.model.dto.rmc;

/**
 * 区域覆盖范围查询实体类
 */
public class RmcDistrictQO {

    /** 省编码 */
    private String provinceCode;

    /** 市编码 */
    private String cityCode;

    /** 区编码 */
    private String districtCode;

    /** 县编码 */
    private String countyCode;

    public RmcDistrictQO(){}

    public RmcDistrictQO(String provinceCode,String cityCode,String districtCode,String countyCode){
        this.provinceCode = provinceCode;
        this.cityCode = cityCode;
        this.districtCode = districtCode;
        this.countyCode = countyCode;
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

    @Override
    public String toString() {
        return "RmcDistrictQO{" +
                "provinceCode='" + provinceCode + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", districtCode='" + districtCode + '\'' +
                ", countyCode='" + countyCode + '\'' +
                '}';
    }
}
