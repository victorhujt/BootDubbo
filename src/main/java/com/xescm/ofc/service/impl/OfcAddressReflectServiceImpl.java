package com.xescm.ofc.service.impl;

import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OfcAddressReflect;
import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.ofc.mapper.OfcAddressReflectMapper;
import com.xescm.ofc.service.OfcAddressReflectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * Created by lyh on 2017/3/24.
 */
@Service
public class OfcAddressReflectServiceImpl extends BaseService<OfcAddressReflect> implements OfcAddressReflectService{

    @Resource
    private OfcAddressReflectMapper ofcAddressReflectMapper;

    @Override
    public OfcAddressReflect selectByAddress(String address) {
        OfcAddressReflect forSearch = new OfcAddressReflect();
        forSearch.setAddress(address);
        return ofcAddressReflectMapper.selectOne(forSearch);
    }

    /**
     * 将保存的地址映射信息映射到运输信息表
     * @param ofcAddressReflect 地址映射
     * @param ofcDistributionBasicInfo 运输信息
     * @param tag 标志位
     */
    @Override
    public void reflectAddressToDis(OfcAddressReflect ofcAddressReflect, OfcDistributionBasicInfo ofcDistributionBasicInfo, String tag) {
        logger.info("将保存的地址映射信息映射到运输信息表: ofcAddressReflect {}", ofcAddressReflect);
        logger.info("将保存的地址映射信息映射到运输信息表: ofcDistributionBasicInfo {}", ofcDistributionBasicInfo);
        logger.info("将保存的地址映射信息映射到运输信息表: tag{}", tag);
        String province = ofcAddressReflect.getProvince();
        String provinceCode = ofcAddressReflect.getProvinceCode();
        String city = ofcAddressReflect.getCity();
        String cityCode = ofcAddressReflect.getCityCode();
        String district = ofcAddressReflect.getDistrict();
        String districtCode = ofcAddressReflect.getDistrictCode();
        String street = ofcAddressReflect.getStreet();
        String streetCode = ofcAddressReflect.getStreetCode();
        StringBuilder sb = new StringBuilder();
        if(!PubUtils.isSEmptyOrNull(provinceCode)){
            sb.append(provinceCode);
            if(!PubUtils.isSEmptyOrNull(cityCode)){
                sb.append(cityCode);
                if(!PubUtils.isSEmptyOrNull(districtCode)){
                    sb.append(districtCode);
                    if(!PubUtils.isSEmptyOrNull(streetCode)){
                        sb.append(streetCode);
                    }
                }
            }
        }
        if(StringUtils.equals(tag, "departure")){
            ofcDistributionBasicInfo.setDepartureProvince(province);
            ofcDistributionBasicInfo.setDepartureCity(city);
            ofcDistributionBasicInfo.setDepartureDistrict(district);
            ofcDistributionBasicInfo.setDepartureTowns(street);
            ofcDistributionBasicInfo.setDeparturePlaceCode(sb.toString());
        } else if (StringUtils.equals(tag, "destination")) {
            ofcDistributionBasicInfo.setDestinationProvince(province);
            ofcDistributionBasicInfo.setDestinationCity(city);
            ofcDistributionBasicInfo.setDestinationDistrict(district);
            ofcDistributionBasicInfo.setDestinationTowns(street);
            ofcDistributionBasicInfo.setDeparturePlaceCode(sb.toString());
        }

    }

    /**
     * 将运输信息表映射到地址映射表
     * @param ofcAddressReflect 地址映射
     * @param ofcDistributionBasicInfo 运输信息
     * @param tag 标志位
     */
    @Override
    public void reflectAddressToRef(OfcAddressReflect ofcAddressReflect, OfcDistributionBasicInfo ofcDistributionBasicInfo, String tag) {
        String[] split = new String[0];
        if(StringUtils.equals(tag, "departure")){
            ofcAddressReflect.setProvince(ofcDistributionBasicInfo.getDepartureProvince());
            ofcAddressReflect.setCity(ofcDistributionBasicInfo.getDepartureCity());
            ofcAddressReflect.setDistrict(ofcDistributionBasicInfo.getDepartureDistrict());
            ofcAddressReflect.setStreet(ofcDistributionBasicInfo.getDepartureTowns());
            ofcAddressReflect.setAddress(ofcDistributionBasicInfo.getDeparturePlace());
            if(!PubUtils.isSEmptyOrNull(ofcDistributionBasicInfo.getDeparturePlaceCode())){
                split = ofcDistributionBasicInfo.getDeparturePlaceCode().split(",");
            }

        } else if (StringUtils.equals(tag, "destination")) {
            ofcAddressReflect.setProvince(ofcDistributionBasicInfo.getDestinationProvince());
            ofcAddressReflect.setCity(ofcDistributionBasicInfo.getDestinationCity());
            ofcAddressReflect.setDistrict(ofcDistributionBasicInfo.getDestinationDistrict());
            ofcAddressReflect.setStreet(ofcDistributionBasicInfo.getDestinationTowns());
            ofcAddressReflect.setAddress(ofcDistributionBasicInfo.getDestination());
            if(!PubUtils.isSEmptyOrNull(ofcDistributionBasicInfo.getDestinationCode())){
                split = ofcDistributionBasicInfo.getDestinationCode().split(",");
            }
        }
        if(split.length > 0){
            ofcAddressReflect.setProvinceCode(split[0]);
            if(split.length > 1) {
                ofcAddressReflect.setCityCode(split[1]);
                if(split.length > 2) {
                    ofcAddressReflect.setDistrictCode(split[2]);
                    if(split.length > 3) {
                        ofcAddressReflect.setStreetCode(split[3]);
                    }
                }
            }
        }
    }
}
