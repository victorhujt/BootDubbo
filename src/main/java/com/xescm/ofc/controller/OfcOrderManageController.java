package com.xescm.ofc.controller;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.OrderConst;
import com.xescm.ofc.utils.PubUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
     * @param ofcOrderDTO
     * @return
     */
    @RequestMapping("/orderOrNotAudit")
    @ResponseBody
    public String orderAudit(@ModelAttribute("ofcOrderDTO")OfcOrderDTO ofcOrderDTO){
        try {
            ofcOrderManageService.orderAudit(ofcOrderDTO);
        } catch (Exception e) {
            return "";
        }
        return "";
    }

    /**
     * 订单删除
     * @param ofcOrderDTO
     * @return
     */
    @RequestMapping("/orderDelete")
    public String orderDelete(@ModelAttribute("ofcOrderDTO")OfcOrderDTO ofcOrderDTO){
        try {
            ofcOrderManageService.orderDelete(ofcOrderDTO);
        } catch (Exception e) {
            return "";
        }
        return "";
    }

    /**
     * 订单取消
     * @param ofcOrderDTO
     * @return
     */
    @RequestMapping("/orderCancel")
    public String orderCancel(@ModelAttribute("ofcOrderDTO")OfcOrderDTO ofcOrderDTO){
        try {
            ofcOrderManageService.orderCancel(ofcOrderDTO);
        } catch (Exception e) {
            return "";
        }
        return "";
    }
}
