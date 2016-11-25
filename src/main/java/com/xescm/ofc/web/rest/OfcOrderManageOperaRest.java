package com.xescm.ofc.web.rest;

import com.xescm.ofc.domain.OrderScreenResult;
import com.xescm.ofc.domain.form.OrderOperForm;
import com.xescm.ofc.enums.OrderStatusEnum;
import com.xescm.ofc.service.OfcOrderManageOperService;
import com.xescm.ofc.service.OfcOrderManageService;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by hiyond on 2016/11/22.
 */
@RequestMapping(value = "/ofc", produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcOrderManageOperaRest extends BaseController {

    @Autowired
    private OfcOrderManageOperService ofcOrderManageOperService;

    @RequestMapping("/queryOrderOper")
    @ResponseBody
    public Object queryOrderOper(OrderOperForm form) {
        try {
            List<OrderScreenResult> dataList = ofcOrderManageOperService.queryOrderOper(form);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE, null);
        } catch (Exception ex) {
            logger.error("运营平台查询订单出错：{}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
    }

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


}
