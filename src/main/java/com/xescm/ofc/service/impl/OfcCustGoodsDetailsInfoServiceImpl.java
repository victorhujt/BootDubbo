package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcCustGoodsDetailsInfo;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.mapper.OfcCustGoodsDetailsInfoMapper;
import com.xescm.ofc.mapper.OfcGoodsDetailsInfoMapper;
import com.xescm.ofc.service.OfcCustGoodsDetailsInfoService;
import com.xescm.ofc.utils.BeanConvertor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by lyh on 2017/9/14.
 */
@Service
public class OfcCustGoodsDetailsInfoServiceImpl extends BaseService<OfcCustGoodsDetailsInfo> implements OfcCustGoodsDetailsInfoService{

    @Resource
    private OfcCustGoodsDetailsInfoMapper ofcCustGoodsDetailsInfoMapper;
    @Resource
    private OfcGoodsDetailsInfoMapper ofcGoodsDetailsInfoMapper;

    @Override
    @SuppressWarnings("unchecked")
    public List<OfcCustGoodsDetailsInfo> queryByOrderCode(String orderCode) {
        List<OfcCustGoodsDetailsInfo> result = new ArrayList<>();
        List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList = ofcGoodsDetailsInfoMapper.queryByOrderCode(orderCode);
        if (CollectionUtils.isNotEmpty(ofcGoodsDetailsInfoList)) {
            return BeanConvertor.listConvertor(ofcGoodsDetailsInfoList, result, OfcCustGoodsDetailsInfo.class);
        }
        OfcCustGoodsDetailsInfo ofcCustGoodsDetailsInfo = new OfcCustGoodsDetailsInfo();
        ofcCustGoodsDetailsInfo.setOrderCode(orderCode);
        return ofcCustGoodsDetailsInfoMapper.queryByInfo(ofcCustGoodsDetailsInfo);
    }
}
