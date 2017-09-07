package com.xescm.ofc.web.controller;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.MD5Util;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.QueryCustomerCodeDto;
import com.xescm.csc.model.dto.QueryStoreDto;
import com.xescm.csc.model.vo.CscCustomerVo;
import com.xescm.csc.model.vo.CscStorevo;
import com.xescm.csc.provider.CscCustomerEdasService;
import com.xescm.csc.provider.CscStoreEdasService;
import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.OfcMerchandiser;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.vo.ofc.OfcGroupVo;
import com.xescm.ofc.service.OfcMerchandiserService;
import com.xescm.ofc.service.OfcMobileOrderService;
import com.xescm.ofc.service.OfcOrderManageOperService;
import com.xescm.ofc.service.OfcWarehouseInformationService;
import com.xescm.ofc.utils.DateUtils;
import com.xescm.rmc.edas.domain.vo.RmcWarehouseRespDto;
import com.xescm.uam.model.dto.group.UamGroupDto;
import com.xescm.uam.provider.UamGroupEdasService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

/**
 * 页面跳转
 * Created by lyh on 2016/10/18.
 */
@Controller
@RequestMapping(produces = {"application/json;charset=UTF-8"})
public class OfcJumpontroller extends BaseController{

    @Resource
    private OfcWarehouseInformationService ofcWarehouseInformationService;
    @Resource
    private CscStoreEdasService cscStoreEdasService;
    @Resource
    private OfcMerchandiserService ofcMerchandiserService;
    @Resource
    private OfcOrderManageOperService ofcOrderManageOperService;
    @Resource
    private CscCustomerEdasService cscCustomerEdasService;
    @Resource
    private UamGroupEdasService uamGroupEdasService;
    @Resource
    private OfcMobileOrderService ofcMobileOrderService;

    @Resource
    private RestConfig restConfig;


    @RequestMapping(value="/ofc/orderPlace")
    public ModelAndView index(Model model,Map<String,Object> map){
        List<RmcWarehouseRespDto> rmcWarehouseByCustCode;
        List<CscStorevo> cscStoreListResult = null;
        setDefaultModel(model);
        try{
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            QueryStoreDto queryStoreDto = new QueryStoreDto();
            String customerCode = authResDtoByToken.getGroupRefCode();
            queryStoreDto.setCustomerCode(customerCode);
            Wrapper<List<CscStorevo>> storeByCustomerId = cscStoreEdasService.getStoreByCustomerId(queryStoreDto);
            cscStoreListResult = storeByCustomerId.getResult();
            rmcWarehouseByCustCode = ofcWarehouseInformationService.getWarehouseListByCustCode(customerCode);

        } catch (BusinessException ex){
            logger.error("订单中心从API获取仓库信息出现异常:{}", ex.getMessage(), ex);
            rmcWarehouseByCustCode = new ArrayList<>();
        }catch (Exception ex){
            logger.error("订单中心跳转下单页面出现异常:{}", ex.getMessage(), ex);
            rmcWarehouseByCustCode = new ArrayList<>();
        }
        map.put("rmcWarehouseByCustCode",rmcWarehouseByCustCode);
        map.put("cscStoreByCustId",cscStoreListResult);
        return new ModelAndView("order_place");

    }
     @RequestMapping(value = "/ofc/orderManage")
    public String orderManage(){
        return "order_manage";
    }

    @RequestMapping(value = "/ofc/orderScreen")
    public String orderScreen(){
        return "order_screen";
    }

