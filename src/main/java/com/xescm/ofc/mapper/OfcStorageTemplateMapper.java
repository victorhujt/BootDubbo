package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcStorageTemplate;
import com.xescm.ofc.model.dto.form.TemplateCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
@Repository
public interface OfcStorageTemplateMapper extends Mapper<OfcStorageTemplate> {

    Integer checkTemplateNameUnique(@Param(value = "templateName") String templateName);

    List<OfcStorageTemplate> selectTemplateByCondition(TemplateCondition templateCondition);

    List<OfcStorageTemplate> selectTemplateDetail(TemplateCondition templateCondition);

    int updateByTemplateCode(OfcStorageTemplate ofcStorageTemplate);

    int deleteByTemplateCode(@Param("templateCode") String temlpateCode);
}