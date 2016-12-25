package com.xescm.ofc.model.dto.csc;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * 封装联系人信息以及联系人所属的公司
 * Created by gsfeng on 2016/10/18.
 */
@Data
public class CscContantAndCompanyInportDto implements Serializable {


    private static final long serialVersionUID = 5167203485913781536L;
    /**
     * 客户编码
     */
    private String custCode;
    /**
     * 类型。1、企业公司；2、个人。默认企业
     */
    private String type;

    /**
     * 联系人所属公司编码
     */
    @Column(name = "contact_company_code")
    private String contactCompanyCode;

    /**
     * 联系人所属公司
     */
    @Column(name = "contact_company_name")
    private String contactCompanyName;

    /**
     * 联系人编码
     */
    @Column(name = "contact_code")
    private String contactCode;

    /**
     * 联系人姓名
     */
    @Column(name = "contact_name")
    private String contactName;

    /**
     * 联系电话
     */
    private String phone;
    /**
     * 省code
     */
    private String province;

    /**
     * 省名称
     */
    @Column(name = "province_name")
    private String provinceName;

    /**
     * 市code
     */
    private String city;

    /**
     * 市名称
     */
    @Column(name = "city_name")
    private String cityName;

    /**
     * 区code
     */
    private String area;

    /**
     * 区名称
     */
    @Column(name = "area_name")
    private String areaName;

    /**
     * 乡镇街道code
     */
    private String street;

    /**
     * 乡镇街道名称
     */
    @Column(name = "street_name")
    private String streetName;

    /**
     * 地址
     */
    private String address;
    /**
     * 四级地址
     */
    private String citypickers;

    private String userId;
    private String userName;


}
