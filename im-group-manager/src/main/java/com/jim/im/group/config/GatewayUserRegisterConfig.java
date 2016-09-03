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
 */package com.jim.im.group.config;

import java.util.HashMap;
import java.util.Map;

import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.core.remoting.impl.netty.TransportConstants;
import org.hornetq.jms.client.HornetQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;

import com.jim.im.consts.IMConstant;
import com.jim.im.group.rpc.UserRegisterListener;

/**
 * 
 * @date 2016年6月14日
 * @version 1.0.0
 */
@Configuration
public class GatewayUserRegisterConfig {
	@Value("${hornetq.host}")
	private String hornetq_host;

	@Value("${hornetq.port}")
	private String hornetq_port;

	@Autowired
	private UserRegisterListener userRegisterListener;

	@Bean
	public MessageListenerContainer hornetQMessageListenerContainer() {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put(TransportConstants.HOST_PROP_NAME, hornetq_host);  
        map.put(TransportConstants.PORT_PROP_NAME, hornetq_port);
		TransportConfiguration transportConfiguration =new TransportConfiguration(
				NettyConnectorFactory.class.getName(), map);
		
		HornetQConnectionFactory hornetQConnectionFactory = HornetQJMSClient.createConnectionFactoryWithoutHA(
				JMSFactoryType.CF, transportConfiguration);
		
		DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
		container.setConnectionFactory((ConnectionFactory) hornetQConnectionFactory);
		container.setDestination(HornetQJMSClient.createTopic(IMConstant.GATEWAY_USERREGISTER_TOPIC));
		container.setPubSubDomain(true);
		container.setPubSubNoLocal(true);
		container.setMessageListener(userRegisterListener);
		container.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
		return container;
	}

}
