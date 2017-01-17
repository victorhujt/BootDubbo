package com.xescm.ofc.web.controller;

import com.xescm.ofc.domain.OfcPlanFedBackCondition;
import com.xescm.ofc.domain.OfcTransplanInfo;
import com.xescm.ofc.enums.DmsCallbackStatusEnum;
import com.xescm.ofc.mapper.OfcTransplanInfoMapper;
import com.xescm.ofc.model.dto.dms.DmsTransferStatusDto;
import com.xescm.ofc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hiyond on 2016/11/19.
 */

@RestController
@RequestMapping("ofcOrder")
public class TestOrder extends BaseController {

    @Autowired
    private CreateOrderService createOrderService;
    @Autowired
    private OfcDmsCallbackStatusService ofcDmsCallbackStatusService;
    @Autowired
    private OfcPlanFedBackService ofcPlanFedBackService;
    @Autowired
    private OfcTransplanInfoMapper ofcTransplanInfoMapper;



    @RequestMapping(value = "/dms")
    public void ttt(){
        DmsTransferStatusDto dmsTransferStatusDto = new DmsTransferStatusDto();
        dmsTransferStatusDto.setTransNo("12197001");
        dmsTransferStatusDto.setWaybillStatusCode(DmsCallbackStatusEnum.DMS_STATUS_SIGNED.getCode());
        dmsTransferStatusDto.setCreator("阿斯顿法国");
        dmsTransferStatusDto.setCreatedTime(new Date());
        dmsTransferStatusDto.setDesp("盐田港buoimoisdfkjp[oia");
        ofcDmsCallbackStatusService.receiveDmsCallbackStatus(dmsTransferStatusDto);
    }


    @RequestMapping(value = "TFC")
    public void dsfadsf(){
        OfcPlanFedBackCondition ofcPlanFedBackCondition = new OfcPlanFedBackCondition();
        ofcPlanFedBackCondition.setStatus("已签收");
        ofcPlanFedBackCondition.setTraceTime(new Date());
        ofcPlanFedBackCondition.setTransportNo("TP161220000125");
        ofcPlanFedBackCondition.setNotes("测试的NOtes");
        String userName = "策士大夫";
        ofcPlanFedBackService.planFedBack(ofcPlanFedBackCondition,userName);
    }


    @RequestMapping(value = "SQ")
    public  void asdf(){
        Map<String,String> mapperMap = new HashMap<>();
        mapperMap.put("ifFinished","planfinish");
        mapperMap.put("orderCode","SO161220000034");
        List<OfcTransplanInfo> ofcTransplanInfos=ofcTransplanInfoMapper.ofcTransplanInfoScreenList(mapperMap);
        List<OfcTransplanInfo> ofcTransplanInfos2=ofcTransplanInfoMapper.ofcTransplanInfoScreenList(mapperMap);
        List<OfcTransplanInfo> ofcTransplanI2nfos2=ofcTransplanInfoMapper.ofcTransplanInfoScreenList(mapperMap);
    }

//    @RequestMapping(value = "/order", method = {RequestMethod.POST, RequestMethod.GET})
//    @ResponseBody
//    public String test() throws Exception {
//        return createOrderService.createOrder(queryJson());
//    }

