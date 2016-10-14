package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.PubUtils;
import org.apache.commons.lang.StringUtils;
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
    public OfcOrderDTO orderDtoSelect(String code,String dtoTag) {
        String orderCode = null;
        String custOrderCode =null;
        String transCode = null;
        if(!PubUtils.trimAndNullAsEmpty(code).equals("")){
            if(dtoTag.equals("orderCode")){
                orderCode = code;
            }else if(dtoTag.equals("custOrderCode")){
                custOrderCode = code;
                //然后拿着这个custOrderCode去数据库, 找到对应的OrderCode
                orderCode = ofcFundamentalInformationService.getOrderCodeByCustOrderCode(custOrderCode);
            }else if(dtoTag.equals("transCode")){
                transCode = code;
                //然后拿着这个transCode数据库关联基本列表, 找到对应的OrderCode,
                orderCode = ofcDistributionBasicInfoService.getOrderCodeByTransCode(transCode);
            }
            if(StringUtils.isBlank(orderCode)){//如果找不到对应的code,就提示直接提示错误.
                return null;//再做处理
            }else{
                OfcFundamentalInformation ofcFundamentalInformation =  ofcFundamentalInformationService.selectByKey(orderCode);
                if(ofcFundamentalInformation == null){
                    return new OfcOrderDTO();//再做处理
                }
                OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoService.distributionBasicInfoSelect(orderCode);
                OfcWarehouseInformation ofcWarehouseInformation = ofcWarehouseInformationService.warehouseInformationSelect(orderCode);
                OfcOrderStatus ofcOrderStatus = ofcOrderStatusService.orderStatusSelect(orderCode, dtoTag);
                /*ModelMapper modelMapper = new ModelMapper();
                OfcOrderDTO ofcOrderDTO = modelMapper.map(ofcFundamentalInformation,OfcOrderDTO.class);
                return ofcOrderDTO;
            }
        }else{
            return null;//再做处理
        }
    }
}
