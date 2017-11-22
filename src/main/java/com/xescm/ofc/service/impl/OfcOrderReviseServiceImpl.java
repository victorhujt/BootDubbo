package com.xescm.ofc.service.impl;

import com.xescm.ac.provider.AcModifyOrderEdasService;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.dpc.edas.dto.DpcSyncOrderInfoDto;
import com.xescm.dpc.edas.service.DpcTransportDocEdasService;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.*;
import com.xescm.tfc.edas.model.dto.ofc.req.GoodsAmountDetailDto;
import com.xescm.tfc.edas.model.dto.ofc.req.GoodsAmountSyncDto;
import com.xescm.tfc.edas.service.TfcUpdateOrderEdasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

import static com.xescm.ofc.constant.OrderConstConstant.*;

@Service
public class OfcOrderReviseServiceImpl implements OfcOrderReviseService {
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
    private AcModifyOrderEdasService acModifyOrderEdasService;
    @Resource
    private DpcTransportDocEdasService dpcTransportDocEdasService;

    @Transactional
    @Override
    public Wrapper<?> goodsAmountSync(GoodsAmountSyncDto goodsAmountSyncDto) {
        Wrapper result = null;
        String orderCode = goodsAmountSyncDto.getOderCode();
        List<GoodsAmountDetailDto> details = goodsAmountSyncDto.getGoodsAmountDetailDtoList();
        // 校验订单是否存在
        OfcFundamentalInformation ofcFundamentalInfo  = ofcFundamentalInformationService.selectByKey(orderCode);
        if (ofcFundamentalInfo == null) {
            throw new BusinessException("订单{"+orderCode+"}不存在");
        }
        OfcOrderNewstatus orderStatus = ofcOrderNewstatusService.selectByKey(orderCode);
        if (!PubUtils.isOEmptyOrNull(orderStatus) && !PubUtils.isOEmptyOrNull(orderStatus.getOrderLatestStatus())) {
            String status = orderStatus.getOrderLatestStatus();
            // 订单取消或者已完成，不调整；待审核只修改订单中心
            if (HASBEEN_COMPLETED.equals(status) || HASBEEN_CANCELED.equals(status)) {
                logger.info("订单{}当前状态为{},不允许调整数量!", orderCode, status);
                throw new BusinessException("订单{"+orderCode+"}当前状态为{"+status+"},不允许调整数量!");
            } else if (PENDING_AUDIT.equals(status)) { // 待审核只调整OFC
                modifyGoodsDetails(goodsAmountSyncDto, details, orderCode, status);
                result = WrapMapper.ok();
            } else {
                Wrapper<Boolean> acStatus = acModifyOrderEdasService.queryOrderIncomeStatus(orderCode);
                if (!PubUtils.isNull(acStatus) && Wrapper.SUCCESS_CODE == acStatus.getCode()) {
                    if (!acStatus.getResult()) {
                        modifyGoodsDetails(goodsAmountSyncDto, details, orderCode, status);
                        result = WrapMapper.ok();
                    } else {
                        logger.error("订单{}已经结算，无法调整数量: {}", orderCode, acStatus.getMessage());
                        throw new BusinessException("订单{"+orderCode+"}已经结算，无法调整数量: {"+acStatus.getMessage()+"}");
                    }
                } else {
                    logger.error("结算中心验证订单是否可以取消接口发生异常！");
                    throw new BusinessException("结算中心验证订单是否可以取消接口发生异常！");
                }
            }
        } else {
            logger.error("订单{}查询不到当前状态.", orderCode);
        }
        return result;
    }

