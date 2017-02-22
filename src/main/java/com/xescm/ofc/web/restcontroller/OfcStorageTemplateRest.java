package com.xescm.ofc.web.restcontroller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OfcMerchandiser;
import com.xescm.ofc.domain.OfcStorageTemplate;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.form.TemplateCondition;
import com.xescm.ofc.service.OfcMerchandiserService;
import com.xescm.ofc.service.OfcStorageTemplateService;
import com.xescm.ofc.web.controller.BaseController;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
    @Resource
    private OfcMerchandiserService ofcMerchandiserService;

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
            logger.error("模板配置筛选入参为空 ==> templateCondition:null");
            return new Wrapper(Wrapper.ERROR_CODE,"模板配置筛选失败!");
        }
        List<OfcStorageTemplate> ofcStorageTemplateList;
        PageInfo<OfcStorageTemplate> pageInfo;
        try {
            new PageHelper().startPage(templateCondition.getPageNum(),templateCondition.getPageSize());
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
     * @param templateCode
     */
    @RequestMapping(value = "/edit/{templateCode}")
    public ModelAndView storageTemplateEdit(@PathVariable String templateCode){
        logger.info("模板配置编辑 ==> templateCode:{}",templateCode);
        if(PubUtils.isSEmptyOrNull(templateCode)){
            logger.error("模板配置编辑错误, 入参为空, templateCode:null or '' ");
            return new ModelAndView("/error/error-500");
        }
        ModelAndView modelAndView = new ModelAndView("/storage/template/template_edit");
        modelAndView.addObject("templateCode",templateCode);
        return modelAndView;
    }

    /**
     * 模板配置编辑确认
     * @param templateList
     */
    @RequestMapping(value = "/edit_confirm")
    @ResponseBody
    public Wrapper storageTemplateEditConfirm(String templateList){
        logger.info("模板配置编辑确认 ==> templateList:{}",templateList);
        if(PubUtils.isSEmptyOrNull(templateList)){
            logger.error("模板配置编辑确认错误, 入参为空, templateList:null or '' ");
            return new Wrapper(Wrapper.ERROR_CODE,"模板配置编辑确认错误");
        }
        AuthResDto authResDto = getAuthResDtoByToken();
        try {
            ofcStorageTemplateService.templateEditConfirm(templateList,authResDto);
        } catch (BusinessException e) {
            logger.error("模板配置编辑确认错误, {}",e);
            return new Wrapper(Wrapper.ERROR_CODE,e.getMessage());
        } catch (Exception e) {
            logger.error("模板配置编辑确认错误, {}",e);
            return new Wrapper(Wrapper.ERROR_CODE,"模板配置编辑确认错误");
        }
        return new Wrapper(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE);
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
            if(PubUtils.isSEmptyOrNull(templateName)){
                logger.error("模板配置删除错误, 入参为空, templateName:null or '' ");
                return new Wrapper(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
            }
            ofcStorageTemplateService.delTemplateByName(templateName);
        } catch (BusinessException e) {
            logger.error("模板配置删除错误, {}",e);
            return new Wrapper(Wrapper.ERROR_CODE,e.getMessage());
        } catch (Exception e) {
            logger.error("模板配置删除错误, {}",e);
            return new Wrapper(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
        }
        return new Wrapper(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE);
    }

    /**
     * 模板配置详情跳转
     * @param templateCode
     */
    @RequestMapping(value = "/detail/{templateCode}")
    public ModelAndView storageTemplateDetail(@PathVariable String templateCode){
        logger.info("模板配置详情跳转 ==> templateCode:{}",templateCode);
        ModelAndView modelAndView = new ModelAndView("/storage/template/template_detail");
        try {
            if(PubUtils.isSEmptyOrNull(templateCode)){
                logger.error("模板配置详情跳转错误, 入参为空, templateCode:null or '' ");
                return new ModelAndView("/error/error-500");
            }
            modelAndView.addObject("templateCode",templateCode);
        } catch (Exception e) {
            logger.error("模板配置详情跳转错误, {}",e);
            return new ModelAndView("/error/error-500");
        }
        return modelAndView;
    }

    /**
     * 模板配置详情数据
     * @param templateCode
     */
    @RequestMapping(value = "/detail_data/{templateCode}")
    @ResponseBody
    public Wrapper storageTemplateDetailData(@PathVariable String templateCode){
        logger.info("模板配置详情数据 ==> templateName:{}",templateCode);
        if(PubUtils.isSEmptyOrNull(templateCode)){
            logger.error("模板配置详情数据错误, 入参为空, templateList:null or '' ");
            return new Wrapper(Wrapper.ERROR_CODE,"模板配置详情数据错误");
        }
        List<OfcStorageTemplate> ofcStorageTemplateList;
        try {
            TemplateCondition templateCondition = new TemplateCondition();
            templateCondition.setTemplateCode(templateCode);
            ofcStorageTemplateList = ofcStorageTemplateService.selectTemplateDetail(templateCondition);
        } catch (BusinessException e) {
            logger.error("模板配置详情数据错误, {}",e);
            return new Wrapper(Wrapper.ERROR_CODE,e.getMessage());
        } catch (Exception e) {
            logger.error("模板配置详情数据错误, {}",e);
            return new Wrapper(Wrapper.ERROR_CODE,"模板配置详情数据错误");
        }
        return new Wrapper(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE,ofcStorageTemplateList);
    }

    /**
     * 加载开单员
     */
    @RequestMapping(value = "merchandiser")
    @ResponseBody
    public List<OfcMerchandiser> loadMerchandiser(){
        List<OfcMerchandiser> merchandiserList = ofcMerchandiserService.selectAll();
        return merchandiserList;
    }


    /**
     * 加载所有仓库
     */
    @RequestMapping(value = "warehouse")
    @ResponseBody
    public Wrapper loadWarehouse(){

//        List<> merchandiserList = ofcMerchandiserService.selectAll();
//        return new Wrapper(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE,merchandiserList);
        return null;
    }

}
