package com.xescm.ofc.service;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.domain.OfcMobileOrder;
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

    String placeOrder(OfcOrderDTO ofcOrderDTO, List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos,AuthResDto authResDtoByToken
            , String custId, CscContantAndCompanyDto cscContantAndCompanyDtoConsignor
            , CscContantAndCompanyDto cscContantAndCompanyDtoConsignee, CscSupplierInfoDto cscSupplierInfoDto);



    int updateByMobileCode(OfcMobileOrder mobileOrder);

    /**
     * 自动获取待受理订单
     * @return
     */
    OfcMobileOrderVo autoAcceptPendingOrder(String user);

    void pushOrderToCache(String orderCode);

    void dealDingdingOrder();

    void deleteMobileOrder(String mobileOrder) throws UnsupportedEncodingException;

}
