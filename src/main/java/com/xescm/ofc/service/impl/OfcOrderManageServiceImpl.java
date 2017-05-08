package com.xescm.ofc.service.impl;

import com.xescm.ac.domain.AcDistributionBasicInfo;
import com.xescm.ac.domain.AcFinanceInformation;
import com.xescm.ac.domain.AcFundamentalInformation;
import com.xescm.ac.domain.AcGoodsDetailsInfo;
import com.xescm.ac.model.dto.AcIncomeSettleDTO;
import com.xescm.ac.model.dto.AcOrderDto;
import com.xescm.ac.model.dto.ofc.AcOrderStatusDto;
import com.xescm.ac.model.dto.ofc.AcPlanDto;
import com.xescm.ac.model.dto.ofc.CancelAcOrderDto;
import com.xescm.ac.model.dto.ofc.CancelAcOrderResultDto;
import com.xescm.ac.provider.AcOrderEdasService;
import com.xescm.ac.provider.AcSettleStatisticsEdasService;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.QueryCustomerCodeDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContactCompanyDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContactDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyResponseDto;
import com.xescm.csc.model.vo.CscCustomerVo;
import com.xescm.csc.provider.CscContactEdasService;
import com.xescm.csc.provider.CscCustomerEdasService;
import com.xescm.csc.provider.CscSupplierEdasService;
import com.xescm.ofc.config.MqConfig;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.edas.model.dto.ofc.OfcOrderAccountDTO;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcAddressReflectMapper;
import com.xescm.ofc.model.dto.form.OrderCountForm;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.model.dto.ofc.OfcStorageTemplateDto;
import com.xescm.ofc.model.dto.tfc.TfcTransport;
import com.xescm.ofc.model.dto.tfc.TfcTransportDetail;
import com.xescm.ofc.model.vo.ofc.OfcGroupVo;
import com.xescm.ofc.mq.producer.DefaultMqProducer;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.ofc.utils.DateUtils;
import com.xescm.rmc.edas.domain.dto.RmcWarehouseDto;
import com.xescm.rmc.edas.domain.qo.RmcCompanyLineQO;
import com.xescm.rmc.edas.domain.vo.RmcCompanyLineVo;
import com.xescm.rmc.edas.domain.vo.RmcServiceCoverageForOrderVo;
import com.xescm.rmc.edas.domain.vo.RmcWarehouseRespDto;
import com.xescm.rmc.edas.service.RmcCompanyInfoEdasService;
import com.xescm.rmc.edas.service.RmcServiceCoverageEdasService;
import com.xescm.rmc.edas.service.RmcWarehouseEdasService;
import com.xescm.tfc.edas.model.dto.CancelOrderDTO;
import com.xescm.tfc.edas.model.dto.DeliverEveryRunDTO;
import com.xescm.tfc.edas.service.CancelOrderEdasService;
import com.xescm.tfc.edas.service.TfcQueryEveryDeliveryService;
import com.xescm.uam.model.dto.group.UamGroupDto;
import com.xescm.whc.edas.dto.InventoryDTO;
import com.xescm.whc.edas.dto.OfcCancelOrderDTO;
import com.xescm.whc.edas.service.WhcOrderCancelEdasService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;

import static com.xescm.base.model.wrap.Wrapper.ERROR_CODE;
import static com.xescm.core.utils.PubUtils.isSEmptyOrNull;
import static com.xescm.core.utils.PubUtils.trimAndNullAsEmpty;
import static com.xescm.ofc.constant.GenCodePreffixConstant.BATCH_PRE;
import static com.xescm.ofc.constant.GenCodePreffixConstant.ORDER_PRE;
import static com.xescm.ofc.constant.OrderConstConstant.*;
import static com.xescm.ofc.constant.OrderConstant.TRANSPORT_ORDER;
import static com.xescm.ofc.constant.OrderConstant.WAREHOUSE_DIST_ORDER;
import static com.xescm.ofc.constant.OrderPlaceTagConstant.*;

/**
 * <p>Title:    .订单编辑 </p>
 * <p>Description TODO </p>
 * <p>Company:    http://www.hnxianyi.com </p>
 *
 * @author 杨东旭;
 * @CreateDate 2016/10/12
 */
@Service
@Transactional
public class OfcOrderManageServiceImpl implements OfcOrderManageService {
    private static final Logger logger = LoggerFactory.getLogger(OfcOrderManageServiceImpl.class);

    @Resource
    private OfcOrderStatusService ofcOrderStatusService;
    @Resource
    private RmcCompanyInfoEdasService rmcCompanyInfoEdasService;
    @Resource
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Resource
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Resource
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;
    @Resource
    private OfcWarehouseInformationService ofcWarehouseInformationService;
    @Resource
    private OfcFinanceInformationService ofcFinanceInformationService;
    @Resource
    private CscSupplierEdasService cscSupplierEdasService;
    @Resource
    private RmcServiceCoverageEdasService rmcServiceCoverageEdasService;
    @Resource
    private RmcWarehouseEdasService rmcWarehouseEdasService;
    @Resource
    private CodeGenUtils codeGenUtils;
    @Resource
    private WhcOrderCancelEdasService whcOrderCancelEdasService;
    @Resource
    private DefaultMqProducer defaultMqProducer;
    @Resource
    private CscContactEdasService cscContactEdasService;
    @Resource
    private AcOrderEdasService acOrderEdasService;
    @Resource
    private OfcOrderManageOperService ofcOrderManageOperService;
    @Resource
    private OfcOrderPlaceService ofcOrderPlaceService;
    @Resource
    private CscCustomerEdasService cscCustomerEdasService;
    @Resource
    private OfcMerchandiserService ofcMerchandiserService;
    @Resource
    private CancelOrderEdasService cancelOrderEdasService;
    @Resource
    private OfcOrderStatusService ofcOrdertatusService;
    @Resource
    private OfcAddressReflectService ofcAddressReflectService;
    @Resource
    private OfcAddressReflectMapper ofcAddressReflectMapper;
    @Resource
    private OfcStorageTemplateService ofcStorageTemplateService;
    @Resource
    private  OfcDailyAccountsService ofcDailyAccountsService;
    @Resource
    private AcSettleStatisticsEdasService acSettleStatisticsEdasService;
    @Resource
    private TfcQueryEveryDeliveryService tfcQueryEveryDeliveryService;
    @Resource
    private DefaultMqProducer mqProducer;
    @Resource
    private MqConfig mqConfig;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public Map orderStorageDetails(String orderCode) {
        Map result = new HashMap();
        OfcDistributionBasicInfo ofcDistributionBasicInfo = null;
        OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
        if (ofcFundamentalInformation == null) {
            throw new BusinessException("该订单不存在订单");
        }
        List<OfcGoodsDetailsInfo> goodsDetailsList = ofcGoodsDetailsInfoService.goodsDetailsScreenList(orderCode, "orderCode");
        if (goodsDetailsList == null && goodsDetailsList.size() == 0) {
            throw new BusinessException("该订单不存在货品详情");
        }

        OfcWarehouseInformation ofcWarehouseInformation = ofcWarehouseInformationService.warehouseInformationSelect(orderCode);
        if (ofcWarehouseInformation == null) {
            throw new BusinessException("该订单不存在仓储信息");
        }


        if (ofcWarehouseInformation.getProvideTransport() == WEARHOUSE_WITH_TRANS) {
            ofcDistributionBasicInfo = ofcDistributionBasicInfoService.distributionBasicInfoSelect(orderCode);
            if (ofcDistributionBasicInfo == null) {
                throw new BusinessException("该订单需要提供运输,但是不存在配送基本信息");
            }
        } else {
            ofcDistributionBasicInfo = ofcDistributionBasicInfoService.distributionBasicInfoSelect(orderCode);
        }
        OfcOrderStatus status = ofcOrderStatusService.orderStatusSelect(orderCode, "orderCode");
        if (status == null) {
            throw new BusinessException("订单号不存在任何状态");
        }

        List<OfcOrderStatus> statusLog = ofcOrderStatusService.orderStatusScreen(orderCode, "orderCode");
        result.put("ofcFundamentalInformation", ofcFundamentalInformation);
        result.put("ofcGoodsDetailsInfo", goodsDetailsList);
        result.put("ofcWarehouseInformation", ofcWarehouseInformation);
        result.put("ofcDistributionBasicInfo", ofcDistributionBasicInfo);
        result.put("status", status);
        result.put("statusLog", statusLog);
        return result;

    }

    @Override
    public String auditStorageOrder(String orderCode, String reviewTag, AuthResDto authResDtoByToken) {
        logger.info(" 审核或反审核的订单号为 {}:", orderCode);
        OfcOrderStatus ofcOrderStatus = ofcOrderStatusService.orderStatusSelect(orderCode, "orderCode");
        if (ofcOrderStatus == null) {
            throw new BusinessException("订单号不存在任何状态");
        }
        //反审核
        if (reviewTag.equals(RE_REVIEW)) {
            if (!ofcOrderStatus.getOrderStatus().equals(ALREADY_EXAMINE)) {
                throw new BusinessException("订单编号[" + orderCode + "]不能执行反审核，仅能对订单状态为【已审核】的订单执行反审核操作！");
            } else {
                ofcOrderStatus.setOrderStatus(PENDING_AUDIT);
                ofcOrderStatus.setOperator("");
                ofcOrderStatus.setLastedOperTime(null);
                ofcOrderStatus.setStatusDesc("待审核");
                ofcOrderStatus.setNotes(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1) + " " + "订单反审核完成");
            }
        } else if (reviewTag.equals(REVIEW)) {
            if (ofcOrderStatus.getOrderStatus().equals(PENDING_AUDIT)) {
                ofcOrderStatus.setOrderStatus(ALREADY_EXAMINE);
                ofcOrderStatus.setStatusDesc("已审核");
                ofcOrderStatus.setNotes(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1) + " " + "订单审核完成");
                ofcOrderStatus.setOperator(authResDtoByToken.getUserName());
                ofcOrderStatus.setLastedOperTime(new Date());
                ofcOrderStatusService.save(ofcOrderStatus);

                OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
                ofcFundamentalInformation.setOperator(authResDtoByToken.getUserId());
                ofcFundamentalInformation.setOperatorName(authResDtoByToken.getUserName());
                ofcFundamentalInformation.setOperTime(new Date());
                List<OfcGoodsDetailsInfo> goodsDetailsList = ofcGoodsDetailsInfoService.goodsDetailsScreenList(orderCode, "orderCode");
                OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoService.distributionBasicInfoSelect(orderCode);
                OfcFinanceInformation ofcFinanceInformation = ofcFinanceInformationService.queryByOrderCode(orderCode);
                if (ofcFinanceInformation == null) {
                    ofcFinanceInformation = new OfcFinanceInformation();
                }
                OfcWarehouseInformation ofcWarehouseInformation = ofcWarehouseInformationService.warehouseInformationSelect(orderCode);
                if (ofcWarehouseInformation.getProvideTransport() == WEARHOUSE_WITH_TRANS) {//提供运输
                    pushOrderToTfc(ofcFundamentalInformation, ofcFinanceInformation, ofcDistributionBasicInfo, goodsDetailsList);
                    //仓储订单推仓储中心
                    pushOrderToWhc(ofcFundamentalInformation, goodsDetailsList, ofcWarehouseInformation, ofcFinanceInformation, ofcDistributionBasicInfo);
                } else if (ofcWarehouseInformation.getProvideTransport() == WAREHOUSE_NO_TRANS) {//不提供运输
                    pushOrderToWhc(ofcFundamentalInformation, goodsDetailsList, ofcWarehouseInformation, ofcFinanceInformation, ofcDistributionBasicInfo);
                } else {
                    throw new BusinessException("无法确定是否需要运输");
                }
            } else {
                throw new BusinessException("订单编号[" + orderCode + "]不能执行审核，仅能对订单状态为【待审核】的订单执行审核操作！");
            }

