package com.xescm.ofc.controller;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.OrderConst;
import com.xescm.ofc.utils.PubUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ydx on 2016/10/11.
 */
@Controller
public class OfcOrderManageController {
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


    @RequestMapping(value = "/orderManage")
    public String orderManage(){
        return "order_manage";
    }

    public PubUtils pubUtils=new PubUtils();
    private OrderConst orderConst=new OrderConst();

    /*订单的审核和反审核*/
    @RequestMapping("/orderOrNotAudit")
    @ResponseBody
    public String orderAudit(@ModelAttribute("ofcFundamentalInformation")OfcFundamentalInformation ofcFundamentalInformation,
                             @ModelAttribute("ofcOrderStatus")OfcOrderStatus ofcOrderStatus){
        ofcOrderStatus.setOrderStatus("1");
        if((!ofcOrderStatus.getOrderStatus().equals(orderConst.IMPLEMENTATIONIN))
                && (!ofcOrderStatus.getOrderStatus().equals(orderConst.HASBEENCOMPLETED))
                && (!ofcOrderStatus.getOrderStatus().equals(orderConst.HASBEENCANCELED))){
            if (ofcOrderStatus.getOrderStatus().equals(orderConst.ALREADYEXAMINE)){
                ofcOrderStatus.setOrderStatus(orderConst.PENDINGAUDIT);
                ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                        +" "+"订单反审核完成");
            }else if(ofcOrderStatus.getOrderStatus().equals(orderConst.PENDINGAUDIT)){
                ofcOrderStatus.setOrderStatus(orderConst.ALREADYEXAMINE);
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

    @RequestMapping("/orderDelete")
    public String orderDelete(@ModelAttribute("ofcFundamentalInformation")OfcFundamentalInformation ofcFundamentalInformation,
                            @ModelAttribute("ofcOrderStatus")OfcOrderStatus ofcOrderStatus){
        //if(ofcOrderStatus.getOrderStatus().equals(orderConst.PENDINGAUDIT)){
            ofcFundamentalInformationService.deleteByKey("SO20161010000005");
            ofcDistributionBasicInfoService.deleteByOrderCode("SO20161010000005");
            ofcOrderStatusService.deleteByOrderCode("SO20161010000005");
            ofcWarehouseInformationService.deleteByOrderCode("SO20161010000005");
            return "success";
        //}else {
            //return "fail";
        //}
    }

    @RequestMapping("/orderCancel")
    public String orderCancel(@ModelAttribute("ofcFundamentalInformation")OfcFundamentalInformation ofcFundamentalInformation,
                              @ModelAttribute("ofcOrderStatus")OfcOrderStatus ofcOrderStatus){
        if((!ofcOrderStatus.getOrderStatus().equals(orderConst.PENDINGAUDIT))
                && (!ofcOrderStatus.getOrderStatus().equals(orderConst.HASBEENCOMPLETED))
                && (!ofcOrderStatus.getOrderStatus().equals(orderConst.HASBEENCANCELED))){
            ofcOrderStatus.setOrderStatus(orderConst.HASBEENCANCELED);
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
