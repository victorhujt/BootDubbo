package com.xescm.ofc.feign.client;

import com.github.pagehelper.PageInfo;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.csc.FeignCscContactAPI;
import com.xescm.ofc.model.dto.csc.CscContantAndCompanyDto;
import com.xescm.ofc.model.dto.csc.CscContantAndCompanyResponseDto;
import com.xescm.ofc.web.jwt.AuthRequestInterceptor;
import feign.Feign;
import feign.RetryableException;
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
    private static final Logger logger = LoggerFactory.getLogger(FeignCscContactAPI.class);

    @Resource
    RestConfig restConfig;
    @Resource
    private AuthRequestInterceptor authRequestInterceptor;

    public FeignCscContactAPI getApi() {
        FeignCscContactAPI res = Feign.builder()
                .requestInterceptor(authRequestInterceptor).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(FeignCscContactAPI.class, restConfig.getCscUrl());
        return res;
    }

    public Wrapper<List<CscContantAndCompanyResponseDto>> queryCscReceivingInfoList(CscContantAndCompanyDto cscContantAndCompanyDto){
        Wrapper<List<CscContantAndCompanyResponseDto>> wrapper = null;
        logger.debug("==>查询客户联系人 cscContantAndCompanyDto={}", cscContantAndCompanyDto);
        if(null == cscContantAndCompanyDto){
            throw new BusinessException("参数为空");
        }
        try {
            wrapper = getApi().queryCscReceivingInfoList(cscContantAndCompanyDto);
        } catch (RetryableException ex) {
            logger.error("==>调用接口发生异常：调用查询客户联系人接口(/api/csc/contact/queryCscReceivingInfoList)无法连接或超时. {}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "调用查询客户联系人接口无法连接或超时！");
        } catch (Exception ex) {
            logger.error("==>调用接口发生异常：查询客户联系人接口(/api/csc/contact/queryCscReceivingInfoList). {}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "调用查询客户联系人接口异常！");
        }
        return wrapper;
    }

    public Wrapper<PageInfo<CscContantAndCompanyResponseDto>> queryCscReceivingInfoListWithPage(CscContantAndCompanyDto cscContantAndCompanyDto){
        Wrapper<PageInfo<CscContantAndCompanyResponseDto>> wrapper = null;
        logger.debug("==>查询客户联系人 cscContantAndCompanyDto={}", cscContantAndCompanyDto);
        if(null == cscContantAndCompanyDto){
            throw new BusinessException("参数为空");
        }
        try {
            wrapper = getApi().queryCscReceivingInfoListWithPage(cscContantAndCompanyDto);
        } catch (RetryableException ex) {
            logger.error("==>调用接口发生异常：调用查询客户联系人接口(/api/csc/contact/queryCscReceivingInfoList)无法连接或超时. {}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "调用查询客户联系人接口无法连接或超时！");
        } catch (Exception ex) {
            logger.error("==>调用接口发生异常：查询客户联系人接口(/api/csc/contact/queryCscReceivingInfoList). {}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "调用查询客户联系人接口异常！");
        }
        return wrapper;
    }
}
