package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcTaskInterfaceLog;
import com.xescm.ofc.edas.model.dto.worker.OfcTaskInterfaceLogDto;
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
    List<OfcTaskInterfaceLogDto> queryTaskInterfaceLog(OfcTaskInterfaceLogDto taskParam);

}