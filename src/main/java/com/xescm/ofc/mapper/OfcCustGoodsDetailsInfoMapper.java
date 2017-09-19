package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcCustGoodsDetailsInfo;
import com.xescm.ofc.utils.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OfcCustGoodsDetailsInfoMapper extends MyMapper<OfcCustGoodsDetailsInfo> {

    List<OfcCustGoodsDetailsInfo> queryByInfo(OfcCustGoodsDetailsInfo ofcCustGoodsDetailsInfo);
}