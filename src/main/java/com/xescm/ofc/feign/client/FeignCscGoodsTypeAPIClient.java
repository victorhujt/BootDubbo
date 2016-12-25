package com.xescm.ofc.feign.client;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.model.dto.csc.CscGoodsType;
import com.xescm.ofc.model.vo.csc.CscGoodsTypeVo;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.csc.FeignCscGoodsAPI;
import com.xescm.ofc.feign.api.csc.FeignCscGoodsTypeAPI;
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
 * Created by lyh on 2016/11/23.
 */
@Service
public class FeignCscGoodsTypeAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignCscGoodsAPI.class);
    @Resource
    RestConfig restConfig;
    @Resource
    private AuthRequestInterceptor authRequestInterceptor;

    public FeignCscGoodsTypeAPI getApi() {
        FeignCscGoodsTypeAPI res = Feign.builder()
                .requestInterceptor(authRequestInterceptor).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder()).target(FeignCscGoodsTypeAPI.class, restConfig.getCscUrl());
        return res;
    }



    public Wrapper<List<CscGoodsTypeVo>> queryCscGoodsTypeList(CscGoodsType cscGoodsType){
        Wrapper<List<CscGoodsTypeVo>> listWrapper = null;
        logger.debug("==>查询货品类别 cscGoodsType={}", cscGoodsType);
        if(null == cscGoodsType){
            throw new BusinessException("参数为空");
        }
        try {
            listWrapper = (Wrapper<List<CscGoodsTypeVo>>) getApi().queryCscGoodsTypeList(cscGoodsType);
        } catch (RetryableException ex) {
            logger.error("==>调用接口发生异常：调用查询货品种类接口(/api/csc/goodstype/queryCscGoodsTypeList)无法连接或超时. {}", ex);
            throw new BusinessException("调用查询货品种类接口无法连接或超时！");
        } catch (Exception ex){
            logger.error("==>调用接口发生异常：通过查询货品种类接口(/api/csc/goodstype/queryCscGoodsTypeList). {}", ex);
            throw new BusinessException("调用查询货品种类接口异常！");
        }
        return listWrapper;
    }

    public Wrapper<?> addCscGoodsType(CscGoodsType cscGoodsType){
        Wrapper<?> listWrapper = null;
        logger.debug("==>查询货品类别 cscGoodsType={}", cscGoodsType);
        if(null == cscGoodsType){
            throw new BusinessException("参数为空");
        }
        try {
            listWrapper = getApi().addCscGoodsType(cscGoodsType);
        } catch (RetryableException ex) {
            logger.error("==>调用接口发生异常：调用查询货品种类接口(/api/csc/goodstype/addCscGoodsType)无法连接或超时. {}", ex);
            throw new BusinessException("调用查询货品种类接口无法连接或超时！");
        } catch (Exception ex){
            logger.error("==>调用接口发生异常：通过查询货品种类接口(/api/csc/goodstype/addCscGoodsType). {}", ex);
            throw new BusinessException("调用查询货品种类接口异常！");
        }
        return listWrapper;
    }
}
