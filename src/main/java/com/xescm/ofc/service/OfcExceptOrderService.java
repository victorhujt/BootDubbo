package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcExceptOrder;
import com.xescm.ofc.model.dto.ofc.ExceptOrderDTO;

import java.util.List;

public interface OfcExceptOrderService extends IService<OfcExceptOrder>{

    void dealExceptOrder();

    List<OfcExceptOrder> selectByDTO(ExceptOrderDTO exceptOrderDTO);
}
