package com.xescm.ofc.service;

import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by lyh on 2016/10/13.
 */
public interface OfcOrderDtoService {
    OfcOrderDTO orderDtoSelect(String code,String dtoTag) throws InvocationTargetException;
}
