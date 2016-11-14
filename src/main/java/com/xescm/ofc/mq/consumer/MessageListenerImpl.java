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
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.utils.JSONUtils;
import com.xescm.ofc.utils.PubUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * MQ消息处理类
 */
@Service
public class MessageListenerImpl implements MessageListener {
    @Override
    public Action consume(Message msg, ConsumeContext consumeContext) {
        Logger logger = LoggerFactory.getLogger(MessageListenerImpl.class);
        String message = new String(msg.getBody());
        String topicName = msg.getTopic();
        System.out.println(message);
        if(!PubUtils.isNull(message)&&!PubUtils.isNull(topicName)){
            // 将获取的json格式字符串转换成相应对象
            OfcFundamentalInformation ofcFundamentalInformation = null;
            try {
                ofcFundamentalInformation = (OfcFundamentalInformation) JSONUtils.jsonToPojo(message , OfcFundamentalInformation.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            return Action.ReconsumeLater;
        }

        //如果想测试消息重投的功能,可以将Action.CommitMessage 替换成Action.ReconsumeLater
        return Action.CommitMessage;
    }

}