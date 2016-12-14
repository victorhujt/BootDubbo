package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcSiloprogramInfo;
import com.xescm.ofc.model.vo.ofc.OfcSiloprogramInfoVo;

import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcSiloprogramInfoService extends IService<OfcSiloprogramInfo> {
    public List<OfcSiloprogramInfo> ofcSiloprogramInfoScreenList(String orderCode);

    public List<OfcSiloprogramInfoVo> ofcSiloprogramAndResourceInfo(String orderCode,String plannedSingleState);

}

