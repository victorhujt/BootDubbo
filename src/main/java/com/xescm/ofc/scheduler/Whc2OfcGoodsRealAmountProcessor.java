package com.xescm.ofc.scheduler;

import com.alibaba.edas.schedulerX.ProcessResult;
import com.alibaba.edas.schedulerX.ScxSimpleJobContext;
import com.alibaba.edas.schedulerX.ScxSimpleJobProcessor;
import com.xescm.core.exception.BusinessException;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.ofc.edas.enums.LogBusinessTypeEnum;
import com.xescm.ofc.edas.enums.LogStatusEnum;
import com.xescm.ofc.edas.model.dto.whc.FeedBackOrderDto;
import com.xescm.ofc.edas.model.dto.worker.OfcTaskInterfaceLogDto;
import com.xescm.ofc.service.SchedulerOfcService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * <p>Title: Whc2OfcGoodsRealAmountProcessor. </p>
 * <p>Description 任务，仓储单出入库单实收实出反馈 </p>
 * <p>Company: http://www.hnxianyi.com </p>
 *
 * @Author 袁宝龙
 * @CreateDate 2017/12/6 11:49
 */
public class Whc2OfcGoodsRealAmountProcessor implements ScxSimpleJobProcessor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    // 一次处理数据量10条
    private static final int fetchNum = 10;
    private SchedulerOfcService schedulerOfcServiceImpl;

    @Override
    public ProcessResult process(ScxSimpleJobContext scxSimpleJobContext) {
        logger.info("执行Processor -----> Whc2OfcGoodsRealAmountProcessor");
        OfcTaskInterfaceLogDto ofcTaskInterfaceLogDto = new OfcTaskInterfaceLogDto();
        try {
            // 查询任务日志
            OfcTaskInterfaceLogDto param = new OfcTaskInterfaceLogDto();
            param.setTaskType(LogBusinessTypeEnum.EDI_WHC_GOODS_REAL_AMOUNT.getCode());
            param.setFetchNum(fetchNum);
            List<OfcTaskInterfaceLogDto> result = schedulerOfcServiceImpl.queryWTaskInterfaceLogForWorker(param);
            if (!CollectionUtils.isEmpty(result)) {
                for (OfcTaskInterfaceLogDto taskInterfaceLogDto : result) {
                    ofcTaskInterfaceLogDto = taskInterfaceLogDto;
                    workOnce(ofcTaskInterfaceLogDto);
                }
            }
        } catch (BusinessException e) {
            workException(e, ofcTaskInterfaceLogDto);
        } catch (Exception e) {
            workException(e, ofcTaskInterfaceLogDto);
        }
        return new ProcessResult(true);
    }

    /**
     * <p>Title: workOnce. </p>
     * <p>仓储单出入库单实收实出反馈，业务处理 </p>
     *
     * @param
     * @Author 袁宝龙
     * @CreateDate 2017/12/6 16:23
     * @return
     */
    public void workOnce(OfcTaskInterfaceLogDto ofcTaskInterfaceLogDto) {
        try {
            logger.info("当前处理作业SourceLogId -----> {}", ofcTaskInterfaceLogDto.getSourceLogId());
            String messageBody = ofcTaskInterfaceLogDto.getTaskData();
            FeedBackOrderDto feedBackOrderDto = JacksonUtil.parseJson(messageBody, FeedBackOrderDto.class);
            schedulerOfcServiceImpl.ofcWarehouseFeedBackFromWhc(feedBackOrderDto);
            // 更新任务日志
            Integer taskExeCount = ofcTaskInterfaceLogDto.getTaskExeCount();
            ofcTaskInterfaceLogDto.setTaskExeCount(taskExeCount + 1);
            ofcTaskInterfaceLogDto.setTaskStatus(LogStatusEnum.SUCCESS.getInnerCode());
            ofcTaskInterfaceLogDto.setTaskResult("处理成功，仓储单出入库单实收实出反馈，任务作业日志编号:" + ofcTaskInterfaceLogDto.getSourceLogId());
            schedulerOfcServiceImpl.updateTaskInterfaceLogStatus(ofcTaskInterfaceLogDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>Title: workException. </p>
     * <p>仓储单出入库单实收实出反馈，处理异常 </p>
     *
     * @param
     * @Author 袁宝龙
     * @CreateDate 2017/12/6 16:23
     * @return
     */
    public void workException(Exception e, OfcTaskInterfaceLogDto ofcTaskInterfaceLogDto) {
        logger.error("仓储单出入库单实收实出反馈，发生异常：{}", e);
        ofcTaskInterfaceLogDto.setTaskStatus(LogStatusEnum.FAILURE.getInnerCode());
        ofcTaskInterfaceLogDto.setTaskExeCount(ofcTaskInterfaceLogDto.getTaskExeCount() + 1);
        ofcTaskInterfaceLogDto.setTaskResult("仓储单出入库单实收实出反馈，发生异常：异常信息=>{}" + ExceptionUtils.getFullStackTrace(e));
        schedulerOfcServiceImpl.updateTaskInterfaceLogStatus(ofcTaskInterfaceLogDto);
    }
}
