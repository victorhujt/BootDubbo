package com.xescm.ofc.web.rest;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.edas.model.dto.ofc.OfcTraceOrderDTO;
import com.xescm.ofc.edas.service.OfcOrderStatusEdasService;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.OfcMobileOrderService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lyh on 2017/3/31.
 */
@RequestMapping(value = "/api/ofc", produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcOrderAPI {

    private Logger logger = LoggerFactory.getLogger(OfcOrderAPI.class);

    @Resource
    private OfcMobileOrderService ofcMobileOrderService;
    @Resource
    private OfcOrderStatusEdasService ofcOrderStatusEdasService;

    /**
     * 订单5分钟后依然未处理完, 重新置为待处理, 并重新缓存到Redis中
     */
    @RequestMapping(value = "dealDingdingOrder", method = {RequestMethod.POST})
    @ResponseBody
    public void dealDingdingOrder() {
        logger.info("处理5分钟后依然未处理完的订单...");
        try {
            ofcMobileOrderService.dealDingdingOrder();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     *
     * @param code 客户订单号 或者运输单号 或者订单号
     * @return  订单号集合
     */
    @RequestMapping(value = "queryOrderByCode", method = {RequestMethod.POST})
    @ResponseBody
    public Wrapper queryOrderByCode(String code) {
        try {
            if (PubUtils.isSEmptyOrNull(code)) {
                logger.error("订单查询入参为空!");
                throw new BusinessException("请输入单号!");
            }
            logger.info("订单查询 ==> code : {}", code);
            //查询结果是订单号集合
            Wrapper result = ofcOrderStatusEdasService.queryOrderByCode(code);
            if(result == null){
                logger.error("没有查询到该订单!");
                throw new BusinessException("不存在符合条件的订单!");
            }
            if(result.getCode() == Wrapper.ERROR_CODE){
                logger.error("没有查询到该订单!");
                throw new BusinessException(result.getMessage());
            }
            List<String> orderCodes = (List<String>) result.getResult();
            if (CollectionUtils.isEmpty(orderCodes)) {
                logger.error("没有查询到该订单!");
                throw new BusinessException("不存在符合条件的订单!");
            }
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
        } catch (BusinessException ex) {
            logger.error("订单查询出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("订单查询出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

    /**
     *
     * @param orderCode 订单号
     * @return 订单的追踪状态
     */
    @RequestMapping(value = "traceByOrderCode", method = {RequestMethod.POST})
    @ResponseBody
    public Wrapper<OfcTraceOrderDTO> traceByOrderCode(String orderCode) {
        Wrapper<OfcTraceOrderDTO> result;
        try {
            if (PubUtils.isSEmptyOrNull(orderCode)) {
                logger.error("订单跟踪查询入参为空!");
                throw new BusinessException("请输入单号!");
            }
            logger.info("订单跟踪查询 ==> orderCode : {}", orderCode);
             result = ofcOrderStatusEdasService.traceByOrderCode(orderCode);
            if(result == null){
                logger.error("没有查询到订单的状态跟踪信息!");
                throw new BusinessException("没有查询到订单的状态跟踪信息!");
            }
            if(result.getCode() == Wrapper.ERROR_CODE){
                logger.error("订单跟踪查询出现异常");
                throw new BusinessException(result.getMessage());
            }
        }catch (Exception e){
            logger.error("订单跟踪查询出现异常:{}", e.getMessage(), e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,e.getMessage());
        }
        return result;
    }
}