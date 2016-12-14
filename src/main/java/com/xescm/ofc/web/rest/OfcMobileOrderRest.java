package com.xescm.ofc.web.rest;

import com.xescm.ofc.domain.OfcMobileOrder;
import com.xescm.ofc.model.vo.ofc.OfcBatchOrderVo;
import com.xescm.ofc.service.CreateOrderService;
import com.xescm.ofc.service.OfcMobileOrderService;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.utils.PubUtils;
import com.xescm.uam.utils.PublicUtil;
import com.xescm.uam.utils.wrap.Wrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hujintao on 2016/12/12.
 */
@RequestMapping(value = "/ofc", produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcMobileOrderRest extends BaseController {

    @Autowired
    private OfcMobileOrderService ofcMobileOrderService;

    /**
     * 获取丁丁的企业名称
     */
    @RequestMapping(value="/queryCustomerName", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> queryCustomerName(){
        return null;
    }

    @RequestMapping("/saveMobileOrder")
    @ResponseBody
    public ModelAndView saveMobileOrder(OfcMobileOrder mobileOrder){
        ModelAndView modelAndView = new ModelAndView("");
        try {
            if(mobileOrder!=null){
                ofcMobileOrderService.saveOfcMobileOrder(mobileOrder);
            }
            modelAndView.addObject("mobileOrder",mobileOrder);
        } catch (Exception ex) {
        }
            return modelAndView;
    }

    /**
     * 获取录单记录
     * @return
     */
    @RequestMapping(value="/queryOrderNotes", method = RequestMethod.POST)
    @ResponseBody
    public List<OfcMobileOrder> queryOrderNotes(String mobileOrderStatus){
        ModelAndView modelAndView = new ModelAndView("");
        List<OfcMobileOrder> ofcMobileOrder=new ArrayList<>();
        try {
            mobileOrderStatus=PubUtils.trimAndNullAsEmpty(mobileOrderStatus);
            ofcMobileOrder=ofcMobileOrderService.queryOrderNotes(mobileOrderStatus);
        }catch (Exception e){
            modelAndView.addObject("info","查询失败");
            logger.info("查询历史订单异常，{}",e.getMessage(),e);
        }
        return ofcMobileOrder;
    }

}
