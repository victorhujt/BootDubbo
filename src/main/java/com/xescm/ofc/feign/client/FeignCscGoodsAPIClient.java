package com.xescm.ofc.feign.client;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.model.dto.csc.CscGoodsApiDto;
import com.xescm.ofc.model.dto.csc.CscGoodsType;
import com.xescm.ofc.model.vo.csc.CscGoodsApiVo;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.csc.FeignCscGoodsAPI;
import com.xescm.ofc.model.vo.csc.CscGoodsTypeVo;
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
    @Resource
    private AuthRequestInterceptor authRequestInterceptor;

    public FeignCscGoodsAPI getApi() {
        FeignCscGoodsAPI res = Feign.builder()
                .requestInterceptor(authRequestInterceptor).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder()).target(FeignCscGoodsAPI.class, restConfig.getCscUrl());
        return res;
    }


    public Wrapper<List<CscGoodsApiVo>> queryCscGoodsList(CscGoodsApiDto cscGoods){
        logger.debug("==>查询货品 cscGoods={}", cscGoods);
        if(null == cscGoods){
            throw new BusinessException("参数为空");
        }
        Wrapper<List<CscGoodsApiVo>> listWrapper = getApi().queryCscGoodsList(cscGoods);
        return listWrapper;
    }

    public Wrapper<List<CscGoodsTypeVo>> getCscGoodsTypeList(CscGoodsType cscGoodsType){
        logger.debug("==>查询货品 cscGoods={}", cscGoodsType);
        Wrapper<List<CscGoodsTypeVo>> listWrapper = getApi().getCscGoodsTypeList(cscGoodsType);
        return listWrapper;
    }
}
