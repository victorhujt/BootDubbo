package com.xescm.ofc.controller;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.OrderConst;
import com.xescm.ofc.utils.PrimaryGenerater;
import com.xescm.ofc.utils.PubUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by lyh on 2016/10/8.
 */
@RestController
public class OfcOrderPlaceOrderController {

    @Autowired
    private OfcOrderPlaceService ofcOrderPlaceService;
    @Autowired
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;

    /**
     * 下单
     * @param ofcOrderDTO
     * @param map
     * @return
     */
    @RequestMapping("/placeOrEditOrder")
    public String orderPlace(OfcOrderDTO ofcOrderDTO, Map<String, Object> map){
        System.out.println(ofcOrderDTO);
        try {
            ofcOrderPlaceService.placeOrder(ofcOrderDTO);
        } catch (Exception e) {
            return "";
        }
        return "order_place";
    }

    /**
     * 货品筛选
     * @param ofcGoodsDetailsInfo
     * @return
     */
    @RequestMapping("/goodsScans")
    public String placeOrder(@ModelAttribute("ofcGoodsDetailsInfo")OfcGoodsDetailsInfo ofcGoodsDetailsInfo){
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
