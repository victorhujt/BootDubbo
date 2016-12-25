package com.xescm.ofc.feign.client;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.dms.FeignOfcDistributionAPI;
import com.xescm.ofc.model.dto.ofc.OfcDistributionBasicInfoDto;
import com.xescm.ofc.web.jwt.AuthRequestInterceptor;
import feign.Feign;
import feign.RetryableException;
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
    private static final Logger logger = LoggerFactory.getLogger(FeignOfcDistributionAPIClient.class);
    @Resource
    RestConfig restConfig;
    @Resource
    private AuthRequestInterceptor authRequestInterceptor;
    public FeignOfcDistributionAPI getApi() {
        FeignOfcDistributionAPI res = Feign.builder()
                .requestInterceptor(authRequestInterceptor).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder()).target(FeignOfcDistributionAPI.class, restConfig.getDmsUrl());
        return res;
    }

    public Wrapper<?> addDistributionBasicInfo(OfcDistributionBasicInfoDto ofcDistributionBasicInfoDto){
        logger.debug("==>推送卡班订单 ofcDistributionBasicInfo={}", ofcDistributionBasicInfoDto);
        if(null == ofcDistributionBasicInfoDto){
            throw new BusinessException("参数为空");
        }
        Wrapper<?> wrapper = null;
        try {
            wrapper = getApi().addDistributionBasicInfo(ofcDistributionBasicInfoDto);
        } catch (RetryableException ex) {
            logger.error("==>调用接口发生异常：调用推送卡班订单接口(/api/ofc/distribution/addDistributionBasicInfo)无法连接或超时. {}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "调用推送卡班订单接口无法连接或超时！");
        } catch (Exception ex){
            logger.error("==>调用接口发生异常：推送卡班订单接口(/api/ofc/distribution/addDistributionBasicInfo). {}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "调用推送卡班订单接口异常！");
        }
        return wrapper;
    }
}
