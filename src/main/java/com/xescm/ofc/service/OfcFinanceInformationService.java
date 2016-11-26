package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcBatchOrderVo;
import com.xescm.ofc.domain.OfcFinanceInformation;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcFinanceInformationService extends IService<OfcFinanceInformation>{

    OfcFinanceInformation queryByOrderCode(String orderCode);


}
