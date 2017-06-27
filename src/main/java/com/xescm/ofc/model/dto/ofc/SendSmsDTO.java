package com.xescm.ofc.model.dto.ofc;

import com.xescm.ofc.enums.SmsTemplatesEnum;
import lombok.Data;

@Data
public class SendSmsDTO {
    //模板编码
    private SmsTemplatesEnum template;
    //参数
    private String paramStr;
    //手机号
    private String number;
    //验证码
    private String code;

}
