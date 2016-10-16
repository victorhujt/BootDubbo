package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.service.*;
import com.xescm.ofc.enums.OrderConstEnum;
import com.xescm.ofc.utils.PrimaryGenerater;
import com.xescm.ofc.utils.PubUtils;
import com.xescm.ofc.wrap.Wrapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ydx on 2016/10/12.
 */
@Service
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
    ModelMapper modelMapper = new ModelMapper();
    @Override
    public String placeOrder(OfcOrderDTO ofcOrderDTO,String tag) {
        OfcGoodsDetailsInfo ofcGoodsDetailsInfo = modelMapper.map(ofcOrderDTO, OfcGoodsDetailsInfo.class);
        OfcFundamentalInformation ofcFundamentalInformation = modelMapper.map(ofcOrderDTO, OfcFundamentalInformation.class);
        OfcDistributionBasicInfo ofcDistributionBasicInfo = modelMapper.map(ofcOrderDTO, OfcDistributionBasicInfo.class);
        OfcWarehouseInformation  ofcWarehouseInformation = modelMapper.map(ofcOrderDTO, OfcWarehouseInformation.class);
        OfcOrderStatus ofcOrderStatus=new OfcOrderStatus();
        if (ofcFundamentalInformation.getOrderType().equals(OrderConstEnum.WAREHOUSEDISTRIBUTIONORDER)){
            ofcFundamentalInformation.setCustCode("001");
            ofcFundamentalInformation.setCustName("众品");
            ofcWarehouseInformation.setProvideTransport(1);
            if(ofcWarehouseInformation.getProvideTransport().toString().equals("1")){
                ofcFundamentalInformation.setSecCustCode("001");
                ofcFundamentalInformation.setSecCustName("众品");
            }
        }else if(ofcFundamentalInformation.getOrderType().equals(OrderConstEnum.TRANSPORTORDER)){
            ofcFundamentalInformation.setSecCustCode("001");
            ofcFundamentalInformation.setSecCustName("众品");
            if (PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getDeparturePlace())
                    .equals(PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getDestination()))){
                ofcFundamentalInformation.setBusinessType(OrderConstEnum.WITHTHECITY);
            }else{
                ofcFundamentalInformation.setBusinessType(OrderConstEnum.WITHTHETRUNK);
            }
        }
//        ofcFundamentalInformation.setStoreCode(ofcOrderDTO.getStoreName());
        ofcFundamentalInformation.setStoreName(ofcOrderDTO.getStoreName());
        ofcFundamentalInformation.setOrderSource("手动");
        try {
            if (PubUtils.trimAndNullAsEmpty(tag).equals("place")){
                if (ofcFundamentalInformationService.selectOne(ofcFundamentalInformation)==null){
                    ofcFundamentalInformation.setOrderCode("SO"+ PrimaryGenerater.getInstance()
                            .generaterNextNumber(PrimaryGenerater.getInstance().getLastNumber()));
                    //ofcFundamentalInformation.setOrderTime(new Date());
                    ofcFundamentalInformation.setCreationTime(new Date());
                    ofcFundamentalInformation.setCreator("001");
                    ofcFundamentalInformation.setOperator("001");
                    ofcFundamentalInformation.setOperTime(new Date());
                    ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                            +" "+"订单已创建");
                    upOrderStatus(ofcOrderStatus,ofcFundamentalInformation);
                    ofcDistributionBasicInfo=upDistributionBasicInfo(ofcDistributionBasicInfo,ofcFundamentalInformation);
                    ofcWarehouseInformation=upOfcWarehouseInformation(ofcWarehouseInformation,ofcFundamentalInformation);
                    ofcDistributionBasicInfoService.save(ofcDistributionBasicInfo);
                    ofcWarehouseInformationService.save(ofcWarehouseInformation);
                    ofcFundamentalInformationService.save(ofcFundamentalInformation);
                }else{
                    return String.valueOf(Wrapper.ERROR_CODE);
                }
            }else if (PubUtils.trimAndNullAsEmpty(tag).equals("manage")){
                if (PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getOrderCode()).equals("")){
                    ofcFundamentalInformation.setOrderCode(ofcFundamentalInformationService.selectOne(ofcFundamentalInformation).getOrderCode());
                }
                ofcFundamentalInformation.setOperator("001");
                ofcFundamentalInformation.setOperTime(new Date());
                ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                        +" "+"订单已更新");
                upOrderStatus(ofcOrderStatus,ofcFundamentalInformation);
                ofcDistributionBasicInfo=upDistributionBasicInfo(ofcDistributionBasicInfo,ofcFundamentalInformation);
                ofcWarehouseInformation=upOfcWarehouseInformation(ofcWarehouseInformation,ofcFundamentalInformation);
                ofcDistributionBasicInfoService.updateByOrderCode(ofcDistributionBasicInfo);
                ofcWarehouseInformationService.updateByOrderCode(ofcWarehouseInformation);
                ofcFundamentalInformationService.update(ofcFundamentalInformation);
            }else {
                return String.valueOf(Wrapper.ERROR_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return String.valueOf(Wrapper.ERROR_CODE);
        }
        return String.valueOf(Wrapper.SUCCESS_CODE);
    }

    public void upOrderStatus(OfcOrderStatus ofcOrderStatus,OfcFundamentalInformation ofcFundamentalInformation){
        ofcOrderStatus.setOrderCode(ofcFundamentalInformation.getOrderCode());
        ofcOrderStatus.setOrderType(ofcFundamentalInformation.getOrderType());
        ofcOrderStatus.setBusinessType(ofcFundamentalInformation.getBusinessType());
        ofcOrderStatus.setCustName(ofcFundamentalInformation.getCustName());
        ofcOrderStatus.setCustCode(ofcFundamentalInformation.getCustName());
        ofcOrderStatus.setOrderStatus(OrderConstEnum.PENDINGAUDIT);
        ofcOrderStatus.setLastedOperTime(new Date());
        ofcOrderStatus.setOperator("001");
        ofcOrderStatusService.save(ofcOrderStatus);
    }

    public OfcDistributionBasicInfo upDistributionBasicInfo(OfcDistributionBasicInfo ofcDistributionBasicInfo
            ,OfcFundamentalInformation ofcFundamentalInformation){
        ofcDistributionBasicInfo.setTransCode("111111111");
        ofcDistributionBasicInfo.setDeparturePlaceCode("001");
        ofcDistributionBasicInfo.setDestinationCode("001");
        ofcDistributionBasicInfo.setTotalStandardBox(8);
        ofcDistributionBasicInfo.setOrderCode(ofcFundamentalInformation.getOrderCode());
        ofcDistributionBasicInfo.setCreationTime(ofcFundamentalInformation.getCreationTime());
        ofcDistributionBasicInfo.setCreator(ofcFundamentalInformation.getCreator());
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

}
