package com.xescm.ofc.domain.dto.wms;

/**
 * Created by hiyond on 2016/11/23.
 */
public class AddressDto {

    private String provinceName;

    private String cityName;

    private String districtName;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
}
