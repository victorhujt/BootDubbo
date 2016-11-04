package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcTransplanStatus;
import com.xescm.ofc.domain.OfcTraplanSourceStatus;
import com.xescm.ofc.service.OfcTransplanStatusService;
import com.xescm.ofc.service.OfcTraplanSourceStatusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcTraplanSourceStatusServiceImpl extends BaseService<OfcTraplanSourceStatus> implements OfcTraplanSourceStatusService {
}
