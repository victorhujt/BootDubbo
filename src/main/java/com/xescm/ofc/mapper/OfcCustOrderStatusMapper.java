package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcCustOrderStatus;
import com.xescm.ofc.utils.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfcCustOrderStatusMapper extends MyMapper<OfcCustOrderStatus> {

    List<OfcCustOrderStatus> queryByOrderCode(@Param(value = "orderCode") String orderCode);
}