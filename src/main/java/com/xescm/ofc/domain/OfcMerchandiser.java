package com.xescm.ofc.domain;

import javax.persistence.*;

@Table(name = "ofc_merchandiser")
public class OfcMerchandiser {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 开单员名称
     */
    private String merchandiser;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取开单员名称
     *
     * @return merchandiser - 开单员名称
     */
    public String getMerchandiser() {
        return merchandiser;
    }

    /**
     * 设置开单员名称
     *
     * @param merchandiser 开单员名称
     */
    public void setMerchandiser(String merchandiser) {
        this.merchandiser = merchandiser;
    }
}