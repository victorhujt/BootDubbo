package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcMobileOrder;
import com.xescm.ofc.model.dto.form.MobileOrderOperForm;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ofcMobileOrderMapper extends Mapper<OfcMobileOrder> {
    List<OfcMobileOrder> queryOrderNotes(@Param("mobileOrderStatus") String mobileOrderStatus);
    List<OfcMobileOrder> queryOrderList(@Param("form") MobileOrderOperForm form);

}