package com.xescm.ofc.feign.api.csc;

import com.xescm.ofc.domain.dto.csc.CscSupplierInfoDto;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Headers;
import feign.RequestLine;

import java.util.List;

/**
 * Created by Dzy on 2016/10/18.
 */
public interface FeignCscSupplierAPI {

    @RequestLine("POST /api/csc/supplier/querySupplierByAttribute")
    @Headers("Content-Type: application/json")
    public Wrapper<List<CscSupplierInfoDto>> querySupplierByAttribute(CscSupplierInfoDto cscSupplierInfoDto);

    @RequestLine("POST /api/csc/supplier/addSupplierByCustomerId")
    @Headers("Content-Type: application/json")
    public Wrapper<?> addSupplierBySupplierCode(CscSupplierInfoDto cscSupplierInfoDto);

}
