package com.xescm.ofc.utils;

import com.xescm.core.exception.BusinessException;
import com.xescm.ofc.enums.ResultCodeEnum;

import java.util.UUID;

/**
 * <p>Title:	  xescm-uam-service <br/> </p>
 * <p>Description TODO <br/> </p>
 * <p>Company:    http://www.hnxianyi.com  <br/> </p>
 *
 * @Author <a href="liu_zhaoming@sina.cn"/>刘兆明</a>  <br/>
 * @Date 2016/12/23 13:55
 */
public class CheckArgumentUtil {
    /**
     * 校验
     * @param result 正确结果
     * @param message 异常信息
     */
    public static void checkArgument(boolean result, String message){
        if(!result){
            throw new BusinessException(message);
        }
    }

    /**
     * 校验
     * @param result 正确结果
     * @param message 异常信息
     */
    public static void checkArgument(boolean result, ResultCodeEnum message){
        if(!result){
            throw new BusinessException(message.code(), message.msg());
        }
    }

    /**
     * @Description   生成UUID
     *
     * @param
     * @return
     * @Author        zhangfutao
     * @CreateDate    2016/12/2 12:04
     */
    public synchronized static String UUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
