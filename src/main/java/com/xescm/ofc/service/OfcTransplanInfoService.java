package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcTransplanInfo;
import com.xescm.ofc.model.vo.ofc.OfcTransplanInfoVo;

import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcTransplanInfoService extends IService<OfcTransplanInfo> {
     List<OfcTransplanInfo> ofcTransplanInfoScreenList(String orderCode);
    List<OfcTransplanInfoVo> ofcTransplanInfoVoList(String planCode);


}
