package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcStorageTemplate;
import com.xescm.ofc.model.dto.form.TemplateCondition;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OfcStorageTemplateMapper extends Mapper<OfcStorageTemplate> {

    Integer checkTemplateNameUnique(@Param(value = "templateName") String templateName);

    List<OfcStorageTemplate> selectTemplateByCondition(TemplateCondition templateCondition);

    List<OfcStorageTemplate> selectTemplateDetail(TemplateCondition templateCondition);
}