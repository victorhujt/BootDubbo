package com.xescm.ofc.service.impl;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.dpc.edas.dto.DpcSyncOrderInfoDto;
import com.xescm.ofc.config.MqConfig;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mq.producer.DefaultMqProducer;
import com.xescm.ofc.service.*;
import com.xescm.tfc.edas.model.dto.ofc.req.GoodsAmountDetailDto;
import com.xescm.tfc.edas.model.dto.ofc.req.GoodsAmountSyncDto;
import com.xescm.tfc.edas.service.TfcUpdateOrderEdasService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

import static com.xescm.ofc.constant.OrderConstConstant.*;

/**
 *
 * Created by hiyond on 2016/11/25.
 */
@Service
public class GoodsAmountSyncServiceImpl implements GoodsAmountSyncService {
    private Logger logger = LoggerFactory.getLogger(GoodsAmountSyncService.class);
    @Resource
    private OfcOrderNewstatusService ofcOrderNewstatusService;
    @Resource
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Resource
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;
    @Resource
    private OfcFinanceInformationService ofcFinanceInformationService;
    @Resource
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Resource
    private TfcUpdateOrderEdasService tfcUpdateOrderEdasService;
    @Resource
    private OfcGoodsRecordModificationService ofcGoodsRecordModificationService;
    @Resource
    private OfcOrderManageService ofcOrderManageService;
    @Resource
    private MqConfig mqConfig;
    @Resource
    private DefaultMqProducer mqProducer;

    @Transactional
    @Override
    public Wrapper<?> goodsAmountSync(GoodsAmountSyncDto goodsAmountSyncDto) {
        Wrapper result = null;
        String custCode = goodsAmountSyncDto.getCustCode();
        String custOrderCode = goodsAmountSyncDto.getCustOrderCode();
        if (PubUtils.isOEmptyOrNull(custCode)) {
            throw new BusinessException("客户编码不能为空");
        }else if (PubUtils.isNotNullAndSmallerSize(goodsAmountSyncDto.getGoodsAmountDetailDtoList(), 1)) {
            throw new BusinessException("货品信息不能为空");
        }
        // 查询订单
        OfcFundamentalInformation ofcFundamentalInfo = new OfcFundamentalInformation();
        ofcFundamentalInfo.setCustCode(custCode);
        if (!PubUtils.isSEmptyOrNull(custOrderCode)) {
            ofcFundamentalInfo.setCustOrderCode(custOrderCode);
        } else {
            if (!PubUtils.isSEmptyOrNull(goodsAmountSyncDto.getOderCode())) {
                ofcFundamentalInfo.setOrderCode(goodsAmountSyncDto.getOderCode());
            }
        }

        try {
            List<OfcFundamentalInformation> orderList = ofcFundamentalInformationService.select(ofcFundamentalInfo);
            if (!PubUtils.isNotNullAndBiggerSize(orderList, 0)) {
                logger.error("未查询到客户订单{}信息.", custOrderCode);
                throw new BusinessException("未查询到客户订单{"+custOrderCode+"}信息.");
            }
            for (OfcFundamentalInformation ofcOrder : orderList) {
                String orderCode = ofcOrder.getOrderCode();
                goodsAmountSyncDto.setOderCode(orderCode);
                OfcOrderNewstatus orderStatusObj = ofcOrderNewstatusService.selectByKey(orderCode);
                if (!PubUtils.isOEmptyOrNull(orderStatusObj) && !PubUtils.isOEmptyOrNull(orderStatusObj.getOrderLatestStatus())) {
                    String orderStatus = orderStatusObj.getOrderLatestStatus();
                    // 订单取消或者已完成，不调整；
                    if (HASBEEN_COMPLETED.equals(orderStatus) || HASBEEN_CANCELED.equals(orderStatus)) {
                        logger.info("订单{}当前状态为{},不允许调整数量!", orderCode, orderStatus);
                        throw new BusinessException("订单{"+orderCode+"}当前状态为{"+orderStatus+"},不允许调整数量!");
                    } else { // 非取消、完成订单，根据tfc返回结果进行调整
                        modifyGoodsDetails(goodsAmountSyncDto, orderCode, orderStatus);
                        result = WrapMapper.ok();
                    }
                } else {
                    logger.error("订单{}查询不到当前状态.", orderCode);
                }
            }
        } catch (BusinessException e) {
            logger.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("更新交货数量发生未知异常 {}", e);
            throw e;
        }
        return result;
    }

