package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.domain.OrderScreenCondition;
import com.xescm.ofc.utils.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OfcGoodsDetailsInfoMapper extends MyMapper<OfcGoodsDetailsInfo> {
    List<OfcGoodsDetailsInfo> goodsDetailsScreen(Map<String,String> mapperMap);
    int deleteByOrderCode(OfcGoodsDetailsInfo ofcGoodsDetailsInfo);

    void deleteAllByOrderCode(@Param(value = "orderCode") String orderCode);

    List<OfcGoodsDetailsInfo> queryByOrderCode(@Param(value = "orderCode") String orderCode);

    int updateByOrderCode(Object key);

    List<OfcGoodsDetailsInfo> queryIds();

    void flushPassLineNoById(OfcGoodsDetailsInfo ofcGoodsDetailsInfo);

    List<String> queryByCondition(OrderScreenCondition orderScreenCondition);

    /**
     * 根据订单批量查询订单货品类型
     * @param codeList 订单号
     * @return
     */
    List<OfcGoodsDetailsInfo> getOrderGoodsTypeByOrderCode(@Param("list") List<String> codeList);
}