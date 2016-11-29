package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcOrderStatus;

import java.util.List;

/**
 * Created by hiyond on 2016/11/24.
 */
public interface OrderFollowOperService {

    List<OfcFundamentalInformation> queryOrder(String code, String searchType);

    List<OfcOrderStatus> queryOrderStatus(String code, String searchType);

}
