package com.xescm.ofc.feign.api.csc;

import com.xescm.ofc.model.dto.csc.*;
import com.xescm.ofc.model.vo.csc.CscContantAndCompanyVo;
import com.xescm.ofc.model.vo.csc.CscCustomerVo;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Headers;
import feign.RequestLine;

import java.util.List;

/**
 * Created by gsfeng on 2016/10/18.
 */
public interface FeignCscCustomerAPI {

    @RequestLine("POST /api/csc/customer/queryCscReceivingInfoList")
    @Headers("Content-Type: application/json")
    public Wrapper<List<CscContantAndCompanyVo>> queryCscReceivingInfoList(CscContantAndCompanyDto cscContantAndCompanyDto);

    @RequestLine("POST /api/csc/customer/addCscContantAndCompany")
    @Headers("Content-Type: application/json")
    public Wrapper<?> addCscContantAndCompany(CscContantAndCompanyDto cscContantAndCompanyDto);

    /*@RequestLine("POST /api/csc/customer/queryCustomerIdByGroupId")
    @Headers("Content-Type: application/json")AuthResDto
    public Wrapper<?> queryCustomerIdByGroupId(String groupId);*/
    @RequestLine("POST /api/csc/customer/queryCustomerIdByGroupId")
    @Headers("Content-Type: application/json")
    public Wrapper<?> queryCustomerIdByGroupId(QueryCustomerIdDto queryCustomerIdDto);

    @RequestLine("POST /api/csc/customer/queryCustomerByCustomerCodeOrId")
    @Headers("Content-Type: application/json")
    Wrapper<CscCustomerVo> queryCustomerByCustomerCodeOrId(QueryCustomerCodeDto queryCustomerCodeDto);

    @RequestLine("POST /api/csc/customer/queryCustomerByName")
    @Headers("Content-Type: application/json")
    public Wrapper<?> queryCustomerByName(QueryCustomerNameDto queryCustomerNameDto);

    @RequestLine("POST /api/csc/customer/QueryCustomerByNameAvgue")
    @Headers("Content-Type: application/json")
    public Wrapper<?> QueryCustomerByNameAvgue(QueryCustomerNameAvgueDto queryCustomerNameDto);
}