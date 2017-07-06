package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcRuntimeProperty;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface OfcRuntimePropertyMapper extends Mapper<OfcRuntimeProperty> {

    OfcRuntimeProperty findByName(@Param("name") String name);
}