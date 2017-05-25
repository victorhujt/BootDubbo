package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcInterfaceReceiveLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OfcInterfaceReceiveLogMapper extends Mapper<OfcInterfaceReceiveLog> {

    /**
     * 新增接口接收日志，并返回id
     * @param receiveLog
     */
    void insertOfcInterfaceReceiveLog(OfcInterfaceReceiveLog receiveLog);
}