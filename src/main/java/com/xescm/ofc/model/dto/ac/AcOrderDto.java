package com.xescm.ofc.model.dto.ac;

import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.ofc.domain.OfcFinanceInformation;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 推送结算中心DTO
 * Created by hiyond on 2016/12/11.
 */
public class AcOrderDto implements Serializable {
    private static final long serialVersionUID = 6888408121506124213L;

    private OfcFundamentalInformation ofcFundamentalInformation;

    private OfcFinanceInformation ofcFinanceInformation;

    private OfcDistributionBasicInfo ofcDistributionBasicInfo;

    private List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList;

    public OfcFundamentalInformation getOfcFundamentalInformation() {
        return ofcFundamentalInformation;
    }

    public void setOfcFundamentalInformation(OfcFundamentalInformation ofcFundamentalInformation) {
        this.ofcFundamentalInformation = ofcFundamentalInformation;
    }

    public OfcFinanceInformation getOfcFinanceInformation() {
        return ofcFinanceInformation;
    }

    public void setOfcFinanceInformation(OfcFinanceInformation ofcFinanceInformation) {
        this.ofcFinanceInformation = ofcFinanceInformation;
    }

    public OfcDistributionBasicInfo getOfcDistributionBasicInfo() {
        return ofcDistributionBasicInfo;
    }

    public void setOfcDistributionBasicInfo(OfcDistributionBasicInfo ofcDistributionBasicInfo) {
        this.ofcDistributionBasicInfo = ofcDistributionBasicInfo;
    }

    public List<OfcGoodsDetailsInfo> getOfcGoodsDetailsInfoList() {
        return ofcGoodsDetailsInfoList;
    }

    public void setOfcGoodsDetailsInfoList(List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList) {
        this.ofcGoodsDetailsInfoList = ofcGoodsDetailsInfoList;
    }
}
