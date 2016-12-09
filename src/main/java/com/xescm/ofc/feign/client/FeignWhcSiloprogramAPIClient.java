package com.xescm.ofc.feign.client;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.whc.FeignWhcOrderAPI;
import com.xescm.ofc.model.dto.whc.CancelOrderDTO;
import com.xescm.ofc.utils.Response;
import com.xescm.uam.domain.feign.AuthRequestInterceptor;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

/**
 * Created by victor on 2016/12/2.
 */
@Service
public class FeignWhcSiloprogramAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignWhcOrderAPI.class);

    @Resource
    RestConfig restConfig;

    public FeignWhcOrderAPI getApi() {
        FeignWhcOrderAPI res = Feign.builder()
                .requestInterceptor(new AuthRequestInterceptor()).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(FeignWhcOrderAPI.class, restConfig.getWhcUrl());
        return res;
    }

    public Response cancelOrder(CancelOrderDTO cancelOrderDTO){
        Response response = null;
        if(null == cancelOrderDTO){
            throw new BusinessException("参数为空");
        }
        try {
            response = getApi().cancelOrder(cancelOrderDTO);
        }catch (Exception ex){
            throw new BusinessException(ex.getMessage());
        }
        return response;
    }
}
