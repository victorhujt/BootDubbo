package com.xescm.ofc.service;

import com.xescm.ofc.domain.*;

/**
 * Created by ydx on 2016/10/12.
 */
public interface OfcOrderPlaceService{
    String placeOrder(OfcOrderDTO ofcOrderDTO,String tag);
}
