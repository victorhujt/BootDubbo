package com.xescm.ofc.web.restcontroller.customerWorkbench.transportOrder;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.service.OfcCustOrderPlaceService;
import com.xescm.ofc.web.controller.BaseController;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 *
 * Created by lyh on 2017/9/8.
 */
@Controller
@RequestMapping(value = "/page/ofc/cust/transport")
@Api(value = "OfcCustTransportOrderController", tags = "OfcCustTransportOrderController", description = "客户工作台运输订单", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OfcCustTransportOrderController extends BaseController{

    @Resource
    private OfcCustOrderPlaceService ofcCustOrderPlaceService;

    /**
     * 客户工作台运输开单
     * @param param        订单基本信息、收发货方信息
     * @param tag           标识下单、编辑、运输开单
     * @return      Wrapper
     */
    @RequestMapping("/orderPlaceCon/{tag}")
    @ResponseBody
    public Wrapper<?> orderPlace(@RequestBody OfcOrderDTO param, @PathVariable String tag){
        logger.info("==>客户工作台运输开单 ofcOrderDTOStr={}", param);
        logger.info("==>客户工作台运输开单 tag={}", tag);
        String resultMessage;
        try {
            resultMessage = ofcCustOrderPlaceService.placeTransOrder(getAuthResDtoByToken(), param, tag);
        } catch (BusinessException ex){
            logger.error("客户工作台运输开单出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("客户工作台运输开单出现未知异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, resultMessage);
    }



}
