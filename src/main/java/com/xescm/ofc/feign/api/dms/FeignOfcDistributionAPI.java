package com.xescm.ofc.feign.api.dms;

import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Headers;
import feign.RequestLine;

/**
 * Created by gsfeng on 2016/11/10.
 */
public interface FeignOfcDistributionAPI {

    @RequestLine("POST /api/ofc/distribution/addDistributionBasicInfo")
    @Headers("Content-Type: application/json")
    public Wrapper<?> addDistributionBasicInfo(OfcDistributionBasicInfo ofcDistributionBasicInfo);
}
