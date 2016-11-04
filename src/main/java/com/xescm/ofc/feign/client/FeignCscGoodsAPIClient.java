package com.xescm.ofc.feign.client;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.domain.dto.csc.CscGoods;
import com.xescm.ofc.domain.dto.csc.vo.CscGoodsVo;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.FeignCscGoodsAPI;
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
 * Created by lyh on 2016/10/19.
 */
@Service
public class FeignCscGoodsAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignCscGoodsAPI.class);
    @Resource
    RestConfig restConfig;

    public FeignCscGoodsAPI getApi() {
        FeignCscGoodsAPI res = Feign.builder()
                .requestInterceptor(new AuthRequestInterceptor()).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder()).target(FeignCscGoodsAPI.class, restConfig.getCscUrl());
        return res;
    }


    public Wrapper<List<CscGoodsVo>> queryCscGoodsList(CscGoods cscGoods){
        logger.debug("==>查询货品 cscGoods={}", cscGoods);
        if(null == cscGoods){
            throw new BusinessException("参数为空");
        }
        Wrapper<List<CscGoodsVo>> listWrapper = getApi().queryCscGoodsList(cscGoods);
        return listWrapper;
    }
}
