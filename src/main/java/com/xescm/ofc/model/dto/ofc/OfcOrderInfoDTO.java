package com.xescm.ofc.model.dto.ofc;

import com.xescm.ofc.domain.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 订单信息
 * @author: nothing
 * @date: 2017/7/17 14:28
 */
@Data
public class OfcOrderInfoDTO implements Serializable {
    private static final long serialVersionUID = -3939214822255358820L;

    private OfcFundamentalInformation ofcFundamentalInformation;

    private OfcDistributionBasicInfo ofcDistributionBasicInfo;

    private OfcFinanceInformation ofcFinanceInformation;

    private OfcWarehouseInformation ofcWarehouseInformation;

    private List<OfcGoodsDetailsInfo> goodsDetailsInfoList;

    private OfcOrderNewstatus currentStatus;

    private List<OfcOrderStatus> orderStatusList;
}
