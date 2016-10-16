package com.xescm.ofc.controller;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by lyh on 2016/10/8.
 */
@Controller
public class OfcOrderPlaceOrderController {

    @Autowired
    private OfcOrderPlaceService ofcOrderPlaceService;
    @Autowired
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;

    /**
     * 下单
     * @param ofcOrderDTO
     * @param response
     * @return
     */
    @RequestMapping("/orderPlaceCon")
    public String orderPlace(OfcOrderDTO ofcOrderDTO, String tag, HttpServletResponse response){
        System.out.println(ofcOrderDTO);
        System.out.println("111111111111111111111");
        try {
            String result = ofcOrderPlaceService.placeOrder(ofcOrderDTO,tag);
            System.out.println("====1111====="+result);
            if(tag.equals("manage")){


                return "order_manage";
            }
            return "order_place";
        } catch (Exception e) {
            e.printStackTrace();
            return "order_place";
        }
    }

    /**
     * 货品筛选
     * @param ofcGoodsDetailsInfo
     * @return
     */
    @RequestMapping("/goodsScans")
    public String placeOrder(OfcGoodsDetailsInfo ofcGoodsDetailsInfo){
        ofcGoodsDetailsInfo.setGoodsCode("1");
        ofcGoodsDetailsInfo.setGoodsCode("1");
        ofcGoodsDetailsInfoService.select(ofcGoodsDetailsInfo);
        return "order_place";
    }

    @RequestMapping(value="/orderPlace")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
        return new ModelAndView("order_place");
    }




}
