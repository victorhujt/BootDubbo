package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.ofc.model.vo.ofc.OfcBatchOrderVo;
import com.xescm.ofc.utils.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OfcDistributionBasicInfoMapper extends MyMapper<OfcDistributionBasicInfo> {
    int deleteByOrderCode(Object key);
    int updateByOrderCode(Object key);
    int checkTransCode(OfcDistributionBasicInfo ofcDistributionBasicInfo);
    OfcDistributionBasicInfo ofcDistributionBasicInfoSelect(Object key);
    List<String> getOrderCodeByTransCode(Map<String,String> mapperMap);
    OfcBatchOrderVo queryByBatchNumber(@Param("orderBatchNumber") String orderBatchNumber);
    List<String> getKabanOrderCodeByTransCode(Map<String, String> mapperMap);
    OfcDistributionBasicInfo queryByOrderCode(@Param("orderCode") String orderCode);
    int updateAddressByOrderCode(Object key);
}