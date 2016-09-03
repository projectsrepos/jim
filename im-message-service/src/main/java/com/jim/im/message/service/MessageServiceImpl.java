/*
 * Copyright 2014 Jim. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jim.im.message.service;

import com.google.common.collect.Lists;
import com.jim.im.api.LoginRpcService;
import com.jim.im.api.TopicRpcService;
import com.jim.im.consts.IMConstant;
import com.jim.im.consts.OsType;
import com.jim.im.consts.TopicType;
import com.jim.im.db.entity.login.TerminalSessionInfo;
import com.jim.im.db.entity.topic.TopicInfo;
import com.jim.im.db.entity.topic.TopicRequest;
import com.jim.im.exception.ImException;
import com.jim.im.exception.TopicRpcException;
import com.jim.im.exception.message.ApnCertException;
import com.jim.im.exception.message.ApnPushException;
import com.jim.im.message.repo.MessageRepo;
import com.jim.im.mongo.entity.msg.ImMessage;
import com.jim.im.mongo.entity.msg.IosMessage;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.List;
import java.util.Map;

/**
 * 
 * @version 1.0.0
 */
@Component
public class MessageServiceImpl implements MessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private LoginRpcService loginRpcService;
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private ApnClientUnit apnClientUnit;
    @Autowired
    private TopicRpcService topicRpcService;

    @Override
    public String pushMessage(ImMessage message) throws ImException {
        if (!message.isInternal()) {
            setTopicName(message);
            message = messageRepo.insert(message);
        }
        TerminalSessionInfo status = loginRpcService.checkConnectStatus(message.getTargetId().toString());
        OsType osType = status.getOsType();
        if (osType != null) {
            switch (osType) {
                case ANDROID:
                    if (!message.isInternal())
                        doSendMq(message);
                    break;
                case IOS:
                    if (status.getTerminalStatus().isOffline())
                        sendMessage2Apn(message.toIosMessage(), status.getDeviceToken());
                    break;
                default:
                    // ignore
            }
        }

        return message.getId().toString();
    }

    @Override
    public void systemPushMessage(ImMessage message, List<Integer> receiverIds) throws ImException {
        List<ImMessage> sendMsgList = Lists.newArrayList();
        for (Integer receiverId : receiverIds) {
            ImMessage temp = message.clone(receiverId);
            setTopicName(temp);
            temp.setInternal(true);
            sendMsgList.add(temp);
        }
        messageRepo.insert(sendMsgList);
        sendMsgList.forEach(this :: doSendMq);
    }

    private void setTopicName(ImMessage message) throws TopicRpcException {
        TopicRequest topicRequest = TopicRequest.build(message.getTargetId(),
                                                       message.getAppId(),
                                                       message.getTenantId(),
                                                       message.getTopicOwnerType(),
                                                       message.getMsgType().getTopicType());
        Map<TopicType, TopicInfo> topicInfo = topicRpcService.getTopicInfo(topicRequest);
        if(CollectionUtils.isEmpty(topicInfo)) throw new TopicRpcException("Topic Info is not exist.");
        TopicType topicType = message.getMsgType().getTopicType();
        message.setTopicName(topicInfo.get(topicType).getName());
    }

    /**
     * @param iosMsg 消息体
     * @param token 接收端IOS token
     */
    private void sendMessage2Apn(IosMessage iosMsg, String token) {
        try {
            iosMsg.setToken(token);
            apnClientUnit.sendMsg(iosMsg);
        } catch (ApnCertException e) {
            LOGGER.error("Apn cert invalidate, msgId:" + iosMsg.getId(), e);
        } catch (ApnPushException e) {
            LOGGER.error("Failed to send message because of ApnPushException, msgId:" + iosMsg.getId(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * @param message 消息体
     */
    protected void doSendMq(final ImMessage message) {
        final ActiveMQTopic mqTopicName = new ActiveMQTopic(IMConstant.MQ_FORWARD_TOPIC_NAME);
        MessageConverter messageConverter = jmsTemplate.getMessageConverter();
        jmsTemplate.send(mqTopicName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message innerMessage = messageConverter.toMessage(message, session);
                innerMessage.setStringProperty(IMConstant.IS_FROM_PUSHER, Boolean.TRUE.toString());
                return innerMessage;
            }
        });
    }
}
