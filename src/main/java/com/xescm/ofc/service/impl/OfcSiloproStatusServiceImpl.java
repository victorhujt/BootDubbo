package com.xescm.ofc.service.impl;

import static com.xescm.ofc.constant.OrderConstConstant.IMPLEMENTATIONIN;
import static com.xescm.ofc.constant.OrderConstConstant.YIDIAODU;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.domain.OfcSiloproNewstatus;
import com.xescm.ofc.domain.OfcSiloproStatus;
import com.xescm.ofc.domain.OfcSiloprogramInfo;
import com.xescm.ofc.domain.OfcTransplanInfo;
import com.xescm.ofc.domain.ofcSiloprogramStatusFedBackCondition;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcSiloproStatusMapper;
import com.xescm.ofc.model.vo.ofc.OfcSiloprogramInfoVo;
import com.xescm.ofc.service.OfcOrderStatusService;
import com.xescm.ofc.service.OfcSiloproNewstatusService;
import com.xescm.ofc.service.OfcSiloproStatusService;
import com.xescm.ofc.service.OfcSiloprogramInfoService;
import com.xescm.ofc.service.OfcTransplanInfoService;

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
    private OfcTransplanInfoService  ofcTransplanInfoService;
    
    @Autowired
    private OfcOrderStatusService ofcOrderStatusService;

    public int updateByPlanCode(Object key){
        ofcSiloproStatusMapper.updateByPlanCode(key);
        return 0;
    }

	@Override
	public void feedBackSiloproStatusFromWhc(
			ofcSiloprogramStatusFedBackCondition condition) {
		String orderCode="";
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
		if(orderStatus!=null){
			if(OrderConstConstant.HASBEENCOMPLETED.equals(orderStatus.getOrderStatus())){
				throw new BusinessException("该仓储计划单对应的订单已经完成");
			}
			
			if(OrderConstConstant.HASBEENCANCELED.equals(orderStatus.getOrderStatus())){
				throw new BusinessException("该仓储计划单对应的订单已经取消");
			}
			
			OfcSiloproNewstatus ofcTransplanNewstatus=new OfcSiloproNewstatus();
			ofcTransplanNewstatus.setPlanCode(planCode);
			//仓储计划单状态任务开始
			if(OrderConstConstant.TRACE_STATUS_1.equals(traceStatus)){
				statusCondition.setPlannedSingleState(OrderConstConstant.RENWUZHONG);
				statusCondition.setPlannedStartTime(condition.getTraceTime());
				ofcTransplanNewstatus.setJobNewStatus((OrderConstConstant.RENWUZHONG));//仓储计划单最新状态
				ofcTransplanNewstatus.setJobStatusUpdateTime(condition.getTraceTime());//作业状态更新时间
				orderStatus.setLastedOperTime(traceTime);
	            orderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(traceTime)
	                     +" "+"仓储计划单开始");

			}
			//仓储计划单状态任务开始任务已完成
			if(OrderConstConstant.TRACE_STATUS_4.equals(traceStatus)){
				statusCondition.setPlannedSingleState(OrderConstConstant.RENWUWANCH);
				statusCondition.setTaskCompletionTime(traceTime);
				ofcTransplanNewstatus.setJobNewStatus((OrderConstConstant.RENWUWANCH));
				ofcTransplanNewstatus.setJobStatusUpdateTime(condition.getTraceTime());
				orderStatus.setLastedOperTime(traceTime);
	            orderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(traceTime)
	                     +" "+"仓储计划单完成");
			}
			updateByPlanCode(statusCondition);//仓储计划单状态的更新
			ofcSiloproNewstatusService.updateByPlanCode(ofcTransplanNewstatus);//仓储计划单最新状态的更新
			//订单下只存在仓储计划单且仓储计划单全部已经完成,该订单状态可以改为已经完成
			List<OfcTransplanInfo> tsnfos=ofcTransplanInfoService.ofcTransplanInfoScreenList(orderCode);
			if(tsnfos!=null&&tsnfos.size()==0){
				List<OfcSiloprogramInfoVo> ofcSiloprogramInfoVoScreenList=ofcSiloprogramInfoService.ofcSiloprogramAndResourceInfo(orderCode,"");
				if(ofcSiloprogramInfoVoScreenList!=null&&ofcSiloprogramInfoVoScreenList.size()>0){
					boolean isCompleted=true;
					for (OfcSiloprogramInfoVo ofcSiloprogramInfoVo : ofcSiloprogramInfoVoScreenList) {
						if(!OrderConstConstant.RENWUWANCH.equals(ofcSiloprogramInfoVo.getPlannedSingleState())){
							isCompleted=false;
							break;
						}
					}
					//所有仓储计划单已经全部完成可将订单状态改为已完成
					if(isCompleted){
						orderStatus.setOrderStatus(OrderConstConstant.HASBEENCOMPLETED);
						orderStatus.setOrderCode(orderCode);
						orderStatus.setStatusDesc("已完成");
						orderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"订单已完成");
					}
				}
			}
			
			ofcOrderStatusService.save(orderStatus);
		}else{
			throw new BusinessException("该仓储计划单对应的订单不存在");
		}
	}
}
