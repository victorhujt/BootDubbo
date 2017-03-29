package com.xescm.ofc.edas.service.impl;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.edas.model.dto.epc.QueryOrderStatusDto;
import com.xescm.ofc.edas.model.dto.whc.FeedBackInventoryDto;
import com.xescm.ofc.edas.service.OfcOrderStatusEdasService;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.CreateOrderService;
import com.xescm.ofc.service.OfcOrderNewstatusService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * Created by wangsongtao on 2016/12/27.
 */
@Service
public class OfcOrderStatusEdasServiceImpl implements OfcOrderStatusEdasService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Resource
    private CreateOrderService createOrderService;

    @Resource
    private OfcOrderNewstatusService OfcOrderNewstatusService;

    @Override
    public Wrapper<List<QueryOrderStatusDto>> queryOrderStatus(QueryOrderStatusDto queryOrderStatusDto) {
        logger.info("取消订单接口参数：queryOrderStatusDto：{}", ToStringBuilder.reflectionToString(queryOrderStatusDto));
        try {
            if (queryOrderStatusDto == null) {
                throw new IllegalArgumentException("查询QueryOrderStatusDto参数不能为空");
            }
            if (StringUtils.isBlank(queryOrderStatusDto.getCustCode())) {
                throw new IllegalArgumentException("客户订单编号不能为空");
            }
            List<QueryOrderStatusDto> list = createOrderService.queryOrderStatusList(queryOrderStatusDto);
            Wrapper<List<QueryOrderStatusDto>> wrapper = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, list);
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

    /**
     * 仓储中心反馈异常
     * @param feedBackInventoryDto
     * @return
     */
    @Override
    public Wrapper<?> feedBackInventory(FeedBackInventoryDto feedBackInventoryDto) {
        try{
            if(feedBackInventoryDto==null){
                throw new IllegalArgumentException("反馈库存异常Dto不能为空");
            }
            if(StringUtils.isEmpty(feedBackInventoryDto.getOrderCode())){
                throw new IllegalArgumentException("订单号不能为空");
            }
            if(StringUtils.isEmpty(feedBackInventoryDto.getReason())){
                throw new IllegalArgumentException("异常原因不能为空");
            }
            OfcOrderNewstatusService.FeedBackInventory(feedBackInventoryDto);

        }catch (Exception e){
            WrapMapper.wrap(Wrapper.ERROR_CODE,e.getMessage() );
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
    }

}
