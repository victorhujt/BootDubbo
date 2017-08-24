package com.xescm.ofc.model.dto.ofc;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * Created by lyh on 2017/7/19.
 */
@Data
public class QueryCustByNameDTO implements Serializable{
    private String custName;
    private Integer pageNum;
    private Integer pageSize;
}
