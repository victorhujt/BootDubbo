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
import com.xescm.epc.edas.dto.OfcDistributionBasicInfoDto;
import com.xescm.epc.edas.dto.TransportNoDTO;
import com.xescm.epc.edas.service.EpcOfc2DmsEdasService;
import com.xescm.epc.edas.service.EpcOrderCancelEdasService;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.edas.model.dto.ofc.OfcOrderAccountDTO;
import com.xescm.ofc.enums.BusinessTypeEnum;
import com.xescm.ofc.enums.OrderSourceEnum;
import com.xescm.ofc.enums.PlatformTypeEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.form.OrderCountForm;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.model.dto.tfc.TfcTransport;
import com.xescm.ofc.model.dto.tfc.TfcTransportDetail;
import com.xescm.ofc.model.dto.tfc.TransportDTO;
import com.xescm.ofc.model.dto.tfc.TransportDetailDTO;
import com.xescm.ofc.model.dto.whc.WhcDelivery;
import com.xescm.ofc.model.dto.whc.WhcDeliveryDetails;
import com.xescm.ofc.model.dto.whc.WhcInStock;
import com.xescm.ofc.model.dto.whc.WhcInStockDetails;
import com.xescm.ofc.model.vo.ofc.OfcGroupVo;
import com.xescm.ofc.model.vo.ofc.OfcSiloprogramInfoVo;
import com.xescm.ofc.mq.producer.DefaultMqProducer;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.ofc.utils.DateUtils;
import com.xescm.ofc.utils.Response;
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
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.xescm.base.model.wrap.Wrapper.ERROR_CODE;
import static com.xescm.core.utils.PubUtils.isSEmptyOrNull;
import static com.xescm.core.utils.PubUtils.trimAndNullAsEmpty;
import static com.xescm.ofc.constant.GenCodePreffixConstant.*;
import static com.xescm.ofc.constant.OrderConstConstant.*;
import static com.xescm.ofc.constant.OrderConstant.TRANSPORT_ORDER;
import static com.xescm.ofc.constant.OrderConstant.WAREHOUSE_DIST_ORDER;
import static com.xescm.ofc.constant.OrderPlaceTagConstant.*;
import static org.springframework.util.CollectionUtils.isEmpty;

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
    private OfcPlannedDetailService ofcPlannedDetailService;
    @Resource
    private OfcTransplanInfoService ofcTransplanInfoService;
    @Resource
    private OfcTransplanStatusService ofcTransplanStatusService;
    @Resource
    private OfcTransplanNewstatusService ofcTransplanNewstatusService;
    @Resource
    private OfcTraplanSourceStatusService ofcTraplanSourceStatusService;
    @Resource
    private OfcSiloprogramInfoService ofcSiloprogramInfoService;
    @Resource
    private OfcSiloproNewstatusService ofcSiloproNewstatusService;
    @Resource
    private OfcSiloproSourceStatusService ofcSiloproSourceStatusService;
    @Resource
    private OfcSiloproStatusService ofcSiloproStatusService;
    @Resource
    private RmcServiceCoverageEdasService rmcServiceCoverageEdasService;
    @Resource
    private RmcWarehouseEdasService rmcWarehouseEdasService;
    @Resource
    private CodeGenUtils codeGenUtils;
    @Resource
    private EpcOrderCancelEdasService epcOrderCancelEdasService;
    @Resource
    private WhcOrderCancelEdasService whcOrderCancelEdasService;
    @Resource
    private EpcOfc2DmsEdasService epcOfc2DmsEdasService;
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
    private OfcOrderNewstatusService ofcOrderNewstatusService;

    @Resource
    private  OfcDailyAccountsService ofcDailyAccountsService;
    @Resource
    private AcSettleStatisticsEdasService acSettleStatisticsEdasService;
    @Resource
    private TfcQueryEveryDeliveryService tfcQueryEveryDeliveryService;

    private ModelMapper modelMapper = new ModelMapper();


    /**
     * 订单审核和反审核
     *
     * @param orderCode         订单编号
     * @param orderStatus       订单状态
     * @param reviewTag         审核标记
     * @param authResDtoByToken 权限token
     * @return String
     */
    @Override
    public String orderAudit(String orderCode, String orderStatus, String reviewTag, AuthResDto authResDtoByToken) {
        OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
        ofcOrderStatus.setOrderCode(orderCode);
        ofcOrderStatus.setOrderStatus(orderStatus);
        logger.info(ofcOrderStatus.toString());
        try {
            if ((!ofcOrderStatus.getOrderStatus().equals(IMPLEMENTATION_IN))
                    && (!ofcOrderStatus.getOrderStatus().equals(HASBEEN_COMPLETED))
                    && (!ofcOrderStatus.getOrderStatus().equals(HASBEEN_CANCELED))) {
                if (ofcOrderStatus.getOrderStatus().equals(ALREADY_EXAMINE) && reviewTag.equals(RE_REVIEW)) {
                    planCancle(orderCode, authResDtoByToken.getUserName());
                    logger.info("作废计划单完成");
                    ofcOrderStatus.setOrderStatus(PENDING_AUDIT);
                    ofcOrderStatus.setStatusDesc("反审核");
                    ofcOrderStatus.setNotes(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1) + " " + "订单反审核完成");
                    logger.info("作废计划单");
                } else if (ofcOrderStatus.getOrderStatus().equals(PENDING_AUDIT) && reviewTag.equals(REVIEW)) {
                    ofcOrderStatus.setOrderStatus(ALREADY_EXAMINE);
                    ofcOrderStatus.setStatusDesc("已审核");
                    ofcOrderStatus.setNotes(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1) + " " + "订单审核完成");
                    ofcOrderStatus.setOperator(authResDtoByToken.getUserName());
                    ofcOrderStatus.setLastedOperTime(new Date());

                    OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
                    ofcFundamentalInformation.setOperator(authResDtoByToken.getUserId());
                    ofcFundamentalInformation.setOperatorName(authResDtoByToken.getUserName());
                    ofcFundamentalInformation.setOperTime(new Date());
                    List<OfcGoodsDetailsInfo> goodsDetailsList = ofcGoodsDetailsInfoService.goodsDetailsScreenList(orderCode, "orderCode");
                    OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoService.distributionBasicInfoSelect(orderCode);
                    OfcFinanceInformation ofcFinanceInformation = ofcFinanceInformationService.queryByOrderCode(orderCode);

                    String orderType = ofcFundamentalInformation.getOrderType();
                    if (trimAndNullAsEmpty(orderType).equals(TRANSPORT_ORDER)) {
                        //运输订单
                        OfcTransplanInfo ofcTransplanInfo = new OfcTransplanInfo();
                        ofcTransplanInfo.setProgramSerialNumber("1");
                        if (!trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(WITH_THE_KABAN)) {
                            transPlanCreate(ofcTransplanInfo, ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFundamentalInformation.getCustName(), ofcFinanceInformation);
                        }//客户平台暂时不支持卡班
                    } else if (trimAndNullAsEmpty(orderType).equals(WAREHOUSE_DIST_ORDER)) {
                        //仓储订单
                        OfcWarehouseInformation ofcWarehouseInformation = ofcWarehouseInformationService.warehouseInformationSelect(orderCode);
                        OfcSiloprogramInfo ofcSiloprogramInfo = new OfcSiloprogramInfo();
                        if (Objects.equals(ofcWarehouseInformation.getProvideTransport(), WEARHOUSE_WITH_TRANS)) {
                            //需要提供运输
                            OfcTransplanInfo ofcTransplanInfo = new OfcTransplanInfo();//(PubUtils.trimAndNullAsEmpty(ofcSiloprogramInfo.getDocumentType()).substring(0,2).equals("61"))
                            if (trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0, 2).equals("61")) {
                                //出库
                                ofcTransplanInfo.setProgramSerialNumber("2");
                                ofcSiloprogramInfo.setProgramSerialNumber("1");

                            } else if (trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0, 2).equals("62")) {
                                //入库
                                ofcTransplanInfo.setProgramSerialNumber("1");
                                ofcSiloprogramInfo.setProgramSerialNumber("2");
                            }
                            transPlanCreate(ofcTransplanInfo, ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFundamentalInformation.getCustName(), ofcFinanceInformation);
                            String planCode = siloProCreate(ofcSiloprogramInfo, ofcFundamentalInformation, goodsDetailsList, ofcWarehouseInformation, ofcFinanceInformation, ofcDistributionBasicInfo, authResDtoByToken.getUserName());
                            //仓储计划单生成以后通过MQ推送到仓储中心
                            List<OfcSiloprogramInfoVo> infos = ofcSiloprogramInfoService.ofcSiloprogramAndResourceInfo(orderCode, RESOURCE_ALLOCATION);
                            List<OfcPlannedDetail> pds = ofcPlannedDetailService.planDetailsScreenList(planCode, "planCode");
                            if (infos != null && infos.size() > 0) {
                                logger.info("开始推送到仓储计划单");
                                sendToWhc(infos.get(0), ofcWarehouseInformation, pds, ofcDistributionBasicInfo, ofcFinanceInformation, ofcFundamentalInformation, authResDtoByToken);
                            } else {
                                logger.info("仓储计划单不存在");
                                throw new BusinessException("仓储计划单不存在");
                            }
                        } else if (Objects.equals(ofcWarehouseInformation.getProvideTransport(), WAREHOUSE_NO_TRANS)) {
                            logger.info("不需要提供运输");
                            //不需要提供运输
                            ofcSiloprogramInfo.setProgramSerialNumber("1");
                            String planCode = siloProCreate(ofcSiloprogramInfo, ofcFundamentalInformation, goodsDetailsList, ofcWarehouseInformation, ofcFinanceInformation, ofcDistributionBasicInfo, authResDtoByToken.getUserName());
                            //仓储计划单生成以后通过MQ推送到仓储中心
                            List<OfcSiloprogramInfoVo> ofcSiloprogramInfoVoList = ofcSiloprogramInfoService.ofcSiloprogramAndResourceInfo(orderCode, RESOURCE_ALLOCATION);
                            if (StringUtils.isEmpty(planCode)) {
                                logger.info("仓储计划单号不能为空");
                                throw new BusinessException("仓储计划单号不能为空");
                            }
                            List<OfcPlannedDetail> pds = ofcPlannedDetailService.planDetailsScreenList(planCode, "planCode");
                            if (ofcSiloprogramInfoVoList != null && ofcSiloprogramInfoVoList.size() > 0) {
                                logger.info("开始推送到仓储计划单");
                                sendToWhc(ofcSiloprogramInfoVoList.get(0), ofcWarehouseInformation, pds, ofcDistributionBasicInfo, ofcFinanceInformation, ofcFundamentalInformation, authResDtoByToken);
                            } else {
                                logger.info("仓储计划单不存在");
                                throw new BusinessException("仓储计划单号不能为空");
                            }
                        } else {
                            throw new BusinessException("无法确定是否需要运输");
                        }
                        ofcOrderStatusService.save(ofcOrderStatus);
                        ofcOrderStatus.setOrderStatus(IMPLEMENTATION_IN);
                        ofcOrderStatus.setStatusDesc("执行中");
                        ofcOrderStatus.setNotes(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1) + " " + "订单开始执行");
                    } else {
                        throw new BusinessException("订单类型有误");
                    }
                    logger.info("计划单创建成功");
                } else {
                    throw new BusinessException("缺少标志位");
                }
                ofcOrderStatus.setOperator(authResDtoByToken.getUserName());
                ofcOrderStatus.setLastedOperTime(new Date());
                ofcOrderStatusService.save(ofcOrderStatus);
                logger.info("=====>订单中心--订单状态推结算中心");
                //订单中心--订单状态推结算中心(执行中和已完成)
                this.pullOfcOrderStatus(ofcOrderStatus);
                return String.valueOf(Wrapper.SUCCESS_CODE);
            } else {
                throw new BusinessException("订单类型既非”已审核“，也非”未审核“，请检查");
            }
        } catch (BusinessException ex) {
            logger.error("==>审核订单发生错误 {}", ex);
            throw ex;
        } catch (Exception ex) {
            logger.error("==>审核订单发生未知异常！", ex);
            ex.printStackTrace();
            throw ex;
        }

    }

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
     * 创建运输计划单-干线、城配
     *
     * @param ofcTransplanInfo          计划单基本信息
     * @param ofcFundamentalInformation 订单基本信息
     * @param goodsDetailsList          货品明细
     * @param ofcDistributionBasicInfo  运输基本信息
     */
    private void transPlanCreate(OfcTransplanInfo ofcTransplanInfo, OfcFundamentalInformation ofcFundamentalInformation
            , List<OfcGoodsDetailsInfo> goodsDetailsList, OfcDistributionBasicInfo ofcDistributionBasicInfo, String userName
            , OfcFinanceInformation ofcFinanceInformation) throws BusinessException {
        OfcTraplanSourceStatus ofcTraplanSourceStatus = new OfcTraplanSourceStatus();
        OfcTransplanStatus ofcTransplanStatus = new OfcTransplanStatus();
        OfcTransplanNewstatus ofcTransplanNewstatus = new OfcTransplanNewstatus();
        AtomicReference<OfcPlannedDetail> ofcPlannedDetail = new AtomicReference<>(null);
        try {
            BeanUtils.copyProperties(ofcTransplanInfo, ofcFundamentalInformation);
            BeanUtils.copyProperties(ofcTransplanInfo, ofcDistributionBasicInfo);
            ofcTransplanInfo.setPlanCode(codeGenUtils.getNewWaterCode(PLAN_PRE, 6));
            ofcTransplanInfo.setCreationTime(new Date());
            ofcTransplanInfo.setCreatePersonnel(userName);
            ofcTransplanInfo.setNotes(ofcDistributionBasicInfo.getTransRequire());
            BeanUtils.copyProperties(ofcTraplanSourceStatus, ofcDistributionBasicInfo);//$$$$
            BeanUtils.copyProperties(ofcTraplanSourceStatus, ofcTransplanInfo);
            BeanUtils.copyProperties(ofcTransplanStatus, ofcTransplanInfo);
            BeanUtils.copyProperties(ofcTransplanNewstatus, ofcTransplanInfo);
            ofcTransplanStatus.setPlannedSingleState(RESOURCE_ALLOCATION);
//            ofcTransplanNewstatus.setTransportSingleLatestStatus(ofcTransplanStatus.getPlannedSingleState());
//            ofcTransplanNewstatus.setTransportSingleUpdateTime(ofcTransplanInfo.getCreationTime());
            ofcTraplanSourceStatus.setResourceAllocationStatus(TO_BE_ASSIGNED);
            Iterator<OfcGoodsDetailsInfo> iter = goodsDetailsList.iterator();
            Map<String, List<OfcPlannedDetail>> ofcPlannedDetailMap = new HashMap<>();
            //保存计划单明细
            List<OfcPlannedDetail> ofcPlannedDetailList = savePlannedDetail(iter, ofcTransplanInfo);
            if (ofcPlannedDetailList.size() > 0) {
                ofcPlannedDetailMap.put(ofcPlannedDetailList.get(0).getPlanCode(), ofcPlannedDetailList);
            }
            RmcCompanyLineQO rmcCompanyLineQO = new RmcCompanyLineQO();
            if (!trimAndNullAsEmpty(ofcTransplanInfo.getBusinessType()).equals("600")
                    && !trimAndNullAsEmpty(ofcTransplanInfo.getBusinessType()).equals("601")) {

                if (!isSEmptyOrNull(ofcTransplanInfo.getDeparturePlaceCode()) && ofcTransplanInfo.getDeparturePlaceCode().length() > 12) {
                    String depatrueCode = ofcTransplanInfo.getDeparturePlaceCode().substring(0, 13);
                    String destinationCode = ofcTransplanInfo.getDestinationCode().substring(0, 13);
                    if (depatrueCode.equals(destinationCode)) {
                        ofcTransplanInfo.setBusinessType(BusinessTypeEnum.WITH_CITY.getCode());
                        rmcCompanyLineQO.setLineType("2");
                    } else {
                        ofcTransplanInfo.setBusinessType(BusinessTypeEnum.MAIN_LINE.getCode());
                        rmcCompanyLineQO.setLineType("1");
                    }
                } else {
                    throw new BusinessException("四级地址编码为空!");
                }
            } else if (trimAndNullAsEmpty(ofcTransplanInfo.getBusinessType()).equals("600")) {
                rmcCompanyLineQO.setLineType("2");
            } else {
                rmcCompanyLineQO.setLineType("1");
            }
            rmcCompanyLineQO.setBeginCityName(ofcTransplanInfo.getDepartureCity());
            rmcCompanyLineQO.setArriveCityName(ofcTransplanInfo.getDestinationCity());
            AtomicReference<Wrapper<List<RmcCompanyLineVo>>> companyList = new AtomicReference<>(null);
            try {
                companyList.set(companySelByApi(rmcCompanyLineQO));

            } catch (Exception ex) {
                throw new BusinessException("服务商查询发生错误", ex);
            }

            if (companyList.get().getCode() == 200
                    && !isEmpty(companyList.get().getResult())) {
                /**
                 * 平台类型。1、线下；2、天猫3、京东；4、鲜易网
                 */
                if (PlatformTypeEnum.XIAN_YI.getCode().equals(ofcFundamentalInformation.getPlatformType())) {
                    ofcTransplanInfo.setSingleSourceOfTransport(OrderSourceEnum.XEBEST.getCode());
                } else if (trimAndNullAsEmpty(ofcFundamentalInformation.getCustCode()).equals("100003")) {
                    ofcTransplanInfo.setSingleSourceOfTransport(OrderSourceEnum.SAP.getCode());
                } else if (isSEmptyOrNull(ofcFundamentalInformation.getPlatformType())) {
                    ofcTransplanInfo.setSingleSourceOfTransport(OrderSourceEnum.OTHERS.getCode());
                } else {
                    ofcTransplanInfo.setSingleSourceOfTransport(OrderSourceEnum.OTHERS.getCode());
                }
                if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsignorCode()).equals("")) {
                    ofcTransplanInfo.setShippinCustomerCode(ofcDistributionBasicInfo.getConsignorCode());
                }
                if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsignorName()).equals("")) {
                    ofcTransplanInfo.setShippinCustomerName(ofcDistributionBasicInfo.getConsignorName());
                }
                if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsignorContactName()).equals("")) {
                    ofcTransplanInfo.setShippingCustomerContact(ofcDistributionBasicInfo.getConsignorContactName());
                }
                if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsignorContactPhone()).equals("")) {
                    ofcTransplanInfo.setCustomerContactPhone(ofcDistributionBasicInfo.getConsignorContactPhone());
                }
                if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsigneeCode()).equals("")) {
                    ofcTransplanInfo.setReceivingCustomerCode(ofcDistributionBasicInfo.getConsigneeCode());
                }
                if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsigneeName()).equals("")) {
                    ofcTransplanInfo.setReceivingCustomerName(ofcDistributionBasicInfo.getConsigneeName());
                }
                if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsigneeContactName()).equals("")) {
                    ofcTransplanInfo.setReceivingCustomerContact(ofcDistributionBasicInfo.getConsigneeContactName());
                }
                if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsigneeContactPhone()).equals("")) {
                    ofcTransplanInfo.setReceivingCustomerContactPhone(ofcDistributionBasicInfo.getConsigneeContactPhone());
                }
                if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getDeparturePlace()).equals("")) {
                    ofcTransplanInfo.setShippingAddress(ofcDistributionBasicInfo.getDeparturePlace());
                }
                if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getDestination()).equals("")) {
                    ofcTransplanInfo.setReceivingCustomerAddress(ofcDistributionBasicInfo.getDestination());
                }
                if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getDestinationTowns()).equals("")) {
                    ofcTransplanInfo.setDestinationTown(ofcDistributionBasicInfo.getDestinationTowns());
                }
                if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getCubage()).equals("")) {
                    ofcTransplanInfo.setVolume(BigDecimal.valueOf(Double.valueOf(ofcDistributionBasicInfo.getCubage())));
                }
                if (!trimAndNullAsEmpty(ofcFundamentalInformation.getCustCode()).equals("")) {
                    ofcTransplanInfo.setCustCode(ofcFundamentalInformation.getCustCode());
                }

                RmcCompanyLineVo rmcCompanyLineVo = companyList.get().getResult().get(0);
                ofcTraplanSourceStatus.setServiceProviderName(rmcCompanyLineVo.getCompanyName());
                ofcTraplanSourceStatus.setServiceProviderContact(rmcCompanyLineVo.getContactName());
                ofcTraplanSourceStatus.setServiceProviderContactPhone(rmcCompanyLineVo.getCompanyPhone());
                ofcTraplanSourceStatus.setResourceAllocationStatus("40");
                ofcTraplanSourceStatus.setResourceConfirmation(ofcFundamentalInformation.getCustName());
                ofcTraplanSourceStatus.setResourceConfirmationTime(new Date());

                String businessType = trimAndNullAsEmpty(ofcTransplanInfo.getBusinessType());
                if (businessType.equals("600") || businessType.equals("601")) {
                    OrderScreenCondition condition = new OrderScreenCondition();
                    condition.setOrderCode(ofcTransplanInfo.getOrderCode());
                    //String status=ofcOrderStatusService.orderStatusSelect(condition.getOrderCode(),"orderCode").getOrderStatus();
                    saveOrderStatusOfImplemente(ofcTransplanInfo, userName);
                }
                RmcServiceCoverageForOrderVo rmcPickup = null;
                if (trimAndNullAsEmpty(ofcTransplanInfo.getBaseId()).equals("")) {
                    RmcServiceCoverageForOrderVo rmcServiceCoverageForOrderVo = new RmcServiceCoverageForOrderVo();
                    rmcServiceCoverageForOrderVo = copyDestinationPlace(ofcDistributionBasicInfo.getDeparturePlaceCode(), rmcServiceCoverageForOrderVo);
                    rmcPickup = rmcServiceCoverageAPI(rmcServiceCoverageForOrderVo, "Pickup");
                    if (rmcPickup != null) {
                        if (!trimAndNullAsEmpty(rmcPickup.getBaseCode()).equals("")) {
                            ofcTransplanInfo.setBaseId(rmcPickup.getBaseCode());
                        }
                        if (!trimAndNullAsEmpty(rmcPickup.getBaseName()).equals("")) {
                            ofcTransplanInfo.setBaseName(rmcPickup.getBaseName());
                        }
                    }
                } else if (!trimAndNullAsEmpty(ofcTransplanInfo.getBaseId()).equals("")
                        && StringUtils.equals(ofcFundamentalInformation.getOperator(), CREATE_ORDER_BYAPI)) {
                    //鲜易网, 给的BaseId是有值的, 但不可用!
                    RmcServiceCoverageForOrderVo rmcServiceCoverageForOrderVo = new RmcServiceCoverageForOrderVo();
                    rmcServiceCoverageForOrderVo = copyDestinationPlace(ofcDistributionBasicInfo.getDeparturePlaceCode(), rmcServiceCoverageForOrderVo);
                    rmcPickup = rmcServiceCoverageAPI(rmcServiceCoverageForOrderVo, "Pickup");
                }
                List<OfcTransplanInfo> ofcTransplanInfoList = new ArrayList<>();
                ofcTransplanInfoList.add(ofcTransplanInfo);
                // 运输单对应的客户订单号
                Map<String, String> custOrderCodes = new HashMap<>();
                custOrderCodes.put(ofcTransplanInfo.getPlanCode(), ofcFundamentalInformation.getCustOrderCode());
                if (!trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(WITH_THE_KABAN)) {
                    //向TFC推送
                    logger.info("计划单最新状态保存成功");
                    ofcTransplanStatusService.save(ofcTransplanStatus);
                    ofcTransplanInfoToTfc(ofcTransplanInfoList, ofcPlannedDetailMap, userName, custOrderCodes);
                    //订单来源为钉钉录单时
                    if (rmcPickup != null) {
                        if (!isSEmptyOrNull(ofcFundamentalInformation.getOrderSource())) {
                            if (StringUtils.equals(ofcFundamentalInformation.getOrderSource(), DING_DING)
                                    || StringUtils.equals(ofcFundamentalInformation.getOperator(), CREATE_ORDER_BYAPI)) {
                                //更新订单的大区和基地
                                updateOrderAreaAndBase(rmcPickup, ofcFundamentalInformation);
                            }
                        }
                    }
                } else if (trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(WITH_THE_KABAN)) {
                    //如果是卡班订单,则应该向DMS推送卡班订单
                    //ofcDistributionBasicInfo.setTransCode("kb"+System.currentTimeMillis());
                    pushKabanOrderToDms(ofcDistributionBasicInfo, ofcTransplanInfo);
                    //订单推送结算中心
                    // pushOrderToAc(ofcFundamentalInformation,ofcFinanceInformation,ofcDistributionBasicInfo,goodsDetailsList);

                }
                ofcTransplanInfoService.save(ofcTransplanInfo);
                //审核后, 将计划单号推给ac
                AcPlanDto acPlanDto = new AcPlanDto();
                acPlanDto.setOrderCode(ofcFundamentalInformation.getOrderCode());
                List<String> planCodeList = new ArrayList<>();
                planCodeList.add(ofcTransplanInfo.getPlanCode());
                acPlanDto.setPlanCodeList(planCodeList);
                pullOfcOrderPlanCode(acPlanDto);

                logger.info("计划单信息保存成功");
                ofcTransplanNewstatusService.save(ofcTransplanNewstatus);

                logger.info("计划单状态保存成功");
                ofcTraplanSourceStatusService.save(ofcTraplanSourceStatus);
                logger.info("计划单资源状态保存成功");
            } else {
                if (isEmpty(companyList.get().getResult())) {
                    throw new BusinessException("没有查询到相关服务商!");
                }
                throw new BusinessException(companyList.get().getMessage());
            }


            /*planUpdate(ofcTransplanInfo.getPlanCode(),"40",ofcTraplanSourceStatus.getServiceProviderName()
                    ,ofcTraplanSourceStatus.getServiceProviderContact(),ofcTraplanSourceStatus.getServiceProviderContactPhone(),ofcFundamentalInformation.getCustName());//&&&&&*/

            //更改计划单状态为执行中//###


        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            if (ex.getCause().getMessage().trim().startsWith("Duplicate entry")) {
                throw new BusinessException("获取单号发生重复，导致保存计划单信息发生错误！", ex);
            } else {
                throw new BusinessException("保存计划单信息发生错误！", ex);
            }
        }
    }

    /**
     * 保存货品详情
     * @param ofcGoodsDetailsInfos 订单货品明细信息
     * @param ofcFundamentalInformation 基本信息
     */
