package com.xescm.ofc.feign.api.rmc;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.model.dto.rmc.RmcCompanyLineQO;
import com.xescm.ofc.model.vo.rmc.RmcCompanyLineVo;
import feign.Headers;
import feign.RequestLine;

import java.util.List;

/**
 * Created by wanghw on 2016/11/4.
 */
public interface FeignRmcCompanyAPI {

    /**
     * 线路资源查询
     * @param rmcCompanyLineQO
     * @return
     */
    @RequestLine("POST /api/rmc/company/queryCompanyLine")
    @Headers("Content-Type: application/json")
    public Wrapper<List<RmcCompanyLineVo>> queryCompanyLine(RmcCompanyLineQO rmcCompanyLineQO);
}
