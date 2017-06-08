package com.xescm.ofc.service.impl;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.ofc.constant.ResultModel;
import com.xescm.ofc.domain.OfcInterfaceReceiveLog;
import com.xescm.ofc.domain.OfcTaskInterfaceLog;
import com.xescm.ofc.edas.enums.LogBusinessTypeEnum;
import com.xescm.ofc.edas.enums.LogStatusEnum;
import com.xescm.ofc.edas.enums.TaskLogSourceEnum;
import com.xescm.ofc.edas.model.dto.worker.OfcTaskInterfaceLogDto;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcInterfaceReceiveLogMapper;
import com.xescm.ofc.mapper.OfcTaskInterfaceLogMapper;
import com.xescm.ofc.model.vo.ofc.OfcTaskInterfaceLogVo;
import com.xescm.ofc.service.CreateOrderService;
import com.xescm.ofc.service.GoodsAmountSyncService;
import com.xescm.ofc.service.OfcTaskInterfaceLogService;
import com.xescm.tfc.edas.model.dto.ofc.req.GoodsAmountSyncDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @description: 任务管理
 * @author: nothing
 * @date: 2017/5/22 14:28
 */
@Service
public class OfcTaskInterfaceLogServiceImpl extends BaseService<OfcTaskInterfaceLog> implements OfcTaskInterfaceLogService {

    @Resource
    private OfcTaskInterfaceLogMapper taskInterfaceLogMapper;
    @Resource
    private OfcInterfaceReceiveLogMapper receiveLogMapper;
    @Resource
    private CreateOrderService createOrderService;
    @Resource
    private GoodsAmountSyncService goodsAmountSyncService;

    /**
     * <p>Title:      insertOfcTaskInterfaceLog. </p>
     * <p>Description 新增订单待处理任务</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/5/24 18:00
     * @return
     */
    @Override
    @Transactional
    public Integer insertOfcTaskInterfaceLog(OfcTaskInterfaceLog taskInterfaceLog) {
        Integer result;
        try {
            result = taskInterfaceLogMapper.insert(taskInterfaceLog);
        } catch (Exception e) {
            logger.error("新增任务日志发生异常：{}", e);
            throw new BusinessException("新增任务日志发生异常");
        }
        return result;
    }

    /**
     * <p>Title:      queryTaskInterfaceLog. </p>
     * <p>Description 查询待处理任务</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/5/24 17:59
     * @return
     */
    @Override
    public List<OfcTaskInterfaceLogDto> queryWTaskInterfaceLogForWorker(OfcTaskInterfaceLogDto taskParam) {
        List<OfcTaskInterfaceLogDto> result;
        try {
            result = taskInterfaceLogMapper.queryWTaskInterfaceLogForWorker(taskParam);
        } catch (Exception e) {
            logger.error("查询worker待处理任务发生异常：参数 -> OfcTaskInterfaceLogDto {}, 异常 -> {}", taskParam, e);
            throw new BusinessException("查询worker待处理任务发生异常");
        }
        return result;
    }

    /**
     * <p>Title:      updateTaskInterfaceLogStatus. </p>
     * <p>Description 更新任务状态</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/5/24 17:59
     * @return
     */
    @Override
    @Transactional
    public Integer updateTaskInterfaceLogStatus(OfcTaskInterfaceLogDto taskParam) {
        Integer result = 0;
        try {
            // 更新任务表状态
            OfcTaskInterfaceLog taskLog = new OfcTaskInterfaceLog();
            taskLog.setId(taskParam.getId());
            taskLog.setTaskExeCount(taskParam.getTaskExeCount());
            taskLog.setTaskStatus(taskParam.getTaskStatus());
            taskLog.setUpdateTime(new Date());
            taskLog.setCreationTime(taskParam.getCreationTime());
            taskLog.setExeTime(new Date());
            int line = taskInterfaceLogMapper.updateByPrimaryKeySelective(taskLog);
            if (line > 0) {
                if (TaskLogSourceEnum.RECEIVE_LOG.getCode().equals(taskParam.getTaskSource())) {
                    OfcInterfaceReceiveLog receiveLog = new OfcInterfaceReceiveLog();
                    receiveLog.setId(taskParam.getSourceLogId());
                    receiveLog.setProcessCount(taskParam.getTaskExeCount());
                    receiveLog.setLogStatus(taskParam.getTaskStatus());
                    receiveLog.setProcessResult(taskParam.getTaskResult());
                    receiveLog.setProcessTime(new Date());
                    receiveLog.setCreationTime(taskParam.getCreationTime());
                    result = receiveLogMapper.updateByPrimaryKeySelective(receiveLog);
                }
            }
        } catch (BusinessException e) {
            logger.error("更新任务状态发生异常：{}", e);
            throw e;
        } catch (Exception e) {
            logger.error("更新任务状态发生未知异常：{}", e);
            throw e;
        }
        return result;
    }

