package com.xescm.ofc.web.restcontroller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.OfcMobileOrder;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.form.MobileOrderOperForm;
import com.xescm.ofc.model.vo.ofc.OfcMobileOrderVo;
import com.xescm.ofc.service.OfcMobileOrderService;
import com.xescm.ofc.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 手机订单
 * Created by hujintao on 2016/12/15.
 */
@RequestMapping(value = "/ofc", produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcMobileOrderRest extends BaseController {

    @Resource
    private OfcMobileOrderService ofcMobileOrderService;

    @Resource
    private RestConfig restConfig;


    @RequestMapping(value = "/queryMobileOrderData", method = {RequestMethod.POST})
    @ResponseBody
    public Wrapper<?> queryMobileOrderData(Page<MobileOrderOperForm> page, MobileOrderOperForm form) {
        try {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<OfcMobileOrder> dataList = ofcMobileOrderService.queryOrderList(form);
            PageInfo<OfcMobileOrder> pageInfo = new PageInfo<>(dataList);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, pageInfo);
        } catch (BusinessException ex) {
            logger.error("运营平台查询订单出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("运营平台查询订单出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

    @RequestMapping(value = "/mobileOrderDetails/{orderCode}")
    public ModelAndView orderDetailByOrderCode(@PathVariable("orderCode") String code) {
        logger.info("==>手机订单详情code code={}", code);
        ModelAndView modelAndView = new ModelAndView("mobile_order_detail_opera");
        modelAndView.addObject(OrderConstConstant.OFC_WEB_URL, restConfig.getOfcWebUrl());
        OfcMobileOrder condition = new OfcMobileOrder();
        condition.setMobileOrderCode(code);
        OfcMobileOrderVo mobileOrder = null;
        try {
            mobileOrder = ofcMobileOrderService.selectOneOfcMobileOrder(condition);
        } catch (UnsupportedEncodingException ex) {
            logger.error("手机订单详情:{}", ex.getMessage(), ex);
        }
        modelAndView.addObject("mobileOrder", mobileOrder);
        return modelAndView;
    }

    @RequestMapping(value = "/acceptMobileOrder/{orderCode}")
    public ModelAndView acceptMobileOrder(@PathVariable("orderCode") String code) {
        logger.info("==>手机订单详情code code={}", code);
        ModelAndView modelAndView = new ModelAndView("mobile_order_accept_opera");
        modelAndView.addObject(OrderConstConstant.OFC_WEB_URL, restConfig.getOfcWebUrl());
        OfcMobileOrder condition = new OfcMobileOrder();
        condition.setMobileOrderCode(code);
        OfcMobileOrderVo mobileOrder = null;
        try {
            mobileOrder = ofcMobileOrderService.selectOneOfcMobileOrder(condition);
        } catch (UnsupportedEncodingException e) {
            logger.error("手机订单详情:{}", e.getMessage(), e);
        }
        modelAndView.addObject("mobileOrder", mobileOrder);
        return modelAndView;
    }





}