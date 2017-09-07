package com.xescm.ofc.model.dto.ofc;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * Created by lyh on 2017/7/25.
 */
@Data
public class OfcBatchCreateByRedisDTO implements Serializable{
    private String batchgoodsKey;
    private String batchconsingeeKey;
}
