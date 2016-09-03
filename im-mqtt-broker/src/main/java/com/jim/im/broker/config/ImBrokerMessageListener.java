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
package com.jim.im.broker.config;

import com.jim.im.consts.IMConstant;
import com.jim.im.mongo.entity.msg.ImMessage;
import com.jim.im.utils.StringUtil;
import io.moquette.broker.server.Server;
import io.moquette.parser.proto.messages.AbstractMessage;
import io.moquette.parser.proto.messages.PublishMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConverter;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @version 1.0
 */
public class ImBrokerMessageListener implements MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImBrokerMessageListener.class);
    private Server mqttServer;
    private MessageConverter messageConverter;

    private final ExecutorService executorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public ImBrokerMessageListener(Server mqttServer, MessageConverter messageConverter) {
        this.mqttServer = mqttServer;
        this.messageConverter = messageConverter;
    }

    @Override
    public void onMessage(Message message) {
        try {
            String brokerName = message.getStringProperty(IMConstant.BROKER_NAME);
            if (!StringUtil.equals(brokerName, mqttServer.getBrokerName())) {
                ImMessage imMessage = (ImMessage) messageConverter.fromMessage(message);
                final PublishMessage publishMessage = createPublishMessage(imMessage);
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        mqttServer.internalPublish(publishMessage);
                    }
                });
            }
        } catch (JMSException e) {
            LOGGER.error("From ActiveMQ message delivery failure.", e);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("From ActiveMQ message that encoding failure.", e);
        }
    }

    private PublishMessage createPublishMessage(ImMessage imMessage) throws UnsupportedEncodingException {
        PublishMessage publishMessage = new PublishMessage();
        publishMessage.setTopicName(imMessage.getTopicName());
        publishMessage.setQos(AbstractMessage.QOSType.MOST_ONE);
        ByteBuffer messageId = ByteBuffer.wrap(imMessage.getId().toString().getBytes());
        messageId.flip();
        publishMessage.setPayload(messageId);
        return publishMessage;
    }
}
