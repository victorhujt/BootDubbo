package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcWarehouseInformation;
import com.xescm.ofc.model.dto.ofc.ModifyWarehouseDTO;
import com.xescm.rmc.edas.domain.vo.RmcWarehouseRespDto;

import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcWarehouseInformationService extends IService<OfcWarehouseInformation>{
    int deleteByOrderCode(Object key);
    int updateByOrderCode(Object key);
    OfcWarehouseInformation warehouseInformationSelect(String code);
    /*List<String> getWarehouseNameListByCustCode(String custCode);*/
    List<RmcWarehouseRespDto> getWarehouseListByCustCode(String custCode);

    OfcWarehouseInformation queryByOrderCode(String orderCode);

    List<RmcWarehouseRespDto> queryWarehouseByCustomerCode(String custCode);

    List<RmcWarehouseRespDto> queryWarehouseByBaseCode(ModifyWarehouseDTO modifyWarehouseDTO);


}
