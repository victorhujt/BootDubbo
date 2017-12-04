package com.xescm.ofc.service.impl;

import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcFundamentalInformationMapper;
import com.xescm.ofc.service.OfcFundamentalInformationService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
    public OfcFundamentalInformation queryDataByCustOrderCode(String custOrderCode) {
        return ofcFundamentalInformationMapper.queryDataByCustOrderCode(custOrderCode);
    }

    @Override
    public OfcFundamentalInformation getLastMerchandiser(String operatorName) {
        return ofcFundamentalInformationMapper.getLastMerchandiser(operatorName);
    }

    /**
     * 根据客户订单编号与客户编号查询不是已经取消的订单
     * @param custOrderCode 客户订单编号
     * @param custCode 客户编码
     * @return
     */
    @Override
    public OfcFundamentalInformation queryOfcFundInfoByCustOrderCodeAndCustCode(String custOrderCode, String custCode){
        OfcFundamentalInformation ofcFundamentalInformation = new OfcFundamentalInformation();
        ofcFundamentalInformation.setCustOrderCode(custOrderCode);
        ofcFundamentalInformation.setCustCode(custCode);
        return ofcFundamentalInformationMapper.queryOfcFundInfoByCustOrderCodeAndCustCode(ofcFundamentalInformation);
    }


    @Override
    public int checkCustOrderCodeRepeat(String custOrderCode) {
        if(PubUtils.isSEmptyOrNull(custOrderCode)) {
            throw new BusinessException("校验客户订单编号重复失败, 原因: 客户订单编号为空!");
        }
        return ofcFundamentalInformationMapper.checkCustOrderCodeRepeat(custOrderCode);
    }


    /**
     * 根据订单批次号查询订单
     * @param orderBatchNumber 订单批次号
     * @return
     */
    @Override
    public List<OfcFundamentalInformation> queryFundamentalByBatchNumber(String orderBatchNumber) {
        logger.info("根据订单批次号查询订单, orderBatchNumber:{}", orderBatchNumber);
        if(PubUtils.isSEmptyOrNull(orderBatchNumber)){
            logger.error("根据订单批次号查询订单失败, 入参有误");
            throw new BusinessException("根据订单批次号查询订单失败");
        }
        List<OfcFundamentalInformation> ofcFundamentalInformationList = ofcFundamentalInformationMapper.queryOrderByOrderBatchNumber(orderBatchNumber);
        if(CollectionUtils.isEmpty(ofcFundamentalInformationList)){
            logger.error("根据订单批次号查询订单失败, 没有在改订单批次号下查到订单!");
            throw new BusinessException("根据订单批次号查询订单失败, 没有在改订单批次号下查到订单!");
        }
        return ofcFundamentalInformationList;
    }
}
