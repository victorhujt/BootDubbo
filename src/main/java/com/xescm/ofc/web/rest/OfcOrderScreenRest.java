package com.xescm.ofc.web.rest;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.ofc.domain.OrderScreenCondition;
import com.xescm.ofc.domain.OrderScreenResult;
import com.xescm.ofc.service.OfcOrderScreenService;
import com.xescm.ofc.utils.JSONUtils;
import com.xescm.ofc.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by lyh on 2016/10/10.
 */
@RequestMapping(value = "/ofc",produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcOrderScreenRest extends BaseController {

    @Autowired
    private OfcOrderScreenService ofcOrderScreenService;

    @RequestMapping(value = "/orderScreenByCondition/{orderScreenConditionJSON}/{tag}/{currPage}/{pageSize}")
    public String orderScreenByCondition(Model model,@PathVariable String orderScreenConditionJSON, Map<String,Object> map,@PathVariable("tag") String tag, @PathVariable("currPage") String currPagePath, @PathVariable("pageSize") String pageSizePath) throws IOException {
        logger.debug("==>订单中心订单查询条件 orderScreenCondition={}", orderScreenConditionJSON);
        logger.debug("==>订单中心订单查询标志位 tag={}", tag);
        if(StringUtils.isBlank(orderScreenConditionJSON)){
            orderScreenConditionJSON = JSONUtils.objectToJson(new OrderScreenCondition());
        }
        OrderScreenCondition orderScreenCondition = JSONUtils.jsonToPojo(orderScreenConditionJSON, OrderScreenCondition.class);
        int pageSize = Integer.parseInt(pageSizePath);
        int currPage = Integer.parseInt(currPagePath);
        PageHelper.startPage(currPage,pageSize);
        List<OrderScreenResult> orderScreenResults = ofcOrderScreenService.orderScreen(orderScreenCondition);
        PageInfo<OrderScreenResult> pageInfo = new PageInfo<>(orderScreenResults);
        pageInfo.setPageSize(pageSize);
        map.put("orderList", pageInfo.getList());
        map.put("totalPage", pageInfo.getPages());
        map.put("totalNum", pageInfo.getTotal());
        map.put("currPage", currPage);
        map.put("pageSize",pageSize);
        if (tag.equals("screen")) {
            return "order_screen";
        } else if (tag.equals("manage")) {
            return "order_manage";
        } else {
            return "error";
        }
    }
}
