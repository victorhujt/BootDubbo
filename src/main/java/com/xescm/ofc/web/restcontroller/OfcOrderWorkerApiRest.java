package com.xescm.ofc.web.restcontroller;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.constant.ResultModel;
import com.xescm.ofc.domain.OfcOrderNewstatus;
import com.xescm.ofc.edas.model.dto.worker.OfcTaskInterfaceLogDto;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.OfcTaskInterfaceLogService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: worker接口
 * @author: nothing
 * @date: 2017/5/23 20:08
 */
@RestController
@RequestMapping(value = "/api/ofc/worker", produces = {"application/json;charset=UTF-8"})
public class OfcOrderWorkerApiRest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OfcTaskInterfaceLogService taskInterfaceLogService;

    /**
     * <p>Title:      queryOfcTaskInterfaceLog. </p>
     * <p>Description 查询订单待处理任务</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/5/24 17:53
     * @return
     */
    @RequestMapping(value = "/queryOfcTaskInterfaceLog", method = RequestMethod.POST)
    public Wrapper<List<OfcTaskInterfaceLogDto>> queryOfcTaskInterfaceLog(@RequestBody OfcTaskInterfaceLogDto taskParam) {
        logger.info("查询worker待处理任务：taskParam={}", taskParam);
        Wrapper<List<OfcTaskInterfaceLogDto>> result;
        try {
            List<OfcTaskInterfaceLogDto> list = taskInterfaceLogService.queryWTaskInterfaceLogForWorker(taskParam);
            result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, list);
        } catch (BusinessException e) {
            logger.error("{}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            logger.error("查询worker待处理任务发生异常：{}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, "查询worker待处理任务发生异常");
        }
        return result;
    }

    /**
     * <p>Title:      updateTaskInterfaceLogStatus. </p>
     * <p>Description 更新任务状态</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/5/24 17:53
     * @return
     */
    @RequestMapping(value = "/updateTaskInterfaceLogStatus", method = RequestMethod.POST)
    public Wrapper<Integer> updateTaskInterfaceLogStatus(@RequestBody  OfcTaskInterfaceLogDto taskParam) {
        logger.info("更新任务状态：taskParam={}", taskParam);
        Wrapper<Integer> result;
        try {
            Integer res = taskInterfaceLogService.updateTaskInterfaceLogStatus(taskParam);
            result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, res);
        } catch (BusinessException e) {
            logger.error("{}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            logger.error("更新任务状态发生异常：{}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, "更新任务状态发生异常");
        }
        return result;
    }

    /**
     * <p>Title:      createOrderByTask. </p>
     * <p>Description 根据待处理任务创建订单</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/5/24 19:11
     * @return
     */
    @RequestMapping(value = "/createOrderByTask", method = RequestMethod.POST)
    public Wrapper<Boolean> createOrderByTask(@RequestBody  OfcTaskInterfaceLogDto taskParam) {
        logger.info("根据待处理任务创建订单：taskParam={}", taskParam);
        Wrapper<Boolean> result;
        try {
            ResultModel resultModel = taskInterfaceLogService.createOrderByTask(taskParam);
            if (resultModel != null) {
                if (ResultModel.ResultEnum.CODE_0000.getCode().equals(resultModel.getCode())) {
                    result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, true);
                } else {
                    result = WrapMapper.wrap(Wrapper.ERROR_CODE, resultModel.getDesc(), false);
                }
            } else {
                result = WrapMapper.wrap(Wrapper.ERROR_CODE, "订单中心创建订单接口返回结果为空!", false);
            }
        } catch (BusinessException e) {
            logger.error("根据待处理任务创建订单发生异常：{}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, "根据待处理任务创建订单发生异常: 异常信息=>" + ExceptionUtils.getFullStackTrace(e), false);
        } catch (Exception e) {
            logger.error("根据待处理任务创建订单发生未知异常：{}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, "根据待处理任务创建订单发生未知异常: 异常信息=>" + ExceptionUtils.getFullStackTrace(e), false);
        }
        return result;
    }

    /**
     * <p>Title:      goodsAmountSync. </p>
     * <p>Description 交货量同步</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/6/1 19:46
     * @return
     */
    @RequestMapping(value = "/goodsAmountSync", method = RequestMethod.POST)
    public Wrapper goodsAmountSync(@RequestBody  OfcTaskInterfaceLogDto taskParam) {
        logger.info("交货量同步：taskParam={}", taskParam);
        Wrapper result;
        try {
            result = taskInterfaceLogService.goodsAmountSync(taskParam);
        } catch (BusinessException e) {
            String msg = "执行交货量同步任务发生异常：异常信息=>";
            logger.error(msg + " {}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, msg + ExceptionUtils.getFullStackTrace(e));
        } catch (Exception e) {
            String msg = "执行交货量同步任务发生未知异常：异常信息=>";
            logger.error(msg + " {}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, msg + ExceptionUtils.getFullStackTrace(e));
        }
        return result;
    }

    /**
     * <p>Title:      deleteTaskInterfaceLog. </p>
     * <p>Description 删除任务日志</p>
     * 
     * @param         
     * @Author	      nothing
     * @CreateDate    2017/7/20 18:00
     * @return        
     */
    @RequestMapping(value = "/deleteTaskInterfaceLog", method = RequestMethod.POST)
    public Wrapper deleteTaskInterfaceLog(@RequestBody Long taskId) {
        logger.info("删除任务日志: taskId={}", taskId);
        Wrapper result;
        try {
            Integer line = taskInterfaceLogService.delTaskLogById(taskId);
            if (!PubUtils.isNull(line)) {
                result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
            } else {
                result = WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
            }
        } catch (BusinessException e) {
            logger.error("删除任务日志发生异常：异常详情 => {}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        } catch (Exception e) {
            logger.error("删除任务日志发生未知异常：异常详情 => {}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
        return result;
    }

    /**
     * <p>Title:      queryOrderStatus. </p>
     * <p>Description 查询订单状态</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/7/20 17:54
     * @return
     */
    @RequestMapping(value = "/queryOrderStatus", method = RequestMethod.POST)
    public Wrapper<OfcOrderNewstatus> queryOrderStatus(@RequestBody OfcTaskInterfaceLogDto taskParam) {
        logger.info("查询订单状态：taskParam={}", taskParam);
        Wrapper<OfcOrderNewstatus> result;
        try {
            OfcOrderNewstatus status = taskInterfaceLogService.queryOrderStatus(taskParam);
            if (status != null) {
                result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, status);
            } else {
                result = WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
            }
        } catch (BusinessException e) {
            String msg = "查询订单状态发生异常：异常信息=>";
            logger.error(msg + " {}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, msg + ExceptionUtils.getFullStackTrace(e));
        } catch (Exception e) {
            String msg = "查询订单状态发生未知异常：异常信息=>";
            logger.error(msg + " {}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, msg + ExceptionUtils.getFullStackTrace(e));
        }
        return result;
    }
}
