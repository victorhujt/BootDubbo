package com.xescm.ofc.service.impl;

import com.xescm.core.utils.JacksonUtil;
import com.xescm.ofc.domain.OfcEnumeration;
import com.xescm.ofc.domain.OfcExceptOrder;
import com.xescm.ofc.domain.OrderScreenCondition;
import com.xescm.ofc.enums.OrderPotEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcExceptOrderMapper;
import com.xescm.ofc.mapper.OfcFundamentalInformationMapper;
import com.xescm.ofc.mapper.OfcGoodsDetailsInfoMapper;
import com.xescm.ofc.model.dto.ofc.OfcExceptOrderDTO;
import com.xescm.ofc.model.dto.ofc.OfcOrderPotDTO;
import com.xescm.ofc.service.OfcEnumerationService;
import com.xescm.ofc.service.OfcExceptOrderService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.xescm.ofc.constant.OrderConstConstant.*;
import static com.xescm.ofc.constant.OrderConstant.*;
import static com.xescm.ofc.enums.OrderExcpetDealStatusEnum.IS_EXCEPTION;
import static com.xescm.ofc.enums.OrderExcpetDealStatusEnum.UN_DEAL;
import static com.xescm.ofc.enums.OrderPotEnum.*;

@Service
public class OfcExceptOrderServiceImpl extends BaseService<OfcExceptOrder> implements OfcExceptOrderService {

    @Resource
    private OfcExceptOrderMapper ofcExceptOrderMapper;
    @Resource
    private OfcFundamentalInformationMapper ofcFundamentalInformationMapper;
    @Resource
    private OfcGoodsDetailsInfoMapper ofcGoodsDetailsInfoMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private OfcEnumerationService ofcEnumerationService;

    private Set<OfcExceptOrder> normalOrderToRemove = new HashSet<>();

    private List<String> orderCodesPartKeys = new ArrayList<>();

    @Override
    public void dealExceptOrder(OfcExceptOrderDTO ofcExceptOrderDTO, List<String> orderCodes, List<OfcEnumeration> enumsOfZp, Map<String, OfcEnumeration> enumsOfTime) throws Exception {
        logger.debug("处理异常订单==> {}", ofcExceptOrderDTO);
        if (null == ofcExceptOrderDTO || StringUtils.isEmpty(ofcExceptOrderDTO.getExceptPot())
                || !OrderPotEnum.getCodeList().contains(ofcExceptOrderDTO.getExceptPot())) throw new BusinessException("处理异常订单失败");
        if (CollectionUtils.isEmpty(orderCodes)) {
            logger.debug("暂无待处理订单...");
            return;
        }
        Map<String, List<OfcExceptOrder>> partOrderExceptMap = this.partOrderExceptListToMap(orderCodes);
        for (String orderCode : orderCodes) {
            List<OfcExceptOrder> ofcExceptOrders = partOrderExceptMap.get(orderCode);
            ofcExceptOrderDTO.setOrderCode(orderCode);
            if (CollectionUtils.isEmpty(ofcExceptOrders)) {
                logger.debug("该订单号{}下无状态", orderCode);
                ofcExceptOrders = this.dealOrderHaventReceive(ofcExceptOrderDTO);
            }
            if (null == ofcExceptOrders || !this.potTypeEqualOrderType(ofcExceptOrderDTO, ofcExceptOrders.get(0))) {
                logger.debug("ofcExceptOrders为空或订单格式与当前处理格式不符, 不予处理....");
                continue;
            }
            // 运输订单
            if (StringUtils.equals(ofcExceptOrders.get(0).getOrderType(), TRANSPORT_ORDER)) {
                this.dealTransOrder(ofcExceptOrders, enumsOfZp, enumsOfTime);
                // 仓储订单
            } else if (StringUtils.equals(ofcExceptOrders.get(0).getOrderType(), WAREHOUSE_DIST_ORDER)) {
                this.dealStoreOrder(ofcExceptOrders, enumsOfZp, enumsOfTime);
            }
        }
    }

