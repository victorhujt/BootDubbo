package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.domain.OfcGoodsDetailsInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OfcGoodsDetailsInfoMapper {
    int countByExample(OfcGoodsDetailsInfoExample example);

    int deleteByExample(OfcGoodsDetailsInfoExample example);

    int deleteByPrimaryKey(String goodsCode);

    int insert(OfcGoodsDetailsInfo record);

    int insertSelective(OfcGoodsDetailsInfo record);

    List<OfcGoodsDetailsInfo> selectByExample(OfcGoodsDetailsInfoExample example);

    OfcGoodsDetailsInfo selectByPrimaryKey(String goodsCode);

    int updateByExampleSelective(@Param("record") OfcGoodsDetailsInfo record, @Param("example") OfcGoodsDetailsInfoExample example);

    int updateByExample(@Param("record") OfcGoodsDetailsInfo record, @Param("example") OfcGoodsDetailsInfoExample example);

    int updateByPrimaryKeySelective(OfcGoodsDetailsInfo record);

    int updateByPrimaryKey(OfcGoodsDetailsInfo record);
}