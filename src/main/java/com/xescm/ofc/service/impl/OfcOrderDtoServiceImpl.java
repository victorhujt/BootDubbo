package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.PubUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by lyh on 2016/10/13.
 * 从4张表中根据code查出订单的4张数据,然后映射到OrderDto中
 */
@Service
@Transactional
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
            if(com.xescm.uam.utils.PubUtils.isSEmptyOrNull(orderCode)){//如果找不到对应的code,就提示直接提示错误.
                throw new BusinessException("找不到该订单编号");
            }else{
                OfcOrderDTO ofcOrderDTO = new OfcOrderDTO();
                try {
                    OfcFundamentalInformation ofcFundamentalInformation =  ofcFundamentalInformationService.selectByKey(orderCode);
                    OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoService.distributionBasicInfoSelect(orderCode);
                    OfcWarehouseInformation ofcWarehouseInformation = ofcWarehouseInformationService.warehouseInformationSelect(orderCode);
                    OfcOrderStatus ofcOrderStatus = ofcOrderStatusService.orderStatusSelect(orderCode, dtoTag);

                    BeanUtils.copyProperties(ofcOrderDTO,ofcOrderStatus);
                    BeanUtils.copyProperties(ofcOrderDTO,ofcFundamentalInformation);
                    ofcOrderDTO.setOrderStatus(ofcOrderStatus.getOrderStatus());
                   if(!PubUtils.isSEmptyOrNull(ofcDistributionBasicInfo.getOrderCode())){
                        BeanUtils.copyProperties(ofcOrderDTO,ofcDistributionBasicInfo);
                    }
                    if(!PubUtils.isSEmptyOrNull(ofcWarehouseInformation.getOrderCode())){
                        BeanUtils.copyProperties(ofcOrderDTO,ofcWarehouseInformation);
                    }
                } catch (IllegalAccessException e) {
                    throw new BusinessException(e.getMessage());
                } catch (InvocationTargetException e) {
                    throw new BusinessException(e.getMessage());
                } catch (BusinessException ex){
                    throw new BusinessException(ex.getMessage());
                }catch (Exception ex){
                    throw new BusinessException(ex.getMessage());
                }

                return ofcOrderDTO;
            }
        }else{
            throw new BusinessException("订单编号为空");
        }
    }
}
