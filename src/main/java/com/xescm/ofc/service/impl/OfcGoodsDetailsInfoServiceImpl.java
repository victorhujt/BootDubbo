package com.xescm.ofc.service.impl;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcGoodsDetailsInfoMapper;
import com.xescm.ofc.service.OfcGoodsDetailsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcGoodsDetailsInfoServiceImpl extends BaseService<OfcGoodsDetailsInfo> implements OfcGoodsDetailsInfoService {
    @Autowired
    private OfcGoodsDetailsInfoMapper ofcGoodsDetailsInfoMapper;
    @Override
    public List<OfcGoodsDetailsInfo> goodsDetailsScreenList(String code, String followTag) {
        if(!PubUtils.trimAndNullAsEmpty(code).equals("")){
            String orderCode = null;
            if(followTag.equals("orderCode")){
                orderCode = code;
            }
            Map<String,String> mapperMap = new HashMap<>();
            mapperMap.put("orderCode",orderCode);
            return ofcGoodsDetailsInfoMapper.goodsDetailsScreen(mapperMap);
        }else {
            throw new BusinessException("获取货品列表有误");
        }
    }

    @Override
    public String deleteByOrderCode(OfcGoodsDetailsInfo ofcGoodsDetailsInfo) {
        ofcGoodsDetailsInfoMapper.deleteByOrderCode(ofcGoodsDetailsInfo);
        return String.valueOf(Wrapper.SUCCESS_CODE);
    }

    @Override
    public void deleteAllByOrderCode(String orderCode) {
        ofcGoodsDetailsInfoMapper.deleteAllByOrderCode(orderCode);
    }

    @Override
    public List<OfcGoodsDetailsInfo> queryByOrderCode(String orderCode) {
        return ofcGoodsDetailsInfoMapper.queryByOrderCode(orderCode);
    }
}
