package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcSiloprogramInfo;
import com.xescm.ofc.domain.OfcSiloprogramInfoVo;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcSiloprogramInfoMapper;
import com.xescm.ofc.service.OfcSiloprogramInfoService;
import com.xescm.ofc.utils.PubUtils;
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
public class OfcSiloprogramInfoServiceImpl extends BaseService<OfcSiloprogramInfo> implements OfcSiloprogramInfoService {
    @Autowired
    private OfcSiloprogramInfoMapper ofcSiloprogramInfoMapper;
    @Override
    public List<OfcSiloprogramInfo> ofcSiloprogramInfoScreenList(String orderCode) {
        if(!PubUtils.trimAndNullAsEmpty(orderCode).equals("")){
            //List<OfcTransplanInfo> ofcTransplanInfoList=new ArrayList<OfcTransplanInfo>();
           // Map<String,String> mapperMap = new HashMap<String,String>();
            Map<String,String> mapperMap = new HashMap<>();
            mapperMap.put("orderCode",orderCode);
            /*OfcTransplanInfo ofcTransplanInfo=new OfcTransplanInfo();
            ofcTransplanInfo.setOrderCode(orderCode);*/
            return ofcSiloprogramInfoMapper.ofcSiloprogramInfoScreenList(mapperMap);
        }else {
            throw new BusinessException();
        }
    }

    @Override
    public OfcSiloprogramInfoVo ofcSiloprogramAndResourceInfo(String orderCode) {
        if(!PubUtils.trimAndNullAsEmpty(orderCode).equals("")){
            Map<String,String> mapperMap = new HashMap<>();
            mapperMap.put("orderCode",orderCode);
           return ofcSiloprogramInfoMapper.ofcSiloprogramAndResourceInfo(mapperMap);
        }else {
            throw new BusinessException();
        }
    }
}
