package com.xescm.ofc.web.rest;

import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.domain.OfcTransplanInfo;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.client.FeignCscCustomerAPIClient;
import com.xescm.ofc.feign.client.FeignCscStoreAPIClient;
import com.xescm.ofc.feign.client.FeignRmcCompanyAPIClient;
import com.xescm.ofc.model.dto.csc.CscSupplierInfoDto;
import com.xescm.ofc.model.dto.csc.QueryCustomerIdDto;
import com.xescm.ofc.model.dto.csc.QueryStoreDto;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.model.dto.rmc.RmcCompanyLineQO;
import com.xescm.ofc.model.dto.rmc.RmcWarehouse;
import com.xescm.ofc.model.vo.csc.CscContantAndCompanyVo;
import com.xescm.ofc.model.vo.csc.CscStorevo;
import com.xescm.ofc.model.vo.rmc.RmcCompanyLineVo;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.JSONUtils;
import com.xescm.ofc.utils.PubUtils;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by ydx on 2016/10/11.
 */
@RequestMapping(value = "/ofc",produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcOrderManageRest extends BaseController{
    @Autowired
    private OfcOrderManageService ofcOrderManageService;
    @Autowired
    private OfcOrderDtoService ofcOrderDtoService;
    @Autowired
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Autowired
    private FeignRmcCompanyAPIClient feignRmcCompanyAPIClient;
    @Autowired
    private FeignCscCustomerAPIClient feignCscCustomerAPIClient;
    @Autowired
    private OfcWarehouseInformationService ofcWarehouseInformationService;
    @Autowired
    private FeignCscStoreAPIClient feignCscStoreAPIClient;
    @Autowired
    private OfcTransplanInfoService ofcTransplanInfoService;

    /**
     * 订单审核/反审核
     * @param
     * @return
     */
    @RequestMapping(value = "/orderOrNotAudit", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> orderAudit(Model model, String orderCode, String orderStatus, String reviewTag, HttpServletResponse response){
        logger.debug("==>订单中心订单管理订单审核反审核订单code orderCode={}", orderCode);
        logger.debug("==>订单中心订单管理订单审核反审核订单状态code orderStatus={}", orderStatus);
        logger.debug("==>订单中心订单管理订单审核反审核标志位 reviewTag={}", reviewTag);
        String result = null;
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        try {
            result = ofcOrderManageService.orderAudit(orderCode,orderStatus,reviewTag,authResDtoByToken);
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单审核反审核出现异常:{}", ex.getMessage(), ex);
            ex.printStackTrace();
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE,result);
    }

    /**
     * 订单删除
     * @param orderCode
     * @return com.xescm.uam.utils.wrap.Wrapper;
     */
    @RequestMapping(value = "/orderDelete", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> orderDelete(Model model, String orderCode, String orderStatus){
        logger.debug("==>订单中心订单管理订单删除订单code orderCode={}", orderCode);
        logger.debug("==>订单中心订单管理订单删除订单状态code orderStatus={}", orderStatus);
        String result = null;
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        try {
            result = ofcOrderManageService.orderDelete(orderCode,orderStatus,authResDtoByToken);
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单删除出现异常:{}", ex.getMessage(), ex);
            ex.printStackTrace();
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * 订单取消
     * @param orderCode
     * @return
     */
    @RequestMapping(value = "/orderCancel", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> orderCancel(Model model, String orderCode, String orderStatus, HttpServletResponse response){
        logger.debug("==>订单中心订单管理订单取消订单code orderCode={}", orderCode);
        logger.debug("==>订单中心订单管理订单取消订单状态code orderStatus={}", orderStatus);
        String result = null;
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        try {
            result = ofcOrderManageService.orderCancel(orderCode,orderStatus,authResDtoByToken);
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单取消出现异常:{}", ex.getMessage());
            //ex.printStackTrace();
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * 进入订单编辑
     * @param orderCode
     * @param dtotag
     * @return
     */
    @RequestMapping(value = "/getOrderDetailByCode/{orderCode}/{dtotag}")
    public String getOrderDetailByCode(Model model, @PathVariable String orderCode, @PathVariable String dtotag, Map<String,Object> map){
        logger.debug("==>订单中心订单管理订单orderCode orderCode={}", orderCode);
        logger.debug("==>订单中心订单管理订单编辑标志位 dtotag={}", dtotag);
        setDefaultModel(model);
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        QueryCustomerIdDto queryCustomerIdDto = new QueryCustomerIdDto();
        queryCustomerIdDto.setGroupId(authResDtoByToken.getGroupId());
        Wrapper<?> wrapper = feignCscCustomerAPIClient.queryCustomerIdByGroupId(queryCustomerIdDto);
        String custId = (String) wrapper.getResult();
        OfcOrderDTO ofcOrderDTO=new OfcOrderDTO();
        orderCode=orderCode.replace(",","");
        List<OfcGoodsDetailsInfo> ofcGoodsDetailsList = null;
        CscContantAndCompanyVo consignorMessage = null;
        CscContantAndCompanyVo consigneeMessage = null;
        CscSupplierInfoDto supportMessage = null;
        List<RmcWarehouse> rmcWarehouseByCustCode = null;
        List<CscStorevo> cscStoreListResult = null;
        Wrapper<List<CscStorevo>> storeByCustomerId = null;
        try{
            ofcOrderDTO = ofcOrderDtoService.orderDtoSelect(orderCode,dtotag);
            ofcGoodsDetailsList= ofcGoodsDetailsInfoService.goodsDetailsScreenList(orderCode,"orderCode");
            rmcWarehouseByCustCode = ofcWarehouseInformationService.getWarehouseListByCustCode(custId);
            //如果是运输订单,就去找收发货方联系人的信息
            if(OrderConstConstant.TRANSPORTORDER.equals(ofcOrderDTO.getOrderType())){
                consignorMessage = ofcOrderManageService.getContactMessage(ofcOrderDTO.getConsignorName(),ofcOrderDTO.getConsignorContactName(), OrderConstConstant.CONTACTPURPOSECONSIGNOR,custId,authResDtoByToken);
                consigneeMessage = ofcOrderManageService.getContactMessage(ofcOrderDTO.getConsigneeName(),ofcOrderDTO.getConsigneeContactName(), OrderConstConstant.CONTACTPURPOSECONSIGNEE,custId,authResDtoByToken);
            }
            //仓配订单
            if(OrderConstConstant.WAREHOUSEDISTRIBUTIONORDER.equals(ofcOrderDTO.getOrderType())){

                String businessTypeHead = ofcOrderDTO.getBusinessType().substring(0,2);
                //如果是仓配订单而且是需要提供运输的,就去找收发货方联系人的信息
                if(OrderConstConstant.WAREHOUSEORDERPROVIDETRANS == ofcOrderDTO.getProvideTransport()){
                    consignorMessage = ofcOrderManageService.getContactMessage(ofcOrderDTO.getConsignorName(),ofcOrderDTO.getConsignorContactName(), OrderConstConstant.CONTACTPURPOSECONSIGNOR,custId,authResDtoByToken);
                    consigneeMessage = ofcOrderManageService.getContactMessage(ofcOrderDTO.getConsigneeName(),ofcOrderDTO.getConsigneeContactName(), OrderConstConstant.CONTACTPURPOSECONSIGNEE,custId,authResDtoByToken);
                }
                //如果是仓配订单而且业务类型是入库单,就去找供应商信息
                if("62".equals(businessTypeHead)){
                    supportMessage = ofcOrderManageService.getSupportMessage(ofcOrderDTO.getSupportName(),ofcOrderDTO.getSupportContactName(),custId,authResDtoByToken);
                }
            }
            QueryStoreDto queryStoreDto = new QueryStoreDto();
            queryStoreDto.setCustomerId(custId);
            storeByCustomerId = feignCscStoreAPIClient.getStoreByCustomerId(queryStoreDto);
            cscStoreListResult = storeByCustomerId.getResult();
        }catch (BusinessException ex) {
            logger.error("订单中心订单管理订单编辑出现异常:{}", ex.getMessage(), ex);
        }catch (Exception ex) {
            logger.error("订单中心订单管理订单编辑出现异常:{}", ex.getMessage(), ex);
            ex.printStackTrace();
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
     * @param ofcGoodsDetailsInfo
     * @return
     */
    @RequestMapping("/goodsDelete")
    public void goodsDelete(Model model, OfcGoodsDetailsInfo ofcGoodsDetailsInfo,HttpServletResponse response){
        logger.debug("==>订单中心订单管理订单删除实体 ofcGoodsDetailsInfo={}", ofcGoodsDetailsInfo);
        try {
            String result = ofcGoodsDetailsInfoService.deleteByOrderCode(ofcGoodsDetailsInfo);
            response.getWriter().print(result);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
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
    public void companySelByApi(Model model, RmcCompanyLineQO rmcCompanyLineQO, HttpServletResponse response){
        //调用外部接口,最低传CustomerCode
        try{
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            if(!PubUtils.trimAndNullAsEmpty(rmcCompanyLineQO.getBeginCityName()).equals("")
                    && !PubUtils.trimAndNullAsEmpty(rmcCompanyLineQO.getBeginCityName()).equals("~")
                    && rmcCompanyLineQO.getBeginCityName().split("~")[0].split("/").length>=2){

                String beginCity[]=rmcCompanyLineQO.getBeginCityName().split("~")[0].split("/");
                rmcCompanyLineQO.setBeginCityName(beginCity[1]);
            }else{
                rmcCompanyLineQO.setBeginCityName(null);
            }
            if(!PubUtils.trimAndNullAsEmpty(rmcCompanyLineQO.getArriveCityName()).equals("")
                    && !PubUtils.trimAndNullAsEmpty(rmcCompanyLineQO.getArriveCityName()).equals("~")
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
            response.getWriter().print(JSONUtils.objectToJson(rmcCompanyLists.getResult()));
        }catch (Exception ex){
            logger.error("订单中心筛选服务商出现异常:{}", ex.getMessage(), ex);
        }
    }

    /**
     * 计划单修改
     * @param
     * @return
     */
    @RequestMapping(value = "/planUpdate", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> planUpdate(Model model, String planCode, String planStatus, String serviceProviderName,String serviceProviderContact,String serviceProviderContactPhone,HttpServletResponse response){
        logger.debug("==>计划单编号", planCode);
        logger.debug("==>计划单资源分配状态", planStatus);
        logger.debug("==>服务商名称", serviceProviderName);
        String result = null;
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        String userName=authResDtoByToken.getUamUser().getUserName();
        
        try {
            result = ofcOrderManageService.planUpdate(planCode,planStatus,serviceProviderName,serviceProviderContact,serviceProviderContactPhone,userName);
        } catch (Exception ex) {
            logger.error("计划单状态更新出错:{}", ex.getMessage(), ex);
            ex.printStackTrace();
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        }

        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE,result);
    }

    @ApiOperation(value="服务商筛选", notes="根据查询条件筛选服务商")
    @ApiImplicitParams({
            //@ApiImplicitParam(name = "cscGoods", value = "服务商筛选条件", required = true, dataType = "CscGoods"),
    })
    @RequestMapping(value = "/planSeleForTime", method = RequestMethod.POST)
    @ResponseBody
    public void planSeleForTime(Model model, String planCode,ServletResponse response){
        logger.debug("==>订单中心订单管理订单审核反审核订单code orderCode={}", planCode);
        String result = null;
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
        if(!PubUtils.trimAndNullAsEmpty(rmcCompanyLineQO.getBeginCityName()).equals("")
                && !PubUtils.trimAndNullAsEmpty(rmcCompanyLineQO.getBeginCityName()).equals("~")
                && rmcCompanyLineQO.getBeginCityName().split("~")[0].split("/").length>=2){

            String beginCity[]=rmcCompanyLineQO.getBeginCityName().split("~")[0].split("/");
            rmcCompanyLineQO.setBeginCityName(beginCity[1]);
        }else{
            rmcCompanyLineQO.setBeginCityName(null);
        }
        if(!PubUtils.trimAndNullAsEmpty(rmcCompanyLineQO.getArriveCityName()).equals("")
                && !PubUtils.trimAndNullAsEmpty(rmcCompanyLineQO.getArriveCityName()).equals("~")
                && rmcCompanyLineQO.getArriveCityName().split("~")[0].split("/").length>=2){
            String arriveCity[]=rmcCompanyLineQO.getArriveCityName().split("~")[0].split("/");
            rmcCompanyLineQO.setArriveCityName(arriveCity[1]);
        }else {
            rmcCompanyLineQO.setArriveCityName(null);
        }
        Wrapper<List<RmcCompanyLineVo>> rmcCompanyLists = feignRmcCompanyAPIClient.queryCompanyLine(rmcCompanyLineQO);
        try{
            response.getWriter().print(JSONUtils.objectToJson(rmcCompanyLists.getResult()));
        }catch (Exception ex){
            logger.error("订单中心筛选服务商出现异常:{}", ex.getMessage(), ex);
        }

    }




}
