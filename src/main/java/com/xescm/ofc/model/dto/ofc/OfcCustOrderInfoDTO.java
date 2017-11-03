package com.xescm.ofc.model.dto.ofc;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.edas.model.dto.ofc.OfcTraceOrderDTO;
import lombok.Data;

import java.util.List;

/**
 *
 * Created by lyh on 2017/9/27.
 */
@Data
public class OfcCustOrderInfoDTO {
    private OfcCustFundamentalInformation ofcFundamentalInformation;

    private OfcCustDistributionBasicInfo ofcDistributionBasicInfo;

    private OfcCustFinanceInformation ofcFinanceInformation;

    private OfcCustWarehouseInformation ofcWarehouseInformation;

    private List<OfcCustGoodsDetailsInfo> goodsDetailsInfoList;

    private OfcCustOrderNewstatus currentStatus;

    private List<OfcCustOrderStatus> orderStatusList;

    private OfcTraceOrderDTO ofcTraceOrderDTO;

}
