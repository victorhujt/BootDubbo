package com.xescm.ofc.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description   分页查询Dto
 *
 * @Author        zhangft
 * @CreateDate    2016/12/19 20:48
 */
@Data
public class PageInfoDto implements Serializable{

    private static final long serialVersionUID = 5435369060293337417L;

    //当前页
    private int pNum;

    //每页的数量
    private int pSize;

}
