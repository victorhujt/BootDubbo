package com.xescm.ofc.scheduler;

import com.alibaba.edas.schedulerX.ProcessResult;
import com.alibaba.edas.schedulerX.ScxSimpleJobContext;
import com.alibaba.edas.schedulerX.ScxSimpleJobProcessor;
import com.xescm.ofc.edas.enums.LogBusinessTypeEnum;
import com.xescm.ofc.edas.enums.LogStatusEnum;
import com.xescm.ofc.edas.model.dto.worker.OfcTaskInterfaceLogDto;
import com.xescm.ofc.mq.producer.DefaultMqProducer;
import com.xescm.ofc.service.SchedulerOfcService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by hujintao on 2018/1/15.
 */
public class CancelOrderPushTfcProcessor  implements ScxSimpleJobProcessor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final int fetchNum = 10;

    private SchedulerOfcService schedulerOfcServiceImpl;


    private DefaultMqProducer defaultMqProducer;

    @Override
    public ProcessResult process(ScxSimpleJobContext scxSimpleJobContext) {
        logger.info("仓配订单取消状态不一致的问题订单推送到TFC ========> CancelOrderPushTfcProcessor");
        // 查询任务日志
        OfcTaskInterfaceLogDto param = new OfcTaskInterfaceLogDto();
        param.setTaskType(LogBusinessTypeEnum.ORDER_STATE_INCONFORMITY.getCode());
        param.setFetchNum(fetchNum);
        List<OfcTaskInterfaceLogDto> result = schedulerOfcServiceImpl.queryWTaskInterfaceLogForWorker(param);
        if (!CollectionUtils.isEmpty(result)) {
            for (OfcTaskInterfaceLogDto taskInterfaceLogDto : result) {
                try {
                    workOnce(taskInterfaceLogDto);
                } catch (Exception e) {
                    workException(e, taskInterfaceLogDto);
                }
            }
        }
        return new ProcessResult(true);
    }

    public void workOnce(OfcTaskInterfaceLogDto ofcTaskInterfaceLogDto) {
        try {
            logger.info("当前处理作业SourceLogId -----> {}", ofcTaskInterfaceLogDto.getSourceLogId());
            String messageBody = ofcTaskInterfaceLogDto.getTaskData();
            defaultMqProducer.toSendTfcTransPlanMQ(messageBody,"cancel_repeat", ofcTaskInterfaceLogDto.getRefNo());
            // 更新任务日志
            Integer taskExeCount = ofcTaskInterfaceLogDto.getTaskExeCount();
            ofcTaskInterfaceLogDto.setTaskExeCount(taskExeCount + 1);
            ofcTaskInterfaceLogDto.setTaskStatus(LogStatusEnum.SUCCESS.getInnerCode());
            ofcTaskInterfaceLogDto.setTaskResult("处理成功，仓配订单取消状态不一致的问题订单推送到TFC，任务作业日志编号:" + ofcTaskInterfaceLogDto.getSourceLogId());
            schedulerOfcServiceImpl.updateTaskInterfaceLogStatus(ofcTaskInterfaceLogDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void workException(Exception e, OfcTaskInterfaceLogDto ofcTaskInterfaceLogDto) {
        logger.error("仓配订单取消状态不一致的问题订单推送到TFC，发生异常：{}", e);
        ofcTaskInterfaceLogDto.setTaskStatus(LogStatusEnum.FAILURE.getInnerCode());
        ofcTaskInterfaceLogDto.setTaskExeCount(ofcTaskInterfaceLogDto.getTaskExeCount() + 1);
        ofcTaskInterfaceLogDto.setTaskResult("仓配订单取消状态不一致的问题订单推送到TFC，发生异常：异常信息=>{}" + ExceptionUtils.getFullStackTrace(e));
        schedulerOfcServiceImpl.updateTaskInterfaceLogStatus(ofcTaskInterfaceLogDto);
    }
}
