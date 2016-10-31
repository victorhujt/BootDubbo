package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.domain.dto.CscContantAndCompanyDto;
import com.xescm.ofc.domain.dto.CscSupplierInfoDto;
import com.xescm.ofc.domain.dto.RmcWarehouse;
import com.xescm.ofc.enums.OrderConstEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.FeignCscCustomerAPI;
import com.xescm.ofc.feign.client.FeignCscCustomerAPIClient;
import com.xescm.ofc.feign.client.FeignCscGoodsAPIClient;
import com.xescm.ofc.feign.client.FeignCscSupplierAPIClient;
import com.xescm.ofc.feign.client.FeignCscWarehouseAPIClient;
import com.xescm.ofc.service.*;
import com.xescm.uam.utils.wrap.Wrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ydx on 2016/10/12.
 */
@Service
public class OfcOrderManageServiceImpl  implements OfcOrderManageService {
    private static final Logger logger = LoggerFactory.getLogger(FeignCscCustomerAPI.class);
    @Autowired
    private OfcOrderStatusService ofcOrderStatusService;

    @Autowired
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Autowired
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Autowired
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;
    @Autowired
    private OfcWarehouseInformationService ofcWarehouseInformationService;
    @Autowired
    private FeignCscGoodsAPIClient feignCscGoodsAPIClient;
    @Autowired
    private FeignCscCustomerAPIClient feignCscCustomerAPIClient;
    @Autowired
    private FeignCscSupplierAPIClient feignCscSupplierAPIClient;
    @Autowired
    private FeignCscWarehouseAPIClient feignCscWarehouseAPIClient;

