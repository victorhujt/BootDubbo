package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcPlannedDetail;
import com.xescm.ofc.domain.OfcSiloprogramInfo;
import com.xescm.ofc.domain.OfcTransplanInfo;
import com.xescm.ofc.service.IService;

import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcSiloprogramInfoService extends IService<OfcSiloprogramInfo> {
    public List<OfcSiloprogramInfo> ofcSiloprogramInfoScreenList(String orderCode);
}
