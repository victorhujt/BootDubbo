package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcTaskDailyReport;

import java.util.List;

/**
 * @description: 钉钉日报任务控制类
 * @author: nothing
 * @date: 2017/5/9 17:35
 */
public interface OfcTaskDailyReportService {

    /**
     * 查询钉钉待处理任务
     * @param task
     * @return
     */
    List<OfcTaskDailyReport> queryDailyReportTask(OfcTaskDailyReport task);

    /**
     * 更新订单任务状态
     * @param task
     * @return
     */
    Integer updateDailyReportTask(OfcTaskDailyReport task);

    /**
     * 新增钉钉日报任务
     * @param task
     * @return
     */
    Integer insertDailyReportTask(OfcTaskDailyReport task);
}
