package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcStorageTemplate;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface OfcStorageTemplateMapper extends Mapper<OfcStorageTemplate> {

    int checkTemplateNameUnique(@Param(value = "templateName") String templateName);
}