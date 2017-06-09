package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcInterfaceReceiveLog;
import com.xescm.ofc.model.vo.ofc.OfcInterfaceReceiveLogVo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface OfcInterfaceReceiveLogMapper extends Mapper<OfcInterfaceReceiveLog> {

    /**
     * 新增接口接收日志，并返回id
     * @param receiveLog
     */
    void insertOfcInterfaceReceiveLog(OfcInterfaceReceiveLog receiveLog);

    /**
     * <p>Title:      queryInterfaceReceiveLog. </p>
     * <p>Description 查询接收日志</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/6/9 11:23
     * @return
     */
    List<OfcInterfaceReceiveLogVo> queryInterfaceReceiveLog(OfcInterfaceReceiveLogVo logParam);

    /**
     * <p>Title:      resendReceiveLogById. </p>
     * <p>Description 重发接收日志</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/6/9 15:36
     * @return
     */
    Integer resendReceiveLogById(Long id);
}