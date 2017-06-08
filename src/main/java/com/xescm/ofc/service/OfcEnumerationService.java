package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcEnumeration;

import java.util.List;

/**
 * @description: 枚举
 * @author: nothing
 * @date: 2017/6/7 18:50
 */
public interface OfcEnumerationService extends IService<OfcEnumeration> {

    /**
     * <p>Title:      queryOfcEnumerationList. </p>
     * <p>Description 根据条件查询枚举</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/6/7 18:58
     * @return
     */
    List<OfcEnumeration> queryOfcEnumerationList(OfcEnumeration ofcEnumeration);
}
