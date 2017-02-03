package com.xescm.ofc.web.restcontroller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OrderScreenCondition;
import com.xescm.ofc.domain.OrderScreenResult;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.OfcOrderScreenService;
import com.xescm.ofc.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String orderScreenByCondition(@PathVariable String orderScreenConditionJSON, Map<String,Object> map,@PathVariable("tag") String tag, @PathVariable("currPage") String currPagePath, @PathVariable("pageSize") String pageSizePath) throws IOException {
        logger.debug("==>订单中心订单查询条件 orderScreenCondition={}", orderScreenConditionJSON);
        logger.debug("==>订单中心订单查询标志位 tag={}", tag);
        OrderScreenCondition orderScreenCondition;
        try {
            if(PubUtils.isSEmptyOrNull(orderScreenConditionJSON)){
                orderScreenConditionJSON = JacksonUtil.toJsonWithFormat(new OrderScreenCondition());
            }
            orderScreenCondition = JacksonUtil.parseJsonWithFormat(orderScreenConditionJSON, OrderScreenCondition.class);
        } catch (Exception e) {
            logger.error("订单查询转换Json异常{}",e);
            throw new BusinessException("订单查询转换Json异常",e);
        }
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

    @RequestMapping(value = "/queryOrderPageByCondition", method=RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> queryOrderPageByCondition(Page<OrderScreenCondition> page,  OrderScreenCondition orderScreenCondition) {
        logger.debug("==>订单中心订单查询条件 queryOrderPageByCondition={}", orderScreenCondition);
//        logger.debug("==>订单中心订单查询标志位 tag={}", tag);
        PageInfo<OrderScreenResult> pageInfo;
        try {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<OrderScreenResult> orderScreenResults = ofcOrderScreenService.orderScreen(orderScreenCondition);
           // pageInfo = new PageInfo<OrderScreenResult>(orderScreenResults);
            pageInfo = new PageInfo<>(orderScreenResults);
            logger.info("pageInfo={}", pageInfo);
        }catch (BusinessException ex){
            logger.error("分页查询订单列表出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }catch (Exception ex){
            logger.error("分页查询订单列表出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, pageInfo);
    }
}
