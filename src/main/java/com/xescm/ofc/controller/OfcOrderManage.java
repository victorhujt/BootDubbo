package com.xescm.ofc.controller;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.PubUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ydx on 2016/10/11.
 */
@RestController
public class OfcOrderManage {
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
    public PubUtils pubUtils=new PubUtils();

    @RequestMapping("/orderOrNotAudit")
    public String orderAudit(@ModelAttribute("ofcFundamentalInformation")OfcFundamentalInformation ofcFundamentalInformation,
                             @ModelAttribute("ofcOrderStatus")OfcOrderStatus ofcOrderStatus){

        if((!ofcOrderStatus.getOrderStatus().equals("2"))
                && (!ofcOrderStatus.getOrderStatus().equals("3"))
                && (!ofcOrderStatus.getOrderStatus().equals("4"))){
            if (ofcOrderStatus.getOrderStatus().equals("1")){
                ofcOrderStatus.setOrderStatus("0");
                ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                        +" "+"订单反审核完成");
            }else if(ofcOrderStatus.getOrderStatus().equals("0")){
                ofcOrderStatus.setOrderStatus("1");
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
        //if(ofcOrderStatus.getOrderStatus().equals("0")){
            ofcFundamentalInformationService.deleteByKey("SO20161010000012");
            ofcDistributionBasicInfoService.deleteByOrderCode("SO20161010000012");
            ofcOrderStatusService.deleteByOrderCode("SO20161010000012");
            ofcWarehouseInformationService.deleteByOrderCode("SO20161010000012");
            return "success";
        //}else {
            //return "fail";
        //}
    }

    @RequestMapping("/orderCancel")
    public String orderCancel(@ModelAttribute("ofcFundamentalInformation")OfcFundamentalInformation ofcFundamentalInformation,
                              @ModelAttribute("ofcOrderStatus")OfcOrderStatus ofcOrderStatus){
        if((!ofcOrderStatus.getOrderStatus().equals("0"))
                && (!ofcOrderStatus.getOrderStatus().equals("3"))
                && (!ofcOrderStatus.getOrderStatus().equals("4"))){
            ofcOrderStatus.setOrderStatus("4");
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
