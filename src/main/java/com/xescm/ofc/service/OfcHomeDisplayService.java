package com.xescm.ofc.service;

import com.xescm.ofc.model.dto.ofc.DailyOrderDto;
import com.xescm.ofc.model.dto.ofc.HomeDisplayDto;
import com.xescm.ofc.model.dto.ofc.HomeDisplaySeleDto;

import java.util.List;

public interface OfcHomeDisplayService extends IService<HomeDisplayDto>{
    HomeDisplayDto homeDisplaySele(HomeDisplaySeleDto homeDisplaySeleDto);
    List<DailyOrderDto> queryDailyOrderNumber(HomeDisplaySeleDto homeDisplaySeleDto);
}
