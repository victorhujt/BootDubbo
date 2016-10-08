package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.ofc.domain.OfcDistributionBasicInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OfcDistributionBasicInfoMapper {
    int countByExample(OfcDistributionBasicInfoExample example);

    int deleteByExample(OfcDistributionBasicInfoExample example);

    int deleteByPrimaryKey(String transCode);

    int insert(OfcDistributionBasicInfo record);

    int insertSelective(OfcDistributionBasicInfo record);

    List<OfcDistributionBasicInfo> selectByExample(OfcDistributionBasicInfoExample example);

    OfcDistributionBasicInfo selectByPrimaryKey(String transCode);

    int updateByExampleSelective(@Param("record") OfcDistributionBasicInfo record, @Param("example") OfcDistributionBasicInfoExample example);

    int updateByExample(@Param("record") OfcDistributionBasicInfo record, @Param("example") OfcDistributionBasicInfoExample example);

    int updateByPrimaryKeySelective(OfcDistributionBasicInfo record);

    int updateByPrimaryKey(OfcDistributionBasicInfo record);
}