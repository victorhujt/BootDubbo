package com.xescm.ofc.feign.client;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.ofc.domain.OfcFinanceInformation;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.ac.PushOrderApi;
import com.xescm.ofc.model.dto.ac.AcOrderDto;
import com.xescm.uam.domain.feign.AuthRequestInterceptor;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 推送订单到结算中心
 * Created by hiyond on 2016/12/11.
 */
@Service
public class FeignPushOrderApiClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignPushOrderApiClient.class);

    @Resource
    RestConfig restConfig;

    @Resource
    private AuthRequestInterceptor authRequestInterceptor;
    public PushOrderApi getApi() {
        PushOrderApi res = Feign.builder()
                .requestInterceptor(authRequestInterceptor).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(PushOrderApi.class, restConfig.getWhcUrl());
        return res;
    }

    /**
     * 推送订单信息到结算中心
     *
     * @param ofcFundamentalInformation 订单中心基本信息
     * @param ofcFinanceInformation     财务信息
     * @param ofcDistributionBasicInfo  订单中心配送基本信息
     * @param ofcGoodsDetailsInfos      货品明细信息
     */
    public Wrapper<?> pullOfcOrder(OfcFundamentalInformation ofcFundamentalInformation,
                                   OfcFinanceInformation ofcFinanceInformation,
                                   OfcDistributionBasicInfo ofcDistributionBasicInfo,
                                   List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos) {
        logger.info("推送订单信息到结算中心");
        if (ofcFundamentalInformation == null) {
            throw new BusinessException("参数ofcFundamentalInformation不能为空");
        }
        if (ofcFinanceInformation == null) {
            throw new BusinessException("参数ofcFinanceInformation不能为空");
        }
        if (ofcDistributionBasicInfo == null) {
            throw new BusinessException("参数ofcDistributionBasicInfo不能为空");
        }
        if (CollectionUtils.isEmpty(ofcGoodsDetailsInfos)) {
            throw new BusinessException("参数ofcGoodsDetailsInfos不能为空");
        }
        AcOrderDto acOrderDto = new AcOrderDto();
        acOrderDto.setOfcFundamentalInformation(ofcFundamentalInformation);
        acOrderDto.setOfcFinanceInformation(ofcFinanceInformation);
        acOrderDto.setOfcDistributionBasicInfo(ofcDistributionBasicInfo);
        acOrderDto.setOfcGoodsDetailsInfoList(ofcGoodsDetailsInfos);
        Wrapper<?> wrapper = null;
        try {
            wrapper = getApi().pullOfcOrder(acOrderDto);
        } catch (Exception ex) {
            logger.error("推送订单信息到结算中心失败：", ex.getMessage(), ex);
            throw new BusinessException(ex.getMessage(), ex);
        }
        return wrapper;
    }


}