    /**
     *
     * @param goodsAmountSyncDto
     * @param details
     * @param orderCode
     */
    @Transactional
    void modifyGoodsDetails(GoodsAmountSyncDto goodsAmountSyncDto, List<GoodsAmountDetailDto> details, String orderCode, String orderStatus) {
        try {
            BigDecimal quantityCount = new BigDecimal(0);
            BigDecimal weightCount = new BigDecimal(0);
            BigDecimal cubageCount = new BigDecimal(0);
            for (GoodsAmountDetailDto goodsDetail : details) {
                if (PubUtils.isOEmptyOrNull(goodsDetail.getGoodsName())) {
                    throw new BusinessException("货品名称不能为空");
                }
                // 通过行号修改货品重量
                if (PubUtils.isOEmptyOrNull(goodsDetail.getPassLineNo())) {
                    throw new BusinessException("货品行号不能为空");
                }
                /* else if (PubUtils.isOEmptyOrNull(goodsDetail.getGoodsCode())) {
                    throw new BusinessException("货品编号不能为空");
                }*/
                // 添加修改记录
              //  this.addGoodsModifyRecord(orderCode, goodsDetail);
                // 修改货品信息
                editGoodsDetailInfo(orderCode, goodsDetail);
            }
            // 调结算中心接口并推送AC
            OfcFundamentalInformation orderInfo = ofcFundamentalInformationService.selectByKey(orderCode);
            OfcDistributionBasicInfo orderDistInfo = ofcDistributionBasicInfoService.selectByKey(orderCode);
            OfcFinanceInformation orderFinanceInfo = ofcFinanceInformationService.selectByKey(orderCode);
            List<OfcGoodsDetailsInfo> detailsInfos = ofcGoodsDetailsInfoService.queryByOrderCode(orderCode);
            for (OfcGoodsDetailsInfo detailsInfo : detailsInfos) {
                BigDecimal quantity = detailsInfo.getQuantity();
                BigDecimal weight = detailsInfo.getWeight();
                BigDecimal cubage = detailsInfo.getCubage();
                quantityCount = quantityCount.add(PubUtils.isNull(quantity) ? new BigDecimal(0) : quantity);
                weightCount = weightCount.add(PubUtils.isNull(weight) ? new BigDecimal(0) : weight);// 重量总计
                cubageCount = cubageCount.add(PubUtils.isNull(cubage) ? new BigDecimal(0) : cubage);
            }
            orderDistInfo.setQuantity(quantityCount);
            orderDistInfo.setWeight(weightCount);
            orderDistInfo.setCubage(cubageCount.toString());
            ofcDistributionBasicInfoService.update(orderDistInfo);

            // 待审核订单不推送结算、运输
            if (!PENDING_AUDIT.equals(orderStatus)) {
                // 待审核订单不推送到结算和运输中心
                if (!PubUtils.isNull(orderInfo) && !PubUtils.isNull(orderDistInfo) && !PubUtils.isNull(orderFinanceInfo)) {
                    ofcOrderManageService.pushOrderToAc(orderInfo, orderFinanceInfo, orderDistInfo, detailsInfos, null);
                }
                // 暂时注释TFC
                // 推送TFC
                tfcUpdateOrderEdasService.updateTransportOrder(goodsAmountSyncDto);
                // 暂时注释dpc
                try {
                    // 推送调度中心
                    DpcSyncOrderInfoDto syncOrderInfoDto = new DpcSyncOrderInfoDto();
                    syncOrderInfoDto.setOrderNo(orderCode);
                    syncOrderInfoDto.setGoodsNum(quantityCount);
                    syncOrderInfoDto.setWeight(weightCount);
                    syncOrderInfoDto.setVolume(cubageCount.toString());
                    Wrapper<Boolean> res = dpcTransportDocEdasService.syncOrderInfo(syncOrderInfoDto);
                    if (res != null && res.getCode() == Wrapper.SUCCESS_CODE && res.getResult()) {
                        logger.info("调度中心更新交货量同步成功！");
                    } else {
                        throw new BusinessException("调度中心更新交货量失败：{}" + JacksonUtil.toJson(res));
                    }
                } catch (Exception e) {
                    logger.error("推送调度中心交货量同步发生异常：异常详情 => {}", e);
                    throw new BusinessException("推送调度中心交货量同步发生异常!");
                }
            }
        } catch (Exception e) {
            logger.error("订单修改，交货量同步更新发生异常. {}", e);
            throw e;
        }
    }

