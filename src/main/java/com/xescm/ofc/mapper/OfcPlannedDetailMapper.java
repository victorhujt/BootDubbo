package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcPlannedDetail;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface OfcPlannedDetailMapper extends Mapper<OfcPlannedDetail> {

    List<OfcPlannedDetail> plannedDetailsScreen(Map<String,String> mapperMap);

    void updateByPlanCode(Object key);
}