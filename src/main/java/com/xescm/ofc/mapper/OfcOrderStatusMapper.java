package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.utils.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface OfcOrderStatusMapper extends MyMapper<OfcOrderStatus> {
    int deleteByOrderCode(Object key);

    List<OfcOrderStatus> orderStatusScreen(Map<String, String> mapperMap);

    OfcOrderStatus orderStatusSelect(Map<String, String> mapperMap);

    OfcOrderStatus queryOrderStateByOrderCode(@Param(value = "orderCode") String orderCode);

    /**
     * 修改订单状态，并标记为作废
     *
     * @param orderCode
     */
    void cancelOrderStateByOrderCode(@Param(value = "orderCode") String orderCode);

    /**
     * 获取订单状态
     * @param code
     * @param searchType
     * @return
     */
    List<OfcOrderStatus> queryOrderStatus(@Param("code") String code, @Param("searchType") String searchType);

    OfcOrderStatus queryLastUpdateOrderByOrderCode(@Param(value = "orderCode") String orderCode);

    OfcOrderStatus queryLastTimeOrderByOrderCode(@Param(value = "orderCode") String orderCode);
}