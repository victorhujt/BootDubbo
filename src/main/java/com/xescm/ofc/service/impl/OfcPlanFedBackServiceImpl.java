package com.xescm.ofc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.enums.SmsTemplatesEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.SendSmsDTO;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.DateUtils;
import com.xescm.ofc.utils.SendSmsManager;
import com.xescm.tfc.mq.dto.TfcTransportStateDTO;
import org.apache.commons.lang.exception.ExceptionUtils;
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
import static com.xescm.tfc.edas.model.constants.PaasStateConstants.PaasStateEnum;

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
    private OfcWarehouseInformationService ofcWarehouseInformationService;
    @Resource
    private OfcFinanceInformationService ofcFinanceInformationService;

    /**
     * 运输单状态反馈
     * @param transportStateDTO      反馈实体
     * @param userName      用户名
     * @return      list
     */
    @Override
    public Wrapper<List<OfcPlanFedBackResult>> planFedBackNew(TfcTransportStateDTO transportStateDTO, String userName, ConcurrentHashMap cmap) {
        String orderCode = trimAndNullAsEmpty(transportStateDTO.getOrderNo());
        try{
            String status = trimAndNullAsEmpty(transportStateDTO.getState());
            Date traceTime = transportStateDTO.getOperatorTime();
            logger.info("订单运输状态更新：transportStateDTO => {}", JacksonUtil.toJson(transportStateDTO));

            if (PubUtils.isSEmptyOrNull(orderCode)) {
                throw new BusinessException("订单运输状态更新异常：订单号为空或者格式不正确");
            }
            if (PubUtils.isSEmptyOrNull(status)) {
                throw new BusinessException("订单["+orderCode+"]运输状态更新异常：跟踪状态不可以为空");
            }
            if (PubUtils.isNull(traceTime)) {
                throw new BusinessException("订单["+orderCode+"]运输状态更新异常：跟踪时间不可以为空");
            }

            logger.info("序号：1 ===== 订单号{}=> 跟踪状态{}", orderCode, status);

            OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
            OfcDistributionBasicInfo distributionBasicInfo = ofcDistributionBasicInfoService.distributionBasicInfoSelect(orderCode);
            if (ofcFundamentalInformation == null || distributionBasicInfo == null) {
                throw new BusinessException("订单["+orderCode+"]运输状态更新异常：根据订单号查询不到订单信息");
            }
            // 订单收货方地址
            String destination = new StringBuilder().append(trimAndNullAsEmpty(distributionBasicInfo.getDestinationProvince()))
                    .append( trimAndNullAsEmpty(distributionBasicInfo.getDestinationCity()))
                    .append(trimAndNullAsEmpty(distributionBasicInfo.getDestinationDistrict()))
                    .append(trimAndNullAsEmpty(distributionBasicInfo.getDestinationTowns()))
                    .append(trimAndNullAsEmpty(distributionBasicInfo.getDestination()))
                    .toString();
            logger.info("######订单{}收货方地址为：{}", orderCode, destination);
            OfcOrderStatus orderStatus = ofcOrderStatusService.orderStatusSelect(orderCode,"orderCode");
            List<OfcOrderStatus> statusList = ofcOrderStatusService.orderStatusScreen(orderCode, "orderCode");
            String statusNotes = orderStatus.getNotes();
            String traceTimeStr = DateUtils.Date2String(traceTime, DateUtils.DateFormatType.TYPE1);
            if (PaasStateEnum.TFC_STATE_3.getCenterState().equals(status)) {        /** 已调度 */

                logger.info("=====> 订单号{}=> 跟踪状态{}", orderCode, "10-[已调度]");
                // 更新调度车辆、司机信息
                updateOrderScheduleInfo(orderCode, transportStateDTO);
                String notes = traceTimeStr + " 订单调度完成，安排车辆车牌号：【" + transportStateDTO.getCarNumber() + "】" + "，司机姓名：【" + transportStateDTO.getDriver() + "】" +
                    "，联系电话：【" + transportStateDTO.getDriverPhone() +"】";
                orderStatus = setOrderStatusInfo(orderStatus, statusList, traceTime, null, null, notes, "full");
            } else if (PaasStateEnum.TFC_STATE_4.getCenterState().equals(status)) { /** 已发运 */

                logger.info("=====> 订单号{}=> 跟踪状态{}", orderCode, "20-[已发运]");
                String notes = traceTimeStr + " " + " 车辆已发运，发往目的地：" + destination;
                orderStatus = setOrderStatusInfo(orderStatus, statusList, traceTime, "30", "发车", notes, "start");
            } else if (PaasStateEnum.TFC_STATE_5.getCenterState().equals(status)) { /** 已到达 */

                logger.info("=====> 订单号{}=> 跟踪状态{}", orderCode, "30-[已到达]");
                String notes = traceTimeStr + " " + "车辆已到达目的地：" + destination;
                orderStatus = setOrderStatusInfo(orderStatus, statusList, traceTime, null, null, notes, "start");
            } else if (PaasStateEnum.TFC_STATE_6.getCenterState().equals(status)) { /** 已签收 */

                logger.info("=====> 订单号{}=> 跟踪状态{}", orderCode, "40-[已签收]");
                orderStatus = setOrderStatusSign(orderStatus, statusList, ofcFundamentalInformation, traceTime, cmap);
            } else if (PaasStateEnum.TFC_STATE_7.getCenterState().equals(status)) { /** 已回单 */

                logger.info("=====> 订单号{}=> 跟踪状态{}订单号{}=> 跟踪状态{}", orderCode, "50-[已回单]");
                String notes = traceTimeStr + " " + "客户已回单";
                orderStatus = setOrderStatusInfo(orderStatus, statusList, traceTime, "60", "回单", notes,"start");
            } else if (PaasStateEnum.TFC_STATE_8.getCenterState().equals(status)) { /** 中转入 */

                logger.info("=====> 订单号{}=> 跟踪状态{}", orderCode, "32-[中转入]");
                String notes = traceTimeStr + "【"+transportStateDTO.getBaseName()+"】收货入库";
                orderStatus = setOrderStatusInfo(orderStatus, statusList, traceTime, null, null, notes,"full");
            } /*else if (PaasStateEnum.TFC_STATE_9.getCenterState().equals(status)) { *** 再调度 **

                logger.info("=====> 订单号{}=> 跟踪状态{}", orderCode, "34-[再调度]");
                String notes = traceTimeStr + " 【"+transportStateDTO.getBaseName()+"】发货出库, 安排车辆车牌号：【"+transportStateDTO.getCarNumber()+"】，司机姓名：【"
                    + transportStateDTO.getDriver()+"】，联系电话：【"+transportStateDTO.getDriverPhone()+"】";
                orderStatus = setOrderStatusInfo(orderStatus, statusList, traceTime, null, null, notes,"full");
            }*/ else if (PaasStateEnum.TFC_STATE_10.getCenterState().equals(status)) {/** 上报异常 */

                logger.info("=====> 订单号{}=> 跟踪状态{}", orderCode, "36-[异常]");
                String notes =  traceTimeStr + " 【"+transportStateDTO.getBaseName()+"】上报异常 【"+transportStateDTO.getRemarks()+"】";
                orderStatus = setOrderStatusInfo(orderStatus, statusList, traceTime, null, null, notes, "full");
            } else if (PaasStateEnum.TFC_STATE_11.getCenterState().equals(status)) {/** 取消签收 */

                logger.info("=====> 订单号{}=> 跟踪状态{}", orderCode, "41-[取消签收]");
                String notes =  traceTimeStr + " 【"+transportStateDTO.getOperatorName()+"】取消签收";
                orderStatus = setOrderStatusInfo(orderStatus, statusList, traceTime, null, null, notes, "start");
            } else if (PaasStateEnum.TFC_STATE_12.getCenterState().equals(status)) { /** 中转出 */

                logger.info("=====> 订单号{}=> 跟踪状态{}", orderCode, "38-[中转出]");
                String notes = traceTimeStr + " 【"+transportStateDTO.getBaseName()+"】发货出库, 安排车辆车牌号：【"+transportStateDTO.getCarNumber()+"】，司机姓名：【"
                    + transportStateDTO.getDriver()+"】，联系电话：【"+transportStateDTO.getDriverPhone()+"】";
                orderStatus = setOrderStatusInfo(orderStatus, statusList, traceTime, null, null, notes,"full");
            } else {
                throw new BusinessException("订单["+orderCode+"]运输状态更新异常：所给订单状态有误 " + status);
            }

            if (!statusNotes.equals(orderStatus.getNotes())) {
                if ("40".equals(status) || "50".equals(status)) {
                    orderStatus.setOrderStatus("40");
                }
                ofcOrderStatusService.save(orderStatus);
                if (StringUtils.equals(orderStatus.getOrderStatus(), OrderConstConstant.HASBEEN_COMPLETED)) {
                    //订单发送签收短信
                    this.sendSmsWhileSigned(ofcFundamentalInformation, distributionBasicInfo);
                    //订单中心--订单状态推结算中心(执行中和已完成)
                    ofcOrderManageService.pullOfcOrderStatus(orderStatus);
                }
            }
        } catch (Exception ex) {
            throw new BusinessException("订单[{"+orderCode+"}]运输状态更新异常：未知异常详情 => " + ExceptionUtils.getFullStackTrace(ex));
        }
        return null;
    }

    /**
     * 更新调度信息，司机、车牌、电话号码
     * @param orderCode
     * @param transportStateDTO
     */
    private void updateOrderScheduleInfo(String orderCode, TfcTransportStateDTO transportStateDTO) {
        OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoService.distributionBasicInfoSelect(orderCode);
        if (trimAndNullAsEmpty(ofcDistributionBasicInfo.getPlateNumber()).equals("")) {
            ofcDistributionBasicInfo.setPlateNumber(transportStateDTO.getCarNumber());
        }
        if (trimAndNullAsEmpty(ofcDistributionBasicInfo.getDriverName()).equals("")) {
            ofcDistributionBasicInfo.setDriverName(transportStateDTO.getDriver());
        }
        if (trimAndNullAsEmpty(ofcDistributionBasicInfo.getContactNumber()).equals("")) {
            ofcDistributionBasicInfo.setContactNumber(transportStateDTO.getDriverPhone());
        }
        ofcDistributionBasicInfoService.updateByOrderCode(ofcDistributionBasicInfo);
    }

    /**
     * 订单状态修改(不含签收)
     *
     * @param orderStatus 订单状态实体
     * @param currentOrderStatus 订单现有状态
     * @param traceTime 操作时间
     * @param traceCode 操作类型
     * @param traceStatus 操作类型描述
     * @param notes
     * @param checkPoint 检查点
     * @return
     */
    private OfcOrderStatus setOrderStatusInfo(OfcOrderStatus orderStatus, List<OfcOrderStatus> currentOrderStatus, Date traceTime, String traceCode, String traceStatus, String notes, String checkPoint) {
        boolean flag;
        flag = checkStatus(false, currentOrderStatus, checkPoint, notes);
        if (!flag) {
            orderStatus.setLastedOperTime(traceTime);
            orderStatus.setNotes(notes);
            if (PubUtils.isSEmptyOrNull(traceCode)) orderStatus.setTraceStatus(traceCode);
            if (PubUtils.isSEmptyOrNull(traceStatus)) orderStatus.setTrace(traceStatus);
        }
        return orderStatus;
    }

    /**
     * 订单签收状态更新
     *
     * @param orderStatus 订单状态实体
     * @param statusList 订单现有状态
     * @param ofcFundamentalInformation 订单
     * @param traceTime 操作时间
     * @param cmap
     * @return
     */
    private OfcOrderStatus setOrderStatusSign(OfcOrderStatus orderStatus, List<OfcOrderStatus> statusList, OfcFundamentalInformation ofcFundamentalInformation, Date traceTime, ConcurrentHashMap cmap) {
        boolean flag;
        Date now = new Date();
        String traceTimeStr = DateUtils.Date2String(traceTime, DateUtils.DateFormatType.TYPE1);
        String orderCode = ofcFundamentalInformation.getOrderCode();
        // 业务类型为卡班并且不是二次配送，DMS签收，否则tfc签收
        String orderType = ofcFundamentalInformation.getBusinessType();
        OfcFinanceInformation ofcFinanceInformation = ofcFinanceInformationService.queryByOrderCode(orderCode);
        String twoDistribution = ofcFinanceInformation != null && PubUtils.isOEmptyOrNull(ofcFinanceInformation.getTwoDistribution()) ? ofcFinanceInformation.getTwoDistribution() : "";
        String signMsg;
        if (WITH_THE_TRUNK.equals(orderType) && !twoDistribution.equals("1")) {
            signMsg = "客户自提签收";
        } else {
            signMsg = "客户已签收";
        }
        flag = checkStatus(false, statusList, "end", signMsg);
        if (!flag) {
            orderStatus.setLastedOperTime(traceTime);
            orderStatus.setTraceStatus("50");
            orderStatus.setTrace("签收");
            orderStatus.setNotes(traceTimeStr + " " + signMsg);
            logger.info("跟踪状态已签收");
            ofcOrderStatusService.save(orderStatus);
            logger.info("序号：4-insertstatus ===== 订单号{}=> 跟踪状态{}", orderCode, orderStatus.getNotes());

            //签收后标记为已完成
            orderStatus = new OfcOrderStatus();
            orderStatus.setOrderCode(orderCode);
            if (WAREHOUSE_DIST_ORDER.equals(ofcFundamentalInformation.getOrderType())) {
                OfcWarehouseInformation ofcWarehouseInformation = new OfcWarehouseInformation();
                ofcWarehouseInformation.setOrderCode(orderCode);
                ofcWarehouseInformation = ofcWarehouseInformationService.selectOne(ofcWarehouseInformation);
                if (ofcWarehouseInformation.getProvideTransport() == WEARHOUSE_WITH_TRANS) {
                    if (cmap.containsKey(orderCode)) {
                        logger.info("仓储订单仓储先完成,订单号为{}", orderCode);
                        orderStatus.setOrderStatus(HASBEEN_COMPLETED);
                        if (null == ofcFundamentalInformation.getFinishedTime()) {
                            ofcFundamentalInformation.setFinishedTime(now);
                        }
                    } else {
                        orderStatus.setOrderStatus(IMPLEMENTATION_IN);
                        cmap.put(ofcFundamentalInformation.getOrderCode(), "");
                        logger.info("===>仓储订单运输先完成,订单号为{}", orderCode);
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
            orderStatus.setOperator("");
            ofcFundamentalInformationService.update(ofcFundamentalInformation);
        }
        return orderStatus;
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
     * @param ofcSchedulingStatus      反馈实体
     * @param userName      用户名
     * @return      List
     */
    @Override
    public Wrapper<List<OfcPlanFedBackResult>> schedulingSingleFeedbackNew(OfcSchedulingSingleFeedbackCondition ofcSchedulingStatus, String userName) {
        for (int i = 0; i < ofcSchedulingStatus.getOrderCode().size(); i++) {
            //注意，运输单号即是订单号
            String orderCode = trimAndNullAsEmpty(ofcSchedulingStatus.getOrderCode().get(i));
            try {
                if (orderCode.equals("") || !trimAndNullAsEmpty(orderCode).startsWith(ORDER_PRE)) {
                    throw new BusinessException("订单调度状态更新异常：订单号为空或者格式不正确: ["+orderCode+"]");
                }
                if (ofcSchedulingStatus.getDeliveryNo().equals("")) {
                    throw new BusinessException("订单["+orderCode+"]调度状态更新异常：调度单号不可以为空");
                }
                if (ofcSchedulingStatus.getCreateTime() == null) {
                    throw new BusinessException("订单["+orderCode+"]调度状态更新异常：调度单时间不可以为空");
                }
                //更新车牌号、司机姓名、联系电话
                OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoService.distributionBasicInfoSelect(orderCode);
                StringBuilder info = new StringBuilder("订单");
                String tag = "";
                info.append("调度完成");
                if (trimAndNullAsEmpty(ofcDistributionBasicInfo.getPlateNumber()).equals("")) {
                    ofcDistributionBasicInfo.setPlateNumber(ofcSchedulingStatus.getVehical());
                }
                info.append("，安排车辆车牌号：【").append(ofcSchedulingStatus.getVehical()).append("】");
                if (trimAndNullAsEmpty(ofcDistributionBasicInfo.getDriverName()).equals("")) {
                    ofcDistributionBasicInfo.setDriverName(ofcSchedulingStatus.getDriver());
                }
                info.append("，司机姓名：【").append(ofcSchedulingStatus.getDriver()).append("】");
                if (trimAndNullAsEmpty(ofcDistributionBasicInfo.getContactNumber()).equals("")) {
                    ofcDistributionBasicInfo.setContactNumber(ofcSchedulingStatus.getTel());
                }
                info.append("，联系电话：【").append(ofcSchedulingStatus.getTel()).append("】");
                logger.info("###############调度状态更新信息为{}", info.toString());
                ofcDistributionBasicInfoService.updateByOrderCode(ofcDistributionBasicInfo);
                //保存订单状态日志
                boolean flag;
                List<OfcOrderStatus> statusList = ofcOrderStatusService.orderStatusScreen(orderCode, "orderCode");
                flag = checkStatus(false, statusList, "start", DateUtils.Date2String(ofcSchedulingStatus.getCreateTime(), DateUtils.DateFormatType.TYPE1)
                    + " 订单" + tag + "调度完成");
                if (!flag) {
                    OfcOrderStatus orderStatus = ofcOrderStatusService.orderStatusSelect(orderCode, "orderCode");
                    orderStatus.setLastedOperTime(ofcSchedulingStatus.getCreateTime());
                    orderStatus.setNotes(DateUtils.Date2String(ofcSchedulingStatus.getCreateTime(), DateUtils.DateFormatType.TYPE1)
                        + " " + info.toString());
                    ofcOrderStatusService.save(orderStatus);
                }
            } catch (BusinessException ex) {
                throw new BusinessException("订单["+orderCode+"]调度状态更新异常：异常详情 => " + ExceptionUtils.getFullStackTrace(ex));
            } catch (Exception ex) {
                throw new BusinessException("订单["+orderCode+"]调度状态更新异常：未知异常详情 => " + ExceptionUtils.getFullStackTrace(ex));
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
                        } else if (trimAndNullAsEmpty(position).equals("full")) {
                            if (statusNote.equals(msg)) {
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
