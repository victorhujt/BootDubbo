package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcCustOrderStatus;
import com.xescm.ofc.utils.MyMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface OfcCustOrderStatusMapper extends MyMapper<OfcCustOrderStatus> {
    OfcCustOrderStatus orderStatusSelect(String orderCode, String tag);
}