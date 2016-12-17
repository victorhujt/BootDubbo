package com.xescm.ofc.feign.api.rmc;

import com.xescm.ofc.model.vo.rmc.RmcServiceCoverageForOrderVo;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Headers;
import feign.RequestLine;

import java.util.List;

/**
 * Created by wanghw on 2016/12/17.
 */
public interface FeignRMcServiceCoverageAPI {

    @RequestLine("POST /api/rmc/serviceCoverage/queryServiceCoverageListForOrder")
    @Headers("Content-Type: application/json")
    Wrapper<List<RmcServiceCoverageForOrderVo>> queryServiceCoverageListForOrder(RmcServiceCoverageForOrderVo rmcServiceCoverageQo);
}
