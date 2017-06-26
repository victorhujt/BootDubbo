package com.xescm.ofc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.enums.SmsTemplatesEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.SendSmsDTO;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.DateUtils;
import com.xescm.ofc.utils.SendSmsManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.xescm.core.utils.PubUtils.trimAndNullAsEmpty;
import static com.xescm.ofc.constant.GenCodePreffixConstant.ORDER_PRE;
import static com.xescm.ofc.constant.OrderConstConstant.*;
import static com.xescm.ofc.constant.OrderConstant.WAREHOUSE_DIST_ORDER;

/**
 *
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class  OfcPlanFedBackServiceImpl implements OfcPlanFedBackService {
    private static final Logger logger = LoggerFactory.getLogger(OfcPlanFedBackServiceImpl.class);
    @Resource
    private OfcOrderStatusService ofcOrderStatusService;
    @Resource
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;
    @Resource
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Resource
    private OfcOrderManageService ofcOrderManageService;
    @Resource
    private SendSmsManager sendSmsManager;
    @Resource
    private OfcWarehouseInformationService   ofcWarehouseInformationService;


    /**
     * 运输单状态反馈
     * @param ofcPlanFedBackCondition      反馈实体
     * @param userName      用户名
     * @return      list
     */
    @Override
    public Wrapper<List<OfcPlanFedBackResult>> planFedBackNew(OfcPlanFedBackCondition ofcPlanFedBackCondition, String userName,ConcurrentHashMap cmap) {
        //根据订单号获取单及状态
        String transPortNo= trimAndNullAsEmpty(ofcPlanFedBackCondition.getOrderCode());
        String status= trimAndNullAsEmpty(ofcPlanFedBackCondition.getState());
        Date traceTime = ofcPlanFedBackCondition.getTraceTime();
        try{
            if (transPortNo.equals("")) {
                throw new BusinessException("运输单号不可以为空");
            }
            if (status.equals("")) {
                throw new BusinessException("跟踪状态不可以为空");
            }
            if (traceTime ==null) {
                throw new BusinessException("跟踪时间不可以为空");
            }

            logger.info("序号：1 ===== 订单号{}=> 跟踪状态{}", transPortNo,status);
            OfcFundamentalInformation ofcFundamentalInformation=ofcFundamentalInformationService.selectByKey(transPortNo);
            OfcDistributionBasicInfo ofcDistributionBasicInfo=ofcDistributionBasicInfoService.distributionBasicInfoSelect(transPortNo);
            if (ofcFundamentalInformation==null || ofcDistributionBasicInfo==null) {
                throw new BusinessException("传送运输单号信息失败，查不到相关订单");
            }

            String destination= new StringBuilder().append(trimAndNullAsEmpty(ofcDistributionBasicInfo.getDestinationProvince()))
                    .append(trimAndNullAsEmpty(ofcDistributionBasicInfo.getDestinationCity()))
                    .append(trimAndNullAsEmpty(ofcDistributionBasicInfo.getDestinationDistrict()))
                    .append(trimAndNullAsEmpty(ofcDistributionBasicInfo.getDestinationTowns()))
                    .append(trimAndNullAsEmpty(ofcDistributionBasicInfo.getDestination()))
                    .toString();
            logger.info("######发货方目的地为：{}",destination);

            OfcOrderStatus orderStatus=ofcOrderStatusService.orderStatusSelect(transPortNo,"orderCode");
            List<OfcOrderStatus> statusList = ofcOrderStatusService.orderStatusScreen(transPortNo, "orderCode");
            String orstatus=orderStatus.getNotes();
            boolean flag;
            switch (status) {
                case "20":
                    logger.info("序号：2 ===== 订单号{}=> 跟踪状态{}", transPortNo, "20-[已发运]");
                    flag = checkStatus(false, statusList, "start", DateUtils.Date2String(traceTime, DateUtils.DateFormatType.TYPE1)
                            + " " + "车辆已发运，发往目的地：");
                    if (!flag) {
                        orderStatus.setLastedOperTime(traceTime);
                        orderStatus.setTraceStatus("30");
                        orderStatus.setTrace("发车");
                        orderStatus.setNotes(DateUtils.Date2String(traceTime, DateUtils.DateFormatType.TYPE1)
                                + " " + "车辆已发运，发往目的地：" + destination);
                        logger.info("序号：2-insertstatus ===== 订单号{}=> 跟踪状态{}", transPortNo, orderStatus.getNotes());
                    }
                    break;
                case "30":
                    logger.info("序号：3 ===== 订单号{}=> 跟踪状态{}", transPortNo, "30-[已到达]");
                    flag = checkStatus(false, statusList, "start", DateUtils.Date2String(traceTime, DateUtils.DateFormatType.TYPE1)
                            + " " + "车辆已到达目的地：");
                    if (!flag) {
                        orderStatus.setLastedOperTime(traceTime);
                        orderStatus.setNotes(DateUtils.Date2String(traceTime, DateUtils.DateFormatType.TYPE1)
                                + " " + "车辆已到达目的地：" + destination);
                        logger.info("序号：3-insertstatus ===== 订单号{}=> 跟踪状态{}", transPortNo, orderStatus.getNotes());
                    }
                    break;
                case "40":
                    logger.info("序号：4 ===== 订单号{}=> 跟踪状态{}", transPortNo, "40-[已签收]");
                    Date now = new Date();
                    flag = checkStatus(false, statusList, "end", "客户已签收");
                    if (!flag) {
                        orderStatus.setLastedOperTime(traceTime);
                        orderStatus.setTraceStatus("50");
                        orderStatus.setTrace("签收");
                        orderStatus.setNotes(DateUtils.Date2String(traceTime, DateUtils.DateFormatType.TYPE1) + " " + "客户已签收");
                        logger.info("跟踪状态已签收");
                        ofcOrderStatusService.save(orderStatus);
                        logger.info("序号：4-insertstatus ===== 订单号{}=> 跟踪状态{}", transPortNo, orderStatus.getNotes());

                        //签收后标记为已完成
                        orderStatus = new OfcOrderStatus();
                        orderStatus.setOrderCode(ofcFundamentalInformation.getOrderCode());
                        if (WAREHOUSE_DIST_ORDER.equals(ofcFundamentalInformation.getOrderType())) {
                            OfcWarehouseInformation ofcWarehouseInformation = new OfcWarehouseInformation();
                            ofcWarehouseInformation.setOrderCode(ofcFundamentalInformation.getOrderCode());
                            ofcWarehouseInformation = ofcWarehouseInformationService.selectOne(ofcWarehouseInformation);
                            if (ofcWarehouseInformation.getProvideTransport() == WEARHOUSE_WITH_TRANS) {
                                if (cmap.containsKey(ofcFundamentalInformation.getOrderCode())) {
                                    logger.info("仓储订单仓储先完成,订单号为{}",ofcFundamentalInformation.getOrderCode());
                                    orderStatus.setOrderStatus(HASBEEN_COMPLETED);
                                    if (null == ofcFundamentalInformation.getFinishedTime()) {
                                        ofcFundamentalInformation.setFinishedTime(now);
                                    }
                                } else {
                                    orderStatus.setOrderStatus(IMPLEMENTATION_IN);
                                    cmap.put(ofcFundamentalInformation.getOrderCode(),"");
                                    logger.info("===>仓储订单运输先完成,订单号为{}",ofcFundamentalInformation.getOrderCode());
                                }
                            }
                        } else {
                            orderStatus.setOrderStatus(HASBEEN_COMPLETED);
                            if (null == ofcFundamentalInformation.getFinishedTime()) {
                                ofcFundamentalInformation.setFinishedTime(now);
                            }
                        }
                        orderStatus.setLastedOperTime(now);
                        orderStatus.setStatusDesc("运输单已完成");
                        orderStatus.setNotes(DateUtils.Date2String(now, DateUtils.DateFormatType.TYPE1) + " " + "运输单订单已完成");
                        orderStatus.setOperator(userName);
                        ofcFundamentalInformationService.update(ofcFundamentalInformation);
                        logger.info("序号：4-insertstatus ===== 订单号{}=> 跟踪状态{}", transPortNo, orderStatus.getNotes());
                    }
                    break;
                case "50":
                    logger.info("序号：5 ===== 订单号{}=> 跟踪状态{}", transPortNo, "50-[已回单]");
                    flag = checkStatus(false, statusList, "start", DateUtils.Date2String(traceTime, DateUtils.DateFormatType.TYPE1)
                            + " " + "客户已回单");
                    if (!flag) {
                        orderStatus.setLastedOperTime(traceTime);
                        orderStatus.setNotes(DateUtils.Date2String(traceTime, DateUtils.DateFormatType.TYPE1)
                                + " " + "客户已回单");
                        logger.info("序号：5-insertstatus ===== 订单号{}=> 跟踪状态{}", transPortNo, orderStatus.getNotes());
                    }
                    break;
                case "32":
                    logger.info("序号：6 ===== 订单号{}=> 跟踪状态{}", transPortNo, "32-[中转入]");
                    if (trimAndNullAsEmpty(ofcPlanFedBackCondition.getDescription()).equals("")) {
                        logger.info("跟踪状态已回单");
                        throw new BusinessException("中转入时状态描述信息不能为空");
                    }
                    flag = checkStatus(false, statusList, "start", DateUtils.Date2String(traceTime, DateUtils.DateFormatType.TYPE1)
                            + " " + ofcPlanFedBackCondition.getDescription());
                    if (!flag) {
                        orderStatus.setTraceStatus("60");
                        orderStatus.setTrace("回单");
                        orderStatus.setLastedOperTime(traceTime);
                        orderStatus.setNotes(DateUtils.Date2String(traceTime, DateUtils.DateFormatType.TYPE1)
                                + " " + ofcPlanFedBackCondition.getDescription());
                        logger.info("序号：6-insertstatus ===== 订单号{}=> 跟踪状态{}", transPortNo, orderStatus.getNotes());
                    }
                    break;
                case "34":
                    logger.info("序号：7 ===== 订单号{}=> 跟踪状态{}", transPortNo, "34-[中转出]");
                    if (trimAndNullAsEmpty(ofcPlanFedBackCondition.getDescription()).equals("")) {
                        logger.info("跟踪状态已回单");
                        throw new BusinessException("中转出时状态描述信息不能为空");
                    }
                    flag = checkStatus(false, statusList, "start", DateUtils.Date2String(traceTime, DateUtils.DateFormatType.TYPE1)
                            + " " + ofcPlanFedBackCondition.getDescription());
                    if (!flag) {
                        orderStatus.setLastedOperTime(traceTime);
                        orderStatus.setNotes(DateUtils.Date2String(traceTime, DateUtils.DateFormatType.TYPE1)
                                + " " + ofcPlanFedBackCondition.getDescription());
                        logger.info("序号：7-insertstatus ===== 订单号{}=> 跟踪状态{}", transPortNo, orderStatus.getNotes());
                    }
                    break;
                case "36":
                    logger.info("跟踪状态返回‘异常’");
                    break;
                default:
                    throw new BusinessException("所给运输计划单状态有误:" + status);
            }
            if (!orstatus.equals(orderStatus.getNotes())) {
                ofcOrderStatusService.save(orderStatus);
                if (StringUtils.equals(orderStatus.getOrderStatus(), OrderConstConstant.HASBEEN_COMPLETED)) {
                    //订单发送签收短信
                    this.sendSmsWhileSigned(ofcFundamentalInformation, ofcDistributionBasicInfo);
                    //订单中心--订单状态推结算中心(执行中和已完成)
                    ofcOrderManageService.pullOfcOrderStatus(orderStatus);
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
        return null;
    }

    private void sendSmsWhileSigned(OfcFundamentalInformation ofcFundamentalInformation, OfcDistributionBasicInfo ofcDistributionBasicInfo) {
        logger.info("订单签收时发送短信给发货方 ofcFundamentalInformation:{}", ofcFundamentalInformation);
        logger.info("订单签收时发送短信给发货方 ofcDistributionBasicInfo:{}", ofcDistributionBasicInfo);
        if (null == ofcDistributionBasicInfo || null == ofcFundamentalInformation) {
            logger.error("入参有误!");
            return;
        }
        String transCode = ofcDistributionBasicInfo.getTransCode();
        String phoneNumber = ofcDistributionBasicInfo.getConsignorContactPhone();
        String signedSms = ofcFundamentalInformation.getSignedSms();
        if (!StringUtils.equals(signedSms, STR_YES)) {
            logger.error("不需发短信!");
            return;
        }
        if (PubUtils.isSEmptyOrNull(phoneNumber)) {
            logger.error("发货方电话号码为空!");
            return;
        }
        if (PubUtils.isSEmptyOrNull(transCode)) {
            logger.error("运输单号为空!");
            return;
        }
        SendSmsDTO sendSmsDTO = new SendSmsDTO();
        sendSmsDTO.setTemplate(SmsTemplatesEnum.SMS_ORDER_SIGEND_MSG);
        sendSmsDTO.setCode(transCode);
        sendSmsDTO.setNumber(phoneNumber);
        Map<String, String> map = Maps.newHashMap();
        map.put("code", transCode);
        String param;
        try {
            param = JSONObject.toJSONString(map);
        } catch (Exception ex) {
            logger.error("转换出错{}", ex);
            return;
        }
        sendSmsDTO.setParamStr(param);
        Wrapper wrapper = sendSmsManager.sendSms(sendSmsDTO);
        logger.info("发送结果 == > wrapper{}", wrapper);
    }

    /**
     * TFC调度单状态反馈
     * @param ofcSchedulingSingleFeedbackCondition      反馈实体
     * @param userName      用户名
     * @return      List
     */
    @Override
    public Wrapper<List<OfcPlanFedBackResult>> schedulingSingleFeedbackNew(OfcSchedulingSingleFeedbackCondition ofcSchedulingSingleFeedbackCondition, String userName) {
        for (int i=0;i<ofcSchedulingSingleFeedbackCondition.getOrderCode().size();i++) {
            //注意，运输单号即是订单号
            String transPortNo= trimAndNullAsEmpty(ofcSchedulingSingleFeedbackCondition.getOrderCode().get(i));
            if (transPortNo.equals("") || !trimAndNullAsEmpty(transPortNo).startsWith(ORDER_PRE)) {
                throw new BusinessException("运输订单号为空或者格式不正确");
            }
            if (ofcSchedulingSingleFeedbackCondition.getDeliveryNo().equals("")) {
                throw new BusinessException("调度单号不可以为空");
            }
            if (ofcSchedulingSingleFeedbackCondition.getCreateTime()==null) {
                throw new BusinessException("调度单时间不可以为空");
            }

            logger.info("##################transPortNo为：{}",transPortNo);

            //更新车牌号、司机姓名、联系电话
            OfcDistributionBasicInfo ofcDistributionBasicInfo =ofcDistributionBasicInfoService.distributionBasicInfoSelect(transPortNo);
            StringBuilder info=new StringBuilder("订单");
            String tag="";
            info.append("调度完成");
            if (trimAndNullAsEmpty(ofcDistributionBasicInfo.getPlateNumber()).equals("")) {
                ofcDistributionBasicInfo.setPlateNumber(ofcSchedulingSingleFeedbackCondition.getVehical());
            }
            info.append("，安排车辆车牌号：【").append(ofcSchedulingSingleFeedbackCondition.getVehical()).append("】");
            if (trimAndNullAsEmpty(ofcDistributionBasicInfo.getDriverName()).equals("")) {
                ofcDistributionBasicInfo.setDriverName(ofcSchedulingSingleFeedbackCondition.getDriver());
            }
            info.append("，司机姓名：【").append(ofcSchedulingSingleFeedbackCondition.getDriver()).append("】");
            if (trimAndNullAsEmpty(ofcDistributionBasicInfo.getContactNumber()).equals("")) {
                ofcDistributionBasicInfo.setContactNumber(ofcSchedulingSingleFeedbackCondition.getTel());
            }
            info.append("，联系电话：【").append(ofcSchedulingSingleFeedbackCondition.getTel()).append("】");
            logger.info("###############调度状态更新信息为{}",info.toString());
            ofcDistributionBasicInfoService.updateByOrderCode(ofcDistributionBasicInfo);


            //保存订单状态日志
            boolean flag;
            List<OfcOrderStatus> statusList = ofcOrderStatusService.orderStatusScreen(transPortNo, "orderCode");
            flag=checkStatus(false,statusList,"start", DateUtils.Date2String(ofcSchedulingSingleFeedbackCondition.getCreateTime(), DateUtils.DateFormatType.TYPE1)
                    +" 订单"+tag+"调度完成");
            if (!flag) {
                OfcOrderStatus orderStatus=ofcOrderStatusService.orderStatusSelect(transPortNo,"orderCode");
                orderStatus.setLastedOperTime(ofcSchedulingSingleFeedbackCondition.getCreateTime());
                orderStatus.setNotes( DateUtils.Date2String(ofcSchedulingSingleFeedbackCondition.getCreateTime(), DateUtils.DateFormatType.TYPE1)
                        +" "+info.toString());
                ofcOrderStatusService.save(orderStatus);
            }
        }
        return null;
    }

    //校验数据库中是否已存在相应状态
    private boolean checkStatus(boolean flag,List<OfcOrderStatus> statusList,String position,String msg) {
        if (PubUtils.isNotNullAndBiggerSize(statusList, 0)) {
            for (OfcOrderStatus status : statusList) {
                if (status != null) {
                    String statusNote = status.getNotes();
                    if (!PubUtils.isSEmptyOrNull(statusNote)) {
                        if (trimAndNullAsEmpty(position).equals("start")) {
                            if (statusNote.startsWith(msg)) {
                                flag = true;
                                break;
                            }
                        } else if (trimAndNullAsEmpty(position).equals("end")) {
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
