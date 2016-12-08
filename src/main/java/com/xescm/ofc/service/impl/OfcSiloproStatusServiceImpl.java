package com.xescm.ofc.service.impl;

import static com.xescm.ofc.constant.OrderConstConstant.IMPLEMENTATIONIN;
import static com.xescm.ofc.constant.OrderConstConstant.YIDIAODU;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcSiloproStatusMapper;
import com.xescm.ofc.model.vo.ofc.OfcSiloprogramInfoVo;
import com.xescm.ofc.model.vo.ofc.OfcTransplanInfoVo;
import com.xescm.ofc.service.*;

import com.xescm.ofc.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcSiloproStatusServiceImpl extends BaseService<OfcSiloproStatus> implements OfcSiloproStatusService {
    @Autowired
    private OfcSiloproStatusMapper ofcSiloproStatusMapper;
    
    @Autowired
    private OfcSiloprogramInfoService ofcSiloprogramInfoService;
    
    @Autowired
    private OfcSiloproNewstatusService ofcSiloproNewstatusService;

    @Autowired
    private OfcOrderStatusService ofcOrderStatusService;

	@Autowired
	private OfcPlannedDetailService ofcPlannedDetailService;

    public int updateByPlanCode(Object key){
        ofcSiloproStatusMapper.updateByPlanCode(key);
        return 0;
    }

	@Override
	public void feedBackSiloproStatusFromWhc(
			ofcSiloprogramStatusFedBackCondition condition) {
		String orderCode;
		String planCode=condition.getPlanCode();
		String traceStatus=condition.getStatus();
		Date traceTime=condition.getTraceTime();
		if(StringUtils.isEmpty(planCode)){
			throw new BusinessException("仓储计划单号不可以为空");
		}
		if(StringUtils.isEmpty(condition.getStatus())){
			throw new BusinessException("跟踪状态不能为空");
		}	
		OfcSiloprogramInfo conditionInfo=new OfcSiloprogramInfo();
		conditionInfo.setPlanCode(planCode);
		OfcSiloprogramInfo info=ofcSiloprogramInfoService.selectOne(conditionInfo);
		if(info==null){
			throw new BusinessException("仓储计划单不存在");
		}
		orderCode=info.getOrderCode();
		OfcSiloproStatus statusCondition=new  OfcSiloproStatus();
		statusCondition.setPlanCode(planCode);
		OfcSiloproStatus infostatus=ofcSiloproStatusMapper.selectOne(statusCondition);
		if(infostatus!=null){
			if(OrderConstConstant.ZIYUANFENPEIZ.equals(infostatus.getPlannedSingleState())){
				throw new BusinessException("该仓储计划处于单资源分配中");
			}
			
			if(OrderConstConstant.RENWUWANCH.equals(infostatus.getPlannedSingleState())){
				throw new BusinessException("该仓储计划单已经完成");
			}
			
			if(OrderConstConstant.YIZUOFEI.equals(infostatus.getPlannedSingleState())){
				throw new BusinessException("该仓储计划单已经作废");
			}
		}
		
		OfcOrderStatus orderStatus=ofcOrderStatusService.orderStatusSelect(orderCode,"orderCode");
		OfcOrderStatus status=new OfcOrderStatus();
		if(orderStatus!=null){
			if(OrderConstConstant.HASBEENCOMPLETED.equals(orderStatus.getOrderStatus())){
				throw new BusinessException("该仓储计划单对应的订单已经完成");
			}
			
			if(OrderConstConstant.HASBEENCANCELED.equals(orderStatus.getOrderStatus())){
				throw new BusinessException("该仓储计划单对应的订单已经取消");
			}
			
			OfcSiloproNewstatus ofcSiloproNewstatus=new OfcSiloproNewstatus();
			ofcSiloproNewstatus.setPlanCode(planCode);
			//仓储计划单状态任务开始
			if(OrderConstConstant.TRACE_STATUS_1.equals(traceStatus)){
				statusCondition.setPlannedSingleState(OrderConstConstant.RENWUZHONG);
				statusCondition.setPlannedStartTime(condition.getTraceTime());
				ofcSiloproNewstatus.setJobNewStatus((OrderConstConstant.RENWUZHONG));//仓储计划单最新状态
				ofcSiloproNewstatus.setJobStatusUpdateTime(condition.getTraceTime());//作业状态更新时间
				ofcSiloproNewstatus.setOrderCode(infostatus.getOrderCode());
				status.setLastedOperTime(traceTime);
				status.setOrderStatus(OrderConstConstant.IMPLEMENTATIONIN);
				status.setNotes(DateUtils.Date2String(traceTime, DateUtils.DateFormatType.TYPE1)
	                     +" "+traceStatus);
				status.setOperator("");
				updateByPlanCode(statusCondition);//仓储计划单状态的更新
				ofcSiloproNewstatusService.updateByPlanCode(ofcSiloproNewstatus);//仓储计划单最新状态的更新
			}
			if(!orderStatus.getStatusDesc().equals(translateStatusToDesc(condition.getStatus(),info.getBusinessType()))){
				status.setLastedOperTime(traceTime);
				status.setStatusDesc(translateStatusToDesc(condition.getStatus(),info.getBusinessType()));
				status.setOrderCode(orderCode);
				status.setOperator("");
				status.setNotes(DateUtils.Date2String(traceTime, DateUtils.DateFormatType.TYPE1)
						 +" "+translateStatusToDesc(condition.getStatus(),info.getBusinessType()));
				status.setOrderCode(orderCode);
				ofcOrderStatusService.save(status);
			}
		}else{
			throw new BusinessException("该仓储计划单对应的订单不存在");
		}
	}

	@Override
	public void ofcWarehouseFeedBackFromWhc(ofcWarehouseFeedBackCondition condition) {
		String planCode=condition.getPlanCode();
		if(StringUtils.isEmpty(planCode)){
			throw new BusinessException("仓储计划单号不可以为空");
		}
		OfcSiloprogramInfo conditionInfo=new OfcSiloprogramInfo();
		conditionInfo.setPlanCode(planCode);
		OfcSiloprogramInfo info=ofcSiloprogramInfoService.selectOne(conditionInfo);
		if(info==null){
			throw new BusinessException("仓储计划单不存在");
		}
		OfcSiloproStatus statusCondition=new  OfcSiloproStatus();
		statusCondition.setPlanCode(planCode);
		OfcSiloproStatus sinfostatus=ofcSiloproStatusMapper.selectOne(statusCondition);
		if(sinfostatus!=null){
			if(OrderConstConstant.ZIYUANFENPEIZ.equals(sinfostatus.getPlannedSingleState())){
				throw new BusinessException("该仓储计划处于单资源分配中");
			}
			if(OrderConstConstant.RENWUWANCH.equals(sinfostatus.getPlannedSingleState())){
				throw new BusinessException("该仓储计划单已经完成");
			}
			if(OrderConstConstant.YIZUOFEI.equals(sinfostatus.getPlannedSingleState())){
				throw new BusinessException("该仓储计划单已经作废");
			}
		}
		List<OfcPlannedDetail> plannedDetails=condition.getPlannedDetails();
		if(plannedDetails!=null&&plannedDetails.size()>0){
      			for (OfcPlannedDetail plannedDetailnfo : plannedDetails) {
				boolean isExist=false;
				List<OfcPlannedDetail>  infos=ofcPlannedDetailService.planDetailsScreenList(condition.getPlanCode(),"planCode");
				if(infos!=null&&infos.size()>0){
					for (OfcPlannedDetail pdinfo:infos) {
						if(plannedDetailnfo.getGoodsCode().equals(pdinfo.getGoodsCode())){
							pdinfo.setRealQuantity(plannedDetailnfo.getRealQuantity());
							ofcPlannedDetailService.updateByPlanCode(pdinfo);//更新库存数量
							isExist=true;
							break;
						}
					}
				}
				if(!isExist){
					ofcPlannedDetailService.save(plannedDetailnfo);
				}
			}
		}else{
			throw new BusinessException("该仓储计划单不存在寄计划单详情");
		}
			statusCondition.setPlannedSingleState(OrderConstConstant.RENWUWANCH);
			updateByPlanCode(statusCondition);//仓储计划单状态的更新
		List<String> infos=ofcSiloprogramInfoService.ofcMaxSiloprogramInfoSerialNumberScreenList(info.getOrderCode(),OrderConstConstant.OFC_WHC_IN_TYPE);
		if(infos!=null&&infos.size()>0){
			String ProgramSerialNumber=infos.get(0);
			if(ProgramSerialNumber.equals(info.getProgramSerialNumber())){
				OfcOrderStatus status=new OfcOrderStatus();
				status.setOrderStatus(OrderConstConstant.HASBEENCOMPLETED);
				status.setOrderCode(info.getOrderCode());
				status.setStatusDesc("已完成");
				status.setNotes(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1)+"订单已完成");
				status.setLastedOperTime(new Date());
				status.setOperator("");
				ofcOrderStatusService.save(status);
			}
		}
	}

	public String translateStatusToDesc(String statusCode,String businessType){
		String statusDesc="";
		if(statusCode.equals(OrderConstConstant.TRACE_STATUS_1)){
			if(OrderConstConstant.OFC_WHC_IN_TYPE.equals(businessType)){
				statusDesc="入库单已创建";

			}else if(OrderConstConstant.OFC_WHC_OUT_TYPE.equals(businessType)){
				statusDesc="出库单已创建";
			}
	}else if(statusCode.equals(OrderConstConstant.TRACE_STATUS_2)){
			if(OrderConstConstant.OFC_WHC_IN_TYPE.equals(businessType)){
				statusDesc="部分收货";

			}else if(OrderConstConstant.OFC_WHC_OUT_TYPE.equals(businessType)){

			}
		}else if(statusCode.equals(OrderConstConstant.TRACE_STATUS_3)){
			if(OrderConstConstant.OFC_WHC_IN_TYPE.equals(businessType)){
				statusDesc="完全收货";
			}else if(OrderConstConstant.OFC_WHC_OUT_TYPE.equals(businessType)){
				statusDesc="出库分配完成";
			}
		}else if(statusCode.equals(OrderConstConstant.TRACE_STATUS_4)){
			if(OrderConstConstant.OFC_WHC_IN_TYPE.equals(businessType)){

			}else if(OrderConstConstant.OFC_WHC_OUT_TYPE.equals(businessType)){
				statusDesc="拣货完成";
			}
		}else if(statusCode.equals(OrderConstConstant.TRACE_STATUS_5)){
			if(OrderConstConstant.OFC_WHC_IN_TYPE.equals(businessType)){

			}else if(OrderConstConstant.OFC_WHC_OUT_TYPE.equals(businessType)){
				statusDesc="二次拣货完成";
			}
		}else if(statusCode.equals(OrderConstConstant.TRACE_STATUS_6)){
			if(OrderConstConstant.OFC_WHC_IN_TYPE.equals(businessType)){

			}else if(OrderConstConstant.OFC_WHC_OUT_TYPE.equals(businessType)){
				statusDesc="装车完成";
			}
		}else if(statusCode.equals(OrderConstConstant.TRACE_STATUS_7)){
			if(OrderConstConstant.OFC_WHC_IN_TYPE.equals(businessType)){

			}else if(OrderConstConstant.OFC_WHC_OUT_TYPE.equals(businessType)){
				statusDesc="出库单已发运";
			}
		}else if(statusCode.equals(OrderConstConstant.TRACE_STATUS_8)){
			if(OrderConstConstant.OFC_WHC_IN_TYPE.equals(businessType)){
				statusDesc="入库单取消";
			}else if(OrderConstConstant.OFC_WHC_OUT_TYPE.equals(businessType)){
				statusDesc="出库单取消";
			}
		}else if(statusCode.equals(OrderConstConstant.TRACE_STATUS_9)){
			if(OrderConstConstant.OFC_WHC_IN_TYPE.equals(businessType)){
				statusDesc="入库完毕";
			}else if(OrderConstConstant.OFC_WHC_OUT_TYPE.equals(businessType)){
				statusDesc="出库完毕";
			}
		}
		return statusDesc;
	}






}
