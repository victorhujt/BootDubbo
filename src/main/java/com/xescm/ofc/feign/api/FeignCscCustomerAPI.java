package com.xescm.ofc.feign.api;

import com.xescm.ofc.domain.dto.csc.CscContantAndCompanyDto;
import com.xescm.ofc.domain.dto.csc.QueryCustomerIdDto;
import com.xescm.ofc.domain.dto.csc.vo.CscContantAndCompanyVo;
import com.xescm.uam.domain.dto.AuthResDto;
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
}