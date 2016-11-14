/**
 * Copyright (C) 2010-2016 Alibaba Group Holding Limited
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xescm.ofc.mq.consumer;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.xescm.ofc.domain.OfcPlanFedBackCondition;
import com.xescm.ofc.domain.OfcPlanFedBackResult;
import com.xescm.ofc.domain.OfcTransplanNewstatus;
import com.xescm.ofc.domain.dto.rmc.RmcCompanyLineVo;
import com.xescm.ofc.enums.MqTopicConstants;
import com.xescm.ofc.service.OfcPlanFedBackService;
import com.xescm.ofc.service.OfcTransplanNewstatusService;
import com.xescm.ofc.utils.PubUtils;
import com.xescm.ofc.utils.JSONUtils;
import com.xescm.uam.constant.UamConstant;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.exception.BusinessException;
import com.xescm.uam.utils.PublicUtil;
import com.xescm.uam.utils.ThreadLocalMap;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static javafx.scene.input.KeyCode.M;

/**
 * MQ消息处理类
 */
@Service
public class MessageListenerImpl implements MessageListener {

    @Autowired
    private OfcPlanFedBackService ofcPlanFedBackService;

    @Override
    public Action consume(Message msg, ConsumeContext consumeContext) {
        Logger logger = LoggerFactory.getLogger(MessageListenerImpl.class);
        AuthResDto authResDto = (AuthResDto) ThreadLocalMap.get(UamConstant.TOKEN_AUTH_DTO);
        if(PublicUtil.isEmpty(authResDto)){
            WrapMapper.wrap(Wrapper.ERROR_CODE,"验证token失败");
        }
        String message = new String(msg.getBody());
        String topicName = msg.getTopic();
        if(!PubUtils.isNull(message)&&!PubUtils.isNull(topicName)){
            if(MqTopicConstants.OFC.equals(topicName)){
                logger.info("OFC订单信息消费："+message);
            }else if(MqTopicConstants.TFC.equals(topicName)){
                logger.info("TFC运输中心消费 ："+message);
                // 将获取的json格式字符串转换成相应对象
                OfcPlanFedBackCondition ofcPlanFedBackCondition = null;
                try {
                    ofcPlanFedBackCondition = (OfcPlanFedBackCondition) JSONUtils.jsonToPojo(message , OfcPlanFedBackCondition.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 保存到数
                Wrapper<List<OfcPlanFedBackResult>> rmcCompanyLists = ofcPlanFedBackService.planFedBack(ofcPlanFedBackCondition,authResDto);
                logger.debug("abc");
            }
        }else{
            return Action.ReconsumeLater;
        }

        //如果想测试消息重投的功能,可以将Action.CommitMessage 替换成Action.ReconsumeLater
        return Action.CommitMessage;
    }
}