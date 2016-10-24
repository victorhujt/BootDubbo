package com.xescm.ofc.web.rest;

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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lyh on 2016/10/10.
 */
@RequestMapping(value = "/ofc")
@Controller
public class OfcOrderScreenRest extends BaseController {


    @Autowired
    private OfcOrderScreenService ofcOrderScreenService;

    @RequestMapping(value = "/orderScreenByCondition/{orderScreenConditionJSON}/{tag}/{currPage}/{pageNum}")
    public String orderScreenByCondition(Model model,@PathVariable String orderScreenConditionJSON, Map<String,Object> map,@PathVariable("tag") String tag, @PathVariable String currPage, @PathVariable String pageNum) throws IOException {
        logger.debug("==>订单中心订单查询条件 orderScreenCondition={}", orderScreenConditionJSON);
        logger.debug("==>订单中心订单查询标志位 tag={}", tag);
        if(StringUtils.isBlank(orderScreenConditionJSON)){
            orderScreenConditionJSON = JSONUtils.objectToJson(new OrderScreenCondition());
        }
        OrderScreenCondition orderScreenCondition = JSONUtils.jsonToPojo(orderScreenConditionJSON, OrderScreenCondition.class);
        List<OrderScreenResult> orderScreenResults = ofcOrderScreenService.orderScreen(orderScreenCondition);
        PageHelper.startPage(Integer.parseInt(currPage),Integer.parseInt(pageNum));
        PageInfo<OrderScreenResult> pageInfo = new PageInfo<>(orderScreenResults);
        map.put("orderList", pageInfo.getList());
        map.put("totalPage", pageInfo.getPages());
        map.put("totalNum", pageInfo.getTotal());
        if (tag.equals("screen")) {
            return "order_screen";
        } else if (tag.equals("manage")) {
            return "order_manage";
        } else {
            return "error";
        }
    }
}
