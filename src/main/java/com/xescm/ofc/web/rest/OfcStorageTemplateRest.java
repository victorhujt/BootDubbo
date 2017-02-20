package com.xescm.ofc.web.rest;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OfcStorageTemplate;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.form.TemplateCondition;
import com.xescm.ofc.service.OfcStorageTemplateService;
import com.xescm.ofc.web.controller.BaseController;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * Created by lyh on 2017/2/17.
 */
@Controller
@RequestMapping(value = "/ofc/storage_template",produces = {"application/json;charset=UTF-8"})
public class OfcStorageTemplateRest extends BaseController{

    @Resource
    private OfcStorageTemplateService ofcStorageTemplateService;

    /**
     * 模板配置保存
     * @param templateList
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Wrapper storageTemplateSave (String templateList){
        logger.info("模板配置保存 ==> templateList:{}",templateList);
        if(PubUtils.isSEmptyOrNull(templateList)){
            logger.error("模板配置保存入参为空 ==> templateList:{}",templateList);
            return new Wrapper(Wrapper.ERROR_CODE,"模板配置保存失败!");
        }
        TypeReference<List<OfcStorageTemplate>> typeReference = new TypeReference<List<OfcStorageTemplate>>() {
        };
        List<OfcStorageTemplate> ofcStorageTemplates;
        try {
            ofcStorageTemplates = JacksonUtil.parseJson(templateList, typeReference);
        } catch (Exception e) {
            logger.error("模板配置保存,json转换异常,{},{}",e,e.getMessage());
            return new Wrapper(Wrapper.ERROR_CODE,"模板配置保存失败!数据转换异常!");
        }
        try {
            AuthResDto authResDto = getAuthResDtoByToken();
            ofcStorageTemplateService.saveTemplate(ofcStorageTemplates,authResDto);
        }catch (BusinessException e) {
            logger.error("模板配置保存失败!{},{}",e,e.getMessage());
            return new Wrapper(Wrapper.ERROR_CODE,e.getMessage());
        }catch (Exception e) {
            logger.error("模板配置保存失败!{},{}",e,e.getMessage());
            return new Wrapper(Wrapper.ERROR_CODE,"模板配置保存失败!未知异常!");
        }
        return new Wrapper(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE);
    }


    /**
     * 模板配置筛选
     * @param templateCondition
     */
    @RequestMapping(value = "/select")
    @ResponseBody
    public Wrapper storageTemplateSelect (TemplateCondition templateCondition){
        logger.info("模板配置筛选 ==> templateCondition:{}",templateCondition);
        if(null == templateCondition){
            logger.error("模板配置筛选入参为空 ==> templateCondition:{}",templateCondition);
            return new Wrapper(Wrapper.ERROR_CODE,"模板配置筛选失败!");
        }
        List<OfcStorageTemplate> ofcStorageTemplateList;
        PageInfo<OfcStorageTemplate> pageInfo;
        try {
            PageHelper pageHelper = new PageHelper();
            pageHelper.startPage(templateCondition.getPageNum(),templateCondition.getPageSize());
            ofcStorageTemplateList = ofcStorageTemplateService.selectTemplateByCondition(templateCondition);
            pageInfo = new PageInfo<>(ofcStorageTemplateList);
        }catch (BusinessException e) {
            logger.error("模板配置筛选失败!{},{}",e,e.getMessage());
            return new Wrapper(Wrapper.ERROR_CODE,e.getMessage());
        }catch (Exception e) {
            logger.error("模板配置筛选失败!{},{}",e,e.getMessage());
            return new Wrapper(Wrapper.ERROR_CODE,"模板配置筛选失败!未知异常!");
        }
        return new Wrapper(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE,pageInfo);
    }


    /**
     * 模板配置编辑
     * @param templateName
     */
    public void storageTemplateEdit(String templateName){
        logger.info("模板配置编辑 ==> templateName:{}",templateName);
//        ofcStorageTemplateService.
    }

    /**
     * 模板配置删除
     * @param templateName
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Wrapper storageTemplateDel(String templateName){
        logger.info("模板配置删除 ==> templateName:{}",templateName);
        try {
            AuthResDto authResDto = getAuthResDtoByToken();
            ofcStorageTemplateService.delTemplateByName(templateName,authResDto);

        } catch (BusinessException e) {
            logger.error("模板配置删除错误, {}",e);
            return new Wrapper(Wrapper.ERROR_CODE,e.getMessage());
        } catch (Exception e) {
            logger.error("模板配置删除错误, {}",e);
            return new Wrapper(Wrapper.ERROR_CODE,"模板配置删除错误");
        }
        return new Wrapper(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE);
    }

    /**
     * 模板配置详情
     * @param templateName
     */
    public void storageTemplateDetail(String templateName){
        logger.info("模板配置编辑 ==> templateName:{}",templateName);
//        ofcStorageTemplateService.
    }

}