            OfcOrderStatus s=new OfcOrderStatus();
            s.setOrderStatus(IMPLEMENTATION_IN);
            s.setOrderCode(orderCode);
            s.setStatusDesc("执行中");
            s.setNotes(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1) + " " + "订单开始执行");
            s.setOperator(authResDtoByToken.getUserName());
            s.setLastedOperTime(new Date());
            ofcOrderStatusService.save(s);
        }

        return String.valueOf(Wrapper.SUCCESS_CODE);
    }


    /**
     * 订单取消
     *
     * @param orderCode 订单号
     */
    private void orderCancel(String orderCode) {
        logger.info("发起订单取消!");
        logger.info("==> orderCode={}", orderCode);
        OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
        if (null == ofcFundamentalInformation) {
            logger.error("订单取消失败,查不到该订单!");
            throw new BusinessException("订单取消失败,查不到该订单!");
        }
        String orderType = ofcFundamentalInformation.getOrderType();
        OfcWarehouseInformation ofcWarehouse = new OfcWarehouseInformation();
        ofcWarehouse.setOrderCode(orderCode);
        OfcWarehouseInformation ofcWarehouseInformation = ofcWarehouseInformationService.selectOne(ofcWarehouse);
        Wrapper response=null;
        Wrapper whcresponse=null;
        try {
            if (StringUtils.equals(orderType, TRANSPORT_ORDER)) {
                long start = System.currentTimeMillis();
                Wrapper<Integer> cancelStatus = acOrderEdasService.queryOrderCancelStatus(ofcFundamentalInformation.getOrderCode());
                logger.info("=============> 查询结算中心是否取消耗时：" + (System.currentTimeMillis() - start)/1000);
                if (cancelStatus.getCode() == 200) {
                    try {
                        long cancelStart = System.currentTimeMillis();
                        response= orderCancelToTfc(orderCode);
                        logger.info("=============> 结算中心取消耗时：" + (System.currentTimeMillis() - cancelStart)/1000);
                    } catch (Exception e) {
                        logger.info("取消订单，调用TFC取消接口发生异常,返回结果：{}", e.getMessage(), e);
                        throw new BusinessException("调用TFC取消接口发生异常,返回结果：{}", e.getMessage(), e);
                    }
                } else {
                    throw new BusinessException("取消订单失败，结算中心结算单暂时无法取消");
                }
                boolean result = cancelAcOrder(ofcFundamentalInformation.getOrderCode());
                logger.info("订单中心取消订单，调用结算中心取消订单接口,返回结果：{}", result);
            } else if (StringUtils.equals(orderType, WAREHOUSE_DIST_ORDER)) {
                String type="";
                if (PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0,2).equals("61")){
                    //出库
                    type="CK";
                }else if (PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0,2).equals("62")){
                    //入库
                    type="RK";
                }
                if (Objects.equals(ofcWarehouseInformation.getProvideTransport(), YES)) {
                    try {
                        long tfcStart = System.currentTimeMillis();
                        response= orderCancelToTfc(orderCode);
                        logger.info("=============> TFC取消耗时：" + (System.currentTimeMillis() - tfcStart)/1000);
                        logger.info("取消订单，调用TFC取消接口返回结果:{},订单号为:{}",response.getCode(),orderCode);
                        if(response!=null&&response.getCode()==Wrapper.SUCCESS_CODE){
                            whcresponse= orderCancelToWhc(orderCode,type,ofcWarehouseInformation.getWarehouseCode(),ofcFundamentalInformation.getCustCode(),ofcFundamentalInformation.getBusinessType());
                            logger.info("取消订单，调用WHC取消接口返回结果:{},订单号为:{}",whcresponse.getCode(),orderCode);
                        }
                    } catch (Exception e) {
                        logger.info("取消订单，调用TFC取消接口发生异常,返回结果：{}", e.getMessage(), e);
                        throw new BusinessException("调用TFC取消接口发生异常,返回结果：{}", e.getMessage(), e);
                    }
                }else{
                    try {
                        whcresponse= orderCancelToWhc(orderCode,type,ofcWarehouseInformation.getWarehouseCode(),ofcFundamentalInformation.getCustCode(),ofcFundamentalInformation.getBusinessType());
                        logger.info("取消订单，调用WHC取消接口返回结果:{},订单号为:{}",whcresponse.getCode(),orderCode);

                    } catch (Exception e) {
                        logger.info("取消订单，调用WHC取消接口发生异常,返回结果：{}", e.getMessage(), e);
                        throw new BusinessException("调用WHC取消接口发生异常,返回结果：{}", e.getMessage(), e);
                    }
                }

            }
        } catch (Exception e) {
            logger.info("取消订单，调用结算中心取消接口发生异常,返回结果：{}", e.getMessage(), e);
            throw new BusinessException("取消订单，调用结算中心取消接口发生异常,返回结果：{}", e.getMessage(), e);
        }

        if(StringUtils.equals(orderType, TRANSPORT_ORDER)){
            if(response!=null&&response.getCode()==Wrapper.SUCCESS_CODE){
                logger.info("运输订单号{}取消成功!",orderCode);
            }else{
                throw new BusinessException("运输订单取消失败");
            }
        }

        if(StringUtils.equals(orderType, WAREHOUSE_DIST_ORDER)){
            if(Objects.equals(ofcWarehouseInformation.getProvideTransport(), YES)){
                if(whcresponse!=null&&whcresponse.getCode()==Wrapper.SUCCESS_CODE){
                    logger.info("仓储订单提供运输时{}取消成功!",orderCode);
                }else{
                    throw new BusinessException("仓储订单取消失败");
                }
            }else{
                if(whcresponse!=null&&whcresponse.getCode()==Wrapper.SUCCESS_CODE){
                    logger.info("仓储订单不提供运输时{}订单取消成功!",orderCode);
                }else{
                    throw new BusinessException("仓储订单取消失败");
                }
            }
        }
    }

    /**
     * 调用仓储中心取消接口
     *
     * @param orderCode 订单编号
     * @return void
     */
    private Wrapper orderCancelToWhc(String orderCode,String type,String warehouseCode,String customerCode,String orderType) {
        logger.info("调用仓储中心取消接口, 订单号:{}，参数type:{},仓库编码:{}，客户编码:{},业务类型:{}", orderCode,type,warehouseCode,customerCode,orderType);
        OfcCancelOrderDTO cancelOrderDTO=new OfcCancelOrderDTO();
        cancelOrderDTO.setOrderNo(orderCode);
        cancelOrderDTO.setBillType(type);
        cancelOrderDTO.setWarehouseID(warehouseCode);
        cancelOrderDTO.setOrderType(orderType);
        cancelOrderDTO.setCustomerID(customerCode);
        Wrapper response=whcOrderCancelEdasService.cancelOrder(cancelOrderDTO);
        logger.info("取消订单，调用WHC取消接口返回结果:{},订单号为:{}",response.getCode(),orderCode);
        return response;
    }

    /**
     * 调用运输中心取消接口
     *
     * @param orderCode 订单编号
     * @return void
     */
    private Wrapper orderCancelToTfc(String orderCode) {
        logger.info("调用运输中心取消接口, 订单号:{}", orderCode);
        CancelOrderDTO cancelOrderDTO = new CancelOrderDTO();
        cancelOrderDTO.setOrderNo(orderCode);
        Wrapper wrapper=null;
        try {
             wrapper = cancelOrderEdasService.cancelOrder(cancelOrderDTO);

            if (wrapper == null || Wrapper.ERROR_CODE == wrapper.getCode()) {
                logger.error("调用运输中心取消接口取消订单失败,原因:{}", null == wrapper ? "wrapper为null" : wrapper.getMessage());
                throw new BusinessException("取消订单失败,原因:" + (null == wrapper ? "接口异常!" : wrapper.getMessage()));
            }
            logger.info("调用运输中心取消接口, 订单号:{}, 取消成功!", orderCode);
        } catch (BusinessException e) {
            logger.info("调用运输中心取消接口失败!", orderCode);
            throw new BusinessException("取消订单失败,原因:{}", e.getMessage(), e);
        }
        return wrapper;
    }


    /**
     * 订单删除
     *
     * @param orderCode         订单编号
     * @param orderStatus       订单状态
     * @param authResDtoByToken 权限token
     * @return String
     */
    @Override
    public String orderDelete(String orderCode, String orderStatus, AuthResDto authResDtoByToken) {
        if (orderStatus.equals(PENDING_AUDIT)) {
            ofcFundamentalInformationService.deleteByKey(orderCode);
            ofcDistributionBasicInfoService.deleteByOrderCode(orderCode);
            ofcOrderStatusService.deleteByOrderCode(orderCode);
            ofcWarehouseInformationService.deleteByOrderCode(orderCode);
            OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
            ofcGoodsDetailsInfo.setOrderCode(orderCode);
            ofcGoodsDetailsInfoService.delete(ofcGoodsDetailsInfo);
            return String.valueOf(Wrapper.SUCCESS_CODE);
        } else {
            throw new BusinessException("计划单状态不在可删除范围内");
        }
    }

    /**
     * 客户中心订单取消
     *
     * @param orderCode         订单编号
     * @param authResDtoByToken 权限token
     * @return String
     */
    @Override
    public String orderCancel(String orderCode, AuthResDto authResDtoByToken) {
        OfcOrderStatus orderStatus = ofcOrderStatusService.orderStatusSelect(orderCode, "orderCode");
        if (orderStatus == null) {
            throw new BusinessException("订单没有任何状态");
        }
        if ((!trimAndNullAsEmpty(orderStatus.getOrderStatus()).equals(PENDING_AUDIT))
                && (!trimAndNullAsEmpty(orderStatus.getOrderStatus()).equals(HASBEEN_COMPLETED))
                && (!trimAndNullAsEmpty(orderStatus.getOrderStatus()).equals(HASBEEN_CANCELED))) {
            StringBuilder notes = new StringBuilder();
            //调用各中心请求直接取消订单
            try {
                orderCancel(orderCode);
            } catch (Exception e) {
                throw new BusinessException("调用其他中心取消接口异常:{}", e.getMessage(), e);
            }
            OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
            ofcOrderStatus.setOrderCode(orderCode);
            ofcOrderStatus.setOrderStatus(HASBEEN_CANCELED);
            ofcOrderStatus.setStatusDesc("已取消");
            notes.append(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1));
            notes.append(" 订单已取消");
            notes.append(" 操作人: ").append(authResDtoByToken.getUserName());
            notes.append(" 操作单位: ").append(authResDtoByToken.getGroupRefName());
            ofcOrderStatus.setNotes(notes.toString());
            ofcOrderStatus.setOperator(authResDtoByToken.getUserName());
            ofcOrderStatus.setLastedOperTime(new Date());
            ofcOrderStatusService.save(ofcOrderStatus);
            OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
            ofcFundamentalInformation.setOperator(authResDtoByToken.getUserId());
            ofcFundamentalInformation.setOperatorName(authResDtoByToken.getUserName());
            ofcFundamentalInformation.setOperTime(new Date());
            ofcFundamentalInformation.setAbolisher(authResDtoByToken.getUserId());
            ofcFundamentalInformation.setAbolisherName(authResDtoByToken.getUserName());
            ofcFundamentalInformation.setAbolishMark(1);//表明已作废
            ofcFundamentalInformation.setAbolishTime(ofcFundamentalInformation.getOperTime());
            ofcFundamentalInformationService.update(ofcFundamentalInformation);

            return String.valueOf(Wrapper.SUCCESS_CODE);
        } else {
            throw new BusinessException("计划单状态不在可取消范围内");
        }
    }

    /**
     * 取消的订单推送到结算中心进行结算取消
     *
     * @param orderCode 订单编号
     * @return
     */
    @Transactional
    private boolean cancelAcOrder(String orderCode) {
        logger.info("订单中心取消订单，调用结算中心取消订单接口==>订单编号:{}", orderCode);
        OfcDistributionBasicInfo ofcDistributionBasicInfo = new OfcDistributionBasicInfo();
        ofcDistributionBasicInfo.setOrderCode(orderCode);
        CancelAcOrderDto cancelOfcOrderDto = new CancelAcOrderDto();
        cancelOfcOrderDto.setOrderCode(orderCode);
        ofcDistributionBasicInfo = ofcDistributionBasicInfoService.selectOne(ofcDistributionBasicInfo);
        if (ofcDistributionBasicInfo != null) {
            cancelOfcOrderDto.setTransCode(ofcDistributionBasicInfo.getTransCode());
        }
        Wrapper<CancelAcOrderResultDto> wrapper = acOrderEdasService.cancelOfcOrder(cancelOfcOrderDto);
        logger.info("订单中心取消订单，调用结算中心取消订单接口==>订单编号:{}, 返回信息", orderCode, ToStringBuilder.reflectionToString(wrapper));
        if (wrapper == null) {
            logger.error("订单取消异常，调用结算中心取消接口失败");
            throw new BusinessException("订单取消异常");
        }
        CancelAcOrderResultDto cancelAcOrderResultDto = wrapper.getResult();
        if (cancelAcOrderResultDto == null) {
            logger.error("订单取消异常，调用结算中心取消接口失败,返回结果为空");
            throw new BusinessException("订单取消异常");
        }
        if (!cancelAcOrderResultDto.isResult()) {
            logger.error("订单取消异常，调用结算中心取消接口失败,返回结果:{}", ToStringBuilder.reflectionToString(cancelAcOrderResultDto));
            throw new BusinessException("订单取消失败：" + cancelAcOrderResultDto.getNotes());
        }
        return true;
    }

    /**
     * 调用CSC返回收发货人信息
     *
     * @param contactCompanyName 发货人
     * @param contactName        发货方联系人
     * @param purpose            收/发货方
     * @param customerCode       客户编号
     * @param authResDtoByToken  权限token
     * @return CscContantAndCompanyResponseDto
     */
    @Override
    public CscContantAndCompanyResponseDto getContactMessage(String contactCompanyName, String contactName, String purpose
            , String customerCode, AuthResDto authResDtoByToken) {
        //Map<String,Object> map = new HashMap<String,Object>();
        CscContantAndCompanyDto cscContantAndCompanyDto = new CscContantAndCompanyDto();
        cscContantAndCompanyDto.setCscContactDto(new CscContactDto());
        cscContantAndCompanyDto.setCscContactCompanyDto(new CscContactCompanyDto());
        cscContantAndCompanyDto.getCscContactDto().setPurpose(purpose);
        cscContantAndCompanyDto.getCscContactDto().setContactName(contactName);
        cscContantAndCompanyDto.getCscContactCompanyDto().setContactCompanyName(contactCompanyName);
        cscContantAndCompanyDto.setCustomerCode(customerCode);
        Wrapper<List<CscContantAndCompanyResponseDto>> listWrapper;
        try {
            listWrapper = cscContactEdasService.queryCscReceivingInfoList(cscContantAndCompanyDto);
            if (null == listWrapper.getResult()) {
                throw new BusinessException("接口返回结果为null");
            }
            if (ERROR_CODE == listWrapper.getCode()) {
                throw new BusinessException(listWrapper.getMessage());
            }
        } catch (Exception ex) {
            throw new BusinessException(ex.getMessage(), ex);
        }
        if (listWrapper.getResult().size() < 1) {
            if (CONTACT_PURPOSE_CONSIGNOR.equals(purpose)) {
                throw new BusinessException("没有查到该发货人的信息!");
            } else if (CONTACT_PURPOSE_CONSIGNEE.equals(purpose)) {
                throw new BusinessException("没有查到该收货人的信息!");
            }
        }
        listWrapper.getResult().get(0);
        listWrapper.getResult().get(0);
        return listWrapper.getResult().get(0);
    }

    /**
     * 调用CSC返回供应商信息
     *
     * @param supportName        供应商
     * @param supportContactName 供应商联系人
     * @param customerCode       客户编号
     * @param authResDtoByToken  权限token
     * @return CscSupplierInfoDto
     */
    @Override
    public CscSupplierInfoDto getSupportMessage(String supportName, String supportContactName, String customerCode, AuthResDto authResDtoByToken) {
        CscSupplierInfoDto cscSupplierInfoDto = new CscSupplierInfoDto();
        cscSupplierInfoDto.setSupplierName(supportName);
        cscSupplierInfoDto.setContactName(supportContactName);
        cscSupplierInfoDto.setCustomerCode(customerCode);
        Wrapper<List<CscSupplierInfoDto>> listWrapper;
        try {
            listWrapper = cscSupplierEdasService.querySupplierByAttribute(cscSupplierInfoDto);
            if (null == listWrapper.getResult()) {
                throw new BusinessException("查询供应商接口返回结果为null");
            }
        } catch (Exception ex) {
            throw new BusinessException(ex.getMessage(), ex);
        }
        if (listWrapper.getResult().size() < 1) {
            throw new BusinessException("没有查到该供应商的信息!");
        }
        if (Objects.equals(ERROR_CODE, listWrapper.getCode())) {
            throw new BusinessException("查询供应商信息错误!");
        }
        return listWrapper.getResult().get(0);
    }


    /**
     * 查询服务商
     *
     * @param rmcCompanyLineQO 服务商实体
     * @return RmcCompanyLineVo
     */
    @Override
    public Wrapper<List<RmcCompanyLineVo>> companySelByApi(RmcCompanyLineQO rmcCompanyLineQO) {
        new Wrapper<List<RmcCompanyLineVo>>();
        Wrapper<List<RmcCompanyLineVo>> rmcCompanyLists;
        try {
            rmcCompanyLists = rmcCompanyInfoEdasService.queryCompanyLine(rmcCompanyLineQO);
        } catch (Exception ex) {
            throw new BusinessException("服务商查询出错", ex);
        }
        return rmcCompanyLists;
    }


    /**
     * 更新创单接口订单和钉钉录单大区基地信息
     *
     * @param ofcFundamentalInformation 订单基本信息
     */
    public void updateOrderAreaAndBase(OfcFundamentalInformation ofcFundamentalInformation, OfcDistributionBasicInfo ofcDistributionBasicInfo) {
        logger.info("更新创单接口订单和钉钉录单大区基地信息 ofcFundamentalInformation : {}", ofcFundamentalInformation);
        logger.info("更新创单接口订单和钉钉录单大区基地信息 ofcDistributionBasicInfo : {}", ofcDistributionBasicInfo);
        RmcServiceCoverageForOrderVo rmcServiceCoverageForOrderVo = new RmcServiceCoverageForOrderVo();
        rmcServiceCoverageForOrderVo = this.copyDestinationPlace(ofcDistributionBasicInfo.getDeparturePlaceCode(), rmcServiceCoverageForOrderVo);
        RmcServiceCoverageForOrderVo rmcPickup = this.rmcServiceCoverageAPI(rmcServiceCoverageForOrderVo, "Pickup");
        this.updateOrderAreaAndBase(rmcPickup, ofcFundamentalInformation);
    }

    @Override
    public Wrapper storageOrderConfirm(List<OfcStorageTemplateDto> ofcStorageTemplateDtoList, AuthResDto authResDto) {
        logger.info("仓储批量下单 ==> ofcStorageTemplateDtoList:{}", ofcStorageTemplateDtoList);
        logger.info("仓储批量下单 ==> authResDto:{}", authResDto);
        Map<String, List<OfcStorageTemplateDto>> orderMap = new HashMap<>();
        for (OfcStorageTemplateDto ofcStorageTemplateDto : ofcStorageTemplateDtoList) {
            if(null == ofcStorageTemplateDto){
                logger.info("仓储开单批量导单确认下单, 订单信息为空! ");
                continue;
            }
            String custOrderCode = ofcStorageTemplateDto.getCustOrderCode();
            List<OfcStorageTemplateDto> orderListByCustOrderCode = orderMap.get(custOrderCode);
            if(!orderMap.containsKey(custOrderCode) && orderListByCustOrderCode == null){
                logger.info("初始化");
                orderListByCustOrderCode = new ArrayList<>();
//                orderMap.put(custOrderCode, orderListByCustOrderCode);
            }
            orderListByCustOrderCode.add(ofcStorageTemplateDto);
            orderMap.put(custOrderCode, orderListByCustOrderCode);
        }
        String orderBatchNumber = codeGenUtils.getNewWaterCode(BATCH_PRE,4);

        for (String orderMapKey : orderMap.keySet()) {

            List<OfcStorageTemplateDto> order = orderMap.get(orderMapKey);
            OfcOrderDTO ofcOrderDTO = new OfcOrderDTO();
            OfcStorageTemplateDto forOrderMsg = order.get(0);
            logger.info("forOrderMsg------, {}", ToStringBuilder.reflectionToString(forOrderMsg));
            org.springframework.beans.BeanUtils.copyProperties(forOrderMsg.getOfcOrderDTO(), ofcOrderDTO);
            org.springframework.beans.BeanUtils.copyProperties(forOrderMsg, ofcOrderDTO, "orderTime");
            ofcOrderDTO.setOrderTime(ofcStorageTemplateService.convertStringToDate(forOrderMsg.getOrderTime()));
            if(!PubUtils.isSEmptyOrNull(forOrderMsg.getProvideTransport())){
                ofcOrderDTO.setProvideTransport(Integer.valueOf(forOrderMsg.getProvideTransport()));
            }
            logger.info("ofcOrderDTO------, {}", ToStringBuilder.reflectionToString(ofcOrderDTO));
            //在这里将订单信息补充完整
            ofcOrderDTO.setOrderBatchNumber(orderBatchNumber);
            ofcOrderDTO.setOrderType(WAREHOUSE_DIST_ORDER);
            if(ofcOrderDTO.getProvideTransport() == null){
                ofcOrderDTO.setProvideTransport(0);
            }
            List<OfcGoodsDetailsInfo> detailsInfos = new ArrayList<>();
            for (OfcStorageTemplateDto ofcStorageTemplateDto : order) {
                OfcGoodsDetailsInfo ofcGoodsDetailsInfo = ofcStorageTemplateService.convertCscGoods(ofcStorageTemplateDto);
                detailsInfos.add(ofcGoodsDetailsInfo);
            }
            CscContantAndCompanyDto cscConsignorDto = ofcStorageTemplateService.convertCscConsignor(forOrderMsg.getConsignor());
            CscContantAndCompanyDto cscConsigneeDto = ofcStorageTemplateService.convertCscConsignee(forOrderMsg.getCscConsigneeDto());
            ofcStorageTemplateService.convertConsignorToDis(forOrderMsg.getConsignor(), ofcOrderDTO);
            ofcStorageTemplateService.convertConsigneeToDis(forOrderMsg.getCscConsigneeDto(), ofcOrderDTO);
            ofcStorageTemplateService.convertSupplierToWare(forOrderMsg.getCscSupplierInfoDto(), ofcOrderDTO);
            Wrapper save = this.saveStorageOrder(ofcOrderDTO, detailsInfos, ORDER_TAG_STOCK_IMPORT
                    , cscConsignorDto, cscConsigneeDto, forOrderMsg.getCscSupplierInfoDto(), authResDto);
            if(save.getCode() == Wrapper.ERROR_CODE){
                logger.error("仓储开单批量导单确认下单失败, 错误信息:{}", save.getMessage());
                return save;
            }
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, orderBatchNumber);
    }


    /**
     * 调用区域覆盖接口
     *
     * @param rmcServiceCoverageForOrderVo 区域覆盖vo
     * @param tag                          判断是上门提货还是二次配送
     * @return RmcServiceCoverageForOrderVo
     */
    @Override
    public RmcServiceCoverageForOrderVo rmcServiceCoverageAPI(RmcServiceCoverageForOrderVo rmcServiceCoverageForOrderVo, String tag) {
        //先判断是上门提货还是二次配送
        if (trimAndNullAsEmpty(tag).equals("Pickup")) {
            rmcServiceCoverageForOrderVo.setIsPickup(1);
            rmcServiceCoverageForOrderVo.setIsDispatch(2);//取货不配送
            logger.info("#################################取货不配送,调用区域覆盖接口#######################");
        } else if (trimAndNullAsEmpty(tag).equals("TwoDistribution")) {
            rmcServiceCoverageForOrderVo.setIsPickup(2);
            rmcServiceCoverageForOrderVo.setIsDispatch(1);//配送不提货
            logger.info("#################################配送不提货,调用区域覆盖接口#######################");
        } else {
            throw new BusinessException("缺少提货或配送标志位");
        }
        Wrapper<List<RmcServiceCoverageForOrderVo>> rmcServiceCoverageList = rmcServiceCoverageEdasService.queryServiceCoverageListForOrder(rmcServiceCoverageForOrderVo);
        if (rmcServiceCoverageList != null && PubUtils.isNotNullAndBiggerSize(rmcServiceCoverageList.getResult(), 0)) {
            logger.info("#####################接口返回数据为：{}###########################", rmcServiceCoverageList.getResult().get(0).toString());
            return rmcServiceCoverageList.getResult().get(0);
        } else {
            logger.info("#####################接口返回数据为：{}###########################", "");
            return null;
        }
    }

    /**
     * 根据仓库编码获取仓库信息
     *
     * @param wareHouseCode 仓库编码
     * @return RmcWarehouseRespDto
     */
    private RmcWarehouseRespDto getWareHouseByCodeToPlan(String wareHouseCode) {
        RmcWarehouseDto rmcWarehouse = new RmcWarehouseDto();
        if (!trimAndNullAsEmpty(wareHouseCode).equals("")) {
            rmcWarehouse.setWarehouseCode(wareHouseCode);
//            rmcWarehouse.setState(1); //调用Rmc接口查询仓库明细,增加入参, 1为仓库有效
            Wrapper<RmcWarehouseRespDto> rmcWarehouseByid = rmcWarehouseEdasService.queryRmcWarehouseByCode(rmcWarehouse);
            return rmcWarehouseByid.getResult();
        } else {
            throw new BusinessException("仓库编码传值为空");
        }
    }

    /**
     * 拷贝方法，用于将四级地址编码split操作后分别存入区域覆盖VO中对应的字段
     *
     * @param addressCode                  地址编号，为以逗号分隔的四级地址
     * @param rmcServiceCoverageForOrderVo 区域覆盖vo
     * @return RmcServiceCoverageForOrderVo
     */
    @Override
    public RmcServiceCoverageForOrderVo copyDestinationPlace(String addressCode, RmcServiceCoverageForOrderVo rmcServiceCoverageForOrderVo) {
        logger.info("拷贝方法，用于将四级地址编码split操作后分别存入区域覆盖VO中对应的字段 addressCode : {}", addressCode);
        logger.info("拷贝方法，用于将四级地址编码split操作后分别存入区域覆盖VO中对应的字段 rmcServiceCoverageForOrderVo : {}", rmcServiceCoverageForOrderVo);
        if (PubUtils.isSEmptyOrNull(addressCode)) {
            logger.error("拷贝方法，用于将四级地址编码split操作后分别存入区域覆盖VO中对应的字段 addressCode 为空");
            throw new BusinessException("四级地址编码为空");
        }
        rmcServiceCoverageForOrderVo = new RmcServiceCoverageForOrderVo();
        String address[] = addressCode.split(",");
        if (address.length >= 1) {
            if (!trimAndNullAsEmpty(address[0]).equals("")) {
                rmcServiceCoverageForOrderVo.setProvinceCode(address[0]);
            }
            if (address.length >= 2) {
                if (!trimAndNullAsEmpty(address[1]).equals("")) {
                    rmcServiceCoverageForOrderVo.setCityCode(address[1]);
                }
                if (address.length >= 3) {
                    if (!trimAndNullAsEmpty(address[2]).equals("")) {
                        rmcServiceCoverageForOrderVo.setDistrictCode(address[2]);
                    }
                    if (address.length == 4) {
                        if (!trimAndNullAsEmpty(address[3]).equals("")) {
                            rmcServiceCoverageForOrderVo.setStreetCode(address[3]);
                        }
                    }
                }
            }
        }
        return rmcServiceCoverageForOrderVo;
    }

    /**
     * 订单信息推送结算中心
     *
     * @param ofcFundamentalInformation 订单基本信息
     * @param ofcFinanceInformation     费用基本信息
     * @param ofcDistributionBasicInfo  运输基本信息
     * @param ofcGoodsDetailsInfos      货品明细
     */
    @Override
    public void pushOrderToAc(OfcFundamentalInformation ofcFundamentalInformation, OfcFinanceInformation ofcFinanceInformation
            , OfcDistributionBasicInfo ofcDistributionBasicInfo, List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos, OfcWarehouseInformation ofcWarehouseInformation) {
        logger.info("订单信息推送结算中心 == > ofcFundamentalInformation{}", ofcFundamentalInformation);
        logger.info("订单信息推送结算中心 == > ofcFinanceInformation{}", ofcFinanceInformation);
        logger.info("订单信息推送结算中心 == > ofcDistributionBasicInfo{}", ofcDistributionBasicInfo);
        logger.info("订单信息推送结算中心 == > ofcGoodsDetailsInfos{}", ofcGoodsDetailsInfos);
        logger.info("订单信息推送结算中心 == > ofcWarehouseInformation{}", ofcWarehouseInformation);
        AcOrderDto acOrderDto = new AcOrderDto();
        try {
            // 转换Ac实体
            AcFundamentalInformation acFundamentalInformation = new AcFundamentalInformation();
            BeanUtils.copyProperties(acFundamentalInformation, ofcFundamentalInformation);

            AcFinanceInformation acFinanceInformation = new AcFinanceInformation();
            BeanUtils.copyProperties(acFinanceInformation, ofcFinanceInformation);

            AcDistributionBasicInfo acDistributionBasicInfo = new AcDistributionBasicInfo();
            BeanUtils.copyProperties(acDistributionBasicInfo, ofcDistributionBasicInfo);

            List<AcGoodsDetailsInfo> acGoodsDetailsInfoList = new ArrayList<>();
            for (OfcGoodsDetailsInfo ofcGoodsDetailsInfo : ofcGoodsDetailsInfos) {
                AcGoodsDetailsInfo acGoodsDetailsInfo = new AcGoodsDetailsInfo();
                BeanUtils.copyProperties(acGoodsDetailsInfo, ofcGoodsDetailsInfo);
                acGoodsDetailsInfoList.add(acGoodsDetailsInfo);
            }
            if (acGoodsDetailsInfoList.size() < 1) {
                throw new BusinessException("订单货品明细不能为空!");
            }
            acOrderDto.setAcFundamentalInformation(acFundamentalInformation);
            acOrderDto.setAcFinanceInformation(acFinanceInformation);
            acOrderDto.setAcDistributionBasicInfo(acDistributionBasicInfo);
            acOrderDto.setAcGoodsDetailsInfoList(acGoodsDetailsInfoList);
            if (null != ofcWarehouseInformation && null != ofcWarehouseInformation.getProvideTransport()) {
                acOrderDto.setProvideTransport(ofcWarehouseInformation.getProvideTransport().toString());
            }
            try {
                String orderCode = ofcFundamentalInformation.getOrderCode();
                String orderInfo = JacksonUtil.toJson(acOrderDto);
                // 推送结算
                boolean isSend = mqProducer.sendMsg(orderInfo, mqConfig.getOfc2AcOrderTopic(), orderCode, "xeOrderToAc");
                if (isSend) {
                    logger.info("订单中心推送结算中心成功，订单号：{}", orderCode);
                } else {
                    logger.info("订单中心推送结算中心失败，订单号：{}", orderCode);
                }
            } catch (Exception e) {
                logger.error("订单中心推送结算订单转换发生错误, 异常： {}", e);
                throw new BusinessException("订单中心推送结算订单转换发生错误!");
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            logger.error("订单信息推送结算中心 转换异常, {}", e);
        }
    }

    /**
     * <p>Title:    .更新 </p>
     * <p>Description 订单的大区和基地</p>
     *
     * @param rmcPickup                 区域覆盖返回
     * @param ofcFundamentalInformation 订单基本信息
     * @return void
     * @author 杨东旭
     * @CreateDate 2017/2/3
     */
    private void updateOrderAreaAndBase(RmcServiceCoverageForOrderVo rmcPickup, OfcFundamentalInformation ofcFundamentalInformation) {
        logger.info("更新订单的大区和基地 rmcPickup :{}", rmcPickup);
        logger.info("更新订单的大区和基地 ofcFundamentalInformation :{}", ofcFundamentalInformation);
        if (null == rmcPickup) {
            logger.error("区域覆盖结果rmcPickup为空");
            return;
        }
        OfcFundamentalInformation ofcInfo = new OfcFundamentalInformation();
        UamGroupDto dto = new UamGroupDto();
        dto.setSerialNo(rmcPickup.getSerialNo());
        OfcGroupVo vo = ofcOrderManageOperService.queryAreaMsgByBase(dto);
        if (vo != null) {
            ofcInfo.setAreaCode(vo.getSerialNo());
            ofcFundamentalInformation.setAreaCode(vo.getSerialNo());
            ofcInfo.setAreaName(vo.getGroupName());
            ofcFundamentalInformation.setAreaName(vo.getGroupName());
        }
        ofcInfo.setBaseCode(rmcPickup.getSerialNo());
        ofcFundamentalInformation.setBaseCode(rmcPickup.getSerialNo());
        ofcInfo.setBaseName(rmcPickup.getBaseName());
        ofcFundamentalInformation.setBaseName(rmcPickup.getBaseName());
        String orderCode = ofcFundamentalInformation.getOrderCode();
        if (PubUtils.isSEmptyOrNull(orderCode)) {
            logger.info("普通下单");
            return;
        }
        ofcInfo.setOrderCode(orderCode);
        int update = ofcFundamentalInformationService.update(ofcInfo);
        if (update == 0) {
            logger.error("更新订单的大区和基地失败!");
            throw new BusinessException("更新订单的大区和基地失败!");
        }
    }

    /**
     * 订单中心--订单状态推结算中心(执行中和已完成)
     *
     * @param ofcOrderStatus 订单状态
     * @return void
     */
    public void pullOfcOrderStatus(OfcOrderStatus ofcOrderStatus) {
        logger.info("订单中心--订单状态推结算中心(执行中和已完成) ofcOrderStatus:{}", ofcOrderStatus);
        if (PubUtils.isNull(ofcOrderStatus)) {
            logger.error("订单状态推结算中心异常");
            throw new BusinessException("订单状态推结算中心异常");
        }
        AcOrderStatusDto acOrderStatusDto = new AcOrderStatusDto();
        logger.info("订单状态开始推结算中心 acOrderStatusDto{}", acOrderStatusDto);
        try {
            BeanUtils.copyProperties(acOrderStatusDto, ofcOrderStatus);
        } catch (Exception e) {
            logger.error("订单状态开始推结算中心 实体转换异常");
            throw new BusinessException("订单状态开始推结算中心 实体转换异常");
        }
        try {
            Wrapper<Integer> integerWrapper = acOrderEdasService.pullOfcOrderStatus(acOrderStatusDto);
            if (null == integerWrapper || integerWrapper.getCode() != Wrapper.SUCCESS_CODE) {
                logger.error("订单中心--订单状态推结算中心(执行中和已完成), AC返回结果异常, {}"
                        , integerWrapper == null ? "integerWrapper 为null" : integerWrapper.getMessage());
            }
            logger.info("订单状态开始推结算中心成功 integerWrapper{}", integerWrapper);
        } catch (Exception e) {
            logger.error("订单中心--订单状态推结算中心(执行中和已完成) 异常, {}", e, e.getMessage());
        }
    }

    /**
     * 订单中心--→计划单
     *
     * @param acPlanDto 推送结算实体
     * @return void
     */
    public void pullOfcOrderPlanCode(AcPlanDto acPlanDto) {
        logger.info("订单中心--→计划单");
        if (PubUtils.isNull(acPlanDto)) {
            logger.error("订单计划单推结算中心异常");
            throw new BusinessException("订单计划单推结算中心异常");
        }
        try {
            Wrapper<Integer> integerWrapper = acOrderEdasService.pullOfcOrderPlanCode(acPlanDto);
            if (null == integerWrapper || integerWrapper.getCode() == Wrapper.ERROR_CODE) {
                logger.error("订单中心--订单计划单推结算中心异常(执行中和已完成) AC返回结果异常, {}"
                        , integerWrapper == null ? "integerWrapper 为null" : integerWrapper.getMessage());
            }
        } catch (Exception e) {
            logger.error("订单中心--订单计划单推结算中心异常(执行中和已完成) 异常, {}", e, e.getMessage());
        }
    }

    @Override
    public Wrapper<?> validateStockCount(List<OfcGoodsDetailsInfo> goodsDetailsList, String custCode, String warehouseCode) {
        int count = 0;
        List<InventoryDTO> inventoryGoods = new ArrayList<>();
        //相同货品编码数量相加
        Map<String,OfcGoodsDetailsInfo> goodInfo=new HashMap<>();
        for (OfcGoodsDetailsInfo ofcGoodsDetails : goodsDetailsList) {
            String goodsCode=ofcGoodsDetails.getGoodsCode();
            if(goodInfo.containsKey(goodsCode)){
                OfcGoodsDetailsInfo info=goodInfo.get(goodsCode);
                info.setQuantity(info.getQuantity().add(ofcGoodsDetails.getQuantity()));
            }else{
                goodInfo.put(goodsCode,ofcGoodsDetails);
            }
        }

        Iterator iter = goodInfo.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String,OfcGoodsDetailsInfo> entry= (Map.Entry<String, OfcGoodsDetailsInfo>) iter.next();
            OfcGoodsDetailsInfo ofcGoodsDetailsInfo=entry.getValue();
            if (ofcGoodsDetailsInfo.getQuantity() == null || ofcGoodsDetailsInfo.getQuantity().compareTo(new BigDecimal(0)) == 0) {
                continue;
            }
            InventoryDTO inventoryDTO = new InventoryDTO();
            inventoryDTO.setLineNo(String.valueOf(++count));
            inventoryDTO.setCustomerCode(custCode);
            inventoryDTO.setWarehouseCode(warehouseCode);
            inventoryDTO.setSkuCode(ofcGoodsDetailsInfo.getGoodsCode());
            inventoryDTO.setSkuName(ofcGoodsDetailsInfo.getGoodsName());
            inventoryDTO.setLotatt05(ofcGoodsDetailsInfo.getProductionBatch());
            Date productionTime = ofcGoodsDetailsInfo.getProductionTime();
            Date invalidTime = ofcGoodsDetailsInfo.getInvalidTime();
            if(null != productionTime){
                inventoryDTO.setLotatt01(DateUtils.Date2String(productionTime, DateUtils.DateFormatType.TYPE2));
            }
            if(null != invalidTime){
                inventoryDTO.setLotatt02(DateUtils.Date2String(ofcGoodsDetailsInfo.getInvalidTime(), DateUtils.DateFormatType.TYPE2));
            }
            inventoryDTO.setQuantity(ofcGoodsDetailsInfo.getQuantity());
            inventoryGoods.add(inventoryDTO);
        }
        return whcOrderCancelEdasService.validateStockCount(inventoryGoods);
    }

    /**
     * 钉钉平台日报
     */
    @Override
    public List<OfcOrderAccountDTO> dailyAccount() {

        OrderCountForm form=new OrderCountForm();
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(2);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        form.setEndDate(DateUtils.Date2String(calendar.getTime(), DateUtils.DateFormatType.TYPE2));
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-1);
        form.setStartDate(DateUtils.Date2String(calendar.getTime(), DateUtils.DateFormatType.TYPE2));
        String beginTime=DateUtils.Date2String(calendar.getTime(), DateUtils.DateFormatType.TYPE2);
        //两小时完成的订单统计
        List<OrderCountResult>  twoHourOrderCount=ofcDailyAccountsService.countTwoHoursOrder(form);
        //前一天的订单统计
        List<OrderCountResult>  yesterdayOrderCount=ofcDailyAccountsService.yesterdayOrderCount(form);
        if(CollectionUtils.isEmpty(twoHourOrderCount)){
            logger.info("两小时完成的订单为零");
        }
        if(CollectionUtils.isEmpty(yesterdayOrderCount)){
            logger.info("前一天订单总计数为零");
        }

        //保存到数据库
        Map<String,OfcDailyAccount> accountDailyResult=new HashMap<>();
        //发送到钉钉机器人的数据
        Map<String,OfcOrderAccountDTO> account=new HashMap<>();

        if(!CollectionUtils.isEmpty(yesterdayOrderCount)){
            for(OrderCountResult yesterdayOrderCountResult:yesterdayOrderCount) {
                StringBuilder yesterdayOrderCountKey = new StringBuilder();
                if (!(PubUtils.isSEmptyOrNull(yesterdayOrderCountResult.getAreaCode()) && PubUtils.isSEmptyOrNull(yesterdayOrderCountResult.getBaseCode()))) {
                    yesterdayOrderCountKey.append(yesterdayOrderCountResult.getAreaCode()).append(yesterdayOrderCountResult.getBaseCode());
                }else if(!PubUtils.isSEmptyOrNull(yesterdayOrderCountResult.getAreaCode())&&PubUtils.isSEmptyOrNull(yesterdayOrderCountResult.getBaseCode())){
                    yesterdayOrderCountKey.append(yesterdayOrderCountResult.getAreaCode());
                }else {
                    continue;
                }
                //保存到数据库的数据
                OfcDailyAccount ofcDailyAccount=new OfcDailyAccount();
                ofcDailyAccount.setAreaCode(yesterdayOrderCountResult.getAreaCode());
                ofcDailyAccount.setAreaName(yesterdayOrderCountResult.getAreaName());
                ofcDailyAccount.setBaseName(yesterdayOrderCountResult.getBaseName());
                ofcDailyAccount.setBaseCode(yesterdayOrderCountResult.getBaseCode());
                ofcDailyAccount.setYesterdayAccount(yesterdayOrderCountResult.getOrderCount()==null?new BigDecimal(0.0):yesterdayOrderCountResult.getOrderCount());
                ofcDailyAccount.setGmtCreate(new Date());
                if(!accountDailyResult.containsKey(yesterdayOrderCountKey.toString())){
                    accountDailyResult.put(yesterdayOrderCountKey.toString(),ofcDailyAccount);
                }

                // 发送到钉钉机器人的数据
                OfcOrderAccountDTO  ofcOrderAccountDTO=new OfcOrderAccountDTO();
                ofcOrderAccountDTO.setAreaCode(yesterdayOrderCountResult.getAreaCode());
                ofcOrderAccountDTO.setAreaName(yesterdayOrderCountResult.getAreaName());
                ofcOrderAccountDTO.setBaseName(yesterdayOrderCountResult.getBaseName());
                ofcOrderAccountDTO.setBaseCode(yesterdayOrderCountResult.getBaseCode());
                if(!account.containsKey(yesterdayOrderCountKey.toString())){
                    account.put(yesterdayOrderCountKey.toString(),ofcOrderAccountDTO);
                }
            }
            if(!CollectionUtils.isEmpty(twoHourOrderCount)){
                for(OrderCountResult twoHourOrderCountResult:twoHourOrderCount){
                    StringBuilder twoHourOrderCountkey = new StringBuilder();
                    if (!(PubUtils.isSEmptyOrNull(twoHourOrderCountResult.getAreaCode()) && PubUtils.isSEmptyOrNull(twoHourOrderCountResult.getBaseCode()))) {
                        twoHourOrderCountkey.append(twoHourOrderCountResult.getAreaCode()).append(twoHourOrderCountResult.getBaseCode());
                    } else if(!PubUtils.isSEmptyOrNull(twoHourOrderCountResult.getAreaCode())&&PubUtils.isSEmptyOrNull(twoHourOrderCountResult.getBaseCode())){
                        twoHourOrderCountkey.append(twoHourOrderCountResult.getAreaCode());
                    }else{
                        continue;
                    }

                    if(account.containsKey(twoHourOrderCountkey.toString())){
                        OfcDailyAccount ofcDailyAccount = accountDailyResult.get(twoHourOrderCountkey.toString());
                        ofcDailyAccount.setTwoHourAccount(twoHourOrderCountResult.getOrderCount()==null?new BigDecimal(0.0):twoHourOrderCountResult.getOrderCount());
                        BigDecimal p=twoHourOrderCountResult.getOrderCount().divide(ofcDailyAccount.getYesterdayAccount(),2, RoundingMode.HALF_UP);
                        //事后补录订单：2小时订单/开单合计
                        ofcDailyAccount.setAdditionalOrder(p.setScale(2));
                        ofcDailyAccount.setAdditionalOrderPercent(percent.format(p.doubleValue()));

                        OfcOrderAccountDTO ofcOrderAccountDTO = account.get(twoHourOrderCountkey.toString());
                        ofcOrderAccountDTO.setAdditionalOrder(p.setScale(2));
                        ofcOrderAccountDTO.setAdditionalOrderPercent(percent.format(p.doubleValue()));
                    }
                }
            }

        }

        //收入确认的订单 海洋提供
        logger.info("调用结算中心的统计接口传的参数为:{}",beginTime);
        Wrapper<List<AcIncomeSettleDTO>> acResult= acSettleStatisticsEdasService.acIncomeSettle(beginTime);
        logger.info("调用结算中心的统计接口响应的code为:{}",acResult.getCode());
        if(acResult.getCode()==Wrapper.SUCCESS_CODE){
            if(!CollectionUtils.isEmpty(acResult.getResult())){
                List<AcIncomeSettleDTO> AcIncomeSettleDTOList=acResult.getResult();
                logger.info("调用结算中心的统计接口查询的结果为:{}",AcIncomeSettleDTOList);
                for(AcIncomeSettleDTO acIncomeSettleDTO:AcIncomeSettleDTOList){
                    StringBuilder key=new StringBuilder();
                    if(!(PubUtils.isSEmptyOrNull(acIncomeSettleDTO.getAreaCode())&&PubUtils.isSEmptyOrNull(acIncomeSettleDTO.getBaseCode()))){
                        key.append(acIncomeSettleDTO.getAreaCode()).append(acIncomeSettleDTO.getBaseCode());
                    }else if(!PubUtils.isSEmptyOrNull(acIncomeSettleDTO.getAreaCode())&&PubUtils.isSEmptyOrNull(acIncomeSettleDTO.getBaseCode())){
                        key.append(acIncomeSettleDTO.getAreaCode());
                    }else{
                        continue;
                    }
                    if(accountDailyResult.containsKey(key.toString())){
                        OfcDailyAccount ofcDailyAccount=accountDailyResult.get(key.toString());
                        OfcOrderAccountDTO ofcOrderAccountDTO=account.get(key.toString());
                        ofcDailyAccount.setHaveIncomeOrderAccount(BigDecimal.valueOf(acIncomeSettleDTO.getReceivableOrderNumber()==null?0:acIncomeSettleDTO.getReceivableOrderNumber()));
                        ofcDailyAccount.setPayableVehicleAccount(BigDecimal.valueOf(acIncomeSettleDTO.getPayableCarNumber()==null?0:acIncomeSettleDTO.getPayableCarNumber()));
                        BigDecimal p=ofcDailyAccount.getHaveIncomeOrderAccount().divide(ofcDailyAccount.getYesterdayAccount(),2, RoundingMode.HALF_UP);
                        //应收确认日清：收入确认的订单/开单合计
                        ofcDailyAccount.setReceivable(p.setScale(2));
                        ofcDailyAccount.setReceivablePercent(percent.format(p.doubleValue()));
                        ofcOrderAccountDTO.setReceivable(p.setScale(2));
                        ofcOrderAccountDTO.setReceivablePercent(percent.format(p.doubleValue()));
                    }else{
                        OfcDailyAccount ofcDailyAccount=new OfcDailyAccount();
                        ofcDailyAccount.setAreaCode(acIncomeSettleDTO.getAreaCode());
                        ofcDailyAccount.setAreaName(acIncomeSettleDTO.getAreaName());
                        ofcDailyAccount.setBaseName(acIncomeSettleDTO.getBaseName());
                        ofcDailyAccount.setBaseCode(acIncomeSettleDTO.getBaseCode());

                        OfcOrderAccountDTO ofcOrderAccountDTO=new OfcOrderAccountDTO();
                        ofcOrderAccountDTO.setAreaCode(acIncomeSettleDTO.getAreaCode());
                        ofcOrderAccountDTO.setAreaName(acIncomeSettleDTO.getAreaName());
                        ofcOrderAccountDTO.setBaseName(acIncomeSettleDTO.getBaseName());
                        ofcOrderAccountDTO.setBaseCode(acIncomeSettleDTO.getBaseCode());

                        ofcDailyAccount.setHaveIncomeOrderAccount(BigDecimal.valueOf(acIncomeSettleDTO.getReceivableOrderNumber()==null?0:acIncomeSettleDTO.getReceivableOrderNumber()));
                        ofcDailyAccount.setPayableVehicleAccount(BigDecimal.valueOf(acIncomeSettleDTO.getPayableCarNumber()==null?0:acIncomeSettleDTO.getPayableCarNumber()));
                        BigDecimal p=ofcDailyAccount.getHaveIncomeOrderAccount().divide(ofcDailyAccount.getYesterdayAccount(),2, RoundingMode.HALF_UP);
                        //应收确认日清：收入确认的订单/开单合计
                        ofcDailyAccount.setReceivable(p.setScale(2));
                        ofcDailyAccount.setReceivablePercent(percent.format(p.doubleValue()));
                        ofcOrderAccountDTO.setReceivable(p.setScale(2));
                        ofcOrderAccountDTO.setReceivablePercent(percent.format(p.doubleValue()));
                        accountDailyResult.put(key.toString(),ofcDailyAccount);
                        account.put(key.toString(),ofcOrderAccountDTO);
                    }
                }
            }else{
                logger.info("调用结算中心的统计结果为空");
            }
        }

        //外部车辆数发运数量 铭涛提供
        logger.info("调用运输中心的统计接口传的参数为:{}",beginTime);
        Wrapper<List<DeliverEveryRunDTO>>  deliverResult=tfcQueryEveryDeliveryService.queryDeliverEveryRun(beginTime);
        logger.info("调用运输中心的统计接口传的响应为:{}",deliverResult.getCode());
        if(deliverResult.getCode()==Wrapper.SUCCESS_CODE){
            List<DeliverEveryRunDTO> tfcDeliverEveryRunDTOList=deliverResult.getResult();
            if(!CollectionUtils.isEmpty(tfcDeliverEveryRunDTOList)){
                logger.info("调用运输中心的统计接口查询的结果为:{}",tfcDeliverEveryRunDTOList);
                for (DeliverEveryRunDTO deliverEveryRunDTO:tfcDeliverEveryRunDTOList){
                    StringBuilder key=new StringBuilder();
                    if(!(PubUtils.isSEmptyOrNull(deliverEveryRunDTO.getAreaCode())&&PubUtils.isSEmptyOrNull(deliverEveryRunDTO.getBaseCode()))){
                        key.append(deliverEveryRunDTO.getAreaCode()).append(deliverEveryRunDTO.getBaseCode());
                    }else if(!PubUtils.isSEmptyOrNull(deliverEveryRunDTO.getAreaCode())&&PubUtils.isSEmptyOrNull(deliverEveryRunDTO.getBaseCode())){
                        key.append(deliverEveryRunDTO.getAreaCode());
                    }else{
                        continue;
                    }
                    if(accountDailyResult.containsKey(key.toString())){
                        OfcDailyAccount ofcDailyAccount=accountDailyResult.get(key.toString());
                        OfcOrderAccountDTO ofcOrderAccountDTO=account.get(key.toString());
                        ofcDailyAccount.setExternalVehicleAccount(BigDecimal.valueOf(deliverEveryRunDTO.getNum()));
                        BigDecimal p=ofcDailyAccount.getPayableVehicleAccount().divide(ofcDailyAccount.getExternalVehicleAccount()==null?new BigDecimal(0.0):ofcDailyAccount.getExternalVehicleAccount(),2, RoundingMode.HALF_UP);
                        //应付确认日清 应付确认车数量/外部车辆发运数量
                        ofcDailyAccount.setPayable(p.setScale(2));
                        ofcDailyAccount.setPayablePercent(percent.format(p.doubleValue()));
                        ofcOrderAccountDTO.setPayable(p.setScale(2));
                        ofcOrderAccountDTO.setPayablePercent(percent.format(p.doubleValue()));
                    }else{
                        OfcDailyAccount ofcDailyAccount=new OfcDailyAccount();
                        OfcOrderAccountDTO ofcOrderAccountDTO=new OfcOrderAccountDTO();
                        ofcDailyAccount.setExternalVehicleAccount(BigDecimal.valueOf(deliverEveryRunDTO.getNum()));
                        BigDecimal p=ofcDailyAccount.getPayableVehicleAccount().divide(ofcDailyAccount.getExternalVehicleAccount()==null?new BigDecimal(0.0):ofcDailyAccount.getExternalVehicleAccount(),2, RoundingMode.HALF_UP);
                        //应付确认日清 应付确认车数量/外部车辆发运数量
                        ofcDailyAccount.setPayable(p.setScale(2));
                        ofcDailyAccount.setPayablePercent(percent.format(p.doubleValue()));
                        ofcOrderAccountDTO.setPayable(p.setScale(2));
                        ofcOrderAccountDTO.setPayablePercent(percent.format(p.doubleValue()));
                        accountDailyResult.put(key.toString(),ofcDailyAccount);
                        account.put(key.toString(),ofcOrderAccountDTO);
                    }
                }
            }else{
                logger.info("调用运输中心统计外部车辆发运数量为空");
            }
        }

        List<OfcOrderAccountDTO> dailyAccountInfo=new ArrayList<>();
        Iterator iter = accountDailyResult.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, OfcDailyAccount> entry = (Map.Entry<String, OfcDailyAccount>) iter.next();
            OfcDailyAccount ofcDailyAccount=entry.getValue();
            if(ofcDailyAccount.getAdditionalOrder()==null){
                ofcDailyAccount.setAdditionalOrder(new BigDecimal(0.0));
            }
            if(ofcDailyAccount.getReceivable()==null){
                ofcDailyAccount.setReceivable(new BigDecimal(0.0));
            }
            if(ofcDailyAccount.getPayable()==null){
                ofcDailyAccount.setPayable(new BigDecimal(0.0));
            }
            BigDecimal total=ofcDailyAccount.getReceivable().add(ofcDailyAccount.getPayable()).subtract(ofcDailyAccount.getAdditionalOrder());

            ofcDailyAccount.setTotal(total.setScale(2));
            ofcDailyAccount.setTotalPercent(percent.format(total.doubleValue()));
            ofcDailyAccountsService.save(ofcDailyAccount);
        }

        Iterator itera = account.entrySet().iterator();
        while (itera.hasNext()) {
            Map.Entry<String, OfcOrderAccountDTO> entry = (Map.Entry<String, OfcOrderAccountDTO>) itera.next();
            OfcOrderAccountDTO ofcOrderAccountDTO=entry.getValue();
            if(ofcOrderAccountDTO.getAdditionalOrder()==null){
                ofcOrderAccountDTO.setAdditionalOrder(new BigDecimal(0.0));
            }
            if(ofcOrderAccountDTO.getReceivable()==null){
                ofcOrderAccountDTO.setReceivable(new BigDecimal(0.0));
            }
            if(ofcOrderAccountDTO.getPayable()==null){
                ofcOrderAccountDTO.setPayable(new BigDecimal(0.0));
            }
            BigDecimal total=ofcOrderAccountDTO.getReceivable().add(ofcOrderAccountDTO.getPayable()).subtract(ofcOrderAccountDTO.getAdditionalOrder());
            ofcOrderAccountDTO.setTotal(total.setScale(2));
            ofcOrderAccountDTO.setTotalPercent(percent.format(total.doubleValue()));
            dailyAccountInfo.add(ofcOrderAccountDTO);
        }

        //按 应收确认日清 + 应付确认日清 - 事后补录订单 排序
        Collections.sort(dailyAccountInfo,new Comparator<OfcOrderAccountDTO>(){
            public int compare(OfcOrderAccountDTO o1, OfcOrderAccountDTO o2) {
                if(o1.getTotal().doubleValue()<o2.getTotal().doubleValue()){
                    return 1;
                }else if(o1.getTotal().doubleValue()>o2.getTotal().doubleValue()){
                    return -1;
                }else{
                    return 0;
                }
            }
        });
        return dailyAccountInfo;
    }

    /**
     * 生成仓储订单
     *
     * @param ofcOrderDTO                      订单信息
     * @param goodsDetailsList                 货品信息
     * @param reviewTag                        操作标志位
     * @param cscContantAndCompanyDtoConsignor 发货方信息
     * @param cscContantAndCompanyDtoConsignee 收货方信息
     * @param cscSupplierInfoDto               供应商信息
     * @param authResDtoByToken 登录用户
     * @return 操作结果
     */
    @Override
    public Wrapper<?> saveStorageOrder(OfcOrderDTO ofcOrderDTO, List<OfcGoodsDetailsInfo> goodsDetailsList, String reviewTag
            , CscContantAndCompanyDto cscContantAndCompanyDtoConsignor, CscContantAndCompanyDto cscContantAndCompanyDtoConsignee
            , CscSupplierInfoDto cscSupplierInfoDto, AuthResDto authResDtoByToken) {
        logger.info("开始创建仓储订单 ofcOrderDTO:{}, goodsDetailsList:{}, reviewTag:{}, cscContantAndCompanyDtoConsignor:{}" +
                ", cscContantAndCompanyDtoConsignee:{}, cscSupplierInfoDto:{}, authResDtoByToken:{}", ofcOrderDTO, goodsDetailsList
                , reviewTag, cscContantAndCompanyDtoConsignor, cscContantAndCompanyDtoConsignee, cscSupplierInfoDto, authResDtoByToken);
        OfcFundamentalInformation ofcFundamentalInformation = modelMapper.map(ofcOrderDTO, OfcFundamentalInformation.class);
        OfcWarehouseInformation ofcWarehouseInformation = modelMapper.map(ofcOrderDTO, OfcWarehouseInformation.class);
        OfcDistributionBasicInfo ofcDistributionBasicInfo = modelMapper.map(ofcOrderDTO, OfcDistributionBasicInfo.class);
        OfcMerchandiser ofcMerchandiser = modelMapper.map(ofcOrderDTO, OfcMerchandiser.class);
        if (null == ofcFundamentalInformation || null == ofcWarehouseInformation) {
            logger.error("创建仓储订单失败,saveStorageOrder转换的某个实体为空" +
                            ", ofcFundamentalInformation:{}, ofcWarehouseInformation:{},ofcDistributionBasicInfo:{}"
                    , ofcFundamentalInformation, ofcWarehouseInformation, ofcDistributionBasicInfo);
            throw new BusinessException("创建仓储订单失败!");
        }
        if (null == ofcWarehouseInformation.getProvideTransport()) {
            logger.error("ofcWarehouseInformation.getProvideTransport为空");
            throw new BusinessException("创建仓储订单失败!");
        }
        if (Objects.equals(ofcWarehouseInformation.getProvideTransport(), YES)) {
            ofcFundamentalInformation.setTransportType("10");//10 零担 20整车
            if (ofcDistributionBasicInfo == null) {
                return WrapMapper.wrap(Wrapper.ERROR_CODE, "需要运输时送基本信息不能为空 ");
            }
            // 运输单号逻辑追加 by lyh
            if (PubUtils.isSEmptyOrNull(ofcDistributionBasicInfo.getTransCode())) {
                ofcDistributionBasicInfo.setTransCode(ofcFundamentalInformation.getOrderCode());
            }
            int repeatNum = ofcDistributionBasicInfoService.checkTransCode(ofcDistributionBasicInfo);
            if (repeatNum > 0) {
                return WrapMapper.wrap(Wrapper.ERROR_CODE, "运输单号重复");
            }
        } else {
            //如果不提供运输, 则运输单号为空
            ofcDistributionBasicInfo.setTransCode("");
        }

        //订单的基本信息
        ofcFundamentalInformation.setCreationTime(new Date());
        ofcFundamentalInformation.setCreator(authResDtoByToken.getUserId());
        ofcFundamentalInformation.setCreatorName(authResDtoByToken.getUserName());
        ofcFundamentalInformation.setOperator(authResDtoByToken.getUserId());
        ofcFundamentalInformation.setOperatorName(authResDtoByToken.getUserName());
        ofcFundamentalInformation.setOrderType(WAREHOUSE_DIST_ORDER);

        //校验当前登录用户的身份信息,并存放大区和基地信息
        ofcOrderPlaceService.orderAuthByConsignorAddr(authResDtoByToken, ofcDistributionBasicInfo, ofcFundamentalInformation);
        ofcFundamentalInformation.setOperTime(new Date());
        OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
        ofcFundamentalInformation.setStoreName(ofcOrderDTO.getStoreName());//店铺还没维护表
        ofcFundamentalInformation.setOrderSource("手动");//订单来源
        int custOrderCode = 0;
        if (!isSEmptyOrNull(ofcFundamentalInformation.getCustOrderCode())) {
            custOrderCode = ofcFundamentalInformationService.checkCustOrderCode(ofcFundamentalInformation);
            if (custOrderCode > 0) {
                if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_EDIT)) {//编辑时排除是自己的客户订单号
                    OfcFundamentalInformation info = ofcFundamentalInformationService.queryDataByCustOrderCode(ofcFundamentalInformation.getCustOrderCode());
                    if (info.getOrderCode().equals(ofcFundamentalInformation.getOrderCode())) {
                        custOrderCode = 0;
                    }
                }
            }
        }
        //仓储下单//根据客户订单编号查询唯一性
        if (custOrderCode < 1) {
            if (!isSEmptyOrNull(ofcFundamentalInformation.getCustName())) {
                QueryCustomerCodeDto queryCustomerCodeDto = new QueryCustomerCodeDto();
                queryCustomerCodeDto.setCustomerCode(ofcFundamentalInformation.getCustCode());
                Wrapper<CscCustomerVo> cscCustomerVo = cscCustomerEdasService.queryCustomerByCustomerCodeOrId(queryCustomerCodeDto);
                if (Wrapper.ERROR_CODE == cscCustomerVo.getCode()) {
                    throw new BusinessException(cscCustomerVo.getMessage());
                } else if (null == cscCustomerVo.getResult()) {
                    throw new BusinessException("客户中心没有查到该客户!");
                }
                ofcFundamentalInformation.setCustName(cscCustomerVo.getResult().getCustomerName());
            }
        } else {
            throw new BusinessException("该客户订单编号已经存在!您不能重复下单!");
        }

        StringBuffer notes = new StringBuffer();
        if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_SAVE) || trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_IMPORT)) {
            ofcFundamentalInformation.setOrderCode(codeGenUtils.getNewWaterCode(ORDER_PRE, 6));
        }

        if (Objects.equals(ofcWarehouseInformation.getProvideTransport(), YES)) {
            // 运输单号逻辑追加 by lyh
            if (PubUtils.isSEmptyOrNull(ofcDistributionBasicInfo.getTransCode())) {
                ofcDistributionBasicInfo.setTransCode(ofcFundamentalInformation.getOrderCode());
            }
            int repeatNum = ofcDistributionBasicInfoService.checkTransCode(ofcDistributionBasicInfo);
            if(!(PubUtils.isSEmptyOrNull(ofcDistributionBasicInfo.getTransCode())&&PubUtils.isSEmptyOrNull(ofcDistributionBasicInfo.getSelfTransCode()))){
                if(ofcDistributionBasicInfo.getTransCode().equals(ofcDistributionBasicInfo.getSelfTransCode())){
                    repeatNum = 0;
                }
            }
            if (repeatNum > 0) {
                throw new BusinessException("运输单号重复!");
            }
        }

        ofcFundamentalInformation.setAbolishMark(ORDER_WASNOT_ABOLISHED);//未作废
        //货品数量
        BigDecimal goodsAmountCount = new BigDecimal(0);
        //保存货品明细
        if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_EDIT)) {
            ofcGoodsDetailsInfoService.deleteAllByOrderCode(ofcFundamentalInformation.getOrderCode());
        }

        //相同货品编码数量相加
        Map<String,OfcGoodsDetailsInfo> goodInfo=new HashMap<>();
        List<OfcGoodsDetailsInfo> ofcGoodsDetail=new ArrayList<>();

        for (OfcGoodsDetailsInfo ofcGoodsDetails : goodsDetailsList) {
            StringBuilder key=new StringBuilder();
            key.append(ofcGoodsDetails.getGoodsCode());
            if(!StringUtils.isEmpty(ofcGoodsDetails.getSupportBatch())){
                key.append(ofcGoodsDetails.getSupportBatch());
            }
            if(!StringUtils.isEmpty(ofcGoodsDetails.getProductionBatch())){
                key.append(ofcGoodsDetails.getProductionBatch());
            }
            if(ofcGoodsDetails.getProductionTime()!=null){
                key.append(DateUtils.Date2String(ofcGoodsDetails.getProductionTime(), DateUtils.DateFormatType.TYPE1));
            }
            if(ofcGoodsDetails.getInvalidTime()!=null){
                key.append(DateUtils.Date2String(ofcGoodsDetails.getInvalidTime(), DateUtils.DateFormatType.TYPE1));
            }
            if(!goodInfo.containsKey(key.toString())){
                goodInfo.put(key.toString(),ofcGoodsDetails);
            }else{
                OfcGoodsDetailsInfo info=goodInfo.get(key.toString());
                info.setQuantity(info.getQuantity().add(ofcGoodsDetails.getQuantity()));
            }
        }

        Iterator iter = goodInfo.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String,OfcGoodsDetailsInfo> entry= (Map.Entry<String, OfcGoodsDetailsInfo>) iter.next();
            OfcGoodsDetailsInfo ofcGoodsDetails=entry.getValue();
            if (ofcGoodsDetails.getQuantity() == null || ofcGoodsDetails.getQuantity().compareTo(new BigDecimal(0)) == 0) {
                 continue;
             }
            String orderCode = ofcFundamentalInformation.getOrderCode();
            ofcGoodsDetails.setOrderCode(orderCode);
            ofcGoodsDetails.setCreationTime(ofcFundamentalInformation.getCreationTime());
            ofcGoodsDetails.setCreator(ofcFundamentalInformation.getCreator());
            ofcGoodsDetails.setOperator(ofcFundamentalInformation.getOperator());
            ofcGoodsDetails.setOperTime(ofcFundamentalInformation.getOperTime());
            goodsAmountCount = goodsAmountCount.add(ofcGoodsDetails.getQuantity());
            ofcGoodsDetail.add(ofcGoodsDetails);
            ofcGoodsDetailsInfoService.save(ofcGoodsDetails);
     }


        if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_SAVE) || trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_IMPORT)) {
            //添加基本信息
            ofcFundamentalInformationService.save(ofcFundamentalInformation);
        } else if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_EDIT)) {
            ofcFundamentalInformationService.update(ofcFundamentalInformation);
        }

        if (!ofcFundamentalInformation.getOrderType().equals(WAREHOUSE_DIST_ORDER)) {
            logger.error("该订单不是仓储类型订单");
            throw new BusinessException("该订单不是仓储类型订单");
        }

        if (null == ofcWarehouseInformation.getProvideTransport()) {
            ofcWarehouseInformation.setProvideTransport(WAREHOUSE_NO_TRANS);
        }

        Wrapper<?> wrapper = null;
        OfcDistributionBasicInfo dinfo = null;
        OfcDistributionBasicInfo condition = new OfcDistributionBasicInfo();
        condition.setOrderCode(ofcFundamentalInformation.getOrderCode());
        dinfo = ofcDistributionBasicInfoService.selectOne(condition);

        if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_EDIT) || ORDER_TAG_STOCK_IMPORT.equals(trimAndNullAsEmpty(reviewTag))) {//
            //编辑时不提供运输改为提供运输时 收货方即是仓库的信息   前台不好处理  后台通过仓库编码获取仓库的信息
            StringBuilder sb = new StringBuilder();
            RmcWarehouseDto rmcWarehouseDto = new RmcWarehouseDto();
            rmcWarehouseDto.setWarehouseCode(ofcWarehouseInformation.getWarehouseCode());
            Wrapper<RmcWarehouseRespDto> wareHouse = rmcWarehouseEdasService.queryRmcWarehouseByCode(rmcWarehouseDto);
            CscContantAndCompanyDto dto = new CscContantAndCompanyDto();
            CscContactCompanyDto c = new CscContactCompanyDto();
            CscContactDto cc = new CscContactDto();
            dto.setCscContactDto(cc);
            dto.setCscContactCompanyDto(c);
            if (wareHouse.getCode() == Wrapper.SUCCESS_CODE) {
                RmcWarehouseRespDto resp = wareHouse.getResult();
                dto.getCscContactCompanyDto().setContactCompanyName(resp.getWarehouseName());
                dto.getCscContactDto().setContactName(resp.getContactName());
                dto.getCscContactDto().setPhone(resp.getPhone());
                dto.getCscContactDto().setProvince(resp.getProvinceCode());
                sb.append(resp.getProvinceCode());
                dto.getCscContactDto().setCity(resp.getCityCode());
                sb.append(",").append(resp.getCityCode());
                dto.getCscContactDto().setArea(resp.getAreaCode());
                sb.append(",").append(resp.getAreaCode());
                if (!StringUtils.isEmpty(resp.getStreetCode())) {
                    dto.getCscContactDto().setStreet(resp.getStreetCode());
                    sb.append(",").append(resp.getStreetCode());
                }
                dto.getCscContactDto().setProvinceName(resp.getProvince());
                dto.getCscContactDto().setCityName(resp.getCity());
                dto.getCscContactDto().setAreaName(resp.getArea());
                if (!StringUtils.isEmpty(resp.getStreetCode())) {
                    dto.getCscContactDto().setStreetName(resp.getStreet());
                }
                dto.getCscContactDto().setAddress(resp.getDetailAddress());
                if (trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0, 2).equals("61")) {
                    ofcDistributionBasicInfo.setDeparturePlaceCode(sb.toString());
                    ofcDistributionBasicInfo.setDeparturePlace(resp.getDetailAddress());
                    ofcDistributionBasicInfo.setDepartureProvince(resp.getProvince());
                    ofcDistributionBasicInfo.setDepartureCity(resp.getCity());
                    ofcDistributionBasicInfo.setDepartureDistrict(resp.getArea());
                    if (!StringUtils.isEmpty(resp.getStreet())) {
                        ofcDistributionBasicInfo.setDepartureTowns(resp.getStreet());
                    }

                } else if (trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0, 2).equals("62")) {
                    ofcDistributionBasicInfo.setConsigneeName(resp.getWarehouseName());
                    ofcDistributionBasicInfo.setConsigneeContactName(resp.getWarehouseName());
                    ofcDistributionBasicInfo.setDestinationCode(sb.toString());
                    ofcDistributionBasicInfo.setDestination(resp.getDetailAddress());
                    ofcDistributionBasicInfo.setDestinationProvince(resp.getProvince());
                    ofcDistributionBasicInfo.setDestinationCity(resp.getCity());
                    ofcDistributionBasicInfo.setDestinationDistrict(resp.getArea());
                    if (!StringUtils.isEmpty(resp.getStreet())) {
                        ofcDistributionBasicInfo.setDestinationTowns(resp.getStreet());
                    }
                }
            }
            if (trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0, 2).equals("61")) {
                cscContantAndCompanyDtoConsignor = dto;//出库发货方是仓库
            } else if (trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0, 2).equals("62")) {
                cscContantAndCompanyDtoConsignee = dto;//入库时收货方是仓库
            }
            if (Objects.equals(ofcWarehouseInformation.getProvideTransport(), YES)) {
                //收货方、发货方校验
                wrapper = validateDistrictContactMessage(cscContantAndCompanyDtoConsignor, cscContantAndCompanyDtoConsignee);
                if (Wrapper.ERROR_CODE == wrapper.getCode()) {
                    throw new BusinessException(wrapper.getMessage());
                }
            }else{
                if (PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0, 2).equals("61")) {
                    wrapper = validateDistrictContactMessage(cscContantAndCompanyDtoConsignor, cscContantAndCompanyDtoConsignee);
                    if (Wrapper.ERROR_CODE == wrapper.getCode()) {
                        throw new BusinessException(wrapper.getMessage());
                    }
                }
            }

        } else {
            if (!trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_IMPORT)) {
                if (Objects.equals(ofcWarehouseInformation.getProvideTransport(), YES)) {
                    wrapper = validateDistrictContactMessage(cscContantAndCompanyDtoConsignor, cscContantAndCompanyDtoConsignee);
                    if (Wrapper.ERROR_CODE == wrapper.getCode()) {
                        throw new BusinessException(wrapper.getMessage());
                    }
                }else{
                    if (PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0, 2).equals("61")) {
                        wrapper = validateDistrictContactMessage(cscContantAndCompanyDtoConsignor, cscContantAndCompanyDtoConsignee);
                        if (Wrapper.ERROR_CODE == wrapper.getCode()) {
                            throw new BusinessException(wrapper.getMessage());
                        }
                    }
                }
            }
        }

        //2017年3月25日 modified by lyh 编辑后将之前无法识别的地址信息匹配表补充完整
        this.fixAddressWhenEdit(reviewTag, ofcDistributionBasicInfo);

        //配送基本信息
        ofcDistributionBasicInfo.setQuantity(goodsAmountCount);
        ofcDistributionBasicInfo.setCreationTime(ofcFundamentalInformation.getCreationTime());
        ofcDistributionBasicInfo.setCreator(ofcFundamentalInformation.getCreator());
        ofcDistributionBasicInfo.setOrderCode(ofcFundamentalInformation.getOrderCode());
        ofcDistributionBasicInfo.setOperator(ofcFundamentalInformation.getOperator());
        ofcDistributionBasicInfo.setOperTime(ofcFundamentalInformation.getOperTime());

        if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_SAVE) || trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_IMPORT)) {
            logger.info("ofcDistributionBasicInfo:{}", ofcDistributionBasicInfo);
            ofcDistributionBasicInfoService.save(ofcDistributionBasicInfo);
        } else if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_EDIT)) {
            if (dinfo != null) {
                ofcDistributionBasicInfo.setQuantity(goodsAmountCount);
                ofcDistributionBasicInfo.setCreationTime(ofcFundamentalInformation.getCreationTime());
                ofcDistributionBasicInfo.setCreator(ofcFundamentalInformation.getCreator());
                ofcDistributionBasicInfo.setOrderCode(ofcFundamentalInformation.getOrderCode());
                ofcDistributionBasicInfo.setOperator(ofcFundamentalInformation.getOperator());
                ofcDistributionBasicInfo.setOperTime(ofcFundamentalInformation.getOperTime());
                ofcDistributionBasicInfoService.updateByOrderCode(ofcDistributionBasicInfo);
            }
        }

        //仓储信息
        ofcWarehouseInformation.setOrderCode(ofcFundamentalInformation.getOrderCode());
        ofcWarehouseInformation.setCreationTime(ofcFundamentalInformation.getCreationTime());
        ofcWarehouseInformation.setCreator(ofcFundamentalInformation.getCreator());
        ofcWarehouseInformation.setOperTime(ofcFundamentalInformation.getOperTime());
        ofcWarehouseInformation.setOperator(ofcFundamentalInformation.getOperator());
        if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_SAVE) || trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_IMPORT)) {
            ofcWarehouseInformationService.save(ofcWarehouseInformation);
        } else if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_EDIT)) {
            ofcWarehouseInformationService.updateByOrderCode(ofcWarehouseInformation);
        }

        //添加开单员
        if (ofcMerchandiserService.select(ofcMerchandiser).size() == 0 && !trimAndNullAsEmpty(ofcMerchandiser.getMerchandiser()).equals("")) {
            ofcMerchandiserService.save(ofcMerchandiser);
        }
        if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_SAVE) || trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_IMPORT)) {
            //保存订单日志
            notes.append(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1));
            notes.append(" 订单已创建");
            notes.append(" 操作人: ").append(authResDtoByToken.getUserName());
            notes.append(" 操作单位: ").append(authResDtoByToken.getGroupRefName());
            ofcOrderStatus.setNotes(notes.toString());
            ofcOrderStatus.setOrderCode(ofcFundamentalInformation.getOrderCode());
            ofcOrderStatus.setOrderStatus(PENDING_AUDIT);
            ofcOrderStatus.setStatusDesc("待审核");
            ofcOrderStatus.setLastedOperTime(new Date());
            ofcOrderStatus.setOperator(authResDtoByToken.getUserName());
            ofcOrderStatusService.save(ofcOrderStatus);
        } else if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_EDIT)) {
            notes.append(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1));
            notes.append(" 订单已更新");
            notes.append(" 操作人: ").append(authResDtoByToken.getUserName());
            notes.append(" 操作单位: ").append(authResDtoByToken.getGroupRefName());
            ofcOrderStatus.setNotes(notes.toString());
            ofcOrderStatus.setOrderCode(ofcFundamentalInformation.getOrderCode());
            ofcOrderStatus.setOrderStatus(PENDING_AUDIT);
            ofcOrderStatus.setStatusDesc("待审核");
            ofcOrderStatus.setLastedOperTime(new Date());
            ofcOrderStatus.setOperator(authResDtoByToken.getUserName());
            ofcOrderStatusService.save(ofcOrderStatus);
        }

        //普通手录订单直接调用自动审核, 批量导入订单另起自动审核.
        logger.info("自动审核. 订单批次号信息:{}", ofcFundamentalInformation.getOrderBatchNumber());
        if (isSEmptyOrNull(ofcFundamentalInformation.getOrderBatchNumber())) {
            //调用自动审核
            this.orderAutoAudit(ofcFundamentalInformation, ofcGoodsDetail, ofcDistributionBasicInfo, ofcWarehouseInformation
                    , new OfcFinanceInformation(), ofcOrderStatus.getOrderStatus(), REVIEW, authResDtoByToken);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE);
    }

    /**
     * 编辑后将地址不完整的订单的地址信息补充完整
     * @param reviewTag 标志位
     * @param ofcDistributionBasicInfo 运输信息
     */
    public void fixAddressWhenEdit(String reviewTag, OfcDistributionBasicInfo ofcDistributionBasicInfo) {
        logger.info("编辑后将地址不完整的订单的地址信息补充完整, reviewTag : {}", reviewTag);
        logger.info("编辑后将地址不完整的订单的地址信息补充完整, ofcDistributionBasicInfo : {}", ofcDistributionBasicInfo);
        if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_EDIT)) {
            String departurePlace = ofcDistributionBasicInfo.getDeparturePlace();
            String destination = ofcDistributionBasicInfo.getDestination();
            if(!PubUtils.isSEmptyOrNull(departurePlace)){
                OfcAddressReflect ofcAddressReflect = ofcAddressReflectService.selectByAddress(departurePlace);
                logger.info("编辑后将地址不完整的订单的地址信息补充完整, ofcAddressReflect : {}", ofcAddressReflect);
                if(null != ofcAddressReflect && PubUtils.isSEmptyOrNull(ofcAddressReflect.getProvince())){
                    ofcAddressReflectService.reflectAddressToRef(ofcAddressReflect, ofcDistributionBasicInfo, "departure");
                    int update = ofcAddressReflectMapper.updateByAddress(ofcAddressReflect);
                    if(update < 1){
                        logger.error("更新出发明细地址映射失败!");
//                        throw new BusinessException("更新出发明细地址映射失败!");
                    }
                }
            }
            if(!PubUtils.isSEmptyOrNull(destination)){
                OfcAddressReflect ofcAddressReflect = ofcAddressReflectService.selectByAddress(destination);
                logger.info("编辑后将地址不完整的订单的地址信息补充完整, ofcAddressReflect : {}", ofcAddressReflect);
                if(null != ofcAddressReflect && PubUtils.isSEmptyOrNull(ofcAddressReflect.getProvince())){
                    ofcAddressReflectService.reflectAddressToRef(ofcAddressReflect, ofcDistributionBasicInfo, "destination");
                    int update = ofcAddressReflectMapper.updateByAddress(ofcAddressReflect);
                    if(update < 1){
                        logger.error("更新到达明细地址映射失败!");
//                        throw new BusinessException("更新到达明细地址映射失败!");
                    }
                }
            }
        }
    }

    /**
     * 订单的复制
     *
     * @param orderCode 订单编号
     * @return
     */
    @Override
    public String copyOrder(String orderCode, AuthResDto authResDtoByToken) {
        OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
        if (ofcFundamentalInformation == null) {
            throw new BusinessException("订单号不存在订单");
        }

        OfcOrderStatus ordertatus = ofcOrdertatusService.orderStatusSelect(orderCode, "orderCode");
        if (ordertatus == null) {
            throw new BusinessException("订单号不存在任何状态");
        }

        String status = ordertatus.getOrderStatus();
        StringBuilder notes = new StringBuilder();
        OfcOrderStatus s = new OfcOrderStatus();
        OfcFundamentalInformation newofcFundamentalInformation = new OfcFundamentalInformation();
        try {
            BeanUtils.copyProperties(newofcFundamentalInformation, ofcFundamentalInformation);
            if (!status.equals(HASBEEN_CANCELED)) {
                newofcFundamentalInformation.setCustOrderCode("");
            }
            newofcFundamentalInformation.setOrderCode(codeGenUtils.getNewWaterCode("SO", 6));
            //保存订单日志
            notes.append(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1));
            notes.append(" 订单已创建");
            notes.append(" 操作人: ").append(authResDtoByToken.getUserName());
            notes.append(" 操作单位: ").append(authResDtoByToken.getGroupRefName());
            s.setNotes(notes.toString());
            s.setOrderCode(newofcFundamentalInformation.getOrderCode());
            s.setOrderStatus(PENDING_AUDIT);
            s.setStatusDesc("待审核");
            s.setLastedOperTime(new Date());
            s.setOperator(authResDtoByToken.getUserName());
            ofcOrderStatusService.save(s);

            //复制订单基本信息
            newofcFundamentalInformation.setFinishedTime(null);
            newofcFundamentalInformation.setAbolishTime(null);
            newofcFundamentalInformation.setOperator("");
            newofcFundamentalInformation.setAbolisherName("");
            newofcFundamentalInformation.setOrderSource("手动录入");
            newofcFundamentalInformation.setCreator(authResDtoByToken.getUserName());
            newofcFundamentalInformation.setAreaCode(ofcFundamentalInformation.getAreaCode());
            newofcFundamentalInformation.setAreaName(ofcFundamentalInformation.getAreaName());
            newofcFundamentalInformation.setBaseCode(ofcFundamentalInformation.getBaseCode());
            newofcFundamentalInformation.setBaseName(ofcFundamentalInformation.getBaseName());
            ofcFundamentalInformationService.save(newofcFundamentalInformation);

            //复制仓储的信息
            OfcWarehouseInformation cw = new OfcWarehouseInformation();
            cw.setOrderCode(ofcFundamentalInformation.getOrderCode());
            OfcWarehouseInformation owinfo = ofcWarehouseInformationService.selectOne(cw);
            if (owinfo != null) {
                OfcWarehouseInformation ofcnewWarehouseInformation = new OfcWarehouseInformation();
                BeanUtils.copyProperties(ofcnewWarehouseInformation, owinfo);
                ofcnewWarehouseInformation.setOrderCode(newofcFundamentalInformation.getOrderCode());
                ofcWarehouseInformationService.save(ofcnewWarehouseInformation);

                //提供运输时 复制配送信息
                OfcDistributionBasicInfo f = new OfcDistributionBasicInfo();
                f.setOrderCode(ofcFundamentalInformation.getOrderCode());
                OfcDistributionBasicInfo BasicInfo = ofcDistributionBasicInfoService.selectOne(f);
                if (BasicInfo != null) {
                    OfcDistributionBasicInfo newofcDistributionBasicInfo = new OfcDistributionBasicInfo();
                    BeanUtils.copyProperties(newofcDistributionBasicInfo, BasicInfo);
                    newofcDistributionBasicInfo.setOrderCode(newofcFundamentalInformation.getOrderCode());
                    ofcDistributionBasicInfoService.save(newofcDistributionBasicInfo);
                }
            }

            //复制货品详情的信息
            OfcGoodsDetailsInfo infoCondition = new OfcGoodsDetailsInfo();
            infoCondition.setOrderCode(ofcFundamentalInformation.getOrderCode());
            List<OfcGoodsDetailsInfo> goodsInfo = ofcGoodsDetailsInfoService.queryByOrderCode(ofcFundamentalInformation.getOrderCode());
            if (goodsInfo != null && goodsInfo.size() > 0) {
                for (OfcGoodsDetailsInfo info : goodsInfo) {
                    OfcGoodsDetailsInfo newGoodsInfo = new OfcGoodsDetailsInfo();
                    BeanUtils.copyProperties(newGoodsInfo, info);
                    newGoodsInfo.setId(UUID.randomUUID().toString().replace("-", ""));
                    newGoodsInfo.setOrderCode(newofcFundamentalInformation.getOrderCode());
                    ofcGoodsDetailsInfoService.save(newGoodsInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("复制订单出现异常");
            throw new BusinessException("复制订单出现异常");
        }
        return newofcFundamentalInformation.getOrderCode();
    }

    /**
     * 订单自动审核
     *
     * @param ofcFundamentalInformation 基本信息
     * @param goodsDetailsList          货品信息
     * @param ofcDistributionBasicInfo  运输信息
     * @param ofcWarehouseInformation   仓储信息
     * @param ofcFinanceInformation     财务信息
     * @param orderStatus               订单状态
     * @param reviewTag                 审核标志位
     * @param authResDtoByToken         当前登录用户
     * @return String
     */
    @Override
    public String orderAutoAudit(OfcFundamentalInformation ofcFundamentalInformation, List<OfcGoodsDetailsInfo> goodsDetailsList
            , OfcDistributionBasicInfo ofcDistributionBasicInfo, OfcWarehouseInformation ofcWarehouseInformation
            , OfcFinanceInformation ofcFinanceInformation, String orderStatus, String reviewTag, AuthResDto authResDtoByToken) {
        OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
        ofcOrderStatus.setOrderCode(ofcFundamentalInformation.getOrderCode());
        ofcOrderStatus.setOrderStatus(orderStatus);
        logger.info("订单进行自动审核,当前订单号:{}, 当前订单状态:{}", ofcFundamentalInformation.getOrderCode(), ofcOrderStatus.toString());
        if (ofcOrderStatus.getOrderStatus().equals(PENDING_AUDIT) && reviewTag.equals(REVIEW)) {
            //创单接口订单和钉钉录单补充大区基地信息
            if (StringUtils.equals(ofcFundamentalInformation.getOrderSource(), DING_DING)
                    || StringUtils.equals(ofcFundamentalInformation.getCreator(), CREATE_ORDER_BYAPI)) {
                // 大区、基地都为空则更新大区基地
                String baseCode = ofcFundamentalInformation.getBaseCode();
                String areaCode = ofcFundamentalInformation.getAreaCode();
                if (PubUtils.isOEmptyOrNull(baseCode) && PubUtils.isOEmptyOrNull(areaCode)) {
                    this.updateOrderAreaAndBase(ofcFundamentalInformation, ofcDistributionBasicInfo);
                }
                this.pushOrderToAc(ofcFundamentalInformation, ofcFinanceInformation, ofcDistributionBasicInfo, goodsDetailsList, ofcWarehouseInformation);
            }
            String userName = authResDtoByToken.getUserName();
            ofcOrderStatus.setOrderStatus(ALREADY_EXAMINE);
            ofcOrderStatus.setStatusDesc("已审核");
            ofcOrderStatus.setNotes(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1) + " " + "订单审核完成");
            ofcOrderStatus.setOperator(userName);
            ofcOrderStatus.setLastedOperTime(new Date());
            int save = ofcOrderStatusService.save(ofcOrderStatus);
            if (save == 0) {
                logger.error("自动审核出错, 更新订单状态为已审核失败");
                throw new BusinessException("自动审核出错!");
            }
            ofcFundamentalInformation.setOperator(authResDtoByToken.getUserId());
            ofcFundamentalInformation.setOperatorName(userName);
            ofcFundamentalInformation.setOperTime(new Date());

            String orderType = ofcFundamentalInformation.getOrderType();
            if (trimAndNullAsEmpty(orderType).equals(TRANSPORT_ORDER)) {  // 运输订单
                pushOrderToTfc(ofcFundamentalInformation, ofcFinanceInformation, ofcDistributionBasicInfo, goodsDetailsList);
            } else if (trimAndNullAsEmpty(orderType).equals(WAREHOUSE_DIST_ORDER)) {//仓储订单
                //仓储订单推仓储中心
                pushOrderToWhc(ofcFundamentalInformation, goodsDetailsList, ofcWarehouseInformation, ofcFinanceInformation, ofcDistributionBasicInfo);
                //仓储带运输订单推仓储中心和运输中心
                if (Objects.equals(ofcWarehouseInformation.getProvideTransport(), YES)) {
                    pushOrderToTfc(ofcFundamentalInformation, ofcFinanceInformation, ofcDistributionBasicInfo, goodsDetailsList);
                    pushOrderToAc(ofcFundamentalInformation,ofcFinanceInformation,ofcDistributionBasicInfo,goodsDetailsList, ofcWarehouseInformation);
                }
            } else {
                logger.error("订单类型有误");
                throw new BusinessException("订单类型有误");
            }
            //订单状态变为执行中
            ofcOrderStatus.setOrderStatus(IMPLEMENTATION_IN);
            ofcOrderStatus.setStatusDesc("执行中");
            ofcOrderStatus.setNotes(new StringBuilder()
                    .append(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1))
                    .append(" ").append("订单开始执行").toString());
            ofcOrderStatus.setOperator(userName);
            ofcOrderStatus.setLastedOperTime(new Date());
            int saveOrderStatus = ofcOrderStatusService.save(ofcOrderStatus);
            if (saveOrderStatus == 0) {
                logger.error("自动审核出错, 更新订单状态为执行中失败");
                throw new BusinessException("自动审核出错!");
            }
            logger.info("=====>订单中心--订单状态推结算中心");
            this.pullOfcOrderStatus(ofcOrderStatus);
        } else {
            logger.error("订单状态错误或缺少审核标志位");
            throw new BusinessException("订单状态错误或缺少审核标志位");
        }

        return String.valueOf(Wrapper.SUCCESS_CODE);
    }

    /**
     * 订单信息推送运输中心
     *
     * @param ofcFundamentalInformation 基本信息
     * @param ofcFinanceInformation     财务信息
     * @param ofcDistributionBasicInfo  运输信息
     * @param ofcGoodsDetailsInfos      货品信息
     * @return void
     */
    @Override
    public void pushOrderToTfc(OfcFundamentalInformation ofcFundamentalInformation, OfcFinanceInformation ofcFinanceInformation
            , OfcDistributionBasicInfo ofcDistributionBasicInfo, List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos) {
        logger.info("订单信息推送运输中心,订单号:{}", ofcFundamentalInformation.getOrderCode());
        //订单中心实体转运输中心接口DTO
        if (ofcFundamentalInformation.getOrderType().equals(WAREHOUSE_DIST_ORDER)) {
            ofcFundamentalInformation.setBusinessType(WITH_THE_CITY);
        }
        TfcTransport tfcTransport = convertOrderToTfc(ofcFundamentalInformation, ofcFinanceInformation, ofcDistributionBasicInfo, ofcGoodsDetailsInfos);
        String json;
        try {
            json = JacksonUtil.toJson(tfcTransport);
        } catch (Exception e) {
            logger.error("订单信息推送运输中心, 转换异常");
            throw new BusinessException("订单信息推送运输中心异常!");
        }
        logger.info("###################推送TFC的最终JSON为{}", json);
        defaultMqProducer.toSendTfcTransPlanMQ(json, tfcTransport.getOrderCode());

    }

    /**
     * 订单信息推送仓储中心
     *
     * @param ofcFundamentalInformation 基本信息
     * @param goodsDetailsList          货品明细
     * @param ofcWarehouseInformation   仓库信息
     * @param ofcFinanceInformation     财务信息
     */
    @Override
    public void pushOrderToWhc(OfcFundamentalInformation ofcFundamentalInformation
            , List<OfcGoodsDetailsInfo> goodsDetailsList, OfcWarehouseInformation ofcWarehouseInformation
            , OfcFinanceInformation ofcFinanceInformation, OfcDistributionBasicInfo dinfo) {
        logger.info("订单信息推送仓储中心 ==> ofcFundamentalInformation:{}", ofcFundamentalInformation);
        String json;
        try {
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);//严格模式
            OfcOrderDTO ofOrderDto = modelMapper.map(ofcFundamentalInformation, OfcOrderDTO.class);
            logger.info("订单信息推送仓储中心 ==> ofOrderDto:{}", ofOrderDto);
            ofOrderDto.setWarehouseName(ofcWarehouseInformation.getWarehouseName());
            ofOrderDto.setWarehouseCode(ofcWarehouseInformation.getWarehouseCode());
            ofOrderDto.setProvideTransport(ofcWarehouseInformation.getProvideTransport());
            if (trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0, 2).equals("61")) {
                //出库
                ofOrderDto.setShipmentTime(ofcWarehouseInformation.getShipmentTime());
                if (dinfo != null) {
                    StringBuilder address=new StringBuilder();
                    ofOrderDto.setConsigneeCode(dinfo.getConsigneeName());//收货方编码
                    ofOrderDto.setConsigneeContactCode(dinfo.getConsigneeContactCode());//收货方联系人编码
                    ofOrderDto.setConsigneeContactName(dinfo.getConsigneeContactName());//收货方联系名称
                    ofOrderDto.setConsigneeContactPhone(dinfo.getConsigneeContactPhone());//收货方联系电话
                    if(!StringUtils.isEmpty(dinfo.getDestinationProvince())){
                        address.append(dinfo.getDestinationProvince());
                    }
                    if(!StringUtils.isEmpty(dinfo.getDestinationCity())){
                        address.append(dinfo.getDestinationCity());
                    }
                    if(!StringUtils.isEmpty(dinfo.getDestinationDistrict())){
                        address.append(dinfo.getDestinationDistrict());
                    }
                    if(!StringUtils.isEmpty(dinfo.getDestinationTowns())){
                        address.append(dinfo.getDestinationTowns());
                    }
                    if(!StringUtils.isEmpty(dinfo.getDestination())){
                        address.append(dinfo.getDestination());
                    }
                    ofOrderDto.setDestination(address.toString());//收货人地址
                }
//                //分拨出库不进行库存的校验
//                if (!("614".equals(ofcFundamentalInformation.getBusinessType())||"613".equals(ofcFundamentalInformation.getBusinessType()))) {
//                    //校验出库商品的库存
//                    Wrapper wrapper =validateStockCount(goodsDetailsList,ofcFundamentalInformation.getCustCode(),ofcWarehouseInformation.getWarehouseCode());
//                    if (wrapper.getCode() != Wrapper.SUCCESS_CODE) {
//                        List<ResponseMsg> msgs = null;
//                        StringBuilder message=new StringBuilder();
//                        TypeReference<List<ResponseMsg>> ResponseMsgsRef = new TypeReference<List<ResponseMsg>>() {};
//                        msgs= JacksonUtil.parseJsonWithFormat(wrapper.getMessage(),ResponseMsgsRef);
//                        if(!CollectionUtils.isEmpty(msgs)){
//                           for(int i=0;i<msgs.size();i++){
//                               ResponseMsg msg=msgs.get(i);
//                               if(i==msgs.size()-1){
//                                   message.append(msg.getMessage());
//                               }else{
//                                   message.append(",").append(msg.getMessage());
//                               }
//                           }
//                        }
//                        throw new BusinessException(message.toString());
//                    }
//                }
            } else if (trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0, 2).equals("62")) {
                //入库
                ofOrderDto.setArriveTime(ofcWarehouseInformation.getArriveTime());
            }
            ofOrderDto.setPlateNumber(ofcWarehouseInformation.getPlateNumber());
            ofOrderDto.setDriverName(ofcWarehouseInformation.getDriverName());
            ofOrderDto.setContactNumber(ofcWarehouseInformation.getContactNumber());
            ofOrderDto.setSupportName(ofcWarehouseInformation.getSupportName());
            ofOrderDto.setSupportCode(ofcWarehouseInformation.getSupportCode());
            ofOrderDto.setGoodsList(goodsDetailsList);
            logger.info("订单信息推送仓储中心 ==> ofOrderDto:{}", ofOrderDto);
            json = JacksonUtil.toJson(ofOrderDto);
            logger.info("订单信息推送仓储中心,订单号:{}", ofcFundamentalInformation.getOrderCode());
            logger.info("推送WHC的最终JSON为{}", json);
            defaultMqProducer.toSendWhc(json, ofcFundamentalInformation.getOrderCode(), ofcFundamentalInformation.getBusinessType());
        } catch (Exception e) {
            logger.error("订单信息推到仓储中心, 转换异常 {}", e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 订单中心实体转运输中心接口DTO
     *
     * @param ofcFundamentalInformation 订单基本信息
     * @param ofcFinanceInformation     财务信息
     * @param ofcDistributionBasicInfo  运输信息
     * @param ofcGoodsDetailsInfos      货品信息
     * @return 运输中心接口DTO
     */
    private TfcTransport convertOrderToTfc(OfcFundamentalInformation ofcFundamentalInformation
            , OfcFinanceInformation ofcFinanceInformation, OfcDistributionBasicInfo ofcDistributionBasicInfo, List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos) {
        logger.info("====>ofcFundamentalInformation{}", ofcFundamentalInformation);
        logger.info("====>ofcFinanceInformation{}", ofcFinanceInformation);
        logger.info("====>ofcDistributionBasicInfo{}", ofcDistributionBasicInfo);
        logger.info("====>ofcGoodsDetailsInfos{}", ofcGoodsDetailsInfos);
        if (null == ofcFundamentalInformation || null == ofcFinanceInformation || null == ofcDistributionBasicInfo || CollectionUtils.isEmpty(ofcGoodsDetailsInfos)) {
            logger.error("订单中心实体转运输中心接口DTO异常! 入参错误");
            throw new BusinessException("订单中心实体转运输中心接口DTO异常! 入参错误");
        }
        TfcTransport tfcTransport = new TfcTransport();
        this.convertOrderToTfcOfBaseInfo(ofcFundamentalInformation, ofcFinanceInformation, ofcDistributionBasicInfo, tfcTransport);
        List<TfcTransportDetail> tfcTransportDetails = new ArrayList<>();
        for (OfcGoodsDetailsInfo ofcGoodsDetailsInfo : ofcGoodsDetailsInfos) {
            if (ofcGoodsDetailsInfo.getQuantity() == null || ofcGoodsDetailsInfo.getQuantity().compareTo(new BigDecimal(0)) == 0) {
                if ((ofcGoodsDetailsInfo.getWeight() == null || ofcGoodsDetailsInfo.getWeight().compareTo(new BigDecimal(0)) == 0) && (ofcGoodsDetailsInfo.getCubage() == null || ofcGoodsDetailsInfo.getCubage().compareTo(new BigDecimal(0)) == 0)) {
                    continue;
                }
            }
            TfcTransportDetail tfcTransportDetail = new TfcTransportDetail();
            tfcTransportDetail.setOrderCode(ofcFundamentalInformation.getOrderCode());
            tfcTransportDetail.setStandard(trimAndNullAsEmpty(ofcGoodsDetailsInfo.getGoodsSpec()));
//            tfcTransportDetail.setPono();
//            tfcTransportDetail.setQtyPicked();
//            tfcTransportDetail.setMarketUnitl();
//            tfcTransportDetail.setQtyPickedEach();
//            tfcTransportDetail.setBasicUnits1();
//            tfcTransportDetail.setQtyOrdered();
//            tfcTransportDetail.setMarketUnit2();
//            tfcTransportDetail.setQtyOrderedEach();
//            tfcTransportDetail.setBasicUnits2();
//            tfcTransportDetail.setTransportId();
//            tfcTransportDetail.setTfcBillNo();
//            tfcTransportDetail.setFromSystem();
            tfcTransportDetail.setTransportNo(trimAndNullAsEmpty(ofcDistributionBasicInfo.getTransCode()));
            tfcTransportDetail.setItemCode(trimAndNullAsEmpty(ofcGoodsDetailsInfo.getGoodsCode()));
            tfcTransportDetail.setItemName(trimAndNullAsEmpty(ofcGoodsDetailsInfo.getGoodsName()));
            tfcTransportDetail.setQty(ofcGoodsDetailsInfo.getQuantity() == null ? null : ofcGoodsDetailsInfo.getQuantity().doubleValue());
            tfcTransportDetail.setWeight(ofcGoodsDetailsInfo.getWeight() == null ? null : ofcGoodsDetailsInfo.getWeight().doubleValue());
            tfcTransportDetail.setVolume(ofcGoodsDetailsInfo.getCubage() == null ? null : ofcGoodsDetailsInfo.getCubage().doubleValue());
            tfcTransportDetail.setPrice(ofcGoodsDetailsInfo.getUnitPrice() == null ? null : ofcGoodsDetailsInfo.getUnitPrice().doubleValue());
//            tfcTransportDetail.setMoney();
            tfcTransportDetail.setUom(trimAndNullAsEmpty(ofcGoodsDetailsInfo.getUnit()));
//            tfcTransportDetail.setContainerQty();
            tfcTransportDetail.setProductionBatch(trimAndNullAsEmpty(ofcGoodsDetailsInfo.getProductionBatch()));
            if (null != ofcGoodsDetailsInfo.getProductionTime()) {
                tfcTransportDetail.setProductionTime(ofcGoodsDetailsInfo.getProductionTime());
            }
            if (null != ofcGoodsDetailsInfo.getInvalidTime()) {
                tfcTransportDetail.setInvalidTime(ofcGoodsDetailsInfo.getInvalidTime());
            }
            if (null != ofcGoodsDetailsInfo.getTotalBox()) {
                tfcTransportDetail.setTotalBox(ofcGoodsDetailsInfo.getTotalBox());
            }
            tfcTransportDetail.setGoodsType(trimAndNullAsEmpty(ofcGoodsDetailsInfo.getGoodsType()));
            tfcTransportDetail.setGoodsCategory(trimAndNullAsEmpty(ofcGoodsDetailsInfo.getGoodsCategory()));
            tfcTransportDetail.setPack(trimAndNullAsEmpty(ofcGoodsDetailsInfo.getPack()));
            tfcTransportDetail.setChargingWays(trimAndNullAsEmpty(ofcGoodsDetailsInfo.getChargingWays()));
            tfcTransportDetails.add(tfcTransportDetail);
        }
        tfcTransport.setProductDetail(tfcTransportDetails);
        return tfcTransport;
    }

    /**
     * 转换TFC实体基本信息
     * @param ofcFundamentalInformation 基本信息
     * @param ofcFinanceInformation 财务信息
     * @param ofcDistributionBasicInfo 运输信息
     * @param tfcTransport TFC实体
     */
    private void convertOrderToTfcOfBaseInfo(OfcFundamentalInformation ofcFundamentalInformation
            , OfcFinanceInformation ofcFinanceInformation, OfcDistributionBasicInfo ofcDistributionBasicInfo, TfcTransport tfcTransport) {
        tfcTransport.setCustomerOrderCode(trimAndNullAsEmpty(ofcFundamentalInformation.getCustOrderCode()));
//        tfcTransport.setBaseName();
//        tfcTransport.setInterfaceStatus();
        tfcTransport.setMarketOrg(trimAndNullAsEmpty(ofcFundamentalInformation.getSaleOrganization()));
        tfcTransport.setPlatformType(trimAndNullAsEmpty(ofcFundamentalInformation.getPlatformType()));
        tfcTransport.setProductTeam(trimAndNullAsEmpty(ofcFundamentalInformation.getProductGroup()));
        tfcTransport.setMarketDep(trimAndNullAsEmpty(ofcFundamentalInformation.getSaleDepartment()));
        tfcTransport.setMarketTeam(trimAndNullAsEmpty(ofcFundamentalInformation.getSaleGroup()));
        tfcTransport.setMarketDepDes(trimAndNullAsEmpty(ofcFundamentalInformation.getSaleDepartmentDesc()));
        tfcTransport.setMarketTeamDes(trimAndNullAsEmpty(ofcFundamentalInformation.getSaleGroupDesc()));
        tfcTransport.setTransportSource(trimAndNullAsEmpty(ofcFundamentalInformation.getOrderSource()));// ??
//        tfcTransport.setTfcBillNo();
        tfcTransport.setFromSystem(trimAndNullAsEmpty(ofcFundamentalInformation.getOrderSource()));
        tfcTransport.setTransportNo(trimAndNullAsEmpty(ofcDistributionBasicInfo.getTransCode()));
//        tfcTransport.setStatus();
        tfcTransport.setBillType(trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()));
        tfcTransport.setItemType(trimAndNullAsEmpty(ofcDistributionBasicInfo.getGoodsType()));
        tfcTransport.setCustomerCode(trimAndNullAsEmpty(ofcFundamentalInformation.getCustCode()));
        tfcTransport.setCustomerName(trimAndNullAsEmpty(ofcFundamentalInformation.getCustName()));
        tfcTransport.setBaseCode(ofcFundamentalInformation.getBaseCode());
        tfcTransport.setBaseDesignation(ofcFundamentalInformation.getBaseName());
        tfcTransport.setAreaCode(ofcFundamentalInformation.getAreaCode());
        tfcTransport.setAreaName(ofcFundamentalInformation.getAreaName());
//        tfcTransport.setCustomerTel();
        tfcTransport.setFromTransportName(trimAndNullAsEmpty(ofcDistributionBasicInfo.getBaseId()));
        if (null != ofcFundamentalInformation.getCreationTime()) {
            tfcTransport.setCreateTime(ofcFundamentalInformation.getCreationTime());
        }
        if (null != ofcDistributionBasicInfo.getPickupTime()) {
            tfcTransport.setExpectedShipmentTime(ofcDistributionBasicInfo.getPickupTime());
            tfcTransport.setPickupTime(ofcDistributionBasicInfo.getPickupTime());
        }
        if (null != ofcDistributionBasicInfo.getExpectedArrivedTime()) {
            tfcTransport.setExpectedArriveTime(ofcDistributionBasicInfo.getExpectedArrivedTime());
        }
        tfcTransport.setWeight(ofcDistributionBasicInfo.getWeight() == null ? null : ofcDistributionBasicInfo.getWeight().doubleValue());
        tfcTransport.setQty(ofcDistributionBasicInfo.getQuantity() == null ? null : ofcDistributionBasicInfo.getQuantity().doubleValue());
        tfcTransport.setVolume(ofcDistributionBasicInfo.getCubage() == null ? null : Double.valueOf(ofcDistributionBasicInfo.getCubage()));
//        tfcTransport.setMoney();// ??
        tfcTransport.setFromCustomerCode(trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsignorCode()));
        tfcTransport.setFromCustomerName(trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsignorName()));
        tfcTransport.setFromCustomerNameCode(trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsignorContactCode()));
        if (!isSEmptyOrNull(ofcDistributionBasicInfo.getDeparturePlace())) {
            tfcTransport.setFromCustomerAddress(ofcDistributionBasicInfo.getDeparturePlace());
        }

        tfcTransport.setFromCustomer(trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsignorContactName()));// ??
        tfcTransport.setFromCustomerTle(trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsignorContactPhone()));
        tfcTransport.setFromProvince(trimAndNullAsEmpty(ofcDistributionBasicInfo.getDepartureProvince()));
        tfcTransport.setFromCity(trimAndNullAsEmpty(ofcDistributionBasicInfo.getDepartureCity()));
        tfcTransport.setFromDistrict(trimAndNullAsEmpty(ofcDistributionBasicInfo.getDepartureDistrict()));
        tfcTransport.setFromTown(trimAndNullAsEmpty(ofcDistributionBasicInfo.getDepartureTowns()));
        if (!PubUtils.isSEmptyOrNull(ofcDistributionBasicInfo.getDeparturePlaceCode())) {
            String[] departurePlaceCode = ofcDistributionBasicInfo.getDeparturePlaceCode().split(",");
            if (departurePlaceCode.length > 0) {
                tfcTransport.setFromProvinceCode(departurePlaceCode[0]);
                if (departurePlaceCode.length > 1) {
                    tfcTransport.setFromCityCode(departurePlaceCode[1]);
                    if (departurePlaceCode.length > 2) {
                        tfcTransport.setFromDistrictCode(departurePlaceCode[2]);
                        if (departurePlaceCode.length > 3) {
                            tfcTransport.setFromStreetCode(departurePlaceCode[3]);
                        }
                    }
                }
            }
        } else {
            logger.error("没有收货方地址编码");
        }
        tfcTransport.setToCustomerCode(trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsigneeCode()));// 收货方编码
        tfcTransport.setToCustomerName(trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsigneeName()));// 收货方
        tfcTransport.setToCustomerNameCode(trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsigneeContactCode()));//收货方联系人编码
        if (!isSEmptyOrNull(ofcDistributionBasicInfo.getDestination())) {
            tfcTransport.setToCustomerAddress(ofcDistributionBasicInfo.getDestination());
        }
        tfcTransport.setToCustomer(trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsigneeContactName()));// 收货方联系人
        tfcTransport.setToCustomerTle(trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsigneeContactPhone()));
        tfcTransport.setToProvince(trimAndNullAsEmpty(ofcDistributionBasicInfo.getDestinationProvince()));
        tfcTransport.setToCity(trimAndNullAsEmpty(ofcDistributionBasicInfo.getDestinationCity()));
        tfcTransport.setToDistrict(trimAndNullAsEmpty(ofcDistributionBasicInfo.getDestinationDistrict()));
        tfcTransport.setToTown(trimAndNullAsEmpty(ofcDistributionBasicInfo.getDestinationTowns()));
        if (!PubUtils.isSEmptyOrNull(ofcDistributionBasicInfo.getDestinationCode())) {
            String[] destinationPlaceCode = ofcDistributionBasicInfo.getDestinationCode().split(",");
            if (destinationPlaceCode.length > 0) {
                tfcTransport.setToProvinceCode(destinationPlaceCode[0]);
                if (destinationPlaceCode.length > 1) {
                    tfcTransport.setToCityCode(destinationPlaceCode[1]);
                    if (destinationPlaceCode.length > 2) {
                        tfcTransport.setToDistrictCode(destinationPlaceCode[2]);
                        if (destinationPlaceCode.length > 3) {
                            tfcTransport.setToStreetCode(destinationPlaceCode[3]);
                        }
                    }
                }
            }
        } else {
            logger.error("没有收货方地址编码");
        }