    /**
     * <p>Title:      createOrderByTask. </p>
     * <p>Description 根据待处理任务创建订单</p>
     * 
     * @param         
     * @Author	      nothing
     * @CreateDate    2017/5/24 18:30
     * @return        
     */
    @Override
    @Transactional
    public ResultModel createOrderByTask(OfcTaskInterfaceLogDto taskParam) throws Exception {
        ResultModel resultModel;
        try {
            String orderData = taskParam.getTaskData();
            resultModel = createOrderService.createOrderByTask(orderData);
        } catch (BusinessException e) {
            logger.error("根据待处理任务创建订单发生异常：{}", e);
            throw e;
        } catch (Exception e) {
            logger.error("根据待处理任务创建订单发生未知异常：{}", e);
            throw e;
        }
        return resultModel;
    }

    /**
     * <p>Title:      goodsAmountSync. </p>
     * <p>Description 交货量同步</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/6/1 19:55
     * @return
     */
    @Override
    @Transactional
    public Wrapper goodsAmountSync(OfcTaskInterfaceLogDto taskParam) throws Exception {
        Wrapper result;
        try {
            String taskData = taskParam.getTaskData();
            if (taskData != null) {
                GoodsAmountSyncDto goodsAmountSyncDto = JacksonUtil.parseJson(taskData, GoodsAmountSyncDto.class);
                result = goodsAmountSyncService.goodsAmountSync(goodsAmountSyncDto);
            } else {
                throw new BusinessException("交货量同步任务数据为空！");
            }
        } catch (BusinessException e) {
            logger.error("执行交货量同步任务发生异常: 异常信息=>", e);
            throw e;
        } catch (Exception e) {
            logger.error("执行交货量同步任务发生未知异常: 异常信息=>", e);
            throw e;
        }
        return result;
    }

    /**
     * <p>Title:      queryTaskInterfaceLog. </p>
     * <p>Description 查询任务日志</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/6/5 15:38
     * @return
     */
    @Override
    public List<OfcTaskInterfaceLogVo> queryTaskInterfaceLog(OfcTaskInterfaceLogVo taskParam) {
        List<OfcTaskInterfaceLogVo> result;
        try {
            result = taskInterfaceLogMapper.queryTaskInterfaceLog(taskParam);
            // 替换枚举
            for (OfcTaskInterfaceLogVo taskLog : result) {
                String taskType = taskLog.getTaskType();
                String taskSource = taskLog.getTaskSource();
                int status = 0;
                try {
                    String taskStatus = taskLog.getTaskStatus();
                    status = taskStatus != null ? Integer.parseInt(taskStatus) : null;
                } catch (Exception e) {
                    logger.error("转换任务日志状态发生异常：异常信息=>{}", e);
                    throw e;
                }
                taskLog.setTaskType(LogBusinessTypeEnum.getLogBusinessTypeByCode(taskType));
                taskLog.setTaskSource(TaskLogSourceEnum.getTaskLogSourceNameByCode(taskSource));
                taskLog.setTaskStatus(LogStatusEnum.getLogStatusNameByInnerCode(status));
            }
        } catch (BusinessException e) {
            logger.error("查询任务日志发生异常：异常信息=>{}", e);
            throw e;
        } catch (Exception e) {
            logger.error("查询任务日志发生未知异常：异常信息=>{}", e);
            throw e;
        }
        return result;
    }

    @Override
    public Integer delTaskLogById(Long id) {
        Integer result;
        try {
            result = taskInterfaceLogMapper.delTaskLogById(id);
        } catch (Exception e) {
            logger.error("删除任务日志发生异常：异常信息=>{}", e);
            throw e;
        }
        return result;
    }

    @Override
    public Integer resendTaskLogById(Long id) {
        Integer result;
        try {
            result = taskInterfaceLogMapper.resendTaskLogById(id);
        } catch (Exception e) {
            logger.error("重发任务日志发生异常：异常信息=>{}", e);
            throw e;
        }
        return result;
    }
}
