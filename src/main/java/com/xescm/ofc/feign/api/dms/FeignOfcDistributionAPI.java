package com.xescm.ofc.feign.api.dms;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.ofc.model.dto.ofc.OfcDistributionBasicInfoDto;
import feign.Headers;
import feign.RequestLine;

/**
 * Created by gsfeng on 2016/11/10.
 */
public interface FeignOfcDistributionAPI {

    /**
     * 推送卡班订单
     * @param ofcDistributionBasicInfo
     * @return
     */
    @RequestLine("POST /api/ofc/distribution/addDistributionBasicInfo")
    @Headers("Content-Type: application/json")
    public Wrapper<?> addDistributionBasicInfo(OfcDistributionBasicInfoDto ofcDistributionBasicInfo);
}
