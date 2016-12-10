package com.xescm.ofc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.model.dto.coo.CreateOrderEntity;
import com.xescm.ofc.model.dto.coo.CreateOrderTrans;
import com.xescm.ofc.model.dto.dms.DmsTransferRecordDto;
import com.xescm.ofc.service.OfcDmsCallbackStatusService;
import com.xescm.ofc.service.OfcOrderManageOperService;
import com.xescm.ofc.utils.JsonUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hiyond on 2016/11/18.
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = XescmOfcApplication.class)
//@WebAppConfiguration
public class CreateOrderApiTest {

    @Autowired
    private OfcDmsCallbackStatusService ofcDmsCallbackStatusService;

    @Test
    public void t3333333333333333t(){
        DmsTransferRecordDto dmsTransferRecordDto = new DmsTransferRecordDto();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dmsTransferRecordDto.setCreatedTime(sdf.format(new Date()));
        dmsTransferRecordDto.setTransNo("transCode001");
        dmsTransferRecordDto.setCreator("creator001");
        dmsTransferRecordDto.setRecordTypeCode("10");
        dmsTransferRecordDto.setRemark("我是Remark001");

        ofcDmsCallbackStatusService.receiveDmsCallbackStatus(dmsTransferRecordDto);
    }
/*


    @Autowired
    private OfcOrderManageOperService ofcOrderManageOperService;

    @Test
    public void test() {
        List<OrderScreenResult> dataList = ofcOrderManageOperService.queryOrderOper(null);
        System.out.println(dataList);
    }

    public static String queryJson() {
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
                "        \"expandSaleOrg\":\"60\",\n" +
                "        \"expandProGroup\": \"60\",\n" +
                "        \"expandSaleDep\": \"60\",\n" +
                "        \"expandSaleGroup\": \"60\",\n" +
                "        \"expandSaleDepDes\": \"60\",\n" +
                "        \"expandSaleGroupDes\": \"60\",\n" +
                "        \"quantity\": \"1111\",\n" +
                "        \"weight\": \"9212.0\",\n" +
                "        \"cubage\": \"60\",\n" +
                "        \"totalStandardBox\": \"60\",\n" +
                "        \"transRequire\": \"60\",\n" +
                "        \"pickupTime\": \"60\",\n" +
                "        \"expectedArrivedTime\": null,\n" +
                "        \"consignorName\": \"鲜易网\",\n" +
                "        \"consignorContact\": \"鲜易网\",\n" +
                "        \"consignorPhone\": \"400-662-6366\",\n" +
                "        \"consignorFax\": \"60\",\n" +
                "        \"consignorEmail\": \"60\",\n" +
                "        \"consignorZip\": \"60\",\n" +
                "        \"consignorProvince\": \"北京\",\n" +
                "        \"consignorCity\": \"北京市\",\n" +
                "        \"consignorCounty\": \"海淀区\",\n" +
                "        \"consignorTown\": \"60\",\n" +
                "        \"consignorAddress\": \"泰鹏大厦\",\n" +
                "        \"consigneeName\": \"李歌\",\n" +
                "        \"consigneeContact\": \"李歌\",\n" +
                "        \"consigneePhone\": \"18637711063\",\n" +
                "        \"consigneeFax\": \"60\",\n" +
                "        \"consigneeEmail\": \"60\",\n" +
                "        \"consigneeZip\": \"60\",\n" +
                "        \"consigneeProvince\": \"河南\",\n" +
                "        \"consigneeCity\": \"郑州市\",\n" +
                "        \"consigneeCounty\": \"二七区\",\n" +
                "        \"consigneeTown\": \"60\",\n" +
                "        \"consigneeAddress\": \"泰鹏大厦\",\n" +
                "        \"warehouseCode\": \"60\",\n" +
                "        \"warehouseName\": \"60\",\n" +
                "        \"provideTransport\": \"1\",\n" +
                "        \"supportName\": \"60\",\n" +
                "        \"supportContact\": \"60\",\n" +
                "        \"supportPhone\": \"60\",\n" +
                "        \"supportFax\": \"60\",\n" +
                "        \"supportEmail\": \"60\",\n" +
                "        \"supportZip\": \"60\",\n" +
                "        \"supportProvince\": \"60\",\n" +
                "        \"supportCity\": \"60\",\n" +
                "        \"supportCounty\": \"60\",\n" +
                "        \"supportTown\": \"60\",\n" +
                "        \"supportAddress\": \"60\",\n" +
                "        \"arriveTime\": \"60\",\n" +
                "        \"plateNumber\": \"60\",\n" +
                "        \"driverName\": \"60\",\n" +
                "        \"contactNumber\": \"60\",\n" +
                "        \"serviceCharge\": \"60\",\n" +
                "        \"orderAmount\": \"1932\",\n" +
                "        \"paymentAmount\": \"1932\",\n" +
                "        \"collectLoanAmount\": \"0\",\n" +
                "        \"collectServiceCharge\": \"60\",\n" +
                "        \"collectFlag\": \"60\",\n" +
                "        \"printInvoice\": \"60\",\n" +
                "        \"buyerPaymentMethod\": \"6830\",\n" +
                "        \"insure\": \"60\",\n" +
                "        \"insureValue\": \"60\",\n" +
                "        \"createOrderGoodsInfos\": [\n" +
                "            {\n" +
                "                \"goodsCode\": \"147643709896153\",\n" +
                "                \"goodsName\": \"鱿鱼切半40克 4.35kg/箱\",\n" +
                "                \"goodsSpec\": \"4.35kg/箱\",\n" +
                "                \"unit\": \"箱\",\n" +
                "                \"quantity\": \"21\",\n" +
                "                \"unitPrice\": \"92\",\n" +
                "                \"productionBatch\": \"60\",\n" +
                "                \"productionTime\": \"60\",\n" +
                "                \"invalidTime\": \"60\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"baseId\": \"40551\"\n" +
                "    }]";
    }

    public static void main(String[] args) throws Exception {
        String data = queryJson();
        List<CreateOrderEntity> createOrderEntityList = (List<CreateOrderEntity>) JsonUtil.json2List(data, new TypeReference<List<CreateOrderEntity>>() {
        });

        String orderCode = "SO123456";
        CreateOrderEntity createOrderEntity = createOrderEntityList.get(0);
        CreateOrderTrans createOrderTrans = new CreateOrderTrans(createOrderEntity, orderCode);
        OfcFundamentalInformation ofcFundamentalInformation = createOrderTrans.getOfcFundamentalInformation();
        ofcFundamentalInformation.setStoreName(null);
        OfcDistributionBasicInfo ofcDistributionBasicInfo = createOrderTrans.getOfcDistributionBasicInfo();
        OfcFinanceInformation ofcFinanceInformation = createOrderTrans.getOfcFinanceInformation();
        OfcWarehouseInformation ofcWarehouseInformation = createOrderTrans.getOfcWarehouseInformation();
        OfcOrderStatus ofcOrderStatus = createOrderTrans.getOfcOrderStatus();
        List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList = createOrderTrans.getOfcGoodsDetailsInfoList();

        System.out.println("ofcFundamentalInformation:"+ ToStringBuilder.reflectionToString(ofcFundamentalInformation));
        System.out.println("ofcDistributionBasicInfo:"+ ToStringBuilder.reflectionToString(ofcDistributionBasicInfo));
        System.out.println("ofcFinanceInformation:"+ ToStringBuilder.reflectionToString(ofcFinanceInformation));
        System.out.println("ofcWarehouseInformation:"+ ToStringBuilder.reflectionToString(ofcWarehouseInformation));
        System.out.println("ofcOrderStatus:"+ ToStringBuilder.reflectionToString(ofcOrderStatus));
        System.out.println("ofcGoodsDetailsInfoList:"+ ToStringBuilder.reflectionToString(ofcGoodsDetailsInfoList));
    }
*/

}
