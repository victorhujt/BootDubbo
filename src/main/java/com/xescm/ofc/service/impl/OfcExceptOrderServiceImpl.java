package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcExceptOrder;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcExceptOrderMapper;
import com.xescm.ofc.model.dto.ofc.ExceptOrderDTO;
import com.xescm.ofc.service.OfcExceptOrderService;
import com.xescm.ofc.service.OfcGoodsDetailsInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.xescm.ofc.constant.OrderConstConstant.*;
import static com.xescm.ofc.constant.OrderConstant.*;
import static com.xescm.ofc.enums.OrderExcpetDealStatusEnum.*;
import static com.xescm.ofc.enums.OrderPotEnum.*;

@Service
public class OfcExceptOrderServiceImpl extends BaseService<OfcExceptOrder> implements OfcExceptOrderService {

    @Resource
    private OfcExceptOrderMapper ofcExceptOrderMapper;
    @Resource
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;

    @Override
    public void dealExceptOrder() {
        List<String> orderCodes = ofcExceptOrderMapper.selectUndealedOrderCode();
        for (String orderCode : orderCodes) {
            List<OfcExceptOrder> ofcExceptOrders = ofcExceptOrderMapper.selectByExample(new OfcExceptOrder(orderCode));
            for (OfcExceptOrder ofcExceptOrder : ofcExceptOrders) {
                ofcExceptOrder.setDealStatus(DEALING.getCode());
                if (super.update(ofcExceptOrder) < 1) logger.error("异常订单更新为处理中失败...");
            }
            if (CollectionUtils.isEmpty(ofcExceptOrders)) {
                logger.error("该订单号{}下无状态", orderCode);
                continue;
            }
            // 运输订单
            if (StringUtils.equals(ofcExceptOrders.get(0).getOrderType(), TRANSPORT_ORDER)) {
                this.dealTransOrder(ofcExceptOrders);
                // 仓储订单
            } else if (StringUtils.equals(ofcExceptOrders.get(0).getOrderType(), WAREHOUSE_DIST_ORDER)) {
                this.dealStoreOrder(ofcExceptOrders);
            }
        }
    }

    @Override
    public List<OfcExceptOrder> selectByDTO(ExceptOrderDTO exceptOrderDTO) {
        if (null == exceptOrderDTO) throw new BusinessException("运营平台查询异常订单出错");
        List<OfcExceptOrder> result = ofcExceptOrderMapper.selectByDTO(exceptOrderDTO);
        logger.info("运营平台查询异常订单{}", result);
        if (CollectionUtils.isEmpty(result)) throw new BusinessException("运营平台查询异常订单出错");
        return result;
    }

