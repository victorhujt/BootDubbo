package com.xescm.ofc.web.rest;

import com.xescm.ofc.constant.ResultModel;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.dto.epc.CancelOrderDto;
import com.xescm.ofc.domain.dto.epc.CannelOrderVo;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.CreateOrderService;
import com.xescm.ofc.service.OfcFundamentalInformationService;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订单中心创建订单
 * Created by hiyond on 2016/11/15.
 */
@Controller
public class OfcCancelOrderRest extends BaseController {

    @Autowired
    private CreateOrderService createOrderService;
    @Autowired
    private OfcFundamentalInformationService ofcFundamentalInformationService;

    @RequestMapping(value = "/api/orderCancel", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object cancelOrder(CancelOrderDto cancelOrderDto) {
        logger.debug("CancelOrderDto:{},custCode:{}", cancelOrderDto);
        Wrapper<CannelOrderVo> wrapper = null;
        try {
            if (cancelOrderDto == null) {
                throw new BusinessException("CancelOrderDto无效");
            }
            String custOrderCode = cancelOrderDto.getCustOrderCode();
            OfcFundamentalInformation ofcFundamentalInformation = new OfcFundamentalInformation();
            ofcFundamentalInformation.setCustOrderCode(custOrderCode);
            ofcFundamentalInformation = ofcFundamentalInformationService.selectOne(ofcFundamentalInformation);
            if (ofcFundamentalInformation != null) {
                String orderCode = ofcFundamentalInformation.getOrderCode();
                String custCode = ofcFundamentalInformation.getCustCode();
                wrapper = createOrderService.cancelOrderStateByOrderCode(custOrderCode, orderCode, custCode);
            } else {
                CannelOrderVo cannelOrderVo = new CannelOrderVo();
                cannelOrderVo.setCustOrderCode(custOrderCode);
                cannelOrderVo.setCustCode("");
                cannelOrderVo.setResultCode("0");
                cannelOrderVo.setReason("无效的客户订单编号");
                return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ILLEGAL_ARGUMENT_MESSAGE, cannelOrderVo);
            }
            logger.debug("取消api订单resultModel:{}", wrapper);
        } catch (Exception ex) {
            logger.error("取消api订单出错：{}，{}", ex.getMessage(), ex);
            wrapper = WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
        return wrapper;
    }

}
