package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcCustOrderNewstatus;
import com.xescm.ofc.domain.OfcOrderNewstatus;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcCustOrderNewstatusMapper;
import com.xescm.ofc.mapper.OfcOrderNewstatusMapper;
import com.xescm.ofc.service.OfcCustOrderNewstatusService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * Created by lyh on 2017/9/14.
 */
@Service
public class OfcCustOrderNewstatusServiceImpl extends BaseService<OfcCustOrderNewstatus> implements OfcCustOrderNewstatusService {

    @Resource
    private OfcCustOrderNewstatusMapper ofcCustOrderNewstatusMapper;
    @Resource
    private OfcOrderNewstatusMapper ofcOrderNewstatusMapper;

    @Override
    public OfcCustOrderNewstatus queryByOrderCode(String orderCode) {
        if (StringUtils.isEmpty(orderCode)) {
            throw new BusinessException("查询出错");
        }
        OfcOrderNewstatus ofcOrderNewstatus = ofcOrderNewstatusMapper.selectByPrimaryKey(orderCode);
        if (null != ofcOrderNewstatus) {
            OfcCustOrderNewstatus result = new OfcCustOrderNewstatus();
            BeanUtils.copyProperties(ofcOrderNewstatus, result);
            return result;
        }
        return ofcCustOrderNewstatusMapper.queryByOrderCode(orderCode);
    }
}
