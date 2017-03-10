package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcPlannedDetail;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface OfcPlannedDetailMapper extends Mapper<OfcPlannedDetail> {

    List<OfcPlannedDetail> plannedDetailsScreen(Map<String,String> mapperMap);

    void updateByPlanCode(Object key);
}