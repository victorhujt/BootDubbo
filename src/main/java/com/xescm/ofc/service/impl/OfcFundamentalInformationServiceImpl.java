package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.mapper.OfcFundamentalInformationMapper;
import com.xescm.ofc.model.vo.ofc.OfcBatchOrderVo;
import com.xescm.ofc.service.OfcFundamentalInformationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 *
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcFundamentalInformationServiceImpl extends BaseService<OfcFundamentalInformation> implements OfcFundamentalInformationService{

    @Resource
    private OfcFundamentalInformationMapper ofcFundamentalInformationMapper;

    @Override
    public String getOrderCodeByCustOrderCode(String custOrderCode) {
        return ofcFundamentalInformationMapper.getOrderCodeByCustOrderCode(custOrderCode);
    }

    @Override
    public int checkCustOrderCode(OfcFundamentalInformation ofcFundamentalInformation) {
        return ofcFundamentalInformationMapper.checkCustOrderCode(ofcFundamentalInformation);
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
     * 根据客户订单编号与客户编号查询不是已经取消的订单
     * @param custOrderCode     客户订单编号
     * @param custCode      客户编号
     * @return  OfcFundamentalInformation
     */
    public OfcFundamentalInformation queryOfcFundInfoByCustOrderCodeAndCustCode(String custOrderCode, String custCode){
        OfcFundamentalInformation ofcFundamentalInformation = new OfcFundamentalInformation();
        ofcFundamentalInformation.setCustOrderCode(custOrderCode);
        ofcFundamentalInformation.setCustCode(custCode);
        return ofcFundamentalInformationMapper.queryOfcFundInfoByCustOrderCodeAndCustCode(ofcFundamentalInformation);
    }
}
