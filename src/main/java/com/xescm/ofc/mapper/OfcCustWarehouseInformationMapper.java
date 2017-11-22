package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcCustWarehouseInformation;
import com.xescm.ofc.utils.MyMapper;
import org.springframework.stereotype.Repository;


@Repository
public interface OfcCustWarehouseInformationMapper extends MyMapper<OfcCustWarehouseInformation> {
    OfcCustWarehouseInformation queryByInfo(OfcCustWarehouseInformation custWarehouseInformation);
}