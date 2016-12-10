package com.xescm.ofc.feign.client;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.csc.FeignCscContactAPI;
import com.xescm.ofc.feign.api.csc.FeignCscCustomerAPI;
import com.xescm.ofc.model.dto.csc.CscContantAndCompanyDto;
import com.xescm.ofc.model.vo.csc.CscContantAndCompanyVo;
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
 * Created by lyh on 2016/12/10.
 */
@Service
public class FeignCscContactAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignCscCustomerAPI.class);
    /*@Value("${restful.uamUrl}")
    private String uamUrl;*/
    @Resource
    RestConfig restConfig;


    public FeignCscContactAPI getApi() {
        FeignCscContactAPI res = Feign.builder()
                .requestInterceptor(new AuthRequestInterceptor()).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(FeignCscContactAPI.class, restConfig.getCscUrl());
        return res;
    }

    public Wrapper<List<CscContantAndCompanyVo>> queryCscReceivingInfoList(CscContantAndCompanyDto cscContantAndCompanyDto){
        logger.debug("==>查询客户联系人 cscContantAndCompanyDto={}", cscContantAndCompanyDto);
        if(null == cscContantAndCompanyDto){
            throw new BusinessException("参数为空");
        }
        Wrapper<List<CscContantAndCompanyVo>> wrapper =  getApi().queryCscReceivingInfoList(cscContantAndCompanyDto);
        return wrapper;
    }
}
