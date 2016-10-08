package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.domain.OfcOrderStatusExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OfcOrderStatusMapper {
    int countByExample(OfcOrderStatusExample example);

    int deleteByExample(OfcOrderStatusExample example);

    int insert(OfcOrderStatus record);

    int insertSelective(OfcOrderStatus record);

    List<OfcOrderStatus> selectByExample(OfcOrderStatusExample example);

    int updateByExampleSelective(@Param("record") OfcOrderStatus record, @Param("example") OfcOrderStatusExample example);

    int updateByExample(@Param("record") OfcOrderStatus record, @Param("example") OfcOrderStatusExample example);
}