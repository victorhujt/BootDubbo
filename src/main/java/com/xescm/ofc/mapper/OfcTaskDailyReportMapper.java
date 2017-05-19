package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcTaskDailyReport;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface OfcTaskDailyReportMapper extends Mapper<OfcTaskDailyReport> {

    /**
     * 查询钉钉待处理任务
     * @param task
     * @return
     */
    List<OfcTaskDailyReport> queryDailyReportTask(@Param("task") OfcTaskDailyReport task);

    /**
     * 更新订单任务状态
     * @param task
     * @return
     */
    Integer updateDailyReportTask(@Param("task") OfcTaskDailyReport task);

}