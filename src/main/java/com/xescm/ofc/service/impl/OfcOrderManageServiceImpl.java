package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.model.dto.csc.CscContantAndCompanyDto;
import com.xescm.ofc.model.dto.csc.CscSupplierInfoDto;
import com.xescm.ofc.model.dto.csc.domain.CscContact;
import com.xescm.ofc.model.dto.csc.domain.CscContactCompany;
import com.xescm.ofc.model.vo.csc.CscContantAndCompanyVo;
import com.xescm.ofc.domain.OrderScreenCondition;
import com.xescm.ofc.model.dto.rmc.RmcCompanyLineQO;
import com.xescm.ofc.model.vo.ofc.OfcSiloprogramInfoVo;
import com.xescm.ofc.model.vo.rmc.RmcCompanyLineVo;
import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.csc.FeignCscCustomerAPI;
import com.xescm.ofc.feign.client.*;
import com.xescm.ofc.model.dto.tfc.TransportDTO;
import com.xescm.ofc.model.dto.tfc.TransportDetailDTO;
import com.xescm.ofc.model.dto.tfc.TransportNoDTO;
import com.xescm.ofc.model.dto.wms.*;
import com.xescm.ofc.mq.producer.DefaultMqProducer;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.*;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
//import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.xescm.ofc.constant.OrderConstConstant.ALREADYEXAMINE;
import static com.xescm.ofc.constant.OrderConstConstant.IMPLEMENTATIONIN;

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
    private FeignTfcTransPlanApiClient feignTfcTransPlanApiClient;
    @Autowired
    private FeignCscCustomerAPIClient feignCscCustomerAPIClient;
    @Autowired
    private FeignOfcDistributionAPIClient feignOfcDistributionAPIClient;
    @Autowired
    private DefaultMqProducer defaultMqProducer;

    @Override
    public String orderAudit(String orderCode,String orderStatus, String reviewTag, AuthResDto authResDtoByToken) {
        OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
        ofcOrderStatus.setOrderCode(orderCode);
        ofcOrderStatus.setOrderStatus(orderStatus);
        logger.debug(ofcOrderStatus.toString());
        if((!ofcOrderStatus.getOrderStatus().equals(IMPLEMENTATIONIN))
                && (!ofcOrderStatus.getOrderStatus().equals(OrderConstConstant.HASBEENCOMPLETED))
                && (!ofcOrderStatus.getOrderStatus().equals(OrderConstConstant.HASBEENCANCELED))){
            if (ofcOrderStatus.getOrderStatus().equals(ALREADYEXAMINE)&&reviewTag.equals("rereview")){
                planCancle(orderCode,authResDtoByToken.getUamUser().getUserName());


                logger.debug("作废计划单完成");
                ofcOrderStatus.setOrderStatus(OrderConstConstant.PENDINGAUDIT);
                ofcOrderStatus.setStatusDesc("反审核");
                ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                        +" "+"订单反审核完成");
                logger.debug("作废计划单");
            }else if(ofcOrderStatus.getOrderStatus().equals(OrderConstConstant.PENDINGAUDIT)&&reviewTag.equals("review")){
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
                if (PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getOrderType()).equals(OrderConstConstant.TRANSPORTORDER)){
                    //运输订单
                    OfcTransplanInfo ofcTransplanInfo=new OfcTransplanInfo();
                    ofcTransplanInfo.setProgramSerialNumber("1");
                    if (!PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(OrderConstConstant.WITHTHEKABAN)){
                        transPlanCreate(ofcTransplanInfo,ofcFundamentalInformation,goodsDetailsList,ofcDistributionBasicInfo,ofcFundamentalInformation.getCustName());
                    }


                }else if (PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getOrderType()).equals(OrderConstConstant.WAREHOUSEDISTRIBUTIONORDER)){
                    //仓储订单
                    OfcWarehouseInformation ofcWarehouseInformation=ofcWarehouseInformationService.warehouseInformationSelect(orderCode);
                    OfcSiloprogramInfo ofcSiloprogramInfo=new OfcSiloprogramInfo();
                    if (ofcWarehouseInformation.getProvideTransport()== OrderConstConstant.WAREHOUSEORDERPROVIDETRANS){
                        //需要提供运输
                        OfcTransplanInfo ofcTransplanInfo=new OfcTransplanInfo();
                        if (PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(OrderConstConstant.SALESOUTOFTHELIBRARY)
                                || PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(OrderConstConstant.TRANSFEROUTOFTHELIBRARY)
                                || PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(OrderConstConstant.LOSSOFREPORTING)
                                || PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(OrderConstConstant.OTHEROUTOFTHELIBRARY)
                        ){
                            //出库
                            ofcTransplanInfo.setProgramSerialNumber("2");
                            ofcSiloprogramInfo.setProgramSerialNumber("1");

                        }else if (PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(OrderConstConstant.PURCHASINGANDSTORAGE)
                                || PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(OrderConstConstant.ALLOCATESTORAGE)
                                || PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(OrderConstConstant.RETURNWAREHOUSING)
                                || PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(OrderConstConstant.PROCESSINGSTORAGE)){
                            //入库
                            ofcTransplanInfo.setProgramSerialNumber("1");
                            ofcSiloprogramInfo.setProgramSerialNumber("2");
                        }
                        transPlanCreate(ofcTransplanInfo,ofcFundamentalInformation,goodsDetailsList,ofcDistributionBasicInfo,ofcFundamentalInformation.getCustName());
                        siloProCreate(ofcSiloprogramInfo,ofcFundamentalInformation,goodsDetailsList,ofcWarehouseInformation,ofcFinanceInformation,authResDtoByToken.getUamUser().getUserName());
                    }else if (ofcWarehouseInformation.getProvideTransport()== OrderConstConstant.WAREHOUSEORDERNOTPROVIDETRANS){
                        //不需要提供运输
                        ofcSiloprogramInfo.setProgramSerialNumber("1");
                        String planCode=siloProCreate(ofcSiloprogramInfo,ofcFundamentalInformation,goodsDetailsList,ofcWarehouseInformation,ofcFinanceInformation,authResDtoByToken.getUamUser().getUserName());
                        //仓储计划单生成以后通过MQ推送到仓储中心
                        OfcSiloprogramInfoVo info= ofcSiloprogramInfoService.ofcSiloprogramAndResourceInfo(orderCode);
                        List<OfcGoodsDetailsInfo> goodDetailInfos=ofcGoodsDetailsInfoService.goodsDetailsScreenList(orderCode, "orderCode");
                        if(goodDetailInfos!=null&&goodDetailInfos.size()>0){
                        	sendToWhc(info,goodDetailInfos);
                        }else{
                            logger.debug("仓储计划单不存在对应的货物明细");
                        }
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
    public void transPlanCreate(OfcTransplanInfo ofcTransplanInfo,OfcFundamentalInformation ofcFundamentalInformation,List<OfcGoodsDetailsInfo> goodsDetailsList,OfcDistributionBasicInfo ofcDistributionBasicInfo,String userName){
        OfcTraplanSourceStatus ofcTraplanSourceStatus=new OfcTraplanSourceStatus();
        OfcTransplanStatus ofcTransplanStatus=new OfcTransplanStatus();
        OfcTransplanNewstatus ofcTransplanNewstatus=new OfcTransplanNewstatus();
        OfcPlannedDetail ofcPlannedDetail=new OfcPlannedDetail();
        try {
            BeanUtils.copyProperties(ofcTransplanInfo,ofcFundamentalInformation);
            BeanUtils.copyProperties(ofcTransplanInfo,ofcDistributionBasicInfo);
            ofcTransplanInfo.setPlanCode(codeGenUtils.getNewWaterCode("TP",6));
            ofcTransplanInfo.setCreationTime(new Date());
            ofcTransplanInfo.setCreatePersonnel(userName);
            ofcTransplanInfo.setNotes(ofcDistributionBasicInfo.getTransRequire());
            BeanUtils.copyProperties(ofcTraplanSourceStatus,ofcDistributionBasicInfo);//$$$$
            BeanUtils.copyProperties(ofcTraplanSourceStatus,ofcTransplanInfo);
            BeanUtils.copyProperties(ofcTransplanStatus,ofcTransplanInfo);
            BeanUtils.copyProperties(ofcTransplanNewstatus,ofcTransplanInfo);
            ofcTransplanStatus.setPlannedSingleState(OrderConstConstant.ZIYUANFENPEIZ);
            //ofcTransplanNewstatus.setTransportSingleLatestStatus(ofcTransplanStatus.getPlannedSingleState());
            //ofcTransplanNewstatus.setTransportSingleUpdateTime(ofcTransplanInfo.getCreationTime());
            ofcTraplanSourceStatus.setResourceAllocationStatus(OrderConstConstant.DAIFENPEI);
            Iterator<OfcGoodsDetailsInfo> iter = goodsDetailsList.iterator();
            Map<String,List<OfcPlannedDetail>> ofcPlannedDetailMap = new HashMap<>();
            while(iter.hasNext())
            {
                List<OfcPlannedDetail> ofcPlannedDetailList = new ArrayList<>();
                //保存计划单明细
                ofcPlannedDetail.setPlanCode(ofcTransplanInfo.getPlanCode());
                OfcGoodsDetailsInfo ofcGoodsDetailsInfo=iter.next();
                BeanUtils.copyProperties(ofcPlannedDetail,ofcTransplanInfo);
                BeanUtils.copyProperties(ofcPlannedDetail,ofcGoodsDetailsInfo);
                ofcPlannedDetailList.add(ofcPlannedDetail);
                ofcPlannedDetailMap.put(ofcPlannedDetail.getPlanCode(),ofcPlannedDetailList);
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
            Wrapper<List<RmcCompanyLineVo>> companyList = null;
            try{
                companyList = companySelByApi(rmcCompanyLineQO);

            }catch (Exception ex){
                throw new BusinessException(companyList.getMessage());
            }

            if(companyList.getCode()==200
                    && !CollectionUtils.isEmpty(companyList.getResult())){
                /**
                 * 平台类型。1、线下；2、天猫3、京东；4、鲜易网
                 */
                if("4".equals(ofcFundamentalInformation.getPlatformType())){
                    ofcTransplanInfo.setSingleSourceOfTransport("6001");
                }else if(PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getPlatformType())){
                    ofcTransplanInfo.setSingleSourceOfTransport("6003");
                }else{
                    ofcTransplanInfo.setSingleSourceOfTransport("6003");
                }
                if(!PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsignorCode()).equals("")){
                    ofcTransplanInfo.setShippinCustomerCode(ofcDistributionBasicInfo.getConsignorCode());
                }
                if(!PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsignorName()).equals("")){
                    ofcTransplanInfo.setShippinCustomerName(ofcDistributionBasicInfo.getConsignorName());
                }
                if(!PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsignorContactName()).equals("")){
                    ofcTransplanInfo.setShippingCustomerContact(ofcDistributionBasicInfo.getConsignorContactName());
                }
                if(!PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsignorContactPhone()).equals("")){
                    ofcTransplanInfo.setCustomerContactPhone(ofcDistributionBasicInfo.getConsignorContactPhone());
                }
                if(!PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsigneeName()).equals("")){
                    ofcTransplanInfo.setReceivingCustomerName(ofcDistributionBasicInfo.getConsigneeName());
                }
                if(!PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsigneeContactName()).equals("")){
                    ofcTransplanInfo.setReceivingCustomerContact(ofcDistributionBasicInfo.getConsigneeContactName());
                }
                if(!PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getConsigneeContactPhone()).equals("")){
                    ofcTransplanInfo.setReceivingCustomerContactPhone(ofcDistributionBasicInfo.getConsigneeContactPhone());
                }
                if(!PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getDeparturePlace()).equals("")){
                    ofcTransplanInfo.setShippingAddress(ofcDistributionBasicInfo.getDeparturePlace());
                }
                if(!PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getDestination()).equals("")){
                    ofcTransplanInfo.setReceivingCustomerAddress(ofcDistributionBasicInfo.getDestination());
                }
                if(!PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getDestinationTowns()).equals("")){
                    ofcTransplanInfo.setDestinationTown(ofcDistributionBasicInfo.getDestinationTowns());
                }
                if(!PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getCubage()).equals("")){
                    String[] cubage = ofcDistributionBasicInfo.getCubage().split("\\*");
                    BigDecimal volume = BigDecimal.valueOf(Double.valueOf(cubage[0])).multiply(BigDecimal.valueOf(Double.valueOf(cubage[1]))).multiply(BigDecimal.valueOf(Double.valueOf(cubage[2]))).divide(BigDecimal.valueOf(1000000));
                    ofcTransplanInfo.setVolume(volume);//$$$
                }
                if(!PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getCustCode()).equals("")){
                    ofcTransplanInfo.setCustCode(ofcFundamentalInformation.getCustCode());
                }


                RmcCompanyLineVo rmcCompanyLineVo=companyList.getResult().get(0);
                ofcTraplanSourceStatus.setServiceProviderName(rmcCompanyLineVo.getCompanyName());
                ofcTraplanSourceStatus.setServiceProviderContact(rmcCompanyLineVo.getContactName());
                ofcTraplanSourceStatus.setServiceProviderContactPhone(rmcCompanyLineVo.getCompanyPhone());
                ofcTraplanSourceStatus.setResourceAllocationStatus("40");
                ofcTraplanSourceStatus.setResourceConfirmation(ofcFundamentalInformation.getCustName());
                ofcTraplanSourceStatus.setResourceConfirmationTime(new Date());
                List<OfcTransplanInfo> ofcTransplanInfoList = new ArrayList<>();
                ofcTransplanInfoList.add(ofcTransplanInfo);
                String businessType=PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getBusinessType());
                if(businessType.equals("600") || businessType.equals("601")){
                    OrderScreenCondition condition=new OrderScreenCondition();
                    condition.setOrderCode(ofcTransplanInfo.getOrderCode());
                    String status=ofcOrderStatusService.orderStatusSelect(condition.getOrderCode(),"orderCode").getOrderStatus();
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
                ofcTransplanInfoService.save(ofcTransplanInfo);
                logger.debug("计划单信息保存成功");
                ofcTransplanNewstatusService.save(ofcTransplanNewstatus);

                logger.debug("计划单状态保存成功");
                ofcTraplanSourceStatusService.save(ofcTraplanSourceStatus);
                logger.debug("计划单资源状态保存成功");


                if(!PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(OrderConstConstant.WITHTHEKABAN)){
                    //向TFC推送
                    logger.debug("计划单最新状态保存成功");
                    ofcTransplanStatusService.save(ofcTransplanStatus);
                    ofcTransplanInfoToTfc(ofcTransplanInfoList,ofcPlannedDetailMap,userName);
                }else if(PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(OrderConstConstant.WITHTHEKABAN)){
                    //如果是卡班订单,则应该向DMS推送卡班订单
                    //ofcDistributionBasicInfo.setTransCode("kb"+System.currentTimeMillis());
                    String[] cubage = ofcDistributionBasicInfo.getCubage().split("\\*");
                    BigDecimal volume = BigDecimal.valueOf(Double.valueOf(cubage[0])).multiply(BigDecimal.valueOf(Double.valueOf(cubage[1]))).multiply(BigDecimal.valueOf(Double.valueOf(cubage[2])));
                    ofcDistributionBasicInfo.setCubage(volume.toString());
                    Wrapper<?> wrapper = feignOfcDistributionAPIClient.addDistributionBasicInfo(ofcDistributionBasicInfo);
                    if(Wrapper.ERROR_CODE == wrapper.getCode()){
                        throw new BusinessException("向分拣中心推送卡班订单失败");
                    }
                }
            }else{
                if(CollectionUtils.isEmpty(companyList.getResult())){
                    throw new BusinessException("没有查询到相关服务商!");
                }
                throw new BusinessException(companyList.getMessage());
            }


            /*planUpdate(ofcTransplanInfo.getPlanCode(),"40",ofcTraplanSourceStatus.getServiceProviderName()
                    ,ofcTraplanSourceStatus.getServiceProviderContact(),ofcTraplanSourceStatus.getServiceProviderContactPhone(),ofcFundamentalInformation.getCustName());//&&&&&*/

            //更改计划单状态为执行中//###



        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }



    /**
     * 创建仓储计划单
     * @param ofcSiloprogramInfo
     * @param ofcFundamentalInformation
     * @param goodsDetailsList
     * @param ofcWarehouseInformation
     * @param ofcFinanceInformation
     */
    public String siloProCreate(OfcSiloprogramInfo ofcSiloprogramInfo,OfcFundamentalInformation ofcFundamentalInformation,List<OfcGoodsDetailsInfo> goodsDetailsList,OfcWarehouseInformation ofcWarehouseInformation,OfcFinanceInformation ofcFinanceInformation,String userId){
        String planCode="";
        OfcSiloproStatus ofcSiloproStatus=new OfcSiloproStatus();
        OfcSiloproNewstatus ofcSiloproNewstatus=new OfcSiloproNewstatus();
        OfcSiloproSourceStatus ofcSiloproSourceStatus=new OfcSiloproSourceStatus();
        OfcPlannedDetail ofcPlannedDetail=new OfcPlannedDetail();
        try {
            BeanUtils.copyProperties(ofcSiloprogramInfo,ofcWarehouseInformation);
            BeanUtils.copyProperties(ofcSiloprogramInfo,ofcFinanceInformation);
            BeanUtils.copyProperties(ofcSiloprogramInfo,ofcFundamentalInformation);
            ofcSiloprogramInfo.setPlanCode(codeGenUtils.getNewWaterCode("WP",6));
            planCode=ofcSiloprogramInfo.getPlanCode();
            ofcSiloprogramInfo.setDocumentType(ofcSiloprogramInfo.getBusinessType());
            if (PubUtils.trimAndNullAsEmpty(ofcSiloprogramInfo.getDocumentType()).equals(OrderConstConstant.SALESOUTOFTHELIBRARY)
                    || PubUtils.trimAndNullAsEmpty(ofcSiloprogramInfo.getDocumentType()).equals(OrderConstConstant.TRANSFEROUTOFTHELIBRARY)
                    || PubUtils.trimAndNullAsEmpty(ofcSiloprogramInfo.getDocumentType()).equals(OrderConstConstant.LOSSOFREPORTING)
                    || PubUtils.trimAndNullAsEmpty(ofcSiloprogramInfo.getDocumentType()).equals(OrderConstConstant.OTHEROUTOFTHELIBRARY)
                    ){
                //出库
                ofcSiloprogramInfo.setBusinessType("出库");

            }else if (PubUtils.trimAndNullAsEmpty(ofcSiloprogramInfo.getDocumentType()).equals(OrderConstConstant.PURCHASINGANDSTORAGE)
                    || PubUtils.trimAndNullAsEmpty(ofcSiloprogramInfo.getDocumentType()).equals(OrderConstConstant.ALLOCATESTORAGE)
                    || PubUtils.trimAndNullAsEmpty(ofcSiloprogramInfo.getDocumentType()).equals(OrderConstConstant.RETURNWAREHOUSING)
                    || PubUtils.trimAndNullAsEmpty(ofcSiloprogramInfo.getDocumentType()).equals(OrderConstConstant.PROCESSINGSTORAGE)){
                //入库
                ofcSiloprogramInfo.setBusinessType("入库");
            }
            ofcSiloprogramInfo.setCreationTime(new Date());
            ofcSiloprogramInfo.setCreatePersonnel(userId);
            BeanUtils.copyProperties(ofcSiloproSourceStatus,ofcWarehouseInformation);
            BeanUtils.copyProperties(ofcSiloproSourceStatus,ofcSiloprogramInfo);
            BeanUtils.copyProperties(ofcSiloproStatus,ofcSiloprogramInfo);
            BeanUtils.copyProperties(ofcSiloproNewstatus,ofcSiloprogramInfo);
            ofcSiloproStatus.setPlannedSingleState(OrderConstConstant.ZIYUANFENPEIZ);
            //ofcSiloproNewstatus.setJobNewStatus(ofcSiloproStatus.getPlannedSingleState());
            //ofcSiloproNewstatus.setJobStatusUpdateTime(ofcSiloprogramInfo.getCreationTime());
            ofcSiloproSourceStatus.setResourceAllocationStatus(OrderConstConstant.YIQUEDING);
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
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return planCode;
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
                    logger.error("TMS已经在执行,您无法取消,请联系管理员!{}",response.getMessage());
                    logger.error("TMS已经在执行,您无法取消,请联系管理员!{}",response.getResult());
                    throw new BusinessException("TMS已经在执行,您无法取消,请联系管理员!");
                }
            }
            catch (Exception ex){
                logger.error("运输计划单调用TFC取消端口出现异常{}",ex.getMessage());
                throw new BusinessException(ex.getMessage());
            }
            OfcTransplanStatus ofcTransplanStatus=new OfcTransplanStatus();
            ofcTransplanStatus.setPlanCode(ofcTransplanInfo.getPlanCode());
            ofcTransplanStatus=ofcTransplanStatusService.selectOne(ofcTransplanStatus);
            if(PubUtils.trimAndNullAsEmpty(ofcTransplanStatus.getPlannedSingleState()).equals(OrderConstConstant.YIZUOFEI)){
                throw new BusinessException("状态错误，该计划单已作废");
            }else if (PubUtils.trimAndNullAsEmpty(ofcTransplanStatus.getPlannedSingleState()).equals(OrderConstConstant.RENWUZHONG)
                    || PubUtils.trimAndNullAsEmpty(ofcTransplanStatus.getPlannedSingleState()).equals(OrderConstConstant.RENWUWANCH)){
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
            if(PubUtils.trimAndNullAsEmpty(ofcSiloproStatus.getPlannedSingleState()).equals(OrderConstConstant.YIZUOFEI)){
                throw new BusinessException("状态错误，该计划单已作废");
            }else if (PubUtils.trimAndNullAsEmpty(ofcSiloproStatus.getPlannedSingleState()).equals(OrderConstConstant.RENWUZHONG)
                    || PubUtils.trimAndNullAsEmpty(ofcSiloproStatus.getPlannedSingleState()).equals(OrderConstConstant.RENWUWANCH)){
                throw new BusinessException("该订单状态已在作业中或已完成，无法取消");
            }else if (PubUtils.trimAndNullAsEmpty(ofcSiloproStatus.getPlannedSingleState()).equals(OrderConstConstant.YITUISONG)){
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
        if(orderStatus.equals(OrderConstConstant.PENDINGAUDIT)){
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
        if((!PubUtils.trimAndNullAsEmpty(orderStatus).equals(OrderConstConstant.PENDINGAUDIT))
                && (!PubUtils.trimAndNullAsEmpty(orderStatus).equals(OrderConstConstant.HASBEENCOMPLETED))
                && (!PubUtils.trimAndNullAsEmpty(orderStatus).equals(OrderConstConstant.HASBEENCANCELED))){
            planCancle(orderCode,authResDtoByToken.getUamUser().getUserName());
            OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
            ofcOrderStatus.setOrderCode(orderCode);
            ofcOrderStatus.setOrderStatus(OrderConstConstant.HASBEENCANCELED);
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
        //Map<String,Object> map = new HashMap<String,Object>();
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
            if(OrderConstConstant.CONTACTPURPOSECONSIGNOR.equals(purpose)){
                throw new BusinessException("没有查到该发货人的信息!");
            }else if (OrderConstConstant.CONTACTPURPOSECONSIGNEE.equals(purpose)){
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

    /**
     * 计划单状态自动更新
     */

    /*private Wrapper<?> autoPlanUpdate*/

    /**
     * 计划单更新
     * @param planCode
     * @param planStatus
     * @param serviceProviderName
     * @param serviceProviderContact
     * @param serviceProviderContactPhone
     * @param userName
     * @return
     */
    @Override
    public String planUpdate(String planCode, String planStatus, String serviceProviderName,String serviceProviderContact,String serviceProviderContactPhone,String userName) {
        OfcTraplanSourceStatus ofcTraplanSourceStatus=new OfcTraplanSourceStatus();
        if(!PubUtils.trimAndNullAsEmpty(planCode).equals("")){
            String[] planCodeList=planCode.split("@");
            //List<OfcTransplanInfo> ofcTransplanInfoList = new ArrayList<OfcTransplanInfo>();
            List<OfcTransplanInfo> ofcTransplanInfoList = new ArrayList<>();
            Map<String,List<OfcPlannedDetail>> ofcPlannedDetailMap = new HashMap<>();
            try {
                for(int i=0;i<planCodeList.length;i++){
                    ofcTraplanSourceStatus.setPlanCode(planCodeList[i]);

                    OfcPlannedDetail ofcPlannedDetail = new OfcPlannedDetail();
                    ofcPlannedDetail.setPlanCode(ofcTraplanSourceStatus.getPlanCode());
                    List<OfcPlannedDetail> ofcPlannedDetailList = ofcPlannedDetailService.select(ofcPlannedDetail);
                    ofcPlannedDetailMap.put(ofcTraplanSourceStatus.getPlanCode(),ofcPlannedDetailList);
                    ofcTraplanSourceStatus.setResourceAllocationStatus(planStatus);
                    if(planStatus.equals("40")){
                        OfcTransplanInfo ofcTransplanInfo=ofcTransplanInfoService.selectByKey(planCodeList[i]);
                        ofcTransplanInfoList.add(ofcTransplanInfo);
                        String businessType=PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getBusinessType());
                        if(businessType.equals("600") || businessType.equals("601")){
                            OrderScreenCondition condition=new OrderScreenCondition();
                            condition.setOrderCode(ofcTransplanInfo.getOrderCode());
                            String status=ofcOrderStatusService.orderStatusSelect(condition.getOrderCode(),"orderCode").getOrderStatus();
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


                ofcTransplanInfoToTfc(ofcTransplanInfoList,ofcPlannedDetailMap,userName);
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


    /**
     * OFC向TFC推送运输计划单(卡班除外)
     * @param ofcTransplanInfoList
     * @param ofcPlannedDetailMap
     * @param userName
     */
    public void ofcTransplanInfoToTfc(List<OfcTransplanInfo> ofcTransplanInfoList, Map<String,List<OfcPlannedDetail>> ofcPlannedDetailMap,String userName) {
       // List<TransportDTO> transportDTOList = new ArrayList<TransportDTO>();
        List<TransportDTO> transportDTOList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            for(OfcTransplanInfo ofcTransplanInfo : ofcTransplanInfoList){
                TransportDTO transportDTO = new TransportDTO();
                transportDTO.setTransportNo(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getPlanCode()));//运输单号
                transportDTO.setCreateTime(PubUtils.trimAndNullAsEmpty(simpleDateFormat.format(ofcTransplanInfo.getCreationTime())));//运输单生成时间
                transportDTO.setBillType(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getBusinessType()));//运输单类型
                transportDTO.setCustomerCode(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getCustCode()));//客户编码(委托方代码)
                transportDTO.setCustomerName(PubUtils.trimAndNullAsEmpty(userName));//客户名称（委托方名称）
                transportDTO.setCustomerTel(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getCustomerContactPhone()));//客户联系方式
                transportDTO.setFromTransportName(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getBaseId()));//运输单产生机构
                if(!PubUtils.isOEmptyOrNull(ofcTransplanInfo.getPickupTime())){
                    simpleDateFormat.format(ofcTransplanInfo.getPickupTime());
                    transportDTO.setExpectedShipmentTime(PubUtils.trimAndNullAsEmpty(simpleDateFormat.format(ofcTransplanInfo.getPickupTime())));//预计发货时间
                }
                if(!PubUtils.isOEmptyOrNull(ofcTransplanInfo.getExpectedArrivedTime())){
                    transportDTO.setExpectedArriveTime(PubUtils.trimAndNullAsEmpty(simpleDateFormat.format(ofcTransplanInfo.getExpectedArrivedTime())));//预计到达时间//$$$
                }
                if(!PubUtils.isOEmptyOrNull(ofcTransplanInfo.getWeight())){
                    transportDTO.setWeight(ofcTransplanInfo.getWeight().doubleValue());//重量
                }else {
                    transportDTO.setWeight(0.0);
                }
                if(!PubUtils.isOEmptyOrNull(ofcTransplanInfo.getQuantity())){
                    transportDTO.setQty(ofcTransplanInfo.getQuantity().doubleValue());//数量
                }else {
                    transportDTO.setQty(0.0);
                }
                if(!PubUtils.isOEmptyOrNull(ofcTransplanInfo.getVolume())){
                    transportDTO.setVolume(ofcTransplanInfo.getVolume().doubleValue());//体积
                }else {
                    transportDTO.setVolume(0.0);
                }
                if(!PubUtils.isOEmptyOrNull(ofcTransplanInfo.getMoney())){
                    transportDTO.setMoney(ofcTransplanInfo.getMoney().doubleValue());//金额
                }else {
                    transportDTO.setMoney(0.0);
                }
                transportDTO.setFromCustomerCode(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getShippinCustomerCode()));//发货客户代码
                transportDTO.setFromCustomerName(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getShippinCustomerName()));//发货客户名称
                transportDTO.setFromCustomerAddress(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDepartureProvince()
                        + ofcTransplanInfo.getDepartureCity()
                        + ofcTransplanInfo.getDepartureDistrict()
                        + ofcTransplanInfo.getDepartureTowns()+ofcTransplanInfo.getShippingAddress()).replace("null",""));//发货客户地址
                transportDTO.setFromCustomer(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getShippingCustomerContact()));//发货客户联系人
                transportDTO.setFromCustomerTle(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getCustomerContactPhone()));//发货客户联系电话
                transportDTO.setFromProvince(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDepartureProvince()).replace("null",""));//出发省份
                transportDTO.setFromCity(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDepartureCity()).replace("null",""));//出发城市
                transportDTO.setFromDistrict(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDepartureDistrict()).replace("null",""));//出发区县
                transportDTO.setFromTown(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDepartureTowns()).replace("null",""));//出发街道
