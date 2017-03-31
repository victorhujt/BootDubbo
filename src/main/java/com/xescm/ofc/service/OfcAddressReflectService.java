package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcAddressReflect;
import com.xescm.ofc.domain.OfcDistributionBasicInfo;

public interface OfcAddressReflectService  {
    OfcAddressReflect selectByAddress(String address);

    void reflectAddressToDis(OfcAddressReflect ofcAddressReflect, OfcDistributionBasicInfo ofcDistributionBasicInfo, String tag);

    void reflectAddressToRef(OfcAddressReflect ofcAddressReflect, OfcDistributionBasicInfo ofcDistributionBasicInfo, String tag);
}