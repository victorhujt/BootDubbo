package com.xescm.ofc.model.dto.ofc;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * Created by lyh on 2017/7/19.
 */
@Data
public class OfcDistributionExcelImportDTO implements Serializable{
    private String goodKey;
    private JSONArray jsonArray;
}
