package com.xescm.ofc.service;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.model.dto.ofc.OfcOrderInfoDTO;

import java.util.List;

/**
 * Created by ydx on 2016/10/12.
 */
public interface OfcOrderPlaceService{
    
    /**
     * <p>Title:      placeOrder. </p>
     * <p>Description 下单（不包含城配导单）</p>
     * 
     * @param 
     * @author nothing
     * @date 2017/8/31 17:26
     * @return 
     */
    String placeOrder(OfcOrderDTO ofcOrderDTO, List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos, String tag, AuthResDto authResDtoByToken
                      , String custId, CscContantAndCompanyDto cscContantAndCompanyDtoConsignor
                      , CscContantAndCompanyDto cscContantAndCompanyDtoConsignee, CscSupplierInfoDto cscSupplierInfoDto);
    OfcFundamentalInformation getAreaAndBaseMsg(AuthResDto authResDtoByToken, OfcFundamentalInformation ofcFundamentalInformation);


    void orderAuthByConsignorAddr(AuthResDto authResDto, OfcDistributionBasicInfo ofcDistributionBasicInfo, OfcFundamentalInformation ofcFundamentalInformation);

    void updateBaseAndAreaBywarehouseCode(String warehouseCode, OfcFundamentalInformation ofcFundamentalInformation);

    /**
     * <p>Title:      distributionPlaceOrder. </p>
     * <p>Description 城配导单</p>
     * 
     * @param 
     * @author nothing
     * @date 2017/8/31 17:26
     * @return 
     */
    OfcOrderInfoDTO distributionPlaceOrder(OfcOrderDTO ofcOrderDTO, List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos, String tag, AuthResDto authResDtoByToken, String custId
        , CscContantAndCompanyDto cscContantAndCompanyDtoConsignor
        , CscContantAndCompanyDto cscContantAndCompanyDtoConsignee);

}
