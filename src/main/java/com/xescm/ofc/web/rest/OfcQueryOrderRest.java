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
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.Random;
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


    @RequestMapping(value = "getCaptcha", method = {RequestMethod.POST})
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response){
        int width = 200;// 验证码图片宽
        int height = 60;// 验证码图片高
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = null;
        ServletOutputStream sos = null;
        try {
            g = image.getGraphics();
            Random random = new Random();// 创建
            g.setColor(getRandColor(200, 250));
            g.fillRect(0, 0, width, height);
            g.setColor(getRandColor(0, 255));
            g.drawRect(0, 0, width - 1, height - 1);
            g.setColor(getRandColor(160, 200));// 随机产生5条干扰线，使图象中的认证码不易被其它程序探测
            for (int i = 0; i < 8; i++) {
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                int x1 = random.nextInt(width);
                int y1 = random.nextInt(height);
                g.drawLine(x, y, x1, y1);
            }
            g.setColor(getRandColor(160, 200));// 随机产生100点，使图象中的认证码不易被其它程序探测到
            for (int i = 0; i < 100; i++) {
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                g.drawLine(x, y, x, y);
            }
            Font font = new Font("Consolas", Font.ITALIC, 50);
            g.setFont(font);// 设置字体
            int length = 6; // 设置默认生成4个验证码
            String s = "abcdefghjklmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789"; // 设置包括"a-z"和数0-9"
            String sRand = "";
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            for (int i = 0; i < length; i++) {
                String ch = String.valueOf(s.charAt(random.nextInt(s.length())));
                sRand += ch;
                g.drawString(ch, 30 * i + 15, (random.nextInt(5) - 2) * i + 45);
            }
            g.dispose();// 图像生效
            // 禁止图像缓存
            response.reset();
            response.resetBuffer();
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            // 创建二进制的输出
            sos = response.getOutputStream();
            // 将图像输出到Servlet输出
            ImageIO.write(image, "jpeg", sos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                g.dispose();
            } finally {
                if (sos != null) {
                    try {
                        sos.flush();
                        sos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private Color getRandColor(int lower, int upper) {
        Random random = new Random();
        if (upper > 255)
            upper = 255;
        if (upper < 1)
            upper = 1;
        if (lower < 1)
            lower = 1;
        if (lower > 255)
            lower = 255;
        int r = lower + random.nextInt(upper - lower);
        int g = lower + random.nextInt(upper - lower);
        int b = lower + random.nextInt(upper - lower);
        return new Color(r, g, b);
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
