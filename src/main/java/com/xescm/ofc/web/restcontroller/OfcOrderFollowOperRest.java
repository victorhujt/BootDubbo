package com.xescm.ofc.web.restcontroller;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.domain.OrderFollowOperResult;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.OfcOrderManageOperService;
import com.xescm.ofc.service.OrderFollowOperService;
import com.xescm.ofc.web.controller.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by lyh on 2016/10/12.
 * 订单追踪
 */
@RequestMapping(value = "/ofc", produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcOrderFollowOperRest extends BaseController {

    @Resource
    private OrderFollowOperService orderFollowOperService;
    @Resource
    private OfcOrderManageOperService ofcOrderManageOperService;

    @RequestMapping(value = "/orderFollowOpera")
    public String orderFollowOpera() {
        return "order_follow_opera";
    }

    @RequestMapping(value = "/orderFollowOperSearch", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> orderFollowOperSearch(String code, String searchType) throws Exception {
        logger.debug("==>订单中心订单追踪条件筛选code code={}", code);
        logger.debug("==>订单中心订单追踪条件标志位 searchType={}", searchType);

        try {
            code = PubUtils.trimAndNullAsEmpty(code);
            searchType = PubUtils.trimAndNullAsEmpty(searchType);
            if (StringUtils.isBlank(code)) {
                throw new Exception("搜索编码不能为空");
            }
            if (StringUtils.isBlank(searchType)) {
                throw new Exception("搜索类型不能为空");
            }
            Set<String> searchTypes = new HashSet<>();
            searchTypes.add("orderCode");
            searchTypes.add("custOrderCode");
            searchTypes.add("transCode");
            searchTypes.add("planCode");
            if (!searchTypes.contains(searchType)) {
                throw new Exception("搜索类型错误！");
            }
            Map<String, Object> map = new HashMap<>();
            List<OrderFollowOperResult> ofcOrderDTOs = ofcOrderManageOperService.queryOrder(code, searchType);
            List<OfcOrderStatus> ofcOrderStatuses = orderFollowOperService.queryOrderStatus(code, searchType);
            if (!CollectionUtils.isEmpty(ofcOrderDTOs)) {
                if (ofcOrderDTOs.size() == 1) {
                    map.put("size", 1);
                    map.put("ofcOrderDTO", ofcOrderDTOs.get(0));
                } else {
                    map.put("size", 2);
                    map.put("ofcOrderDTO", ofcOrderDTOs);
                }
            }
            if (!CollectionUtils.isEmpty(ofcOrderDTOs)) {
                map.put("ofcOrderStatus", ofcOrderStatuses);
            }
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, map);
        } catch (BusinessException ex){
            logger.error("订单中心订单追踪出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }catch (Exception ex) {
            logger.error("订单中心订单追踪出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

    /**
     * 根据orderCode查询  订单跟踪
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "queryOrderFollowByCode")
    @ResponseBody
    public Object queryOrderFollowByCode(String code) {
        try {
            final String searchType = "orderCode";
            List<OrderFollowOperResult> ofcOrderDTOs = ofcOrderManageOperService.queryOrder(code, searchType);
            List<OfcOrderStatus> ofcOrderStatuses = orderFollowOperService.queryOrderStatus(code, searchType);
            Map<String, Object> map = new HashMap<>();
            OrderFollowOperResult ofcFundamentalInformation = null;
            if (!CollectionUtils.isEmpty(ofcOrderDTOs)) {
                ofcFundamentalInformation = ofcOrderDTOs.get(0);
            }
            map.put("ofcOrderDTO", ofcFundamentalInformation);
            map.put("ofcOrderStatus", ofcOrderStatuses);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, map);
        } catch (BusinessException ex) {
            logger.error("订单中心订单追踪出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("订单中心订单追踪出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

}
