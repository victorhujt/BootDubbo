package com.xescm.ofc.web.rest;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.ofc.domain.OrderScreenResult;
import com.xescm.ofc.domain.form.OrderOperForm;
import com.xescm.ofc.enums.OrderStatusEnum;
import com.xescm.ofc.feign.client.FeignCscCustomerAPIClient;
import com.xescm.ofc.feign.client.FeignCscStoreAPIClient;
import com.xescm.ofc.feign.client.FeignRmcCompanyAPIClient;
import com.xescm.ofc.service.*;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 运营 订单管理
 * Created by hiyond on 2016/11/22.
 */
@RequestMapping(value = "/ofc", produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcOrderManageOperaRest extends BaseController {

    @Autowired
    private OfcOrderManageOperService ofcOrderManageOperService;

    @Autowired
    private OfcOrderManageService ofcOrderManageService;

    /**
     * 运营→订单管理 orderManageOpera
     * @return modelAndView
     */
    @RequestMapping("/orderManageOpera")
    public ModelAndView orderManageOpera() {
        ModelAndView modelAndView = new ModelAndView("order_manage_opera");
        modelAndView.addObject("orderStatus",OrderStatusEnum.queryList());
        return modelAndView;
    }

    /**
     * 查询订单
     * @param page
     * @param form
     * @return
     */
    @RequestMapping("/queryOrderOper")
    @ResponseBody
    public Object queryOrderOper(Page<OrderOperForm> page, OrderOperForm form) {
        try {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<OrderScreenResult> dataList = ofcOrderManageOperService.queryOrderOper(form);
            PageInfo<OrderScreenResult> pageInfo = new PageInfo<>(dataList);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, pageInfo);
        } catch (Exception ex) {
            logger.error("运营平台查询订单出错：{}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
    }

    /**
     * 审核与反审核订单
     * @param orderCode
     * @param orderStatus
     * @param reviewTag
     * @return
     */
    @RequestMapping(value = "/orderOrNotAuditOper", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> orderAuditOper(String orderCode, String orderStatus, String reviewTag) {
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        try {
            String result = ofcOrderManageService.orderAudit(orderCode, orderStatus, reviewTag, authResDtoByToken);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单审核反审核出现异常:{},{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
    }

    /**
     * 订单删除
     * @param orderCode
     * @return
     */
    @RequestMapping(value = "/orderDeleteOper", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> orderDeleteOper(String orderCode, String orderStatus) {
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        try {
            String result = ofcOrderManageService.orderDelete(orderCode, orderStatus, authResDtoByToken);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单删除出现异常:{},{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
    }

    /**
     * 订单取消
     * @param orderCode
     * @return
     */
    @RequestMapping(value = "/orderCancelOper", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> orderCancelOper(String orderCode, String orderStatus) {
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        try {
            String result = ofcOrderManageService.orderCancel(orderCode, orderStatus, authResDtoByToken);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单取消出现异常:{},{}", "", null);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
    }

    /**
     * 订单详情
     * @param orderCode
     * @param dtotag
     * @return
     */
    @RequestMapping(value = "/orderDetailPageByCode/{orderCode}/{dtotag}")
    public ModelAndView orderDetailByOrderCode(@PathVariable String orderCode, @PathVariable String dtotag) {
        ModelAndView modelAndView = new ModelAndView("order_detail_opera");
        return modelAndView;
    }

    /**
     * 订单批次
     * @param orderBatchCode
     * @param dtotag
     * @return
     */
    @RequestMapping(value = "/orderDetailBatchOpera/{orderBatchCode}/{dtotag}")
    public ModelAndView orderDetailBatchOpera(@PathVariable String orderBatchCode, @PathVariable String dtotag) {
        ModelAndView modelAndView = new ModelAndView("order_detail_batch_opera");
        return modelAndView;
    }


}
