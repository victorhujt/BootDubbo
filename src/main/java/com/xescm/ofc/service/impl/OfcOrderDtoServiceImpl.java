package com.xescm.ofc.service.impl;

import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * Created by lyh on 2016/10/13.
 * 从4张表中根据code查出订单的4张数据,然后映射到OrderDto中
 */
@Service
@Transactional
public class OfcOrderDtoServiceImpl implements OfcOrderDtoService {
    @Resource
    private OfcOrderStatusService ofcOrderStatusService;
    @Resource
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Resource
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;
    @Resource
    private OfcWarehouseInformationService ofcWarehouseInformationService;
    @Resource
    private OfcOrderNewstatusService ofcOrderNewstatusService;
    @Resource
    private OfcFinanceInformationService ofcFinanceInformationService;

    @Override
    public OfcOrderDTO orderDtoSelect(String code, String dtoTag) {
        String orderCode="";
        String custOrderCode;
        String transCode;
        if(!PubUtils.trimAndNullAsEmpty(code).equals("")){
            switch (dtoTag) {
                case "orderCode":
                    orderCode = code;
                    break;
                case "custOrderCode":
                    custOrderCode = code;
                    //然后拿着这个custOrderCode去数据库, 找到对应的OrderCode
                    orderCode = ofcFundamentalInformationService.getOrderCodeByCustOrderCode(custOrderCode);
                    break;
                case "transCode":
                    transCode = code;
                    //然后拿着这个transCode数据库关联基本列表, 找到对应的OrderCode,
                    ///BUG
                    orderCode = ofcDistributionBasicInfoService.getOrderCodeByTransCode(transCode);
                    break;
            }
            if(PubUtils.isSEmptyOrNull(orderCode)){//如果找不到对应的code,就提示直接提示错误.
                throw new BusinessException("找不到该订单编号");
            }else{
                OfcOrderDTO ofcOrderDTO = new OfcOrderDTO();
                try {
                    OfcFundamentalInformation ofcFundamentalInformation =  ofcFundamentalInformationService.selectByKey(orderCode);
                    OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoService.distributionBasicInfoSelect(orderCode);
                    OfcWarehouseInformation ofcWarehouseInformation = ofcWarehouseInformationService.warehouseInformationSelect(orderCode);
                    OfcOrderStatus ofcOrderStatus = ofcOrderStatusService.orderStatusSelect(orderCode, dtoTag);
                    BeanUtils.copyProperties(ofcOrderStatus,ofcOrderDTO);
                    BeanUtils.copyProperties(ofcFundamentalInformation,ofcOrderDTO);

                    ofcOrderDTO.setOrderStatus(ofcOrderStatus.getOrderStatus());
                   if(!PubUtils.isSEmptyOrNull(ofcDistributionBasicInfo.getOrderCode())){
                       BeanUtils.copyProperties(ofcDistributionBasicInfo,ofcOrderDTO);

                   }
                    if(!PubUtils.isSEmptyOrNull(ofcWarehouseInformation.getOrderCode())){
                        BeanUtils.copyProperties(ofcWarehouseInformation,ofcOrderDTO);

                    }
                } catch (BusinessException ex){
                    throw new BusinessException("查询订单失败", ex);
                }catch (Exception ex){
                    throw new BusinessException(ex.getMessage(), ex);
                }

                return ofcOrderDTO;
            }
        }else{
            throw new BusinessException("订单编号为空");
        }
    }

    @Override
    public OfcOrderDTO transOrderDotSelect(String orderCode) throws InvocationTargetException {
        /**如果找不到对应的code,就提示直接提示错误**/
        if(PubUtils.isSEmptyOrNull(orderCode)){
            throw new BusinessException("找不到该订单编号");
        }else{
            OfcOrderDTO ofcOrderDTO = new OfcOrderDTO();
            try {
                OfcFundamentalInformation ofcFundamentalInformation =  ofcFundamentalInformationService.selectByKey(orderCode);
                OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoService.selectByKey(orderCode);
                OfcFinanceInformation ofcFinanceInformation = ofcFinanceInformationService.selectByKey(orderCode);
                OfcOrderNewstatus ofcOrderNewstatus = ofcOrderNewstatusService.selectByKey(orderCode);
                if(null!=ofcFinanceInformation){
                    BeanUtils.copyProperties(ofcFinanceInformation,ofcOrderDTO);
                }
                if(null!=ofcFundamentalInformation){
                    BeanUtils.copyProperties(ofcFundamentalInformation,ofcOrderDTO);

                }
                if(null!=ofcDistributionBasicInfo){
                    BeanUtils.copyProperties(ofcDistributionBasicInfo,ofcOrderDTO);

                }
                ofcOrderDTO.setOrderStatus(ofcOrderNewstatus.getOrderLatestStatus());
            } catch (BusinessException ex){
                throw new BusinessException("查询订单失败", ex);
            }catch (Exception ex){
                throw new BusinessException(ex.getMessage(), ex);
            }
            return ofcOrderDTO;
        }
    }
}
