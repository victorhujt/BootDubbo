package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.csc.FeignCscCustomerAPI;
import com.xescm.ofc.mapper.OfcTransplanInfoMapper;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.PubUtils;
import com.xescm.uam.utils.wrap.Wrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xescm.ofc.constant.OrderConstConstant.*;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class  OfcPlanFedBackServiceImpl implements OfcPlanFedBackService {
    private static final Logger logger = LoggerFactory.getLogger(FeignCscCustomerAPI.class);
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
    @Autowired
    private OfcFundamentalInformationService ofcFundamentalInformationService;

    @Override
    public Wrapper<List<OfcPlanFedBackResult>> planFedBack(OfcPlanFedBackCondition ofcPlanFedBackCondition, String userName) {
        //Map<String,String> mapperMap = new HashMap<String,String>();
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
                logger.info("跟踪状态{}",status);
                logger.info("跟踪状态"+status);
                ofcTransplanStatus.setPlanCode(ofcPlanFedBackCondition.getTransportNo());
                ofcTransplanNewstatus.setPlanCode(ofcPlanFedBackCondition.getTransportNo());
                String orderCode=ofcTransplanInfoService.selectByKey(ofcPlanFedBackCondition.getTransportNo()).getOrderCode();
                OfcTransplanInfo ofcTransplanInfo=ofcTransplanInfoService.selectByKey(transPortNo);
                if(status.equals("")){
                    throw new BusinessException("跟踪状态不可以为空");
                }else {
                    if(traceTime==null){
                        throw new BusinessException("跟踪时间不可以为空");
                    }else {
                        String destination=PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDestinationProvince())
                                +PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDestinationCity())
                                +PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDestinationDistrict())
                                +PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDestinationTown())
                                +PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getReceivingCustomerAddress());
                        logger.info("######发货方目的地为：{}",destination);
                        OfcOrderStatus orderStatus=ofcOrderStatusService.orderStatusSelect(orderCode,"orderCode");
                        String orstatus=orderStatus.getNotes();
                        if(status.equals("已发运")){
                            ofcTransplanNewstatus.setTransportSingleLatestStatus(YIFAYUN);
                            orderStatus.setLastedOperTime(new Date());
                            orderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(traceTime)
                                    +" "+"车辆已发运，发往目的地："+destination);
                            logger.info("跟踪状态已发运");
                        }else if(status.equals("已到达")){
                            ofcTransplanNewstatus.setTransportSingleLatestStatus(YIDAODA);
                            orderStatus.setLastedOperTime(new Date());
                            orderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(traceTime)
                                    +" "+"车辆已到达目的地："+destination);
                            logger.info("跟踪状态已到达");
                        }else if(status.equals("已签收")){
                            ofcTransplanNewstatus.setTransportSingleLatestStatus(YIQIANSHOU);
                            ofcTransplanStatus.setPlannedSingleState(RENWUWANCH);
                            ofcTransplanStatus.setTaskCompletionTime(traceTime);
                            ofcTransplanStatusService.updateByPlanCode(ofcTransplanStatus);
                            orderStatus.setLastedOperTime(new Date());
                            orderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(traceTime)
                                    +" "+"客户已签收");
                            logger.info("跟踪状态已签收");
                            ofcOrderStatusService.save(orderStatus);
                            mapperMap.put("ifFinished","planfinish");
                            mapperMap.put("orderCode",orderCode);
                            List<OfcTransplanInfo> ofcTransplanInfos=ofcTransplanInfoMapper.ofcTransplanInfoScreenList(mapperMap);
                            if(ofcTransplanInfos.size()==0){
                                orderStatus=new OfcOrderStatus();
                                orderStatus.setOrderCode(orderCode);
                                orderStatus.setOrderStatus(HASBEENCOMPLETED);
                                orderStatus.setLastedOperTime(new Date());
                                orderStatus.setStatusDesc("已完成");
                                orderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                                        +" "+"订单已完成");
                                orderStatus.setOperator(userName);
                            }
                            logger.info("跟踪状态已完成");
                        }else if(status.equals("已回单")){
                            ofcTransplanNewstatus.setTransportSingleLatestStatus(YIHUIDAN);
                            orderStatus.setLastedOperTime(new Date());
                            orderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(traceTime)
                                    +" "+"客户已回单");
                            logger.info("跟踪状态已回单");
                        }
                        if(!orstatus.equals(orderStatus.getNotes())){
                            ofcOrderStatusService.save(orderStatus);
                        }
                        ofcTransplanNewstatus.setTransportSingleUpdateTime(traceTime);
                        ofcTransplanNewstatusService.updateByPlanCode(ofcTransplanNewstatus);
                    }

                }
            }catch (Exception e){
                throw new BusinessException(e.getMessage(), e);
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
                    logger.info("##################transPortNo为：{}",transPortNo);
                    OfcTraplanSourceStatus traplanSourceStatus=ofcTraplanSourceStatusService.selectOne(ofcTraplanSourceStatus);
                    if(traplanSourceStatus==null){
                        logger.info("##################traplanSourceStatus为：{}",traplanSourceStatus);
                        throw new BusinessException("获取transSourceStatus实体异常，NULL");
                    }else{
                        String tranNo=traplanSourceStatus.getTransCode();
                        logger.info("##################tranNo为：{}",tranNo);
                    }
                /*if(PubUtils.trimAndNullAsEmpty(tranNo).equals("")){
                    ofcTraplanSourceStatus.setTransCode(ofcSchedulingSingleFeedbackCondition.getDeliveryNo());
                }*/
                }
                /*if(ofcSchedulingSingleFeedbackCondition.getVehical().equals("")){
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
                    }*/
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


                    OfcFundamentalInformation ofcFundamentalInformation=ofcFundamentalInformationService.selectByKey(orderCode);
                    OfcTransplanInfo ofcTransplanInfo=ofcTransplanInfoService.selectByKey(transPortNo);
                    String info="订单";
                    String tag="";
                    if((ofcFundamentalInformation!=null
                            && PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(WITHTHEKABAN))
                            && (ofcTransplanInfo!=null
                            && !PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getBusinessType()).equals(WITHTHEKABAN))){
                        if(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getProgramSerialNumber()).equals("1")){
                            info+="上门提货";
                            tag="上门提货";
                        }else if(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getProgramSerialNumber()).equals("2")
                                || PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getProgramSerialNumber()).equals("3")){
                            info+="二次配送";
                            tag="二次配送";
                        }else{
                            logger.info("计划单序号存在问题:{}",ofcTransplanInfo.getProgramSerialNumber());
                            throw new BusinessException("计划单序号存在问题，调度失败");
                        }
                    }
                    info+="调度完成";
                    if(PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getPlateNumber()).equals("")){
                        ofcDistributionBasicInfo.setPlateNumber(ofcSchedulingSingleFeedbackCondition.getVehical());
                        info+="，安排车辆车牌号：【"+ofcSchedulingSingleFeedbackCondition.getVehical()+"】";
                    }
                    if(PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getDriverName()).equals("")){
                        ofcDistributionBasicInfo.setDriverName(ofcSchedulingSingleFeedbackCondition.getDriver());
                        info+="，司机姓名：【"+ofcSchedulingSingleFeedbackCondition.getDriver()+"】";
                    }
                    if(PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getContactNumber()).equals("")){
                        ofcDistributionBasicInfo.setContactNumber(ofcSchedulingSingleFeedbackCondition.getTel());
                        info+="，联系电话：【"+ofcSchedulingSingleFeedbackCondition.getTel()+"】";
                    }/*else if(PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getTransCode()).equals("")){
                            ofcDistributionBasicInfo.setTransCode(ofcSchedulingSingleFeedbackCondition.getDeliveryNo());
                            }*/
                    logger.info("###############调度状态更新信息为{}",info);
                    ofcDistributionBasicInfoService.updateByOrderCode(ofcDistributionBasicInfo);
                    boolean flag = false;
                    List<OfcOrderStatus> statusList = ofcOrderStatusService.orderStatusScreen(orderCode, "orderCode");
                    if (PubUtils.isNotNullAndBiggerSize(statusList, 0)) {
                        for (OfcOrderStatus status : statusList) {
                            String statusNote = status.getNotes();
                            if (!PubUtils.isSEmptyOrNull(statusNote) && statusNote.startsWith(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ofcSchedulingSingleFeedbackCondition.getCreateTime())
                                    +" 订单"+tag+"调度完成")) {
                                flag = true;
                                break;
                            }
                        }
                    } else {
                        flag = true;
                    }
                    if (!flag) {
                        OfcOrderStatus orderStatus=ofcOrderStatusService.orderStatusSelect(orderCode,"orderCode");
                        orderStatus.setLastedOperTime(new Date());
                        orderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ofcSchedulingSingleFeedbackCondition.getCreateTime())
                                +" "+info);
                        ofcOrderStatusService.save(orderStatus);
                    }
                /*}*/
            }
        }
        return null;
    }
}
