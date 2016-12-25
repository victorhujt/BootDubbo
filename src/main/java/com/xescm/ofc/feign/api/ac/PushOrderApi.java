package com.xescm.ofc.feign.api.ac;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.ofc.domain.OfcFinanceInformation;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.model.dto.ac.AcOrderDto;
import com.xescm.ofc.model.dto.ac.CancelOfcOrderDto;
import feign.Headers;
import feign.RequestLine;

import java.util.List;

/**
 * 推送订单到结算中心
 * Created by hiyond on 2016/12/11.
 */
public interface PushOrderApi {

    //推送订单信息到结算中心
    @RequestLine("POST /api/ofc/order/pullOfcOrder")
    @Headers("Content-Type: application/json")
    Wrapper<?> pullOfcOrder(AcOrderDto acOrderDto);

    /**
     * 订单中心取消订单
     * @param cancelOfcOrderDto
     * @return
     */
    @RequestLine("POST /api/ofc/order/cancelOfcOrder")
    @Headers("Content-Type: application/json")
    Wrapper<?> cancelOfcOrder(CancelOfcOrderDto cancelOfcOrderDto);

}
