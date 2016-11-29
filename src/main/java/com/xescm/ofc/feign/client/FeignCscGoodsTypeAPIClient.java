package com.xescm.ofc.feign.client;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.model.dto.csc.CscGoodsType;
import com.xescm.ofc.model.vo.csc.CscGoodsTypeVo;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.csc.FeignCscGoodsAPI;
import com.xescm.ofc.feign.api.csc.FeignCscGoodsTypeAPI;
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
 * Created by lyh on 2016/11/23.
 */
@Service
public class FeignCscGoodsTypeAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignCscGoodsAPI.class);
    @Resource
    RestConfig restConfig;

    public FeignCscGoodsTypeAPI getApi() {
        FeignCscGoodsTypeAPI res = Feign.builder()
                .requestInterceptor(new AuthRequestInterceptor()).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder()).target(FeignCscGoodsTypeAPI.class, restConfig.getCscUrl());
        return res;
    }



    public Wrapper<List<CscGoodsTypeVo>> queryCscGoodsTypeList(CscGoodsType cscGoodsType){
        logger.debug("==>查询货品类别 cscGoodsType={}", cscGoodsType);
        if(null == cscGoodsType){
            throw new BusinessException("参数为空");
        }
        Wrapper<List<CscGoodsTypeVo>> listWrapper = (Wrapper<List<CscGoodsTypeVo>>) getApi().queryCscGoodsTypeList(cscGoodsType);
        return listWrapper;
    }
    public Wrapper<?> addCscGoodsType(CscGoodsType cscGoodsType){
        logger.debug("==>查询货品类别 cscGoodsType={}", cscGoodsType);
        if(null == cscGoodsType){
            throw new BusinessException("参数为空");
        }
        Wrapper<?> listWrapper = getApi().addCscGoodsType(cscGoodsType);
        return listWrapper;
    }
}
