package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcWarehouseInformation;
import com.xescm.ofc.domain.OfcWarehouseInformationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OfcWarehouseInformationMapper {
    int countByExample(OfcWarehouseInformationExample example);

    int deleteByExample(OfcWarehouseInformationExample example);

    int insert(OfcWarehouseInformation record);

    int insertSelective(OfcWarehouseInformation record);

    List<OfcWarehouseInformation> selectByExample(OfcWarehouseInformationExample example);

    int updateByExampleSelective(@Param("record") OfcWarehouseInformation record, @Param("example") OfcWarehouseInformationExample example);

    int updateByExample(@Param("record") OfcWarehouseInformation record, @Param("example") OfcWarehouseInformationExample example);
}