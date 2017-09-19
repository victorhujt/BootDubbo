package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcCustGoodsDetailsInfo;
import com.xescm.ofc.mapper.OfcCustGoodsDetailsInfoMapper;
import com.xescm.ofc.service.OfcCustGoodsDetailsInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * Created by lyh on 2017/9/14.
 */
@Service
public class OfcCustGoodsDetailsInfoServiceImpl extends BaseService<OfcCustGoodsDetailsInfo> implements OfcCustGoodsDetailsInfoService{

    @Resource
    private OfcCustGoodsDetailsInfoMapper ofcCustGoodsDetailsInfoMapper;

    @Override
    public List<OfcCustGoodsDetailsInfo> queryByOrderCode(String orderCode) {
        OfcCustGoodsDetailsInfo ofcCustGoodsDetailsInfo = new OfcCustGoodsDetailsInfo();
        ofcCustGoodsDetailsInfo.setOrderCode(orderCode);
        return ofcCustGoodsDetailsInfoMapper.queryByInfo(ofcCustGoodsDetailsInfo);
    }
}
