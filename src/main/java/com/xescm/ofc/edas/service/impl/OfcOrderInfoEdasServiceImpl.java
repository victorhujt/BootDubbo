package com.xescm.ofc.edas.service.impl;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.edas.model.dto.ofc.OfcOrderDetailInfoDto;
import com.xescm.ofc.edas.model.dto.ofc.OfcOrderInfoDto;
import com.xescm.ofc.edas.service.OfcOrderInfoEdasService;
import com.xescm.ofc.mapper.OfcOrderOperMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Title:      OfcOrderInfoEdasServiceImpl. </p>
 * <p>Description 订单信息edas接口 </p>
 * <p>Company:    http://www.hnxianyi.com </p>
 *
 * @Author nothing
 * @CreateDate 2017/3/2 13:31
 */
@Service
public class OfcOrderInfoEdasServiceImpl implements OfcOrderInfoEdasService {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OfcOrderOperMapper ofcOrderOperMapper;

    /**
     * <p>Title:      getOrderInfoByOrderCode. </p>
     * <p>Description 根据订单号批量查询订单信息.</p>
     *
     * @param orderCodes 订单号
     * @return 订单信息
     * @Author nothing
     * @CreateDate 2017/3/2 13:33
     */
    @Override
    public Wrapper<List<OfcOrderInfoDto>> getOrderInfoByOrderCode(List<String> orderCodes) {
        long start = System.currentTimeMillis();
        Wrapper<List<OfcOrderInfoDto>> result = null;
        try {
            if (PubUtils.isNotNullAndSmallerSize(orderCodes, 101)) {
                List<OfcOrderInfoDto> orderList = ofcOrderOperMapper.getOrderInfoByOrderCode(orderCodes);
                for (OfcOrderInfoDto orderInfo : orderList) {
                    String orderCode = orderInfo.getOrderCode();
                    if (!PubUtils.isOEmptyOrNull(orderCode)) {
                        List<OfcOrderDetailInfoDto> detail = ofcOrderOperMapper.getOrderDetailInfoByQrderCode(orderCode);
                        orderInfo.setOrderDetails(detail);
                    }
                }
                result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, orderList);
            } else if (PubUtils.isNull(orderCodes)) {
                result = WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_, "订单号参数不能为空！");
            } else if (PubUtils.isNotNullAndBiggerSize(orderCodes, 100)) {
                result = WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_, "每次最多查询100单！");
            }
        } catch (Exception ex) {
            LOGGER.error("查询订单发生异常: ", ex);
            result = WrapMapper.error();
        }
        LOGGER.info("======>查询耗时: {} 毫秒", (System.currentTimeMillis() - start));
        return result;
    }
}
