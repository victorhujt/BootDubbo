package com.xescm.ofc.web.api;

import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.epc.QueryOrderStatusDto;
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

import java.util.List;

/**
 * Created by hiyond on 2016/12/19.
 */
@Controller
@RequestMapping(value = "api", produces = {"application/json;charset=UTF-8"})
public class OfcQueryOrderStatusApiController extends BaseController {

    @Autowired
    private CreateOrderService createOrderService;

    /**
     * 对接平台定时获取订单中心的鲜易网七天内待发货状态中的订单
     * 参数：客户编码 订单日期开始日期 订单日期结束日期
     * 仅需要订单状态为【待审核】、【已审核】、【任务中】 三种状态的订单列表
     * 返回：List
     *
     * @param queryOrderStatusDto
     * @return
     */
    @RequestMapping(value = "epc/order/queryOrderStatus")
    @ResponseBody
    @ApiOperation(value = "对接平台定时获取订单中心的鲜易网", notes = "list", response = Wrapper.class)
    public Wrapper<List<QueryOrderStatusDto>> queryOrderStatus(@RequestBody QueryOrderStatusDto queryOrderStatusDto) {
        logger.info("取消订单接口参数：queryOrderStatusDto：{}", ToStringBuilder.reflectionToString(queryOrderStatusDto));
        Wrapper<List<QueryOrderStatusDto>> wrapper = null;
        try {
            if (queryOrderStatusDto == null) {
                throw new IllegalArgumentException("查询参数不能为空");
            }
            if (StringUtils.isBlank(queryOrderStatusDto.getCustCode())) {
                throw new IllegalArgumentException("客户订单编号不能为空");
            }
            List<QueryOrderStatusDto> list = createOrderService.queryOrderStatusList(queryOrderStatusDto);
            wrapper = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, list);
            return wrapper;
        } catch (IllegalArgumentException ex) {
            logger.error("取消订单接口处理失败：错误原因：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (BusinessException ex) {
            logger.error("取消订单接口处理失败：错误原因：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("取消订单接口处理失败：错误原因：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }
}
