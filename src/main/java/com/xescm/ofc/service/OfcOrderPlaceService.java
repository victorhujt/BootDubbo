package com.xescm.ofc.service;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;

import java.util.List;

/**
 * Created by ydx on 2016/10/12.
 */
public interface OfcOrderPlaceService{
    String placeOrder(OfcOrderDTO ofcOrderDTO, List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos, String tag, AuthResDto authResDtoByToken
                      , String custId, CscContantAndCompanyDto cscContantAndCompanyDtoConsignor
                      , CscContantAndCompanyDto cscContantAndCompanyDtoConsignee, CscSupplierInfoDto cscSupplierInfoDto);
    OfcFundamentalInformation getAreaAndBaseMsg(AuthResDto authResDtoByToken, OfcFundamentalInformation ofcFundamentalInformation);
}
