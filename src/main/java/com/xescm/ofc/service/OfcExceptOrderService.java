package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcEnumeration;
import com.xescm.ofc.domain.OfcExceptOrder;
import com.xescm.ofc.model.dto.ofc.OfcOrderPotDTO;
import com.xescm.ofc.model.dto.ofc.OfcExceptOrderDTO;

import java.util.List;
import java.util.Map;

public interface OfcExceptOrderService extends IService<OfcExceptOrder> {

    void dealExceptOrder(OfcExceptOrderDTO ofcExceptOrderDTO, List<String> orderCodes, List<OfcEnumeration> enumsOfZp, Map<String, OfcEnumeration> enumsOfTime) throws Exception;

    Map<String,OfcEnumeration> loadOfcTimeEnumMap(OfcEnumeration ofcEnumeration);

    List<String> loadUndealOrders(int indexNum) throws Exception;

    List<OfcExceptOrder> selectByDTO(OfcExceptOrderDTO ofcExceptOrderDTO);

    boolean isZhongPin(OfcExceptOrder ofcExceptOrder, List<OfcEnumeration> ofcEnumerations);

    int loadYesterdayOrder() throws Exception;

    int insertUndealOrder(OfcOrderPotDTO ofcOrderPotDTO);
}
