package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcInterfaceReceiveLog;
import com.xescm.ofc.edas.model.dto.whc.FeedBackOrderDto;
import com.xescm.ofc.edas.model.dto.worker.OfcTaskInterfaceLogDto;

import java.util.List;

/**
 * <p>Title: SchedulerOfcService. </p>
 * <p>Description 统一OFC，Scheduler </p>
 * <p>关于无法自动注入Service的依赖</p>
 * <p>Company: http://www.hnxianyi.com </p>
 *
 * @Author 袁宝龙
 * @CreateDate 2017/12/6 15:07
 */
public interface SchedulerOfcService {
    /**
     * worker 查询待处理任务
     * @return
     */
    List<OfcTaskInterfaceLogDto> queryWTaskInterfaceLogForWorker(OfcTaskInterfaceLogDto param);

    /**
     * 仓储单出入库单实收实出反馈，Scheduler业务
     * @return
     */
    void ofcWarehouseFeedBackFromWhc(FeedBackOrderDto feedBackOrderDto);

    /**
     * 更新任务最新状态、执行时间、执行次数
     * @return
     */
    Integer updateTaskInterfaceLogStatus(OfcTaskInterfaceLogDto ofcTaskInterfaceLogDto);

    /**
     * 测试用例，无业务意义
     * 更新任务最新状态、执行时间、执行次数
     * @return
     */
    void insertOfcInterfaceReceiveLogWithTask(OfcInterfaceReceiveLog receiveLog);
}
