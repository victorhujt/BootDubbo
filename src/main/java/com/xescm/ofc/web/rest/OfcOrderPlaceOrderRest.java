package com.xescm.ofc.web.rest;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.CscGoodsApiDto;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyResponseDto;
import com.xescm.csc.model.dto.goodstype.CscGoodsTypeDto;
import com.xescm.csc.model.vo.CscGoodsApiVo;
import com.xescm.csc.model.vo.CscGoodsTypeVo;
import com.xescm.csc.provider.CscContactEdasService;
import com.xescm.csc.provider.CscGoodsEdasService;
import com.xescm.csc.provider.CscGoodsTypeEdasService;
import com.xescm.csc.provider.CscSupplierEdasService;
import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.enums.BusinessTypeEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.service.OfcDistributionBasicInfoService;
import com.xescm.ofc.service.OfcFundamentalInformationService;
import com.xescm.ofc.service.OfcOrderPlaceService;
import com.xescm.ofc.web.controller.BaseController;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
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
    private CscGoodsEdasService cscGoodsEdasService;
    @Autowired
    private CscGoodsTypeEdasService cscGoodsTypeEdasService;
    @Autowired
    private CscSupplierEdasService cscSupplierEdasService;
    @Autowired
    private CscContactEdasService cscContactEdasService;

    /**
     * 编辑
     * @param ofcOrderDTOStr
     * @param orderGoodsListStr
     * @param cscContantAndCompanyDtoConsignorStr
     * @param cscContantAndCompanyDtoConsigneeStr
     * @param cscSupplierInfoDtoStr
     * @param tag
     * @return
     */
    @RequestMapping("/orderEdit")
    @ResponseBody
    public Wrapper<?> orderEdit(String ofcOrderDTOStr, String orderGoodsListStr, String cscContantAndCompanyDtoConsignorStr
            , String cscContantAndCompanyDtoConsigneeStr, String cscSupplierInfoDtoStr, String tag){
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
                ofcOrderDTOJson = JacksonUtil.toJsonWithFormat(new OfcOrderDTO());
            }*/
            if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignorStr)){
                cscContantAndCompanyDtoConsignorStr = JacksonUtil.toJsonWithFormat(new CscContantAndCompanyDto());
            }
            if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsigneeStr)){
                cscContantAndCompanyDtoConsigneeStr = JacksonUtil.toJsonWithFormat(new CscContantAndCompanyDto());
            }
            if(PubUtils.isSEmptyOrNull(cscSupplierInfoDtoStr)){
                cscSupplierInfoDtoStr = JacksonUtil.toJsonWithFormat(new CscSupplierInfoDto());
            }
           // List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<OfcGoodsDetailsInfo>();
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<>();
            if(!PubUtils.isSEmptyOrNull(orderGoodsListStr)){ // 如果货品不空才去添加
                //orderGoodsListStr = JacksonUtil.toJsonWithFormat(new OfcGoodsDetailsInfo());
                ofcGoodsDetailsInfos = JSONObject.parseArray(orderGoodsListStr, OfcGoodsDetailsInfo.class);
            }
            OfcOrderDTO ofcOrderDTO = JacksonUtil.parseJsonWithFormat(ofcOrderDTOStr, OfcOrderDTO.class);
            CscContantAndCompanyDto cscContantAndCompanyDtoConsignor = JacksonUtil.parseJsonWithFormat(cscContantAndCompanyDtoConsignorStr, CscContantAndCompanyDto.class);
            CscContantAndCompanyDto cscContantAndCompanyDtoConsignee = JacksonUtil.parseJsonWithFormat(cscContantAndCompanyDtoConsigneeStr, CscContantAndCompanyDto.class);
            CscSupplierInfoDto cscSupplierInfoDto = JacksonUtil.parseJsonWithFormat(cscSupplierInfoDtoStr,CscSupplierInfoDto.class);
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
    public Wrapper<?> orderPlace(String ofcOrderDTOStr,String orderGoodsListStr,String cscContantAndCompanyDtoConsignorStr
            ,String cscContantAndCompanyDtoConsigneeStr,String cscSupplierInfoDtoStr, String tag, HttpServletResponse response){
        logger.debug("==>订单中心下单或编辑实体 ofcOrderDTOStr={}", ofcOrderDTOStr);
        logger.debug("==>订单中心下单或编辑标志位 tag={}", tag);
        String resultMessage;
        try {
            orderGoodsListStr = orderGoodsListStr.replace("~`","");
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            if(PubUtils.isSEmptyOrNull(ofcOrderDTOStr)){
                ofcOrderDTOStr = JacksonUtil.toJsonWithFormat(new OfcOrderDTO());
            }
            if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignorStr)){
                cscContantAndCompanyDtoConsignorStr = JacksonUtil.toJsonWithFormat(new CscContantAndCompanyDto());
            }
            if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsigneeStr)){
                cscContantAndCompanyDtoConsigneeStr = JacksonUtil.toJsonWithFormat(new CscContantAndCompanyDto());
            }
            if(PubUtils.isSEmptyOrNull(cscSupplierInfoDtoStr)){
                cscSupplierInfoDtoStr = JacksonUtil.toJsonWithFormat(new CscSupplierInfoDto());
            }

           // List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<OfcGoodsDetailsInfo>();
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<>();
            if(!PubUtils.isSEmptyOrNull(orderGoodsListStr)){ // 如果货品不空才去添加
                //orderGoodsListStr = JacksonUtil.toJsonWithFormat(new OfcGoodsDetailsInfo());
                ofcGoodsDetailsInfos = JSONObject.parseArray(orderGoodsListStr, OfcGoodsDetailsInfo.class);
            }
            OfcOrderDTO ofcOrderDTO = JacksonUtil.parseJsonWithFormat(ofcOrderDTOStr, OfcOrderDTO.class);
            logger.info(cscContantAndCompanyDtoConsignorStr);
            CscContantAndCompanyDto cscContantAndCompanyDtoConsignor = JacksonUtil.parseJsonWithFormat(cscContantAndCompanyDtoConsignorStr, CscContantAndCompanyDto.class);
            logger.info(cscContantAndCompanyDtoConsigneeStr);
            CscContantAndCompanyDto cscContantAndCompanyDtoConsignee = JacksonUtil.parseJsonWithFormat(cscContantAndCompanyDtoConsigneeStr, CscContantAndCompanyDto.class);
            if(cscContantAndCompanyDtoConsignor==null){
                throw new BusinessException("发货人信息不允许为空！");
            }
            if(cscContantAndCompanyDtoConsignee==null){
                throw new BusinessException("收货人信息不允许为空！");
            }

            CscSupplierInfoDto cscSupplierInfoDto = JacksonUtil.parseJsonWithFormat(cscSupplierInfoDtoStr,CscSupplierInfoDto.class);
            //校验业务类型，如果是卡班，必须要有运输单号
            if(StringUtils.equals(ofcOrderDTO.getBusinessType(), BusinessTypeEnum.CABANNES.getCode())){
                if(StringUtils.isBlank(ofcOrderDTO.getTransCode())){
                    throw new Exception("业务类型是卡班，运输单号是必填项");
                }
            }

            if (null == ofcOrderDTO.getProvideTransport()){
                ofcOrderDTO.setProvideTransport(OrderConstConstant.WAREHOUSEORDERNOTPROVIDETRANS);
            }
            if (null == ofcOrderDTO.getUrgent()){
                ofcOrderDTO.setUrgent(OrderConstConstant.DISTRIBUTIONORDERNOTURGENT);
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
     * @param ofcOrderDTOStr        订单基本信息、收发货方信息
     * @param orderGoodsListStr     货品信息
     * @param cscContantAndCompanyDtoConsignorStr   发货人信息
     * @param cscContantAndCompanyDtoConsigneeStr   收货人信息
     * @param cscSupplierInfoDtoStr                 供应商信息
     * @param tag           标识下单、编辑、运输开单
     * @return
     */
    @RequestMapping("/MobileorderPlaceCon")
    @ResponseBody
    public Wrapper<?> mobileOrderPlace(String ofcOrderDTOStr,String orderGoodsListStr,String cscContantAndCompanyDtoConsignorStr
            ,String cscContantAndCompanyDtoConsigneeStr,String cscSupplierInfoDtoStr, String tag){
        logger.debug("==>订单中心下单或编辑实体 ofcOrderDTOStr={}", ofcOrderDTOStr);
        logger.debug("==>订单中心下单或编辑标志位 tag={}", tag);
        String resultMessage = null;
        try {
            orderGoodsListStr = orderGoodsListStr.replace("~`","");
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            if(PubUtils.isSEmptyOrNull(ofcOrderDTOStr)){
                ofcOrderDTOStr = JacksonUtil.toJsonWithFormat(new OfcOrderDTO());
            }
            if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignorStr)){
                cscContantAndCompanyDtoConsignorStr = JacksonUtil.toJsonWithFormat(new CscContantAndCompanyDto());
            }
            if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsigneeStr)){
                cscContantAndCompanyDtoConsigneeStr = JacksonUtil.toJsonWithFormat(new CscContantAndCompanyDto());
            }
            if(PubUtils.isSEmptyOrNull(cscSupplierInfoDtoStr)){
                cscSupplierInfoDtoStr = JacksonUtil.toJsonWithFormat(new CscSupplierInfoDto());
            }
            // List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<OfcGoodsDetailsInfo>();
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<>();
            if(!PubUtils.isSEmptyOrNull(orderGoodsListStr)){ // 如果货品不空才去添加
                //orderGoodsListStr = JacksonUtil.toJsonWithFormat(new OfcGoodsDetailsInfo());
                ofcGoodsDetailsInfos = JSONObject.parseArray(orderGoodsListStr, OfcGoodsDetailsInfo.class);
            }
            OfcOrderDTO ofcOrderDTO = JacksonUtil.parseJsonWithFormat(ofcOrderDTOStr, OfcOrderDTO.class);
            logger.info(cscContantAndCompanyDtoConsignorStr);
            CscContantAndCompanyDto cscContantAndCompanyDtoConsignor = JacksonUtil.parseJsonWithFormat(cscContantAndCompanyDtoConsignorStr, CscContantAndCompanyDto.class);
            logger.info(cscContantAndCompanyDtoConsigneeStr);
            CscContantAndCompanyDto cscContantAndCompanyDtoConsignee = JacksonUtil.parseJsonWithFormat(cscContantAndCompanyDtoConsigneeStr, CscContantAndCompanyDto.class);
            if(cscContantAndCompanyDtoConsignor==null){
                throw new BusinessException("发货人信息不允许为空！");
            }
            if(cscContantAndCompanyDtoConsignee==null){
                throw new BusinessException("收货人信息不允许为空！");
            }
            CscSupplierInfoDto cscSupplierInfoDto = JacksonUtil.parseJsonWithFormat(cscSupplierInfoDtoStr,CscSupplierInfoDto.class);
            //校验业务类型，如果是卡班，必须要有运输单号
            if(StringUtils.equals(ofcOrderDTO.getBusinessType(), BusinessTypeEnum.CABANNES.getCode())){
                if(StringUtils.isBlank(ofcOrderDTO.getTransCode())){
                    throw new Exception("业务类型是卡班，运输单号是必填项");
                }
            }

            if (null == ofcOrderDTO.getProvideTransport()){
                ofcOrderDTO.setProvideTransport(OrderConstConstant.WAREHOUSEORDERNOTPROVIDETRANS);
            }
            if (null == ofcOrderDTO.getUrgent()){
                ofcOrderDTO.setUrgent(OrderConstConstant.DISTRIBUTIONORDERNOTURGENT);
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
    public void goodsSelectByCscApi(CscGoodsApiDto cscGoods, HttpServletResponse response){
        logger.debug("==>下单货品筛选,cscGoods = {}",cscGoods);
        //调用外部接口,最低传CustomerCode
        try{
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            cscGoods.setCustomerCode(authResDtoByToken.getGroupRefCode());
            cscGoods.setGoodsCode(PubUtils.trimAndNullAsEmpty(cscGoods.getGoodsCode()));
            cscGoods.setGoodsName(PubUtils.trimAndNullAsEmpty(cscGoods.getGoodsName()));
            Wrapper<List<CscGoodsApiVo>> cscGoodsLists = cscGoodsEdasService.queryCscGoodsList(cscGoods);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(JacksonUtil.toJsonWithFormat(cscGoodsLists.getResult()));
        }catch (Exception ex){
            logger.error("订单中心筛选货品出现异常:{}", ex.getMessage(), ex);
        }
    }

    /**
     * 运营中心货品筛选(调用客户中心API)
     */
    @RequestMapping(value = "/goodsSelects",method = RequestMethod.POST)
    @ResponseBody
    public Object goodsSelectByCsc(String  cscGoods,String customerCode){
        //调用外部接口,最低传CustomerCode
        logger.debug("==>下单货品筛选,cscGoods = {}",cscGoods);
        logger.debug("==>下单货品筛选,customerCode = {}",customerCode);
        Wrapper<PageInfo<CscGoodsApiVo>> cscGoodsLists=null;
        try{

            CscGoodsApiDto cscGood=new CscGoodsApiDto();
            if(!PubUtils.trimAndNullAsEmpty(cscGoods).equals("")){
                cscGood= JSONObject.parseObject(cscGoods, CscGoodsApiDto.class);
            }

            cscGood.setCustomerCode(customerCode);
            cscGood.setGoodsCode(PubUtils.trimAndNullAsEmpty(cscGood.getGoodsCode()));
            cscGood.setGoodsName(PubUtils.trimAndNullAsEmpty(cscGood.getGoodsName()));
            cscGoodsLists = cscGoodsEdasService.queryCscGoodsPageList(cscGood);
            //response.getWriter().print(JacksonUtil.toJsonWithFormat(cscGoodsLists.getResult()));
        }catch (Exception ex){
            logger.error("订单中心筛选货品出现异常:{}", ex.getMessage(), ex);
        }
        return cscGoodsLists;
    }


    @ApiOperation(value="下单收发货方筛选", notes="根据查询条件筛选收发货方")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/contactSelect",method = RequestMethod.POST)
    public void contactSelectByCscApi(  String cscContantAndCompanyDto, String customerCode, HttpServletResponse response){
        logger.debug("==>下单收发货方筛选,cscContantAndCompanyDto = {}",cscContantAndCompanyDto);
        logger.debug("==>下单收发货方筛选,customerCode = {}",customerCode);
        //调用外部接口,最低传CustomerCode和purpose
        try {
            CscContantAndCompanyDto csc = JacksonUtil.parseJsonWithFormat(cscContantAndCompanyDto, CscContantAndCompanyDto.class);
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            if(PubUtils.isSEmptyOrNull(customerCode)){
                customerCode = authResDtoByToken.getGroupRefCode();
            }
            csc.setCustomerCode(customerCode);
            csc.getCscContactCompanyDto().setContactCompanyName(PubUtils.trimAndNullAsEmpty(csc.getCscContactCompanyDto().getContactCompanyName()));
            csc.getCscContactDto().setContactName(PubUtils.trimAndNullAsEmpty(csc.getCscContactDto().getContactName()));
            csc.getCscContactDto().setPhone(PubUtils.trimAndNullAsEmpty(csc.getCscContactDto().getPhone()));
            Wrapper<List<CscContantAndCompanyResponseDto>> cscReceivingInfoList = cscContactEdasService.queryCscReceivingInfoList(csc);
            List<CscContantAndCompanyResponseDto> result = cscReceivingInfoList.getResult();

            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(JacksonUtil.toJsonWithFormat(result));
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
            CscContantAndCompanyDto csc = JacksonUtil.parseJsonWithFormat(cscContantAndCompanyDto, CscContantAndCompanyDto.class);
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            if(PubUtils.isSEmptyOrNull(customerCode)){
                customerCode = authResDtoByToken.getGroupRefCode();
            }
            csc.setCustomerCode(customerCode);
            csc.getCscContactCompanyDto().setContactCompanyName(PubUtils.trimAndNullAsEmpty(csc.getCscContactCompanyDto().getContactCompanyName()));
            csc.getCscContactDto().setContactName(PubUtils.trimAndNullAsEmpty(csc.getCscContactDto().getContactName()));
            csc.getCscContactDto().setPhone(PubUtils.trimAndNullAsEmpty(csc.getCscContactDto().getPhone()));
            Wrapper<PageInfo<CscContantAndCompanyResponseDto>> cscReceivingInfoList = cscContactEdasService.queryCscReceivingInfoListWithPage(csc);
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
    public void supplierSelectByCscApi( CscSupplierInfoDto cscSupplierInfoDto, HttpServletResponse response) throws InvocationTargetException{
        logger.debug("==>下单供应商筛选,cscSupplierInfoDto = {}",cscSupplierInfoDto);
        //调用外部接口,最低传CustomerCode
        try {
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            cscSupplierInfoDto.setCustomerCode(authResDtoByToken.getGroupRefCode());
            cscSupplierInfoDto.setSupplierName(PubUtils.trimAndNullAsEmpty(cscSupplierInfoDto.getSupplierName()));
            cscSupplierInfoDto.setContactName(PubUtils.trimAndNullAsEmpty(cscSupplierInfoDto.getContactName()));
            cscSupplierInfoDto.setContactPhone(PubUtils.trimAndNullAsEmpty(cscSupplierInfoDto.getContactPhone()));
            Wrapper<List<CscSupplierInfoDto>> cscSupplierList = cscSupplierEdasService.querySupplierByAttribute(cscSupplierInfoDto);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(JacksonUtil.toJsonWithFormat(cscSupplierList.getResult()));
        }catch (Exception ex) {
            logger.error("订单中心筛选供应商出现异常:{}", ex.getMessage(), ex);
        }
    }
        /*
        校验客户订单编号
         */
    @RequestMapping(value = "/checkCustOrderCode",method = RequestMethod.POST)
    @ResponseBody
    public boolean checkCustOrderCode(String custOrderCode, String selfCustOrderCode){
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
    public boolean checkTransCode(String transCode, String selfTransCode){
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
    public void getCscGoodsTypeList(String cscGoodsType, HttpServletResponse response){
        logger.info("下单货品筛选==> cscGoodsType={}", cscGoodsType);
        //调用外部接口,最低传CustomerCode
        try{
            CscGoodsTypeDto cscGoodType=new CscGoodsTypeDto();
            if(!PubUtils.trimAndNullAsEmpty(cscGoodsType).equals("")){
                cscGoodType.setPid(cscGoodsType);
            }
            Wrapper<List<CscGoodsTypeVo>> CscGoodsType = cscGoodsTypeEdasService.getCscGoodsTypeList(cscGoodType);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(JacksonUtil.toJsonWithFormat(CscGoodsType.getResult()));
            logger.info("###############返回货品类别列表为{}####################",JacksonUtil.toJsonWithFormat(CscGoodsType.getResult()));
        }catch (Exception ex){
            logger.error("订单中心筛选货品出现异常:{}", ex.getMessage(), ex);
        }
    }
}
