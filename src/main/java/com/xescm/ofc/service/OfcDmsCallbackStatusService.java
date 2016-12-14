package com.xescm.ofc.service;

import com.xescm.ofc.model.dto.dms.DmsTransferStatusDto;

/**
 * Created by lyh on 2016/12/9.
 */
public interface OfcDmsCallbackStatusService {

    void receiveDmsCallbackStatus(DmsTransferStatusDto dmsTransferRecordDto);
}
