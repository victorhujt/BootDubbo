package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

/**
 *
 * Created by hiyond on 2016/11/15.
 */
public interface OfcCreateOrderMapper {

    /**
     * 根据订单号与订单状态查询
     * @param orderCode 订单号
     * @param orderStatus 订单状态
     * @return queryCountByOrderStatus
     */
    int queryCountByOrderStatus(@Param(value = "orderCode") String orderCode, @Param(value = "orderStatus") String orderStatus);



}
