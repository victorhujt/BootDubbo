package com.xescm.ofc.web.restcontroller;

import com.alibaba.fastjson.JSONObject;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.QueryStoreDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyResponseDto;
import com.xescm.csc.model.vo.CscStorevo;
import com.xescm.csc.provider.CscStoreEdasService;
import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.constant.OrderConstant;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.domain.OfcTransplanInfo;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.service.*;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.rmc.edas.domain.qo.RmcCompanyLineQO;
import com.xescm.rmc.edas.domain.vo.RmcCompanyLineVo;
import com.xescm.rmc.edas.domain.vo.RmcWarehouseRespDto;
import com.xescm.rmc.edas.service.RmcCompanyInfoEdasService;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.xescm.core.utils.PubUtils.isSEmptyOrNull;
import static com.xescm.core.utils.PubUtils.trimAndNullAsEmpty;
import static com.xescm.ofc.constant.OrderConstant.TRANSPORT_ORDER;
import static com.xescm.ofc.constant.OrderConstant.WAREHOUSE_DIST_ORDER;

/**
 * <p>Title:    .客户中心 订单管理 </p>
 * <p>Description TODO </p>
 * <p>Company:    http://www.hnxianyi.com </p>
 *
 * @author        杨东旭
 * @CreateDate    2016/10/11
 */
