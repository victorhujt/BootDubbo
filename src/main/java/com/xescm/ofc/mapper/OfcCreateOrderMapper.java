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
     * @param custOrderCode 客户订单号
     * @param custCode 货主编码
     * @return queryCountByOrderStatus
     */
    int queryCountByOrderStatus(@Param(value = "custOrderCode") String custOrderCode, @Param(value = "custCode") String custCode);



}
