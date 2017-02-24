package com.xescm.ofc.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "ofc_merchandiser")
public class OfcMerchandiser {
    @Id
    private Integer id;

    /**
     * 开单员名称
     */
    private String merchandiser;

}