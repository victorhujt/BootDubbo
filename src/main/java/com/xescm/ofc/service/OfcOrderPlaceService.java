package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.model.dto.csc.CscContantAndCompanyDto;
import com.xescm.ofc.model.dto.csc.CscSupplierInfoDto;
import com.xescm.uam.domain.dto.AuthResDto;

import java.util.List;

/**
 * Created by ydx on 2016/10/12.
 */
public interface OfcOrderPlaceService{
    String placeOrder(OfcOrderDTO ofcOrderDTO, List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos, String tag, AuthResDto authResDtoByToken
                      , String custId, CscContantAndCompanyDto cscContantAndCompanyDtoConsignor
                      , CscContantAndCompanyDto cscContantAndCompanyDtoConsignee, CscSupplierInfoDto cscSupplierInfoDto);
}
