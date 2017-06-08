package com.xescm.ofc.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "ofc_enumeration")
public class OfcEnumeration {

    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    /**
     * 所属系统
     */
    @Column(name = "enum_sys")
    private String enumSys;

    /**
     * 枚举类型
     */
    @Column(name = "enum_type")
    private String enumType;

    /**
     * 枚举名称
     */
    @Column(name = "enum_name")
    private String enumName;

    /**
     * 枚举值
     */
    @Column(name = "enum_value")
    private String enumValue;

}