package com.xescm.ofc.service;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.ofc.domain.OfcDistributionBasicInfo;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcDistributionBasicInfoService extends IService<OfcDistributionBasicInfo> {
    int deleteByOrderCode(Object key);
    int updateByOrderCode(Object key);
    int checkTransCode(OfcDistributionBasicInfo ofcDistributionBasicInfo);
    OfcDistributionBasicInfo queryByOrderCode(String orderCode);
    OfcDistributionBasicInfo distributionBasicInfoSelect(String code);

    String getOrderCodeByTransCode(String transCode);

    String getKabanOrderCodeByTransCode(String transCode);

    String getLastedKabanOrderCodeByTransCode(String transCode);


    Wrapper<?> validateDistrictContactMessage(CscContantAndCompanyDto cscContantAndCompanyDtoConsignor, CscContantAndCompanyDto cscContantAndCompanyDtoConsignee);

    int updateAddressByOrderCode(Object key);
}
