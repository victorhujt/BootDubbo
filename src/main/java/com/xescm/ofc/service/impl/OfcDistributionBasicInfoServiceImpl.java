package com.xescm.ofc.service.impl;


import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcWarehouseInformation;
import com.xescm.ofc.mapper.OfcDistributionBasicInfoMapper;
import com.xescm.ofc.service.OfcDistributionBasicInfoService;
import com.xescm.rmc.edas.domain.dto.RmcWarehouseDto;
import com.xescm.rmc.edas.domain.vo.RmcWarehouseRespDto;
import com.xescm.rmc.edas.service.RmcWarehouseEdasService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xescm.core.utils.PubUtils.trimAndNullAsEmpty;
import static com.xescm.ofc.constant.OrderConstant.STOCK_IN_ORDER;
import static com.xescm.ofc.constant.OrderConstant.STOCK_OUT_ORDER;

/**
 *
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcDistributionBasicInfoServiceImpl extends BaseService<OfcDistributionBasicInfo> implements OfcDistributionBasicInfoService{
    @Resource
    private OfcDistributionBasicInfoMapper ofcDistributionBasicInfoMapper;
    @Resource
    private RmcWarehouseEdasService rmcWarehouseEdasService;

    @Override
    public int deleteByOrderCode(Object key) {

        return ofcDistributionBasicInfoMapper.deleteByOrderCode(key);
    }

    @Override
    public int updateByOrderCode(Object key) {
        return ofcDistributionBasicInfoMapper.updateByOrderCode(key);
    }

    @Override
    public OfcDistributionBasicInfo distributionBasicInfoSelect(String orderCode) {
        OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoMapper.ofcDistributionBasicInfoSelect(orderCode);
        if(ofcDistributionBasicInfo == null){
            return new OfcDistributionBasicInfo();
        }
        return ofcDistributionBasicInfo;
    }

    @Override
    public String getOrderCodeByTransCode(String transCode) {
//        String custCode = "001";
       // Map<String,String> mapperMap = new HashMap<String,String>();
        Map<String,String> mapperMap = new HashMap<>();
//        mapperMap.put("custCode",custCode);
        mapperMap.put("transCode",transCode);
        List<String> orderCode = ofcDistributionBasicInfoMapper.getOrderCodeByTransCode(mapperMap);
        return orderCode.get(0);
    }

    @Override
    public String getKabanOrderCodeByTransCode(String transCode) {
        Map<String,String> mapperMap = new HashMap<>();
        mapperMap.put("transCode",transCode);
        List<String> orderCode = ofcDistributionBasicInfoMapper.getKabanOrderCodeByTransCode(mapperMap);
        if(orderCode.size() > 0){
            return orderCode.get(0);
        }else{
            return null;
        }
    }

    @Override
    public String getLastedKabanOrderCodeByTransCode(String transCode) {
        Map<String,String> mapperMap = new HashMap<>();
        mapperMap.put("transCode",transCode);
        List<String> orderCodeList = ofcDistributionBasicInfoMapper.getKabanOrderCodeByTransCode(mapperMap);
        if(orderCodeList.size() > 0){
            String lastedOrderCode = orderCodeList.get(0);
            for(String orderCode : orderCodeList){
                if(orderCode.compareTo(lastedOrderCode) == 1){
                    lastedOrderCode = orderCode;
                }
            }
            return lastedOrderCode;
        }else{
            return null;
        }
    }

    @Override
    public Wrapper<?> validateDistrictContactMessage(CscContantAndCompanyDto cscContantAndCompanyDtoConsignor, CscContantAndCompanyDto cscContantAndCompanyDtoConsignee) {
        if(null == cscContantAndCompanyDtoConsignor || null == cscContantAndCompanyDtoConsignee){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"校验收货方信息入参为空");
        }
        if(null == cscContantAndCompanyDtoConsignor.getCscContactCompanyDto() || null == cscContantAndCompanyDtoConsignee.getCscContactCompanyDto()){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"校验收货方信息入参收货方信息为空");
        }
        if(null == cscContantAndCompanyDtoConsignor.getCscContactDto() || null == cscContantAndCompanyDtoConsignee.getCscContactDto()){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"校验收货方信息入参收货方联系人信息为空");
        }
        if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignor.getCscContactCompanyDto().getContactCompanyName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"请输入发货方信息");
        }
        if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignor.getCscContactDto().getContactName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"发货方联系人名称未填写");
        }
        if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignor.getCscContactDto().getPhone())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"发货方联系人电话未填写");
        }
        //二级地址还需特殊处理
        if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignor.getCscContactDto().getProvinceName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"发货方联系人地址未选择");
        }
        if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignor.getCscContactDto().getCityName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"发货方联系人地址不完整");
        }
        /*if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignor.getCscContact().getAreaName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"发货方联系人地址不完整");
        }*/

        if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignee.getCscContactCompanyDto().getContactCompanyName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"请输入收货方信息");
        }
        if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignee.getCscContactDto().getContactName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"收货方联系人名称未填写");
        }
        if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignee.getCscContactDto().getPhone())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"收货方联系人电话未填写");
        }
        if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignee.getCscContactDto().getProvinceName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"收货方联系人地址未选择");
        }
        if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignee.getCscContactDto().getCityName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"收货方联系人地址不完整");
        }
        /*if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignee.getCscContact().getAreaName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"收货方联系人地址不完整");
        }*/
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE);
    }


    @Override
    public int checkTransCode(OfcDistributionBasicInfo ofcDistributionBasicInfo) {
        return ofcDistributionBasicInfoMapper.checkTransCode(ofcDistributionBasicInfo);
    }

    @Override
    public OfcDistributionBasicInfo queryByOrderCode(String orderCode) {
        return ofcDistributionBasicInfoMapper.queryByOrderCode(orderCode);
    }

    @Override
    public int updateAddressByOrderCode(Object key) {
        return ofcDistributionBasicInfoMapper.updateAddressByOrderCode(key);
    }

    /**
     * 仓储订单 通过仓库编码填充收发货方地址
     * @param ofcWarehouseInformation 仓库信息
     * @param ofcDistributionBasicInfo  地址
     * @param ofcFundamentalInformation 订单基本信息
     * @return
     */
    @Override
    public OfcDistributionBasicInfo fillAddress(OfcWarehouseInformation ofcWarehouseInformation, OfcDistributionBasicInfo ofcDistributionBasicInfo, OfcFundamentalInformation ofcFundamentalInformation) {
        StringBuilder sb = new StringBuilder();
        RmcWarehouseDto rmcWarehouseDto = new RmcWarehouseDto();
        rmcWarehouseDto.setWarehouseCode(ofcWarehouseInformation.getWarehouseCode());
        Wrapper<RmcWarehouseRespDto> wareHouse = rmcWarehouseEdasService.queryRmcWarehouseByCode(rmcWarehouseDto);
        if (wareHouse.getCode() == Wrapper.SUCCESS_CODE) {
            RmcWarehouseRespDto resp = wareHouse.getResult();
            if (!StringUtils.isEmpty(resp.getStreetCode())) {
                sb.append(",").append(resp.getStreetCode());
            }
            if (trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0, 2).equals(STOCK_OUT_ORDER)) {
                ofcDistributionBasicInfo.setConsignorName(resp.getWarehouseName());
                ofcDistributionBasicInfo.setConsignorCode(resp.getWarehouseCode());
                ofcDistributionBasicInfo.setConsignorContactName(resp.getWarehouseName());
                ofcDistributionBasicInfo.setConsignorContactCode(resp.getWarehouseCode());
                ofcDistributionBasicInfo.setDeparturePlaceCode(sb.toString());
                ofcDistributionBasicInfo.setDeparturePlace(resp.getDetailAddress());
                ofcDistributionBasicInfo.setDepartureProvince(resp.getProvince());
                ofcDistributionBasicInfo.setDepartureCity(resp.getCity());
                ofcDistributionBasicInfo.setDepartureDistrict(resp.getArea());
                if (!StringUtils.isEmpty(resp.getStreet())) {
                    ofcDistributionBasicInfo.setDepartureTowns(resp.getStreet());
                }
            } else if (trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0, 2).equals(STOCK_IN_ORDER)) {
                ofcDistributionBasicInfo.setConsigneeName(resp.getWarehouseName());
                ofcDistributionBasicInfo.setConsigneeCode(resp.getWarehouseCode());
                ofcDistributionBasicInfo.setConsigneeContactName(resp.getWarehouseName());
                ofcDistributionBasicInfo.setConsigneeContactCode(resp.getWarehouseCode());
                ofcDistributionBasicInfo.setDestinationCode(sb.toString());
                ofcDistributionBasicInfo.setDestination(resp.getDetailAddress());
                ofcDistributionBasicInfo.setDestinationProvince(resp.getProvince());
                ofcDistributionBasicInfo.setDestinationCity(resp.getCity());
                ofcDistributionBasicInfo.setDestinationDistrict(resp.getArea());
                if (!StringUtils.isEmpty(resp.getStreet())) {
                    ofcDistributionBasicInfo.setDestinationTowns(resp.getStreet());
                }
            }
        }
        return ofcDistributionBasicInfo;
    }
}
