package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.domain.OfcMobileOrder;
import com.xescm.ofc.model.dto.csc.CscContantAndCompanyDto;
import com.xescm.ofc.model.dto.csc.CscSupplierInfoDto;
import com.xescm.ofc.model.dto.form.MobileOrderOperForm;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.uam.domain.dto.AuthResDto;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by hujintao on 2016/12/17.
 */
public interface OfcMobileOrderMapper extends Mapper<OfcMobileOrder> {

    OfcMobileOrder saveOfcMobileOrder(OfcMobileOrder ofcMobileOrder);

    List<OfcMobileOrder> queryOrderNotes(OfcMobileOrder ofcMobileOrder);

    List<OfcMobileOrder> queryOrderList(@Param(value = "form") MobileOrderOperForm form);

    String placeOrder(OfcOrderDTO ofcOrderDTO, List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos, String tag, AuthResDto authResDtoByToken
            , String custId, CscContantAndCompanyDto cscContantAndCompanyDtoConsignor
            , CscContantAndCompanyDto cscContantAndCompanyDtoConsignee, CscSupplierInfoDto cscSupplierInfoDto, String orderCode);



    int updateByMobileCode(Object key);
}
