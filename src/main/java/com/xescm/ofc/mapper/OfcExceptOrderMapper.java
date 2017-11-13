package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcExceptOrder;
import com.xescm.ofc.model.dto.ofc.OfcExceptOrderDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
@Repository
public interface OfcExceptOrderMapper extends Mapper<OfcExceptOrder> {
    /**
     * 这里未处理订单指: 非(正常订单, 已经异常的订单)
     * @return
     * @param ofcExceptOrderDTO
     */
    List<String> selectUndealedOrderCodeByType(OfcExceptOrderDTO ofcExceptOrderDTO);

    List<OfcExceptOrder> selectExceptOrderByDTO(OfcExceptOrderDTO exceptOrderDTO);

    List<OfcExceptOrder> selectByOrderCode(@Param(value = "orderCode") String orderCode);

    int updateByOrderCode(OfcExceptOrder ofcExceptOrder);

    List<OfcExceptOrder> selectByOrderCodeList(@Param(value = "orderCodes") List<String> orderCodes);
}