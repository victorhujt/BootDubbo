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

    @Override
    public Wrapper<List<OfcPlanFedBackResult>> planFedBack(OfcPlanFedBackCondition ofcPlanFedBackCondition,AuthResDto authResDtoByToken) {
        Map<String,String> mapperMap = new HashMap<>();
        String transPortNo= PubUtils.trimAndNullAsEmpty(ofcPlanFedBackCondition.getTransportNo());
        String status= PubUtils.trimAndNullAsEmpty(ofcPlanFedBackCondition.getStatus());
        Date traceTime= ofcPlanFedBackCondition.getTraceTime();
        OfcTransplanNewstatus ofcTransplanNewstatus=new OfcTransplanNewstatus();
        OfcTransplanStatus ofcTransplanStatus=new OfcTransplanStatus();
        if(transPortNo.equals("")){
            throw new BusinessException("运输计划单号不可以为空");
        }else{
            ofcTransplanStatus.setPlanCode(ofcPlanFedBackCondition.getTransportNo());
            ofcTransplanNewstatus.setPlanCode(ofcPlanFedBackCondition.getTransportNo());
            if(status.equals("")){
                throw new BusinessException("跟踪状态不可以为空");
            }else {
                if(traceTime==null){
                    throw new BusinessException("跟踪时间不可以为空");
                }else {
                    if(status.equals(YIFAYUN)){
                        ofcTransplanNewstatus.setTransportSingleLatestStatus(status);
                    }else if(status.equals(YIDAODA)){
                        ofcTransplanNewstatus.setTransportSingleLatestStatus(status);
                        ofcTransplanStatus.setPlannedSingleState(RENWUWANCH);
                        ofcTransplanStatus.setTaskCompletionTime(traceTime);
                        ofcTransplanStatusService.updateByPlanCode(ofcTransplanStatus);
                        String orderCode=ofcTransplanInfoService.selectByKey(ofcPlanFedBackCondition.getTransportNo()).getOrderCode();
                        mapperMap.put("ifFinished","planfinish");
                        mapperMap.put("orderCode",orderCode);
                        List<OfcTransplanInfo> ofcTransplanInfos=ofcTransplanInfoMapper.ofcTransplanInfoScreenList(mapperMap);
                        if(ofcTransplanInfos.size()==0){
                            OfcOrderStatus ofcOrderStatus=new OfcOrderStatus();
                            ofcOrderStatus.setOrderCode(orderCode);
                            ofcOrderStatus.setOrderStatus(HASBEENCOMPLETED);
                            ofcOrderStatus.setLastedOperTime(new Date());
                            ofcOrderStatus.setStatusDesc(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                                    +" "+"订单已完成");
                            ofcOrderStatus.setOperator(authResDtoByToken.getUamUser().getUserName());
                            ofcOrderStatusService.save(ofcOrderStatus);
                        }
                    }else if(status.equals(YIQIANSHOU)){
                        ofcTransplanNewstatus.setTransportSingleLatestStatus(status);
                    }else if(status.equals(YIHUIDAN)){
                        ofcTransplanNewstatus.setTransportSingleLatestStatus(status);
                    }
                    ofcTransplanNewstatus.setTransportSingleUpdateTime(traceTime);
                    ofcTransplanNewstatusService.updateByPlanCode(ofcTransplanNewstatus);
                }

            }

        }
        return null;
    }
}
