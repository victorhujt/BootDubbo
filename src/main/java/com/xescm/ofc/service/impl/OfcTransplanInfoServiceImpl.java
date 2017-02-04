package com.xescm.ofc.service.impl;

import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OfcTransplanInfo;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcTransplanInfoMapper;
import com.xescm.ofc.model.vo.ofc.OfcTransplanInfoVo;
import com.xescm.ofc.service.OfcTransplanInfoService;
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
public class OfcTransplanInfoServiceImpl extends BaseService<OfcTransplanInfo> implements OfcTransplanInfoService {
    @Autowired
    private OfcTransplanInfoMapper ofcTransplanInfoMapper;
    @Override
    public List<OfcTransplanInfo> ofcTransplanInfoScreenList(String orderCode) {
        if(!"".equals(PubUtils.trimAndNullAsEmpty(orderCode))){
            Map<String,String> mapperMap = new HashMap<>();
            mapperMap.put("orderCode",orderCode);
            return ofcTransplanInfoMapper.ofcTransplanInfoScreenList(mapperMap);
        }else {
            throw new BusinessException("运输计划单查询入参为空");
        }
    }

    @Override
    public List<OfcTransplanInfoVo> ofcTransplanInfoVoList(String planCode) {
        if(!"".equals(PubUtils.trimAndNullAsEmpty(planCode))){
            Map<String,String> mapperMap = new HashMap<>();
            mapperMap.put("planCode",planCode);
            return ofcTransplanInfoMapper.ofcTransplanInfoVoList(mapperMap);
        }else {
            throw new BusinessException("运输计划单查询入参为空");
        }
    }

    @Override
    public int queryNotInvalidAndNotCompleteTransOrder(String orderCode) {
        return ofcTransplanInfoMapper.queryNotInvalidAndNotCompleteTransOrder(orderCode);
    }

    /**
     * 根据订单编号查询计划单编号列表
     * @param orderCode     订单编号
     * @return     List
     */
    @Override
    public List<String> queryPlanCodesByOrderCode(String orderCode) {
        return ofcTransplanInfoMapper.queryTransCodeByOrderCode(orderCode);
    }

    /**
     * 根据订单编号查询未完成运输计划单编号列表
     * @param orderCode     订单编号
     * @return      List
     */
    @Override
    public List<String> queryUncompletedPlanCodesByOrderCode(String orderCode) {
        return ofcTransplanInfoMapper.queryUncompletedPlanCodesByOrderCode(orderCode);
    }


}
