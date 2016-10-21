package com.xescm.ofc.feign.api;

import com.xescm.ofc.domain.dto.CscSupplierInfoDto;
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

    @RequestLine("POST /api/csc/supplier/addSupplierBySupplierCode")
    @Headers("Content-Type: application/json")
    public Wrapper<?> addSupplierBySupplierCode(CscSupplierInfoDto cscSupplierInfoDto);

    @Headers("Content-Type: application/json")
    @RequestLine("POST /api/csc/supplier/modifySupplierBySupplierCode")
    public Wrapper<?> modifySupplierBySupplierCode(CscSupplierInfoDto cscSupplierInfoDto);

}