    /**
     * 修改商品数量、重量、体积
     * @param orderCode
     * @param goodsAmountDetailDto
     */
    @Transactional
    void editGoodsDetailInfo(String orderCode, GoodsAmountDetailDto goodsAmountDetailDto) {
        try {
            OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
            ofcGoodsDetailsInfo.setOrderCode(orderCode);
            // ofcGoodsDetailsInfo.setGoodsCode(goodsAmountDetailDto.getGoodsCode());
            // 修改重量 通过货品行号
            ofcGoodsDetailsInfo.setPaasLineNo(goodsAmountDetailDto.getPassLineNo());
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList = ofcGoodsDetailsInfoService.select(ofcGoodsDetailsInfo);

            if (PubUtils.isNotNullAndBiggerSize(ofcGoodsDetailsInfoList, 0)) {
                ofcGoodsDetailsInfo = ofcGoodsDetailsInfoList.get(0);
                String quantity = goodsAmountDetailDto.getQty();
                String weight = goodsAmountDetailDto.getWeight();
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
                logger.error("订单修改，订单{}查询不到货品{}信息，货品行号{}！", orderCode, goodsAmountDetailDto.getGoodsName(), goodsAmountDetailDto.getPassLineNo());
            }
        } catch (Exception e) {
            logger.error("订单修改，订单中心更新货品信息失败",e.getMessage(),e);
            throw new BusinessException("订单修改，订单中心更新货品信息失败",e.getMessage(),e);
        }
    }

    /**
     * 商品数量、重量、体积修改记录
     * @param orderCode
     * @param goodsAmountDetailDto
     */
    @Transactional
    void addGoodsModifyRecord(String orderCode, GoodsAmountDetailDto goodsAmountDetailDto) {
        try {
            // 行号
            Long lineNo = goodsAmountDetailDto.getPassLineNo();
            String goodsCode = goodsAmountDetailDto.getGoodsCode();
            String goodsName = goodsAmountDetailDto.getGoodsName();
            OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
            ofcGoodsDetailsInfo.setOrderCode(orderCode);
            // 行号
            ofcGoodsDetailsInfo.setPaasLineNo(lineNo);
            // ofcGoodsDetailsInfo.setGoodsCode(goodsCode);
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
                /*if (!PubUtils.isOEmptyOrNull(modifyQuantity)) {
                    String oldQuantity = !PubUtils.isOEmptyOrNull(goodsDetail.getQuantity()) ? goodsDetail.getQuantity().toString() : " ";
                    ofcGoodsRecordModification.setValueBeforeModifyQty(oldQuantity);
                    ofcGoodsRecordModification.setValueAfterModifyQty(modifyQuantity);
                    desc.append("商品").append(goodsCode).append("数量由").append(oldQuantity).append("调整为").append(modifyQuantity).append(";");
                }*/
                // 调整重量
                if (!PubUtils.isOEmptyOrNull(modifyWeight)) {
                    String oldWeight = !PubUtils.isOEmptyOrNull(goodsDetail.getWeight()) ? goodsDetail.getWeight().toString() : " ";
                    ofcGoodsRecordModification.setValueBeforeModifyWet(oldWeight);
                    ofcGoodsRecordModification.setValueAfterModifyWet(modifyWeight);
                    if (PubUtils.isSEmptyOrNull(goodsCode)){
                        desc.append("商品").append("行号").append(lineNo).append("重量由").append(oldWeight).append("调整为").append(modifyWeight).append(";");
                    }else{
                        desc.append("商品").append(goodsCode).append("重量由").append(oldWeight).append("调整为").append(modifyWeight).append(";");
                    }
                }
                // 调整体积
                /*if (!PubUtils.isOEmptyOrNull(modifyVolume)) {
                    String oldVolume = !PubUtils.isOEmptyOrNull(goodsDetail.getCubage()) ? goodsDetail.getCubage().toString() : " ";
                    ofcGoodsRecordModification.setValueBeforeModifyVol(oldVolume);
                    ofcGoodsRecordModification.setValueAfterModifyVol(modifyVolume);
                    desc.append("商品").append(goodsCode).append("体积由").append(oldVolume).append("调整为").append(modifyVolume).append(";");
                }*/
                ofcGoodsRecordModification.setModificationDesc(desc.toString());
                ofcGoodsRecordModificationService.save(ofcGoodsRecordModification);
            }
        } catch (Exception e) {
            logger.error("订单修改，订单中心记录需要更新货品信息失败",e.getMessage(),e);
            throw new BusinessException("订单修改，订单中心记录需要更新货品信息失败",e.getMessage(),e);
        }
    }
}