@RequestMapping(value = "/ofc",produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcOrderManageRest extends BaseController{
    @Resource
    private OfcOrderManageService ofcOrderManageService;
    @Resource
    private OfcOrderDtoService ofcOrderDtoService;
    @Resource
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Resource
    private RmcCompanyInfoEdasService rmcCompanyInfoEdasService;
    @Resource
    private OfcWarehouseInformationService ofcWarehouseInformationService;
    @Resource
    private CscStoreEdasService cscStoreEdasService;
    @Resource
    private OfcTransplanInfoService ofcTransplanInfoService;
    @Resource
    private OfcFundamentalInformationService ofcFundamentalInformationService;

    /**
     * 订单审核/反审核
     * @param orderCode  订单编号
     * @param orderStatus   订单状态
     * @param reviewTag     审核标记
     * @return      Wrapper
     */
    @RequestMapping(value = "/orderOrNotAudit", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> orderAudit(String orderCode, String orderStatus, String reviewTag){
        logger.info("==>订单中心订单管理订单审核反审核订单code orderCode={}", orderCode);
        logger.info("==>订单中心订单管理订单审核反审核订单状态code orderStatus={}", orderStatus);
        logger.info("==>订单中心订单管理订单审核反审核标志位 reviewTag={}", reviewTag);
        String result;
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        try {
            result = ofcOrderManageService.orderAudit(orderCode,orderStatus,reviewTag,authResDtoByToken);
        }catch (BusinessException ex){
            logger.error("订单中心订单管理订单审核反审核出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        }catch (Exception ex) {
            logger.error("订单中心订单管理订单审核反审核出现未知异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE,result);
    }

    /**
     * 订单删除
     * @param orderCode     订单编号
     * @return com.xescm.uam.utils.wrap.Wrapper;
     */
    @RequestMapping(value = "/orderDelete", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> orderDelete( String orderCode, String orderStatus){
        logger.info("==>订单中心订单管理订单删除订单code orderCode={}", orderCode);
        logger.info("==>订单中心订单管理订单删除订单状态code orderStatus={}", orderStatus);
        String result;
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        try {
            result = ofcOrderManageService.orderDelete(orderCode,orderStatus,authResDtoByToken);
        } catch (BusinessException ex){
            logger.error("订单中心订单管理订单删除出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单删除出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * 订单取消
     * @param orderCode     订单编号
     * @return      Wrapper
     */
    @RequestMapping(value = "/orderCancel", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> orderCancel( String orderCode, String orderStatus){
        logger.info("==>订单中心订单管理订单取消订单code orderCode={}", orderCode);
        logger.info("==>订单中心订单管理订单取消订单状态code orderStatus={}", orderStatus);
        String result;
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        try {
            result = ofcOrderManageService.orderCancel(orderCode,authResDtoByToken);
        } catch (BusinessException ex){
            logger.error("订单中心订单管理订单取消出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单取消出现未知异常:{}", ex.getMessage(),ex);
            //
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "订单中心订单管理订单取消出现异常");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * 进入订单编辑
     * @param orderCode    订单编号
     * @param dtotag    标记
     * @return  String
     */
    @RequestMapping(value = "/getOrderDetailByCode/{orderCode}/{dtotag}")
    public String getOrderDetailByCode(Model model, @PathVariable String orderCode, @PathVariable String dtotag, Map<String,Object> map){
        logger.info("==>订单中心订单管理订单orderCode orderCode={}", orderCode);
        logger.info("==>订单中心订单管理订单编辑标志位 dtotag={}", dtotag);
        setDefaultModel(model);
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
//        QueryCustomerIdDto queryCustomerIdDto = new QueryCustomerIdDto();
//        queryCustomerIdDto.setGroupId(authResDtoByToken.getGroupId());
//        Wrapper<?> wrapper = feignCscCustomerAPIClient.queryCustomerIdByGroupId(queryCustomerIdDto);
//        String custId = (String) wrapper.getResult();
        String customerCode = authResDtoByToken.getGroupRefCode();
        OfcOrderDTO ofcOrderDTO=new OfcOrderDTO();
        orderCode=orderCode.replace(",","");
        List<OfcGoodsDetailsInfo> ofcGoodsDetailsList = null;
        CscContantAndCompanyResponseDto consignorMessage = null;
        CscContantAndCompanyResponseDto consigneeMessage = null;
        CscSupplierInfoDto supportMessage = null;
        List<RmcWarehouseRespDto> rmcWarehouseByCustCode = null;
        List<CscStorevo> cscStoreListResult = null;
        Wrapper<List<CscStorevo>> storeByCustomerId;
        try{
            ofcOrderDTO = ofcOrderDtoService.orderDtoSelect(orderCode,dtotag);
            ofcGoodsDetailsList= ofcGoodsDetailsInfoService.goodsDetailsScreenList(orderCode,"orderCode");
            rmcWarehouseByCustCode = ofcWarehouseInformationService.getWarehouseListByCustCode(customerCode);
            //如果是运输订单,就去找收发货方联系人的信息
            if(OrderConstant.TRANSPORT_ORDER.equals(ofcOrderDTO.getOrderType())){
                consignorMessage = ofcOrderManageService.getContactMessage(ofcOrderDTO.getConsignorName(),ofcOrderDTO.getConsignorContactName(), OrderConstConstant.CONTACT_PURPOSE_CONSIGNOR,customerCode,authResDtoByToken);
                consigneeMessage = ofcOrderManageService.getContactMessage(ofcOrderDTO.getConsigneeName(),ofcOrderDTO.getConsigneeContactName(), OrderConstConstant.CONTACT_PURPOSE_CONSIGNEE,customerCode,authResDtoByToken);
            }
            //仓配订单
            if(WAREHOUSE_DIST_ORDER.equals(ofcOrderDTO.getOrderType())){

                String businessTypeHead = ofcOrderDTO.getBusinessType().substring(0,2);
                //如果是仓配订单而且是需要提供运输的,就去找收发货方联系人的信息
                if(Objects.equals(OrderConstConstant.WEARHOUSE_WITH_TRANS, ofcOrderDTO.getProvideTransport())){
                    consignorMessage = ofcOrderManageService.getContactMessage(ofcOrderDTO.getConsignorName(),ofcOrderDTO.getConsignorContactName(), OrderConstConstant.CONTACT_PURPOSE_CONSIGNOR,customerCode,authResDtoByToken);
                    consigneeMessage = ofcOrderManageService.getContactMessage(ofcOrderDTO.getConsigneeName(),ofcOrderDTO.getConsigneeContactName(), OrderConstConstant.CONTACT_PURPOSE_CONSIGNEE,customerCode,authResDtoByToken);
                }
                //如果是仓配订单而且业务类型是入库单,就去找供应商信息
                if("62".equals(businessTypeHead)){
                    supportMessage = ofcOrderManageService.getSupportMessage(ofcOrderDTO.getSupportName(),ofcOrderDTO.getSupportContactName(),customerCode,authResDtoByToken);
                }
            }
            QueryStoreDto queryStoreDto = new QueryStoreDto();
            queryStoreDto.setCustomerCode(customerCode);
            storeByCustomerId = (Wrapper<List<CscStorevo>>) cscStoreEdasService.getStoreByCustomerId(queryStoreDto);
            cscStoreListResult = storeByCustomerId.getResult();
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单编辑出现异常:{}", ex.getMessage(), ex);
        }
        if (ofcOrderDTO!=null){
            map.put("ofcGoodsDetailsList",ofcGoodsDetailsList);
            map.put("orderInfo", ofcOrderDTO);
            map.put("consignorMessage",consignorMessage);
            map.put("consigneeMessage", consigneeMessage);
            map.put("supportMessage",supportMessage);
            map.put("rmcWarehouseByCustCode",rmcWarehouseByCustCode);
            map.put("cscStoreByCustId",cscStoreListResult);
            /*map.put("warehouseCodeByOrderCode",warehouseCodeByOrderCode);
            map.put("storeCodeByOrderCode",storeCodeByOrderCode);*/
            return "/order_edit";
        }
        return "order_manage";
    }

    /**
     * 订单删除货品
     * @param ofcGoodsDetailsInfo   货品明细
     * @return  void
     */
    @RequestMapping("/goodsDelete")
    public void goodsDelete( OfcGoodsDetailsInfo ofcGoodsDetailsInfo,HttpServletResponse response){
        logger.info("==>订单中心订单管理订单删除实体 ofcGoodsDetailsInfo={}", ofcGoodsDetailsInfo);
        try {
            String result = ofcGoodsDetailsInfoService.deleteByOrderCode(ofcGoodsDetailsInfo);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(result);
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单取消出现异常:{}", ex.getMessage(), ex);
        }
    }

    /**
     * 服务商筛选(调用客户中心API)
     */
    @ApiOperation(value="服务商筛选", notes="根据查询条件筛选服务商")
    @ApiImplicitParams({
            //@ApiImplicitParam(name = "cscGoods", value = "服务商筛选条件", required = true, dataType = "CscGoods"),
    })
    @RequestMapping(value = "/companySelect",method = RequestMethod.POST)
    public void companySelByApi(RmcCompanyLineQO rmcCompanyLineQO, HttpServletResponse response){
        //调用外部接口,最低传CustomerCode
        try{
            if(!trimAndNullAsEmpty(rmcCompanyLineQO.getBeginCityName()).equals("")
                    && !trimAndNullAsEmpty(rmcCompanyLineQO.getBeginCityName()).equals("~")
                    && rmcCompanyLineQO.getBeginCityName().split("~")[0].split("/").length>=2){

                String beginCity[]=rmcCompanyLineQO.getBeginCityName().split("~")[0].split("/");
                rmcCompanyLineQO.setBeginCityName(beginCity[1]);
            }else{
                rmcCompanyLineQO.setBeginCityName(null);
            }
            if(!trimAndNullAsEmpty(rmcCompanyLineQO.getArriveCityName()).equals("")
                    && !trimAndNullAsEmpty(rmcCompanyLineQO.getArriveCityName()).equals("~")
                    && rmcCompanyLineQO.getArriveCityName().split("~")[0].split("/").length>=2){
                String arriveCity[]=rmcCompanyLineQO.getArriveCityName().split("~")[0].split("/");
                rmcCompanyLineQO.setArriveCityName(arriveCity[1]);
            }else {
                rmcCompanyLineQO.setArriveCityName(null);
            }
            /*QueryCustomerIdDto queryCustomerIdDto = new QueryCustomerIdDto();
            queryCustomerIdDto.setGroupId(authResDtoByToken.getGroupId());
            Wrapper<?> wrapper = feignCscCustomerAPIClient.queryCustomerIdByGroupId(queryCustomerIdDto);
            String custId = (String) wrapper.getResult();
            cscGoods.setCustomerId(custId);*/
            //rmcCompanyLineQO.setLineType("1");
            Wrapper<List<RmcCompanyLineVo>> rmcCompanyLists = ofcOrderManageService.companySelByApi(rmcCompanyLineQO);
            /*List<RmcCompanyLineVo> lineVos1=rmcCompanyLists.getResult();
            List<RmcCompanyLineVo> lineVos2=rmcCompanyLists.getResult();
            lineVos1.addAll(lineVos2);
            lineVos1.get(0).setCompanyName("孙悟空");
            rmcCompanyLists.setResult(lineVos1);*/
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(JacksonUtil.toJsonWithFormat(rmcCompanyLists.getResult()));
        }catch (Exception ex){
            logger.error("订单中心筛选服务商出现异常:{}", ex.getMessage(), ex);
        }
    }

    /**
     * 计划单修改
     * @param planCode  计划单编号
     * @param planStatus    计划单状态
     * @param serviceProviderName   服务商
     * @param serviceProviderContact    服务商联系人
     * @param serviceProviderContactPhone   服务商联系电话
     * @return  Wrapper
     */
    @RequestMapping(value = "/planUpdate", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> planUpdate(String planCode, String planStatus, String serviceProviderName
            ,String serviceProviderContact,String serviceProviderContactPhone){
        logger.info("==>计划单编号", planCode);
        logger.info("==>计划单资源分配状态", planStatus);
        logger.info("==>服务商名称", serviceProviderName);
        String result;
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        String userName=authResDtoByToken.getUserName();

        try {
            result = ofcOrderManageService.planUpdate(planCode,planStatus,serviceProviderName,serviceProviderContact,serviceProviderContactPhone,userName);
        } catch (BusinessException ex) {
            logger.error("计划单状态更新出错:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        } catch (Exception ex) {
            logger.error("计划单状态更新出错:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
        }

        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE,result);
    }

    /**
     * 服务商筛选
     * @param planCode 计划单编号
     * @param response ServletResponse
     */
    @ApiOperation(value="服务商筛选", notes="根据查询条件筛选服务商")
    @ApiImplicitParams({
            //@ApiImplicitParam(name = "cscGoods", value = "服务商筛选条件", required = true, dataType = "CscGoods"),
    })
    @RequestMapping(value = "/planSeleForTime", method = RequestMethod.POST)
    @ResponseBody
    public void planSeleForTime( String planCode,ServletResponse response){
        logger.info("==>订单中心订单管理订单审核反审核订单code orderCode={}", planCode);
        OfcTransplanInfo ofcTransplanInfo= ofcTransplanInfoService.selectByKey(planCode);
        RmcCompanyLineQO rmcCompanyLineQO=new RmcCompanyLineQO();
        if(ofcTransplanInfo.getExpectedArrivedTime()!=null){
            Calendar ca=Calendar.getInstance();
            ca.setTime(ofcTransplanInfo.getExpectedArrivedTime());
            ca.add(Calendar.HOUR_OF_DAY, 3);
            rmcCompanyLineQO.setDepartureTime(ca.getTime());
        }else if(ofcTransplanInfo.getOrderTime()!=null){
            Calendar ca=Calendar.getInstance();
            ca.setTime(ofcTransplanInfo.getOrderTime());
            ca.add(Calendar.HOUR_OF_DAY, 3);
            rmcCompanyLineQO.setDepartureTime(ca.getTime());
        }else {
            logger.error("订单中心筛选服务商出现异常:{}");
            return;
        }
        if(!trimAndNullAsEmpty(rmcCompanyLineQO.getBeginCityName()).equals("")
                && !trimAndNullAsEmpty(rmcCompanyLineQO.getBeginCityName()).equals("~")
                && rmcCompanyLineQO.getBeginCityName().split("~")[0].split("/").length>=2){

            String beginCity[]=rmcCompanyLineQO.getBeginCityName().split("~")[0].split("/");
            rmcCompanyLineQO.setBeginCityName(beginCity[1]);
        }else{
            rmcCompanyLineQO.setBeginCityName(null);
        }
        if(!trimAndNullAsEmpty(rmcCompanyLineQO.getArriveCityName()).equals("")
                && !trimAndNullAsEmpty(rmcCompanyLineQO.getArriveCityName()).equals("~")
                && rmcCompanyLineQO.getArriveCityName().split("~")[0].split("/").length>=2){
            String arriveCity[]=rmcCompanyLineQO.getArriveCityName().split("~")[0].split("/");
            rmcCompanyLineQO.setArriveCityName(arriveCity[1]);
        }else {
            rmcCompanyLineQO.setArriveCityName(null);
        }
        Wrapper<List<RmcCompanyLineVo>> rmcCompanyLists = rmcCompanyInfoEdasService.queryCompanyLine(rmcCompanyLineQO);
        try{
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(JacksonUtil.toJsonWithFormat(rmcCompanyLists.getResult()));
        }catch (Exception ex){
            logger.error("订单中心筛选服务商出现异常:{}", ex.getMessage(), ex);
        }

    }

    /**
     *
     * @param ofcOrderDTOStr 订单信息
     * @param orderGoodsListStr  货品信息
     * @param cscContantAndCompanyDtoConsignorStr  发货方信息
     * @param cscContantAndCompanyDtoConsigneeStr  收货方信息
     * @param cscSupplierInfoDtoStr 供应商信息
     * @param tag
     * @return
     */
    @RequestMapping(value ="saveStorage", method = {RequestMethod.POST})
    @ResponseBody
    public Wrapper<?> saveStorage(String ofcOrderDTOStr,String orderGoodsListStr,String cscContantAndCompanyDtoConsignorStr
            ,String cscContantAndCompanyDtoConsigneeStr,String cscSupplierInfoDtoStr,String tag) {
        logger.info("==>仓储开单或编辑实体 ofcOrderDTOStr={}", ofcOrderDTOStr);
        logger.info("==>仓储开单或编辑标志位 tag={}", tag);
        try {
            if(isSEmptyOrNull(ofcOrderDTOStr)){
               throw new BusinessException("订单的基本信息不能为空");
            }
            if(isSEmptyOrNull(orderGoodsListStr)){
                throw new BusinessException("请至少添加一条货品信息");
            }
            //订单基本信息
            OfcOrderDTO ofcOrderDTO = JacksonUtil.parseJsonWithFormat(ofcOrderDTOStr, OfcOrderDTO.class);
            //货品详情信息
            List<OfcGoodsDetailsInfo>   ofcGoodsDetailsInfos = JSONObject.parseArray(orderGoodsListStr, OfcGoodsDetailsInfo.class);

            CscContantAndCompanyDto consignor=null;
            CscContantAndCompanyDto consignee=null;
            if(ofcOrderDTO.getProvideTransport()==1) {
                if (trimAndNullAsEmpty(ofcOrderDTO.getBusinessType()).substring(0, 2).equals("61")) {
                    if (isSEmptyOrNull(cscContantAndCompanyDtoConsigneeStr)) {
                        throw new BusinessException("需要提供运输时,配送基本信息收货方不能为空");
                    }
                } else if (trimAndNullAsEmpty(ofcOrderDTO.getBusinessType()).substring(0, 2).equals("62")) {
                    if (isSEmptyOrNull(cscContantAndCompanyDtoConsignorStr)) {
                        throw new BusinessException("需要提供运输时,配送基本信息发货方不能为空");
                    }
                }
            }

            //发货方信息
            if((isSEmptyOrNull(cscContantAndCompanyDtoConsignorStr))){
                consignor=new CscContantAndCompanyDto();
            }else{
                logger.info(cscContantAndCompanyDtoConsignorStr);
                consignor = JacksonUtil.parseJsonWithFormat(cscContantAndCompanyDtoConsignorStr, CscContantAndCompanyDto.class);
            }

            if (isSEmptyOrNull(cscContantAndCompanyDtoConsigneeStr)) {
                consignee=new CscContantAndCompanyDto();
            }else{
                //收货方信息
                logger.info(cscContantAndCompanyDtoConsigneeStr);
                consignee = JacksonUtil.parseJsonWithFormat(cscContantAndCompanyDtoConsigneeStr, CscContantAndCompanyDto.class);
            }

            //供应商信息
            CscSupplierInfoDto cscSupplierInfoDto=null;
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            Wrapper<?> result=ofcOrderManageService.saveStorageOrder(ofcOrderDTO,ofcGoodsDetailsInfos,tag,consignor,consignee,cscSupplierInfoDto,authResDtoByToken);
            if(result.getCode()!=Wrapper.SUCCESS_CODE){
                if(!StringUtils.isEmpty(result.getMessage())){
                    throw new BusinessException(result.getMessage());
                }else{
                    throw new BusinessException(Wrapper.ERROR_MESSAGE);
                }
            }
        } catch (BusinessException ex){
            logger.error("仓储订单下单或编辑出现异常:{}", ex.getMessage(), ex);
            if(!StringUtils.isEmpty(ex.getMessage())){
                return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
            }else{
                return WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
                logger.error("仓储订单下单或编辑出现未知异常:{}", ex.getMessage(), ex);
                return WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,"仓储下单成功");
    }

    /**
     * 进入运输订单编辑
     * @param orderCode    订单编号
     * @return  String
     */
    @RequestMapping(value = "/getTransOrderDetailByCode/{orderCode}")
    public String getTransOrderDetailByCode(Model model, @PathVariable String orderCode,Map<String,Object> map){
        logger.info("==>订单中心订单管理订单orderCode orderCode={}", orderCode);
        setDefaultModel(model);
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        String customerCode = authResDtoByToken.getGroupRefCode();
        OfcOrderDTO ofcOrderDTO=new OfcOrderDTO();
        List<OfcGoodsDetailsInfo> ofcGoodsDetailsList = null;
        CscContantAndCompanyResponseDto consignorMessage = null;
        CscContantAndCompanyResponseDto consigneeMessage = null;
        try{
            ofcOrderDTO = ofcOrderDtoService.transOrderDotSelect(orderCode);
            ofcGoodsDetailsList= ofcGoodsDetailsInfoService.goodsDetailsScreenList(orderCode,"orderCode");
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单编辑出现异常:{}", ex.getMessage(), ex);
        }
        if (ofcOrderDTO!=null){
            map.put("ofcGoodsDetailsList",ofcGoodsDetailsList);
            map.put("orderInfo", ofcOrderDTO);
            return "/order_edit_tranload";
        }
        return "order_manage_opera";
    }

    /**
     * 进入订单编辑
     * @param orderCode    订单编号
     * @return  String
     */
    @RequestMapping(value = "/getOrderEditByCode/{orderCode}")
    public String getOrderEditByCode(Model model, @PathVariable String orderCode,Map<String,Object> map){
        logger.info("==>订单中心订单管理订单orderCode orderCode={}", orderCode);
        OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
        if (null!=ofcFundamentalInformation){
            if(trimAndNullAsEmpty(ofcFundamentalInformation.getOrderType()).equals(TRANSPORT_ORDER)){
                return getTransOrderDetailByCode(model,orderCode,map);
            }
        }
        return "order_manage_opera";
    }
}
