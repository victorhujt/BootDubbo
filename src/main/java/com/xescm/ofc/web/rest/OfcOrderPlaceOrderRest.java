package com.xescm.ofc.web.rest;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.enums.BusinessTypeEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.client.FeignCscContactAPIClient;
import com.xescm.ofc.feign.client.FeignCscGoodsAPIClient;
import com.xescm.ofc.feign.client.FeignCscSupplierAPIClient;
import com.xescm.ofc.model.dto.csc.*;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.model.vo.csc.CscCustomerVo;
import com.xescm.ofc.model.vo.csc.CscGoodsApiVo;
import com.xescm.ofc.model.vo.csc.CscGoodsTypeVo;
import com.xescm.ofc.service.OfcDistributionBasicInfoService;
import com.xescm.ofc.service.OfcFundamentalInformationService;
import com.xescm.ofc.service.OfcOrderPlaceService;
import com.xescm.ofc.utils.JSONUtils;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.ofc.wrap.WrapMapper;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.PubUtils;
import com.xescm.uam.utils.wrap.Wrapper;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Autowired
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;
    @Autowired
    private FeignCscGoodsAPIClient feignCscGoodsAPIClient;
    @Autowired
    private FeignCscSupplierAPIClient feignCscSupplierAPIClient;
    @Autowired
    private FeignCscContactAPIClient feignCscContactAPIClient;

    /**
     * 编辑
     * @param
     * @param response
     * @return
     */
    @RequestMapping("/orderEdit")
    @ResponseBody
    public Wrapper<?> orderEdit(Model model, String ofcOrderDTOStr,String orderGoodsListStr,String cscContantAndCompanyDtoConsignorStr
            ,String cscContantAndCompanyDtoConsigneeStr,String cscSupplierInfoDtoStr, String tag, HttpServletResponse response){
        logger.debug("==>订单中心下单或编辑实体 ofcOrderDTOJson={}", ofcOrderDTOStr);
        logger.debug("==>订单中心下单或编辑标志位 tag={}", tag);
        String result = null;
        if(PubUtils.isSEmptyOrNull(ofcOrderDTOStr)){
            logger.debug("订单中心编辑入参实体出现异常ofcOrderDTOJson={}", ofcOrderDTOStr);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"订单中心编辑入参实体出现异常ofcOrderDTOJson");
        }
        try {
            orderGoodsListStr = orderGoodsListStr.replace("~`","");
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
           /* if(PubUtils.isSEmptyOrNull(ofcOrderDTOJson)){
                logger.debug(ofcOrderDTOJson);
                ofcOrderDTOJson = JSONUtils.objectToJson(new OfcOrderDTO());
            }*/
            if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignorStr)){
                cscContantAndCompanyDtoConsignorStr = JSONUtils.objectToJson(new CscContantAndCompanyDto());
            }
            if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsigneeStr)){
                cscContantAndCompanyDtoConsigneeStr = JSONUtils.objectToJson(new CscContantAndCompanyDto());
            }
            if(PubUtils.isSEmptyOrNull(cscSupplierInfoDtoStr)){
                cscSupplierInfoDtoStr = JSONUtils.objectToJson(new CscSupplierInfoDto());
            }
           // List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<OfcGoodsDetailsInfo>();
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<>();
            if(!PubUtils.isSEmptyOrNull(orderGoodsListStr)){ // 如果货品不空才去添加
                //orderGoodsListStr = JSONUtils.objectToJson(new OfcGoodsDetailsInfo());
                ofcGoodsDetailsInfos = JSONObject.parseArray(orderGoodsListStr, OfcGoodsDetailsInfo.class);
            }
            OfcOrderDTO ofcOrderDTO = JSONUtils.jsonToPojo(ofcOrderDTOStr, OfcOrderDTO.class);
            CscContantAndCompanyDto cscContantAndCompanyDtoConsignor = JSONUtils.jsonToPojo(cscContantAndCompanyDtoConsignorStr, CscContantAndCompanyDto.class);
            CscContantAndCompanyDto cscContantAndCompanyDtoConsignee = JSONUtils.jsonToPojo(cscContantAndCompanyDtoConsigneeStr, CscContantAndCompanyDto.class);
            CscSupplierInfoDto cscSupplierInfoDto = JSONUtils.jsonToPojo(cscSupplierInfoDtoStr,CscSupplierInfoDto.class);
            if (null == ofcOrderDTO.getOrderTime()){
                ofcOrderDTO.setOrderTime(new Date());
            }
            if (null == ofcOrderDTO.getProvideTransport()){
                ofcOrderDTO.setProvideTransport(OrderConstConstant.WAREHOUSEORDERNOTPROVIDETRANS);
            }
            if (null == ofcOrderDTO.getUrgent()){
                ofcOrderDTO.setUrgent(OrderConstConstant.DISTRIBUTIONORDERNOTURGENT);
            }

            result =  ofcOrderPlaceService.placeOrder(ofcOrderDTO,ofcGoodsDetailsInfos,tag,authResDtoByToken,authResDtoByToken.getGroupRefCode()
                    ,cscContantAndCompanyDtoConsignor,cscContantAndCompanyDtoConsignee,cscSupplierInfoDto);
        } catch (BusinessException ex) {
            logger.error("订单中心编辑出现异常:{},{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        } catch (Exception ex) {
            logger.error("订单中心编辑出现异常:{},{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,result);
    }

    /**
     *
     * @param model
     * @param ofcOrderDTOStr        订单基本信息、收发货方信息
     * @param orderGoodsListStr     货品信息
     * @param cscContantAndCompanyDtoConsignorStr   发货人信息
     * @param cscContantAndCompanyDtoConsigneeStr   收货人信息
     * @param cscSupplierInfoDtoStr                 供应商信息
     * @param tag           标识下单、编辑、运输开单
     * @param response
     * @return
     */
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

           // List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<OfcGoodsDetailsInfo>();
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<>();
            if(!PubUtils.isSEmptyOrNull(orderGoodsListStr)){ // 如果货品不空才去添加
                //orderGoodsListStr = JSONUtils.objectToJson(new OfcGoodsDetailsInfo());
                ofcGoodsDetailsInfos = JSONObject.parseArray(orderGoodsListStr, OfcGoodsDetailsInfo.class);
            }
            OfcOrderDTO ofcOrderDTO = JSONUtils.jsonToPojo(ofcOrderDTOStr, OfcOrderDTO.class);
            logger.info(cscContantAndCompanyDtoConsignorStr);
            CscContantAndCompanyDto cscContantAndCompanyDtoConsignor = JSONUtils.jsonToPojo(cscContantAndCompanyDtoConsignorStr, CscContantAndCompanyDto.class);
            logger.info(cscContantAndCompanyDtoConsigneeStr);
            CscContantAndCompanyDto cscContantAndCompanyDtoConsignee = JSONUtils.jsonToPojo(cscContantAndCompanyDtoConsigneeStr, CscContantAndCompanyDto.class);
            if(cscContantAndCompanyDtoConsignor==null){
                throw new BusinessException("发货人信息不允许为空！");
            }
            if(cscContantAndCompanyDtoConsignee==null){
                throw new BusinessException("收货人信息不允许为空！");
            }

            CscSupplierInfoDto cscSupplierInfoDto = JSONUtils.jsonToPojo(cscSupplierInfoDtoStr,CscSupplierInfoDto.class);
            //校验业务类型，如果是卡班，必须要有运输单号
            if(StringUtils.equals(ofcOrderDTO.getBusinessType(), BusinessTypeEnum.CABANNES.getCode())){
                if(StringUtils.isBlank(ofcOrderDTO.getTransCode())){
                    throw new Exception("业务类型是卡班，运输单号是必填项");
                }
            }
            if(null !=ofcOrderDTO){
                if (null == ofcOrderDTO.getProvideTransport()){
                    ofcOrderDTO.setProvideTransport(OrderConstConstant.WAREHOUSEORDERNOTPROVIDETRANS);
                }
                if (null == ofcOrderDTO.getUrgent()){
                    ofcOrderDTO.setUrgent(OrderConstConstant.DISTRIBUTIONORDERNOTURGENT);
                }
            }else{
                return WrapMapper.wrap(Wrapper.ERROR_CODE,"订单相关信息有误");
            }
            resultMessage = ofcOrderPlaceService.placeOrder(ofcOrderDTO,ofcGoodsDetailsInfos,tag,authResDtoByToken,authResDtoByToken.getGroupRefCode()
                    ,cscContantAndCompanyDtoConsignor,cscContantAndCompanyDtoConsignee,cscSupplierInfoDto);
        } catch (BusinessException ex){
            logger.error("订单中心下单或编辑出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        } catch (Exception ex) {
            if (ex.getCause().getMessage().trim().startsWith("Duplicate entry")) {
                logger.error("订单中心下单或编辑出现异常:{}", "获取订单号发生重复!", ex);
                return WrapMapper.wrap(Wrapper.ERROR_CODE, "获取订单号发生重复!");
            } else {
                logger.error("订单中心下单或编辑出现未知异常:{}", ex.getMessage(), ex);
                return WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
            }
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,resultMessage);
    }




    /**
     *
     * @param model
     * @param ofcOrderDTOStr        订单基本信息、收发货方信息
     * @param orderGoodsListStr     货品信息
     * @param cscContantAndCompanyDtoConsignorStr   发货人信息
     * @param cscContantAndCompanyDtoConsigneeStr   收货人信息
     * @param cscSupplierInfoDtoStr                 供应商信息
     * @param tag           标识下单、编辑、运输开单
     * @param response
     * @return
     */
    @RequestMapping("/MobileorderPlaceCon")
    @ResponseBody
    public Wrapper<?> mobileOrderPlace(Model model, String ofcOrderDTOStr,String orderGoodsListStr,String cscContantAndCompanyDtoConsignorStr
            ,String cscContantAndCompanyDtoConsigneeStr,String cscSupplierInfoDtoStr, String tag, HttpServletResponse response){
        logger.debug("==>订单中心下单或编辑实体 ofcOrderDTOStr={}", ofcOrderDTOStr);
        logger.debug("==>订单中心下单或编辑标志位 tag={}", tag);
        String resultMessage = null;
        try {
            orderGoodsListStr = orderGoodsListStr.replace("~`","");
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
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
            // List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<OfcGoodsDetailsInfo>();
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<>();
            if(!PubUtils.isSEmptyOrNull(orderGoodsListStr)){ // 如果货品不空才去添加
                //orderGoodsListStr = JSONUtils.objectToJson(new OfcGoodsDetailsInfo());
                ofcGoodsDetailsInfos = JSONObject.parseArray(orderGoodsListStr, OfcGoodsDetailsInfo.class);
            }
            OfcOrderDTO ofcOrderDTO = JSONUtils.jsonToPojo(ofcOrderDTOStr, OfcOrderDTO.class);
            logger.info(cscContantAndCompanyDtoConsignorStr);
            CscContantAndCompanyDto cscContantAndCompanyDtoConsignor = JSONUtils.jsonToPojo(cscContantAndCompanyDtoConsignorStr, CscContantAndCompanyDto.class);
            logger.info(cscContantAndCompanyDtoConsigneeStr);
            CscContantAndCompanyDto cscContantAndCompanyDtoConsignee = JSONUtils.jsonToPojo(cscContantAndCompanyDtoConsigneeStr, CscContantAndCompanyDto.class);
            if(cscContantAndCompanyDtoConsignor==null){
                throw new BusinessException("发货人信息不允许为空！");
            }
            if(cscContantAndCompanyDtoConsignee==null){
                throw new BusinessException("收货人信息不允许为空！");
            }
            CscSupplierInfoDto cscSupplierInfoDto = JSONUtils.jsonToPojo(cscSupplierInfoDtoStr,CscSupplierInfoDto.class);
            //校验业务类型，如果是卡班，必须要有运输单号
            if(StringUtils.equals(ofcOrderDTO.getBusinessType(), BusinessTypeEnum.CABANNES.getCode())){
                if(StringUtils.isBlank(ofcOrderDTO.getTransCode())){
                    throw new Exception("业务类型是卡班，运输单号是必填项");
                }
            }
            if(null !=ofcOrderDTO){
                if (null == ofcOrderDTO.getProvideTransport()){
                    ofcOrderDTO.setProvideTransport(OrderConstConstant.WAREHOUSEORDERNOTPROVIDETRANS);
                }
                if (null == ofcOrderDTO.getUrgent()){
                    ofcOrderDTO.setUrgent(OrderConstConstant.DISTRIBUTIONORDERNOTURGENT);
                }
            }else{
                return WrapMapper.wrap(Wrapper.ERROR_CODE,"订单相关信息有误");
            }
            resultMessage = ofcOrderPlaceService.placeOrder(ofcOrderDTO,ofcGoodsDetailsInfos,tag,authResDtoByToken,authResDtoByToken.getGroupRefCode()
                    ,cscContantAndCompanyDtoConsignor,cscContantAndCompanyDtoConsignee,cscSupplierInfoDto);
        } catch (BusinessException ex){
            logger.error("订单中心下单或编辑出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        } catch (Exception ex) {
            if (ex.getCause().getMessage().trim().startsWith("Duplicate entry")) {
                logger.error("订单中心下单或编辑出现异常:{}", "获取订单号发生重复!", ex);
                return WrapMapper.wrap(Wrapper.ERROR_CODE, "获取订单号发生重复!");
            } else {
                logger.error("订单中心下单或编辑出现未知异常:{}", ex.getMessage(), ex);
                return WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
            }
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
    public void goodsSelectByCscApi(Model model, CscGoodsApiDto cscGoods, HttpServletResponse response){
        logger.debug("==>下单货品筛选,cscGoods = {}",cscGoods);
        //调用外部接口,最低传CustomerCode
        try{
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            cscGoods.setCustomerCode(authResDtoByToken.getGroupRefCode());
            cscGoods.setGoodsCode(PubUtils.trimAndNullAsEmpty(cscGoods.getGoodsCode()));
            cscGoods.setGoodsName(PubUtils.trimAndNullAsEmpty(cscGoods.getGoodsName()));
            Wrapper<List<CscGoodsApiVo>> cscGoodsLists = feignCscGoodsAPIClient.queryCscGoodsList(cscGoods);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(JSONUtils.objectToJson(cscGoodsLists.getResult()));
        }catch (Exception ex){
            logger.error("订单中心筛选货品出现异常:{}", ex.getMessage(), ex);
        }
    }

    /**
     * 运营中心货品筛选(调用客户中心API)
     */
    @RequestMapping(value = "/goodsSelects",method = RequestMethod.POST)
    @ResponseBody
    public Object goodsSelectByCsc(String  cscGoods,String customerCode, HttpServletResponse response){
        //调用外部接口,最低传CustomerCode
        logger.debug("==>下单货品筛选,cscGoods = {}",cscGoods);
        logger.debug("==>下单货品筛选,customerCode = {}",customerCode);
        Wrapper<PageInfo<CscGoodsApiVo>> cscGoodsLists=null;
        try{

            CscGoodsApiDto cscGood=new CscGoodsApiDto();
            if(!PubUtils.trimAndNullAsEmpty(cscGoods).equals("")){
                cscGood= JSONObject.parseObject(cscGoods, CscGoodsApiDto.class);
            }
            /*if(cscGood!=null){
                PageHelper.startPage(cscGood.getPNum(), cscGood.getPSize());
            }*/
            cscGood.setCustomerCode(customerCode);
            cscGood.setGoodsCode(PubUtils.trimAndNullAsEmpty(cscGood.getGoodsCode()));
            cscGood.setGoodsName(PubUtils.trimAndNullAsEmpty(cscGood.getGoodsName()));
            cscGoodsLists = feignCscGoodsAPIClient.queryCscGoodsPageList(cscGood);
            //response.getWriter().print(JSONUtils.objectToJson(cscGoodsLists.getResult()));
        }catch (Exception ex){
            logger.error("订单中心筛选货品出现异常:{}", ex.getMessage(), ex);
        }
        return cscGoodsLists;
    }


    @ApiOperation(value="下单收发货方筛选", notes="根据查询条件筛选收发货方")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/contactSelect",method = RequestMethod.POST)
    public void contactSelectByCscApi(Model model,  String cscContantAndCompanyDto, String customerCode, HttpServletResponse response){
        logger.debug("==>下单收发货方筛选,cscContantAndCompanyDto = {}",cscContantAndCompanyDto);
        logger.debug("==>下单收发货方筛选,customerCode = {}",customerCode);
        //调用外部接口,最低传CustomerCode和purpose
        try {
            CscContantAndCompanyDto csc = JSONUtils.jsonToPojo(cscContantAndCompanyDto, CscContantAndCompanyDto.class);
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            if(PubUtils.isSEmptyOrNull(customerCode)){
                customerCode = authResDtoByToken.getGroupRefCode();
            }
            csc.setCustomerCode(customerCode);
            csc.getCscContactCompany().setContactCompanyName(PubUtils.trimAndNullAsEmpty(csc.getCscContactCompany().getContactCompanyName()));
            csc.getCscContact().setContactName(PubUtils.trimAndNullAsEmpty(csc.getCscContact().getContactName()));
            csc.getCscContact().setPhone(PubUtils.trimAndNullAsEmpty(csc.getCscContact().getPhone()));
            Wrapper<List<CscContantAndCompanyResponseDto>> cscReceivingInfoList = feignCscContactAPIClient.queryCscReceivingInfoList(csc);
            List<CscContantAndCompanyResponseDto> result = cscReceivingInfoList.getResult();

            /*csc.getCscContact().setPurpose("3");
            Wrapper<List<CscContantAndCompanyVo>> cscReceivingInfoListOfBoth = feignCscCustomerAPIClient.queryCscReceivingInfoList(csc);
            List<CscContantAndCompanyVo> resultOfBoth = cscReceivingInfoListOfBoth.getResult();
            result.addAll(resultOfBoth);*/
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(JSONUtils.objectToJson(result));
        } catch (Exception ex) {
            logger.error("订单中心筛选收发货方出现异常:{}", ex.getMessage(), ex);
        }
    }

    @RequestMapping(value = "/contactSelectForPage",method = RequestMethod.POST)
    @ResponseBody
    public Object contactSelectByPage(String cscContantAndCompanyDto, String customerCode){
        logger.debug("==>下单收发货方筛选,cscContantAndCompanyDto = {}",cscContantAndCompanyDto);
        logger.debug("==>下单收发货方筛选,customerCode = {}",customerCode);
        //调用外部接口,最低传CustomerCode和purpose
        Wrapper<PageInfo<CscContantAndCompanyResponseDto>> result=null;
        try {
            CscContantAndCompanyDto csc = JSONUtils.jsonToPojo(cscContantAndCompanyDto, CscContantAndCompanyDto.class);
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            if(PubUtils.isSEmptyOrNull(customerCode)){
                customerCode = authResDtoByToken.getGroupRefCode();
            }
            csc.setCustomerCode(customerCode);
            csc.getCscContactCompany().setContactCompanyName(PubUtils.trimAndNullAsEmpty(csc.getCscContactCompany().getContactCompanyName()));
            csc.getCscContact().setContactName(PubUtils.trimAndNullAsEmpty(csc.getCscContact().getContactName()));
            csc.getCscContact().setPhone(PubUtils.trimAndNullAsEmpty(csc.getCscContact().getPhone()));
            Wrapper<PageInfo<CscContantAndCompanyResponseDto>> cscReceivingInfoList = feignCscContactAPIClient.queryCscReceivingInfoListWithPage(csc);
            result = cscReceivingInfoList;
            /*
            csc.getCscContact().setPurpose("3");
            Wrapper<List<CscContantAndCompanyVo>> cscReceivingInfoListOfBoth = feignCscCustomerAPIClient.queryCscReceivingInfoList(csc);
            List<CscContantAndCompanyVo> resultOfBoth = cscReceivingInfoListOfBoth.getResult();
            result.addAll(resultOfBoth);*/
        } catch (Exception ex) {
            logger.error("订单中心筛选收发货方出现异常:{}", ex.getMessage(), ex);
        }
        return result;
    }


    /**
     * 供应商筛选(调用客户中心API)
     */
    @ApiOperation(value="下单供应商筛选", notes="根据查询条件筛选供应商")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/supplierSelect",method = RequestMethod.POST)
    public void supplierSelectByCscApi(Model model, CscSupplierInfoDto cscSupplierInfoDto, HttpServletResponse response) throws InvocationTargetException{
        logger.debug("==>下单供应商筛选,cscSupplierInfoDto = {}",cscSupplierInfoDto);
        //调用外部接口,最低传CustomerCode
        try {
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            cscSupplierInfoDto.setCustomerCode(authResDtoByToken.getGroupRefCode());
            cscSupplierInfoDto.setSupplierName(PubUtils.trimAndNullAsEmpty(cscSupplierInfoDto.getSupplierName()));
            cscSupplierInfoDto.setContactName(PubUtils.trimAndNullAsEmpty(cscSupplierInfoDto.getContactName()));
            cscSupplierInfoDto.setContactPhone(PubUtils.trimAndNullAsEmpty(cscSupplierInfoDto.getContactPhone()));
            Wrapper<List<CscSupplierInfoDto>> cscSupplierList = feignCscSupplierAPIClient.querySupplierByAttribute(cscSupplierInfoDto);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(JSONUtils.objectToJson(cscSupplierList.getResult()));
        }catch (IOException ex) {
            logger.error("订单中心筛选供应商出现异常:{}", ex.getMessage(), ex);
        }
    }
        /*
        校验客户订单编号
         */
    @RequestMapping(value = "/checkCustOrderCode",method = RequestMethod.POST)
    @ResponseBody
    public boolean checkCustOrderCode(Model model, String custOrderCode, String selfCustOrderCode){
        logger.info("校验客户订单编号==> custOrderCode={}", custOrderCode);
        logger.info("校验客户订单编号==> selfCustOrderCode={}", selfCustOrderCode);

        OfcFundamentalInformation ofcFundamentalInformation = new OfcFundamentalInformation();
        ofcFundamentalInformation.setCustOrderCode(custOrderCode);
        ofcFundamentalInformation.setSelfCustOrderCode(selfCustOrderCode);
        boolean flag = false;
        try {
            int count = ofcFundamentalInformationService.checkCustOrderCode(ofcFundamentalInformation);
            if (count < 1){
                flag = true;
            }

        } catch (Exception e) {
            logger.error("校验客户订单编号出错:　{}", e.getMessage(),e);
        }
        return flag;
    }

        /*
    校验运输单号
     */
    @RequestMapping(value = "/checkTransCode",method = RequestMethod.POST)
    @ResponseBody
    public boolean checkTransCode(Model model, String transCode, String selfTransCode){
        logger.info("校验运输单号==> custOrderCode={}", transCode);
        logger.info("校验运输单号==> selfCustOrderCode={}", selfTransCode);

        OfcDistributionBasicInfo ofcDistributionBasicInfo=new OfcDistributionBasicInfo();
        ofcDistributionBasicInfo.setTransCode(transCode);
        ofcDistributionBasicInfo.setSelfTransCode(selfTransCode);
        boolean flag = false;
        try {
            int count = ofcDistributionBasicInfoService.checkTransCode(ofcDistributionBasicInfo);
            if (count < 1){
                flag = true;
            }

        } catch (Exception e) {
            logger.error("校验运输单号出错:{}　", e.getMessage(),e);
        }
        return flag;
    }

    /**
     * 货品筛选(调用客户中心API)
     */
    @ApiOperation(value="下单货品筛选", notes="根据查询条件筛选货品")
    @ApiImplicitParams({
            //@ApiImplicitParam(name = "cscGoods", value = "货品筛选条件", required = true, dataType = "CscGoods"),
    })
    @RequestMapping(value = "/getCscGoodsTypeList",method = RequestMethod.POST)
    public void getCscGoodsTypeList(Model model,String cscGoodsType, HttpServletResponse response){
        logger.info("下单货品筛选==> cscGoodsType={}", cscGoodsType);
        //调用外部接口,最低传CustomerCode
        try{
            CscGoodsType cscGoodType=new CscGoodsType();
            if(!PubUtils.trimAndNullAsEmpty(cscGoodsType).equals("")){
                cscGoodType.setPid(cscGoodsType);
            }
            Wrapper<List<CscGoodsTypeVo>> CscGoodsType = feignCscGoodsAPIClient.getCscGoodsTypeList(cscGoodType);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(JSONUtils.objectToJson(CscGoodsType.getResult()));
            logger.info("###############返回货品类别列表为{}####################",JSONUtils.objectToJson(CscGoodsType.getResult()));
            CscGoodsTypeVo cscGoodsTypeVo=new CscGoodsTypeVo();
        }catch (Exception ex){
            logger.error("订单中心筛选货品出现异常:{}", ex.getMessage(), ex);
        }
    }
}
