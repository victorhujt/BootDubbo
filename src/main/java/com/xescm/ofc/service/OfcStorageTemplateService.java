package com.xescm.ofc.service;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.domain.OfcStorageTemplate;
import com.xescm.ofc.model.dto.form.TemplateCondition;
import com.xescm.rmc.edas.domain.vo.RmcWarehouseRespDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 * Created by lyh on 2017/2/18.
 */
public interface OfcStorageTemplateService {
    void saveTemplate(List<OfcStorageTemplate> templateList, AuthResDto authResDto);

    List<OfcStorageTemplate> selectTemplate(TemplateCondition templateCondition);

    List<OfcStorageTemplate> selectTemplateByCondition(TemplateCondition templateCondition);

    void  delTemplateByCode(String temlpateCode);

    void templateEditConfirm(String templateName, AuthResDto authResDto, String lastTemplateType) throws Exception;

    List<OfcStorageTemplate> selectTemplateDetail(TemplateCondition templateCondition);

    Wrapper<?> checkStorageTemplate(MultipartFile file, AuthResDto authResDto,OfcStorageTemplate ofcStorageTemplate, Integer sheetNum);

    Integer checkStorageTemplate(MultipartFile file);

    List<RmcWarehouseRespDto> allWarehouseByRmc();

    Wrapper orderConfirm(String orderList, AuthResDto authResDto) throws Exception;

    Wrapper storageTemplateAudit(Object result, AuthResDto authResDto);

    void checkTemplateListRequired(List<OfcStorageTemplate> ofcStorageTemplates);
}
