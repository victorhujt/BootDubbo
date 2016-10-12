package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.OrderConst;
import com.xescm.ofc.utils.PubUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ydx on 2016/10/12.
 */
public class OfcOrderManageServiceImpl extends BaseService<OfcOrderDTO> implements OfcOrderManageService {
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
    public String orderAudit(OfcOrderDTO ofcOrderDTO) {
        OfcFundamentalInformation ofcFundamentalInformation;
        OfcOrderStatus ofcOrderStatus=new OfcOrderStatus();
        ofcOrderStatus.setOrderStatus("1");
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
            return "success";
        }else {
            return "fail";
        }
    }

    @Override
    public String orderDelete(OfcOrderDTO ofcOrderDTO) {
        OfcFundamentalInformation ofcFundamentalInformation;
        OfcOrderStatus ofcOrderStatus=new OfcOrderStatus();
        //if(ofcOrderStatus.getOrderStatus().equals(OrderConst.PENDINGAUDIT)){
        ofcFundamentalInformationService.deleteByKey("SO20161010000005");
        ofcDistributionBasicInfoService.deleteByOrderCode("SO20161010000005");
        ofcOrderStatusService.deleteByOrderCode("SO20161010000005");
        ofcWarehouseInformationService.deleteByOrderCode("SO20161010000005");
        return "success";
        //}else {
        //return "fail";
        //}
    }

    @Override
    public String orderCancel(OfcOrderDTO ofcOrderDTO) {
        OfcFundamentalInformation ofcFundamentalInformation;
        OfcOrderStatus ofcOrderStatus=new OfcOrderStatus();
        if((!ofcOrderStatus.getOrderStatus().equals(OrderConst.PENDINGAUDIT))
                && (!ofcOrderStatus.getOrderStatus().equals(OrderConst.HASBEENCOMPLETED))
                && (!ofcOrderStatus.getOrderStatus().equals(OrderConst.HASBEENCANCELED))){
            ofcOrderStatus.setOrderStatus(OrderConst.HASBEENCANCELED);
            ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                    +" "+"订单已取消");
            ofcOrderStatus.setOperator("001");
            ofcOrderStatus.setLastedOperTime(new Date());
            ofcOrderStatusService.save(ofcOrderStatus);
            return "success";
        }else {
            return "fail";
        }
    }
}
