package com.xescm.ofc.service;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.domain.dto.csc.CscSupplierInfoDto;
import com.xescm.ofc.domain.dto.csc.vo.CscContantAndCompanyVo;
import com.xescm.ofc.domain.dto.rmc.RmcCompanyLineQO;
import com.xescm.ofc.domain.dto.rmc.RmcCompanyLineVo;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.Wrapper;

import java.util.List;

/**
 * Created by ydx on 2016/10/12.
 */
public interface OfcOrderManageService {
    String orderAudit(String orderCode,String orderStatus,String reviewTag, AuthResDto authResDtoByToken);
    String orderAuditByTrans(OfcFundamentalInformation ofcFundamentalInformation, List<OfcGoodsDetailsInfo> goodsDetailsList,
                             OfcDistributionBasicInfo ofcDistributionBasicInfo, OfcFinanceInformation ofcFinanceInformation,
                             String orderStatus, String reviewTag, AuthResDto authResDtoByToken);

    String orderDelete(String orderCode,String orderStatus, AuthResDto authResDtoByToken);
    String orderCancel(String orderCode,String orderStatus, AuthResDto authResDtoByToken);
    CscContantAndCompanyVo getContactMessage(String contactCompanyName, String contactName, String purpose,String custId, AuthResDto authResDtoByToken);
    CscSupplierInfoDto getSupportMessage(String suppulierName, String suppulierContactName,String custId, AuthResDto authResDtoByToken);
    String planUpdate(String planCode, String planStatus,String serviceProviderName,String serviceProviderContact,String serviceProviderContactPhone,String userId);
    Wrapper<List<RmcCompanyLineVo>> companySelByApi(RmcCompanyLineQO rmcCompanyLineQO);
    Wrapper<?> orderAutoAuditFromOperation(OfcFundamentalInformation ofcFundamentalInformation, List<OfcGoodsDetailsInfo> goodsDetailsList,
                                          OfcDistributionBasicInfo ofcDistributionBasicInfo, OfcWarehouseInformation ofcWarehouseInformation,
                                          OfcFinanceInformation ofcFinanceInformation,String orderStatus, String reviewTag, AuthResDto authResDtoByToken);
	Wrapper<?> orderAutoAuditFromOperation(
			OfcFundamentalInformation ofcFundamentalInformation,
			List<OfcGoodsDetailsInfo> goodsDetailsList,
			OfcDistributionBasicInfo ofcDistributionBasicInfo,
			OfcWarehouseInformation ofcWarehouseInformation,
			OfcFinanceInformation ofcFinanceInformation, String orderStatus,
			String reviewTag, AuthResDto authResDtoByToken);
}
