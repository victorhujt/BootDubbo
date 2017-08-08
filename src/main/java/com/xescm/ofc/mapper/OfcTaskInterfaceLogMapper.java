package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcTaskInterfaceLog;
import com.xescm.ofc.edas.model.dto.worker.OfcTaskInterfaceLogDto;
import com.xescm.ofc.model.vo.ofc.OfcTaskInterfaceLogVo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface OfcTaskInterfaceLogMapper extends Mapper<OfcTaskInterfaceLog> {

    /**
     * worker 查询待处理任务
     * @param taskParam 参数
     * @return
     */
    List<OfcTaskInterfaceLogDto> queryWTaskInterfaceLogForWorker(OfcTaskInterfaceLogDto taskParam);

    /**
     * worker 查询两天内失败任务
     * @param taskParam
     * @return
     */
    List<OfcTaskInterfaceLogDto> queryFailTaskInTwoDays(OfcTaskInterfaceLogDto taskParam);

    /**
     * worker 查询超三十天失败任务
     * @param taskParam
     * @return
     */
    List<OfcTaskInterfaceLogDto> queryFailTaskOverThirtyDays(OfcTaskInterfaceLogDto taskParam);

    /**
     * <p>Title:      queryTaskInterfaceLog. </p>
     * <p>Description 查询任务日志</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/6/5 15:44
     * @return
     */
    List<OfcTaskInterfaceLogVo> queryTaskInterfaceLog(OfcTaskInterfaceLogVo taskParam);

    /**
     * <p>Title:      delTaskLogById. </p>
     * <p>Description 删除任务日志</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/6/8 16:31
     * @return
     */
    Integer delTaskLogById(Long id);

    /**
     * <p>Title:      resendTaskLogById. </p>
     * <p>Description 重发任务日志</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/6/8 16:39
     * @return
     */
    Integer resendTaskLogById(Long id);
}