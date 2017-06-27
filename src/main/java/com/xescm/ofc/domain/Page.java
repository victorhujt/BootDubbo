package com.xescm.ofc.domain;

import lombok.Data;

/**
 * Created by Liqd on 2017/3/29.
 */
@Data
public class Page<T> {

    private int pageNum;

    private int pageSize;

    private T param;
}
