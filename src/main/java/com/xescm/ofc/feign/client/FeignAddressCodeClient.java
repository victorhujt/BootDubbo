package com.xescm.ofc.feign.client;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.domain.dto.wms.AddressDto;
import com.xescm.ofc.feign.api.addr.AddressInterface;
import com.xescm.ofc.feign.api.wms.AddressCodeInterface;
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
 * Created by hiyond on 2016/11/23.
 */
@Service
public class FeignAddressCodeClient {

    private static final Logger logger = LoggerFactory.getLogger(FeignAddressInterfaceClient.class);

    @Resource
    RestConfig restConfig;


    public AddressCodeInterface getApi() {
        AddressCodeInterface res = Feign.builder()
                .requestInterceptor(new AuthRequestInterceptor())
                .encoder(new JacksonEncoder())
                .target(AddressCodeInterface.class, restConfig.getAddrUrl());
        return res;
    }

    public String findCodeByName(AddressDto addressDto) {
        return getApi().findCodeByName(addressDto);
    }

}
