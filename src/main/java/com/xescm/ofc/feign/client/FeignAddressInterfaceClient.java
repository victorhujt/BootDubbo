package com.xescm.ofc.feign.client;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.domain.dto.addr.QueryAddress;
import com.xescm.ofc.feign.api.AddressInterface;
import com.xescm.ofc.feign.api.FeignCscCustomerAPI;
import com.xescm.uam.domain.feign.AuthRequestInterceptor;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by lyh on 2016/11/10.
 */
public class FeignAddressInterfaceClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignCscCustomerAPI.class);

    @Resource
    RestConfig restConfig;


    public AddressInterface getApi() {
        AddressInterface res = Feign.builder()
                .requestInterceptor(new AuthRequestInterceptor()).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(AddressInterface.class, restConfig.getAddrUrl());
        return res;
    }
    Wrapper<?> queryAddressByCodeAndType(QueryAddress queryAddress){
        logger.debug("==>查询四级地址 queryAddress={}", queryAddress);
        Wrapper<?> wrapper = getApi().queryAddressByCodeAndType(queryAddress);
        return wrapper;
    }
}
