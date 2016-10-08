package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcFinanceInformation;
import com.xescm.ofc.domain.OfcFinanceInformationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OfcFinanceInformationMapper {
    int countByExample(OfcFinanceInformationExample example);

    int deleteByExample(OfcFinanceInformationExample example);

    int insert(OfcFinanceInformation record);

    int insertSelective(OfcFinanceInformation record);

    List<OfcFinanceInformation> selectByExample(OfcFinanceInformationExample example);

    int updateByExampleSelective(@Param("record") OfcFinanceInformation record, @Param("example") OfcFinanceInformationExample example);

    int updateByExample(@Param("record") OfcFinanceInformation record, @Param("example") OfcFinanceInformationExample example);
}