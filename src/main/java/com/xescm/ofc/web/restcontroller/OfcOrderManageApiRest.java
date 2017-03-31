package com.xescm.ofc.web.restcontroller;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.dingtalk.dto.robot.AtDto;
import com.xescm.dingtalk.dto.robot.ChatRobotMsgDto;
import com.xescm.dingtalk.dto.robot.MarkdownDto;
import com.xescm.dingtalk.enums.robot.MsgTypeEnum;
import com.xescm.ofc.config.DingDingRobotConfig;
import com.xescm.ofc.edas.model.dto.ofc.OfcOrderAccountDTO;
import com.xescm.ofc.service.OfcDailyAccountsService;
import com.xescm.ofc.service.OfcOrderManageService;
import com.xescm.uam.provider.ChatRobotEdasService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hujintao on 2017/3/28.
 */
@RestController
@RequestMapping(value = "/api/ofc", produces = {"application/json;charset=UTF-8"})
public class OfcOrderManageApiRest {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private OfcOrderManageService ofcOrderManageService;
    @Resource
    private ChatRobotEdasService chatRobotEdasService;
    @Resource
    private DingDingRobotConfig dingDingRobotConfig;
    @Resource
    private Configuration configuration;
    @Resource
    private OfcDailyAccountsService ofcDailyAccountsService;

    @ResponseBody
    @RequestMapping(value = "/dailyAccount", method = RequestMethod.POST)
    public void  dailyAccount() {
        logger.info("平台使用情况日报统计开始......");
        List<OfcOrderAccountDTO> accountDaily=ofcOrderManageService.dailyAccount();
        if(CollectionUtils.isEmpty(accountDaily)){
            logger.info("平台使用情况日报统计为空");
            return;
        }
        Map<String, Object> model = new HashMap<>();
        model.put("dailyAccountInfo",accountDaily);
        String content="";
        try {
            Template t = configuration.getTemplate("dailyAccount.ftl");
            content = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        } catch (IOException e) {
            logger.error("freeMark获取模板异常",e);
        } catch (TemplateException e) {
            logger.error("freeMark获取模板异常",e);
        }
        if(PubUtils.isSEmptyOrNull(content)){
            logger.info("平台使用情况日报统计内容为空");
            return;
        }
        logger.info("平台使用情况日报markDown内容为:{}",content);
        //发送到钉钉机器人
        ChatRobotMsgDto chatRobotMsgDto=new ChatRobotMsgDto();
        chatRobotMsgDto.setWebhookToken(dingDingRobotConfig.getAccessToken());
        chatRobotMsgDto.setMsgType(MsgTypeEnum.MARKDOWN.getType());
        MarkdownDto markdownDto=new MarkdownDto();
        AtDto atDto=new AtDto();
        atDto.setAtAll(dingDingRobotConfig.isAtAll());
        String[] phones=dingDingRobotConfig.getAtMobiles().split(",");
        atDto.setAtMobiles(phones);
        chatRobotMsgDto.setAt(atDto);
        markdownDto.setText(content);
        markdownDto.setTitle("平台日报红黑榜");
        chatRobotMsgDto.setMarkdown(markdownDto);
        logger.info("发送到钉钉机器人");
        Wrapper<Boolean> success=chatRobotEdasService.sendChatRobotMsg(chatRobotMsgDto);
        if(success.getCode()==Wrapper.SUCCESS_CODE){
            if(success.getResult()){
                logger.info("钉钉机器人发送成功");
            }else{
                logger.info("钉钉机器人发送失败");
            }
        }else{
            logger.info("钉钉机器人发送失败");
        }
    }


}
