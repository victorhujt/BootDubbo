package com.xescm.ofc.feign.api.rmc;

import com.xescm.ofc.domain.dto.rmc.RmcCompanyLineQO;
import com.xescm.ofc.domain.dto.rmc.RmcCompanyLineVo;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Headers;
import feign.RequestLine;

import java.util.List;

/**
 * Created by wanghw on 2016/11/4.
 */
public interface FeignRmcCompanyAPI {

    @RequestLine("POST /api/rmc/company/queryCompanyLine")
    @Headers("Content-Type: application/json")
    public Wrapper<List<RmcCompanyLineVo>> queryCompanyLine(RmcCompanyLineQO rmcCompanyLineQO);
}
