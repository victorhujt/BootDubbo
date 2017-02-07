package com.xescm.ofc.mapper;

import com.xescm.ofc.model.vo.ofc.OfcBatchOrderVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * Created by hiyond on 2016/11/25.
 */
@Component
public interface OfcBatchOrderVoMapper {


    OfcBatchOrderVo queryByBatchNumber(@Param("orderBatchNumber") String orderBatchNumber);

    List<OfcBatchOrderVo> queryOrderByOrderBatchNumer(@Param("orderBatchNumber") String orderBatchNumer);

}
