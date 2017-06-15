package com.xescm.ofc.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

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

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取配置的名称
     *
     * @return name - 配置的名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置配置的名称
     *
     * @param name 配置的名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取修改人名称
     *
     * @return modifier_cn - 修改人名称
     */
    public String getModifierCn() {
        return modifierCn;
    }

    /**
     * 设置修改人名称
     *
     * @param modifierCn 修改人名称
     */
    public void setModifierCn(String modifierCn) {
        this.modifierCn = modifierCn;
    }

    /**
     * 获取配置名称对应的值
     *
     * @return value - 配置名称对应的值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置配置名称对应的值
     *
     * @param value 配置名称对应的值
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取修改备注
     *
     * @return modify_rmk - 修改备注
     */
    public String getModifyRmk() {
        return modifyRmk;
    }

    /**
     * 设置修改备注
     *
     * @param modifyRmk 修改备注
     */
    public void setModifyRmk(String modifyRmk) {
        this.modifyRmk = modifyRmk;
    }

    /**
     * 获取备注
     *
     * @return rmk - 备注
     */
    public String getRmk() {
        return rmk;
    }

    /**
     * 设置备注
     *
     * @param rmk 备注
     */
    public void setRmk(String rmk) {
        this.rmk = rmk;
    }
}