package com.xescm.ofc.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
@Data
@Table(name = "ofc_runtime_property")
public class OfcRuntimeProperty {
    @Id
    private String id;

    /**
     * 配置的名称
     */
    private String name;

    /**
     * 修改人名称
     */
    @Column(name = "modifier_cn")
    private String modifierCn;

    /**
     * 配置名称对应的值
     */
    private String value;

    /**
     * 修改备注
     */
    @Column(name = "modify_rmk")
    private String modifyRmk;

    /**
     * 备注
     */
    private String rmk;
}