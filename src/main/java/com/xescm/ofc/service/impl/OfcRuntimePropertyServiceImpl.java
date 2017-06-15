package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcRuntimeProperty;
import com.xescm.ofc.mapper.OfcRuntimePropertyMapper;
import com.xescm.ofc.service.OfcRuntimePropertyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hujintao on 2017/6/13.
 */
@Service
public class OfcRuntimePropertyServiceImpl extends BaseService<OfcRuntimeProperty> implements OfcRuntimePropertyService {

    @Resource
    private OfcRuntimePropertyMapper ofcRuntimePropertyMapper;



    @Override
    public OfcRuntimeProperty findByName(String name) {
        return ofcRuntimePropertyMapper.findByName(name);
    }

    @Override
    public OfcRuntimeProperty getProperty(String name) {


        return null;
    }
}
