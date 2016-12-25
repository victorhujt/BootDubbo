package com.xescm.ofc.service;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.domain.OfcMobileOrder;
import com.xescm.ofc.model.dto.csc.CscContantAndCompanyDto;
import com.xescm.ofc.model.dto.csc.CscSupplierInfoDto;
import com.xescm.ofc.model.dto.form.MobileOrderOperForm;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.model.vo.ofc.OfcMobileOrderVo;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by hujintao on 2016/12/12.
 */

public interface OfcMobileOrderService extends IService<OfcMobileOrder>{

     OfcMobileOrder saveOfcMobileOrder(OfcMobileOrder ofcMobileOrder);

      List<OfcMobileOrder> queryOrderNotes(OfcMobileOrder ofcMobileOrder);

    List<OfcMobileOrder> queryOrderList(MobileOrderOperForm form);

    OfcMobileOrderVo selectOneOfcMobileOrder(OfcMobileOrder ofcMobileOrder) throws UnsupportedEncodingException;

    String placeOrder(OfcOrderDTO ofcOrderDTO, List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos, String tag, AuthResDto authResDtoByToken
            , String custId, CscContantAndCompanyDto cscContantAndCompanyDtoConsignor
            , CscContantAndCompanyDto cscContantAndCompanyDtoConsignee, CscSupplierInfoDto cscSupplierInfoDto,String orderCode);



    int updateByMobileCode(OfcMobileOrder mobileOrder);
}
