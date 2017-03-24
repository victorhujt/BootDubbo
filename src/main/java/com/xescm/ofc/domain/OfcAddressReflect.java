package com.xescm.ofc.domain;

import javax.persistence.*;

@Table(name = "ofc_address_reflect")
public class OfcAddressReflect {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 完整地址
     */
    private String address;

    /**
     * 省名称
     */
    private String province;

    /**
     * 省编码
     */
    @Column(name = "province_code")
    private String provinceCode;

    /**
     * 市名称
     */
    private String city;

    /**
     * 市编码
     */
    @Column(name = "city_code")
    private String cityCode;

    /**
     * 区名称
     */
    private String district;

    /**
     * 区编码
     */
    @Column(name = "district_code")
    private String districtCode;

    /**
     * 街道名称
     */
    private String street;

    /**
     * 街道编码
     */
    @Column(name = "street_code")
    private String streetCode;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取完整地址
     *
     * @return address - 完整地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置完整地址
     *
     * @param address 完整地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取省名称
     *
     * @return province - 省名称
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省名称
     *
     * @param province 省名称
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取省编码
     *
     * @return province_code - 省编码
     */
    public String getProvinceCode() {
        return provinceCode;
    }

    /**
     * 设置省编码
     *
     * @param provinceCode 省编码
     */
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    /**
     * 获取市名称
     *
     * @return city - 市名称
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置市名称
     *
     * @param city 市名称
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取市编码
     *
     * @return city_code - 市编码
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * 设置市编码
     *
     * @param cityCode 市编码
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    /**
     * 获取区名称
     *
     * @return district - 区名称
     */
    public String getDistrict() {
        return district;
    }

    /**
     * 设置区名称
     *
     * @param district 区名称
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * 获取区编码
     *
     * @return district_code - 区编码
     */
    public String getDistrictCode() {
        return districtCode;
    }

    /**
     * 设置区编码
     *
     * @param districtCode 区编码
     */
    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    /**
     * 获取街道名称
     *
     * @return street - 街道名称
     */
    public String getStreet() {
        return street;
    }

    /**
     * 设置街道名称
     *
     * @param street 街道名称
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * 获取街道编码
     *
     * @return street_code - 街道编码
     */
    public String getStreetCode() {
        return streetCode;
    }

    /**
     * 设置街道编码
     *
     * @param streetCode 街道编码
     */
    public void setStreetCode(String streetCode) {
        this.streetCode = streetCode;
    }
}