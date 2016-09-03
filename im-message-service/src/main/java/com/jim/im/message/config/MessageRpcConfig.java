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

import com.jim.im.api.GroupRpcService;
import com.jim.im.api.MessageRpcService;
import com.jim.im.api.LoginRpcService;
import com.jim.im.api.TopicRpcService;
import com.jim.im.config.GenericMQConfig;
import com.jim.im.consts.IMConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.remoting.JmsInvokerProxyFactoryBean;
import org.springframework.jms.remoting.JmsInvokerServiceExporter;

/**
 * 
 * @version 1.0.0
 */
@Configuration
public class MessageRpcConfig extends GenericMQConfig {
    @Autowired
    private ImMessageListener messageListener;

    /**
     * 群组RPC服务代理配置
     */
    @Bean
    JmsInvokerProxyFactoryBean groupRpcServiceProxy() {
        return buildRpcServiceProxyFactoryBean(IMConstant.API_QUEUE_GROUP_SERVICE, GroupRpcService.class);
    }

    /**
     * 主题RPC服务代理配置
     */
    @Bean
    JmsInvokerProxyFactoryBean topicRpcServiceProxy() {
        return buildRpcServiceProxyFactoryBean(IMConstant.API_QUEUE_TOPIC_QUEUE, TopicRpcService.class);
    }

    /**
     * 登录RPC服务代理配置
     */
    @Bean
    JmsInvokerProxyFactoryBean loginRpcServiceProxy() {
        return buildRpcServiceProxyFactoryBean(IMConstant.API_QUEUE_LOGIN_SERVICE, LoginRpcService.class);
    }

    /**
     * 消息服务暴露接口
     */
    @Bean
    JmsInvokerServiceExporter messageRpcServiceExporter(MessageRpcService messageRpcService) {
        return buildRpcServiceExport(messageRpcService, MessageRpcService.class);
    }

    /**
     * RPC 消息监听容器
     */
    @Bean
    MessageListenerContainer messageListenerContainer(JmsInvokerServiceExporter messageRpcServiceExporter) {
        return buildListenerContainer(messageRpcServiceExporter, IMConstant.API_QUEUE_MESSAGE_QUEUE);
    }

    /**
     * 配置基于发布订阅模式的消息监听容器,用于接收MQ转发过来的消息
     */
    @Bean
    public MessageListenerContainer mqMessageReceiver() {
        MessageListenerContainer container = mqMessageReceiver(IMConstant.MQ_FORWARD_TOPIC_NAME, messageListener);
        return container;
    }
}
