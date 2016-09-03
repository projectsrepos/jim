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
package com.jim.im.topic.rpc;

import com.google.common.collect.Maps;
import com.jim.im.api.TopicRpcService;
import com.jim.im.consts.TopicType;
import com.jim.im.db.entity.topic.TopicInfo;
import com.jim.im.db.entity.topic.TopicRequest;
import com.jim.im.exception.TopicRpcException;
import com.jim.im.topic.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * topic rpc 服务提供者
 * 
 * @version 1.0
 */
@Service
public class TopicRpcServiceImpl implements TopicRpcService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TopicRpcService.class);

    @Autowired
    private TopicService topicService;

    @Override
    public Collection<TopicInfo> createTopics(TopicRequest request) throws TopicRpcException {
        try {
            return topicService.createTopic(request.getOperatorId(),
                                            request.getAppId(),
                                            request.getTenantId(),
                                            request.getTopicOwnerType(),
                                            request.getTopicTypes());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new TopicRpcException(e.getMessage(), e);
        }
    }

    @Override
    public List<TopicInfo> deleteTopic(TopicRequest request) throws TopicRpcException {
        try {
            return topicService.deleteTopicInfo(request.getOperatorId(),
                                         request.getAppId(),
                                         request.getTenantId(),
                                         request.getTopicOwnerType(),
                                         request.getTopicTypes());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new TopicRpcException(e.getMessage(), e);
        }
    }

    @Override
    public void subscribeTopic(TopicRequest request, Integer... subscriberIds) throws TopicRpcException {
        try {
            topicService.subscribe(request.getOperatorId(),
                                   request.getAppId(),
                                   request.getTenantId(),
                                   request.getTopicOwnerType(),
                                   request.getTopicTypes(),
                                   subscriberIds);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new TopicRpcException(e.getMessage(), e);
        }
    }

    @Override
    public void unSubscribeTopic(TopicRequest request, Integer... unSubscriberIds) throws TopicRpcException {
        try {
            topicService.unSubscribe(request.getOperatorId(),
                                     request.getAppId(),
                                     request.getTenantId(),
                                     request.getTopicOwnerType(),
                                     request.getTopicTypes(),
                                     unSubscriberIds);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new TopicRpcException(e.getMessage(), e);
        }
    }

    @Override
    public Map<TopicType, TopicInfo> getTopicInfo(TopicRequest request) throws TopicRpcException {
        List<String> topicNames = topicService.invalidTopicNames(request.getOperatorId(),
                                                                 request.getAppId(),
                                                                 request.getTenantId(),
                                                                 request.getTopicOwnerType(),
                                                                 request.getTopicTypes());
        if (CollectionUtils.isEmpty(topicNames))
            throw  new TopicRpcException("The set of topic name can be not empty.");

            List<TopicInfo> infoList = topicService.getTopicInfo(topicNames);
            Map<TopicType, TopicInfo> topicMap = Maps.newHashMap();
            for (TopicInfo topicInfo : infoList)
                topicMap.put(topicInfo.getTopicType(), topicInfo);
            return topicMap;
    }
}
