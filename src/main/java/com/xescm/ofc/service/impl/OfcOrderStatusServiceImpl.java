package com.xescm.ofc.service.impl;

import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.edas.model.dto.whc.FeedBackOrderDetailDto;
import com.xescm.ofc.edas.model.dto.whc.FeedBackOrderDto;
import com.xescm.ofc.edas.model.dto.whc.FeedBackOrderStatusDto;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcOrderNewstatusMapper;
import com.xescm.ofc.mapper.OfcOrderStatusMapper;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.DateUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
public class OfcOrderStatusServiceImpl extends BaseService<OfcOrderStatus> implements OfcOrderStatusService {
    private static final Logger logger = LoggerFactory.getLogger(OfcOrderStatusServiceImpl.class);
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
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;



    @Override
    public int deleteByOrderCode(Object key) {
        return ofcOrderStatusMapper.deleteByOrderCode(key);
    }

    @Override
    public List<OfcOrderStatus> orderStatusScreen(String code, String followTag) {
        if (!PubUtils.trimAndNullAsEmpty(code).equals("")) {
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
        if (!PubUtils.trimAndNullAsEmpty(code).equals("")) {
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
                    || PubUtils.trimAndNullAsEmpty(orderNewstatus.getOrderCode()).equals("")
                    || PubUtils.trimAndNullAsEmpty(orderNewstatus.getOrderLatestStatus()).equals("")){

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
        orderNewstatus.setOrderLatestStatus(HASBEENCANCELED);
        orderNewstatus.setStatusUpdateTime(new Date());
        ofcOrderNewstatusService.update(orderNewstatus);
        ofcOrderStatusMapper.cancelOrderStateByOrderCode(orderCode);
    }

    @Override
    public OfcOrderStatus queryLastUpdateOrderByOrderCode(String orderCode) {
        OfcOrderNewstatus orderNewstatus=ofcOrderNewstatusService.selectByKey(orderCode);
        OfcOrderStatus ofcOrderStatus=new OfcOrderStatus();
        if(orderNewstatus==null
                || PubUtils.trimAndNullAsEmpty(orderNewstatus.getOrderCode()).equals("")
                || PubUtils.trimAndNullAsEmpty(orderNewstatus.getOrderLatestStatus()).equals("")){
            ofcOrderStatus = ofcOrderStatusMapper.queryLastUpdateOrderByOrderCode(orderCode);
        }else{
            ofcOrderStatus.setOrderCode(orderNewstatus.getOrderCode());
            ofcOrderStatus.setOrderStatus(orderNewstatus.getOrderLatestStatus());
        }
        return ofcOrderStatus;
    }

    public OfcOrderStatus queryLastTimeOrderByOrderCode(String orderCode) {
        OfcOrderNewstatus orderNewstatus=ofcOrderNewstatusService.selectByKey(orderCode);
        OfcOrderStatus ofcOrderStatus=new OfcOrderStatus();
        if(orderNewstatus==null
                || PubUtils.trimAndNullAsEmpty(orderNewstatus.getOrderCode()).equals("")
                || PubUtils.trimAndNullAsEmpty(orderNewstatus.getOrderLatestStatus()).equals("")){
            ofcOrderStatus = ofcOrderStatusMapper.queryLastTimeOrderByOrderCode(orderCode);
        }else{
            ofcOrderStatus.setOrderCode(orderNewstatus.getOrderCode());
            ofcOrderStatus.setOrderStatus(orderNewstatus.getOrderLatestStatus());
        }
        return ofcOrderStatus;
    }

    @Override
    public int save(OfcOrderStatus ofcOrderStatus) {
        if (ofcOrderStatus!=null
                && !PubUtils.trimAndNullAsEmpty(ofcOrderStatus.getOrderCode()).equals("")){
            if(!PubUtils.trimAndNullAsEmpty(ofcOrderStatus.getOrderStatus()).equals("")){
                OfcOrderNewstatus orderNewstatus=ofcOrderNewstatusService.selectByKey(ofcOrderStatus.getOrderCode());
                String tag="noStatus";
                if(orderNewstatus!=null){
                    tag="haveStatus";
                }else {
                    orderNewstatus=new OfcOrderNewstatus();
                }
                if(ofcOrderStatus.getOrderStatus().equals(IMPLEMENTATIONIN)){
                    if(!PubUtils.trimAndNullAsEmpty(orderNewstatus.getOrderLatestStatus()).equals(HASBEENCOMPLETED)){
                        updateOrderNewStatus(ofcOrderStatus,tag);
                    }
                }else{
                    updateOrderNewStatus(ofcOrderStatus,tag);
                }
            }else {
                throw new BusinessException("订单状态为空，保存订单状态失败");
            }
        }
        return super.save(ofcOrderStatus);
    }

    @Override
    public void feedBackStatusFromWhc(FeedBackOrderStatusDto feedBackOrderStatusDto) {
        try {
            String orderCode=feedBackOrderStatusDto.getOrderCode();
            String traceStatus=feedBackOrderStatusDto.getStatus();
            Date traceTime=feedBackOrderStatusDto.getTraceTime();
            if(StringUtils.isEmpty(orderCode)){
                throw new BusinessException("订单号不可以为空");
            }
            if(StringUtils.isEmpty(feedBackOrderStatusDto.getStatus())){
                throw new BusinessException("跟踪状态不能为空");
            }

            OfcFundamentalInformation ofcFundamentalInformation=ofcFundamentalInformationService.selectByKey(orderCode);

            OfcWarehouseInformation ofcWarehouseInformation=ofcWarehouseInformationService.selectByKey(orderCode);
            if(ofcFundamentalInformation==null){
                throw new BusinessException("订单不存在");
            }

            OfcOrderStatus orderStatus=orderStatusSelect(orderCode,"orderCode");
            OfcOrderStatus status=new OfcOrderStatus();
            if(orderStatus!=null){
                if(HASBEENCOMPLETED.equals(orderStatus.getOrderStatus())){
                    throw new BusinessException("订单已经完成");
                }

                if(HASBEENCANCELED.equals(orderStatus.getOrderStatus())){
                    throw new BusinessException("订单已经取消");
                }

                String statusDesc=translateStatusToDesc(traceStatus,ofcFundamentalInformation.getBusinessType());

                if(orderStatus.getStatusDesc().indexOf(statusDesc)<0){
                    status.setLastedOperTime(new Date());
                    status.setStatusDesc(statusDesc);
                    status.setOrderCode(orderCode);
                    status.setOperator("");
                    status.setOrderStatus(orderStatus.getOrderStatus());
                    status.setNotes(DateUtils.Date2String(traceTime, DateUtils.DateFormatType.TYPE1)
                            +" "+translateStatusToDesc(orderCode,ofcFundamentalInformation.getBusinessType()));
                    status.setOrderCode(orderCode);
                    if (PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0,2).equals("61")){
                        if("出库完毕".equals(statusDesc)){
                            //提供运输
                            if(ofcWarehouseInformation!=null&&ofcWarehouseInformation.getProvideTransport()==WAREHOUSEORDERPROVIDETRANS){


                            //不提供运输
                            }else if(ofcWarehouseInformation.getProvideTransport()==null||ofcWarehouseInformation.getProvideTransport()==WAREHOUSEORDERNOTPROVIDETRANS){
                                save(status);
                            }
                            super.save(status);
                        }
                    }else if(PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0,2).equals("62")){
                        if("入库完毕".equals(statusDesc)){
                            save(status);
                        }else{
                            super.save(status);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public void ofcWarehouseFeedBackFromWhc(FeedBackOrderDto feedBackOrderDto) {
        try {
			String orderCode=feedBackOrderDto.getOrderCode();
			List<FeedBackOrderDetailDto> detailDtos=feedBackOrderDto.getFeedBackOrderDetail();
			if(StringUtils.isEmpty(orderCode)){
				throw new BusinessException("订单号不可以为空");
			}
			if(detailDtos==null||detailDtos!=null&&detailDtos.size()==0){
                throw new BusinessException("货品详情不能为空");
            }

            for (FeedBackOrderDetailDto feedBackOrderDetailDto : detailDtos) {
                boolean isExist=false;
                List<OfcGoodsDetailsInfo>  infos=ofcGoodsDetailsInfoService.queryByOrderCode(orderCode);
                if(infos!=null&&infos.size()>0){
                    for (OfcGoodsDetailsInfo ofcGoodsDetailsInfo:infos) {
                        if(feedBackOrderDetailDto.getGoodsCode().equals(ofcGoodsDetailsInfo.getGoodsCode())){
                            ofcGoodsDetailsInfo.setRealQuantity(feedBackOrderDetailDto.getRealQuantity());
                            ofcGoodsDetailsInfoService.updateByOrderCode(ofcGoodsDetailsInfo);
                            isExist=true;
                            break;
                        }
                    }
                }
                if(!isExist){
                    OfcGoodsDetailsInfo newInfo=new OfcGoodsDetailsInfo();
                    BeanUtils.copyProperties(newInfo,feedBackOrderDetailDto);
                    ofcGoodsDetailsInfoService.save(newInfo);
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
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
