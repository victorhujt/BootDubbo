package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OrderFollowOperResult;
import com.xescm.ofc.domain.OrderSearchOperResult;
import com.xescm.ofc.model.dto.form.OrderOperForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OfcOrderOperMapper {

    /**
     *查询订单列表
     * @param form
     * @return list
     */
    List<OrderSearchOperResult> queryOrderList(@Param("form") OrderOperForm form);

    /**
     * 根据订单批次号查询订单列表
     * @param orderBatchNumber
     * @return list
     */
    List<OrderSearchOperResult> queryOrderByOrderBatchNumber(@Param("orderBatchNumber") String orderBatchNumber);

    List<OrderFollowOperResult> queryOrder(@Param("code") String code, @Param("searchType") String searchType);

}