    private void dealStoreOrder(List<OfcExceptOrder> ofcExceptOrders) {
        if (CollectionUtils.isEmpty(ofcExceptOrders)) {
            throw new BusinessException("入参为空");
        }
        OfcExceptOrder ofcExceptOrder = ofcExceptOrders.get(0);
        String businessType = ofcExceptOrder.getBusinessType();
        boolean isZhongPin = this.isZhongPin(ofcExceptOrder);
        boolean isNormalCust = this.isNormalCust(ofcExceptOrder);
        boolean goodsTypeHasChilledPork = this.checkGoodsTypeHasChilledPork(ofcExceptOrder);
        boolean provideTransport = StringUtils.equals(ofcExceptOrder.getProvideTransport(), STR_YES);
        // 入库
        if (StringUtils.equals(businessType.substring(0,2), STOCK_IN_ORDER)) {
            // 入库 提供运输 众品 冷鲜肉 入库时效6小时 城配调度时效12小时
            if (provideTransport && isZhongPin && goodsTypeHasChilledPork) {
                this.checkDelay(ofcExceptOrders, STORAGE_IN.getPotCode(), 6);
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode(), 12);
            }
            // 入库 提供运输 众品 非冷鲜肉 入库时效6小时 城配调度时效48小时
            if (provideTransport && isZhongPin && !goodsTypeHasChilledPork) {
                this.checkDelay(ofcExceptOrders, STORAGE_IN.getPotCode(), 6);
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode(), 48);
            }
            // 入库 提供运输 普通客户 入库时效6小时 城配调度时效12小时
            if (provideTransport && isNormalCust) {
                this.checkDelay(ofcExceptOrders, STORAGE_IN.getPotCode(), 6);
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode(), 12);
            }
            // 入库 不提供运输 众品 冷鲜肉 入库时效6小时
            if (!provideTransport && isZhongPin && goodsTypeHasChilledPork) this.checkDelay(ofcExceptOrders, STORAGE_IN.getPotCode(), 6);
            // 入库 不提供运输 众品 非冷鲜肉 入库时效6小时
            if (!provideTransport && isZhongPin && !goodsTypeHasChilledPork) this.checkDelay(ofcExceptOrders, STORAGE_IN.getPotCode(), 6);
            // 入库 不提供运输 普通客户 入库时效6小时
            if (!provideTransport && isNormalCust) this.checkDelay(ofcExceptOrders, STORAGE_IN.getPotCode(), 6);
            // 出库
        } else if (StringUtils.equals(businessType.substring(0,2), STOCK_OUT_ORDER)) {
            // 出库 提供运输 众品 冷鲜肉 出库时效6小时 城配调度时效12小时
            if (provideTransport && isZhongPin && goodsTypeHasChilledPork) {
                this.checkDelay(ofcExceptOrders, STORAGE_OUT.getPotCode(), 6);
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode(), 12);
            }
            // 出库 提供运输 众品 非冷鲜肉 出库时效6小时 城配调度时效48小时
            if (provideTransport && isZhongPin && !goodsTypeHasChilledPork) {
                this.checkDelay(ofcExceptOrders, STORAGE_OUT.getPotCode(), 6);
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode(), 48);
            }
            // 出库 提供运输 普通客户 出库时效6小时 城配调度时效12小时
            if (provideTransport && isNormalCust)  {
                this.checkDelay(ofcExceptOrders, STORAGE_OUT.getPotCode(), 6);
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode(), 12);
            }
            // 出库 不提供运输 众品 冷鲜肉 出库时效6小时
            if (!provideTransport && isZhongPin && goodsTypeHasChilledPork) {
                this.checkDelay(ofcExceptOrders, STORAGE_OUT.getPotCode(), 6);
            }
            // 出库 不提供运输 众品 非冷鲜肉 出库时效6小时
            if (!provideTransport && isZhongPin && !goodsTypeHasChilledPork) {
                this.checkDelay(ofcExceptOrders, STORAGE_OUT.getPotCode(), 6);
            }
            // 出库 不提供运输 普通客户 出库时效6小时
            if (!provideTransport && isNormalCust) {
                this.checkDelay(ofcExceptOrders, STORAGE_OUT.getPotCode(), 6);
            }
        }
    }

    private void dealTransOrder(List<OfcExceptOrder> ofcExceptOrders) {
        if (CollectionUtils.isEmpty(ofcExceptOrders)) {
            throw new BusinessException("入参为空");
        }
        OfcExceptOrder ofcExceptOrder = ofcExceptOrders.get(0);
        String businessType = ofcExceptOrder.getBusinessType();
        boolean isZhongPin = this.isZhongPin(ofcExceptOrder);
        boolean goodsTypeHasChilledPork = this.checkGoodsTypeHasChilledPork(ofcExceptOrder);
        boolean isNormalCust = this.isNormalCust(ofcExceptOrder);
        //     城配
        if (StringUtils.equals(businessType, WITH_THE_CITY)) {
            // 众品 冷鲜肉 城配调度时效12小时
            if (isZhongPin && goodsTypeHasChilledPork) {
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode(), 12);
            }
            // 众品 非冷鲜肉 城配调度时效48小时
            if (isZhongPin && !goodsTypeHasChilledPork) {
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode(), 48);
            }
            // 普通客户 城配调度时效12小时
            if (isNormalCust) {
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode(), 12);
            }
            //     干线
        } else if (StringUtils.equals(businessType, WITH_THE_TRUNK)) {
            // 众品 冷鲜肉 干线调度时效12小时
            if (isZhongPin && goodsTypeHasChilledPork) {
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode(), 12);
            }
            // 众品 非冷鲜肉 干线调度时效48小时
            if (isZhongPin && !goodsTypeHasChilledPork) {
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode(), 48);
            }
            // 普通客户 干线调度时效24小时
            if (isNormalCust) {
                this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode(), 24);
            }
            //     卡班
        } else if (StringUtils.equals(businessType, WITH_THE_KABAN)) {
            // 卡班调度时效96小时
            this.checkDelay(ofcExceptOrders, DELIVERY.getPotCode(), 96);
        }
    }

    private boolean checkGoodsTypeHasChilledPork(OfcExceptOrder ofcExceptOrder) {
        String orderCode = ofcExceptOrder.getOrderCode();
        List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = ofcGoodsDetailsInfoService.queryByOrderCode(orderCode);
        boolean result = false;
        for (OfcGoodsDetailsInfo good : ofcGoodsDetailsInfos) {
            String goodsType = good.getGoodsType();// 大类
            String goodsCategory = good.getGoodsCategory();// 小类
            if ((StringUtils.equals(goodsType, "畜禽类") && StringUtils.equals(goodsCategory, "冷鲜猪肉"))
                    || (StringUtils.equals(goodsType, "xxxx") && StringUtils.equals(goodsCategory, "xxxx"))) {
                result = true;
                break;
            }
        }
        return result;
    }

    private boolean isZhongPin(OfcExceptOrder ofcExceptOrder) {
        return ofcExceptOrder.getCustName().contains("众品");
    }

    /**
     * 普通客户
     * @param ofcExceptOrder 订单信息
     * @return 结果
     */
    private boolean isNormalCust(OfcExceptOrder ofcExceptOrder) {
        // 目前普通客户定义为, 非众品客户即为普通客户
        return !isZhongPin(ofcExceptOrder);
    }

    private void checkDelay(List<OfcExceptOrder> ofcExceptOrders, String pot, Integer allowHour) {
        for (OfcExceptOrder ofcExceptOrder : ofcExceptOrders) {
            if (StringUtils.equals(ofcExceptOrder.getPotType(), pot)) {
                ofcExceptOrder = this.dealExceptPot(ofcExceptOrder, allowHour);
                if ((StringUtils.isNotEmpty(ofcExceptOrder.getExceptPot())
                        || StringUtils.isNotEmpty(ofcExceptOrder.getExceptReason()))
                        && super.update(ofcExceptOrder) < 1) {
                    logger.error("更新失败!");
                    throw new BusinessException("更新失败!");
                }
            }
        }
    }

    private OfcExceptOrder dealExceptPot(OfcExceptOrder ofcExceptOrder, Integer allowHour) {
//        String potType = ofcExceptOrder.getPotType();
        Calendar calendarOfOrder = DateUtils.toCalendar(ofcExceptOrder.getCreatTime());
        Date potTime = ofcExceptOrder.getPotTime();
        calendarOfOrder.add(Calendar.HOUR, allowHour);
        Calendar compareTime = null == potTime ? DateUtils.toCalendar(new Date()) : DateUtils.toCalendar(potTime);
        if (compareTime.compareTo(calendarOfOrder) > 0) {// 超时
            ofcExceptOrder.setExceptPot(STORAGE_IN.getPotCode());
            ofcExceptOrder.setExceptReason(String.valueOf((compareTime.getTime().getTime() - calendarOfOrder.getTime().getTime())));
            ofcExceptOrder.setDealStatus(IS_EXCEPTION.getCode());
        }
        /*
        switch (potType) {
            case "storageIn": {
                break;
            }
            case "storageOut": {
                break;
            }
            case "delivery": {
                break;
            }
            case "dispatch": {
                break;
            }
            case "arrived": {
                break;
            }
            case "receipt": {
                break;
            }
            default: {
                throw new BusinessException("");
            }
        }
        */
        return ofcExceptOrder;
    }

    private String coutTimeSub(Calendar first, Calendar second) {
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
    }
}
