package com.xescm.ofc.service;

import com.xescm.ofc.constant.ResultModel;
import com.xescm.ofc.domain.OfcTaskInterfaceLog;
import com.xescm.ofc.edas.model.dto.worker.OfcTaskInterfaceLogDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description: 任务管理
 * @author: nothing
 * @date: 2017/5/22 14:25
 */
public interface OfcTaskInterfaceLogService extends IService<OfcTaskInterfaceLog> {

    /**
     * 新增任务日志
     * @param taskInterfaceLog 任务实体
     * @return
     */
    @Transactional
    Integer insertOfcTaskInterfaceLog(OfcTaskInterfaceLog taskInterfaceLog);

    /**
     * worker 查询待处理任务
     * @param taskParam 参数
     * @return
     */
    List<OfcTaskInterfaceLogDto> queryTaskInterfaceLog(OfcTaskInterfaceLogDto taskParam);

    /**
     * 更新任务状态
     * @param taskParam
     * @return
     */
    @Transactional
    Integer updateTaskInterfaceLogStatus(OfcTaskInterfaceLogDto taskParam);

   /**
    * <p>Title:      createOrderByTask. </p>
    * <p>Description 根据待处理任务创建订单</p>
    *
    * @param
    * @Author	      nothing
    * @CreateDate    2017/5/24 18:31
    * @return
    */
    @Transactional
    ResultModel createOrderByTask(OfcTaskInterfaceLogDto taskParam) throws Exception;
}
