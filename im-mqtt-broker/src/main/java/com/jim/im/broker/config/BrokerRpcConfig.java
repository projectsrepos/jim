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

import com.jim.im.api.LoginRpcService;
import com.jim.im.broker.handler.LoginInterceptHandler;
import com.jim.im.broker.handler.PublishInterceptHandler;
import com.jim.im.broker.repo.BrokerMessageRepo;
import com.jim.im.config.GenericMQConfig;
import com.jim.im.consts.IMConstant;
import io.moquette.broker.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.remoting.JmsInvokerProxyFactoryBean;
import org.springframework.jms.support.converter.MessageConverter;


/**
 * Jms Client Config
 * 
 * @version 1.0.0
 */
@Configuration
public class BrokerRpcConfig extends GenericMQConfig {
    Logger logger = LoggerFactory.getLogger(BrokerRpcConfig.class);
    @Autowired
    private BrokerMessageRepo messageRepo;

    /**
     * 登录RPC服务代理配置
     */
    @Bean
    public JmsInvokerProxyFactoryBean loginRpcServiceProxy() {
        return buildRpcServiceProxyFactoryBean(IMConstant.API_QUEUE_LOGIN_SERVICE, LoginRpcService.class);
    }

    @Bean
    public LoginInterceptHandler loginInterceptHandler(LoginRpcService loginRpcService) {
        return new LoginInterceptHandler(loginRpcService);
    }

    @Bean
    public PublishInterceptHandler publishInterceptHandler(JmsTemplate jmsTemplate) {
        return new PublishInterceptHandler(jmsTemplate, messageRepo);
    }

    @Bean(initMethod = "init")
    public Server mqttServer(LoginInterceptHandler loginInterceptHandler, PublishInterceptHandler publishInterceptHandler) {
        return new Server()
                .setLoginInterceptHandler(loginInterceptHandler)
                .setPublishInterceptHandler(publishInterceptHandler)
                .setBrokerMessageRepo(messageRepo);
    }

    @Bean
    public ImBrokerMessageListener imBrokerMessageListener(Server mqttServer, MessageConverter messageConverter) {
        return new ImBrokerMessageListener(mqttServer, messageConverter);
    }

    /**
     * 配置基于发布订阅模式的消息监听容器,用于接收MQ转发过来的消息
     * 
     * @return
     */
    @Bean
    public MessageListenerContainer mqMessageReceiver(ImBrokerMessageListener imBrokerMessageListener) {
        MessageListenerContainer container = mqMessageReceiver(IMConstant.MQ_FORWARD_TOPIC_NAME, imBrokerMessageListener);
        logger.info("Message listener based on pub-sub mode was created!");
        return container;
    }
}
