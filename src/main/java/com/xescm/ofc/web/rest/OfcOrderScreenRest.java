package com.xescm.ofc.web.rest;

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
 订单状态:
 待审核:0
 已审核:1
 执行中:2
 已完成:3
 已取消:4
 */
@RequestMapping(value = "/ofc")
@Controller
/*@RequestMapping(value = "/orderScreen")*/
public class OfcOrderScreenRest extends BaseController {
    /*
    订单查询界面一进来,一条数据都没有, 这样可以减轻.
    */
    /*
    进来之后,重置所有筛选条件,如果此时再点击筛选,就是查所有.
     */

    @Autowired
    private OfcOrderScreenService ofcOrderScreenService;




    @RequestMapping(value = "/orderScreenByCondition/{orderScreenConditionJSON}/{tag}")
    public String orderScreenByCondition(Model model,@PathVariable String orderScreenConditionJSON, Map<String,Object> map,@PathVariable("tag") String tag) throws IOException {
        logger.debug("==>订单中心订单查询条件 orderScreenCondition={}", orderScreenConditionJSON);
        logger.debug("==>订单中心订单查询标志位 tag={}", tag);
        if(StringUtils.isBlank(orderScreenConditionJSON)){
            orderScreenConditionJSON = JSONUtils.objectToJson(new OrderScreenCondition());
        }
        OrderScreenCondition orderScreenCondition = JSONUtils.jsonToPojo(orderScreenConditionJSON, OrderScreenCondition.class);
        List<OrderScreenResult> orderScreenResults = ofcOrderScreenService.orderScreen(orderScreenCondition);
        map.put("orderList", orderScreenResults);
        if (tag.equals("screen")) {
            return "order_screen";
        } else if (tag.equals("manage")) {
            return "order_manage";
        } else {
            return "error";
        }
    }
}
