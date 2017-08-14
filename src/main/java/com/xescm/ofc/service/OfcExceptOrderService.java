package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcExceptOrder;
import com.xescm.ofc.edas.model.dto.ofc.OfcOrderPotDTO;
import com.xescm.ofc.model.dto.ofc.OfcExceptOrderDTO;

import java.util.List;

public interface OfcExceptOrderService extends IService<OfcExceptOrder> {

    void dealExceptOrder(OfcExceptOrderDTO ofcExceptOrderDTO) throws Exception;

    List<OfcExceptOrder> selectByDTO(OfcExceptOrderDTO ofcExceptOrderDTO);

    int loadYesterdayOrder() throws Exception;

    int insertUndealOrder(OfcOrderPotDTO ofcOrderPotDTO);
}
