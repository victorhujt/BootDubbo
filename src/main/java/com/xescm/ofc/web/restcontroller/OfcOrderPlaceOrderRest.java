package com.xescm.ofc.web.restcontroller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.core.utils.PublicUtil;
import com.xescm.csc.model.dto.CscGoodsApiDto;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.QueryWarehouseDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyResponseDto;
import com.xescm.csc.model.dto.goodstype.CscGoodsTypeDto;
import com.xescm.csc.model.dto.warehouse.CscWarehouseDto;
import com.xescm.csc.model.vo.CscGoodsApiVo;
import com.xescm.csc.model.vo.CscGoodsTypeVo;
import com.xescm.csc.provider.*;
import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.enums.BusinessTypeEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.model.vo.ofc.OfcGroupVo;
import com.xescm.ofc.service.OfcDistributionBasicInfoService;
import com.xescm.ofc.service.OfcFundamentalInformationService;
import com.xescm.ofc.service.OfcOrderManageOperService;
import com.xescm.ofc.service.OfcOrderPlaceService;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.rmc.edas.domain.dto.RmcWarehouseDto;
import com.xescm.rmc.edas.domain.qo.RmcWareHouseQO;
import com.xescm.rmc.edas.domain.vo.RmcWarehouseRespDto;
import com.xescm.rmc.edas.service.RmcWarehouseEdasService;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 我要下单
 * Created by lyh on 2016/10/8.
 */
