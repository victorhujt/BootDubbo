package com.xescm.ofc.feign.api.rmc;

import com.xescm.ofc.model.dto.rmc.RmcCompanyLineQO;
import com.xescm.ofc.model.dto.rmc.RmcDistrictQO;
import com.xescm.ofc.model.vo.rmc.RmcCompanyLineVo;
import com.xescm.ofc.model.vo.rmc.RmcPickup;
import com.xescm.ofc.model.vo.rmc.RmcRecipient;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Headers;
import feign.RequestLine;

import java.util.List;

/**
 * Created by wanghw on 2016/11/4.
 */
public interface FeignRmcPickUpOrRecipientAPI {

    /**
     * 上门提货
     * @param rmcDistrictQO
     * @return
     */
    @RequestLine("POST /api/rmc/district/queryPickUp")
    @Headers("Content-Type: application/json")
    public Wrapper<List<RmcPickup>> queryPickUp(RmcDistrictQO rmcDistrictQO);

    /**
     * 二次配送
     * @param rmcDistrictQO
     * @return
     */
    @RequestLine("POST /api/rmc/district/queryRecipient")
    @Headers("Content-Type: application/json")
    public Wrapper<List<RmcRecipient>> queryRecipient(RmcDistrictQO rmcDistrictQO);
}
