package com.xescm.ofc.feign.client;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.model.dto.addr.QueryAddress;
import com.xescm.ofc.feign.api.addr.AddressInterface;
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
 * Created by lyh on 2016/11/10.
 */
@Service
public class FeignAddressInterfaceClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignAddressInterfaceClient.class);

    @Resource
    RestConfig restConfig;

    @Resource
    private AuthRequestInterceptor authRequestInterceptor;



    public AddressInterface getApi() {
        AddressInterface res = Feign.builder()
                .requestInterceptor(authRequestInterceptor)
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(AddressInterface.class, restConfig.getAddrUrl());
        return res;
    }
    public Wrapper<?> queryAddressByCodeAndType(QueryAddress queryAddress){
        logger.debug("==>查询四级地址 queryAddress={}", queryAddress);
        Wrapper<?> wrapper = getApi().queryAddressByCodeAndType(queryAddress);
        return wrapper;
    }
}
