package com.xescm.ofc.model.dto.ofc;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * Created by lyh on 2017/7/18.
 */
@Data
public class OfcDistributionUploadDTO implements Serializable{
    private String custCode;
    private String templateType;
    private String modelMappingCode;
}
