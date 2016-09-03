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
package com.jim.im.message.config;

import com.jim.im.consts.IMConstant;
import com.jim.im.exception.ImException;
import com.jim.im.message.service.MessageService;
import com.jim.im.mongo.entity.msg.ImMessage;
import com.jim.im.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @version 1.0
 */
@Component
public class ImMessageListener implements MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImMessageListener.class);
    @Autowired
    private MessageConverter messageConverter;
    @Autowired
    MessageService messageService;

    @Override
    public void onMessage(Message message) {
        try {
            String isFromPusher = message.getStringProperty(IMConstant.IS_FROM_PUSHER);
            if (!StringUtil.equals(isFromPusher, Boolean.TRUE.toString())) {
                ImMessage imMessage = (ImMessage) messageConverter.fromMessage(message);
                messageService.pushMessage(imMessage);
            }
        } catch (JMSException e) {
            LOGGER.error("From ActiveMQ message delivery failure.", e);
        } catch (ImException e) {
            LOGGER.error("From ActiveMQ message delivery failure.", e);
        }
    }
}
