package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcOrderNewstatus;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

public interface OfcOrderNewstatusMapper extends Mapper<OfcOrderNewstatus> {
    OfcOrderNewstatus orderStatusSelectNew(Map<String, String> mapperMap);
}