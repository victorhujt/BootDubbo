package com.xescm.ofc.service.impl;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.QueryCustomerCodeDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.csc.model.vo.CscCustomerVo;
import com.xescm.csc.provider.CscCustomerEdasService;
import com.xescm.ofc.constant.GenCodePreffixConstant;
import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.constant.OrderConstant;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.enums.ResultCodeEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.model.vo.ofc.OfcGroupVo;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.ofc.utils.DateUtils;
import com.xescm.uam.model.dto.group.UamGroupDto;
import com.xescm.uam.provider.UamGroupEdasService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.xescm.ofc.constant.GenCodePreffixConstant.ORDER_PRE;
import static com.xescm.ofc.constant.OrderConstConstant.*;
import static com.xescm.ofc.constant.OrderConstant.TRANSPORT_ORDER;
import static com.xescm.ofc.constant.OrderPlaceTagConstant.*;

/**
 * 订单下单相关处理Service
 * Created by ydx on 2016/10/12.
 */
@Service
@Transactional//既能注解在方法上,也能注解在类上.当注解在类上的时候,意味着这个类的所有public方法都是开启事务的,如果类级别和方法级别同事使用了该注解,则方法覆盖类.
//@Transactional(rollbackFor={xxx.class})
public class OfcOrderPlaceServiceImpl implements OfcOrderPlaceService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OfcOrderStatusService ofcOrderStatusService;
    @Resource
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Resource
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Resource
    private OfcFinanceInformationService ofcFinanceInformationService;
    @Resource
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;
    @Resource
    private OfcWarehouseInformationService ofcWarehouseInformationService;
    @Resource
    private OfcOrderManageService ofcOrderManageService;
    @Resource
    private OfcMerchandiserService ofcMerchandiserService;
    @Resource
    private CodeGenUtils codeGenUtils;
    @Resource
    private CscCustomerEdasService cscCustomerEdasService;
    @Resource
    private UamGroupEdasService uamGroupEdasService;
    @Resource
    private OfcOrderManageOperService ofcOrderManageOperService;

    private ModelMapper modelMapper = new ModelMapper();


    /**
     * 根据四级判断设置城配或者干线处理
     * @param ofcDistributionBasicInfo 运输信息
     * @param ofcFundamentalInformation 基本信息
     */
    private  void setCityOrTrunk(OfcDistributionBasicInfo ofcDistributionBasicInfo,OfcFundamentalInformation ofcFundamentalInformation){
        if(!PubUtils.isSEmptyOrNull(ofcDistributionBasicInfo.getDeparturePlaceCode()) && ofcDistributionBasicInfo.getDeparturePlaceCode().length() > 12){
            String depatrueCode = ofcDistributionBasicInfo.getDeparturePlaceCode().substring(0,13);
            String destinationCode = ofcDistributionBasicInfo.getDestinationCode().substring(0,13);
            if(depatrueCode.equals(destinationCode)){
                ofcFundamentalInformation.setBusinessType(WITH_THE_CITY);
            }else {
                ofcFundamentalInformation.setBusinessType(WITH_THE_TRUNK);
            }
        }else{
            throw new BusinessException("四级地址编码为空!");
        }
    }

    /**
     * 保存货品详情
     * @param ofcGoodsDetailsInfos 订单货品明细信息
     * @param ofcFundamentalInformation 基本信息
     */
    private BigDecimal saveDetails(List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos,OfcFundamentalInformation ofcFundamentalInformation){
        BigDecimal goodsAmountCount = new BigDecimal(0);
        for(OfcGoodsDetailsInfo ofcGoodsDetails : ofcGoodsDetailsInfos){
            if(ofcGoodsDetails.getQuantity() == null || ofcGoodsDetails.getQuantity().compareTo(new BigDecimal(0)) == 0 ){
                if ((ofcGoodsDetails.getWeight() == null || ofcGoodsDetails.getWeight().compareTo(new BigDecimal(0)) == 0) && (ofcGoodsDetails.getCubage() == null || ofcGoodsDetails.getCubage().compareTo(new BigDecimal(0)) == 0)) {
                    continue;
                }
            }
            String orderCode = ofcFundamentalInformation.getOrderCode();
            ofcGoodsDetails.setGoodsCode(ofcGoodsDetails.getGoodsCode().split("@")[0]);
            ofcGoodsDetails.setOrderCode(orderCode);
            ofcGoodsDetails.setCreationTime(ofcFundamentalInformation.getCreationTime());
            ofcGoodsDetails.setCreator(ofcFundamentalInformation.getCreator());
            ofcGoodsDetails.setOperator(ofcFundamentalInformation.getOperator());
            ofcGoodsDetails.setOperTime(ofcFundamentalInformation.getOperTime());
            goodsAmountCount = goodsAmountCount.add(ofcGoodsDetails.getQuantity());
            ofcGoodsDetailsInfoService.save(ofcGoodsDetails);
        }
        return goodsAmountCount;
    }

    /**
     * 下单入口
     * @param ofcOrderDTO 订单实体
     * @param ofcGoodsDetailsInfos 货品信息
     * @param tag 标记位 : place 普通下单 , manage 编辑 , tranplace 运输开单,  distributionPlace 城配开单
     * @param authResDtoByToken   token
     * @param custId    客户ID
     * @param cscContantAndCompanyDtoConsignor   发货方信息
     * @param cscContantAndCompanyDtoConsignee   收货方信息
     * @param cscSupplierInfoDto    供应商信息
     * @return  String
     */
    @Override
    public String placeOrder(OfcOrderDTO ofcOrderDTO, List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos, String tag, AuthResDto authResDtoByToken, String custId
                            , CscContantAndCompanyDto cscContantAndCompanyDtoConsignor
                            , CscContantAndCompanyDto cscContantAndCompanyDtoConsignee, CscSupplierInfoDto cscSupplierInfoDto) {

        OfcFinanceInformation  ofcFinanceInformation =modelMapper.map(ofcOrderDTO, OfcFinanceInformation.class);
        OfcFundamentalInformation ofcFundamentalInformation = modelMapper.map(ofcOrderDTO, OfcFundamentalInformation.class);
        OfcDistributionBasicInfo ofcDistributionBasicInfo = modelMapper.map(ofcOrderDTO, OfcDistributionBasicInfo.class);
        OfcWarehouseInformation  ofcWarehouseInformation = modelMapper.map(ofcOrderDTO, OfcWarehouseInformation.class);
        OfcMerchandiser ofcMerchandiser=modelMapper.map(ofcOrderDTO,OfcMerchandiser.class);
        ofcFundamentalInformation.setCreationTime(new Date());
        ofcFundamentalInformation.setCreator(authResDtoByToken.getUserId());
        ofcFundamentalInformation.setCreatorName(authResDtoByToken.getUserName());
        ofcFundamentalInformation.setOperator(authResDtoByToken.getUserId());
        ofcFundamentalInformation.setOperatorName(authResDtoByToken.getUserName());

        //校验当前登录用户的身份信息,并存放大区和基地信息
        ofcFundamentalInformation = getAreaAndBaseMsg(authResDtoByToken,ofcFundamentalInformation);
        ofcFundamentalInformation.setOperTime(new Date());
        OfcOrderStatus ofcOrderStatus=new OfcOrderStatus();
        ofcFundamentalInformation.setStoreName(ofcOrderDTO.getStoreName());//店铺还没维护表
        ofcFundamentalInformation.setOrderSource(MANUAL);//订单来源

        if (PubUtils.trimAndNullAsEmpty(tag).equals(ORDER_TAG_NORMAL_PLACE)){//下单
            orderPlaceTagPlace(ofcGoodsDetailsInfos, authResDtoByToken, custId, cscContantAndCompanyDtoConsignor
                    , cscContantAndCompanyDtoConsignee, ofcFinanceInformation, ofcFundamentalInformation, ofcDistributionBasicInfo, ofcWarehouseInformation, ofcMerchandiser, ofcOrderStatus);
        }else if (PubUtils.trimAndNullAsEmpty(tag).equals(ORDER_TAG_NORMAL_EDIT)){ //编辑
            orderPlaceTagManage(ofcOrderDTO, ofcGoodsDetailsInfos, authResDtoByToken, cscContantAndCompanyDtoConsignor
                    , cscContantAndCompanyDtoConsignee, ofcFundamentalInformation, ofcDistributionBasicInfo, ofcWarehouseInformation, ofcOrderStatus);
        }else if(PubUtils.trimAndNullAsEmpty(tag).equals(ORDER_TAG_OPER_TRANS)){// 运输开单
            orderPlaceTagTransPlace(ofcGoodsDetailsInfos, authResDtoByToken, cscContantAndCompanyDtoConsignor
                    , cscContantAndCompanyDtoConsignee, ofcFinanceInformation, ofcFundamentalInformation, ofcDistributionBasicInfo, ofcMerchandiser, ofcOrderStatus);
        } else if(PubUtils.trimAndNullAsEmpty(tag).equals(ORDER_TAG_OPER_DISTRI)){ //城配开单
            distributionOrderPlace(ofcFundamentalInformation,ofcGoodsDetailsInfos,ofcDistributionBasicInfo
                    ,ofcWarehouseInformation,ofcFinanceInformation,custId,cscContantAndCompanyDtoConsignor,cscContantAndCompanyDtoConsignee,authResDtoByToken
                    ,ofcOrderStatus,ofcMerchandiser);
        } else if(PubUtils.trimAndNullAsEmpty(tag).equals(ORDER_TAG_OPER_TRANEDIT)){// 运输开单编辑
            orderTransPlaceTagManage(ofcOrderDTO, ofcGoodsDetailsInfos, authResDtoByToken, cscContantAndCompanyDtoConsignor
                    , cscContantAndCompanyDtoConsignee, ofcFundamentalInformation, ofcDistributionBasicInfo, ofcWarehouseInformation, ofcOrderStatus);
        }else {
            throw new BusinessException("未知操作!系统无法识别!");
        }
        if(ORDER_TAG_NORMAL_PLACE.equals(tag) || ORDER_TAG_OPER_TRANS.equals(tag) || ORDER_TAG_OPER_DISTRI.equals(tag)){
            return "您已成功下单!";
        }else if(ORDER_TAG_NORMAL_EDIT.equals(tag)  || ORDER_TAG_OPER_TRANEDIT.equals(tag)){
            return "您的订单修改成功!";
        }else {
            return ResultCodeEnum.ERROROPER.getName();
        }
    }

    /**
     * 运输开单
     * @param ofcGoodsDetailsInfos 货品信息
     * @param authResDtoByToken 登录信息
     * @param cscContantAndCompanyDtoConsignor 发货方
     * @param cscContantAndCompanyDtoConsignee 收货方
     * @param ofcFinanceInformation 财务信息
     * @param ofcFundamentalInformation 基本信息
     * @param ofcDistributionBasicInfo 运输信息
     * @param ofcMerchandiser 开单员
     * @param ofcOrderStatus 订单状态
     */
    private void orderPlaceTagTransPlace(List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos, AuthResDto authResDtoByToken
            , CscContantAndCompanyDto cscContantAndCompanyDtoConsignor, CscContantAndCompanyDto cscContantAndCompanyDtoConsignee
            , OfcFinanceInformation ofcFinanceInformation, OfcFundamentalInformation ofcFundamentalInformation
            , OfcDistributionBasicInfo ofcDistributionBasicInfo, OfcMerchandiser ofcMerchandiser, OfcOrderStatus ofcOrderStatus) {
        StringBuilder notes = new StringBuilder();
        // 校验当前客户的客户订单号是否重复
        String custOrderCode = ofcFundamentalInformation.getCustOrderCode();
        String custCode = ofcFundamentalInformation.getCustCode();
        if (!PubUtils.isSEmptyOrNull(custOrderCode) && !PubUtils.isSEmptyOrNull(custCode)) {
            boolean isDup = checkOrderCode(custOrderCode, custCode);
            if (isDup) {
                throw new BusinessException("当前客户存在重复客户订单号！");
            }
        } else {
            if (PubUtils.isSEmptyOrNull(custCode)) {
                throw new BusinessException("客户不能为空！");
            }
        }

        if(!PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getTransCode()).equals("")){
            int orderCodeByTransCode = ofcDistributionBasicInfoService.checkTransCode(ofcDistributionBasicInfo);
            if(orderCodeByTransCode>=1){
                throw new BusinessException("该运输单号号已经存在!您不能重复下单!");
            }
        }
        String orderType = TRANSPORT_ORDER;
        ofcFundamentalInformation.setOrderCode(codeGenUtils.getNewWaterCode(GenCodePreffixConstant.ORDER_PRE,6));
        ofcFundamentalInformation.setAbolishMark(ORDER_WASNOT_ABOLISHED);//未作废
        ofcFundamentalInformation.setOrderType(orderType);
        if(ofcFundamentalInformation.getOrderType().equals(orderType)){
            Wrapper<?> wrapper = ofcDistributionBasicInfoService.validateDistrictContactMessage(cscContantAndCompanyDtoConsignor, cscContantAndCompanyDtoConsignee);
            if(Wrapper.ERROR_CODE == wrapper.getCode()){
                throw new BusinessException(wrapper.getMessage());
            }

            //运输订单
            if(PubUtils.isSEmptyOrNull(ofcDistributionBasicInfo.getDeparturePlaceCode()) || ofcDistributionBasicInfo.getDeparturePlaceCode().length() <= 12){
                throw new BusinessException("四级地址编码为空!");
            }
            addFinanceInformation(ofcFinanceInformation,ofcFundamentalInformation);
            addDistributionInfo(ofcDistributionBasicInfo, ofcFundamentalInformation);
        }else{
            throw new BusinessException("您选择的订单类型系统无法识别!");
        }

        notes.append(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1));
        notes.append(" 订单已创建");
        notes.append(" 操作人: ").append(authResDtoByToken.getUserName());
        notes.append(" 操作单位: ").append(authResDtoByToken.getGroupRefName());
        ofcOrderStatus.setNotes(notes.toString());
        upOrderStatus(ofcOrderStatus,ofcFundamentalInformation,authResDtoByToken);
        //添加该订单的货品信息
        for(OfcGoodsDetailsInfo ofcGoodsDetails : ofcGoodsDetailsInfos){
            String orderCode = ofcFundamentalInformation.getOrderCode();
            ofcGoodsDetails.setOrderCode(orderCode);
            ofcGoodsDetails.setCreationTime(ofcFundamentalInformation.getCreationTime());
            ofcGoodsDetails.setCreator(ofcFundamentalInformation.getCreator());
            ofcGoodsDetails.setOperator(ofcFundamentalInformation.getOperator());
            ofcGoodsDetails.setOperTime(ofcFundamentalInformation.getOperTime());
            ofcGoodsDetailsInfoService.save(ofcGoodsDetails);
        }
        try {
            //添加基本信息
            ofcFundamentalInformationService.save(ofcFundamentalInformation);
        } catch (Exception ex) {
            if (ex.getCause().getMessage().trim().startsWith("Duplicate entry")) {
                logger.error("获取订单号发生重复，导致保存计划单基本信息发生错误！{}", ex);
                throw new BusinessException("获取订单号发生重复，导致保存计划单基本信息发生错误！");
            } else {
                logger.error("保存计划单信息发生错误:{}", ex);
                throw new BusinessException("保存计划单信息发生错误！", ex);
            }
        }
        if(ofcMerchandiserService.select(ofcMerchandiser).size()==0 && !PubUtils.trimAndNullAsEmpty(ofcMerchandiser.getMerchandiser()).equals("")){
            ofcMerchandiserService.save(ofcMerchandiser);
        }
        //推结算
        ofcOrderManageService.pushOrderToAc(ofcFundamentalInformation,ofcFinanceInformation,ofcDistributionBasicInfo,ofcGoodsDetailsInfos);
        String code = ofcOrderManageService.orderAutoAudit(ofcFundamentalInformation, ofcGoodsDetailsInfos, ofcDistributionBasicInfo,
                null, ofcFinanceInformation, ofcOrderStatus.getOrderStatus(), REVIEW, authResDtoByToken);
        if(StringUtils.equals(String.valueOf(Wrapper.ERROR_CODE),code)){
            throw new BusinessException("自动审核操作失败!");
        }
    }

    /**
     * 订单编辑
     * @param ofcOrderDTO 订单实体
     * @param ofcGoodsDetailsInfos 货品信息
     * @param authResDtoByToken 登录用户
     * @param cscContantAndCompanyDtoConsignor 收货方
     * @param cscContantAndCompanyDtoConsignee 发货方
     * @param ofcFundamentalInformation 基本信息
     * @param ofcDistributionBasicInfo 运输信息
     * @param ofcWarehouseInformation 仓库信息
     * @param ofcOrderStatus 订单状态
     */
    private void orderPlaceTagManage(OfcOrderDTO ofcOrderDTO, List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos, AuthResDto authResDtoByToken, CscContantAndCompanyDto cscContantAndCompanyDtoConsignor, CscContantAndCompanyDto cscContantAndCompanyDtoConsignee, OfcFundamentalInformation ofcFundamentalInformation, OfcDistributionBasicInfo ofcDistributionBasicInfo, OfcWarehouseInformation ofcWarehouseInformation, OfcOrderStatus ofcOrderStatus) {
        //删除之前订单的货品信息
        OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
        ofcGoodsDetailsInfo.setOrderCode(ofcOrderDTO.getOrderCode());
        ofcGoodsDetailsInfoService.delete(ofcGoodsDetailsInfo);
        //添加该订单的货品信息 modify by wangst 做抽象处理
        BigDecimal goodsAmountCount = saveDetails(ofcGoodsDetailsInfos,ofcFundamentalInformation);
        ofcDistributionBasicInfo.setQuantity(goodsAmountCount);
        String orderType = ofcFundamentalInformation.getOrderType();
        if (OrderConstant.WAREHOUSE_DIST_ORDER.equals(orderType)){//编辑时仓配订单
            if(null == ofcWarehouseInformation.getProvideTransport()){
                ofcWarehouseInformation.setProvideTransport(WAREHOUSE_NO_TRANS);
            }
            //仓配单需要运输
            if(Objects.equals(ofcWarehouseInformation.getProvideTransport(), WEARHOUSE_WITH_TRANS)){
                Wrapper<?> wrapper =ofcDistributionBasicInfoService.validateDistrictContactMessage(cscContantAndCompanyDtoConsignor, cscContantAndCompanyDtoConsignee);
                if(Wrapper.ERROR_CODE == wrapper.getCode()){
                    throw new BusinessException(wrapper.getMessage());
                }

                //如果编辑订单后, 还是需要提供运输, 就要更新运输信息
                upDistributionBasicInfo(ofcDistributionBasicInfo,ofcFundamentalInformation);
                OfcDistributionBasicInfo ofcDist = new OfcDistributionBasicInfo();
                ofcDist.setOrderCode(ofcFundamentalInformation.getOrderCode());
                List<OfcDistributionBasicInfo> select = ofcDistributionBasicInfoService.select(ofcDist);
                if(select.size() > 0){//有运输信息
                    ofcDistributionBasicInfoService.updateByOrderCode(ofcDistributionBasicInfo);
                }else if (select.size() == 0) addDistributionInfo(ofcDistributionBasicInfo, ofcFundamentalInformation);
            //仓配单不需要运输,需要将属于该订单的运输信息删除
            }else if (Objects.equals(ofcWarehouseInformation.getProvideTransport(), WAREHOUSE_NO_TRANS)){
                ofcFundamentalInformation.setSecCustCode("");
                ofcFundamentalInformation.setSecCustName("");
                ofcDistributionBasicInfoService.deleteByOrderCode(ofcFundamentalInformation.getOrderCode());

            }
            // 更新仓配信息
            upOfcWarehouseInformation(ofcWarehouseInformation,ofcFundamentalInformation);
            //入库
            if("62".equals(ofcFundamentalInformation.getBusinessType().substring(0,2))){//如果是入库才有供应商信息
                logger.info("这是入库");
            }
            //出库
            if("61".equals(ofcFundamentalInformation.getBusinessType().substring(0,2))){//如果是入库才有供应商信息
                ofcWarehouseInformation.setSupportCode("");
                ofcWarehouseInformation.setSupportName("");
                //去数据库清掉相应的供应商信息
            }
            //先去数据库查, 如果有就是改, 如果没有就是增
            OfcWarehouseInformation ofcWare = new OfcWarehouseInformation();
            ofcWare.setOrderCode(ofcFundamentalInformation.getOrderCode());
            List<OfcWarehouseInformation> select = ofcWarehouseInformationService.select(ofcWare);
            if(select.size() > 0){//有
                ofcWarehouseInformationService.updateByOrderCode(ofcWarehouseInformation);
            }else if(select.size() == 0){
                saveWarehouseMessage(ofcWarehouseInformation);
            }
        }else if(TRANSPORT_ORDER.equals(orderType)){
            Wrapper<?> wrapper = ofcDistributionBasicInfoService.validateDistrictContactMessage(cscContantAndCompanyDtoConsignor, cscContantAndCompanyDtoConsignee);
            if(Wrapper.ERROR_CODE == wrapper.getCode()){
                throw new BusinessException(wrapper.getMessage());
            }

            //删除仓配信息
            OfcWarehouseInformation ofcWarehouseInformationForTrans = new OfcWarehouseInformation();
            ofcWarehouseInformationForTrans.setOrderCode(ofcFundamentalInformation.getOrderCode());
            ofcWarehouseInformationService.delete(ofcWarehouseInformationForTrans);
            OfcDistributionBasicInfo ofcDist = new OfcDistributionBasicInfo();
            ofcDist.setOrderCode(ofcFundamentalInformation.getOrderCode());
            List<OfcDistributionBasicInfo> select = ofcDistributionBasicInfoService.select(ofcDist);
            //设置城配或者干线
            setCityOrTrunk(ofcDistributionBasicInfo,ofcFundamentalInformation);
            if(select.size() > 0){//有运输信息
                ofcDistributionBasicInfoService.updateByOrderCode(ofcDistributionBasicInfo);
            }else{
                addDistributionInfo(ofcDistributionBasicInfo, ofcFundamentalInformation);
            }
        }else{
            throw new BusinessException("您的订单类型系统无法识别!");
        }

        ofcFundamentalInformation.setOperator(authResDtoByToken.getUserId());
        ofcFundamentalInformation.setOperatorName(authResDtoByToken.getUserName());
        ofcFundamentalInformation.setOperTime(new Date());
        ofcOrderStatus.setNotes(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1) +" "+"订单已更新");
        upOrderStatus(ofcOrderStatus,ofcFundamentalInformation,authResDtoByToken);
        ofcFundamentalInformationService.update(ofcFundamentalInformation);
    }

    /**
     * 普通下单
     * @param ofcGoodsDetailsInfos 货品信息
     * @param authResDtoByToken 登录用户
     * @param custId 客户编码
     * @param cscContantAndCompanyDtoConsignor 发货方信息
     * @param cscContantAndCompanyDtoConsignee 收货方信息
     * @param ofcFinanceInformation 财务信息
     * @param ofcFundamentalInformation 基本信息
     * @param ofcDistributionBasicInfo 运输信息
     * @param ofcWarehouseInformation 仓库信息
     * @param ofcMerchandiser 开单员
     * @param ofcOrderStatus 订单状态
     */
    private void orderPlaceTagPlace(List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos, AuthResDto authResDtoByToken, String custId
            , CscContantAndCompanyDto cscContantAndCompanyDtoConsignor, CscContantAndCompanyDto cscContantAndCompanyDtoConsignee
            , OfcFinanceInformation ofcFinanceInformation, OfcFundamentalInformation ofcFundamentalInformation, OfcDistributionBasicInfo ofcDistributionBasicInfo
            , OfcWarehouseInformation ofcWarehouseInformation, OfcMerchandiser ofcMerchandiser, OfcOrderStatus ofcOrderStatus) {
        StringBuilder notes = new StringBuilder();
        int custOrderCode = 0;
        if(!PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getCustOrderCode())){
            custOrderCode = ofcFundamentalInformationService.checkCustOrderCode(ofcFundamentalInformation);
        }
        //根据客户订单编号查询唯一性
        if (custOrderCode < 1){
            ofcFundamentalInformation.setOrderCode(codeGenUtils.getNewWaterCode(ORDER_PRE,6));
            ofcFundamentalInformation.setCustCode(custId);

            if(PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getCustName())){
                QueryCustomerCodeDto queryCustomerCodeDto = new QueryCustomerCodeDto();
                queryCustomerCodeDto.setCustomerCode(custId);
                Wrapper<CscCustomerVo> cscCustomerVo = cscCustomerEdasService.queryCustomerByCustomerCodeOrId(queryCustomerCodeDto);
                if(Wrapper.ERROR_CODE == cscCustomerVo.getCode()){
                    throw new BusinessException(cscCustomerVo.getMessage());
                }else if(null == cscCustomerVo.getResult()){
                    throw new BusinessException("客户中心没有查到该客户!");
                }
                ofcFundamentalInformation.setCustName(cscCustomerVo.getResult().getCustomerName());
            }

            ofcFundamentalInformation.setAbolishMark(ORDER_WASNOT_ABOLISHED);//未作废
            //添加该订单的货品信息
            BigDecimal goodsAmountCount = saveDetails(ofcGoodsDetailsInfos,ofcFundamentalInformation);
            ofcDistributionBasicInfo.setQuantity(goodsAmountCount);

            String orderType = ofcFundamentalInformation.getOrderType();
            if (PubUtils.isOEmptyOrNull(orderType)) {
                throw new BusinessException("您选择的订单类型编码为空!");
            }
            if (OrderConstant.WAREHOUSE_DIST_ORDER.equals(orderType)){
                if(null == ofcWarehouseInformation.getProvideTransport()){
                    ofcWarehouseInformation.setProvideTransport(WAREHOUSE_NO_TRANS);
                }
                if(Objects.equals(ofcWarehouseInformation.getProvideTransport(), WEARHOUSE_WITH_TRANS)){
                    Wrapper<?> wrapper =ofcDistributionBasicInfoService.validateDistrictContactMessage(cscContantAndCompanyDtoConsignor, cscContantAndCompanyDtoConsignee);
                    if(Wrapper.ERROR_CODE == wrapper.getCode()){
                        throw new BusinessException(wrapper.getMessage());
                    }
                    addDistributionInfo(ofcDistributionBasicInfo, ofcFundamentalInformation);
                }
                // 更新仓配信息
                upOfcWarehouseInformation(ofcWarehouseInformation,ofcFundamentalInformation);
                String businessTypeHead = ofcFundamentalInformation.getBusinessType().substring(0,2);
                if("62".equals(businessTypeHead)){//如果是入库才有供应商信息//这儿才是入库
                    logger.info("这儿才是入库");
                }
                ofcWarehouseInformationService.save(ofcWarehouseInformation);
                if("61".equals(businessTypeHead)){//如果是入库才有供应商信息//这儿是出库
                    ofcWarehouseInformation.setSupportCode("");
                    ofcWarehouseInformation.setSupportName("");
                }
            }else if(TRANSPORT_ORDER.equals(orderType)){
                Wrapper<?> wrapper = ofcDistributionBasicInfoService.validateDistrictContactMessage(cscContantAndCompanyDtoConsignor, cscContantAndCompanyDtoConsignee);
                if(Wrapper.ERROR_CODE == wrapper.getCode()){
                    throw new BusinessException(wrapper.getMessage());
                }

                //设置城配或者干线
                setCityOrTrunk(ofcDistributionBasicInfo,ofcFundamentalInformation);
                //保存运输信息
                addDistributionInfo(ofcDistributionBasicInfo, ofcFundamentalInformation);
            }else{
                throw new BusinessException("您选择的订单类型系统无法识别!");
            }

            notes.append(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1));
            notes.append(" 订单已创建");
            notes.append(" 操作人: ").append(authResDtoByToken.getUserName());
            notes.append(" 操作单位: ").append(authResDtoByToken.getGroupRefName());
            ofcOrderStatus.setNotes(notes.toString());
            upOrderStatus(ofcOrderStatus,ofcFundamentalInformation,authResDtoByToken);

            //添加基本信息
            ofcFundamentalInformationService.save(ofcFundamentalInformation);
            if(ofcMerchandiserService.select(ofcMerchandiser).size()==0 && !PubUtils.trimAndNullAsEmpty(ofcMerchandiser.getMerchandiser()).equals("")){
                ofcMerchandiserService.save(ofcMerchandiser);
            }
            //推结算
            ofcOrderManageService.pushOrderToAc(ofcFundamentalInformation,ofcFinanceInformation,ofcDistributionBasicInfo,ofcGoodsDetailsInfos);
            if(!PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getOrderBatchNumber())){
                //进行自动审核
                String code = ofcOrderManageService.orderAutoAudit(ofcFundamentalInformation, ofcGoodsDetailsInfos, ofcDistributionBasicInfo,
                        ofcWarehouseInformation, ofcFinanceInformation, ofcOrderStatus.getOrderStatus(), REVIEW, authResDtoByToken);
                if(StringUtils.equals(String.valueOf(Wrapper.ERROR_CODE),code)){
                    throw new BusinessException("自动审核操作失败!");
                }
            }
        }else{
            throw new BusinessException("该客户订单编号已经存在!您不能重复下单!");
        }
    }

    /**
     * 校验当前登录用户的身份信息,并存放大区和基地信息
     * @param authResDtoByToken     token
     * @param ofcFundamentalInformation     订单基本信息
     * @return  OfcFundamentalInformation
     */
    @Override
    public OfcFundamentalInformation getAreaAndBaseMsg(AuthResDto authResDtoByToken, OfcFundamentalInformation ofcFundamentalInformation) {
        UamGroupDto uamGroupDto = new UamGroupDto();
        uamGroupDto.setSerialNo(authResDtoByToken.getGroupRefCode());
        Wrapper<List<UamGroupDto>> allGroupByType = uamGroupEdasService.getAllGroupByType(uamGroupDto);
        ofcOrderManageOperService.checkUamGroupEdasResultNullOrError(allGroupByType);
        if(CollectionUtils.isEmpty(allGroupByType.getResult()) || allGroupByType.getResult().size() > 1){
            throw new BusinessException("查询当前登录用户组织信息出错:查询到的结果为空或有误");
        }
        UamGroupDto uamGroupDtoResult = allGroupByType.getResult().get(0);
        if(null == uamGroupDtoResult || PubUtils.isSEmptyOrNull(uamGroupDtoResult.getType())){
            throw new BusinessException("查询当前登录用户组织信息出错:查询到的结果有误");
        }
        if(PubUtils.isSEmptyOrNull(uamGroupDtoResult.getSerialNo())){
            throw new BusinessException("当前登录的用户没有流水号!");
        }
        String groupType = uamGroupDtoResult.getType();
        if(StringUtils.equals(groupType,"1")){
            //鲜易供应链身份
            if(StringUtils.equals("GD1625000003",uamGroupDtoResult.getSerialNo())){
                ofcFundamentalInformation.setAreaCode("");
                ofcFundamentalInformation.setAreaName("");
                ofcFundamentalInformation.setBaseCode("");
                ofcFundamentalInformation.setBaseName("");
                //大区身份
            }else{
                ofcFundamentalInformation.setAreaCode(uamGroupDtoResult.getSerialNo());
                ofcFundamentalInformation.setAreaName(uamGroupDtoResult.getGroupName());
                ofcFundamentalInformation.setBaseCode("");
                ofcFundamentalInformation.setBaseName("");
            }
            //基地身份
        }else if(StringUtils.equals(groupType,"3")){
            OfcGroupVo ofcGroupVo = ofcOrderManageOperService.queryAreaMsgByBase(uamGroupDto);
            ofcFundamentalInformation.setAreaCode(ofcGroupVo.getSerialNo());
            ofcFundamentalInformation.setAreaName(ofcGroupVo.getGroupName());
            ofcFundamentalInformation.setBaseCode(uamGroupDtoResult.getSerialNo());
            ofcFundamentalInformation.setBaseName(uamGroupDtoResult.getGroupName());
            //仓库身份, 其他身份怎么处理?
        }else{
            logger.info("当前下单的登陆用户的身份无法匹配大区基地");
        }

        return ofcFundamentalInformation;
    }

    /**
     * 根据客户编号和客户订单号校验重复
     * @param custOrderCode     客户订单编号
     * @param custCode      客户编号
     * @return  boolean
     */
    private boolean checkOrderCode (String custOrderCode, String custCode) {
        boolean isDup = false;
        // 校验客户订单号
        if(!PubUtils.isStrsEmptyOrNull(custOrderCode)) {
            OfcFundamentalInformation ofcFundamentalInfo = new OfcFundamentalInformation();
            ofcFundamentalInfo.setCustOrderCode(custOrderCode);
            ofcFundamentalInfo.setCustCode(custCode);
            int count = ofcFundamentalInformationService.checkCustOrderCode(ofcFundamentalInfo);
            if (count >= 1) {
                isDup = true;
            }
        }
        return isDup;
    }

    /**
     * 更新并保存订单状态
     * @param ofcOrderStatus 订单状态信息
     * @param ofcFundamentalInformation 基本信息
     * @param authResDtoByToken 登录的授权DTO
     */
    private void upOrderStatus(OfcOrderStatus ofcOrderStatus,OfcFundamentalInformation ofcFundamentalInformation,AuthResDto authResDtoByToken){
        ofcOrderStatus.setOrderCode(ofcFundamentalInformation.getOrderCode());
        ofcOrderStatus.setOrderStatus(PENDING_AUDIT);
        ofcOrderStatus.setStatusDesc("待审核");
        ofcOrderStatus.setLastedOperTime(new Date());
        ofcOrderStatus.setOperator(authResDtoByToken.getUserName());
        ofcOrderStatusService.save(ofcOrderStatus);
    }

    /**
     * 添加财务信息
     * @param ofcFinanceInformation 财务信息
     * @param ofcFundamentalInformation 基本信息
     */
    private void addFinanceInformation(OfcFinanceInformation ofcFinanceInformation, OfcFundamentalInformation ofcFundamentalInformation){
        ofcFinanceInformation=upFinanceInformation(ofcFinanceInformation,ofcFundamentalInformation);
        ofcFinanceInformationService.save(ofcFinanceInformation);
    }

    /**
     * 更新财务信息
     * @param ofcFinanceInformation 财务信息
     * @param ofcFundamentalInformation 订单基本信息
     * @return      OfcFinanceInformation
     */
    private OfcFinanceInformation upFinanceInformation(OfcFinanceInformation ofcFinanceInformation
            ,OfcFundamentalInformation ofcFundamentalInformation){
        ofcFinanceInformation.setCreationTime(ofcFundamentalInformation.getCreationTime());
        ofcFinanceInformation.setCreator(ofcFundamentalInformation.getCreator());
        ofcFinanceInformation.setOrderCode(ofcFundamentalInformation.getOrderCode());
        ofcFinanceInformation.setOperator(ofcFundamentalInformation.getOperator());
        ofcFinanceInformation.setOperTime(ofcFundamentalInformation.getOperTime());
        return ofcFinanceInformation;
    }

    /**
     * 添加运输订单
     * @param ofcDistributionBasicInfo 运输信息
     * @param ofcFundamentalInformation 订单基本信息
     */
    private void addDistributionInfo(OfcDistributionBasicInfo ofcDistributionBasicInfo, OfcFundamentalInformation ofcFundamentalInformation){
        upDistributionBasicInfo(ofcDistributionBasicInfo,ofcFundamentalInformation);
        ofcDistributionBasicInfoService.save(ofcDistributionBasicInfo);
    }

    /**
     * 更新运输信息
     * @param ofcDistributionBasicInfo 运输信息
     * @param ofcFundamentalInformation 订单基本信息
     */
    private void upDistributionBasicInfo(OfcDistributionBasicInfo ofcDistributionBasicInfo,OfcFundamentalInformation ofcFundamentalInformation){
        ofcDistributionBasicInfo.setCreationTime(ofcFundamentalInformation.getCreationTime());
        ofcDistributionBasicInfo.setCreator(ofcFundamentalInformation.getCreator());
        ofcDistributionBasicInfo.setOrderCode(ofcFundamentalInformation.getOrderCode());
        ofcDistributionBasicInfo.setOperator(ofcFundamentalInformation.getOperator());
        ofcDistributionBasicInfo.setOperTime(ofcFundamentalInformation.getOperTime());
    }

    /**
     * 更新仓库信息
     * @param ofcWarehouseInformation 仓库信息
     * @param ofcFundamentalInformation 订单基本信息
     */
    private void upOfcWarehouseInformation(OfcWarehouseInformation ofcWarehouseInformation
            ,OfcFundamentalInformation ofcFundamentalInformation){
        ofcWarehouseInformation.setOrderCode(ofcFundamentalInformation.getOrderCode());
        ofcWarehouseInformation.setCreationTime(ofcFundamentalInformation.getCreationTime());
        ofcWarehouseInformation.setCreator(ofcFundamentalInformation.getCreator());
        ofcWarehouseInformation.setOperTime(ofcFundamentalInformation.getOperTime());
        ofcWarehouseInformation.setOperator(ofcFundamentalInformation.getOperator());
    }

    /**
     * 下单或编辑时在订单中心为用户保存订单中的仓库信息
     */
    private String saveWarehouseMessage(OfcWarehouseInformation ofcWarehouseInformation){
        if(null == ofcWarehouseInformation){
            return "未添加仓库信息";
        }
        try {
            int save = ofcWarehouseInformationService.save(ofcWarehouseInformation);
            if(save == 0){
                throw new BusinessException("保存仓库信息失败");
            }
        }catch (Exception ex){
            throw new BusinessException("保存仓库信息失败!", ex);
        }
        return Wrapper.SUCCESS_MESSAGE;
    }

    /**
     * 城配开单下单
     * @param ofcFundamentalInformation 订单基本信息
     * @param ofcGoodsDetailsInfos 货品信息
     * @param ofcDistributionBasicInfo 运输信息
     * @param ofcWarehouseInformation 仓库信息
     * @param ofcFinanceInformation 财务信息
     * @param custId 客户编码
     * @param cscContantAndCompanyDtoConsignor 发货方信息
     * @param cscContantAndCompanyDtoConsignee 收货方信息
     * @param authResDtoByToken 登录用户
     * @param ofcOrderStatus 订单状态
     * @param ofcMerchandiser 开单员
     */
    private void distributionOrderPlace(OfcFundamentalInformation ofcFundamentalInformation,List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos,
                                        OfcDistributionBasicInfo ofcDistributionBasicInfo,OfcWarehouseInformation ofcWarehouseInformation,
                                        OfcFinanceInformation ofcFinanceInformation,String custId,CscContantAndCompanyDto cscContantAndCompanyDtoConsignor,
                                        CscContantAndCompanyDto cscContantAndCompanyDtoConsignee,AuthResDto authResDtoByToken,
                                        OfcOrderStatus ofcOrderStatus,OfcMerchandiser ofcMerchandiser) {
        int custOrderCode = 0;
        StringBuilder notes = new StringBuilder();
        if(!PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getCustOrderCode())){
            custOrderCode = ofcFundamentalInformationService.checkCustOrderCode(ofcFundamentalInformation);
        }

        //根据客户订单编号查询唯一性
        if (custOrderCode > 1) {
            throw new BusinessException("客户订单编号" + ofcFundamentalInformation.getCustOrderCode() + "已经存在!您不能重复下单!");
        } else {

            ofcFundamentalInformation.setOrderCode(codeGenUtils.getNewWaterCode(ORDER_PRE, 6));
            ofcFundamentalInformation.setCustCode(custId);

            if (PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getCustName())) {
                QueryCustomerCodeDto queryCustomerCodeDto = new QueryCustomerCodeDto();
                queryCustomerCodeDto.setCustomerCode(custId);
                Wrapper<CscCustomerVo> cscCustomerVo = cscCustomerEdasService.queryCustomerByCustomerCodeOrId(queryCustomerCodeDto);
                if (Wrapper.ERROR_CODE == cscCustomerVo.getCode()) {
                    throw new BusinessException(cscCustomerVo.getMessage());
                } else if (null == cscCustomerVo.getResult()) {
                    throw new BusinessException("客户中心没有查到该客户!");
                }
                ofcFundamentalInformation.setCustName(cscCustomerVo.getResult().getCustomerName());
            }
            ofcFundamentalInformation.setAbolishMark(ORDER_WASNOT_ABOLISHED);//未作废
            //添加该订单的货品信息 modify by wangst 做抽象处理
            BigDecimal goodsAmountCount = saveDetails(ofcGoodsDetailsInfos, ofcFundamentalInformation);
            ofcDistributionBasicInfo.setQuantity(goodsAmountCount);

            String orderType = ofcFundamentalInformation.getOrderType();
            if (PubUtils.isOEmptyOrNull(orderType)) {
                throw new BusinessException("您选择的订单类型编码为空!");
            } else {
                if (OrderConstant.WAREHOUSE_DIST_ORDER.equals(orderType)) {
                    if (null == ofcWarehouseInformation.getProvideTransport()) {
                        ofcWarehouseInformation.setProvideTransport(WAREHOUSE_NO_TRANS);
                    }
                    if (Objects.equals(ofcWarehouseInformation.getProvideTransport(), WEARHOUSE_WITH_TRANS)) {
                        String consingneeSerialNo = cscContantAndCompanyDtoConsignee.getCscContactDto().getSerialNo();
                        if (null == consingneeSerialNo) {
                            throw new BusinessException("该收货方联系人编码为空");
                        }
                        cscContantAndCompanyDtoConsignee.getCscContactDto().setSerialNo(consingneeSerialNo.split("@")[0]);
                        Wrapper<?> wrapper = ofcDistributionBasicInfoService.validateDistrictContactMessage(cscContantAndCompanyDtoConsignor, cscContantAndCompanyDtoConsignee);
                        if (Wrapper.ERROR_CODE == wrapper.getCode()) {
                            throw new BusinessException(wrapper.getMessage());
                        }

                        addDistributionInfo(ofcDistributionBasicInfo, ofcFundamentalInformation);
                    }
                    // 更新仓配信息
                    upOfcWarehouseInformation(ofcWarehouseInformation, ofcFundamentalInformation);
                    String businessTypeHead = ofcFundamentalInformation.getBusinessType().substring(0, 2);
                    if ("62".equals(businessTypeHead)) {//如果是入库才有供应商信息//这儿才是入库
                        logger.info("这儿才是入库");
                    }
                    ofcWarehouseInformationService.save(ofcWarehouseInformation);
                    if ("61".equals(businessTypeHead)) {//如果是入库才有供应商信息//这儿是出库
                        ofcWarehouseInformation.setSupportCode("");
                        ofcWarehouseInformation.setSupportName("");
                    }
                } else if (TRANSPORT_ORDER.equals(orderType)) {
                    Wrapper<?> wrapper = ofcDistributionBasicInfoService.validateDistrictContactMessage(cscContantAndCompanyDtoConsignor, cscContantAndCompanyDtoConsignee);
                    if (Wrapper.ERROR_CODE == wrapper.getCode()) {
                        throw new BusinessException(wrapper.getMessage());
                    }

                    //运输订单
                    //设置城配
                    ofcFundamentalInformation.setBusinessType(OrderConstConstant.WITH_THE_CITY);
                    //保存运输信息
                    addDistributionInfo(ofcDistributionBasicInfo, ofcFundamentalInformation);
                } else {
                    throw new BusinessException("您选择的订单类型系统无法识别!");
                }
            }
            notes.append(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1));
            notes.append(" 订单已创建");
            notes.append(" 操作人: ").append(authResDtoByToken.getUserName());
            notes.append(" 操作单位: ").append(authResDtoByToken.getGroupRefName());
            ofcOrderStatus.setNotes(notes.toString());
            upOrderStatus(ofcOrderStatus, ofcFundamentalInformation, authResDtoByToken);
            addFinanceInformation(ofcFinanceInformation, ofcFundamentalInformation);
            //添加基本信息
            ofcFundamentalInformationService.save(ofcFundamentalInformation);
            if (ofcMerchandiserService.select(ofcMerchandiser).size() == 0 && !PubUtils.trimAndNullAsEmpty(ofcMerchandiser.getMerchandiser()).equals("")) {
                ofcMerchandiserService.save(ofcMerchandiser);
            }
            if (!PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getOrderBatchNumber())) {
                //进行自动审核
                String code = ofcOrderManageService.orderAutoAudit(ofcFundamentalInformation, ofcGoodsDetailsInfos, ofcDistributionBasicInfo,
                    ofcWarehouseInformation, ofcFinanceInformation, ofcOrderStatus.getOrderStatus(), REVIEW, authResDtoByToken);
                if(StringUtils.equals(String.valueOf(Wrapper.ERROR_CODE),code)){
                    throw new BusinessException("自动审核操作失败!");
                }
            }
            //城配开单订单推结算中心
            ofcOrderManageService.pushOrderToAc(ofcFundamentalInformation,ofcFinanceInformation,ofcDistributionBasicInfo,ofcGoodsDetailsInfos);
        }
    }

    /**
     * 运输开单编辑
     * @param ofcOrderDTO 订单实体
     * @param ofcGoodsDetailsInfos 货品信息
     * @param authResDtoByToken 登录用户
     * @param cscContantAndCompanyDtoConsignor 收货方
     * @param cscContantAndCompanyDtoConsignee 发货方
     * @param ofcFundamentalInformation 基本信息
     * @param ofcDistributionBasicInfo 运输信息
     * @param ofcWarehouseInformation 仓库信息
     * @param ofcOrderStatus 订单状态
     */
    private void orderTransPlaceTagManage(OfcOrderDTO ofcOrderDTO, List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos, AuthResDto authResDtoByToken,
                                          CscContantAndCompanyDto cscContantAndCompanyDtoConsignor, CscContantAndCompanyDto cscContantAndCompanyDtoConsignee,
                                          OfcFundamentalInformation ofcFundamentalInformation, OfcDistributionBasicInfo ofcDistributionBasicInfo,
                                          OfcWarehouseInformation ofcWarehouseInformation, OfcOrderStatus ofcOrderStatus) {
        String orderType = ofcFundamentalInformation.getOrderType();
        if(TRANSPORT_ORDER.equals(orderType)){
            Wrapper<?> wrapper = ofcDistributionBasicInfoService.validateDistrictContactMessage(cscContantAndCompanyDtoConsignor, cscContantAndCompanyDtoConsignee);
            if(Wrapper.ERROR_CODE == wrapper.getCode()){
                throw new BusinessException(wrapper.getMessage());
            }
            OfcDistributionBasicInfo ofcDist = new OfcDistributionBasicInfo();
            ofcDist.setOrderCode(ofcFundamentalInformation.getOrderCode());
            List<OfcDistributionBasicInfo> select = ofcDistributionBasicInfoService.select(ofcDist);
            if(select.size() > 0){//有运输信息
                ofcDistributionBasicInfoService.updateAddressByOrderCode(ofcDistributionBasicInfo);
            }
            //2017年3月25日 modified by lyh 增加逻辑: 编辑后将之前无法识别的地址信息匹配表补充完整
            ofcOrderManageService.fixAddressWhenEdit(ORDER_TAG_STOCK_EDIT, ofcDistributionBasicInfo);
        }else{
            throw new BusinessException("您的订单类型系统无法识别!");
        }
        OfcFundamentalInformation fundamentalInformation = new OfcFundamentalInformation();
        fundamentalInformation.setOrderCode(ofcFundamentalInformation.getOrderCode());
        fundamentalInformation.setOperator(authResDtoByToken.getUserId());
        fundamentalInformation.setOperatorName(authResDtoByToken.getUserName());
        fundamentalInformation.setOperTime(new Date());
        ofcOrderStatus.setNotes(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1) +" "+"订单已更新");
        upOrderStatus(ofcOrderStatus,fundamentalInformation,authResDtoByToken);
        ofcFundamentalInformationService.update(fundamentalInformation);

        // 更新大区基地
        updateOrderAreaAndBase(ofcFundamentalInformation, ofcDistributionBasicInfo, fundamentalInformation);

    }

    /**
     * 更新大区基地
     * @param ofcFundamentalInformation
     * @param ofcDistributionBasicInfo
     * @param fundamentalInformation
     */
    private void updateOrderAreaAndBase(OfcFundamentalInformation ofcFundamentalInformation, OfcDistributionBasicInfo ofcDistributionBasicInfo, OfcFundamentalInformation fundamentalInformation) {
        // 更新大区基地
        String departurePlaceCode = ofcDistributionBasicInfo.getDeparturePlaceCode();
        String departureProvince = ofcDistributionBasicInfo.getDepartureProvince();
        String destinationProvince = ofcDistributionBasicInfo.getDestinationProvince();
        String departureCity = ofcDistributionBasicInfo.getDepartureCity();
        String destinationCity = ofcDistributionBasicInfo.getDestinationCity();
        logger.info("=-=========================== 订单编辑: departurePlaceCode = {}, departureProvince = {}, departureCity = {}, destinationProvince = {}, destinationCity = {}",
            departurePlaceCode, departureProvince, departureCity, destinationProvince, destinationCity);
        // 如果大区基地为空则更新
        if (!PubUtils.isSEmptyOrNull(departurePlaceCode) && !PubUtils.isSEmptyOrNull(departureProvince)
            && !PubUtils.isSEmptyOrNull(departureCity) && !PubUtils.isSEmptyOrNull(destinationProvince) && !PubUtils.isSEmptyOrNull(destinationCity)) {
            String baseCode = fundamentalInformation.getBaseCode();
            String areaCode = fundamentalInformation.getAreaCode();
            logger.info("================================ 更新大区基地: baseCode = {}, areaCode = {}", baseCode, areaCode);
            if (PubUtils.isOEmptyOrNull(baseCode) || PubUtils.isOEmptyOrNull(areaCode)) {
                ofcOrderManageService.updateOrderAreaAndBase(ofcFundamentalInformation, ofcDistributionBasicInfo);
            }
        } else {
            throw new BusinessException("发货方地址不能为空!");
        }
    }
}