//                transportDTO.setFromCustomerAddress(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDepartureTowns()+ofcTransplanInfo.getDe));
                transportDTO.setToCustomerCode(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getReceivingCustomerCode()));//收货客户代码
                transportDTO.setToCustomerName(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getReceivingCustomerName()));//收货客户名称
                transportDTO.setToCustomerAddress(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDestinationProvince()
                        + ofcTransplanInfo.getDestinationCity()
                        + ofcTransplanInfo.getDestinationDistrict()
                        + ofcTransplanInfo.getDestinationTown()+ofcTransplanInfo.getReceivingCustomerAddress()).replace("null",""));//收货客户地址
                transportDTO.setToCustomer(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getReceivingCustomerContact()));//收货客户联系人
                transportDTO.setToCustomerTle(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getReceivingCustomerContactPhone()));//收货客户联系电话
                transportDTO.setToProvince(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDestinationProvince()).replace("null",""));//目的省份
                transportDTO.setToCity(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDestinationCity()).replace("null",""));//目的城市
                transportDTO.setToDistrict(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDestinationDistrict()).replace("null",""));//目的区县
                transportDTO.setToTown(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getDestinationTown()).replace("null",""));//目的区县
                transportDTO.setToLon(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getReceivingAddressLongitude()));//收货地址经度
                transportDTO.setToLat(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getReceivingAddressLatitude()));//收货地址纬度
                transportDTO.setNotes(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getNotes()));//备注
                transportDTO.setMarketOrg(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getProductGroup()));//产品组
                transportDTO.setMarketDep(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getSaleDepartment()));//销售部门
                transportDTO.setMarketTeam(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getSaleGroup()));//销售组
                transportDTO.setMarketDepDes(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getSaleDepartmentDesc()));//销售部门描述
                transportDTO.setMarketTeamDes(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getSaleGroupDesc()));//销售组描述
                transportDTO.setTransportSource(PubUtils.trimAndNullAsEmpty(ofcTransplanInfo.getSingleSourceOfTransport()));//运输单来源
                //OfcPlannedDetail ofcPlannedDetail = new OfcPlannedDetail();
                //ofcPlannedDetail.setPlanCode(ofcTransplanInfo.getPlanCode());
                List<OfcPlannedDetail> ofcPlannedDetailList = ofcPlannedDetailMap.get(ofcTransplanInfo.getPlanCode());
                for(OfcPlannedDetail detail : ofcPlannedDetailList){
                    TransportDetailDTO transportDetailDTO = new TransportDetailDTO();
                    transportDetailDTO.setTransportNo(detail.getPlanCode());
                    transportDetailDTO.setItemCode(detail.getGoodsCode());
                    transportDetailDTO.setItemName(detail.getGoodsName());
                    if(null == detail.getQuantity()){
                        transportDetailDTO.setQty(0.0);
                    }else{
                        transportDetailDTO.setQty(detail.getQuantity().doubleValue());
                    }
                    if(null == detail.getWeight()){
                        transportDetailDTO.setWeight(0.0);
                    }else{
                        transportDetailDTO.setWeight(detail.getWeight().doubleValue());
                    }
                    if(null == detail.getCubage()){
                        transportDetailDTO.setVolume(0.0);
                    }else{
                        transportDetailDTO.setVolume(detail.getCubage().doubleValue());
                    }
                    if(null == detail.getUnitPrice()){
                        transportDetailDTO.setPrice(0.0);
                    }else{
                        transportDetailDTO.setPrice(detail.getUnitPrice().doubleValue());
                    }
                    transportDetailDTO.setMoney(0.0);
                    transportDetailDTO.setUom(detail.getUnit());
                    if(null == detail.getTotalBox()){
                        detail.setTotalBox(0);
                    }else{
                        detail.setTotalBox(detail.getTotalBox());
                    }
                    transportDetailDTO.setContainerQty(detail.getTotalBox().toString());
                    transportDetailDTO.setStandard(PubUtils.trimAndNullAsEmpty(detail.getGoodsSpec()));
                    transportDTO.getProductDetail().add(transportDetailDTO);
                }
                transportDTOList.add(transportDTO);
                String json = JacksonUtil.toJsonWithFormat(transportDTO);
                defaultMqProducer.toSendTfcTransPlanMQ(json,ofcTransplanInfo.getPlanCode());   ///####
                OfcTransplanStatus ofcTransplanStatus = new OfcTransplanStatus();
                ofcTransplanStatus.setPlanCode(ofcTransplanInfo.getPlanCode());
                ofcTransplanStatus.setPlannedSingleState(OrderConstConstant.YITUISONG);

                ofcTransplanStatusService.updateByPlanCode(ofcTransplanStatus);//$$$
            }
        }catch (Exception ex){
            throw new BusinessException("OFC推送TFC运输订单异常"+ex.getMessage(),ex);
        }
    }

    @Override
    public String orderAuditByTrans(OfcFundamentalInformation ofcFundamentalInformation,List<OfcGoodsDetailsInfo> goodsDetailsList,
                                    OfcDistributionBasicInfo ofcDistributionBasicInfo,OfcFinanceInformation ofcFinanceInformation,
                                    String orderStatus, String reviewTag, AuthResDto authResDtoByToken) {
        OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
        ofcOrderStatus.setOrderCode(ofcDistributionBasicInfo.getOrderCode());
        ofcOrderStatus.setOrderStatus(orderStatus);
        logger.debug(ofcOrderStatus.toString());
        if((!ofcOrderStatus.getOrderStatus().equals(IMPLEMENTATIONIN))
                && (!ofcOrderStatus.getOrderStatus().equals(OrderConstConstant.HASBEENCOMPLETED))
                && (!ofcOrderStatus.getOrderStatus().equals(OrderConstConstant.HASBEENCANCELED))){
            if(ofcOrderStatus.getOrderStatus().equals(OrderConstConstant.PENDINGAUDIT)&&reviewTag.equals("review")){
                ofcOrderStatus.setOrderStatus(ALREADYEXAMINE);
                ofcOrderStatus.setStatusDesc("已审核");
                ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                        +" "+"订单审核完成");
                ofcOrderStatus.setOperator(authResDtoByToken.getUamUser().getUserName());
                ofcOrderStatus.setLastedOperTime(new Date());
                ofcOrderStatus.setOperator(authResDtoByToken.getUamUser().getUserName());
                ofcOrderStatus.setLastedOperTime(new Date());
                ofcOrderStatusService.save(ofcOrderStatus);
                ofcFundamentalInformation.setOperator(authResDtoByToken.getUamUser().getUserName());
                ofcFundamentalInformation.setOperatorName(authResDtoByToken.getUamUser().getUserName());
                ofcFundamentalInformation.setOperTime(new Date());
                if (PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getOrderType()).equals(OrderConstConstant.TRANSPORTORDER)){
                    //运输订单
                    OfcTransplanInfo ofcTransplanInfo=new OfcTransplanInfo();
                    ofcTransplanInfo.setProgramSerialNumber("1");
                    transPlanCreate(ofcTransplanInfo,ofcFundamentalInformation,goodsDetailsList,ofcDistributionBasicInfo,ofcFundamentalInformation.getCustName());
                }else {
                    throw new BusinessException("订单类型有误");
                }
                logger.debug("计划单创建成功");
            }else {
                throw new BusinessException("缺少标志位");
            }

            return String.valueOf(Wrapper.SUCCESS_CODE);
        }else {
            throw new BusinessException("订单类型既非”已审核“，也非”未审核“，请检查");
        }
    }

    /**
     * 自动审核
     * @param ofcFundamentalInformation
     * @param goodsDetailsList
     * @param ofcDistributionBasicInfo
     * @param ofcWarehouseInformation
     * @param ofcFinanceInformation
     * @param orderStatus
     * @param reviewTag
     * @param authResDtoByToken
     * @return
     */
    @Override
    public Wrapper<?> orderAutoAuditFromOperation(OfcFundamentalInformation ofcFundamentalInformation,
                                                 List<OfcGoodsDetailsInfo> goodsDetailsList,
                                                 OfcDistributionBasicInfo ofcDistributionBasicInfo,
                                                 OfcWarehouseInformation ofcWarehouseInformation,
                                                 OfcFinanceInformation ofcFinanceInformation,
                                                 String orderStatus, String reviewTag, AuthResDto authResDtoByToken) {
        OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
        ofcOrderStatus.setOrderCode(ofcFundamentalInformation.getOrderCode());
        ofcOrderStatus.setOrderStatus(orderStatus);
        logger.debug(ofcOrderStatus.toString());
        if((!ofcOrderStatus.getOrderStatus().equals(IMPLEMENTATIONIN))
                && (!ofcOrderStatus.getOrderStatus().equals(OrderConstConstant.HASBEENCOMPLETED))
                && (!ofcOrderStatus.getOrderStatus().equals(OrderConstConstant.HASBEENCANCELED))){
            if(ofcOrderStatus.getOrderStatus().equals(OrderConstConstant.PENDINGAUDIT)&&reviewTag.equals("review")){
                ofcOrderStatus.setOrderStatus(ALREADYEXAMINE);
                ofcOrderStatus.setStatusDesc("已审核");
                ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                        +" "+"订单审核完成");
                ofcOrderStatus.setOperator(authResDtoByToken.getUamUser().getUserName());
                ofcOrderStatus.setLastedOperTime(new Date());
                ofcOrderStatus.setOperator(authResDtoByToken.getUamUser().getUserName());
                ofcOrderStatus.setLastedOperTime(new Date());
                // ofcOrderStatus.setOrderCode(ofcFundamentalInformation.getOrderCode());
                ofcOrderStatusService.save(ofcOrderStatus);
                ofcFundamentalInformation.setOperator(authResDtoByToken.getUamUser().getUserName());
                ofcFundamentalInformation.setOperatorName(authResDtoByToken.getUamUser().getUserName());
                ofcFundamentalInformation.setOperTime(new Date());
                if (PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getOrderType()).equals(OrderConstConstant.TRANSPORTORDER)){
                    //运输订单
                    OfcTransplanInfo ofcTransplanInfo=new OfcTransplanInfo();
                    ofcTransplanInfo.setProgramSerialNumber("1");
                    if (!PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(OrderConstConstant.WITHTHEKABAN)){//在城配下单这边没有卡班
                        transPlanCreate(ofcTransplanInfo,ofcFundamentalInformation,goodsDetailsList,ofcDistributionBasicInfo,ofcFundamentalInformation.getCustName());
                    }
                }else if(PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getOrderType()).equals(OrderConstConstant.WAREHOUSEDISTRIBUTIONORDER)
                        && PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).equals(OrderConstConstant.SALESOUTOFTHELIBRARY)
                        && ofcWarehouseInformation.getProvideTransport() == OrderConstConstant.WAREHOUSEORDERPROVIDETRANS){
                    //000创建仓储计划单
                    OfcSiloprogramInfo ofcSiloprogramInfo=new OfcSiloprogramInfo();
                    ofcSiloprogramInfo.setProgramSerialNumber("1");
                    siloProCreate(ofcSiloprogramInfo,ofcFundamentalInformation,goodsDetailsList,ofcWarehouseInformation,ofcFinanceInformation,authResDtoByToken.getUamUser().getUserName());

                    if(ofcWarehouseInformation.getProvideTransport()== OrderConstConstant.WAREHOUSEORDERPROVIDETRANS){
                        OfcTransplanInfo ofcTransplanInfo=new OfcTransplanInfo();
                        ofcTransplanInfo.setProgramSerialNumber("1");
                        transPlanCreate(ofcTransplanInfo,ofcFundamentalInformation,goodsDetailsList,ofcDistributionBasicInfo,ofcFundamentalInformation.getCustName());
                    }
                }
                else {
                    throw new BusinessException("订单类型有误");
                }
                logger.debug("计划单创建成功");
            }else {
                throw new BusinessException("缺少标志位");
            }

            //return String.valueOf(Wrapper.SUCCESS_CODE);
        }else {
            throw new BusinessException("订单类型既非”已审核“，也非”未审核“，请检查");
        }

        return WrapMapper.wrap(Wrapper.SUCCESS_CODE);
    }


    /**
     * 发送到仓储中心
     * @param info
     * @param goodDetails
     */
    public void sendToWhc(OfcSiloprogramInfoVo info,List<OfcGoodsDetailsInfo> goodDetails){
    	String documentType=info.getDocumentType();
    	String tag="";
    	String jsonStr="";
    	if(OrderConstConstant.OFC_WHC_OUT_TYPE.equals(info.getBusinessType())){
    		if( OrderConstConstant.SALESOUTOFTHELIBRARY.equals(documentType)){
                tag=documentType;
                WhcSalesDelivery wsv=new WhcSalesDelivery();
                List<WhcSalesDeliveryDetails> detailList=new ArrayList<>();
                wsv.setOrderNo(info.getOrderCode());
                wsv.setShipNo(info.getOrderCode());
                wsv.setWhcBillno("");//流水号
                wsv.setOrderType(documentType);//订单类型
                wsv.setShipType(documentType);
                wsv.setFromSystem(OrderConstConstant.ORDER_SOURCE);//订单来源
                wsv.setCustomerCode(PubUtils.trimAndNullAsEmpty(info.getCustCode()));//货主编号
                wsv.setWarehouseCode(PubUtils.trimAndNullAsEmpty(info.getWarehouseCode()));//仓库编号
                wsv.setCreateTime(info.getCreationTime()==null?new Date():info.getCreationTime());//创建时间
                wsv.setExpectedShipmentTime(info.getArriveTime());//预计货物到达时间
                wsv.setRequiredDeliveryTime(info.getArriveTime());//预计货物到达时间
                wsv.setRouteCode("");//线路
                wsv.setStop(PubUtils.trimAndNullAsEmpty(info.getEceivingPlatform()));//站点 月台待确认
                wsv.setFromAddressCodel("");//寄件地代码
                wsv.setToAddressCodel(PubUtils.trimAndNullAsEmpty(info.getConsigneeCode()));//目的地代码
                wsv.setChannel("");//渠道
                wsv.setFromWarehouseCode(PubUtils.trimAndNullAsEmpty(info.getWarehouseCode()));//来源仓库编码
                wsv.setToWarehouseCode("");//目标仓库编码
                wsv.setCarrierCode("");//承运商编码
                wsv.setCarrierName("");//承运商名称
                wsv.setPlatformOrderNo("");//平台订单号
                wsv.setStoreName("");//店铺名称
                wsv.setDeliveryNo("");//快递单号
                wsv.setConsigneeCode(PubUtils.trimAndNullAsEmpty(info.getConsigneeCode()));//收货人编码
                wsv.setConsigneeName(PubUtils.trimAndNullAsEmpty(info.getConsigneeName()));//收货方名称
                wsv.setcCountry("");//国家
                wsv.setcCity(PubUtils.trimAndNullAsEmpty(info.getConsigneeCity()));//城市
                wsv.setcDistrict(PubUtils.trimAndNullAsEmpty(info.getConsigneeDistrictAndCounty()));//县
                wsv.setcTown(PubUtils.trimAndNullAsEmpty(info.getConsigneeTownshipStreets()));//乡镇街道
                wsv.setcTel1("");//手机号码
                wsv.setcTel2("");//固定号码
                wsv.setcZip(PubUtils.trimAndNullAsEmpty(info.getConsigneePostCode()));//邮编
                wsv.setcMail("");//旺旺号
                wsv.setcAddress1(PubUtils.trimAndNullAsEmpty(info.getConsigneeAddress()));//详细地址
                wsv.setcAddress2("");//备用地址1
                wsv.setcAddress2("");//备用地址2
                wsv.setcAddress3("");//备用地址3
                wsv.setDeliveryWare("");//平台发货仓库
                wsv.setServiceNotes("");//客服备注
                wsv.setNotes("");//备注（顾客留言）
                wsv.setPrintinvoice(PubUtils.trimAndNullAsEmpty(info.getPrintInvoice()));//是否打印发票
                wsv.setPayment(PubUtils.trimAndNullAsEmpty(info.getBuyerPaymentMethod()));//支付方式
                //wsv.setTotal(Long.parseLong(info.getOrderAmount()));//总价
                wsv.setPaid(0l);//已付金额
                wsv.setPayDeliveryFlag(PubUtils.trimAndNullAsEmpty(info.getCollectFlag()));//是否货到付款（Y、N）
                wsv.setAmountPayable(0L);//应付金额
                wsv.setInsuredFlag(PubUtils.trimAndNullAsEmpty(info.getInsure()));//是否报价
               // wsv.setInsuredMoney(Long.parseLong(info.getInsureValue()));//报价金额
                wsv.setFreight(0l);//运费
                for (OfcGoodsDetailsInfo gdinfo: goodDetails) {
                    WhcSalesDeliveryDetails detail=new WhcSalesDeliveryDetails();
                    detail.setWhcBillno("");//流水号
                    detail.setCustomerCode(PubUtils.trimAndNullAsEmpty(info.getCustCode()));//货主编号
                    detail.setItemCode(PubUtils.trimAndNullAsEmpty(gdinfo.getGoodsCode()));//货品编号
                    detail.setItemName(PubUtils.trimAndNullAsEmpty(gdinfo.getGoodsName()));//货品名称
                    detail.setPrice(gdinfo.getUnitPrice());//价格是否是单价
                    detail.setUom(PubUtils.trimAndNullAsEmpty(gdinfo.getUnit()));//单位
                    detail.setPackid(PubUtils.trimAndNullAsEmpty(gdinfo.getPack()));//包装
                    detail.setItemWeight(gdinfo.getWeight());//货品重量
                    detail.setItemCubic(gdinfo.getCubage());//货品体积
                    detail.setQtyOrdered(gdinfo.getQuantity());//订货数
                    detailList .add(detail);
                }
                wsv.setDetailsList(detailList);
                jsonStr=JSONUtils.objectToJson(wsv);
    		}else if(OrderConstConstant.TRANSFEROUTOFTHELIBRARY.equals(documentType)){
                tag=documentType;
                WhcTransShip wsv=new WhcTransShip();
                List<WhcTransShipDetails> detailList=new ArrayList<>();
                wsv.setOrderNo(PubUtils.trimAndNullAsEmpty(info.getOrderCode()));
                wsv.setShipNo(PubUtils.trimAndNullAsEmpty(info.getOrderCode()));
                wsv.setWhcBillno("");//流水号
                wsv.setOrderType(PubUtils.trimAndNullAsEmpty(documentType));//订单类型
                wsv.setFromSystem("");//订单来源
                wsv.setCustomerCode(PubUtils.trimAndNullAsEmpty(info.getCustCode()));//货主编号
                wsv.setWarehouseCode(PubUtils.trimAndNullAsEmpty(info.getWarehouseCode()));//仓库编号
                wsv.setCreateTime(info.getCreationTime()==null?new Date():info.getCreationTime());//创建时间
                wsv.setExpectedShipmentTime(info.getArriveTime());//预计货物到达时间
                wsv.setRequiredDeliveryTime(info.getArriveTime());//预计货物到达时间
                wsv.setChannel("");//渠道
                wsv.setFromWarehouseCode(PubUtils.trimAndNullAsEmpty(info.getWarehouseCode()));//来源仓库编码
                wsv.setCarrierCode("");//承运商编码
                wsv.setCarrierName("");//承运商名称
                wsv.setConsigneeCode(PubUtils.trimAndNullAsEmpty(info.getConsigneeCode()));//收货人编码
                wsv.setConsigneeName(PubUtils.trimAndNullAsEmpty(info.getConsigneeName()));//收货方名称
                wsv.setcCountry("");//国家
                wsv.setcCity(info.getConsigneeCity());//城市
                wsv.setcDistrict(PubUtils.trimAndNullAsEmpty(info.getConsigneeDistrictAndCounty()));//县
                wsv.setcTown(PubUtils.trimAndNullAsEmpty(info.getConsigneeTownshipStreets()));//乡镇街道
                wsv.setcTel1("");//手机号码
                wsv.setcTel2("");//固定号码
                wsv.setcZip(PubUtils.trimAndNullAsEmpty(info.getConsigneePostCode()));//邮编
                wsv.setcMail("");//旺旺号
                wsv.setcAddress1(PubUtils.trimAndNullAsEmpty(info.getConsigneeAddress()));//详细地址
                wsv.setcAddress2("");//备用地址1
                wsv.setcAddress2("");//备用地址2
                wsv.setcAddress3("");//备用地址3
                wsv.setNotes("");//备注（顾客留言）
                wsv.setPrintinvoice(PubUtils.trimAndNullAsEmpty(info.getPrintInvoice()));//是否打印发票
                wsv.setPayment(PubUtils.trimAndNullAsEmpty(info.getBuyerPaymentMethod()));//支付方式
                wsv.setTotal(Long.parseLong(info.getOrderAmount()));//总价
                wsv.setPaid(0l);//已付金额
                wsv.setPayDeliveryFlag(PubUtils.trimAndNullAsEmpty(info.getCollectFlag()));//是否货到付款（Y、N）
                wsv.setAmountPayable(0L);//应付金额
                wsv.setInsuredFlag(info.getInsure());//是否报价
                wsv.setInsuredMoney(Long.parseLong(info.getInsureValue()));//报价金额
                wsv.setFreight(0l);//运费
                for (OfcGoodsDetailsInfo gdinfo: goodDetails) {
                    WhcTransShipDetails detail=new WhcTransShipDetails();
                    detail.setWhcBillno("");//流水号
                    detail.setCustomerCode(PubUtils.trimAndNullAsEmpty(info.getCustCode()));//货主编号
                    detail.setItemCode(PubUtils.trimAndNullAsEmpty(gdinfo.getGoodsCode()));//货品编号
                    detail.setItemName(PubUtils.trimAndNullAsEmpty(gdinfo.getGoodsName()));//货品名称
                    detail.setPrice(gdinfo.getUnitPrice());//价格是否是单价
                    detail.setUom(PubUtils.trimAndNullAsEmpty(gdinfo.getUnit()));//单位
                    detail.setPackid(PubUtils.trimAndNullAsEmpty(gdinfo.getPack()));//包装
                    detail.setItemWeight(gdinfo.getWeight());//货品重量
                    detail.setItemCubic(gdinfo.getCubage());//货品体积
                    detail.setQtyOrdered(gdinfo.getQuantity());//订货数
                    detailList .add(detail);
                }
                wsv.setDetailsList(detailList);
                jsonStr=JSONUtils.objectToJson(wsv);
            }else if(OrderConstConstant.LOSSOFREPORTING.equals(documentType)){
                tag=documentType;
                WhcRework wsv=new WhcRework();
                List<WhcReworkDetails> detailList=new ArrayList<>();
                wsv.setOrderNo(info.getOrderCode());
                wsv.setShipNo(info.getOrderCode());
                wsv.setWhcBillno("");//流水号
                wsv.setOrderType(documentType);//订单类型
                wsv.setFromSystem("");//订单来源
                wsv.setCustomerCode(PubUtils.trimAndNullAsEmpty(info.getCustCode()));//货主编号
                wsv.setWarehouseCode(PubUtils.trimAndNullAsEmpty(info.getWarehouseCode()));//仓库编号
                wsv.setCreateTime(info.getCreationTime()==null?new Date():info.getCreationTime());//创建时间
                wsv.setExpectedShipmentTime(info.getArriveTime());//预计货物到达时间
                wsv.setRequiredDeliveryTime(info.getArriveTime());//预计货物到达时间
                wsv.setChannel("");//渠道
                wsv.setFromWarehouseCode(PubUtils.trimAndNullAsEmpty(info.getWarehouseCode()));//来源仓库编码
                wsv.setCarrierCode("");//承运商编码
                wsv.setCarrierName("");//承运商名称
                wsv.setConsigneeName(PubUtils.trimAndNullAsEmpty(info.getConsigneeName()));//收货方名称
                wsv.setcCountry("");//国家
                wsv.setcCity(PubUtils.trimAndNullAsEmpty(info.getConsigneeCity()));//城市
                wsv.setcDistrict(PubUtils.trimAndNullAsEmpty(info.getConsigneeDistrictAndCounty()));//县
                wsv.setcTown(PubUtils.trimAndNullAsEmpty(info.getConsigneeTownshipStreets()));//乡镇街道
                wsv.setcTel1("");//手机号码
                wsv.setcTel2("");//固定号码
                wsv.setcZip(PubUtils.trimAndNullAsEmpty(info.getConsigneePostCode()));//邮编
                wsv.setcMail("");//旺旺号
                wsv.setcAddress1(PubUtils.trimAndNullAsEmpty(info.getConsigneeAddress()));//详细地址
                wsv.setcAddress2("");//备用地址1
                wsv.setcAddress2("");//备用地址2
                wsv.setcAddress3("");//备用地址3
                wsv.setNotes("");//备注（顾客留言）
                wsv.setPrintinvoice(info.getPrintInvoice());//是否打印发票
                wsv.setPayment(info.getBuyerPaymentMethod());//支付方式
                wsv.setTotal(Long.parseLong(info.getOrderAmount()));//总价

                wsv.setPaid(0l);//已付金额
                wsv.setPayDeliveryFlag(PubUtils.trimAndNullAsEmpty(info.getCollectFlag()));//是否货到付款（Y、N）
                wsv.setAmountPayable(0L);//应付金额
                wsv.setInsuredFlag(info.getInsure());//是否报价
                wsv.setInsuredMoney(Long.parseLong(info.getInsureValue()));//报价金额
                wsv.setFreight(0l);//运费
                for (OfcGoodsDetailsInfo gdinfo: goodDetails) {
                    WhcReworkDetails detail=new WhcReworkDetails();
                    detail.setWhcBillno("");//流水号
                    detail.setCustomerCode(PubUtils.trimAndNullAsEmpty(info.getCustCode()));//货主编号
                    detail.setItemCode(PubUtils.trimAndNullAsEmpty(gdinfo.getGoodsCode()));//货品编号
                    detail.setItemName(PubUtils.trimAndNullAsEmpty(gdinfo.getGoodsName()));//货品名称
                    detail.setPrice(gdinfo.getUnitPrice());//价格是否是单价
                    detail.setUom(PubUtils.trimAndNullAsEmpty(gdinfo.getUnit()));//单位
                    detail.setPackid(PubUtils.trimAndNullAsEmpty(gdinfo.getPack()));//包装
                    detail.setItemWeight(gdinfo.getWeight());//货品重量
                    detail.setItemCubic(gdinfo.getCubage());//货品体积
                    detail.setQtyOrdered(gdinfo.getQuantity());//订货数
                    detailList .add(detail);
                }
                wsv.setDetailsList(detailList);
                jsonStr=JSONUtils.objectToJson(wsv);
            }else if(OrderConstConstant.OTHEROUTOFTHELIBRARY.equals(documentType)){
                tag=documentType;
            }
    	}else if(OrderConstConstant.OFC_WHC_IN_TYPE.equals(info.getBusinessType())){
    		//采购入库
			if(OrderConstConstant.PURCHASINGANDSTORAGE.equals(documentType)){
                tag=documentType;
				WhcPurchase wp=new WhcPurchase();
				List<WhcPurchaseDetail> detailList=new ArrayList<WhcPurchaseDetail>();
				wp.setOrderNo(info.getOrderCode());
				wp.setWhcBillno("");//仓库中心入库单
				wp.setOrdertype(documentType);//入库单类型
				wp.setFromSystem("");//订单来源
				wp.setCustomerCode(PubUtils.trimAndNullAsEmpty(info.getCustCode()));//货主编号
				wp.setCustomerName("");//货主名称
				wp.setWarehouseCode(PubUtils.trimAndNullAsEmpty(info.getWarehouseCode()));//仓库编号
				wp.setWarehouseName(PubUtils.trimAndNullAsEmpty(info.getWarehouseName()));//仓库名称
				wp.setCreateTime(info.getCreationTime()==null?new Date():info.getCreationTime());//创建时间
				wp.setExpectedArriveTime(info.getArriveTime());//预计货物到达时间
				wp.setSupplierCode(PubUtils.trimAndNullAsEmpty(info.getSupportCode()));//供应商编码
				wp.setSupplierName(PubUtils.trimAndNullAsEmpty(info.getSupportName()));//供应商名称
				wp.setCarrierCode("");//承运人编码
				wp.setCarrierName("");//承运人姓名
				for (OfcGoodsDetailsInfo gdinfo: goodDetails) {
					WhcPurchaseDetail detail=new WhcPurchaseDetail();
					detail.setWhcBillno("");//仓库中心入库单
					detail.setLineno(0);//行号
					detail.setCustomerCode(PubUtils.trimAndNullAsEmpty(info.getCustCode()));//货主编号
					detail.setCustomerName("");//货主名称
					detail.setItemCode(PubUtils.trimAndNullAsEmpty(gdinfo.getGoodsCode()));//货品编号
					detail.setItemName(PubUtils.trimAndNullAsEmpty(gdinfo.getGoodsName()));//货品名称
					detail.setQty(gdinfo.getQuantity());//货品数量
					detail.setUom(PubUtils.trimAndNullAsEmpty(gdinfo.getUnit()));//单位
					detail.setPackid(PubUtils.trimAndNullAsEmpty(gdinfo.getPack()));//包装
					detail.setItemWeight(gdinfo.getWeight());//货品重量
					detail.setItemCubic(gdinfo.getCubage());//货品体积
					detail.setStandard("");//货品规格
					detail.setReceivedQty(gdinfo.getQuantity());//实际入库数量
					detailList.add(detail);
				}
					wp.setDetailList(detailList);
				  jsonStr=JSONUtils.objectToJson(wp);
			//调拨入库
			}else if(OrderConstConstant.ALLOCATESTORAGE.equals(documentType)){
                tag=documentType;
				WhcTransReceive r=new WhcTransReceive();
				List<WhcTransReceiveDetail> detailList=new ArrayList<WhcTransReceiveDetail>();
				r.setOrderNo(PubUtils.trimAndNullAsEmpty(info.getOrderCode()));
				r.setWhcBillno("");//仓库中心入库单
				r.setOrdertype(documentType);//入库单类型
				r.setOaOrderNo("");//调拨出库单号
				r.setFromSystem("");//订单来源
				r.setCustomerCode(PubUtils.trimAndNullAsEmpty(info.getCustCode()));//货主编号
				r.setCustomerName("");//货主名称
				r.setWarehouseCode(PubUtils.trimAndNullAsEmpty(info.getWarehouseCode()));//仓库编号
				r.setWarehouseName(PubUtils.trimAndNullAsEmpty(info.getWarehouseName()));//仓库名称
				r.setSourceWarehouseCode(PubUtils.trimAndNullAsEmpty(info.getWarehouseCode()));//来源仓库编码
				r.setSourceWarehouseName(PubUtils.trimAndNullAsEmpty(info.getWarehouseName()));//来源仓库名称
				r.setCreateTime(info.getCreationTime()==null?new Date():info.getCreationTime());//创建时间
				r.setExpectedArriveTime(info.getArriveTime());//预计货物到达时间
				r.setCarrierCode("");//承运人编码
				r.setCarrierName("");//承运人姓名
				for (OfcGoodsDetailsInfo gdinfo: goodDetails) {
					WhcTransReceiveDetail detail=new WhcTransReceiveDetail();
					detail.setWhcBillno("");//仓库中心入库单
					detail.setLineno(0);//行号
					detail.setCustomerCode(PubUtils.trimAndNullAsEmpty(info.getCustCode()));//货主编号
					detail.setCustomerName("");//货主名称
					detail.setItemCode(PubUtils.trimAndNullAsEmpty(gdinfo.getGoodsCode()));//货品编号
					detail.setItemName(PubUtils.trimAndNullAsEmpty(gdinfo.getGoodsName()));//货品名称
					detail.setQty(gdinfo.getQuantity());//货品数量
					detail.setUom(PubUtils.trimAndNullAsEmpty(gdinfo.getUnit()));//单位
					detail.setPackid(PubUtils.trimAndNullAsEmpty(gdinfo.getPack()));//包装
					detail.setItemWeight(gdinfo.getWeight());//货品重量
					detail.setItemCubic(gdinfo.getCubage());//货品体积
					detail.setStandard("");//货品规格
					detail.setReceivedQty(gdinfo.getQuantity());//实际入库数量
					detailList.add(detail);
				}
				r.setDetailList(detailList);
				  jsonStr=JSONUtils.objectToJson(r);
			//退货入库
    		}else if(OrderConstConstant.RETURNWAREHOUSING.equals(documentType)){
                tag=documentType;
                WhcReturns w=new WhcReturns();
                //List<WhcTransReceiveDetail> detailList=new ArrayList<>();
                List<WhcReturnsDetail> detailList=new ArrayList<WhcReturnsDetail>();
                w.setOrderNo(PubUtils.trimAndNullAsEmpty(info.getOrderCode()));
                w.setWhcBillno("");//仓库中心入库单
                w.setOrdertype(documentType);//入库单类型
                w.setPoNo("");//原出库单号
                w.setFromSystem("");//订单来源
                w.setCustomerCode(PubUtils.trimAndNullAsEmpty(info.getCustCode()));//货主编号
                w.setCustomerName("");//货主名称
                w.setWarehouseCode(PubUtils.trimAndNullAsEmpty(info.getWarehouseCode()));//仓库编号
                w.setWarehouseName(PubUtils.trimAndNullAsEmpty(info.getWarehouseName()));//仓库名称
                w.setCreateTime(info.getCreationTime()==null?new Date():info.getCreationTime());//创建时间
                w.setExpectedArriveTime(info.getArriveTime());//预计货物到达时间
                w.setCarrierCode("");//承运人编码
                w.setCarrierName("");//承运人姓名
                w.setSupplierCode(PubUtils.trimAndNullAsEmpty(info.getSupportCode()));//供应商编码
                w.setSupplierName(PubUtils.trimAndNullAsEmpty(info.getSupportName()));//供应商名称
                w.setCarrierName("");//退货人姓名
                w.setReturnPeople("");//退货运单号
                w.setReturnTrackingNo("");//退货原因
                for (OfcGoodsDetailsInfo gdinfo: goodDetails) {
                    WhcReturnsDetail detail=new WhcReturnsDetail();
                    detail.setWhcBillno("");//仓库中心入库单
                    detail.setLineno(0);//行号
                    detail.setCustomerCode(PubUtils.trimAndNullAsEmpty(info.getCustCode()));//货主编号
                    detail.setCustomerName("");//货主名称
                    detail.setItemCode(PubUtils.trimAndNullAsEmpty(gdinfo.getGoodsCode()));//货品编号
                    detail.setItemName(PubUtils.trimAndNullAsEmpty(gdinfo.getGoodsName()));//货品名称
                    detail.setQty(gdinfo.getQuantity());//货品数量
                    detail.setUom(gdinfo.getUnit());//单位
                    detail.setPackid(PubUtils.trimAndNullAsEmpty(gdinfo.getPack()));//包装
                    detail.setItemWeight(gdinfo.getWeight());//货品重量
                    detail.setItemCubic(gdinfo.getCubage());//货品体积
                    detail.setStandard("");//货品规格
                    detail.setReceivedQty(gdinfo.getQuantity());//实际入库数量
                    detailList.add(detail);
                }
                    w.setDetailList(detailList);
                jsonStr=JSONUtils.objectToJson(w);
             //加工入库
            }else if(OrderConstConstant.PROCESSINGSTORAGE.equals(documentType)){


            }
    	}
    	logger.info("send to whc json is :" +jsonStr);
    	defaultMqProducer.toSendWhc(jsonStr, info.getPlanCode(), tag);
    }
}