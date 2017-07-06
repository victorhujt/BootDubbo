package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcRuntimeProperty;

/**
 * Created by hujintao on 2017/6/13.
 */
public interface OfcRuntimePropertyService extends IService<OfcRuntimeProperty> {

     OfcRuntimeProperty findByName(String name);

     OfcRuntimeProperty getProperty(String name);
}
