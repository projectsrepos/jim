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
package com.jim.im.consts;

/**
 * 
 * @version 1.0.0
 */
public interface IMConstant {

    String MSG_PARAM_ERROR = "参数错误";
    String MSG_INNER_ERROR = "服务器内部错误";
    String MSG_INNER_RUNTIME_ERROR = "服务器内部运行时错误";

    String REQUEST_ID = "requestId";

    String APP_ID = "appId";
    String TENANT_ID = "tenantId";
    String OPERATOR_ID = "operatorId";

    /**
     * 登录服务接口对应的activeMq
     */
    String API_QUEUE_LOGIN_SERVICE = "im.apiQueue.loginService";
    String API_QUEUE_GROUP_SERVICE = "im.apiQueue.groupService";
    String API_QUEUE_TOPIC_QUEUE = "im.apiQueue.topicService";
    String API_QUEUE_MESSAGE_QUEUE = "im.apiQueue.messageService";

    /** ActiveMQ固定主题*/
    String MQ_FORWARD_TOPIC_NAME = "mq.forward.topic";
    String PROPERTIES_TOPIC = "properties.topic";

    String GATEWAY_CLIENTTYPENO_ANDROID = "8001";
    String GATEWAY_CLIENTTYPENO_IOS = "3001";
    
    /**
     * 网关用户注册topic
     */
    String GATEWAY_USERREGISTER_TOPIC ="AccountTopicQueue";

    /** 主题名称组成成员数量，组成规则：[/appId/tenantId/topicOwnerType/targetId/topicType] */
    int TOPIC_NAME_MEMBER_NUM = 5;
    String BROKER_NAME = "mqtt_broker_name";
    String IS_FROM_PUSHER = "is_from_pusher";
}