    /**
     * 修改
     * @param goodsAmountSyncDto
     * @param orderCode
     */
    @Transactional
    void modifyGoodsDetails(GoodsAmountSyncDto goodsAmountSyncDto, String orderCode, String orderStatus) {
        try {
            // 1.修改订单中心重量、数量、体积
            this.modifyOfcGoodsDetailInfo(orderCode,orderStatus,goodsAmountSyncDto.getGoodsAmountDetailDtoList());
            // 2.修改运输、结算、调度中心
            this.modifyOtherCenterGoodsDetailInfo(orderCode, orderStatus, goodsAmountSyncDto);

        } catch (BusinessException ex) {
            logger.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            logger.error("交货量同步更新发生异常. {}", ex);
            throw ex;
        }
    }

    /**
     * 修改下方系统
     * @param orderCode
     * @param orderStatus
     */
    private void modifyOtherCenterGoodsDetailInfo(String orderCode, String orderStatus, GoodsAmountSyncDto goodsAmountSyncDto) {
        // 待审核订单不推送结算、运输
        if (!PENDING_AUDIT.equals(orderStatus)) {
            // 修改运输中心
            Wrapper tfcRes = tfcUpdateOrderEdasService.updateTransportOrder(goodsAmountSyncDto);
            logger.info("修复运输中心重量的返回结果为:====={}=======>",tfcRes);
            if (tfcRes == null) {
                throw new BusinessException("调用运输中心交货量接口发生异常：接口返回值为null");
            } else if (Wrapper.SUCCESS_CODE != tfcRes.getCode()) {
                throw new BusinessException("运输中心修改订单货品信息失败：异常详情 => {}", tfcRes.getMessage());
            }
            // 修改结算中心
            OfcFundamentalInformation orderInfo = ofcFundamentalInformationService.selectByKey(orderCode);
            OfcDistributionBasicInfo orderDistInfo = ofcDistributionBasicInfoService.selectByKey(orderCode);
            OfcFinanceInformation orderFinanceInfo = ofcFinanceInformationService.selectByKey(orderCode);
            List<OfcGoodsDetailsInfo> detailsInfos = ofcGoodsDetailsInfoService.queryByOrderCode(orderCode);
            // 待审核订单不推送到结算和运输中心
            if (!PubUtils.isNull(orderInfo) && !PubUtils.isNull(orderDistInfo) && !PubUtils.isNull(orderFinanceInfo)) {
                ofcOrderManageService.pushOrderToAc(orderInfo, orderFinanceInfo, orderDistInfo, detailsInfos, null);
            }
            // 修改调度中心
            try {
                DpcSyncOrderInfoDto syncOrderInfoDto = new DpcSyncOrderInfoDto();
                syncOrderInfoDto.setOrderNo(orderCode);
                syncOrderInfoDto.setWeight(orderDistInfo.getWeight());
                syncOrderInfoDto.setGoodsNum(orderDistInfo.getQuantity());
                syncOrderInfoDto.setVolume(orderDistInfo.getCubage());
                String msgStr = JacksonUtil.toJson(syncOrderInfoDto);
                boolean result = mqProducer.sendMsg(msgStr, mqConfig.getOrderAmountModifyTopic(), orderCode, null);
                if (!result) {
                    throw new BusinessException("订单修改推送调度中心失败！");
                }
            } catch (Exception e) {
                logger.error("推送调度中心交货量同步发生异常：异常详情 => {}", e);
                throw new BusinessException("推送调度中心交货量同步发生异常!");
            }
        }
    }

