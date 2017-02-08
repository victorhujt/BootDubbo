package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcTransplanInfo;
import com.xescm.ofc.model.vo.ofc.OfcTransplanInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface OfcTransplanInfoMapper extends Mapper<OfcTransplanInfo> {
    List<OfcTransplanInfo> ofcTransplanInfoScreenList(Map<String,String> mapperMap);
    List<OfcTransplanInfoVo> ofcTransplanInfoVoList(Map<String,String> mapperMap);
    int queryNotInvalidAndNotCompleteTransOrder(@Param(value = "orderCode") String orderCode);
    List<String> queryTransCodeByOrderCode(@Param(value = "orderCode") String orderCode);
    List<String> queryUncompletedPlanCodesByOrderCode(@Param(value = "orderCode")String orderCode);
}