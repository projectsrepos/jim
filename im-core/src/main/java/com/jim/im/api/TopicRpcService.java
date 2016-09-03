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
package com.jim.im.api;

import com.jim.im.consts.TopicType;
import com.jim.im.db.entity.topic.TopicInfo;
import com.jim.im.db.entity.topic.TopicRequest;
import com.jim.im.exception.TopicRpcException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * topic rpc 客户端接口
 * 
 * @version 1.0
 * @since 2016-04-12
 */
public interface TopicRpcService {

    /**
     * 创建主题
     * 
     * @param request {@link TopicRequest}
     * @return
     * @throws TopicRpcException
     */
    Collection<TopicInfo> createTopics(TopicRequest request) throws TopicRpcException;

    /**
     * 删除主题
     *
     * 删除主题的时候，一并退订其订阅者
     * 
     * @param request {@link TopicRequest}
     * @throws TopicRpcException
     */
    List<TopicInfo> deleteTopic(TopicRequest request) throws TopicRpcException;

    /**
     * 订阅主题
     * 
     * @param request {@link TopicRequest}
     * @param subscriberIds 订阅者ID
     * @throws TopicRpcException
     */
    void subscribeTopic(TopicRequest request, Integer... subscriberIds) throws TopicRpcException;

    /**
     * * 退订
     * <p>
     * <b>注意：</b>退订主题所有者的订阅关系，一定是伴随着用户/群主删除操作
     * </p>
     * 
     * @param request {@link TopicRequest}
     * @param unSubscriberIds 退订者ID
     * @throws TopicRpcException
     */
    void unSubscribeTopic(TopicRequest request, Integer... unSubscriberIds) throws TopicRpcException;

    /**
     * 获取用户拥有的主题
     * 
     * @param request {@link TopicRequest}
     * @return
     * @throws TopicRpcException
     */
    Map<TopicType, TopicInfo> getTopicInfo(TopicRequest request) throws TopicRpcException;
}
