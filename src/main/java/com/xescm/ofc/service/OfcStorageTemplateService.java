package com.xescm.ofc.service;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.ofc.domain.OfcStorageTemplate;

import java.util.List;

/**
 *
 * Created by lyh on 2017/2/18.
 */
public interface OfcStorageTemplateService {
    void saveTemplate(List<OfcStorageTemplate> templateList, AuthResDto authResDto);
}
