package com.xescm.ofc.service.impl;

import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OfcInterfaceReceiveLog;
import com.xescm.ofc.domain.OfcTaskInterfaceLog;
import com.xescm.ofc.edas.enums.TaskLogSourceEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcInterfaceReceiveLogMapper;
import com.xescm.ofc.service.OfcInterfaceReceiveLogService;
import com.xescm.ofc.service.OfcTaskInterfaceLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @description: OFC接收日志业务
 * @author: nothing
 * @date: 2017/5/15 17:18
 */
@Service
public class OfcInterfaceReceiveLogServiceImpl extends BaseService<OfcInterfaceReceiveLog> implements OfcInterfaceReceiveLogService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OfcInterfaceReceiveLogMapper receiveLogMapper;
    @Resource
    private OfcTaskInterfaceLogService taskInterfaceLogService;

    @Override
    @Transactional
    public String insertOfcInterfaceReceiveLog(OfcInterfaceReceiveLog receiveLog) {
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            receiveLog.setId(id);
            receiveLogMapper.insertOfcInterfaceReceiveLog(receiveLog);
        } catch (Exception e) {
            logger.error("新增接口接收日志发生异常：{}", e);
            throw new BusinessException("新增接口接收日志发生异常");
        }
        return id;
    }

    @Override
    @Transactional
    public Integer insertOfcInterfaceReceiveLogWithTask(OfcInterfaceReceiveLog receiveLog) {
        Integer result;
        try {
            String pk = this.insertOfcInterfaceReceiveLog(receiveLog);
            if (!PubUtils.isNull(pk)) {
                OfcTaskInterfaceLog taskLog = new OfcTaskInterfaceLog();
                taskLog.setTaskType(receiveLog.getLogBusinessType());
                taskLog.setRefNo(receiveLog.getRefNo());
                taskLog.setTaskSource(TaskLogSourceEnum.RECEIVE_LOG.getCode());
                taskLog.setSourceLogId(pk);
                taskLog.setTaskData(receiveLog.getLogData());
                result = taskInterfaceLogService.insertOfcTaskInterfaceLog(taskLog);
            } else {
                logger.error("新增接口接收日志失败！");
                throw new BusinessException("新增接口接收日志失败");
            }
        } catch (BusinessException e) {
            logger.error("{}", e);
            throw e;
        } catch (Exception e) {
            logger.error("新增接口接收日志、任务日志发生异常：{}", e);
            throw e;
        }
        return result;
    }

}
