package com.xescm.ofc.web.rest;

import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.domain.OfcOrderDTO;
import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.domain.dto.csc.CscSupplierInfoDto;
import com.xescm.ofc.domain.dto.csc.QueryCustomerIdDto;
import com.xescm.ofc.domain.dto.csc.vo.CscContantAndCompanyVo;
import com.xescm.ofc.enums.OrderConstEnum;
import com.xescm.ofc.feign.client.FeignCscCustomerAPIClient;
import com.xescm.ofc.service.OfcGoodsDetailsInfoService;
import com.xescm.ofc.service.OfcOrderDtoService;
import com.xescm.ofc.service.OfcOrderManageService;
import com.xescm.ofc.service.OfcOrderStatusService;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.PubUtils;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyh on 2016/10/12.
 * 订单追踪
 */
@RequestMapping(value = "/ofc",produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcOrderFollowRest extends BaseController{
    @Autowired
    private OfcOrderDtoService ofcOrderDtoService;
    @Autowired
    private OfcOrderStatusService ofcOrderStatusService;
    @Autowired
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Autowired
    private OfcOrderManageService ofcOrderManageService;
    @Autowired
    private FeignCscCustomerAPIClient feignCscCustomerAPIClient;

    /*@RequestMapping(value = "/orderFollowCon/{code}/{followTag}",method = RequestMethod.GET)
    public String orderFollowCon(Model model, @PathVariable String code, @PathVariable String followTag, Map<String,Object> map) throws InvocationTargetException {
        logger.debug("==>订单中心订单追踪条件筛选code code={}", code);
        logger.debug("==>订单中心订单追踪条件标志位 followTag={}", followTag);
        OfcOrderDTO ofcOrderDTO = ofcOrderDtoService.orderDtoSelect(code, followTag);
        List<OfcOrderStatus> ofcOrderStatuses = ofcOrderStatusService.orderStatusScreen(code, followTag);
        map.put("ofcOrderDTO",ofcOrderDTO);
        map.put("orderStatusList",ofcOrderStatuses);
        return "order_follow";
    }*/
    @RequestMapping(value = "/orderFollowCon",method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> orderFollowCon(Model model, String code, String followTag) throws InvocationTargetException {
        logger.debug("==>订单中心订单追踪条件筛选code code={}", code);
        logger.debug("==>订单中心订单追踪条件标志位 followTag={}", followTag);
        //Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<>();
        try{
            code = PubUtils.trimAndNullAsEmpty(code);
            followTag = PubUtils.trimAndNullAsEmpty(followTag);
            OfcOrderDTO ofcOrderDTO = ofcOrderDtoService.orderDtoSelect(code, followTag);
            List<OfcOrderStatus> ofcOrderStatuses = ofcOrderStatusService.orderStatusScreen(code, followTag);//PubUtils.trimAndNullAsEmpty

            map.put("ofcOrderDTO",ofcOrderDTO);
            map.put("ofcOrderStatus",ofcOrderStatuses);
        }catch (Exception ex){
            logger.error("订单中心订单追踪出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE,map);
    }


    @RequestMapping(value = "/orderDetails/{orderCode}/{followTag}/{historyUrlTag}", method = RequestMethod.GET)
    public String orderDetails(Model model,@PathVariable("orderCode") String code,@PathVariable String followTag,@PathVariable String historyUrlTag, Map<String,Object> map) throws InvocationTargetException{
        logger.debug("==>订单中心订单详情code code={}", code);
        logger.debug("==>订单中心订单详情标志位 followTag={}", followTag);
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        QueryCustomerIdDto queryCustomerIdDto = new QueryCustomerIdDto();
        queryCustomerIdDto.setGroupId(authResDtoByToken.getGroupId());
        Wrapper<?> wrapper = feignCscCustomerAPIClient.queryCustomerIdByGroupId(queryCustomerIdDto);
        String custId = (String) wrapper.getResult();
        OfcOrderDTO ofcOrderDTO = new OfcOrderDTO();
        //List<OfcOrderStatus> ofcOrderStatuses = new ArrayList<OfcOrderStatus>();
        List<OfcOrderStatus> ofcOrderStatuses = new ArrayList<>();
        
        //List<OfcGoodsDetailsInfo> goodsDetailsList = new ArrayList<OfcGoodsDetailsInfo>();
        List<OfcGoodsDetailsInfo> goodsDetailsList = new ArrayList<>();
        //CscContantAndCompanyVo consignorMessage = null;
        //CscContantAndCompanyVo consigneeMessage = null;
        CscSupplierInfoDto supportMessage = null;
        try{
            ofcOrderDTO = ofcOrderDtoService.orderDtoSelect(code, followTag);
            ofcOrderStatuses = ofcOrderStatusService.orderStatusScreen(code, followTag);
            goodsDetailsList=ofcGoodsDetailsInfoService.goodsDetailsScreenList(code,followTag);
            //如果是运输订单,就去找收发货方联系人的信息
            /*if(OrderConstEnum.TRANSPORTORDER.equals(ofcOrderDTO.getOrderType())){
                if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getConsignorName()) || !PubUtils.isSEmptyOrNull(ofcOrderDTO.getConsignorContactName()) ){
                    consignorMessage = ofcOrderManageService.getContactMessage(ofcOrderDTO.getConsignorName(),ofcOrderDTO.getConsignorContactName(),OrderConstEnum.CONTACTPURPOSECONSIGNOR,custId,authResDtoByToken);
                }
                if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getConsignorName()) || !PubUtils.isSEmptyOrNull(ofcOrderDTO.getConsignorContactName()) ){
                    consigneeMessage = ofcOrderManageService.getContactMessage(ofcOrderDTO.getConsigneeName(),ofcOrderDTO.getConsigneeContactName(),OrderConstEnum.CONTACTPURPOSECONSIGNEE,custId,authResDtoByToken);
                }
            }*/
            //仓配订单
            if(OrderConstEnum.WAREHOUSEDISTRIBUTIONORDER.equals(ofcOrderDTO.getOrderType())){
                /*rmcWarehouseByCustCode = ofcWarehouseInformationService.getWarehouseListByCustCode(custId);*/
                String businessTypeHead = ofcOrderDTO.getBusinessType().substring(0,2);
                //如果是仓配订单而且是需要提供运输的,就去找收发货方联系人的信息
                /*if(OrderConstEnum.WAREHOUSEORDERPROVIDETRANS == ofcOrderDTO.getProvideTransport()){
                    if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getConsignorName()) || !PubUtils.isSEmptyOrNull(ofcOrderDTO.getConsignorContactName()) ){
                        consignorMessage = ofcOrderManageService.getContactMessage(ofcOrderDTO.getConsignorName(),ofcOrderDTO.getConsignorContactName(),OrderConstEnum.CONTACTPURPOSECONSIGNOR,custId,authResDtoByToken);
                    }
                    if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getConsignorName()) || !PubUtils.isSEmptyOrNull(ofcOrderDTO.getConsignorContactName()) ){
                        consigneeMessage = ofcOrderManageService.getContactMessage(ofcOrderDTO.getConsigneeName(),ofcOrderDTO.getConsigneeContactName(),OrderConstEnum.CONTACTPURPOSECONSIGNEE,custId,authResDtoByToken);
                    }
                }*/
                //如果是仓配订单而且业务类型是入库单,就去找供应商信息
                if("62".equals(businessTypeHead)){
                    if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getSupportName()) || !PubUtils.isSEmptyOrNull(ofcOrderDTO.getSupportContactName()) ){
                        supportMessage = ofcOrderManageService.getSupportMessage(ofcOrderDTO.getSupportName(),ofcOrderDTO.getSupportContactName(),custId,authResDtoByToken);
                    }
                }
            }

        }catch (Exception ex){
            logger.error("订单中心订单追踪出现异常:{}", ex.getMessage(), ex);
//            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        }
        map.put("ofcOrderDTO",ofcOrderDTO);
        map.put("orderStatusList",ofcOrderStatuses);
        map.put("goodsDetailsList",goodsDetailsList);
        //map.put("consignorMessage",consignorMessage);
        //map.put("consigneeMessage",consigneeMessage);
        map.put("supportMessage",supportMessage);
        if("orderManage".equals(historyUrlTag)){
            map.put("historyUrl",historyUrlTag);
        }else if("orderScreen".equals(historyUrlTag)){
            map.put("historyUrl",historyUrlTag);
        }

        return "order_detail";
    }



}
