package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.utils.MyMapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface OfcGoodsDetailsInfoMapper extends MyMapper<OfcGoodsDetailsInfo> {
    List<OfcGoodsDetailsInfo> goodsDetailsScreen(Map<String,String> mapperMap);
    int deleteByOrderCode(OfcGoodsDetailsInfo ofcGoodsDetailsInfo);

    void deleteAllByOrderCode(@Param(value = "orderCode") String orderCode);
}