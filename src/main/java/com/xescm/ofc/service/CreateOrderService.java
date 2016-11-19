package com.xescm.ofc.service;

import com.xescm.ofc.constant.ResultModel;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.domain.dto.coo.CreateOrderEntity;

import java.util.List;

/**
 * 创单
 * Created by hiyond on 2016/11/15.
 */
public interface CreateOrderService {

    /**
     * 创建订单
     * @return boolean
     */
    boolean CreateOrders(List<CreateOrderEntity> list);

    /**
     * 创建订单
     * @param ofcFundamentalInformation 订单中心基本信息
     * @param ofcDistributionBasicInfo 订单中心配送基本信息
     * @param ofcFinanceInformation 财务信息
     * @param ofcWarehouseInformation 仓配信息
     * @param ofcGoodsDetailsInfoList 货品明细信息
     * @return
     */
    ResultModel createOrders(OfcFundamentalInformation ofcFundamentalInformation,
                             OfcDistributionBasicInfo ofcDistributionBasicInfo,
                             OfcFinanceInformation ofcFinanceInformation,
                             OfcWarehouseInformation ofcWarehouseInformation,
                             List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList);

    ResultModel cancelOrderStateByOrderCode(String custOrderCode, String custCode);

    String createOrder(String data) throws Exception;


}
