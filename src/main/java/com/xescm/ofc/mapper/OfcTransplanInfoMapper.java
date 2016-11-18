package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcTransplanInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface OfcTransplanInfoMapper extends Mapper<OfcTransplanInfo> {
    List<OfcTransplanInfo> ofcTransplanInfoScreenList(Map<String,String> mapperMap);
}