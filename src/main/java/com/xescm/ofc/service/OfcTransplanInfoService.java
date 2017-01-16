package com.xescm.ofc.service;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.domain.OfcTransplanInfo;
import com.xescm.ofc.edas.model.dto.dpc.req.TranPlanOfcReqDTO;
import com.xescm.ofc.edas.model.dto.dpc.resp.TranPlanOfcRespDTO;
import com.xescm.ofc.model.vo.ofc.OfcTransplanInfoVo;

import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcTransplanInfoService extends IService<OfcTransplanInfo> {
    List<OfcTransplanInfo> ofcTransplanInfoScreenList(String orderCode);
    List<OfcTransplanInfoVo> ofcTransplanInfoVoList(String planCode);
    int queryNotInvalidAndNotCompleteTransOrder(String orderCode);
    List<String> queryPlanCodesByOrderCode(String orderCode);
    List<String> queryUncompletedPlanCodesByOrderCode(String orderCode);
    Wrapper<List<TranPlanOfcRespDTO>> tranPlanSel(TranPlanOfcReqDTO tranPlanOfcReqDTO);
}
