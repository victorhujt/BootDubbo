package com.xescm.ofc.service;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyResponseDto;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.rmc.edas.domain.qo.RmcCompanyLineQO;
import com.xescm.rmc.edas.domain.vo.RmcCompanyLineVo;
import com.xescm.rmc.edas.domain.vo.RmcServiceCoverageForOrderVo;

import java.util.List;
import java.util.Map;

/**
 * Created by ydx on 2016/10/12.
 */
public interface OfcOrderManageService {
    String orderAudit(String orderCode,String orderStatus,String reviewTag, AuthResDto authResDtoByToken);

    Map orderStorageDetails(String orderCode);


    String auditStorageOrder(String orderCode,String reviewTag, AuthResDto authResDtoByToken);
    String orderAuditByTrans(OfcFundamentalInformation ofcFundamentalInformation, List<OfcGoodsDetailsInfo> goodsDetailsList,
                             OfcDistributionBasicInfo ofcDistributionBasicInfo, OfcFinanceInformation ofcFinanceInformation,
                             String orderStatus, String reviewTag, AuthResDto authResDtoByToken);

    String orderDelete(String orderCode,String orderStatus, AuthResDto authResDtoByToken);

    String orderCancel(String orderCode,AuthResDto authResDtoByToken);
    CscContantAndCompanyResponseDto getContactMessage(String contactCompanyName, String contactName, String purpose, String custId, AuthResDto authResDtoByToken);
    CscSupplierInfoDto getSupportMessage(String suppulierName, String suppulierContactName, String custId, AuthResDto authResDtoByToken);
    String planUpdate(String planCode, String planStatus,String serviceProviderName,String serviceProviderContact,String serviceProviderContactPhone,String userId);
    Wrapper<List<RmcCompanyLineVo>> companySelByApi(RmcCompanyLineQO rmcCompanyLineQO);
    Wrapper<?> orderAutoAuditFromOperation(OfcFundamentalInformation ofcFundamentalInformation, List<OfcGoodsDetailsInfo> goodsDetailsList,
                                          OfcDistributionBasicInfo ofcDistributionBasicInfo, OfcWarehouseInformation ofcWarehouseInformation,
                                          OfcFinanceInformation ofcFinanceInformation,String orderStatus, String reviewTag, AuthResDto authResDtoByToken);
	void pushOrderToAc(OfcFundamentalInformation ofcFundamentalInformation,
                       OfcFinanceInformation ofcFinanceInformation,
                       OfcDistributionBasicInfo ofcDistributionBasicInfo,
                       List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos);

    String orderAutoAudit(OfcFundamentalInformation ofcFundamentalInformation, List<OfcGoodsDetailsInfo> goodsDetailsList
            , OfcDistributionBasicInfo ofcDistributionBasicInfo, OfcWarehouseInformation ofcWarehouseInformation
            , OfcFinanceInformation ofcFinanceInformation, String orderStatus, String reviewTag, AuthResDto authResDtoByToken);

    void pushOrderToTfc(OfcFundamentalInformation ofcFundamentalInformation, OfcFinanceInformation ofcFinanceInformation
            , OfcDistributionBasicInfo ofcDistributionBasicInfo, List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos);

    void pushOrderToWhc(OfcFundamentalInformation ofcFundamentalInformation
            , List<OfcGoodsDetailsInfo> goodsDetailsList, OfcWarehouseInformation ofcWarehouseInformation
            , OfcFinanceInformation ofcFinanceInformation);

    RmcServiceCoverageForOrderVo copyDestinationPlace(String departurePlaceCode, RmcServiceCoverageForOrderVo rmcServiceCoverageForOrderVo);

    RmcServiceCoverageForOrderVo rmcServiceCoverageAPI(RmcServiceCoverageForOrderVo rmcServiceCoverageForOrderVo, String pickup);

    Wrapper<?> saveStorageOrder(OfcOrderDTO ofcOrderDTO, List<OfcGoodsDetailsInfo> goodsDetailsList, String reviewTag,CscContantAndCompanyDto cscContantAndCompanyDtoConsignor,CscContantAndCompanyDto cscContantAndCompanyDtoConsignee, CscSupplierInfoDto cscSupplierInfoDto,
                                AuthResDto authResDtoByToken);

    String copyOrder(String orderCode,AuthResDto authResDtoByToken);

}
