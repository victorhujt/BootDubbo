package com.xescm.ofc.feign.api.wms;

import com.xescm.ofc.model.dto.wms.AddressDto;
import feign.Headers;
import feign.RequestLine;

/**
 * 获取省市区编码
 * Created by hiyond on 2016/11/23.
 */
public interface AddressCodeInterface {

    @RequestLine("POST /api/addr/citypicker/findCodeByName")
    @Headers("Content-Type: application/json")
    String findCodeByName(AddressDto addressDto);

}
