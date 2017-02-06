package com.xescm.ofc.edas.service.impl;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.edas.model.dto.epc.CancelOrderDto;
import com.xescm.ofc.edas.model.vo.epc.CannelOrderVo;
import com.xescm.ofc.edas.service.OfcOrderCancelEdasServie;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.CreateOrderService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * Created by wangsongtao on 2016/12/27.
 */
@Service
public class OfcOrderCancelEdasServieImpl implements OfcOrderCancelEdasServie{
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private CreateOrderService createOrderService;

    /**
     * 鲜易网取消接口
     *
     * @param
     * @return
     */
    @Override
    public Wrapper<CannelOrderVo> orderCancel(CancelOrderDto cancelOrderDto) {
        logger.info("取消订单接口参数：custOrderCode：{}", ToStringBuilder.reflectionToString(cancelOrderDto));
        try {
            if (cancelOrderDto == null) {
                throw new IllegalArgumentException("客户订单编号不能为空");
            }
            if (StringUtils.isBlank(cancelOrderDto.getCustOrderCode())) {
                throw new IllegalArgumentException("客户订单编号不能为空");
            }
            if (StringUtils.isBlank(cancelOrderDto.getCustCode())) {
                throw new IllegalArgumentException("货主编码不能为空");
            }
            Wrapper<CannelOrderVo> wrapper = createOrderService.cancelOrderStateByOrderCode(cancelOrderDto);
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
