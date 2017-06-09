package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcInterfaceReceiveLog;
import com.xescm.ofc.model.vo.ofc.OfcInterfaceReceiveLogVo;

import java.util.List;

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

    /**
     * <p>Title:      queryInterfaceReceiveLog. </p>
     * <p>Description 查询接收日志</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/6/9 11:09
     * @return
     */
    List<OfcInterfaceReceiveLogVo> queryInterfaceReceiveLog(OfcInterfaceReceiveLogVo logParam);
}
