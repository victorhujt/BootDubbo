package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcWarehouseInformation;
import com.xescm.ofc.domain.dto.CscWarehouse;
import com.xescm.ofc.domain.dto.RmcWarehouse;
import com.xescm.ofc.exception.BusinessException;
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
    public List<RmcWarehouse> getWarehouseListByCustCode(String custCode) {
        try{
            Wrapper<List<CscWarehouse>> cscWarehouseByCustomerId = feignCscWarehouseAPIClient.getCscWarehouseByCustomerId(custCode);
            List<CscWarehouse> result = cscWarehouseByCustomerId.getResult();
            if(result.size() < 1){
                return new ArrayList<>();
            }
            List<RmcWarehouse> warehouseList = new ArrayList<>();
            for(CscWarehouse cscWarehouse : result){
                Wrapper<RmcWarehouse> rmcWarehouseByid = feignCscWarehouseAPIClient.getRmcWarehouseByid(cscWarehouse.getWarehouseCode());
                RmcWarehouse rmcWarehouse = rmcWarehouseByid.getResult();
                if (null == rmcWarehouse) {
                    continue;
                }
                warehouseList.add(rmcWarehouse);
            }
            if(warehouseList.size() < 1){
                return new ArrayList<>();
            }
            return warehouseList;

        }catch (Exception ex){
            throw new BusinessException("下单页面抓取仓库信息失败");
        }

    }
}
