package com.xescm.ofc.service;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.domain.OfcStorageTemplate;
import com.xescm.ofc.model.dto.form.TemplateCondition;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 * Created by lyh on 2017/2/18.
 */
public interface OfcStorageTemplateService {
    void saveTemplate(List<OfcStorageTemplate> templateList, AuthResDto authResDto);

    List<OfcStorageTemplate> selectTemplateByCondition(TemplateCondition templateCondition);

    void  delTemplateByName(String temlpateName);

    void templateEditConfirm(String templateName, AuthResDto authResDto) throws Exception;

    List<OfcStorageTemplate> selectTemplateDetail(TemplateCondition templateCondition);

    Wrapper<?> checkStorageTemplate(MultipartFile file, AuthResDto authResDto,String templateType, String custCode, String templateCode, Integer sheetNum);

    Integer checkStorageTemplate(MultipartFile file);
}
