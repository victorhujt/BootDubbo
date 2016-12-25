package com.xescm.ofc.feign.client;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.model.dto.addr.QueryAddress;
import com.xescm.ofc.feign.api.addr.AddressInterface;
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
        Wrapper<?> wrapper = null;
        logger.debug("==>查询四级地址 queryAddress={}", queryAddress);
        try {
            wrapper = getApi().queryAddressByCodeAndType(queryAddress);
        }  catch (RetryableException ex) {
            logger.error("==>调用接口发生异常：调用查询四级地址接口(/api/addr/citypicker/findByCodeAndType)无法连接或超时. {}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "调用查询四级地址接口无法连接或超时！");
        } catch (Exception ex){
            logger.error("==>调用接口发生异常：调用查询四级地址接口(/api/addr/citypicker/findByCodeAndType). {}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "调用查询四级地址接口异常！");
        }

        return wrapper;
    }
}