    public String queryJson() {
        return "[{\n" +
                "        \"custOrderCode\": \"D161115107629044\",\n" +
                "        \"orderTime\": \"2016-11-15\",\n" +
                "        \"custCode\": \"XEBEST\",\n" +
                "        \"custName\": \"XE\",\n" +
                "        \"orderType\": \"60\",\n" +
                "        \"businessType\": \"600\",\n" +
                "        \"notes\": \"订单备注\",\n" +
                "        \"storeCode\": \"20161122000001\",\n" +
                "        \"orderSource\": \"EDI\",\n" +
                "        \"expandSaleOrg\": null,\n" +
                "        \"expandProGroup\": null,\n" +
                "        \"expandSaleDep\": null,\n" +
                "        \"expandSaleGroup\": null,\n" +
                "        \"expandSaleDepDes\": null,\n" +
                "        \"expandSaleGroupDes\": null,\n" +
                "        \"quantity\": \"1111\",\n" +
                "        \"weight\": \"9212.0\",\n" +
                "        \"cubage\": null,\n" +
                "        \"totalStandardBox\": null,\n" +
                "        \"transRequire\": null,\n" +
                "        \"pickupTime\": null,\n" +
                "        \"expectedArrivedTime\": null,\n" +
                "        \"consignorName\": \"鲜易网\",\n" +
                "        \"consignorContact\": \"鲜易网\",\n" +
                "        \"consignorPhone\": \"400-662-6366\",\n" +
                "        \"consignorFax\": null,\n" +
                "        \"consignorEmail\": null,\n" +
                "        \"consignorZip\": null,\n" +
                "        \"consignorProvince\": \"北京\",\n" +
                "        \"consignorCity\": \"北京市\",\n" +
                "        \"consignorCounty\": \"海淀区\",\n" +
                "        \"consignorTown\": null,\n" +
                "        \"consignorAddress\": \"泰鹏大厦\",\n" +
                "        \"consigneeName\": \"李歌\",\n" +
                "        \"consigneeContact\": \"李歌\",\n" +
                "        \"consigneePhone\": \"18637711063\",\n" +
                "        \"consigneeFax\": null,\n" +
                "        \"consigneeEmail\": null,\n" +
                "        \"consigneeZip\": null,\n" +
                "        \"consigneeProvince\": \"河南\",\n" +
                "        \"consigneeCity\": \"郑州市\",\n" +
                "        \"consigneeCounty\": \"二七区\",\n" +
                "        \"consigneeTown\": null,\n" +
                "        \"consigneeAddress\": \"泰鹏大厦\",\n" +
                "        \"warehouseCode\": null,\n" +
                "        \"warehouseName\": null,\n" +
                "        \"provideTransport\": \"1\",\n" +
                "        \"supportName\": null,\n" +
                "        \"supportContact\": null,\n" +
                "        \"supportPhone\": null,\n" +
                "        \"supportFax\": null,\n" +
                "        \"supportEmail\": null,\n" +
                "        \"supportZip\": null,\n" +
                "        \"supportProvince\": null,\n" +
                "        \"supportCity\": null,\n" +
                "        \"supportCounty\": null,\n" +
                "        \"supportTown\": null,\n" +
                "        \"supportAddress\": null,\n" +
                "        \"arriveTime\": null,\n" +
                "        \"plateNumber\": null,\n" +
                "        \"driverName\": null,\n" +
                "        \"contactNumber\": null,\n" +
                "        \"serviceCharge\": null,\n" +
                "        \"orderAmount\": \"1932\",\n" +
                "        \"paymentAmount\": \"1932\",\n" +
                "        \"collectLoanAmount\": \"0\",\n" +
                "        \"collectServiceCharge\": null,\n" +
                "        \"collectFlag\": null,\n" +
                "        \"printInvoice\": null,\n" +
                "        \"buyerPaymentMethod\": \"6830\",\n" +
                "        \"insure\": null,\n" +
                "        \"insureValue\": null,\n" +
                "        \"createOrderGoodsInfos\": [\n" +
                "            {\n" +
                "                \"goodsCode\": \"147643709896154\",\n" +
                "                \"goodsName\": \"鱿鱼切半40克 4.35kg/箱\",\n" +
                "                \"goodsSpec\": \"4.35kg/箱\",\n" +
                "                \"unit\": \"箱\",\n" +
                "                \"quantity\": \"21\",\n" +
                "                \"unitPrice\": \"92\",\n" +
                "                \"productionBatch\": null,\n" +
                "                \"productionTime\": null,\n" +
                "                \"invalidTime\": null\n" +
                "            }\n" +
                "            ,{\n" +
                "                \"goodsCode\": \"147643709896153\",\n" +
                "                \"goodsName\": \"鱿鱼切半80克 14.35kg/箱\",\n" +
                "                \"goodsSpec\": \"14.35kg/箱\",\n" +
                "                \"unit\": \"箱\",\n" +
                "                \"quantity\": \"21\",\n" +
                "                \"unitPrice\": \"92\",\n" +
                "                \"productionBatch\": null,\n" +
                "                \"productionTime\": null,\n" +
                "                \"invalidTime\": null\n" +
                "            }\n" +
                "        ],\n" +
                "        \"baseId\": \"40551\"\n" +
                "    }]";
    }

}
