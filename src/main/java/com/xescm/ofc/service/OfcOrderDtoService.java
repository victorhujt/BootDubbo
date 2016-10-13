package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcOrderDTO;

/**
 * Created by lyh on 2016/10/13.
 */
public interface OfcOrderDtoService {
    OfcOrderDTO orderDtoSelect(String code,String dtoTag);
}
