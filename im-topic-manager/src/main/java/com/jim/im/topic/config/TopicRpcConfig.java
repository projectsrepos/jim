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
package com.jim.im.topic.config;

import com.jim.im.api.TopicRpcService;
import com.jim.im.config.GenericMQConfig;
import com.jim.im.consts.IMConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.remoting.JmsInvokerServiceExporter;

/**
 * topic rpc 配置
 *
 * @version 1.0
 */
@Configuration
public class TopicRpcConfig extends GenericMQConfig {

    /**
     * 主题服务暴露接口
     */
    @Bean
    JmsInvokerServiceExporter topicRpcServiceExporter(TopicRpcService topicRpcService) {
        return buildRpcServiceExport(topicRpcService, TopicRpcService.class);
    }

    /**
     * RPC 消息监听容器
     */
    @Bean
    MessageListenerContainer topicListenerContainer(JmsInvokerServiceExporter topicRpcServiceExporter) {
        return buildListenerContainer(topicRpcServiceExporter, IMConstant.API_QUEUE_TOPIC_QUEUE);
    }
}
