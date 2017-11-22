package com.xescm.ofc.domain;

import lombok.Data;

import javax.persistence.*;
@Data
@Table(name = "ofc_address_reflect")
public class OfcAddressReflect {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "UUID")
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
}