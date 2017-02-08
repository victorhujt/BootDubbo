package com.xescm.ofc.service.impl;

import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcSiloproStatusMapper;
import com.xescm.ofc.model.vo.ofc.OfcTransplanInfoVo;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.xescm.ofc.constant.OrderConstConstant.*;

/**
 *
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcSiloproStatusServiceImpl extends BaseService<OfcSiloproStatus> implements OfcSiloproStatusService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private OfcSiloproStatusMapper ofcSiloproStatusMapper;
    
    @Resource
    private OfcSiloprogramInfoService ofcSiloprogramInfoService;
    
    @Resource
    private OfcSiloproNewstatusService ofcSiloproNewstatusService;

    @Resource
    private OfcOrderStatusService ofcOrderStatusService;

	@Resource
	private OfcPlannedDetailService ofcPlannedDetailService;
	@Resource
	private OfcTransplanInfoService ofcTransplanInfoService;
	@Resource
	private OfcTransplanStatusService ofcTransplanStatusService;
	@Resource
	private OfcOrderManageService ofcOrderManageService;

    public int updateByPlanCode(Object key){

        return ofcSiloproStatusMapper.updateByPlanCode(key);
    }

	@Override
	public void feedBackSiloproStatusFromWhc(
			OfcSiloprogramStatusFedBackCondition condition) {
		try {
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
				if(RESOURCE_ALLOCATION.equals(infostatus.getPlannedSingleState())){
					throw new BusinessException("该仓储计划处于单资源分配中");
				}

				if(TASK_ACCOMPLISHED.equals(infostatus.getPlannedSingleState())){
					throw new BusinessException("该仓储计划单已经完成");
				}

				if(ALREADY_CANCELLED.equals(infostatus.getPlannedSingleState())){
					throw new BusinessException("该仓储计划单已经作废");
				}
			}

			OfcOrderStatus orderStatus=ofcOrderStatusService.orderStatusSelect(orderCode,"orderCode");
			OfcOrderStatus status=new OfcOrderStatus();
			if(orderStatus!=null){
				if(HASBEEN_COMPLETED.equals(orderStatus.getOrderStatus())){
					throw new BusinessException("该仓储计划单对应的订单已经完成");
				}

				if(HASBEEN_CANCELED.equals(orderStatus.getOrderStatus())){
					throw new BusinessException("该仓储计划单对应的订单已经取消");
				}

				OfcSiloproNewstatus ofcSiloproNewstatus=new OfcSiloproNewstatus();
				ofcSiloproNewstatus.setPlanCode(planCode);
				//仓储计划单状态任务开始
				if(TRACE_STATUS_1.equals(traceStatus)){
					statusCondition.setPlannedSingleState(TASK);
					statusCondition.setPlannedStartTime(condition.getTraceTime());
					ofcSiloproNewstatus.setJobNewStatus((TASK));//仓储计划单最新状态
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String dateStr =format.format(condition.getTraceTime());
					Date dateFor=format.parse(dateStr);
					ofcSiloproNewstatus.setJobStatusUpdateTime(dateFor);//作业状态更新时间
					ofcSiloproNewstatus.setOrderCode(infostatus.getOrderCode());
					status.setLastedOperTime(traceTime);
					status.setOrderStatus(IMPLEMENTATION_IN);
					status.setNotes(DateUtils.Date2String(traceTime, DateUtils.DateFormatType.TYPE1)
							+" "+traceStatus);
					status.setOperator("");
					updateByPlanCode(statusCondition);//仓储计划单状态的更新
					ofcSiloproNewstatusService.updateByPlanCode(ofcSiloproNewstatus);//仓储计划单最新状态的更新
				}
				if(orderStatus.getStatusDesc().indexOf(translateStatusToDesc(condition.getStatus(),info.getBusinessType()))<0){
					status.setLastedOperTime(new Date());
					status.setStatusDesc(translateStatusToDesc(condition.getStatus(),info.getBusinessType()));
					status.setOrderCode(orderCode);
					status.setOperator("");
					status.setOrderStatus(orderStatus.getOrderStatus());
					status.setNotes(DateUtils.Date2String(traceTime, DateUtils.DateFormatType.TYPE1)
							+" "+translateStatusToDesc(condition.getStatus(),info.getBusinessType()));
					status.setOrderCode(orderCode);
					ofcOrderStatusService.save(status);
				}
			}else{
				throw new BusinessException("该仓储计划单对应的订单不存在");
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}

	}

	@Override
	public void ofcWarehouseFeedBackFromWhc(ofcWarehouseFeedBackCondition condition) {
		try {
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
				if(RESOURCE_ALLOCATION.equals(sinfostatus.getPlannedSingleState())){
					throw new BusinessException("该仓储计划处于单资源分配中");
				}
				if(TASK_ACCOMPLISHED.equals(sinfostatus.getPlannedSingleState())){
					throw new BusinessException("该仓储计划单已经完成");
				}
				if(ALREADY_CANCELLED.equals(sinfostatus.getPlannedSingleState())){
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
				throw new BusinessException("该仓储计划单不存在计划单详情");
			}
			statusCondition.setPlannedSingleState(TASK_ACCOMPLISHED);
			updateByPlanCode(statusCondition);//仓储计划单状态的更新
			List<OfcTransplanInfoVo> transInfos=ofcTransplanInfoService.ofcTransplanInfoVoList(planCode);
			if(OrderConstConstant.OFC_WHC_IN_TYPE.equals(condition.getBuniessType())){
				if(transInfos!=null&&transInfos.size()>0){
					OfcTransplanInfoVo vo=transInfos.get(0);
					OfcOrderStatus status=new OfcOrderStatus();
					if(OrderConstConstant.TASK_ACCOMPLISHED.equals(vo.getPlannedSingleState())){
						status.setOrderStatus(HASBEEN_COMPLETED);
						status.setOrderCode(info.getOrderCode());
						status.setStatusDesc("已完成");
						status.setNotes(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1)+"订单已完成");
						status.setLastedOperTime(new Date());
						status.setOperator("");
					}else{
						OfcTransplanStatus ofcTransplanStatus=new OfcTransplanStatus();
						ofcTransplanStatus.setPlannedSingleState(OrderConstConstant.TASK_ACCOMPLISHED);
						ofcTransplanStatusService.updateByPlanCode(ofcTransplanStatus);
						status.setOrderStatus(HASBEEN_COMPLETED);
						status.setOrderCode(info.getOrderCode());
						status.setStatusDesc("已完成");
						status.setNotes(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1)+"订单已完成");
						status.setLastedOperTime(new Date());
						status.setOperator("");
					}
					ofcOrderStatusService.save(status);
					if(StringUtils.equals(status.getOrderStatus(), OrderConstConstant.HASBEEN_COMPLETED)){
						//订单中心--订单状态推结算中心(执行中和已完成)
						ofcOrderManageService.pullOfcOrderStatus(status);
					}
				}
			}
			if(transInfos==null||transInfos.size()==0){
				OfcOrderStatus status=new OfcOrderStatus();
				status.setOrderStatus(HASBEEN_COMPLETED);
				status.setOrderCode(info.getOrderCode());
				status.setStatusDesc("已完成");
				status.setNotes(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1)+"订单已完成");
				status.setLastedOperTime(new Date());
				status.setOperator("");
				ofcOrderStatusService.save(status);
				//订单中心--订单状态推结算中心(执行中和已完成)
				ofcOrderManageService.pullOfcOrderStatus(status);
			}
		} catch (Exception e) {
			logger.error("ofcWarehouseFeedBackFromWhc, {}",e);
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


	/**
	 * 查询未完成未作废的仓储计划单
	 * @param orderCode
	 * @return
	 */
	@Override
	public List<OfcSiloproStatus> queryUncompletedPlanCodesByOrderCode(String orderCode) {
		return ofcSiloproStatusMapper.queryUncompletedPlanCodesByOrderCode(orderCode);
	}



}
