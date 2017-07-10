package com.xescm.ofc.web.restcontroller;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.xescm.ofc.constant.OrderConstant.WAREHOUSE_DIST_ORDER;

/**
 * Created by hujintao on 2017/7/10.
 */
@RestController
@RequestMapping(value = "www/ofc",produces = {"application/json;charset=UTF-8"})
public class TempPushMqController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private OfcWarehouseInformationService ofcWarehouseInformationService;
    @Resource
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Resource
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Resource
    private  OfcOrderManageService ofcOrderManageService;
    @Resource
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;
    @Resource
    private OfcFinanceInformationService ofcFinanceInformationService;

    @ResponseBody
    @RequestMapping(value = "/pushToAc", method = RequestMethod.GET)
    public void  pushToAc (){
        OfcFundamentalInformation ofcFundamentalInformation = new OfcFundamentalInformation();
        //	BN1707070001
        ofcFundamentalInformation.setOrderBatchNumber("BN1707100027");//BN1707100027 线上
        ofcFundamentalInformation.setOrderType(WAREHOUSE_DIST_ORDER);
        logger.info("查询的参数为,批次号:{},订单类型:{}",ofcFundamentalInformation.getOrderBatchNumber(),ofcFundamentalInformation.getOrderType());
        List<OfcFundamentalInformation> ofcFundamentalInformations = ofcFundamentalInformationService.select(ofcFundamentalInformation);
        if(!CollectionUtils.isEmpty(ofcFundamentalInformations)){
            for (int i = 0; i <ofcFundamentalInformations.size();i++){
                OfcFundamentalInformation temp = ofcFundamentalInformations.get(i);
                List<OfcGoodsDetailsInfo> goodsDetailsList = ofcGoodsDetailsInfoService.queryByOrderCode(temp.getOrderCode());
                OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoService.distributionBasicInfoSelect(temp.getOrderCode());
                OfcFinanceInformation ofcFinanceInformation = ofcFinanceInformationService.queryByOrderCode(temp.getOrderCode());
                if (ofcFinanceInformation == null) {
                    ofcFinanceInformation = new OfcFinanceInformation();
                }
                OfcWarehouseInformation ofcWarehouseInformation = ofcWarehouseInformationService.queryByOrderCode(temp.getOrderCode());
                ofcOrderManageService.pushOrderToAc(temp, ofcFinanceInformation, ofcDistributionBasicInfo, goodsDetailsList, ofcWarehouseInformation);
            }
        }

    }

}
