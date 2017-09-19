package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcCustFinanceInformation;
import com.xescm.ofc.service.OfcCustFinanceInformationService;
import org.springframework.stereotype.Service;

/**
 *
 * Created by lyh on 2017/9/15.
 */
@Service
public class OfcCustFinanceInformationServiceImpl extends BaseService<OfcCustFinanceInformation> implements OfcCustFinanceInformationService {
    @Override
    public OfcCustFinanceInformation queryByOrderCode(String orderCode) {
        return null;
    }
}
