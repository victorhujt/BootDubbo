package com.xescm.ofc.web.rest;

import com.xescm.ofc.enums.OrderStatusEnum;
import com.xescm.ofc.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by hiyond on 2016/11/22.
 */
@RequestMapping(value = "/ofc", produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcOrderManageOperaRest extends BaseController {

    @RequestMapping(value = "/orderDetailPageByCode/{orderCode}/{dtotag}")
    public ModelAndView orderDetailByOrderCode(@PathVariable String orderCode, @PathVariable String dtotag) {
        ModelAndView modelAndView = new ModelAndView("order_detail_opera");
        return modelAndView;
    }

    @RequestMapping(value = "/orderDetailBatchOpera/{orderBatchCode}/{dtotag}")
    public ModelAndView orderDetailBatchOpera(@PathVariable String orderBatchCode, @PathVariable String dtotag) {
        ModelAndView modelAndView = new ModelAndView("order_detail_batch_opera");
        return modelAndView;
    }

    @RequestMapping(value = "/orderFollowOpera")
    public String orderFollowOpera() {
        return "order_follow_opera";
    }

}
