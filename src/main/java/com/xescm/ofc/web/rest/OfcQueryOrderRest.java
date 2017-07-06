package com.xescm.ofc.web.rest;

import com.google.common.collect.Maps;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.exception.BusinessException;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.epc.edas.dto.SmsCodeApiDto;
import com.xescm.epc.edas.service.EpcSendMessageEdasService;
import com.xescm.ofc.config.IpLimitRuleConfig;
import com.xescm.ofc.edas.model.dto.ofc.OfcTraceOrderDTO;
import com.xescm.ofc.edas.service.OfcOrderStatusEdasService;
import com.xescm.ofc.enums.ResultCodeEnum;
import com.xescm.ofc.service.OfcIpLimitRuleService;
import com.xescm.ofc.service.OfcValidationCodeService;
import com.xescm.ofc.utils.CheckUtils;
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.ofc.utils.IpUtils;
import com.xescm.ofc.utils.RedisOperationUtils;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.xescm.ofc.constant.OrderConstConstant.SENDSMS_REQUEST_COUNT;

/**
 * Created by hujintao on 2017/6/27.
 */
@RequestMapping(value = "/www/ofc", produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcQueryOrderRest {

    private Logger logger = LoggerFactory.getLogger(OfcQueryOrderRest.class);
    @Resource
    private OfcOrderStatusEdasService OfcOrderStatusEdasService;
    @Resource
    private OfcIpLimitRuleService ofcIpLimitRuleService;
    @Resource
    private EpcSendMessageEdasService epcSendMessageEdasService;
    @Resource
    private OfcValidationCodeService ofcValidationCodeService;
    @Resource
    private IpLimitRuleConfig ipLimitRuleConfig;
    @Resource
    private RedisOperationUtils redisOperationUtils;

    @Resource
    private CodeGenUtils codeGenUtils;
    /**
     *
     * @param code 客户订单号 或者运输单号 或者订单号
     * @return  订单号集合
     */
    @RequestMapping(value = "queryOrderByCode", method = {RequestMethod.POST})
    @ResponseBody
    public Wrapper queryOrderByCode(HttpServletRequest request, String code, String phone, String captchaCode) {
        Wrapper result;
        String ip = IpUtils.getIpAddr(request);
        try {
            CheckUtils.checkArgument(PubUtils.isSEmptyOrNull(code), ResultCodeEnum.PARAMERROR);
            //验证码发送过 手机号和验证码不能为空
            if(redisOperationUtils.hasKey("SMS:"+ip)){
                CheckUtils.checkArgument(PubUtils.isSEmptyOrNull(phone), ResultCodeEnum.PARAMERROR);
                CheckUtils.checkArgument(PubUtils.isSEmptyOrNull(captchaCode), ResultCodeEnum.PARAMERROR);
            }

            if(!PubUtils.isSEmptyOrNull(captchaCode) && !PubUtils.isSEmptyOrNull(phone)){
                if(redisOperationUtils.hasKey("SMSCODE:"+ip+":"+phone)){
                    CheckUtils.checkArgument(!(redisOperationUtils.getValue("SMSCODE:"+ip+":"+phone) == Long.parseLong(captchaCode)), ResultCodeEnum.CAPTCHACODEERROR);
                }
            }
            logger.info("订单查询 ==> code : {}", code);
            ofcIpLimitRuleService.checkLimit(redisOperationUtils,request);
            //查询结果是订单号集合
            result = OfcOrderStatusEdasService.queryOrderByCode(code);
            CheckUtils.checkArgument(result == null, ResultCodeEnum.RESULTISNULL);
            CheckUtils.checkArgument(result.getCode() == Wrapper.ERROR_CODE, ResultCodeEnum.RESULTISNULL);
            List<String> orderCodes = (List<String>) result.getResult();
            CheckUtils.checkArgument(CollectionUtils.isEmpty(orderCodes), ResultCodeEnum.RESULTISNULL);
        } catch (BusinessException ex) {
            logger.error("订单查询出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(ResultCodeEnum.getErrorCode(ex.getCode()), ex.getMessage());
        } catch (Exception ex) {
            logger.error("订单查询出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
        return result;
    }

    /**
     *
     * @param orderCode 订单号
     * @return 订单的追踪状态
     */
    @RequestMapping(value = "traceByOrderCode", method = {RequestMethod.POST})
    @ResponseBody
    public Wrapper<OfcTraceOrderDTO> traceByOrderCode(HttpServletRequest request, String orderCode) {
        Wrapper<OfcTraceOrderDTO> result;
        try {
            CheckUtils.checkArgument(PubUtils.isSEmptyOrNull(orderCode), ResultCodeEnum.PARAMERROR);
            ofcIpLimitRuleService.checkLimit(redisOperationUtils,request);
            logger.info("订单跟踪查询 ==> orderCode : {}", orderCode);
            result = OfcOrderStatusEdasService.traceByOrderCode(orderCode);
            CheckUtils.checkArgument(result == null, ResultCodeEnum.RESULTISNULL);
            CheckUtils.checkArgument(result.getCode() == Wrapper.ERROR_CODE, ResultCodeEnum.RESULTISNULL);
        }catch (BusinessException e){
            logger.error("订单跟踪查询出现异常:{}", e.getMessage(), e);
            return WrapMapper.wrap(ResultCodeEnum.getErrorCode(e.getCode()), e.getMessage());
        }catch (Exception e){
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "订单跟踪查询出现异常");
        }
        return result;
    }

    /**
     * 获取随机的图片验证码
     * @return
     */
    @RequestMapping(value = "getCaptcha", method = {RequestMethod.POST})
    @ResponseBody
    public Wrapper<?> getCaptcha(){
        Map<String,String> result = ofcValidationCodeService.getValidationCode();
        return WrapMapper.wrap(200,"生成验证码成功",result);
    }

    /**
     * 15分钟请求短信接口的次数不能超过10次
     * @param request
     * @param phone
     */
    @RequestMapping(value = "getValidateCode", method = {RequestMethod.POST})
    @ResponseBody
    public Wrapper<?> getValidateCode(HttpServletRequest request,String phone) throws Exception {
        String ip = IpUtils.getIpAddr(request);
        if(redisOperationUtils.hasKey("ip:"+ip+SENDSMS_REQUEST_COUNT)){
            Long reqCount =  redisOperationUtils.getValue("ip:"+ip+SENDSMS_REQUEST_COUNT);
            redisOperationUtils.increment("ip:"+ip+SENDSMS_REQUEST_COUNT, 1L);
            CheckUtils.checkArgument(reqCount > ipLimitRuleConfig.getSendSmsLimit(), ResultCodeEnum.OPERATIONSTOOFREQUENT);
        }else{
            redisOperationUtils.set("ip:"+ip+SENDSMS_REQUEST_COUNT,String.valueOf(1L),15L,TimeUnit.MINUTES);
        }
        String validateCode = codeGenUtils.getSmsCode(4);
        logger.info("接收验证码的手机号为:{},验证码为:{}",phone,validateCode);

        SmsCodeApiDto SmsCodeApiDto = new SmsCodeApiDto();
        SmsCodeApiDto.setMobile(phone);
        SmsCodeApiDto.setSmsTempletCode("SMS_70510558");
        Map<String, String> param = Maps.newHashMap();
        param.put("code", validateCode);
        param.put("product", "鲜易供应链");
        JSONObject json = JSONObject.fromObject(param);
        SmsCodeApiDto.setParam(json.toString());
        SmsCodeApiDto.setCode(validateCode);

        logger.info("调用短信接口的参数为:{}", JacksonUtil.toJson(SmsCodeApiDto));
        Long begin = System.currentTimeMillis();
        Wrapper result = epcSendMessageEdasService.sendSms(SmsCodeApiDto);
        Long end = System.currentTimeMillis();
        logger.info("短信接口耗时为:{}ms",(end-begin));
        logger.info("调用短信接口的响应结果为:{}", JacksonUtil.toJson(result));
        if(result.getCode() == Wrapper.SUCCESS_CODE){
            logger.info("发送到手机号的验证码成功发送，手机号为:{},验证码为:{}",phone,validateCode);
            //缓存三分钟
            if(redisOperationUtils.hasKey("SMSCODE:"+ip+":"+phone)){
                redisOperationUtils.set("SMSCODE:"+ip+":"+phone,validateCode);
            }else{
                redisOperationUtils.set("SMSCODE:"+ip+":"+phone,validateCode,3L, TimeUnit.MINUTES);
                redisOperationUtils.set("SMS:"+ip,"isSend",5L, TimeUnit.MINUTES);//ip缓存五分钟
            }
        }
        return result;
    }
}
