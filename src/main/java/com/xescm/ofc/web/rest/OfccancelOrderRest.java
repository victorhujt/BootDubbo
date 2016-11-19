package com.xescm.ofc.web.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xescm.ofc.constant.ResultModel;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.domain.dto.coo.CreateOrderEntity;
import com.xescm.ofc.domain.dto.coo.CreateOrderGoodsInfo;
import com.xescm.ofc.domain.dto.coo.CreateOrderResult;
import com.xescm.ofc.domain.dto.coo.CreateOrderTrans;
import com.xescm.ofc.domain.dto.csc.*;
import com.xescm.ofc.domain.dto.csc.vo.CscContantAndCompanyVo;
import com.xescm.ofc.domain.dto.csc.vo.CscGoodsVo;
import com.xescm.ofc.domain.dto.csc.vo.CscStorevo;
import com.xescm.ofc.feign.client.FeignCscCustomerAPIClient;
import com.xescm.ofc.service.CreateOrderService;
import com.xescm.ofc.service.OfcOrderManageService;
import com.xescm.ofc.utils.CheckUtils;
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.ofc.utils.JsonUtil;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.domain.UamUser;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.Wrapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import static com.xescm.ofc.enums.OrderConstEnum.ALREADYEXAMINE;
import static com.xescm.ofc.enums.OrderConstEnum.CREATE_ORDER_BYAPI;

/**
 * 订单中心创建订单
 * Created by hiyond on 2016/11/15.
 */
@Controller
public class OfccancelOrderRest extends BaseController {

    @Autowired
    private CreateOrderService createOrderService;

    //    @RequestMapping(value = "")
    public void cancelOrder(String custOrderCode, String custCode) {
        logger.debug("取消api订单custOrderCode:{},custCode:{}", custOrderCode, custCode);
        try {
            ResultModel resultModel = createOrderService.cancelOrderStateByOrderCode(custOrderCode, custCode);
            logger.debug("取消api订单resultModel:{}", resultModel);
        } catch (Exception ex) {
            logger.error("取消api订单出错：{}，{}", ex.getMessage(), ex);
        }
    }

}
