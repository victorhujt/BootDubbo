package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.mapper.OfcFundamentalInformationMapper;
import com.xescm.ofc.mapper.OfcOrderStatusMapper;
import com.xescm.ofc.service.OrderFollowOperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * Created by hiyond on 2016/11/24.
 */
@Service
public class OrderFollowOperServiceImpl implements OrderFollowOperService {

    @Resource
    private OfcFundamentalInformationMapper ofcFundamentalInformationMapper;

    @Resource
    private OfcOrderStatusMapper ofcOrderStatusMapper;

    @Override
    public List<OfcFundamentalInformation> queryOrder(String code, String searchType) {
        return ofcFundamentalInformationMapper.queryOrder(code, searchType);
    }

    @Override
    public List<OfcOrderStatus> queryOrderStatus(String code, String searchType) {
        return ofcOrderStatusMapper.queryOrderStatus(code, searchType);
    }
}
