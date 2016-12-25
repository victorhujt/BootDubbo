package com.xescm.ofc.feign.client;

import com.github.pagehelper.PageInfo;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.model.dto.csc.CscGoodsApiDto;
import com.xescm.ofc.model.dto.csc.CscGoodsType;
import com.xescm.ofc.model.vo.csc.CscGoodsApiVo;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.csc.FeignCscGoodsAPI;
import com.xescm.ofc.model.vo.csc.CscGoodsTypeVo;
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
        Wrapper<List<CscGoodsApiVo>> listWrapper = null;
        logger.debug("==>查询货品 cscGoods={}", cscGoods);
        if(null == cscGoods){
            throw new BusinessException("参数为空");
        }
        try {
            listWrapper = getApi().queryCscGoodsList(cscGoods);
        } catch (RetryableException ex) {
            logger.error("==>调用接口发生异常：调用查询客户货品列表接口(/api/csc/goods/queryCscGoodsList)无法连接或超时. {}", ex);
            throw new BusinessException("调用查询客户货品列表接口无法连接或超时！");
        } catch (Exception ex){
            logger.error("==>调用接口发生异常：查询客户货品列表接口(/api/csc/goods/queryCscGoodsList). {}", ex);
            throw new BusinessException("调用查询客户货品列表接口异常！");
        }
        return listWrapper;
    }

    public Wrapper<List<CscGoodsTypeVo>> getCscGoodsTypeList(CscGoodsType cscGoodsType){
        Wrapper<List<CscGoodsTypeVo>> listWrapper = null;
        logger.debug("==>查询货品 cscGoods={}", cscGoodsType);
        if(null == cscGoodsType){
            throw new BusinessException("参数为空");
        }
        try {
            listWrapper = getApi().getCscGoodsTypeList(cscGoodsType);
        } catch (RetryableException ex) {
            logger.error("==>调用接口发生异常：调用查询货品类别接口(api/csc/goodstype/getCscGoodsTypeList)无法连接或超时. {}", ex);
            throw new BusinessException("调用查询货品类别接口无法连接或超时！");
        } catch (Exception ex){
            logger.error("==>调用接口发生异常：查询货品类别接口(api/csc/goodstype/getCscGoodsTypeList). {}", ex);
            throw new BusinessException("调用查询货品类别接口异常！");
        }
        return listWrapper;
    }

    public Wrapper<PageInfo<CscGoodsApiVo>> queryCscGoodsPageList(CscGoodsApiDto cscGoods){
        Wrapper<PageInfo<CscGoodsApiVo>> listWrapper = null;
        logger.debug("==>查询货品 cscGoods={}", cscGoods);
        if(null == cscGoods){
            throw new BusinessException("参数为空");
        }
        try {
            listWrapper = getApi().queryCscGoodsPageList(cscGoods);
        } catch (RetryableException ex) {
            logger.error("==>调用接口发生异常：调用查询客户货品列表接口(/api/csc/goods/queryCscGoodsList)无法连接或超时. {}", ex);
            throw new BusinessException("调用查询客户货品列表接口无法连接或超时！");
        } catch (Exception ex){
            logger.error("==>调用接口发生异常：查询客户货品列表接口(/api/csc/goods/queryCscGoodsList). {}", ex);
            throw new BusinessException("调用查询客户货品列表接口异常！");
        }
        return listWrapper;
    }
}
