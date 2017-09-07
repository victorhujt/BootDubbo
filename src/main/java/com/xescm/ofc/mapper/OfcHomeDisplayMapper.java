package com.xescm.ofc.mapper;

import com.xescm.ofc.model.dto.ofc.DailyOrderDto;
import com.xescm.ofc.model.dto.ofc.HomeDisplayDto;
import com.xescm.ofc.model.dto.ofc.HomeDisplaySeleDto;
import com.xescm.ofc.utils.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfcHomeDisplayMapper extends MyMapper<HomeDisplayDto> {
    HomeDisplayDto queryTransportOrderIncompleteNumber(HomeDisplaySeleDto homeDisplaySeleDto);

    HomeDisplayDto queryStorageOrderIncompleteNumber(HomeDisplaySeleDto homeDisplaySeleDto);

    HomeDisplayDto queryMonthCreateOrderNumber(HomeDisplaySeleDto homeDisplaySeleDto);

    HomeDisplayDto queryMonthCompletedOrderNumber(HomeDisplaySeleDto homeDisplaySeleDto);

    List<DailyOrderDto> queryDailyOrderNumber(HomeDisplaySeleDto homeDisplaySeleDto);
}