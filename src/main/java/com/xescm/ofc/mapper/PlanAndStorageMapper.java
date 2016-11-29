package com.xescm.ofc.mapper;

import com.xescm.ofc.model.dto.vo.PlanAndStorageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hiyond on 2016/11/25.
 */
public interface PlanAndStorageMapper {

    List<PlanAndStorageVo> queryPlanAndStorage(@Param("orderCode") String orderCode, @Param("transCode") String transCode);

    List<PlanAndStorageVo> queryPlanAndStorageTrans(@Param("orderCode") String orderCode, @Param("transCode") String transCode);

}
