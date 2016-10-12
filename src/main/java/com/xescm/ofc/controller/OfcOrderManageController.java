package com.xescm.ofc.controller;

import com.xescm.ofc.domain.OfcOrderDTO;
import com.xescm.ofc.service.OfcOrderManageService;
import com.xescm.ofc.wrap.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ydx on 2016/10/11.
 */
@Controller
public class OfcOrderManageController {
    @Autowired
    private OfcOrderManageService ofcOrderManageService;

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
    public void orderAudit(String orderCode,  String orderStatus,HttpServletResponse response){
        try {
            ofcOrderManageService.orderAudit(orderCode,orderStatus);
            response.getWriter().print(Wrapper.SUCCESS_CODE);
        } catch (Exception e) {
            try {
                response.getWriter().print(Wrapper.ERROR_CODE);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
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
            ofcOrderManageService.orderDelete(orderCode,orderStatus);
            response.getWriter().print(Wrapper.SUCCESS_CODE);
        } catch (Exception e) {
            try {
                response.getWriter().print(Wrapper.ERROR_CODE);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
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
            ofcOrderManageService.orderCancel(orderCode,orderStatus);
            response.getWriter().print(Wrapper.SUCCESS_CODE);
        } catch (Exception e) {
            try {
                response.getWriter().print(Wrapper.ERROR_CODE);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/getOrderDetailByCode")
    public void getOrderDetailByCode(String orderCode, HttpServletResponse response){
        OfcOrderDTO ofcOrderDTO = ofcOrderManageService.getOrderDetailByCode(orderCode);
        try {
            response.getWriter().print(ofcOrderDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
