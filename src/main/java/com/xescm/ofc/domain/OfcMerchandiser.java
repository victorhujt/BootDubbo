package com.xescm.ofc.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "ofc_merchandiser")
public class OfcMerchandiser {
    /**
     * 开单员
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String merchandiser;

    /**
     * 获取开单员
     *
     * @return merchandiser - 开单员
     */
    public String getMerchandiser() {
        return merchandiser;
    }

    /**
     * 设置开单员
     *
     * @param merchandiser 开单员
     */
    public void setMerchandiser(String merchandiser) {
        this.merchandiser = merchandiser;
    }
}