@RequestMapping(value = "/ofc",produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcOrderPlaceOrderRest extends BaseController{

    @Resource
    private OfcOrderPlaceService ofcOrderPlaceService;
    @Resource
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Resource
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;
    @Resource
    private CscGoodsEdasService cscGoodsEdasService;
    @Resource
    private CscGoodsTypeEdasService cscGoodsTypeEdasService;
    @Resource
    private CscSupplierEdasService cscSupplierEdasService;
    @Resource
    private CscContactEdasService cscContactEdasService;
    @Resource
    private  CscWarehouseEdasService cscWarehouseEdasService;
    @Resource
    private RmcWarehouseEdasService rmcWarehouseEdasService;

    @Resource
    private OfcOrderManageOperService ofcOrderManageOperService;



    /**
     * 编辑
     * @param ofcOrderDTOStr      orderDto的json
     * @param orderGoodsListStr     货品列表json
     * @param cscContantAndCompanyDtoConsignorStr       发货方json
     * @param cscContantAndCompanyDtoConsigneeStr       收货方json
     * @param cscSupplierInfoDtoStr     供应商json
     * @param tag   各种下单方式标记
     * @return      Wrapper
     */
    @RequestMapping("/orderEdit")
    @ResponseBody
    public Wrapper<?> orderEdit(String ofcOrderDTOStr, String orderGoodsListStr, String cscContantAndCompanyDtoConsignorStr
            , String cscContantAndCompanyDtoConsigneeStr, String cscSupplierInfoDtoStr, String tag){
        logger.debug("==>订单中心下单或编辑实体 ofcOrderDTOJson={}", ofcOrderDTOStr);
        logger.debug("==>订单中心下单或编辑标志位 tag={}", tag);
        String result;
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
                ofcOrderDTO.setProvideTransport(OrderConstConstant.WAREHOUSE_NO_TRANS);
            }
            if (null == ofcOrderDTO.getUrgent()){
                ofcOrderDTO.setUrgent(OrderConstConstant.DISTRIBUTION_ORDER_NOT_URGENT);
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
     * 订单中心下单
     * @param ofcOrderDTOStr        订单基本信息、收发货方信息
     * @param orderGoodsListStr     货品信息
     * @param cscContantAndCompanyDtoConsignorStr   发货人信息
     * @param cscContantAndCompanyDtoConsigneeStr   收货人信息
     * @param cscSupplierInfoDtoStr                 供应商信息
     * @param tag           标识下单、编辑、运输开单
     * @return      Wrapper
     */
    @RequestMapping("/orderPlaceCon")
    @ResponseBody
    public Wrapper<?> orderPlace(String ofcOrderDTOStr,String orderGoodsListStr,String cscContantAndCompanyDtoConsignorStr
            ,String cscContantAndCompanyDtoConsigneeStr,String cscSupplierInfoDtoStr, String tag){
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
                ofcOrderDTO.setProvideTransport(OrderConstConstant.WAREHOUSE_NO_TRANS);
            }
            if (null == ofcOrderDTO.getUrgent()){
                ofcOrderDTO.setUrgent(OrderConstConstant.DISTRIBUTION_ORDER_NOT_URGENT);
            }

            if(null == ofcOrderDTO.getOrderTime()){
                throw new BusinessException("请选择订单日期");
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
     * @param cscGoods 货品筛选条件
     * @param response HttpServletResponse
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
     * @param cscGoods 货品筛选条件
     * @param customerCode 客户编码
     * @return
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


    /**
     * 下单收发货方筛选
     * @param cscContantAndCompanyDto 收发货方筛选条件
     * @param customerCode 客户编码
     * @param response HttpServletResponse
     */
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


    /**
     * 加载当前用户下的仓库信息
     * @return
     */
    @RequestMapping(value = "/loadWarehouseByUser",method = RequestMethod.POST)
    @ResponseBody
    public Object loadWarehouseByUser(){
        try {
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            RmcWareHouseQO rmcWareHouseQO=new RmcWareHouseQO();
            rmcWareHouseQO.setUserId(authResDtoByToken.getUserId());
            Wrapper<List<RmcWarehouseRespDto>>  warehouseResult=rmcWarehouseEdasService.queryWarehouseList(rmcWareHouseQO);
            if(warehouseResult.getCode()!=warehouseResult.SUCCESS_CODE){
                logger.error("查询用户下的仓库产生异常{}",warehouseResult.getMessage());
                return WrapMapper.wrap(Wrapper.ERROR_CODE,warehouseResult.getMessage());
            }
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "操作成功", warehouseResult.getResult());
        }catch (Exception ex) {
            logger.error("查询用户下的仓库产生异常{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        }
    }


    @RequestMapping(value = "/loadAreaAndBaseByUser",method = RequestMethod.POST)
    @ResponseBody
    public Object loadAreaAndBaseByUser() {
        AuthResDto authResDto = getAuthResDtoByToken();
        Map<String, List<OfcGroupVo>> groupMap = null;
        try {
            groupMap = ofcOrderManageOperService.queryGroupList(authResDto);
            if (groupMap==null) {
                return WrapMapper.wrap(Wrapper.ERROR_CODE,"没有查询到大区和基地信息");
            }
        } catch (Exception ex) {
            logger.error("查询用户下的大区和基地信息异常{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
           return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "操作成功",groupMap);
    }

    /**
     * 下单收发货方筛选
     * @param cscContantAndCompanyDto 收发货方筛选条件
     * @param customerCode 客户编码
     * @return
     */
    @RequestMapping(value = "/contactSelectForPage",method = RequestMethod.POST)
    @ResponseBody
    public Object contactSelectByPage(String cscContantAndCompanyDto, String customerCode){
        logger.debug("==>下单收发货方筛选,cscContantAndCompanyDto = {}",cscContantAndCompanyDto);
        logger.debug("==>下单收发货方筛选,customerCode = {}",customerCode);
        //调用外部接口,最低传CustomerCode和purpose
        AtomicReference<Wrapper<PageInfo<CscContantAndCompanyResponseDto>>> result= new AtomicReference<>(null);
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
            result.set(cscContactEdasService.queryCscReceivingInfoListWithPage(csc));
            /*
            csc.getCscContact().setPurpose("3");
            Wrapper<List<CscContantAndCompanyVo>> cscReceivingInfoListOfBoth = feignCscCustomerAPIClient.queryCscReceivingInfoList(csc);
            List<CscContantAndCompanyVo> resultOfBoth = cscReceivingInfoListOfBoth.getResult();
            result.addAll(resultOfBoth);*/
        } catch (Exception ex) {
            logger.error("订单中心筛选收发货方出现异常:{}", ex.getMessage(), ex);
        }
        return result.get();
    }

    /**
     * 供应商筛选(调用客户中心API)
     * @param cscSupplierInfoDto 供应商筛选条件
     * @param response HttpServletResponse
     * @throws InvocationTargetException
     */
    @ApiOperation(value="下单供应商筛选", notes="根据查询条件筛选供应商")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/supplierSelect",method = RequestMethod.POST)
    @ResponseBody
    public Wrapper supplierSelectByCscApi( CscSupplierInfoDto cscSupplierInfoDto, HttpServletResponse response) throws InvocationTargetException{
        logger.debug("==>下单供应商筛选,cscSupplierInfoDto = {}",cscSupplierInfoDto);
        Wrapper<PageInfo<CscSupplierInfoDto>> result;
        //调用外部接口,最低传CustomerCode
        try {
            if (cscSupplierInfoDto == null) {
                AuthResDto authResDtoByToken = getAuthResDtoByToken();
                cscSupplierInfoDto.setCustomerCode(authResDtoByToken.getGroupRefCode());
                cscSupplierInfoDto.setSupplierName(PubUtils.trimAndNullAsEmpty(cscSupplierInfoDto.getSupplierName()));
                cscSupplierInfoDto.setContactName(PubUtils.trimAndNullAsEmpty(cscSupplierInfoDto.getContactName()));
                cscSupplierInfoDto.setContactPhone(PubUtils.trimAndNullAsEmpty(cscSupplierInfoDto.getContactPhone()));
            }
            result = cscSupplierEdasService.querySupplierByAttributePageList(cscSupplierInfoDto);
            logger.info("=================>>>>>> result :{}", ToStringBuilder.reflectionToString(result));
            logger.info("=================>>>>>> result JSON :{}", JacksonUtil.toJson(result));
            if(null == result.getResult()){
                logger.error("下单供应商筛选出错 result.getResult() is null");
                throw new BusinessException("下单供应商筛选出错");
            }
            PageInfo<CscSupplierInfoDto> cscSupplierInfoDtoPageInfo = result.getResult();
            if(CollectionUtils.isEmpty(result.getResult().getList())){
                logger.error("下单供应商筛选出错 result.getResult().getList() is null");
                throw new BusinessException("下单供应商筛选出错");
            }
            List<CscSupplierInfoDto> cscSupplierInfoDtos = result.getResult().getList();
            logger.info("=================>>>>>> cscSupplierInfoDtoPageInfo :{}", ToStringBuilder.reflectionToString(cscSupplierInfoDtoPageInfo));
            logger.info("=================>>>>>> cscSupplierInfoDtos :{}", ToStringBuilder.reflectionToString(cscSupplierInfoDtos));
            if (Wrapper.ERROR_CODE == result.getCode()) {
                logger.error("查询供应商列表失败,查询结果有误!");
            }
        } catch (BusinessException ex) {
            logger.error("==>订单中心筛选供应商出现异常：{}", ex);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("订单中心筛选供应商出现异常:{}", ex.getMessage(), ex);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, "查询供应商发生未知异常！");
        }
        return result;
    }

    /**
     * 校验客户订单编号
     * @param custOrderCode 客户订单编号
     * @param selfCustOrderCode 校验用客户订单编号
     * @return
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

    /**
     * 校验运输单号
     * @param transCode 运输单号
     * @param selfTransCode 校验用运输单号
     * @return
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
     * 货品类别(调用客户中心API)
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

    /**
     * 客户编码查询客户下的仓库信息
     * @param customerCode 客户编码
     * @return
     */
    @RequestMapping(value = "/queryWarehouseByCustomerCode",method = RequestMethod.POST)
    @ResponseBody
    public Object queryWarehouseByCustomerCode(String customerCode){
        try {
            if(PublicUtil.isEmpty(customerCode)){
                throw new BusinessException("客户编码不可以为空！");
            }
            //客户编码查询出绑定的仓库编码
            List<RmcWarehouseRespDto> warehouseRespDtoList=new ArrayList<>();
            QueryWarehouseDto dto=new QueryWarehouseDto();
            dto.setCustomerCode(customerCode);
            Wrapper<List<CscWarehouseDto>>  warehouse=cscWarehouseEdasService.getCscWarehouseByCustomerId(dto);
            if(warehouse.getCode()==warehouse.SUCCESS_CODE){
                //通过查询出的仓库编码查询出仓库的信息
                if(!PublicUtil.isEmpty(warehouse.getResult())){
                    RmcWarehouseDto rmcWarehouseDto=new RmcWarehouseDto();
                    for (CscWarehouseDto cscWarehouseDto : warehouse.getResult()){
                        rmcWarehouseDto.setWarehouseCode(cscWarehouseDto.getWarehouseCode());
                        Wrapper<RmcWarehouseRespDto> resp=rmcWarehouseEdasService.queryRmcWarehouseByCode(rmcWarehouseDto);
                        if(resp.getCode()==Wrapper.SUCCESS_CODE){
                            warehouseRespDtoList.add(resp.getResult());
                        }else{
                            logger.error("通过仓库编码查询仓库信息产生异常{},仓库编码为{}",resp.getMessage(),rmcWarehouseDto.getWarehouseCode());
                        }
                    }
                }else{
                    logger.info("客户没有开通仓库{}",warehouse.getMessage());
                }
            }else{
                logger.error("通过客户编码查询客户绑定的仓库编码产生异常{}",warehouse.getMessage());
                return WrapMapper.wrap(Wrapper.ERROR_CODE,warehouse.getMessage());
            }
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "操作成功", warehouseRespDtoList);
        }catch (Exception ex) {
            logger.error("客户编码查询绑定的仓库信息出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        }

    }

}
