package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.domain.dto.csc.CscContantAndCompanyDto;
import com.xescm.ofc.domain.dto.csc.CscSupplierInfoDto;
import com.xescm.ofc.domain.dto.csc.domain.CscContact;
import com.xescm.ofc.domain.dto.csc.domain.CscContactCompany;
import com.xescm.ofc.domain.dto.csc.vo.CscContantAndCompanyVo;
import com.xescm.ofc.domain.dto.rmc.RmcCompanyLineQO;
import com.xescm.ofc.domain.dto.rmc.RmcCompanyLineVo;
import com.xescm.ofc.enums.OrderConstEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.csc.FeignCscCustomerAPI;
import com.xescm.ofc.feign.client.*;
import com.xescm.ofc.model.dto.tfc.TransportDTO;
import com.xescm.ofc.model.dto.tfc.TransportDetailDTO;
import com.xescm.ofc.model.dto.tfc.TransportNoDTO;
import com.xescm.ofc.mq.consumer.DefaultMqConsumer;
import com.xescm.ofc.mq.producer.DefaultMqProducer;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.*;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.Wrapper;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.xescm.ofc.enums.OrderConstEnum.*;

/**
 * Created by ydx on 2016/10/12.
 */
@Service
@Transactional
public class OfcOrderManageServiceImpl  implements OfcOrderManageService {
    private static final Logger logger = LoggerFactory.getLogger(FeignCscCustomerAPI.class);

    @Autowired
    private OfcOrderStatusService ofcOrderStatusService;
    @Autowired
    private FeignRmcCompanyAPIClient feignRmcCompanyAPIClient;
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
    private FeignRmcWarehouseAPIClient feignRmcWarehouseAPIClient;
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
    @Autowired
    private OfcOrderScreenService ofcOrderScreenService;
    @Resource
    private CodeGenUtils codeGenUtils;
    @Autowired
    private DefaultMqProducer defaultMqProducer;

    @Autowired
    private DefaultMqConsumer defaultMqConsumer;
    @Autowired
    private FeignTfcTransPlanApiClient feignTfcTransPlanApiClient;

