package com.xescm.ofc.service.impl;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcTransplanInfoMapper;
import com.xescm.ofc.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.xescm.ofc.constant.OrderConstConstant.*;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class  OfcPlanFedBackServiceImpl implements OfcPlanFedBackService {
    private static final Logger logger = LoggerFactory.getLogger(OfcPlanFedBackServiceImpl.class);
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
    @Autowired
    private OfcOrderManageService ofcOrderManageService;
    @Autowired
    private OfcSiloproStatusService ofcSiloproStatusService ;
    @Autowired
    private OfcTransplanInfoMapper ofcTransplanInfoMapper;


    /**
     * 运输计划单状态反馈
     * @param ofcPlanFedBackCondition
     * @param userName
     * @return
     */
    @Override
    public Wrapper<List<OfcPlanFedBackResult>> planFedBack(OfcPlanFedBackCondition ofcPlanFedBackCondition, String userName) {
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
                if(ofcTransplanInfoService.selectByKey(ofcPlanFedBackCondition.getTransportNo())==null){
                    throw new BusinessException("传送运输单号信息失败，查不到相关计划单");
                }
                OfcTransplanInfo ofcTransplanInfot=ofcTransplanInfoService.selectByKey(ofcPlanFedBackCondition.getTransportNo());
                String orderCode=ofcTransplanInfot.getOrderCode();
                String programSerialNumber = ofcTransplanInfot.getProgramSerialNumber();
                mapperMap.put("orderCode",orderCode);
                //订单下所有非作废计划单列表
                List<OfcTransplanInfo> ofcTransplanInfoList=ofcTransplanInfoMapper.ofcTransplanInfoScreenList(mapperMap);
                int serialNumberCount = -1;
                if(ofcTransplanInfoList!=null){//订单下运输计划单数量
                    serialNumberCount=ofcTransplanInfoList.size();
                }
                mapperMap.put("ifFinished","planfinish");
                //当前非作废未完成的运输计划单的LIST
                List<OfcTransplanInfo> ofcTransplanInfos=ofcTransplanInfoMapper.ofcTransplanInfoScreenList(mapperMap);
                OfcTransplanInfo ofcTransplanInfo=ofcTransplanInfoService.selectByKey(transPortNo);
                if(ofcTransplanInfo==null){
                    logger.info("相关运输计划单号为：{},未查询到相关计划单",transPortNo);
                    throw new BusinessException("根据所反馈的运输单号未查询到相关计划单！");
                }
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
                        List<OfcOrderStatus> statusList = ofcOrderStatusService.orderStatusScreen(orderCode, "orderCode");
                        String orstatus=orderStatus.getNotes();
                        boolean flag=false;
                        if(status.equals("已发运")){
                            flag=false;
                            flag=checkStatus(flag,statusList,"start",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(traceTime)
                                            +" "+"车辆已发运，发往目的地：");
                            if(!flag){
                                ofcTransplanNewstatus.setTransportSingleLatestStatus(YIFAYUN);
                                orderStatus.setLastedOperTime(traceTime);
                                orderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(traceTime)
                                        +" "+"车辆已发运，发往目的地："+destination);
                                logger.info("跟踪状态已发运");
                            }
                        }else if(status.equals("已到达")){
                            flag=false;
                            flag=checkStatus(flag,statusList,"start",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(traceTime)
                                    +" "+"车辆已到达目的地：");
                            if(!flag){
                                ofcTransplanNewstatus.setTransportSingleLatestStatus(YIDAODA);
                                orderStatus.setLastedOperTime(traceTime);
                                orderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(traceTime)
                                        +" "+"车辆已到达目的地："+destination);
                                logger.info("跟踪状态已到达");
                            }
                        }else if(status.equals("已签收")
                                //当前计划单序号等于订单下计划单数量，表示最后一个计划单
                                && (PubUtils.trimAndNullAsEmpty(programSerialNumber).equals(serialNumberCount+""))){
                            Date now = new Date();
                            flag=false;
                            flag=checkStatus(flag,statusList,"end","客户已签收");
                            if(!flag){
                                ofcTransplanNewstatus.setTransportSingleLatestStatus(YIQIANSHOU);
                                ofcTransplanStatus.setPlannedSingleState(RENWUWANCH);
                                ofcTransplanStatus.setTaskCompletionTime(traceTime);

                                orderStatus.setId(UUID.randomUUID().toString().replace("-", ""));
                                orderStatus.setLastedOperTime(traceTime);
                                orderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(traceTime)
                                        +" "+"客户已签收");
                                orderStatus.setCreationTime(new Date());
                                logger.info("跟踪状态已签收");
                                ofcOrderStatusService.save(orderStatus);

                                //当前订单下的未完成未作废的仓储计划单的订单状态
                                List<OfcSiloproStatus> ofcSiloproStatusList = ofcSiloproStatusService.queryUncompletedPlanCodesByOrderCode(orderCode);
                                OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
                                if(null == ofcFundamentalInformation){
                                    throw new BusinessException("无法查到该订单相应基本信息");
                                }
                                //当前所有未作废的运输计划单单号
                                List<String> planCodesByOrderCode = ofcTransplanInfoService.queryPlanCodesByOrderCode(orderCode);
                                //当前所有未作废未完成的运输计划单单号
                                List<String> planCodesUncompletedByOrderCode = ofcTransplanInfoService.queryUncompletedPlanCodesByOrderCode(orderCode);
                                //如果是卡班订单, 而且是拆三段的第三段,拆两段的第二段
                                if(ofcFundamentalInformation != null
                                        && PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getOrderType()).equals(TRANSPORTORDER)
                                        && PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(WITHTHEKABAN)
                                        && planCodesByOrderCode.size() <= 3 && planCodesByOrderCode.size() >=2){
                                    String lastPlanCode = planCodesByOrderCode.get(0);
                                    for(String planCode : planCodesByOrderCode){
                                        if(planCode.compareTo(lastPlanCode) == 1){
                                            lastPlanCode = planCode;
                                        }
                                    }
                                    if(lastPlanCode.equals(ofcPlanFedBackCondition.getTransportNo()) && ofcTransplanInfos.size() > 0){
                                        //如果是最后一个,且未完成的运输计划单超过1个, 就将其他运输计划单的状态也改为已完成.
                                        for(String planCode : planCodesByOrderCode){
                                            //其他未完成的运输计划单的状态如果是未完成
                                            if(!planCode.equals(lastPlanCode) && planCodesUncompletedByOrderCode.contains(planCode) ){
                                                OfcTransplanStatus ofcTransplanStatusOther = new OfcTransplanStatus();
                                                ofcTransplanStatusOther.setPlanCode(planCode);
                                                if(ofcTransplanStatusService.select(ofcTransplanStatusOther) != null
                                                        && ofcTransplanStatusService.select(ofcTransplanStatusOther).size() > 0){
                                                    ofcTransplanStatusOther = ofcTransplanStatusService.select(ofcTransplanStatusOther).get(0);
                                                    ofcTransplanStatusOther.setPlannedSingleState(RENWUWANCH);
                                                    ofcTransplanStatusOther.setTaskCompletionTime(traceTime);
                                                    int i = ofcTransplanStatusService.updateByPlanCode(ofcTransplanStatusOther);
                                                    if(i < 1){
                                                        throw new BusinessException("更新运输计划单状态失败");
                                                    }
                                                }else {
                                                    throw new BusinessException("无法查到该计划单状态");
                                                }
                                            }
                                        }
                                        orderStatus=new OfcOrderStatus();
                                        orderStatus.setOrderCode(orderCode);
                                        orderStatus.setOrderStatus(HASBEENCOMPLETED);
                                        orderStatus.setLastedOperTime(now);
                                        orderStatus.setStatusDesc("已完成");
                                        orderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now)
                                                +" "+"订单已完成");
                                        orderStatus.setOperator(userName);
                                        if(null == ofcFundamentalInformation.getFinishedTime()){
                                            ofcFundamentalInformation.setFinishedTime(now);
                                        }
                                        ofcFundamentalInformationService.update(ofcFundamentalInformation);
                                    }
                                    //如果是出库带运输的仓储订单(先仓储计划单, 后运输计划单)
                                }else if(ofcFundamentalInformation != null
                                        && PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getOrderType()).equals(WAREHOUSEDISTRIBUTIONORDER)
                                        && PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0,2).equals("61")
                                        && planCodesByOrderCode.size() == 1
                                        && ofcSiloproStatusList.size() > 0){
                                    //未完成的仓储运输计划单
                                    OfcSiloproStatus ofcSiloproStatus = ofcSiloproStatusList.get(0);
                                    //改其状态为已完成,
                                    ofcSiloproStatus.setPlannedSingleState(RENWUWANCH);
                                    ofcSiloproStatus.setTaskCompletionTime(traceTime);
                                    ofcSiloproStatusService.updateByPlanCode(ofcSiloproStatus);
                                    //改订单状态为已完成.
                                    orderStatus=new OfcOrderStatus();
                                    orderStatus.setOrderCode(orderCode);
                                    orderStatus.setOrderStatus(HASBEENCOMPLETED);
                                    orderStatus.setLastedOperTime(now);
                                    orderStatus.setStatusDesc("已完成");
                                    orderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now)
                                            +" "+"订单已完成");
                                    orderStatus.setOperator(userName);
                                    if(null == ofcFundamentalInformation.getFinishedTime()){
                                        ofcFundamentalInformation.setFinishedTime(now);
                                    }
                                    ofcFundamentalInformationService.update(ofcFundamentalInformation);
                                }else if(ofcTransplanInfos.size() == 1
                                        && ofcFundamentalInformation.getOrderType().equals(TRANSPORTORDER)
                                        && !ofcFundamentalInformation.getBusinessType().equals(WITHTHEKABAN)){
                                    //单个城配或干线运输单的情况, 只剩一个未完成的运输计划单
                                    orderStatus=new OfcOrderStatus();
                                    orderStatus.setOrderCode(orderCode);
                                    orderStatus.setOrderStatus(HASBEENCOMPLETED);
                                    orderStatus.setLastedOperTime(now);
                                    orderStatus.setStatusDesc("已完成");
                                    orderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now)
                                            +" "+"订单已完成");
                                    orderStatus.setOperator(userName);
                                    if(null == ofcFundamentalInformation.getFinishedTime()){
                                        ofcFundamentalInformation.setFinishedTime(now);
                                    }
                                    ofcFundamentalInformationService.update(ofcFundamentalInformation);
                                }
                                logger.info("跟踪状态已完成");
                            }
                        }else if(status.equals("已回单")){
                            flag=false;
                            flag=checkStatus(flag,statusList,"start",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(traceTime)
                                    +" "+"客户已回单");
                            if(!flag){
                                ofcTransplanNewstatus.setTransportSingleLatestStatus(YIHUIDAN);
                                orderStatus.setLastedOperTime(traceTime);
                                orderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(traceTime)
                                        +" "+"客户已回单");
                                logger.info("跟踪状态已回单");
                            }
                        }else {
                            throw new BusinessException("所给运输计划单状态有误:" + status);
                        }
                        if(!orstatus.equals(orderStatus.getNotes())){
                            orderStatus.setId(UUID.randomUUID().toString().replace("-", ""));
                            orderStatus.setCreationTime(new Date());
                            ofcOrderStatusService.save(orderStatus);
                            if(StringUtils.equals(orderStatus.getOrderStatus(), OrderConstConstant.HASBEENCOMPLETED)){
                                //订单中心--订单状态推结算中心(执行中和已完成)
                                ofcOrderManageService.pullOfcOrderStatus(orderStatus);
                            }
                        }
                        ofcTransplanStatusService.updateByPlanCode(ofcTransplanStatus);
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

    /**
     * TFC调度单状态反馈
     * @param ofcSchedulingSingleFeedbackCondition
     * @param userName
     * @return
     */
    @Override
    public Wrapper<List<OfcPlanFedBackResult>> schedulingSingleFeedback(OfcSchedulingSingleFeedbackCondition ofcSchedulingSingleFeedbackCondition, String userName) {
        for(int i=0;i<ofcSchedulingSingleFeedbackCondition.getTransportNo().size();i++){
            String transPortNo= PubUtils.trimAndNullAsEmpty(ofcSchedulingSingleFeedbackCondition.getTransportNo().get(i));
            if(transPortNo.equals("") || !PubUtils.trimAndNullAsEmpty(transPortNo).startsWith("TP")){
                throw new BusinessException("运输计划单号为空或者格式不正确");
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
                    }
                    info+="，安排车辆车牌号：【"+ofcSchedulingSingleFeedbackCondition.getVehical()+"】";
                    if(PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getDriverName()).equals("")){
                        ofcDistributionBasicInfo.setDriverName(ofcSchedulingSingleFeedbackCondition.getDriver());
                    }
                    info+="，司机姓名：【"+ofcSchedulingSingleFeedbackCondition.getDriver()+"】";
                    if(PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getContactNumber()).equals("")){
                        ofcDistributionBasicInfo.setContactNumber(ofcSchedulingSingleFeedbackCondition.getTel());
                    }/*else if(PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getTransCode()).equals("")){
                            ofcDistributionBasicInfo.setTransCode(ofcSchedulingSingleFeedbackCondition.getDeliveryNo());
                            }*/
                    info+="，联系电话：【"+ofcSchedulingSingleFeedbackCondition.getTel()+"】";
                    logger.info("###############调度状态更新信息为{}",info);
                    ofcDistributionBasicInfoService.updateByOrderCode(ofcDistributionBasicInfo);
                    boolean flag = false;
                    List<OfcOrderStatus> statusList = ofcOrderStatusService.orderStatusScreen(orderCode, "orderCode");
                    flag=checkStatus(false,statusList,"start",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ofcSchedulingSingleFeedbackCondition.getCreateTime())
                            +" 订单"+tag+"调度完成");
                    if (!flag) {
                        OfcOrderStatus orderStatus=ofcOrderStatusService.orderStatusSelect(orderCode,"orderCode");
                        orderStatus.setId(UUID.randomUUID().toString().replace("-", ""));
                        orderStatus.setLastedOperTime(ofcSchedulingSingleFeedbackCondition.getCreateTime());
                        orderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ofcSchedulingSingleFeedbackCondition.getCreateTime())
                                +" "+info);
                        ofcOrderStatusService.save(orderStatus);
                    }
                /*}*/
            }
        }
        return null;
    }

    public boolean checkStatus(boolean flag,List<OfcOrderStatus> statusList,String position,String msg){
        if (PubUtils.isNotNullAndBiggerSize(statusList, 0)) {
            for (OfcOrderStatus status : statusList) {
                if (status != null) {
                    String statusNote = status.getNotes();
                    if (!PubUtils.isSEmptyOrNull(statusNote)) {
                        if (PubUtils.trimAndNullAsEmpty(position).equals("start")) {
                            if (statusNote.startsWith(msg)) {
                                flag = true;
                                break;
                            }
                        } else if (PubUtils.trimAndNullAsEmpty(position).equals("end")) {
                            if (statusNote.endsWith(msg)) {
                                flag = true;
                                break;
                            }
                        }
                    }
                }
            }
        } else {
            flag = true;
        }
        return flag;
    }
}
