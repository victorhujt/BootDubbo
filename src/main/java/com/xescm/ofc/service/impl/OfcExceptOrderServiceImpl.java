package com.xescm.ofc.service.impl;

import com.xescm.core.utils.JacksonUtil;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.model.dto.ofc.OfcOrderPotDTO;
import com.xescm.ofc.enums.OrderPotEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcExceptOrderMapper;
import com.xescm.ofc.mapper.OfcFundamentalInformationMapper;
import com.xescm.ofc.model.dto.ofc.OfcExceptOrderDTO;
import com.xescm.ofc.model.dto.ofc.OfcOrderInfoDTO;
import com.xescm.ofc.service.OfcEnumerationService;
import com.xescm.ofc.service.OfcExceptOrderService;
import com.xescm.ofc.service.OfcGoodsDetailsInfoService;
import com.xescm.ofc.service.OfcOrderManageOperService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.xescm.ofc.constant.OrderConstConstant.*;
import static com.xescm.ofc.constant.OrderConstant.*;
import static com.xescm.ofc.enums.OrderExcpetDealStatusEnum.*;
import static com.xescm.ofc.enums.OrderPotEnum.*;

@Service
public class OfcExceptOrderServiceImpl extends BaseService<OfcExceptOrder> implements OfcExceptOrderService {

    @Resource
    private OfcExceptOrderMapper ofcExceptOrderMapper;
    @Resource
    private OfcFundamentalInformationMapper ofcFundamentalInformationMapper;
    @Resource
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private OfcEnumerationService ofcEnumerationService;
    @Resource
    private OfcOrderManageOperService ofcOrderManageOperService;

    private Set<OfcExceptOrder> normalOrderToRemove = new HashSet<>();

    @Override
    public void dealExceptOrder(OfcExceptOrderDTO ofcExceptOrderDTO) throws Exception {
        logger.debug("处理异常订单==> {}", ofcExceptOrderDTO);
        if (null == ofcExceptOrderDTO || StringUtils.isEmpty(ofcExceptOrderDTO.getExceptPot())
                || !OrderPotEnum.getCodeList().contains(ofcExceptOrderDTO.getExceptPot())) throw new BusinessException("处理异常订单失败");
        List<String> orderCodes = this.loadUndealOrders(ofcExceptOrderDTO);
        if (CollectionUtils.isEmpty(orderCodes)) {
            logger.debug("暂无待处理订单...");
            return;
        }
        OfcEnumeration ofcEnumeration = new OfcEnumeration();
        ofcEnumeration.setEnumType("SpecialCustZhongpinEnum");
        List<OfcEnumeration> ofcEnumerations = ofcEnumerationService.queryOfcEnumerationList(ofcEnumeration);
        ofcEnumeration.setEnumType("OfcTimeEnum");
        Map<String, OfcEnumeration> ofcTimeEnumMap = this.loadOfcTimeEnumMap(ofcEnumeration);
        for (String orderCode : orderCodes) {
            List<OfcExceptOrder> ofcExceptOrders = ofcExceptOrderMapper.selectByOrderCode(orderCode);
            ofcExceptOrderDTO.setOrderCode(orderCode);
            if (CollectionUtils.isEmpty(ofcExceptOrders)) {
                logger.debug("该订单号{}下无状态", orderCode);
                ofcExceptOrders = this.dealOrderHaventReceive(ofcExceptOrderDTO);
            }
            if (null == ofcExceptOrders || !this.potTypeEqualOrderType(ofcExceptOrderDTO, ofcExceptOrders.get(0))) {
                logger.debug("订单格式与当前处理格式不符, 不予处理....");
                continue;
            }
            // 运输订单
            if (StringUtils.equals(ofcExceptOrders.get(0).getOrderType(), TRANSPORT_ORDER)) {
                this.dealTransOrder(ofcExceptOrders, ofcEnumerations, ofcTimeEnumMap);
                // 仓储订单
            } else if (StringUtils.equals(ofcExceptOrders.get(0).getOrderType(), WAREHOUSE_DIST_ORDER)) {
                this.dealStoreOrder(ofcExceptOrders, ofcEnumerations, ofcTimeEnumMap);
            }
        }
        this.removeFromCodeList();
    }

