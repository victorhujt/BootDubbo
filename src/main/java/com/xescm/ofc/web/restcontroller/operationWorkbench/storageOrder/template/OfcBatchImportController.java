package com.xescm.ofc.web.restcontroller.operationWorkbench.storageOrder.template;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OfcStorageTemplate;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.form.TemplateCondition;
import com.xescm.ofc.model.dto.ofc.OfcStorageTemplateDto;
import com.xescm.ofc.model.dto.ofc.OfcStorageTemplateEditDTO;
import com.xescm.ofc.service.OfcStorageTemplateService;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.rmc.edas.domain.vo.RmcWarehouseRespDto;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

import static com.xescm.ofc.constant.StorageTemplateConstant.ERROR_CUST;
import static com.xescm.ofc.constant.StorageTemplateConstant.ERROR_TEMPLATE;

/**
 *
 * Created by lyh on 2017/7/20.
 */
@Controller
@RequestMapping(value = "/page/ofc/storage/import", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "OfcBatchImportController", tags = "OfcBatchImportController", description = "仓储批量导入", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OfcBatchImportController extends BaseController{
    @Resource
    private OfcStorageTemplateService ofcStorageTemplateService;

    /**
     * 模板配置保存
     * @param ofcStorageTemplates 模板列表
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Wrapper storageTemplateSave (@RequestBody List<OfcStorageTemplate> ofcStorageTemplates) {
        logger.info("模板配置保存 ==> ofcStorageTemplates:{}", ofcStorageTemplates);
        if (CollectionUtils.isEmpty(ofcStorageTemplates)) {
            logger.error("模板配置保存入参为空 ==> ofcStorageTemplates:{}", ofcStorageTemplates);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"模板配置保存失败!");
        }
        try {
            ofcStorageTemplateService.checkTemplateListRequired(ofcStorageTemplates);
            AuthResDto authResDto = getAuthResDtoByToken();
            ofcStorageTemplateService.saveTemplate(ofcStorageTemplates, authResDto);
        } catch (BusinessException e) {
            logger.error("模板配置保存失败!{},{}", e, e.getMessage());
            return WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            logger.error("模板配置保存失败!{},{}", e, e.getMessage());
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"模板配置保存失败!未知异常!");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE);
    }


    /**
     * 模板配置筛选
     * @param templateCondition 筛选条件
     */
    @RequestMapping(value = "/select")
    @ResponseBody
    public Wrapper storageTemplateSelect (@RequestBody TemplateCondition templateCondition) {
        logger.info("模板配置筛选 ==> templateCondition:{}",templateCondition);
        if (null == templateCondition) {
            logger.error("模板配置筛选入参为空 ==> templateCondition:null");
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"模板配置筛选失败!");
        }
        List<OfcStorageTemplate> ofcStorageTemplateList;
        PageInfo<OfcStorageTemplate> pageInfo;
        try {
            new PageHelper().startPage(templateCondition.getPageNum(),templateCondition.getPageSize());
            ofcStorageTemplateList = ofcStorageTemplateService.selectTemplateByCondition(templateCondition);
            pageInfo = new PageInfo<>(ofcStorageTemplateList);
        } catch (BusinessException e) {
            logger.error("模板配置筛选失败!{},{}", e, e.getMessage());
            return WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            logger.error("模板配置筛选失败!{},{}", e, e.getMessage());
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"模板配置筛选失败!未知异常!");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE,pageInfo);
    }


    /**
     * 模板配置编辑
     * @param templateCode 模板编码
     */
    @RequestMapping(value = "/edit/{templateCode}")
    public String storageTemplateEdit(@PathVariable String templateCode, Model model) {
        logger.info("模板配置编辑 ==> templateCode:{}",templateCode);
        if (PubUtils.isSEmptyOrNull(templateCode)) {
            logger.error("模板配置编辑错误, 入参为空, templateCode:null or '' ");
            return "/error/error-500";
        }
        setDefaultModel(model);
        model.addAttribute("templateCode",templateCode);
        return "/storage/template/template_edit";
    }

    /**
     * 模板配置编辑确认
     * @param ofcStorageTemplateEditDTO 模板列表
     */
    @RequestMapping(value = "/editConfirm")
    @ResponseBody
    public Wrapper storageTemplateEditConfirm(@RequestBody OfcStorageTemplateEditDTO ofcStorageTemplateEditDTO) {
        logger.info("模板配置编辑确认 ==> ofcStorageTemplateEditDTO:{}", ofcStorageTemplateEditDTO);
        if (null == ofcStorageTemplateEditDTO || CollectionUtils.isEmpty(ofcStorageTemplateEditDTO.getTemplateList()) || null == ofcStorageTemplateEditDTO.getLastTemplateType()) {
            logger.error("模板配置编辑确认错误, 入参为空, templateList:null or '' ");
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"模板配置编辑确认错误");
        }
        AuthResDto authResDto = getAuthResDtoByToken();
        try {
            ofcStorageTemplateService.templateEditConfirm(ofcStorageTemplateEditDTO.getTemplateList(), authResDto, ofcStorageTemplateEditDTO.getLastTemplateType());
        } catch (BusinessException e) {
            logger.error("模板配置编辑确认错误, {}", e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            logger.error("模板配置编辑确认错误, {}", e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"模板配置编辑确认错误");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE);
    }

    /**
     * 模板配置删除
     * @param templateCondition 模板编码
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Wrapper storageTemplateDel(@RequestBody TemplateCondition templateCondition) {
        logger.info("模板配置删除 ==> templateCondition:{}",templateCondition);
        try {
            if (templateCondition == null || PubUtils.isSEmptyOrNull(templateCondition.getTemplateCode())) {
                logger.error("模板配置删除错误, 入参为空, temlpateCode:null or '' ");
                return WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
            }
            ofcStorageTemplateService.delTemplateByCode(templateCondition.getTemplateCode());
        } catch (BusinessException e) {
            logger.error("模板配置删除错误, {}", e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            logger.error("模板配置删除错误, {}", e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE);
    }

    /**
     * 模板配置详情跳转
     * @param templateCode 模板编码
     */
    @RequestMapping(value = "/detail/{templateCode}")
    public ModelAndView storageTemplateDetail(@PathVariable String templateCode) {
        logger.info("模板配置详情跳转 ==> templateCode:{}",templateCode);
        ModelAndView modelAndView = new ModelAndView("/storage/template/template_detail");
        try {
            if (PubUtils.isSEmptyOrNull(templateCode)) {
                logger.error("模板配置详情跳转错误, 入参为空, templateCode:null or '' ");
                return new ModelAndView("/error/error-500");
            }
            modelAndView.addObject("templateCode",templateCode);
        } catch (Exception e) {
            logger.error("模板配置详情跳转错误, {}", e);
            return new ModelAndView("/error/error-500");
        }
        return modelAndView;
    }

    /**
     * 模板配置详情数据
     * @param templateCondition 模板编码
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Wrapper storageTemplateDetailData(@RequestBody TemplateCondition templateCondition) {
        logger.info("模板配置详情数据 ==> templateCondition:{}",templateCondition);
        if (null == templateCondition || PubUtils.isSEmptyOrNull(templateCondition.getTemplateCode())) {
            logger.error("模板配置详情数据错误, 入参为空, templateList:null or '' ");
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"模板配置详情数据错误");
        }
        List<OfcStorageTemplate> ofcStorageTemplateList;
        try {
            ofcStorageTemplateList = ofcStorageTemplateService.selectTemplateDetail(templateCondition);
        } catch (BusinessException e) {
            logger.error("模板配置详情数据错误, {}", e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            logger.error("模板配置详情数据错误, {}", e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"模板配置详情数据错误");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE,ofcStorageTemplateList);
    }

    /**
     * 加载所有仓库
     */
    @RequestMapping(value = "/warehouse")
    @ResponseBody
    public Wrapper loadWarehouse() {
        List<RmcWarehouseRespDto> rmcWarehouseRespDtos = ofcStorageTemplateService.allWarehouseByRmc();
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,rmcWarehouseRespDtos);
    }


    /**
     * 跳转入库开单批量导单
     */
    @RequestMapping(value = "/batch_import/{templateType}")
    public ModelAndView batchIn(Model model, @PathVariable("templateType") String templateType) {
        if (PubUtils.isSEmptyOrNull(templateType)) {
            logger.error("跳转入库开单批量导单,templateType为空, templateType:{}", templateType);
            return new ModelAndView("/error/error-500");
        }
        ModelAndView modelAndView = new ModelAndView("/storage/template/batch_import");
        setDefaultModel(model);
        model.addAttribute("templateType", templateType);
        return modelAndView;
    }

    /**
     * 根据客户编码查询配置模板列表
     */
    @RequestMapping(value = "/templist")
    @ResponseBody
    public Wrapper templateListByCustCode(@RequestBody TemplateCondition templateCondition) {
        if (null == templateCondition || PubUtils.isSEmptyOrNull(templateCondition.getTemplateType())
                || PubUtils.isSEmptyOrNull(templateCondition.getCustCode())) {
            logger.error("根据客户编码查询配置模板列表入参错误 templateCondition:{}", templateCondition);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"根据客户编码查询配置模板列表入参错误");
        }
        List<OfcStorageTemplate> ofcStorageTemplateList;
        try {
            ofcStorageTemplateList = ofcStorageTemplateService.selectTemplate(templateCondition);
        } catch (BusinessException e) {
            logger.error("模板配置保存失败!{},{}", e, e.getMessage());
            return WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            logger.error("模板配置保存失败!{},{}", e, e.getMessage());
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"根据客户编码查询配置模板列表入参错误");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, ofcStorageTemplateList);
    }


    /**
     * 仓储开单批量导单
     */
    @RequestMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Wrapper batchInUpload(@RequestParam(value = "file") MultipartFile file, OfcStorageTemplate ofcStorageTemplate) {
        logger.info("仓储开单批量导单: {}", ofcStorageTemplate);
        Wrapper<?> result = null;
        try {
            if (PubUtils.isSEmptyOrNull(ofcStorageTemplate.getCustCode())) {
                return WrapMapper.wrap(ERROR_CUST, "请先选择客户");
            } else if (PubUtils.isSEmptyOrNull(ofcStorageTemplate.getTemplateCode())) {
                return WrapMapper.wrap(ERROR_TEMPLATE, "请选择模板");
            }
            AuthResDto authResDto = getAuthResDtoByToken();
            Integer activeSheetNum = ofcStorageTemplateService.checkStorageTemplate(file);
            Wrapper<?> checkResult = ofcStorageTemplateService.checkStorageTemplate(file, authResDto, ofcStorageTemplate, activeSheetNum);

            if (checkResult.getCode() == Wrapper.ERROR_CODE) {
                List<String> errorMsg = (List<String>) checkResult.getResult();
                result =  WrapMapper.wrap(Wrapper.ERROR_CODE,checkResult.getMessage(),errorMsg);
            } else if (checkResult.getCode() == Wrapper.SUCCESS_CODE) {
                List<Object> resultList = (List<Object>) checkResult.getResult();
                result =  WrapMapper.wrap(Wrapper.SUCCESS_CODE,checkResult.getMessage(),resultList);
            }
        } catch (BusinessException e) {
            logger.error("仓储开单Excel导入校验出错:{}", e.getMessage(), e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            logger.error("仓储开单Excel导入校验出错:{}", e.getMessage(), e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
        }
        logger.info("导单结果:{}", ToStringBuilder.reflectionToString(result));
        return result;
    }



    /**
     * 跳转入库开单批量导单
     */
    @RequestMapping(value = "batch_out")
    public ModelAndView batchOut() {
        return new ModelAndView("/storage/out/batch_import_out");
    }


    /**
     * 仓储开单批量导单确认下单
     */
    @RequestMapping(value = "/confirm")
    @ResponseBody
    public Wrapper confirm(@RequestBody List<OfcStorageTemplateDto> orderList) {
        logger.info("仓储开单批量导单确认下单 ==> orderList:{}", orderList);
        try {
            AuthResDto authResDto = getAuthResDtoByToken();
            //下单
            Wrapper placeOrderResult = ofcStorageTemplateService.orderConfirm(orderList, authResDto);
            if (Wrapper.ERROR_CODE == placeOrderResult.getCode()) {
                return placeOrderResult;
            }
            logger.info("批量下单成功,开始进行批量审核");
            //审核
            Wrapper auditOrderResult = ofcStorageTemplateService.storageTemplateAudit(placeOrderResult.getResult(), authResDto);
            if (Wrapper.ERROR_CODE == auditOrderResult.getCode()) {
                return auditOrderResult;
            }
        } catch (BusinessException e) {
            logger.error("仓储开单批量导单确认下单出错, 错误信息:{},{}", e.getMessage(), e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            logger.error("仓储开单批量导单确认下单出错, 错误信息:{},{}", e.getMessage(), e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
    }
}
