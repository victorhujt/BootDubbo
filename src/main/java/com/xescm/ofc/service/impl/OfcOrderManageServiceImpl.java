package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.*;
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
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.ofc.utils.PubUtils;
import com.xescm.uam.domain.constants.SystemHeader;
import com.xescm.uam.utils.wrap.Wrapper;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    private OfcPlannedDetailService ofcPlannedDetailService;
    @Autowired
    private OfcTransplanInfoService ofcTransplanInfoService;
    @Autowired
    private OfcTransplanStatusService ofcTransplanStatusService;
    @Autowired
    private OfcTransplanNewstatusService ofcTransplanNewstatusService;
    @Autowired
    private OfcTraplanSourceStatusService ofcTraplanSourceStatusService;
    @Autowired
    private OfcSiloprogramInfoService ofcSiloprogramInfoService;
    @Autowired
    private OfcSiloproNewstatusService ofcSiloproNewstatusService;
    @Autowired
    private OfcSiloproSourceStatusService ofcSiloproSourceStatusService;
    @Autowired
    private OfcSiloproStatusService ofcSiloproStatusService;
    @Resource
    private CodeGenUtils codeGenUtils;

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
                List<OfcTransplanInfo> ofcTransplanInfoList=ofcTransplanInfoService.ofcTransplanInfoScreenList(orderCode);
                Iterator<OfcTransplanInfo> iter = ofcTransplanInfoList.iterator();
                while(iter.hasNext())
                {
                    //筛选非作废计划单
                    /*ofcPlannedDetail.setPlanCode(ofcTransplanInfo.getPlanCode());
                    OfcGoodsDetailsInfo ofcGoodsDetailsInfo=iter.next();
                    BeanUtils.copyProperties(ofcPlannedDetail,ofcGoodsDetailsInfo);
                    BeanUtils.copyProperties(ofcPlannedDetail,ofcTransplanInfo);
                    ofcPlannedDetailService.save(ofcPlannedDetail);
                    logger.debug("计划单明细保存成功");*/
                }
                //ofcOrderStatus.setOrderStatus(OrderConstEnum.PENDINGAUDIT);
                //ofcOrderStatus.setStatusDesc("反审核");
               // ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                       // +" "+"订单反审核完成");
                logger.debug("作废计划单");
            }else if(ofcOrderStatus.getOrderStatus().equals(OrderConstEnum.PENDINGAUDIT)&&reviewTag.equals("review")){
                ofcOrderStatus.setOrderStatus(OrderConstEnum.ALREADYEXAMINE);
                ofcOrderStatus.setStatusDesc("已审核");
                ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                        +" "+"订单审核完成");
                ofcOrderStatus.setOperator("001");
                ofcOrderStatus.setLastedOperTime(new Date());

                OfcFundamentalInformation ofcFundamentalInformation=ofcFundamentalInformationService.selectByKey(orderCode);
                List<OfcGoodsDetailsInfo> goodsDetailsList=ofcGoodsDetailsInfoService.goodsDetailsScreenList(orderCode,"orderCode");
                OfcDistributionBasicInfo ofcDistributionBasicInfo=ofcDistributionBasicInfoService.distributionBasicInfoSelect(orderCode);
                OfcFinanceInformation ofcFinanceInformation=new OfcFinanceInformation();
                if (PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getOrderType()).equals(OrderConstEnum.TRANSPORTORDER)){
                    //运输订单
                    OfcTransplanInfo ofcTransplanInfo=new OfcTransplanInfo();
                    ofcTransplanInfo.setProgramSerialNumber("1");
                    transPlanCreate(ofcTransplanInfo,ofcFundamentalInformation,goodsDetailsList,ofcDistributionBasicInfo);
                }else if (PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getOrderType()).equals(OrderConstEnum.WAREHOUSEDISTRIBUTIONORDER)){
                    //仓储订单
                    OfcWarehouseInformation ofcWarehouseInformation=ofcWarehouseInformationService.warehouseInformationSelect(orderCode);
                    OfcSiloprogramInfo ofcSiloprogramInfo=new OfcSiloprogramInfo();
                    if (ofcWarehouseInformation.getProvideTransport()==OrderConstEnum.WAREHOUSEORDERPROVIDETRANS){
                        //需要提供运输
                        OfcTransplanInfo ofcTransplanInfo=new OfcTransplanInfo();
                        if (PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(OrderConstEnum.SALESOUTOFTHELIBRARY)
                                || PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(OrderConstEnum.TRANSFEROUTOFTHELIBRARY)
                                || PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(OrderConstEnum.LOSSOFREPORTING)
                                || PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(OrderConstEnum.OTHEROUTOFTHELIBRARY)
                        ){
                            //出库
                            ofcTransplanInfo.setProgramSerialNumber("2");
                            ofcSiloprogramInfo.setProgramSerialNumber("1");

                        }else if (PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(OrderConstEnum.PURCHASINGANDSTORAGE)
                                || PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(OrderConstEnum.ALLOCATESTORAGE)
                                || PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(OrderConstEnum.RETURNWAREHOUSING)
                                || PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(OrderConstEnum.PROCESSINGSTORAGE)){
                            //入库
                            ofcTransplanInfo.setProgramSerialNumber("1");
                            ofcSiloprogramInfo.setProgramSerialNumber("2");
                        }
                        transPlanCreate(ofcTransplanInfo,ofcFundamentalInformation,goodsDetailsList,ofcDistributionBasicInfo);
                        siloProCreate(ofcSiloprogramInfo,ofcFundamentalInformation,goodsDetailsList,ofcWarehouseInformation,ofcFinanceInformation);
                    }else if (ofcWarehouseInformation.getProvideTransport()==OrderConstEnum.WAREHOUSEORDERNOTPROVIDETRANS){
                        //不需要提供运输
                        ofcSiloprogramInfo.setProgramSerialNumber("1");
                        siloProCreate(ofcSiloprogramInfo,ofcFundamentalInformation,goodsDetailsList,ofcWarehouseInformation,ofcFinanceInformation);
                    }else {
                        return String.valueOf(Wrapper.ERROR_CODE);
                    }
                    ofcOrderStatusService.save(ofcOrderStatus);
                    ofcOrderStatus.setOrderStatus(OrderConstEnum.IMPLEMENTATIONIN);
                    ofcOrderStatus.setStatusDesc("执行中");
                    ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                            +" "+"订单审核完成");
                }else {
                    return String.valueOf(Wrapper.ERROR_CODE);
                }
                logger.debug("计划单创建成功");
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

    /**
     * 创建运输计划单
     * @param ofcTransplanInfo
     * @param ofcFundamentalInformation
     * @param goodsDetailsList
     * @param ofcDistributionBasicInfo
     */
    public void transPlanCreate(OfcTransplanInfo ofcTransplanInfo,OfcFundamentalInformation ofcFundamentalInformation,List<OfcGoodsDetailsInfo> goodsDetailsList,OfcDistributionBasicInfo ofcDistributionBasicInfo){
        OfcTraplanSourceStatus ofcTraplanSourceStatus=new OfcTraplanSourceStatus();
        OfcTransplanStatus ofcTransplanStatus=new OfcTransplanStatus();
        OfcTransplanNewstatus ofcTransplanNewstatus=new OfcTransplanNewstatus();
        OfcPlannedDetail ofcPlannedDetail=new OfcPlannedDetail();
        try {
            BeanUtils.copyProperties(ofcTransplanInfo,ofcDistributionBasicInfo);
            BeanUtils.copyProperties(ofcTransplanInfo,ofcFundamentalInformation);
            ofcTransplanInfo.setPlanCode(codeGenUtils.getNewWaterCode("TP",6));
            ofcTransplanInfo.setShippinCustomerCode(ofcFundamentalInformation.getCustCode());
            ofcTransplanInfo.setCreationTime(new Date());
            ofcTransplanInfo.setCreatePersonnel("001");
            BeanUtils.copyProperties(ofcTraplanSourceStatus,ofcDistributionBasicInfo);
            BeanUtils.copyProperties(ofcTraplanSourceStatus,ofcTransplanInfo);
            BeanUtils.copyProperties(ofcTransplanStatus,ofcTransplanInfo);
            BeanUtils.copyProperties(ofcTransplanNewstatus,ofcTransplanInfo);
            Iterator<OfcGoodsDetailsInfo> iter = goodsDetailsList.iterator();
            while(iter.hasNext())
            {
                //保存计划单明细
                ofcPlannedDetail.setPlanCode(ofcTransplanInfo.getPlanCode());
                OfcGoodsDetailsInfo ofcGoodsDetailsInfo=iter.next();
                BeanUtils.copyProperties(ofcPlannedDetail,ofcGoodsDetailsInfo);
                BeanUtils.copyProperties(ofcPlannedDetail,ofcTransplanInfo);
                ofcPlannedDetailService.save(ofcPlannedDetail);
                logger.debug("计划单明细保存成功");
            }
            ofcTransplanInfoService.save(ofcTransplanInfo);
            logger.debug("计划单信息保存成功");
            ofcTransplanNewstatusService.save(ofcTransplanNewstatus);
            logger.debug("计划单最新状态保存成功");
            ofcTransplanStatusService.save(ofcTransplanStatus);
            logger.debug("计划单状态保存成功");
            ofcTraplanSourceStatusService.save(ofcTraplanSourceStatus);
            logger.debug("计划单资源状态保存成功");
        } catch (IllegalAccessException e) {
            throw new BusinessException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new BusinessException(e.getMessage());
        }
        //ofcTransplanInfo.setPlanCode(ofcFundamentalInformation.getOrderCode().replace("SO","TP"));
    }

    /**
     * 创建仓储计划单
     * @param ofcSiloprogramInfo
     * @param ofcFundamentalInformation
     * @param goodsDetailsList
     * @param ofcWarehouseInformation
     * @param ofcFinanceInformation
     */
    public void siloProCreate(OfcSiloprogramInfo ofcSiloprogramInfo,OfcFundamentalInformation ofcFundamentalInformation,List<OfcGoodsDetailsInfo> goodsDetailsList,OfcWarehouseInformation ofcWarehouseInformation,OfcFinanceInformation ofcFinanceInformation){
        OfcSiloproStatus ofcSiloproStatus=new OfcSiloproStatus();
        OfcSiloproNewstatus ofcSiloproNewstatus=new OfcSiloproNewstatus();
        OfcSiloproSourceStatus ofcSiloproSourceStatus=new OfcSiloproSourceStatus();
        OfcPlannedDetail ofcPlannedDetail=new OfcPlannedDetail();
        try {
            BeanUtils.copyProperties(ofcSiloprogramInfo,ofcWarehouseInformation);
            BeanUtils.copyProperties(ofcSiloprogramInfo,ofcFinanceInformation);
            BeanUtils.copyProperties(ofcSiloprogramInfo,ofcFundamentalInformation);
            ofcSiloprogramInfo.setPlanCode(codeGenUtils.getNewWaterCode("WP",6));
            ofcSiloprogramInfo.setCreationTime(new Date());
            ofcSiloprogramInfo.setCreatePersonnel("001");
            BeanUtils.copyProperties(ofcSiloproSourceStatus,ofcWarehouseInformation);
            BeanUtils.copyProperties(ofcSiloproSourceStatus,ofcSiloprogramInfo);
            BeanUtils.copyProperties(ofcSiloproStatus,ofcSiloprogramInfo);
            BeanUtils.copyProperties(ofcSiloproNewstatus,ofcSiloprogramInfo);

            Iterator<OfcGoodsDetailsInfo> iter = goodsDetailsList.iterator();
            while(iter.hasNext())
            {
                //保存计划单明细
                ofcPlannedDetail.setPlanCode(ofcSiloprogramInfo.getPlanCode());
                OfcGoodsDetailsInfo ofcGoodsDetailsInfo=iter.next();
                BeanUtils.copyProperties(ofcPlannedDetail,ofcGoodsDetailsInfo);
                BeanUtils.copyProperties(ofcPlannedDetail,ofcSiloprogramInfo);
                ofcPlannedDetailService.save(ofcPlannedDetail);
                logger.debug("计划单明细保存成功");
            }
            ofcSiloprogramInfoService.save(ofcSiloprogramInfo);
            logger.debug("计划单信息保存成功");
            ofcSiloproNewstatusService.save(ofcSiloproNewstatus);
            logger.debug("计划单最新状态保存成功");
            ofcSiloproStatusService.save(ofcSiloproStatus);
            logger.debug("计划单状态保存成功");
            ofcSiloproSourceStatusService.save(ofcSiloproSourceStatus);
            logger.debug("计划单资源状态保存成功");
        } catch (IllegalAccessException e) {
            throw new BusinessException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public void siloProCreate(String orderCode){

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
