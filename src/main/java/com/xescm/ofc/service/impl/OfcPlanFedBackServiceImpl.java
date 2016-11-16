package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcOrderScreenMapper;
import com.xescm.ofc.mapper.OfcTransplanInfoMapper;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.PubUtils;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xescm.ofc.enums.OrderConstEnum.*;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcPlanFedBackServiceImpl implements OfcPlanFedBackService {

    @Autowired
    private OfcTransplanInfoMapper ofcTransplanInfoMapper;
    @Autowired
    private OfcTransplanInfoService ofcTransplanInfoService;
    @Autowired
    private OfcTransplanStatusService ofcTransplanStatusService;
    @Autowired
    private OfcOrderStatusService ofcOrderStatusService;
    @Autowired
    private OfcTransplanNewstatusService ofcTransplanNewstatusService;
    @Autowired
    private OfcTraplanSourceStatusService ofcTraplanSourceStatusService;
    @Autowired
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;

    @Override
    public Wrapper<List<OfcPlanFedBackResult>> planFedBack(OfcPlanFedBackCondition ofcPlanFedBackCondition,String userName) {
        Map<String,String> mapperMap = new HashMap<>();
        String transPortNo= PubUtils.trimAndNullAsEmpty(ofcPlanFedBackCondition.getTransportNo());
        String status= PubUtils.trimAndNullAsEmpty(ofcPlanFedBackCondition.getStatus());
        Date traceTime= ofcPlanFedBackCondition.getTraceTime();
        OfcTransplanNewstatus ofcTransplanNewstatus=new OfcTransplanNewstatus();
        OfcTransplanStatus ofcTransplanStatus=new OfcTransplanStatus();
        if(transPortNo.equals("")){
            throw new BusinessException("运输计划单号不可以为空");
        }else{
            try{
                ofcTransplanStatus.setPlanCode(ofcPlanFedBackCondition.getTransportNo());
                ofcTransplanNewstatus.setPlanCode(ofcPlanFedBackCondition.getTransportNo());
                String orderCode=ofcTransplanInfoService.selectByKey(ofcPlanFedBackCondition.getTransportNo()).getOrderCode();
                if(status.equals("")){
                    throw new BusinessException("跟踪状态不可以为空");
                }else {
                    if(traceTime==null){
                        throw new BusinessException("跟踪时间不可以为空");
                    }else {
                        OfcOrderStatus orderStatus=ofcOrderStatusService.orderStatusSelect(orderCode,"orderCode");
                        if(status.equals("已发运")){
                            ofcTransplanNewstatus.setTransportSingleLatestStatus(YIFAYUN);
                            orderStatus.setLastedOperTime(traceTime);
                            orderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(traceTime)
                                    +" "+"订单已发运");
                        }else if(status.equals("已到达")){
                            ofcTransplanNewstatus.setTransportSingleLatestStatus(YIDAODA);
                            ofcTransplanStatus.setPlannedSingleState(RENWUWANCH);
                            ofcTransplanStatus.setTaskCompletionTime(traceTime);
                            ofcTransplanStatusService.updateByPlanCode(ofcTransplanStatus);
                            orderStatus.setLastedOperTime(traceTime);
                            orderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(traceTime)
                                    +" "+"订单已到达");
                            ofcOrderStatusService.save(orderStatus);
                            mapperMap.put("ifFinished","planfinish");
                            mapperMap.put("orderCode",orderCode);
                            List<OfcTransplanInfo> ofcTransplanInfos=ofcTransplanInfoMapper.ofcTransplanInfoScreenList(mapperMap);
                            if(ofcTransplanInfos.size()==0){
                                orderStatus=new OfcOrderStatus();
                                orderStatus.setOrderCode(orderCode);
                                orderStatus.setOrderStatus(HASBEENCOMPLETED);
                                orderStatus.setLastedOperTime(new Date());
                                orderStatus.setStatusDesc(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                                        +" "+"订单已完成");
                                orderStatus.setOperator(userName);
                            }
                        }else if(status.equals("已签收")){
                            ofcTransplanNewstatus.setTransportSingleLatestStatus(YIQIANSHOU);
                            orderStatus.setLastedOperTime(traceTime);
                            orderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(traceTime)
                                    +" "+"订单已签收");
                        }else if(status.equals("已回单")){
                            ofcTransplanNewstatus.setTransportSingleLatestStatus(YIHUIDAN);
                            orderStatus.setLastedOperTime(traceTime);
                            orderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(traceTime)
                                    +" "+"订单已回单");
                        }
                        ofcOrderStatusService.save(orderStatus);
                        ofcTransplanNewstatus.setTransportSingleUpdateTime(traceTime);
                        ofcTransplanNewstatusService.updateByPlanCode(ofcTransplanNewstatus);
                    }

                }
            }catch (Exception e){
                throw new BusinessException(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Wrapper<List<OfcPlanFedBackResult>> schedulingSingleFeedback(OfcSchedulingSingleFeedbackCondition ofcSchedulingSingleFeedbackCondition, String userName) {
        for(int i=0;i<ofcSchedulingSingleFeedbackCondition.getTransportNo().size();i++){
            String transPortNo= PubUtils.trimAndNullAsEmpty(ofcSchedulingSingleFeedbackCondition.getTransportNo().get(0));
            if(transPortNo.equals("")){
                throw new BusinessException("运输计划单号不可以为空");
            }else{
                OfcTraplanSourceStatus ofcTraplanSourceStatus=new OfcTraplanSourceStatus();
                ofcTraplanSourceStatus.setPlanCode(transPortNo);
                if(ofcSchedulingSingleFeedbackCondition.getDeliveryNo().equals("")){
                    throw new BusinessException("调度单号不可以为空");
                }else {
                    String tranNo=ofcTraplanSourceStatusService.selectOne(ofcTraplanSourceStatus).getTransCode();
                /*if(PubUtils.trimAndNullAsEmpty(tranNo).equals("")){
                    ofcTraplanSourceStatus.setTransCode(ofcSchedulingSingleFeedbackCondition.getDeliveryNo());
                }*/
                }
                if(ofcSchedulingSingleFeedbackCondition.getVehical().equals("")){
                    throw new BusinessException("车牌号不可以为空");
                }else {
                    ofcTraplanSourceStatus.setPlateNumber(ofcSchedulingSingleFeedbackCondition.getVehical());
                    if(ofcSchedulingSingleFeedbackCondition.getDriver()==null){
                        throw new BusinessException("司机姓名不可以为空");
                    }else {
                        ofcTraplanSourceStatus.setDriverName(ofcSchedulingSingleFeedbackCondition.getDriver());
                        if(ofcSchedulingSingleFeedbackCondition.getTel()==null){
                            throw new BusinessException("联系电话不可以为空");
                        }else {
                            ofcTraplanSourceStatus.setContactNumber(ofcSchedulingSingleFeedbackCondition.getTel());
                        }
                    }
                    ofcTraplanSourceStatusService.updateByPlanCode(ofcTraplanSourceStatus);
                    if(ofcSchedulingSingleFeedbackCondition.getCreateTime()==null){
                        throw new BusinessException("调度单时间不可以为空");
                    }else {
                        OfcTransplanNewstatus ofcTransplanNewstatus=new OfcTransplanNewstatus();
                        ofcTransplanNewstatus.setPlanCode(transPortNo);
                        ofcTransplanNewstatus.setTransportSingleLatestStatus(YIDIAODU);
                        ofcTransplanNewstatus.setTransportSingleUpdateTime(ofcSchedulingSingleFeedbackCondition.getCreateTime());
                        ofcTransplanNewstatusService.updateByPlanCode(ofcTransplanNewstatus);
                        OfcTransplanStatus ofcTransplanStatus=new OfcTransplanStatus();
                        ofcTransplanStatus.setPlanCode(transPortNo);
                        ofcTransplanStatus.setTaskStartTime(ofcSchedulingSingleFeedbackCondition.getCreateTime());
                        ofcTransplanStatus.setPlannedSingleState(RENWUZHONG);
                        ofcTransplanStatusService.updateByPlanCode(ofcTransplanStatus);
                    }
                    String orderCode=ofcTransplanInfoService.selectByKey(transPortNo).getOrderCode();
                    OfcDistributionBasicInfo ofcDistributionBasicInfo
                            =ofcDistributionBasicInfoService.distributionBasicInfoSelect(orderCode);
                    if(PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getPlateNumber()).equals("")){
                        ofcDistributionBasicInfo.setPlateNumber(ofcSchedulingSingleFeedbackCondition.getVehical());
                    }
                    if(PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getDriverName()).equals("")){
                        ofcDistributionBasicInfo.setDriverName(ofcSchedulingSingleFeedbackCondition.getDriver());
                    }
                    if(PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getContactNumber()).equals("")){
                        ofcDistributionBasicInfo.setContactNumber(ofcSchedulingSingleFeedbackCondition.getTel());
                    }/*else if(PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getTransCode()).equals("")){
                    ofcDistributionBasicInfo.setTransCode(ofcSchedulingSingleFeedbackCondition.getDeliveryNo());
                }*/
                    ofcDistributionBasicInfoService.updateByOrderCode(ofcDistributionBasicInfo);
                    OfcOrderStatus orderStatus=ofcOrderStatusService.orderStatusSelect(orderCode,"orderCode");
                    if(!(orderStatus.getLastedOperTime().toString().equals(ofcSchedulingSingleFeedbackCondition.getCreateTime().toString()))
                            ||!(orderStatus.getNotes().endsWith("调度"))){
                        orderStatus.setLastedOperTime(ofcSchedulingSingleFeedbackCondition.getCreateTime());
                        orderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ofcSchedulingSingleFeedbackCondition.getCreateTime())
                                +" "+"订单已调度");
                        ofcOrderStatusService.save(orderStatus);
                    }
                }
            }
        }
        return null;
    }
}
