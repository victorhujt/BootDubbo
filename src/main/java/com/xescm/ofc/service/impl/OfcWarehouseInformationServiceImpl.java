package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcWarehouseInformation;
import com.xescm.ofc.feign.client.FeignCscWarehouseAPIClient;
import com.xescm.ofc.mapper.OfcWarehouseInformationMapper;
import com.xescm.ofc.domain.OfcWarehouseInformation;
import com.xescm.ofc.service.OfcWarehouseInformationService;
import com.xescm.uam.utils.wrap.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
public class OfcWarehouseInformationServiceImpl extends BaseService<OfcWarehouseInformation> implements OfcWarehouseInformationService{
    @Autowired
    private OfcWarehouseInformationMapper ofcWarehouseInformationMapper;
    @Autowired
    private FeignCscWarehouseAPIClient feignCscWarehouseAPIClient;

    @Override
    public int deleteByOrderCode(Object key) {
        ofcWarehouseInformationMapper.deleteByOrderCode(key);
        return 0;
    }

    @Override
    public int updateByOrderCode(Object key) {
        ofcWarehouseInformationMapper.updateByOrderCode(key);
        return 0;
    }

    @Override
    public OfcWarehouseInformation warehouseInformationSelect(String orderCode) {
        OfcWarehouseInformation ofcWarehouseInformation = ofcWarehouseInformationMapper.ofcWarehouseInformationSelect(orderCode);
        if(ofcWarehouseInformation == null){
            return new OfcWarehouseInformation();
        }
        return ofcWarehouseInformation;
    }


    @Override
    public List<OfcWarehouseInformation> getWarehouseListByCustCode(String custCode) {
        Wrapper<List<String>> cscWarehouseByCustomerId = feignCscWarehouseAPIClient.getCscWarehouseByCustomerId(custCode);
        List<String> result = cscWarehouseByCustomerId.getResult();
        if(result.size() < 1){
            return new ArrayList<>();
        }
        List<OfcWarehouseInformation> warehouseList = new ArrayList<>();
        for(String warehouseCode : result){
            Wrapper<OfcWarehouseInformation> rmcWarehouseByid = feignCscWarehouseAPIClient.getRmcWarehouseByid(warehouseCode);
            OfcWarehouseInformation ofcWarehouseInformation = rmcWarehouseByid.getResult();
            if (null == ofcWarehouseInformation) {
                continue;
            }
            warehouseList.add(ofcWarehouseInformation);
        }
        if(warehouseList.size() < 1){
            return new ArrayList<>();
        }
        return warehouseList;
    }
}
