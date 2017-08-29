package com.xescm.ofc.web.restcontroller;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.exception.BusinessException;
import com.xescm.ofc.domain.OfcTaskDailyReport;
import com.xescm.ofc.service.OfcTaskDailyReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hujintao on 2017/3/28.
 */
@RestController
@RequestMapping(value = "/api/ofc", produces = {"application/json;charset=UTF-8"})
public class OfcOrderManageApiRest {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private OfcTaskDailyReportService ofcTaskDailyReportService;


    /**
     * 查询钉钉日报待处理任务
     * @param task
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryDailyReportTask", method = RequestMethod.POST)
    public Wrapper<List<OfcTaskDailyReport>> queryDailyReportTask(@RequestBody OfcTaskDailyReport task) {
        Wrapper<List<OfcTaskDailyReport>> result = null;
        try {
            List<OfcTaskDailyReport> list = ofcTaskDailyReportService.queryDailyReportTask(task);
            result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, list);
        } catch (BusinessException e) {
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
            logger.error("==>查询钉钉日报任务发生异常.");
        } catch (Exception e) {
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
            logger.error("==>查询钉钉日报任务发生异常：{}", e);
        }
        return result;
    }

    /**
     * 更新钉钉日报任务状态
     * @param task
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateDailyReportTask", method = RequestMethod.POST)
    public Wrapper<Integer> updateDailyReportTask(@RequestBody OfcTaskDailyReport task) {
        Wrapper<Integer> result = null;
        try {
            Integer line = ofcTaskDailyReportService.updateDailyReportTask(task);
            result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, line);
        } catch (BusinessException e) {
            logger.error("==>查询钉钉日报任务发生异常.");
        } catch (Exception e) {
            logger.error("==>查询钉钉日报任务发生异常：{}", e);
        }
        return result;
    }

    /**
     * 新增钉钉日报任务
     * @param task
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insertDailyReportTask", method = RequestMethod.POST)
    public Wrapper<Integer> insertDailyReportTask(@RequestBody OfcTaskDailyReport task) {
        Wrapper<Integer> result = null;
        try {
            Integer line = ofcTaskDailyReportService.insertDailyReportTask(task);
            result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, line);
        } catch (BusinessException e) {
            logger.error("==>新增钉钉日报任务发生异常.");
        } catch (Exception e) {
            logger.error("==>新增钉钉日报任务发生异常：{}", e);
        }
        return result;
    }
}
