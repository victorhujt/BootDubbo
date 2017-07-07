package com.xescm.ofc.service.impl;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.edas.model.dto.ofc.OfcOrderStatusDTO;
import com.xescm.ofc.edas.model.dto.ofc.OfcRealTimeTraceDTO;
import com.xescm.ofc.edas.model.dto.ofc.OfcTraceOrderDTO;
import com.xescm.ofc.edas.model.dto.whc.FeedBackOrderDetailDto;
import com.xescm.ofc.edas.model.dto.whc.FeedBackOrderDto;
import com.xescm.ofc.edas.model.dto.whc.FeedBackOrderStatusDto;
import com.xescm.ofc.enums.ResultCodeEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcOrderNewstatusMapper;
import com.xescm.ofc.mapper.OfcOrderStatusMapper;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.CheckUtils;
import com.xescm.ofc.utils.DateUtils;
import com.xescm.tfc.edas.model.dto.ofc.req.OfcRealTimeTraceReqDTO;
import com.xescm.tfc.edas.service.TfcQueryGpsInfoEdasService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.xescm.core.utils.PubUtils.trimAndNullAsEmpty;
import static com.xescm.ofc.constant.OrderConstConstant.*;
import static com.xescm.ofc.constant.OrderConstant.WAREHOUSE_DIST_ORDER;