    @Override
    public String orderAudit(String orderCode,String orderStatus, String reviewTag, AuthResDto authResDtoByToken) {
        OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
        ofcOrderStatus.setOrderCode(orderCode);
        ofcOrderStatus.setOrderStatus(orderStatus);
        logger.debug(ofcOrderStatus.toString());
        if((!ofcOrderStatus.getOrderStatus().equals(IMPLEMENTATIONIN))
                && (!ofcOrderStatus.getOrderStatus().equals(OrderConstEnum.HASBEENCOMPLETED))
                && (!ofcOrderStatus.getOrderStatus().equals(OrderConstEnum.HASBEENCANCELED))){
            if (ofcOrderStatus.getOrderStatus().equals(ALREADYEXAMINE)&&reviewTag.equals("rereview")){
                //planCancle(orderCode,authResDtoByToken.getUamUser().getUserName());


                logger.debug("作废计划单完成");
                ofcOrderStatus.setOrderStatus(OrderConstEnum.PENDINGAUDIT);
                ofcOrderStatus.setStatusDesc("反审核");
                ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                        +" "+"订单反审核完成");
                logger.debug("作废计划单");
            }else if(ofcOrderStatus.getOrderStatus().equals(OrderConstEnum.PENDINGAUDIT)&&reviewTag.equals("review")){
                ofcOrderStatus.setOrderStatus(ALREADYEXAMINE);
                ofcOrderStatus.setStatusDesc("已审核");
                ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                        +" "+"订单审核完成");
                ofcOrderStatus.setOperator(authResDtoByToken.getUamUser().getUserName());
                ofcOrderStatus.setLastedOperTime(new Date());

                OfcFundamentalInformation ofcFundamentalInformation=ofcFundamentalInformationService.selectByKey(orderCode);
                ofcFundamentalInformation.setOperator(authResDtoByToken.getUamUser().getUserName());
                ofcFundamentalInformation.setOperatorName(authResDtoByToken.getUamUser().getUserName());
                ofcFundamentalInformation.setOperTime(new Date());
                List<OfcGoodsDetailsInfo> goodsDetailsList=ofcGoodsDetailsInfoService.goodsDetailsScreenList(orderCode,"orderCode");
                OfcDistributionBasicInfo ofcDistributionBasicInfo=ofcDistributionBasicInfoService.distributionBasicInfoSelect(orderCode);
                OfcFinanceInformation ofcFinanceInformation=new OfcFinanceInformation();
                if (PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getOrderType()).equals(OrderConstEnum.TRANSPORTORDER)){
                    //运输订单
                    OfcTransplanInfo ofcTransplanInfo=new OfcTransplanInfo();
                    ofcTransplanInfo.setProgramSerialNumber("1");
                    transPlanCreate(ofcTransplanInfo,ofcFundamentalInformation,goodsDetailsList,ofcDistributionBasicInfo,authResDtoByToken.getUamUser().getUserName());
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
                        transPlanCreate(ofcTransplanInfo,ofcFundamentalInformation,goodsDetailsList,ofcDistributionBasicInfo,authResDtoByToken.getUamUser().getUserName());
                        siloProCreate(ofcSiloprogramInfo,ofcFundamentalInformation,goodsDetailsList,ofcWarehouseInformation,ofcFinanceInformation,authResDtoByToken.getUamUser().getUserName());
                    }else if (ofcWarehouseInformation.getProvideTransport()==OrderConstEnum.WAREHOUSEORDERNOTPROVIDETRANS){
                        //不需要提供运输
                        ofcSiloprogramInfo.setProgramSerialNumber("1");
                        siloProCreate(ofcSiloprogramInfo,ofcFundamentalInformation,goodsDetailsList,ofcWarehouseInformation,ofcFinanceInformation,authResDtoByToken.getUamUser().getUserName());
                    }else {
                        throw new BusinessException("无法确定是否需要运输");
                    }
                    ofcOrderStatusService.save(ofcOrderStatus);
                    ofcOrderStatus.setOrderStatus(IMPLEMENTATIONIN);
                    ofcOrderStatus.setStatusDesc("执行中");
                    ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                            +" "+"订单开始执行");
                }else {
                    throw new BusinessException("订单类型有误");
                }
                logger.debug("计划单创建成功");
            }else {
                throw new BusinessException("缺少标志位");
            }
            ofcOrderStatus.setOperator(authResDtoByToken.getUamUser().getUserName());
            ofcOrderStatus.setLastedOperTime(new Date());
            ofcOrderStatusService.save(ofcOrderStatus);
            return String.valueOf(Wrapper.SUCCESS_CODE);
        }else {
            throw new BusinessException("订单类型既非”已审核“，也非”未审核“，请检查");
        }
    }

    /**
     * 创建运输计划单
     * @param ofcTransplanInfo
     * @param ofcFundamentalInformation
     * @param goodsDetailsList
     * @param ofcDistributionBasicInfo
     */
    public void transPlanCreate(OfcTransplanInfo ofcTransplanInfo,OfcFundamentalInformation ofcFundamentalInformation,List<OfcGoodsDetailsInfo> goodsDetailsList,OfcDistributionBasicInfo ofcDistributionBasicInfo,String userId){
        OfcTraplanSourceStatus ofcTraplanSourceStatus=new OfcTraplanSourceStatus();
        OfcTransplanStatus ofcTransplanStatus=new OfcTransplanStatus();
        OfcTransplanNewstatus ofcTransplanNewstatus=new OfcTransplanNewstatus();
        OfcPlannedDetail ofcPlannedDetail=new OfcPlannedDetail();
        try {
            BeanUtils.copyProperties(ofcTransplanInfo,ofcDistributionBasicInfo);
            BeanUtils.copyProperties(ofcTransplanInfo,ofcFundamentalInformation);
            ofcTransplanInfo.setPlanCode(codeGenUtils.getNewWaterCode("TP",6));
            ofcTransplanInfo.setShippinCustomerCode(ofcDistributionBasicInfo.getConsignorCode());
            ofcTransplanInfo.setCreationTime(new Date());
            ofcTransplanInfo.setCreatePersonnel(userId);
            BeanUtils.copyProperties(ofcTraplanSourceStatus,ofcDistributionBasicInfo);
            BeanUtils.copyProperties(ofcTraplanSourceStatus,ofcTransplanInfo);
            BeanUtils.copyProperties(ofcTransplanStatus,ofcTransplanInfo);
            BeanUtils.copyProperties(ofcTransplanNewstatus,ofcTransplanInfo);
            ofcTransplanStatus.setPlannedSingleState(OrderConstEnum.ZIYUANFENPEIZ);
            //ofcTransplanNewstatus.setTransportSingleLatestStatus(ofcTransplanStatus.getPlannedSingleState());
            //ofcTransplanNewstatus.setTransportSingleUpdateTime(ofcTransplanInfo.getCreationTime());
            ofcTraplanSourceStatus.setResourceAllocationStatus(OrderConstEnum.DAIFENPEI);
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
            RmcCompanyLineQO rmcCompanyLineQO=new RmcCompanyLineQO();
            if(!PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getBusinessType()).equals("600")
                    && !PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getBusinessType()).equals("601")){

                if(!PubUtils.isSEmptyOrNull(ofcTransplanInfo.getDeparturePlaceCode()) && ofcTransplanInfo.getDeparturePlaceCode().length() > 12){
                    String depatrueCode = ofcTransplanInfo.getDeparturePlaceCode().substring(0,12);
                    String destinationCode = ofcTransplanInfo.getDestinationCode().substring(0,12);
                    if(depatrueCode.equals(destinationCode)){
                        ofcTransplanInfo.setBusinessType("600");
                        rmcCompanyLineQO.setLineType("2");
                    }else {
                        ofcTransplanInfo.setBusinessType("601");
                        rmcCompanyLineQO.setLineType("1");

                    }
                }else{
                    throw new BusinessException("四级地址编码为空!");
                }
            }else if(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getBusinessType()).equals("600")){
                rmcCompanyLineQO.setLineType("2");
            }else{
                rmcCompanyLineQO.setLineType("1");
            }
            rmcCompanyLineQO.setBeginCityName(ofcTransplanInfo.getDepartureCity());
            rmcCompanyLineQO.setArriveCityName(ofcTransplanInfo.getDestinationCity());
            Wrapper<List<RmcCompanyLineVo>> companyList = companySelByApi(rmcCompanyLineQO);
            if(companyList.getCode()==200 && companyList.getMessage().equals("操作成功")
                    && companyList.getResult().size()==1){
                RmcCompanyLineVo rmcCompanyLineVo=companyList.getResult().get(0);
                ofcTraplanSourceStatus.setServiceProviderName(rmcCompanyLineVo.getCompanyName());
                ofcTraplanSourceStatus.setServiceProviderContact(rmcCompanyLineVo.getContactName());
                ofcTraplanSourceStatus.setServiceProviderContactPhone(rmcCompanyLineVo.getCompanyPhone());
                ofcTraplanSourceStatus.setResourceAllocationStatus("30");
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
    public void siloProCreate(OfcSiloprogramInfo ofcSiloprogramInfo,OfcFundamentalInformation ofcFundamentalInformation,List<OfcGoodsDetailsInfo> goodsDetailsList,OfcWarehouseInformation ofcWarehouseInformation,OfcFinanceInformation ofcFinanceInformation,String userId){
        OfcSiloproStatus ofcSiloproStatus=new OfcSiloproStatus();
        OfcSiloproNewstatus ofcSiloproNewstatus=new OfcSiloproNewstatus();
        OfcSiloproSourceStatus ofcSiloproSourceStatus=new OfcSiloproSourceStatus();
        OfcPlannedDetail ofcPlannedDetail=new OfcPlannedDetail();
        try {
            BeanUtils.copyProperties(ofcSiloprogramInfo,ofcWarehouseInformation);
            BeanUtils.copyProperties(ofcSiloprogramInfo,ofcFinanceInformation);
            BeanUtils.copyProperties(ofcSiloprogramInfo,ofcFundamentalInformation);
            ofcSiloprogramInfo.setPlanCode(codeGenUtils.getNewWaterCode("WP",6));
            ofcSiloprogramInfo.setDocumentType(ofcSiloprogramInfo.getBusinessType());
            if (PubUtils.trimAndNullAsEmpty(ofcSiloprogramInfo.getDocumentType()).equals(OrderConstEnum.SALESOUTOFTHELIBRARY)
                    || PubUtils.trimAndNullAsEmpty(ofcSiloprogramInfo.getDocumentType()).equals(OrderConstEnum.TRANSFEROUTOFTHELIBRARY)
                    || PubUtils.trimAndNullAsEmpty(ofcSiloprogramInfo.getDocumentType()).equals(OrderConstEnum.LOSSOFREPORTING)
                    || PubUtils.trimAndNullAsEmpty(ofcSiloprogramInfo.getDocumentType()).equals(OrderConstEnum.OTHEROUTOFTHELIBRARY)
                    ){
                //出库
                ofcSiloprogramInfo.setBusinessType("出库");

            }else if (PubUtils.trimAndNullAsEmpty(ofcSiloprogramInfo.getDocumentType()).equals(OrderConstEnum.PURCHASINGANDSTORAGE)
                    || PubUtils.trimAndNullAsEmpty(ofcSiloprogramInfo.getDocumentType()).equals(OrderConstEnum.ALLOCATESTORAGE)
                    || PubUtils.trimAndNullAsEmpty(ofcSiloprogramInfo.getDocumentType()).equals(OrderConstEnum.RETURNWAREHOUSING)
                    || PubUtils.trimAndNullAsEmpty(ofcSiloprogramInfo.getDocumentType()).equals(OrderConstEnum.PROCESSINGSTORAGE)){
                //入库
                ofcSiloprogramInfo.setBusinessType("入库");
            }
            ofcSiloprogramInfo.setCreationTime(new Date());
            ofcSiloprogramInfo.setCreatePersonnel(userId);
            BeanUtils.copyProperties(ofcSiloproSourceStatus,ofcWarehouseInformation);
            BeanUtils.copyProperties(ofcSiloproSourceStatus,ofcSiloprogramInfo);
            BeanUtils.copyProperties(ofcSiloproStatus,ofcSiloprogramInfo);
            BeanUtils.copyProperties(ofcSiloproNewstatus,ofcSiloprogramInfo);
            ofcSiloproStatus.setPlannedSingleState(OrderConstEnum.ZIYUANFENPEIZ);
            //ofcSiloproNewstatus.setJobNewStatus(ofcSiloproStatus.getPlannedSingleState());
            //ofcSiloproNewstatus.setJobStatusUpdateTime(ofcSiloprogramInfo.getCreationTime());
            ofcSiloproSourceStatus.setResourceAllocationStatus(OrderConstEnum.YIQUEDING);
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

    public void planCancle(String orderCode,String userId){
        List<OfcTransplanInfo> ofcTransplanInfoList=ofcTransplanInfoService.ofcTransplanInfoScreenList(orderCode);
        for(int i=0;i<ofcTransplanInfoList.size();i++){
            OfcTransplanInfo ofcTransplanInfo = ofcTransplanInfoList.get(i);
            try {
                //defaultMqProducer.toSendMQTfcCancelPlanOrder(JacksonUtil.toJsonWithFormat(ofcTransplanInfo.getPlanCode()));
                TransportNoDTO transportNoDTO = new TransportNoDTO();
                transportNoDTO.setTransportNo(ofcTransplanInfo.getPlanCode());
                Response response = feignTfcTransPlanApiClient.cancelTransport(transportNoDTO);
                if(Response.ERROR_CODE == response.getCode()){
                    logger.error("运输计划单调用TFC取消端口出现异常",response.getMessage());
                    throw new BusinessException("TMS已经在执行,您无法取消,请联系管理员!");
                }
            }
            catch (Exception ex){
                logger.error("运输计划单调用TFC取消端口出现异常",ex.getMessage());
                throw new BusinessException("运输计划单调用TFC取消端口出现异常"+ex.getMessage());
            }
            OfcTransplanStatus ofcTransplanStatus=new OfcTransplanStatus();
            ofcTransplanStatus.setPlanCode(ofcTransplanInfo.getPlanCode());
            ofcTransplanStatus=ofcTransplanStatusService.selectOne(ofcTransplanStatus);
            if(PubUtils.trimAndNullAsEmpty(ofcTransplanStatus.getPlannedSingleState()).equals(YIZUOFEI)){
                throw new BusinessException("状态错误，该计划单已作废");
            }else if (PubUtils.trimAndNullAsEmpty(ofcTransplanStatus.getPlannedSingleState()).equals(RENWUZHONG)
                    || PubUtils.trimAndNullAsEmpty(ofcTransplanStatus.getPlannedSingleState()).equals(RENWUWANCH)){
                throw new BusinessException("该订单状态已在作业中或已完成，无法取消");
            }/*else if (PubUtils.trimAndNullAsEmpty(ofcTransplanStatus.getPlannedSingleState()).equals(YITUISONG)){
                throw new BusinessException("其是运输计划单，需调用【配送中心】运单取消接口");
            }*/else if (PubUtils.trimAndNullAsEmpty(ofcTransplanStatus.getPlannedSingleState()).equals("")){
                throw new BusinessException("状态有误");
            }
            ofcTransplanInfo.setVoidPersonnel(userId);
            ofcTransplanInfo.setVoidTime(new Date());
                    /*OfcTransplanNewstatus ofcTransplanNewstatus=new OfcTransplanNewstatus();
                    ofcTransplanNewstatus.setPlanCode(ofcTransplanInfo.getPlanCode());*/
            ofcTransplanStatus.setPlannedSingleState("50");
            //ofcTransplanNewstatus.setTransportSingleLatestStatus("50");
            //ofcTransplanNewstatusService.updateByPlanCode(ofcTransplanNewstatus);
            ofcTransplanStatusService.updateByPlanCode(ofcTransplanStatus);
            ofcTransplanInfoService.update(ofcTransplanInfo);
        }
        List<OfcSiloprogramInfo> ofcSiloprogramInfoList=ofcSiloprogramInfoService.ofcSiloprogramInfoScreenList(orderCode);
        for(int i=0;i<ofcSiloprogramInfoList.size();i++){
            OfcSiloprogramInfo ofcSiloprogramInfo=ofcSiloprogramInfoList.get(i);
            OfcSiloproStatus ofcSiloproStatus=new OfcSiloproStatus();
            ofcSiloproStatus.setPlanCode(ofcSiloprogramInfo.getPlanCode());
            ofcSiloproStatus=ofcSiloproStatusService.selectOne(ofcSiloproStatus);
            if(PubUtils.trimAndNullAsEmpty(ofcSiloproStatus.getPlannedSingleState()).equals(YIZUOFEI)){
                throw new BusinessException("状态错误，该计划单已作废");
            }else if (PubUtils.trimAndNullAsEmpty(ofcSiloproStatus.getPlannedSingleState()).equals(RENWUZHONG)
                    || PubUtils.trimAndNullAsEmpty(ofcSiloproStatus.getPlannedSingleState()).equals(RENWUWANCH)){
                throw new BusinessException("该订单状态已在作业中或已完成，无法取消");
            }else if (PubUtils.trimAndNullAsEmpty(ofcSiloproStatus.getPlannedSingleState()).equals(YITUISONG)){
                throw new BusinessException("为仓储计划单，则调用【仓储中心】运单取消接口");
            }else if (PubUtils.trimAndNullAsEmpty(ofcSiloproStatus.getPlannedSingleState()).equals("")){
                throw new BusinessException("状态有误");
            }
            ofcSiloprogramInfo.setVoidPersonnel(userId);
            ofcSiloprogramInfo.setVoidTime(new Date());
            //OfcTransplanNewstatus ofcTransplanNewstatus=new OfcTransplanNewstatus();
            //ofcTransplanNewstatus.setPlanCode(ofcTransplanInfo.getPlanCode());
            ofcSiloproStatus.setPlannedSingleState("50");
            //ofcTransplanNewstatus.setTransportSingleLatestStatus("50");
            //ofcTransplanNewstatusService.updateByPlanCode(ofcTransplanNewstatus);
            ofcSiloproStatusService.updateByPlanCode(ofcSiloproStatus);
            ofcSiloprogramInfoService.update(ofcSiloprogramInfo);
        }
    }
    @Override
    public String orderDelete(String orderCode,String orderStatus, AuthResDto authResDtoByToken) {
        if(orderStatus.equals(OrderConstEnum.PENDINGAUDIT)){
            ofcFundamentalInformationService.deleteByKey(orderCode);
            ofcDistributionBasicInfoService.deleteByOrderCode(orderCode);
            ofcOrderStatusService.deleteByOrderCode(orderCode);
            ofcWarehouseInformationService.deleteByOrderCode(orderCode);
            OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
            ofcGoodsDetailsInfo.setOrderCode(orderCode);
            ofcGoodsDetailsInfoService.delete(ofcGoodsDetailsInfo);
            return String.valueOf(Wrapper.SUCCESS_CODE);
        }else {
            throw new BusinessException("计划单状态不在可删除范围内");
       }
    }

    @Override
    public String orderCancel(String orderCode,String orderStatus, AuthResDto authResDtoByToken) {
        if((!PubUtils.trimAndNullAsEmpty(orderStatus).equals(OrderConstEnum.PENDINGAUDIT))
                && (!PubUtils.trimAndNullAsEmpty(orderStatus).equals(OrderConstEnum.HASBEENCOMPLETED))
                && (!PubUtils.trimAndNullAsEmpty(orderStatus).equals(OrderConstEnum.HASBEENCANCELED))){
            planCancle(orderCode,authResDtoByToken.getUamUser().getUserName());
            OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
            ofcOrderStatus.setOrderCode(orderCode);
            ofcOrderStatus.setOrderStatus(OrderConstEnum.HASBEENCANCELED);
            ofcOrderStatus.setStatusDesc("已取消");
            ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                    +" "+"订单已取消");
            ofcOrderStatus.setOperator(authResDtoByToken.getUamUser().getUserName());
            ofcOrderStatus.setLastedOperTime(new Date());
            ofcOrderStatusService.save(ofcOrderStatus);
            OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
            ofcFundamentalInformation.setOperator(authResDtoByToken.getUamUser().getUserName());
            ofcFundamentalInformation.setOperatorName(authResDtoByToken.getUamUser().getUserName());
            ofcFundamentalInformation.setOperTime(new Date());
            ofcFundamentalInformation.setAbolisher(authResDtoByToken.getUamUser().getUserName());
            ofcFundamentalInformation.setAbolisherName(authResDtoByToken.getUamUser().getUserName());
            ofcFundamentalInformation.setAbolishMark(1);//表明已作废
            ofcFundamentalInformation.setAbolishTime(ofcFundamentalInformation.getOperTime());
            ofcFundamentalInformationService.update(ofcFundamentalInformation);
            return String.valueOf(Wrapper.SUCCESS_CODE);
        }else {
            throw new BusinessException("计划单状态不在可取消范围内");
        }
    }

    @Override
    public CscContantAndCompanyVo getContactMessage(String contactCompanyName, String contactName, String purpose,String custId,AuthResDto authResDtoByToken) {
        Map<String,Object> map = new HashMap<>();
        CscContantAndCompanyDto cscContantAndCompanyDto = new CscContantAndCompanyDto();
        cscContantAndCompanyDto.setCscContact(new CscContact());
        cscContantAndCompanyDto.setCscContactCompany(new CscContactCompany());
        cscContantAndCompanyDto.getCscContact().setPurpose(purpose);
        cscContantAndCompanyDto.getCscContact().setContactName(contactName);
        cscContantAndCompanyDto.getCscContactCompany().setContactCompanyName(contactCompanyName);
        cscContantAndCompanyDto.setCustomerId(custId);
        cscContantAndCompanyDto.setGroupId(authResDtoByToken.getGroupId());
        Wrapper<List<CscContantAndCompanyVo>> listWrapper = null;
        try {
            listWrapper = feignCscCustomerAPIClient.queryCscReceivingInfoList(cscContantAndCompanyDto);
            if(null == listWrapper.getResult()){
                throw new BusinessException("接口返回结果为null");
            }
            if(Wrapper.ERROR_CODE == listWrapper.getCode()){
                throw new BusinessException(listWrapper.getMessage());
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
        CscContantAndCompanyVo cscContantAndCompanyVo = listWrapper.getResult().get(0);
        return cscContantAndCompanyVo;
    }

    @Override
    public CscSupplierInfoDto getSupportMessage(String supportName, String supportContactName,String custId, AuthResDto authResDtoByToken) {
        CscSupplierInfoDto cscSupplierInfoDto = new CscSupplierInfoDto();
        cscSupplierInfoDto.setSupplierName(supportName);
        cscSupplierInfoDto.setContactName(supportContactName);
        cscSupplierInfoDto.setCustomerId(custId);
        Wrapper<List<CscSupplierInfoDto>> listWrapper = null;
        try {
            listWrapper =  feignCscSupplierAPIClient.querySupplierByAttribute(cscSupplierInfoDto);
            if(null == listWrapper.getResult()){
                throw new BusinessException("查询供应商接口返回结果为null");
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
        CscSupplierInfoDto result = listWrapper.getResult().get(0);
        return result;
    }


    /**
     * 去本地数据库拿该订单号的仓库信息
     */
   /* @Override
    public OfcWarehouseInformation getWarehouseMessage(String orderCode) {
        OfcWarehouseInformation ofcWarehouseInformation=ofcWarehouseInformationService.warehouseInformationSelect(orderCode);
        return ofcWarehouseInformation;
    }*/

    /*@Override
    public RmcWarehouse getWarehouseMessage(String warehouseId, String custId) {
        RmcWarehouse rmcWarehouse = new RmcWarehouse();
        rmcWarehouse.setId(warehouseId);
        Wrapper<RmcWarehouse> rmcWarehouseByid = feignRmcWarehouseAPIClient.queryByWarehouseCode(rmcWarehouse);
        RmcWarehouse result = rmcWarehouseByid.getResult();
        if(null == result){
            throw new BusinessException("没有查到仓库的信息!");
        }
        if(String.valueOf(Wrapper.ERROR_CODE).equals(rmcWarehouseByid.getCode())){
            throw new BusinessException("查询仓库信息错误!");
        }
        return result;
    }*/

    @Override
    public String planUpdate(String planCode, String planStatus, String serviceProviderName,String serviceProviderContact,String serviceProviderContactPhone,String userName) {
        OfcTraplanSourceStatus ofcTraplanSourceStatus=new OfcTraplanSourceStatus();
        if(!PubUtils.trimAndNullAsEmpty(planCode).equals("")){
            String[] planCodeList=planCode.split("@");
            List<OfcTransplanInfo> ofcTransplanInfoList = new ArrayList<>();
            try {
                for(int i=0;i<planCodeList.length;i++){
                    ofcTraplanSourceStatus.setPlanCode(planCodeList[i]);
                    ofcTraplanSourceStatus.setResourceAllocationStatus(planStatus);
                    if(planStatus.equals("40")){
                        OfcTransplanInfo ofcTransplanInfo=ofcTransplanInfoService.selectByKey(planCodeList[i]);
                        ofcTransplanInfoList.add(ofcTransplanInfo);
                        String businessType=PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getBusinessType());
                        if(businessType.equals("600") || businessType.equals("601")){
                            OrderScreenCondition condition=new OrderScreenCondition();
                            condition.setOrderCode(ofcTransplanInfo.getOrderCode());
                            String status=ofcOrderScreenService.orderScreen(condition).get(0).getOrderStatus();
                            if(PubUtils.trimAndNullAsEmpty(status).equals("")){
                                throw new BusinessException("订单状态更新异常！");
                            }else if(!status.equals(ALREADYEXAMINE)
                                    && ofcSiloprogramInfoService.ofcSiloprogramInfoScreenList(ofcTransplanInfo.getOrderCode()).size()==0){
                                throw new BusinessException("订单状态异常，无法变更为执行中");
                            }else {
                                OfcOrderStatus ofcOrderStatus=new OfcOrderStatus();
                                ofcOrderStatus.setOrderCode(ofcTransplanInfo.getOrderCode());
                                ofcOrderStatus.setOrderStatus(IMPLEMENTATIONIN);
                                ofcOrderStatus.setStatusDesc("执行中");
                                ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                                        +" "+"订单开始执行");
                                ofcOrderStatus.setOperator(userName);
                                ofcOrderStatus.setLastedOperTime(new Date());
                                ofcOrderStatusService.save(ofcOrderStatus);
                            }
                        }
                        ofcTraplanSourceStatus.setResourceConfirmation(userName);
                        ofcTraplanSourceStatus.setResourceConfirmationTime(new Date());

                    }
                    if(!PubUtils.trimAndNullAsEmpty(serviceProviderName).equals("")){
                        ofcTraplanSourceStatus.setServiceProviderName(serviceProviderName);
                    }
                    if(!PubUtils.trimAndNullAsEmpty(serviceProviderContact).equals("")){
                        ofcTraplanSourceStatus.setServiceProviderContact(serviceProviderContact);
                    }
                    if(!PubUtils.trimAndNullAsEmpty(serviceProviderContactPhone).equals("")){
                        ofcTraplanSourceStatus.setServiceProviderContactPhone(serviceProviderContactPhone);
                    }
                    ofcTraplanSourceStatusService.updateByPlanCode(ofcTraplanSourceStatus);
                }
                //向TFC推送
                ofcTransplanInfoToTfc(ofcTransplanInfoList);
            } catch (Exception ex) {
                throw new BusinessException("计划单状态更新异常！"+ex.getMessage());
            }
        }else {
            throw new BusinessException("缺少计划单编号");
        }
        return null;
    }

    @Override
    public Wrapper<List<RmcCompanyLineVo>> companySelByApi(RmcCompanyLineQO rmcCompanyLineQO) {
        Wrapper<List<RmcCompanyLineVo>> rmcCompanyLists=new Wrapper<List<RmcCompanyLineVo>>();
        try{
            rmcCompanyLists = feignRmcCompanyAPIClient.queryCompanyLine(rmcCompanyLineQO);
        }catch (Exception ex){
            throw new BusinessException("服务商查询出错");
        }
        return rmcCompanyLists;
    }

    public void ofcTransplanInfoToTfc(List<OfcTransplanInfo> ofcTransplanInfoList) {
        List<TransportDTO> transportDTOList = new ArrayList<>();
        try{
            for(OfcTransplanInfo ofcTransplanInfo : ofcTransplanInfoList){
                TransportDTO transportDTO = new TransportDTO();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                transportDTO.setTransportNo(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getPlanCode()));//运输单号
                transportDTO.setCreateTime(PubUtils.trimAndNullAsEmpty(simpleDateFormat.format(ofcTransplanInfo.getCreationTime())));//运输单生成时间
                transportDTO.setBillType(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getBusinessType()));//运输单类型
                transportDTO.setCustomerCode(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getCustCode()));//客户编码(委托方代码)
//            transportDTO.setCustomerName(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getCustName()));//客户名称（委托方名称）
                transportDTO.setCustomerTel(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getCustomerContactPhone()));//客户联系方式
                transportDTO.setFromTransportName(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getBaseId()));//运输单产生机构
                transportDTO.setExpectedShipmentTime(PubUtils.trimAndNullAsEmpty(simpleDateFormat.format(ofcTransplanInfo.getPickupTime())));//预计发货时间
                transportDTO.setExpectedArriveTime(PubUtils.trimAndNullAsEmpty(simpleDateFormat.format(ofcTransplanInfo.getExpectedArrivedTime())));//预计到达时间
                if(!PubUtils.isOEmptyOrNull(ofcTransplanInfo.getWeight())){
                    transportDTO.setWeight(ofcTransplanInfo.getWeight().doubleValue());//重量
                }
                if(!PubUtils.isOEmptyOrNull(ofcTransplanInfo.getQuantity())){
                    transportDTO.setQty(ofcTransplanInfo.getQuantity().doubleValue());//数量
                }
                if(!PubUtils.isOEmptyOrNull(ofcTransplanInfo.getVolume())){
                    transportDTO.setVolume(ofcTransplanInfo.getVolume().doubleValue());//体积
                }
                if(!PubUtils.isOEmptyOrNull(ofcTransplanInfo.getMoney())){
                    transportDTO.setMoney(ofcTransplanInfo.getMoney().doubleValue());//金额
                }
                transportDTO.setFromCustomerCode(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getShippinCustomerCode()));//发货客户代码
//            transportDTO.setFromCustomerName(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getShippinCustomerName()));//发货客户名称
                transportDTO.setFromCustomerAddress(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getShippingAddress()));//发货客户地址
                transportDTO.setFromCustomer(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getShippingCustomerContact()));//发货客户联系人
                transportDTO.setFromCustomerTle(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getCustomerContactPhone()));//发货客户联系电话
                transportDTO.setFromProvince(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDepartureProvince()));//出发省份
                transportDTO.setFromCity(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDepartureCity()));//出发城市
                transportDTO.setFromDistrict(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDepartureDistrict()));//出发区县
                transportDTO.setFromTown(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDestinationTown()));//出发乡镇
                transportDTO.setToCustomerCode(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getReceivingCustomerCode()));//收货客户代码
