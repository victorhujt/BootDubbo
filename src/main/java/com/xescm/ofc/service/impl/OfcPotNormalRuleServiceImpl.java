package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.service.OfcEnumerationService;
import com.xescm.ofc.service.OfcExceptOrderService;
import com.xescm.ofc.service.OfcPotNormalRuleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static com.xescm.ofc.constant.OrderConstConstant.*;
import static com.xescm.ofc.constant.OrderConstant.*;
import static com.xescm.ofc.enums.OrderPotEnum.*;

/**
 *
 * Created by lyh on 2017/11/14.
 */
@Service
public class OfcPotNormalRuleServiceImpl extends BaseService<OfcPotNormalRule> implements OfcPotNormalRuleService{

    @Resource
    private OfcEnumerationService ofcEnumerationService;
    @Resource
    private OfcExceptOrderService ofcExceptOrderService;





    @Override
    public void insertOrderNormalRule(OfcFundamentalInformation ofcFundamentalInformation
            , OfcDistributionBasicInfo ofcDistributionBasicInfo, List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos, OfcWarehouseInformation ofcWarehouseInformation) {
        logger.info("insertOrderNormalRule == > ofcFundamentalInformation == > {}", ofcFundamentalInformation);
        logger.info("insertOrderNormalRule == > ofcDistributionBasicInfo == > {}", ofcDistributionBasicInfo);
        logger.info("insertOrderNormalRule == > ofcGoodsDetailsInfos == > {}", ofcGoodsDetailsInfos);
        logger.info("insertOrderNormalRule == > ofcWarehouseInformation == > {}", ofcWarehouseInformation);
        if (!this.checkParam(ofcFundamentalInformation, ofcWarehouseInformation)) {
            return;
        }
        OfcExceptOrder ofcExceptOrder = new OfcExceptOrder();
        BeanUtils.copyProperties(ofcFundamentalInformation, ofcExceptOrder);
        ofcExceptOrder.setProvideTransport(ofcWarehouseInformation == null ? "0" : ofcWarehouseInformation.getProvideTransport() == null ? "0" : String.valueOf(ofcWarehouseInformation.getProvideTransport()));
        for (OfcGoodsDetailsInfo ofcGoodsDetailsInfo : ofcGoodsDetailsInfos) {
            String goodsType = ofcGoodsDetailsInfo.getGoodsType();
            String goodsCategory = ofcGoodsDetailsInfo.getGoodsCategory();
            if (StringUtils.equals(goodsType, "畜禽类") && StringUtils.equals(goodsCategory, "冷鲜猪肉")) {
                ofcExceptOrder.setCoolFreshPork(STR_YES);
                break;
            }
        }
        // 异常原因暂时只有超时
        this.dealDelay(ofcExceptOrder);
    }

    private boolean checkParam(OfcFundamentalInformation ofcFundamentalInformation, OfcWarehouseInformation ofcWarehouseInformation) {
        boolean checkPass = true;
        if (WITH_THE_GROUND_DISTRIBUTION.equals(ofcFundamentalInformation.getBusinessType())) {
            logger.error("暂不支持落地配业务类型");
            checkPass = false;
        }
        if (StringUtils.equals(ofcFundamentalInformation.getOrderType(), WAREHOUSE_DIST_ORDER)
                && !STR_YES.equals(ofcWarehouseInformation.getProvideTransport().toString())) {
            logger.error("仓储订单暂时只支持带运输的");
            checkPass = false;
        }

        return checkPass;
    }

    private void dealDelay(OfcExceptOrder ofcExceptOrder) {
        String orderType = ofcExceptOrder.getOrderType();
        String businessType = ofcExceptOrder.getBusinessType();
        String coolFreshPork = ofcExceptOrder.getCoolFreshPork();
        OfcEnumeration ofcEnumeration = new OfcEnumeration();
        ofcEnumeration.setEnumType("SpecialCustZhongpinEnum");
        List<OfcEnumeration> enumsOfZp = ofcEnumerationService.queryOfcEnumerationList(ofcEnumeration);
        ofcEnumeration.setEnumType("OfcTimeEnum");
        Map<String, OfcEnumeration> enumsOfTime = ofcExceptOrderService.loadOfcTimeEnumMap(ofcEnumeration);
        boolean isZhongPin = ofcExceptOrderService.isZhongPin(ofcExceptOrder, enumsOfZp);
        boolean goodsTypeHasChilledPork = StringUtils.equals(coolFreshPork, STR_YES);
        boolean isNormalCust = !isZhongPin;
        int calendarHour = Integer.valueOf(enumsOfTime.get("CALENDAR_HOUR").getEnumValue());
        // 运输订单
        if (StringUtils.equals(orderType, TRANSPORT_ORDER)) {
            this.dealTransOrder(ofcExceptOrder, enumsOfTime, businessType, isZhongPin, goodsTypeHasChilledPork, isNormalCust, calendarHour);
            // 仓储订单
        } else if (StringUtils.equals(orderType, WAREHOUSE_DIST_ORDER)) {
            this.dealStorageOrder(ofcExceptOrder, enumsOfZp, enumsOfTime);
        }

    }

