package com.xescm.ofc.feign.client;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.wms.AddressDto;
import com.xescm.ofc.feign.api.wms.AddressCodeInterface;
import com.xescm.ofc.web.jwt.AuthRequestInterceptor;
import feign.Feign;
import feign.RetryableException;
import feign.jackson.JacksonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hiyond on 2016/11/23.
 */
@Service
public class FeignAddressCodeClient {

    private static final Logger logger = LoggerFactory.getLogger(FeignAddressInterfaceClient.class);

    @Resource
    RestConfig restConfig;
    @Resource
    private AuthRequestInterceptor authRequestInterceptor;


    public AddressCodeInterface getApi() {
        AddressCodeInterface res = Feign.builder()
                .requestInterceptor(authRequestInterceptor)
                .encoder(new JacksonEncoder())
                .target(AddressCodeInterface.class, restConfig.getAddrUrl());
        return res;
    }

    public String findCodeByName(AddressDto addressDto) {
        String result = null;
        logger.debug("==>根据省市区名称获取编码 addressDto={}", addressDto);
        if(null == addressDto){
            throw new BusinessException("参数为空");
        }
        try {
            result = getApi().findCodeByName(addressDto);
        } catch (RetryableException ex) {
            logger.error("==>调用接口发生异常：调用根据省市区名称获取编码接口(/api/addr/citypicker/findCodeByName)无法连接或超时. {}", ex);
            throw new BusinessException("调用根据省市区名称获取编码接口无法连接或超时！");
        } catch (Exception ex) {
            logger.error("==>调用接口发生异常：根据省市区名称获取编码接口(/api/addr/citypicker/findCodeByName). {}", ex);
            throw new BusinessException("调用根据省市区名称获取编码接口异常！");
        }
        return result;
    }

}