    private boolean potTypeEqualOrderType(OfcExceptOrderDTO ofcExceptOrderDTO, OfcExceptOrder ofcExceptOrder) {
        String exceptPot = ofcExceptOrderDTO.getExceptPot();
        String orderType = ofcExceptOrder.getOrderType();
        if ((StringUtils.equals(exceptPot, STORAGE_IN.getPotCode()) || StringUtils.equals(exceptPot, STORAGE_OUT.getPotCode()))
                && StringUtils.equals(orderType, WAREHOUSE_DIST_ORDER)) {
            return true;
        } else if ((StringUtils.equals(exceptPot, DELIVERY.getPotCode()) || StringUtils.equals(exceptPot, DISPATCH.getPotCode())
                || StringUtils.equals(exceptPot, ARRIVED.getPotCode()) || StringUtils.equals(exceptPot, SIGNED.getPotCode())
                || StringUtils.equals(exceptPot, RECEIPT.getPotCode())) && StringUtils.equals(orderType, TRANSPORT_ORDER)) {
            return true;
        }
        return false;
    }

    private List<OfcExceptOrder> dealOrderHaventReceive(OfcExceptOrderDTO ofcExceptOrderDTO) {
        List<OfcExceptOrder> result = new ArrayList<>();
        String orderCode = ofcExceptOrderDTO.getOrderCode();
        OfcOrderPotDTO ofcOrderPotDTO = new OfcOrderPotDTO();
        ofcOrderPotDTO.setOrderCode(orderCode);
        OfcExceptOrder ofcExceptOrder = this.getOrderDetail(ofcOrderPotDTO);
        if (!this.potTypeEqualOrderType(ofcExceptOrderDTO, ofcExceptOrder)) {
            logger.debug("订单格式与当前处理格式不符, 不予处理....");
            return null;
        }
        ofcExceptOrder.setPotType(ofcExceptOrderDTO.getExceptPot());
        if (ofcExceptOrderMapper.insert(ofcExceptOrder) < 1) logger.error("ofcExceptOrder插入失败...");
        result.add(ofcExceptOrder);
        return result;
    }

    private Map<String,OfcEnumeration> loadOfcTimeEnumMap(OfcEnumeration ofcEnumeration) {
        List<OfcEnumeration> ofcTimeEnum = ofcEnumerationService.queryOfcEnumerationList(ofcEnumeration);
        Map<String,OfcEnumeration> result = new HashMap<>();
        for (OfcEnumeration enumeration : ofcTimeEnum) {
            result.put(enumeration.getEnumName(), enumeration);
        }
        return result;
    }

    private List<String> loadUndealOrders(OfcExceptOrderDTO ofcExceptOrderDTO) throws Exception {
        logger.debug("loadUndealOrders ==> {}", ofcExceptOrderDTO);
        if (null == ofcExceptOrderDTO || StringUtils.isEmpty(ofcExceptOrderDTO.getExceptPot())) throw new BusinessException("处理异常订单失败");
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        String potKeyPrefix = "orderPot:" + ofcExceptOrderDTO.getExceptPot() + ":";
        Date now = new Date();
        TypeReference<List<String>> typeReference = new TypeReference<List<String>>() {
        };
        List<String> result = new ArrayList<>();
        for (int i = 1; i <= 10; i ++) {
            Calendar calendar = DateUtils.toCalendar(now);
            calendar.add(Calendar.DAY_OF_MONTH, -i);
            String suffix = com.xescm.ofc.utils.DateUtils.Date2String(calendar.getTime(), com.xescm.ofc.utils.DateUtils.DateFormatType.TYPE2);
            String undealCodes = stringStringValueOperations.get(potKeyPrefix + suffix);
            if (StringUtils.isEmpty(undealCodes)) continue;
            result.addAll(JacksonUtil.parseJson(undealCodes, typeReference));
        }
        return result;
    }

    @Override
    public List<OfcExceptOrder> selectByDTO(OfcExceptOrderDTO ofcExceptOrderDTO) {
        if (null == ofcExceptOrderDTO) throw new BusinessException("运营平台查询异常订单出错");
        ofcExceptOrderDTO.setDealStatus(IS_EXCEPTION.getCode());
        ofcExceptOrderDTO.setExceptReason("");
        List<OfcExceptOrder> result = ofcExceptOrderMapper.selectExceptOrderByDTO(ofcExceptOrderDTO);
        logger.debug("运营平台查询异常订单{}", result);
        if (CollectionUtils.isEmpty(result)) throw new BusinessException("暂无异常订单");
        return result;
    }



