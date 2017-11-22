package com.xescm.ofc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * Created by lyh on 2017/9/11.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ValidParam {

    ValidType validType() default ValidType.CHECK_AUTH;

    enum ValidType{
        /**
         * 校验当前登录用户是否有权限操作
         */
        CHECK_AUTH,
        /**
         * 校验用户权限,并添加客户信息
         */
        ADD_CUSTOMER_MSG
    }



}
