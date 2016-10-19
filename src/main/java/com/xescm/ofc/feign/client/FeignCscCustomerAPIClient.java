package com.xescm.ofc.feign.client;
import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.domain.dto.CscContantAndCompanyDto;
import com.xescm.ofc.feign.api.FeignCscCustomerAPI;
import com.xescm.uam.domain.feign.AuthRequestInterceptor;
import com.xescm.uam.domain.feign.FeignUamShiroAPI;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;

/**
 * Created by lyh on 2016/10/19.
 */
public class FeignCscCustomerAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignCscCustomerAPI.class);
    /*@Value("${restful.uamUrl}")
    private String uamUrl;*/
    @Resource
    RestConfig restConfig;


    public FeignCscCustomerAPI getApi() {
        FeignCscCustomerAPI res = Feign.builder()
                .requestInterceptor(new AuthRequestInterceptor()).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder()).target(FeignCscCustomerAPI.class, restConfig.getCscUrl());
        return res;
    }

    public Wrapper<?> queryCscReceivingInfoList(CscContantAndCompanyDto cscContantAndCompanyDto){
        logger.debug("==>查询客户联系人 cscContantAndCompanyDto={}", cscContantAndCompanyDto);
        Wrapper<?> wrapper = getApi().queryCscReceivingInfoList(cscContantAndCompanyDto);
        return wrapper;
    }

    public Wrapper<?> addCscContantAndCompany(CscContantAndCompanyDto cscContantAndCompanyDto){
        logger.debug("==>添加客户联系人 cscContantAndCompanyDto={}", cscContantAndCompanyDto);
        Wrapper<?> wrapper = getApi().addCscContantAndCompany(cscContantAndCompanyDto);
        return wrapper;
    }

    public Wrapper<?> modifyCscContantAndCompany(CscContantAndCompanyDto cscContantAndCompanyDto){
        logger.debug("==>添加客户联系人 cscContantAndCompanyDto={}", cscContantAndCompanyDto);
        Wrapper<?> wrapper = getApi().modifyCscContantAndCompany(cscContantAndCompanyDto);
        return wrapper;
    }






}
