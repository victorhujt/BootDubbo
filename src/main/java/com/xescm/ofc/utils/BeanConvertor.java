package com.xescm.ofc.utils;

import com.xescm.ofc.exception.BusinessException;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 *
 * Created by lyh on 2017/9/28.
 */
public class BeanConvertor {
    public static List listConvertor(List source, List target, Class<?> targetClazz) {
        try {
            for (Object o : source) {
                Object targetObj = targetClazz.newInstance();
                BeanUtils.copyProperties(o, targetObj);
                target.add(targetObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("转换异常");
        }
        return target;
    }
}
