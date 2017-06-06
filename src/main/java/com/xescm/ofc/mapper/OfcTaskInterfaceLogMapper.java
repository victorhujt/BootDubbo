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
     * <p>Title:      queryTaskInterfaceLog. </p>
     * <p>Description 查询任务日志</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/6/5 15:44
     * @return
     */
    List<OfcTaskInterfaceLog> queryTaskInterfaceLog(OfcTaskInterfaceLogVo taskParam);
}