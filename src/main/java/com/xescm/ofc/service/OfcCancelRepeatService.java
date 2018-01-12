package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcCancelRepeat;

/**
 * Created by victor on 2018/1/10.
 */
public interface OfcCancelRepeatService extends IService<OfcCancelRepeat> {

    boolean sendTotfc(String orderCode);
}
