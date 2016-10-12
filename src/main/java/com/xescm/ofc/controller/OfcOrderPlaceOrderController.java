package com.xescm.ofc.controller;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.PrimaryGenerater;
import com.xescm.ofc.utils.PubUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by lyh on 2016/10/8.
 */
@RestController
public class OfcOrderPlaceOrderController {

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
    /**
     * 下单接口
     * @param ofcGoodsDetailsInfo
     * @param ofcFundamentalInformation
     * @param ofcDistributionBasicInfo
     * @param ofcWarehouseInformation
     * @return
     */



    @RequestMapping("/placeOrEditOrder")
    public String placeOrder(@ModelAttribute("ofcGoodsDetailsInfo")OfcGoodsDetailsInfo ofcGoodsDetailsInfo,
                             @ModelAttribute("ofcFundamentalInformation")OfcFundamentalInformation ofcFundamentalInformation,
                             @ModelAttribute("ofcDistributionBasicInfo")OfcDistributionBasicInfo ofcDistributionBasicInfo,
                             @ModelAttribute("ofcWarehouseInformation")OfcWarehouseInformation ofcWarehouseInformation
                             ){
        OfcOrderStatus ofcOrderStatus=new OfcOrderStatus();
        try {
            if (ofcFundamentalInformationService.selectOne(ofcFundamentalInformation)==null){
                ofcFundamentalInformation.setOrderCode("SO"+PrimaryGenerater.getInstance()
                        .generaterNextNumber(PrimaryGenerater.getInstance().getLastNumber()));
                ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                        +" "+"订单已创建");
                upOrderStatus(ofcOrderStatus,ofcFundamentalInformation);
                ofcDistributionBasicInfo=upDistributionBasicInfo(ofcDistributionBasicInfo,ofcFundamentalInformation);
                ofcWarehouseInformation=upOfcWarehouseInformation(ofcWarehouseInformation,ofcFundamentalInformation);
                ofcDistributionBasicInfoService.save(ofcDistributionBasicInfo);
                ofcWarehouseInformationService.save(ofcWarehouseInformation);
            }else{
                ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                        +" "+"订单已更新");
            }
        } catch (Exception e) {
            return "fail";
        }

        /*if (ofcFundamentalInformation.getOrderType().equals("1")){
            ofcFundamentalInformation.setCustCode("001");
            ofcFundamentalInformation.setCustName("众品");
            if(ofcWarehouseInformation.getProvideTransport().equals("1")){
                ofcFundamentalInformation.setSecCustCode("001");
                ofcFundamentalInformation.setSecCustName("众品");
            }
        }else {
            ofcFundamentalInformation.setSecCustCode("001");
            ofcFundamentalInformation.setSecCustName("众品");
            if (pubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getDeparturePlace())
                    .equals(pubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getDestination()))){
                ofcFundamentalInformation.setBusinessType("00");
            }else{
                ofcFundamentalInformation.setBusinessType("01");
            }
        }*/
        ofcFundamentalInformation.setOrderTime(new Date());
        ofcFundamentalInformation.setStoreCode("000");
        ofcFundamentalInformation.setStoreName("线下销售");
        ofcFundamentalInformation.setOrderSource("手动");
        ofcFundamentalInformation.setCreationTime(new Date());
        ofcFundamentalInformation.setCreator("001");
        ofcFundamentalInformation.setOperator("001");
        ofcFundamentalInformation.setOpertime(new Date());

        ofcFundamentalInformationService.save(ofcFundamentalInformation);
        return "success";
    }

    public void upOrderStatus(OfcOrderStatus ofcOrderStatus,OfcFundamentalInformation ofcFundamentalInformation){
        ofcOrderStatus.setOrderCode(ofcFundamentalInformation.getOrderCode());
        ofcOrderStatus.setOrderType(ofcFundamentalInformation.getOrderType());
        ofcOrderStatus.setBusinessType(ofcFundamentalInformation.getBusinessType());
        ofcOrderStatus.setCustName(ofcFundamentalInformation.getCustName());
        ofcOrderStatus.setCustCode(ofcFundamentalInformation.getCustName());
        ofcOrderStatus.setOrderStatus("0");
        ofcOrderStatus.setLastedOperTime(new Date());

        ofcOrderStatus.setOperator("001");
        ofcOrderStatusService.save(ofcOrderStatus);
    }

    public OfcDistributionBasicInfo upDistributionBasicInfo(OfcDistributionBasicInfo ofcDistributionBasicInfo
    ,OfcFundamentalInformation ofcFundamentalInformation){
        ofcDistributionBasicInfo.setTransCode("111111111");
        ofcDistributionBasicInfo.setDeparturePlaceCode("001");
        ofcDistributionBasicInfo.setDestinationCode("001");
        ofcDistributionBasicInfo.setTotalStandardBox(0);
        ofcDistributionBasicInfo.setOrderCode(ofcFundamentalInformation.getOrderCode());
        ofcDistributionBasicInfo.setCreationTime(ofcFundamentalInformation.getCreationTime());
        ofcDistributionBasicInfo.setCreator(ofcFundamentalInformation.getCreator());
        ofcDistributionBasicInfo.setOperator(ofcFundamentalInformation.getOperator());
        ofcDistributionBasicInfo.setOperTime(ofcFundamentalInformation.getOpertime());
        return ofcDistributionBasicInfo;
    }

    public OfcWarehouseInformation upOfcWarehouseInformation(OfcWarehouseInformation ofcWarehouseInformation
            ,OfcFundamentalInformation ofcFundamentalInformation){
        ofcWarehouseInformation.setSupportCode("001");
        ofcWarehouseInformation.setSupportName("众品");
        ofcWarehouseInformation.setOrderCode(ofcFundamentalInformation.getOrderCode());
        ofcWarehouseInformation.setCreationTime(ofcFundamentalInformation.getCreationTime());
        ofcWarehouseInformation.setCreator(ofcFundamentalInformation.getCreator());
        ofcWarehouseInformation.setOperTime(ofcFundamentalInformation.getOpertime());
        ofcWarehouseInformation.setOperator(ofcFundamentalInformation.getOperator());
        return ofcWarehouseInformation;
    }


    @RequestMapping("/orderPlaceCon")
    public String orderPlace(OfcOrderDTO ofcOrderDTO, Map<String, Object> map){
        System.out.println(ofcOrderDTO);
        //ofcFundamentalInformationService.orderPlace(ofcOrderDTO);
        return "order_place";
    }


    @RequestMapping(value="/orderPlace")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
        return new ModelAndView("order_place");
    }




}
