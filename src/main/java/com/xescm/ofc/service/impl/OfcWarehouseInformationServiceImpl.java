package com.xescm.ofc.service.impl;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.csc.model.domain.CscWarehouse;
import com.xescm.csc.model.dto.QueryWarehouseDto;
import com.xescm.csc.provider.CscWarehouseEdasService;
import com.xescm.ofc.domain.OfcWarehouseInformation;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.client.FeignRmcWarehouseAPIClient;
import com.xescm.ofc.mapper.OfcWarehouseInformationMapper;
import com.xescm.ofc.model.dto.rmc.RmcWarehouse;
import com.xescm.ofc.service.OfcWarehouseInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcWarehouseInformationServiceImpl extends BaseService<OfcWarehouseInformation> implements OfcWarehouseInformationService{
    @Autowired
    private OfcWarehouseInformationMapper ofcWarehouseInformationMapper;
    @Autowired
    private CscWarehouseEdasService cscWarehouseEdasService;
    @Autowired
    private FeignRmcWarehouseAPIClient feignRmcWarehouseAPIClient;

    @Override
    public int deleteByOrderCode(Object key) {

        return ofcWarehouseInformationMapper.deleteByOrderCode(key);
    }

    @Override
    public int updateByOrderCode(Object key) {

        return ofcWarehouseInformationMapper.updateByOrderCode(key);
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
    public List<RmcWarehouse> getWarehouseListByCustCode(String customerCode) {///1
        try{
            QueryWarehouseDto cscWarehouse = new QueryWarehouseDto();
            cscWarehouse.setCustomerCode(customerCode);
            logger.info("###################################当前的客户编码为："+customerCode);
            Wrapper<List<CscWarehouse>> cscWarehouseByCustomerId = (Wrapper<List<CscWarehouse>>)cscWarehouseEdasService.getCscWarehouseByCustomerId(cscWarehouse);
            if(Wrapper.ERROR_CODE == cscWarehouseByCustomerId.getCode()){
                throw new BusinessException(cscWarehouseByCustomerId.getMessage());
            }
            List<CscWarehouse> result = cscWarehouseByCustomerId.getResult();
            logger.info("###############################查询该客户对应的仓库信息为："+result+"###########");
            if(null == result){
                return new ArrayList<>();
            }
            List<RmcWarehouse> warehouseList = new ArrayList<>();
            for(CscWarehouse cscWH : result){
                RmcWarehouse rmcWarehouse = new RmcWarehouse();
                rmcWarehouse.setWarehouseCode(cscWH.getWarehouseCode());
                RmcWarehouse rmcWarehouseResult = new RmcWarehouse();
                Wrapper<RmcWarehouse> rmcWarehouseByid = feignRmcWarehouseAPIClient.queryByWarehouseCode(rmcWarehouse);
                if(Wrapper.ERROR_CODE == rmcWarehouseByid.getCode()){
                    //throw new BusinessException(rmcWarehouseByid.getMessage());
                    continue;
                }
                rmcWarehouseResult = rmcWarehouseByid.getResult();
                if (null == rmcWarehouseResult) {
                    continue;
                }
                warehouseList.add(rmcWarehouseResult);
            }
            if(warehouseList.size() < 1){
                return new ArrayList<>();
            }
            return warehouseList;

        }catch (BusinessException ex){
            throw new BusinessException(ex.getMessage());
        }
        catch (Exception ex){
            throw new BusinessException("下单页面抓取仓库信息失败",ex);
        }

    }
}
