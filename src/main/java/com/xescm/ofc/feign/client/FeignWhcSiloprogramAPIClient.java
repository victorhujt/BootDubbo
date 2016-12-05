package com.xescm.ofc.feign.client;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.whc.FeignWhcOrderAPI;
import com.xescm.ofc.model.dto.whc.CancelWhcOrderDTO;
import com.xescm.ofc.utils.Response;
import com.xescm.uam.domain.feign.AuthRequestInterceptor;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by victor on 2016/12/2.
 */
@Service
public class FeignWhcSiloprogramAPIClient {

    @Resource
    RestConfig restConfig;

    public FeignWhcOrderAPI getApi() {
        FeignWhcOrderAPI res = Feign.builder()
                .requestInterceptor(new AuthRequestInterceptor()).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(FeignWhcOrderAPI.class, restConfig.getWhcUrl());
        return res;
    }

    public Response inOrderCancel(CancelWhcOrderDTO cancelWhcOrderDTO){
        Response response = null;
        if(null == cancelWhcOrderDTO){
            throw new BusinessException("参数为空");
        }
        try {
            response = getApi().inOrderCancel(cancelWhcOrderDTO);
        }catch (Exception ex){
            throw new BusinessException(ex.getMessage());
        }
        return response;
    }

    public Response outOrderCancel(CancelWhcOrderDTO cancelWhcOrderDTO){
        Response response = null;
        if(null == cancelWhcOrderDTO){
            throw new BusinessException("参数为空");
        }
        try {
            response = getApi().outOrderCancel(cancelWhcOrderDTO);
        }catch (Exception ex){
            throw new BusinessException(ex.getMessage());
        }
        return response;
    }









}
