package com.xescm.ofc.feign.client;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.csc.FeignCscWarehouseAPI;
import com.xescm.ofc.feign.api.dms.FeignOfcDistributionAPI;
import com.xescm.uam.domain.feign.AuthRequestInterceptor;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lyh on 2016/11/14.
 */
@Service
public class FeignOfcDistributionAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignCscWarehouseAPI.class);
    @Resource
    RestConfig restConfig;

    public FeignOfcDistributionAPI getApi() {
        FeignOfcDistributionAPI res = Feign.builder()
                .requestInterceptor(new AuthRequestInterceptor()).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder()).target(FeignOfcDistributionAPI.class, restConfig.getDmsUrl());
        return res;
    }

    public Wrapper<?> addDistributionBasicInfo(OfcDistributionBasicInfo ofcDistributionBasicInfo){
        logger.debug("==>推送卡班订单 ofcDistributionBasicInfo={}", ofcDistributionBasicInfo);
        if(null == ofcDistributionBasicInfo){
            throw new BusinessException("参数为空");
        }
        Wrapper<?> wrapper = null;
        try {
            wrapper = getApi().addDistributionBasicInfo(ofcDistributionBasicInfo);
        }catch (Exception ex){
            throw new BusinessException(ex.getMessage(), ex);
        }
        return wrapper;
    }
}
