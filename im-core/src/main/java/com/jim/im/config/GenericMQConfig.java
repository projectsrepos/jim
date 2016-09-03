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
package com.jim.im.config;

import com.jim.im.consts.IMConstant;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.remoting.JmsInvokerProxyFactoryBean;
import org.springframework.jms.remoting.JmsInvokerServiceExporter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;
import javax.jms.Session;

/**
 * 
 * @version 1.0.0
 */
public abstract class GenericMQConfig {

    @Value("${im.mq.brokerUrl}")
    String brokerUrl;

    @Value("${im.mq.pooled}")
    boolean pooled;

    @Value("${im.mq.user}")
    String user;

    @Value("${im.mq.password}")
    String password;

    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    public void setPooled(boolean pooled) {
        this.pooled = pooled;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Bean
    public ConnectionFactory jmsConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, brokerUrl);
        if (pooled) {
            PooledConnectionFactory pool = new PooledConnectionFactory();
            pool.setConnectionFactory(connectionFactory);
            return pool;
        }
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, JmsProperties properties,
            MessageConverter messageConverter, DestinationResolver destinationResolver) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setMessageConverter(messageConverter);
        jmsTemplate.setPubSubDomain(properties.isPubSubDomain());
        jmsTemplate.setDeliveryMode(Session.AUTO_ACKNOWLEDGE);
        if (destinationResolver != null) {
            jmsTemplate.setDestinationResolver(destinationResolver);
        }
        jmsTemplate.setDefaultDestinationName(IMConstant.MQ_FORWARD_TOPIC_NAME);
        return jmsTemplate;
    }

    @Bean
    MessageConverter messageConverter() {
        return new SimpleMessageConverter();
    }

    @Bean
    DestinationResolver destinationResolver() {
        return new DynamicDestinationResolver();
    }

    /**
     * 构建基于队列模式的消息监听容器
     *
     * @param queueName 队列名字
     * @param rpcServiceExport 服务暴露接口
     * @return
     */
    protected MessageListenerContainer buildListenerContainer(JmsInvokerServiceExporter rpcServiceExport,
            String queueName) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(jmsConnectionFactory());
        container.setDestinationName(queueName);
        container.setMessageConverter(messageConverter());
        container.setMessageListener(rpcServiceExport);
        container.setConcurrentConsumers(Runtime.getRuntime().availableProcessors());
        return container;
    }

    /**
     * 配置基于发布订阅模式的消息监听容器,用于接收MQ转发过来的消息
     *
     * @param topicName
     * @param messageListener
     * @return
     */
    public MessageListenerContainer mqMessageReceiver(String topicName, MessageListener messageListener) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(jmsConnectionFactory());
        container.setDestinationName(topicName);
        container.setPubSubDomain(true);
        container.setPubSubNoLocal(true);
        container.setMessageListener(messageListener);
        container.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        return container;
    }

    /**
     * 构建RPC服务代理
     *
     * @param queueName 队列名称
     * @param serviceInterfaceClazz 被代理服务接口
     * @return
     */
    protected JmsInvokerProxyFactoryBean buildRpcServiceProxyFactoryBean(String queueName,
            Class<?> serviceInterfaceClazz) {
        JmsInvokerProxyFactoryBean proxy = new JmsInvokerProxyFactoryBean();
        proxy.setConnectionFactory(jmsConnectionFactory());
        proxy.setMessageConverter(messageConverter());
        proxy.setQueueName(queueName);
        proxy.setServiceInterface(serviceInterfaceClazz);
        return proxy;
    }

    /**
     * 构建RPC服务暴露接口
     * 
     * @param rpcService 服务的实体
     * @param rpcServiceInterfaceClazz
     * @return
     */
    protected JmsInvokerServiceExporter buildRpcServiceExport(Object rpcService, Class<?> rpcServiceInterfaceClazz) {
        JmsInvokerServiceExporter export = new JmsInvokerServiceExporter();
        export.setServiceInterface(rpcServiceInterfaceClazz);
        export.setService(rpcService);
        return export;
    }
}