    private Map<String, List<OfcExceptOrder>> partOrderExceptListToMap(List<String> orderCodes) {
        Map<String, List<OfcExceptOrder>> result = new HashMap<>();
        List<OfcExceptOrder> partExceptOrdersList = ofcExceptOrderMapper.selectByOrderCodeList(orderCodes);
        for (OfcExceptOrder ofcExceptOrder : partExceptOrdersList) {
            String orderCode = ofcExceptOrder.getOrderCode();
            List<OfcExceptOrder> mapValue = result.get(orderCode);
            if (CollectionUtils.isEmpty(mapValue)) {
                mapValue = new ArrayList<>();
                result.put(orderCode, mapValue);
            }
            mapValue.add(ofcExceptOrder);
        }
        return result;
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
        OfcExceptOrder ofcExceptOrder = this.getOrderDetailFromRedis(ofcOrderPotDTO);
        if (null == ofcExceptOrder) {
            logger.error("无法从Redis中获取该订单信息");
            return null;
        }
        if (!this.potTypeEqualOrderType(ofcExceptOrderDTO, ofcExceptOrder)) {
            logger.debug("订单格式与当前处理格式不符, 不予处理....");
            return null;
        }
        ofcExceptOrder.setPotType(ofcExceptOrderDTO.getExceptPot());
        if (ofcExceptOrderMapper.insert(ofcExceptOrder) < 1) logger.error("ofcExceptOrder插入失败...");
        result.add(ofcExceptOrder);
        return result;
    }

    private OfcExceptOrder getOrderDetailFromRedis(OfcOrderPotDTO ofcOrderPotDTO) {
        OfcExceptOrder ofcExceptOrder = null;
        String orderCode = ofcOrderPotDTO.getOrderCode();
        ListOperations<String, String> listOps = stringRedisTemplate.opsForList();
        for (String orderCodesPartKey : orderCodesPartKeys) {
            if (orderCodesPartKey.contains(orderCode)) {
                List<String> orders;
                try {
                    orders = Arrays.asList(orderCodesPartKey.split(","));
                } catch (Exception e) {
                    logger.error("orderCodesPartKey转换异常");
                    throw new BusinessException("orderCodesPartKey实体转换异常");
                }
                int indexOfOrder = orders.indexOf(orderCode);
                List<String> orderMsg = listOps.range(orderCodesPartKey, indexOfOrder, indexOfOrder);
                if (CollectionUtils.isEmpty(orderMsg)) {
                    logger.error("查无此key");
                    throw new BusinessException("查无此key");
                }
                try {
                    ofcExceptOrder = JacksonUtil.parseJson(orderMsg.get(0), OfcExceptOrder.class);
                } catch (Exception e) {
                    logger.error("orderMsg转换异常{}", e);
                    throw new BusinessException("orderMsg转换异常");
                }
            }
        }
        return ofcExceptOrder;
    }

    @Override
    public Map<String,OfcEnumeration> loadOfcTimeEnumMap(OfcEnumeration ofcEnumeration) {
        List<OfcEnumeration> ofcTimeEnum = ofcEnumerationService.queryOfcEnumerationList(ofcEnumeration);
        Map<String,OfcEnumeration> result = new HashMap<>();
        for (OfcEnumeration enumeration : ofcTimeEnum) {
            result.put(enumeration.getEnumName(), enumeration);
        }
        return result;
    }

