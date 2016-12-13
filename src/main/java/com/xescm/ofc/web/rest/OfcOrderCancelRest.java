package com.xescm.ofc.web.rest;

import com.xescm.ofc.model.dto.epc.CancelOrderDto;
import com.xescm.ofc.model.vo.epc.CannelOrderVo;
import com.xescm.ofc.service.CreateOrderService;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/api/epc/order/orderCancel", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @ApiOperation(value = "鲜易网取消接口", notes = "返回是否成功", response = Wrapper.class)
    public Wrapper<CannelOrderVo> orderCancel(@RequestBody CancelOrderDto cancelOrderDto) {
        logger.info("取消订单接口参数：custOrderCode：{}", ToStringBuilder.reflectionToString(cancelOrderDto));
        Wrapper<CannelOrderVo> wrapper = null;
        try {
            if (cancelOrderDto == null) {
                throw new Exception("客户订单编号不能为空");
            }
            if (StringUtils.isBlank(cancelOrderDto.getCustOrderCode())) {
                throw new Exception("客户订单编号不能为空");
            }
            wrapper = createOrderService.cancelOrderStateByOrderCode(cancelOrderDto.getCustOrderCode());
            return wrapper;
        } catch (Exception ex) {
            logger.error("取消订单接口处理失败：错误原因：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
    }

}