    /**
     * 修改商品数量、重量、体积
     * @param orderCode
     * @param goodsAmountDetailDtoList
     */
    @Transactional
    void modifyOfcGoodsDetailInfo(String orderCode,String orderStatus, List<GoodsAmountDetailDto> goodsAmountDetailDtoList) {
        try {
            // 修改订单商品重量、数量、体积
            for (GoodsAmountDetailDto goodsAmountDetailDto: goodsAmountDetailDtoList) {
                if (PubUtils.isOEmptyOrNull(goodsAmountDetailDto.getGoodsName())) {
                    throw new BusinessException("货品名称不能为空！");
                } else if (PubUtils.isOEmptyOrNull(goodsAmountDetailDto.getGoodsCode())) {
                    throw new BusinessException("货品编号不能为空！");
                }
                OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
                ofcGoodsDetailsInfo.setOrderCode(orderCode);
                ofcGoodsDetailsInfo.setGoodsCode(goodsAmountDetailDto.getGoodsCode());
                List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList = ofcGoodsDetailsInfoService.select(ofcGoodsDetailsInfo);
                if (CollectionUtils.isNotEmpty(ofcGoodsDetailsInfoList) && ofcGoodsDetailsInfoList.size() == 1) {
                    ofcGoodsDetailsInfo = ofcGoodsDetailsInfoList.get(0);
                    String weight = goodsAmountDetailDto.getWeight();
                    String quantity = goodsAmountDetailDto.getQty();
                    String volume = goodsAmountDetailDto.getVolume();
                    if (!PubUtils.isOEmptyOrNull(quantity)) {
                        ofcGoodsDetailsInfo.setQuantity(new BigDecimal(quantity));
                    }
                    if (!PubUtils.isOEmptyOrNull(weight)) {
                        ofcGoodsDetailsInfo.setWeight(new BigDecimal(weight));
                    }
                    if (!PubUtils.isOEmptyOrNull(volume)) {
                        ofcGoodsDetailsInfo.setCubage(new BigDecimal(volume));
                    }
                    ofcGoodsDetailsInfoService.update(ofcGoodsDetailsInfo);
                } else {
                    logger.error("订单{}查询不到货品{}信息！", orderCode, goodsAmountDetailDto.getGoodsCode());
                }
                // 添加修改记录
                this.addGoodsModifyRecord(orderCode, goodsAmountDetailDto);
            }
            // 修改订单总重量、数量、体积
            this.modifyOfcOrderTotalAmount(orderCode);
        } catch (NumberFormatException ex) {
            logger.error("众品交货量同步转换数字类型失败：异常详情 => {}", ex);
            throw new BusinessException("众品交货量同步转换数字类型失败，请检查类型！");
        } catch (BusinessException ex) {
            logger.error("订单中心更新货品信息失败：异常详情 => {} ", ex);
            throw ex;
        } catch (Exception ex) {
            logger.error("订单中心更新货品信息失败：异常详情 => {}", ex);
            throw ex;
        }
    }

    /**
     * 修改订单总重量、数量、体积
     * @param orderCode
     */
    private void modifyOfcOrderTotalAmount(String orderCode) {
        BigDecimal quantityCount = new BigDecimal(0);
        BigDecimal weightCount = new BigDecimal(0);
        BigDecimal cubageCount = new BigDecimal(0);
        OfcDistributionBasicInfo orderDistInfo = ofcDistributionBasicInfoService.selectByKey(orderCode);
        List<OfcGoodsDetailsInfo> detailsInfos = ofcGoodsDetailsInfoService.queryByOrderCode(orderCode);
        for (OfcGoodsDetailsInfo detailsInfo : detailsInfos) {
            BigDecimal weight = detailsInfo.getWeight();
            BigDecimal quantity = detailsInfo.getQuantity();
            BigDecimal cubage = detailsInfo.getCubage();
            weightCount = weightCount.add(PubUtils.isNull(weight) ? new BigDecimal(0) : weight);
            quantityCount = quantityCount.add(PubUtils.isNull(quantity) ? new BigDecimal(0) : quantity);
            cubageCount = cubageCount.add(PubUtils.isNull(cubage) ? new BigDecimal(0) : cubage);
        }
        orderDistInfo.setQuantity(quantityCount.setScale(2,BigDecimal.ROUND_HALF_UP));
        orderDistInfo.setWeight(weightCount.setScale(2,BigDecimal.ROUND_HALF_UP));
        orderDistInfo.setCubage(cubageCount.toString());
        ofcDistributionBasicInfoService.update(orderDistInfo);
    }