    @Override
    public List<String> loadUndealOrders(int indexNum) throws Exception {
        ListOperations<String, String> listOps = stringRedisTemplate.opsForList();
        String potKeyPrefix = "orderPot:";
        Date now = new Date();
        Calendar calendar = DateUtils.toCalendar(now);
        calendar.add(Calendar.DAY_OF_MONTH, -indexNum);
        String suffix = com.xescm.ofc.utils.DateUtils.Date2String(calendar.getTime(), com.xescm.ofc.utils.DateUtils.DateFormatType.TYPE2);
        List<String> range = listOps.range(potKeyPrefix + suffix, 0, -1);// 取出来的是当天所有的订单号part的List, 每一个part存放的是订单号集合
        if (CollectionUtils.isEmpty(range)) {
            return new ArrayList<>();
        }
        orderCodesPartKeys.addAll(range);
        return range;
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
        boolean goodsTypeHasChilledPork = StringUtils.equals(ofcExceptOrder.getCoolFreshPork(), STR_YES);
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
//            if (!provideTransport && isZhongPin && goodsTypeHasChilledPork) {
//                this.checkDelay(ofcExceptOrders, STORAGE_IN.getPotCode()
//                        , Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue()), calendarHour);
//            }
            // 入库 不提供运输 众品 非冷鲜肉 入库时效6小时
//            if (!provideTransport && isZhongPin && !goodsTypeHasChilledPork) {
//                this.checkDelay(ofcExceptOrders, STORAGE_IN.getPotCode()
//                        , Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue()), calendarHour);
//            }
            // 入库 不提供运输 普通客户 入库时效6小时
//            if (!provideTransport && isNormalCust) {
//                this.checkDelay(ofcExceptOrders, STORAGE_IN.getPotCode()
//                        , Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue()), calendarHour);
//            }
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
//            if (!provideTransport && isZhongPin && goodsTypeHasChilledPork) {
//                this.checkDelay(ofcExceptOrders, STORAGE_OUT.getPotCode()
//                        , Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue()), calendarHour);
//            }
            // 出库 不提供运输 众品 非冷鲜肉 出库时效6小时
//            if (!provideTransport && isZhongPin && !goodsTypeHasChilledPork) {
//                this.checkDelay(ofcExceptOrders, STORAGE_OUT.getPotCode()
//                        , Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue()), calendarHour);
//            }
            // 出库 不提供运输 普通客户 出库时效6小时
//            if (!provideTransport && isNormalCust) {
//                this.checkDelay(ofcExceptOrders, STORAGE_OUT.getPotCode()
//                        , Integer.valueOf(ofcTimeEnum.get("HOUR_6").getEnumValue()), calendarHour);
//            }
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
        boolean goodsTypeHasChilledPork = StringUtils.equals(ofcExceptOrder.getCoolFreshPork(), STR_YES);
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

    @Override
    public boolean isZhongPin(OfcExceptOrder ofcExceptOrder, List<OfcEnumeration> ofcEnumerations) {
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
                if (StringUtils.equals(ofcExceptOrder.getDealStatus(), IS_EXCEPTION.getCode())) continue;
                ofcExceptOrder = this.dealExceptPot(ofcExceptOrder, allowHour, delayTimeLevel);
                if (StringUtils.equals(ofcExceptOrder.getDealStatus(), IS_EXCEPTION.getCode())
                        && ofcExceptOrderMapper.updateByOrderCode(ofcExceptOrder) < 1) {
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


    @Override
    public int loadYesterdayOrder() throws Exception {
        OrderScreenCondition orderScreenCondition = new OrderScreenCondition();
        Date nowDate = new Date();
        Calendar now = DateUtils.toCalendar(nowDate);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        orderScreenCondition.setOrderTimeSuf(now.getTime());
        now.add(Calendar.DATE, -1);
        orderScreenCondition.setOrderTimePre(now.getTime());
        long sOd = System.currentTimeMillis();
        List<OfcExceptOrder> undealedOrders = ofcFundamentalInformationMapper.queryByCondition(orderScreenCondition);
        System.out.println("============================异常订单：1 -> 查询昨日订单耗时：" + (System.currentTimeMillis() - sOd));
        if (CollectionUtils.isEmpty(undealedOrders)) {
            logger.error("加载昨日订单失败! 订单号列表为空!");
            throw new BusinessException("处理异常订单失败");
        }
        long sOdd = System.currentTimeMillis();
        orderScreenCondition.setGoodsType("畜禽类");
        orderScreenCondition.setGoodsCategory("冷鲜猪肉");
        List<String> ordersGoodsList = ofcGoodsDetailsInfoMapper.queryByCondition(orderScreenCondition);
        System.out.println("============================异常订单：2 -> 查询昨日订单明细耗时：" + (System.currentTimeMillis() - sOdd));
        Map<String, Boolean> matchedGoods = new HashMap<>();
//        Map<String, Boolean> matchedGoods = ordersGoodsList.stream().distinct().collect(Collectors.toMap(Function.identity(), (goods) -> true));
        for (String orderCode : ordersGoodsList) {
            matchedGoods.put(orderCode, true);
        }
        long sMatch = System.currentTimeMillis();
        for (OfcExceptOrder orderInfo : undealedOrders) {
            String orderCode = orderInfo.getOrderCode();
            Boolean isMatch = matchedGoods.get(orderCode);
            if (isMatch != null && isMatch) {
                orderInfo.setCoolFreshPork(STR_YES);
            }
        }
        System.out.println("============================异常订单：3 -> 匹配货品分类明细耗时：" + (System.currentTimeMillis() - sMatch));
        long sRedis = System.currentTimeMillis();
        ListOperations<String, String> listOps = stringRedisTemplate.opsForList();
        int undealedSize = undealedOrders.size();
        String ordersKey = "orderPot:" + com.xescm.ofc.utils.DateUtils.Date2String(now.getTime(), com.xescm.ofc.utils.DateUtils.DateFormatType.TYPE2);
        StringBuilder key = new StringBuilder();
        List<String> value = new ArrayList<>();
        for (int index = 1; index <= undealedSize; index ++) {
            OfcExceptOrder ofcExceptOrder = undealedOrders.get(index - 1);
            String orderCode = ofcExceptOrder.getOrderCode();
            key.append(orderCode).append(",");
            String aPartKey = key.toString();
            value.add(JacksonUtil.toJson(ofcExceptOrder));
            if (aPartKey.split(",").length / 50 > 0 || index == undealedSize) {
                aPartKey = key.deleteCharAt(key.length() - 1).toString();
                stringRedisTemplate.delete(aPartKey);
//                stringRedisTemplate.delete(ordersKey);
                listOps.rightPushAll(aPartKey, value);
                listOps.rightPush(ordersKey, aPartKey);
                stringRedisTemplate.expire(aPartKey, 11L, TimeUnit.DAYS);
                stringRedisTemplate.expire(ordersKey, 11L, TimeUnit.DAYS);
                if (index != undealedSize) {
                    key.delete(0, key.length());
                    value.clear();
                }
            }
        }
        System.out.println("============================异常订单：4 -> 保存redis耗时：" + (System.currentTimeMillis() - sRedis));
        return undealedSize;
    }

    @Override
    public int insertUndealOrder(OfcOrderPotDTO ofcOrderPotDTO) {
        logger.debug("ofcOrderPotDTO == > {}", ofcOrderPotDTO);
        if (this.alreadyExistThisPot(ofcOrderPotDTO)) {
            logger.debug("aleadyExistThisPot");
            return 0;
        }
        if (this.dealSpecialStatus(ofcOrderPotDTO)) return 0;
        OfcExceptOrder ofcExceptOrder = this.getOrderDetail(ofcOrderPotDTO);
        return ofcExceptOrderMapper.insert(ofcExceptOrder);
    }

    private boolean dealSpecialStatus(OfcOrderPotDTO ofcOrderPotDTO) {
        String orderCode = ofcOrderPotDTO.getOrderCode();
        if (StringUtils.equals(ofcOrderPotDTO.getPotCode(), CANCEL_DELIVERY.getPotCode())) {
            List<OfcExceptOrder> ofcExceptOrders = ofcExceptOrderMapper.selectByOrderCode(orderCode);
            if (CollectionUtils.isEmpty(ofcExceptOrders)
                    || !StringUtils.equals(ofcExceptOrders.get(0).getOrderType(), TRANSPORT_ORDER)) return false;
            for (OfcExceptOrder ofcExceptOrder : ofcExceptOrders) {
                if (StringUtils.equals(ofcExceptOrder.getPotType(), DELIVERY.getPotCode())
                        && StringUtils.isNotEmpty(ofcExceptOrder.getExceptReason())
                        && StringUtils.equals(ofcExceptOrder.getDealStatus(), "102")) {
                    ofcExceptOrder.setExceptReason("");
                    ofcExceptOrder.setDealStatus("");
                    int update = ofcExceptOrderMapper.updateByOrderCode(ofcExceptOrder);
                    if (update == 0) logger.error("取消调度更新失败");
                    return true;
                }
            }
        }
        return false;
    }

    private OfcExceptOrder getOrderDetail(OfcOrderPotDTO ofcOrderPotDTO) {
        logger.debug("ofcOrderPotDTO==>{}", ofcOrderPotDTO);
        if (null == ofcOrderPotDTO) {
            logger.error("getOrderDetail入参有误");
            throw new BusinessException("处理异常订单失败");
        }
        String orderCode = ofcOrderPotDTO.getOrderCode();
        OrderScreenCondition orderScreenCondition = new OrderScreenCondition();
        orderScreenCondition.setOrderCode(orderCode);
        List<OfcExceptOrder> exceptOrders = ofcFundamentalInformationMapper.queryByCondition(orderScreenCondition);
        if (CollectionUtils.isEmpty(exceptOrders)) {
            logger.error("查询不到该订单");
            throw new BusinessException("查询不到该订单");
        }
        OfcExceptOrder ofcExceptOrder = exceptOrders.get(0);
        BeanUtils.copyProperties(ofcOrderPotDTO, ofcExceptOrder);
        ofcExceptOrder.setPotType(ofcOrderPotDTO.getPotCode());
        ofcExceptOrder.setPotTime(ofcOrderPotDTO.getPotTime());
        ofcExceptOrder.setTwoDistribution(ofcOrderPotDTO.getTwoDistribution());
        return ofcExceptOrder;
    }

    private boolean alreadyExistThisPot(OfcOrderPotDTO ofcOrderPotDTO) {
        String orderCode = ofcOrderPotDTO.getOrderCode();
        List<OfcExceptOrder> ofcExceptOrders = ofcExceptOrderMapper.selectByOrderCode(orderCode);
        boolean result = false;
        for (OfcExceptOrder ofcExceptOrder : ofcExceptOrders) {
            if (StringUtils.equals(ofcExceptOrder.getPotType(), ofcOrderPotDTO.getPotCode()) && null != ofcExceptOrder.getPotTime()) {
                result = true;
                break;
            }
        }
        return result;
    }
}