/**
 * 订单状态
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcOrderStatusServiceImpl extends BaseService<OfcOrderStatus> implements OfcOrderStatusService {
    @Resource
    private OfcOrderStatusMapper ofcOrderStatusMapper;
    @Resource
    private OfcOrderNewstatusMapper ofcOrderNewstatusMapper;
    @Resource
    private OfcOrderNewstatusService ofcOrderNewstatusService;
    @Resource
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Resource
    private OfcWarehouseInformationService ofcWarehouseInformationService;

    @Resource
    private OrderFollowOperService orderFollowOperService;

    @Resource
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;

    @Resource
    private TfcQueryGpsInfoEdasService tfcQueryGpsInfoEdasService;



    @Override
    public int deleteByOrderCode(Object key) {
        return ofcOrderStatusMapper.deleteByOrderCode(key);
    }

    @Override
    public List<OfcOrderStatus> orderStatusScreen(String code, String followTag) {
        if (!trimAndNullAsEmpty(code).equals("")) {
            String orderCode = null;
            String custOrderCode = null;
            String transCode = null;
            switch (followTag) {
                case "orderCode":
                    orderCode = code;
                    break;
                case "custOrderCode":
                    custOrderCode = code;
                    break;
                case "transCode":
                    transCode = code;
                    break;
            }
            // Map<String,String> mapperMap = new HashMap<String,String>();
            Map<String, String> mapperMap = new HashMap<>();
            mapperMap.put("orderCode", orderCode);
            mapperMap.put("custOrderCode", custOrderCode);
            mapperMap.put("transCode", transCode);
            return ofcOrderStatusMapper.orderStatusScreen(mapperMap);
        } else {
            throw new BusinessException("订单状态查询有误");
        }
    }

    @Override
    public OfcOrderStatus orderStatusSelect(String code, String followTag) {
        if (!trimAndNullAsEmpty(code).equals("")) {
            String orderCode = null;
            String custOrderCode = null;
            String transCode = null;

            switch (followTag) {
                case "orderCode":
                    orderCode = code;
                    break;
                case "custOrderCode":
                    custOrderCode = code;
                    break;
                case "transCode":
                    transCode = code;
                    break;
            }
            // Map<String,String> mapperMap = new HashMap<String,String>();
            Map<String, String> mapperMap = new HashMap<>();
            mapperMap.put("orderCode", orderCode);
            mapperMap.put("custOrderCode", custOrderCode);
            mapperMap.put("transCode", transCode);
            OfcOrderNewstatus orderNewstatus=ofcOrderNewstatusMapper.orderStatusSelectNew(mapperMap);
            OfcOrderStatus ofcOrderStatus = ofcOrderStatusMapper.orderStatusSelect(mapperMap);
            if(orderNewstatus==null
                    || trimAndNullAsEmpty(orderNewstatus.getOrderCode()).equals("")
                    || trimAndNullAsEmpty(orderNewstatus.getOrderLatestStatus()).equals("")){
                OfcOrderNewstatus orderNewstatu=new OfcOrderNewstatus();
                orderNewstatu.setOrderCode(ofcOrderStatus.getOrderCode());
                orderNewstatu.setOrderLatestStatus(ofcOrderStatus.getOrderStatus());
                orderNewstatu.setStatusUpdateTime(new Date());
                orderNewstatu.setStatusCreateTime(new Date());
                ofcOrderNewstatusService.save(orderNewstatus);

            }else{
                ofcOrderStatus.setOrderCode(orderNewstatus.getOrderCode());
                ofcOrderStatus.setOrderStatus(orderNewstatus.getOrderLatestStatus());
            }
            return ofcOrderStatus;
        } else {
            throw new BusinessException("订单状态查询有误");
        }

    }

    @Override
    public OfcOrderStatus queryOrderStateByOrderCode(String orderCode) {
        return ofcOrderStatusMapper.queryOrderStateByOrderCode(orderCode);
    }

    @Override
    public void cancelOrderStateByOrderCode(String orderCode) {
        OfcOrderNewstatus orderNewstatus=new OfcOrderNewstatus();
        orderNewstatus.setOrderCode(orderCode);
        orderNewstatus.setOrderLatestStatus(HASBEEN_CANCELED);
        orderNewstatus.setStatusUpdateTime(new Date());
        ofcOrderNewstatusService.update(orderNewstatus);
        ofcOrderStatusMapper.cancelOrderStateByOrderCode(orderCode);
    }

    @Override
    public OfcOrderStatus queryLastUpdateOrderByOrderCode(String orderCode) {
        OfcOrderNewstatus orderNewstatus=ofcOrderNewstatusService.selectByKey(orderCode);
        OfcOrderStatus ofcOrderStatus=new OfcOrderStatus();
        if (orderNewstatus != null
                && !trimAndNullAsEmpty(orderNewstatus.getOrderCode()).equals("")
                && !trimAndNullAsEmpty(orderNewstatus.getOrderLatestStatus()).equals("")) {
                    ofcOrderStatus.setOrderCode(orderNewstatus.getOrderCode());
                    ofcOrderStatus.setOrderStatus(orderNewstatus.getOrderLatestStatus());
                } else {
            ofcOrderStatus = ofcOrderStatusMapper.queryLastUpdateOrderByOrderCode(orderCode);
            if (null == ofcOrderStatus) {
                logger.error("查不到该订单的状态, 订单号: {}", orderCode);
                return new OfcOrderStatus();
            }
            OfcOrderNewstatus orderNewstatu=new OfcOrderNewstatus();
            orderNewstatu.setOrderCode(ofcOrderStatus.getOrderCode());
            orderNewstatu.setOrderLatestStatus(ofcOrderStatus.getOrderStatus());
            orderNewstatu.setStatusUpdateTime(new Date());
            orderNewstatu.setStatusCreateTime(new Date());
        }
        return ofcOrderStatus;
    }

    public OfcOrderStatus queryLastTimeOrderByOrderCode(String orderCode) {
        OfcOrderNewstatus orderNewstatus=ofcOrderNewstatusService.selectByKey(orderCode);
        OfcOrderStatus ofcOrderStatus=new OfcOrderStatus();
        if (orderNewstatus != null
                && !trimAndNullAsEmpty(orderNewstatus.getOrderCode()).equals("")
                && !trimAndNullAsEmpty(orderNewstatus.getOrderLatestStatus()).equals("")) {
                    ofcOrderStatus.setOrderCode(orderNewstatus.getOrderCode());
                    ofcOrderStatus.setOrderStatus(orderNewstatus.getOrderLatestStatus());
                } else {
            ofcOrderStatus = ofcOrderStatusMapper.queryLastTimeOrderByOrderCode(orderCode);
            OfcOrderNewstatus orderNewstatu=new OfcOrderNewstatus();
            orderNewstatu.setOrderCode(ofcOrderStatus.getOrderCode());
            orderNewstatu.setOrderLatestStatus(ofcOrderStatus.getOrderStatus());
            orderNewstatu.setStatusUpdateTime(new Date());
            orderNewstatu.setStatusCreateTime(new Date());
        }
        return ofcOrderStatus;
    }

    @Override
    public int save(OfcOrderStatus ofcOrderStatus) {
        if (ofcOrderStatus!=null
                && !trimAndNullAsEmpty(ofcOrderStatus.getOrderCode()).equals("")){
            if(!trimAndNullAsEmpty(ofcOrderStatus.getOrderStatus()).equals("")){
                OfcOrderNewstatus orderNewstatus=ofcOrderNewstatusService.selectByKey(ofcOrderStatus.getOrderCode());
                String tag="noStatus";
                if(orderNewstatus!=null){
                    tag="haveStatus";
                }else {
                    orderNewstatus=new OfcOrderNewstatus();
                }
                if (!trimAndNullAsEmpty(orderNewstatus.getOrderLatestStatus()).equals(HASBEEN_CANCELED))
                    if (!trimAndNullAsEmpty(orderNewstatus.getOrderLatestStatus()).equals(HASBEEN_COMPLETED)) {
                        updateOrderNewStatus(ofcOrderStatus, tag);
                    }
                ofcOrderStatus.setId(UUID.randomUUID().toString().replace("-", ""));
                return super.save(ofcOrderStatus);
            }else {
                throw new BusinessException("订单状态为空，保存订单状态失败");
            }
        }
        return 0;
    }

    @Override
    public void feedBackStatusFromWhc(FeedBackOrderStatusDto feedBackOrderStatusDto) {
        try {
            String orderCode=feedBackOrderStatusDto.getOrderCode();
            String type="";
            String traceStatus=feedBackOrderStatusDto.getStatus();
            Date traceTime=feedBackOrderStatusDto.getTraceTime();
            if(StringUtils.isEmpty(orderCode)){
                throw new BusinessException("订单号不可以为空");
            }
            if(StringUtils.isEmpty(feedBackOrderStatusDto.getStatus())){
                throw new BusinessException("跟踪状态不能为空");
            }

            OfcFundamentalInformation ofcFundamentalInformation=ofcFundamentalInformationService.selectByKey(orderCode);
            if(ofcFundamentalInformation==null){
                throw new BusinessException("订单不存在");
            }
            OfcOrderStatus orderStatus=orderStatusSelect(orderCode,"orderCode");
            OfcOrderStatus status=new OfcOrderStatus();
            if(orderStatus!=null){
                if(HASBEEN_COMPLETED.equals(orderStatus.getOrderStatus())){
                    throw new BusinessException("订单已经完成");
                }

                if(HASBEEN_CANCELED.equals(orderStatus.getOrderStatus())){
                    throw new BusinessException("订单已经取消");
                }
            }

            if(trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0,2).equals("62")){
                type=OFC_WHC_IN_TYPE;
            }
            else if (trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0,2).equals("61")){
                type=OFC_WHC_OUT_TYPE;
            }
            String statusDesc=translateStatusToDesc(traceStatus,type);
            if(orderStatus.getStatusDesc().indexOf(statusDesc)<0){
                status.setLastedOperTime(new Date());
                status.setStatusDesc(statusDesc);
                status.setOrderCode(orderCode);
                status.setOperator("");
                status.setOrderStatus(orderStatus.getOrderStatus());
                status.setNotes(DateUtils.Date2String(traceTime, DateUtils.DateFormatType.TYPE1)
                        +" "+statusDesc);
                status.setOrderCode(orderCode);
                super.save(status);
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public void ofcWarehouseFeedBackFromWhc(FeedBackOrderDto feedBackOrderDto,ConcurrentHashMap cmap) {
        try {
            String orderCode=feedBackOrderDto.getOrderCode();
            List<FeedBackOrderDetailDto> detailDtos=feedBackOrderDto.getFeedBackOrderDetail();
            if(StringUtils.isEmpty(orderCode)){
                throw new BusinessException("订单号不可以为空");
            }
            if(detailDtos==null||(detailDtos!=null&&detailDtos.size()==0)){
                throw new BusinessException("货品详情不能为空");
            }
            OfcWarehouseInformation ofcWarehouseInformation=new OfcWarehouseInformation();
            ofcWarehouseInformation.setOrderCode(orderCode);
            ofcWarehouseInformation=ofcWarehouseInformationService.selectOne(ofcWarehouseInformation);
            OfcFundamentalInformation ofcFundamentalInformation=ofcFundamentalInformationService.selectByKey(orderCode);
            if(ofcFundamentalInformation==null){
                throw new BusinessException("订单不存在");
            }

            OfcOrderStatus orderStatus=orderStatusSelect(orderCode,"orderCode");
            OfcOrderStatus status=new OfcOrderStatus();
            if(orderStatus!=null) {
                if (HASBEEN_COMPLETED.equals(orderStatus.getOrderStatus())) {
                    throw new BusinessException("订单已经完成");
                }
                if (HASBEEN_CANCELED.equals(orderStatus.getOrderStatus())) {
                    throw new BusinessException("订单已经取消");
                }
            }
            String str ="";
            if (trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0, 2).equals("62")) {
                status.setOrderStatus(HASBEEN_COMPLETED);
                str = "入库单";
            } else if (trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0, 2).equals("61")) {
                str = "出库单";
                status.setTraceStatus("20");
                status.setTrace("出库");
            }

            if(ofcWarehouseInformation!=null){
                if(ofcWarehouseInformation.getProvideTransport() == WEARHOUSE_WITH_TRANS){
                    if(cmap.containsKey(ofcFundamentalInformation.getOrderCode())){
                        logger.info("仓储订单运输先完成,订单号为{}",ofcFundamentalInformation.getOrderCode());
                        status.setOrderStatus(HASBEEN_COMPLETED);
                        //更新订单完成时间
                        ofcFundamentalInformation.setFinishedTime(new Date());
                    }else{
                        status.setOrderStatus(IMPLEMENTATION_IN);
                        logger.info("===>仓储订单仓储先完成,订单号为{}",ofcFundamentalInformation.getOrderCode());
                        cmap.put(ofcFundamentalInformation.getOrderCode(),"");
                    }
                }else{
                    status.setOrderStatus(HASBEEN_COMPLETED);
                    //更新订单完成时间
                    ofcFundamentalInformation.setFinishedTime(new Date());
                }
            }
            status.setLastedOperTime(new Date());
            status.setStatusDesc("订单号为"+orderCode+str+"已完成");
            status.setOrderCode(orderCode);
            status.setOperator("");
            status.setNotes(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1)
                    +" "+"订单号为"+orderCode+str+"已完成");
            status.setOrderCode(orderCode);
            save(status);
            ofcFundamentalInformationService.update(ofcFundamentalInformation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param orderCode 订单号
     * @return 状态跟踪
     */
    @Override
    public OfcTraceOrderDTO queryOrderByCode(String orderCode) throws Exception {
        logger.info("查单的订单号为:{}",orderCode);
        String departureTime = "";//发运时间
        String signTime = "";//签收时间
        OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
        CheckUtils.checkArgument(ofcFundamentalInformation == null, ResultCodeEnum.RESULTISNULL);
        if(ofcFundamentalInformation.getOrderType().equals(WAREHOUSE_DIST_ORDER)){
          OfcWarehouseInformation ofcWarehouseInformation = new OfcWarehouseInformation();
          ofcWarehouseInformation.setOrderCode(orderCode);
          List<OfcWarehouseInformation> ofcWarehouseInformations = ofcWarehouseInformationService.select(ofcWarehouseInformation);
              if(!CollectionUtils.isEmpty(ofcWarehouseInformations) && ofcWarehouseInformations.size() == 1){
                  CheckUtils.checkArgument(ofcWarehouseInformations.get(0).getProvideTransport() != WEARHOUSE_WITH_TRANS, ResultCodeEnum.ISNOTSUPPORT);
              }
        }
        OfcTraceOrderDTO ofcTraceOrderDTO = new OfcTraceOrderDTO();
        List<OfcOrderStatusDTO> orderStatusDTOs = new ArrayList<>();
        //获取订单的跟踪状态
        List<OfcOrderStatus> ofcOrderStatuses = orderFollowOperService.queryOrderStatus(orderCode, "orderCode");
        CheckUtils.checkArgument(CollectionUtils.isEmpty(ofcOrderStatuses), ResultCodeEnum.RESULTISNULL);
        for (OfcOrderStatus status : ofcOrderStatuses) {
            if(PubUtils.isSEmptyOrNull(status.getTrace())){
                continue;
            }
            //发运时间
            if("30".equals(status.getTraceStatus())){
                departureTime = DateUtils.Date2String(status.getLastedOperTime(), DateUtils.DateFormatType.TYPE1);
            }
            //签收时间
            if("50".equals(status.getTraceStatus())){
                signTime = DateUtils.Date2String(status.getLastedOperTime(), DateUtils.DateFormatType.TYPE1);
            }
            OfcOrderStatusDTO dto = new OfcOrderStatusDTO();
            dto.setLastOperTime(status.getLastedOperTime());
            dto.setOperator(PubUtils.isSEmptyOrNull(status.getOperator())?"":status.getOperator());
            dto.setTrace(status.getTrace());
            dto.setStatus(status.getTraceStatus());
            dto.setNotes(status.getNotes());
            orderStatusDTOs.add(dto);
        }
            ofcTraceOrderDTO.setOfcOrderStatusDTOs(orderStatusDTOs);
        //实时跟踪的状态
        OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoService.selectByKey(orderCode);
        if(ofcDistributionBasicInfo != null){
            String plateNumber = ofcDistributionBasicInfo.getPlateNumber();
            OfcRealTimeTraceReqDTO reqDTO = new OfcRealTimeTraceReqDTO();
            reqDTO.setPlateNumber(plateNumber);
            reqDTO.setStartTime(departureTime);
            reqDTO.setEndTime(signTime);
            long beginTime = System.currentTimeMillis();
            logger.info("调用获取实时位置的接口参数为:{}", JacksonUtil.toJson(reqDTO));
            Wrapper<List<OfcRealTimeTraceDTO>> ofcRealTimeTraceDTOResult  =  tfcQueryGpsInfoEdasService.queryGpsforOfc(reqDTO);
            long endTime = System.currentTimeMillis();
            logger.info("调用获取实时位置的接口接口耗时:{}ms",(endTime - beginTime));
            if(ofcRealTimeTraceDTOResult.getCode() == Wrapper.SUCCESS_CODE){
                if(!CollectionUtils.isEmpty(ofcRealTimeTraceDTOResult.getResult())){
                    logger.info("调用获取实时位置的结果集大小为:{}",ofcRealTimeTraceDTOResult.getResult().size());
                    ofcTraceOrderDTO.setOfcRealTimeTraceDTOs(ofcRealTimeTraceDTOResult.getResult());
                }
            }
        }
        return ofcTraceOrderDTO;
    }

    public void updateOrderNewStatus(OfcOrderStatus ofcOrderStatus,String tag){
        OfcOrderNewstatus orderNewstatus=new OfcOrderNewstatus();
        orderNewstatus.setOrderCode(ofcOrderStatus.getOrderCode());
        orderNewstatus.setOrderLatestStatus(ofcOrderStatus.getOrderStatus());
        if(tag.equals("haveStatus")){
            orderNewstatus.setStatusUpdateTime(new Date());
            ofcOrderNewstatusService.update(orderNewstatus);
        }else if(tag.equals("noStatus")){
            orderNewstatus.setStatusUpdateTime(new Date());
            orderNewstatus.setStatusCreateTime(new Date());
            ofcOrderNewstatusService.save(orderNewstatus);
        }
    }

    private String translateStatusToDesc(String statusCode,String businessType){
        String statusDesc="";
        if(statusCode.equals(TRACE_STATUS_1)){
            if(OFC_WHC_IN_TYPE.equals(businessType)){
                statusDesc="入库单已创建";

            }else if(OFC_WHC_OUT_TYPE.equals(businessType)){
                statusDesc="出库单已创建";
            }
        }else if(statusCode.equals(TRACE_STATUS_2)){
            if(OFC_WHC_IN_TYPE.equals(businessType)){
                statusDesc="部分收货";

            }else if(OFC_WHC_OUT_TYPE.equals(businessType)){

            }
        }else if(statusCode.equals(TRACE_STATUS_3)){
            if(OFC_WHC_IN_TYPE.equals(businessType)){
                statusDesc="完全收货";
            }else if(OFC_WHC_OUT_TYPE.equals(businessType)){
                statusDesc="出库分配完成";
            }
        }else if(statusCode.equals(TRACE_STATUS_4)){
            if(OFC_WHC_IN_TYPE.equals(businessType)){

            }else if(OFC_WHC_OUT_TYPE.equals(businessType)){
                statusDesc="拣货完成";
            }
        }else if(statusCode.equals(TRACE_STATUS_5)){
            if(OFC_WHC_IN_TYPE.equals(businessType)){

            }else if(OFC_WHC_OUT_TYPE.equals(businessType)){
                statusDesc="二次拣货完成";
            }
        }else if(statusCode.equals(TRACE_STATUS_6)){
            if(OFC_WHC_IN_TYPE.equals(businessType)){

            }else if(OFC_WHC_OUT_TYPE.equals(businessType)){
                statusDesc="装车完成";
            }
        }else if(statusCode.equals(TRACE_STATUS_7)){
            if(OFC_WHC_IN_TYPE.equals(businessType)){

            }else if(OFC_WHC_OUT_TYPE.equals(businessType)){
                statusDesc="出库单已发运";
            }
        }else if(statusCode.equals(TRACE_STATUS_8)){
            if(OFC_WHC_IN_TYPE.equals(businessType)){
                statusDesc="入库单取消";
            }else if(OFC_WHC_OUT_TYPE.equals(businessType)){
                statusDesc="出库单取消";
            }
        }else if(statusCode.equals(TRACE_STATUS_9)){
            if(OFC_WHC_IN_TYPE.equals(businessType)){
                statusDesc="入库完毕";
            }else if(OFC_WHC_OUT_TYPE.equals(businessType)){
                statusDesc="出库完毕";
            }
        }
        return statusDesc;
    }




}
