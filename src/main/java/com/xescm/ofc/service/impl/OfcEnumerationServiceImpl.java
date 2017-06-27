package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcEnumeration;
import com.xescm.ofc.mapper.OfcEnumerationMapper;
import com.xescm.ofc.service.OfcEnumerationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 枚举
 * @author: nothing
 * @date: 2017/6/7 18:51
 */
@Service
public class OfcEnumerationServiceImpl extends BaseService<OfcEnumeration> implements OfcEnumerationService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OfcEnumerationMapper ofcEnumerationMapper;

    @Override
    public List<OfcEnumeration> queryOfcEnumerationList(OfcEnumeration ofcEnumeration) {
        List<OfcEnumeration> result;
        try {
            result = ofcEnumerationMapper.select(ofcEnumeration);
        } catch (Exception e) {
            logger.error("查询枚举类型发生异常：异常信息=>{}", e);
            throw e;
        }
        return result;
    }
}
