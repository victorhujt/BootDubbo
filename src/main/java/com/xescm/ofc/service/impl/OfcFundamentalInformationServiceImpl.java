package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.mapper.OfcFundamentalInformationMapper;
import com.xescm.ofc.model.vo.ofc.OfcBatchOrderVo;
import com.xescm.ofc.service.OfcFundamentalInformationService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcFundamentalInformationServiceImpl extends BaseService<OfcFundamentalInformation> implements OfcFundamentalInformationService{

    @Autowired
    private OfcFundamentalInformationMapper ofcFundamentalInformationMapper;

    @Override
    public String getOrderCodeByCustOrderCode(String custOrderCode) {
        String orderCode = ofcFundamentalInformationMapper.getOrderCodeByCustOrderCode(custOrderCode);
        return orderCode;
    }

    @Override
    public int checkCustOrderCode(OfcFundamentalInformation ofcFundamentalInformation) {
        int result =  ofcFundamentalInformationMapper.checkCustOrderCode(ofcFundamentalInformation);
        return result;
    }

    @Override
    public String getOrderCodeByCustOrderCodeAndCustCode(String custOrderCode, String custCode) {
        return ofcFundamentalInformationMapper.getOrderCodeByCustOrderCodeAndCustCode(custOrderCode, custCode);
    }

    @Override
    public OfcBatchOrderVo queryByBatchNumber(String orderBatchNumber) {
        return ofcFundamentalInformationMapper.queryByBatchNumber(orderBatchNumber);
    }

    @Override
    public OfcFundamentalInformation queryDataByCustOrderCode(String custOrderCode) {
        return ofcFundamentalInformationMapper.queryDataByCustOrderCode(custOrderCode);
    }

    @Override
    public OfcFundamentalInformation getLastMerchandiser(String operatorName) {
        return ofcFundamentalInformationMapper.getLastMerchandiser(operatorName);
    }

    /**
     * 根据客户订单编号与客户编号查询订单
     * @param custOrderCode
     * @param custCode
     * @return
     */
    public OfcFundamentalInformation queryOfcFundInfoByCustOrderCodeAndCustCode(String custOrderCode, String custCode){
        OfcFundamentalInformation ofcFundamentalInformation = new OfcFundamentalInformation();
        ofcFundamentalInformation.setCustOrderCode(custOrderCode);
        ofcFundamentalInformation.setCustCode(custCode);
        List<OfcFundamentalInformation> ofcFundamentalInformations = ofcFundamentalInformationMapper.select(ofcFundamentalInformation);
        if(CollectionUtils.isNotEmpty(ofcFundamentalInformations)){
            return ofcFundamentalInformations.get(0);
        }
        return null;
    }
}
