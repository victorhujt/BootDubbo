package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcTaskDailyReport;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcTaskDailyReportMapper;
import com.xescm.ofc.service.OfcTaskDailyReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 钉钉日报任务控制类
 * @author: nothing
 * @date: 2017/5/9 17:36
 */
@Service
public class OfcTaskDailyReportServiceImpl implements OfcTaskDailyReportService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OfcTaskDailyReportMapper ofcTaskDailyReportMapper;

    @Override
    public List<OfcTaskDailyReport> queryDailyReportTask(OfcTaskDailyReport task) {
        List<OfcTaskDailyReport> result = null;
        try {
            result = ofcTaskDailyReportMapper.queryDailyReportTask(task);
        } catch (Exception e) {
            logger.error("查询钉钉日报任务发生异常：{}", e);
            throw new BusinessException("查询钉钉日报任务发生异常!");
        }
        return result;
    }

    @Override
    public Integer updateDailyReportTask(OfcTaskDailyReport task) {
        Integer result = 0;
        try {
            result = ofcTaskDailyReportMapper.updateDailyReportTask(task);
        } catch (Exception e) {
            logger.error("更新钉钉日报任务发生异常：{}", e);
            throw new BusinessException("更新钉钉日报任务发生异常!");
        }
        return result;
    }

    @Override
    public Integer insertDailyReportTask(OfcTaskDailyReport task) {
        Integer line = 0;
        try {
            line = ofcTaskDailyReportMapper.insert(task);
        } catch (Exception e) {
            logger.error("新增钉钉日报任务发生异常：{}", e);
            throw new BusinessException("新增钉钉日报任务发生异常");
        }
        return line;
    }
}
