package com.xescm.ofc.web.rest;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.service.*;
import com.xescm.ofc.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by lyh on 2016/10/8.
 */
@RequestMapping(value = "/ofc")
@Controller
public class OfcOrderPlaceOrderRest extends BaseController{

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
    public String orderPlace(Model model, OfcOrderDTO ofcOrderDTO, String tag, HttpServletResponse response){
        logger.debug("==>订单中心下单或编辑实体 ofcOrderDTO={}", ofcOrderDTO);
        logger.debug("==>订单中心下单或编辑标志位 tag={}", tag);
        System.out.println(ofcOrderDTO);
        try {
            String result = ofcOrderPlaceService.placeOrder(ofcOrderDTO,tag);
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
    public String placeOrder(Model model,OfcGoodsDetailsInfo ofcGoodsDetailsInfo){
        logger.debug("==>订单中心下单货品筛选实体 ofcGoodsDetailsInfo={}", ofcGoodsDetailsInfo);
        ofcGoodsDetailsInfo.setGoodsCode("1");
        ofcGoodsDetailsInfo.setGoodsCode("1");
        ofcGoodsDetailsInfoService.select(ofcGoodsDetailsInfo);
        return "order_place";
    }






}
