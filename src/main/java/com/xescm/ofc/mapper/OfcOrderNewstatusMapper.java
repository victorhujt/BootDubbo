package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcOrderNewstatus;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;
@Repository
public interface OfcOrderNewstatusMapper extends Mapper<OfcOrderNewstatus> {
    OfcOrderNewstatus orderStatusSelectNew(Map<String, String> mapperMap);
}