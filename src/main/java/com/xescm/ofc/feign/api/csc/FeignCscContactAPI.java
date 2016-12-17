package com.xescm.ofc.feign.api.csc;

import com.github.pagehelper.PageInfo;
import com.xescm.ofc.model.dto.csc.CscContantAndCompanyDto;
import com.xescm.ofc.model.dto.csc.CscContantAndCompanyResponseDto;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Headers;
import feign.RequestLine;

import java.util.List;

/**
 * Created by gsfeng on 2016/10/18.
 */
public interface FeignCscContactAPI {

    /**
     * 对当前客户的收/或货方信息进行查询[不分页]
     * @param cscContantAndCompanyDto 参数
     * @return
     */
    @RequestLine("POST /api/csc/contact/queryCscReceivingInfoList")
    @Headers("Content-Type: application/json")
    public Wrapper<List<CscContantAndCompanyResponseDto>> queryCscReceivingInfoList(CscContantAndCompanyDto cscContantAndCompanyDto);

    @RequestLine("POST /api/csc/contact/addCscContantAndCompany")
    @Headers("Content-Type: application/json")
    @Deprecated
    public Wrapper<?> addCscContantAndCompany(CscContantAndCompanyDto cscContantAndCompanyDto);

    /**
     * 对当前客户的收/或货方信息进行查询[分页]
     * @param cscContantAndCompanyDto 参数
     * @return
     */
    @RequestLine("POST /api/csc/contact/queryCscReceivingInfoListWithPage")
    @Headers("Content-Type: application/json")
    public Wrapper<PageInfo<CscContantAndCompanyResponseDto>> queryCscReceivingInfoListWithPage(CscContantAndCompanyDto cscContantAndCompanyDto);

}
