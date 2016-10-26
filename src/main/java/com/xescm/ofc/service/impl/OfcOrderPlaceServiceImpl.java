package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.domain.dto.CscContantAndCompanyDto;
import com.xescm.ofc.domain.dto.CscSupplierInfoDto;
import com.xescm.ofc.enums.OrderConstEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.client.FeignCscCustomerAPIClient;
import com.xescm.ofc.feign.client.FeignCscSupplierAPIClient;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.PrimaryGenerater;
import com.xescm.ofc.utils.PubUtils;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xescm.uam.utils.wrap.Wrapper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ydx on 2016/10/12.
 */
@Service
@Transactional//既能注解在方法上,也能注解在类上.当注解在类上的时候,意味着这个类的所有public方法都是开启事务的,如果类级别和方法级别同事使用了该注解,则方法覆盖类.
//@Transactional(rollbackFor={xxx.class})
public class OfcOrderPlaceServiceImpl implements OfcOrderPlaceService {
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
    private FeignCscCustomerAPIClient feignCscCustomerAPIClient;
    @Autowired
    private FeignCscSupplierAPIClient feignCscSupplierAPIClient;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public String placeOrder(OfcOrderDTO ofcOrderDTO,String tag) {
        OfcGoodsDetailsInfo ofcGoodsDetailsInfo = modelMapper.map(ofcOrderDTO, OfcGoodsDetailsInfo.class);
        OfcFundamentalInformation ofcFundamentalInformation = modelMapper.map(ofcOrderDTO, OfcFundamentalInformation.class);
        OfcDistributionBasicInfo ofcDistributionBasicInfo = modelMapper.map(ofcOrderDTO, OfcDistributionBasicInfo.class);
        OfcWarehouseInformation  ofcWarehouseInformation = modelMapper.map(ofcOrderDTO, OfcWarehouseInformation.class);
        OfcOrderStatus ofcOrderStatus=new OfcOrderStatus();
        //ofcFundamentalInformation.setStoreCode(ofcOrderDTO.getStoreName());//店铺还没维护表
        ofcFundamentalInformation.setStoreName(ofcOrderDTO.getStoreName());//店铺还没维护表
        ofcFundamentalInformation.setOrderSource("手动");//订单来源
        try {
            if (PubUtils.trimAndNullAsEmpty(tag).equals("place")){//下单
                if(StringUtils.isBlank(ofcFundamentalInformation.getCustOrderCode())){
                    throw new BusinessException("您的客户订单编号填写有误!");
                }
                String orderCodeByCustOrderCode = ofcFundamentalInformationService.getOrderCodeByCustOrderCode(ofcFundamentalInformation.getCustOrderCode());
                if (StringUtils.isBlank(orderCodeByCustOrderCode)){//根据客户订单编号查询唯一性
                    ofcFundamentalInformation.setOrderCode("SO"+ PrimaryGenerater.getInstance()
                            .generaterNextNumber(PrimaryGenerater.getInstance().getLastNumber()));
                    ofcFundamentalInformation.setCustCode("001");
                    ofcFundamentalInformation.setCustName("众品");
                    ofcFundamentalInformation.setAbolishMark(OrderConstEnum.ORDERWASNOTABOLISHED);//未作废
                    if (ofcFundamentalInformation.getOrderType().equals(OrderConstEnum.WAREHOUSEDISTRIBUTIONORDER)){
                        if(null == ofcWarehouseInformation.getProvideTransport()){
                            ofcWarehouseInformation.setProvideTransport(OrderConstEnum.WAREHOUSEORDERNOTPROVIDETRANS);
                        }
                        if(ofcWarehouseInformation.getProvideTransport().toString().equals(OrderConstEnum.WAREHOUSEORDERPROVIDETRANS)){
                            addDistributionInfo(ofcDistributionBasicInfo, ofcFundamentalInformation);
                        }
                        ofcWarehouseInformation=upOfcWarehouseInformation(ofcWarehouseInformation,ofcFundamentalInformation);
                        ofcWarehouseInformationService.save(ofcWarehouseInformation);
                    }else if(ofcFundamentalInformation.getOrderType().equals(OrderConstEnum.TRANSPORTORDER)){
                        addDistributionInfo(ofcDistributionBasicInfo, ofcFundamentalInformation);
                    }else{
                        throw new BusinessException("您选择的订单类型系统无法识别!");
                    }
                    ofcFundamentalInformation.setCreationTime(new Date());
                    ofcFundamentalInformation.setCreator("001");
                    ofcFundamentalInformation.setOperator("001");
                    ofcFundamentalInformation.setOperTime(new Date());
                    ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                            +" "+"订单已创建");
                    upOrderStatus(ofcOrderStatus,ofcFundamentalInformation);
                    ofcFundamentalInformationService.save(ofcFundamentalInformation);
                }else{
                    throw new BusinessException("该客户订单编号已经存在!您不能重复下单!请查看订单编号为:" + orderCodeByCustOrderCode+ "的订单");
                }
            }else if (PubUtils.trimAndNullAsEmpty(tag).equals("manage")){ //编辑
                if(StringUtils.isBlank(ofcFundamentalInformation.getCustOrderCode())){
                    throw new BusinessException("您的客户订单编号填写有误!");
                }
                if (("").equals(PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getOrderCode()))){
                    ofcFundamentalInformation.setOrderCode(ofcFundamentalInformationService.selectOne(ofcFundamentalInformation).getOrderCode());
                }
                //仓配订单
                if (ofcFundamentalInformation.getOrderType().equals(OrderConstEnum.WAREHOUSEDISTRIBUTIONORDER)){
                    if(null == ofcWarehouseInformation.getProvideTransport()){
                        ofcWarehouseInformation.setProvideTransport(OrderConstEnum.WAREHOUSEORDERNOTPROVIDETRANS);
                    }
                    //仓配单需要运输
                    if(ofcWarehouseInformation.getProvideTransport().toString().equals(OrderConstEnum.WAREHOUSEORDERPROVIDETRANS)){
                        //如果编辑订单后, 还是需要提供运输, 就要更新运输信息
                        ofcDistributionBasicInfo=upDistributionBasicInfo(ofcDistributionBasicInfo,ofcFundamentalInformation);
                        ofcDistributionBasicInfoService.updateByOrderCode(ofcDistributionBasicInfo);
                    }else if (ofcWarehouseInformation.getProvideTransport().toString().equals(OrderConstEnum.WAREHOUSEORDERNOTPROVIDETRANS)){
                        //仓配单不需要运输,需要将属于该订单的仓配信息删除
                        ofcFundamentalInformation.setSecCustCode("");
                        ofcFundamentalInformation.setSecCustName("");
                        ofcDistributionBasicInfoService.deleteByOrderCode(ofcFundamentalInformation.getOrderCode());
                    }
                    // 更新仓配信息
                    ofcWarehouseInformation=upOfcWarehouseInformation(ofcWarehouseInformation,ofcFundamentalInformation);
                    ofcWarehouseInformationService.updateByOrderCode(ofcWarehouseInformation);
                }else if(ofcFundamentalInformation.getOrderType().equals(OrderConstEnum.TRANSPORTORDER)){
                    //更新运输信息
                    ofcDistributionBasicInfo=upDistributionBasicInfo(ofcDistributionBasicInfo,ofcFundamentalInformation);
                    ofcDistributionBasicInfoService.updateByOrderCode(ofcDistributionBasicInfo);

                }else{
                    throw new BusinessException("您的订单类型系统无法识别!");
                }
                ofcFundamentalInformation.setOperator("001");
                ofcFundamentalInformation.setOperTime(new Date());
                ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                        +" "+"订单已更新");
                upOrderStatus(ofcOrderStatus,ofcFundamentalInformation);
                ofcFundamentalInformationService.update(ofcFundamentalInformation);
            }else {
                throw new BusinessException("未知操作!系统无法识别!");
            }
        } catch (BusinessException e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("未知异常!请联系管理员!");
        }
        return "您已成功下单!";
    }

    public void upOrderStatus(OfcOrderStatus ofcOrderStatus,OfcFundamentalInformation ofcFundamentalInformation){
        ofcOrderStatus.setOrderCode(ofcFundamentalInformation.getOrderCode());
        ofcOrderStatus.setOrderStatus(OrderConstEnum.PENDINGAUDIT);
        ofcOrderStatus.setStatusDesc("待审核");
        ofcOrderStatus.setLastedOperTime(new Date());
        ofcOrderStatus.setOperator("001");
        ofcOrderStatusService.save(ofcOrderStatus);
    }

    public void addDistributionInfo(OfcDistributionBasicInfo ofcDistributionBasicInfo, OfcFundamentalInformation ofcFundamentalInformation){
        ofcFundamentalInformation.setSecCustCode("001");
        ofcFundamentalInformation.setSecCustName("众品");
        if (PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getDeparturePlace())
                .equals(PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getDestination()))){
            ofcFundamentalInformation.setBusinessType(OrderConstEnum.WITHTHECITY);
        }else{
            ofcFundamentalInformation.setBusinessType(OrderConstEnum.WITHTHETRUNK);
        }
        ofcDistributionBasicInfo=upDistributionBasicInfo(ofcDistributionBasicInfo,ofcFundamentalInformation);
        ofcDistributionBasicInfoService.save(ofcDistributionBasicInfo);
    }

    public OfcDistributionBasicInfo upDistributionBasicInfo(OfcDistributionBasicInfo ofcDistributionBasicInfo
            ,OfcFundamentalInformation ofcFundamentalInformation){
        ofcDistributionBasicInfo.setTransCode(ofcFundamentalInformation.getOrderCode().replace("SO","TSO"));
        ofcDistributionBasicInfo.setDeparturePlaceCode("001");
        ofcDistributionBasicInfo.setDestinationCode("001");
        if(ofcFundamentalInformation.getCreationTime()== null){
            ofcDistributionBasicInfo.setCreationTime(ofcFundamentalInformation.getCreationTime());
            ofcDistributionBasicInfo.setCreator(ofcFundamentalInformation.getCreator());
        }
        ofcDistributionBasicInfo.setOrderCode(ofcFundamentalInformation.getOrderCode());
        ofcDistributionBasicInfo.setOperator(ofcFundamentalInformation.getOperator());
        ofcDistributionBasicInfo.setOperTime(ofcFundamentalInformation.getOperTime());
        return ofcDistributionBasicInfo;
    }

    public OfcWarehouseInformation upOfcWarehouseInformation(OfcWarehouseInformation ofcWarehouseInformation
            ,OfcFundamentalInformation ofcFundamentalInformation){
        ofcWarehouseInformation.setSupportCode("001");
        ofcWarehouseInformation.setSupportName("众品");
        ofcWarehouseInformation.setOrderCode(ofcFundamentalInformation.getOrderCode());
        ofcWarehouseInformation.setCreationTime(ofcFundamentalInformation.getCreationTime());
        ofcWarehouseInformation.setCreator(ofcFundamentalInformation.getCreator());
        ofcWarehouseInformation.setOperTime(ofcFundamentalInformation.getOperTime());
        ofcWarehouseInformation.setOperator(ofcFundamentalInformation.getOperator());
        return ofcWarehouseInformation;
    }

    /**
     * 下单或编辑时保存货品信息
     */
    public String saveOrderGoodsList(List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList){
        if (ofcGoodsDetailsInfoList.size() < 1){
            return "未添加货品信息";
        }
        for (OfcGoodsDetailsInfo ofcGoodsDetailsInfo: ofcGoodsDetailsInfoList) {
            int save = ofcGoodsDetailsInfoService.save(ofcGoodsDetailsInfo);
            if(save == 0){
                throw new BusinessException("保存货品信息失败");
            }
        }
        return Wrapper.SUCCESS_MESSAGE;


    }
    /**
     * 下单或编辑时保存发货方收货方及其联系人
     */
    public String saveContactMessage(CscContantAndCompanyDto cscContantAndCompanyDto){
        if(null == cscContantAndCompanyDto){
            return "未添加联系人信息";
        }
        try {
            Wrapper<?> wrapper = feignCscCustomerAPIClient.addCscContantAndCompany(cscContantAndCompanyDto);
            if(Wrapper.ERROR_CODE == wrapper.getCode()){
                throw new BusinessException(wrapper.getMessage());
            }
        }catch (Exception ex){
            throw new BusinessException("添加联系人信息出错!");
        }
        return Wrapper.SUCCESS_MESSAGE;
    }
    /**
     * 下单或编辑时保存供应商及供应商联系人
     */
    public String saveSupportMessage(CscSupplierInfoDto cscSupplierInfoDto){
        if(null == cscSupplierInfoDto){
            return "未添加供应商信息";
        }
        try {
            Wrapper<?> wrapper = feignCscSupplierAPIClient.addSupplierBySupplierCode(cscSupplierInfoDto);
            if(Wrapper.ERROR_CODE == wrapper.getCode()){
                throw new BusinessException(wrapper.getMessage());
            }
        }catch (Exception ex){
            throw new BusinessException("添加供应商信息出错!");
        }
        return Wrapper.SUCCESS_MESSAGE;
    }
    /**
     * 下单或编辑时保存仓库信息
     */
    public String saveWarehouseMessage(OfcWarehouseInformation ofcWarehouseInformation){
        if(null == ofcWarehouseInformation){
            return "未添加仓库信息";
        }
        try {
            int save = ofcWarehouseInformationService.save(ofcWarehouseInformation);
            if(save == 0){
                throw new BusinessException("保存仓库信息失败");
            }
        }catch (Exception ex){
            throw new BusinessException("保存仓库信息失败!");
        }
        return Wrapper.SUCCESS_MESSAGE;
    }
}
