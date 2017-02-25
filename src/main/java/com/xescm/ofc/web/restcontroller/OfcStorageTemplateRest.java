package com.xescm.ofc.web.restcontroller;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OfcStorageTemplate;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.form.TemplateCondition;
import com.xescm.ofc.service.OfcStorageTemplateService;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.rmc.edas.domain.dto.RmcWarehouseDto;
import com.xescm.rmc.edas.domain.qo.RmcWareHouseQO;
import com.xescm.rmc.edas.domain.vo.RmcWarehouseRespDto;
import com.xescm.rmc.edas.service.RmcWarehouseEdasService;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
    private RmcWarehouseEdasService rmcWarehouseEdasService;

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
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"模板配置保存失败!");
        }
        TypeReference<List<OfcStorageTemplate>> typeReference = new TypeReference<List<OfcStorageTemplate>>() {
        };
        List<OfcStorageTemplate> ofcStorageTemplates;
        try {
            ofcStorageTemplates = JacksonUtil.parseJson(templateList, typeReference);
        } catch (Exception e) {
            logger.error("模板配置保存,json转换异常,{},{}",e,e.getMessage());
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"模板配置保存失败!数据转换异常!");
        }
        try {
            AuthResDto authResDto = getAuthResDtoByToken();
            ofcStorageTemplateService.saveTemplate(ofcStorageTemplates,authResDto);
        }catch (BusinessException e) {
            logger.error("模板配置保存失败!{},{}",e,e.getMessage());
            return WrapMapper.wrap(Wrapper.ERROR_CODE,e.getMessage());
        }catch (Exception e) {
            logger.error("模板配置保存失败!{},{}",e,e.getMessage());
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"模板配置保存失败!未知异常!");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE);
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
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"模板配置筛选失败!");
        }
        List<OfcStorageTemplate> ofcStorageTemplateList;
        PageInfo<OfcStorageTemplate> pageInfo;
        try {
            new PageHelper().startPage(templateCondition.getPageNum(),templateCondition.getPageSize());
            ofcStorageTemplateList = ofcStorageTemplateService.selectTemplateByCondition(templateCondition);
            pageInfo = new PageInfo<>(ofcStorageTemplateList);
        }catch (BusinessException e) {
            logger.error("模板配置筛选失败!{},{}",e,e.getMessage());
            return WrapMapper.wrap(Wrapper.ERROR_CODE,e.getMessage());
        }catch (Exception e) {
            logger.error("模板配置筛选失败!{},{}",e,e.getMessage());
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"模板配置筛选失败!未知异常!");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE,pageInfo);
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
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"模板配置编辑确认错误");
        }
        AuthResDto authResDto = getAuthResDtoByToken();
        try {
            ofcStorageTemplateService.templateEditConfirm(templateList,authResDto);
        } catch (BusinessException e) {
            logger.error("模板配置编辑确认错误, {}",e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,e.getMessage());
        } catch (Exception e) {
            logger.error("模板配置编辑确认错误, {}",e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"模板配置编辑确认错误");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE);
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
                return WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
            }
            ofcStorageTemplateService.delTemplateByName(templateName);
        } catch (BusinessException e) {
            logger.error("模板配置删除错误, {}",e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,e.getMessage());
        } catch (Exception e) {
            logger.error("模板配置删除错误, {}",e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE);
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
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"模板配置详情数据错误");
        }
        List<OfcStorageTemplate> ofcStorageTemplateList;
        try {
            TemplateCondition templateCondition = new TemplateCondition();
            templateCondition.setTemplateCode(templateCode);
            ofcStorageTemplateList = ofcStorageTemplateService.selectTemplateDetail(templateCondition);
        } catch (BusinessException e) {
            logger.error("模板配置详情数据错误, {}",e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,e.getMessage());
        } catch (Exception e) {
            logger.error("模板配置详情数据错误, {}",e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"模板配置详情数据错误");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE,ofcStorageTemplateList);
    }

    /**
     * 加载所有仓库
     */
    @RequestMapping(value = "/warehouse")
    @ResponseBody
    public Wrapper loadWarehouse(){
        Wrapper<List<RmcWarehouseRespDto>> listWrapper = rmcWarehouseEdasService.queryWarehouseList(new RmcWareHouseQO());
        if(listWrapper.getCode() == Wrapper.ERROR_CODE) {
            logger.error("从资源中心加载所有仓库列表失败, 接口返回的错误信息为: {}", listWrapper.getMessage());
            return WrapMapper.wrap(Wrapper.ERROR_CODE, listWrapper.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,listWrapper.getResult());
    }


    /**
     * 跳转入库开单批量导单
     */
    @RequestMapping(value = "batch_in")
    public ModelAndView batchIn(Model model){
        ModelAndView modelAndView = new ModelAndView("/storage/in/batch_import_in");
        setDefaultModel(model);
        return modelAndView;
    }

    /**
     * 根据客户编码查询配置模板列表
     */
    @RequestMapping(value = "/templist")
    @ResponseBody
    public List<OfcStorageTemplate> templateListByCustCode(String custCode){
        TemplateCondition templateCondition = new TemplateCondition();
        templateCondition.setCustCode(custCode);
        List<OfcStorageTemplate> ofcStorageTemplateList = ofcStorageTemplateService.selectTemplateByCondition(templateCondition);
        return ofcStorageTemplateList;
    }


    /**
     * 跳转入库开单批量导单
     */
    @RequestMapping(value = "/batch_in_upload", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Wrapper batchInUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest httpServletRequest, OfcStorageTemplate ofcStorageTemplate){
        Wrapper<?> result = null;
        try {
            AuthResDto authResDto = getAuthResDtoByToken();
            String templateType = "";
            String custCode = "";
            String templateCode = "";
            Integer activeSheetNum = ofcStorageTemplateService.checkStorageTemplate(file);
            Wrapper<?> checkResult = ofcStorageTemplateService.checkStorageTemplate(file,authResDto,templateType,custCode,templateCode,activeSheetNum);
            if(checkResult.getCode() == Wrapper.ERROR_CODE){

            }else if(checkResult.getCode() == Wrapper.SUCCESS_CODE){
                Map<String,JSONArray> resultMap = (Map<String, JSONArray>) checkResult.getResult();
                String resultJSON = JacksonUtil.toJsonWithFormat(resultMap);
                result =  WrapMapper.wrap(Wrapper.SUCCESS_CODE,checkResult.getMessage(),resultJSON);
            }
        } catch (BusinessException e) {
            e.printStackTrace();
            logger.error("仓储开单Excel导入校验出错:{}",e.getMessage(),e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("仓储开单Excel导入校验出错:{}",e.getMessage(),e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
        }
        return result;
    }



    /**
     * 跳转入库开单批量导单
     */
    @RequestMapping(value = "batch_out")
    public ModelAndView batchOut(){
        ModelAndView modelAndView = new ModelAndView("/storage/out/batch_import_out");
        return modelAndView;
    }


}
