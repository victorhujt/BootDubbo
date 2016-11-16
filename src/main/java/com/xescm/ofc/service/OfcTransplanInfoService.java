package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcTransplanInfo;

import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcTransplanInfoService extends IService<OfcTransplanInfo> {
    public List<OfcTransplanInfo> ofcTransplanInfoScreenList(String orderCode);
}
