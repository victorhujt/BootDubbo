package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcInterfaceReceiveLog;

/**
 * @description: OFC接收日志业务
 * @author: nothing
 * @date: 2017/5/15 17:14
 */
public interface OfcInterfaceReceiveLogService extends IService<OfcInterfaceReceiveLog> {

    /**
     * 新增接收日志
     * @param receiveLog 日志
     * @return 主键id
     */
    String insertOfcInterfaceReceiveLog(OfcInterfaceReceiveLog receiveLog);

    /**
     * 新增接收日志 + 任务日志
     * @return 执行行数
     */
    Integer insertOfcInterfaceReceiveLogWithTask(OfcInterfaceReceiveLog receiveLog);
}
