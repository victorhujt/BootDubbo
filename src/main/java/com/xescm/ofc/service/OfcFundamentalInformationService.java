package com.xescm.ofc.service;

import com.xescm.ofc.model.vo.ofc.OfcBatchOrderVo;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcFundamentalInformationService extends IService<OfcFundamentalInformation> {
    String getOrderCodeByCustOrderCode(String custOrderCode);


    int checkCustOrderCode(OfcFundamentalInformation ofcFundamentalInformation);

    String getOrderCodeByCustOrderCodeAndCustCode(String custOrderCode, String custCode);

    OfcFundamentalInformation queryDataByCustOrderCode(String custOrderCode);

    OfcFundamentalInformation getLastMerchandiser(String operatorName);

    /**
     * 根据客户订单编号与客户编号查询订单
     * @param custOrderCode
     * @param custCode
     * @return
     */
    OfcFundamentalInformation queryOfcFundInfoByCustOrderCodeAndCustCode(String custOrderCode, String custCode);

    int checkCustOrderCodeRepeat(String custOrderCode);

    List<OfcFundamentalInformation> queryFundamentalByBatchNumber(String orderBatchNumber);
}
