package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcInterfaceReceiveLog;
import com.xescm.ofc.edas.model.dto.whc.FeedBackOrderDto;
import com.xescm.ofc.edas.model.dto.worker.OfcTaskInterfaceLogDto;
import com.xescm.ofc.service.OfcInterfaceReceiveLogService;
import com.xescm.ofc.service.OfcOrderStatusService;
import com.xescm.ofc.service.OfcTaskInterfaceLogService;
import com.xescm.ofc.service.SchedulerOfcService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("schedulerOfcServiceImpl")
public class SchedulerOfcServiceImpl implements SchedulerOfcService{

    @Resource
    private OfcOrderStatusService ofcOrderStatusService;
    @Resource
    private OfcTaskInterfaceLogService taskInterfaceLogService;
    @Resource
    private OfcInterfaceReceiveLogService receiveLogService;

    @Override
    public List<OfcTaskInterfaceLogDto> queryWTaskInterfaceLogForWorker(OfcTaskInterfaceLogDto param) {
        return taskInterfaceLogService.queryWTaskInterfaceLogForWorker(param);
    }

    @Override
    public void ofcWarehouseFeedBackFromWhc(FeedBackOrderDto feedBackOrderDto) {
        ofcOrderStatusService.ofcWarehouseFeedBackFromWhc(feedBackOrderDto);
    }

    @Override
    public Integer updateTaskInterfaceLogStatus(OfcTaskInterfaceLogDto ofcTaskInterfaceLogDto) {
        return taskInterfaceLogService.updateTaskInterfaceLogStatus(ofcTaskInterfaceLogDto);
    }

    @Override
    public void insertOfcInterfaceReceiveLogWithTask(OfcInterfaceReceiveLog receiveLog) {
        receiveLogService.insertOfcInterfaceReceiveLogWithTask(receiveLog);
    }
}
