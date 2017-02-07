package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.PlanAndStorage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * Created by hiyond on 2016/11/25.
 */
@Component
public interface PlanAndStorageMapper {

    List<PlanAndStorage> queryPlanAndStorage(@Param("orderCode") String orderCode, @Param("transCode") String transCode);

    List<PlanAndStorage> queryPlanAndStorageTrans(@Param("orderCode") String orderCode, @Param("transCode") String transCode);

}
