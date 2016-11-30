package com.xescm.ofc.web.rest;

import com.xescm.ofc.model.dto.epc.CancelOrderDto;
import com.xescm.ofc.model.vo.epc.CannelOrderVo;
import com.xescm.ofc.service.CreateOrderService;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订单取消接口
 * Created by hiyond on 2016/11/30.
 */
@Controller
public class OfcOrderCancelRest extends BaseController {

    @Autowired
    private CreateOrderService createOrderService;

    /**
     * 鲜易网取消接口
     * @param cancelOrderDto
     * @return
     */
    @RequestMapping("/api/epc/order/orderCancel")
    @ResponseBody
    public Wrapper<?> orderCancel(CancelOrderDto cancelOrderDto) {
        logger.info("取消订单接口参数：cancelOrderDto：{}", cancelOrderDto);
        Wrapper<CannelOrderVo> wrapper = null;
        try {
            if (cancelOrderDto == null) {
                throw new Exception("取消参数不能为空");
            }
            wrapper = createOrderService.cancelOrderStateByOrderCode(cancelOrderDto.getCustOrderCode());
            return wrapper;
        } catch (Exception ex) {
            logger.error("取消订单接口处理失败：错误原因：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
    }

}
