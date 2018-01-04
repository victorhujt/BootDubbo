package com.xescm.ofc.service.impl;

import com.github.pagehelper.PageInfo;
import com.xescm.ac.domain.AcDistributionBasicInfo;
import com.xescm.ac.domain.AcFinanceInformation;
import com.xescm.ac.domain.AcFundamentalInformation;
import com.xescm.ac.domain.AcGoodsDetailsInfo;
import com.xescm.ac.model.dto.AcOrderDto;
import com.xescm.ac.model.dto.ofc.AcOrderStatusDto;
import com.xescm.ac.model.dto.ofc.CancelAcOrderDto;
import com.xescm.ac.model.dto.ofc.CancelAcOrderResultDto;
import com.xescm.ac.provider.AcOrderEdasService;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.core.utils.PublicUtil;
import com.xescm.csc.model.dto.CscGoodsApiDto;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.QueryCustomerCodeDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContactCompanyDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContactDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyResponseDto;
import com.xescm.csc.model.dto.contract.ContractDto;
import com.xescm.csc.model.dto.contract.ofc.CscContractProEdasDto;
import com.xescm.csc.model.dto.packing.GoodsPackingDto;
import com.xescm.csc.model.vo.CscCustomerVo;
import com.xescm.csc.model.vo.CscEdasContractVo;
import com.xescm.csc.model.vo.CscGoodsApiVo;
import com.xescm.csc.provider.CscContactEdasService;
import com.xescm.csc.provider.CscContractEdasService;
import com.xescm.csc.provider.CscCustomerEdasService;
import com.xescm.csc.provider.CscSupplierEdasService;
import com.xescm.dpc.edas.dto.DpcOrderGroupInfoDto;
import com.xescm.dpc.edas.service.DpcTransportDocEdasService;
import com.xescm.ofc.config.MqConfig;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.edas.model.dto.ofc.ModifyAbwKbOrderDTO;
import com.xescm.ofc.edas.model.dto.ofc.OfcOrderCancelDto;
import com.xescm.ofc.enums.OrderStatusEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcAddressReflectMapper;
import com.xescm.ofc.model.dto.ofc.*;
import com.xescm.ofc.model.dto.tfc.TfcTransport;
import com.xescm.ofc.model.dto.tfc.TfcTransportDetail;
import com.xescm.ofc.model.vo.ofc.OfcGroupVo;
import com.xescm.ofc.mq.producer.DefaultMqProducer;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.BeanConvertor;
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.ofc.utils.DateUtils;
import com.xescm.rmc.edas.domain.qo.RmcCompanyLineQO;
import com.xescm.rmc.edas.domain.vo.RmcCompanyLineVo;
import com.xescm.rmc.edas.domain.vo.RmcServiceCoverageForOrderVo;
import com.xescm.rmc.edas.service.RmcCompanyInfoEdasService;
import com.xescm.rmc.edas.service.RmcServiceCoverageEdasService;
import com.xescm.rmc.edas.service.RmcWarehouseEdasService;
import com.xescm.tfc.edas.model.dto.CancelOrderDTO;
import com.xescm.tfc.edas.service.CancelOrderEdasService;
import com.xescm.uam.model.dto.group.UamGroupDto;
import com.xescm.whc.edas.dto.*;
import com.xescm.whc.edas.dto.req.WhcModifWmsCodeReqDto;
import com.xescm.whc.edas.service.WhcModifWmsCodeEdasService;
import com.xescm.whc.edas.service.WhcOrderCancelEdasService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.xescm.base.model.wrap.Wrapper.ERROR_CODE;
import static com.xescm.core.utils.PubUtils.isSEmptyOrNull;
import static com.xescm.core.utils.PubUtils.trimAndNullAsEmpty;
import static com.xescm.ofc.constant.CreateOrderApiConstant.DACHEN_CUST_CODE;
import static com.xescm.ofc.constant.GenCodePreffixConstant.*;
import static com.xescm.ofc.constant.OrderConstConstant.*;
import static com.xescm.ofc.constant.OrderConstant.*;
import static com.xescm.ofc.constant.OrderPlaceTagConstant.*;
import static com.xescm.ofc.enums.OrderStatusEnum.PEND_AUDIT;

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
    private RmcCompanyInfoEdasService rmcCompanyInfoEdasService;
    @Resource
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Resource
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Resource
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;
    @Resource
    private OfcWarehouseInformationService ofcWarehouseInformationService;
    @Resource
    private OfcFinanceInformationService ofcFinanceInformationService;
    @Resource
    private OfcOrderStatusService ofcOrderStatusService;
    @Resource
    private OfcCustFundamentalInformationService ofcCustFundamentalInformationService;
    @Resource
    private OfcCustGoodsDetailsInfoService ofcCustGoodsDetailsInfoService;
    @Resource
    private OfcCustDistributionBasicInfoService ofcCustDistributionBasicInfoService;
    @Resource
    private OfcCustWarehouseInformationService ofcCustWarehouseInformationService;
    @Resource
    private OfcCustFinanceInformationService ofcCustFinanceInformationService;
    @Resource
    private OfcCustOrderStatusService ofcCustOrderStatusService;
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
    private OfcOrderNewstatusService ofcOrderNewstatusService;
    @Resource
    private OfcPotNormalRuleService ofcPotNormalRuleService;
    @Resource
    private DefaultMqProducer mqProducer;
    @Resource
    private MqConfig mqConfig;
    @Resource
    private OfcEnumerationService ofcEnumerationService;

    private ModelMapper modelMapper = new ModelMapper();

    @Resource
    private WhcModifWmsCodeEdasService whcModifWmsCodeEdasService;

    @Resource
    private DpcTransportDocEdasService dpcTransportDocEdasService;
    @Resource
    private CscContractEdasService cscContractEdasService;

    @Override
    public Map orderStorageDetails(String orderCode) {
        Map<String , Object> result = new HashMap<>();
        ConcurrentHashMap amap = new ConcurrentHashMap();
        OfcDistributionBasicInfo ofcDistributionBasicInfo;
        OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
        if (ofcFundamentalInformation == null) {
            throw new BusinessException("该订单不存在订单");
        }
        List<OfcGoodsDetailsInfo> goodsDetailsList = ofcGoodsDetailsInfoService.goodsDetailsScreenList(orderCode, "orderCode");
        if (CollectionUtils.isEmpty(goodsDetailsList)) {
            throw new BusinessException("该订单不存在货品详情");
        }

        OfcWarehouseInformation ofcWarehouseInformation = ofcWarehouseInformationService.warehouseInformationSelect(orderCode);
        if (ofcWarehouseInformation == null) {
            throw new BusinessException("该订单不存在仓储信息");
        }


        if (WEARHOUSE_WITH_TRANS.equals(ofcWarehouseInformation.getProvideTransport())) {
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
        if (reviewTag.equals(REVIEW)) {
            if (ofcOrderStatus.getOrderStatus().equals(PENDING_AUDIT)) {
                OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
                    OfcWarehouseInformation ofcWarehouseInformation = ofcWarehouseInformationService.warehouseInformationSelect(orderCode);
                /**
                 * 金融中心锁定客户不允许出库，追加逻辑
                 */
                validateCustomerIsLocked(ofcFundamentalInformation);
                /**
                 * 校验仓储无合同客户不允许下单 需求号:1357
                 */
                validateCustomerContract(ofcFundamentalInformation);
                //2017年6月13日 追加逻辑: 判断订单上是否有基地信息, 若无, 则不允许审核, 即维持待审核
                if (PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getBaseCode())
                        || PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getBaseName())) {
                    ofcOrderPlaceService.updateBaseAndAreaBywarehouseCode(ofcWarehouseInformation.getWarehouseCode(),ofcFundamentalInformation);
                    if (PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getBaseCode())
                        || PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getBaseName())) {
                        logger.error("订单没有基地信息, 维持待审核状态");
                        return String.valueOf(Wrapper.SUCCESS_CODE);
                    }
                }
                ofcFundamentalInformation.setOperator(authResDtoByToken.getUserId());
                ofcFundamentalInformation.setOperatorName(authResDtoByToken.getUserName());
                ofcFundamentalInformation.setOperTime(new Date());
                List<OfcGoodsDetailsInfo> goodsDetailsList = ofcGoodsDetailsInfoService.goodsDetailsScreenList(orderCode, "orderCode");
                if (!CollectionUtils.isEmpty(goodsDetailsList)){
                    validateGoodsPackage(ofcFundamentalInformation, goodsDetailsList, ofcWarehouseInformation);
                }

                OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoService.distributionBasicInfoSelect(orderCode);
                OfcFinanceInformation ofcFinanceInformation = ofcFinanceInformationService.queryByOrderCode(orderCode);
                if (ofcFinanceInformation == null) {
                    ofcFinanceInformation = new OfcFinanceInformation();
                }
                //提供运输
                if (WEARHOUSE_WITH_TRANS.equals(ofcWarehouseInformation.getProvideTransport())) {
                    if (ofcDistributionBasicInfo != null && PubUtils.isSEmptyOrNull(ofcDistributionBasicInfo.getTransCode())) {
                        throw new BusinessException("提供运输时，运输单号不能为空");
                    }
                    //仓储订单推仓储中心
                    pushOrderToWhc(ofcFundamentalInformation, goodsDetailsList, ofcWarehouseInformation, ofcFinanceInformation, ofcDistributionBasicInfo);
                    this.pushOrderToAc(ofcFundamentalInformation, ofcFinanceInformation, ofcDistributionBasicInfo, goodsDetailsList, ofcWarehouseInformation);
                    pushOrderToTfc(ofcFundamentalInformation, ofcFinanceInformation,ofcWarehouseInformation, ofcDistributionBasicInfo, goodsDetailsList);
                    //不提供运输
                } else if (WAREHOUSE_NO_TRANS.equals(ofcWarehouseInformation.getProvideTransport())) {
                    pushOrderToWhc(ofcFundamentalInformation, goodsDetailsList, ofcWarehouseInformation, ofcFinanceInformation, ofcDistributionBasicInfo);
                } else {
                    throw new BusinessException("无法确定是否需要运输");
                }
            } else {
                throw new BusinessException("订单编号[" + orderCode + "]不能执行审核，仅能对订单状态为【待审核】的订单执行审核操作！");
            }

            OfcOrderStatus s=new OfcOrderStatus();
            s.setOrderStatus(OrderStatusEnum.ALREADY_ACCEPTED.getCode());
            s.setOrderCode(orderCode);
            s.setStatusDesc(OrderStatusEnum.ALREADY_ACCEPTED.getDesc());
            s.setNotes(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1) + " " + "订单已受理");
            s.setOperator(authResDtoByToken.getUserName());
            s.setLastedOperTime(new Date());
            ofcOrderStatusService.save(s);
        }

        return String.valueOf(Wrapper.SUCCESS_CODE);
    }

    private void validateGoodsPackage(OfcFundamentalInformation ofcFundamentalInformation, List<OfcGoodsDetailsInfo> goodsDetailsList, OfcWarehouseInformation ofcWarehouseInformation) {
        if ("000001".equals(ofcWarehouseInformation.getWarehouseCode())) {
            for (OfcGoodsDetailsInfo goodsInfo :goodsDetailsList) {
                if (goodsInfo.getPrimaryQuantity() == null || goodsInfo.getPrimaryQuantity().doubleValue() == 0.0) {
                    goodsInfo.setPrimaryQuantity(goodsInfo.getQuantity());
                    goodsInfo.setPackageName(goodsInfo.getUnit());
                }
            }
        }
        String custCode = ofcFundamentalInformation.getCustCode();
        //没有匹配到包装的货品编码
        List<String> noPackageGoods = new ArrayList<>();
        //没有匹配到包装的货品编码
        List<String> notExistGoodsCodes = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        for (OfcGoodsDetailsInfo good:goodsDetailsList) {
            boolean isHavePackage = false;
            CscGoodsApiDto cscGoods = new CscGoodsApiDto();
            String goodsCode = good.getGoodsCode();
            String packageName = !PubUtils.isSEmptyOrNull(good.getPackageName())? good.getPackageName():good.getUnit();
            cscGoods.setWarehouseCode(ofcWarehouseInformation.getWarehouseCode());
            //天津自动化仓 用天津仓包装校验
            if ("000001".equals(ofcWarehouseInformation.getWarehouseCode()) && DACHEN_CUST_CODE.equals(custCode)) {
                cscGoods.setWarehouseCode("ck0024");
            } else {
                cscGoods.setWarehouseCode(ofcWarehouseInformation.getWarehouseCode());
            }
            cscGoods.setFromSys("WMS");
            cscGoods.setGoodsCode(goodsCode);
            cscGoods.setCustomerCode(custCode);
            cscGoods.setPNum(1);
            cscGoods.setPSize(10);
            Wrapper<PageInfo<CscGoodsApiVo>> goodsRest = null;
            try {
                logger.info("csc调用接口响应的参数为:{}",JacksonUtil.toJson(cscGoods));
                goodsRest = ofcGoodsDetailsInfoService.validateGoodsByCode(cscGoods);
                logger.info("csc调用接口响应的结果为:{}",JacksonUtil.toJson(goodsRest));
            }catch (Exception e) {
                e.printStackTrace();
            }
            if (goodsRest != null && Wrapper.SUCCESS_CODE == goodsRest.getCode() && goodsRest.getResult() != null &&
                    PubUtils.isNotNullAndBiggerSize(goodsRest.getResult().getList(), 0)) {
                CscGoodsApiVo cscGoodsApiVo = goodsRest.getResult().getList().get(0);
                List<GoodsPackingDto>  packages = cscGoodsApiVo.getGoodsPackingDtoList();
                if (!CollectionUtils.isEmpty(packages)) {
                    for (GoodsPackingDto packingDto : packages) {
                        if (StringUtils.equals(packageName,packingDto.getLevelDescription())) {
                            logger.info("orderCode is {}",ofcFundamentalInformation.getOrderCode());
                            logger.info("good.getPackageName() is {}",good.getUnit());
                            logger.info("packingDto.getLevelDescription() is {}",packingDto.getLevelDescription());
                            BigDecimal ls = packingDto.getLevelSpecification();
                            if (ls == null || ls.compareTo(new BigDecimal(0)) == 0) {
                                break;
                            }
                            good.setConversionRate(ls);
                            good.setPackageName(packingDto.getLevelDescription());
                            good.setPackageType(packingDto.getLevel());
                            if (!PubUtils.isSEmptyOrNull(good.getRemark())) {
                                good.setRemark("");
                            }
                            //保留三位小数
                            BigDecimal pquantity = good.getQuantity().divide(packingDto.getLevelSpecification(),3,BigDecimal.ROUND_HALF_DOWN);
                            good.setPrimaryQuantity(pquantity);
                            isHavePackage = true;
                            break;
                        }
                    }
                    if (!isHavePackage) {
                        if (!noPackageGoods.contains(goodsCode)) {
                            good.setRemark("货品编码包装或规格有误");
                            noPackageGoods.add(goodsCode);
                        }
                    }
                } else {
                    if (!noPackageGoods.contains(goodsCode)) {
                        good.setRemark("货品编码包装不存在");
                        noPackageGoods.add(goodsCode);
                    }
                }
            } else {
                if (!notExistGoodsCodes.contains(goodsCode)) {
                    good.setRemark("货品编码不存在");
                    notExistGoodsCodes.add(goodsCode);
                }
            }
            ofcGoodsDetailsInfoService.update(good);
        }
        if (noPackageGoods.size() > 0) {
            str.append("订单对应货品编码为");
            for (int i = 0; i < noPackageGoods.size(); i++) {
                if (i == noPackageGoods.size() -1) {
                    str.append(noPackageGoods.get(i));
                } else {
                    str.append(noPackageGoods.get(i)).append(",");
                }
            }
            str.append("没有包装.");
        }

        if (notExistGoodsCodes.size() > 0) {
            str.append("订单对应货品编码为");
            for (int i = 0; i < notExistGoodsCodes.size(); i++) {
                if (i == notExistGoodsCodes.size() -1) {
                    str.append(notExistGoodsCodes.get(i));
                } else {
                    str.append(notExistGoodsCodes.get(i)).append(",");
                }
            }
            str.append("不存在.");
        }
        if (!PubUtils.isSEmptyOrNull(str.toString())) {
            throw new BusinessException(str.toString());
        }
        if (!PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getExceptionReason())) {
            // 0 正常  1 异常
            ofcFundamentalInformation.setIsException(STR_NO);
            //异常原因置为空
            ofcFundamentalInformation.setExceptionReason("");
            ofcFundamentalInformationService.update(ofcFundamentalInformation);
        }
    }


    /**
     * 订单取消
     *
     * @param orderCode 订单号
     */
    private void orderCancel(String orderCode,String userName) {
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
        Wrapper response = null;
        Wrapper whcresponse = null;
        try {
            if (StringUtils.equals(orderType, TRANSPORT_ORDER)) {
                long start = System.currentTimeMillis();
                Wrapper<Integer> cancelStatus = acOrderEdasService.queryOrderCancelStatus(orderCode);
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
                boolean result = cancelAcOrder(orderCode);
                logger.info("订单中心取消订单，调用结算中心取消订单接口,返回结果：{}", result);
            } else if (StringUtils.equals(orderType, WAREHOUSE_DIST_ORDER)) {
                if (null == ofcWarehouseInformation) {
                    logger.error("仓储订单取消失败, ofcWarehouseInformation为null");
                    throw new BusinessException("订单取消失败, 订单信息不完整!");
                }
                String businessType = ofcFundamentalInformation.getBusinessType();
                String custOrderCode = ofcFundamentalInformation.getCustOrderCode();
                String warehouseCode = ofcWarehouseInformation.getWarehouseCode();
                String custCode = ofcFundamentalInformation.getCustCode();
                String type="";
                if (PubUtils.trimAndNullAsEmpty(businessType).substring(0,2).equals(STOCK_OUT_ORDER)) {
                    //出库
                    type="CK";
                }else if (PubUtils.trimAndNullAsEmpty(businessType).substring(0,2).equals(STOCK_IN_ORDER)) {
                    //入库
                    type="RK";
                }
                if (Objects.equals(ofcWarehouseInformation.getProvideTransport(), YES)) {
                    try {
                        long tfcStart = System.currentTimeMillis();
                        response= orderCancelToTfc(orderCode);
                        logger.info("=============> TFC取消耗时：" + (System.currentTimeMillis() - tfcStart)/1000);
                        logger.info("取消订单，调用TFC取消接口返回结果:{},订单号为:{}",response.getCode(),orderCode);
                        if (response != null && response.getCode()==Wrapper.SUCCESS_CODE) {
                            whcresponse= orderCancelToWhc(orderCode, type, custOrderCode, warehouseCode, custCode, businessType, userName);
                            logger.info("取消订单，调用WHC取消接口返回结果:{},订单号为:{}",whcresponse.getCode(),orderCode);
                        }
                    } catch (Exception e) {
                        logger.info("取消订单，调用TFC取消接口发生异常,返回结果：{}", e.getMessage(), e);
                        throw new BusinessException("调用TFC取消接口发生异常,返回结果：{}", e.getMessage(), e);
                    }
                }else{
                    try {
                        whcresponse= orderCancelToWhc(orderCode, type, custOrderCode, warehouseCode, custCode, businessType, userName);
                        logger.info("取消订单，调用WHC取消接口返回结果:{},订单号为:{}",whcresponse.getCode(),orderCode);
                    } catch (Exception e) {
                        logger.info("取消订单，调用WHC取消接口发生异常,返回结果：{}", e.getMessage(), e);
                        throw new BusinessException("调用WHC取消接口发生异常,返回结果：{}", e.getMessage(), e);
                    }
                }
            }
        } catch (Exception e) {
            logger.info("取消订单，调用结算中心、运输中心、仓储中心取消接口发生异常,返回结果：{}", e.getMessage(), e);
            throw new BusinessException("取消订单，调用结算中心、运输中心、仓储中心取消接口发生异常,返回结果：{}", e.getMessage(), e);
        }

        if (StringUtils.equals(orderType, TRANSPORT_ORDER)) {
            if (response!=null&&response.getCode()==Wrapper.SUCCESS_CODE) {
                logger.info("运输订单号{}取消成功!",orderCode);
            }else{
                throw new BusinessException("运输订单取消失败");
            }
        }

        if (StringUtils.equals(orderType, WAREHOUSE_DIST_ORDER)) {
            if (Objects.equals(ofcWarehouseInformation.getProvideTransport(), YES)) {
                if (whcresponse!=null&&whcresponse.getCode()==Wrapper.SUCCESS_CODE) {
                    logger.info("仓储订单提供运输时{}取消成功!",orderCode);
                }else{
                    throw new BusinessException("仓储订单取消失败");
                }
            }else{
                if (whcresponse!=null&&whcresponse.getCode()==Wrapper.SUCCESS_CODE) {
                    logger.info("仓储订单不提供运输时{}订单取消成功!",orderCode);
                }else{
                    throw new BusinessException("仓储订单取消失败");
                }
            }
        }
    }

    /**
     * 取消调度中心订单
     * @param orderCode 订单号
     */
    private void cancelDpcOrder(String orderCode, String cancelUserId, String cancelUserName) {
        OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
        OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoService.selectByKey(orderCode);
        OfcWarehouseInformation ofcWarehouseInformation = ofcWarehouseInformationService.queryByOrderCode(orderCode);
        if (ofcFundamentalInformation != null && ofcDistributionBasicInfo != null) {
            //排除仓储不带运输的订单
            if (null != ofcWarehouseInformation && StringUtils.equals(WAREHOUSE_DIST_ORDER, ofcFundamentalInformation.getOrderType())
                    && 0 != WEARHOUSE_WITH_TRANS.compareTo(null == ofcWarehouseInformation.getProvideTransport() ? 0 : ofcWarehouseInformation.getProvideTransport())) {
                return;
            }
            String custOrderCode = ofcFundamentalInformation.getCustOrderCode();
            String transCode = ofcDistributionBasicInfo.getTransCode();
            try {
                OfcOrderCancelDto dpcCancel = new OfcOrderCancelDto();
                dpcCancel.setOrderCode(orderCode);
                dpcCancel.setCustOrderCode(custOrderCode);
                dpcCancel.setTransCode(transCode);
                dpcCancel.setCancelUserId(cancelUserId);
                dpcCancel.setCancelUserName(cancelUserName);
                String jsonStr = JacksonUtil.toJson(dpcCancel);
                String key = orderCode + "@" + transCode;
                mqProducer.sendMsg(jsonStr, mqConfig.getOfc2DpcStatusTopic(), key, null);
            } catch (Exception e) {
                logger.error("推送调度中心取消订单MQ发生异常：{}", e);
                throw new BusinessException("取消订单推送调度中心失败");
            }
        }
    }

    /**
     * 调用仓储中心取消接口
     *
     * @param orderCode 订单编号
     * @return void
     */
    private Wrapper orderCancelToWhc(String orderCode,String type, String custOrderCode, String warehouseCode,String customerCode,String orderType,String userName) {
        logger.info("调用仓储中心取消接口, 订单号:{}，参数type:{},仓库编码:{}，客户编码:{},业务类型:{}", orderCode,type,warehouseCode,customerCode,orderType);
        Wrapper response = null;
        try {
            OfcCancelOrderDTO cancelOrderDTO = new OfcCancelOrderDTO();
            cancelOrderDTO.setOrderCode(orderCode);
            cancelOrderDTO.setBillType(type);
            cancelOrderDTO.setWarehouseID(warehouseCode);
            cancelOrderDTO.setOrderType(orderType);
            cancelOrderDTO.setCustomerID(customerCode);
            cancelOrderDTO.setCustomerOrderNo(custOrderCode );
            //cancelOrderDTO.setOperationName(userName);
            response = whcOrderCancelEdasService.cancelOrder(cancelOrderDTO);
            logger.info("取消订单，调用WHC取消接口返回结果:{},订单号为:{}", response.getCode(), orderCode);
        } catch (Exception e) {
            logger.error("仓储中心取消订单发生异常：异常详情 => {}", e);
            throw new BusinessException("取消订单失败：仓储中心取消订单发生异常!");
        }
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
        Wrapper wrapper;
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

    @Override
    /**
     *  @param orderCode         订单编号
     */
    public String orderDelete(String orderCode) {
        logger.info("删除的订单号为:{}",orderCode);
        OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
        if (ofcFundamentalInformation == null) {
            throw new BusinessException("订单号不存在");
        }
        OfcOrderNewstatus ofcOrderNewstatus = ofcOrderNewstatusService.selectByKey(orderCode);
        if (ofcOrderNewstatus != null) {
            if (!ofcOrderNewstatus.getOrderLatestStatus().equals(PENDING_AUDIT)) {
                throw new BusinessException("订单状态只有待审核状态才可以删除");
            }
            ofcFundamentalInformationService.deleteByKey(orderCode);
            ofcDistributionBasicInfoService.deleteByOrderCode(orderCode);
            ofcOrderStatusService.deleteByOrderCode(orderCode);
            ofcWarehouseInformationService.deleteByOrderCode(orderCode);
            OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
            ofcGoodsDetailsInfo.setOrderCode(orderCode);
            ofcGoodsDetailsInfoService.delete(ofcGoodsDetailsInfo);
            return String.valueOf(Wrapper.SUCCESS_CODE);
        }
        return null;
    }

    /**
     * 订单取消
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
        if ((!trimAndNullAsEmpty(orderStatus.getOrderStatus()).equals(HASBEEN_COMPLETED))
                && (!trimAndNullAsEmpty(orderStatus.getOrderStatus()).equals(HASBEEN_CANCELED))) {
            //调用各中心请求直接取消订单
            try {
                this.orderCancel(orderCode,authResDtoByToken.getUserName());
                // 向调度中心发送取消mq
                this.cancelDpcOrder(orderCode, authResDtoByToken.getUserId(), authResDtoByToken.getUserName());
            } catch (Exception e) {
                throw new BusinessException("调用其他中心取消接口异常:{}", e.getMessage(), e);
            }
            this.modifyRelativeWhenCancel(orderCode, authResDtoByToken);

            return String.valueOf(Wrapper.SUCCESS_CODE);
        } else {
            throw new BusinessException("计划单状态不在可取消范围内");
        }
    }

    private void modifyRelativeWhenCancel(String orderCode, AuthResDto authResDtoByToken) {
        StringBuilder notes = new StringBuilder();
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
    }

    /**
     * 取消的订单推送到结算中心进行结算取消
     *
     * @param orderCode 订单编号
     */
    @Transactional
    boolean cancelAcOrder(String orderCode) {
        logger.info("订单中心取消订单，调用结算中心取消订单接口==>订单编号:{}", orderCode);
        OfcDistributionBasicInfo ofcDistributionBasicInfo = new OfcDistributionBasicInfo();
        ofcDistributionBasicInfo.setOrderCode(orderCode);
        CancelAcOrderDto cancelOfcOrderDto = new CancelAcOrderDto();
        cancelOfcOrderDto.setOrderCode(orderCode);
        ofcDistributionBasicInfo = ofcDistributionBasicInfoService.selectOne(ofcDistributionBasicInfo);
        if (ofcDistributionBasicInfo == null) {
            logger.error("运输订单取消失败, ofcDistributionBasicInfo为null");
            throw new BusinessException("订单取消失败, 订单信息不完整!");
        }
        cancelOfcOrderDto.setTransCode(ofcDistributionBasicInfo.getTransCode());
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
    @Override
    public void updateOrderAreaAndBase(OfcFundamentalInformation ofcFundamentalInformation, OfcDistributionBasicInfo ofcDistributionBasicInfo) {
        logger.info("更新创单接口订单和钉钉录单大区基地信息 ofcFundamentalInformation : {}", ofcFundamentalInformation);
        logger.info("更新创单接口订单和钉钉录单大区基地信息 ofcDistributionBasicInfo : {}", ofcDistributionBasicInfo);
        RmcServiceCoverageForOrderVo rmcServiceCoverageForOrderVo = new RmcServiceCoverageForOrderVo();
        rmcServiceCoverageForOrderVo = this.copyDestinationPlace(ofcDistributionBasicInfo.getDeparturePlaceCode(), rmcServiceCoverageForOrderVo);
        RmcServiceCoverageForOrderVo rmcPickup = this.rmcServiceCoverageAPI(rmcServiceCoverageForOrderVo, "Pickup");
        this.updateOrderAreaAndBase(rmcPickup, ofcFundamentalInformation);
    }

    @Override
    public Wrapper storageOrderConfirm(OfcStorageImportDTO ofcStorageImportDTO, AuthResDto authResDto) {
        logger.info("仓储批量下单 ==> OfcStorageImportDTO:{}", ofcStorageImportDTO);
        logger.info("仓储批量下单 ==> authResDto:{}", authResDto);
        Map<String, List<OfcStorageTemplateDto>> orderMap = new HashMap<>();
        for (OfcStorageTemplateDto ofcStorageTemplateDto : ofcStorageImportDTO.getOrderList()) {
            if (null == ofcStorageTemplateDto) {
                logger.info("仓储开单批量导单确认下单, 订单信息为空! ");
                continue;
            }
            String custOrderCode = ofcStorageTemplateDto.getCustOrderCode();
            List<OfcStorageTemplateDto> orderListByCustOrderCode = orderMap.get(custOrderCode);
            if (!orderMap.containsKey(custOrderCode) && orderListByCustOrderCode == null) {
                logger.info("初始化");
                orderListByCustOrderCode = new ArrayList<>();
            }
            orderListByCustOrderCode.add(ofcStorageTemplateDto);
            orderMap.put(custOrderCode, orderListByCustOrderCode);
        }
        String orderBatchNumber = codeGenUtils.getNewWaterCode(BATCH_PRE,4);
        int index = 0;
        for (String orderMapKey : orderMap.keySet()) {
            List<OfcStorageTemplateDto> order = orderMap.get(orderMapKey);
            OfcOrderDTO ofcOrderDTO = new OfcOrderDTO();
            OfcStorageTemplateDto forOrderMsg = order.get(0);
            forOrderMsg.getOfcOrderDTO().setServiceProductCode(ofcStorageImportDTO.getServiceProductCode());
            forOrderMsg.getOfcOrderDTO().setServiceProductName(ofcStorageImportDTO.getServiceProductName());
            logger.info("forOrderMsg------, {}", ToStringBuilder.reflectionToString(forOrderMsg));
            org.springframework.beans.BeanUtils.copyProperties(forOrderMsg.getOfcOrderDTO(), ofcOrderDTO);
            org.springframework.beans.BeanUtils.copyProperties(forOrderMsg, ofcOrderDTO, "orderTime");
            ofcOrderDTO.setOrderTime(ofcStorageTemplateService.convertStringToDate(forOrderMsg.getOrderTime()));
            if (!PubUtils.isSEmptyOrNull(forOrderMsg.getProvideTransport())) {
                ofcOrderDTO.setProvideTransport(Integer.valueOf(forOrderMsg.getProvideTransport()));
            }
            if (!PubUtils.isSEmptyOrNull(forOrderMsg.getGroundDistribution())) {
                ofcOrderDTO.setGroundDistribution(forOrderMsg.getGroundDistribution());
            }
            logger.info("ofcOrderDTO------, {}", ToStringBuilder.reflectionToString(ofcOrderDTO));
            //在这里将订单信息补充完整
            ofcOrderDTO.setOrderBatchNumber(orderBatchNumber);
            ofcOrderDTO.setOrderType(WAREHOUSE_DIST_ORDER);
            if (ofcOrderDTO.getProvideTransport() == null) {
                ofcOrderDTO.setProvideTransport(0);
            } else if (1 == ofcOrderDTO.getProvideTransport()) {
                // 仓储带运输，默认签收回单，金额为0
                ofcOrderDTO.setReturnList("1");
                ofcOrderDTO.setReturnListFee(new BigDecimal(0));
            }
            List<OfcGoodsDetailsInfoDTO> ofcGoodsDetailsInfoDTOList = new ArrayList<>();
            for (OfcStorageTemplateDto ofcStorageTemplateDto : order) {
                OfcGoodsDetailsInfoDTO ofcGoodsDetailsInfo = ofcStorageTemplateService.convertCscGoods(ofcStorageTemplateDto);
                GoodsPackingDto goodsPackingDto = ofcStorageTemplateDto.getGoodsPackingDto();
                ofcGoodsDetailsInfo.setUnit(goodsPackingDto.getLevel());
                ofcGoodsDetailsInfo.setPackageName(goodsPackingDto.getLevelDescription());
                ofcGoodsDetailsInfo.setPackageType(goodsPackingDto.getLevel());
                ofcGoodsDetailsInfo.setWeight(ofcStorageTemplateDto.getWeight());
                ofcGoodsDetailsInfoDTOList.add(ofcGoodsDetailsInfo);
            }
            CscContantAndCompanyDto cscConsignorDto = ofcStorageTemplateService.convertCscConsignor(forOrderMsg.getConsignor());
            CscContantAndCompanyDto cscConsigneeDto = ofcStorageTemplateService.convertCscConsignee(forOrderMsg.getCscConsigneeDto());
            ofcStorageTemplateService.convertConsignorToDis(forOrderMsg.getConsignor(), ofcOrderDTO);
            ofcStorageTemplateService.convertConsigneeToDis(forOrderMsg.getCscConsigneeDto(), ofcOrderDTO);
            ofcStorageTemplateService.convertSupplierToWare(forOrderMsg.getCscSupplierInfoDto(), ofcOrderDTO);

            OfcSaveStorageDTO ofcSaveStorageDTO = this.convertToOfcSaveStorageDTO(ofcOrderDTO);
            //ofcOrderDTO, detailsInfos
            Wrapper save = this.saveStorageOrder(ofcSaveStorageDTO, ofcGoodsDetailsInfoDTOList, ORDER_TAG_STOCK_IMPORT
                    , cscConsignorDto, cscConsigneeDto, forOrderMsg.getCscSupplierInfoDto(), authResDto);
            if (save.getCode() == Wrapper.ERROR_CODE) {
                logger.error("仓储开单批量导单确认下单失败, 错误信息:{}", save.getMessage());
                return save;
            }
            index++ ;
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, orderBatchNumber);
    }

    /**
     * 通过收货地地址匹配基地
     * @param ofcDistributionBasicInfo 运输信息
     */
    @Override
    public boolean consigneeAdressIsCoverBase(OfcDistributionBasicInfo ofcDistributionBasicInfo) {
        RmcServiceCoverageForOrderVo rmcServiceCoverageForOrderVo = new RmcServiceCoverageForOrderVo();
        rmcServiceCoverageForOrderVo = this.copyDestinationPlace(ofcDistributionBasicInfo.getDestinationCode(), rmcServiceCoverageForOrderVo);
        RmcServiceCoverageForOrderVo rmcPickup = this.rmcServiceCoverageAPI(rmcServiceCoverageForOrderVo, "Pickup");
        return rmcPickup !=null && !PubUtils.isSEmptyOrNull(rmcPickup.getSerialNo());
    }

    private OfcSaveStorageDTO convertToOfcSaveStorageDTO(OfcOrderDTO ofcOrderDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);//严格模式
        OfcSaveStorageDTO ofcSaveStorageDTO = new OfcSaveStorageDTO();
        OfcFundamentalInformationDTO ofcFundamentalInformationDTO = modelMapper.map(ofcOrderDTO, OfcFundamentalInformationDTO.class);
        OfcWarehouseInformationDTO ofcWarehouseInformationDTO = modelMapper.map(ofcOrderDTO, OfcWarehouseInformationDTO.class);
        OfcDistributionBasicInfoDTO ofcDistributionBasicInfoDTO = modelMapper.map(ofcOrderDTO, OfcDistributionBasicInfoDTO.class);
        ofcSaveStorageDTO.setFundamentalInformation(ofcFundamentalInformationDTO);
        ofcSaveStorageDTO.setWarehouseInformation(ofcWarehouseInformationDTO);
        ofcSaveStorageDTO.setDistributionBasicInfo(ofcDistributionBasicInfoDTO);
        return ofcSaveStorageDTO;
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
        if ("Pickup".equals(trimAndNullAsEmpty(tag))) {
            rmcServiceCoverageForOrderVo.setIsPickup(1);
            rmcServiceCoverageForOrderVo.setIsDispatch(2);//取货不配送
            logger.info("#################################取货不配送,调用区域覆盖接口#######################");
        } else if ("TwoDistribution".equals(trimAndNullAsEmpty(tag))) {
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
            if (StringUtils.isNotEmpty(trimAndNullAsEmpty(address[0]))) {
                rmcServiceCoverageForOrderVo.setProvinceCode(address[0]);
            }
            if (address.length >= 2) {
                if (StringUtils.isNotEmpty(trimAndNullAsEmpty(address[1]))) {
                    rmcServiceCoverageForOrderVo.setCityCode(address[1]);
                }
                if (address.length >= 3) {
                    if (StringUtils.isNotEmpty(trimAndNullAsEmpty(address[2]))) {
                        rmcServiceCoverageForOrderVo.setDistrictCode(address[2]);
                    }
                    if (address.length == 4) {
                        if (StringUtils.isNotEmpty(trimAndNullAsEmpty(address[3]))) {
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
        logger.info("订单信息推送结算中心......");
        AcOrderDto acOrderDto = new AcOrderDto();
        try {
            // 转换Ac实体
            AcFundamentalInformation acFundamentalInformation = new AcFundamentalInformation();
            BeanUtils.copyProperties( ofcFundamentalInformation, acFundamentalInformation);
            AcFinanceInformation acFinanceInformation = new AcFinanceInformation();
            BeanUtils.copyProperties( ofcFinanceInformation, acFinanceInformation);
            AcDistributionBasicInfo acDistributionBasicInfo = new AcDistributionBasicInfo();
            BeanUtils.copyProperties( ofcDistributionBasicInfo, acDistributionBasicInfo);
            List<AcGoodsDetailsInfo> acGoodsDetailsInfoList = new ArrayList<>();
            for (OfcGoodsDetailsInfo ofcGoodsDetailsInfo : ofcGoodsDetailsInfos) {
                AcGoodsDetailsInfo acGoodsDetailsInfo = new AcGoodsDetailsInfo();
                BeanUtils.copyProperties(ofcGoodsDetailsInfo, acGoodsDetailsInfo);
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
                logger.info("订单中心推送结算中心结果: {}，订单号：{}", isSend, orderCode);
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
            OfcCustFundamentalInformation custFundamentalInformation = new OfcCustFundamentalInformation();
            org.springframework.beans.BeanUtils.copyProperties(ofcInfo, custFundamentalInformation);
            int updateOfCust = ofcCustFundamentalInformationService.update(custFundamentalInformation);
            if (updateOfCust == 0) {
                throw new BusinessException("更新订单的大区和基地失败!");
            }
        }
    }

    /**
     * 订单中心--订单状态推结算中心(执行中和已完成)
     *
     * @param ofcOrderStatus 订单状态
     * @return void
     */
    @Override
    public void pullOfcOrderStatus(OfcOrderStatus ofcOrderStatus) {
        logger.info("订单中心--订单状态推结算中心(执行中和已完成) ofcOrderStatus:{}", ofcOrderStatus);
        if (PubUtils.isNull(ofcOrderStatus)) {
            logger.error("订单状态推结算中心异常");
            throw new BusinessException("订单状态推结算中心异常");
        }
        AcOrderStatusDto acOrderStatusDto = new AcOrderStatusDto();
        logger.info("订单状态开始推结算中心 acOrderStatusDto{}", acOrderStatusDto);
        try {
            BeanUtils.copyProperties(ofcOrderStatus, acOrderStatusDto);
        } catch (Exception e) {
            logger.error("订单状态开始推结算中心 实体转换异常");
            throw new BusinessException("订单状态开始推结算中心 实体转换异常");
        }
        BeanUtils.copyProperties(ofcOrderStatus, acOrderStatusDto);

        try {
            Wrapper<Integer> integerWrapper = acOrderEdasService.pullOfcOrderStatus(acOrderStatusDto);
            if (null == integerWrapper || integerWrapper.getCode() != Wrapper.SUCCESS_CODE) {
                logger.error("订单中心--订单状态推结算中心(执行中和已完成), AC返回结果异常, {}"
                        , integerWrapper == null ? "integerWrapper 为null" : integerWrapper.getMessage());
            }
            logger.info("订单状态开始推结算中心成功 integerWrapper{}", integerWrapper);
        } catch (Exception e) {
            logger.error("订单中心--订单状态推结算中心(执行中和已完成) 异常, {}", e, e.getMessage());
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public Wrapper<?> validateStockCount(List<OfcGoodsDetailsInfo> goodsDetailsList, String custCode, String warehouseCode) {
        int count = 0;
        List<InventoryDTO> inventoryGoods = new ArrayList<>();
        //相同货品编码数量相加
        Map<String,OfcGoodsDetailsInfo> goodInfo=new HashMap<>();
        for (OfcGoodsDetailsInfo ofcGoodsDetails : goodsDetailsList) {
            String goodsCode=ofcGoodsDetails.getGoodsCode();
            if (goodInfo.containsKey(goodsCode)) {
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
            if (null != productionTime) {
                inventoryDTO.setLotatt01(DateUtils.Date2String(productionTime, DateUtils.DateFormatType.TYPE2));
            }
            if (null != invalidTime) {
                inventoryDTO.setLotatt02(DateUtils.Date2String(ofcGoodsDetailsInfo.getInvalidTime(), DateUtils.DateFormatType.TYPE2));
            }
            inventoryDTO.setQuantity(ofcGoodsDetailsInfo.getQuantity());
            inventoryGoods.add(inventoryDTO);
        }
        return whcOrderCancelEdasService.validateStockCount(inventoryGoods);
    }


    /**
     * 生成仓储订单
     * @param ofcOrderDTO                      订单信息
     * @param goodsDetailsListDTO                 货品信息
     * @param reviewTag                        操作标志位
     * @param cscContantAndCompanyDtoConsignor 发货方信息
     * @param cscContantAndCompanyDtoConsignee 收货方信息
     * @param cscSupplierInfoDto               供应商信息
     * @param authResDtoByToken 登录用户
     * @return 操作结果
     */
    @Override
    @SuppressWarnings("unchecked")
    public Wrapper<?> saveStorageOrder(OfcSaveStorageDTO ofcOrderDTO, List<OfcGoodsDetailsInfoDTO> goodsDetailsListDTO, String reviewTag
            , CscContantAndCompanyDto cscContantAndCompanyDtoConsignor, CscContantAndCompanyDto cscContantAndCompanyDtoConsignee
            , CscSupplierInfoDto cscSupplierInfoDto, AuthResDto authResDtoByToken) {
        logger.info("开始创建仓储订单 ofcOrderDTO:{}, goodsDetailsList:{}, reviewTag:{}, cscContantAndCompanyDtoConsignor:{}" +
                        ", cscContantAndCompanyDtoConsignee:{}, cscSupplierInfoDto:{}, authResDtoByToken:{}", ofcOrderDTO, goodsDetailsListDTO
                , reviewTag, cscContantAndCompanyDtoConsignor, cscContantAndCompanyDtoConsignee, cscSupplierInfoDto, authResDtoByToken);
        OfcFundamentalInformation ofcFundamentalInformation = modelMapper.map(ofcOrderDTO.getFundamentalInformation(), OfcFundamentalInformation.class);
        OfcWarehouseInformation ofcWarehouseInformation = modelMapper.map(ofcOrderDTO.getWarehouseInformation(), OfcWarehouseInformation.class);
        if (null == ofcFundamentalInformation || null == ofcWarehouseInformation) {
            logger.error("创建仓储订单失败,saveStorageOrder转换的某个实体为空" +
                            ", ofcFundamentalInformation:{}, ofcWarehouseInformation:{}:{}"
                    , ofcFundamentalInformation, ofcWarehouseInformation);
            throw new BusinessException("创建仓储订单失败!");
        }
        OfcDistributionBasicInfo ofcDistributionBasicInfo = modelMapper.map(ofcOrderDTO.getDistributionBasicInfo(), OfcDistributionBasicInfo.class);
        OfcFinanceInformation ofcFinanceInformation = modelMapper.map(ofcOrderDTO, OfcFinanceInformation.class);
        /**取供应商的名称**/
        getSupportName(ofcFundamentalInformation, ofcWarehouseInformation);
        List<OfcGoodsDetailsInfo> goodsDetailsList = new ArrayList<>();
        for(OfcGoodsDetailsInfoDTO  dto: goodsDetailsListDTO){
            OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
            org.springframework.beans.BeanUtils.copyProperties(dto,ofcGoodsDetailsInfo);
            goodsDetailsList.add(ofcGoodsDetailsInfo);
        }
        if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_SAVE) || trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_IMPORT) || StringUtils.equals(ORDER_TAG_STOCK_CUST_SAVE, reviewTag)) {
            ofcFundamentalInformation.setOrderCode(codeGenUtils.getNewWaterCode(ORDER_PRE, 6));
        }
        /**
         * 出库业务没有仓库时匹配仓储中心推荐的仓库 需求号  1322
         */
        if (ofcFundamentalInformation.getBusinessType().contains("62")) {
            if(PublicUtil.isEmpty(ofcWarehouseInformation.getWarehouseCode())){
                matchWarehouse(ofcFundamentalInformation,ofcDistributionBasicInfo,ofcWarehouseInformation,goodsDetailsList);
            }
        }

        OfcMerchandiser ofcMerchandiser = new OfcMerchandiser();
        ofcMerchandiser.setMerchandiser(ofcOrderDTO.getMerchandiser());
        if(PublicUtil.isEmpty(ofcWarehouseInformation.getWarehouseCode())){
            throw new BusinessException("仓库编码不能为空!");
        }
        if (null == ofcWarehouseInformation.getProvideTransport()) {
            logger.error("ofcWarehouseInformation.getProvideTransport为空");
            throw new BusinessException("创建仓储订单失败!");
        }


        //订单的基本信息
        ofcFundamentalInformation.setCreationTime(new Date());
        ofcFundamentalInformation.setCreator(authResDtoByToken.getUserId());
        ofcFundamentalInformation.setCreatorName(authResDtoByToken.getUserName());
        ofcFundamentalInformation.setOperator(authResDtoByToken.getUserId());
        ofcFundamentalInformation.setOperatorName(authResDtoByToken.getUserName());
        ofcFundamentalInformation.setOrderType(WAREHOUSE_DIST_ORDER);

        //校验当前登录用户的身份信息,并存放大区和基地信息
        //ofcOrderPlaceService.orderAuthByConsignorAddr(authResDtoByToken, ofcDistributionBasicInfo, ofcFundamentalInformation);
        ofcFundamentalInformation.setOperTime(new Date());
        OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
//        ofcFundamentalInformation.setStoreName(ofcOrderDTO.getStoreName());//店铺还没维护表
        ofcFundamentalInformation.setOrderSource("手动");//订单来源

        /**校验客户订单号**/
        validateCustOrderCode(reviewTag, ofcFundamentalInformation);
        /** 校验客户是否有仓储合同**/
        validateCustomerContract(ofcFundamentalInformation);
        String businessTypePrefix = ofcFundamentalInformation.getBusinessType().substring(0,2);
        if (businessTypePrefix.equals(STOCK_OUT_ORDER) && ORDER_TAG_STOCK_SAVE.equals(reviewTag)) {
           validateCustomerIsLocked(ofcFundamentalInformation);
        }

        StringBuilder notes = new StringBuilder();
        if (ofcWarehouseInformation.getProvideTransport().equals(YES)) {
            ofcFundamentalInformation.setTransportType("10");//10 零担 20整车
            // 带运输仓储单默认签收回单，费用为0
            ofcFinanceInformation.setReturnList("1");
            ofcFinanceInformation.setReturnListFee(new BigDecimal(0));
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

        //未作废
        ofcFundamentalInformation.setAbolishMark(ORDER_WASNOT_ABOLISHED);
        //货品数量
        BigDecimal goodsAmountCount = new BigDecimal(0);
        //保存货品明细
        String orderCode = ofcFundamentalInformation.getOrderCode();
        if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_EDIT)) {
            ofcGoodsDetailsInfoService.deleteAllByOrderCode(orderCode);
        }
        if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_CUST_EDIT)) {
            ofcCustGoodsDetailsInfoService.deleteByKey(orderCode);
        }

        //相同货品编码数量相加
        Map<String,OfcGoodsDetailsInfo> goodInfo = new HashMap<>();
        List<OfcGoodsDetailsInfo> ofcGoodsDetail = new ArrayList<>();
        /**计算订单货品总重量**/
        BigDecimal totalWeight = accountTotalWeight(goodsDetailsList, goodInfo);
        ofcDistributionBasicInfo.setWeight(totalWeight.setScale(3, BigDecimal.ROUND_HALF_UP));
        Iterator iter = goodInfo.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String,OfcGoodsDetailsInfo> entry= (Map.Entry<String, OfcGoodsDetailsInfo>) iter.next();
            OfcGoodsDetailsInfo ofcGoodsDetails=entry.getValue();
            if (ofcGoodsDetails.getQuantity() == null || ofcGoodsDetails.getQuantity().compareTo(new BigDecimal(0)) == 0) {
                continue;
            }
            ofcGoodsDetails.setOrderCode(orderCode);
            ofcGoodsDetails.setCreationTime(ofcFundamentalInformation.getCreationTime());
            ofcGoodsDetails.setCreator(ofcFundamentalInformation.getCreator());
            ofcGoodsDetails.setOperator(ofcFundamentalInformation.getOperator());
            ofcGoodsDetails.setOperTime(ofcFundamentalInformation.getOperTime());
            goodsAmountCount = goodsAmountCount.add(ofcGoodsDetails.getQuantity());
            ofcGoodsDetails.setPaasLineNo(codeGenUtils.getPaasLineNo(PAAS_LINE_NO));
            ofcGoodsDetail.add(ofcGoodsDetails);
            ofcGoodsDetailsInfoService.fillGoodType(ofcGoodsDetails);
            if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_CUST_SAVE) || trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_CUST_EDIT)) {
                ofcCustGoodsDetailsInfoService.save((OfcCustGoodsDetailsInfo) ofcGoodsDetails);
            } else {
                ofcGoodsDetailsInfoService.save(ofcGoodsDetails);
            }
     }

        if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_SAVE) || trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_IMPORT) || StringUtils.equals(ORDER_TAG_CUST_STOCK, reviewTag)) {
            //添加基本信息
            ofcFundamentalInformationService.save(ofcFundamentalInformation);
        } else if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_EDIT)) {
            ofcFundamentalInformationService.update(ofcFundamentalInformation);
        } else if (StringUtils.equals(ORDER_TAG_STOCK_CUST_SAVE, reviewTag)) {
            ofcCustFundamentalInformationService.save((OfcCustFundamentalInformation)BeanConvertor.objConvertor(ofcFundamentalInformation, new OfcCustFundamentalInformation()));
            // 客户工作台编辑
        } else if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_CUST_EDIT)) {
            ofcCustFundamentalInformationService.update((OfcCustFundamentalInformation) BeanConvertor.objConvertor(ofcFundamentalInformation, new OfcCustFundamentalInformation()));
        }
        ofcOrderPlaceService.updateBaseAndAreaBywarehouseCode(ofcWarehouseInformation.getWarehouseCode(),ofcFundamentalInformation);

        if (null == ofcWarehouseInformation.getProvideTransport()) {
            ofcWarehouseInformation.setProvideTransport(WAREHOUSE_NO_TRANS);
        }

        OfcDistributionBasicInfo dinfo;
        OfcDistributionBasicInfo condition = new OfcDistributionBasicInfo();
        condition.setOrderCode(ofcFundamentalInformation.getOrderCode());
        dinfo = ofcDistributionBasicInfoService.selectOne(condition);

        ofcDistributionBasicInfo = ofcDistributionBasicInfoService.fillAddress(ofcWarehouseInformation,ofcDistributionBasicInfo,ofcFundamentalInformation);
        /**
         * 校验收发货方的地址
         */
        validateAddress(ofcWarehouseInformation, ofcDistributionBasicInfo, businessTypePrefix);

        //落地配的需求  需求号834 modified by hujt 2017/8/8
        if (StringUtils.equals(ofcFundamentalInformation.getGroundDistribution(), IS_NEED_GROUND_DISTRIBUTION)) {
            boolean isCover = this.consigneeAdressIsCoverBase(ofcDistributionBasicInfo);
            if (!isCover) {
                throw new BusinessException("落地配订单收货地无基地");
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

        if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_SAVE) || trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_IMPORT) || StringUtils.equals(ORDER_TAG_CUST_STOCK, reviewTag)) {
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
        } else if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_CUST_EDIT)) {
            OfcCustDistributionBasicInfo custDistributionBasicInfo = new OfcCustDistributionBasicInfo();
            org.springframework.beans.BeanUtils.copyProperties(ofcDistributionBasicInfo, custDistributionBasicInfo);
            ofcDistributionBasicInfo.setQuantity(goodsAmountCount);
            ofcDistributionBasicInfo.setCreationTime(ofcFundamentalInformation.getCreationTime());
            ofcDistributionBasicInfo.setCreator(ofcFundamentalInformation.getCreator());
            ofcDistributionBasicInfo.setOrderCode(ofcFundamentalInformation.getOrderCode());
            ofcDistributionBasicInfo.setOperator(ofcFundamentalInformation.getOperator());
            ofcDistributionBasicInfo.setOperTime(ofcFundamentalInformation.getOperTime());
            ofcCustDistributionBasicInfoService.update(custDistributionBasicInfo);
        } else if (StringUtils.equals(ORDER_TAG_STOCK_CUST_SAVE, reviewTag)) {
            OfcCustDistributionBasicInfo custDistributionBasicInfo = new OfcCustDistributionBasicInfo();
            org.springframework.beans.BeanUtils.copyProperties(ofcDistributionBasicInfo, custDistributionBasicInfo);
            ofcCustDistributionBasicInfoService.save(custDistributionBasicInfo);
        }

        // 服务费用
        ofcFinanceInformation.setOrderCode(ofcFundamentalInformation.getOrderCode());
        ofcFinanceInformation.setOperTime(ofcFundamentalInformation.getOperTime());
        //仓储信息
        ofcWarehouseInformation.setOrderCode(ofcFundamentalInformation.getOrderCode());
        ofcWarehouseInformation.setCreationTime(ofcFundamentalInformation.getCreationTime());
        ofcWarehouseInformation.setCreator(ofcFundamentalInformation.getCreator());
        ofcWarehouseInformation.setOperTime(ofcFundamentalInformation.getOperTime());
        ofcWarehouseInformation.setOperator(ofcFundamentalInformation.getOperator());
        if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_SAVE) || trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_IMPORT) || StringUtils.equals(ORDER_TAG_CUST_STOCK, reviewTag)) {
            ofcWarehouseInformationService.save(ofcWarehouseInformation);
            ofcFinanceInformationService.save(ofcFinanceInformation);
        } else if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_EDIT)) {
            ofcWarehouseInformationService.updateByOrderCode(ofcWarehouseInformation);
            ofcFinanceInformationService.updateByOrderCode(ofcFinanceInformation);
        } else if (StringUtils.equals(ORDER_TAG_STOCK_CUST_SAVE, reviewTag)) {
            OfcCustWarehouseInformation custWarehouseInformation = new OfcCustWarehouseInformation();
            org.springframework.beans.BeanUtils.copyProperties(ofcWarehouseInformation, custWarehouseInformation);
            ofcCustWarehouseInformationService.save(custWarehouseInformation);
            OfcCustFinanceInformation custFinanceInformation = new OfcCustFinanceInformation();
            org.springframework.beans.BeanUtils.copyProperties(ofcFinanceInformation, custFinanceInformation);
            ofcCustFinanceInformationService.save(custFinanceInformation);
        } else if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_CUST_EDIT)) {
            OfcCustWarehouseInformation custWarehouseInformation = new OfcCustWarehouseInformation();
            org.springframework.beans.BeanUtils.copyProperties(ofcWarehouseInformation, custWarehouseInformation);
            ofcCustWarehouseInformationService.update(custWarehouseInformation);
            OfcCustFinanceInformation custFinanceInformation = new OfcCustFinanceInformation();
            org.springframework.beans.BeanUtils.copyProperties(ofcFinanceInformation, custFinanceInformation);
            ofcCustFinanceInformationService.update(custFinanceInformation);
        }


        //添加开单员
        if (ofcMerchandiserService.select(ofcMerchandiser).size() == 0
                && org.apache.commons.lang3.StringUtils.isNotEmpty(trimAndNullAsEmpty(ofcMerchandiser.getMerchandiser()))) {
            ofcMerchandiserService.save(ofcMerchandiser);
        }
        if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_SAVE) || trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_IMPORT)
                || StringUtils.equals(ORDER_TAG_STOCK_CUST_SAVE, reviewTag) || StringUtils.equals(ORDER_TAG_CUST_STOCK, reviewTag)) {
            //保存订单日志
            notes.append(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1));
            notes.append(" 订单已创建");
            notes.append(" 操作人: ").append(authResDtoByToken.getUserName());
            notes.append(" 操作单位: ").append(authResDtoByToken.getGroupRefName());
            ofcOrderStatus.setNotes(notes.toString());
            ofcOrderStatus.setOrderCode(ofcFundamentalInformation.getOrderCode());
            ofcOrderStatus.setOrderStatus(OrderStatusEnum.PEND_AUDIT.getCode());
            ofcOrderStatus.setStatusDesc(OrderStatusEnum.PEND_AUDIT.getDesc());
            ofcOrderStatus.setTrace("接收订单");
            ofcOrderStatus.setTraceStatus(OrderStatusEnum.PEND_AUDIT.getCode());
            ofcOrderStatus.setLastedOperTime(new Date());
            ofcOrderStatus.setOperator(authResDtoByToken.getUserName());
            if (StringUtils.equals(ORDER_TAG_STOCK_CUST_SAVE, reviewTag)) {
                ofcCustOrderStatusService.saveOrderStatus((OfcCustOrderStatus) ofcOrderStatus);
            } else {
                ofcOrderStatusService.save(ofcOrderStatus);
            }
        } else if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_EDIT)) {
            notes.append(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1));
            notes.append(" 订单已更新");
            notes.append(" 操作人: ").append(authResDtoByToken.getUserName());
            notes.append(" 操作单位: ").append(authResDtoByToken.getGroupRefName());
            ofcOrderStatus.setNotes(notes.toString());
            ofcOrderStatus.setOrderCode(ofcFundamentalInformation.getOrderCode());
            ofcOrderStatus.setOrderStatus(OrderStatusEnum.PEND_AUDIT.getCode());
            ofcOrderStatus.setStatusDesc(OrderStatusEnum.PEND_AUDIT.getDesc());
            ofcOrderStatus.setTrace("接收订单");
            ofcOrderStatus.setTraceStatus(OrderStatusEnum.PEND_AUDIT.getCode());
            ofcOrderStatus.setLastedOperTime(new Date());
            ofcOrderStatus.setOperator(authResDtoByToken.getUserName());
            ofcOrderStatusService.save(ofcOrderStatus);
        }
        if (StringUtils.equals(ORDER_TAG_STOCK_CUST_SAVE, reviewTag)) {
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE);
        }
        logger.info("自动审核. 订单批次号信息:{}", ofcFundamentalInformation.getOrderBatchNumber());
        //普通手录订单直接调用自动审核, 批量导入订单另起自动审核.
        if (isSEmptyOrNull(ofcFundamentalInformation.getOrderBatchNumber())) {
            //调用自动审核
            this.orderAutoAudit(ofcFundamentalInformation, ofcGoodsDetail, ofcDistributionBasicInfo, ofcWarehouseInformation
                    , ofcFinanceInformation, ofcOrderStatus.getOrderStatus(), REVIEW, authResDtoByToken);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE);
    }

    private void validateAddress(OfcWarehouseInformation ofcWarehouseInformation, OfcDistributionBasicInfo ofcDistributionBasicInfo, String businessTypePrefix) {
        Wrapper<?> wrapper;
        if (YES.equals(ofcWarehouseInformation.getProvideTransport())) {
            //收货方、发货方校验
            wrapper = validateDistrictContactMessage(ofcDistributionBasicInfo);
            if (Wrapper.ERROR_CODE == wrapper.getCode()) {
                throw new BusinessException(wrapper.getMessage());
            }
        } else if (STOCK_OUT_ORDER.equals(trimAndNullAsEmpty(businessTypePrefix))) {
            wrapper = validateDistrictContactMessage(ofcDistributionBasicInfo);
            if (Wrapper.ERROR_CODE == wrapper.getCode()) {
                throw new BusinessException(wrapper.getMessage());
            }
        }
    }

    private BigDecimal accountTotalWeight(List<OfcGoodsDetailsInfo> goodsDetailsList, Map<String, OfcGoodsDetailsInfo> goodInfo) {
        BigDecimal totalWeight = new BigDecimal(0.0);
        for (OfcGoodsDetailsInfo ofcGoodsDetails : goodsDetailsList) {
            StringBuilder key=new StringBuilder();
            key.append(ofcGoodsDetails.getGoodsCode());
            key.append(ofcGoodsDetails.getPackageType());
            if (!StringUtils.isEmpty(ofcGoodsDetails.getSupportBatch())) {
                key.append(ofcGoodsDetails.getSupportBatch());
            }
            if (!StringUtils.isEmpty(ofcGoodsDetails.getProductionBatch())) {
                key.append(ofcGoodsDetails.getProductionBatch());
            }
            if(ofcGoodsDetails.getProductionTime()!=null){
                key.append(DateUtils.Date2String(ofcGoodsDetails.getProductionTime(), DateUtils.DateFormatType.TYPE1));
            }
            if (ofcGoodsDetails.getInvalidTime() != null) {
                key.append(DateUtils.Date2String(ofcGoodsDetails.getInvalidTime(), DateUtils.DateFormatType.TYPE1));
            }
            if (!goodInfo.containsKey(key.toString())) {
                goodInfo.put(key.toString(),ofcGoodsDetails);
            }else{
                OfcGoodsDetailsInfo info=goodInfo.get(key.toString());
                info.setPrimaryQuantity(info.getPrimaryQuantity().add(ofcGoodsDetails.getPrimaryQuantity()));
                info.setQuantity(info.getQuantity().add(ofcGoodsDetails.getQuantity()));
                BigDecimal undealWeight = null == ofcGoodsDetails.getWeight() ? new BigDecimal(0) : ofcGoodsDetails.getWeight();
                BigDecimal preWeight = info.getWeight();
                BigDecimal infoWeight = null == preWeight ? undealWeight : preWeight.add(undealWeight);
                info.setWeight(infoWeight.setScale(3, BigDecimal.ROUND_HALF_UP));
            }
            totalWeight = totalWeight.add(null == ofcGoodsDetails.getWeight() ? new BigDecimal(0) : ofcGoodsDetails.getWeight());
        }
        return totalWeight;
    }

    private void validateCustOrderCode(String reviewTag, OfcFundamentalInformation ofcFundamentalInformation) {
        int custOrderCode = 0;
        if (!isSEmptyOrNull(ofcFundamentalInformation.getCustOrderCode()) && !StringUtils.equals(ORDER_TAG_CUST_STOCK, reviewTag)) { // 订单确认不需要再次校验客户订单号
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
    }

    private void getSupportName(OfcFundamentalInformation ofcFundamentalInformation, OfcWarehouseInformation ofcWarehouseInformation) {
        if(!PubUtils.isSEmptyOrNull(ofcWarehouseInformation.getSupportCode())){
            CscSupplierInfoDto dto = new CscSupplierInfoDto();
            dto.setCustomerCode(ofcFundamentalInformation.getCustCode());
            dto.setSupplierCode(ofcWarehouseInformation.getSupportCode());
            List<CscSupplierInfoDto> supplierListInfo = this.getSupplierInfo(dto);
            if (CollectionUtils.isEmpty(supplierListInfo)) {
                logger.error("供应商信息有误");
                throw new BusinessException("供应商信息有误");
            }
            ofcWarehouseInformation.setSupportName(supplierListInfo.get(0).getSupplierName());
        }
    }

    /**
     * 编辑后将地址不完整的订单的地址信息补充完整
     * @param reviewTag 标志位
     * @param ofcDistributionBasicInfo 运输信息
     */
    @Override
    public void fixAddressWhenEdit(String reviewTag, OfcDistributionBasicInfo ofcDistributionBasicInfo) {
        logger.info("编辑后将地址不完整的订单的地址信息补充完整, reviewTag : {}", reviewTag);
        logger.info("编辑后将地址不完整的订单的地址信息补充完整, ofcDistributionBasicInfo : {}", ofcDistributionBasicInfo);
        if (trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_EDIT) || trimAndNullAsEmpty(reviewTag).equals(ORDER_TAG_STOCK_CUST_EDIT)) {
            String departurePlace = ofcDistributionBasicInfo.getDeparturePlace();
            String destination = ofcDistributionBasicInfo.getDestination();
            if (!PubUtils.isSEmptyOrNull(departurePlace)) {
                OfcAddressReflect ofcAddressReflect = ofcAddressReflectService.selectByAddress(departurePlace);
                logger.info("编辑后将地址不完整的订单的地址信息补充完整, ofcAddressReflect : {}", ofcAddressReflect);
                if (null != ofcAddressReflect && PubUtils.isSEmptyOrNull(ofcAddressReflect.getProvince())) {
                    ofcAddressReflectService.reflectAddressToRef(ofcAddressReflect, ofcDistributionBasicInfo, "departure");
                    int update = ofcAddressReflectMapper.updateByAddress(ofcAddressReflect);
                    if (update < 1) {
                        logger.error("更新出发明细地址映射失败!");
//                        throw new BusinessException("更新出发明细地址映射失败!");
                    }
                }
            }
            if (!PubUtils.isSEmptyOrNull(destination)) {
                OfcAddressReflect ofcAddressReflect = ofcAddressReflectService.selectByAddress(destination);
                logger.info("编辑后将地址不完整的订单的地址信息补充完整, ofcAddressReflect : {}", ofcAddressReflect);
                if (null != ofcAddressReflect && PubUtils.isSEmptyOrNull(ofcAddressReflect.getProvince())) {
                    ofcAddressReflectService.reflectAddressToRef(ofcAddressReflect, ofcDistributionBasicInfo, "destination");
                    int update = ofcAddressReflectMapper.updateByAddress(ofcAddressReflect);
                    if (update < 1) {
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
     * @return 返回值
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
        /**
         *  金融中心锁定客户不允许出库，追加逻辑
         */
        String orderBusinessType = ofcFundamentalInformation.getBusinessType().substring(0, 2);
        if (orderBusinessType.equals(TRACE_STATUS_5)){
            validateCustomerIsLocked(ofcFundamentalInformation);
        }
        /**
         * 仓储无合同客户不允许下单 需求号:1357
         */
        validateCustomerContract(ofcFundamentalInformation);
        try {
            BeanUtils.copyProperties(ofcFundamentalInformation,newofcFundamentalInformation);
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
            s.setTrace(PEND_AUDIT.getDesc());
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
            newofcFundamentalInformation.setOrderTime(new Date());
            newofcFundamentalInformation.setOrderSource("手动录入");
            newofcFundamentalInformation.setCreator(authResDtoByToken.getUserName());
            newofcFundamentalInformation.setAreaCode(ofcFundamentalInformation.getAreaCode());
            newofcFundamentalInformation.setAreaName(ofcFundamentalInformation.getAreaName());
            newofcFundamentalInformation.setBaseCode(ofcFundamentalInformation.getBaseCode());
            newofcFundamentalInformation.setBaseName(ofcFundamentalInformation.getBaseName());
            ofcFundamentalInformationService.save(newofcFundamentalInformation);

            //复制仓储的信息
            OfcWarehouseInformation cw = new OfcWarehouseInformation();
            cw.setOrderCode(orderCode);
            OfcWarehouseInformation owinfo = ofcWarehouseInformationService.selectOne(cw);
            if (owinfo != null) {
                OfcWarehouseInformation ofcnewWarehouseInformation = new OfcWarehouseInformation();
                BeanUtils.copyProperties( owinfo, ofcnewWarehouseInformation);
                ofcnewWarehouseInformation.setOrderCode(newofcFundamentalInformation.getOrderCode());
                ofcWarehouseInformationService.save(ofcnewWarehouseInformation);

                //提供运输时 复制配送信息
                OfcDistributionBasicInfo f = new OfcDistributionBasicInfo();
                f.setOrderCode(ofcFundamentalInformation.getOrderCode());
                OfcDistributionBasicInfo BasicInfo = ofcDistributionBasicInfoService.selectOne(f);
                if (BasicInfo != null) {
                    OfcDistributionBasicInfo newofcDistributionBasicInfo = new OfcDistributionBasicInfo();
                    BeanUtils.copyProperties( BasicInfo, newofcDistributionBasicInfo);
                    newofcDistributionBasicInfo.setOrderCode(newofcFundamentalInformation.getOrderCode());
                    if (owinfo != null) {
                        if (Objects.equals(owinfo.getProvideTransport(), YES)) {
                            newofcDistributionBasicInfo.setTransCode(newofcFundamentalInformation.getOrderCode());
                        }
                    }
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
                    BeanUtils.copyProperties( info,newGoodsInfo);
                    newGoodsInfo.setId(UUID.randomUUID().toString().replace("-", ""));
                    newGoodsInfo.setOrderCode(newofcFundamentalInformation.getOrderCode());
                    newGoodsInfo.setPaasLineNo(codeGenUtils.getPaasLineNo(PAAS_LINE_NO));
                    ofcGoodsDetailsInfoService.fillGoodType(newGoodsInfo);
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
     * 校验客户是否被金融中心锁定
     * @param ofcFundamentalInformation
     */
    @Override
    public void validateCustomerIsLocked(OfcFundamentalInformation ofcFundamentalInformation) {
        String orderCode = ofcFundamentalInformation.getOrderCode();
        String customerCode = ofcFundamentalInformation.getCustCode();
        QueryCustomerCodeDto queryCustomerCodeDto = new QueryCustomerCodeDto();
        queryCustomerCodeDto.setCustomerCode(customerCode);
        Wrapper<CscCustomerVo> customerVoWrapper = cscCustomerEdasService.queryCustomerByCustomerCodeDto(queryCustomerCodeDto);
        if (Wrapper.ERROR_CODE == customerVoWrapper.getCode()) {
            throw new BusinessException("校验客户锁定状态时出现异常");
        }
        String customerStatus = customerVoWrapper.getResult().getCustomerStatus();
        if (!PubUtils.isSEmptyOrNull(customerStatus) && "1".equals(customerStatus)){
            throw new BusinessException("订单编号[" + orderCode + "]不能执行复制，此订单客户被金融中心锁定，辛苦联系金融中心同事！");
        }
    }

    private void validateCustomerContract(OfcFundamentalInformation ofcFundamentalInformation){
        CscContractProEdasDto dto = new CscContractProEdasDto();
        dto.setCustomerCode(ofcFundamentalInformation.getCustCode());
        dto.setProductCatlogCode("warehouse,delivery");
        boolean isHaveContract =  validateCustomerIsHaveContract(dto);
        if (!isHaveContract) {
            throw new BusinessException("该客户没有对应的合同，辛苦联系营销中心同事");
        }
    }

    /**
     * 没有仓库匹配仓库
     * @param ofcFundamentalInformation
     * @param ofcWarehouseInformation
     * @param goodsDetailsInfos
     */
    @Override
    public void matchWarehouse(OfcFundamentalInformation ofcFundamentalInformation,OfcDistributionBasicInfo ofcDistributionBasicInfo,OfcWarehouseInformation ofcWarehouseInformation,List<OfcGoodsDetailsInfo> goodsDetailsInfos){
        logger.info("没有仓库时开始匹配仓储中心推荐的仓库，订单号为:{}",ofcFundamentalInformation.getOrderCode());
        WhcDeliveryDTO dto = new WhcDeliveryDTO();
        dto.setCustomerCode(ofcFundamentalInformation.getCustCode());
        dto.setCustomerName(ofcFundamentalInformation.getCustName());
        dto.setCProvince(ofcDistributionBasicInfo.getDestinationProvince());
        dto.setCCity(ofcDistributionBasicInfo.getDestinationCity());
        dto.setCDistrict(ofcDistributionBasicInfo.getDestinationDistrict());
        dto.setCStreet(ofcDistributionBasicInfo.getDestinationTowns());
        dto.setOrderCode(ofcFundamentalInformation.getOrderCode());
        List<WhcDeliveryDetailsDTO> goodDetail = new ArrayList<>();
        for (OfcGoodsDetailsInfo good :goodsDetailsInfos) {
            WhcDeliveryDetailsDTO dd = new WhcDeliveryDetailsDTO();
            dd.setItemCode(good.getGoodsCode());
            dd.setItemName(good.getGoodsName());
            dd.setPrimaryQuantity(good.getPrimaryQuantity());
            goodDetail.add(dd);
        }
        dto.setDetailsList(goodDetail);
        WareHouseDTO warehouse = ofcWarehouseInformationService.matchWareHouse(dto);
        if (warehouse != null) {
            ofcWarehouseInformation.setWarehouseName(warehouse.getWareHouseName());
            ofcWarehouseInformation.setWarehouseCode(warehouse.getWareHouseCode());
        }
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

        List<OfcGoodsDetailsInfo> goodsDetailsInfo = new ArrayList<>();
        String specialCust = "";
        OfcEnumeration ofcEnumeration = new OfcEnumeration();
        ofcEnumeration.setEnumSys("OFC");
        ofcEnumeration.setEnumType("SpecialCustEnum");
        ofcEnumeration.setEnumName("大成万达（天津）有限公司");
        List<OfcEnumeration> enumerations = ofcEnumerationService.queryOfcEnumerationList(ofcEnumeration);
        if (!CollectionUtils.isEmpty(enumerations)) {
            specialCust = enumerations.get(0).getEnumValue();
        }
        //纬度   货品编码、供应商批次、生产日期、失效日期不同包装合并主单位数量 仓储订单
        if(trimAndNullAsEmpty(ofcFundamentalInformation.getOrderType()).equals(WAREHOUSE_DIST_ORDER) && !StringUtils.equals(ofcFundamentalInformation.getCustCode(),specialCust)){
            Map<String,OfcGoodsDetailsInfo> goodInfo=new HashMap<>();
            for (OfcGoodsDetailsInfo ofcGoodsDetails : goodsDetailsList) {
                StringBuilder key=new StringBuilder();
                key.append(ofcGoodsDetails.getGoodsCode());
                if (!StringUtils.isEmpty(ofcGoodsDetails.getSupportBatch())) {
                    key.append(ofcGoodsDetails.getSupportBatch());
                }
                if (!StringUtils.isEmpty(ofcGoodsDetails.getProductionBatch())) {
                    key.append(ofcGoodsDetails.getProductionBatch());
                }
                if (ofcGoodsDetails.getProductionTime() != null) {
                    key.append(DateUtils.Date2String(ofcGoodsDetails.getProductionTime(), DateUtils.DateFormatType.TYPE1));
                }
                if (ofcGoodsDetails.getInvalidTime() != null) {
                    key.append(DateUtils.Date2String(ofcGoodsDetails.getInvalidTime(), DateUtils.DateFormatType.TYPE1));
                }
                if (!goodInfo.containsKey(key.toString())) {
                    goodInfo.put(key.toString(),ofcGoodsDetails);
                }else{
                    OfcGoodsDetailsInfo info=goodInfo.get(key.toString());
                    info.setPrimaryQuantity(info.getPrimaryQuantity().add(ofcGoodsDetails.getPrimaryQuantity()));
                }
            }

            Iterator iter = goodInfo.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, OfcGoodsDetailsInfo> entry = (Map.Entry<String, OfcGoodsDetailsInfo>) iter.next();
                OfcGoodsDetailsInfo ofcGoodsDetails = entry.getValue();
                if (ofcGoodsDetails.getPaasLineNo() == null) {
                    ofcGoodsDetails.setPaasLineNo(codeGenUtils.getPaasLineNo(PAAS_LINE_NO));
                }
                if (ofcGoodsDetails.getQuantity() == null || ofcGoodsDetails.getQuantity().compareTo(new BigDecimal(0)) == 0) {
                    continue;
                }
                goodsDetailsInfo.add(ofcGoodsDetails);
            }
        }

        if (StringUtils.equals(ofcFundamentalInformation.getCustCode(),specialCust)) {
            goodsDetailsInfo = goodsDetailsList;
        }

        logger.info("订单进行自动审核,当前订单号:{}, 当前订单状态:{}", ofcFundamentalInformation.getOrderCode(), ofcOrderStatus.toString());
        if (ofcOrderStatus.getOrderStatus().equals(OrderStatusEnum.PEND_AUDIT.getCode()) && reviewTag.equals(REVIEW)) {
            //创单接口订单和钉钉录单补充大区基地信息
            if (StringUtils.equals(ofcFundamentalInformation.getOrderSource(), DING_DING)
                    || StringUtils.equals(ofcFundamentalInformation.getCreator(), CREATE_ORDER_BYAPI)) {
                if (fillAreaAndBase(ofcFundamentalInformation, ofcDistributionBasicInfo, ofcWarehouseInformation)) {
                    return String.valueOf(Wrapper.SUCCESS_CODE);
                }
//                this.pushOrderToAc(ofcFundamentalInformation, ofcFinanceInformation, ofcDistributionBasicInfo, goodsDetailsList, ofcWarehouseInformation);
            }
            //2017年6月13日 追加逻辑: 判断订单上是否有基地信息, 若无, 则不允许审核, 即维持待审核
            if (PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getBaseCode())
                    || PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getBaseName())) {
                logger.error("订单没有基地信息, 维持待审核状态");
                return String.valueOf(Wrapper.SUCCESS_CODE);
            }
            String userName = authResDtoByToken.getUserName();
            ofcFundamentalInformation.setOperator(authResDtoByToken.getUserId());
            ofcFundamentalInformation.setOperatorName(userName);
            ofcFundamentalInformation.setOperTime(new Date());

            String orderType = ofcFundamentalInformation.getOrderType();
            if (trimAndNullAsEmpty(orderType).equals(TRANSPORT_ORDER)) {  // 运输订单
                pushOrderToTfc(ofcFundamentalInformation, ofcFinanceInformation,ofcWarehouseInformation, ofcDistributionBasicInfo, goodsDetailsList);
                pushOrderToAc(ofcFundamentalInformation,ofcFinanceInformation,ofcDistributionBasicInfo,goodsDetailsList, ofcWarehouseInformation);
            } else if (trimAndNullAsEmpty(orderType).equals(WAREHOUSE_DIST_ORDER)) {//仓储订单
                //仓储订单推仓储中心
                pushOrderToWhc(ofcFundamentalInformation, goodsDetailsInfo, ofcWarehouseInformation, ofcFinanceInformation, ofcDistributionBasicInfo);
                //仓储带运输订单推仓储中心和运输中心
                if (Objects.equals(ofcWarehouseInformation.getProvideTransport(), YES)) {
                    if (!StringUtils.equals(reviewTag, ORDER_TAG_STOCK_EDIT) && !StringUtils.equals(reviewTag, ORDER_TAG_STOCK_CUST_EDIT)) {
                        ofcPotNormalRuleService.insertOrderNormalRule(ofcFundamentalInformation, ofcDistributionBasicInfo, goodsDetailsList, ofcWarehouseInformation);
                    }
                    pushOrderToAc(ofcFundamentalInformation,ofcFinanceInformation,ofcDistributionBasicInfo,goodsDetailsInfo, ofcWarehouseInformation);
                    pushOrderToTfc(ofcFundamentalInformation, ofcFinanceInformation,ofcWarehouseInformation, ofcDistributionBasicInfo, goodsDetailsInfo);
                }
            } else {
                logger.error("订单类型有误");
                throw new BusinessException("订单类型有误");
            }
            //订单状态变为已受理
            ofcOrderStatus.setOrderStatus(OrderStatusEnum.ALREADY_ACCEPTED.getCode());
            ofcOrderStatus.setStatusDesc(OrderStatusEnum.ALREADY_ACCEPTED.getDesc());
            ofcOrderStatus.setNotes(new StringBuilder()
                    .append(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1))
                    .append(" ").append("订单已经受理").toString());
            ofcOrderStatus.setOperator(userName);
            ofcOrderStatus.setLastedOperTime(new Date());
            int saveOrderStatus = ofcOrderStatusService.save(ofcOrderStatus);
            if (saveOrderStatus == 0) {
                logger.error("受理出错, 更新订单状态为执行中失败");
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

    @Override
    public boolean fillAreaAndBase(OfcFundamentalInformation ofcFundamentalInformation, OfcDistributionBasicInfo ofcDistributionBasicInfo, OfcWarehouseInformation ofcWarehouseInformation) {
        // 大区、基地都为空则更新大区基地
        String baseCode = ofcFundamentalInformation.getBaseCode();
        String areaCode = ofcFundamentalInformation.getAreaCode();
        String orderType = ofcFundamentalInformation.getOrderType();
        if (PubUtils.isOEmptyOrNull(baseCode) && PubUtils.isOEmptyOrNull(areaCode) && TRANSPORT_ORDER.equals(orderType)) {
            this.updateOrderAreaAndBase(ofcFundamentalInformation, ofcDistributionBasicInfo);
        } else if (PubUtils.isOEmptyOrNull(baseCode) && PubUtils.isOEmptyOrNull(areaCode) && WAREHOUSE_DIST_ORDER.equals(orderType)) {
            ofcOrderPlaceService.updateBaseAndAreaBywarehouseCode(ofcWarehouseInformation.getWarehouseCode(),ofcFundamentalInformation);
        }
        //2017年6月13日 追加逻辑: 判断订单上是否有基地信息, 若无, 则不允许审核, 即维持待审核
        if (PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getBaseCode())
                || PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getBaseName())) {
            logger.error("订单没有基地信息, 维持待审核状态");
            return true;
        }
        return false;
    }

    /**
     * 订单管理修改订单详情
     * @param whcModifWmsCodeReqDto 修改订单详情信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOrderDetail(WhcModifWmsCodeReqDto whcModifWmsCodeReqDto) {
        boolean succees = false;
        try {
            String orderCode = whcModifWmsCodeReqDto.getOrderCode();
            String warehouseCode = whcModifWmsCodeReqDto.getNewWareHouseCode();
            logger.info("订单详情修改的订单为:{},修改后的仓库编码为:{}",orderCode,warehouseCode);
            validateOrderCodeIsExist(orderCode);
            OfcWarehouseInformation ofcWarehouseInformation = new OfcWarehouseInformation();
            OfcFundamentalInformation ofcFundamentalInformation = new OfcFundamentalInformation();
            ofcFundamentalInformation.setOrderCode(orderCode);
            ofcWarehouseInformation.setOrderCode(orderCode);
            ofcWarehouseInformation.setWarehouseCode(warehouseCode);
            ofcWarehouseInformation.setWarehouseName(whcModifWmsCodeReqDto.getNewWareHouseName());
            logger.info("request parameter is {}",whcModifWmsCodeReqDto);
            Wrapper result =  whcModifWmsCodeEdasService.modifWmsCodeByOrderCode(whcModifWmsCodeReqDto);
            logger.info("response result is {}",result);
            if ( Wrapper.SUCCESS_CODE == result.getCode()) {
                /**更新仓库信息的仓库编码和仓库名称**/
                int reuslt = ofcWarehouseInformationService.updateByOrderCode(ofcWarehouseInformation);
                if (reuslt == 1) {
                    succees = true;
                }
            }
        }catch (Exception e) {
            throw  e;
        }
        return succees;
    }

    private void validateOrderCodeIsExist(String orderCode) {
        OfcFundamentalInformation info = ofcFundamentalInformationService.selectByKey(orderCode);
        if (info == null) {
            throw new BusinessException("订单不存在");
        }
    }

    @Override
    public Wrapper<DpcOrderGroupInfoDto> queryOrderGroupInfoByOrderCode(String orderCode) {
        Wrapper<DpcOrderGroupInfoDto> result;
        try {
            logger.info("订单号查询卡班二次配送的基地和仓库,orderCode is {}",orderCode);
            /**校验卡班订单是否为二次配送**/
            validateKbOrder(orderCode);
             result = dpcTransportDocEdasService.queryOrderGroupInfoByOrderCode(orderCode);
            logger.info(" dpcTransportDocEdasService.queryOrderGroupInfoByOrderCode response result is {}",result);
        }catch(Exception e) {
            logger.error("订单号查询卡班二次配送的基地和仓库发生异常：异常详情{}", e);
            throw e;
        }
        return result;
    }

    @Override
    public boolean pushOrderToTfcAndDpc(ModifyAbwKbOrderDTO dto) {
        logger.info("修改卡班二次配送订单负责的基地以及仓库的dto为:{}",dto);
        boolean isSend;
        String orderCode = dto.getOrderCode();
        try {
            String orderInfo = JacksonUtil.toJson(dto);
             isSend = mqProducer.sendMsg(orderInfo, mqConfig.getKbTwoDisBaseModifyTopic(), orderCode, "");
            logger.info("订单中心推送dpc or tfc 结果: {}，订单号：{}", isSend, orderCode);
        } catch (Exception e) {
            logger.error("订单中心推送dpc or tfc 订单转换发生错误, 异常： {}", e);
            throw new BusinessException("订单中心推送dpc or tfc 订单转换发生错误!");
        }
        return isSend;
    }

    /**
     * 校验客户是否存在仓储合同 true:存在  false:不存在
     */
    @Override
    public boolean validateCustomerIsHaveContract(CscContractProEdasDto dto) {
        boolean isHaveContract = false;
        logger.info("校验客户是否存在仓储合同参数为:{}",dto);
        if (dto == null) {
            throw new BusinessException("校验客户仓储合同dto不能为空");
        }
        String custCode = dto.getCustomerCode();
        if (PubUtils.isSEmptyOrNull(custCode)) {
            throw new BusinessException("客户编码不能为空");
        }
        if (PubUtils.isSEmptyOrNull(dto.getProductCatlogCode())) {
            throw new BusinessException("产品分类不能为空");
        }
        try {
            /**
             * 客户是否没有合同也可以下单
             */
            Wrapper<List<CscEdasContractVo>>  contractResp = cscContractEdasService.ofcPlaceOrderByContract(dto);
            logger.info("校验客户是否需要仓储合同才可以下单的响应为:{}",contractResp);
            if (contractResp != null && contractResp.getCode() == Wrapper.SUCCESS_CODE) {
                    isHaveContract = true;
                /**
                 * 客户必须存在合同才可以下单，校验改客户是否存在合同
                 */
            } else {
                logger.info("客户必须存在合同才可下单，客户编码为:{}",custCode);
                ContractDto contractDto = new ContractDto();
                contractDto.setCustomerCode(custCode);
                contractDto.setProductCatlogCode(dto.getProductCatlogCode());
                logger.info("查询客户是否存在合同参数为:{}",contractDto);
                Wrapper<List<CscEdasContractVo>> cscConstractResp = cscContractEdasService.queryCscContractByCustomerCode(contractDto);
                logger.info("查询客户是否存在合同响应为:{}",cscConstractResp);
                if (cscConstractResp.getCode() == Wrapper.SUCCESS_CODE && CollectionUtils.isNotEmpty(cscConstractResp.getResult())) {
                    isHaveContract = true;
                }
            }
        }catch (Exception e) {
            logger.error("调用csc校验客户是否存在仓储合同出现异常:{}",e);
            throw  e;
        }
        return isHaveContract;
    }

    private void validateKbOrder(String orderCode) {
        OfcFundamentalInformation info = ofcFundamentalInformationService.selectByKey(orderCode);
        if (info == null) {
            throw new BusinessException("订单不存在");
        }
        if (!TRANSPORT_ORDER.equals(info.getOrderType())) {
            throw new BusinessException("订单类型必须是运输订单");
        }
        if (!WITH_THE_KABAN.equals(info.getBusinessType())) {
            throw new BusinessException("订单业务类型必须是卡班类型");
        }
        OfcFinanceInformation financeInfo = ofcFinanceInformationService.selectByKey(orderCode);
        if (financeInfo == null) {
            throw new BusinessException("订单费用信息为空");
        }
        if (!TWO_DISTRIBUTION.equals(financeInfo.getTwoDistribution())) {
            throw new BusinessException("订单必须为二次配送");
        }
    }

    /**
     * 订单信息推送运输中心
     *
     * @param ofcFundamentalInformation 基本信息
     * @param ofcFinanceInformation     财务信息
     * @param ofcDistributionBasicInfo  运输信息
     * @param ofcGoodsDetailsInfos      货品信息
     */
    @Override
    public void pushOrderToTfc(OfcFundamentalInformation ofcFundamentalInformation, OfcFinanceInformation ofcFinanceInformation
            ,OfcWarehouseInformation ofcWarehouseInformation, OfcDistributionBasicInfo ofcDistributionBasicInfo, List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos) {
        logger.info("订单信息推送运输中心,订单号:{}", ofcFundamentalInformation.getOrderCode());
        //订单中心实体转运输中心接口DTO
        if (!ofcFundamentalInformation.getOrderType().equals(WAREHOUSE_DIST_ORDER)) {
            ofcPotNormalRuleService.insertOrderNormalRule(ofcFundamentalInformation, ofcDistributionBasicInfo , ofcGoodsDetailsInfos, ofcWarehouseInformation);
        }
        TfcTransport tfcTransport = convertOrderToTfc(ofcFundamentalInformation, ofcFinanceInformation,ofcWarehouseInformation, ofcDistributionBasicInfo, ofcGoodsDetailsInfos);
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
        logger.info("订单信息推送仓储中心 ==> goodsDetailsList:{}", goodsDetailsList);
        logger.info("订单信息推送仓储中心 ==> ofcWarehouseInformation:{}", ofcWarehouseInformation);
        logger.info("订单信息推送仓储中心 ==> ofcFinanceInformation:{}", ofcFinanceInformation);
        logger.info("订单信息推送仓储中心 ==> dinfo:{}", dinfo);
        String json;
        try {
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);//严格模式
            OfcOrderDTO ofOrderDto = modelMapper.map(ofcFundamentalInformation, OfcOrderDTO.class);
            logger.info("订单信息推送仓储中心 ==> ofOrderDto:{}", ofOrderDto);
            ofOrderDto.setWarehouseName(ofcWarehouseInformation.getWarehouseName());
            ofOrderDto.setWarehouseCode(ofcWarehouseInformation.getWarehouseCode());
            ofOrderDto.setProvideTransport(ofcWarehouseInformation.getProvideTransport());
            ofOrderDto.setWeight(null == dinfo ? new BigDecimal(0) : dinfo.getWeight());
            if (STOCK_OUT_ORDER.equals(trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0, 2))) {
                //出库
                ofOrderDto.setShipmentTime(ofcWarehouseInformation.getShipmentTime());
                if (dinfo != null) {
                    StringBuilder address=new StringBuilder();
                    ofOrderDto.setConsigneeCode(dinfo.getConsigneeName());//收货方编码
                    ofOrderDto.setConsigneeContactCode(dinfo.getConsigneeContactCode());//收货方联系人编码
                    ofOrderDto.setConsigneeContactName(dinfo.getConsigneeContactName());//收货方联系名称
                    ofOrderDto.setConsigneeContactPhone(dinfo.getConsigneeContactPhone());//收货方联系电话
                    if (!StringUtils.isEmpty(dinfo.getDestinationProvince())) {
                        address.append(dinfo.getDestinationProvince());
                    }
                    if (!StringUtils.isEmpty(dinfo.getDestinationCity())) {
                        address.append(dinfo.getDestinationCity());
                    }
                    if (!StringUtils.isEmpty(dinfo.getDestinationDistrict())) {
                        address.append(dinfo.getDestinationDistrict());
                    }
                    if (!StringUtils.isEmpty(dinfo.getDestinationTowns())) {
                        address.append(dinfo.getDestinationTowns());
                    }
                    if (!StringUtils.isEmpty(dinfo.getDestination())) {
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
//                        if (!CollectionUtils.isEmpty(msgs)) {
//                           for(int i=0;i<msgs.size();i++) {
//                               ResponseMsg msg=msgs.get(i);
//                               if (i==msgs.size()-1) {
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
            , OfcFinanceInformation ofcFinanceInformation,OfcWarehouseInformation ofcWarehouseInformation, OfcDistributionBasicInfo ofcDistributionBasicInfo, List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos) {
        logger.info("====>ofcFundamentalInformation{}", ofcFundamentalInformation);
        logger.info("====>ofcFinanceInformation{}", ofcFinanceInformation);
        logger.info("====>ofcDistributionBasicInfo{}", ofcDistributionBasicInfo);
        logger.info("====>ofcGoodsDetailsInfos{}", ofcGoodsDetailsInfos);
        if (null == ofcFundamentalInformation || null == ofcFinanceInformation || null == ofcDistributionBasicInfo || CollectionUtils.isEmpty(ofcGoodsDetailsInfos)) {
            logger.error("订单中心实体转运输中心接口DTO异常! 入参错误");
            throw new BusinessException("订单中心实体转运输中心接口DTO异常! 入参错误");
        }
        TfcTransport tfcTransport = new TfcTransport();
        this.convertOrderToTfcOfBaseInfo(ofcFundamentalInformation, ofcFinanceInformation,ofcWarehouseInformation, ofcDistributionBasicInfo, tfcTransport);
        //调度中心运力调度订单列表展示货品分类展示的问题  取第一条货品种类填充
        if (PubUtils.isSEmptyOrNull(ofcDistributionBasicInfo.getGoodsTypeName())){
            for (OfcGoodsDetailsInfo ofcGoodsDetailsInfo : ofcGoodsDetailsInfos) {
                if (!PubUtils.isSEmptyOrNull(ofcGoodsDetailsInfo.getGoodsType())) {
                    tfcTransport.setGoodsTypeName(ofcGoodsDetailsInfo.getGoodsType());
                    break;
                }
            }
        }

        List<TfcTransportDetail> tfcTransportDetails = new ArrayList<>();
        for (OfcGoodsDetailsInfo ofcGoodsDetailsInfo : ofcGoodsDetailsInfos) {
            if (ofcGoodsDetailsInfo.getQuantity() == null || ofcGoodsDetailsInfo.getQuantity().compareTo(new BigDecimal(0)) == 0) {
                if ((ofcGoodsDetailsInfo.getWeight() == null || ofcGoodsDetailsInfo.getWeight().compareTo(new BigDecimal(0)) == 0) && (ofcGoodsDetailsInfo.getCubage() == null || ofcGoodsDetailsInfo.getCubage().compareTo(new BigDecimal(0)) == 0)) {
                    continue;
                }
            }
            TfcTransportDetail tfcTransportDetail = new TfcTransportDetail();
            tfcTransportDetail.setPaasLineNo(ofcGoodsDetailsInfo.getPaasLineNo());
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
            tfcTransportDetail.setUom(trimAndNullAsEmpty(ofcFundamentalInformation.getOrderType().equals(WAREHOUSE_DIST_ORDER)?ofcGoodsDetailsInfo.getPackageName():ofcGoodsDetailsInfo.getUnit()));
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
            tfcTransportDetail.setPack(trimAndNullAsEmpty(ofcGoodsDetailsInfo.getPackageName()));
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
     * @param ofcWarehouseInformation 仓储信息
     * @param ofcDistributionBasicInfo 运输信息
     * @param tfcTransport TFC实体
     */
    private void convertOrderToTfcOfBaseInfo(OfcFundamentalInformation ofcFundamentalInformation
            , OfcFinanceInformation ofcFinanceInformation, OfcWarehouseInformation ofcWarehouseInformation,OfcDistributionBasicInfo ofcDistributionBasicInfo, TfcTransport tfcTransport) {
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
        tfcTransport.setGroundDistribution(PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getGroundDistribution()) ? "0": ofcFundamentalInformation.getGroundDistribution());
//        tfcTransport.setTfcBillNo();
        tfcTransport.setFromSystem(trimAndNullAsEmpty(ofcFundamentalInformation.getOrderSource()));
        tfcTransport.setTransportNo(trimAndNullAsEmpty(ofcDistributionBasicInfo.getTransCode()));
//        tfcTransport.setStatus();
        tfcTransport.setBillType(trimAndNullAsEmpty(ofcFundamentalInformation.getOrderType()));
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
        // 仓储带运输业务类型写死600
        String businessType = trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType());
        if (ofcFundamentalInformation.getOrderType().equals(WAREHOUSE_DIST_ORDER)) {
            businessType = WITH_THE_CITY;
        }
        tfcTransport.setBusinessType(businessType);
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
        tfcTransport.setPaymentWay(ofcFinanceInformation.getPayment());
        tfcTransport.setCarrFeePayer(ofcFinanceInformation.getExpensePaymentParty());
        tfcTransport.setIsReceipt(ofcFinanceInformation.getReturnList());
        if (ofcWarehouseInformation != null && !PubUtils.isOEmptyOrNull(ofcWarehouseInformation.getProvideTransport())) {
            tfcTransport.setProvideTransport(ofcWarehouseInformation.getProvideTransport().equals(WEARHOUSE_WITH_TRANS) ? WEARHOUSE_WITH_TRANS:WAREHOUSE_NO_TRANS);
        } else {
            tfcTransport.setProvideTransport(WAREHOUSE_NO_TRANS);
        }
    }

    /**
     * 校验收发货方信息
     */
    private Wrapper<?> validateDistrictContactMessage(OfcDistributionBasicInfo ofcDistributionBasicInfo) {
        if (null == ofcDistributionBasicInfo) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "校验收发货方信息入参为空");
        }
//        if (null == cscContantAndCompanyDtoConsignor.getCscContactCompanyDto() || null == cscContantAndCompanyDtoConsignee.getCscContactCompanyDto()) {
//            return WrapMapper.wrap(Wrapper.ERROR_CODE, "校验收货方信息入参收货方信息为空");
//        }
//        if (null == cscContantAndCompanyDtoConsignor.getCscContactDto() || null == cscContantAndCompanyDtoConsignee.getCscContactDto()) {
//            return WrapMapper.wrap(Wrapper.ERROR_CODE, "校验收货方信息入参收货方联系人信息为空");
//        }
        if (isSEmptyOrNull(ofcDistributionBasicInfo.getConsignorName()) || isSEmptyOrNull(ofcDistributionBasicInfo.getConsignorCode())) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "请输入发货方信息");
        }
        if (isSEmptyOrNull(ofcDistributionBasicInfo.getConsignorContactName()) || isSEmptyOrNull(ofcDistributionBasicInfo.getConsignorContactCode())) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "发货方联系人名称未填写");
        }
        if (isSEmptyOrNull(ofcDistributionBasicInfo.getConsignorContactPhone())) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "发货方联系人电话未填写");
        }
        //二级地址还需特殊处理
        if (isSEmptyOrNull(ofcDistributionBasicInfo.getDepartureProvince())) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "发货方联系人地址未选择");
        }
        if (isSEmptyOrNull(ofcDistributionBasicInfo.getDepartureCity())) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "发货方联系人地址不完整");
        }
        /*if (PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignor.getCscContact().getAreaName())) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"发货方联系人地址不完整");
        }*/

        if (isSEmptyOrNull(ofcDistributionBasicInfo.getConsigneeName())) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "请输入收货方信息");
        }
        if (isSEmptyOrNull(ofcDistributionBasicInfo.getConsigneeContactName()) || isSEmptyOrNull(ofcDistributionBasicInfo.getConsigneeContactCode())) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "收货方联系人名称未填写");
        }
        if (isSEmptyOrNull(ofcDistributionBasicInfo.getConsigneeContactPhone())) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "收货方联系人电话未填写");
        }
        if (isSEmptyOrNull(ofcDistributionBasicInfo.getDestinationProvince())) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "收货方联系人地址未选择");
        }
        if (isSEmptyOrNull(ofcDistributionBasicInfo.getDestinationCity())) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "收货方联系人地址不完整");
        }
        /*if (PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignee.getCscContact().getAreaName())) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"收货方联系人地址不完整");
        }*/

        return WrapMapper.wrap(Wrapper.SUCCESS_CODE);
    }

    /**
     * 众品订单审核
     *
     * @param reviewTag                 审核标志位
     * @param authResDtoByToken         当前登录用户
     * @return String
     */
    @Override
    public String orderAutoAuditForTran(String orderCode, String reviewTag, AuthResDto authResDtoByToken) {
        OfcOrderStatus ofcOrderStatus = ofcOrderStatusService.queryLastTimeOrderByOrderCode(orderCode);
        logger.info("订单进行自动审核,当前订单号:{}, 当前订单状态:{}", orderCode, ofcOrderStatus.toString());
        if (ofcOrderStatus.getOrderStatus().equals(PENDING_AUDIT) && reviewTag.equals(REVIEW)) {
            OfcFundamentalInformation ofcFundamentalInformation= ofcFundamentalInformationService.selectByKey(orderCode);
            if (null==ofcFundamentalInformation) {
                throw new BusinessException("该订单不存在或者已删除");
            }
            //2017年6月13日 追加逻辑: 判断订单上是否有基地信息, 若无, 则不允许审核, 即维持待审核
            if (PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getBaseCode())
                    || PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getBaseName())) {
                logger.error("订单没有基地信息, 维持待审核状态");
                return String.valueOf(Wrapper.SUCCESS_CODE);
            }
            String userName = authResDtoByToken.getUserName();
            OfcFinanceInformation ofcFinanceInformation = ofcFinanceInformationService.selectByKey(orderCode);
            OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoService.selectByKey(orderCode);
            OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
            ofcGoodsDetailsInfo.setOrderCode(orderCode);
            List<OfcGoodsDetailsInfo> goodsDetailsList = ofcGoodsDetailsInfoService.select(ofcGoodsDetailsInfo);
            String orderType = ofcFundamentalInformation.getOrderType();
            OfcWarehouseInformation cw = new OfcWarehouseInformation();
            cw.setOrderCode(ofcFundamentalInformation.getOrderCode());
            OfcWarehouseInformation ofcWarehouseInformation = ofcWarehouseInformationService.selectOne(cw);
            // 运输订单
            if (TRANSPORT_ORDER.equals(trimAndNullAsEmpty(orderType))) {
                pushOrderToTfc(ofcFundamentalInformation, ofcFinanceInformation,ofcWarehouseInformation, ofcDistributionBasicInfo, goodsDetailsList);
                this.pushOrderToAc(ofcFundamentalInformation, ofcFinanceInformation, ofcDistributionBasicInfo, goodsDetailsList, null);
                //仓储订单
            } else if (WAREHOUSE_DIST_ORDER.equals(trimAndNullAsEmpty(orderType))) {
                //仓储订单推仓储中心
                pushOrderToWhc(ofcFundamentalInformation, goodsDetailsList, ofcWarehouseInformation, ofcFinanceInformation, ofcDistributionBasicInfo);
                //仓储带运输订单推仓储中心和运输中心
                if (Objects.equals(ofcWarehouseInformation.getProvideTransport(), YES)) {
                    pushOrderToTfc(ofcFundamentalInformation, ofcFinanceInformation,ofcWarehouseInformation, ofcDistributionBasicInfo, goodsDetailsList);
                    this.pushOrderToAc(ofcFundamentalInformation, ofcFinanceInformation, ofcDistributionBasicInfo, goodsDetailsList, null);
                }
            } else {
                logger.error("订单类型有误");
                throw new BusinessException("订单类型有误");
            }
            //订单状态变为执行中
            ofcOrderStatus.setOrderStatus(OrderStatusEnum.ALREADY_ACCEPTED.getCode());
            ofcOrderStatus.setStatusDesc(OrderStatusEnum.ALREADY_ACCEPTED.getDesc());
            ofcOrderStatus.setNotes(new StringBuilder()
                    .append(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1))
                    .append(" ").append("订单已受理").toString());
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
     * 获取供应商相关的信息
     */
    private List<CscSupplierInfoDto> getSupplierInfo(CscSupplierInfoDto dto){
        Wrapper<List<CscSupplierInfoDto>> suppliers= cscSupplierEdasService.querySupplierByAttribute(dto);
        if(suppliers.getCode() == Wrapper.SUCCESS_CODE){
            if (!CollectionUtils.isEmpty(suppliers.getResult())) {
                return suppliers.getResult();
            }
        }
        return null;
    }



}