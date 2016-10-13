package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lyh on 2016/10/13.
 * 从4张表中根据code查出订单的4张数据,然后映射到OrderDto中
 */
@Service
public class OfcOrderDtoServiceImpl implements OfcOrderDtoService {
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


    @Override
    public OfcOrderDTO getOrderDto(String orderCode) {
        OfcFundamentalInformation ofcFundamentalInformation =  ofcFundamentalInformationService.getFundamentalInformation(orderCode);
        OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoService.getDistributionBasicInfo(orderCode);
        OfcWarehouseInformation ofcWarehouseInformation = ofcWarehouseInformationService.getWarehouseInformation(orderCode);
        OfcOrderStatus ofcOrderStatus = ofcOrderStatusService.getOrderStatus(orderCode);
        ModelMapper modelMapper = new ModelMapper();
        OfcOrderDTO ofcOrderDTO = new OfcOrderDTO();
        ofcOrderDTO = modelMapper.map(ofcFundamentalInformation,OfcOrderDTO.class);
        ofcOrderDTO = modelMapper.map(ofcDistributionBasicInfo,OfcOrderDTO.class);
        ofcOrderDTO = modelMapper.map(ofcWarehouseInformation,OfcOrderDTO.class);
        ofcOrderDTO = modelMapper.map(ofcOrderStatus,OfcOrderDTO.class);
        return ofcOrderDTO;
    }
}
