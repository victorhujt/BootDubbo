package com.xescm.ofc.model.dto.csc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tbl_csc_contact")
@Data
public class CscContact implements Serializable {
    private static final long serialVersionUID = 5343469432572636524L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 流水号
     */
    @Column(name = "serial_no")
    private String serialNo;

    /**
     * 流水号
     */
    @Column(name = "company_serial_no")
    private String companySerialNo;

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
     * 传真
     */
    private String fax;

    /**
     * Email
     */
    private String email;

    /**
     * 邮编
     */
    @Column(name = "post_code")
    private String postCode;

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
     * 详细地址
     */
    @Column(name = "detail_address")
    private String detailAddress;

    /**
     * 联系人职务
     */
    @Column(name = "contact_job")
    private String contactJob;

    /**
     * 用途。1、收货方；2、发货方；3、收发货方;4、无
     */
    private String purpose;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建人ID
     */
    @Column(name = "creator_id")
    private String creatorId;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

    /**
     * 最近操作人
     */
    @Column(name = "last_operator")
    private String lastOperator;

    /**
     * 最后操作人ID
     */
    @Column(name = "last_operator_id")
    private String lastOperatorId;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 删除（1 - 已删除；0未删除）
     */
    private Integer yn = 0;

    /**
     * 客户编码
     */
    @Transient
    private String customerCode;

    /**
     * 公司名称
     */
    @Transient
    private String contactCompanyName;

    /**
     * 类型
     */
    @Transient
    private String type;



}