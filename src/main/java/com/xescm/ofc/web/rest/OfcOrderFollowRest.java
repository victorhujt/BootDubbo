package com.xescm.ofc.web.rest;

import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.domain.OfcOrderDTO;
import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.service.OfcGoodsDetailsInfoService;
import com.xescm.ofc.service.OfcOrderDtoService;
import com.xescm.ofc.service.OfcOrderStatusService;
import com.xescm.ofc.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by lyh on 2016/10/12.
 * 订单追踪
 */
@RequestMapping(value = "/ofc")
@Controller
public class OfcOrderFollowRest extends BaseController{
    @Autowired
    private OfcOrderDtoService ofcOrderDtoService;
    @Autowired
    private OfcOrderStatusService ofcOrderStatusService;
    @Autowired
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;

    @RequestMapping("/orderFollowCon/{code}/{followTag}")
    public String orderFollowCon(Model model, @PathVariable String code, @PathVariable String followTag, Map<String,Object> map) throws InvocationTargetException {
        logger.debug("==>订单中心订单追踪条件筛选code code={}", code);
        logger.debug("==>订单中心订单追踪条件标志位 followTag={}", followTag);
        OfcOrderDTO ofcOrderDTO = ofcOrderDtoService.orderDtoSelect(code, followTag);
        List<OfcOrderStatus> ofcOrderStatuses = ofcOrderStatusService.orderStatusScreen(code, followTag);
        map.put("ofcOrderDTO",ofcOrderDTO);
        map.put("orderStatusList",ofcOrderStatuses);
        return "order_follow";
    }


    @RequestMapping("/orderDetails")
    public String orderDetails(Model model, String code, String followTag, Map<String,Object> map) throws InvocationTargetException{
        logger.debug("==>订单中心订单详情code code={}", code);
        logger.debug("==>订单中心订单详情标志位 followTag={}", followTag);
        OfcOrderDTO ofcOrderDTO = ofcOrderDtoService.orderDtoSelect(code, followTag);
        List<OfcOrderStatus> ofcOrderStatuses = ofcOrderStatusService.orderStatusScreen(code, followTag);
        List<OfcGoodsDetailsInfo> goodsDetailsList=ofcGoodsDetailsInfoService.goodsDetailsScreenList(code,followTag);
        map.put("ofcOrderDTO",ofcOrderDTO);
        map.put("orderStatusList",ofcOrderStatuses);
        map.put("goodsDetailsList",goodsDetailsList);
        return "order_detail";
    }



}
