package com.xescm.ofc.feign.client;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.rmc.FeignRMcServiceCoverageAPI;
import com.xescm.ofc.feign.api.rmc.FeignRmcPickUpOrRecipientAPI;
import com.xescm.ofc.model.vo.rmc.RmcServiceCoverageForOrderVo;
import com.xescm.uam.domain.feign.AuthRequestInterceptor;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
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
 * Created by lyh on 2016/11/1.
 */

@Service
public class FeignRmcServiceCoverageAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignRmcPickUpOrRecipientAPI.class);
    @Resource
    private AuthRequestInterceptor authRequestInterceptor;
    @Resource
    RestConfig restConfig;
    public FeignRMcServiceCoverageAPI getRmcApi() {
        FeignRMcServiceCoverageAPI res = Feign.builder()
                .requestInterceptor(authRequestInterceptor).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder()).target(FeignRMcServiceCoverageAPI.class,restConfig.getRmcUrl());
        return res;
    }

    public Wrapper<List<RmcServiceCoverageForOrderVo>> queryServiceCoverageListForOrder(RmcServiceCoverageForOrderVo rmcServiceCoverageForOrderVo){
        logger.debug("==>相关地址 rmcServiceCoverageForOrderVo={}", rmcServiceCoverageForOrderVo);
        if(null == rmcServiceCoverageForOrderVo){
            throw new BusinessException("参数为空");
        }
        Wrapper<List<RmcServiceCoverageForOrderVo>> rmcServiceCoverageForOrder = null;
        try {
            rmcServiceCoverageForOrder = getRmcApi().queryServiceCoverageListForOrder(rmcServiceCoverageForOrderVo);
        } catch (RetryableException ex) {
            logger.error("==>调用接口发生异常：调用查询上门提货覆盖区域接口(/api/rmc/district/queryPickUp)无法连接或超时. {}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "调用查询上门提货覆盖区域接口无法连接或超时！");
        } catch (Exception ex){
            logger.error("==>调用接口发生异常：查询上门提货覆盖区域接口(/api/rmc/district/queryPickUp). {}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "调用查询上门提货覆盖区域接口发生异常！");
        }
        return rmcServiceCoverageForOrder;
    }
}