package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcCustOrderNewstatus;
import com.xescm.ofc.utils.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OfcCustOrderNewstatusMapper extends MyMapper<OfcCustOrderNewstatus> {
    OfcCustOrderNewstatus queryByOrderCode(@Param(value = "orderCode") String orderCode);
}