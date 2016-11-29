package com.xescm.ofc.feign.client;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.model.dto.csc.QueryStoreDto;
import com.xescm.ofc.model.vo.csc.CscStorevo;
import com.xescm.ofc.feign.api.csc.FeignCscStoreAPI;
import com.xescm.uam.domain.feign.AuthRequestInterceptor;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lyh on 2016/11/6.
 */
@Service
public class FeignCscStoreAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignCscStoreAPI.class);
    @Resource
    RestConfig restConfig;

    public FeignCscStoreAPI getApi() {
        FeignCscStoreAPI res = Feign.builder()
                .requestInterceptor(new AuthRequestInterceptor()).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder()).target(FeignCscStoreAPI.class, restConfig.getCscUrl());
        return res;
    }
    public Wrapper<List<CscStorevo>> getStoreByCustomerId(QueryStoreDto queryStoreDto){
        Wrapper<List<CscStorevo>> storeByCustomerId = getApi().getStoreByCustomerId(queryStoreDto);
        return  storeByCustomerId;
    }

}
