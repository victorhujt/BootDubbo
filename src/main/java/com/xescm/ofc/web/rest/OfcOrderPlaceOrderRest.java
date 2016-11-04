package com.xescm.ofc.web.rest;

import com.alibaba.fastjson.JSONObject;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.domain.dto.csc.CscContantAndCompanyDto;
import com.xescm.ofc.domain.dto.csc.CscGoods;
import com.xescm.ofc.domain.dto.csc.CscSupplierInfoDto;
import com.xescm.ofc.domain.dto.csc.QueryCustomerIdDto;
import com.xescm.ofc.domain.dto.csc.vo.CscContantAndCompanyVo;
import com.xescm.ofc.domain.dto.csc.vo.CscGoodsVo;
import com.xescm.ofc.enums.OrderConstEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.client.FeignCscCustomerAPIClient;
import com.xescm.ofc.feign.client.FeignCscGoodsAPIClient;
import com.xescm.ofc.feign.client.FeignCscSupplierAPIClient;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.JSONUtils;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.ofc.wrap.WrapMapper;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.PubUtils;
import com.xescm.uam.utils.wrap.Wrapper;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lyh on 2016/10/8.
 */
@RequestMapping(value = "/ofc",produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcOrderPlaceOrderRest extends BaseController{

    @Autowired
    private OfcOrderPlaceService ofcOrderPlaceService;
    @Autowired
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Autowired
    private FeignCscGoodsAPIClient feignCscGoodsAPIClient;
    @Autowired
    private FeignCscCustomerAPIClient feignCscCustomerAPIClient;
    @Autowired
    private FeignCscSupplierAPIClient feignCscSupplierAPIClient;

    /**
     * 编辑
     * @param
     * @param response
     * @return
     */
    @RequestMapping("/orderEdit")
    @ResponseBody
    public Wrapper<?> orderEdit(Model model, String ofcOrderDTOJson,String orderGoodsListStr,String cscContantAndCompanyDtoConsignorStr
            ,String cscContantAndCompanyDtoConsigneeStr,String cscSupplierInfoDtoStr, String tag, HttpServletResponse response){
        logger.debug("==>订单中心下单或编辑实体 ofcOrderDTO={}", ofcOrderDTOJson);
        logger.debug("==>订单中心下单或编辑标志位 tag={}", tag);
        String result = null;
        try {
            orderGoodsListStr = orderGoodsListStr.replace("~`","");
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            QueryCustomerIdDto queryCustomerIdDto = new QueryCustomerIdDto();
            queryCustomerIdDto.setGroupId(authResDtoByToken.getGroupId());
            Wrapper<?> wrapper = feignCscCustomerAPIClient.queryCustomerIdByGroupId(queryCustomerIdDto);
            String custId = (String) wrapper.getResult();
            if(PubUtils.isSEmptyOrNull(ofcOrderDTOJson)){
                logger.debug(ofcOrderDTOJson);
                ofcOrderDTOJson = JSONUtils.objectToJson(new OfcOrderDTO());
            }
            if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignorStr)){
                cscContantAndCompanyDtoConsignorStr = JSONUtils.objectToJson(new CscContantAndCompanyDto());
            }
            if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsigneeStr)){
                cscContantAndCompanyDtoConsigneeStr = JSONUtils.objectToJson(new CscContantAndCompanyDto());
            }
            if(PubUtils.isSEmptyOrNull(cscSupplierInfoDtoStr)){
                cscSupplierInfoDtoStr = JSONUtils.objectToJson(new CscSupplierInfoDto());
            }
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<>();
            if(!PubUtils.isSEmptyOrNull(orderGoodsListStr)){ // 如果货品不空才去添加
                //orderGoodsListStr = JSONUtils.objectToJson(new OfcGoodsDetailsInfo());
                ofcGoodsDetailsInfos = JSONObject.parseArray(orderGoodsListStr, OfcGoodsDetailsInfo.class);
            }
            OfcOrderDTO ofcOrderDTO = JSONUtils.jsonToPojo(ofcOrderDTOJson, OfcOrderDTO.class);
            CscContantAndCompanyDto cscContantAndCompanyDtoConsignor = JSONUtils.jsonToPojo(cscContantAndCompanyDtoConsignorStr, CscContantAndCompanyDto.class);
            CscContantAndCompanyDto cscContantAndCompanyDtoConsignee = JSONUtils.jsonToPojo(cscContantAndCompanyDtoConsigneeStr, CscContantAndCompanyDto.class);
            CscSupplierInfoDto cscSupplierInfoDto = JSONUtils.jsonToPojo(cscSupplierInfoDtoStr,CscSupplierInfoDto.class);
            if (null == ofcOrderDTO.getOrderTime()){
                ofcOrderDTO.setOrderTime(new Date());
            }
            if (null == ofcOrderDTO.getProvideTransport()){
                ofcOrderDTO.setProvideTransport(OrderConstEnum.WAREHOUSEORDERNOTPROVIDETRANS);
            }
            if (null == ofcOrderDTO.getUrgent()){
                ofcOrderDTO.setUrgent(OrderConstEnum.DISTRIBUTIONORDERNOTURGENT);
            }

            result =  ofcOrderPlaceService.placeOrder(ofcOrderDTO,ofcGoodsDetailsInfos,tag,authResDtoByToken,custId
                    ,cscContantAndCompanyDtoConsignor,cscContantAndCompanyDtoConsignee,cscSupplierInfoDto);
        } catch (Exception ex) {
            logger.error("订单中心编辑出现异常:{},{}", ex.getMessage(), ex);
            ex.printStackTrace();
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,result);
    }

    @RequestMapping("/orderPlaceCon")
    @ResponseBody
    public Wrapper<?> orderPlace(Model model, String ofcOrderDTOStr,String orderGoodsListStr,String cscContantAndCompanyDtoConsignorStr
            ,String cscContantAndCompanyDtoConsigneeStr,String cscSupplierInfoDtoStr, String tag, HttpServletResponse response){
        logger.debug("==>订单中心下单或编辑实体 ofcOrderDTOStr={}", ofcOrderDTOStr);
        logger.debug("==>订单中心下单或编辑标志位 tag={}", tag);
        String resultMessage = null;
        try {
            orderGoodsListStr = orderGoodsListStr.replace("~`","");
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            QueryCustomerIdDto queryCustomerIdDto = new QueryCustomerIdDto();
            queryCustomerIdDto.setGroupId(authResDtoByToken.getGroupId());
            Wrapper<?> wrapper = feignCscCustomerAPIClient.queryCustomerIdByGroupId(queryCustomerIdDto);
            String custId = (String) wrapper.getResult();
            if(PubUtils.isSEmptyOrNull(ofcOrderDTOStr)){
                ofcOrderDTOStr = JSONUtils.objectToJson(new OfcOrderDTO());
            }
            if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignorStr)){
                cscContantAndCompanyDtoConsignorStr = JSONUtils.objectToJson(new CscContantAndCompanyDto());
            }
            if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsigneeStr)){
                cscContantAndCompanyDtoConsigneeStr = JSONUtils.objectToJson(new CscContantAndCompanyDto());
            }
            if(PubUtils.isSEmptyOrNull(cscSupplierInfoDtoStr)){
                cscSupplierInfoDtoStr = JSONUtils.objectToJson(new CscSupplierInfoDto());
            }
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<>();
            if(!PubUtils.isSEmptyOrNull(orderGoodsListStr)){ // 如果货品不空才去添加
                //orderGoodsListStr = JSONUtils.objectToJson(new OfcGoodsDetailsInfo());
                ofcGoodsDetailsInfos = JSONObject.parseArray(orderGoodsListStr, OfcGoodsDetailsInfo.class);
            }
            OfcOrderDTO ofcOrderDTO = JSONUtils.jsonToPojo(ofcOrderDTOStr, OfcOrderDTO.class);
            CscContantAndCompanyDto cscContantAndCompanyDtoConsignor = JSONUtils.jsonToPojo(cscContantAndCompanyDtoConsignorStr, CscContantAndCompanyDto.class);
            CscContantAndCompanyDto cscContantAndCompanyDtoConsignee = JSONUtils.jsonToPojo(cscContantAndCompanyDtoConsigneeStr, CscContantAndCompanyDto.class);
            CscSupplierInfoDto cscSupplierInfoDto = JSONUtils.jsonToPojo(cscSupplierInfoDtoStr,CscSupplierInfoDto.class);
            if(null == ofcOrderDTO.getOrderTime()){
                ofcOrderDTO.setOrderTime(new Date());
            }

            if (null == ofcOrderDTO.getProvideTransport()){
                ofcOrderDTO.setProvideTransport(OrderConstEnum.WAREHOUSEORDERNOTPROVIDETRANS);
            }
            if (null == ofcOrderDTO.getUrgent()){
                ofcOrderDTO.setUrgent(OrderConstEnum.DISTRIBUTIONORDERNOTURGENT);
            }
            resultMessage = ofcOrderPlaceService.placeOrder(ofcOrderDTO,ofcGoodsDetailsInfos,tag,authResDtoByToken,custId
                    ,cscContantAndCompanyDtoConsignor,cscContantAndCompanyDtoConsignee,cscSupplierInfoDto);
       }catch (BusinessException ex){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        }
        catch (Exception ex) {
            logger.error("订单中心下单或编辑出现异常:{},{}", ex.getMessage(), ex);
            ex.printStackTrace();
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,resultMessage);
    }

    /**
     * 货品筛选(调用客户中心API)
     */
    @ApiOperation(value="下单货品筛选", notes="根据查询条件筛选货品")
    @ApiImplicitParams({
            //@ApiImplicitParam(name = "cscGoods", value = "货品筛选条件", required = true, dataType = "CscGoods"),
    })
    @RequestMapping(value = "/goodsSelect",method = RequestMethod.POST)
    public void goodsSelectByCscApi(Model model, CscGoods cscGoods, HttpServletResponse response){
        //调用外部接口,最低传CustomerCode
        try{
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            QueryCustomerIdDto queryCustomerIdDto = new QueryCustomerIdDto();
            queryCustomerIdDto.setGroupId(authResDtoByToken.getGroupId());
            Wrapper<?> wrapper = feignCscCustomerAPIClient.queryCustomerIdByGroupId(queryCustomerIdDto);
            String custId = (String) wrapper.getResult();
            cscGoods.setCustomerId(custId);
            Wrapper<List<CscGoodsVo>> cscGoodsLists = feignCscGoodsAPIClient.queryCscGoodsList(cscGoods);
            response.getWriter().print(JSONUtils.objectToJson(cscGoodsLists.getResult()));
        }catch (Exception ex){
            logger.error("订单中心筛选货品出现异常:{},{}", ex.getMessage(), ex);
        }
    }

    /**
     * 收货方发货方筛选(调用客户中心API)
     */
    @ApiOperation(value="下单收发货方筛选", notes="根据查询条件筛选收发货方")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/contactSelect",method = RequestMethod.POST)
    public void contactSelectByCscApi(Model model,  String cscContantAndCompanyDto, HttpServletResponse response){
        //调用外部接口,最低传CustomerCode和purpose
        try {
            CscContantAndCompanyDto csc = JSONUtils.jsonToPojo(cscContantAndCompanyDto, CscContantAndCompanyDto.class);
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            QueryCustomerIdDto queryCustomerIdDto = new QueryCustomerIdDto();
            queryCustomerIdDto.setGroupId(authResDtoByToken.getGroupId());
            Wrapper<?> wrapper = feignCscCustomerAPIClient.queryCustomerIdByGroupId(queryCustomerIdDto);
            String custId = (String) wrapper.getResult();
            csc.setCustomerId(custId);
            csc.setGroupId(authResDtoByToken.getGroupId());
            Wrapper<List<CscContantAndCompanyVo>> cscReceivingInfoList = feignCscCustomerAPIClient.queryCscReceivingInfoList(csc);
            List<CscContantAndCompanyVo> result = cscReceivingInfoList.getResult();
            csc.getCscContact().setPurpose("3");
            Wrapper<List<CscContantAndCompanyVo>> cscReceivingInfoListOfBoth = feignCscCustomerAPIClient.queryCscReceivingInfoList(csc);
            List<CscContantAndCompanyVo> resultOfBoth = cscReceivingInfoListOfBoth.getResult();
            result.addAll(resultOfBoth);
            response.getWriter().print(JSONUtils.objectToJson(result));
        } catch (Exception ex) {
            logger.error("订单中心筛选收货方出现异常:{},{}", ex.getMessage(), ex);
        }
    }

    /**
     * 供应商筛选(调用客户中心API)
     */
    @ApiOperation(value="下单供应商筛选", notes="根据查询条件筛选供应商")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/supplierSelect",method = RequestMethod.POST)
    public void supplierSelectByCscApi(Model model, CscSupplierInfoDto cscSupplierInfoDto, HttpServletResponse response) throws InvocationTargetException{
        //调用外部接口,最低传CustomerCode
        try {
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            QueryCustomerIdDto queryCustomerIdDto = new QueryCustomerIdDto();
            queryCustomerIdDto.setGroupId(authResDtoByToken.getGroupId());
            Wrapper<?> wrapper = feignCscCustomerAPIClient.queryCustomerIdByGroupId(queryCustomerIdDto);
            String custId = (String) wrapper.getResult();
            cscSupplierInfoDto.setCustomerId(custId);//
            Wrapper<List<CscSupplierInfoDto>> cscSupplierList = feignCscSupplierAPIClient.querySupplierByAttribute(cscSupplierInfoDto);
            response.getWriter().print(JSONUtils.objectToJson(cscSupplierList.getResult()));
        }catch (IOException ex) {
            logger.error("订单中心筛选供应商出现异常:{},{}", ex.getMessage(), ex);
        }
    }


}
