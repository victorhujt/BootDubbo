package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.OrderConst;
import com.xescm.ofc.utils.PubUtils;
import com.xescm.ofc.wrap.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ydx on 2016/10/12.
 */
@Service
public class OfcOrderManageServiceImpl  implements OfcOrderManageService {
    @Autowired
    private OfcOrderStatusService ofcOrderStatusService;

    @Autowired
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Autowired
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Autowired
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;
    @Autowired
    private OfcWarehouseInformationService ofcWarehouseInformationService;

    @Override
    public String orderAudit(String orderCode,String orderStatus) {
        OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
        ofcOrderStatus.setOrderCode(orderCode);
        ofcOrderStatus.setOrderStatus(orderStatus);
        System.out.println(ofcOrderStatus);
        if((!ofcOrderStatus.getOrderStatus().equals(OrderConst.IMPLEMENTATIONIN))
                && (!ofcOrderStatus.getOrderStatus().equals(OrderConst.HASBEENCOMPLETED))
                && (!ofcOrderStatus.getOrderStatus().equals(OrderConst.HASBEENCANCELED))){
            if (ofcOrderStatus.getOrderStatus().equals(OrderConst.ALREADYEXAMINE)){
                ofcOrderStatus.setOrderStatus(OrderConst.PENDINGAUDIT);
                ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                        +" "+"订单反审核完成");
            }else if(ofcOrderStatus.getOrderStatus().equals(OrderConst.PENDINGAUDIT)){
                ofcOrderStatus.setOrderStatus(OrderConst.ALREADYEXAMINE);
                ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                        +" "+"订单审核完成");
            }
            ofcOrderStatus.setOperator("001");
            ofcOrderStatus.setLastedOperTime(new Date());
            ofcOrderStatusService.save(ofcOrderStatus);
            return String.valueOf(Wrapper.SUCCESS_CODE);
        }else {
            return String.valueOf(Wrapper.ERROR_CODE);
        }
    }

    @Override
    public String orderDelete(String orderCode,String orderStatus) {
        if(orderStatus.equals(OrderConst.PENDINGAUDIT)){
            ofcFundamentalInformationService.deleteByKey(orderCode);
            ofcDistributionBasicInfoService.deleteByOrderCode(orderCode);
            ofcOrderStatusService.deleteByOrderCode(orderCode);
            ofcWarehouseInformationService.deleteByOrderCode(orderCode);
            return String.valueOf(Wrapper.SUCCESS_CODE);
        }else {
            return String.valueOf(Wrapper.ERROR_CODE);
       }
    }

    @Override
    public String orderCancel(String orderCode,String orderStatus) {
        OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
        ofcOrderStatus.setOrderCode(orderCode);
        ofcOrderStatus.setOrderStatus(orderStatus);
        if((!ofcOrderStatus.getOrderStatus().equals(OrderConst.PENDINGAUDIT))
                && (!ofcOrderStatus.getOrderStatus().equals(OrderConst.HASBEENCOMPLETED))
                && (!ofcOrderStatus.getOrderStatus().equals(OrderConst.HASBEENCANCELED))){
            ofcOrderStatus.setOrderStatus(OrderConst.HASBEENCANCELED);
            ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                    +" "+"订单已取消");
            ofcOrderStatus.setOperator("001");
            ofcOrderStatus.setLastedOperTime(new Date());
            ofcOrderStatusService.save(ofcOrderStatus);
            return String.valueOf(Wrapper.SUCCESS_CODE);
        }else {
            return String.valueOf(Wrapper.ERROR_CODE);
        }
    }

    @Override
    public OfcOrderDTO getOrderDetailByCode(String orderCode) {
        return null;
    }
}
