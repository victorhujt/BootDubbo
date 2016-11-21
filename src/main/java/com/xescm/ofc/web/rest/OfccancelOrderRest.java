package com.xescm.ofc.web.rest;

import com.xescm.ofc.constant.ResultModel;
import com.xescm.ofc.service.CreateOrderService;
import com.xescm.ofc.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 订单中心创建订单
 * Created by hiyond on 2016/11/15.
 */
@Controller
public class OfccancelOrderRest extends BaseController {

    @Autowired
    private CreateOrderService createOrderService;

    //    @RequestMapping(value = "")
    public void cancelOrder(String custOrderCode, String custCode) {
        logger.debug("取消api订单custOrderCode:{},custCode:{}", custOrderCode, custCode);
        try {
            ResultModel resultModel = createOrderService.cancelOrderStateByOrderCode(custOrderCode, custCode);
            logger.debug("取消api订单resultModel:{}", resultModel);
        } catch (Exception ex) {
            logger.error("取消api订单出错：{}，{}", ex.getMessage(), ex);
        }
    }

}
