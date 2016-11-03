package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcWarehouseInformation;
import com.xescm.ofc.domain.dto.csc.CscSupplierInfoDto;
import com.xescm.ofc.domain.dto.csc.vo.CscContantAndCompanyVo;
import com.xescm.ofc.domain.dto.rmc.RmcWarehouse;

import java.util.Map;

/**
 * Created by ydx on 2016/10/12.
 */
public interface OfcOrderManageService {
    String orderAudit(String orderCode,String orderStatus,String reviewTag);
    String orderDelete(String orderCode,String orderStatus);
    String orderCancel(String orderCode,String orderStatus);
    CscContantAndCompanyVo getContactMessage(String contactCompanyName, String contactName, String purpose, String custId);
    CscSupplierInfoDto getSupportMessage(String suppulierName, String suppulierContactName, String custId);
    /*OfcWarehouseInformation getWarehouseMessage(String orderCode);*/
}
