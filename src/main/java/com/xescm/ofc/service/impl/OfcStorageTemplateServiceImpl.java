package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcStorageTemplate;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcStorageTemplateMapper;
import com.xescm.ofc.service.OfcStorageTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lyh on 2017/2/18.
 */
@Service
public class OfcStorageTemplateServiceImpl implements OfcStorageTemplateService{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OfcStorageTemplateMapper ofcStorageTemplateMapper;


    /**
     * 模板配置保存
     * @param templateList
     */
    @Override
    public void saveTemplate(List<OfcStorageTemplate> templateList) {
        if(null == templateList || templateList.size() < 1){
            logger.error("模板配置保存失败,入参为空");
            throw new BusinessException("模板配置保存失败,入参为空");
        }
        for (OfcStorageTemplate ofcStorageTemplate : templateList) {
            if(null == ofcStorageTemplate){
                logger.error("模板配置保存失败");
                throw new BusinessException("模板配置保存失败");
            }
            ofcStorageTemplateMapper.insert(ofcStorageTemplate);
        }
    }
}
