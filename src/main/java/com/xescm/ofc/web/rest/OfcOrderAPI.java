package com.xescm.ofc.web.rest;

import com.xescm.ofc.model.dto.ofc.OfcExceptOrderDTO;
import com.xescm.ofc.service.OfcExceptOrderService;
import com.xescm.ofc.service.OfcFundamentalInformationService;
import com.xescm.ofc.service.OfcMobileOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
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
    private OfcExceptOrderService ofcExceptOrderService;

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


    @RequestMapping(value = "dealExceptOrder", method = {RequestMethod.POST})
    @ResponseBody
    public void dealExceptOrder(OfcExceptOrderDTO ofcExceptOrderDTO) {
        logger.info("处理常订单...");
        try {
            ofcExceptOrderService.dealExceptOrder(ofcExceptOrderDTO);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }



}