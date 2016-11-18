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
import com.xescm.ofc.domain.OfcSchedulingSingleFeedbackCondition;
import com.xescm.ofc.service.OfcPlanFedBackService;
import com.xescm.ofc.utils.JSONUtils;
import com.xescm.ofc.utils.PubUtils;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.Wrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MQ消息处理类
 */
@Service
public class SchedulingSingleFedbackImpl extends BaseController implements MessageListener {

    @Autowired
    private OfcPlanFedBackService ofcPlanFedBackService;

    @Override
    public Action consume(Message msg, ConsumeContext consumeContext) {
        Logger logger = LoggerFactory.getLogger(SchedulingSingleFedbackImpl.class);
        //AuthResDto authResDtoByToken = getAuthResDtoByToken();
        String userName="";//authResDtoByToken.getUamUser().getUserName();
        String message = new String(msg.getBody());
        String topicName = msg.getTopic();
        if(!PubUtils.isNull(message)&&!PubUtils.isNull(topicName)){
            if(msg.getTag().equals("deliveryTag")){
                logger.info("调度单："+message);
                List<OfcSchedulingSingleFeedbackCondition> ofcSchedulingSingleFeedbackConditions = null;
                try {
                    ofcSchedulingSingleFeedbackConditions= JSONUtils.jsonToList(message , OfcSchedulingSingleFeedbackCondition.class);
                    for(int i=0;i<ofcSchedulingSingleFeedbackConditions.size();i++){
                        // 保存到数
                        Wrapper<List<OfcPlanFedBackResult>> rmcCompanyLists = ofcPlanFedBackService.schedulingSingleFeedback(ofcSchedulingSingleFeedbackConditions.get(i),userName);
                    }
                } catch (Exception e) {
                    logger.info(e.getMessage());
                }
            }else if(msg.getTag().equals("transportTag")){
                logger.info("运输单消费 ："+message);
                // 将获取的json格式字符串转换成相应对象
                List<OfcPlanFedBackCondition> ofcPlanFedBackConditions = null;
                try {
                    ofcPlanFedBackConditions= JSONUtils.jsonToList(message , OfcPlanFedBackCondition.class);
                    for(int i=0;i<ofcPlanFedBackConditions.size();i++){
                        // 保存到数
                        Wrapper<List<OfcPlanFedBackResult>> rmcCompanyLists = ofcPlanFedBackService.planFedBack(ofcPlanFedBackConditions.get(i),userName);
                    }
                } catch (Exception e) {
                    logger.info(e.getMessage());
                }
            }
        }else{
            return Action.ReconsumeLater;
        }

        //如果想测试消息重投的功能,可以将Action.CommitMessage 替换成Action.ReconsumeLater
        return Action.CommitMessage;
    }

    /*public MessageListenerImpl(String userName) {
    }*/

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}