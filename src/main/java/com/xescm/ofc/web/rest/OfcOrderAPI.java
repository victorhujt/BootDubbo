package com.xescm.ofc.web.rest;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.OfcCancelRepeatService;
import com.xescm.ofc.service.OfcMobileOrderService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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
    private OfcCancelRepeatService ofcCancelRepeatService;

    /**
     * 订单5分钟后依然未处理完, 重新置为待处理, 并重新存到Redis中
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

    @RequestMapping(value = "dealCancelOrder/{orderCode}", method = {RequestMethod.GET})
    @ResponseBody
    public Wrapper<?> dealCancelOrder(@ApiParam(name = "orderCode", value = "订单号") @PathVariable String orderCode) {
        try {
            if (PubUtils.isSEmptyOrNull(orderCode)) {
                throw new Exception("订单编号不能为空！");
            }
            logger.info("处理取消的订单时状态不一致的订单,订单号为:{}",orderCode);
            boolean isDealSuccess = ofcCancelRepeatService.sendTotfc(orderCode);
            if (isDealSuccess) {
                return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
            }
        }catch (BusinessException ex) {
            logger.error("处理取消的订单时状态不一致的订单出现异常,{}", "", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return WrapMapper.wrap(Wrapper.ERROR_CODE, "处理取消订单失败");
    }
}