//            transportDTO.setToCustomerName(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getReceivingCustomerName()));//收货客户名称
                transportDTO.setToCustomerAddress(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getReceivingCustomerAddress()));//收货客户地址
                transportDTO.setToCustomer(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getReceivingCustomerContact()));//收货客户联系人
                transportDTO.setToCustomerTle(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getReceivingCustomerContactPhone()));//收货客户联系电话
                transportDTO.setToProvince(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDestinationProvince()));//目的省份
                transportDTO.setToCity(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDestinationCity()));//目的城市
                transportDTO.setToDistrict(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDestinationDistrict()));//目的区县
                transportDTO.setToTown(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDestinationTown()));//目的乡镇
                transportDTO.setToLon(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getReceivingAddressLongitude()));//收货地址经度
                transportDTO.setToLat(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getReceivingAddressLatitude()));//收货地址纬度
                transportDTO.setNotes(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getNotes()));//备注
                transportDTO.setMarketOrg(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getProductGroup()));//产品组
                transportDTO.setMarketDep(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getSaleDepartment()));//销售部门
                transportDTO.setMarketTeam(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getSaleGroup()));//销售组
                transportDTO.setMarketDepDes(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getSaleDepartmentDesc()));//销售部门描述
                transportDTO.setMarketTeamDes(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getSaleGroupDesc()));//销售组描述
                transportDTO.setTransportSource(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getSingleSourceOfTransport()));//运输单来源
                OfcPlannedDetail ofcPlannedDetail = new OfcPlannedDetail();
                ofcPlannedDetail.setPlanCode(ofcTransplanInfo.getPlanCode());
                List<OfcPlannedDetail> ofcPlannedDetailList = ofcPlannedDetailService.select(ofcPlannedDetail);
                for(OfcPlannedDetail detail : ofcPlannedDetailList){
                    TransportDetailDTO transportDetailDTO = new TransportDetailDTO();
                    transportDetailDTO.setTransportNo(detail.getPlanCode());
                    transportDetailDTO.setItemCode(detail.getGoodsCode());
                    transportDetailDTO.setItemName(detail.getGoodsName());
                    transportDetailDTO.setQty(detail.getQuantity().doubleValue());
                    transportDetailDTO.setUom(detail.getUnit());
                    transportDetailDTO.setStandard(detail.getGoodsSpec());
                    transportDTO.getProductDetail().add(transportDetailDTO);
                }
                transportDTOList.add(transportDTO);
                String json = JacksonUtil.toJsonWithFormat(transportDTO);
                //defaultMqProducer.sendTransactionalMQ(json,ofcTransplanInfo.getPlanCode());

                OfcTransplanStatus ofcTransplanStatus = new OfcTransplanStatus();
                ofcTransplanStatus.setPlanCode(ofcTransplanInfo.getPlanCode());
                ofcTransplanStatus.setPlannedSingleState(OrderConstEnum.YITUISONG);
                ofcTransplanStatusService.updateByPlanCode(ofcTransplanStatus);
                defaultMqProducer.toSendMQ(json,ofcTransplanInfo.getPlanCode());
            }
        }catch (Exception ex){
            throw new BusinessException("OFC推送TFC运输订单异常"+ex.getMessage());
        }
    }
}