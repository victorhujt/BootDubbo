package com.xescm.ofc.service.impl;

import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.enums.DmsCallbackStatusEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.dms.DmsTransferStatusDto;
import com.xescm.ofc.service.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lyh on 2016/12/9.
 */
@Service
public class OfcDmsCallbackStatusServiceImpl implements OfcDmsCallbackStatusService {

    @Autowired
    private OfcOrderStatusService ofcOrderStatusService;
    @Autowired
    private OfcTransplanStatusService ofcTransplanStatusService;
    @Autowired
    private OfcTransplanNewstatusService ofcTransplanNewstatusService;
    @Autowired
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;
    @Autowired
    private OfcTransplanInfoService ofcTransplanInfoService;
    @Autowired
    private OfcFinanceInformationService ofcFinanceInformationService;

    /**
     * 接收分拣中心回传的状态,并更新相应的运输计划单和订单的状态
     * @param dmsTransferStatusDto
     */
    @Override
    public void receiveDmsCallbackStatus(DmsTransferStatusDto dmsTransferStatusDto) {
        try {
            //根据Dto提供的运输单号和卡班类型查询对应的计划单号, 需要运输基本信息表和运输计划单表联查到对应的运输计划单号
            String transCode = dmsTransferStatusDto.getTransNo();
            String dmsCallbackStatus = dmsTransferStatusDto.getWaybillStatusCode();
            Date operTime = dmsTransferStatusDto.getCreatedTime();
            String description = dmsTransferStatusDto.getDesp();
            if(PubUtils.isSEmptyOrNull(transCode) || PubUtils.isSEmptyOrNull(dmsCallbackStatus) || null == operTime || PubUtils.isSEmptyOrNull(description)){
                throw new BusinessException("DMS回传状态信息不完整!");
            }
            String orderCode = ofcDistributionBasicInfoService.getKabanOrderCodeByTransCode(transCode);
            if(PubUtils.isSEmptyOrNull(orderCode)){
                throw new BusinessException("没有查到所属订单");
            }
            OfcTransplanInfo ofcTransplanInfo = new OfcTransplanInfo();
            ofcTransplanInfo.setOrderCode(orderCode);//SO161210000047
            ofcTransplanInfo.setBusinessType("602");
            List<OfcTransplanInfo> ofcTransplanInfoList = ofcTransplanInfoService.select(ofcTransplanInfo);//去掉作废的
            if(ofcTransplanInfoList.size() < 1){
                throw new BusinessException("查不到相应运输订单");
            }
            String planCode = ofcTransplanInfoList.get(0).getPlanCode();
            //往运输单最新状态表里更新一条记录
            OfcTransplanNewstatus ofcTransplanNewstatus = new OfcTransplanNewstatus();
            BeanUtils.copyProperties(ofcTransplanNewstatus,dmsTransferStatusDto);
            ofcTransplanNewstatus.setPlanCode(planCode);//运输计划单号
            ofcTransplanNewstatus.setTransportSingleLatestStatus(dmsCallbackStatus);//操作类型
            ofcTransplanNewstatus.setOperator(PubUtils.trimAndNullAsEmpty(dmsTransferStatusDto.getCreator()));//操作员
            ofcTransplanNewstatus.setOperUnit(PubUtils.trimAndNullAsEmpty(dmsTransferStatusDto.getUnit()));//操作单位
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ofcTransplanNewstatus.setTransportSingleUpdateTime(operTime);//操作时间
            ofcTransplanNewstatus.setDescription(PubUtils.trimAndNullAsEmpty(dmsTransferStatusDto.getDesp()));//描述信息
            //无论回传哪种状态, 都向运输单最新状态表里更新
            int ofcTransplanNewstatusUpdateNum  = ofcTransplanNewstatusService.updateByPlanCode(ofcTransplanNewstatus);
            if(ofcTransplanNewstatusUpdateNum == 0){
                throw new BusinessException("无法更新相应的运输单状态!");
            }
            //更新订单状态
            //如果运输单状态为已签收,则将对应的运输计划单状态改为已完成
            OfcOrderStatus ofcOrderStatus = ofcOrderStatusService.orderStatusSelect(orderCode,"orderCode");
            ofcOrderStatus.setNotes(sdf.format(operTime) + " " + description);
            ofcOrderStatus.setLastedOperTime(operTime);
            if(StringUtils.equals(DmsCallbackStatusEnum.DMS_STATUS_SIGNED.getCode(),dmsCallbackStatus)){

                //如果有上门取货,则判断上门取货的运输计划单的状态是否是已完成, 如果不是, 则更新该订单的计划单的状态为已完成
                OfcFinanceInformation ofcFinanceInformation = ofcFinanceInformationService.queryByOrderCode(orderCode);
                //如果该卡班订单有上门取货的业务, 那么在卡班单签收的时候判断一下上门取货的运输计划单的状态是否是已完成, 如果不是则将该计划单的状态也改变为已完成
                //取当前订单下的第一截计划单
                List<String> ofcTranCodeListActive = ofcTransplanInfoService.queryPlanCodesByOrderCode(orderCode);
                if(ofcFinanceInformation != null
                        && "1".equals(PubUtils.trimAndNullAsEmpty(ofcFinanceInformation.getPickUpGoods()))
                        && ofcTranCodeListActive.size() > 1){
                    String pickUpTranCode = ofcTranCodeListActive.get(0);
                    for(String transCodeActive : ofcTranCodeListActive){
                        if(transCodeActive.compareTo(pickUpTranCode) == -1){
                            pickUpTranCode = transCodeActive;
                        }
                    }
                    //获取该计划单的最新状态
                    OfcTransplanStatus ofcTransplanStatus = new OfcTransplanStatus();
                    ofcTransplanStatus.setPlanCode(pickUpTranCode);
                    List<OfcTransplanStatus> ofcTransplanStatusPickUpList =  ofcTransplanStatusService.select(ofcTransplanStatus);
                    if(ofcTransplanStatusPickUpList.size() < 1){
                        throw new BusinessException("没有查到该计划单下运输计划单状态");
                    }
                    OfcTransplanStatus ofcTransplanStatusPickUp = ofcTransplanStatusPickUpList.get(0);
                    if(!StringUtils.equals(ofcTransplanStatusPickUp.getPlannedSingleState(),OrderConstConstant.YIQIANSHOU)
                            && !StringUtils.equals(ofcTransplanStatusPickUp.getPlannedSingleState(),OrderConstConstant.YIHUIDAN)){
                        ofcTransplanStatusPickUp.setPlannedSingleState(OrderConstConstant.YIQIANSHOU);
                        ofcTransplanStatusPickUp.setTaskCompletionTime(new Date());
                        ofcTransplanStatusService.updateByPlanCode(ofcTransplanStatusPickUp);
                    }
                }
                //更新卡班运输计划单状态
                OfcTransplanStatus ofcTransplanStatus = new OfcTransplanStatus();
                ofcTransplanStatus.setPlanCode(planCode);
                ofcTransplanStatus.setOrderCode(orderCode);
                ofcTransplanStatus.setPlannedCompletionTime(operTime);
                ofcTransplanStatus.setPlannedSingleState(OrderConstConstant.RENWUWANCH);
                ofcTransplanStatusService.updateByPlanCode(ofcTransplanStatus);
                //再查询该运输计划单是否是该该订单下最后一个待完成的运输计划单, 如果是就把订单的状态改为已完成
                int queryResult = ofcTransplanInfoService.queryNotInvalidAndNotCompleteTransOrder(orderCode);
                if(queryResult == 0){
                    ofcOrderStatusService.save(ofcOrderStatus);
                    ofcOrderStatus.setOrderStatus(OrderConstConstant.HASBEENCOMPLETED);
                    ofcOrderStatus.setStatusDesc("已完成");
                    ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                            +" "+"订单已完成");
                }
            }else if(StringUtils.equals(DmsCallbackStatusEnum.DMS_STATUS_RECEIPT.getCode(),dmsCallbackStatus)){
                //如果是回单状态
            }else if(StringUtils.equals(DmsCallbackStatusEnum.DMS_STATUS_EXCEPTION.getCode(),dmsCallbackStatus)){
                //如果是异常状态
            }else{
                //如果是签收之前的状态,计划单状态不变
            }
            //无论哪种状态都更新订单状态的描述信息
            ofcOrderStatusService.save(ofcOrderStatus);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

    }
}
