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
package com.jim.im.group.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.remoting.JmsInvokerProxyFactoryBean;
import org.springframework.jms.remoting.JmsInvokerServiceExporter;

import com.jim.im.api.GroupRpcService;
import com.jim.im.api.MessageRpcService;
import com.jim.im.api.TopicRpcService;
import com.jim.im.config.GenericMQConfig;
import com.jim.im.consts.IMConstant;

/**
 * 
 * @author Martin
 * @date 2016年4月8日
 * @version 1.0.0
 */
@Configuration
public class GroupRpcConfig extends GenericMQConfig{

    /**
     * rpc proxy section, proxy
     * */
    @Bean
    JmsInvokerProxyFactoryBean topicRpcServiceProxy() {
        return buildRpcServiceProxyFactoryBean(
   			 IMConstant.API_QUEUE_TOPIC_QUEUE, TopicRpcService.class);
    }
    
    /**
     * MessageRpcService rpc proxy
     * @return
     */
    @Bean
    JmsInvokerProxyFactoryBean messageRpcSeriviceProxy(){
    	return buildRpcServiceProxyFactoryBean(
    			IMConstant.API_QUEUE_MESSAGE_QUEUE,MessageRpcService.class);
    }
    
    /**
     * rpc export section, export
     * */
    @Bean
    JmsInvokerServiceExporter groupRpcServiceExport(GroupRpcService groupRmiService) {
        return buildRpcServiceExport(groupRmiService, GroupRpcService.class);
    }
    
    /**
    * rpc export section, listener
    * */
   @Bean
   MessageListenerContainer groupListenerContainer(JmsInvokerServiceExporter messageListener) {
       return buildListenerContainer(messageListener, IMConstant.API_QUEUE_GROUP_SERVICE);
   }
}
