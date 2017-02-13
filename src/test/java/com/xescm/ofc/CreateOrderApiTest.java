//package com.xescm.ofc;
//
//import com.xescm.ofc.domain.OfcPlanFedBackCondition;
//import com.xescm.ofc.edas.model.dto.dpc.req.TranPlanOfcReqDto;
//import com.xescm.ofc.edas.service.OfcMobileOrderEdasService;
//import com.xescm.ofc.edas.service.OfcTranPlanDpcEdasServie;
//import com.xescm.ofc.service.CreateOrderService;
//import com.xescm.ofc.service.OfcPlanFedBackService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import java.util.Date;
//
///**
// * Created by hiyond on 2016/11/18.
// */
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = XescmOfcApplication.class)
//@WebAppConfiguration
//public class CreateOrderApiTest {
//
//    {
//        HsfXml.loadConsumer();
//    }
//
//    @Autowired
//    private OfcPlanFedBackService ofcPlanFedBackService;
//
//    @Autowired
//    private OfcMobileOrderEdasService ofcMobileOrderEdasService;
//
//
//    @Autowired
//    private CreateOrderService createOrderService;
//
//    @Autowired
//    private OfcTranPlanDpcEdasServie ofcTranPlanDpcEdasServie;
//
//
//    @Test
//    public void sadd(){
//        try {
//            createOrderService.createOrder(queryJson());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public String queryJson() {
//        //custCode": "10d4e45fd1844882a14bd4d8a063694c\
//        return "[{\n" +
//                "        \"custOrderCode\": \"D161115107629061\",\n" +
//                "        \"orderTime\": \"2016-11-15\",\n" +
//                "        \"custCode\": \"100002\",\n" +
//                "        \"custName\": \"XE\",\n" +
//                "        \"orderType\": \"60\",\n" +
//                "        \"businessType\": \"600\",\n" +
//                "        \"notes\": \"订单备注\",\n" +
//                "        \"storeCode\": \"20161122000001\",\n" +
//                "        \"orderSource\": \"EDI\",\n" +
//                "        \"expandSaleOrg\": null,\n" +
//                "        \"expandProGroup\": null,\n" +
//                "        \"expandSaleDep\": null,\n" +
//                "        \"expandSaleGroup\": null,\n" +
//                "        \"expandSaleDepDes\": null,\n" +
//                "        \"expandSaleGroupDes\": null,\n" +
//                "        \"quantity\": \"1111\",\n" +
//                "        \"weight\": \"9212.0\",\n" +
//                "        \"cubage\": null,\n" +
//                "        \"totalStandardBox\": null,\n" +
//                "        \"transRequire\": null,\n" +
//                "        \"pickupTime\": null,\n" +
//                "        \"expectedArrivedTime\": null,\n" +
//                "        \"consignorName\": \"鲜易网\",\n" +
//                "        \"consignorContact\": \"鲜易网\",\n" +
//                "        \"consignorPhone\": \"400-662-6366\",\n" +
//                "        \"consignorFax\": null,\n" +
//                "        \"consignorEmail\": null,\n" +
//                "        \"consignorZip\": null,\n" +
//                "        \"consignorProvince\": \"北京\",\n" +
//                "        \"consignorProvinceCode\": \"110000\",\n" +
//                "        \"consignorCity\": \"北京市\",\n" +
//                "        \"consignorCityCode\": \"110100\",\n" +
//                "        \"consignorCounty\": \"海淀区\",\n" +
//                "        \"consignorCountyCode\": \"110108\",\n" +
//                "        \"consignorTown\": null,\n" +
//                "        \"consignorAddress\": \"泰鹏大厦\",\n" +
//                "        \"consigneeName\": \"李歌\",\n" +
//                "        \"consigneeContact\": \"李歌\",\n" +
//                "        \"consigneePhone\": \"18637711063\",\n" +
//                "        \"consigneeFax\": null,\n" +
//                "        \"consigneeEmail\": null,\n" +
//                "        \"consigneeZip\": null,\n" +
//                "        \"consigneeProvince\": \"北京\",\n" +
//                "        \"consigneeCity\": \"北京市\",\n" +
//                "        \"consigneeCounty\": \"昌平区\",\n" +
//                "        \"consigneeTown\": null,\n" +
//                "        \"consigneeAddress\": \"泰鹏大厦\",\n" +
//                "        \"warehouseCode\": null,\n" +
//                "        \"warehouseName\": null,\n" +
//                "        \"provideTransport\": \"1\",\n" +
//                "        \"supportName\": null,\n" +
//                "        \"supportContact\": null,\n" +
//                "        \"supportPhone\": null,\n" +
//                "        \"supportFax\": null,\n" +
//                "        \"supportEmail\": null,\n" +
//                "        \"supportZip\": null,\n" +
//                "        \"supportProvince\": null,\n" +
//                "        \"supportCity\": null,\n" +
//                "        \"supportCounty\": null,\n" +
//                "        \"supportTown\": null,\n" +
//                "        \"supportAddress\": null,\n" +
//                "        \"arriveTime\": null,\n" +
//                "        \"plateNumber\": null,\n" +
//                "        \"driverName\": null,\n" +
//                "        \"contactNumber\": null,\n" +
//                "        \"serviceCharge\": null,\n" +
//                "        \"orderAmount\": \"1932\",\n" +
//                "        \"paymentAmount\": \"1932\",\n" +
//                "        \"collectLoanAmount\": \"0\",\n" +
//                "        \"collectServiceCharge\": null,\n" +
//                "        \"collectFlag\": null,\n" +
//                "        \"printInvoice\": null,\n" +
//                "        \"buyerPaymentMethod\": \"6830\",\n" +
//                "        \"insure\": null,\n" +
//                "        \"insureValue\": null,\n" +
//                "        \"createOrderGoodsInfos\": [\n" +
//                "            {\n" +
//                "                \"goodsCode\": \"147643709896153\",\n" +
//                "                \"goodsName\": \"鱿鱼切半40克 4.35kg/箱\",\n" +
//                "                \"goodsSpec\": \"4.35kg/箱\",\n" +
//                "                \"unit\": \"箱\",\n" +
//                "                \"quantity\": \"21\",\n" +
//                "                \"unitPrice\": \"92\",\n" +
//                "                \"productionBatch\": null,\n" +
//                "                \"productionTime\": null,\n" +
//                "                \"invalidTime\": null\n" +
//                "            }\n" +
//                "        ],\n" +
//                "        \"baseId\": \"40551\"\n" +
//                "    }]";
//    }
//
//    @Test
//    public void sdf(){
//        OfcPlanFedBackCondition ofcPlanFedBackCondition = new OfcPlanFedBackCondition();
//        ofcPlanFedBackCondition.setTransportNo("TP161229000032");
//        ofcPlanFedBackCondition.setStatus("已回单");
//        ofcPlanFedBackCondition.setTraceTime(new Date());
//        ofcPlanFedBackService.planFedBack(ofcPlanFedBackCondition,"123");
//    }
//
//    @Test
//    public void checkTranCode(){
//        TranPlanOfcReqDto TranPlanOfcReqDto=new TranPlanOfcReqDto();
//        ofcTranPlanDpcEdasServie.tranPlanSel(TranPlanOfcReqDto);
//    }
//}
