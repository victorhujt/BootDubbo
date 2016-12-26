package com.xescm.ofc.web.controller;

import com.xescm.ac.provider.AcOrderEdasService;
import com.xescm.ofc.domain.OfcPlanFedBackCondition;
import com.xescm.ofc.domain.OfcTransplanInfo;
import com.xescm.ofc.enums.DmsCallbackStatusEnum;
import com.xescm.ofc.mapper.OfcTransplanInfoMapper;
import com.xescm.ofc.model.dto.dms.DmsTransferStatusDto;
import com.xescm.ofc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
    private OfcOrderManageOperService ofcOrderManageOperService;

    @Autowired
    private OfcOrderDtoService ofcOrderDtoService;
    @Autowired
    private OfcOrderStatusService ofcOrderStatusService;

    @Autowired
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Autowired
    private OfcFinanceInformationService ofcFinanceInformationService;
    @Autowired
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;
    @Autowired
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
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

   /*
    @RequestMapping(value = "/order", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String test() throws Exception {









////        OrderOperForm orderOperForm = new OrderOperForm();
////        System.out.println(ofcOrderManageOperService.queryOrderOper(orderOperForm));
//        OfcOrderDTO ofcOrderDTO = ofcOrderDtoService.orderDtoSelect("SO161124000237", "orderCode");
//        List<OfcOrderStatus> ofcOrderStatuses = ofcOrderStatusService.orderStatusScreen("SO161124000237", "orderCode");
//        System.out.println(ofcOrderDTO);
//        System.out.println(ofcOrderStatuses);
        return createOrderService.createOrder(queryJson());
//        createOrderService.createOrder(queryJ());
//        return null;
    }

    public String queryJson() {
        //custCode": "10d4e45fd1844882a14bd4d8a063694c\
        return "[{\n" +
                "        \"custOrderCode\": \"D161115107629044\",\n" +
                "        \"orderTime\": \"2016-11-15\",\n" +
                "        \"custCode\": \"110603444ce84af7ab0c62e2936be426\",\n" +
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
                "                \"goodsCode\": \"147643709896153\",\n" +
                "                \"goodsName\": \"鱿鱼切半40克 4.35kg/箱\",\n" +
                "                \"goodsSpec\": \"4.35kg/箱\",\n" +
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

    public String queryJ() {
        return "{\n" +
                "    \"custOrderCode\": \"D161111115522382\",\n" +
                "    \"orderTime\": \"2016-11-11.18.10. 37. 373000000\",\n" +
                "    \"\n" +
                "custCode\": \"10d4e45fd1844882a14bd4d8a063694c\",\n" +
                "    \"custName\": \"XE\",\n" +
                "    \"orderType\": \"60\",\n" +
                "    \"businessType\": \"600\",\n" +
                "    \"notes\": \"订单备注\",\n" +
                "    \"storeCode\": null,\n" +
                "    \"orderSource\": \"EDI\",\n" +
                "    \"expandSaleOrg\": null,\n" +
                "    \"expandProGroup\n" +
                "\": null,\n" +
                "    \"expandSaleDep\": null,\n" +
                "    \"expandSaleGroup\": null,\n" +
                "    \"expandSaleDepDes\": null,\n" +
                "    \"expandSaleGroupDes\": null,\n" +
                "    \"quantity\": null,\n" +
                "    \"weight\": \"565.86\",\n" +
                "    \"cubage\": null,\n" +
                "    \"totalStandardBox\": null,\n" +
                "    \"transRequire\": null,\n" +
                "    \"pickupTime\": null,\n" +
                "    \"expectedArrivedTime\": null,\n" +
                "    \"consignorName\": \"鲜易网\",\n" +
                "    \"consignorContact\": \"鲜易网\",\n" +
                "    \"consignorPhone\": \"400-662-6366\",\n" +
                "    \"consignorFax\": null,\n" +
                "    \"consignorEmail\": null,\n" +
                "    \"consignorZip\": null,\n" +
                "    \"consignorProvince\": \"河南\",\n" +
                "    \"consignorCity\": \"郑州\",\n" +
                "    \"consignorCounty\": \"郑州新区\",\n" +
                "    \"consignorTown\": null,\n" +
                "    \"consignorAddress\": \"东风南路七里河路交叉口绿地之窗云峰座\",\n" +
                "    \"consigneeName\": \"刘山虎\",\n" +
                "    \"consigneeContact\": \"刘山虎\",\n" +
                "    \"consigneePhone\": \"15538667200\",\n" +
                "    \"consigneeFax\": null,\n" +
                "    \"consigneeEmail\": null,\n" +
                "    \"consigneeZip\": null,\n" +
                "    \"consigneeProvince\": \"河南\",\n" +
                "    \"consigneeCity\": \"周口市\",\n" +
                "    \"consigneeCounty\": \"川汇区\",\n" +
                "    \"consigneeTown\": null,\n" +
                "    \"consigneeAddress\": \"黄淮市场\",\n" +
                "    \"warehouseCode\": null,\n" +
                "    \"warehouseName\": null,\n" +
                "    \"provideTransport\": null,\n" +
                "    \"supportName\": null,\n" +
                "    \"supportContact\": null,\n" +
                "    \"supportPhone\": null,\n" +
                "    \"supportFax\": null,\n" +
                "    \"supportEmail\": null,\n" +
                "    \"supportZip\": null,\n" +
                "    \"supportProvince\": null,\n" +
                "    \"supportCity\": null,\n" +
                "    \"supportCounty\": null,\n" +
                "    \"supportTown\": null,\n" +
                "    \"supportAddress\": null,\n" +
                "    \"arriveTime\": null,\n" +
                "    \"plateNumber\": null,\n" +
                "    \"driverName\": null,\n" +
                "    \"contactNumber\": null,\n" +
                "    \"serviceCharge\": null,\n" +
                "    \"orderAmount\": \"9585.9\",\n" +
                "    \"paymentAmount\": \"9585.9\",\n" +
                "    \"collectLoanAmount\": \"0\",\n" +
                "    \"collectServiceCharge\": null,\n" +
                "    \"collectFlag\": null,\n" +
                "    \"printInvoice\": null,\n" +
                "    \"buyerPaymentMethod\": \"6830\",\n" +
                "    \"insure\": null,\n" +
                "    \"insureValue\": null,\n" +
                "    \"createOrderGoodsInfos\": [\n" +
                "        {\n" +
                "            \"goodsCode\": null,\n" +
                "            \"goodsName\": \"加拿大147冷冻猪后腿\",\n" +
                "            \"goodsSpec\": \"散装\",\n" +
                "            \"unit\": \"千克\",\n" +
                "            \"quantity\": \"417\n" +
                "\",\n" +
                "            \"unitPrice\": \"16.3\",\n" +
                "            \"productionBatch\": null,\n" +
                "            \"productionTime\": null,\n" +
                "            \"invalidTime\": null\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }

    @Autowired
    private FeignAddressCodeClient feignAddressCodeClient;

    @RequestMapping(value = "address")
    public String testAddress() {
        String provi = "北京";
        String city = "北京";
        String coun = "海淀区";
        AddressDto addressDto = new AddressDto();
        addressDto.setProvinceName(provi);
        addressDto.setCityName(city);
        addressDto.setDistrictName(coun);
        String result = feignAddressCodeClient.findCodeByName(addressDto);
        logger.info(result);
        return result;
    }


    //测试推送结算中心
    @RequestMapping(value = "test/pushAc", method = {RequestMethod.POST, RequestMethod.GET})
    public void testPushAcOrder() {
        final String orderCode = "SO161202000014";
        OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
        OfcFinanceInformation ofcFinanceInformation = ofcFinanceInformationService.queryByOrderCode(orderCode);
        OfcDistributionBasicInfo ofcDistributionBasicInfo = new OfcDistributionBasicInfo();
        ofcDistributionBasicInfo.setOrderCode(orderCode);
        ofcDistributionBasicInfo = ofcDistributionBasicInfoService.selectOne(ofcDistributionBasicInfo);
        OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
        ofcGoodsDetailsInfo.setOrderCode(orderCode);
        List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = ofcGoodsDetailsInfoService.select(ofcGoodsDetailsInfo);
        pushOrderApiClient.pullOfcOrder(ofcFundamentalInformation, ofcFinanceInformation, ofcDistributionBasicInfo, ofcGoodsDetailsInfos);
    }*/

}