    private void dealStorageOrder(OfcExceptOrder ofcExceptOrder, List<OfcEnumeration> enumsOfZp, Map<String, OfcEnumeration> ofcTimeEnum) {
        String businessType = ofcExceptOrder.getBusinessType();
        boolean isZhongPin = ofcExceptOrderService.isZhongPin(ofcExceptOrder, enumsOfZp);
        boolean isNormalCust = !isZhongPin;
        boolean goodsTypeHasChilledPork = StringUtils.equals(ofcExceptOrder.getCoolFreshPork(), STR_YES);
        boolean provideTransport = StringUtils.equals(ofcExceptOrder.getProvideTransport(), STR_YES);
        int calendarHour = Integer.valueOf(ofcTimeEnum.get("CALENDAR_HOUR").getEnumValue());
        OfcPotNormalRule ofcPotNormalRule = new OfcPotNormalRule();
        ofcPotNormalRule.setOrderCode(ofcExceptOrder.getOrderCode());
        String potType = "";
        Date creationTime = ofcExceptOrder.getCreationTime();
        Date potEndTime = new Date();
        BeanUtils.copyProperties(creationTime, potEndTime);
        ofcPotNormalRule.setPotStartTime(creationTime);
        Integer effectiveTimeOfStock = 0;
        Integer effectiveTimeOfDelivery = 0;
        // 入库
        if (StringUtils.equals(businessType.substring(0,2), STOCK_IN_ORDER)) {
            // 入库 提供运输 众品 冷鲜肉 入库时效6小时 城配调度时效12小时
            if (provideTransport && isZhongPin && goodsTypeHasChilledPork) {
                effectiveTimeOfStock = Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue());
                effectiveTimeOfDelivery = Integer.valueOf(ofcTimeEnum.get("HOUR_12").getEnumValue());
                potType = STORAGE_IN.getPotCode();
            }
            // 入库 提供运输 众品 非冷鲜肉 入库时效6小时 城配调度时效48小时
            if (provideTransport && isZhongPin && !goodsTypeHasChilledPork) {
                effectiveTimeOfStock = Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue());
                effectiveTimeOfDelivery = Integer.valueOf(ofcTimeEnum.get("HOUR_48").getEnumValue());
                potType = STORAGE_IN.getPotCode();
            }
            // 入库 提供运输 普通客户 入库时效6小时 城配调度时效12小时
            if (provideTransport && isNormalCust) {
                effectiveTimeOfStock = Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue());
                effectiveTimeOfDelivery = Integer.valueOf(ofcTimeEnum.get("HOUR_12").getEnumValue());
                potType = STORAGE_IN.getPotCode();
            }
            // 出库
        } else if (StringUtils.equals(businessType.substring(0,2), STOCK_OUT_ORDER)) {
            // 出库 提供运输 众品 冷鲜肉 出库时效6小时 城配调度时效12小时
            if (provideTransport && isZhongPin && goodsTypeHasChilledPork) {
                effectiveTimeOfStock = Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue());
                effectiveTimeOfDelivery = Integer.valueOf(ofcTimeEnum.get("HOUR_12").getEnumValue());
                potType = STORAGE_OUT.getPotCode();
            }
            // 出库 提供运输 众品 非冷鲜肉 出库时效6小时 城配调度时效48小时
            if (provideTransport && isZhongPin && !goodsTypeHasChilledPork) {
                effectiveTimeOfStock = Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue());
                effectiveTimeOfDelivery = Integer.valueOf(ofcTimeEnum.get("HOUR_48").getEnumValue());
                potType = STORAGE_OUT.getPotCode();
            }
            // 出库 提供运输 普通客户 出库时效6小时 城配调度时效12小时
            if (provideTransport && isNormalCust)  {
                effectiveTimeOfStock = Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue());
                effectiveTimeOfDelivery = Integer.valueOf(ofcTimeEnum.get("HOUR_12").getEnumValue());
                potType = STORAGE_OUT.getPotCode();
            }
        }
        Calendar calendar = DateUtils.toCalendar(potEndTime);
        calendar.add(calendarHour, effectiveTimeOfStock);
        ofcPotNormalRule.setPotEndTime(calendar.getTime());
        ofcPotNormalRule.setPotType(potType);
        super.save(ofcPotNormalRule);
        calendar = DateUtils.toCalendar(potEndTime);
        calendar.add(calendarHour, effectiveTimeOfDelivery);
        ofcPotNormalRule.setPotEndTime(calendar.getTime());
        ofcPotNormalRule.setPotType(DELIVERY.getPotCode());
        super.save(ofcPotNormalRule);
    }

    private void dealTransOrder(OfcExceptOrder ofcExceptOrder, Map<String, OfcEnumeration> ofcTimeEnum, String businessType, boolean isZhongPin, boolean goodsTypeHasChilledPork, boolean isNormalCust, int calendarHour) {
        // 处理调度
        this.dealPotOfDelivery(ofcExceptOrder, businessType, isZhongPin, ofcTimeEnum, goodsTypeHasChilledPork, isNormalCust, calendarHour);
    }

    private void dealPotOfDelivery(OfcExceptOrder ofcExceptOrder, String businessType, boolean isZhongPin, Map<String, OfcEnumeration> ofcTimeEnum, boolean goodsTypeHasChilledPork, boolean isNormalCust, int calendarHour) {
        OfcPotNormalRule ofcPotNormalRule = new OfcPotNormalRule();
        ofcPotNormalRule.setOrderCode(ofcExceptOrder.getOrderCode());
        ofcPotNormalRule.setPotType(DELIVERY.getPotCode());
        Date creationTime = ofcExceptOrder.getCreationTime();
        ofcPotNormalRule.setPotStartTime(creationTime);
        Integer effectiveTime = 0;
        //     城配
        if (StringUtils.equals(businessType, WITH_THE_CITY)) {
            // 众品 冷鲜肉 城配调度时效12小时
            if (isZhongPin && goodsTypeHasChilledPork) {
                effectiveTime = Integer.valueOf(ofcTimeEnum.get("HOUR_12").getEnumValue());
            }
            // 众品 非冷鲜肉 城配调度时效48小时
            if (isZhongPin && !goodsTypeHasChilledPork) {
                effectiveTime = Integer.valueOf(ofcTimeEnum.get("HOUR_48").getEnumValue());
            }
            // 普通客户 城配调度时效12小时
            if (isNormalCust) {
                effectiveTime = Integer.valueOf(ofcTimeEnum.get("HOUR_12").getEnumValue());
            }
            //     干线
        } else if (StringUtils.equals(businessType, WITH_THE_TRUNK)) {
            // 众品 冷鲜肉 干线调度时效12小时
            if (isZhongPin && goodsTypeHasChilledPork) {
                effectiveTime = Integer.valueOf(ofcTimeEnum.get("HOUR_12").getEnumValue());
            }
            // 众品 非冷鲜肉 干线调度时效48小时
            if (isZhongPin && !goodsTypeHasChilledPork) {
                effectiveTime = Integer.valueOf(ofcTimeEnum.get("HOUR_48").getEnumValue());
            }
            // 普通客户 干线调度时效24小时
            if (isNormalCust) {
                effectiveTime = Integer.valueOf(ofcTimeEnum.get("HOUR_24").getEnumValue());
            }
            //     卡班
        } else if (StringUtils.equals(businessType, WITH_THE_KABAN)) {
            // 卡班调度时效96小时
            effectiveTime = Integer.valueOf(ofcTimeEnum.get("HOUR_96").getEnumValue());
        }
        Calendar calendar = DateUtils.toCalendar(creationTime);
        calendar.add(calendarHour, effectiveTime);
        ofcPotNormalRule.setPotEndTime(calendar.getTime());
        super.save(ofcPotNormalRule);
    }

}
