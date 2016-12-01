package com.xescm.ofc.service;

import com.xescm.ofc.model.vo.ofc.OfcBatchOrderVo;
import com.xescm.ofc.domain.OfcFundamentalInformation;

import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcFundamentalInformationService extends IService<OfcFundamentalInformation> {
    String getOrderCodeByCustOrderCode(String custOrderCode);


    int checkCustOrderCode(OfcFundamentalInformation ofcFundamentalInformation);

    String getOrderCodeByCustOrderCodeAndCustCode(String custOrderCode, String custCode);

    OfcBatchOrderVo queryByBatchNumber(String orderBatchNumber);

    OfcFundamentalInformation queryDataByCustOrderCode(String custOrderCode);

}
