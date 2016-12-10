package com.xescm.ofc.feign.api.csc;

import com.xescm.ofc.model.dto.csc.CscContantAndCompanyDto;
import com.xescm.ofc.model.vo.csc.CscContantAndCompanyVo;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Headers;
import feign.RequestLine;

import java.util.List;

/**
 * Created by gsfeng on 2016/10/18.
 */
public interface FeignCscContactAPI {

    @RequestLine("POST /api/csc/contact/queryCscReceivingInfoList")
    @Headers("Content-Type: application/json")
    public Wrapper<List<CscContantAndCompanyVo>> queryCscReceivingInfoList(CscContantAndCompanyDto cscContantAndCompanyDto);

    @RequestLine("POST /api/csc/contact/addCscContantAndCompany")
    @Headers("Content-Type: application/json")
    @Deprecated
    public Wrapper<?> addCscContantAndCompany(CscContantAndCompanyDto cscContantAndCompanyDto);
 
}
