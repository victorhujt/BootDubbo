package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.domain.OfcWarehouseInformation;

import java.util.List;
import java.util.Map;

/**
 * Created by ydx on 2016/10/12.
 */
public interface OfcOrderManageService {
    String orderAudit(String orderCode,String orderStatus,String reviewTag);
    String orderDelete(String orderCode,String orderStatus);
    String orderCancel(String orderCode,String orderStatus);
    Map<String,Object> getContactMessage(String contactCompanyName, String contactName, String purpose);
    Map<String,Object> getSupportMessage(String suppulierName, String suppulierContactName);
    OfcWarehouseInformation getWarehouseMessage(String warehouseCode);
}
