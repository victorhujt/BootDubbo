package com.xescm.ofc.service.impl;

import com.xescm.ofc.mapper.OfcHomeDisplayMapper;
import com.xescm.ofc.model.dto.ofc.DailyOrderDto;
import com.xescm.ofc.model.dto.ofc.HomeDisplayDto;
import com.xescm.ofc.model.dto.ofc.HomeDisplaySeleDto;
import com.xescm.ofc.service.OfcHomeDisplayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * 地址Service实现类
 */
@Service
public class OfcHomeDisplayServiceImpl extends BaseService<HomeDisplayDto> implements OfcHomeDisplayService {

    @Resource
    private OfcHomeDisplayMapper ofcHomeDisplayMapper;

    @Override
    public HomeDisplayDto homeDisplaySele(HomeDisplaySeleDto homeDisplaySeleDto) {
        HomeDisplayDto homeDisplayDto = new HomeDisplayDto();
        homeDisplayDto.setTransportOrderIncompleteNumber(ofcHomeDisplayMapper.queryTransportOrderIncompleteNumber(homeDisplaySeleDto).getTransportOrderIncompleteNumber());
        homeDisplayDto.setStorageOrderIncompleteNumber(ofcHomeDisplayMapper.queryStorageOrderIncompleteNumber(homeDisplaySeleDto).getStorageOrderIncompleteNumber());
        homeDisplayDto.setMonthCompletedOrderNumber(ofcHomeDisplayMapper.queryMonthCompletedOrderNumber(homeDisplaySeleDto).getMonthCompletedOrderNumber());
        homeDisplayDto.setMonthCreateOrderNumber(ofcHomeDisplayMapper.queryMonthCreateOrderNumber(homeDisplaySeleDto).getMonthCreateOrderNumber());
        return homeDisplayDto;
    }

    @Override
    public List<DailyOrderDto> queryDailyOrderNumber(HomeDisplaySeleDto homeDisplaySeleDto) {
        return ofcHomeDisplayMapper.queryDailyOrderNumber(homeDisplaySeleDto);
    }
}
