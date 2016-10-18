package com.xescm.ofc.controller;

import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.domain.OfcOrderDTO;
import com.xescm.ofc.service.OfcGoodsDetailsInfoService;
import com.xescm.ofc.service.OfcOrderDtoService;
import com.xescm.ofc.service.OfcOrderManageService;
import com.xescm.ofc.wrap.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.apache.coyote.http11.Constants.a;

/**
 * Created by ydx on 2016/10/11.
 */
@Controller
public class OfcOrderManageController {
    @Autowired
    private OfcOrderManageService ofcOrderManageService;
    @Autowired
    private OfcOrderDtoService ofcOrderDtoService;
    @Autowired
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;

    @RequestMapping(value = "/orderManage")
    public String orderManage(){
        return "order_manage";
    }

    /**
     * 订单审核/反审核
     * @param
     * @return
     */
    @RequestMapping("/orderOrNotAudit")
    public void orderAudit(String orderCode,  String orderStatus, String reviewTag,HttpServletResponse response){
        try {
            String result = ofcOrderManageService.orderAudit(orderCode,orderStatus,reviewTag);
            System.out.println("============"+result);
            response.getWriter().print(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 订单删除
     * @param orderCode
     * @return
     */
    @RequestMapping("/orderDelete")
    public void orderDelete(String orderCode,  String orderStatus,HttpServletResponse response){
        try {
            String result = ofcOrderManageService.orderDelete(orderCode,orderStatus);
            response.getWriter().print(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 订单取消
     * @param orderCode
     * @return
     */
    @RequestMapping("/orderCancel")
    public void orderCancel(String orderCode, String orderStatus, HttpServletResponse response){
        try {
            String result = ofcOrderManageService.orderCancel(orderCode,orderStatus);
            response.getWriter().print(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 进入订单编辑
     * @param orderCode
     * @param dtotag
     * @return
     */
    @RequestMapping(value = "/getOrderDetailByCode")
    public String getOrderDetailByCode(String orderCode, String dtotag, Map<String,Object> map){
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "order_manage";
    }

    /**
     * 订单删除
     * @param orderCode
     * @return
     */
    @RequestMapping("/goodsDelete")
    public void goodsDelete(String orderCode,  String goodsCode,HttpServletResponse response){
        try {
            String result = ofcGoodsDetailsInfoService.deleteByOrderCode(orderCode,goodsCode);
            response.getWriter().print(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
