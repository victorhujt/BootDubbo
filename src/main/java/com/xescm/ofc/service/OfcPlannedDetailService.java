package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcPlannedDetail;

import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcPlannedDetailService extends IService<OfcPlannedDetail> {
     List<OfcPlannedDetail> planDetailsScreenList(String code, String followTag);
}
