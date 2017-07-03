package com.xescm.ofc.service.impl;

import com.xescm.ofc.service.OfcValidationCodeService;
import com.xescm.ofc.utils.Base64Utils;
import com.xescm.ofc.utils.RandomGraphic;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 *
 * Created by hujintao on 2016/12/18.
 */
@Service
public class OfcValidationCodeServiceImpl implements OfcValidationCodeService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Map<String, String> getValidationCode() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Map<String,String> result = new HashedMap();
        String str = null;
        try {
            str = RandomGraphic.createInstance(4).drawInputstr(4,RandomGraphic.GRAPHIC_PNG,output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("-----------str:{}",str);
        result.put("str",str);
        byte[] captcha = output.toByteArray();
        String imagestr = Base64Utils.Encrypt(captcha);// 返回Base64编码过的字节数组字符串
        logger.info("-----------imagestr:{}",imagestr);
        result.put("imagestr","data:image/png;base64,"+imagestr);
        logger.info("-----------captcha:{}",captcha.toString());
        return result;
    }
}

