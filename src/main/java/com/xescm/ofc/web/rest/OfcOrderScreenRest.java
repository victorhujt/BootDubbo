package com.xescm.ofc.web.rest;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.ofc.domain.OrderScreenCondition;
import com.xescm.ofc.domain.OrderScreenResult;
import com.xescm.ofc.service.OfcOrderScreenService;
import com.xescm.ofc.utils.JSONUtils;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.utils.PubUtils;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

//import org.apache.commons.lang.StringUtils;

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
        if(PubUtils.isSEmptyOrNull(orderScreenConditionJSON)){
            orderScreenConditionJSON = JSONUtils.objectToJson(new OrderScreenCondition());
        }
        OrderScreenCondition orderScreenCondition = JSONUtils.jsonToPojo(orderScreenConditionJSON, OrderScreenCondition.class);
        int pageSize = Integer.parseInt(pageSizePath);
        int currPage = Integer.parseInt(currPagePath);
        PageHelper.startPage(currPage,pageSize);
        List<OrderScreenResult> orderScreenResults = ofcOrderScreenService.orderScreen(orderScreenCondition);
        PageInfo<OrderScreenResult> pageInfo = new PageInfo<OrderScreenResult>(orderScreenResults);
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

    @RequestMapping(value = "/queryOrderPageByCondition", method=RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> queryOrderPageByCondition(Page<OrderScreenCondition> page, HttpServletRequest request, OrderScreenCondition orderScreenCondition) {
        logger.debug("==>订单中心订单查询条件 queryOrderPageByCondition={}", orderScreenCondition);
//        logger.debug("==>订单中心订单查询标志位 tag={}", tag);
        PageInfo<OrderScreenResult> pageInfo = null;
        try {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<OrderScreenResult> orderScreenResults = ofcOrderScreenService.orderScreen(orderScreenCondition);
            pageInfo = new PageInfo<OrderScreenResult>(orderScreenResults);
            logger.info("pageInfo={}", pageInfo);
        }catch (Exception ex){
            logger.error("分页查询订单列表出现异常:{},{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, pageInfo);
    }
}