//        tfcTransport.setToLon();// ??
//        tfcTransport.setToLat();// ??
//        tfcTransport.setWareHouesCode();// ??
//        tfcTransport.setDeliveryNo();
        tfcTransport.setNotes(trimAndNullAsEmpty(ofcFundamentalInformation.getNotes()));
        tfcTransport.setOrderCode(trimAndNullAsEmpty(ofcFundamentalInformation.getOrderCode()));
        tfcTransport.setOrderBatchNumber(trimAndNullAsEmpty(ofcFundamentalInformation.getOrderBatchNumber()));
//        tfcTransport.setProgramSerialNumber();
        tfcTransport.setDestinationCode(trimAndNullAsEmpty(ofcDistributionBasicInfo.getDestinationCode()));
        tfcTransport.setServiceCharge(ofcFinanceInformation.getServiceCharge() == null ? null : ofcFinanceInformation.getServiceCharge());
        if (null != ofcFundamentalInformation.getOrderTime()) {
            tfcTransport.setOrderTime(ofcFundamentalInformation.getOrderTime());
        }
        tfcTransport.setCreatePersonnel(trimAndNullAsEmpty(ofcFundamentalInformation.getCreatorName()));
        tfcTransport.setVoidPersonnel(trimAndNullAsEmpty(ofcFundamentalInformation.getAbolisherName()));
        if (null != ofcFundamentalInformation.getAbolishTime()) {
            tfcTransport.setVoidTime(ofcFundamentalInformation.getAbolishTime());
        }
        tfcTransport.setMerchandiser(trimAndNullAsEmpty(ofcFundamentalInformation.getMerchandiser()));
        tfcTransport.setBusinessType(trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()));
        tfcTransport.setGoodsTypeName(trimAndNullAsEmpty(ofcDistributionBasicInfo.getGoodsTypeName()));
        tfcTransport.setTwoDistribution(ofcFinanceInformation.getTwoDistribution() == null ? null : ofcFinanceInformation.getTwoDistribution());
        tfcTransport.setFaceOrder(trimAndNullAsEmpty(ofcDistributionBasicInfo.getTransCode()));//面单号
        if (null != ofcFinanceInformation.getCollectLoanAmount()) {
            tfcTransport.setCollectServiceCharge(ofcFinanceInformation.getCollectLoanAmount());
        }
        if (null != ofcFinanceInformation.getLuggage()) {
            tfcTransport.setLuggage(ofcFinanceInformation.getLuggage());
        }

        tfcTransport.setTransportType(trimAndNullAsEmpty(ofcFundamentalInformation.getTransportType()));
