package com.xescm.ofc.controller;

import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.domain.OfcOrderDTO;
import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by lyh on 2016/10/12.
 * 订单追踪
 */
@Controller
public class OfcOrderFollowController {
    @Autowired
    private OfcOrderDtoService ofcOrderDtoService;
    @Autowired
    private OfcOrderStatusService ofcOrderStatusService;
    @Autowired
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;

    @RequestMapping("/orderFollowCon")
    public String orderFollowCon(String code, String followTag, Map<String,Object> map) throws InvocationTargetException {
        OfcOrderDTO ofcOrderDTO = ofcOrderDtoService.orderDtoSelect(code, followTag);
        List<OfcOrderStatus> ofcOrderStatuses = ofcOrderStatusService.orderStatusScreen(code, followTag);
        map.put("ofcOrderDTO",ofcOrderDTO);
        map.put("orderStatusList",ofcOrderStatuses);
        return "order_follow";
    }


    @RequestMapping("/orderDetails")
    public String orderDetails(String code, String followTag, Map<String,Object> map) throws InvocationTargetException{
        OfcOrderDTO ofcOrderDTO = ofcOrderDtoService.orderDtoSelect(code, followTag);
        List<OfcOrderStatus> ofcOrderStatuses = ofcOrderStatusService.orderStatusScreen(code, followTag);
        List<OfcGoodsDetailsInfo> goodsDetailsList=ofcGoodsDetailsInfoService.goodsDetailsScreenList(code,followTag);
        map.put("ofcOrderDTO",ofcOrderDTO);
        map.put("orderStatusList",ofcOrderStatuses);
        map.put("goodsDetailsList",goodsDetailsList);
        return "order_detail";
    }



}
