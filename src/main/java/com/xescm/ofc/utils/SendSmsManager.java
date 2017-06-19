package com.xescm.ofc.utils;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.epc.edas.dto.SmsCodeApiDto;
import com.xescm.epc.edas.service.EpcSendMessageEdasService;
import com.xescm.ofc.constant.ResultModel;
import com.xescm.ofc.model.dto.ofc.SendSmsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Random;

@Component
public class SendSmsManager {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private EpcSendMessageEdasService epcSendMessageEdasService;


    public Wrapper sendSms(SendSmsDTO sendSmsDTO) {
        //内部调用,无限制
        logger.info("发送短信,sendSmsDTO:{}", sendSmsDTO);
        if (null == sendSmsDTO) {
            logger.error(ResultModel.ResultEnum.CODE_SMS_0001.getDesc());
            return WrapMapper.error();
        }
        return this.sendByEpc(sendSmsDTO);
    }
//
//    public Wrapper sendSms(SendSmsDTO sendSmsDTO, HttpServletRequest request) {
//        logger.info("发送短信,sendSmsDTO:{}", sendSmsDTO);
//        logger.info("发送短信,request:{}", request);
//        CheckUtils.validateParam(null != sendSmsDTO, ResultModel.ResultEnum.CODE_SMS_0001.getDesc());
//        CheckUtils.validateParam(null != request ,ResultModel.ResultEnum.CODE_SMS_0001.getDesc());
//        //对调用方进行限制
//
//
//
//        return this.sendByEpc(sendSmsDTO);
//    }
//
//
//    private void settingNum(HttpServletRequest request, SmsSetting smsSetting) {
//
//    }
//
//    private void validateNum(HttpServletRequest request, SmsSetting smsSetting) {
//
//    }


    private Wrapper sendByEpc(SendSmsDTO sendSmsDTO) {
        logger.info("发送短信入参sendSmsDTO:{}", sendSmsDTO);
        CheckUtils.validateParam(null != sendSmsDTO, ResultModel.ResultEnum.CODE_SMS_0001.getDesc());
        SmsCodeApiDto smsCodeApiDto = new SmsCodeApiDto();
        smsCodeApiDto.setCode(sendSmsDTO.getCode());
        smsCodeApiDto.setSmsTempletCode(sendSmsDTO.getTemplate().getCode());
        smsCodeApiDto.setParam(sendSmsDTO.getParamStr());
        smsCodeApiDto.setMobile(sendSmsDTO.getNumber());
        Wrapper wrapper = epcSendMessageEdasService.sendSms(smsCodeApiDto);
        logger.info("发送短信结果:{}", wrapper);
        return wrapper;
    }


    public static String getSmsCode() {
        Random random = new Random();// 创建
        String s = "abcdefghjklmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        StringBuilder validateCode = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String ch = String.valueOf(s.charAt(random.nextInt(s.length())));
            validateCode .append(ch);
        }
        return validateCode.toString();
    }


}