    @Override
    public String orderAudit(String orderCode,String orderStatus, String reviewTag) {
        OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
        ofcOrderStatus.setOrderCode(orderCode);
        ofcOrderStatus.setOrderStatus(orderStatus);
        logger.debug(ofcOrderStatus.toString());
        if((!ofcOrderStatus.getOrderStatus().equals(OrderConstEnum.IMPLEMENTATIONIN))
                && (!ofcOrderStatus.getOrderStatus().equals(OrderConstEnum.HASBEENCOMPLETED))
                && (!ofcOrderStatus.getOrderStatus().equals(OrderConstEnum.HASBEENCANCELED))){
            if (ofcOrderStatus.getOrderStatus().equals(OrderConstEnum.ALREADYEXAMINE)&&reviewTag.equals("rereview")){
                ofcOrderStatus.setOrderStatus(OrderConstEnum.PENDINGAUDIT);
                ofcOrderStatus.setStatusDesc("反审核");
                ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                        +" "+"订单反审核完成");
            }else if(ofcOrderStatus.getOrderStatus().equals(OrderConstEnum.PENDINGAUDIT)&&reviewTag.equals("review")){
                ofcOrderStatus.setOrderStatus(OrderConstEnum.ALREADYEXAMINE);
                ofcOrderStatus.setStatusDesc("已审核");
                ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                        +" "+"订单审核完成");
            }else {
                return String.valueOf(Wrapper.ERROR_CODE);
            }
            ofcOrderStatus.setOperator("001");
            ofcOrderStatus.setLastedOperTime(new Date());
            ofcOrderStatusService.save(ofcOrderStatus);
            return String.valueOf(Wrapper.SUCCESS_CODE);
        }else {
            return String.valueOf(Wrapper.ERROR_CODE);
        }
    }

    @Override
    public String orderDelete(String orderCode,String orderStatus) {
        if(orderStatus.equals(OrderConstEnum.PENDINGAUDIT)){
            ofcFundamentalInformationService.deleteByKey(orderCode);
            ofcDistributionBasicInfoService.deleteByOrderCode(orderCode);
            ofcOrderStatusService.deleteByOrderCode(orderCode);
            ofcWarehouseInformationService.deleteByOrderCode(orderCode);
            return String.valueOf(Wrapper.SUCCESS_CODE);
        }else {
            return String.valueOf(Wrapper.ERROR_CODE);
       }
    }

    @Override
    public String orderCancel(String orderCode,String orderStatus) {
        OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
        ofcOrderStatus.setOrderCode(orderCode);
        ofcOrderStatus.setOrderStatus(orderStatus);
        if((!ofcOrderStatus.getOrderStatus().equals(OrderConstEnum.PENDINGAUDIT))
                && (!ofcOrderStatus.getOrderStatus().equals(OrderConstEnum.HASBEENCOMPLETED))
                && (!ofcOrderStatus.getOrderStatus().equals(OrderConstEnum.HASBEENCANCELED))){
            ofcOrderStatus.setOrderStatus(OrderConstEnum.HASBEENCANCELED);
            ofcOrderStatus.setStatusDesc("已取消");
            ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                    +" "+"订单已取消");
            ofcOrderStatus.setOperator("001");
            ofcOrderStatus.setLastedOperTime(new Date());
            ofcOrderStatusService.save(ofcOrderStatus);
            OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
            ofcFundamentalInformation.setAbolisher("001");
            ofcFundamentalInformation.setAbolishMark(1);//表明已作废
            return String.valueOf(Wrapper.SUCCESS_CODE);
        }else {
            return String.valueOf(Wrapper.ERROR_CODE);
        }
    }

    @Override
    public Map<String, Object> getContactMessage(String contactCompanyName, String contactName, String purpose) {
        Map<String,Object> map = new HashMap<>();
        CscContantAndCompanyDto cscContantAndCompanyDto = new CscContantAndCompanyDto();
        cscContantAndCompanyDto.setContactCompanyName(contactCompanyName);
        cscContantAndCompanyDto.setContactName(contactName);
        cscContantAndCompanyDto.setPurpose(purpose);
        //cscContantAndCompanyDto.setContactCompanyId("");
        Wrapper<List<CscContantAndCompanyDto>> listWrapper = null;
        try {
            listWrapper = feignCscCustomerAPIClient.queryCscReceivingInfoList(cscContantAndCompanyDto);
            if(null == listWrapper.getResult()){
                throw new BusinessException("接口返回结果为null");
            }
        }catch (Exception ex){
            throw new BusinessException(ex.getMessage());
        }
        if(listWrapper.getResult().size() < 1 ){
            if(OrderConstEnum.CONTACTPURPOSECONSIGNOR.equals(purpose)){
                throw new BusinessException("没有查到该发货人的信息!");
            }else if (OrderConstEnum.CONTACTPURPOSECONSIGNEE.equals(purpose)){
                throw new BusinessException("没有查到该收货人的信息!");
            }
        }

        CscContantAndCompanyDto cscContantAndCompanyDtoDetail = listWrapper.getResult().get(0);
        if(OrderConstEnum.CONTACTPURPOSECONSIGNOR.equals(purpose)){
            map.put("consignorMessage",cscContantAndCompanyDtoDetail);
        }else if (OrderConstEnum.CONTACTPURPOSECONSIGNEE.equals(purpose)){
            map.put("consigneeMessage",cscContantAndCompanyDtoDetail);
        }
        return map;
    }

    @Override
    public Map<String, Object> getSupportMessage(String supportName, String supportContactName) {
        Map<String, Object> map = new HashMap<>();
        CscSupplierInfoDto cscSupplierInfoDto = new CscSupplierInfoDto();
        cscSupplierInfoDto.setSupplierName(supportName);
        cscSupplierInfoDto.setContactName(supportContactName);
        Wrapper<List<CscSupplierInfoDto>> listWrapper = null;
        try {
            listWrapper =  feignCscSupplierAPIClient.querySupplierByAttribute(cscSupplierInfoDto);
            if(null == listWrapper.getResult()){
                throw new BusinessException("接口返回结果为null");
            }
        }catch (Exception ex){
            throw new BusinessException(ex.getMessage());
        }
        if(listWrapper.getResult().size() < 1){
                throw new BusinessException("没有查到该供应商的信息!");
        }
        if(String.valueOf(Wrapper.ERROR_CODE).equals(listWrapper.getCode())){
            throw new BusinessException("查询供应商信息错误!");
        }
        map.put("consigneeMessage",listWrapper.getResult());
        return map;
    }

    @Override
    public RmcWarehouse getWarehouseMessage(String warehouseCode) {
        Wrapper<RmcWarehouse> rmcWarehouseByid = feignCscWarehouseAPIClient.getRmcWarehouseByid(warehouseCode);
        RmcWarehouse result = rmcWarehouseByid.getResult();
        if(null == result){
            throw new BusinessException("没有查到仓库的信息!");
        }
        if(String.valueOf(Wrapper.ERROR_CODE).equals(rmcWarehouseByid.getCode())){
            throw new BusinessException("查询仓库信息错误!");
        }
        return result;
    }

}