    @ApiOperation(value="追踪订单", notes="根据code和followTag来追踪订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "订单编号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "followTag", value = "追踪标记", required = true, dataType = "String")
    })

    @RequestMapping(value="/ofc/orderFollow",method = RequestMethod.GET)
    public String orderFollow(String code, String followTag){
        logger.info("==>订单中心订单追踪条件筛选code code={}", code);
        logger.info("==>订单中心订单追踪条件标志位 followTag={}", followTag);
        return "order_follow";
    }

    /**
     * 进入主页
     * @return
     */
    @RequestMapping(value = "/index")
    public String toIndex(){

        return "index";
    }

    /**
     * 进入主页
     * @param model
     * @return
     */
    @RequestMapping(value = "/ofc/warehouse/in")
    public String whOpen(Model model){

        return "vue/whopen";
    }
    @RequestMapping(value = "/ofc/warehouse/in1")
    public String whOpen1(Model model){

        return "vue/whopen1";
    }


    @RequestMapping(value = "/ofc/planAllocation")
    public String planAllocation(Model model){
        try{
            Date now = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(now);
            c.add(Calendar.MONTH, -1);
            model.addAttribute("startDate",c.getTime());
            model.addAttribute("endDate",now);
        }catch (Exception ex){
            logger.error("跳转运输计划单分配页面出错!",ex.getMessage(),ex);
        }
        return "plan_allocation";
    }

    /**
     * 城配开单
     * @param model
     * @return
     */
    @RequestMapping(value = "/ofc/operationDistributing")
    public String operationDistributing(Model model,Map<String,Object> map){

        try {
            List<OfcMerchandiser> merchandiserList = ofcMerchandiserService.selectAll();
            map.put("merchandiserList",merchandiserList);
            map.put("currentTime",new Date());
            // 加密登录用户名，用于前端缓存cookie的key
            String loginUser = MD5Util.encodeByAES(getAuthResDtoByToken().getUserName());
            map.put("loginUser", loginUser);
            setDefaultModel(model);
        } catch (Exception ex) {
            logger.error("跳转城配开单页面出错!",ex.getMessage(),ex);
        }
        return "operation_distributing";
    }

    /**
     * 城配开单Excel导入
     * @param model
     * @param historyUrl
     * @param map
     * @return
     */
    @RequestMapping(value = "/ofc/operationDistributingExcel/{historyUrl}/{customerCode}/{cookieKey}")
    public String operationDistributingExcel(Model model, @PathVariable String historyUrl,@PathVariable String customerCode,@PathVariable String cookieKey,  Map<String,Object> map){
        logger.info("城配开单Excel导入==> historyUrl={}", historyUrl);
        logger.info("城配开单Excel导入==> custId={}", customerCode);
        /*if("operation_distributing".equals(historyUrl)){
            historyUrl = "/ofc/operationDistributing";
        }*/
        setDefaultModel(model);
        map.put("historyUrl",historyUrl);
        if(!PubUtils.isSEmptyOrNull(customerCode)) {
            QueryCustomerCodeDto queryCustomerCodeDto = new QueryCustomerCodeDto();
            queryCustomerCodeDto.setCustomerCode(customerCode);
            Wrapper<CscCustomerVo> customerVoWrapper = cscCustomerEdasService.queryCustomerByCustomerCodeOrId(queryCustomerCodeDto);
            if (customerVoWrapper.getResult() == null || customerVoWrapper.getCode() == Wrapper.ERROR_CODE) {
                logger.error("获取客戶信息失败：custId:{}，{}", customerCode, customerVoWrapper.getMessage());
                return "/error/error-500";
            }
            String custName = customerVoWrapper.getResult().getCustomerName();
            map.put("customerCode",customerCode);
            map.put("custName",custName);
            map.put("cookieKey",cookieKey);
        }
        return "operation_distributing_excel";
    }

    /**
     * Excel确认导入,跳转城配开单
     * @param model
     * @param excelImportTag
     * @return
     */
    @RequestMapping(value = "/ofc/distributing/excelImportConfirm/{excelImportTag}/{customerCode}/{cookieKey}")
    public String excelImportConfirm(Model model, @PathVariable String excelImportTag, @PathVariable String customerCode, @PathVariable String cookieKey){
        logger.info("Excel确认导入,跳转城配开单==> excelImportTag={}", excelImportTag);
        logger.info("Excel确认导入,跳转城配开单==> excelImportTag={}", customerCode);
        List<OfcMerchandiser> merchandiserList = ofcMerchandiserService.selectAll();
        setDefaultModel(model);
        model.addAttribute("merchandiserList",merchandiserList);
        model.addAttribute("currentTime",new Date());
        model.addAttribute("excelImportTag",excelImportTag);
        if(!PubUtils.isSEmptyOrNull(customerCode)){
            QueryCustomerCodeDto queryCustomerCodeDto = new QueryCustomerCodeDto();
            queryCustomerCodeDto.setCustomerCode(customerCode);
            Wrapper<CscCustomerVo> customerVoWrapper = cscCustomerEdasService.queryCustomerByCustomerCodeOrId(queryCustomerCodeDto);
            if (customerVoWrapper.getResult() == null || customerVoWrapper.getCode() == Wrapper.ERROR_CODE) {
                logger.error("获取客戶信息失败：custId:{}，{}", customerCode, customerVoWrapper.getMessage());
                return "/error/error-500";
            }
            String custName = customerVoWrapper.getResult().getCustomerName();
            model.addAttribute("custIdFromExcelImport",customerCode);
            model.addAttribute("custNameFromExcelImport",custName);
            model.addAttribute("loginUser",cookieKey);
        }
        return "operation_distributing";
    }
    /**
     * Excel导入跳转模板配置页
     */
    @RequestMapping(value = "/ofc/distributing/toTemplatesList/{custCode}/{historyUrl}")
    public String toTemplatesList( Model model,  @PathVariable String custCode,  @PathVariable String historyUrl){
        setDefaultModel(model);
        if(!PubUtils.isSEmptyOrNull(custCode)) {
            QueryCustomerCodeDto queryCustomerCodeDto = new QueryCustomerCodeDto();
            queryCustomerCodeDto.setCustomerCode(custCode);
            Wrapper<CscCustomerVo> customerVoWrapper = cscCustomerEdasService.queryCustomerByCustomerCodeOrId(queryCustomerCodeDto);
            if (customerVoWrapper.getResult() == null || customerVoWrapper.getCode() == Wrapper.ERROR_CODE) {
                logger.error("获取客戶信息失败：custId:{}，{}", custCode, customerVoWrapper.getMessage());
                return "/error/error-500";
            }
            String custName = customerVoWrapper.getResult().getCustomerName();
            model.addAttribute("customerCode",custCode);
            model.addAttribute("custName",custName);
        }
        model.addAttribute("historyUrl",historyUrl);
        return "operation_distributing_templateslist";
    }

    /**
     * 模板配置页跳转模板添加, 编辑, 查看页面, 用同一个页面做这个事儿
     * @param model
     * @param custCode
     * @param historyUrl
     * @param tag  add
     * @return
     */
    @RequestMapping(value = "/ofc/distributing/toTemplatesoper/{custCode}/{historyUrl}/{tag}")
    public String toTemplates(Model model,@PathVariable String custCode,  @PathVariable String tag, @PathVariable String historyUrl){
        setDefaultModel(model);
        if(!PubUtils.isSEmptyOrNull(custCode)) {
            QueryCustomerCodeDto queryCustomerCodeDto = new QueryCustomerCodeDto();
            queryCustomerCodeDto.setCustomerCode(custCode);
            Wrapper<CscCustomerVo> customerVoWrapper = cscCustomerEdasService.queryCustomerByCustomerCodeOrId(queryCustomerCodeDto);
            if (customerVoWrapper.getResult() == null || customerVoWrapper.getCode() == Wrapper.ERROR_CODE) {
                logger.error("获取客戶信息失败：custId:{}，{}", custCode, customerVoWrapper.getMessage());
                return "/error/error-500";
            }
            String custName = customerVoWrapper.getResult().getCustomerName();
            model.addAttribute("customerCode",custCode);
            model.addAttribute("custName",custName);
        }
        model.addAttribute("historyUrl",historyUrl);
        return "operation_distributing_templatesoper";
    }
    /**
     * 运输开单
     * @param model
     * @param map
     * @return
     */
    @RequestMapping(value="/ofc/tranLoad")
    public ModelAndView tranLoad(Model model,Map<String,Object> map){
        try{
            logger.info("当前用户为{}",getAuthResDtoByToken().getGroupRefName());
            List<OfcMerchandiser> merchandiserList = ofcMerchandiserService.selectAll();
            map.put("merchandiserList",merchandiserList);
            map.put("currentTime",new Date());
            // 加密登录用户名，用于前端缓存cookie的key
            String loginUser = MD5Util.encodeByAES(getAuthResDtoByToken().getUserName());
            map.put("loginUser", loginUser);
            setDefaultModel(model);
        }catch (Exception ex){
            logger.error("跳转运输开单页面出错!",ex.getMessage(),ex);
        }
        return new ModelAndView("order_tranload");
    }

    /**
     * 跳转运营中心→订单跟踪
     * @return
     */
    @RequestMapping(value = "/orderFollowOpera")
    public String orderFollowOpera() {
        return "order_follow_opera";
    }

    /**
     * 跳转到运营→订单管理 orderManageOpera
     *
     * @return modelAndView
     */
    @RequestMapping(value = "ofc/orderManageOpera", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView orderManageOpera(Model model) {
        ModelAndView modelAndView = new ModelAndView("order_manage_opera");
        setDefaultModel(model);
        Map<String,List<OfcGroupVo>> groupMap = null;
        try {
            groupMap = ofcOrderManageOperService.loadGroupList();
        } catch (Exception e) {
            logger.error("查询当前登录用户所属组织失败:{},原因:{}",e,e.getMessage());
        }
        if(null == groupMap){
            modelAndView = new ModelAndView("/error/error-500");
            logger.error("查询当前登录用户所属组织失败或您的权限太低");
            return modelAndView;
        }
        model.addAttribute("areaList", groupMap.get("area"));
        model.addAttribute("baseList", groupMap.get("base"));
        return modelAndView;
    }

    @RequestMapping(value = "ofc/mobileOrderManageOpera", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView mobileOrderManageOpera(Model model) {
        ModelAndView modelAndView = new ModelAndView("mobile_order_manager_opera");
        setDefaultModel(model);
        Calendar cal=Calendar.getInstance();
        cal.setTime(new Date());
        model.addAttribute("endTime", DateUtils.Date2String(cal.getTime(), DateUtils.DateFormatType.TYPE2));
        cal.add(Calendar.DATE,-7);
        model.addAttribute("beginTime", DateUtils.Date2String(cal.getTime(), DateUtils.DateFormatType.TYPE2));
        return modelAndView;
    }


    @RequestMapping(value = "/ofc/operDistiExcelAdditions")
    public String excelAdditions(){
        return "oper_distri_excel_additions";
    }

    /**
     * 跳转运营中心→入库开单
     * @return
     */
    @RequestMapping(value = "/ofc/orderStorageIn")
    public ModelAndView orderStorageIn(Model model) {
        ModelAndView modelAndView = new ModelAndView("order_storage_in");
        logger.info("当前用户为{}",getAuthResDtoByToken().getGroupRefName());
        model.addAttribute("merchandiser",getAuthResDtoByToken().getUserName());
        modelAndView.addObject(OrderConstConstant.OFC_WEB_URL, restConfig.getOfcWebUrl());
        setDefaultModel(model);
        return modelAndView;
    }

    /**
     * 跳转运营中心→出库开单
     * @return
     */
    @RequestMapping(value = "/ofc/orderStorageOut")
    public ModelAndView orderStorageOut(Model model) {
        ModelAndView modelAndView = new ModelAndView("order_storage_out");
        logger.info("当前用户为{}",getAuthResDtoByToken().getGroupRefName());
        model.addAttribute("merchandiser",getAuthResDtoByToken().getUserName());
        setDefaultModel(model);
        return modelAndView;
    }

    @RequestMapping(value = "/ofc/orderStorageOutManager")
    public ModelAndView orderStorageOutManager(Model model) {
        ModelAndView modelAndView = new ModelAndView("order_storage_out_manager");
        UamGroupDto uamGroupDto=new UamGroupDto();
        uamGroupDto.setSerialNo(getAuthResDtoByToken().getGroupRefCode());
        Wrapper<List<UamGroupDto>> allGroupByType = uamGroupEdasService.getAllGroupByType(uamGroupDto);
        UamGroupDto uamGroupDtoResult = allGroupByType.getResult().get(0);
        String groupType=uamGroupDtoResult.getType();
        if(StringUtils.equals(groupType,"1")) {
            //鲜易供应链身份
            if (StringUtils.equals("GD1625000003", uamGroupDtoResult.getSerialNo())) {
                model.addAttribute("isSuper", "Y");
            }
        }
        return modelAndView;
    }

    @RequestMapping(value = "/ofc/orderStorageOutEdit")
    public ModelAndView orderStorageOutEdit(Model model) {
        ModelAndView modelAndView = new ModelAndView("order_storage_out_edit");
        modelAndView.addObject(OrderConstConstant.OFC_WEB_URL, restConfig.getOfcWebUrl());
        return modelAndView;
    }

    @RequestMapping(value = "/ofc/orderStorageOutDetails")
    public ModelAndView orderStorageOutDetails(Model model) {
        ModelAndView modelAndView = new ModelAndView("order_storage_out_details");
        return modelAndView;
    }


    @RequestMapping(value = "/ofc/orderStorageInManager")
    public ModelAndView orderStorageInManager(Model model) {
        ModelAndView modelAndView = new ModelAndView("order_storage_in_manager");
        return modelAndView;
    }

    @RequestMapping(value = "/ofc/orderStorageInEdit")
    public ModelAndView orderStorageInEdit(Model model) {
        ModelAndView modelAndView = new ModelAndView("order_storage_in_edit");
        modelAndView.addObject(OrderConstConstant.OFC_WEB_URL, restConfig.getOfcWebUrl());
        return modelAndView;
    }

    @RequestMapping(value = "/ofc/orderStorageInDetails")
    public ModelAndView orderStorageInDetail(Model model) {
        ModelAndView modelAndView = new ModelAndView("order_storage_in_details");
        return modelAndView;
    }

    @RequestMapping(value = "/ofc/platformDaily")
    public ModelAndView platformDaily(Model model) {
        ModelAndView modelAndView = new ModelAndView("platform_daily");
        model.addAttribute("ofcUrl",restConfig.getOfcWebUrl());
        return modelAndView;
    }

    @RequestMapping(value = "/ofc/rule")
    public ModelAndView platformDailyrRule(Model model) {
        ModelAndView modelAndView = new ModelAndView("rule");
        model.addAttribute("ofcUrl",restConfig.getOfcWebUrl());
        return modelAndView;
    }

    /**
     * 运营中心--跳转导入模板配置
     */
    @RequestMapping(value = "/ofc/storage/template")
    public String storageTemplate(Model model){
        setDefaultModel(model);
        return "storage/template/template_manage";
    }

    /**
     * 仓储模板配置跳转添加页面
     */
    @RequestMapping(value = "/ofc/storage/template_add")
    public String storageTemplateAdd(Model model){
        AuthResDto authResDto = getAuthResDtoByToken();
        model.addAttribute("userName", authResDto.getUserName());
        model.addAttribute("orderTime", DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE2));
        setDefaultModel(model);
        return "/storage/template/template_design";
    }


    /**
     * 导出
     */
    @RequestMapping(value = "/ofc/order_load_export")
    public ModelAndView zpLoadOrderExport(Model model){
        ModelAndView modelAndView = new ModelAndView("order_load_export");
        modelAndView.addObject(OrderConstConstant.REPORT, restConfig.getReport());
        return modelAndView;
    }

    /**
     * 跳转到众品遗漏订单导入界面
     */
    @RequestMapping(value = "/ofc/toZPMissOrderImport")
    public String toZPMissOrderImport(Model model) {
        setDefaultModel(model);
        return "zp_import";
    }

    /**
     * 任务日志管理
     * @param model
     * @return
     */
    @RequestMapping(value = "/ofc/interface/taskInterfaceLog")
    public String taskInterfaceLog(Model model) {
        setDefaultModel(model);
        return "/interface/task_interface_log";
    }

    /**
     * 任务日志详情页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/ofc/interface/showTaskLogDetail")
    public String showTaskLogDetail(Model model) {
        setDefaultModel(model);
        return "/interface/task_interface_log_detail";
    }

    /**
     * 接收日志管理
     * @param model
     * @return
     */
    @RequestMapping(value = "ofc/interface/interfaceReceiveLog")
    public String interfaceReceiveLog(Model model) {
        setDefaultModel(model);
        return "/interface/interface_receive_log";
    }

    /**
     * 接收日志详情页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/ofc/interface/showReceiveLogDetail")
    public String showReceiveLogDetail(Model model) {
        setDefaultModel(model);
        return "/interface/interface_receive_log_detail";
    }
}