    private void dealStoreOrder(List<OfcExceptOrder> ofcExceptOrders, List<OfcEnumeration> ofcEnumerations, Map<String, OfcEnumeration> ofcTimeEnum) {
        if (CollectionUtils.isEmpty(ofcExceptOrders)) {
            logger.error("处理异常订单失败, 入参为空!");
            throw new BusinessException("处理异常订单失败");
        }
        OfcExceptOrder ofcExceptOrder = ofcExceptOrders.get(0);
        String businessType = ofcExceptOrder.getBusinessType();
        boolean isZhongPin = this.isZhongPin(ofcExceptOrder, ofcEnumerations);
        boolean isNormalCust = this.isNormalCust(ofcExceptOrder, ofcEnumerations);
        boolean goodsTypeHasChilledPork = this.checkGoodsTypeHasChilledPork(ofcExceptOrder);
        boolean provideTransport = StringUtils.equals(ofcExceptOrder.getProvideTransport(), STR_YES);
        int calendarHour = Integer.valueOf(ofcTimeEnum.get("CALENDAR_HOUR").getEnumValue());
        // 入库
        if (StringUtils.equals(businessType.substring(0,2), STOCK_IN_ORDER)) {
            // 入库 提供运输 众品 冷鲜肉 入库时效6小时 城配调度时效12小时
            if (provideTransport && isZhongPin && goodsTypeHasChilledPork) {
                this.checkDelay(ofcExceptOrders, STORAGE_IN.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue()), calendarHour);
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_12").getEnumValue()), calendarHour);
            }
            // 入库 提供运输 众品 非冷鲜肉 入库时效6小时 城配调度时效48小时
            if (provideTransport && isZhongPin && !goodsTypeHasChilledPork) {
                this.checkDelay(ofcExceptOrders, STORAGE_IN.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue()), calendarHour);
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_48").getEnumValue()), calendarHour);
            }
            // 入库 提供运输 普通客户 入库时效6小时 城配调度时效12小时
            if (provideTransport && isNormalCust) {
                this.checkDelay(ofcExceptOrders, STORAGE_IN.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue()), calendarHour);
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_12").getEnumValue()), calendarHour);
            }
            // 入库 不提供运输 众品 冷鲜肉 入库时效6小时
            if (!provideTransport && isZhongPin && goodsTypeHasChilledPork) {
                this.checkDelay(ofcExceptOrders, STORAGE_IN.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue()), calendarHour);
            }
            // 入库 不提供运输 众品 非冷鲜肉 入库时效6小时
            if (!provideTransport && isZhongPin && !goodsTypeHasChilledPork) {
                this.checkDelay(ofcExceptOrders, STORAGE_IN.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue()), calendarHour);
            }
            // 入库 不提供运输 普通客户 入库时效6小时
            if (!provideTransport && isNormalCust) {
                this.checkDelay(ofcExceptOrders, STORAGE_IN.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue()), calendarHour);
            }
            // 出库
        } else if (StringUtils.equals(businessType.substring(0,2), STOCK_OUT_ORDER)) {
            // 出库 提供运输 众品 冷鲜肉 出库时效6小时 城配调度时效12小时
            if (provideTransport && isZhongPin && goodsTypeHasChilledPork) {
                this.checkDelay(ofcExceptOrders, STORAGE_OUT.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue()), calendarHour);
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_12").getEnumValue()), calendarHour);
            }
            // 出库 提供运输 众品 非冷鲜肉 出库时效6小时 城配调度时效48小时
            if (provideTransport && isZhongPin && !goodsTypeHasChilledPork) {
                this.checkDelay(ofcExceptOrders, STORAGE_OUT.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue()), calendarHour);
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_48").getEnumValue()), calendarHour);
            }
            // 出库 提供运输 普通客户 出库时效6小时 城配调度时效12小时
            if (provideTransport && isNormalCust)  {
                this.checkDelay(ofcExceptOrders, STORAGE_OUT.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue()), calendarHour);
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_12").getEnumValue()), calendarHour);
            }
            // 出库 不提供运输 众品 冷鲜肉 出库时效6小时
            if (!provideTransport && isZhongPin && goodsTypeHasChilledPork) {
                this.checkDelay(ofcExceptOrders, STORAGE_OUT.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue()), calendarHour);
            }
            // 出库 不提供运输 众品 非冷鲜肉 出库时效6小时
            if (!provideTransport && isZhongPin && !goodsTypeHasChilledPork) {
                this.checkDelay(ofcExceptOrders, STORAGE_OUT.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue()), calendarHour);
            }
            // 出库 不提供运输 普通客户 出库时效6小时
            if (!provideTransport && isNormalCust) {
                this.checkDelay(ofcExceptOrders, STORAGE_OUT.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue()), calendarHour);
            }
        }
    }

    private void dealTransOrder(List<OfcExceptOrder> ofcExceptOrders, List<OfcEnumeration> ofcEnumerations, Map<String, OfcEnumeration> ofcTimeEnum) {
        if (CollectionUtils.isEmpty(ofcExceptOrders)) {
            logger.error("dealTransOrder入参为空!");
            throw new BusinessException("处理异常订单失败");
        }
        OfcExceptOrder ofcExceptOrder = ofcExceptOrders.get(0);
        String businessType = ofcExceptOrder.getBusinessType();
        boolean isZhongPin = this.isZhongPin(ofcExceptOrder, ofcEnumerations);
        boolean goodsTypeHasChilledPork = this.checkGoodsTypeHasChilledPork(ofcExceptOrder);
        boolean isNormalCust = this.isNormalCust(ofcExceptOrder, ofcEnumerations);
        int calendarHour = Integer.valueOf(ofcTimeEnum.get("CALENDAR_HOUR").getEnumValue());
        //     城配
        if (StringUtils.equals(businessType, WITH_THE_CITY)) {
            // 众品 冷鲜肉 城配调度时效12小时
            if (isZhongPin && goodsTypeHasChilledPork) {
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_12").getEnumValue()), calendarHour);
            }
            // 众品 非冷鲜肉 城配调度时效48小时
            if (isZhongPin && !goodsTypeHasChilledPork) {
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_48").getEnumValue()), calendarHour);
            }
            // 普通客户 城配调度时效12小时
            if (isNormalCust) {
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_12").getEnumValue()), calendarHour);
            }
            //     干线
        } else if (StringUtils.equals(businessType, WITH_THE_TRUNK)) {
            // 众品 冷鲜肉 干线调度时效12小时
            if (isZhongPin && goodsTypeHasChilledPork) {
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_12").getEnumValue()), calendarHour);
            }
            // 众品 非冷鲜肉 干线调度时效48小时
            if (isZhongPin && !goodsTypeHasChilledPork) {
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_48").getEnumValue()), calendarHour);
            }
            // 普通客户 干线调度时效24小时
            if (isNormalCust) {
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode()
                        , Integer.valueOf(ofcTimeEnum.get("HOUR_24").getEnumValue()), calendarHour);
            }
            //     卡班
        } else if (StringUtils.equals(businessType, WITH_THE_KABAN)) {
            // 卡班调度时效96小时
            this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode()
                    , Integer.valueOf(ofcTimeEnum.get("HOUR_96").getEnumValue()), calendarHour);
        }
    }

    private boolean checkGoodsTypeHasChilledPork(OfcExceptOrder ofcExceptOrder) {
        String orderCode = ofcExceptOrder.getOrderCode();
        List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = ofcGoodsDetailsInfoService.queryByOrderCode(orderCode);
        boolean result = false;
        for (OfcGoodsDetailsInfo good : ofcGoodsDetailsInfos) {
            String goodsType = good.getGoodsType();// 大类
            String goodsCategory = good.getGoodsCategory();// 小类
            // fixme
            if ((StringUtils.equals(goodsType, "畜禽类") && StringUtils.equals(goodsCategory, "冷鲜猪肉"))
                    /*|| (StringUtils.equals(goodsTypeCode, "xxxx") && StringUtils.equals(goodsCategoryCode, "xxxx"))*/) {
                result = true;
                break;
            }
        }
        return result;
    }

    private boolean isZhongPin(OfcExceptOrder ofcExceptOrder, List<OfcEnumeration> ofcEnumerations) {
        if (null == ofcExceptOrder) throw new BusinessException("处理异常订单失败!");
        OfcEnumeration ofcEnumeration = new OfcEnumeration();
        ofcEnumeration.setEnumType("SpecialCustZhongpinEnum");
        for (OfcEnumeration enumeration : ofcEnumerations) {
            boolean isZhongPin = StringUtils.equals(enumeration.getEnumName(), ofcExceptOrder.getCustName())
                    && StringUtils.equals(enumeration.getEnumValue(), ofcExceptOrder.getCustCode());
            if (isZhongPin) return true;
        }
        return false;
    }

    private boolean isNormalCust(OfcExceptOrder ofcExceptOrder, List<OfcEnumeration> ofcEnumerations) {
        // 目前普通客户定义为, 非众品客户即为普通客户
        return !isZhongPin(ofcExceptOrder, ofcEnumerations);
    }

    private void checkDelay(List<OfcExceptOrder> ofcExceptOrders, String pot, Integer allowHour, Integer delayTimeLevel) {
        for (OfcExceptOrder ofcExceptOrder : ofcExceptOrders) {
            if (StringUtils.equals(ofcExceptOrder.getPotType(), pot)) {
                ofcExceptOrder = this.dealExceptPot(ofcExceptOrder, allowHour, delayTimeLevel);
                if (ofcExceptOrderMapper.updateByOrderCode(ofcExceptOrder) < 1) {
                    logger.error("更新失败!");
                    throw new BusinessException("处理异常订单失败!");
                }
            }
        }
    }

    private OfcExceptOrder dealExceptPot(OfcExceptOrder ofcExceptOrder, Integer allowHour, Integer delayTimeLevel) {
        logger.debug("dealExceptPot ==> ofcExceptOrder{}", ofcExceptOrder);
        logger.debug("dealExceptPot ==> allowHour{}", allowHour);
        logger.debug("dealExceptPot ==> delayTimeLevel{}", delayTimeLevel);
        if (null == ofcExceptOrder || null == allowHour || null == delayTimeLevel) {
            logger.error("dealExceptPot入参有误");
            throw new BusinessException("处理异常订单失败");
        }
        Calendar orderCreateTime = DateUtils.toCalendar(ofcExceptOrder.getCreationTime());
        Date potTime = ofcExceptOrder.getPotTime();
        String potType = ofcExceptOrder.getPotType();
        switch (potType) {
            case "storageIn": { // 入库
                ofcExceptOrder = this.dealNormalExceptPot(ofcExceptOrder, allowHour, delayTimeLevel, orderCreateTime, potTime);
                break;
            }
            case "storageOut": { // 出库
                ofcExceptOrder = this.dealNormalExceptPot(ofcExceptOrder, allowHour, delayTimeLevel, orderCreateTime, potTime);
                break;
            }
            case "delivery": { // 调度
                ofcExceptOrder = this.dealNormalExceptPot(ofcExceptOrder, allowHour, delayTimeLevel, orderCreateTime, potTime);
                break;
            }
            case "dispatch": { // 发运
                break;
            }
            case "arrived": { // 到达
                break;
            }
            case "signed": { // 签收
                break;
            }
            case "receipt": { // 回单
                break;
            }
        }
        return ofcExceptOrder;
    }

    private OfcExceptOrder dealNormalExceptPot(OfcExceptOrder ofcExceptOrder, Integer allowHour, Integer delayTimeLevel, Calendar orderCreateTime, Date potTime) {
        orderCreateTime.add(delayTimeLevel, allowHour);
        Calendar deadLineTime = null == potTime ? DateUtils.toCalendar(new Date()) : DateUtils.toCalendar(potTime);
        if (deadLineTime.compareTo(orderCreateTime) > 0) {// 超时
            ofcExceptOrder.setExceptReason(String.valueOf((deadLineTime.getTime().getTime() - orderCreateTime.getTime().getTime())));
            ofcExceptOrder.setDealStatus(IS_EXCEPTION.getCode());
            // 已经异常, 从Redis里删掉该订单号, 从异常表里删掉这条记录
            normalOrderToRemove.add(ofcExceptOrder);
        } else if (null != potTime && (potTime.compareTo(orderCreateTime.getTime())) <= 0) {
            // 已接收到时效信息, 且时效正常
            // 从Redis里删掉该订单号, 从异常表里删掉这条记录
            normalOrderToRemove.add(ofcExceptOrder);
        } else {
            // 处于正常时效中, 还未接收到结点时间信息
            ofcExceptOrder.setDealStatus(UN_DEAL.getCode());
        }
        return ofcExceptOrder;
    }

    private void removeFromCodeList() {
        logger.debug("normalOrderToRemove===>{}", normalOrderToRemove);
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        TypeReference<List<String>> typeReference = new TypeReference<List<String>>() {
        };
        for (OfcExceptOrder ofcExceptOrder : normalOrderToRemove) {
            String orderCode = ofcExceptOrder.getOrderCode();
            String potType = ofcExceptOrder.getPotType();
            Date creatTime = ofcExceptOrder.getCreationTime();
            if (StringUtils.isEmpty(orderCode) || StringUtils.isEmpty(potType) || null == creatTime) {
                logger.error("removeFromCodeList入参有误");
                continue;
            }
            StringBuilder key = new StringBuilder("orderPot:").append(potType).append(":");
            key.append(com.xescm.ofc.utils.DateUtils.Date2String(creatTime, com.xescm.ofc.utils.DateUtils.DateFormatType.TYPE2));
            String orderCodeList = stringStringValueOperations.get(key.toString());
            List<String> orderCodes = new ArrayList<>();
            try {
                orderCodes = JacksonUtil.parseJson(orderCodeList, typeReference);
            } catch (Exception e) {
                logger.error("orderCodeList转换异常");
            }
            boolean orderCodesEmpty = CollectionUtils.isEmpty(orderCodes);
            if (!orderCodesEmpty && !orderCodes.remove(orderCode)) {
                logger.error("移除订单{}失败" ,orderCode);
                throw new BusinessException("处理异常订单失败");
            }
            if (orderCodesEmpty) {
                stringRedisTemplate.delete(key.toString());
                return;
            }
            try {
                stringStringValueOperations.set(key.toString(), JacksonUtil.toJsonWithFormat(orderCodes));
            } catch (Exception e) {
                logger.error("orderCodes转换异常");
            }
        }
    }

    @Override
    public int loadYesterdayOrder() throws Exception {
        OrderScreenCondition orderScreenCondition = new OrderScreenCondition();
        Date nowDate = new Date();
        Calendar now = DateUtils.toCalendar(nowDate);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.SECOND, -1);
        orderScreenCondition.setOrderTimeSuf(now.getTime());
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        orderScreenCondition.setOrderTimePre(now.getTime());
        orderScreenCondition.setOrderType(TRANSPORT_ORDER);// 运输订单
        List<String> yesterdayTransOrders = ofcFundamentalInformationMapper.queryOrderCodeList(orderScreenCondition);
        orderScreenCondition.setOrderType(WAREHOUSE_DIST_ORDER);// 仓储订单
        List<String> yesterdayStorageOrders = ofcFundamentalInformationMapper.queryOrderCodeList(orderScreenCondition);
        if (CollectionUtils.isEmpty(yesterdayTransOrders) && CollectionUtils.isEmpty(yesterdayStorageOrders)) {
            logger.error("加载昨日订单失败! 订单号列表为空!");
            throw new BusinessException("处理异常订单失败");
        }
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        for (String potCode : OrderPotEnum.getCodeList()) {
            String potKey = "orderPot:" + potCode + ":" + com.xescm.ofc.utils.DateUtils.Date2String(now.getTime(), com.xescm.ofc.utils.DateUtils.DateFormatType.TYPE2);
            if (StringUtils.isNotEmpty(ops.get(potKey))) continue;
            stringRedisTemplate.expire(potKey, 10L, TimeUnit.DAYS);
            if (StringUtils.equals(potCode, STORAGE_IN.getPotCode())
                    || StringUtils.equals(potCode, STORAGE_OUT.getPotCode())) {
                ops.set(potKey, JacksonUtil.toJsonWithFormat(yesterdayStorageOrders));
            } else {
                ops.set(potKey, JacksonUtil.toJsonWithFormat(yesterdayTransOrders));
            }
        }
        return yesterdayTransOrders.size() + yesterdayStorageOrders.size();
    }

    @Override
    public int insertUndealOrder(OfcOrderPotDTO ofcOrderPotDTO) {
        logger.debug("ofcOrderPotDTO == > {}", ofcOrderPotDTO);
        if (this.alreadyExistThisPot(ofcOrderPotDTO)) {
            logger.debug("aleadyExistThisPot");
            return 0;
        }
        OfcExceptOrder ofcExceptOrder = this.getOrderDetail(ofcOrderPotDTO);
        return ofcExceptOrderMapper.insert(ofcExceptOrder);
    }

    private OfcExceptOrder getOrderDetail(OfcOrderPotDTO ofcOrderPotDTO) {
        logger.debug("ofcOrderPotDTO==>{}", ofcOrderPotDTO);
        if (null == ofcOrderPotDTO) {
            logger.error("getOrderDetail入参有误");
            throw new BusinessException("处理异常订单失败");
        }
        OfcExceptOrder ofcExceptOrder = new OfcExceptOrder();
        BeanUtils.copyProperties(ofcOrderPotDTO, ofcExceptOrder);
        ofcExceptOrder.setPotType(ofcOrderPotDTO.getPotCode());
        ofcExceptOrder.setPotTime(ofcOrderPotDTO.getPotTime());
        ofcExceptOrder.setTwoDistribution(ofcOrderPotDTO.getTwoDistribution());
        String orderCode = ofcOrderPotDTO.getOrderCode();
        OfcOrderInfoDTO orderInfoDTO = ofcOrderManageOperService.queryOrderMainDetailByOrderCode(orderCode);
        OfcFundamentalInformation ofcFundamentalInformation = orderInfoDTO.getOfcFundamentalInformation();
        if (null == ofcFundamentalInformation) {
            logger.error("查无该订单号{}信息", orderCode);
            throw new BusinessException("查无该订单号信息");
        }
        BeanUtils.copyProperties(ofcFundamentalInformation, ofcExceptOrder);
        String provideTransport = null;
        if (null != orderInfoDTO.getOfcWarehouseInformation() && null != orderInfoDTO.getOfcWarehouseInformation().getProvideTransport()) {
            provideTransport = orderInfoDTO.getOfcWarehouseInformation().getProvideTransport().toString();
        }
        ofcExceptOrder.setProvideTransport(provideTransport);
        String transCode = null;
        if (null != orderInfoDTO.getOfcDistributionBasicInfo() && !StringUtils.isEmpty(orderInfoDTO.getOfcDistributionBasicInfo().getTransCode())) {
            transCode = orderInfoDTO.getOfcDistributionBasicInfo().getTransCode();
        }
        ofcExceptOrder.setTransCode(transCode);
        return ofcExceptOrder;
    }

    private boolean alreadyExistThisPot(OfcOrderPotDTO ofcOrderPotDTO) {
        String orderCode = ofcOrderPotDTO.getOrderCode();
        List<OfcExceptOrder> ofcExceptOrders = ofcExceptOrderMapper.selectByOrderCode(orderCode);
        boolean result = false;
        for (OfcExceptOrder ofcExceptOrder : ofcExceptOrders) {
            if (StringUtils.equals(ofcExceptOrder.getPotType(), ofcOrderPotDTO.getPotCode())) {
                result = true;
                break;
            }
        }
        return result;
    }

    /*private String coutTimeSub(Calendar first, Calendar second) {
        long sub = first.getTime().getTime() - second.getTime().getTime();
        long day = sub / (24 * 60 * 60 * 1000);
        long hour = (sub / (60 * 60 * 1000) - day * 24);
        long min = ((sub / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (sub / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        StringBuilder sb = new StringBuilder();
        if (day != 0) sb.append(day).append("天");
        if (hour != 0) sb.append(hour).append("小时");
        if (min != 0) sb.append(min).append("分");
        if (s != 0) sb.append(s).append("秒");
        return sb.toString();
    }*/
}
