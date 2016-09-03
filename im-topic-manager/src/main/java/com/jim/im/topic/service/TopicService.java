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
package com.jim.im.topic.service;

import com.jim.im.consts.TopicOwnerType;
import com.jim.im.consts.TopicType;
import com.jim.im.db.entity.topic.TopicInfo;
import com.jim.im.exception.ImParamException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * topic 服务接口
 * 
 * @version 1.0
 */
public interface TopicService {

    /**
     * 获取用户关注过的topic列表
     * 
     * @param userId 用户ID
     * @param appId 应用ID
     * @param tenantId 租户ID
     * @return
     */
    Map<TopicType, List<String>> getSubTopics(Integer userId, String appId, String tenantId) throws ImParamException;

    /**
     * 创建主题
     *
     * @param ownerId 主题所有者ID
     * @param appId 应用ID
     * @param tenantId 租户ID
     * @param topicOwnerType 主题所有者类型 {@link TopicOwnerType}
     * @param topicTypes 主题类型 {@link TopicType}
     * @return
     * @throws ImParamException
     */
    Collection<TopicInfo> createTopic(Integer ownerId,
                                      String appId,
                                      String tenantId,
                                      TopicOwnerType topicOwnerType,
                                      TopicType[] topicTypes) throws ImParamException;

    /**
     * 删除主题
     *
     * 删除主题的时候，一并退订其订阅者
     *
     * @param ownerId 主题所有者ID
     * @param appId 应用ID
     * @param tenantId 租户ID
     * @param topicOwnerType 主题所有者类型 {@link TopicOwnerType}
     * @param topicTypes 主题类型 {@link TopicType}
     * @return
     * @throws ImParamException
     */
    List<TopicInfo> deleteTopicInfo(Integer ownerId,
                         String appId,
                         String tenantId,
                         TopicOwnerType topicOwnerType,
                         TopicType... topicTypes) throws ImParamException;

    /**
     * 订阅主题
     *
     * @param ownerId 主题所有者ID
     * @param appId 应用ID
     * @param tenantId 租户ID
     * @param topicOwnerType 主题所有者类型 {@link TopicOwnerType}
     * @param topicTypes 主题类型 {@link TopicType}
     * @param subscriberIds 订阅者ID
     * @return
     * @throws ImParamException
     */
    void subscribe(Integer ownerId,
                   String appId,
                   String tenantId,
                   TopicOwnerType topicOwnerType,
                   TopicType[] topicTypes,
                   Integer... subscriberIds) throws ImParamException;

    /**
     * 退订
     * <p>
     * <b>注意：</b>退订主题所有者的订阅关系，一定是伴随着用户/群主删除操作
     * </p>
     *
     * @param ownerId 主题所有者ID
     * @param appId 应用ID
     * @param tenantId 租户ID
     * @param topicOwnerType 主题所有者类型 {@link TopicOwnerType}
     * @param topicTypes 主题类型 {@link TopicType}
     * @param unSubscriberIds 退订者ID
     * @return
     * @throws ImParamException
     */
    void unSubscribe(Integer ownerId,
                     String appId,
                     String tenantId,
                     TopicOwnerType topicOwnerType,
                     TopicType[] topicTypes,
                     Integer... unSubscriberIds) throws ImParamException;

    /**
     * 获取用户主拥有的主题信息
     *
     * @param topicNames 主题所有者ID
     * @return
     */
    List<TopicInfo> getTopicInfo(List<String> topicNames);

    /**
     * xx
     * @param ownerId
     * @param appId
     * @param tenantId
     * @param topicOwnerType
     * @param topicTypes
     * @return
     */
    List<String> invalidTopicNames(Integer ownerId,
                                   String appId,
                                   String tenantId,
                                   TopicOwnerType topicOwnerType,
                                   TopicType... topicTypes);
}
