package com.xescm.ofc.web.rest;

import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.domain.OfcOrderDTO;
import com.xescm.ofc.service.OfcGoodsDetailsInfoService;
import com.xescm.ofc.service.OfcOrderDtoService;
import com.xescm.ofc.service.OfcOrderManageService;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.ofc.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.apache.coyote.http11.Constants.a;

/**
 * Created by ydx on 2016/10/11.
 */
@RequestMapping(value = "/ofc")
@Controller
public class OfcOrderManageRest extends BaseController{
    @Autowired
    private OfcOrderManageService ofcOrderManageService;
    @Autowired
    private OfcOrderDtoService ofcOrderDtoService;
    @Autowired
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;


    /**
     * 订单审核/反审核
     * @param
     * @return
     */
    @RequestMapping(value = "/orderOrNotAudit", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> orderAudit(Model model, String orderCode, String orderStatus, String reviewTag, HttpServletResponse response){
        logger.debug("==>订单中心订单管理订单审核反审核订单code orderCode={}", orderCode);
        logger.debug("==>订单中心订单管理订单审核反审核订单状态code orderStatus={}", orderStatus);
        logger.debug("==>订单中心订单管理订单审核反审核标志位 reviewTag={}", reviewTag);
        String result = null;
        try {
            result = ofcOrderManageService.orderAudit(orderCode,orderStatus,reviewTag);
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单审核反审核出现异常:{},{}", ex.getMessage(), ex);
            ex.printStackTrace();
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE,result);
    }

    /**
     * 订单删除
     * @param orderCode
     * @return com.xescm.uam.utils.wrap.Wrapper;
     */
    @RequestMapping(value = "/orderDelete", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> orderDelete(Model model, String orderCode, String orderStatus){
        logger.debug("==>订单中心订单管理订单删除订单code orderCode={}", orderCode);
        logger.debug("==>订单中心订单管理订单删除订单状态code orderStatus={}", orderStatus);
        String result = null;
        try {
            result = ofcOrderManageService.orderDelete(orderCode,orderStatus);
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单删除出现异常:{},{}", ex.getMessage(), ex);
            ex.printStackTrace();
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * 订单取消
     * @param orderCode
     * @return
     */
    @RequestMapping(value = "/orderCancel", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> orderCancel(Model model, String orderCode, String orderStatus, HttpServletResponse response){
        logger.debug("==>订单中心订单管理订单取消订单code orderCode={}", orderCode);
        logger.debug("==>订单中心订单管理订单取消订单状态code orderStatus={}", orderStatus);
        String result = null;
        try {
            result = ofcOrderManageService.orderCancel(orderCode,orderStatus);
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单取消出现异常:{},{}", ex.getMessage(), ex);
            ex.printStackTrace();
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * 进入订单编辑
     * @param orderCode
     * @param dtotag
     * @return
     */
    @RequestMapping(value = "/getOrderDetailByCode")
    public String getOrderDetailByCode(Model model, String orderCode, String dtotag, Map<String,Object> map){
        logger.debug("==>订单中心订单管理订单orderCode orderCode={}", orderCode);
        logger.debug("==>订单中心订单管理订单编辑标志位 dtotag={}", dtotag);
        OfcOrderDTO ofcOrderDTO=new OfcOrderDTO();

        try {
            orderCode=orderCode.replace(",","");
            ofcOrderDTO = ofcOrderDtoService.orderDtoSelect(orderCode,dtotag);
            System.out.println(ofcOrderDTO);
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsList= ofcGoodsDetailsInfoService.goodsDetailsScreenList(orderCode,"orderCode");
            if (ofcOrderDTO!=null){
                map.put("ofcGoodsDetailsList",ofcGoodsDetailsList);
                map.put("orderInfo", ofcOrderDTO);
                return "order_edit";
            }
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单编辑出现异常:{},{}", ex.getMessage(), ex);
            ex.printStackTrace();
        }
        return "order_manage";
    }

    /**
     * 订单删除
     * @param ofcGoodsDetailsInfo
     * @return
     */
    @RequestMapping("/goodsDelete")
    public void goodsDelete(Model model, OfcGoodsDetailsInfo ofcGoodsDetailsInfo,HttpServletResponse response){
        logger.debug("==>订单中心订单管理订单删除实体 ofcGoodsDetailsInfo={}", ofcGoodsDetailsInfo);
        try {
            String result = ofcGoodsDetailsInfoService.deleteByOrderCode(ofcGoodsDetailsInfo);
            response.getWriter().print(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
