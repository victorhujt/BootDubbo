package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcSiloproStatus;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface OfcSiloproStatusMapper extends Mapper<OfcSiloproStatus> {
    int updateByPlanCode(Object key);

    List<OfcSiloproStatus> queryUncompletedPlanCodesByOrderCode(@Param(value = "orderCode") String orderCode);
}