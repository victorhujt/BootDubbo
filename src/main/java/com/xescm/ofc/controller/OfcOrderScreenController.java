package com.xescm.ofc.controller;

import com.xescm.ofc.domain.OrderScreenCondition;
import com.xescm.ofc.domain.OrderScreenResult;
import com.xescm.ofc.service.OfcOrderScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
@Controller
@RequestMapping(value = "/orderScreen")
public class OfcOrderScreenController {
    /*
    订单查询界面一进来,一条数据都没有, 这样可以减轻.
    */
    /*
    进来之后,重置所有筛选条件,如果此时再点击筛选,就是查所有.
     */

    @Autowired
    private OfcOrderScreenService ofcOrderScreenService;

    @RequestMapping(value = "/index")
    public String orderScreen(){
        return "OrderScreen";
    }

    @RequestMapping(value = "/orderScreenByCondition")
    public String orderScreenByCondition(OrderScreenCondition orderScreenCondition, Map<String,Object> map) throws IOException {
        List<OrderScreenResult> orderScreenResults = ofcOrderScreenService.orderScreen(orderScreenCondition);
        System.out.println("==============="+orderScreenCondition);
        map.put("orderList",orderScreenResults);
        map.put("test","test1111");
        System.out.println("1111111111111111111111");
        return "OrderScreen";
    }
}
