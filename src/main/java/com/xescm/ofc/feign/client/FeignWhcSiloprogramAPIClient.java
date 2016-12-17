package com.xescm.ofc.feign.client;

import javax.annotation.Resource;

import feign.RetryableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.whc.FeignWhcOrderAPI;
import com.xescm.ofc.model.dto.whc.CancelOrderDTO;
import com.xescm.ofc.utils.Response;
import com.xescm.uam.domain.feign.AuthRequestInterceptor;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

/**
 * Created by victor on 2016/12/2.
 */
@Service
public class FeignWhcSiloprogramAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignWhcOrderAPI.class);

    @Resource
    RestConfig restConfig;
    @Resource
    private AuthRequestInterceptor authRequestInterceptor;
    public FeignWhcOrderAPI getApi() {
        FeignWhcOrderAPI res = Feign.builder()
                .requestInterceptor(authRequestInterceptor).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(FeignWhcOrderAPI.class, restConfig.getWhcUrl());
        return res;
    }

    public Response cancelOrder(CancelOrderDTO cancelOrderDTO){
        logger.info("==>cancelOrderDTO={}",cancelOrderDTO);
        Response response = null;
        if(null == cancelOrderDTO){
            throw new BusinessException("参数为空");
        }
        try {
            response = getApi().cancelOrder(cancelOrderDTO);
        } catch (RetryableException ex) {
            logger.error("==>调用接口发生异常：调用取消仓储订单接口(/api/whc/order/cancel/cancelOrder)无法连接或超时. {} ", ex);
            throw new BusinessException("调用取消仓储订单接口无法连接或超时！");
        } catch (Exception ex){
            logger.error("==>调用接口发生异常：取消仓储订单接口(/api/whc/order/cancel/cancelOrder). {}", ex);
            throw new BusinessException("调用取消仓储订单接口发生异常");
        }
        return response;
    }
}
