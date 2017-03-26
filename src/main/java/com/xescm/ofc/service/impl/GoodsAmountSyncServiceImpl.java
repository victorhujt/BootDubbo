package com.xescm.ofc.service.impl;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.domain.OfcGoodsRecordModification;
import com.xescm.ofc.domain.OfcOrderNewstatus;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.*;
import com.xescm.tfc.edas.model.dto.ofc.req.GoodsAmountDetailDto;
import com.xescm.tfc.edas.model.dto.ofc.req.GoodsAmountSyncDto;
import com.xescm.tfc.edas.service.TfcUpdateOrderEdasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

import static com.xescm.core.utils.PubUtils.trimAndNullAsEmpty;
import static com.xescm.ofc.constant.OrderConstConstant.HASBEEN_CANCELED;
import static com.xescm.ofc.constant.OrderConstConstant.HASBEEN_COMPLETED;

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
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Resource
    private TfcUpdateOrderEdasService tfcUpdateOrderEdasService;
    @Resource
    private OfcGoodsRecordModificationService ofcGoodsRecordModificationService;

    public Wrapper<?> GoodsAmountSync(GoodsAmountSyncDto goodsAmountSyncDto){
        Wrapper result = null;
        String custCode = goodsAmountSyncDto.getCustCode();
        String custOrderCode = goodsAmountSyncDto.getCustOrderCode();
        List<GoodsAmountDetailDto> details = goodsAmountSyncDto.getGoodsAmountDetailDtoList();
        if(PubUtils.isOEmptyOrNull(custCode)){
            throw new BusinessException("客户编码不能为空");
        }else if(PubUtils.isOEmptyOrNull(custOrderCode)){
            throw new BusinessException("客户订单编号不能为空");
        }else if(PubUtils.isNull(details) || PubUtils.isNotNullAndSmallerSize(details, 1)){
            throw new BusinessException("货品信息不能为空");
        }
        // 查询订单
        OfcFundamentalInformation ofcFundamentalInfo = new OfcFundamentalInformation();
        ofcFundamentalInfo.setCustCode(custCode);
        ofcFundamentalInfo.setCustName(goodsAmountSyncDto.getCustName());
        ofcFundamentalInfo.setCustOrderCode(custOrderCode);
        try {
            List<OfcFundamentalInformation> orderList = ofcFundamentalInformationService.select(ofcFundamentalInfo);
            if (PubUtils.isNotNullAndBiggerSize(orderList, 0)) {
                for (OfcFundamentalInformation ofcOrder : orderList) {
                    String orderCode = ofcOrder.getOrderCode();
                    OfcOrderNewstatus orderStatusObj = ofcOrderNewstatusService.selectByKey(orderCode);
                    if (!PubUtils.isOEmptyOrNull(orderStatusObj)) {
                        String orderStatus = orderStatusObj.getOrderLatestStatus();
                        // 订单取消或者已完成，不调整
                        if (!PubUtils.isOEmptyOrNull(orderStatus) && (HASBEEN_COMPLETED.equals(orderStatus)
                            || HASBEEN_CANCELED.equals(orderStatus))) {
                            logger.info("订单{}当前状态为{},不允许调整数量!", orderCode, orderStatus);
                            result = WrapMapper.wrap(Wrapper.ERROR_CODE, "订单已经完成或取消,不允许调整数量!");
                        } else if (!PubUtils.isOEmptyOrNull(orderStatus)) {
                            //调结算中心接口并推送AC

                            for (GoodsAmountDetailDto goodsDetail : details) {
                                if (PubUtils.isOEmptyOrNull(goodsDetail.getGoodsName())) {
                                    throw new BusinessException("货品名称不能为空");
                                } else if (PubUtils.isOEmptyOrNull(goodsDetail.getGoodsCode())) {
                                    throw new BusinessException("货品编号不能为空");
                                }
                                //修改货品信息
                                editGoodsDetailInfo(ofcOrder.getOrderCode(), goodsDetail);
                            }
                            //再次推送TFC
                            tfcUpdateOrderEdasService.updateTransportOrder(goodsAmountSyncDto);
                            result = WrapMapper.ok();
                        }
                    } else {
                        logger.error("订单{}查询不到当前状态.", ofcOrder.getOrderCode());
                    }
                }
            } else {
                result = WrapMapper.wrap(Wrapper.ERROR_CODE, "未查询到客户订单{}信息.");
                logger.error("未查询到客户订单{}信息.", custOrderCode);
            }
        } catch (BusinessException e) {
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
            logger.error(e.getMessage());
        } catch (Exception e) {
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, "更新交货数量发生内部异常");
            logger.error("更新交货数量发生内部异常 {}", e);
        }
        return result;
    }

    private void editGoodsDetailInfo(String orderCode, GoodsAmountDetailDto goodsAmountDetailDto){
        try {
            OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
            ofcGoodsDetailsInfo.setOrderCode(orderCode);
            ofcGoodsDetailsInfo.setGoodsCode(goodsAmountDetailDto.getGoodsCode());
            ofcGoodsDetailsInfo.setGoodsName(goodsAmountDetailDto.getGoodsName());
            ofcGoodsDetailsInfo.setUnit(trimAndNullAsEmpty(goodsAmountDetailDto.getUnit()));
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList = ofcGoodsDetailsInfoService.select(ofcGoodsDetailsInfo);
            if(PubUtils.isNotNullAndBiggerSize(ofcGoodsDetailsInfoList, 0)){
                ofcGoodsDetailsInfo=ofcGoodsDetailsInfoList.get(0);
                String quantity = goodsAmountDetailDto.getQty();
                String weight = goodsAmountDetailDto.getWeight();
                String volume = goodsAmountDetailDto.getVolume();
                if(!PubUtils.isOEmptyOrNull(quantity)){
                    ofcGoodsDetailsInfo.setQuantity(new BigDecimal(quantity));
                }else if(!PubUtils.isOEmptyOrNull(weight)){
                    ofcGoodsDetailsInfo.setWeight(new BigDecimal(weight));
                }else if(!PubUtils.isOEmptyOrNull(volume)){
                    ofcGoodsDetailsInfo.setCubage(new BigDecimal(volume));
                }
                ofcGoodsDetailsInfoService.update(ofcGoodsDetailsInfo);
            } else {
                logger.error("订单{}查询不到货品{}信息！", orderCode, goodsAmountDetailDto.getGoodsCode());
            }
        } catch (Exception e) {
            logger.error("订单中心更新货品信息失败",e.getMessage(),e);
            throw new BusinessException("订单中心更新货品信息失败",e.getMessage(),e);
        }
    }

    private void addGoodsModifyRecord(GoodsAmountDetailDto goodsAmountDetailDto,OfcFundamentalInformation ofcFundamentalInformation){
        try {
            String orderCode = ofcFundamentalInformation.getOrderCode();
            OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
            ofcGoodsDetailsInfo.setOrderCode(orderCode);
            ofcGoodsDetailsInfo.setGoodsCode(goodsAmountDetailDto.getGoodsCode());
            ofcGoodsDetailsInfo.setGoodsName(goodsAmountDetailDto.getGoodsName());
            ofcGoodsDetailsInfo.setUnit(trimAndNullAsEmpty(goodsAmountDetailDto.getUnit()));
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList = ofcGoodsDetailsInfoService.select(ofcGoodsDetailsInfo);
            if(PubUtils.isNotNullAndBiggerSize(ofcGoodsDetailsInfoList, 0)){
                OfcGoodsRecordModification ofcGoodsRecordModification = new OfcGoodsRecordModification();
                ofcGoodsRecordModification.setOrderCode(orderCode);
                OfcGoodsDetailsInfo goodsDetail = ofcGoodsDetailsInfoList.get(0);
                StringBuffer desc = new StringBuffer();
                String modifyQuantity = goodsAmountDetailDto.getQty();
                String modifyWeight = goodsAmountDetailDto.getWeight();
                String modifyVolume = goodsAmountDetailDto.getVolume();
                // 调整数量
                if(!PubUtils.isOEmptyOrNull(modifyQuantity)){
                    String oldQuantity = goodsDetail.getQuantity().toString();
                    ofcGoodsRecordModification.setValueBeforeModifyQty(oldQuantity);
                    ofcGoodsRecordModification.setValueAfterModifyQty(modifyQuantity);
                    desc.append("商品").append(goodsAmountDetailDto.getGoodsCode()).append("数量由")
                        .append(oldQuantity).append("调整为").append(modifyQuantity).append(";");
                }
                // 调整重量
                if(!PubUtils.isOEmptyOrNull(modifyWeight)){
                    String oldWeight = goodsDetail.getWeight().toString();
                    ofcGoodsRecordModification.setValueBeforeModifyWet(oldWeight);
                    ofcGoodsRecordModification.setValueAfterModifyWet(modifyWeight);
                    desc.append("商品").append(goodsAmountDetailDto.getGoodsCode()).append("重量由")
                        .append(oldWeight).append("调整为").append(modifyWeight).append(";");
                }
                // 调整体积
                if(!PubUtils.isOEmptyOrNull(modifyVolume)){
                    String oldVolume = goodsDetail.getCubage().toString();
                    ofcGoodsRecordModification.setValueBeforeModifyVol(oldVolume);
                    ofcGoodsRecordModification.setValueAfterModifyVol(modifyVolume);
                    desc.append("商品").append(goodsAmountDetailDto.getGoodsCode()).append("体积由")
                        .append(oldVolume).append("调整为").append(modifyVolume).append(";");
                }
                ofcGoodsRecordModificationService.save(ofcGoodsRecordModification);
            }
        } catch (Exception e) {
            logger.error("订单中心记录需要更新货品信息失败",e.getMessage(),e);
            throw new BusinessException("订单中心记录需要更新货品信息失败",e.getMessage(),e);
        }
    }
}