//        tfcTransport.setTransportPool();//
//        tfcTransport.setMatchingMode();//
//        tfcTransport.setSchedulingState();//
//        tfcTransport.setTransportPoolName();//
    }

    /**
     * 校验收发货方信息
     *
     * @param cscContantAndCompanyDtoConsignor 发货方
     * @param cscContantAndCompanyDtoConsignee 收货方
     * @return 校验结果
     */
    private Wrapper<?> validateDistrictContactMessage(CscContantAndCompanyDto cscContantAndCompanyDtoConsignor, CscContantAndCompanyDto cscContantAndCompanyDtoConsignee) {
        if (null == cscContantAndCompanyDtoConsignor || null == cscContantAndCompanyDtoConsignee) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "校验收货方信息入参为空");
        }
        if (null == cscContantAndCompanyDtoConsignor.getCscContactCompanyDto() || null == cscContantAndCompanyDtoConsignee.getCscContactCompanyDto()) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "校验收货方信息入参收货方信息为空");
        }
        if (null == cscContantAndCompanyDtoConsignor.getCscContactDto() || null == cscContantAndCompanyDtoConsignee.getCscContactDto()) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "校验收货方信息入参收货方联系人信息为空");
        }
        if (isSEmptyOrNull(cscContantAndCompanyDtoConsignor.getCscContactCompanyDto().getContactCompanyName())) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "请输入发货方信息");
        }
        if (isSEmptyOrNull(cscContantAndCompanyDtoConsignor.getCscContactDto().getContactName())) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "发货方联系人名称未填写");
        }
        if (isSEmptyOrNull(cscContantAndCompanyDtoConsignor.getCscContactDto().getPhone())) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "发货方联系人电话未填写");
        }
        //二级地址还需特殊处理
        if (isSEmptyOrNull(cscContantAndCompanyDtoConsignor.getCscContactDto().getProvinceName())) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "发货方联系人地址未选择");
        }
        if (isSEmptyOrNull(cscContantAndCompanyDtoConsignor.getCscContactDto().getCityName())) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "发货方联系人地址不完整");
        }
        /*if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignor.getCscContact().getAreaName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"发货方联系人地址不完整");
        }*/

        if (isSEmptyOrNull(cscContantAndCompanyDtoConsignee.getCscContactCompanyDto().getContactCompanyName())) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "请输入收货方信息");
        }
        if (isSEmptyOrNull(cscContantAndCompanyDtoConsignee.getCscContactDto().getContactName())) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "收货方联系人名称未填写");
        }
        if (isSEmptyOrNull(cscContantAndCompanyDtoConsignee.getCscContactDto().getPhone())) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "收货方联系人电话未填写");
        }
        if (isSEmptyOrNull(cscContantAndCompanyDtoConsignee.getCscContactDto().getProvinceName())) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "收货方联系人地址未选择");
        }
        if (isSEmptyOrNull(cscContantAndCompanyDtoConsignee.getCscContactDto().getCityName())) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "收货方联系人地址不完整");
        }
        /*if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignee.getCscContact().getAreaName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"收货方联系人地址不完整");
        }*/

        return WrapMapper.wrap(Wrapper.SUCCESS_CODE);
    }

    /**
     * 众品订单审核
     *
     * @param orderStatus               订单状态
     * @param reviewTag                 审核标志位
     * @param authResDtoByToken         当前登录用户
     * @return String
     */
    @Override
    public String orderAutoAuditForTran(String orderCode,String orderStatus, String reviewTag, AuthResDto authResDtoByToken) {
        OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
        ofcOrderStatus.setOrderCode(orderCode);
        ofcOrderStatus.setOrderStatus(orderStatus);
        logger.info("订单进行自动审核,当前订单号:{}, 当前订单状态:{}", orderCode, ofcOrderStatus.toString());
        if (ofcOrderStatus.getOrderStatus().equals(PENDING_AUDIT) && reviewTag.equals(REVIEW)) {
            String userName = authResDtoByToken.getUserName();
            ofcOrderStatus.setOrderStatus(ALREADY_EXAMINE);
            ofcOrderStatus.setStatusDesc("已审核");
            ofcOrderStatus.setNotes(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1) + " " + "订单审核完成");
            ofcOrderStatus.setOperator(userName);
            ofcOrderStatus.setLastedOperTime(new Date());
            int save = ofcOrderStatusService.save(ofcOrderStatus);
            if (save == 0) {
                logger.error("自动审核出错, 更新订单状态为已审核失败");
                throw new BusinessException("自动审核出错!");
            }
            OfcFundamentalInformation ofcFundamentalInformation= ofcFundamentalInformationService.selectByKey(orderCode);
            if(null==ofcFundamentalInformation){
                throw new BusinessException("该订单不存在或者已删除");
            }
            OfcFinanceInformation ofcFinanceInformation = ofcFinanceInformationService.selectByKey(orderCode);
            OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoService.selectByKey(orderCode);
            OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
            ofcGoodsDetailsInfo.setOrderCode(orderCode);
            List<OfcGoodsDetailsInfo> goodsDetailsList = ofcGoodsDetailsInfoService.select(ofcGoodsDetailsInfo);
            String orderType = ofcFundamentalInformation.getOrderType();


            this.pushOrderToAc(ofcFundamentalInformation, ofcFinanceInformation, ofcDistributionBasicInfo, goodsDetailsList, null);
            if (trimAndNullAsEmpty(orderType).equals(TRANSPORT_ORDER)) {  // 运输订单
                pushOrderToTfc(ofcFundamentalInformation, ofcFinanceInformation, ofcDistributionBasicInfo, goodsDetailsList);
            } else if (trimAndNullAsEmpty(orderType).equals(WAREHOUSE_DIST_ORDER)) {//仓储订单
                OfcWarehouseInformation ofcWarehouseInformation = ofcWarehouseInformationService.selectByKey(orderCode);
                //仓储订单推仓储中心
                pushOrderToWhc(ofcFundamentalInformation, goodsDetailsList, ofcWarehouseInformation, ofcFinanceInformation, ofcDistributionBasicInfo);
                //仓储带运输订单推仓储中心和运输中心
                if (Objects.equals(ofcWarehouseInformation.getProvideTransport(), YES)) {
                    pushOrderToTfc(ofcFundamentalInformation, ofcFinanceInformation, ofcDistributionBasicInfo, goodsDetailsList);
                }
            } else {
                logger.error("订单类型有误");
                throw new BusinessException("订单类型有误");
            }
            //订单状态变为执行中
            ofcOrderStatus.setOrderStatus(IMPLEMENTATION_IN);
            ofcOrderStatus.setStatusDesc("执行中");
            ofcOrderStatus.setNotes(new StringBuilder()
                    .append(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1))
                    .append(" ").append("订单开始执行").toString());
            ofcOrderStatus.setOperator(userName);
            ofcOrderStatus.setLastedOperTime(new Date());
            int saveOrderStatus = ofcOrderStatusService.save(ofcOrderStatus);
            if (saveOrderStatus == 0) {
                logger.error("自动审核出错, 更新订单状态为执行中失败");
                throw new BusinessException("自动审核出错!");
            }
            logger.info("=====>订单中心--订单状态推结算中心");
            this.pullOfcOrderStatus(ofcOrderStatus);
        } else {
            logger.error("订单状态错误或缺少审核标志位");
            throw new BusinessException("订单状态错误或缺少审核标志位");
        }

        return String.valueOf(Wrapper.SUCCESS_CODE);
    }
}