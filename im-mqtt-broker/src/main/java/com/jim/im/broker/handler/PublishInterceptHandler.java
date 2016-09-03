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
package com.jim.im.broker.handler;

import com.jim.im.broker.repo.BrokerMessageRepo;
import com.jim.im.consts.IMConstant;
import com.jim.im.mongo.entity.msg.ImMessage;
import io.moquette.broker.interception.AbstractInterceptHandler;
import io.moquette.broker.interception.messages.InterceptPublishMessage;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.MessageConverter;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.math.BigInteger;

/**
 * @version 1.0
 * @since 2016-06-16
 */
public class PublishInterceptHandler extends AbstractInterceptHandler {
	private JmsTemplate jmsTemplate;
	private BrokerMessageRepo messageRepo;
	private String brokerName;

	public PublishInterceptHandler(JmsTemplate jmsTemplate, BrokerMessageRepo messageRepo) {
		this.jmsTemplate = jmsTemplate;
		this.messageRepo = messageRepo;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	@Override
	public void onPublish(InterceptPublishMessage msg) {
		BigInteger messageId = new BigInteger(msg.getPayload().array());
		final ImMessage imMessage = messageRepo.findOne(messageId);
		final ActiveMQTopic mqTopicName = new ActiveMQTopic(IMConstant.MQ_FORWARD_TOPIC_NAME);
		MessageConverter messageConverter = jmsTemplate.getMessageConverter();
		jmsTemplate.send(mqTopicName, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				Message message = messageConverter.toMessage(imMessage, session);
				message.setStringProperty(IMConstant.BROKER_NAME, brokerName);
				return message;
			}
		});
	}
}
