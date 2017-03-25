package com.xescm.ofc.service.impl;

import com.xescm.base.model.wrap.Wrapper;
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
        if(trimAndNullAsEmpty(goodsAmountSyncDto.getCustCode()).equals("")){
            throw new BusinessException("客户编码不能为空");
        }else if(trimAndNullAsEmpty(goodsAmountSyncDto.getCustOrderCode()).equals("")){
            throw new BusinessException("客户订单编号不能为空");
        }else if(null !=goodsAmountSyncDto.getGoodsAmountDetailDtoList()
                && goodsAmountSyncDto.getGoodsAmountDetailDtoList().size()==0){
            throw new BusinessException("货品信息不能为空");
        }
        OfcFundamentalInformation ofcFundamentalInformation = new OfcFundamentalInformation();
        ofcFundamentalInformation.setCustCode(goodsAmountSyncDto.getCustCode());
        ofcFundamentalInformation.setCustName(goodsAmountSyncDto.getCustName());
        ofcFundamentalInformation.setCustOrderCode(goodsAmountSyncDto.getCustOrderCode());
        List<OfcFundamentalInformation> ofcFundamentalInformationList = ofcFundamentalInformationService.select(ofcFundamentalInformation);
        for (OfcFundamentalInformation anOfcFundamentalInformationList : ofcFundamentalInformationList) {
            OfcOrderNewstatus ofcOrderNewstatus = ofcOrderNewstatusService.selectByKey(anOfcFundamentalInformationList.getOrderCode());
            if (null != ofcOrderNewstatus && !trimAndNullAsEmpty(ofcOrderNewstatus.getOrderLatestStatus()).equals("")) {
                if(trimAndNullAsEmpty(ofcOrderNewstatus.getOrderLatestStatus()).equals(HASBEEN_COMPLETED)
                        || trimAndNullAsEmpty(ofcOrderNewstatus.getOrderLatestStatus()).equals(HASBEEN_CANCELED)){
                    for (GoodsAmountDetailDto goodsAmountDetailDto:goodsAmountSyncDto.getGoodsAmountDetailDtoList()) {
                        if(trimAndNullAsEmpty(goodsAmountDetailDto.getGoodsName()).equals("")){
                            throw new BusinessException("货品名称不能为空");
                        }else if(trimAndNullAsEmpty(goodsAmountDetailDto.getGoodsCode()).equals("")){
                            throw new BusinessException("货品编号不能为空");
                        }
                        //增加记录到货品修改记录表
                        addGoodsModifyRecord(goodsAmountDetailDto,ofcFundamentalInformation);
                    }
                }else {
                    for (GoodsAmountDetailDto goodsAmountDetailDto:goodsAmountSyncDto.getGoodsAmountDetailDtoList()) {
                        if(trimAndNullAsEmpty(goodsAmountDetailDto.getGoodsName()).equals("")){
                            throw new BusinessException("货品名称不能为空");
                        }else if(trimAndNullAsEmpty(goodsAmountDetailDto.getGoodsCode()).equals("")){
                            throw new BusinessException("货品编号不能为空");
                        }
                        //修改货品信息
                        editGoodsDetailInfo(goodsAmountDetailDto);
                    }
                    //调结算中心接口并推送AC
                    //再次推送TFC
                    tfcUpdateOrderEdasService.updateTransportOrder(goodsAmountSyncDto);
                }
            }
        }
        return null;
    }

    private void editGoodsDetailInfo(GoodsAmountDetailDto goodsAmountDetailDto){
        try {
            OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
            ofcGoodsDetailsInfo.setGoodsCode(goodsAmountDetailDto.getGoodsCode());
            ofcGoodsDetailsInfo.setGoodsName(goodsAmountDetailDto.getGoodsName());
            ofcGoodsDetailsInfo.setUnit(trimAndNullAsEmpty(goodsAmountDetailDto.getUnit()));
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList = ofcGoodsDetailsInfoService.select(ofcGoodsDetailsInfo);
            if(null!=ofcGoodsDetailsInfoList &&
                    ofcGoodsDetailsInfoList.size()>0){
                ofcGoodsDetailsInfo=ofcGoodsDetailsInfoList.get(0);
                if(!trimAndNullAsEmpty(goodsAmountDetailDto.getQty()).equals("")){
                    ofcGoodsDetailsInfo.setQuantity(new BigDecimal(goodsAmountDetailDto.getQty()));
                }else if(!trimAndNullAsEmpty(goodsAmountDetailDto.getWeight()).equals("")){
                    ofcGoodsDetailsInfo.setWeight(new BigDecimal(goodsAmountDetailDto.getWeight()));
                }else if(!trimAndNullAsEmpty(goodsAmountDetailDto.getVolume()).equals("")){
                    ofcGoodsDetailsInfo.setCubage(new BigDecimal(goodsAmountDetailDto.getVolume()));
                }
                ofcGoodsDetailsInfoService.update(ofcGoodsDetailsInfo);
            }
        } catch (Exception e) {
            logger.error("订单中心更新货品信息失败",e.getMessage(),e);
            throw new BusinessException("订单中心更新货品信息失败",e.getMessage(),e);
        }
    }

    private void addGoodsModifyRecord(GoodsAmountDetailDto goodsAmountDetailDto,OfcFundamentalInformation ofcFundamentalInformation){
        try {
            OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
            ofcGoodsDetailsInfo.setGoodsCode(goodsAmountDetailDto.getGoodsCode());
            ofcGoodsDetailsInfo.setGoodsName(goodsAmountDetailDto.getGoodsName());
            ofcGoodsDetailsInfo.setUnit(trimAndNullAsEmpty(goodsAmountDetailDto.getUnit()));
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList = ofcGoodsDetailsInfoService.select(ofcGoodsDetailsInfo);
            if(null!=ofcGoodsDetailsInfoList &&
                    ofcGoodsDetailsInfoList.size()>0){
                OfcGoodsRecordModification ofcGoodsRecordModification = new OfcGoodsRecordModification();
                ofcGoodsRecordModification.setOrderCode(ofcFundamentalInformation.getOrderCode());
                if(null == ofcGoodsDetailsInfoList.get(0).getQuantity()){
                    ofcGoodsRecordModification.setValueBeforeModifyQty("");
                }else {
                    ofcGoodsRecordModification.setValueBeforeModifyQty(trimAndNullAsEmpty(ofcGoodsDetailsInfoList.get(0).getQuantity().toString()));
                }
                if(null == goodsAmountDetailDto.getQty()){
                    ofcGoodsRecordModification.setValueAfterModifyQty("");
                }else {
                    ofcGoodsRecordModification.setValueAfterModifyQty(trimAndNullAsEmpty(goodsAmountDetailDto.getQty()));
                }
                if(null == ofcGoodsDetailsInfoList.get(0).getWeight()){
                    ofcGoodsRecordModification.setValueBeforeModifyWet("");
                }else {
                    ofcGoodsRecordModification.setValueBeforeModifyWet(trimAndNullAsEmpty(ofcGoodsDetailsInfoList.get(0).getWeight().toString()));
                }
                if(null == goodsAmountDetailDto.getWeight()){
                    ofcGoodsRecordModification.setValueAfterModifyWet("");
                }else {
                    ofcGoodsRecordModification.setValueAfterModifyWet(trimAndNullAsEmpty(goodsAmountDetailDto.getVolume()));
                }
                if(null == ofcGoodsDetailsInfoList.get(0).getCubage()){
                    ofcGoodsRecordModification.setValueBeforeModifyVol("");
                }else {
                    ofcGoodsRecordModification.setValueBeforeModifyVol(trimAndNullAsEmpty(ofcGoodsDetailsInfoList.get(0).getCubage().toString()));
                }
                if(null == goodsAmountDetailDto.getVolume()){
                    ofcGoodsRecordModification.setValueAfterModifyVol("");
                }else {
                    ofcGoodsRecordModification.setValueAfterModifyVol(trimAndNullAsEmpty(goodsAmountDetailDto.getVolume()));
                }
                ofcGoodsRecordModificationService.save(ofcGoodsRecordModification);
            }
        } catch (Exception e) {
            logger.error("订单中心记录需要更新货品信息失败",e.getMessage(),e);
            throw new BusinessException("订单中心记录需要更新货品信息失败",e.getMessage(),e);
        }
    }
}