//    private BigDecimal saveDetails(List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos,OfcFundamentalInformation ofcFundamentalInformation){
//        BigDecimal goodsAmountCount = new BigDecimal(0);
//        for(OfcGoodsDetailsInfo ofcGoodsDetails : ofcGoodsDetailsInfos){
//            if(ofcGoodsDetails.getQuantity() == null || ofcGoodsDetails.getQuantity().compareTo(new BigDecimal(0)) == 0 ){
//                if((ofcGoodsDetails.getWeight() != null && ofcGoodsDetails.getWeight().compareTo(new BigDecimal(0)) != 0 ) || (ofcGoodsDetails.getCubage() != null && ofcGoodsDetails.getCubage().compareTo(new BigDecimal(0)) != 0 )){
//                }else{
//                    continue;
//                }
//            }
//            String orderCode = ofcFundamentalInformation.getOrderCode();
//            ofcGoodsDetails.setOrderCode(orderCode);
//            ofcGoodsDetails.setCreationTime(ofcFundamentalInformation.getCreationTime());
//            ofcGoodsDetails.setCreator(ofcFundamentalInformation.getCreator());
//            ofcGoodsDetails.setOperator(ofcFundamentalInformation.getOperator());
//            ofcGoodsDetails.setOperTime(ofcFundamentalInformation.getOperTime());
//            goodsAmountCount = goodsAmountCount.add(ofcGoodsDetails.getQuantity(), new MathContext(3));
//            ofcGoodsDetailsInfoService.save(ofcGoodsDetails);
//        }
//        return goodsAmountCount;
//    }

    /**
     * 创建运输计划单-卡班
     *
     * @param ofcTransplanInfo          计划单基本信息
     * @param ofcFundamentalInformation 订单基本信息
     * @param goodsDetailsList          货品明细
     * @param ofcDistributionBasicInfo  运输基本信息
     */
    private void transPlanCreateKaBan(OfcTransplanInfo ofcTransplanInfo, OfcFundamentalInformation ofcFundamentalInformation
            , List<OfcGoodsDetailsInfo> goodsDetailsList, OfcDistributionBasicInfo ofcDistributionBasicInfo
            , OfcFinanceInformation ofcFinanceInformation, String userName) {
        OfcTraplanSourceStatus ofcTraplanSourceStatus = new OfcTraplanSourceStatus();
        OfcTransplanStatus ofcTransplanStatus = new OfcTransplanStatus();
        OfcTransplanNewstatus ofcTransplanNewstatus = new OfcTransplanNewstatus();
        AtomicReference<OfcPlannedDetail> ofcPlannedDetail = new AtomicReference<>(null);
        try {
            ofcTransplanInfo.setPlanCode(codeGenUtils.getNewWaterCode(PLAN_PRE, 6));
            ofcTransplanInfo.setCreationTime(new Date());
            ofcTransplanInfo.setCreatePersonnel(userName);
            //ofcTransplanInfo.setNotes(ofcDistributionBasicInfo.getTransRequire());
            BeanUtils.copyProperties(ofcTraplanSourceStatus, ofcDistributionBasicInfo);//$$$$
            BeanUtils.copyProperties(ofcTraplanSourceStatus, ofcTransplanInfo);
            BeanUtils.copyProperties(ofcTransplanStatus, ofcTransplanInfo);
            BeanUtils.copyProperties(ofcTransplanNewstatus, ofcTransplanInfo);
            ofcTransplanStatus.setPlannedSingleState(RESOURCE_ALLOCATION);
//            ofcTransplanNewstatus.setTransportSingleLatestStatus(ofcTransplanStatus.getPlannedSingleState());
//            ofcTransplanNewstatus.setTransportSingleUpdateTime(ofcTransplanInfo.getCreationTime());
            ofcTraplanSourceStatus.setResourceAllocationStatus(TO_BE_ASSIGNED);
            Iterator<OfcGoodsDetailsInfo> iter = goodsDetailsList.iterator();
            Map<String, List<OfcPlannedDetail>> ofcPlannedDetailMap = new HashMap<>();
            //保存计划单明细
            List<OfcPlannedDetail> ofcPlannedDetailList = savePlannedDetail(iter, ofcTransplanInfo);
            if (PubUtils.isNotNullAndBiggerSize(ofcPlannedDetailList, 0)) {
                ofcPlannedDetailMap.put(ofcPlannedDetailList.get(0).getPlanCode(), ofcPlannedDetailList);
            }
            RmcCompanyLineQO rmcCompanyLineQO = new RmcCompanyLineQO();
            if (!trimAndNullAsEmpty(ofcTransplanInfo.getBusinessType()).equals("600")
                    && !trimAndNullAsEmpty(ofcTransplanInfo.getBusinessType()).equals("601")) {

                if (!isSEmptyOrNull(ofcTransplanInfo.getDeparturePlaceCode()) && ofcTransplanInfo.getDeparturePlaceCode().length() > 12) {
                    String depatrueCode = ofcTransplanInfo.getDeparturePlaceCode().substring(0, 13);
                    String destinationCode = ofcTransplanInfo.getDestinationCode().substring(0, 13);
                    if (depatrueCode.equals(destinationCode)) {
                        rmcCompanyLineQO.setLineType("2");
                    } else {
                        rmcCompanyLineQO.setLineType("1");

                    }
                } else {
                    throw new BusinessException("四级地址编码为空!");
                }
            } else if (trimAndNullAsEmpty(ofcTransplanInfo.getBusinessType()).equals("600")) {
                rmcCompanyLineQO.setLineType("2");
            } else {
                rmcCompanyLineQO.setLineType("1");
            }
            rmcCompanyLineQO.setBeginCityName(ofcTransplanInfo.getDepartureCity());
            rmcCompanyLineQO.setArriveCityName(ofcTransplanInfo.getDestinationCity());
            AtomicReference<Wrapper<List<RmcCompanyLineVo>>> companyList = new AtomicReference<>(null);
            try {
                companyList.set(companySelByApi(rmcCompanyLineQO));

            } catch (Exception ex) {
                throw new BusinessException("查询服务商发生错误", ex);
            }

            if ((companyList.get() != null) && (companyList.get().getCode() == 200)
                    && !isEmpty(companyList.get().getResult())) {
                //平台类型。1、线下；2、天猫3、京东；4、鲜易网
                if ("4".equals(ofcFundamentalInformation.getPlatformType())) {
                    ofcTransplanInfo.setSingleSourceOfTransport(OrderSourceEnum.XEBEST.getCode());
                } else if (trimAndNullAsEmpty(ofcFundamentalInformation.getCustCode()).equals("100003")) {
                    ofcTransplanInfo.setSingleSourceOfTransport(OrderSourceEnum.SAP.getCode());
                } else if (isSEmptyOrNull(ofcFundamentalInformation.getPlatformType())) {
                    ofcTransplanInfo.setSingleSourceOfTransport(OrderSourceEnum.OTHERS.getCode());
                } else {
                    ofcTransplanInfo.setSingleSourceOfTransport(OrderSourceEnum.OTHERS.getCode());
                }

                if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getCubage()).equals("")) {
                    ofcTransplanInfo.setVolume(BigDecimal.valueOf(Double.valueOf(ofcDistributionBasicInfo.getCubage())));
                }
                if (!trimAndNullAsEmpty(ofcFundamentalInformation.getCustCode()).equals("")) {
                    ofcTransplanInfo.setCustCode(ofcFundamentalInformation.getCustCode());
                }
                RmcCompanyLineVo rmcCompanyLineVo = companyList.get().getResult().get(0);
                ofcTraplanSourceStatus.setServiceProviderName(rmcCompanyLineVo.getCompanyName());
                ofcTraplanSourceStatus.setServiceProviderContact(rmcCompanyLineVo.getContactName());
                ofcTraplanSourceStatus.setServiceProviderContactPhone(rmcCompanyLineVo.getCompanyPhone());
                ofcTraplanSourceStatus.setResourceAllocationStatus("40");
                ofcTraplanSourceStatus.setResourceConfirmation(ofcFundamentalInformation.getCustName());
                ofcTraplanSourceStatus.setResourceConfirmationTime(new Date());
                List<OfcTransplanInfo> ofcTransplanInfoList = new ArrayList<>();
                String businessType = trimAndNullAsEmpty(ofcTransplanInfo.getBusinessType());
                if (businessType.equals("600") || businessType.equals("601") || businessType.equals("602")) {
                    //OrderScreenCondition condition=new OrderScreenCondition();
                    //condition.setOrderCode(ofcTransplanInfo.getOrderCode());
                    //String status=ofcOrderStatusService.orderStatusSelect(condition.getOrderCode(),"orderCode").getOrderStatus();
                    if (trimAndNullAsEmpty(ofcTransplanInfo.getProgramSerialNumber()).equals("1")) {
                        saveOrderStatusOfImplemente(ofcTransplanInfo, userName);
                    }
                }
                if (!trimAndNullAsEmpty(ofcFundamentalInformation.getNotes()).equals("")) {
                    ofcTransplanInfo.setNotes(ofcFundamentalInformation.getNotes());
                }
                if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getGoodsType()).equals("")) {
                    ofcTransplanInfo.setGoodsType(ofcDistributionBasicInfo.getGoodsType());
                }
                if (!trimAndNullAsEmpty(ofcFinanceInformation.getTwoDistribution()).equals("")) {
                    ofcTransplanInfo.setTwoDistribution(ofcFinanceInformation.getTwoDistribution());
                }
                if (trimAndNullAsEmpty(ofcTransplanInfo.getBusinessType()).equals(WITH_THE_CITY)) {//卡班拆城配
                    //向TFC推送
                    logger.info("计划单状态保存成功");
                    ofcTransplanStatusService.save(ofcTransplanStatus);
                    ofcTransplanInfoList.add(ofcTransplanInfo);

                    Map<String, String> custOrderCodes = new HashMap<>();
                    custOrderCodes.put(ofcTransplanInfo.getPlanCode(), ofcFundamentalInformation.getCustOrderCode());

                    ofcTransplanInfoToTfc(ofcTransplanInfoList, ofcPlannedDetailMap, userName, custOrderCodes);
                } else if (trimAndNullAsEmpty(ofcTransplanInfo.getBusinessType()).equals(WITH_THE_KABAN)) {//卡班拆卡班
                    //如果是卡班订单,则应该向DMS推送卡班订单
                    ofcTransplanStatusService.save(ofcTransplanStatus);
                    pushKabanOrderToDms(ofcDistributionBasicInfo, ofcTransplanInfo);
                    //订单推送结算中心,这期暂时不上
                    //pushOrderToAc(ofcFundamentalInformation,ofcFinanceInformation,ofcDistributionBasicInfo,goodsDetailsList);
                }
                try {
                    ofcTransplanInfoService.save(ofcTransplanInfo);

                    //审核后, 将计划单号推给ac
                    AcPlanDto acPlanDto = new AcPlanDto();
                    acPlanDto.setOrderCode(ofcFundamentalInformation.getOrderCode());
                    List<String> planCodeList = new ArrayList<>();
                    planCodeList.add(ofcTransplanInfo.getPlanCode());
                    acPlanDto.setPlanCodeList(planCodeList);
                    pullOfcOrderPlanCode(acPlanDto);
                    logger.info("计划单信息保存成功");
                } catch (Exception ex) {
                    if (ex.getCause().getMessage().trim().startsWith("Duplicate entry")) {
                        logger.error("获取单号发生重复，导致保存计划单信息发生错误！{}", ex);
                        throw new BusinessException("获取单号发生重复，导致保存计划单信息发生错误！");
                    } else {
                        logger.error("保存计划单信息发生错误:{}", ex);
                        throw ex;
                    }
                }
                ofcTransplanNewstatusService.save(ofcTransplanNewstatus);
                logger.info("计划单状态保存成功");
                ofcTraplanSourceStatusService.save(ofcTraplanSourceStatus);
                logger.info("计划单资源状态保存成功");

            } else {
                if ((companyList.get() != null) && isEmpty(companyList.get().getResult())) {
                    throw new BusinessException("没有查询到相关服务商!");
                }
            }
            /*planUpdate(ofcTransplanInfo.getPlanCode(),"40",ofcTraplanSourceStatus.getServiceProviderName()
                    ,ofcTraplanSourceStatus.getServiceProviderContact(),ofcTraplanSourceStatus.getServiceProviderContactPhone(),ofcFundamentalInformation.getCustName());//&&&&&*/

            //更改计划单状态为执行中//###
        } catch (BusinessException ex) {
            logger.error("创建卡班运输计划单发生异常: {}", ex);
            throw ex;
        } catch (Exception ex) {
            logger.error("创建卡班运输计划单发生异常: {}", ex);
            throw new BusinessException("创建卡班运输计划单发生异常! ");
        }
    }

    /**
     * 订单中心向DMS推送卡班订单
     *
     * @param ofcDistributionBasicInfo 运输基本信息
     */
    private void pushKabanOrderToDms(OfcDistributionBasicInfo ofcDistributionBasicInfo, OfcTransplanInfo ofcTransplanInfo) {
        OfcDistributionBasicInfoDto pushDistributionBasicInfo = new OfcDistributionBasicInfoDto();
        try {
            BeanUtils.copyProperties(pushDistributionBasicInfo, ofcDistributionBasicInfo);
        } catch (Exception e) {
            throw new BusinessException("推送卡班信息拷贝属性错误", e);
        }
        if (!trimAndNullAsEmpty(ofcTransplanInfo.getNotes()).equals("")) {
            pushDistributionBasicInfo.setNotes(ofcTransplanInfo.getNotes());
        }
        if (!trimAndNullAsEmpty(ofcTransplanInfo.getCustName()).equals("")) {
            pushDistributionBasicInfo.setCustName(ofcTransplanInfo.getCustName());
        }
        if (!trimAndNullAsEmpty(ofcTransplanInfo.getCustCode()).equals("")) {
            pushDistributionBasicInfo.setCustCode(ofcTransplanInfo.getCustCode());
        }
        if (!trimAndNullAsEmpty(ofcTransplanInfo.getTwoDistribution()).equals("")) {
            pushDistributionBasicInfo.setTwoDistribution(ofcTransplanInfo.getTwoDistribution());
        }
        OfcDistributionBasicInfoDto ofcDistributionBasicInfoDtoEpc = new OfcDistributionBasicInfoDto();
        try {
            BeanUtils.copyProperties(ofcDistributionBasicInfoDtoEpc, pushDistributionBasicInfo);
        } catch (Exception e) {
            logger.error("订单中心向DMS推送卡班订单, 实体转换异常,{}", e);
        }
        Wrapper<?> wrapper = epcOfc2DmsEdasService.addDistributionBasicInfo(ofcDistributionBasicInfoDtoEpc);
        if (ERROR_CODE == wrapper.getCode()) {
            logger.error("向分拣中心推送卡班订单失败,失败原因:{}", wrapper.getMessage());
            throw new BusinessException("向分拣中心推送卡班订单失败");
        } else if (Objects.equals("100101", String.format("%d", wrapper.getCode()))) {
            logger.error("分拣中心已存在您所输入的运输单号,请重新输入!");
            throw new BusinessException("分拣中心已存在您所输入的运输单号,请重新输入!");
        }
        //更新运输计划单状态为已推送, 略, 因只更新不记录
        //一旦向DMS推送过去, 就更新运输计划单状态为执行中
        OfcTransplanStatus ofcTransplanStatus = new OfcTransplanStatus();
        ofcTransplanStatus.setPlanCode(ofcTransplanInfo.getPlanCode());
        ofcTransplanStatus.setPlannedSingleState(TASK);
        ofcTransplanStatusService.updateByPlanCode(ofcTransplanStatus);
    }


    /**
     * 创建仓储计划单
     *
     * @param ofcSiloprogramInfo        仓储计划单基本信息
     * @param ofcFundamentalInformation 订单基本信息
     * @param goodsDetailsList          货品明细
     * @param ofcWarehouseInformation   仓储基本信息
     * @param ofcFinanceInformation     费用基本信息
     */
    private String siloProCreate(OfcSiloprogramInfo ofcSiloprogramInfo, OfcFundamentalInformation ofcFundamentalInformation
            , List<OfcGoodsDetailsInfo> goodsDetailsList, OfcWarehouseInformation ofcWarehouseInformation
            , OfcFinanceInformation ofcFinanceInformation, OfcDistributionBasicInfo ofcDistributionBasicInfo, String userId) {
        String planCode;
        OfcSiloproStatus ofcSiloproStatus = new OfcSiloproStatus();
        OfcSiloproNewstatus ofcSiloproNewstatus = new OfcSiloproNewstatus();
        OfcSiloproSourceStatus ofcSiloproSourceStatus = new OfcSiloproSourceStatus();
        OfcPlannedDetail ofcPlannedDetail = new OfcPlannedDetail();
        try {
            BeanUtils.copyProperties(ofcSiloprogramInfo, ofcDistributionBasicInfo);
            if (ofcFinanceInformation != null) {
                BeanUtils.copyProperties(ofcSiloprogramInfo, ofcFinanceInformation);
            }
            BeanUtils.copyProperties(ofcSiloprogramInfo, ofcWarehouseInformation);
            BeanUtils.copyProperties(ofcSiloprogramInfo, ofcFundamentalInformation);
            ofcSiloprogramInfo.setPlanCode(codeGenUtils.getNewWaterCode(SILOPROGRAMINFO_PRE, 6));
            planCode = ofcSiloprogramInfo.getPlanCode();
            ofcSiloprogramInfo.setDocumentType(ofcSiloprogramInfo.getBusinessType());
            if (trimAndNullAsEmpty(ofcSiloprogramInfo.getDocumentType()).substring(0, 2).equals("61")) {
                //出库
                ofcSiloprogramInfo.setBusinessType("出库");

            } else if (trimAndNullAsEmpty(ofcSiloprogramInfo.getDocumentType()).substring(0, 2).equals("62")) {
                //入库
                ofcSiloprogramInfo.setBusinessType("入库");
            }
            ofcSiloprogramInfo.setCreationTime(new Date());
            ofcSiloprogramInfo.setCreatePersonnel(userId);
            BeanUtils.copyProperties(ofcSiloproSourceStatus, ofcWarehouseInformation);
            BeanUtils.copyProperties(ofcSiloproSourceStatus, ofcSiloprogramInfo);
            BeanUtils.copyProperties(ofcSiloproStatus, ofcSiloprogramInfo);
            BeanUtils.copyProperties(ofcSiloproNewstatus, ofcSiloprogramInfo);
            ofcSiloproStatus.setPlannedSingleState(RESOURCE_ALLOCATION);
            //ofcSiloproNewstatus.setJobNewStatus(ofcSiloproStatus.getPlannedSingleState());
            //ofcSiloproNewstatus.setJobStatusUpdateTime(ofcSiloprogramInfo.getCreationTime());
            ofcSiloproSourceStatus.setResourceAllocationStatus(ALREADY_DETERMINED);
            for (OfcGoodsDetailsInfo aGoodsDetailsList : goodsDetailsList) {
                //保存计划单明细
                ofcPlannedDetail.setPlanCode(ofcSiloprogramInfo.getPlanCode());
                if (((aGoodsDetailsList.getQuantity() != null) && (aGoodsDetailsList.getQuantity().compareTo(new BigDecimal(0)) != 0))
                        || ((aGoodsDetailsList.getWeight() != null) && (aGoodsDetailsList.getWeight().compareTo(new BigDecimal(0)) != 0))
                        || ((aGoodsDetailsList.getCubage() != null) && (aGoodsDetailsList.getCubage().compareTo(new BigDecimal(0)) != 0))) {
                    aGoodsDetailsList.setGoodsCode(aGoodsDetailsList.getGoodsCode().split("@")[0]);
                    BeanUtils.copyProperties(ofcPlannedDetail, aGoodsDetailsList);
                    BeanUtils.copyProperties(ofcPlannedDetail, ofcSiloprogramInfo);
                    ofcPlannedDetailService.save(ofcPlannedDetail);
                }

                logger.info("计划单明细保存成功");
            }
            ofcSiloprogramInfoService.save(ofcSiloprogramInfo);
            logger.info("计划单信息保存成功");
            ofcSiloproNewstatusService.save(ofcSiloproNewstatus);
            logger.info("计划单最新状态保存成功");
            ofcSiloproStatusService.save(ofcSiloproStatus);
            logger.info("计划单状态保存成功");
            ofcSiloproSourceStatusService.save(ofcSiloproSourceStatus);
            logger.info("计划单资源状态保存成功");
        } catch (Exception ex) {
            if (ex.getCause().getMessage().trim().startsWith("Duplicate entry")) {
                logger.error("获取单号发生重复:{}", ex);
                throw new BusinessException("获取单号发生重复！");
            } else {
                logger.error("创建仓储计划单发生异常：{}", ex);
                throw new BusinessException("创建仓储计划单发生异常！");
            }
        }
        return planCode;
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
                Wrapper<Integer> cancelStatus = acOrderEdasService.queryOrderCancelStatus(ofcFundamentalInformation.getOrderCode());
                if (cancelStatus.getCode() == 200) {
                    try {
                        response= orderCancelToTfc(orderCode);
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
                        response= orderCancelToTfc(orderCode);
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
     * 计划单取消
     *
     * @param orderCode 订单编号
     * @param userName  用户名
     */
    private void planCancle(String orderCode, String userName) {
        logger.info("==> orderCode={}", orderCode);
        logger.info("==> userName={}", userName);
        List<OfcTransplanInfo> ofcTransplanInfoList = ofcTransplanInfoService.ofcTransplanInfoScreenList(orderCode);
        for (OfcTransplanInfo ofcTransplanInfo : ofcTransplanInfoList) {
            if (ofcTransplanInfo != null) {
                OfcTransplanStatus ofcTransplanStatus = new OfcTransplanStatus();
                ofcTransplanStatus.setPlanCode(ofcTransplanInfo.getPlanCode());
                ofcTransplanStatus = ofcTransplanStatusService.selectOne(ofcTransplanStatus);
                if (!trimAndNullAsEmpty(ofcTransplanInfo.getBusinessType()).equals(WITH_THE_KABAN)) {
                    try {
                        //defaultMqProducer.toSendMQTfcCancelPlanOrder(JacksonUtil.toJsonWithFormat(ofcTransplanInfo.getPlanCode()));
                        TransportNoDTO transportNoDTO = new TransportNoDTO();
                        transportNoDTO.setTransportNo(ofcTransplanInfo.getPlanCode());
                        Wrapper response = epcOrderCancelEdasService.cancelTransport(transportNoDTO);
                        if (response == null) {
                            logger.error("调用epc接口出现异常!取消订单失败!{}", "epc返回的订单取消状态为空");
                            throw new BusinessException("您无法取消!");
                        }
                        if (Response.ERROR_CODE == response.getCode()) {
                            //运单号不存在,没有发现该订单
                            //该订单已经取消, 取消失败
                            logger.error("您无法取消,请联系管理员!{}", response.getResult());
                            throw new BusinessException("您无法取消,{}", response.getResult().toString());
                        }
                    } catch (BusinessException ex) {
                        throw ex;
                    } catch (Exception ex) {
                        logger.error("运输计划单调用TFC取消端口出现异常{}", ex.getMessage(), ex);
                        throw new BusinessException("运输计划单调用TFC取消端口出现异常{}", ex.getMessage(), ex);
                    }
                    if (trimAndNullAsEmpty(ofcTransplanStatus.getPlannedSingleState()).equals(TASK)
                            || trimAndNullAsEmpty(ofcTransplanStatus.getPlannedSingleState()).equals(TASK_ACCOMPLISHED)) {
                        throw new BusinessException("该订单状态已在作业中或已完成，无法取消");
                    }
                }
                if (trimAndNullAsEmpty(ofcTransplanStatus.getPlannedSingleState()).equals(ALREADY_CANCELLED)) {
                    throw new BusinessException("状态错误，该计划单已作废");
                }/*else if (PubUtils.trimAndNullAsEmpty(ofcTransplanStatus.getPlannedSingleState()).equals(ALREADY_PUSH)){
                throw new BusinessException("其是运输计划单，需调用【配送中心】运单取消接口");
                }*/ else if (trimAndNullAsEmpty(ofcTransplanStatus.getPlannedSingleState()).equals("")) {
                    throw new BusinessException("状态有误");
                }
                ofcTransplanInfo.setVoidPersonnel(userName);
                ofcTransplanInfo.setVoidTime(new Date());
                    /*OfcTransplanNewstatus ofcTransplanNewstatus=new OfcTransplanNewstatus();
                    ofcTransplanNewstatus.setPlanCode(ofcTransplanInfo.getPlanCode());*/
                ofcTransplanStatus.setPlannedSingleState(ALREADY_CANCELLED);
                //ofcTransplanNewstatus.setTransportSingleLatestStatus("50");
                //ofcTransplanNewstatusService.updateByPlanCode(ofcTransplanNewstatus);
                ofcTransplanStatusService.updateByPlanCode(ofcTransplanStatus);
                ofcTransplanInfoService.update(ofcTransplanInfo);
            }
        }
        List<OfcSiloprogramInfo> ofcSiloprogramInfoList = ofcSiloprogramInfoService.ofcSiloprogramInfoScreenList(orderCode);
        for (OfcSiloprogramInfo ofcSiloprogramInfo : ofcSiloprogramInfoList) {
            Wrapper response = null;
            OfcSiloproStatus ofcSiloproStatus = new OfcSiloproStatus();
            ofcSiloproStatus.setPlanCode(ofcSiloprogramInfo.getPlanCode());
            ofcSiloproStatus = ofcSiloproStatusService.selectOne(ofcSiloproStatus);
            if (trimAndNullAsEmpty(ofcSiloproStatus.getPlannedSingleState()).equals(ALREADY_CANCELLED)) {
                throw new BusinessException("状态错误，该计划单已作废");
            }
            if (trimAndNullAsEmpty(ofcSiloproStatus.getPlannedSingleState()).equals(TASK)
                    || trimAndNullAsEmpty(ofcSiloproStatus.getPlannedSingleState()).equals(TASK_ACCOMPLISHED)) {
                throw new BusinessException("该订单状态已在作业中或已完成，无法取消");
            }
            if (trimAndNullAsEmpty(ofcSiloproStatus.getPlannedSingleState()).equals("")) {
                throw new BusinessException("状态有误");
            }

            if (trimAndNullAsEmpty(ofcSiloproStatus.getPlannedSingleState()).equals(ALREADY_PUSH)) {
                try {
                    OfcCancelOrderDTO dto = new OfcCancelOrderDTO();
                    dto.setOrderNo(ofcSiloprogramInfo.getPlanCode());
                    dto.setOrderType(ofcSiloprogramInfo.getDocumentType());
                    if (OFC_WHC_IN_TYPE.equals(ofcSiloprogramInfo.getBusinessType())) {
                        dto.setBillType(ORDER_TYPE_IN);
                    } else if (OFC_WHC_OUT_TYPE.equals(ofcSiloprogramInfo.getBusinessType())) {
                        dto.setBillType(ORDER_TYPE_OUT);
                    }
                    dto.setCustomerID(ofcSiloprogramInfo.getCustCode());
                    dto.setWarehouseID(ofcSiloprogramInfo.getWarehouseCode());
                    dto.setReason("");
                    logger.info("==> 仓储计划单号{}开始取消,业务类型为{}", ofcSiloprogramInfo.getPlanCode(), ofcSiloprogramInfo.getDocumentType());
                    response = whcOrderCancelEdasService.cancelOrder(dto);
                } catch (Exception e) {
                    logger.error("仓储计划单调用WHC取消端口出现异常{}", e.getMessage(), e);
                    throw new BusinessException("仓储计划单调用WHC取消端口出现异常{}", e);
                }
                if (null != response) {
                    if (Response.ERROR_CODE == response.getCode()) {
                        logger.error("仓储计划单调用WHC响应状态码{}", response.getCode());
                        throw new BusinessException(response.getMessage());
                    }
                } else {
                    throw new BusinessException("仓储计划单调用WHC取消端口出现异常");
                }
            }
            if (Response.SUCCESS_CODE == (response != null ? response.getCode() : 0)) {
                ofcSiloprogramInfo.setVoidPersonnel(userName);
                ofcSiloprogramInfo.setVoidTime(new Date());
                //OfcTransplanNewstatus ofcTransplanNewstatus=new OfcTransplanNewstatus();
                //ofcTransplanNewstatus.setPlanCode(ofcTransplanInfo.getPlanCode());
                ofcSiloproStatus.setPlannedSingleState(ALREADY_CANCELLED);
                //ofcTransplanNewstatus.setTransportSingleLatestStatus("50");
                //ofcTransplanNewstatusService.updateByPlanCode(ofcTransplanNewstatus);
                // ofcSiloproStatusService.updateByPlanCode(ofcSiloproStatus);
                ofcSiloprogramInfoService.update(ofcSiloprogramInfo);
            }
        }
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
     * 计划单更新
     *
     * @param planCode                    计划单编号
     * @param planStatus                  计划单状态
     * @param serviceProviderName         服务商
     * @param serviceProviderContact      服务商联系人
     * @param serviceProviderContactPhone 服务商联系电话
     * @param userName                    用户名
     * @return String
     */
    @Override
    public String planUpdate(String planCode, String planStatus, String serviceProviderName, String serviceProviderContact
            , String serviceProviderContactPhone, String userName) {
        OfcTraplanSourceStatus ofcTraplanSourceStatus = new OfcTraplanSourceStatus();
        if (!trimAndNullAsEmpty(planCode).equals("")) {
            String[] planCodeList = planCode.split("@");//批量更新，计划单编号以@分隔
            //List<OfcTransplanInfo> ofcTransplanInfoList = new ArrayList<OfcTransplanInfo>();
            List<OfcTransplanInfo> ofcTransplanInfoList = new ArrayList<>();
            Map<String, List<OfcPlannedDetail>> ofcPlannedDetailMap = new HashMap<>();
            // 给TFC传客户订单号
            Map<String, String> custOrderCodes = new HashMap<>();
            try {
                for (String plan_code : planCodeList) {

                    ofcTraplanSourceStatus.setPlanCode(plan_code);

                    OfcPlannedDetail ofcPlannedDetail = new OfcPlannedDetail();
                    //ofcPlannedDetail.setPlanCode(plan_code);
                    List<OfcPlannedDetail> ofcPlannedDetailList = ofcPlannedDetailService.select(ofcPlannedDetail);
                    ofcPlannedDetailMap.put(ofcTraplanSourceStatus.getPlanCode(), ofcPlannedDetailList);
                    ofcTraplanSourceStatus.setResourceAllocationStatus(planStatus);
                    if (planStatus.equals("40")) {
                        OfcTransplanInfo ofcTransplanInfo = ofcTransplanInfoService.selectByKey(plan_code);
                        ofcTransplanInfoList.add(ofcTransplanInfo);

                        try {
                            OfcFundamentalInformation param = new OfcFundamentalInformation();
                            param.setOrderCode(ofcTransplanInfo.getOrderCode());
                            OfcFundamentalInformation ofcFundamentalInfo = ofcFundamentalInformationService.selectOne(param);
                            custOrderCodes.put(plan_code, ofcFundamentalInfo.getCustOrderCode());
                        } catch (Exception ex) {
                            logger.error("==> 根据运输计划单号查询订单信息发生错误！{}", ex);
                            throw new BusinessException("根据运输计划单号查询订单信息发生错误！");
                        }

                        String businessType = trimAndNullAsEmpty(ofcTransplanInfo.getBusinessType());
                        if (businessType.equals("600") || businessType.equals("601")) {
                            OrderScreenCondition condition = new OrderScreenCondition();
                            condition.setOrderCode(ofcTransplanInfo.getOrderCode());
                            String status = ofcOrderStatusService.orderStatusSelect(condition.getOrderCode(), "orderCode").getOrderStatus();
                            if (trimAndNullAsEmpty(status).equals("")) {
                                throw new BusinessException("订单状态更新异常！");
                            } else if (!status.equals(ALREADY_EXAMINE)
                                    && ofcSiloprogramInfoService.ofcSiloprogramInfoScreenList(ofcTransplanInfo.getOrderCode()).size() == 0) {
                                throw new BusinessException("订单状态异常，无法变更为执行中");
                            } else {
                                saveOrderStatusOfImplemente(ofcTransplanInfo, userName);
                            }
                        }
                        ofcTraplanSourceStatus.setResourceConfirmation(userName);
                        ofcTraplanSourceStatus.setResourceConfirmationTime(new Date());

                    }
                    if (!trimAndNullAsEmpty(serviceProviderName).equals("")) {
                        ofcTraplanSourceStatus.setServiceProviderName(serviceProviderName);
                    }
                    if (!trimAndNullAsEmpty(serviceProviderContact).equals("")) {
                        ofcTraplanSourceStatus.setServiceProviderContact(serviceProviderContact);
                    }
                    if (!trimAndNullAsEmpty(serviceProviderContactPhone).equals("")) {
                        ofcTraplanSourceStatus.setServiceProviderContactPhone(serviceProviderContactPhone);
                    }
                    ofcTraplanSourceStatusService.updateByPlanCode(ofcTraplanSourceStatus);
                }


                ofcTransplanInfoToTfc(ofcTransplanInfoList, ofcPlannedDetailMap, userName, custOrderCodes);
            } catch (Exception ex) {
                throw new BusinessException("计划单状态更新异常！" + ex.getMessage());
            }
        } else {
            throw new BusinessException("缺少计划单编号");
        }
        return null;
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
     * OFC向TFC推送运输计划单(卡班除外)
     *
     * @param ofcTransplanInfoList 计划单列表
     * @param ofcPlannedDetailMap  计划单明细Map
     * @param userName             用户名
     */
    private void ofcTransplanInfoToTfc(List<OfcTransplanInfo> ofcTransplanInfoList, Map<String, List<OfcPlannedDetail>> ofcPlannedDetailMap
            , String userName, Map<String, String> custOrderCodes) {
        try {
            for (OfcTransplanInfo ofcTransplanInfo : ofcTransplanInfoList) {
                TransportDTO transportDTO = null;
                if (PubUtils.isNotNullAndBiggerSize(ofcPlannedDetailMap.get(ofcTransplanInfo.getPlanCode()), 0)) {
                    String planCode = ofcTransplanInfo.getPlanCode();
                    List<OfcPlannedDetail> ofcPlannedDetail = ofcPlannedDetailMap.get(planCode);
                    transportDTO = createOfcTransplanInfoToTfc(ofcTransplanInfo, ofcPlannedDetail, userName, custOrderCodes.get(planCode));
                }
                if (transportDTO != null) {
                    String json = JacksonUtil.toJsonWithFormat(transportDTO);
                    logger.info("###################推送TFC的最终JSON为{}", json);
                    defaultMqProducer.toSendTfcTransPlanMQ(json, ofcTransplanInfo.getPlanCode());
                }
                OfcTransplanStatus ofcTransplanStatus = new OfcTransplanStatus();
                ofcTransplanStatus.setPlanCode(ofcTransplanInfo.getPlanCode());
                ofcTransplanStatus.setPlannedSingleState(ALREADY_PUSH);
                ofcTransplanStatusService.updateByPlanCode(ofcTransplanStatus);
            }
        } catch (Exception ex) {
            throw new BusinessException("OFC推送TFC运输订单异常" + ex.getMessage(), ex);
        }
    }

    /**
     * 拼装TFC运输订单实体类
     *
     * @param ofcTransplanInfo 运输计划单基本信息
     * @param ofcPlannedDetail 计划单明细
     * @param userName         用户名
     * @param custOrderCode    客户订单编号
     * @return TransportDTO
     */
    private TransportDTO createOfcTransplanInfoToTfc(OfcTransplanInfo ofcTransplanInfo, List<OfcPlannedDetail> ofcPlannedDetail
            , String userName, String custOrderCode) {
        TransportDTO transportDTO = new TransportDTO();
        transportDTO.setTransportNo(trimAndNullAsEmpty(ofcTransplanInfo.getPlanCode()));//运输单号
        transportDTO.setCreateTime(trimAndNullAsEmpty(DateUtils.Date2String(ofcTransplanInfo.getCreationTime(), DateUtils.DateFormatType.TYPE1)));//运输单生成时间
        transportDTO.setBillType(trimAndNullAsEmpty(ofcTransplanInfo.getBusinessType()));//运输单类型
        transportDTO.setCustomerCode(trimAndNullAsEmpty(ofcTransplanInfo.getCustCode()));//客户编码(委托方代码)
        transportDTO.setCustomerName(trimAndNullAsEmpty(userName));//客户名称（委托方名称）
        transportDTO.setCustomerTel(trimAndNullAsEmpty(ofcTransplanInfo.getCustomerContactPhone()));//客户联系方式
        transportDTO.setFromTransportName(trimAndNullAsEmpty(ofcTransplanInfo.getBaseId()));//运输单产生机构
        transportDTO.setCustomerOrderCode(trimAndNullAsEmpty(custOrderCode));
        if (!PubUtils.isOEmptyOrNull(ofcTransplanInfo.getPickupTime())) {
            transportDTO.setExpectedShipmentTime(trimAndNullAsEmpty(DateUtils.Date2String(ofcTransplanInfo.getPickupTime(), DateUtils.DateFormatType.TYPE1)));//预计发货时间
        }
        if (!PubUtils.isOEmptyOrNull(ofcTransplanInfo.getExpectedArrivedTime())) {
            transportDTO.setExpectedArriveTime(trimAndNullAsEmpty(DateUtils.Date2String(ofcTransplanInfo.getExpectedArrivedTime(), DateUtils.DateFormatType.TYPE1)));//预计到达时间//$$$
        }
        if (!PubUtils.isOEmptyOrNull(ofcTransplanInfo.getWeight())) {
            transportDTO.setWeight(ofcTransplanInfo.getWeight().doubleValue());//重量
        } else {
            transportDTO.setWeight(0.0);
        }
        if (!PubUtils.isOEmptyOrNull(ofcTransplanInfo.getQuantity())) {
            transportDTO.setQty(ofcTransplanInfo.getQuantity().doubleValue());//数量
        } else {
            transportDTO.setQty(0.0);
        }
        if (!PubUtils.isOEmptyOrNull(ofcTransplanInfo.getVolume())) {
            transportDTO.setVolume(ofcTransplanInfo.getVolume().doubleValue());//体积
        } else {
            transportDTO.setVolume(0.0);
        }
        if (!PubUtils.isOEmptyOrNull(ofcTransplanInfo.getMoney())) {
            transportDTO.setMoney(ofcTransplanInfo.getMoney().doubleValue());//金额
        } else {
            transportDTO.setMoney(0.0);
        }
        transportDTO.setFromCustomerCode(trimAndNullAsEmpty(ofcTransplanInfo.getShippinCustomerCode()));//发货客户代码
        transportDTO.setFromCustomerName(trimAndNullAsEmpty(ofcTransplanInfo.getShippinCustomerName()));//发货客户名称
        transportDTO.setFromCustomerAddress(trimAndNullAsEmpty(ofcTransplanInfo.getDepartureProvince()
                + ofcTransplanInfo.getDepartureCity()
                + ofcTransplanInfo.getDepartureDistrict()
                + ofcTransplanInfo.getDepartureTowns() + ofcTransplanInfo.getShippingAddress()).replace("null", ""));//发货客户地址
        transportDTO.setFromCustomer(trimAndNullAsEmpty(ofcTransplanInfo.getShippingCustomerContact()));//发货客户联系人
        transportDTO.setFromCustomerTle(trimAndNullAsEmpty(ofcTransplanInfo.getCustomerContactPhone()));//发货客户联系电话
        transportDTO.setFromProvince(trimAndNullAsEmpty(ofcTransplanInfo.getDepartureProvince()).replace("null", ""));//出发省份
        transportDTO.setFromCity(trimAndNullAsEmpty(ofcTransplanInfo.getDepartureCity()).replace("null", ""));//出发城市
        transportDTO.setFromDistrict(trimAndNullAsEmpty(ofcTransplanInfo.getDepartureDistrict()).replace("null", ""));//出发区县
        transportDTO.setFromTown(trimAndNullAsEmpty(ofcTransplanInfo.getDepartureTowns()).replace("null", ""));//出发街道
//                transportDTO.setFromCustomerAddress(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDepartureTowns()+ofcTransplanInfo.getDe));
        transportDTO.setToCustomerCode(trimAndNullAsEmpty(ofcTransplanInfo.getReceivingCustomerCode()));//收货客户代码
        transportDTO.setToCustomerName(trimAndNullAsEmpty(ofcTransplanInfo.getReceivingCustomerName()));//收货客户名称
        transportDTO.setToCustomerAddress(trimAndNullAsEmpty(ofcTransplanInfo.getDestinationProvince()
                + ofcTransplanInfo.getDestinationCity()
                + ofcTransplanInfo.getDestinationDistrict()
                + ofcTransplanInfo.getDestinationTown() + ofcTransplanInfo.getReceivingCustomerAddress()).replace("null", ""));//收货客户地址
        transportDTO.setToCustomer(trimAndNullAsEmpty(ofcTransplanInfo.getReceivingCustomerContact()));//收货客户联系人
        transportDTO.setToCustomerTle(trimAndNullAsEmpty(ofcTransplanInfo.getReceivingCustomerContactPhone()));//收货客户联系电话
        transportDTO.setToProvince(trimAndNullAsEmpty(ofcTransplanInfo.getDestinationProvince()).replace("null", ""));//目的省份
        transportDTO.setToCity(trimAndNullAsEmpty(ofcTransplanInfo.getDestinationCity()).replace("null", ""));//目的城市
        transportDTO.setToDistrict(trimAndNullAsEmpty(ofcTransplanInfo.getDestinationDistrict()).replace("null", ""));//目的区县
        transportDTO.setToTown(trimAndNullAsEmpty(ofcTransplanInfo.getDestinationTown()).replace("null", ""));//目的区县
        transportDTO.setToLon(trimAndNullAsEmpty(ofcTransplanInfo.getReceivingAddressLongitude()));//收货地址经度
        transportDTO.setToLat(trimAndNullAsEmpty(ofcTransplanInfo.getReceivingAddressLatitude()));//收货地址纬度
        transportDTO.setNotes(trimAndNullAsEmpty(ofcTransplanInfo.getNotes()));//备注
        transportDTO.setMarketOrg(trimAndNullAsEmpty(ofcTransplanInfo.getProductGroup()));//产品组
        transportDTO.setMarketDep(trimAndNullAsEmpty(ofcTransplanInfo.getSaleDepartment()));//销售部门
        transportDTO.setMarketTeam(trimAndNullAsEmpty(ofcTransplanInfo.getSaleGroup()));//销售组
        transportDTO.setMarketDepDes(trimAndNullAsEmpty(ofcTransplanInfo.getSaleDepartmentDesc()));//销售部门描述
        transportDTO.setMarketTeamDes(trimAndNullAsEmpty(ofcTransplanInfo.getSaleGroupDesc()));//销售组描述
        transportDTO.setTransportSource(trimAndNullAsEmpty(ofcTransplanInfo.getSingleSourceOfTransport()));//运输单来源
        transportDTO.setBaseName(trimAndNullAsEmpty(ofcTransplanInfo.getBaseName()));
        //OfcPlannedDetail ofcPlannedDetail = new OfcPlannedDetail();
        //ofcPlannedDetail.setPlanCode(ofcTransplanInfo.getPlanCode());
        if (ofcPlannedDetail != null) {
            for (OfcPlannedDetail detail : ofcPlannedDetail) {
                TransportDetailDTO transportDetailDTO = new TransportDetailDTO();
                //transportDetailDTO.setTransportNo(detail.getPlanCode());
                transportDetailDTO.setItemCode(detail.getGoodsCode());
                transportDetailDTO.setItemName(detail.getGoodsName());
                if (null == detail.getQuantity()) {
                    transportDetailDTO.setQty(0.0);
                } else {
                    transportDetailDTO.setQty(detail.getQuantity().doubleValue());
                }
                if (null == detail.getWeight()) {
                    transportDetailDTO.setWeight(0.0);
                } else {
                    transportDetailDTO.setWeight(detail.getWeight().doubleValue());
                }
                if (null == detail.getCubage()) {
                    transportDetailDTO.setVolume(0.0);
                } else {
                    transportDetailDTO.setVolume(detail.getCubage().doubleValue());
                }
                if (null == detail.getUnitPrice()) {
                    transportDetailDTO.setPrice(0.0);
                } else {
                    transportDetailDTO.setPrice(detail.getUnitPrice().doubleValue());
                }
                transportDetailDTO.setMoney(0.0);
                transportDetailDTO.setUom(detail.getUnit());
                if (detail.getTotalBox() == null) {
                    detail.setTotalBox(0);
                }
                transportDetailDTO.setContainerQty(detail.getTotalBox().toString());
                transportDetailDTO.setStandard(trimAndNullAsEmpty(detail.getGoodsSpec()));
                transportDTO.getProductDetail().add(transportDetailDTO);
            }
        }
        return transportDTO;
    }

    /**
     * 运输开单自动审核
     *
     * @param ofcFundamentalInformation 订单基本信息
     * @param goodsDetailsList          货品明细
     * @param ofcDistributionBasicInfo  运输基本信息
     * @param ofcFinanceInformation     费用明细
     * @param orderStatus               订单状态
     * @param reviewTag                 审核标记
     * @param authResDtoByToken         token
     * @return String
     */
    @Override
    public String orderAuditByTrans(OfcFundamentalInformation ofcFundamentalInformation, List<OfcGoodsDetailsInfo> goodsDetailsList,
                                    OfcDistributionBasicInfo ofcDistributionBasicInfo, OfcFinanceInformation ofcFinanceInformation,
                                    String orderStatus, String reviewTag, AuthResDto authResDtoByToken) {
        OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
        ofcOrderStatus.setOrderCode(ofcDistributionBasicInfo.getOrderCode());
        ofcOrderStatus.setOrderStatus(orderStatus);
        logger.info(ofcOrderStatus.toString());
        String status = ofcOrderStatus.getOrderStatus();
        if ((!status.equals(IMPLEMENTATION_IN)) && (!status.equals(HASBEEN_COMPLETED)) && (!status.equals(HASBEEN_CANCELED))) {
            if (ofcOrderStatus.getOrderStatus().equals(PENDING_AUDIT) && reviewTag.equals(REVIEW)) {
                String userName = authResDtoByToken.getUserName();
                ofcOrderStatus.setOrderStatus(ALREADY_EXAMINE);
                ofcOrderStatus.setStatusDesc("已审核");
                ofcOrderStatus.setNotes(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1) + " " + "订单审核完成");
                ofcOrderStatus.setOperator(userName);
                ofcOrderStatus.setLastedOperTime(new Date());
                ofcOrderStatusService.save(ofcOrderStatus);

                ofcFundamentalInformation.setOperator(authResDtoByToken.getUserId());
                ofcFundamentalInformation.setOperatorName(userName);
                ofcFundamentalInformation.setOperTime(new Date());
                if (trimAndNullAsEmpty(ofcFundamentalInformation.getOrderType()).equals(TRANSPORT_ORDER)) {
                    //运输订单
                    OfcTransplanInfo ofcTransplanInfo = new OfcTransplanInfo();
                    if (!ofcFundamentalInformation.getBusinessType().equals(WITH_THE_KABAN)) {//非卡班类型直接创建运输计划单
                        ofcTransplanInfo.setProgramSerialNumber("1");
                        transPlanCreate(ofcTransplanInfo, ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFundamentalInformation.getCustName(), ofcFinanceInformation);
                    } else {
                        kabanAutoReview(ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFinanceInformation, ofcTransplanInfo);
                    }
                } else {
                    logger.info("订单类型为{}", ofcFundamentalInformation.getOrderType());
                    throw new BusinessException("订单类型有误");
                }
                logger.info("计划单创建成功");
            } else {
                throw new BusinessException("缺少标志位");
            }

            return String.valueOf(Wrapper.SUCCESS_CODE);
        } else {
            throw new BusinessException("订单类型既非“已审核”，也非“未审核”，请检查！");
        }
    }

    /**
     * 卡班类型订单自动审核
     *
     * @param ofcFundamentalInformation 订单基本信息
     * @param goodsDetailsList          货品明细
     * @param ofcDistributionBasicInfo  运输基本信息
     * @param ofcFinanceInformation     费用明细
     * @param ofcTransplanInfo          运输计划单基本信息
     */
    private void kabanAutoReview(OfcFundamentalInformation ofcFundamentalInformation, List<OfcGoodsDetailsInfo> goodsDetailsList
            , OfcDistributionBasicInfo ofcDistributionBasicInfo, OfcFinanceInformation ofcFinanceInformation, OfcTransplanInfo ofcTransplanInfo) {
        RmcServiceCoverageForOrderVo rmcServiceCoverageForOrderVo = new RmcServiceCoverageForOrderVo();
        try {
            BeanUtils.copyProperties(ofcTransplanInfo, ofcFundamentalInformation);
            BeanUtils.copyProperties(ofcTransplanInfo, ofcDistributionBasicInfo);
        } catch (Exception ex) {
            throw new BusinessException("复制基本档案和运输档案异常", ex);
        }
        if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsignorCode()).equals("")) {
            ofcTransplanInfo.setShippinCustomerCode(ofcDistributionBasicInfo.getConsignorCode());
        }
        if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsignorName()).equals("")) {
            ofcTransplanInfo.setShippinCustomerName(ofcDistributionBasicInfo.getConsignorName());
        }
        if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsignorContactName()).equals("")) {
            ofcTransplanInfo.setShippingCustomerContact(ofcDistributionBasicInfo.getConsignorContactName());
        }
        if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsignorContactPhone()).equals("")) {
            ofcTransplanInfo.setCustomerContactPhone(ofcDistributionBasicInfo.getConsignorContactPhone());
        }
        if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsigneeCode()).equals("")) {
            ofcTransplanInfo.setReceivingCustomerCode(ofcDistributionBasicInfo.getConsigneeCode());
        }
        if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsigneeName()).equals("")) {
            ofcTransplanInfo.setReceivingCustomerName(ofcDistributionBasicInfo.getConsigneeName());
        }
        if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsigneeContactName()).equals("")) {
            ofcTransplanInfo.setReceivingCustomerContact(ofcDistributionBasicInfo.getConsigneeContactName());
        }
        if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsigneeContactPhone()).equals("")) {
            ofcTransplanInfo.setReceivingCustomerContactPhone(ofcDistributionBasicInfo.getConsigneeContactPhone());
        }
        if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getDeparturePlace()).equals("")) {
            ofcTransplanInfo.setShippingAddress(ofcDistributionBasicInfo.getDeparturePlace());
        }
        if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getDestination()).equals("")) {
            ofcTransplanInfo.setReceivingCustomerAddress(ofcDistributionBasicInfo.getDestination());
        }
        if (!trimAndNullAsEmpty(ofcDistributionBasicInfo.getDestinationTowns()).equals("")) {
            ofcTransplanInfo.setDestinationTown(ofcDistributionBasicInfo.getDestinationTowns());
        }
        RmcServiceCoverageForOrderVo rmcPickup;
        if (trimAndNullAsEmpty(ofcFinanceInformation.getPickUpGoods()).equals(YES.toString())
                && trimAndNullAsEmpty(ofcFinanceInformation.getTwoDistribution()).equals(NO.toString())) {
            //需要上门提货
            rmcPickup = kabanAuditWithPickup(ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFinanceInformation, ofcTransplanInfo, rmcServiceCoverageForOrderVo);
        } else if (trimAndNullAsEmpty(ofcFinanceInformation.getPickUpGoods()).equals(NO.toString())
                && trimAndNullAsEmpty(ofcFinanceInformation.getTwoDistribution()).equals(YES.toString())) {
            //需要二次配送
            rmcPickup = kabanAuditWithTwoDistri(ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFinanceInformation, ofcTransplanInfo, rmcServiceCoverageForOrderVo);
        } else if (trimAndNullAsEmpty(ofcFinanceInformation.getPickUpGoods()).equals(YES.toString())
                && trimAndNullAsEmpty(ofcFinanceInformation.getTwoDistribution()).equals(YES.toString())) {
            //需要上门提货和二次配送
            rmcPickup = kabanAuditWithBoth(ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFinanceInformation, ofcTransplanInfo, rmcServiceCoverageForOrderVo);
        } else if (trimAndNullAsEmpty(ofcFinanceInformation.getPickUpGoods()).equals(NO.toString())
                && trimAndNullAsEmpty(ofcFinanceInformation.getTwoDistribution()).equals(NO.toString())) {
            //不需要上门提货和二次配送
            rmcPickup = kabanAuditWithoutBoth(ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFinanceInformation, ofcTransplanInfo, rmcServiceCoverageForOrderVo);
        } else {
            throw new BusinessException("订单信息是否上门提货或是否二次配送传值有误，请检查");
        }
        //订单来源为钉钉录单时
        if (rmcPickup != null) {
            if (!isSEmptyOrNull(ofcFundamentalInformation.getOrderSource())) {
                if (DING_DING.equals(ofcFundamentalInformation.getOrderSource())) {
                    //更新订单的大区和基地
                    updateOrderAreaAndBase(rmcPickup, ofcFundamentalInformation);
                }
            }
        }
    }

    /**
     * 卡班订单自动审核: 没有上门取货和二次配送
     *
     * @param ofcFundamentalInformation    订单基本信息
     * @param goodsDetailsList             货品明细
     * @param ofcDistributionBasicInfo     运输基本信息
     * @param ofcFinanceInformation        费用明细
     * @param ofcTransplanInfo             运输计划单基本
     * @param rmcServiceCoverageForOrderVo 区域覆盖vo
     * @return RmcServiceCoverageForOrderVo
     */
    private RmcServiceCoverageForOrderVo kabanAuditWithoutBoth(OfcFundamentalInformation ofcFundamentalInformation
            , List<OfcGoodsDetailsInfo> goodsDetailsList, OfcDistributionBasicInfo ofcDistributionBasicInfo
            , OfcFinanceInformation ofcFinanceInformation, OfcTransplanInfo ofcTransplanInfo, RmcServiceCoverageForOrderVo rmcServiceCoverageForOrderVo) {
        RmcServiceCoverageForOrderVo rmcPickup;//使用发货地省市区街道 获取覆范围的提货类型的【发货TC库编码】,传入计划单信息的【基地ID】字段, 计划单序号为1
        rmcServiceCoverageForOrderVo = copyDestinationPlace(ofcDistributionBasicInfo.getDeparturePlaceCode(), rmcServiceCoverageForOrderVo);
        rmcPickup = rmcServiceCoverageAPI(rmcServiceCoverageForOrderVo, "Pickup");
        if (rmcPickup != null
                && !trimAndNullAsEmpty(rmcPickup.getWarehouseCode()).equals("")
                && !trimAndNullAsEmpty(rmcPickup.getWarehouseName()).equals("")) {
            ofcTransplanInfo.setBaseId(rmcPickup.getWarehouseCode());
            ofcTransplanInfo.setBaseName(rmcPickup.getWarehouseName());
        }
        ofcTransplanInfo.setProgramSerialNumber("1");
        ofcTransplanInfo.setBusinessType(BusinessTypeEnum.CABANNES.getCode());//卡班计划单

        transPlanCreateKaBan(ofcTransplanInfo, ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFinanceInformation, ofcFundamentalInformation.getCustName());
        return rmcPickup;
    }

    /**
     * 卡班订单自动审核: 有上门取货和二次配送
     *
     * @param ofcFundamentalInformation    订单基本信息
     * @param goodsDetailsList             货品明细
     * @param ofcDistributionBasicInfo     运输基本信息
     * @param ofcFinanceInformation        费用明细
     * @param ofcTransplanInfo             运输计划单基本
     * @param rmcServiceCoverageForOrderVo 区域覆盖vo
     * @return RmcServiceCoverageForOrderVo
     */
    private RmcServiceCoverageForOrderVo kabanAuditWithBoth(OfcFundamentalInformation ofcFundamentalInformation
            , List<OfcGoodsDetailsInfo> goodsDetailsList, OfcDistributionBasicInfo ofcDistributionBasicInfo
            , OfcFinanceInformation ofcFinanceInformation, OfcTransplanInfo ofcTransplanInfo, RmcServiceCoverageForOrderVo rmcServiceCoverageForOrderVo) {
        RmcServiceCoverageForOrderVo rmcPickup;//调用资源中心获取TC仓覆盖接口，传值【类型】、【地址编码】分别对应为【提货】、【发货地地址编码】。
        rmcServiceCoverageForOrderVo = copyDestinationPlace(ofcDistributionBasicInfo.getDeparturePlaceCode(), rmcServiceCoverageForOrderVo);
        rmcPickup = rmcServiceCoverageAPI(rmcServiceCoverageForOrderVo, "Pickup");
        //调用资源中心获取TC仓覆盖接口，传值【类型】、【地址编码】分别对应为【配送】、【收货地地址编码】。获取收货TC仓信息
        rmcServiceCoverageForOrderVo = copyDestinationPlace(ofcDistributionBasicInfo.getDestinationCode(), rmcServiceCoverageForOrderVo);
        RmcServiceCoverageForOrderVo rmcRecipient = rmcServiceCoverageAPI(rmcServiceCoverageForOrderVo, "TwoDistribution");
        //收发货仓都有返回值
        if (rmcPickup != null
                && !trimAndNullAsEmpty(rmcPickup.getWarehouseCode()).equals("")
                && rmcRecipient != null
                && !trimAndNullAsEmpty(rmcRecipient.getWarehouseCode()).equals("")) {
            //获取发货TC仓，则获取仓库编码、仓库名称、仓库省、仓库市、仓库区、仓库乡镇街道、仓库地址编码、仓库详细地址、仓库联系人、仓库联系电话、调度单位
            RmcWarehouseRespDto rmcWarehouseor = getWareHouseByCodeToPlan(rmcPickup.getWarehouseCode());//发货仓
            RmcWarehouseRespDto rmcWarehouseee = getWareHouseByCodeToPlan(rmcRecipient.getWarehouseCode());//收货仓
            if (rmcWarehouseor != null && rmcWarehouseee != null) {
                OfcTransplanInfo ofcTransplanInfoReflect1 = new OfcTransplanInfo();
                OfcTransplanInfo ofcTransplanInfoReflect2 = new OfcTransplanInfo();
                try {
                    BeanUtils.copyProperties(ofcTransplanInfoReflect1, ofcTransplanInfo);
                    BeanUtils.copyProperties(ofcTransplanInfoReflect2, ofcTransplanInfo);
                } catch (Exception ex) {
                    logger.error("复制计划单信息异常,{}", ex);
                    throw new BusinessException("复制计划单信息异常", ex);
                }
                //创建第一个城配计划单
                ofcTransplanInfo.setBusinessType(BusinessTypeEnum.WITH_CITY.getCode());
                //收货方信息更改为已获取的发货TC 仓信息，收货方编码与名称对应仓库编码及仓库名称，其他信息同类对应。
                copyDestinationPlace(ofcTransplanInfo, rmcWarehouseor);
                ofcTransplanInfo.setBaseId(rmcPickup.getWarehouseCode());
                ofcTransplanInfo.setBaseName(rmcPickup.getWarehouseName());
                ofcTransplanInfo.setProgramSerialNumber("1");
                transPlanCreateKaBan(ofcTransplanInfo, ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFinanceInformation, ofcFundamentalInformation.getCustName());
                //创建第二个卡班计划单
                ofcTransplanInfoReflect1.setBusinessType(BusinessTypeEnum.CABANNES.getCode());
                ofcTransplanInfoReflect1.setBaseId(rmcPickup.getWarehouseCode());
                ofcTransplanInfoReflect1.setBaseName(rmcPickup.getWarehouseName());
                ofcTransplanInfoReflect1.setProgramSerialNumber("2");
                transPlanCreateKaBan(ofcTransplanInfoReflect1, ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFinanceInformation, ofcFundamentalInformation.getCustName());
                //创建第三个城配计划单
                ofcTransplanInfoReflect2.setBusinessType(BusinessTypeEnum.WITH_CITY.getCode());
                //发货方信息更改为已获取的收货TC 仓信息，发货方编码与名称对应仓库编码及仓库名称，其他信息同类对应。
                copyDarturePlace(ofcTransplanInfoReflect2, rmcWarehouseee);
                ofcTransplanInfoReflect2.setBaseId(rmcRecipient.getWarehouseCode());
                ofcTransplanInfoReflect2.setBaseName(rmcRecipient.getWarehouseName());
                ofcTransplanInfoReflect2.setProgramSerialNumber("3");
                transPlanCreateKaBan(ofcTransplanInfoReflect2, ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFinanceInformation, ofcFundamentalInformation.getCustName());
            } else {
                throw new BusinessException("获取仓库相关信息失败");
            }

        } else if (rmcPickup != null
                && !trimAndNullAsEmpty(rmcPickup.getWarehouseCode()).equals("")
                && (rmcRecipient == null
                || trimAndNullAsEmpty(rmcRecipient.getWarehouseCode()).equals(""))) {//发货仓有信息，收货仓无
            //获取发货TC仓，则获取仓库编码、仓库名称、仓库省、仓库市、仓库区、仓库乡镇街道、仓库地址编码、仓库详细地址、仓库联系人、仓库联系电话、调度单位
            RmcWarehouseRespDto rmcWarehouse = getWareHouseByCodeToPlan(rmcPickup.getWarehouseCode());
            if (rmcWarehouse != null) {
                OfcTransplanInfo ofcTransplanInfoReflect = new OfcTransplanInfo();
                try {
                    BeanUtils.copyProperties(ofcTransplanInfoReflect, ofcTransplanInfo);
                } catch (Exception ex) {
                    logger.error("复制基本档案和运输档案异常,{}", ex);
                    throw new BusinessException("复制基本档案和运输档案异常", ex);
                }
                //创建第一个城配计划单
                ofcTransplanInfo.setBusinessType(BusinessTypeEnum.WITH_CITY.getCode());
                //收货方信息更改为已获取的发货TC 仓信息，收货方编码与名称对应仓库编码及仓库名称，其他信息同类对应。
                copyDestinationPlace(ofcTransplanInfo, rmcWarehouse);
                ofcTransplanInfo.setBaseId(rmcPickup.getWarehouseCode());
                ofcTransplanInfo.setBaseName(rmcPickup.getWarehouseName());
                ofcTransplanInfo.setProgramSerialNumber("1");
                transPlanCreateKaBan(ofcTransplanInfo, ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFinanceInformation, ofcFundamentalInformation.getCustName());
                //创建第二个卡班计划单
                ofcTransplanInfoReflect.setBusinessType(BusinessTypeEnum.CABANNES.getCode());
                ofcTransplanInfoReflect.setBaseId(rmcPickup.getWarehouseCode());
                ofcTransplanInfoReflect.setBaseName(rmcPickup.getWarehouseName());
                ofcTransplanInfoReflect.setProgramSerialNumber("2");
                transPlanCreateKaBan(ofcTransplanInfoReflect, ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFinanceInformation, ofcFundamentalInformation.getCustName());
            } else {
                throw new BusinessException("获取仓库相关信息失败");
            }

        } else if (rmcRecipient != null
                && !trimAndNullAsEmpty(rmcRecipient.getWarehouseCode()).equals("")
                && (rmcPickup == null
                || trimAndNullAsEmpty(rmcPickup.getWarehouseCode()).equals(""))) {//收货仓有信息，发货仓无
            //获取收货TC仓信息，则获取仓库编码、仓库名称、仓库省、仓库市、仓库区、仓库乡镇街道、仓库地址编码、仓库详细地址、仓库联系人、仓库联系电话、调度单位
            RmcWarehouseRespDto rmcWarehouse = getWareHouseByCodeToPlan(rmcRecipient.getWarehouseCode());
            if (rmcWarehouse != null) {
                OfcTransplanInfo ofcTransplanInfoReflect = new OfcTransplanInfo();
                try {
                    BeanUtils.copyProperties(ofcTransplanInfoReflect, ofcTransplanInfo);
                } catch (Exception ex) {
                    logger.error("复制基本档案和运输档案异常,{}", ex);
                    throw new BusinessException("复制基本档案和运输档案异常", ex);
                }
                //创建第一个卡班单
                ofcTransplanInfo.setBusinessType(BusinessTypeEnum.CABANNES.getCode());
                ofcTransplanInfo.setProgramSerialNumber("1");
                transPlanCreateKaBan(ofcTransplanInfo, ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFinanceInformation, ofcFundamentalInformation.getCustName());
                //创建第二个城配单
                ofcTransplanInfoReflect.setBusinessType(BusinessTypeEnum.WITH_CITY.getCode());
                //发货方信息更改为已获取的收货TC 仓信息，发货方编码与名称对应仓库编码及仓库名称，其他信息同类对应。
                copyDarturePlace(ofcTransplanInfoReflect, rmcWarehouse);
                ofcTransplanInfoReflect.setBaseId(rmcRecipient.getWarehouseCode());
                ofcTransplanInfoReflect.setBaseName(rmcRecipient.getWarehouseName());
                ofcTransplanInfoReflect.setProgramSerialNumber("2");
                transPlanCreateKaBan(ofcTransplanInfoReflect, ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFinanceInformation, ofcFundamentalInformation.getCustName());
            } else {
                throw new BusinessException("获取仓库相关信息失败");
            }

        } else if ((rmcPickup == null
                || trimAndNullAsEmpty(rmcPickup.getWarehouseCode()).equals(""))
                && (rmcRecipient == null
                || trimAndNullAsEmpty(rmcRecipient.getWarehouseCode()).equals(""))) {//收发货仓都没有信息
            //创建一个运输计划单
            ofcTransplanInfo.setBusinessType(BusinessTypeEnum.CABANNES.getCode());
            ofcTransplanInfo.setProgramSerialNumber("1");
            transPlanCreateKaBan(ofcTransplanInfo, ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFinanceInformation, ofcFundamentalInformation.getCustName());
        }
        return rmcPickup;
    }

    /**
     * 卡班订单自动审核: 只有二次配送
     *
     * @param ofcFundamentalInformation    订单基本信息
     * @param goodsDetailsList             货品明细
     * @param ofcDistributionBasicInfo     运输基本信息
     * @param ofcFinanceInformation        费用明细
     * @param ofcTransplanInfo             运输计划单基本
     * @param rmcServiceCoverageForOrderVo 区域覆盖vo
     * @return RmcServiceCoverageForOrderVo
     */
    private RmcServiceCoverageForOrderVo kabanAuditWithTwoDistri(OfcFundamentalInformation ofcFundamentalInformation
            , List<OfcGoodsDetailsInfo> goodsDetailsList, OfcDistributionBasicInfo ofcDistributionBasicInfo
            , OfcFinanceInformation ofcFinanceInformation, OfcTransplanInfo ofcTransplanInfo, RmcServiceCoverageForOrderVo rmcServiceCoverageForOrderVo) {
        RmcServiceCoverageForOrderVo rmcPickup;//调用资源中心获取TC仓覆盖接口，传值【类型】、【地址编码】分别对应为【提货】、【发货地地址编码】。
        rmcServiceCoverageForOrderVo = copyDestinationPlace(ofcDistributionBasicInfo.getDeparturePlaceCode(), rmcServiceCoverageForOrderVo);
        rmcPickup = rmcServiceCoverageAPI(rmcServiceCoverageForOrderVo, "Pickup");
        //调用资源中心获取TC仓覆盖接口，传值【类型】、【地址编码】分别对应为【配送】、【收货地地址编码】。获取收货TC仓信息
        rmcServiceCoverageForOrderVo = copyDestinationPlace(ofcDistributionBasicInfo.getDestinationCode(), rmcServiceCoverageForOrderVo);
        RmcServiceCoverageForOrderVo rmcRecipient = rmcServiceCoverageAPI(rmcServiceCoverageForOrderVo, "TwoDistribution");
        if (rmcRecipient != null
                && !trimAndNullAsEmpty(rmcRecipient.getWarehouseCode()).equals("")) {//有返回值
            //获取仓库编码、仓库名称、仓库省、仓库市、仓库区、仓库乡镇街道、仓库地址编码、仓库详细地址、仓库联系人、仓库联系电话、调度单位
            RmcWarehouseRespDto rmcWarehouse = getWareHouseByCodeToPlan(rmcRecipient.getWarehouseCode());
            if (rmcWarehouse != null) {
                OfcTransplanInfo ofcTransplanInfoReflect = new OfcTransplanInfo();
                try {
                    BeanUtils.copyProperties(ofcTransplanInfoReflect, ofcTransplanInfo);
                } catch (Exception ex) {
                    logger.error("复制计划单信息异常{}", ex);
                    throw new BusinessException("复制计划单信息异常", ex);
                }
                //创建第一个卡班单
                ofcTransplanInfo.setBusinessType(BusinessTypeEnum.CABANNES.getCode());
                if (!trimAndNullAsEmpty(rmcPickup.getWarehouseCode()).equals("")
                        && !trimAndNullAsEmpty(rmcPickup.getWarehouseName()).equals("")) {
                    ofcTransplanInfo.setBaseId(rmcPickup.getWarehouseCode());
                    ofcTransplanInfo.setBaseName(rmcPickup.getWarehouseName());
                }
                ofcTransplanInfo.setProgramSerialNumber("1");
                transPlanCreateKaBan(ofcTransplanInfo, ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFinanceInformation, ofcFundamentalInformation.getCustName());
                //创建第二个城配单
                ofcTransplanInfoReflect.setBusinessType(BusinessTypeEnum.WITH_CITY.getCode());
                //发货方信息更改为已获取的收货TC 仓信息，发货方编码与名称对应仓库编码及仓库名称，其他信息同类对应。
                copyDarturePlace(ofcTransplanInfoReflect, rmcWarehouse);
                ofcTransplanInfoReflect.setBaseId(rmcRecipient.getWarehouseCode());
                ofcTransplanInfoReflect.setBaseName(rmcRecipient.getWarehouseName());
                ofcTransplanInfoReflect.setProgramSerialNumber("2");
                transPlanCreateKaBan(ofcTransplanInfoReflect, ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFinanceInformation, ofcFundamentalInformation.getCustName());
            } else {
                throw new BusinessException("获取仓库相关信息失败");
            }
        } else {//无返回值
            //创建一个运输计划单
            ofcTransplanInfo.setBusinessType(BusinessTypeEnum.CABANNES.getCode());
            ofcTransplanInfo.setProgramSerialNumber("1");
            transPlanCreateKaBan(ofcTransplanInfo, ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFinanceInformation, ofcFundamentalInformation.getCustName());
        }
        return rmcPickup;
    }

    /**
     * 卡班订单自动审核: 只有上门取货
     *
     * @param ofcFundamentalInformation    订单基本信息
     * @param goodsDetailsList             货品明细
     * @param ofcDistributionBasicInfo     运输基本信息
     * @param ofcFinanceInformation        费用明细
     * @param ofcTransplanInfo             运输计划单基本
     * @param rmcServiceCoverageForOrderVo 区域覆盖vo
     * @return RmcServiceCoverageForOrderVo
     */
    private RmcServiceCoverageForOrderVo kabanAuditWithPickup(OfcFundamentalInformation ofcFundamentalInformation
            , List<OfcGoodsDetailsInfo> goodsDetailsList, OfcDistributionBasicInfo ofcDistributionBasicInfo
            , OfcFinanceInformation ofcFinanceInformation, OfcTransplanInfo ofcTransplanInfo, RmcServiceCoverageForOrderVo rmcServiceCoverageForOrderVo) {
        //调用资源中心获取TC仓覆盖接口，传值【类型】、【地址编码】分别对应为【提货】、【发货地地址编码】。
        RmcServiceCoverageForOrderVo rmcPickup;
        rmcServiceCoverageForOrderVo = copyDestinationPlace(ofcDistributionBasicInfo.getDeparturePlaceCode(), rmcServiceCoverageForOrderVo);
        rmcPickup = rmcServiceCoverageAPI(rmcServiceCoverageForOrderVo, "Pickup");
        if (rmcPickup != null
                && !trimAndNullAsEmpty(rmcPickup.getWarehouseCode()).equals("")) {//有返回值
            //获取仓库编码、仓库名称、仓库省、仓库市、仓库区、仓库乡镇街道、仓库地址编码、仓库详细地址、仓库联系人、仓库联系电话、调度单位
            RmcWarehouseRespDto rmcWarehouse = getWareHouseByCodeToPlan(rmcPickup.getWarehouseCode());
            if (rmcWarehouse != null) {
                //创建第一个城配计划单
                OfcTransplanInfo ofcTransplanInfoReflect = new OfcTransplanInfo();
                try {
                    BeanUtils.copyProperties(ofcTransplanInfoReflect, ofcTransplanInfo);
                } catch (Exception ex) {
                    throw new BusinessException("复制基本档案和运输档案异常", ex);
                }
                ofcTransplanInfo.setBusinessType(BusinessTypeEnum.WITH_CITY.getCode());
                //收货方信息更改为已获取的发货TC 仓信息，收货方编码与名称对应仓库编码及仓库名称，其他信息同类对应。
                copyDestinationPlace(ofcTransplanInfo, rmcWarehouse);
                ofcTransplanInfo.setBaseId(rmcPickup.getWarehouseCode());
                ofcTransplanInfo.setBaseName(rmcPickup.getWarehouseName());
                ofcTransplanInfo.setProgramSerialNumber("1");
                transPlanCreateKaBan(ofcTransplanInfo, ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFinanceInformation, ofcFundamentalInformation.getCustName());
                //创建第二个卡班计划单
                ofcTransplanInfoReflect.setBusinessType(BusinessTypeEnum.CABANNES.getCode());
                ofcTransplanInfoReflect.setBaseId(rmcPickup.getWarehouseCode());
                ofcTransplanInfoReflect.setBaseName(rmcPickup.getWarehouseName());
                ofcTransplanInfoReflect.setProgramSerialNumber("2");
                transPlanCreateKaBan(ofcTransplanInfoReflect, ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFinanceInformation, ofcFundamentalInformation.getCustName());
            } else {
                throw new BusinessException("获取仓库相关信息失败");
            }
        } else {//无返回值
            //创建一个运输计划单
            ofcTransplanInfo.setBusinessType(BusinessTypeEnum.CABANNES.getCode());
            ofcTransplanInfo.setProgramSerialNumber("1");
            transPlanCreateKaBan(ofcTransplanInfo, ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFinanceInformation, ofcFundamentalInformation.getCustName());
        }
        return rmcPickup;
    }

    /**
     * 城配开单自动审核
     *
     * @param ofcFundamentalInformation 订单基本信息
     * @param goodsDetailsList          货品明细
     * @param ofcDistributionBasicInfo  运输基本信息
     * @param ofcWarehouseInformation   仓储基本信息
     * @param orderStatus               订单状态
     * @param reviewTag                 审核标记
     * @param authResDtoByToken         token
     * @return Wrapper
     */
    @Override
    public Wrapper<?> orderAutoAuditFromOperation(OfcFundamentalInformation ofcFundamentalInformation,
                                                  List<OfcGoodsDetailsInfo> goodsDetailsList,
                                                  OfcDistributionBasicInfo ofcDistributionBasicInfo,
                                                  OfcWarehouseInformation ofcWarehouseInformation,
                                                  OfcFinanceInformation ofcFinanceInformation,
                                                  String orderStatus, String reviewTag, AuthResDto authResDtoByToken) {
        OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
        ofcOrderStatus.setOrderCode(ofcFundamentalInformation.getOrderCode());
        ofcOrderStatus.setOrderStatus(orderStatus);
        logger.info(ofcOrderStatus.toString());
        if ((!ofcOrderStatus.getOrderStatus().equals(IMPLEMENTATION_IN))
                && (!ofcOrderStatus.getOrderStatus().equals(HASBEEN_COMPLETED))
                && (!ofcOrderStatus.getOrderStatus().equals(HASBEEN_CANCELED))) {
            if (ofcOrderStatus.getOrderStatus().equals(PENDING_AUDIT) && reviewTag.equals(REVIEW)) {
                String userName = authResDtoByToken.getUserName();
                ofcOrderStatus.setOrderStatus(ALREADY_EXAMINE);
                ofcOrderStatus.setStatusDesc("已审核");
                ofcOrderStatus.setNotes(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1) + " " + "订单审核完成");
                ofcOrderStatus.setOperator(userName);
                ofcOrderStatus.setLastedOperTime(new Date());
                ofcOrderStatusService.save(ofcOrderStatus);

                ofcFundamentalInformation.setOperator(authResDtoByToken.getUserId());
                ofcFundamentalInformation.setOperatorName(userName);
                ofcFundamentalInformation.setOperTime(new Date());

                String orderType = ofcFundamentalInformation.getOrderType();
                String businessType = ofcFundamentalInformation.getBusinessType();
                if (trimAndNullAsEmpty(orderType).equals(TRANSPORT_ORDER)) {  // 运输订单
                    //运输订单
                    OfcTransplanInfo ofcTransplanInfo = new OfcTransplanInfo();
                    ofcTransplanInfo.setProgramSerialNumber("1");
                    if (!trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(WITH_THE_KABAN)) {
                        transPlanCreate(ofcTransplanInfo, ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFundamentalInformation.getCustName(), ofcFinanceInformation);
                    } else {
                        transPlanCreateKaBan(ofcTransplanInfo, ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFinanceInformation, userName);
                    }
                } else if (trimAndNullAsEmpty(orderType).equals(WAREHOUSE_DIST_ORDER)
                        && trimAndNullAsEmpty(businessType).equals(SALES_OUT_OF_THE_LIBRARY)
                        && Objects.equals(ofcWarehouseInformation.getProvideTransport(), WEARHOUSE_WITH_TRANS)) {            // 仓配订单
                    //000创建仓储计划单
                    OfcSiloprogramInfo ofcSiloprogramInfo = new OfcSiloprogramInfo();
                    ofcSiloprogramInfo.setProgramSerialNumber("1");
                    siloProCreate(ofcSiloprogramInfo, ofcFundamentalInformation, goodsDetailsList, ofcWarehouseInformation, ofcFinanceInformation, ofcDistributionBasicInfo, authResDtoByToken.getUserName());

                    if (Objects.equals(ofcWarehouseInformation.getProvideTransport(), WEARHOUSE_WITH_TRANS)) {
                        OfcTransplanInfo ofcTransplanInfo = new OfcTransplanInfo();
                        ofcTransplanInfo.setProgramSerialNumber("1");
                        transPlanCreate(ofcTransplanInfo, ofcFundamentalInformation, goodsDetailsList, ofcDistributionBasicInfo, ofcFundamentalInformation.getCustName(), ofcFinanceInformation);
                    }
                } else {
                    throw new BusinessException("订单类型有误");
                }
                logger.info("计划单创建成功");
            } else {
                throw new BusinessException("缺少标志位");
            }

            //return String.valueOf(Wrapper.SUCCESS_CODE);
        } else {
            throw new BusinessException("订单类型既非“已审核”，也非“未审核”，请检查！");
        }

        return WrapMapper.wrap(Wrapper.SUCCESS_CODE);
    }


    /**
     * 更新创单接口订单和钉钉录单大区基地信息
     *
     * @param ofcFundamentalInformation 订单基本信息
     */
    private void updateOrderAreaAndBase(OfcFundamentalInformation ofcFundamentalInformation, OfcDistributionBasicInfo ofcDistributionBasicInfo) {
        RmcServiceCoverageForOrderVo rmcServiceCoverageForOrderVo = new RmcServiceCoverageForOrderVo();
        rmcServiceCoverageForOrderVo = copyDestinationPlace(ofcDistributionBasicInfo.getDeparturePlaceCode(), rmcServiceCoverageForOrderVo);
        RmcServiceCoverageForOrderVo rmcPickup = rmcServiceCoverageAPI(rmcServiceCoverageForOrderVo, "Pickup");
        updateOrderAreaAndBase(rmcPickup, ofcFundamentalInformation);
    }


    /**
     * 发送到仓储中心
     *
     * @param info        仓库发送vo
     * @param planDetails 计划单明细
     */
    private void sendToWhc(OfcSiloprogramInfoVo info, OfcWarehouseInformation ofcWarehouseInformation, List<OfcPlannedDetail> planDetails
            , OfcDistributionBasicInfo disInfo, OfcFinanceInformation finfo, OfcFundamentalInformation fuInfo, AuthResDto authResDtoByToken) {
        try {
            String tag = "";
            String jsonStr = "";
            boolean isSend = false;
            String documentType = info.getDocumentType();
            String businessType = info.getBusinessType();
            if (planDetails == null || planDetails.size() == 0) {
                logger.info("仓储计划单详情不存在");
                throw new BusinessException("仓储计划单详情不存在");
            }
            if (OFC_WHC_OUT_TYPE.equals(businessType)) {
                tag = documentType;
                WhcDelivery wsv = new WhcDelivery();
                List<WhcDeliveryDetails> detailList = new ArrayList<>();
                wsv.setWhcBillno("");//出库单号
                wsv.setBillType(documentType);//订单类型
                // wsv.setOutDate(new Date());//出库日期
                wsv.setCreateTime(info.getCreationTime() == null ? new Date() : info.getCreationTime());//创建时间
                wsv.setOpetator(authResDtoByToken.getUserName());//创建人
                //wsv.setOperatingTime(new Date());//操作日期
                // wsv.setStatus("");//出库单状态
                wsv.setWareHouseCode(trimAndNullAsEmpty(info.getWarehouseCode()));//仓库编号
                wsv.setWareHouseName(trimAndNullAsEmpty(info.getWarehouseName()));//仓库名称
                wsv.setCustomerCode(trimAndNullAsEmpty(fuInfo.getCustCode()));//货主编号
                wsv.setCustomerName(trimAndNullAsEmpty(fuInfo.getCustName()));//货主名称
                wsv.setExpectedArriveTime(info.getArriveTime());//预计出库时间
                wsv.setOrderNo(info.getOrderCode());//订单编号
                wsv.setWmsNo("");//wms单据号
                wsv.setNotes("");//备注
                wsv.setConsigneeCode(trimAndNullAsEmpty(info.getConsigneeCode()));//收货人编码
                wsv.setConsigneeName(trimAndNullAsEmpty(info.getConsigneeName()));//收货方名称
                wsv.setConsigneeContact(trimAndNullAsEmpty(info.getConsigneeContact()));//收货方联系人
                wsv.setConsigneeTel(trimAndNullAsEmpty(info.getConsigneeContactPhone()));//手机号码
                if (disInfo != null) {
                    wsv.setcProvince(trimAndNullAsEmpty(disInfo.getDestinationProvince()));//省
                    wsv.setcCity(trimAndNullAsEmpty(disInfo.getDestinationCity()));//城市
                    wsv.setcDistrict(trimAndNullAsEmpty(disInfo.getDestinationDistrict()));//县
                    wsv.setcStreet(trimAndNullAsEmpty(disInfo.getDestinationTowns()));//乡镇街道
                    wsv.setConsigneeAddr(trimAndNullAsEmpty(disInfo.getDestination()));
                } else {
                    wsv.setcProvince(trimAndNullAsEmpty(info.getConsigneeProvince()));//省
                    wsv.setcCity(trimAndNullAsEmpty(info.getConsigneeCity()));//城市
                    wsv.setcDistrict(trimAndNullAsEmpty(info.getConsigneeDistrictAndCounty()));//县
                    wsv.setcStreet(trimAndNullAsEmpty(info.getConsigneeTownshipStreets()));//乡镇街道
                    wsv.setConsigneeAddr(trimAndNullAsEmpty(info.getConsigneeAddress()));//详细地址
                }
                if (disInfo != null) {
                    wsv.setCarrierCode(trimAndNullAsEmpty(disInfo.getCarrierCode()));//承运商编码
                    wsv.setCarrierName(trimAndNullAsEmpty(disInfo.getCarrierName()));//承运商名称
                    wsv.setVehical(disInfo.getPlateNumber());//车牌号
                    wsv.setDriver(disInfo.getDriverName());//司机名称
                    wsv.setDriverTel(disInfo.getContactNumber());//联系电话
                }
                //wsv.setWareHouseExpense(12);//仓储费用
                wsv.setPrintInvoice(trimAndNullAsEmpty(info.getPrintInvoice()));//是否打印发票
                wsv.setInvoiceTitle("");//发票抬头
                wsv.setInvoiceContent("");//发票内容
                if (finfo != null) {
                    wsv.setCollect(trimAndNullAsEmpty(finfo.getCollectFlag()));//是否代收
                }
                //  wsv.setcollectAmount(info.getOrderAmount());//代收金额
                wsv.setPlanNo(info.getPlanCode());//计划单号
                wsv.setCustomerOrderNo(fuInfo.getCustOrderCode());//客户订单号
                for (int i = 0; i < planDetails.size(); i++) {
                    OfcPlannedDetail gdinfo = planDetails.get(i);
                    WhcDeliveryDetails detail = new WhcDeliveryDetails();
                    detail.setWhcBillno("");//入库单号
                    detail.setLineNo(i + 1);//行号
                    detail.setItemCode(trimAndNullAsEmpty(gdinfo.getGoodsCode()));//货品编号
                    detail.setItemName(trimAndNullAsEmpty(gdinfo.getGoodsName()));//货品名称
                    detail.setStandard(trimAndNullAsEmpty(gdinfo.getGoodsSpec()));//货品规格
                    detail.setUom(trimAndNullAsEmpty(gdinfo.getUnit()));//单位
                    detail.setPrice(gdinfo.getUnitPrice());//价格是否是单价
                    detail.setQty(gdinfo.getQuantity());//出库数量
                    detail.setSendQty(gdinfo.getQuantity());//实际出库数量
                    detail.setLotInfo(info.getOrderBatchNumber());//生产批次号
                    detail.setProductionDate(gdinfo.getProductionTime());//生产日期
                    detail.setExpiryDate(gdinfo.getInvalidTime());//失效日期
                    detailList.add(detail);
                }
                wsv.setDetailsList(detailList);
                jsonStr = JacksonUtil.toJsonWithFormat(wsv);
            } else if (OFC_WHC_IN_TYPE.equals(businessType)) {
                tag = documentType;
                WhcInStock wp = new WhcInStock();
                List<WhcInStockDetails> detailList = new ArrayList<>();
                wp.setOrderNo(info.getOrderCode());//订单编号
                wp.setWhcBillno("");//入库单号
                wp.setBillType(documentType);//入库单类型
                //wp.setStorageDate(new Date());//入库日期
                wp.setCreateTime(info.getCreationTime() == null ? new Date() : info.getCreationTime());//创建时间
                wp.setCreator(authResDtoByToken.getUserName());//创建人
                wp.setOperator("");//操作人
                //wp.setOperatingTime(new Date());//操作时间
                wp.setStatus("");//入库单状态
                wp.setWareHouseCode(trimAndNullAsEmpty(info.getWarehouseCode()));//仓库编号
                wp.setWareHouseName(trimAndNullAsEmpty(info.getWarehouseName()));//仓库名称
                wp.setCustomerCode(trimAndNullAsEmpty(fuInfo.getCustCode()));//货主编号
                wp.setCustomerName(trimAndNullAsEmpty(fuInfo.getCustName()));//货主名称
                wp.setExpectedArriveTime(info.getArriveTime());//预计货物到达时间
                wp.setWmsNo("");//wms单号
                wp.setNotes("");//备注
                wp.setSupplierCode(trimAndNullAsEmpty(ofcWarehouseInformation.getSupportCode()));//供应商编码
                wp.setSupplierName(trimAndNullAsEmpty(ofcWarehouseInformation.getSupportName()));//供应商名称
                wp.setSupplierContact("");//供应商联系人
                wp.setSupplierAddr("");//供应商地址
                if (disInfo != null) {
                    wp.setCarrierCode(trimAndNullAsEmpty(disInfo.getCarrierCode()));//承运人编码
                    wp.setCarrierName(trimAndNullAsEmpty(disInfo.getCarrierName()));//承运人姓名
                    wp.setVehical(disInfo.getPlateNumber());//车牌号
                    wp.setDriver(disInfo.getDriverName());//司机名称
                    wp.setDriverTel(disInfo.getContactNumber());//联系电话
                }
                //wp.setWareHouseExpense(12);//仓储费用
                wp.setPlanNo(info.getPlanCode());//计划单号
                wp.setCustomerOrderNo(fuInfo.getCustOrderCode());//客户订单编号
                for (int i = 0; i < planDetails.size(); i++) {
                    OfcPlannedDetail gdinfo = planDetails.get(i);
                    WhcInStockDetails detail = new WhcInStockDetails();
                    detail.setWhcBillno("");//仓库中心入库单
                    detail.setLineNo(i + 1);//行号
                    detail.setItemCode(trimAndNullAsEmpty(gdinfo.getGoodsCode()));//货品编号
                    detail.setItemName(trimAndNullAsEmpty(gdinfo.getGoodsName()));//货品名称
                    detail.setStandard(trimAndNullAsEmpty(gdinfo.getGoodsSpec()));//货品规格
                    detail.setUom(trimAndNullAsEmpty(gdinfo.getUnit()));//单位
                    detail.setPrice(gdinfo.getUnitPrice());//单价
                    detail.setQty(gdinfo.getQuantity());//货品数量
                    detail.setReceivedQty(gdinfo.getQuantity());//实际入库数量
                    detail.setLotInfo(info.getOrderBatchNumber());//生产批次号
                    detail.setProductionDate(gdinfo.getProductionTime());//生产日期
                    detail.setExpiryDate(gdinfo.getInvalidTime());//失效日期
                    detail.setItemStatus("");//良品状态
                    detailList.add(detail);
                }
                wp.setDetailsList(detailList);
                jsonStr = JacksonUtil.toJsonWithFormat(wp);
            }
            if (!StringUtils.isEmpty(jsonStr)) {
                logger.info("send to whc json is :" + jsonStr);
                isSend = defaultMqProducer.toSendWhc(jsonStr, info.getPlanCode(), tag);
            }
            if (isSend) {
                //推送成功后将计划单状态更新为已推送
                OfcSiloproStatus ofcSiloproStatus = new OfcSiloproStatus();
                OfcSiloproNewstatus ofcSiloproNewStatus = new OfcSiloproNewstatus();
                ofcSiloproNewStatus.setJobNewStatus(ALREADY_PUSH);
                ofcSiloproNewStatus.setPlanCode(info.getPlanCode());
                ofcSiloproStatus.setPlannedSingleState(ALREADY_PUSH);
                ofcSiloproNewStatus.setJobStatusUpdateTime(new Date());
                ofcSiloproStatus.setPlanCode(info.getPlanCode());
                // ofcSiloproStatusService.updateByPlanCode(ofcSiloproStatus);//更新仓储计划单的状态
                ofcSiloproNewstatusService.updateByPlanCode(ofcSiloproNewStatus);//更新仓储计划单最新的状态
            }
        } catch (Exception e) {
            logger.error("组装数据产生异常,{}", e);
            throw new BusinessException("推送到whc组装数据发生异常");
        }

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
     * 拷贝方法，用于将仓库实体中的对应信息拷贝入运输计划单中的发货方信息
     *
     * @param ofcTransplanInfo 运输计划单基本信息
     * @param rmcWarehouse     仓库
     * @return OfcTransplanInfo
     */
    private OfcTransplanInfo copyDarturePlace(OfcTransplanInfo ofcTransplanInfo, RmcWarehouseRespDto rmcWarehouse) {
        if (!trimAndNullAsEmpty(rmcWarehouse.getWarehouseCode()).equals("")) {
            ofcTransplanInfo.setShippinCustomerCode(rmcWarehouse.getWarehouseCode());
        }
        if (!trimAndNullAsEmpty(rmcWarehouse.getWarehouseName()).equals("")) {
            ofcTransplanInfo.setShippinCustomerName(rmcWarehouse.getWarehouseName());
        }
        if (!trimAndNullAsEmpty(rmcWarehouse.getDetailAddress()).equals("")) {
            ofcTransplanInfo.setShippingAddress(rmcWarehouse.getDetailAddress());
        }
        if (!trimAndNullAsEmpty(rmcWarehouse.getContactName()).equals("")) {
            ofcTransplanInfo.setShippingCustomerContact(rmcWarehouse.getContactName());
        }
        if (!trimAndNullAsEmpty(rmcWarehouse.getPhone()).equals("")) {
            ofcTransplanInfo.setCustomerContactPhone(rmcWarehouse.getPhone());
        }
        if (!trimAndNullAsEmpty(rmcWarehouse.getProvince()).equals("")) {
            ofcTransplanInfo.setDepartureProvince(rmcWarehouse.getProvince());
        }
        if (!trimAndNullAsEmpty(rmcWarehouse.getCity()).equals("")) {
            ofcTransplanInfo.setDepartureCity(rmcWarehouse.getCity());
        }
        if (!trimAndNullAsEmpty(rmcWarehouse.getArea()).equals("")) {
            ofcTransplanInfo.setDepartureDistrict(rmcWarehouse.getArea());
        } else {
            ofcTransplanInfo.setDepartureDistrict("");
        }
        if (!trimAndNullAsEmpty(rmcWarehouse.getStreet()).equals("")) {
            ofcTransplanInfo.setDepartureTowns(rmcWarehouse.getStreet());
        } else {
            ofcTransplanInfo.setDepartureTowns("");
        }
        if (!trimAndNullAsEmpty(rmcWarehouse.getProvinceCode()).equals("")
                && !trimAndNullAsEmpty(rmcWarehouse.getCityCode()).equals("")
                && !trimAndNullAsEmpty(rmcWarehouse.getAreaCode()).equals("")
                && !trimAndNullAsEmpty(rmcWarehouse.getStreetCode()).equals("")) {
            ofcTransplanInfo.setDeparturePlaceCode(
                    rmcWarehouse.getProvinceCode() + ","
                            + rmcWarehouse.getCityCode() + ","
                            + rmcWarehouse.getAreaCode() + ","
                            + rmcWarehouse.getStreetCode() + ","
            );
        }
        return ofcTransplanInfo;
    }

    /**
     * 拷贝方法，用于将仓库实体中的对应信息拷贝入运输计划单中的收货方信息
     *
     * @param ofcTransplanInfo 运输计划单基本信息
     * @param rmcWarehouse     仓库
     * @return OfcTransplanInfo
     */
    private OfcTransplanInfo copyDestinationPlace(OfcTransplanInfo ofcTransplanInfo, RmcWarehouseRespDto rmcWarehouse) {
        if (!trimAndNullAsEmpty(rmcWarehouse.getWarehouseCode()).equals("")) {
            ofcTransplanInfo.setReceivingCustomerCode(rmcWarehouse.getWarehouseCode());
        }
        if (!trimAndNullAsEmpty(rmcWarehouse.getWarehouseName()).equals("")) {
            ofcTransplanInfo.setReceivingCustomerName(rmcWarehouse.getWarehouseName());
        }
        if (!trimAndNullAsEmpty(rmcWarehouse.getDetailAddress()).equals("")) {
            ofcTransplanInfo.setReceivingCustomerAddress(rmcWarehouse.getDetailAddress());
        }
        if (!trimAndNullAsEmpty(rmcWarehouse.getContactName()).equals("")) {
            ofcTransplanInfo.setReceivingCustomerContact(rmcWarehouse.getContactName());
        }
        if (!trimAndNullAsEmpty(rmcWarehouse.getPhone()).equals("")) {
            ofcTransplanInfo.setReceivingCustomerContactPhone(rmcWarehouse.getPhone());
        }
        if (!trimAndNullAsEmpty(rmcWarehouse.getProvince()).equals("")) {
            ofcTransplanInfo.setDestinationProvince(rmcWarehouse.getProvince());
        }
        if (!trimAndNullAsEmpty(rmcWarehouse.getCity()).equals("")) {
            ofcTransplanInfo.setDestinationCity(rmcWarehouse.getCity());
        }
        if (!trimAndNullAsEmpty(rmcWarehouse.getArea()).equals("")) {
            ofcTransplanInfo.setDestinationDistrict(rmcWarehouse.getArea());
        } else {
            ofcTransplanInfo.setDestinationDistrict("");
        }
        if (!trimAndNullAsEmpty(rmcWarehouse.getStreet()).equals("")) {
            ofcTransplanInfo.setDestinationTown(rmcWarehouse.getStreet());
        } else {
            ofcTransplanInfo.setDestinationTown("");
        }
        if (!trimAndNullAsEmpty(rmcWarehouse.getProvinceCode()).equals("")
                && !trimAndNullAsEmpty(rmcWarehouse.getCityCode()).equals("")
                && !trimAndNullAsEmpty(rmcWarehouse.getAreaCode()).equals("")
                && !trimAndNullAsEmpty(rmcWarehouse.getStreetCode()).equals("")) {
            ofcTransplanInfo.setDestinationCode(
                    rmcWarehouse.getProvinceCode() + ","
                            + rmcWarehouse.getCityCode() + ","
                            + rmcWarehouse.getAreaCode() + ","
                            + rmcWarehouse.getStreetCode() + ","
            );
        }
        return ofcTransplanInfo;
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
            , OfcDistributionBasicInfo ofcDistributionBasicInfo, List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos) {
        AcOrderDto acOrderDto = new AcOrderDto();
        try {
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

        } catch (Exception e) {
            logger.error("订单信息推送结算中心 转换异常, {}", e);
        }
        Wrapper<?> wrapper = acOrderEdasService.pullOfcOrder(acOrderDto);
        if (ERROR_CODE == wrapper.getCode()) {
            logger.error(wrapper.getMessage());
            throw new BusinessException(wrapper.getMessage());
        }
    }

    /**
     * <p>Title:    .保存</p>
     * <p>Description 计划单明细</p>
     *
     * @param iter             循环变量
     * @param ofcTransplanInfo 计划单基本信息
     * @return List<OfcPlannedDetail>
     * @author 杨东旭
     * @CreateDate 2017/2/3
     */
    private List<OfcPlannedDetail> savePlannedDetail(Iterator<OfcGoodsDetailsInfo> iter
            , OfcTransplanInfo ofcTransplanInfo) {
        List<OfcPlannedDetail> ofcPlannedDetailList = new ArrayList<>();
        while (iter.hasNext()) {
            OfcPlannedDetail ofcPlannedDetail = new OfcPlannedDetail();
            //保存计划单明细
            ofcPlannedDetail.setPlanCode(ofcTransplanInfo.getPlanCode());
            OfcGoodsDetailsInfo ofcGoodsDetailsInfo = iter.next();
            if ((ofcGoodsDetailsInfo.getQuantity() != null && ofcGoodsDetailsInfo.getQuantity().compareTo(new BigDecimal(0)) != 0)
                    || (ofcGoodsDetailsInfo.getWeight() != null && ofcGoodsDetailsInfo.getWeight().compareTo(new BigDecimal(0)) != 0)
                    || (ofcGoodsDetailsInfo.getCubage() != null && ofcGoodsDetailsInfo.getCubage().compareTo(new BigDecimal(0)) != 0)) {
                try {
                    BeanUtils.copyProperties(ofcPlannedDetail, ofcTransplanInfo);
                    ofcGoodsDetailsInfo.setGoodsCode(ofcGoodsDetailsInfo.getGoodsCode().split("@")[0]);
                    BeanUtils.copyProperties(ofcPlannedDetail, ofcGoodsDetailsInfo);
                    ofcPlannedDetailList.add(ofcPlannedDetail);
                } catch (IllegalAccessException e) {
                    logger.error("保存计划单明细实体失败:{}", e);
                    throw new BusinessException("保存计划单明细实体失败！");
                } catch (InvocationTargetException e) {
                    logger.error("复制计划单实体属性失败:{}", e);
                    throw new BusinessException("复制计划单实体属性失败！");
                }
                ofcPlannedDetailService.save(ofcPlannedDetail);
            }
            logger.info("计划单明细保存成功");
        }
        return ofcPlannedDetailList;
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
        ofcInfo.setOrderCode(ofcFundamentalInformation.getOrderCode());
        int update = ofcFundamentalInformationService.update(ofcInfo);
        if (update == 0) {
            logger.error("更新订单的大区和基地失败!");
            throw new BusinessException("更新订单的大区和基地失败!");
        }
    }

    /**
     * <p>Title:    .仅用于 </p>
     * <p>Description 保存订单状态为执行中</p>
     *
     * @param ofcTransplanInfo 计划单基本信息
     * @param userName         用户名
     * @return void
     * @author 杨东旭
     * @CreateDate
     */
    private void saveOrderStatusOfImplemente(OfcTransplanInfo ofcTransplanInfo, String userName) {
        OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
        ofcOrderStatus.setOrderCode(ofcTransplanInfo.getOrderCode());
        ofcOrderStatus.setOrderStatus(IMPLEMENTATION_IN);
        ofcOrderStatus.setStatusDesc("执行中");
        ofcOrderStatus.setNotes(new StringBuilder()
                .append(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1))
                .append(" ").append("订单开始执行").toString());
        ofcOrderStatus.setOperator(userName);
        ofcOrderStatus.setLastedOperTime(new Date());
        ofcOrderStatusService.save(ofcOrderStatus);

        //订单中心--订单状态推结算中心(执行中和已完成)
        logger.info("=====>订单中心--订单状态推结算中心");
        this.pullOfcOrderStatus(ofcOrderStatus);
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
        form.setStartDate("2016-12-29");
        form.setEndDate("2017-03-29");
        beginTime="2016-12-29";
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
        if(!(CollectionUtils.isEmpty(twoHourOrderCount)&&CollectionUtils.isEmpty(yesterdayOrderCount))){
            for(OrderCountResult twoHourOrderCountResult:twoHourOrderCount){
                for(OrderCountResult yesterdayOrderCountResult:yesterdayOrderCount){
                    StringBuilder key=new StringBuilder();
                    if(!(PubUtils.isSEmptyOrNull(yesterdayOrderCountResult.getAreaCode())&&
                            PubUtils.isSEmptyOrNull(yesterdayOrderCountResult.getBaseCode()))&&!(PubUtils.isSEmptyOrNull(twoHourOrderCountResult.getAreaCode())&&PubUtils.isSEmptyOrNull(twoHourOrderCountResult.getBaseCode()))){
                        if(yesterdayOrderCountResult.getAreaCode().equals(twoHourOrderCountResult.getAreaCode())&&yesterdayOrderCountResult.getBaseCode().equals(twoHourOrderCountResult.getBaseCode())){
                            key.append(yesterdayOrderCountResult.getAreaCode()).append(yesterdayOrderCountResult.getBaseCode());
                        }
                    }else if((!PubUtils.isSEmptyOrNull(yesterdayOrderCountResult.getAreaCode())&&PubUtils.isSEmptyOrNull(yesterdayOrderCountResult.getBaseCode()))&&(!PubUtils.isSEmptyOrNull(twoHourOrderCountResult.getAreaCode())&&PubUtils.isSEmptyOrNull(twoHourOrderCountResult.getBaseCode()))){
                        if(yesterdayOrderCountResult.getAreaCode().equals(twoHourOrderCountResult.getAreaCode())){
                            key.append(yesterdayOrderCountResult.getAreaCode());
                        }
                    }else{
                        key.append("ALL");
                    }
                    //发送到钉钉机器人的数据
                    OfcOrderAccountDTO  ofcOrderAccountDTO=new OfcOrderAccountDTO();
                    ofcOrderAccountDTO.setAreaCode(yesterdayOrderCountResult.getAreaCode());
                    ofcOrderAccountDTO.setAreaName(yesterdayOrderCountResult.getAreaName());
                    ofcOrderAccountDTO.setBaseName(yesterdayOrderCountResult.getBaseName());
                    ofcOrderAccountDTO.setBaseCode(yesterdayOrderCountResult.getBaseCode());

                    //保存到数据库的数据
                    OfcDailyAccount ofcDailyAccount=new OfcDailyAccount();
                    ofcDailyAccount.setAreaCode(yesterdayOrderCountResult.getAreaCode());
                    ofcDailyAccount.setAreaName(yesterdayOrderCountResult.getAreaName());
                    ofcDailyAccount.setBaseName(yesterdayOrderCountResult.getBaseName());
                    ofcDailyAccount.setBaseCode(yesterdayOrderCountResult.getBaseCode());
                    ////两小时的订单总计
                    ofcDailyAccount.setTwoHourAccount(twoHourOrderCountResult.getOrderCount());
                    ////前一天的开单总计
                    ofcDailyAccount.setYesterdayAccount(yesterdayOrderCountResult.getOrderCount());
                    ofcDailyAccount.setGmtCreate(new Date());
                    BigDecimal p=twoHourOrderCountResult.getOrderCount().divide(yesterdayOrderCountResult.getOrderCount(),2, RoundingMode.HALF_UP);
                    //事后补录订单：2小时订单/开单合计
                    ofcDailyAccount.setAdditionalOrder(p.setScale(2));
                    ofcDailyAccount.setAdditionalOrderPercent(percent.format(p.doubleValue()));
                    ofcOrderAccountDTO.setAdditionalOrder(p.setScale(2));
                    ofcOrderAccountDTO.setAdditionalOrderPercent(percent.format(p.doubleValue()));
                    if(!PubUtils.isSEmptyOrNull(key.toString())){
                        if(!accountDailyResult.containsKey(key.toString())){
                            accountDailyResult.put(key.toString(),ofcDailyAccount);
                        }
                        if(!account.containsKey(key.toString())){
                            account.put(key.toString(),ofcOrderAccountDTO);
                        }
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
                        key.append("ALL");
                    }
                    if(accountDailyResult.containsKey(key.toString())){
                        OfcDailyAccount ofcDailyAccount=accountDailyResult.get(key.toString());
                        OfcOrderAccountDTO ofcOrderAccountDTO=account.get(key.toString());
                        ofcDailyAccount.setHaveIncomeOrderAccount(BigDecimal.valueOf(acIncomeSettleDTO.getReceivableOrderNumber()));
                        ofcDailyAccount.setPayableVehicleAccount(BigDecimal.valueOf(acIncomeSettleDTO.getPayableCarNumber()));
                        BigDecimal p=ofcDailyAccount.getHaveIncomeOrderAccount().divide(ofcDailyAccount.getYesterdayAccount(),2, RoundingMode.HALF_UP);
                        //应收确认日清：收入确认的订单/开单合计
                        ofcDailyAccount.setReceivable(p.setScale(2));
                        ofcDailyAccount.setReceivablePercent(percent.format(p.doubleValue()));
                        ofcOrderAccountDTO.setReceivable(p.setScale(2));
                        ofcOrderAccountDTO.setReceivablePercent(percent.format(p.doubleValue()));
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
                        key.append("ALL");
                    }
                    if(accountDailyResult.containsKey(key.toString())){
                        OfcDailyAccount ofcDailyAccount=accountDailyResult.get(key.toString());
                        OfcOrderAccountDTO ofcOrderAccountDTO=account.get(key.toString());
                        ofcDailyAccount.setExternalVehicleAccount(BigDecimal.valueOf(deliverEveryRunDTO.getNum()));
                        BigDecimal p=ofcDailyAccount.getPayableVehicleAccount().divide(ofcDailyAccount.getExternalVehicleAccount(),2, RoundingMode.HALF_UP);
                        //应付确认日清 应付确认车数量/外部车辆发运数量
                        ofcDailyAccount.setPayable(p.setScale(2));
                        ofcDailyAccount.setPayablePercent(percent.format(p.doubleValue()));
                        ofcOrderAccountDTO.setPayable(p.setScale(2));
                        ofcOrderAccountDTO.setPayablePercent(percent.format(p.doubleValue()));
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
        if (ofcWarehouseInformation.getProvideTransport() == 1) {
            ofcFundamentalInformation.setTransportType("整车");
            if (ofcDistributionBasicInfo == null) {
                return WrapMapper.wrap(Wrapper.ERROR_CODE, "需要运输时送基本信息不能为空 ");
            }
        }

        //订单的基本信息
        ofcFundamentalInformation.setCreationTime(new Date());
        ofcFundamentalInformation.setCreator(authResDtoByToken.getUserId());
        ofcFundamentalInformation.setCreatorName(authResDtoByToken.getUserName());
        ofcFundamentalInformation.setOperator(authResDtoByToken.getUserId());
        ofcFundamentalInformation.setOperatorName(authResDtoByToken.getUserName());
        ofcFundamentalInformation.setOrderType(WAREHOUSE_DIST_ORDER);

        //校验当前登录用户的身份信息,并存放大区和基地信息
        ofcFundamentalInformation = ofcOrderPlaceService.getAreaAndBaseMsg(authResDtoByToken, ofcFundamentalInformation);
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
            if(!StringUtils.isEmpty(ofcGoodsDetails.getProductionBatch())){
                key.append(ofcGoodsDetails.getProductionBatch());
            }
            if(ofcGoodsDetails.getCreationTime()!=null){
                key.append(DateUtils.Date2String(ofcGoodsDetails.getCreationTime(), DateUtils.DateFormatType.TYPE1));
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

        if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_EDIT)) {
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
     * 订单的复制
     *
     * @param orderCode
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
            newofcFundamentalInformation = ofcOrderPlaceService.getAreaAndBaseMsg(authResDtoByToken, newofcFundamentalInformation);
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
                this.updateOrderAreaAndBase(ofcFundamentalInformation, ofcDistributionBasicInfo);
                this.pushOrderToAc(ofcFundamentalInformation, ofcFinanceInformation, ofcDistributionBasicInfo, goodsDetailsList);
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

}