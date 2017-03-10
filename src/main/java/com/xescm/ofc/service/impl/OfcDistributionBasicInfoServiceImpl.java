package com.xescm.ofc.service.impl;


import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.ofc.mapper.OfcDistributionBasicInfoMapper;
import com.xescm.ofc.service.OfcDistributionBasicInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcDistributionBasicInfoServiceImpl extends BaseService<OfcDistributionBasicInfo> implements OfcDistributionBasicInfoService{
    @Resource
    private OfcDistributionBasicInfoMapper ofcDistributionBasicInfoMapper;

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
}