    /**
     * 新增商品数量、重量、体积修改记录
     * @param orderCode
     * @param goodsAmountDetailDto
     */
    @Transactional
    void addGoodsModifyRecord(String orderCode, GoodsAmountDetailDto goodsAmountDetailDto) {
        try {
            String goodsCode = goodsAmountDetailDto.getGoodsCode();
            String goodsName = goodsAmountDetailDto.getGoodsName();
            OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
            ofcGoodsDetailsInfo.setOrderCode(orderCode);
            ofcGoodsDetailsInfo.setGoodsCode(goodsCode);
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList = ofcGoodsDetailsInfoService.select(ofcGoodsDetailsInfo);
            if (PubUtils.isNotNullAndBiggerSize(ofcGoodsDetailsInfoList, 0)) {
                OfcGoodsRecordModification ofcGoodsRecordModification = new OfcGoodsRecordModification();
                ofcGoodsRecordModification.setOrderCode(orderCode);
                ofcGoodsRecordModification.setGoodsCode(goodsCode);
                ofcGoodsRecordModification.setGoodsName(goodsName);
                OfcGoodsDetailsInfo goodsDetail = ofcGoodsDetailsInfoList.get(0);
                StringBuffer desc = new StringBuffer();
                String modifyQuantity = goodsAmountDetailDto.getQty();
                String modifyWeight = goodsAmountDetailDto.getWeight();
                String modifyVolume = goodsAmountDetailDto.getVolume();
                // 调整数量
                if (!PubUtils.isOEmptyOrNull(modifyQuantity)) {
                    String oldQuantity = !PubUtils.isOEmptyOrNull(goodsDetail.getQuantity()) ? goodsDetail.getQuantity().toString() : " ";
                    ofcGoodsRecordModification.setValueBeforeModifyQty(oldQuantity);
                    ofcGoodsRecordModification.setValueAfterModifyQty(modifyQuantity);
                    desc.append("商品").append(goodsCode).append("数量由").append(oldQuantity).append("调整为").append(modifyQuantity).append(";");
                }
                // 调整重量
                if (!PubUtils.isOEmptyOrNull(modifyWeight)) {
                    String oldWeight = !PubUtils.isOEmptyOrNull(goodsDetail.getWeight()) ? goodsDetail.getWeight().toString() : " ";
                    ofcGoodsRecordModification.setValueBeforeModifyWet(oldWeight);
                    ofcGoodsRecordModification.setValueAfterModifyWet(modifyWeight);
                    desc.append("商品").append(goodsCode).append("重量由").append(oldWeight).append("调整为").append(modifyWeight).append(";");
                }
                // 调整体积
                if (!PubUtils.isOEmptyOrNull(modifyVolume)) {
                    String oldVolume = !PubUtils.isOEmptyOrNull(goodsDetail.getCubage()) ? goodsDetail.getCubage().toString() : " ";
                    ofcGoodsRecordModification.setValueBeforeModifyVol(oldVolume);
                    ofcGoodsRecordModification.setValueAfterModifyVol(modifyVolume);
                    desc.append("商品").append(goodsCode).append("体积由").append(oldVolume).append("调整为").append(modifyVolume).append(";");
                }
                ofcGoodsRecordModification.setModificationDesc(desc.toString());
                ofcGoodsRecordModificationService.save(ofcGoodsRecordModification);
            }
        } catch (Exception e) {
            logger.error("订单中心记录需要更新货品信息失败",e.getMessage(),e);
            throw new BusinessException("订单中心记录需要更新货品信息失败",e.getMessage(),e);
        }
    }

}
