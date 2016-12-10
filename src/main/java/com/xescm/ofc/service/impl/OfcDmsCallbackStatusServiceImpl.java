package com.xescm.ofc.service.impl;

import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.domain.OfcTransplanInfo;
import com.xescm.ofc.domain.OfcTransplanNewstatus;
import com.xescm.ofc.domain.OfcTransplanStatus;
import com.xescm.ofc.enums.DmsCallbackStatusEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.dms.DmsTransferRecordDto;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.PubUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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

    /**
     * 接收分拣中心回传的状态,并更新相应的运输计划单和订单的状态
     * @param dmsTransferRecordDto
     */
    @Override
    public void receiveDmsCallbackStatus(DmsTransferRecordDto dmsTransferRecordDto) {
        try {
            //根据Dto提供的运输单号和卡班类型查询对应的计划单号, 需要运输基本信息表和运输计划单表联查到对应的运输计划单号
            String transCode = dmsTransferRecordDto.getTransNo();
            String orderCode = ofcDistributionBasicInfoService.getKabanOrderCodeByTransCode(transCode);
            OfcTransplanInfo ofcTransplanInfo = new OfcTransplanInfo();
            ofcTransplanInfo.setOrderCode(orderCode);
            List<OfcTransplanInfo> ofcTransplanInfoList = ofcTransplanInfoService.select(ofcTransplanInfo);
            String planCode = ofcTransplanInfoList.get(0).getPlanCode();
            //往运输单最新状态表里更新一条记录
            OfcTransplanNewstatus ofcTransplanNewstatus = new OfcTransplanNewstatus();
            ofcTransplanNewstatus.setPlanCode(planCode);//运输计划单号
            ofcTransplanNewstatus.setTransportSingleLatestStatus(dmsTransferRecordDto.getRecordTypeCode());//操作类型
            ofcTransplanNewstatus.setLocation("");//当前位置,暂空
            ofcTransplanNewstatus.setTemperature("");//当前温度,暂空
            ofcTransplanNewstatus.setHumidity("");//当前湿度,暂空
            ofcTransplanNewstatus.setOperator(dmsTransferRecordDto.getCreator());//操作员
            ofcTransplanNewstatus.setOperUnit("");//操作单位,暂空
            Date operTime = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(!PubUtils.isSEmptyOrNull(dmsTransferRecordDto.getCreatedTime())){
                operTime = sdf.parse(dmsTransferRecordDto.getCreatedTime());
            }
            ofcTransplanNewstatus.setTransportSingleUpdateTime(operTime);//操作时间
            ofcTransplanNewstatus.setDescription(dmsTransferRecordDto.getRemark());//描述信息
            //无论回传哪种状态, 都向运输单最新状态表里更新
            ofcTransplanNewstatusService.updateByPlanCode(ofcTransplanNewstatus);
            //更新订单状态
            //如果运输单状态为已签收,则将对应的运输计划单状态改为已完成
            OfcOrderStatus ofcOrderStatus = ofcOrderStatusService.orderStatusSelect(orderCode,"orderCode");
            ofcOrderStatus.setNotes(sdf.format(operTime) + "运输单号:" + transCode + "状态:"  + ofcTransplanNewstatus.getDescription());
            ofcOrderStatus.setLastedOperTime(operTime);
            if(StringUtils.equals(DmsCallbackStatusEnum.DMS_STATUS_SIGNED.getCode(),dmsTransferRecordDto.getRecordTypeCode())){
                //更新运输计划单状态
                OfcTransplanStatus ofcTransplanStatus = new OfcTransplanStatus();
                ofcTransplanStatus.setPlanCode(planCode);
                ofcTransplanStatus.setOrderCode(orderCode);
                ofcTransplanStatus.setPlannedCompletionTime(operTime);
                ofcTransplanStatus.setPlannedSingleState(OrderConstConstant.RENWUWANCH);
                ofcTransplanStatusService.updateByPlanCode(ofcTransplanStatus);
                //再查询该运输计划单是否是该该订单下最后一个待完成的运输计划单, 如果是就把订单的状态改为已完成
                int queryResult = ofcTransplanInfoService.queryNotInvalidAndNotCompleteTransOrder(orderCode);
                if(queryResult == 0){
                    ofcOrderStatus.setOrderStatus(OrderConstConstant.HASBEENCOMPLETED);
                    ofcOrderStatus.setStatusDesc("已完成");
                }
            }else if(StringUtils.equals(DmsCallbackStatusEnum.DMS_STATUS_RECEIPT.getCode(),dmsTransferRecordDto.getRecordTypeCode())){
                //如果是回单状态
            }else if(StringUtils.equals(DmsCallbackStatusEnum.DMS_STATUS_EXCEPTION.getCode(),dmsTransferRecordDto.getRecordTypeCode())){
                //如果是异常状态
            }else{
                //如果是签收之前的状态,计划单状态不变
            }
            //无论哪种状态都更新订单状态
            ofcOrderStatusService.save(ofcOrderStatus);
        } catch (ParseException e) {
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

    }
}
