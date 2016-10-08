package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcFundamentalInformationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OfcFundamentalInformationMapper {
    int countByExample(OfcFundamentalInformationExample example);

    int deleteByExample(OfcFundamentalInformationExample example);

    int insert(OfcFundamentalInformation record);

    int insertSelective(OfcFundamentalInformation record);

    List<OfcFundamentalInformation> selectByExample(OfcFundamentalInformationExample example);

    int updateByExampleSelective(@Param("record") OfcFundamentalInformation record, @Param("example") OfcFundamentalInformationExample example);

    int updateByExample(@Param("record") OfcFundamentalInformation record, @Param("example") OfcFundamentalInformationExample example);
}