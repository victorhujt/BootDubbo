package com.xescm.ofc.web.rest;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.ofc.domain.OfcPlanScreenCondition;
import com.xescm.ofc.domain.OfcPlanScreenResult;
import com.xescm.ofc.domain.OrderScreenCondition;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.OfcPlanScreenService;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//import org.apache.commons.lang.StringUtils;

/**
 * Created by lyh on 2016/10/10.
 */
@RequestMapping(value = "/ofc",produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcPlanScreenRest extends BaseController {

    @Autowired
    private OfcPlanScreenService ofcPlanScreenService;

    @RequestMapping(value = "/queryPlanPageByCondition", method=RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> queryPlanPageByCondition(Page<OrderScreenCondition> page, HttpServletRequest request, OfcPlanScreenCondition ofcPlanScreenCondition) {
        logger.debug("==>订单中心订单查询条件 queryPlanPageByCondition={}", ofcPlanScreenCondition);
//        logger.debug("==>订单中心订单查询标志位 tag={}", tag);
        PageInfo<OfcPlanScreenResult> pageInfo = null;
        try {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<OfcPlanScreenResult> orderScreenResults = ofcPlanScreenService.planScreen(ofcPlanScreenCondition);
           // pageInfo = new PageInfo<OfcPlanScreenResult>(orderScreenResults);
            pageInfo = new PageInfo<>(orderScreenResults);
            logger.info("pageInfo={}", pageInfo);
        }catch (BusinessException ex){
            logger.error("分页查询供应商集合出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }catch (Exception ex){
            logger.error("分页查询供应商集合出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, pageInfo);
    }
}
